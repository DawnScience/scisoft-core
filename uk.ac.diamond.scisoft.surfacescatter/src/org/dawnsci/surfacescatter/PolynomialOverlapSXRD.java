package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class PolynomialOverlapSXRD {

	
	public static double correctionRatio(Dataset[] xLowerDataset, Dataset yLowerDataset,
			Dataset[] xHigherDataset, Dataset yHigherDataset, double attenuationFactor, int power) {

		Polynomial polyFitLower = Fitter.polyFit(xLowerDataset, yLowerDataset, 1e-5,power);
		Polynomial polyFitHigher = Fitter.polyFit(xHigherDataset, yHigherDataset, 1e-5,power);
		
		Dataset calculatedValuesHigher = polyFitHigher.calculateValues(xHigherDataset);
		Dataset calculatedValuesLower = polyFitLower.calculateValues(xLowerDataset);
		
		Dataset calculatedValuesHigherAv = Maths.divide(calculatedValuesHigher.sum(), calculatedValuesHigher.getShape()[0]);
		Dataset calculatedValuesLowerAv = Maths.divide(calculatedValuesLower.sum(), calculatedValuesLower.getShape()[0]);
		
		Dataset correctionsRatioDataset = Maths.divide(calculatedValuesLowerAv, 
				calculatedValuesHigherAv);
		
		double correction = ((double) correctionsRatioDataset.sum())*attenuationFactor;
		
		return correction;
	}
	
	
	public static double correctionRatio1(Dataset[] xLowerDataset, Dataset yLowerDataset,
			Dataset[] xHigherDataset, Dataset yHigherDataset, double attenuationFactor, int power) {

		Polynomial polyFitLower = Fitter.polyFit(xLowerDataset, yLowerDataset, 1e-5,power);
		Polynomial polyFitHigher = Fitter.polyFit(xHigherDataset, yHigherDataset, 1e-5,power);
		
		double minXLower = minValue(xLowerDataset);
		double maxXLower = maxValue(xLowerDataset);
		
		double minXHigher = minValue(xHigherDataset);
		double maxXHigher = maxValue(xHigherDataset);
		
		int numberOfTestPoints = 100;
//		
//		Dataset[] xLowerTestArray = new Dataset[numberOfTestPoints];
//		Dataset[] xHigherTestArray = new Dataset[numberOfTestPoints];
		
		Dataset xLowerTestArray= DatasetFactory.zeros(new int[] {numberOfTestPoints}, Dataset.ARRAYFLOAT64);
		Dataset xHigherTestArray = DatasetFactory.zeros(new int[] {numberOfTestPoints}, Dataset.ARRAYFLOAT64);
		
		for(int i =0 ; i <numberOfTestPoints ; i++){
			
//			xLowerTestArray[i] = DatasetFactory.zeros(new int[] {1}, Dataset.ARRAYFLOAT64);
//			xHigherTestArray[i] = DatasetFactory.zeros(new int[] {1}, Dataset.ARRAYFLOAT64);
			
			double p = minXLower + (i+1)*((maxXLower - minXLower)/numberOfTestPoints);
			xLowerTestArray.set(p, i);
			
			double q = minXHigher+ (i+1)*((maxXHigher- minXHigher)/numberOfTestPoints);
			xHigherTestArray.set(q, i);
		}
		
		
		Dataset calculatedValuesHigher = polyFitHigher.calculateValues(xHigherTestArray);
		Dataset calculatedValuesLower = polyFitLower.calculateValues(xLowerTestArray);
		
		
//		System.out.println( "calculatedValuesLower.shape:  "  + calculatedValuesLower.getShape());
		double runningSum = 0;
		
		for(int j = 0; j <numberOfTestPoints ; j++){
			
			double r = (calculatedValuesLower.getDouble(j) / calculatedValuesHigher.getDouble(j));
			runningSum += r;
		}
		
		double correction = runningSum/numberOfTestPoints;
		
		return correction;
	}
	
	
	public static double maxValue(Dataset[] input){
		
		 double maxValue = input[0].getDouble(0);
		 
		 for (int i = 1; i < input.length; i++) {
		     if (input[i].getDouble(0) > maxValue) {
		         maxValue = input[i].getDouble(0);
		     }
		  }
		
		return maxValue;
	}
	
	public static double minValue(Dataset[] input){
		
		 double minValue = input[0].getDouble(0);
		 
		 for (int i = 1; i < input.length; i++) {
		     if (input[i].getDouble(0) < minValue) {
		         minValue = input[i].getDouble(0);
		     }
		  }
		
		return minValue;
	}
}
