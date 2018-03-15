package cn.com.dingduoduo.utils.common.okhttputil.entity;

import okhttp3.MediaType;

import java.io.File;

/**
 * 文件数据
 * @author li
 *
 */
public class FileData {

	private MediaType mediaType;

	private String paramName;

	private String fileName;

	private File file;

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileData(MediaType mediaType, String paramName, String fileName, File file) {
		super();
		this.mediaType = mediaType;
		this.paramName = paramName;
		this.fileName = fileName;
		this.file = file;
	}

	public FileData() {
		super();
	}

	@Override
	public String toString() {
		return "FileData [mediaType=" + mediaType + ", paramName=" + paramName + ", fileName=" + fileName + ", file="
				+ file + "]";
	}

}
