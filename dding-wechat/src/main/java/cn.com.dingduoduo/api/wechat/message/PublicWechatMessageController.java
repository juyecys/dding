package cn.com.dingduoduo.api.wechat.message;


import cn.com.dingduoduo.utils.common.Dom4jUtils;
import cn.com.dingduoduo.service.wechat.event.WechatEventService;
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
@RequestMapping(value = { "/ding/wechat/public/message" })
public class PublicWechatMessageController {

    @Autowired
    private WechatEventService wechatEventService;

    private static Logger logger = LoggerFactory.getLogger(PublicWechatMessageController.class);

    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = "application/xml")
    public String receiveMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 接收参数微信加密签名、 时间戳、随机数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        PrintWriter out = response.getWriter();
        InputStream inputStream = request.getInputStream();


        try {
            HashMap<String, Object> data = Dom4jUtils.readXml(inputStream);
            logger.debug("recive wechat event xml data: {}", data);
            wechatEventService.processEvent(data);
        } catch (DocumentException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("read xml error: {}", e);
        } catch (Exception e) {
            logger.error("not find this channel qrcode: {}", e);
        }
        return "";
    }
}
