package cn.com.dingduoduo.service.wechat.qrcode.impl;

import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import cn.com.dingduoduo.entity.wechat.event.WechatSubscribeEvent;
import cn.com.dingduoduo.entity.wechat.qrcode.WechatQRCode;
import cn.com.dingduoduo.entity.wechat.qrcode.WechatQRCodeResult;
import cn.com.dingduoduo.service.wechat.accesstoken.WechatAccessTokenService;
import cn.com.dingduoduo.service.wechat.qrcode.WechatQRCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;

/**
 * Created by jeysine on 2018/1/23.
 */
@Service("wechatQRCodeService")
public class WechatQRCodeServiceImpl implements WechatQRCodeService {
    private static Logger logger = LoggerFactory.getLogger(WechatQRCodeServiceImpl.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private WechatAccessTokenService wechatAccessTokenService;

    @Override
    public WechatQRCodeResult createForverQRCode(WechatQRCode qrCode) throws IOException {
        return createQRCode(qrCode);
    }

    @Override
    public WechatQRCodeResult createTemporaryQRCode(WechatQRCode qrCode) throws IOException {
        return createQRCode(qrCode);
    }

    @Override
    public WechatQRCodeResult createQRCode(WechatQRCode qrCode) throws IOException {
        WechatAccessToken accessToken = wechatAccessTokenService.getAccessToken();
        String url = WechatConfigParams.WECHAT_CREATE_QRCODE_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
        String qrCodeJson = mapper.writeValueAsString(qrCode);
        logger.debug("qrCodeJson:{}",qrCodeJson);
        Response response = OkHttpUtils.postString().content(qrCodeJson).url(url).build().execute();
        WechatQRCodeResult result = mapper.readValue(response.body().string(),WechatQRCodeResult.class);
        return result;
    }

    @Override
    public InputStream getQRCode(String ticket) {
        try {
            ticket = URLEncoder.encode(ticket, "utf-8");
            String  url = WechatConfigParams.WECHAT_GET_QRCODE_URL.replace("TICKET", ticket);
            return OkHttpUtils.get().url(url).build().execute().body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {

        Class<?> clazz = WechatSubscribeEvent.class;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            if(fields != null){
                for (Field field:fields){
                    field.setAccessible(true);
                    System.out.println(field.getName());
                }
            }
        }
    }
}
