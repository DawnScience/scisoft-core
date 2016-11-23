/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.sxrdrods;

import org.dawnsci.surfacescatter.ExampleModel;
import org.dawnsci.surfacescatter.TwoDFitting;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.processing.surfacescattering.BackgroundSetting;
import uk.ac.diamond.scisoft.analysis.processing.surfacescattering.BoxSlicer;

public class TestImageGenerator extends AbstractOperation<RodScanPolynomial1DModel, OperationData> {

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.sxrdrods.TestImageGenerator";
	}
		
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO ;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO ;
	}
		
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		

		ExampleModel m = new ExampleModel();
		
		RectangularROI box= new RectangularROI();
		box.setLengths(10, 10);
		box.setPoint(45, 45);
		int[][] lenPt = new int[2][];
		lenPt[0] = new int[] {10,10};
		lenPt[1] = new int[] {45,45};
		m.setBox(box);
		m.setBoundaryBox(10);
		m.setFitPower(FitPower.TWO);
		m.setLenPt(lenPt);
		
		IDataset input1 = (IDataset) DatasetFactory.ones(new int [] {100,100});
		
		double a = 100;
		double x0 = 50;
		double y0 = 50;
		double sigmaX = 2;
		double sigmaY = 2;
			
		
		///running over x direction:
		for (int x =0; x< input1.getShape()[0]; x++){
			///running over y direction:
			for (int y =0; y< input1.getShape()[1]; y++){
				double xTerm = ((x - x0)*(x - x0))/(2*sigmaX*sigmaX);
				double yTerm = ((y - y0)*(y - y0))/(2*sigmaY*sigmaY);
				double z = a*Math.exp(-(xTerm + yTerm));
				try{
					input1.set(z, x, y);
				}
				catch(Error e){
					System.out.println("x:  " + x + "   y:  " +y);
				}
			}
		}
		
		IDataset output = TwoDFitting.TwoDFitting1(input1, m);
		
		return new OperationData(output);

	}
//

}
