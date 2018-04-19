package cn.com.dingduoduo.entity.audio;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/3/16.
 */
@Alias("AudioAnswerM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioAnswer extends Base {
    @JsonProperty("courseId")
    private String courseId;

    /**
     * 序列号
     */
    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("question")
    private String question;

    @JsonProperty("audioUrl")
    private String audioUrl;

    @JsonProperty("audioName")
    private String audioName;

    @JsonProperty("audioTimestamp")
    private Long audioTimestamp;

    @JsonProperty("freeListen")
    private Boolean freeListen;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public Long getAudioTimestamp() {
        return audioTimestamp;
    }

    public void setAudioTimestamp(Long audioTimestamp) {
        this.audioTimestamp = audioTimestamp;
    }

    public Boolean getFreeListen() {
        return freeListen;
    }

    public void setFreeListen(Boolean freeListen) {
        this.freeListen = freeListen;
    }

    @Override
    public String toString() {
        return "AudioAnswer{" +
                "courseId='" + courseId + '\'' +
                ", sequence=" + sequence +
                ", question='" + question + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", audioName='" + audioName + '\'' +
                ", audioTimestamp=" + audioTimestamp +
                ", freeListen=" + freeListen +
                '}';
    }
}
