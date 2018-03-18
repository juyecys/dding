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
    @JsonProperty("lectureGroupList")
    private List<AudioLectureGroupDTO> lectureGroupList;

    public List<AudioLectureGroupDTO> getLectureGroupList() {
        return lectureGroupList;
    }

    public void setLectureGroupList(List<AudioLectureGroupDTO> lectureGroupList) {
        this.lectureGroupList = lectureGroupList;
    }

    @Override
    public String toString() {
        return super.toString() + "AudioCourseDTO{" +
                "lectureGroupList=" + lectureGroupList +
                '}';
    }
}
