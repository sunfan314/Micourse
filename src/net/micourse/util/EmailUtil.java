package net.micourse.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
	public static boolean emailFormat(String email) // 验证邮箱格式是否正确
	{
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static String transferEmailToUsername(String email) {// 根据邮箱生成用户名
		int index = email.indexOf("@");
		String part1 = email.substring(0, index);
		String part2 = email.substring(index + 1, email.length());
		int index2 = part2.indexOf(".");
		String result = part1 + "_" + part2.substring(0, index2);
		return result;

	}

}
