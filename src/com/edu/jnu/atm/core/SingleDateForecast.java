package com.edu.jnu.atm.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.edu.jnu.atm.io.DBFactory;
import com.edu.jnu.atm.io.MySQLFactory;
import com.edu.jnu.atm.io.SourceData;
import com.edu.jnu.atm.io.SourceDataPool;
import com.edu.jnu.atm.util.DateProfileUtil;

/**
 * ʵ�ֵ������ڵ�Ԥ��
 * @author Teacher Lee
 *
 */
public class SingleDateForecast {
	
	
	public double[] forecast (String DEV_CODE, Calendar TRNS_DATE, int HISTORY_DAYS ) {		
		double bpForecastResult = 0, svmForecastResult = 0,
				arimaForecastResult = 0, forecastResult = 0;
		
		//��ʷ���ݷ���Դ���ݳ�
		SourceDataPool sdp = new SourceDataPool();
		ArrayList<DateProfileUtil> sourcedata = sdp.getSourceDataPool(DEV_CODE, TRNS_DATE, HISTORY_DAYS); 
				
		//������ģ��
	/*	ForecastContext forecastContext0;
		forecastContext0 = new ForecastContext (new BPStrategy ());
	    bpForecastResult = forecastContext0.forcast(sourcedata);
	*/
		
	    //֧��������ģ��
	/*    ForecastContext forecastContext1;
		forecastContext1 = new ForecastContext (new SVMStrategy ());
	    svmForecastResult = forecastContext1.forcast(sourcedata);
    */		    
	    //ARIMAģ��
	    ForecastContext forecastContext2;
		forecastContext2 = new ForecastContext (new ArimaStrategy ());
	    arimaForecastResult = forecastContext2.forcast(sourcedata);
	    
	 //   forecastResult = 0.7 * bpForecastResult + 0.3 * svmForecastResult;	
	  
	    //�õ�TRNS_DATE����ʵֵ
	    SourceData dbcon;
		DBFactory datafactory = new MySQLFactory(); //MySQL���ݿ�
	    //DBFactory datafactory = new OracleFactory(); //Oracle���ݿ�
		dbcon = datafactory.getDBConnection();
		double realValue = dbcon.getSourceData(DEV_CODE, TRNS_DATE);
		
		//�������
		double[] result = new double[2];
		result[0] = realValue;
		result[1] = arimaForecastResult;
		return result;	
		
	}
	
	
}
