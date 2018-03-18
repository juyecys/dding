package cn.com.dingduoduo.entity.audio;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
@Alias("AudioLectureGroupM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioLectureGroup extends Base{

    @JsonProperty("courseId")
    private String courseId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("isExist")
    private Boolean isExist;



    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }


    @Override
    public String toString() {
        return "AudioLectureGroup{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", sequence=" + sequence +
                ", isExist=" + isExist +
                '}';
    }
}
