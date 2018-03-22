package cn.com.dingduoduo.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by jeysine on 2018/2/9.
 */
@Document(collection="dding_message")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable{
    private static final long serialVersionUID = -8818767389760162084L;
    @Id
    private String id;
    /**
     * 消息类型
     */
    @JsonProperty("msg_type")
    private String msgType;

    @JsonProperty("content")
    private String content;

    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("thumb_media_id")
    private String thumbMediaId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("music_url")
    private String musicUrl;

    @JsonProperty("hq_music_url")
    private String hqMusicUrl;

    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("qr_code_scene")
    private String qrCodeScene;

    @JsonProperty("keyWordId")
    private String keyWordId;

    @JsonProperty("article_list")
    private List<Article> articleList;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonProperty("updated_date")
    private Date updatedDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    public enum TypeEnum {
        CHANNEL,
        SUBSCRIBE,
        KEY_WORD
    }

    public enum MsgTypeEnum {
        IMAGE("image"),
        VOICE("voice"),
        VIDEO("video"),
        TEXT("text"),
        MUSIC("music"),
        ARTICLE_LIST("article_list"),
        ARTICLE("article"),
        MPNEWS("mpnews"),
        WXCARD("wxcard");

        private String value;

        MsgTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static MsgTypeEnum getEnumByValue(String value) {
            for (MsgTypeEnum e: MsgTypeEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
            return null;
        }
    }

    public String getKeyWordId() {
        return keyWordId;
    }

    public void setKeyWordId(String keyWordId) {
        this.keyWordId = keyWordId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }


    public String getQrCodeScene() {
        return qrCodeScene;
    }

    public void setQrCodeScene(String qrCodeScene) {
        this.qrCodeScene = qrCodeScene;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cardId='" + cardId + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", hqMusicUrl='" + hqMusicUrl + '\'' +
                ", sequence=" + sequence +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", qrCodeScene='" + qrCodeScene + '\'' +
                ", keyWordId='" + keyWordId + '\'' +
                ", articleList=" + articleList +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
