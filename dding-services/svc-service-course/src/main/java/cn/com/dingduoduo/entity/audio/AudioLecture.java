package cn.com.dingduoduo.entity.audio;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/3/16.
 */
@Alias("AudioLectureM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioLecture extends Base{

    @JsonProperty("courseId")
    private String courseId;

    /**
     * 分类id
     */
    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("name")
    private String name;

    /**
     * 序列号
     */
    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("audioUrl")
    private String audioUrl;

    @JsonProperty("audioName")
    private String audioName;

    @JsonProperty("audioTimestamp")
    private Long audioTimestamp;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    @Override
    public String toString() {
        return "AudioLecture{" +
                "courseId='" + courseId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", sequence=" + sequence +
                ", audioUrl='" + audioUrl + '\'' +
                ", audioName='" + audioName + '\'' +
                ", audioTimestamp=" + audioTimestamp +
                '}';
    }
}
