package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class PolynomialOverlap {

	
	public static double correctionRatio(Dataset[] xLowerDataset, Dataset yLowerDataset,
			Dataset[] xHigherDataset, Dataset yHigherDataset, double attenuationFactor) {
	
	
		Polynomial polyFitLower = Fitter.polyFit(xLowerDataset, yLowerDataset, 1e-5,4);
		Polynomial polyFitHigher = Fitter.polyFit(xHigherDataset, yHigherDataset, 1e-5,4);
		
		Dataset calculatedValuesHigher = polyFitHigher.calculateValues(xLowerDataset);
		Dataset calculatedValuesLower = polyFitLower.calculateValues(xLowerDataset);
		
		Dataset correctionsRatioDataset = Maths.divide(calculatedValuesLower.sum(), 
				calculatedValuesHigher.sum());
		
		
		double correction = ((double) correctionsRatioDataset.sum())/((double) correctionsRatioDataset.getSize())*attenuationFactor;
		
		
		return correction;
	}
	
	
	public static double[] extrapolatedLocation(double desiredl, 
												Dataset lValues, 
												Dataset xValues,
												Dataset yValues,
												int[] len,
												int fitPower){
		
		
		Dataset[] lValuesHolder = new Dataset[1];
		Dataset lValuesNullHolder = DatasetFactory.zeros(lValues, Dataset.ARRAYFLOAT64);
		Dataset[] lValuesHolder2 = new Dataset[1];
		Dataset lValuesNullHolder2 = DatasetFactory.zeros(lValues, Dataset.ARRAYFLOAT64);
		
		
		for(int t = 0; t<lValues.getSize(); t++){
			lValuesNullHolder.set(lValues.getDouble(t), lValues.getSize()-1-t);
		}
		
		lValuesHolder[0] = lValuesNullHolder;
		lValuesHolder2[0] = lValues;
		
		
		Polynomial polyFitYValues = Fitter.polyFit(lValuesHolder2, yValues, 1e-30,fitPower);
		Polynomial polyFitXValues = Fitter.polyFit(lValuesHolder2, xValues, 1e-30,fitPower);
		
		Dataset desiredlDat = DatasetFactory.zeros(1);
		desiredlDat.set(desiredl, 0);
		
		Dataset yValueDat = polyFitYValues.calculateValues(desiredlDat);
		Dataset xValueDat = polyFitXValues.calculateValues(desiredlDat);
		
		
		double yValue = yValueDat.getDouble(0);
		double xValue = xValueDat.getDouble(0);
		
		double[] output = new double[] {yValue, xValue, 
									    yValue, (xValue + len[1]), 
										(yValue +len[0]), xValue, 
										(yValue +len[0]), (xValue + len[1])}; 
		
		

		
		return output;
	}
	
	
	
		
}
