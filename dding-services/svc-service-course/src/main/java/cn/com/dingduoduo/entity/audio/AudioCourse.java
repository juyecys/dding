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
@Alias("AudioCourseM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioCourse extends Base {
    @JsonProperty("name")
    private String name;

    @JsonProperty("introduction")
    private String introduction;

    @JsonProperty("pictureUrl")
    private String pictureUrl;

    @JsonProperty("peopleCount")
    private Long peopleCount;

    @JsonProperty("doctorName")
    private String doctorName;

    @JsonProperty("hospitalName")
    private String hospitalName;

    @JsonProperty("doctorTitle")
    private String doctorTitle;

    @JsonProperty("doctorHeadImg")
    private String doctorHeadImg;
    /**
     * 名医讲座
     */
    @JsonProperty("isLecture")
    private Boolean isLecture;

    /**
     * 名医解答
     */
    @JsonProperty("isSolution")
    private Boolean isSolution;

    /**
     * 课程总时长
     */
    @JsonProperty("totalTime")
    private Long totalTime;

    /**
     * 开启关闭
     */
    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("sequence")
    private Integer sequence;

    /**
     * 尾部html
     */
    @JsonProperty("tail")
    private String tail;

    /**
     * 头部html
     */
    @JsonProperty("head")
    private String head;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Long getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Long peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getDoctorHeadImg() {
        return doctorHeadImg;
    }

    public void setDoctorHeadImg(String doctorHeadImg) {
        this.doctorHeadImg = doctorHeadImg;
    }

    public Boolean getLecture() {
        return isLecture;
    }

    public void setLecture(Boolean lecture) {
        isLecture = lecture;
    }

    public Boolean getSolution() {
        return isSolution;
    }

    public void setSolution(Boolean solution) {
        isSolution = solution;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "AudioCourse{" +
                "name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", peopleCount=" + peopleCount +
                ", doctorName='" + doctorName + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", doctorTitle='" + doctorTitle + '\'' +
                ", doctorHeadImg='" + doctorHeadImg + '\'' +
                ", isLecture=" + isLecture +
                ", isSolution=" + isSolution +
                ", totalTime=" + totalTime +
                ", status=" + status +
                ", sequence=" + sequence +
                '}';
    }
}
