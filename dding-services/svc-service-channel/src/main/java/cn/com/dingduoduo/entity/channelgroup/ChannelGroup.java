package cn.com.dingduoduo.entity.channelgroup;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/2/21.
 */
@Alias("ChannelGroupM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY )
public class ChannelGroup extends Base {
    @JsonProperty("remark")
    private String remark;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("name")
    private String name;

    @JsonProperty("scene")
    private String scene;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    @Override
    public String toString() {
        return "ChannelGroup{" +
                "remark='" + remark + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", scene='" + scene + '\'' +
                '}';
    }
}
