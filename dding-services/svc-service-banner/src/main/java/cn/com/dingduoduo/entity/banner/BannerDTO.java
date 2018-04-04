package cn.com.dingduoduo.entity.banner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/4/2.
=======
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/4/8.
>>>>>>> Stashed changes
 */
@Alias("BannerQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BannerDTO extends Banner {
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
        return super.toString() + "BannerDTO{" +
                "delete=" + delete +
                '}';
    }
}
