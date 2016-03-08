package com.edu.jnu.atm.core.strategy;
import java.util.*;  
  
public class ARIMA {  
  
    double[] originalData={};  
    double[] originalDatafirDif={};  
    double[] originalDatasecDif={};  
    double[] originalDatathiDif={};  
    double[] originalDataforDif={};  
    double[] originalDatafriDif={};  
      
    ARMAMath armamath=new ARMAMath();  
    double stderrDara=0;  
    double avgsumData=0;  
    Vector<double[]> armaARMAcoe=new Vector<double[]>();  
    Vector<double[]> bestarmaARMAcoe=new Vector<double[]>();  
    int typeofPredeal=0;  
/** 
 * ���캯�� 
 * @param originalData ԭʼʱ���������� 
 */  
    public ARIMA(double [] originalData,int typeofPredeal)  
    {  
        this.originalData=originalData;  
        this.typeofPredeal=typeofPredeal;//����Ԥ�������� 1:һ����ͨ���7�������Բ��  
    }  
/** 
 * ԭʼ���ݱ�׼������һ�׼����Բ�� 
 * @return ��ֹ�������� 
 */   
    public double[] preDealDif(double[] originalData)  
    {  
        //seasonal Difference:Peroid=7  
        double []tempData=new double[originalData.length-7];  
        for(int i=0;i<originalData.length-7;i++)  
        {  
            tempData[i]=originalData[i+7]-originalData[i];  
        }  
        return tempData;  
    }  
      
      
/** 
 *  
 */  
    public double[] preFirDif(double[] originalData)   
    {  
        // Difference:Peroid=1  
        double []tempData=new double[originalData.length-1];  
        for(int i=0;i<originalData.length-1;i++)  
        {  
            tempData[i]=originalData[i+1]-originalData[i];  
        }  
  
        return tempData;  
    }  
      
/** 
 * ԭʼ���ݱ�׼������Z-Score��һ�� 
 * @param ���������� 
 * @return ��һ����������� 
 */  
    public double[] preDealNor(double[] tempData)  
    {  
        //Z-Score  
        avgsumData=armamath.avgData(tempData);  
        stderrDara=armamath.stderrData(tempData);  
          
        for(int i=0;i<tempData.length;i++)  
        {  
            tempData[i]=(tempData[i]-avgsumData)/stderrDara;  
        }  
        return tempData;  
    }  
      
    public modelandpara getARIMAmodel(int[] bestmodel)  
    {  
        double[] stdoriginalData=null;  
          
        if(typeofPredeal==0)  
            {  
                stdoriginalData=new double[originalData.length];  
                System.arraycopy(originalData, 0, stdoriginalData, 0,originalData.length);  
            }  
        else if(typeofPredeal==1)       //ԭʼ����һ����ͨ��ִ���  
            {  
                originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ���  
                System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);   
          
                stdoriginalData=new double[originalDatafirDif.length];  
                System.arraycopy(originalDatafirDif, 0, stdoriginalData, 0,originalDatafirDif.length);    
            }  
        else if (typeofPredeal==2)  
            {  
                originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ���  
                System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);   
  
                originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length];  
                System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);     
  
                stdoriginalData=new double[originalDatasecDif.length];  
                System.arraycopy(originalDatasecDif, 0, stdoriginalData, 0,originalDatasecDif.length);    
            }  
        else if(typeofPredeal==3)  
            {  
                originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ���  
                System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);   
      
                originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length];  
                System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);     
  
                originalDatathiDif=new double[this.preFirDif(originalDatasecDif).length];  
                System.arraycopy(this.preFirDif(originalDatasecDif), 0, originalDatathiDif, 0,originalDatathiDif.length);     
      
                stdoriginalData=new double[originalDatathiDif.length];  
                System.arraycopy(originalDatathiDif, 0, stdoriginalData, 0,originalDatathiDif.length);    
  
            }  
        else if(typeofPredeal==4)  
            {  
              
                originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ���  
                System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);   
      
                originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length];  
                System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);     
      
                originalDatathiDif=new double[this.preFirDif(originalDatasecDif).length];  
                System.arraycopy(this.preFirDif(originalDatasecDif), 0, originalDatathiDif, 0,originalDatathiDif.length);     
      
                originalDataforDif=new double[this.preFirDif(originalDatathiDif).length];  
                System.arraycopy(this.preFirDif(originalDatathiDif), 0, originalDataforDif, 0,originalDataforDif.length);     
  
                stdoriginalData=new double[originalDataforDif.length];  
                System.arraycopy(originalDataforDif, 0, stdoriginalData, 0,originalDataforDif.length);    
  
            }  
        else if(typeofPredeal==5)  
            {  
                originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ���  
                System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);   
      
                originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length];  
                System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);     
      
                originalDatathiDif=new double[this.preFirDif(originalDatasecDif).length];  
                System.arraycopy(this.preFirDif(originalDatasecDif), 0, originalDatathiDif, 0,originalDatathiDif.length);     
      
                originalDataforDif=new double[this.preFirDif(originalDatathiDif).length];  
                System.arraycopy(this.preFirDif(originalDatathiDif), 0, originalDataforDif, 0,originalDataforDif.length);     
                  
                originalDatafriDif=new double[this.preFirDif(originalDataforDif).length];  
                System.arraycopy(this.preFirDif(originalDataforDif), 0, originalDatafriDif, 0,originalDatafriDif.length);     
                  
                stdoriginalData=new double[originalDatafriDif.length];  
                System.arraycopy(originalDatafriDif, 0, stdoriginalData, 0,originalDatafriDif.length);    
  
            }  
        else//ԭʼ���ݼ����Բ�ִ���     
            {  
                stdoriginalData=new double[this.preDealDif(originalData).length];  
                System.arraycopy(this.preDealDif(originalData), 0, stdoriginalData, 0,this.preDealDif(originalData).length);      
            }  
          
        armaARMAcoe.clear();  
        bestarmaARMAcoe.clear();  
          
        if(bestmodel[0]==0)  
        {  
            MA ma=new MA(stdoriginalData, bestmodel[1]);  
            armaARMAcoe=ma.MAmodel(); //�õ�maģ�͵Ĳ���  
              
        }  
        else if(bestmodel[1]==0)  
        {  
            AR ar=new AR(stdoriginalData, bestmodel[0]);  
            armaARMAcoe=ar.ARmodel(); //�õ�arģ�͵Ĳ���  
              
        }  
        else  
        {  
            ARMA arma=new ARMA(stdoriginalData, bestmodel[0], bestmodel[1]);  
            armaARMAcoe=arma.ARMAmodel();//�õ�armaģ�͵Ĳ���  
              
        }  
          
        bestarmaARMAcoe=armaARMAcoe;  
        modelandpara mp=new modelandpara(bestmodel, bestarmaARMAcoe);  
          
        return mp;  
    }  
/** 
* �õ�ARMAģ��=[p,q] 
 * @return ARMAģ�͵Ľ�����Ϣ 
 *//* 
    public modelandpara getARIMAmodel() 
    { 
        double[] stdoriginalData=null; 
        if(typeofPredeal==0) 
        { 
            stdoriginalData=new double[originalData.length]; 
            System.arraycopy(originalData, 0, stdoriginalData, 0,originalData.length); 
        } 
    else if(typeofPredeal==1)       //ԭʼ����һ����ͨ��ִ��� 
        { 
         
            originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ��� 
            System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);  
     
            stdoriginalData=new double[originalDatafirDif.length]; 
            System.arraycopy(originalDatafirDif, 0, stdoriginalData, 0,originalDatafirDif.length);   
        } 
    else if (typeofPredeal==2) 
        { 
            originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ��� 
            System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);  
 
            originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length]; 
            System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);    
 
            stdoriginalData=new double[originalDatasecDif.length]; 
            System.arraycopy(originalDatasecDif, 0, stdoriginalData, 0,originalDatasecDif.length);   
        } 
    else if(typeofPredeal==3) 
        { 
            originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ��� 
            System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);  
 
            originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length]; 
            System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);    
 
            originalDatathiDif=new double[this.preFirDif(originalDatasecDif).length]; 
            System.arraycopy(this.preFirDif(originalDatasecDif), 0, originalDatathiDif, 0,originalDatathiDif.length);    
 
            stdoriginalData=new double[originalDatathiDif.length]; 
            System.arraycopy(originalDatathiDif, 0, stdoriginalData, 0,originalDatathiDif.length);   
 
        } 
    else if(typeofPredeal==4) 
        { 
            originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ��� 
            System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);  
 
            originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length]; 
            System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);    
 
            originalDatathiDif=new double[this.preFirDif(originalDatasecDif).length]; 
            System.arraycopy(this.preFirDif(originalDatasecDif), 0, originalDatathiDif, 0,originalDatathiDif.length);    
 
            originalDataforDif=new double[this.preFirDif(originalDatathiDif).length]; 
            System.arraycopy(this.preFirDif(originalDatathiDif), 0, originalDataforDif, 0,originalDataforDif.length);    
 
            stdoriginalData=new double[originalDataforDif.length]; 
            System.arraycopy(originalDataforDif, 0, stdoriginalData, 0,originalDataforDif.length);   
 
        } 
    else if(typeofPredeal==5) 
        { 
            originalDatafirDif=new double[this.preFirDif(originalData).length];//ԭʼ����һ����ͨ��ִ��� 
            System.arraycopy(this.preFirDif(originalData), 0, originalDatafirDif, 0,originalDatafirDif.length);  
 
            originalDatasecDif=new double[this.preFirDif(originalDatafirDif).length]; 
            System.arraycopy(this.preFirDif(originalDatafirDif), 0, originalDatasecDif, 0,originalDatasecDif.length);    
 
            originalDatathiDif=new double[this.preFirDif(originalDatasecDif).length]; 
            System.arraycopy(this.preFirDif(originalDatasecDif), 0, originalDatathiDif, 0,originalDatathiDif.length);    
 
            originalDataforDif=new double[this.preFirDif(originalDatathiDif).length]; 
            System.arraycopy(this.preFirDif(originalDatathiDif), 0, originalDataforDif, 0,originalDataforDif.length);    
             
            originalDatafriDif=new double[this.preFirDif(originalDataforDif).length]; 
            System.arraycopy(this.preFirDif(originalDataforDif), 0, originalDatafriDif, 0,originalDatafriDif.length);    
             
            stdoriginalData=new double[this.preFirDif(originalDatafriDif).length]; 
            System.arraycopy(this.preFirDif(originalDatafriDif), 0, stdoriginalData, 0,originalDatafriDif.length);   
 
        } 
    else//ԭʼ���ݼ����Բ�ִ���    
        { 
            stdoriginalData=new double[this.preDealDif(originalData).length]; 
            System.arraycopy(this.preDealDif(originalData), 0, stdoriginalData, 0,this.preDealDif(originalData).length);     
        } 
     
        int paraType=0; 
        double minAIC=9999999; 
        int bestModelindex=0; 
        int[][] model=new int[][]{{0,1},{1,0},{1,1},{0,2},{2,0},{2,2},{1,2},{2,1},{3,0},{0,3},{3,1},{1,3},{3,2},{2,3},{3,3}}; 
        //��ģ�ͽ��е�����ѡ��ƽ��Ԥ�������С��ģ����Ϊ���ǵ�ģ�� 
        for(int i=0;i<model.length;i++) 
        { 
             
            if(model[i][0]==0) 
            { 
                MA ma=new MA(stdoriginalData, model[i][1]); 
                armaARMAcoe=ma.MAmodel(); //�õ�maģ�͵Ĳ��� 
                paraType=1; 
            } 
            else if(model[i][1]==0) 
            { 
                AR ar=new AR(stdoriginalData, model[i][0]); 
                armaARMAcoe=ar.ARmodel(); //�õ�arģ�͵Ĳ��� 
                paraType=2; 
            } 
            else 
            { 
                ARMA arma=new ARMA(stdoriginalData, model[i][0], model[i][1]); 
                armaARMAcoe=arma.ARMAmodel();//�õ�armaģ�͵Ĳ��� 
                paraType=3; 
            } 
         
            double temp=getmodelAIC(armaARMAcoe,stdoriginalData,paraType); 
            if (temp<minAIC) 
            { 
                bestModelindex=i; 
                minAIC=temp; 
                bestarmaARMAcoe=armaARMAcoe; 
            } 
        } 
         
        modelandpara mp=new modelandpara(model[bestModelindex], bestarmaARMAcoe); 
         
        return mp; 
    }*/  
/** 
 * ����ARMAģ�͵�AIC 
 * @param para װ��ģ�͵Ĳ�����Ϣ 
 * @param stdoriginalData   Ԥ��������ԭʼ���� 
 * @param type 1��MA��2��AR��3��ARMA 
 * @return ģ�͵�AICֵ 
 */  
    public double getmodelAIC(Vector<double[]> para,double[] stdoriginalData,int type)  
    {  
        double temp=0;  
        double temp2=0;  
        double sumerr=0;  
        int p=0;//ar1,ar2,...,sig2  
        int q=0;//sig2,ma1,ma2...  
        int n=stdoriginalData.length;  
        Random random=new Random();  
          
        if(type==1)  
        {  
            double[] maPara=new double[para.get(0).length];  
            System.arraycopy(para.get(0), 0, maPara, 0, para.get(0).length);  
              
            q=maPara.length;  
            double[] err=new double[q];  //error(t),error(t-1),error(t-2)...  
            for(int k=q-1;k<n;k++)  
            {  
                temp=0;  
                  
                for(int i=1;i<q;i++)  
                {  
                    temp+=maPara[i]*err[i];  
                }  
              
                //��������ʱ�̵�����  
                for(int j=q-1;j>0;j--)  
                {  
                    err[j]=err[j-1];  
                }  
                err[0]=random.nextGaussian()*Math.sqrt(maPara[0]);  
                  
                //���Ƶķ���֮��  
                sumerr+=(stdoriginalData[k]-(temp))*(stdoriginalData[k]-(temp));  
                  
            }  
              
            //return  (n-(q-1))*Math.log(sumerr/(n-(q-1)))+(q)*Math.log(n-(q-1));//AIC ��С���˹���  
            return (n-(q-1))*Math.log(sumerr/(n-(q-1)))+(q+1)*2;  
        }  
        else if(type==2)  
        {  
            double[] arPara=new double[para.get(0).length];  
            System.arraycopy(para.get(0), 0, arPara, 0, para.get(0).length);  
              
            p=arPara.length;  
            for(int k=p-1;k<n;k++)  
            {  
                temp=0;  
                for(int i=0;i<p-1;i++)  
                {  
                    temp+=arPara[i]*stdoriginalData[k-i-1];  
                }  
                //���Ƶķ���֮��  
                sumerr+=(stdoriginalData[k]-temp)*(stdoriginalData[k]-temp);  
            }  
          
            return (n-(q-1))*Math.log(sumerr/(n-(q-1)))+(p+1)*2;  
            //return (n-(p-1))*Math.log(sumerr/(n-(p-1)))+(p)*Math.log(n-(p-1));//AIC ��С���˹���  
        }  
        else  
        {  
            double[] arPara=new double[para.get(0).length];  
            System.arraycopy(para.get(0), 0, arPara, 0, para.get(0).length);  
            double[] maPara=new double[para.get(1).length];  
            System.arraycopy(para.get(1), 0, maPara, 0, para.get(1).length);  
                  
            p=arPara.length;  
            q=maPara.length;  
            double[] err=new double[q];  //error(t),error(t-1),error(t-2)...  
              
            for(int k=p-1;k<n;k++)  
            {  
                temp=0;  
                temp2=0;  
                for(int i=0;i<p-1;i++)  
                {  
                    temp+=arPara[i]*stdoriginalData[k-i-1];  
                }  
              
                for(int i=1;i<q;i++)  
                {  
                    temp2+=maPara[i]*err[i];  
                }  
              
                //��������ʱ�̵�����  
                for(int j=q-1;j>0;j--)  
                {  
                    err[j]=err[j-1];  
                }  
                //System.out.println("predictBeforeDiff="+1);  
                err[0]=random.nextGaussian()*Math.sqrt(maPara[0]);  
                //���Ƶķ���֮��  
                sumerr+=(stdoriginalData[k]-(temp2+temp))*(stdoriginalData[k]-(temp2+temp));  
            }  
              
            return (n-(q-1))*Math.log(sumerr/(n-(q-1)))+(p+q)*2;  
            //return (n-(p-1))*Math.log(sumerr/(n-(p-1)))+(p+q-1)*Math.log(n-(p-1));//AIC ��С���˹���  
        }  
    }  
/** 
 * ��Ԥ��ֵ���з���ִ��� 
 * @param predictValue Ԥ���ֵ 
 * @return ����ֹ����Ԥ��ֵ 
 */  
    public int aftDeal(int predictValue)  
    {  
        int temp=0;  
        //System.out.println("predictBeforeDiff="+predictValue);  
        if(typeofPredeal==0)  
            temp=((int)predictValue);  
        else if(typeofPredeal==1)  
            temp=(int)(predictValue+originalData[originalData.length-1]);  
        else if(typeofPredeal==2)     
            temp=(int)(predictValue+originalDatafirDif[originalDatafirDif.length-1]+originalData[originalData.length-1]);     
        else if(typeofPredeal==3)  
            temp=(int)(predictValue+originalDatasecDif[originalDatasecDif.length-1]+originalDatafirDif[originalDatafirDif.length-1]+originalData[originalData.length-1]);             
        else if(typeofPredeal==4)  
            temp=(int)(predictValue+originalDatathiDif[originalDatathiDif.length-1]+originalDatasecDif[originalDatasecDif.length-1]+originalDatafirDif[originalDatafirDif.length-1]+originalData[originalData.length-1]);             
        else if(typeofPredeal==5)  
            temp=(int)(predictValue+originalDataforDif[originalDataforDif.length-1]+originalDatathiDif[originalDatathiDif.length-1]+originalDatasecDif[originalDatasecDif.length-1]+originalDatafirDif[originalDatafirDif.length-1]+originalData[originalData.length-1]);             
        else  
            temp=(int)(predictValue+originalData[originalData.length-7]);     
              
                return temp>0?temp:0;  
    }  
      
      
/** 
 * ����һ��Ԥ�� 
 * @param p ARMAģ�͵�AR�Ľ��� 
 * @param q ARMAģ�͵�MA�Ľ��� 
 * @return Ԥ��ֵ 
 */  
    public int predictValue(int p,int q,Vector<double[]> bestpara)  
    {  
        double[] stdoriginalData=null;  
        if (typeofPredeal==0)  
            {  
                stdoriginalData=new double[originalData.length];  
                System.arraycopy(originalData, 0, stdoriginalData, 0, originalData.length);  
                  
            }  
        else if(typeofPredeal==1)  
            {  
                stdoriginalData=new double[originalDatafirDif.length];  
                  
                System.arraycopy(originalDatafirDif, 0, stdoriginalData, 0, originalDatafirDif.length);   
            }  
        else if(typeofPredeal==2)  
            {  
                stdoriginalData=new double[originalDatasecDif.length];//��ͨ���ײ�ִ���  
                System.arraycopy(originalDatasecDif, 0, stdoriginalData, 0, originalDatasecDif.length);   
            }  
              
        else if(typeofPredeal==3)  
            {  
                stdoriginalData=new double[originalDatathiDif.length];//��ͨ���ײ�ִ���  
                System.arraycopy(originalDatathiDif, 0, stdoriginalData, 0, originalDatathiDif.length);   
            }  
              
        else if(typeofPredeal==4)  
            {  
                stdoriginalData=new double[originalDataforDif.length];//��ͨ�Ľײ�ִ���  
                System.arraycopy(originalDataforDif, 0, stdoriginalData, 0, originalDataforDif.length);   
            }  
              
        else if(typeofPredeal==5)  
            {  
                stdoriginalData=new double[originalDatafriDif.length];//��ͨ��ײ�ִ���  
                System.arraycopy(originalDatafriDif, 0, stdoriginalData, 0, originalDatafriDif.length);   
            }  
        else  
            {  
                stdoriginalData=new double[this.preDealDif(originalData).length];//������һ�ײ��  
                System.arraycopy(this.preDealDif(originalData), 0, stdoriginalData, 0, this.preDealDif(originalData).length);     
            }  
        //System.out.println("typeofPredeal= "+typeofPredeal+typeofPredeal);  
          
//      for(int i=0;i<originalDatafirDif.length;i++)  
//          System.out.println(originalDatafirDif[i]);  
//        
        int predict=0;  
        int n=stdoriginalData.length;  
        double temp=0,temp2=0;  
        double[] err=new double[q+1];  
      
        Random random=new Random();  
        if(p==0)  
        {  
            double[] maPara=bestpara.get(0);  
              
              
            for(int k=q;k<n;k++)  
            {  
                temp=0;  
                for(int i=1;i<=q;i++)  
                {  
                    temp+=maPara[i]*err[i];  
                }  
                //��������ʱ�̵�����  
                for(int j=q;j>0;j--)  
                {  
                    err[j]=err[j-1];  
                }  
                err[0]=random.nextGaussian()*Math.sqrt(maPara[0]);  
            }  
            predict=(int)(temp); //����Ԥ��  
            //System.out.println("predict=q "+predict);  
        }  
        else if(q==0)  
        {  
            double[] arPara=bestpara.get(0);  
          
            for(int k=p;k<n;k++)  
            {  
                temp=0;  
                for(int i=0;i<p;i++)  
                {  
                    temp+=arPara[i]*stdoriginalData[k-i-1];  
                }  
            }  
            predict=(int)(temp);  
            //System.out.println("predict= p"+predict);  
        }  
        else  
        {  
            double[] arPara=bestpara.get(0);  
            double[] maPara=bestpara.get(1);  
              
            err=new double[q+1];  //error(t),error(t-1),error(t-2)...  
            for(int k=p;k<n;k++)  
            {  
                temp=0;  
                temp2=0;  
                for(int i=0;i<p;i++)  
                {  
                    temp+=arPara[i]*stdoriginalData[k-i-1];  
                }  
              
                for(int i=1;i<=q;i++)  
                {  
                    temp2+=maPara[i]*err[i];  
                }  
              
                //��������ʱ�̵�����  
                for(int j=q;j>0;j--)  
                {  
                    err[j]=err[j-1];  
                }  
                  
                err[0]=random.nextGaussian()*Math.sqrt(maPara[0]);  
            }  
              
            predict=(int)(temp2+temp);  
            //System.out.println("predict=p,q "+predict);  
        }  
        return predict;  
    }  
  
}  
  
  
class modelandpara  
{  
    int[] model;  
    Vector<double[]> para;  
    public modelandpara(int[] model,Vector<double[]> para)  
    {  
        this.model=model;  
        this.para=para;  
    }  
}  