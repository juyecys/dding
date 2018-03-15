package cn.com.dingduoduo.utils.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicateCodeVerificationUtil {
	private static final Logger logger = LoggerFactory.getLogger(DuplicateCodeVerificationUtil.class);

	private DuplicateCodeVerificationUtil() {

	}

	public static void assertNoDuplicatePulicStaticFinalInt(Class<?> classToBeVerified) {
		Set<Object> usedCodes = new HashSet<>();
		Field[] allFields = classToBeVerified.getDeclaredFields();
		int counter = 0;
		for (Field field : allFields) {
			int modifier = field.getModifiers();
			final boolean isPublicStaticFinalInt = Modifier.isPublic(modifier) && Modifier.isStatic(modifier)
					&& Modifier.isFinal(modifier) && field.getType().equals(int.class);
			if (!isPublicStaticFinalInt) {
				continue;
			}

			Object value;
			try {
				value = field.get(null);
			} catch (Exception e) {
				throw new IllegalArgumentException("无法判断是否有重复字段。", e);
			}
			logger.info("正在检查 {} 的值{}是否重复.", field.getName(), value);

			if (usedCodes.contains(value)) {
				throw new IllegalArgumentException(String.format("%s的值%s跟其它字段有重复.", field.getName(), value));
			} else {
				usedCodes.add(value);
				counter++;
			}
		}
		logger.info("{}的{}个值不重复。", classToBeVerified.getName(), counter);
	}
}
