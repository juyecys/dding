package cn.com.dingduoduo.entity.audio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Alias("AudioLectureGroupQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioLectureGroupDTO extends AudioLectureGroup {
    @Transient
    @JsonProperty("delete")
    private Boolean delete;

    @Transient
    @JsonProperty("audioLectureList")
    private List<AudioLectureDTO> audioLectureList;

    public List<AudioLectureDTO> getAudioLectureList() {
        return audioLectureList;
    }

    public void setAudioLectureList(List<AudioLectureDTO> audioLectureList) {
        this.audioLectureList = audioLectureList;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "AudioLectureGroupDTO{" +
                "delete=" + delete +
                ", audioLectureList=" + audioLectureList +
                '}';
    }
}
