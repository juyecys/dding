package cn.com.dingduoduo.entity.wechat.material;

import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by jeysine on 2018/2/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY )
public class WechatMaterial extends WechatCommonResult{

    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("type")
    private String type;

    @JsonProperty("media")
    private File media;

    public static enum TypeEnum {
        IMAGE("image"), VOICE("voice"),
        VIDEO("video"), THUMB("thumb");

        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getMedia() {
        return media;
    }

    public void setMedia(File media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "WechatMaterial{" +
                "mediaId='" + mediaId + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
