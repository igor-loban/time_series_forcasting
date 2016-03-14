package com.edu.jnu.atm.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import com.edu.jnu.atm.core.strategy.BP;
import com.edu.jnu.atm.util.StringToCalendar;

public class DateSeriesForecast {	
	
	
	protected int NumberOfPredictingDate = 0,
			      SvmTrainingDate = 5,
	              SvmInputDate = 7;
	protected int TYPE = 1;
	double err = 0.3;
	
	public List<Double> forecast (String DEV_CODE, String TRNS_DATE) { 
		
		double[] PredictingResult = new double[2];
		List<Double> sourceList = new ArrayList<Double>(); 
		List<Double> predictList = new ArrayList<Double>(); 
		StringToCalendar stc = new StringToCalendar();
		Calendar TRNSDATE = stc.ToCalendar(TRNS_DATE);
   		SingleDateForecast dp = new SingleDateForecast(); 
   		BPStrategy bp =new BPStrategy();
		BP model =bp.bptrain(DEV_CODE, TYPE);
		
        for (int i = 0; i < NumberOfPredictingDate; i++)  {
        	PredictingResult = dp.predict (model, DEV_CODE, TRNSDATE, TYPE, SvmTrainingDate, SvmInputDate);
            sourceList.add(PredictingResult[0]);
            predictList.add(PredictingResult[1]);
            TRNSDATE.add(Calendar.DATE,1);	
        }
              
        return predictList;	
	 }

	public double[][] forecast(HashMap<Calendar, Double> sourcedata, String tRNS_DATE, int dates_of_predict) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
