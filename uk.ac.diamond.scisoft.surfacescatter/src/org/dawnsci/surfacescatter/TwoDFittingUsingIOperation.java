/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

/**
 * Cuts out the region of interest and fits it with a 2D polynomial background.
 */
public class TwoDFittingUsingIOperation extends AbstractOperation<TwoDFittingModel, OperationData> {

	private static Dataset output;
	private static Polynomial2D g2;
	private static int DEBUG = 0;
	private IDataset in1Background;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.surfacescatter.TwoDFittingUsingIOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {

		g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		
		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];



		IRectangularROI greenRectangle = new RectangularROI(pt[0], pt[1],
				 len[0], len[1], 0);

		
		Dataset in1 = (Dataset) PlotSystem2DataSetter.PlotSystem2DataSetter1(greenRectangle, input);
		

		if (Arrays.equals(in1.getShape(), new int[] { len[1], len[0] }) == false) {
			IDataset location = DatasetFactory.ones(new int[] {2,2});
			Dataset errorDat = DatasetFactory.zeros(new int[] { 2, 2 });
			return new OperationData(errorDat, location);
		}

		
		if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters()) {
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		}

		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomBoxes(input, len, pt,
				model.getBoundaryBox());

		if (Arrays.equals(fittingBackground[0].getShape(), (new int[] { 2, 2 }))) {
			IDataset location = DatasetFactory.ones(new int[] {2,2});
			return new OperationData(fittingBackground[0], location);
		}

		Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
				AnalaysisMethodologies.toInt(model.getFitPower()), fittingBackground[0], fittingBackground[1]);

		DoubleDataset test = (DoubleDataset) LinearAlgebra.solveSVD(matrix, fittingBackground[2]);
		double[] params = test.getData();

		in1Background = g2.getOutputValues2(params, len, model.getBoundaryBox(),
						AnalaysisMethodologies.toInt(model.getFitPower()));


		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);

		output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);

		output.setName("Region of Interest, polynomial background removed");
				
		return new OperationData(output, (IDataset) in1Background);
	}

}