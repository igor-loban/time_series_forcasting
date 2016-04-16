package com.edu.jnu.atm.samples;

import java.util.Calendar;
import com.edu.jnu.atm.core.DateSeriesForecast;
import com.edu.jnu.atm.util.ResultDataPool;
import com.edu.jnu.atm.util.StringToCalendarUtil;

/**
 * ��������ڣ��������ڼ�Ԥ�����������Ԥ����
 * 
 * @author Teacher Lee
 *
 */
public class Client {

	public static void main(String args[]) {
		String DEV_CODE = ""; // �豸��
		String TRNS_DATE = "";// Ԥ����ʼ����(��Ԥ�����ڵ�ǰһ��)
		int dates_of_predict = 400;// Ԥ������

		// �û��������
		DEV_CODE = "990030270001";
		TRNS_DATE = "20130515";

		// ת��Ϊ������
		StringToCalendarUtil stc = new StringToCalendarUtil();
		Calendar TRANS_DATE = stc.ToCalendar(TRNS_DATE);

		// Ԥ��
		DateSeriesForecast DSF = new DateSeriesForecast();
		ResultDataPool RDP = DSF.seriesForest(DEV_CODE, TRANS_DATE, dates_of_predict);

		// ������
		Window wnd = new Window();
		wnd.show(RDP);

	}

}
