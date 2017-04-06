package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class SplineInterpolationTracker {

	public static ArrayList<double[][]> interpolatedTrackerLenPtArray
									   (ArrayList<double[][]> boxes, 
										Dataset xValues
										){
		
		Dataset xpts = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset ypts = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset xlens = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset ylens = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset xVals = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		
		for(int ty = 0; ty<boxes.size(); ty++){

			double[][] consideredBox = boxes.get(ty);
			
			xpts.set(consideredBox[1][0], ty);
			ypts.set(consideredBox[1][1], ty);
			
			xlens.set(consideredBox[0][0], ty);
			ylens.set(consideredBox[0][1], ty);
			
			xVals.set(consideredBox[2][1], ty);
		}
		
		ArrayList<double[][]> output =new ArrayList<>();
				
		Dataset calculatedXptsDat = Interpolation1D.splineInterpolation(xVals, xpts, xValues);
		Dataset calculatedYptsDat = Interpolation1D.splineInterpolation(xVals, ypts, xValues);
		
		Dataset calculatedXlenDat = Interpolation1D.splineInterpolation(xVals, xlens, xValues);
		Dataset calculatedYlenDat = Interpolation1D.splineInterpolation(xVals, ylens, xValues);
		
		for(int lj = 0; lj<xValues.getSize(); lj++){
			double[][] localOutput = new double[2][];
			
			localOutput[0] = new double[2];
			localOutput[1] = new double[2];
			
			localOutput[0][0] = calculatedXlenDat.getDouble(lj);
			localOutput[0][1] = calculatedYlenDat.getDouble(lj);
			
			localOutput[1][0] = calculatedXptsDat.getDouble(lj);
			localOutput[1][1] = calculatedYptsDat.getDouble(lj);
			
			output.add(localOutput);
		}
		
		return output;
	}
	
	

	public  ArrayList<double[][]> interpolatedTrackerLenPtArray1
										   (ArrayList<double[][]> boxes, 
											Dataset xValues
											){
			
			Dataset xpts = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
			Dataset ypts = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
			Dataset xlens = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
			Dataset ylens = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
			Dataset xVals = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
			
			for(int ty = 0; ty<boxes.size(); ty++){

				double[][] consideredBox = boxes.get(ty);
				
				xpts.set(consideredBox[1][0], ty);
				ypts.set(consideredBox[1][1], ty);
				
				xlens.set(consideredBox[0][0], ty);
				ylens.set(consideredBox[0][1], ty);
				
				xVals.set(consideredBox[2][1], ty);
			}
			
			ArrayList<double[][]> output =new ArrayList<>();
					
			Dataset calculatedXptsDat = splineInterpolationExtrapolation(xVals, xpts, xValues);
			Dataset calculatedYptsDat = splineInterpolationExtrapolation(xVals, ypts, xValues);
			
			Dataset calculatedXlenDat = splineInterpolationExtrapolation(xVals, xlens, xValues);
			Dataset calculatedYlenDat = splineInterpolationExtrapolation(xVals, ylens, xValues);
			
			for(int lj = 0; lj<xValues.getSize(); lj++){
				double[][] localOutput = new double[2][];
				
				localOutput[0] = new double[2];
				localOutput[1] = new double[2];
				
				localOutput[0][0] = calculatedXlenDat.getDouble(lj);
				localOutput[0][1] = calculatedYlenDat.getDouble(lj);
				
				localOutput[1][0] = calculatedXptsDat.getDouble(lj);
				localOutput[1][1] = calculatedYptsDat.getDouble(lj);
				
				output.add(localOutput);
			}
			
			return output;
		}
	
	
	public Dataset splineInterpolationExtrapolation (Dataset xValues, Dataset yValues, Dataset targetValues){
		
		////targetValues may contain values outside of the range of xValues. We need to sort them out. 
		
		ArrayList<Double> aboveRangeValues = new ArrayList<>();
		ArrayList<Double> belowRangeValues = new ArrayList<>();
		ArrayList<Double> inRangeValues = new ArrayList<>();
		
		Dataset output = DatasetFactory.zeros(DoubleDataset.class, new int[] {targetValues.getSize()});
		
		double lowestValueOfXValues = findDatasetMin(xValues);
		System.out.println( "lowestValueOfXValues: " + lowestValueOfXValues);
		double highestValueOfXValues = findDatasetMax(xValues);
		System.out.println( "highestValueOfXValues: " + highestValueOfXValues);
		
		double secondLowestValueOfXValues = findDataset2ndToMin(xValues);
		System.out.println( "secondLowestValueOfXValues: " + secondLowestValueOfXValues);				
		double secondHighestValueOfXValues = findDataset2ndToMax(xValues);
		System.out.println( "secondHighestValueOfXValues: " + secondHighestValueOfXValues);		
		
		double highEndLastStep = highestValueOfXValues - secondHighestValueOfXValues;
		double highEndLastStepIncrement = highEndLastStep/100;
		
		double lowEndLastStep = secondLowestValueOfXValues - lowestValueOfXValues;
		double lowEndLastStepIncrement = lowEndLastStep/100;
		
		
		for(int i =0 ; i < targetValues.getSize(); i++){
			
				if(targetValues.getDouble(i) <= lowestValueOfXValues){
					belowRangeValues.add(targetValues.getDouble(i));
				}
				
				else if(targetValues.getDouble(i) >= highestValueOfXValues){
					aboveRangeValues.add(targetValues.getDouble(i));
				}
				else{
					inRangeValues.add(targetValues.getDouble(i));
				}
			
		}
			
		Dataset inRangeInput = DatasetFactory.createFromList(inRangeValues);	
		
		Dataset aboveRangeInput = DatasetFactory.createFromList(aboveRangeValues);	
		Dataset belowRangeInput = DatasetFactory.createFromList(belowRangeValues);	
		
		inRangeInput.sort(0);
		try{
			aboveRangeInput.sort(0);
			belowRangeInput.sort(0);
		}
		catch(Exception d){
			
		}
		
		Dataset inRangeOutput = DatasetFactory.zeros(inRangeInput, Dataset.ARRAYFLOAT64);
		
		try{
			inRangeOutput = Interpolation1D.splineInterpolation( xValues, yValues,  inRangeInput);
		}
		catch(Exception h){
			System.out.println(h.getMessage());
			System.out.println(h.getStackTrace());
		}
		
		
		
		try{
		@SuppressWarnings("deprecation")

			Dataset higherExtrapolationRange = DatasetFactory.createRange(DoubleDataset.class,
																		  (highestValueOfXValues - (20*highEndLastStepIncrement)),
																		  highestValueOfXValues,
																		  highEndLastStepIncrement
																		  );
		
		
		
		
		
		higherExtrapolationRange.sort(0);
		
		Dataset[] higherExtrapolationRangeArray = new Dataset[] {higherExtrapolationRange};
		
		Polynomial polyFitAboveRange = Fitter.polyFit(higherExtrapolationRangeArray, 
												Interpolation1D.splineInterpolation(xValues, yValues, higherExtrapolationRange), 
												1e-30, 
												1);
		
		Dataset test = Interpolation1D.splineInterpolation(xValues, yValues, higherExtrapolationRange);
		
		Dataset calculatedAboveRangeValues = polyFitAboveRange.calculateValues(aboveRangeInput);
		
		
		DoubleDataset lowerExtrapolationRange = DatasetFactory.createRange(DoubleDataset.class,
																	lowestValueOfXValues,
				  													lowestValueOfXValues + (20*lowEndLastStepIncrement),
				  													lowEndLastStepIncrement
				  													);

//		lowerExtrapolationRange.sort(0);
		
		Dataset[] lowerExtrapolationRangeArray = new Dataset[] {lowerExtrapolationRange};

//		Dataset test1 = Interpolation1D.splineInterpolation(xValues, yValues, lowerExtrapolationRange);
		
		
		Polynomial polyFitBelowRange = Fitter.polyFit(lowerExtrapolationRangeArray, 
													  Interpolation1D.splineInterpolation(xValues, yValues, lowerExtrapolationRange), 
													  1e-30, 
													  1);	

//		Polynomial Test = Fitter.
		
		Dataset calculatedBelowRangeValues = polyFitBelowRange.calculateValues(belowRangeInput);
		
		Dataset[] fullRange = new Dataset[] {calculatedBelowRangeValues,
											 inRangeOutput,
											 calculatedAboveRangeValues
										 	 };
		
		
		output = DatasetUtils.concatenate(fullRange, 0);
		
		return output;
		
		}
		catch(Exception o){
			System.out.println(o.getMessage());
			System.out.println(o.getStackTrace());
			return null;
		}
		
	}
		
		
		
	public double findDatasetMin(Dataset input){
		
		
		double min = input.getDouble(0);
		
		for(int i= 0; i <input.getSize(); i++){
			
			if(input.getDouble(i) <= min){
				min = input.getDouble(i);
			}
		}
		
		return min;
	
	}
	
	public double findDatasetMax(Dataset input){
		
		
		double max = input.getDouble(0);
		
		for(int i= 0; i <input.getSize(); i++){
			
			if(input.getDouble(i) >= max){
				max = input.getDouble(i);
			}
		}
		
		return max;
	
	}
	
	public double findDataset2ndToMin(Dataset input){
		
	    double smallest = input.getDouble(0);
	    double secondSmallest = input.getDouble(1);
	    for (int i = 0; i < input.getSize(); i++) {
	        if(input.getDouble(i)==smallest){
//	          secondSmallest=smallest;
	        } else if (input.getDouble(i) < smallest) {
	            secondSmallest = smallest;
	            smallest = input.getDouble(i);
//	            return secondSmallest;
	        } else if (input.getDouble(i) < secondSmallest) {
	            secondSmallest = input.getDouble(i);
	        }
	    }

	    return secondSmallest;
	
	}
	
	public double findDataset2ndToMax(Dataset input){
		
	    double largest = input.getDouble(0);
	    double secondLargest = input.getDouble(1);
	    for (int i = 0; i < input.getSize(); i++) {
	        if(input.getDouble(i)==largest){
//	          secondLargest=largest;
	        } else if (input.getDouble(i) > largest) {
	            secondLargest = largest;
	            largest = input.getDouble(i);
//	    	    return secondLargest;
	        } else if (input.getDouble(i) > secondLargest) {
	            secondLargest = input.getDouble(i);
	        }
	    }

	    return secondLargest;
	
	}
	
	
}
	
	
	
	
