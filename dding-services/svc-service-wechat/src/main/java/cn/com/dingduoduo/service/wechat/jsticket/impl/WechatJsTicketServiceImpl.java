package cn.com.dingduoduo.service.wechat.jsticket.impl;

import cn.com.dingduoduo.config.wechat.WechatConfigSecret;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import cn.com.dingduoduo.entity.wechat.jsticket.WechatJsTicket;
import cn.com.dingduoduo.service.wechat.accesstoken.WechatAccessTokenService;
import cn.com.dingduoduo.service.wechat.jsticket.WechatJsTicketService;
import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by jeysine on 2018/3/26.
 */
@Service("wechatJsTicketService")
public class WechatJsTicketServiceImpl implements WechatJsTicketService {

    @Autowired
    private JedisPool jedisPool;

    private static Logger logger = LoggerFactory.getLogger(WechatJsTicketServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WechatAccessTokenService wechatAccessTokenService;

    @Override
    public WechatJsTicket getJsTicket() {
        Jedis jedis = jedisPool.getResource();
        if (jedis == null) {
            logger.error("Redis is not reachable");
        }
        logger.debug("start to get accessToken");

        WechatJsTicket wechatJsTicket = null;
        try {
            do {
                Long ticketExpired = jedis.ttl(WechatConfigParams.JS_TICKET_KEY);
                String ticket = jedis.get(WechatConfigParams.JS_TICKET_KEY);

                if (ticket != null && ticketExpired > 0) {
                    wechatJsTicket =  new WechatJsTicket(ticket, ticketExpired);
                    logger.debug("get js api ticket from redis success, js api ticket:{} expired:{}", ticket, ticketExpired);
                } else {
                    //logger.debug("Redis is not exists access token , start get lock to get access token from cn.com.dingduoduo.api.admin.wechat.");
                    String mutexKeyValue = UUID.randomUUID().toString();
                    if (isLeader(jedis, mutexKeyValue)) {
                        wechatJsTicket = getJsTicketFromWechat();
                    }
                }
            } while (wechatJsTicket == null);
        } finally {
            jedis.close();
        }
        return wechatJsTicket;
    }

    private Boolean isLeader(Jedis jedis, String mutexKeyValue) {
        Long mutexKeyExpired = 10L;
        String existsMutexKeyValue = jedis.get(WechatConfigParams.ACCESS_TOKEN_KEY_MUTEX);

        //防止某线程或进程第一次获得leader但获取或设置accessToken失败而进行第二次设置
        if (existsMutexKeyValue != null && mutexKeyValue.equals(existsMutexKeyValue)) {
            logger.debug("set access token not need lock");
            return true;
        } else if (existsMutexKeyValue != null){
            logger.debug("get leader failed");
            return false;
        }

        String result = jedis.set(WechatConfigParams.JS_TICKET_KEY_MUTEX, mutexKeyValue, "NX", "EX", mutexKeyExpired);
        if (result != null) {
            logger.debug("get leader success");
            return true;
        } else {
            logger.debug("get leader failed");
        }
        return false;
    }

    private WechatJsTicket getJsTicketFromWechat()  {
        String url = WechatConfigParams.WECHAT_GET_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", wechatAccessTokenService.getAccessToken().getAccessToken());

        WechatJsTicket wechatJsTicket = null;
        logger.debug("get js api ticket from wechat start:{}", url);
        try {
            String result = OkHttpUtils.get().url(url)
                    .build().execute().body().string();
            wechatJsTicket = mapper.readValue(result, WechatJsTicket.class);
            logger.debug("get js api ticket from wechat success:{}", wechatJsTicket);
            if (wechatJsTicket.getTicket() == null) {
                logger.error("get js api ticket from wechat error, err_code:{},err_msg:{}", wechatJsTicket.getErrcode(),wechatJsTicket.getErrmsg());
                return null;
            }

            wechatJsTicket.setExpiresIn(
                    wechatJsTicket.getExpiresIn());
            logger.debug("get wechat js api ticket success, js api ticket:{}, accessToken expire time: {}", wechatJsTicket.getTicket(), wechatJsTicket.getExpiresIn());
            setJsTicketToRedis(wechatJsTicket);
        } catch (IOException e) {
            logger.error("visit wechat get js api ticket failed:{}", e);
        }


        return wechatJsTicket;
    }

    private void setJsTicketToRedis(WechatJsTicket wechatJsTicket) throws IOException {
        logger.debug("set js api ticket to redis start.");
        Jedis jedis = jedisPool.getResource();
        Long ticketExpiredIn = wechatJsTicket.getExpiresIn() - 60L;
        if (ticketExpiredIn < 0) {
            return;
        }
        jedis.del(WechatConfigParams.JS_TICKET_KEY);
        String result = jedis.set(WechatConfigParams.JS_TICKET_KEY, wechatJsTicket.getTicket(), "NX", "EX", ticketExpiredIn);
        logger.debug("result:{}",result);
        if (result != null) {
            logger.debug("set js api ticket to redis success, accessToken:{}, expired: {}.", wechatJsTicket.getTicket(),ticketExpiredIn);
            jedis.del(WechatConfigParams.JS_TICKET_KEY_MUTEX);
        } else {
            logger.debug("js api ticket had saved.");
        }
    }
}
