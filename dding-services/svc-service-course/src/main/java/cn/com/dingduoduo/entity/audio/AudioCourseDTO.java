package cn.com.dingduoduo.entity.audio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

@Alias("AudioCourseQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioCourseDTO extends AudioCourse {
    @Transient
    @JsonProperty("audioLectureGroupList")
    private List<AudioLectureGroupDTO> audioLectureGroupList;

    @Transient
    @JsonProperty("audioAnswerList")
    private List<AudioAnswer> audioAnswerList;


    @JsonProperty("sequenceStart")
    private Integer sequenceStart;

    @JsonProperty("sequenceEnd")
    private Integer sequenceEnd;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonProperty("createdDateStart")
    private Date createdDateStart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonProperty("createdDateEnd")
    private Date createdDateEnd;

    @JsonProperty("buy")
    private Boolean buy;

    @JsonProperty("openId")
    private String openId;

    @JsonProperty("courseOrderStatus")
    private String courseOrderStatus;


    public List<AudioLectureGroupDTO> getAudioLectureGroupList() {
        return audioLectureGroupList;
    }

    public void setAudioLectureGroupList(List<AudioLectureGroupDTO> audioLectureGroupList) {
        this.audioLectureGroupList = audioLectureGroupList;
    }

    public List<AudioAnswer> getAudioAnswerList() {
        return audioAnswerList;
    }

    public void setAudioAnswerList(List<AudioAnswer> audioAnswerList) {
        this.audioAnswerList = audioAnswerList;
    }

    public Integer getSequenceStart() {
        return sequenceStart;
    }

    public void setSequenceStart(Integer sequenceStart) {
        this.sequenceStart = sequenceStart;
    }

    public Integer getSequenceEnd() {
        return sequenceEnd;
    }

    public void setSequenceEnd(Integer sequenceEnd) {
        this.sequenceEnd = sequenceEnd;
    }

    public Date getCreatedDateStart() {
        return createdDateStart;
    }

    public void setCreatedDateStart(Date createdDateStart) {
        this.createdDateStart = createdDateStart;
    }

    public Date getCreatedDateEnd() {
        return createdDateEnd;
    }

    public void setCreatedDateEnd(Date createdDateEnd) {
        this.createdDateEnd = createdDateEnd;
    }

    public Boolean getBuy() {
        return buy;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCourseOrderStatus() {
        return courseOrderStatus;
    }

    public void setCourseOrderStatus(String courseOrderStatus) {
        this.courseOrderStatus = courseOrderStatus;
    }

    @Override
    public String toString() {
        return super.toString() + "AudioCourseDTO{" +
                "audioLectureGroupList=" + audioLectureGroupList +
                ", audioAnswerList=" + audioAnswerList +
                ", sequenceStart=" + sequenceStart +
                ", sequenceEnd=" + sequenceEnd +
                ", createdDateStart=" + createdDateStart +
                ", createdDateEnd=" + createdDateEnd +
                ", buy=" + buy +
                ", openId='" + openId + '\'' +
                ", courseOrderStatus='" + courseOrderStatus + '\'' +
                '}';
    }
}
