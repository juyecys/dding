package cn.com.dingduoduo.entity.wechat.qrcode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/1/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatQRCode {

    @JsonProperty("expire_seconds")
    private Long expireSeconds;

    @JsonProperty("action_name")
    private String actionName;

    @JsonProperty("action_info")
    private ActionInfo actionInfo;

    public static enum ActionNameEnum {
        QR_LIMIT_STR_SCENE,//永久二维码 编码字符串
        QR_LIMIT_SCENE, //永久二维码 编码id
        QR_STR_SCENE,//临时二维码 编码字符串
        QR_SCENE //临时二维码 编码id
    }

    public class ActionInfo {
        @JsonProperty("scene")
        private Scene scene;

        public Scene getScene() {
            return scene;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }
    }

    public class Scene {
        @JsonProperty("scene_str")
        private String sceneStr;
        @JsonProperty("scene_id")
        private String sceneId;

        public String getSceneStr() {
            return sceneStr;
        }

        public void setSceneStr(String sceneStr) {
            this.sceneStr = sceneStr;
        }

        public String getSceneId() {
            return sceneId;
        }

        public void setSceneId(String sceneId) {
            this.sceneId = sceneId;
        }
    }

    public Long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }
}
