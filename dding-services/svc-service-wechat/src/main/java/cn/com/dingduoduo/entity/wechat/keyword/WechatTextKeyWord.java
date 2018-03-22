package cn.com.dingduoduo.entity.wechat.keyword;

/**
 * Created by jeysine on 2018/3/22.
 */
public class WechatTextKeyWord extends WechatBaseKeyWord {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return super.toString() + "WechatTextKeyWord{" +
                "content='" + content + '\'' +
                '}';
    }
}
