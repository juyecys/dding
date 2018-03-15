package cn.com.dingduoduo.entity.wechat.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jeysine on 2018/2/8.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WechatUser {
    /**
     * 0表示用户没有关注该公众号,1表示有
     */
    @JsonProperty("subscribe")
    private Integer subscribe;
    /**
     *
     */
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("nickname")
    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @JsonProperty("sex")
    private Integer sex;
    /**
     * 用户的语言
     */
    @JsonProperty("language")
    private String language;
    @JsonProperty("city")
    private String city;
    @JsonProperty("province")
    private String province;
    @JsonProperty("country")
    private String country;
    /**
     * 用户头像
     */
    @JsonProperty("headimgurl")
    private String headImgUrl;
    /**
     * 关注时间
     */
    @JsonProperty("subscribe_time")
    private String subscribeTime;
    /**
     * 公众号绑定到微信开放平台,才有该字段
     */
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("remark")
    private String remark;
    /**
     * 用户所在的分组id
     */
    @JsonProperty("groupid")
    private String groupId;
    /**
     * 用户被打上的标签ID列表
     */
    @JsonProperty("tagid_list")
    private List<Integer> tagidList;

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<Integer> tagidList) {
        this.tagidList = tagidList;
    }

    @Override
    public String toString() {
        return "WechatUser{" +
                "subscribe=" + subscribe +
                ", openId='" + openId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", subscribeTime='" + subscribeTime + '\'' +
                ", unionId='" + unionId + '\'' +
                ", remark='" + remark + '\'' +
                ", groupId='" + groupId + '\'' +
                ", tagidList=" + tagidList +
                '}';
    }
}
