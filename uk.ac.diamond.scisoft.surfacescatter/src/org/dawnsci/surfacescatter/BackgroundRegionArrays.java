package org.dawnsci.surfacescatter;

import java.util.ArrayList;

public class BackgroundRegionArrays {

		private ArrayList<Integer> xArray;
		private ArrayList<Integer> yArray;
		private ArrayList<Double> zArray;
		
		public BackgroundRegionArrays(){
			xArray = new ArrayList<Integer>();
			yArray = new ArrayList<Integer>();
			zArray = new ArrayList<Double>();
		}
		
		public void xArrayAdd(int i){
			xArray.add(i);
		}
		
		public void yArrayAdd(int j){
			yArray.add(j);
		}
		
		public void zArrayAdd(double k){
			zArray.add(k);
		}
	
		public ArrayList<Integer> getXArray(){
			return xArray;
		}
	
		public ArrayList<Integer> getYArray(){
			return yArray;
		}
		
		public ArrayList<Double> getZArray(){
			return zArray;
		}
		
		public void setXArray(ArrayList<Integer> iA){
			xArray = iA;
		}
	
		public void setYArray(ArrayList<Integer> jA){
			yArray = jA;
		}
	
		public void setZArray(ArrayList<Double> kA){
			zArray = kA;
		}
		
}
