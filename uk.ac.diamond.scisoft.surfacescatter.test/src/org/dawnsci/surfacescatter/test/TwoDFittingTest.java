package org.dawnsci.surfacescatter.test;

import static org.junit.Assert.*;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.ExampleModel;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Test;

public class TwoDFittingTest {

	public Dataset generateGaussian2DImage(Dataset input, double a, double x0, double y0, double sigmaX, double sigmaY){
		
		Dataset output = DatasetFactory.ones(input);
		
		///running over x direction:
		for (int x =0; x< input.getShape()[0]; x++){
			///running over y direction:
			for (int y =0; y< input.getShape()[0]; y++){
				double xTerm = ((x - x0)*(x - x0))/(2*sigmaX*sigmaX);
				double yTerm = ((y - y0)*(y - y0))/(2*sigmaY*sigmaY);
				double z = a*Math.exp(-(xTerm + yTerm));
				output.set(z, x, y);
			}
		}
		
		return output;
	}
	
	
	
	@Test
	public void testTwoDFitting1() {
		
		ExampleModel m = new ExampleModel();
		
		RectangularROI box= new RectangularROI();
		box.setLengths(10, 10);
		box.setPoint(45, 45);
		int[][] LenPt = new int[2][];
		m.setBox(box);
		m.setBoundaryBox(10);
		m.setFitPower(FitPower.TWO);
		
		
	}

}
