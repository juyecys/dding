package cn.com.dingduoduo.entity.audio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

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

    @Override
    public String toString() {
        return super.toString() + "AudioCourseDTO{" +
                "audioLectureGroupList=" + audioLectureGroupList +
                ", audioAnswerList=" + audioAnswerList +
                '}';
    }
}
