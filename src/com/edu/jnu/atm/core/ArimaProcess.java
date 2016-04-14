package com.edu.jnu.atm.core;

import java.util.*;

import com.edu.jnu.atm.core.strategy.ARIMA;
import com.edu.jnu.atm.core.strategy.MatrixUtlis;
import com.edu.jnu.atm.core.strategy.modelandpara;

public class ArimaProcess {

	int count = 0;
	int[] model = new int[2];
	int[][] modelOri = new int[][] {{0,1},{1,0},{1,1},{0,2}, {2,0}, {2,2}, {1,2},{2,1},
		{0,3}, {1,3},{2,3},{3,3},{3,0},{3,1},{3,2}};
	modelandpara mp = null;
	int predictValuetemp = 0;
	int avgpredictValue = 0;
	int[] bestmodel = new int[2];
	double[][] predictErr = new double[7][modelOri.length];
	double minpreDicterr = 9999999;
	int bestpreDictValue = 0;
	int bestDif = 0;
	int memory = 10;
	double[] traindataArray = null;
	double validate = 0;
	double[] predataArray = null;
	static int i = 0;
	double[] dataArrayPredict = null;
	Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
	Hashtable<String, Integer> ht2 = new Hashtable<String, Integer>();
	double thresvalue = 0;
	
	//ARIMAģ��Ԥ�����
	public double forecast(double[] dataArray) {
		
		// ģ��ѵ��
		Vector<int[]> trainResult = this.Train(dataArray);
		
		// Ԥ�����ݳ�ʼ��
		int tempPredict = 0;
		for (int i = 0; i < trainResult.size(); i++) {
			thresvalue = 0;
			tempPredict += this.Predict(dataArray, memory, trainResult.get(i), 0.1);
		}
		tempPredict = tempPredict / trainResult.size();
		return tempPredict;
	}
	
	public void preData(double[] dataArray, int type, int memory) {
		this.traindataArray = new double[dataArray.length - memory];
		System.arraycopy(dataArray, type, traindataArray, 0, traindataArray.length);
		this.validate = dataArray[traindataArray.length + type];// ���һ��ֵ��Ϊѵ��ʱ�����ֵ֤��
	}

	public int Predict(double[] dataArray, int memory, int[] trainResult, double fanwei) {
		if (memory < 0)
			return (int) (dataArray[dataArray.length - 1] + dataArray[dataArray.length - 2]) / 2;

		this.predataArray = new double[dataArray.length - memory];
		System.arraycopy(dataArray, memory, predataArray, 0, predataArray.length);
		ARIMA arima = new ARIMA(predataArray, trainResult[0]); // ��ԭʼ���������ײ�ִ���0,1,2,7
		// ������ʼ��
		int count = 100;
		int predictValuetemp = 0;
		// ͳ��ÿ��ģ�͵�Ԥ��ƽ��ֵ
		while (count-- > 0) {
			mp = arima.getARIMAmodel(modelOri[trainResult[1]]);
			predictValuetemp += arima.aftDeal(arima.predictValue(mp.model[0], mp.model[1], mp.para));
		}
		predictValuetemp /= 100;
		// System.out.println("Predict value is:"+predictValuetemp);

		if (Math.abs(predictValuetemp - predataArray[predataArray.length - 1])
				/ predataArray[predataArray.length - 1] > (0.2 + fanwei)) {
			thresvalue++;
		//	System.out.println("thresvalue=" + thresvalue);
			// ����ѵ����Ԥ��
			// ģ��ѵ��
			Vector<int[]> trainResult2 = this.Train(dataArray);
			// Ԥ�����ݳ�ʼ��
			int tempPredict = 0;
			for (int i = 0; i < trainResult2.size(); i++) {
				tempPredict += this.Predict(dataArray, (memory - 10), trainResult2.get(i), 0.1 * thresvalue);
			}
			tempPredict = tempPredict / trainResult2.size();
			// System.out.println("tempPredict="+tempPredict);
			return tempPredict;
		} else {
			return predictValuetemp;
		}
	}

	public Vector<int[]> Train(double[] dataArray) {
		int memory = 10;// ѵ����ʱ��Ԥ���ֵ�ĸ���

		for (int datai = 0; datai < memory; datai++) {
			// System.out.println("train... "+datai+"/"+memory);
			this.preData(dataArray, datai, memory);// ׼��ѵ������

			for (int diedai = 0; diedai < 7; diedai++) {
				ARIMA arima = new ARIMA(traindataArray, diedai); // ��ԭʼ���������ײ�ִ���0,1,2,7

				// ͳ��ÿ��ģ�͵�Ԥ��ƽ��ֵ
				for (int modeli = 0; modeli < modelOri.length; modeli++) {
					// ������ʼ��
					count = 100;
					predictValuetemp = 0;

					while (count-- > 0) {
						mp = arima.getARIMAmodel(modelOri[modeli]);
						predictValuetemp += arima.aftDeal(arima.predictValue(mp.model[0], mp.model[1], mp.para));
						// System.out.println("predictValuetemp"+predictValuetemp);
					}
					predictValuetemp /= 100;
					// ����ѵ�����
					predictErr[diedai][modeli] += Math.abs((predictValuetemp - validate) / validate);
				}
			}
		}

		double minvalue = 10000000;
		int tempi = 0;
		int tempj = 0;
		int i = 0;
		int j = 0;
		Vector<int[]> bestmodelVector = new Vector<int[]>();
		int[][] flag = new int[7][modelOri.length];
		
		//�޸ĵĴ���
		MatrixUtlis matrix = new MatrixUtlis(predictErr);
		matrix.sort();
		for (i = 0; i < 20; i++) {
			bestmodelVector.add(new int[] { matrix.getRow(i), matrix.getCol(i) });
			// System.out.println(matrix.getRow(i)+"\t"+matrix.getCol(i)+"\t"+matrix.getVal(i));
		}
		
		//ԭʼ����
		/*for (int ii = 0; ii < 10; ii++) {
			minvalue = 10000000;
			for (i = 0; i < predictErr.length; i++) {
				for (j = 0; j < predictErr[i].length; j++) {
					if (flag[i][j] == 0) {
						if (predictErr[i][j] < minvalue) {
							minvalue = predictErr[i][j];
							tempi = i;
							tempj = j;
							flag[i][j] = 1;
						}
					}
				}
			}

			bestmodelVector.add(new int[] { tempi, tempj });
		}*/
		
		return bestmodelVector;
	}

}

