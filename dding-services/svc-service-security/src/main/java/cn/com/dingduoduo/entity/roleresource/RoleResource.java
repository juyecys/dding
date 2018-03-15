package cn.com.dingduoduo.entity.roleresource;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleResource extends Base implements Serializable {
    private static final long serialVersionUID = 3398623423650797577L;

    @JsonProperty("roleId")
    private String roleId;

    @JsonProperty("resourceId")
    private String resourceId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "RoleResource{" +
                "roleId='" + roleId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                '}';
    }
}
