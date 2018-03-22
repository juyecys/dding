package cn.com.dingduoduo.api.wechat.event;

import cn.com.dingduoduo.utils.common.Dom4jUtils;
import cn.com.dingduoduo.service.wechat.event.WechatEventService;
import cn.com.dingduoduo.untils.wechat.WechatSignUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


@RestController
@RequestMapping(value = { "/ding/wechat/public/event" }, produces = "application/json")
public class PublicWechatEventController {


    @Autowired
    private WechatEventService wechatEventService;

    private static Logger logger = LoggerFactory.getLogger(PublicWechatEventController.class);

    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = "application/xml")
    public void receiveMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 接收参数微信加密签名、 时间戳、随机数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        InputStream inputStream = request.getInputStream();

        PrintWriter out = response.getWriter();

        try {
            HashMap<String, Object> data = Dom4jUtils.readXml(inputStream);
            if ("event".equals(data.get("MsgType"))) {
                wechatEventService.processEvent(data);
            } else {

            }
            logger.debug("微信事件处理结束");
            out.print("");
        } catch (DocumentException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("read xml error: {}", e);
        } catch (Exception e) {
            logger.error("error: {}", e);
        }
        out.close();
        out = null;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET,  produces = "application/json")
    public void receive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WechatSignUtil.checkUserMessageSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }
}
