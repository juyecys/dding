package cn.com.dingduoduo.entity.resource;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/17.
 */
@Alias("ResourceM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource extends Base implements Serializable {
    private static final long serialVersionUID = 9107301064993142032L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("resource")
    private String resource;
    @JsonProperty("description")
    private String description;

    public static enum TypeEnum {
        MENU
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Resource{" +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", resource='" + resource + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
