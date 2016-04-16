package com.edu.jnu.atm.core.strategy;

import java.util.*;

/**
 * ARIMAģ��
 * 
 * @author Teacher Lee
 *
 */
public class ARIMA {
	double[] originalData;
	ARMAMath armamath = new ARMAMath();

	Vector<double[]> armaARMAcoe = new Vector<double[]>();
	Vector<double[]> bestarmaARMAcoe = new Vector<double[]>();

	public ARIMA(double[] originalData) {
		this.originalData = originalData;
	}

	/**
	 * ԭʼ���ݱ�׼������һ�׼����Բ��+Z-Score��һ��
	 * 
	 * @return
	 */
	double stderrDara = 0;
	double avgsumData = 0;

	public double[] preDealDif() {
		// seasonal Difference:Peroid=7
		double[] tempData = new double[originalData.length - 4];
		for (int i = 0; i < originalData.length - 4; i++) {
			tempData[i] = originalData[i + 4] - originalData[i];
		}
		// Z-Score
		avgsumData = armamath.avgData(tempData);
		stderrDara = armamath.stderrData(tempData);
		for (int i = 0; i < tempData.length; i++) {
			tempData[i] = (tempData[i] - avgsumData) / stderrDara;
		}
		return tempData;
	}

	/**
	 * ���������ѵģ�p,q��ģ��
	 * 
	 * @return
	 */
	public int[] getARIMAmodel() {
		int paraType = 0;
		int bestModelindex = 0;
		int[][] model = new int[][] { { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, { 2, 3 },
				{ 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 } };
		double[] stdoriginalData = this.preDealDif();// ԭʼ���ݱ�׼������
		double minAIC = 9999999;

		// ��ģ�ͽ��е�����ѡ��AICֵ��С��ģ����Ϊ���ǵ�ģ��
		for (int i = 0; i < model.length; i++) {
			if (model[i][0] == 0) {
				MA ma = new MA(stdoriginalData, model[i][1]);
				armaARMAcoe = ma.MAmodel();
				paraType = 1;
			} else if (model[i][1] == 0) {
				AR ar = new AR(stdoriginalData, model[i][0]);
				armaARMAcoe = ar.ARmodel();
				paraType = 2;
			} else {
				ARMA arma = new ARMA(stdoriginalData, model[i][0], model[i][1]);
				armaARMAcoe = arma.ARMAmodel();
				paraType = 3;
			}

			double temp = getmodelAIC(armaARMAcoe, stdoriginalData, paraType);
			if (temp < minAIC) {
				bestModelindex = i;
				minAIC = temp;
				bestarmaARMAcoe = armaARMAcoe;
			}
		}
		return model[bestModelindex];
	}

	/**
	 * �õ�ģ�͵�AICֵ
	 * 
	 * @param para
	 * @param stdoriginalData
	 * @param type
	 * @return
	 */
	public double getmodelAIC(Vector<double[]> para, double[] stdoriginalData, int type) {
		double temp = 0;
		double temp2 = 0;
		double sumerr = 0;
		int p = 0;// ar1,ar2,...,sig2
		int q = 0;// sig2,ma1,ma2...
		int n = stdoriginalData.length;

		if (type == 1) {
			double[] maPara = para.get(0);
			q = maPara.length;
			double[] err = new double[q]; // error(t),error(t-1),error(t-2)...
			err[0] = Math.sqrt(maPara[0]);

			for (int k = q - 1; k < n; k++) {
				temp = 0;
				for (int i = 1; i < q; i++) {
					temp += maPara[i] * err[i];
				}

				// ��������ʱ�̵�����
				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				err[0] = stdoriginalData[k] - (err[0] - temp);
				// ���Ƶķ���֮��
				sumerr += err[0] * err[0];

			}
			return n * Math.log(sumerr / (n - (q - 1))) + (q) * Math.log(n);// AIC
																			// ��С���˹���
		}

		else if (type == 2) {
			double[] arPara = para.get(0);
			p = arPara.length;
			for (int k = p - 1; k < n; k++) {
				temp = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}
				temp += Math.sqrt(arPara[p - 1]);
				// ���Ƶķ���֮��
				sumerr += (stdoriginalData[k] - temp) * (stdoriginalData[k] - temp);
			}
			return n * Math.log(sumerr / (n - (p - 1))) + (p) * Math.log(n);// AIC
																			// ��С���˹���
		}

		else {
			double[] arPara = para.get(0);
			double[] maPara = para.get(1);
			p = arPara.length;
			q = maPara.length;
			double[] err = new double[q]; // error(t),error(t-1),error(t-2)...
			err[0] = Math.sqrt(maPara[0]);

			for (int k = p - 1; k < n; k++) {
				temp = 0;
				temp2 = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}
				for (int i = 1; i < q; i++) {
					temp2 += maPara[i] * err[i];
				}

				// ��������ʱ�̵�����
				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				err[0] = stdoriginalData[k] - (err[0] - temp2 + temp);
				// ���Ƶķ���֮��
				sumerr += err[0] * err[0];
			}
			return n * Math.log(sumerr / (n - (p - 1))) + (p + q - 1) * Math.log(n);// AIC
																					// ��С���˹���
		}
	}

	/**
	 * ����ģ�͵�����Ԥ��ֵ
	 * 
	 * @param predictValue
	 * @return
	 */
	public int aftDeal(int predictValue) {
		return (int) (predictValue * stderrDara + avgsumData + originalData[originalData.length - 4]);
	}

	/**
	 * ��ģ�ͣ�p,q���µ�Ԥ�����
	 * 
	 * @param p
	 * @param q
	 * @return
	 */
	public int forecast(int p, int q) {
		int predictValue = 0;
		double[] stdoriginalData = this.preDealDif();
		int n = stdoriginalData.length;
		double temp = 0, temp2 = 0;
		double[] err = new double[q];

		if (p == 0) {
			double[] maPara = bestarmaARMAcoe.get(0);

			for (int k = q - 1; k <= n; k++) {
				temp = 0;
				for (int i = 1; i < q; i++) {
					temp += maPara[i] * err[i];
				}

				// ��������ʱ�̵�����
				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				if (k == n)
					predictValue = (int) (err[0] - temp); // ����Ԥ��
				else
					err[0] = stdoriginalData[k] - (err[0] - temp);
			}
		}

		else if (q == 0) {
			double[] arPara = bestarmaARMAcoe.get(0);
			for (int k = p - 1; k <= n; k++) {
				temp = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}
				temp += Math.sqrt(arPara[p - 1]);
			}
			predictValue = (int) temp;
		} else {

			double[] arPara = bestarmaARMAcoe.get(0);
			double[] maPara = bestarmaARMAcoe.get(1);

			err = new double[q]; // error(t),error(t-1),error(t-2)...
			err[0] = Math.sqrt(maPara[0]);

			for (int k = p - 1; k <= n; k++) {
				temp = 0;
				temp2 = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}
				for (int i = 1; i < q; i++) {
					temp2 += maPara[i] * err[i];
				}

				// ��������ʱ�̵�����
				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				if (k == n)
					predictValue = (int) (err[0] - temp2 + temp);
				else
					err[0] = stdoriginalData[k] - (err[0] - temp2 + temp);

			}

		}
		return predictValue;
	}

}
