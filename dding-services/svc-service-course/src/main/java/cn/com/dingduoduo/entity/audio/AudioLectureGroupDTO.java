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
    @JsonProperty("audioLectureList")
    private List<AudioLecture> audioLectureList;

    public List<AudioLecture> getAudioLectureList() {
        return audioLectureList;
    }

    public void setAudioLectureList(List<AudioLecture> audioLectureList) {
        this.audioLectureList = audioLectureList;
    }

    @Override
    public String toString() {
        return super.toString() + "AudioLectureGroupDTO{" +
                "audioLectureList=" + audioLectureList +
                '}';
    }
}
