package com.edu.jnu.atm.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.edu.jnu.atm.core.DateSeriesForecast;
import com.edu.jnu.atm.core.SingleDateForecast;
import com.edu.jnu.atm.io.SourceDataPool;
import com.edu.jnu.atm.util.DateProfile;
import com.edu.jnu.atm.util.StringToCalendar;

public class Client {
	
	public static void main (String args[]) {			
		String DEV_CODE = ""; //�豸��
		String TRNS_DATE = "";//Ԥ����ʼ����(��Ԥ�����ڵ�ǰһ��)
		int dates_of_predict = 0;//Ԥ������
		final int HISTORY_DAYS  = 30;//��ȥ����ʷ����
		double result ;//������
		
		try{		
			System.out.println("Please input the DEV_CODE :");
			BufferedReader br0 = new BufferedReader(new InputStreamReader(System.in));
			DEV_CODE = br0.readLine();
			System.out.println("Please input the TRNS_DATE:");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			TRNS_DATE = br1.readLine();				
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		//ת��Ϊ������
		StringToCalendar stc = new StringToCalendar();
		Calendar TRANS_DATE = stc.ToCalendar(TRNS_DATE);
		
	
		//��������Ԥ��
		SingleDateForecast dateseries = new SingleDateForecast();
	    result = dateseries.forecast(DEV_CODE, TRANS_DATE, 30);
		
		Window wnd = new Window();
	 //  wnd.show(result);	    
	    
	}
	
	
}
