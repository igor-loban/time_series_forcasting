package com.edu.jnu.atm.core;

import java.util.ArrayList;

import com.edu.jnu.atm.core.strategy.ARIMA;
import com.edu.jnu.atm.util.DateProfileUtil;

/**
 * ARIMAʱ������Ԥ��ģ�͹���
 * 
 * @author Teacher Lee
 *
 */
public class ArimaStrategy extends Strategy {

	@Override
	public double Algorithm(ArrayList<DateProfileUtil> sourcedata) {
		double result;
		int DATE_NUMBER = 30;
		double[] input = new double[DATE_NUMBER]; // ������������
		for (int i = DATE_NUMBER; i > 0; i--) {
			input[DATE_NUMBER-i] = sourcedata.get(sourcedata.size() - i).value;
		}

		ARIMA arima = new ARIMA(input);
		int[] model = arima.getARIMAmodel();
		result = arima.aftDeal(arima.forecast(model[0], model[1]));
		
		return result;
	}

}
