package com.edu.jnu.atm.core.strategy;

import java.util.Vector;

/**
 * MAģ��
 * 
 * @author Teacher Lee
 *
 */
public class MA {

	double[] stdoriginalData;
	int q;
	ARMAMath armamath = new ARMAMath();

	/**
	 * MAģ��
	 * 
	 * @param stdoriginalData
	 *            //Ԥ������������
	 * @param q
	 *            //qΪMAģ�ͽ���
	 */
	public MA(double[] stdoriginalData, int q) {
		this.stdoriginalData = stdoriginalData;
		this.q = q;
	}

	/**
	 * �õ�MAģ�͵Ĳ���ֵ
	 * 
	 * @return
	 */
	public Vector<double[]> MAmodel() {
		Vector<double[]> v = new Vector<double[]>();
		v.add(armamath.getMApara(armamath.autocorData(stdoriginalData, q), q));
		return v;
	}

}
