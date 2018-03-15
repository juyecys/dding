package cn.com.dingduoduo.entity.wechat.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;

import java.util.List;

/**
 * Created by jeysine on 2018/2/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WechatUserDTO extends WechatCommonResult {
    @JsonProperty("user_info_list")
    private List<WechatUser> userInfoList;

    public List<WechatUser> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<WechatUser> userInfoList) {
        this.userInfoList = userInfoList;
    }

    @Override
    public String toString() {
        return super.toString() + "WechatUserDTO{" +
                "userInfoList=" + userInfoList +
                '}';
    }
}
