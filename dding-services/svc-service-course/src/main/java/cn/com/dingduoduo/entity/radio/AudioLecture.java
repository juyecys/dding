package cn.com.dingduoduo.entity.radio;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/3/16.
 */
@Alias("RadioLectureM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioLecture extends Base{
    private String courseId;

    /**
     * 分类id
     */
    private String groupId;

    private String name;

    /**
     * 序列号
     */
    private Integer sequence;

    private String audioUrl;

    private String audioName;

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
