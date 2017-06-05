package org.dawnsci.surfacescatter;

public class LocationLenPtConverterUtils {


	public static double[] lenPtToLocationConverter(double[][] lenPt){
	
		double[] pt = lenPt[1];
		double[] len = lenPt[0];
		
		return new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
			(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
			(double) (pt[1] + len[1]) };
	
	
	}
	
	
	public static double[] lenPtToLocationConverter(int[][] lenPt){
		
		int[] pt = lenPt[1];
		int[] len = lenPt[0];
		
		return new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
			(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
			(double) (pt[1] + len[1]) };
	
	
	}
	
	public static int[][] locationToLenPtConverter(double[] location){
		
		
		int[] pt = new int[] {(int) (location[0]), (int) (location[1])};
		int[] len = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};

		return new int[][] {len, pt};
		
	}
	
	public static int[][] locationToLenPtConverter(int[] location){
		
		double[] doubleLocation = new double[location.length]; 
		
		for(int i=0; i<location.length; i++){
			doubleLocation[i] = (double) location[i];
		}
		
		return locationToLenPtConverter(doubleLocation);
		
	}
	
}
