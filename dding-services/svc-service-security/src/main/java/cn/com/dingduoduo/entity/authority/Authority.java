package cn.com.dingduoduo.entity.authority;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 权限类
 */
@Alias("AuthorityM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Authority extends Base implements Serializable{

    private static final long serialVersionUID = 1994186453740994244L;

    @JsonProperty("sequence")
    private String resourceId;

    @JsonProperty("roleId")
    private String roleId;
    /**
     * 是否为父级菜单
     */
    @JsonProperty("isParentMenu")
    private Boolean isParentMenu;

    @JsonProperty("menuCode")
    private String menuCode;

    @JsonProperty("parentMenuCode")
    private String parentMenuCode;

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

    public Boolean getParentMenu() {
        return isParentMenu;
    }

    public void setParentMenu(Boolean parentMenu) {
        isParentMenu = parentMenu;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getParentMenuCode() {
        return parentMenuCode;
    }

    public void setParentMenuCode(String parentMenuCode) {
        this.parentMenuCode = parentMenuCode;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "resourceId='" + resourceId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", isParentMenu=" + isParentMenu +
                ", menuCode='" + menuCode + '\'' +
                ", parentMenuCode='" + parentMenuCode + '\'' +
                '}';
    }
}
