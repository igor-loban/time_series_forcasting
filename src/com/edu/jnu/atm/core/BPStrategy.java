package com.edu.jnu.atm.core;

import java.util.ArrayList;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.random.WeightsRandomizer;

import com.edu.jnu.atm.util.DateProfileUtil;

/**
 * ���������ʵ��Ԥ��
 * @author Teacher Lee
 *
 */
public class BPStrategy extends Strategy {

	@Override
	public double Algorithm (ArrayList<DateProfileUtil> sourcedata) {	
		int inputDate = 6; //��������������
		int trainingDate = 40; //ѵ������
		double[] predictresult; //Ԥ����
		
		//BP������Ĺ������ʼ��,�������Ԫ�����ֱ�Ϊ��inputDate��3��1	
		int[] neuronsInLayers = {inputDate,3,2,1};
		NeuralNetwork<BackPropagation> neuralNetwork = new MultiLayerPerceptron(neuronsInLayers);
		WeightsRandomizer WR = new WeightsRandomizer();
		WR.randomize(neuralNetwork);
		
		//���ѵ��������
		DataSet trainingSet = new DataSet(inputDate, 1);
		for (int i = 0; i < trainingDate; i++) {
			double[] data = new double[inputDate]; //ѵ����������
			double[] label = new double[1]; //ѵ���������
        	for (int j = 0; j < inputDate; j++) {
        		double value = sourcedata.get(sourcedata.size()-(inputDate + 1 + i) + j).value;
        		data[j] = value;
        	}
        	label[0] = sourcedata.get(sourcedata.size()-(inputDate + 1 + i) + inputDate).value;
    		trainingSet.addRow(data, label);
        }
		
		//��һ��ѵ����
		MaxMinNormalizer DSN = new MaxMinNormalizer();
		DSN.normalize(trainingSet);
		
		//�������������
		BackPropagation learningRule = new BackPropagation();
		learningRule.setMaxIterations(10000);
		learningRule.setLearningRate(0.05);
		neuralNetwork.learn(trainingSet, learningRule);
		
		//Ԥ�����
		double[] input0 = new double[inputDate], input1 = new double[inputDate];
		for (int i = 0; i < inputDate; i++) {
			input0[i] = sourcedata.get(sourcedata.size()-(inputDate-i)).value;
		}
		
		//�����һ������
		double MaxIn, MinIn, MaxOut, MinOut;
		for (int i = 0; i < inputDate; i++) {
			MaxIn = DSN.getMaxIn(i);
			MinIn = DSN.getMinIn(i);
			input1[i] = (input0[i] - MinIn) / (MaxIn - MinIn);
		}
		
		neuralNetwork.setInput(input1);
		neuralNetwork.calculate();
		predictresult = neuralNetwork.getOutput();
		
		//�����ԭ
		MaxOut = DSN.getMaxOut(0);
		MinOut = DSN.getMinOut(0);
		predictresult[0] = predictresult[0] * (MaxOut - MinOut); 
		
		//���ؽ��
		return predictresult[0];
		
	}
	
	
}
