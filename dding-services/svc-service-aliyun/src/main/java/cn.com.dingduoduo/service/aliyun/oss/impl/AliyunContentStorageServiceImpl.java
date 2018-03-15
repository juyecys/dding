package cn.com.dingduoduo.service.aliyun.oss.impl;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import cn.com.dingduoduo.utils.common.AliyunContentStorageUtils;
import cn.com.dingduoduo.utils.common.DateUtils;
import cn.com.dingduoduo.utils.common.FileUtils;
import cn.com.dingduoduo.utils.common.StringUtil;
import cn.com.dingduoduo.constants.AliyunOssPath;
import cn.com.dingduoduo.entity.aliyun.oss.AliYunOssConfig;
import cn.com.dingduoduo.entity.aliyun.oss.AliyunContentStorageResult;
import cn.com.dingduoduo.exception.aliyun.oss.AliyunContentStorageException;
import cn.com.dingduoduo.service.aliyun.oss.AliyunContentStorageService;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.LifecycleRule;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.SetBucketLifecycleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Service;

@Service("aliyunContentStorageService")
public class AliyunContentStorageServiceImpl implements AliyunContentStorageService, Closeable {

	private Logger logger = LoggerFactory.getLogger(AliyunContentStorageServiceImpl.class);

	private AliYunOssConfig ossConfig;

	private OSSClient client;

	public AliYunOssConfig getOssConfig() {
		return ossConfig;
	}

	public void setOssConfig(AliYunOssConfig ossConfig) {
		this.ossConfig = ossConfig;
	}

	@Override
	public void store(final String key, byte[] content, final String mimeType) throws AliyunContentStorageException {
		throw new UnsupportedOperationException();
	}

	/*
	 * 对于阿里云的上传实现，直接将InputStream中的内容往上传
	 */
	@Override
	public void store(final String key, InputStream inputStream, final String mimeType) throws AliyunContentStorageException {
		logger.debug("Uploading a {} object '{}' to bucket {} from input stream", mimeType, key,
				ossConfig.getBucketName());

		if (client == null) {
			client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		}

		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(mimeType);

		try {
			client.putObject(ossConfig.getBucketName(), key, inputStream);

		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: {}, key: {}, bucket: {}", oe.getErrorMessage(), key, ossConfig.getBucketName());
			logger.error("Error Code:    {}", oe.getErrorCode());
			logger.error("Request ID:    {}", oe.getRequestId());
			logger.error("Host ID:       {}", oe.getHostId());
			throw new AliyunContentStorageException("upload file failed.", oe);
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ce.getMessage());
			throw new AliyunContentStorageException("check object existence failed.", ce);
		}
	}

	@Override
	public void store(final String key, InputStream inputStream, final String mimeType, Date expirationTime)
			throws AliyunContentStorageException {
		logger.debug("Uploading a {} object '{}' to bucket {} from input stream", mimeType, key,
				ossConfig.getBucketName());

		if (client == null) {
			client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		}

		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(mimeType);

		meta.setExpirationTime(expirationTime);
		try {
			client.putObject(ossConfig.getBucketName(), key, inputStream, meta);

			SetBucketLifecycleRequest request = new SetBucketLifecycleRequest(ossConfig.getBucketName());
			// 特定日期后过期
			request.AddLifecycleRule(new LifecycleRule(key, key, LifecycleRule.RuleStatus.Enabled, expirationTime));
			client.setBucketLifecycle(request);
		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: {}, key: {}, bucket: {}", oe.getErrorMessage(), key, ossConfig.getBucketName());
			logger.error("Error Code:    {}", oe.getErrorCode());
			logger.error("Request ID:    {}", oe.getRequestId());
			logger.error("Host ID:       {}", oe.getHostId());
			throw new AliyunContentStorageException("upload file failed.", oe);
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ce.getMessage());
			throw new AliyunContentStorageException("check object existence failed.", ce);
		}
	}

	@Override
	public void delete(String key) throws AliyunContentStorageException {
		logger.debug("Deleting an object'{}' from bucket {}", key, ossConfig.getBucketName());

		if (client == null) {
			client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		}

		try {
			client.deleteObject(ossConfig.getBucketName(), key);

		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: {}, key: {}, bucket: {}", oe.getErrorMessage(), key, ossConfig.getBucketName());
			logger.error("Error Code:    {}", oe.getErrorCode());
			logger.error("Request ID:    {}", oe.getRequestId());
			logger.error("Host ID:       {}", oe.getHostId());
			throw new AliyunContentStorageException("delete file failed.", oe);
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ce.getMessage());
			throw new AliyunContentStorageException("delete file failed.", ce);
		}
	}

	@Override
	public void copy(String srcKey, String destKey) throws AliyunContentStorageException {
		logger.debug("copy an object from {}  to {}", srcKey, destKey);

		if (client == null) {
			client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		}

		try {
			client.copyObject(ossConfig.getBucketName(), srcKey, ossConfig.getBucketName(), destKey);
		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: {}, srcKey: {}, destKey:{}, bucket: {}", oe.getErrorMessage(), srcKey,
					destKey, ossConfig.getBucketName());
			logger.error("Error Code:    {}", oe.getErrorCode());
			logger.error("Request ID:    {}", oe.getRequestId());
			logger.error("Host ID:       {}", oe.getHostId());
			throw new AliyunContentStorageException("copyObject file failed.", oe);
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ce.getMessage());
			throw new AliyunContentStorageException("copy file failed.", ce);
		}
	}

	@Override
	public void move(String srcKey, String destKey) throws AliyunContentStorageException {
		logger.debug("move an object from {}  to {}", srcKey, destKey);

		try {
			copy(srcKey, destKey);
			delete(srcKey);

		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: {}, srcKey: {}, destKey:{}, bucket: {}", oe.getErrorMessage(), srcKey,
					destKey, ossConfig.getBucketName());
			logger.error("Error Code:    {}", oe.getErrorCode());
			logger.error("Request ID:    {}", oe.getRequestId());
			logger.error("Host ID:       {}", oe.getHostId());
			throw new AliyunContentStorageException("delete file failed.", oe);
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ce.getMessage());
			throw new AliyunContentStorageException("delete file failed.", ce);
		}
	}

	@Override
	public boolean isExisting(String key) throws AliyunContentStorageException {
		if (client == null) {
			client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		}

		boolean existence = false;
		try {
			existence = client.doesObjectExist(ossConfig.getBucketName(), key);

		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: {}, key: {}, bucket: {}", oe.getErrorMessage(), key, ossConfig.getBucketName());
			logger.error("Error Code:    {}", oe.getErrorCode());
			logger.error("Request ID:    {}", oe.getRequestId());
			logger.error("Host ID:       {}", oe.getHostId());
			throw new AliyunContentStorageException("check file existence failed.", oe);
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ce.getMessage());
			throw new AliyunContentStorageException("check file existence failed.", ce);
		}

		return existence;
	}

	@Override
	public void close() throws IOException {
		if (client != null) {
			client.shutdown();
		}
	}

	@Override
	public String getObjAccessUrl(String key, int expiration) throws AliyunContentStorageException {
		if (client == null) {
			client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		}

		Calendar cal = Calendar.getInstance();
		if (expiration <= 0) {
			logger.error("invalid negative expiration: {}", expiration);
			throw new AliyunContentStorageException("invalid negative expiration");
		}

		cal.add(Calendar.SECOND, expiration);
		Date expirationDate = cal.getTime();

		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossConfig.getBucketName(), key,
				HttpMethod.GET);

		// 设置过期时间
		request.setExpiration(expirationDate);

		// 生成URL签名(HTTP GET请求)
		URL signedUrl = client.generatePresignedUrl(request);
		logger.trace("signed url for getObject: {} ", signedUrl);
		return signedUrl.toString();
	}

	@Override
	public AliyunContentStorageResult storeCommon(String originalFilename, InputStream inputStream, String contentType,
												  String basePath) throws AliyunContentStorageException {
		String finalBasePath = StringUtil.isNotEmpty(basePath) ? basePath : AliyunOssPath.TEMP_FILEPATH;
		String fileType = FileUtils.getExtendWithDot(originalFilename);
		String newFilename = FileUtils.getDateRandomFileName() + fileType;
		String key = finalBasePath + newFilename;
		logger.trace("Upload file - key:{}, fileType:{}", key, fileType);

		store(key, inputStream, contentType);

		AliyunContentStorageResult result = new AliyunContentStorageResult();
		result.setOriginalFilename(originalFilename);
		result.setFilename(newFilename);
		result.setKey(key);
		result.setResourceUrl(AliyunContentStorageUtils.getFullAccessUrlForKey(key));
		if (contentType.indexOf("image") != -1) {
			result.setImageThumbnailUrl(AliyunContentStorageUtils.getImageThumbnailUrlForKey(key));
		}

		return result;
	}

	@Override
	public AliyunContentStorageResult storeCommon(String originalFilename, byte[] bytes, String contentType, String basePath)
			throws AliyunContentStorageException {
		return storeCommon(originalFilename, new ByteArrayInputStream(bytes), contentType, basePath);
	}

	@Override
	public AliyunContentStorageResult storeCommon(File file, String basePath) throws AliyunContentStorageException,
			FileNotFoundException {
		String fileType = FileUtils.getExtendWithDot(file.getName());
		String contentType = FileUtils.getFileContentTypeByExtension(fileType);
		return storeCommon(file.getName(), new FileInputStream(file), contentType, basePath);
	}

	@Override
	public String transferResourceToStorageServer(String resourceUrl, String resouceId, String keyPath) {
		logger.trace("fetch file from url: {}", resourceUrl);
		String key = null;
		int attempts = 0;
		HttpURLConnection conn = null;

		while (attempts++ < 3) {
			try {
				URL url = new URL(resourceUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setRequestMethod("GET");
				conn.connect();
				if (conn.getResponseCode() > 299) {
					throw new Exception("从微信下载文件错误!");
				}
				InputStream inputStream = conn.getInputStream();

				// 根据内容类型获取扩展名
				String fileExt = FileUtils.getFileExtByContentType(conn.getHeaderField("Content-Type"));
				if (fileExt == null || fileExt.equals("")) {
					fileExt = ".jpg";
				}

				if (!keyPath.endsWith("/")) {
					keyPath += "/";
				}

				// 设置文件名
				key = keyPath + DateUtils.format(new Date(), "yyyyMMddHHmmssSSS") + "_" + resouceId + fileExt;
				store(key, inputStream, conn.getHeaderField("Content-Type"));

				conn.disconnect();

				logger.info("存储文件到存储服务器成功，key=" + key);

				break;
			} catch (Exception e) {
				logger.error("", e);
				if (conn != null) {
					conn.disconnect();
				}
				continue;
			}
		}
		return key;
	}
}
