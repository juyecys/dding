package cn.com.dingduoduo.entity.banner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by jeysine on 2018/4/2.
 */
@Alias("BannerGroupQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BannerGroupDTO extends BannerGroup{
    @JsonProperty("bannerList")
    private List<BannerDTO> bannerList;

    public List<BannerDTO> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerDTO> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public String toString() {
        return "BannerGroupDTO{" +
                "bannerList=" + bannerList +
                '}';
    }
}
