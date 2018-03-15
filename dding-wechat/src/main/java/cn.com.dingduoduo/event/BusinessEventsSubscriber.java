package cn.com.dingduoduo.event;

import cn.com.dingduoduo.contants.wechat.WechatEventConstant;
import cn.com.dingduoduo.event.listener.wechat.WechatScanQRCodeEventListener;
import cn.com.dingduoduo.event.listener.wechat.WechatUserSubscribeEventListener;
import cn.com.dingduoduo.event.listener.wechat.WechatUserUnSubscribeEventListener;
import cn.com.dingduoduo.service.event.EventServiceException;
import cn.com.dingduoduo.service.event.EventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Administrator on 2016/4/27.
 */
@Component
public class BusinessEventsSubscriber {
	private static Logger logger = LoggerFactory.getLogger(BusinessEventsSubscriber.class);

	@Autowired
	private EventServiceImpl eventService;

	@Autowired
	private WechatUserSubscribeEventListener wechatUserSubscribeEventListener;

	@Autowired
	private WechatUserUnSubscribeEventListener wechatUserUnSubscribeEventListener;

	@Autowired
	private WechatScanQRCodeEventListener wechatScanQRCodeEventListener;

	@PostConstruct
	public void init() {
		try {
			eventService.subscribe(WechatEventConstant.EVENT_TYPE_WECHAT_USER_SUBSCRIBE, wechatUserSubscribeEventListener);
			eventService.subscribe(WechatEventConstant.EVENT_TYPE_WECHAT_USER_UNSUBSCRIBE, wechatUserUnSubscribeEventListener);
			//eventService.subscribe(WechatEventConstant.EVENT_TYPE_WECHAT_SCAN_QR_CODE, wechatScanQRCodeEventListener);
		} catch (EventServiceException e) {
			logger.error("订阅业务事件失败！！ ", e);
			throw new IllegalStateException();
		}
	}

	@PreDestroy
	public void destroy() {
	}

}
