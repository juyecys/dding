package cn.com.dingduoduo.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("UserQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO extends User implements Serializable {
    private static final long serialVersionUID = 3930220600362884579L;

    @JsonProperty("roleId")
    private String roleId;

    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("roleDescription")
    private String roleDescription;

    @JsonProperty("roleIsActive")
    private Boolean roleIsActive;

    @JsonProperty("roleCode")
    private String roleCode;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Boolean getRoleIsActive() {
        return roleIsActive;
    }

    public void setRoleIsActive(Boolean roleIsActive) {
        this.roleIsActive = roleIsActive;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleIsActive=" + roleIsActive +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }
}
