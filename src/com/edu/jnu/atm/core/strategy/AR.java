package com.edu.jnu.atm.core.strategy;
import java.util.*;  
  
public class AR {  
      
    double[] stdoriginalData={};  
    int p;  
    ARMAMath armamath=new ARMAMath();  
      
    /** 
     * ARģ�� 
     * @param stdoriginalData 
     * @param p //pΪMAģ�ͽ��� 
     */  
    public AR(double [] stdoriginalData,int p)  
    {  
        this.stdoriginalData=new double[stdoriginalData.length];  
        System.arraycopy(stdoriginalData, 0, this.stdoriginalData, 0, stdoriginalData.length);  
        this.p=p;  
    }  
/** 
 * ����ARģ�Ͳ��� 
 * @return 
 */  
    public Vector<double[]> ARmodel()  
    {  
        Vector<double[]> v=new Vector<double[]>();  
        v.add(armamath.parcorrCompute(stdoriginalData, p, 0));  
        return v;//�õ����Իع�ϵ��  
    }  
      
}  