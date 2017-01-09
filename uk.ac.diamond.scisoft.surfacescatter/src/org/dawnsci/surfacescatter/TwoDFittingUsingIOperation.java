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

import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
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
public class TwoDFittingUsingIOperation extends AbstractOperation<TwoDFittingModel, OperationData> {

	private static Dataset output;
	private static Polynomial2D g2;
	private static int DEBUG = 1;
	private DoubleDataset in1Background;
	
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

		g2 = null;

		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];

		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);

		if (Arrays.equals(in1.getShape(), new int[] { len[1], len[0] }) == false) {
			double[] location = new double[] { 2, 2 };
			Dataset errorDat = DatasetFactory.zeros(new int[] { 2, 2 });
			IndexIterator it2 = errorDat.getIterator();
			while (it2.hasNext()) {
				double q = errorDat.getElementDoubleAbs(it2.index);
				if (q <= 0)
					errorDat.setObjectAbs(it2.index, 0.1);
			}
			return new OperationData(errorDat, location);
		}

		if (g2 == null) {
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		}
		if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters()) {
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		}

		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomBoxes(input, len, pt,
				model.getBoundaryBox());

		if (Arrays.equals(fittingBackground[0].getShape(), (new int[] { 2, 2 }))) {
			double[] location = new double[] { 2, 2 };
			return new OperationData(fittingBackground[0], location);
		}

		Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
				AnalaysisMethodologies.toInt(model.getFitPower()), fittingBackground[0], fittingBackground[1]);

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				DoubleDataset test = (DoubleDataset) LinearAlgebra.solveSVD(matrix, fittingBackground[2]);
				double[] params = test.getData();

				in1Background = g2.getOutputValues0(params, len, model.getBoundaryBox(),
						AnalaysisMethodologies.toInt(model.getFitPower()));

				if (model.getFitPower() == FitPower.ZERO) {

					double probe = in1Background.get(0, 0);

					IndexIterator check = in1Background.getIterator();

					while (check.hasNext()) {
						double v = in1Background.getElementDoubleAbs(check.index);
						if (v != probe)
							debug("caution background inaccurate!");
					}

				}
//				IndexIterator it = in1Background.getIterator();
//
//				while (it.hasNext()) {
//					double v = in1Background.getElementDoubleAbs(it.index);
//					if (v <= 0)
//						in1Background.setObjectAbs(it.index, 0.1);
//				}

				Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);

				IndexIterator it1 = pBackgroundSubtracted.getIterator();

//				while (it1.hasNext()) {
//					double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
//
//					if (q <= 0) {
//						pBackgroundSubtracted.setObjectAbs(it1.index, 0.1);
//					}
//				}
				output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);

				output.setName("Region of Interest, polynomial background removed");

			}
		});

		double[] location = new double[] { (double) model.getInitialLenPt()[1][1],
				(double) model.getInitialLenPt()[1][0],
				(double) (model.getInitialLenPt()[1][1] + model.getInitialLenPt()[0][1]),
				(double) (model.getInitialLenPt()[1][0]), (double) model.getInitialLenPt()[1][1],
				(double) model.getInitialLenPt()[1][0] + model.getInitialLenPt()[0][0],
				(double) (model.getInitialLenPt()[1][1] + model.getInitialLenPt()[0][1]),
				(double) (model.getInitialLenPt()[1][0] + model.getInitialLenPt()[0][0]) };

		return new OperationData(output, location, in1Background);
	}

	private void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}

}