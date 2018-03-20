package cn.com.dingduoduo.entity.audio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

/**
 * Created by jeysine on 2018/3/16.
 */
@Alias("AudioLectureQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioLectureDTO extends AudioLecture{

    @Transient
    @JsonProperty("delete")
    private Boolean delete;

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return super.toString() + "AudioLectureDTO{" +
                "delete=" + delete +
                '}';
    }
}
