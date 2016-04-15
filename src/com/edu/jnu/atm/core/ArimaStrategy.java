package com.edu.jnu.atm.core;

import java.util.ArrayList;

import com.edu.jnu.atm.core.strategy.ARIMA;
import com.edu.jnu.atm.util.DateProfileUtil;

/**
 * ARIMAʱ������Ԥ��ģ�͹���
 * @author Teacher Lee
 *
 */
public class ArimaStrategy extends Strategy {

	@Override
	public double Algorithm(ArrayList<DateProfileUtil> sourcedata) {
		double result;
		double[] input = new double[30]; //������������
		for (int i = 0; i < 30; i++) {
			input[i] = sourcedata.get(sourcedata.size()-(i + 1)).value;
		}
		
		ARIMA arima = new ARIMA(input);
		int[] model = arima.getARIMAmodel();
		result = arima.aftDeal(arima.predictValue(model[0], model[1]));
		return result;
	}

}
