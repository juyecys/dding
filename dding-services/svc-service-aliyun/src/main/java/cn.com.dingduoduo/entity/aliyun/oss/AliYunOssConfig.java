package cn.com.dingduoduo.entity.aliyun.oss;

import org.springframework.stereotype.Component;

@Component("ossConfig")
public class AliYunOssConfig {

	private String endpoint;

	/**
	 * 内网 endpoint
	 */
	private String endpointInternal;

	private String accessKeyId;

	private String accessKeySecret;

	private String bucketName;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getEndpointInternal() {
		return endpointInternal;
	}

	public void setEndpointInternal(String endpointInternal) {
		this.endpointInternal = endpointInternal;
	}

}
