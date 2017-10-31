package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class PolynomialOverlapSXRD {


	
	public static double[][] correctionRatio3(Dataset[] xLowerDataset, 
											  Dataset yLowerDataset,
											  Dataset[] xHigherDataset, 
											  Dataset yHigherDataset, 
											  double attenuationFactor, 
											  int power) {

		Polynomial polyFitLower = Fitter.polyFit(xLowerDataset, yLowerDataset, 1e-5,power);
		Polynomial polyFitHigher = Fitter.polyFit(xHigherDataset, yHigherDataset, 1e-5,power);
		
		double[] lowerParams = polyFitLower.getParameterValues();
		double[] higherParams = polyFitLower.getParameterValues();
		
		double minXLower = minValue(xLowerDataset);
		double maxXLower = maxValue(xLowerDataset);
		
		double minXHigher = minValue(xHigherDataset);
		double maxXHigher = maxValue(xHigherDataset);
		
		int numberOfTestPoints = 100;
		
		Dataset xLowerTestArray= DatasetFactory.zeros(new int[] {numberOfTestPoints}, Dataset.ARRAYFLOAT64);
		Dataset xHigherTestArray = DatasetFactory.zeros(new int[] {numberOfTestPoints}, Dataset.ARRAYFLOAT64);
		
		for(int i =0 ; i <numberOfTestPoints ; i++){
						
			double p = minXLower + (i)*((maxXLower - minXLower)/numberOfTestPoints);
			xLowerTestArray.set(p, i);
			
			double q = minXHigher+ (i)*((maxXHigher- minXHigher)/numberOfTestPoints);
			xHigherTestArray.set(q, i);
		}
		
		Dataset calculatedValuesHigher = polyFitHigher.calculateValues(xHigherTestArray);
		Dataset calculatedValuesLower = polyFitLower.calculateValues(xLowerTestArray);
		
		double runningSum = 0;
		double runningSumDelta = 0;
		double runningSumNorm = 0;
		
		
		for(int j = 0; j <numberOfTestPoints ; j++){
			
			double r = (calculatedValuesLower.getDouble(j) / calculatedValuesHigher.getDouble(j));
			runningSum += r;
			
			double rDelta = r * Math.pow(Math.pow(r, 2) + Math.pow(Math.pow(calculatedValuesLower.getDouble(j), 0.5)/Math.pow(calculatedValuesHigher.getDouble(j), 0.5),2),0.5);
			
			rDelta = 1/Math.pow(rDelta,2);
			
			runningSumNorm +=rDelta;
			runningSumDelta += r*rDelta;
			
		}
		
		double temp = runningSumDelta/runningSumNorm;
		
		double[] correction  = new double[] {(temp)*attenuationFactor};
		
		double[] correctionDelta  = new double[] {(runningSum/numberOfTestPoints)*attenuationFactor};
		
		return new double[][] {lowerParams, higherParams, correction};
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
