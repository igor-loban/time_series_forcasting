package com.edu.jnu.atm.core;

import java.sql.SQLException;
import java.util.Calendar;

import com.edu.jnu.atm.io.DBConnectionPool;
import com.edu.jnu.atm.util.ResultDataPool;

/**
 * ������ڵ�Ԥ��
 * 
 * @author Teacher Lee
 *
 */
public class DateSeriesForecast {
	ResultDataPool rdp = new ResultDataPool();
	int HISTORY_DAYS = 60; // ��ʷ���ݸ���

	public ResultDataPool seriesForest(String DEV_CODE, Calendar TRNS_DATE, int Days) {

		// �������ݿ����ӳ�
		DBConnectionPool connPool = new DBConnectionPool("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/ATM?autoReconnect=true&useSSL=false", "root", "administrator");
		try {
			connPool.createPool();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// �������Ԥ��
		SingleDateForecast SDF = new SingleDateForecast();
		
		//Ԥ��ĵ�һ��
		double[] result = SDF.forecast(DEV_CODE, TRNS_DATE, HISTORY_DAYS, connPool, 1);
		rdp.sourceList.add(result[0]);
		rdp.predictList.add(result[1]);
		TRNS_DATE.add(Calendar.DATE, 1);
		
		
		for (int i = 1; i < Days; i++) {
			result = SDF.forecast(DEV_CODE, TRNS_DATE, HISTORY_DAYS, connPool, 1);
			
			//Ԥ��ֵ��ǿѧϰ��������
			if ((rdp.predictList.get(i - 1) < rdp.sourceList.get(i - 1)) && (result[1] > rdp.predictList.get(i - 1)))
				result = SDF.forecast(DEV_CODE, TRNS_DATE, HISTORY_DAYS, connPool, 3);

			rdp.sourceList.add(result[0]);
			rdp.predictList.add(result[1]);
			TRNS_DATE.add(Calendar.DATE, 1);
		}
		
		
		
		// �ͷ����ݿ�����
		try {
			connPool.closeConnectionPool();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rdp;

	}
}
