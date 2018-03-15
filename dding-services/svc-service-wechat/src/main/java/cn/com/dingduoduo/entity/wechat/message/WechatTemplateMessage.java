package cn.com.dingduoduo.entity.wechat.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/3/8.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WechatTemplateMessage {
    @JsonProperty("touser")
    private String touser;

    @JsonProperty("template_id")
    private String templateId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("miniprogram")
    private String miniProgram;

    @JsonProperty("data")
    private Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class Data {
        @JsonProperty("first")
        private DataContent first;

        @JsonProperty("keyword1")
        private DataContent keyword1;

        @JsonProperty("keyword2")
        private DataContent keyword2;

        @JsonProperty("keyword3")
        private DataContent keyword3;

        @JsonProperty("remark")
        private DataContent remark;

        public DataContent getFirst() {
            return first;
        }

        public void setFirst(DataContent first) {
            this.first = first;
        }

        public DataContent getKeyword1() {
            return keyword1;
        }

        public void setKeyword1(DataContent keyword1) {
            this.keyword1 = keyword1;
        }

        public DataContent getKeyword2() {
            return keyword2;
        }

        public void setKeyword2(DataContent keyword2) {
            this.keyword2 = keyword2;
        }

        public DataContent getKeyword3() {
            return keyword3;
        }

        public void setKeyword3(DataContent keyword3) {
            this.keyword3 = keyword3;
        }

        public DataContent getRemark() {
            return remark;
        }

        public void setRemark(DataContent remark) {
            this.remark = remark;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class DataContent {
        @JsonProperty("value")
        private String value;

        @JsonProperty("color")
        private final String color = "#459ae9";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMiniProgram() {
        return miniProgram;
    }

    public void setMiniProgram(String miniProgram) {
        this.miniProgram = miniProgram;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
