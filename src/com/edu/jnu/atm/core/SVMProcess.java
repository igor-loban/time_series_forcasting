package com.edu.jnu.atm.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.edu.jnu.atm.core.strategy.Svm;
import com.edu.jnu.atm.core.strategy.SvmModel;
import com.edu.jnu.atm.core.strategy.SvmNode;
import com.edu.jnu.atm.core.strategy.SvmParameter;
import com.edu.jnu.atm.core.strategy.SvmProblem;
import com.edu.jnu.atm.util.DateProfile;

public class SVMProcess {
	int SvmInputDate = 6; //ģ�͵�����ֵ����

	/**
	 * ѵ������
	 * @param sourcedata
	 * @return
	 */
	public SvmModel svmtrain(ArrayList<DateProfile> sourcedata) {
		int SvmTrainingDate = 23; //ѵ������
        SvmNode[][] datas = new SvmNode[SvmTrainingDate][SvmInputDate]; //�����ѵ������
        double lables[] = new double[SvmTrainingDate]; //Ŀ���������
        
        for (int i = 0; i < SvmTrainingDate; i++) {
        	for (int j = 0; j < SvmInputDate; j++) {
        		SvmNode node = new SvmNode();
        		node.index = j+1;
        		node.value = sourcedata.get(i + j).value;
        		datas[i][j] = node;
        	}
        	lables[i] = sourcedata.get(6 + i).value;
        }
        
        SvmProblem problem = new SvmProblem();
        problem.l = datas.length; 
        problem.x = datas; 
        problem.y = lables; 
        SvmParameter param = new SvmParameter();
        param.svm_type = SvmParameter.EPSILON_SVR;
        param.kernel_type = SvmParameter.RBF;
        param.cache_size = 100;
        param.eps = 0.00001;
        param.C = 1.9;
        SvmModel model = Svm.svm_train(problem, param);
        return model;
        
	}
	
	/**
     * TYPE = 0 means DEPOSIT,TYPE = 1 means WITHDRAW,TYPE = 2 means NETVALUE
     * return the true value and the predict result
     */
	public double svmpredict(SvmModel model, ArrayList<DateProfile> sourcedata) {
        SvmNode[] vector = new SvmNode[SvmInputDate];
		for (int i = 0; i < SvmInputDate; i++) {
        	SvmNode node = new SvmNode();
        	node.index = i+1;
        	node.value = sourcedata.get(sourcedata.size() - SvmInputDate + i).value;
        	vector[i] = node;
        }
    	double predictValue = Svm.svm_predict(model, vector);
        return predictValue; 
    }	
	
	
}
