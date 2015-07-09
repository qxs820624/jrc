package com.app.chatroom.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextUtil {
	public static void lengthFilter(final Context context,
			final EditText editText, final int max_length, final String err_msg) {

		InputFilter[] filters = new InputFilter[1];

		filters[0] = new InputFilter.LengthFilter(max_length) {

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				int destLen = getCharacterNum(dest.toString()); // 获取字符个数(一个中文算2个字符)
				int sourceLen = getCharacterNum(source.toString());
				if (destLen + sourceLen > max_length) {
					Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
					return "";
				}
				return source;
			}
		};
		editText.setFilters(filters);

	}

	/**
	 * 
	 * @description 获取一段字符串的字符个数（包含中英文，一个中文算2个字符）
	 * 
	 * @param content
	 * 
	 * @return
	 */

	public static int getCharacterNum(final String content) {
		if (null == content || "".equals(content)) {
			return 0;
		} else {

			return (content.length() + getChineseNum(content));
		}

	}

	/**
	 * 
	 * @description 返回字符串里中文字或者全角字符的个数
	 * 
	 * @param s
	 * 
	 * @return
	 */

	public static int getChineseNum(String s) {
		int num = 0;
		char[] myChar = s.toCharArray();
		for (int i = 0; i < myChar.length; i++) {
			if ((char) (byte) myChar[i] != myChar[i]) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 验证手机合法性
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean VerificationPhone(String phone) {
		String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		boolean isMatched = m.matches();
		if (isMatched) {
			return isMatched;
		}
		return isMatched;
	}

	/**
	 * 验证邮箱合法性
	 * 
	 * @param email
	 * @return
	 */
	public static boolean VerificationEmail(String email) {
		// String check =
		// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		if (isMatched) {
			return isMatched;
		}
		return isMatched;
	}

	/**
	 * 去除空格
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

}
