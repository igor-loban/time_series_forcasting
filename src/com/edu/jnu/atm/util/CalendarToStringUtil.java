package com.edu.jnu.atm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ���ڶ���ת��Ϊ�ַ���
 * @author Teacher Lee
 *
 */
public class CalendarToStringUtil {
	public static String toString (Calendar Date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");			
		String DATE = dateformat.format(Date.getTime());
		return DATE;
	}
}
