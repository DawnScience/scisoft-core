/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.widgets.Display;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

/**
 * Cuts out the region of interest and fits it with a 2D polynomial background.
 */
public class TwoDFitting{
	
	private static Dataset output;
	private static Polynomial2D g2;
	
	public static Dataset TwoDFitting1(IDataset input, ExampleModel model){
		g2 = null;
		
		RectangularROI box = model.getBox();

		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];
		
		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);
	
		if (g2 == null)
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters())
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
	
		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomBoxes(input, len,
				pt, model.getBoundaryBox());
		
		Dataset offset = DatasetFactory.ones(fittingBackground[2].getShape(), Dataset.FLOAT64);
		
		Dataset intermediateFitTest = Maths.add(offset, fittingBackground[2]);
		Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
				AnalaysisMethodologies.toInt(model.getFitPower()), fittingBackground[0], fittingBackground[1]);
		
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
			DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, intermediateFitTest);
			double[] params = test.getData();
			
			DoubleDataset in1Background = g2.getOutputValues0(params, len, model.getBoundaryBox(),
					AnalaysisMethodologies.toInt(model.getFitPower()));
		
	
			IndexIterator it = in1Background.getIterator();
		
			while (it.hasNext()) {
				double v = in1Background.getElementDoubleAbs(it.index);
				if (v < 0)
					in1Background.setObjectAbs(it.index, 0);
			}
		
			Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
		
			pBackgroundSubtracted.setName("pBackgroundSubtracted");
		
			IndexIterator it1 = pBackgroundSubtracted.getIterator();
		
			while (it1.hasNext()) {
				double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
				if (q < 0)
					pBackgroundSubtracted.setObjectAbs(it1.index, 0);
			}
			
			output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
			
			output.setName("Region of Interest, polynomial background removed");
			
			}
		});
		return output;
	}
	

}