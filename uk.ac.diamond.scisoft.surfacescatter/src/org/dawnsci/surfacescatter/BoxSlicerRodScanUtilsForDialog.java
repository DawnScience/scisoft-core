/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;


public class BoxSlicerRodScanUtilsForDialog {
	
	
	private static int DEBUG = 0;
	/*
	 * Slices n dices an image as required to produce background data.
	 * 
	 */

	public static Dataset rOIBox(IDataset input , int[] len, int[] pt) throws OperationException{ 
	//// This creates in1, which is the ROI of interest
		SliceND slice1 = new SliceND(input.getShape());
		slice1.setSlice(1, pt[0], pt[0] + len[0], 1);
		slice1.setSlice(0, pt[1], pt[1] + len[1], 1);
		IDataset small1 = input.getSlice(slice1);
		Dataset in1 = DatasetUtils.convertToDataset(small1);
		return in1;
	}
	
	public static Dataset rOIHalfBox(IDataset input , int[] len, int[] pt) throws OperationException{ 
		//// This creates in1, which is the ROI of interest
			SliceND slice1 = new SliceND(input.getShape());
			slice1.setSlice(1, (int) Math.round((pt[0]-0.5*len[0])), (int) Math.round(pt[0] + 0.5*len[0]), 1);
			slice1.setSlice(0, (int) Math.round(pt[1]-0.5*len[1]), (int) Math.round(pt[1] + 0.5*len[1]), 1);
			IDataset small1 = input.getSlice(slice1);
			Dataset in1 = DatasetUtils.convertToDataset(small1);
			return in1;
		}

	
	@SuppressWarnings("incomplete-switch")
	public static IDataset iAboveOrLeftBox (IDataset input , 
			int[] len, int[] pt, int boundaryBox, Methodology direction) throws OperationException{
		   
		switch (direction){
		   	case Y:
		   		SliceND slice2 = new SliceND(input.getShape());
//				slice2.setSlice(0, pt[0], pt[0] + len[0], 1);
//				slice2.setSlice(1, pt[1] + len[1], pt[1] + len[1] + boundaryBox, 1);
		   		slice2.setSlice(1,  pt[0], pt[0]+ len[0], 1);
				slice2.setSlice(0, pt[1]+ len[1], pt[1] + len[1] + boundaryBox, 1);
				IDataset small2 = input.getSlice(slice2);
				return small2;
		   	case X:
		   		SliceND slice4 = new SliceND(input.getShape());
				slice4.setSlice(1, pt[0] + len[0], pt[0]+ len[0] + boundaryBox, 1);
				slice4.setSlice(0, pt[1], pt[1] + len[1], 1);
				IDataset small4 = input.getSlice(slice4);
				return small4;
		   }
		   return null;
	}
	
	@SuppressWarnings("incomplete-switch")
	public static IDataset iBelowOrRightBox (IDataset input , 
			int[] len, int[] pt, int boundaryBox, Methodology direction) throws OperationException{
		   switch (direction){
		   	case Y:
		   		SliceND slice0 = new SliceND(input.getShape());
//				slice0.setSlice(0, pt[0], pt[0] + len[0], 1);
//				slice0.setSlice(1, pt[1] - boundaryBox, pt[1], 1);
		   		slice0.setSlice(0, pt[1] - boundaryBox, pt[1], 1);
				slice0.setSlice(1, pt[0], pt[0] + len[0], 1);
				IDataset small0 = input.getSlice(slice0);
				return small0;
		   	case X:
		   		SliceND slice3 = new SliceND(input.getShape());
				slice3.setSlice(1, pt[0] - boundaryBox, pt[0], 1);
				slice3.setSlice(0, pt[1], pt[1] +len[1], 1);
				IDataset small3 = input.getSlice(slice3);
				return small3;
		   }
		   return null;
	}	
	

	public static Dataset regionOfRegard (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		   
	
		   	SliceND slice0 = new SliceND(input.getShape());
			slice0.setSlice(1, pt[0]-boundaryBox, pt[0]+len[0]+boundaryBox, 1);
			slice0.setSlice(0, pt[1]-boundaryBox, pt[1] + len[1] + boundaryBox, 1);
			IDataset small0 = input.getSlice(slice0);
			Dataset small0d = DatasetUtils.cast(DoubleDataset.class, small0);
			
		
		
		return small0d;
	}
	
	public static Dataset regionOfRegardHalf (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		   
	
		   	SliceND slice0 = new SliceND(input.getShape());
			slice0.setSlice(1, (int) Math.round(pt[0]-0.5*len[0]-boundaryBox), (int) Math.round(pt[0]+0.5*len[0]+boundaryBox), 1);
			slice0.setSlice(0, (int) Math.round(pt[1]-0.5*len[1]-boundaryBox), (int) Math.round(pt[1] + 0.5*len[1] + boundaryBox), 1);
			IDataset small0 = input.getSlice(slice0);
			Dataset small0d = DatasetUtils.cast(DoubleDataset.class, small0);
			
		
		
		return small0d;
	}
	
	public static List<Dataset> coordMesh(IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		
		Dataset regionOfRegard = regionOfRegard(input, len, pt, boundaryBox);
		
		Dataset x = DatasetFactory.createRange(DoubleDataset.class, regionOfRegard.getShapeRef()[0]);
		Dataset y = DatasetFactory.createRange(DoubleDataset.class, regionOfRegard.getShapeRef()[1]);
		
		List<Dataset> meshGrid = DatasetUtils.meshGrid(x,y);
		
		return meshGrid;
	}
	

	public static Dataset[] LeftRightTopBottomBoxes (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		
		
		Dataset regionOfRegard = regionOfRegard(input, len, pt, boundaryBox);
		
		int noOfPoints = (len[1] + 2*boundaryBox)*(len[0] +2*boundaryBox) - len[1]*len[0];
		
		
		DoubleDataset xset = DatasetFactory.zeros(noOfPoints);
		DoubleDataset yset = DatasetFactory.zeros(noOfPoints);
		DoubleDataset zset = DatasetFactory.zeros(noOfPoints);
		
		int l =0;
				
		for (int i =0; i<(len[1]+2*boundaryBox); i++){
			for (int j = 0; j<(len[0] + 2*boundaryBox);j++){

				if ((j<boundaryBox || j>=(boundaryBox+len[0]))||(i<boundaryBox || i>=(boundaryBox+len[1]))){
//					if(DEBUG ==1 && i == 83){
//						System.out.println("What's wrong?");
//					}
					xset.set(i, l);
					yset.set(j, l);
					try{
						zset.set(regionOfRegard.getDouble(i, j), l);
					}
					catch(ArrayIndexOutOfBoundsException e){
						
						Dataset[] errorDatArray = new Dataset[1];
						Dataset errorDat = DatasetFactory.zeros(new int [] {2,2});
						IndexIterator it1 = ((Dataset) errorDat).getIterator();
						
						while (it1.hasNext()) {
							double q = ((Dataset) errorDat).getElementDoubleAbs(it1.index);
							if (q <= 0)
								((Dataset) errorDat).setObjectAbs(it1.index, 0.1);
						}
						
						errorDatArray[0] = errorDat;
						return errorDatArray;
					}
						l++;
				}
				else{
					
					}
				
				}
		}	
		
		Dataset[] output = new Dataset[3];
		
		output[0] = xset;
		output[1] = yset;
		output[2] = zset;
		

	return output;
	}	
	
	public static Dataset[] LeftRightTopBottomBoxesForGaussian (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		
		
		Dataset regionOfRegard = regionOfRegard(input, len, pt, boundaryBox);
		
		int noOfPoints = (len[1] + 2*boundaryBox)*(len[0] +2*boundaryBox) - len[1]*len[0];
		
		DoubleDataset xset = DatasetFactory.createRange(len[0]+2*boundaryBox);
		DoubleDataset yset = DatasetFactory.createRange(len[1]+2*boundaryBox);
		DoubleDataset zset = DatasetFactory.zeros(DoubleDataset.class,
												  new int[] {(len[1] +2*boundaryBox),(len[0] + 2*boundaryBox)});
		
				
		for (int i =0; i<(len[1]+2*boundaryBox); i++){
			for (int j = 0; j<(len[0] + 2*boundaryBox);j++){

				if ((j<boundaryBox || j>=(boundaryBox+len[0]))||(i<boundaryBox || i>=(boundaryBox+len[1]))){

					try{
						zset.set(regionOfRegard.getDouble(i, j), i, j);
						
					}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("i:  " + i + "      j: " + j);;
						Dataset[] errorDatArray = new Dataset[1];
						Dataset errorDat = DatasetFactory.zeros(new int [] {2,2});
						IndexIterator it1 = ((Dataset) errorDat).getIterator();
						
						while (it1.hasNext()) {
							double q = ((Dataset) errorDat).getElementDoubleAbs(it1.index);
							if (q <= 0)
								((Dataset) errorDat).setObjectAbs(it1.index, 0.1);
						}
						
						errorDatArray[0] = errorDat;
						return errorDatArray;
					}	
				}
				else{
					zset.set(Double.NaN, i, j);
					}		
				}
		}	
		
		Dataset[] output = new Dataset[3];
		
		output[0] = xset;
		output[1] = yset;
		output[2] = zset;
		

	return output;
	}	
	
	
	
	public static Dataset[] LeftRightTopBottomHalfBoxes (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		
		
		Dataset regionOfRegard = regionOfRegardHalf(input, len, pt, boundaryBox);
		
		int noOfPoints = (len[1] + 2*boundaryBox)*(len[0] +2*boundaryBox) - len[1]*len[0];
		
		
		DoubleDataset xset = DatasetFactory.zeros(noOfPoints);
		DoubleDataset yset = DatasetFactory.zeros(noOfPoints);
		DoubleDataset zset = DatasetFactory.zeros(noOfPoints);
		
		int l =0;
				
		for (int i =0; i<(len[1]+2*boundaryBox); i++){
			for (int j = 0; j<(len[0] + 2*boundaryBox);j++){

				if ((j<boundaryBox || j>=(boundaryBox+len[0]))||(i<boundaryBox || i>=(boundaryBox+len[1]))){
					xset.set(i, l);
					yset.set(j, l);
					try{
						zset.set(regionOfRegard.getDouble(i, j), l);
					}
					catch(ArrayIndexOutOfBoundsException e){
						
						Dataset[] errorDatArray = new Dataset[1];
						Dataset errorDat = DatasetFactory.zeros(new int [] {2,2});
						Maths.add(errorDat, 0.1);
						errorDatArray[0] = errorDat;
						return errorDatArray;
					}
					l++;
				}
				else{
					}
				}
		}	
		
		Dataset[] output = new Dataset[3];
		
		output[0] = xset;
		output[1] = yset;
		output[2] = zset;
		

	return output;
	}
	
	
	
	
	public static Dataset[] subRange (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		   
		Dataset[] ranges = LeftRightTopBottomBoxes(input, len, pt, boundaryBox);
		
		int subRangeNo = (int) 0.01*ranges[0].getShape()[0];
		
		if (subRangeNo<100){
			subRangeNo = 100;
		}
		
		DoubleDataset xSub = DatasetFactory.zeros(subRangeNo);
		DoubleDataset ySub = DatasetFactory.zeros(subRangeNo);
		DoubleDataset zSub = DatasetFactory.zeros(subRangeNo);
		
		Random rng = new Random(); 
		
		Set<Integer> subSet = new HashSet<Integer>();
		
		while (subSet.size() < subRangeNo){
		    Integer next = rng.nextInt(subRangeNo) + 1;
		    subSet.add(next);
		}
		
		java.util.Iterator<Integer> itr = subSet.iterator();
		
		while(itr.hasNext()){
			int p = itr.next();
			xSub.set(ranges[0].getObject(itr.next()),p);
			ySub.set(ranges[1].getObject(itr.next()),p);
			zSub.set(ranges[2].getObject(itr.next()),p);
		}
		
		Dataset[] output = new Dataset[3];
		
		output[0] = xSub;
		output[1] = ySub;
		output[2] = zSub;
		
	return output;
	}	
	
	public static Dataset[] subRange (IDataset input , 
			int[] len, int[] pt, int boundaryBox, int size) throws OperationException{
		
		Dataset[] ranges = LeftRightTopBottomBoxes(input, len, pt, boundaryBox);
		
		int subRangeNo = (int) ((int) size*0.01*ranges[0].getShape()[0]);
		
		if (subRangeNo<(size*100)){
			subRangeNo = size*100;
		}
		
		DoubleDataset xSub = DatasetFactory.zeros(subRangeNo);
		DoubleDataset ySub = DatasetFactory.zeros(subRangeNo);
		DoubleDataset zSub = DatasetFactory.zeros(subRangeNo);
		
		Random rng = new Random(); 
		
		Set<Integer> subSet = new HashSet<Integer>();
		
		while (subSet.size() < subRangeNo){
		    Integer next = rng.nextInt(subRangeNo) + 1;
		    subSet.add(next);
		}
		
		java.util.Iterator<Integer> itr = subSet.iterator();
		
		while(itr.hasNext()){
			int p = itr.next();
			xSub.set(ranges[0].getObject(itr.next()),p);
			ySub.set(ranges[1].getObject(itr.next()),p);
			zSub.set(ranges[2].getObject(itr.next()),p);
		}
		
		Dataset[] output = new Dataset[3];
		
		output[0] = xSub;
		output[1] = ySub;
		output[2] = zSub;
	
	return output;
	}
	
	public static Dataset[] subRangeDownSample (IDataset input , 
			int[] len, int[] pt, int boundaryBox, int size, String enc) throws OperationException{
		   
		
		Dataset[] ranges = LeftRightTopBottomBoxes(input, len, pt, boundaryBox);
		
		int subRangeNo = (int) ((int) size*0.01*ranges[0].getShape()[0]);
		
		if (subRangeNo<(size*100)){
			subRangeNo = size*100;
		}
		
		DoubleDataset xSub = DatasetFactory.zeros(subRangeNo);
		DoubleDataset ySub = DatasetFactory.zeros(subRangeNo);
		DoubleDataset zSub = DatasetFactory.zeros(subRangeNo);
		
		Random rng = new Random(); 
		
		Set<Integer> subSet = new HashSet<Integer>();
		
		while (subSet.size() < subRangeNo){
		    Integer next = rng.nextInt(subRangeNo) + 1;
		    subSet.add(next);
		}
		
		java.util.Iterator<Integer> itr = subSet.iterator();
		
		while(itr.hasNext()){
			int p = itr.next();
			xSub.set(ranges[0].getObject(itr.next()),p);
			ySub.set(ranges[1].getObject(itr.next()),p);
			zSub.set(ranges[2].getObject(itr.next()),p);
		}
		
		Dataset[] output = new Dataset[3];
		
		output[0] = xSub;
		output[1] = ySub;
		output[2] = zSub;
	
	return output;
	}
	

	
	public static DoubleDataset weightingMask (IDataset input , 
			int[] len, int[] pt, int boundaryBox) throws OperationException{
		   
	
			Dataset regionOfRegard = regionOfRegard(input, len, pt, boundaryBox);

			DoubleDataset mask = DatasetFactory.zeros(new int[] {regionOfRegard.getShape()[0], regionOfRegard.getShape()[1]});
					

			for (int i =0; i<len[1]+2*boundaryBox; i++){
				for (int j = 0; j<len[0] + 2*boundaryBox;j++){
					
					if ((i<boundaryBox || i>=boundaryBox+len[1]) || (j<boundaryBox || j>=boundaryBox+len[0])){
						mask.set(i,j, 1);
					}
				}	
			}
			
		return mask; 
		}
	
	
	public static double[] backgroundBoxForDisplay(int[][] lenpt, 
												   int bgBox, 
												   AnalaysisMethodologies.Methodology am){
		
		double[] bgLen = new double[] {(double) lenpt[0][0] , (double) lenpt[0][1]}; 
		double[] bgPt = new double[] {(double) lenpt[1][0] , (double) lenpt[1][1]};
		
		switch(am){
//			case  TWOD_TRACKING:
//				bgLen[0] = bgLen[0] + 2*bgBox+1;
//				bgLen[1] = bgLen[1] + 2*bgBox+1;
//				
//				bgPt[0] = bgPt[0] - bgBox+1;
//				bgPt[1] = bgPt[1] - bgBox+1;
//				
//				return new double[] {bgPt[0], bgPt[1], bgLen[0], bgLen[1], 0};
			
			case  TWOD:
				bgLen[0] = bgLen[0] + 2*bgBox+1;
				bgLen[1] = bgLen[1] + 2*bgBox+1;
				
				bgPt[0] = bgPt[0] - bgBox;
				bgPt[1] = bgPt[1] - bgBox;
				
				return new double[] {bgPt[0], bgPt[1], bgLen[0], bgLen[1], 0};
				
			case  X:
				bgLen[0] = bgLen[0] + 2*bgBox+1;
				bgLen[1] = bgLen[1];
				
				bgPt[0] = bgPt[0] - bgBox;
				bgPt[1] = bgPt[1];
				
				return new double[] {bgPt[0], bgPt[1], bgLen[0], bgLen[1], 0};
				
			case  Y:
				bgLen[0] = bgLen[0];
				bgLen[1] = bgLen[1] + 2*bgBox+1;
				
				bgPt[0] = bgPt[0];
				bgPt[1] = bgPt[1] - bgBox;
				
				return new double[] {bgPt[0], bgPt[1], bgLen[0], bgLen[1], 0};
				
			case  OVERLAPPING_BACKGROUND_BOX:
				
				return new double[] {0, 0, 0, 0, 0};
				
				
			case  SECOND_BACKGROUND_BOX:
				
				return new double[] {0, 0, 0, 0, 0};
				
		}
		
		return null;
	}
}
//TEST 