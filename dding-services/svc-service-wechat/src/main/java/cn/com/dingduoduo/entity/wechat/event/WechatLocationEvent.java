package cn.com.dingduoduo.entity.wechat.event;

/**
 * Created by jeysine on 2018/1/24.
 */
public class WechatLocationEvent extends WechatBaseEvent {

    private String latitude;

    private String longitude;

    private String precision;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}
