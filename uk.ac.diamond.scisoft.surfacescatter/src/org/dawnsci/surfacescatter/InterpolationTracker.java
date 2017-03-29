package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class InterpolationTracker {

	public static ArrayList<double[][]> interpolatedTrackerLenPtArray
									   (ArrayList<double[][]> boxes, 
										Dataset xValues
										){

		int fitPower = 1;
		
		int bs = boxes.size();
		
		Dataset xpts = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset ypts = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset xlens = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset ylens = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		Dataset xVals = DatasetFactory.zeros(new int[] {boxes.size()}, Dataset.ARRAYFLOAT64);
		
		Dataset[] xValsArray = new Dataset[] {xVals};
		
		for(int ty = 0; ty<boxes.size(); ty++){
			double[][] consideredBox = boxes.get(ty);
			
			xpts.set(consideredBox[1][0], ty);
			ypts.set(consideredBox[1][1], ty);
			
			xlens.set(consideredBox[0][0], ty);
			ylens.set(consideredBox[0][1], ty);
			
			xVals.set(consideredBox[2][1], ty);
		}
		
		ArrayList<double[][]> output =new ArrayList<>();
		
		Polynomial polyFitxpts = Fitter.polyFit(xValsArray, xpts, 1e-30,fitPower);
		Polynomial polyFitypts = Fitter.polyFit(xValsArray, ypts, 1e-30,fitPower);
		
		Polynomial polyFitxlens = Fitter.polyFit(xValsArray, xlens, 1e-30,fitPower);
		Polynomial polyFitylens = Fitter.polyFit(xValsArray, ylens, 1e-30,fitPower);
		
		Dataset calculatedXptsDat = polyFitxpts.calculateValues(xValues);
		Dataset calculatedYptsDat = polyFitypts.calculateValues(xValues);
		
		Dataset calculatedXlenDat = polyFitxlens.calculateValues(xValues);
		Dataset calculatedYlenDat = polyFitylens.calculateValues(xValues);
		
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
}
