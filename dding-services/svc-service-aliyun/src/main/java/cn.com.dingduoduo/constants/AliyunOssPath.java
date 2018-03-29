package cn.com.dingduoduo.constants;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统参数常量
 */
public class AliyunOssPath {

	private static Logger log = LoggerFactory.getLogger(AliyunOssPath.class);

	/*
	 * Spring 使用的Profile配置("dev"、"test"、"prod"取值,注意是数组)
	 */
	public static String[] PROFILE;

	/** 临时保存路径 **/
	public static final String TEMP_FILEPATH = "ding_tmp_dir/";

	/** 微信推广二维码保存路径 **/
	public static final String CHANNEL_QRCODE_FILEPATH = "wechat/channel_qrcode/";

	/** 微信消息图片保存路径 **/
	public static final String MESSAGE_FILEPATH = "wechat/message/";

	/** 音频课程音频保存路径 **/
	public static final String AUDIO_COURSE_LECTURE_FILEPATH = "audio/course/lecture/";

	/** 音频课程音频保存路径 **/
	public static final String AUDIO_COURSE_ANSWER_FILEPATH = "audio/course/answer/";

	/** 音频课程封面保存路径 **/
	public static final String AUDIO_COURSE_HEAD_IMG_FILEPATH = "audio/course/doctor/head_img/";

	/** 音频课程医生头像保存路径 **/
	public static final String AUDIO_COURSE_DOCTOR_HEAD_IMG_FILEPATH = "audio/course/head_img/";

	/** UEDITOR图片路径 **/
	public static final String UEDITOR_IMG_FILEPATH = "ueditor/img/";

	public static void setPROFILE(String[] profile) {
		PROFILE = profile;
		log.info("当前系统profile配置为:{}", Arrays.asList(profile));
	}
}
