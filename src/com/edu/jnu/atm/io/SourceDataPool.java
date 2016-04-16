package com.edu.jnu.atm.io;

import java.util.ArrayList;
import java.util.Calendar;
import com.edu.jnu.atm.util.DateProfileUtil;

/**
 * �����ݿ���ȡ����ʷ�������һ��������������HashMap.
 * 
 * @author Teacher Lee
 *
 */
public class SourceDataPool implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public ArrayList<DateProfileUtil> getSourceDataPool(String DEV_CODE, Calendar TRNS_DATE, int HISTORY_DAYS,
			DBConnectionPool connPool) {
		double value = 0;
		ArrayList<DateProfileUtil> sourcedata = new ArrayList<>();
		TRNS_DATE.add(Calendar.DATE, -HISTORY_DAYS);

		// ��ʷ���ݷ���sourcedata
		for (int i = 0; i < HISTORY_DAYS; i++) {
			SourceData dbcon;

			// select the Database: MySQL or Oracle
			DBFactory datafactory = new MySQLFactory();
			// DBFactory datafactory = new OracleFactory();

			dbcon = datafactory.getDBConnection();
			value = dbcon.getSourceData(DEV_CODE, TRNS_DATE, connPool);
			DateProfileUtil df = new DateProfileUtil();
			df.DATE = TRNS_DATE;
			df.value = value;
			sourcedata.add(df);
			TRNS_DATE.add(Calendar.DATE, 1);

		}
		return sourcedata;
	}

}
