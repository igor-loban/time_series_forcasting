
package com.edu.jnu.atm.core;

import java.util.ArrayList;

import com.edu.jnu.atm.util.DateProfileUtil;

/**
 * ���������
 * @author Teacher Lee
 *
 */
public abstract class Strategy {
	public abstract double Algorithm(ArrayList<DateProfileUtil> sourcedata);
}
