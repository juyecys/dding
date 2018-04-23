package cn.com.dingduoduo.entity.courseorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by jeysine on 2018/4/18.
 */
@Alias("CourseOrderQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseOrderDTO extends CourseOrder {

    @JsonProperty("userNickName")
    private String userNickName;

    @JsonProperty("doctorName")
    private String doctorName;

    @JsonProperty("hospitalName")
    private String hospitalName;

    @JsonProperty("doctorTitle")
    private String doctorTitle;

    @JsonProperty("createdDateStart")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createdDateStart;

    @JsonProperty("createdDateEnd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createdDateEnd;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    @Override
    public String toString() {
        return "CourseOrderDTO{" +
                "userNickName='" + userNickName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", doctorTitle='" + doctorTitle + '\'' +
                ", createdDateStart=" + createdDateStart +
                ", createdDateEnd=" + createdDateEnd +
                '}';
    }
}
