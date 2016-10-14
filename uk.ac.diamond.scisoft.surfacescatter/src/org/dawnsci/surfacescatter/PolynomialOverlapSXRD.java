package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class PolynomialOverlapSXRD {

	
	public static double correctionRatio(Dataset[] xLowerDataset, Dataset yLowerDataset,
			Dataset[] xHigherDataset, Dataset yHigherDataset, double attenuationFactor, int power) {
	
	
		Polynomial polyFitLower = Fitter.polyFit(xLowerDataset, yLowerDataset, 1e-5,power);
		Polynomial polyFitHigher = Fitter.polyFit(xHigherDataset, yHigherDataset, 1e-5,power);
		
//		Dataset calculatedValuesHigher = polyFitHigher.calculateValues(xLowerDataset);
		Dataset calculatedValuesHigher = polyFitHigher.calculateValues(xHigherDataset);
		Dataset calculatedValuesLower = polyFitLower.calculateValues(xLowerDataset);
		
		Dataset calculatedValuesHigherAv = Maths.divide(calculatedValuesHigher.sum(), calculatedValuesHigher.getShape()[0]);
		Dataset calculatedValuesLowerAv = Maths.divide(calculatedValuesLower.sum(), calculatedValuesLower.getShape()[0]);
		
		Dataset correctionsRatioDataset = Maths.divide(calculatedValuesLowerAv, 
				calculatedValuesHigherAv);
		
		
		double correction = ((double) correctionsRatioDataset.sum())*attenuationFactor;
		
		
		return correction;
	}
	
}
