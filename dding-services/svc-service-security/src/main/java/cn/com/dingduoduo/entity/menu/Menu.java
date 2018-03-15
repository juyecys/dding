package cn.com.dingduoduo.entity.menu;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/1/23.
 */
@Alias("MenuM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Menu extends Base {
    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("isParentMenu")
    private Boolean isParentMenu;

    @JsonProperty("resourceId")
    private String resourceId;

    @JsonProperty("resource")
    private String resource;

    @JsonProperty("parentMenuCode")
    private String parentMenuCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getParentMenuCode() {
        return parentMenuCode;
    }

    public void setParentMenuCode(String parentMenuCode) {
        this.parentMenuCode = parentMenuCode;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sequence=" + sequence +
                ", isParentMenu=" + isParentMenu +
                ", resourceId='" + resourceId + '\'' +
                ", resource='" + resource + '\'' +
                ", parentMenuCode='" + parentMenuCode + '\'' +
                '}';
    }
}