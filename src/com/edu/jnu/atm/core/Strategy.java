
package com.edu.jnu.atm.core;

import java.util.ArrayList;

import com.edu.jnu.atm.util.DateProfile;

/**
 * ���������
 * @author Teacher Lee
 *
 */
public abstract class Strategy {
	public abstract double Algorithm(ArrayList<DateProfile> sourcedata);
}
