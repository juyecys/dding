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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseOrderDTO extends CourseOrder {

    @JsonProperty("userNickName")
    private String userNickName;

    @JsonProperty("doctorName")
    private String doctorName;

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

    @Override
    public String toString() {
        return "CourseOrderDTO{" +
                "userNickName='" + userNickName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", createdDateStart=" + createdDateStart +
                ", createdDateEnd=" + createdDateEnd +
                '}';
    }
}