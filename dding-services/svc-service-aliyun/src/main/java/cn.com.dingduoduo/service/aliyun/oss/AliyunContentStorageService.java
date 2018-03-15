package cn.com.dingduoduo.service.aliyun.oss;


import cn.com.dingduoduo.entity.aliyun.oss.AliyunContentStorageResult;
import cn.com.dingduoduo.exception.aliyun.oss.AliyunContentStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public interface AliyunContentStorageService {
	/**
	 * Store the content
	 * 
	 * @param key
	 *            The specified key for the content
	 * @param content
	 *            The bytes of the content
	 * @return The key (same as the input, if the key is not null) and the
	 *         content hash.
	 * @throws AliyunContentStorageException
	 */
	void store(final String key, final byte[] content, final String mimeType) throws AliyunContentStorageException;

	void delete(final String key) throws AliyunContentStorageException;

	boolean isExisting(final String key) throws AliyunContentStorageException;

	void store(final String key, InputStream inputStream, final String mimeType, Date expirationTime)
			throws AliyunContentStorageException;

	void store(final String key, InputStream inputStream, final String mimeType) throws AliyunContentStorageException;

	/**
	 * 从url下载的文件直接上传至存储服务器上并返回oss存储的key
	 * 
	 * @param mediaUrl
	 * @param mediaId
	 * @param keyPath
	 * @return
	 */
	String transferResourceToStorageServer(String mediaUrl, String mediaId, String keyPath);

	/**
	 * @param key
	 *            访问对象的key(名字)
	 * @param expiration
	 *            访问对象的超时时间，按秒计
	 * @return 访问对象的url
	 * @throws AliyunContentStorageException
	 */
	String getObjAccessUrl(final String key, final int expiration) throws AliyunContentStorageException;

	void move(String srcKey, String destKey) throws AliyunContentStorageException;

	void copy(String srcKey, String destKey) throws AliyunContentStorageException;

	/**
	 * 通用上传方法
	 * 
	 * @param originalFilename
	 *            原始文件名(带后缀)
	 * @param bytes
	 * @param contentType
	 * @param basePath
	 *            上传到OSS的前缀路径
	 * @return
	 */
	AliyunContentStorageResult storeCommon(String originalFilename, byte[] bytes, String contentType, String basePath)
			throws AliyunContentStorageException;

	/**
	 * 通用上传方法
	 * 
	 * @param originalFilename
	 *            原始文件名(带后缀)
	 * @param inputStream
	 * @param contentType
	 * @param basePath
	 *            上传到OSS的前缀路径
	 * @return
	 */
	AliyunContentStorageResult storeCommon(String originalFilename, InputStream inputStream, String contentType, String basePath)
			throws AliyunContentStorageException;

	/**
	 * 通用上传方法
	 * 
	 * @param file
	 *            文件对象,这里要注意!!!如果是mvc框架获得的上传file对象,要注意filename可能是临时文件的名字等问题
	 * @param basePath
	 *            上传到OSS的前缀路径
	 * @return
	 */
	AliyunContentStorageResult storeCommon(File file, String basePath) throws AliyunContentStorageException, FileNotFoundException;
}
