package cn.com.dingduoduo.utils.common;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	public static boolean idInStringArray(String[] array, String id) {
		for (String s : array) {
			if (s.equals(id))
				return true;
		}
		return false;
	}

	public static boolean idInStringList(List<String> list, String id) {
		for (String s : list) {
			if (s.equals(id))
				return true;
		}
		return false;
	}

	/**
	 * 生成随机编码
	 *
	 * @param type
	 *            类型：0-字母和数字；1-字母；2-数字
	 * @param length
	 *            长度
	 */
	public static String generateRandomCode(int type, int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			if (type == 0) {
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
				if ("char".equalsIgnoreCase(charOrNum)) {
					int choice = 65;
					val += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) {
					val += String.valueOf(random.nextInt(10));
				}
			} else if (type == 1) {
				int choice = 65;
				val += (char) (choice + random.nextInt(26));
			} else {
				val += String.valueOf(random.nextInt(9) + 1);
			}
		}
		return val;
	}

	/**
	 * 手机号验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 邮箱验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isEmail(String str) {
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		return b;
	}

	public static String uuidToString(Object uuid) {
		return uuid == null ? null : uuid.toString();
	}

	public static UUID stringToUuid(String str) {
		return str == null ? null : UUID.fromString(str);
	}

	public static String genTraceId() {
		return "TRACEID_" + System.currentTimeMillis() + CommonUtils.generateRandomCode(2, 9);
	}

}
