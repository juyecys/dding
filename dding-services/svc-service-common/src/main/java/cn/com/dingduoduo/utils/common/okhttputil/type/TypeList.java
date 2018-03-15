package cn.com.dingduoduo.utils.common.okhttputil.type;

import okhttp3.MediaType;

/**
 * Created by li on 2016/12/4.
 */
public enum TypeList {

    JSON("application/json"),
    JSON_UTF8("application/json; charset=utf-8"),
    KEY_VALUE("application/x-www-form-urlencoded"),
    FILE("multipart/form-data"),
    MEDIA_TYPE_MARKDOWN("text/x-markdown"),
    PNG("image/png"),
    TEXT("text/plain"),
	MARK_DOWN("text/x-markdown; charset=utf-8");

    private MediaType mediaType;

    //private String content;

    TypeList(String content){
        this.mediaType = MediaType.parse(content);
    }

    public MediaType getMediaType() {
        return mediaType;
    }

//    public String getContent() {
//        return content;
//    }

}
