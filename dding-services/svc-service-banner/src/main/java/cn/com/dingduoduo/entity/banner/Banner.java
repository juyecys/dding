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

    @JsonProperty("bannerGroupId")
    private String bannerGroupId;

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
    @JsonProperty("pictureUrl")
    private String pictureUrl;

    @JsonProperty("bannerGroupCode")
    private String bannerGroupCode;

    public String getBannerGroupId() {
        return bannerGroupId;
    }

    public void setBannerGroupId(String bannerGroupId) {
        this.bannerGroupId = bannerGroupId;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getBannerGroupCode() {
        return bannerGroupCode;
    }

    public void setBannerGroupCode(String bannerGroupCode) {
        this.bannerGroupCode = bannerGroupCode;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerGroupId='" + bannerGroupId + '\'' +
                ", sequence=" + sequence +
                ", url='" + url + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", bannerGroupCode='" + bannerGroupCode + '\'' +
                '}';
    }
}
