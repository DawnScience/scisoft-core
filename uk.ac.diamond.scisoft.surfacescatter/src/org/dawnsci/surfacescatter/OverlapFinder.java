package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Collections;

import org.dawnsci.surfacescatter.ArrayMaximForSorting;
import org.dawnsci.surfacescatter.SortingArrayMaxima;
import org.eclipse.january.dataset.IDataset;

public class OverlapFinder {

	public static double[][] overlapFinderOperation(IDataset[] xArray) {
		int size = xArray.length;
		IDataset[] xArrayClone = new IDataset[size];
		IDataset[] xArrayCloneClone = new IDataset[size];
		
		for (int k = 0; k < size; k++) {

			try {
				xArrayClone[k] = xArray[k];
				xArrayCloneClone[k] = xArray[k];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 
		
		
		return overlapFinderOperation(xArrayClone,xArrayCloneClone,size);
	}
	
	public static double[][] overlapFinderOperation(ArrayList<IDataset> arrayILDx) {

		int size = arrayILDx.size();
		
		
		IDataset[] xArray = new IDataset[size];
		IDataset[] xArrayClone = new IDataset[size];

		for (int k = 0; k < size; k++) {

			try {
				xArray[k] = arrayILDx.get(k);
				xArrayClone[k] = arrayILDx.get(k);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return overlapFinderOperation(xArray,xArrayClone,size);
		
	}
	
	public static double[][] overlapFinderOperation(IDataset[] xArray,
			IDataset[] xArrayClone, int size) {

		double[][] maxMinArray = new double[size][2];
		double[][] maxMinArrayClone = new double[size][2];
		double[][] overlap = new double[size-1][2];
		double[][] overlapClone = new double[size-1][2];
		double[] maxArray = new double[size];
		double[] maxArrayClone = new double[size];

		
		
		// getArrayOfMaximums(double[] input)

		for (int k = 0; k < size; k++) {

			try {
			
				maxMinArray[k][0] = (double) xArray[k].max(null);
				maxMinArray[k][1] = (double) xArray[k].min(null);

				maxMinArrayClone[k][0] = (double) xArray[k].max(null);
				maxMinArrayClone[k][1] = (double) xArray[k].min(null);

				maxArray[k] = (double) xArray[k].max(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ArrayMaximForSorting[] amfs = SortingArrayMaxima.getArrayOfMaximums(maxArray);

		SortingArrayMaxima.sort(amfs);

		for (int i = 0; i < size; i++) {
			int pos = amfs[i].getPos();
			maxMinArray[i] = maxMinArrayClone[pos];
			maxArray[i] = maxArrayClone[pos];
			xArray[i] = xArrayClone[pos];
			
		}

		for (int k = 0; k < size - 1; k++) {

			ArrayList<Integer> overlapLower = new ArrayList<Integer>();
			ArrayList<Integer> overlapHigher = new ArrayList<Integer>();

			if (maxMinArray[k][0] > maxMinArray[k + 1][1]) {
				for (int l = 0; l < xArray[k].getSize(); l++) {
					if (xArray[k].getDouble(l) > 0.99 * maxMinArray[k + 1][1]) {
						overlapLower.add(l);
					}
				}
			}

			if (overlapLower.size() > 0) {
				
				double ma =  xArray[k].getDouble(Collections.max(overlapLower));
				double mi =  xArray[k].getDouble(Collections.min(overlapLower));
				
				if(ma<mi){
					mi=ma;
				}
				
				overlap[k][1] = mi;
				overlap[k][0] = maxMinArray[k][0];

				overlapClone[k][1] = (double) mi;
				overlapClone[k][0] = (double) maxMinArray[k][0];
			} else {
				overlap[k][1] = 1000000;
				overlap[k][0] = 1000001;

				overlapClone[k][1] = 1000000;
				overlapClone[k][0] = 1000001;
			}

		}
		
		

		for (int i = 1; i < size; i++) {
			int pos = amfs[i].getPos();
			// maxMinArray[i] = maxMinArrayClone[pos];
			if(!overlapClone[i-1].equals(new double[] {0.0,0.0}) && pos!=0){
				overlap[pos-1] = overlapClone[i-1];
			}
		}

		return overlap;
	}
}