package org.dawnsci.surfacescatter;

import java.util.ArrayList;

public class ClosestNoFinder {

	public static double closestNo (double myNum, ArrayList<Double> numbers){
	
	
	double distance = Math.abs(numbers.get(0) - myNum);
	int idx = 0;
	for(int c = 1; c < numbers.size(); c++){
	    double cdistance = Math.abs(numbers.get(c) - myNum);
	    if(cdistance < distance){
	        idx = c;
	        distance = cdistance;
	    }
	}
	return numbers.get(idx);

	}
	
	
	public static int closestNoPos (double myNum, ArrayList<Double> numbers){
		
		
		double distance = Math.abs(numbers.get(0) - myNum);
		int idx = 0;
		for(int c = 1; c < numbers.size(); c++){
		    double cdistance = Math.abs(numbers.get(c)- myNum);
		    if(cdistance < distance){
		        idx = c;
		        distance = cdistance;
		    }
		}
		return idx;

		}
}
