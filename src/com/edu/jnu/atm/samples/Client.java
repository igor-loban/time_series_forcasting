package com.edu.jnu.atm.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.edu.jnu.atm.core.DateSeriesForecast;
import com.edu.jnu.atm.core.SingleDateForecast;
import com.edu.jnu.atm.util.ResultDataPool;
import com.edu.jnu.atm.io.SourceDataPool;
import com.edu.jnu.atm.util.DateProfileUtil;
import com.edu.jnu.atm.util.StringToCalendarUtil;

public class Client {
	
	public static void main (String args[]) {			
		String DEV_CODE = ""; //�豸��
		String TRNS_DATE = "";//Ԥ����ʼ����(��Ԥ�����ڵ�ǰһ��)
		int dates_of_predict = 50;//Ԥ������
		
	/*	try{		
			System.out.println("Please input the DEV_CODE :");
			BufferedReader br0 = new BufferedReader(new InputStreamReader(System.in));
			DEV_CODE = br0.readLine();
			System.out.println("Please input the TRNS_DATE:");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			TRNS_DATE = br1.readLine();				
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	*/	
		
		DEV_CODE = "990030270001";
		TRNS_DATE = "20140815";
		
		//ת��Ϊ������		
		StringToCalendarUtil stc = new StringToCalendarUtil();
		Calendar TRANS_DATE = stc.ToCalendar(TRNS_DATE);
			
		//Ԥ��
		DateSeriesForecast DSF = new DateSeriesForecast();
	    ResultDataPool RDP = DSF.seriesForest(DEV_CODE, TRANS_DATE, dates_of_predict);
		
	    //������
		Window wnd = new Window();
	    wnd.show(RDP);	    
	    
	}
	
	
}
