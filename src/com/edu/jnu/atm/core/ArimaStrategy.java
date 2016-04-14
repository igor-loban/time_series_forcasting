package com.edu.jnu.atm.core;

import java.util.ArrayList;

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
		double[] input = new double[sourcedata.size()]; //������������
		for (int i = 0; i < sourcedata.size(); i++) {
			input[i] = sourcedata.get(i).value;
		}
		
		ArimaProcess AP = new ArimaProcess();
		result = AP.forecast(input);
		return result;
	}

}
