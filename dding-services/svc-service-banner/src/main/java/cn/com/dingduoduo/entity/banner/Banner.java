package cn.com.dingduoduo.entity.banner;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/3/29.
 */
@Alias("BannerM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Banner extends Base {

    @JsonProperty("location")
    private String location;

    @JsonProperty("code")
    private String code;

    @JsonProperty("type")
    private String type;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("sequence")
    private Integer sequence;

    /**
     * 跳转url
     */
    @JsonProperty("url")
    private String url;


    /**
     * 图片url
     */
    @JsonProperty("bannerUrl")
    private String bannerUrl;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "location='" + location + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", sequence=" + sequence +
                ", url='" + url + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                '}';
    }
}
