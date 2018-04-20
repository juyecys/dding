package cn.com.dingduoduo.entity.courseorder;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jeysine on 2018/4/18.
 */
@Alias("CourseOrderM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseOrder extends Base {
    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("openId")
    private String openId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("courseId")
    private String courseId;

    /* 购买渠道 */
    @JsonProperty("source")
    private String source;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("effectiveDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveDate;

    @JsonProperty("courseName")
    private String courseName;

    @JsonProperty("status")
    private String status;

    public enum CourseOrderTypeEnum {
        COURSE("课程"), MEMBER("会员");

        private String value;

        CourseOrderTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum CourseOrderStatusEnum {
        WAIT_PAID, PAID
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseOrder{" +
                "orderNumber='" + orderNumber + '\'' +
                ", openId='" + openId + '\'' +
                ", type='" + type + '\'' +
                ", courseId='" + courseId + '\'' +
                ", source='" + source + '\'' +
                ", price=" + price +
                ", effectiveDate=" + effectiveDate +
                ", courseName='" + courseName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
