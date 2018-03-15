package cn.com.dingduoduo.untils.wechat;

import cn.com.dingduoduo.entity.channel.Channel;
import cn.com.dingduoduo.entity.wechat.qrcode.WechatQRCode;

/**
 * Created by jeysine on 2018/1/25.
 */
public class WechatQRCodeUtils {
    public static WechatQRCode getForeverQRCode(Channel channel) {
        WechatQRCode wechatQRCode = new WechatQRCode();
        WechatQRCode.ActionInfo actionInfo = wechatQRCode.new ActionInfo();

        WechatQRCode.Scene scene = wechatQRCode.new Scene();
        scene.setSceneStr(channel.getScene());
        actionInfo.setScene(scene);

        wechatQRCode.setActionName(WechatQRCode.ActionNameEnum.QR_LIMIT_STR_SCENE.name());
        wechatQRCode.setActionInfo(actionInfo);
        return wechatQRCode;
    }
}
