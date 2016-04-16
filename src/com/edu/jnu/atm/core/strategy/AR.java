package com.edu.jnu.atm.core.strategy;

import java.util.*;

/**
 * ARģ��
 * 
 * @author Teacher Lee
 *
 */
public class AR {

	double[] stdoriginalData;
	int p;
	ARMAMath armamath = new ARMAMath();

	/**
	 * ARģ��
	 * 
	 * @param stdoriginalData//Ԥ������������
	 * @param p
	 *            //pΪMAģ�ͽ���
	 */
	public AR(double[] stdoriginalData, int p) {
		this.stdoriginalData = stdoriginalData;
		this.p = p;
	}

	/**
	 * �õ�ARģ�͵��Իع�ϵ��
	 * 
	 * @return
	 */
	public Vector<double[]> ARmodel() {
		Vector<double[]> v = new Vector<double[]>();
		v.add(armamath.parcorrCompute(stdoriginalData, p, 0));
		return v;
	}

}
