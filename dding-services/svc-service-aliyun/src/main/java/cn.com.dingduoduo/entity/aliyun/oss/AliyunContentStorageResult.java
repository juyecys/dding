package cn.com.dingduoduo.entity.aliyun.oss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AliyunContentStorageResult {
    /**
     * 原始文件名
     */
    private String originalFilename;

    /**
     * 最终存储文件名
     */
    private String filename;

    /**
     * 存储服务器的key
     */
    private String key;

    /**
     * 文件上传后的路径
     */
    private String resourceUrl;

    /**
     * 图片类型文件上传后的缩略图路径
     */
    private String imageThumbnailUrl;

    /**
     * 文件大小(byte)
     */
    private Long fileSize;


    /**
     */
    private String base64Code;


    public String getBase64Code() {
        return base64Code;
    }

    public void setBase64Code(String base64Code) {
        this.base64Code = base64Code;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "AliyunContentStorageResult [originalFilename=" + originalFilename + ", filename=" + filename + ", key=" + key
                + ", resourceUrl=" + resourceUrl + ", imageThumbnailUrl=" + imageThumbnailUrl + ", fileSize="
                + fileSize + "]";
    }
}
