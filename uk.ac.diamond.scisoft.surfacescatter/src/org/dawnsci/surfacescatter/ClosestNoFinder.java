package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;

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
	
	public static double closestNo (double myNum, Dataset numbers){
		
		
		double distance = Math.abs(numbers.getDouble(0) - myNum);
		int idx = 0;
		for(int c = 1; c < numbers.getSize(); c++){
		    double cdistance = Math.abs(numbers.getDouble(c) - myNum);
		    if(cdistance < distance){
		        idx = c;
		        distance = cdistance;
		    }
		}
		return numbers.getDouble(idx);

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
	
	public static int closestNoPos (double myNum, Dataset numbers){
		
		
		double distance = Math.abs(numbers.getDouble(0) - myNum);
		int idx = 0;
		for(int c = 1; c < numbers.getSize(); c++){
		    double cdistance = Math.abs(numbers.getDouble(c)- myNum);
		    if(cdistance < distance){
		        idx = c;
		        distance = cdistance;
		    }
		}
		return idx;

		}
	
	public static int closestNoWithLocation (double myNum, 
											 Dataset numbers,
											 ArrayList<double[]> locationList){
		
		double[] test = new double[] {0,0,0,0,0,0,0,0};
		double distance = Math.abs(numbers.getDouble(0) - myNum);
		int idx = 0;
		
		for(int d =0; d<numbers.getSize(); d++){
			if(test.equals(Arrays.equals(locationList.get(d), test) == false) & Arrays.equals(locationList.get(d), null) == false){
				idx = d;
				distance = Math.abs(numbers.getDouble(0) - myNum);
				break;
			}
		}
		
		
		for(int c = 0; c < numbers.getSize(); c++){
		    double cdistance = Math.abs(numbers.getDouble(c)- myNum);
		    if((cdistance < distance) & (Arrays.equals(locationList.get(c), test) == false) & Arrays.equals(locationList.get(c), null) == false){
		        idx = c;
		        distance = cdistance;
		    }
		}
		return idx;

	}
	
	
	public static int closestNoWithoutDone (double myNum, 
												Dataset numbers,
												String[] doneArray,
												int[] filepathSortedArray){

		String test = "done";
		double distance = Math.abs(numbers.getDouble(0) - myNum);
		int idx = 0;
		
		for(int d =0; d<numbers.getSize(); d++){
			if(test.equals(doneArray[filepathSortedArray[d]]) == false){
				idx = d;
				distance = Math.abs(numbers.getDouble(0) - myNum);
				break;
			}
		}
		
		for(int c = 1; c < numbers.getSize(); c++){
			double cdistance = Math.abs(numbers.getDouble(c)- myNum);
			if((cdistance < distance) & (test.equals(doneArray[filepathSortedArray[c]]) == false)){
				idx = c;
				distance = cdistance;
			}
		}
		return idx;

	}
	
	
	public static boolean full (String[] stringArray, String test){
		
		for (int u = 0; u <stringArray.length; u++){
			if (test.equals(stringArray[u]) == false){
				return false;
			}
		}
		
		return true;
	}
	
	public static int closestNoWithDone (double myNum, 
										 Dataset numbers,
										 String[] doneArray,
										 int[] filepathSortedArray){

		String test = "done";
		double distance = Math.abs(numbers.getDouble(0) - myNum);
		int idx = 0;
		
		for(int d =0; d<numbers.getSize(); d++){
			if(test.equals(doneArray[filepathSortedArray[d]])){
				idx = d;
				distance = Math.abs(numbers.getDouble(0) - myNum);
				break;
			}
		}
		
		for(int c = 1; c < numbers.getSize(); c++){
			double cdistance = Math.abs(numbers.getDouble(c)- myNum);
			if((cdistance < distance) & (test.equals(doneArray[filepathSortedArray[c]]))){
				idx = c;
				distance = cdistance;
			}
		}
		return idx;

	}
	
}
