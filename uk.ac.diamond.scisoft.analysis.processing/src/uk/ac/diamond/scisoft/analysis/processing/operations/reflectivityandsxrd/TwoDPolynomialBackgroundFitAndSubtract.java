/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

/**
 * Cuts out the region of interest and fits it with a 2D polynomial background.
 */
public class TwoDPolynomialBackgroundFitAndSubtract extends AbstractOperation<TwoDPolynomialBackgroundFitAndSubtractModel, OperationData> {
	
	private Polynomial2D g2;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.TwoDPolynomialBackgroundFitAndSubtract";
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
		
		RectangularROI box = model.getBox();
	
		Dataset in1 = BoxSlicerRodScanUtils.rOIBox(input, monitor, box.getIntLengths(), box.getIntPoint());
	
		if (g2 == null)
			g2 = new Polynomial2D(model.getFitPower());
		if ((int) Math.pow(model.getFitPower() + 1, 2) != g2.getNoOfParameters())
			g2 = new Polynomial2D(model.getFitPower());
	
		Dataset[] fittingBackground = BoxSlicerRodScanUtils.LeftRightTopBottomBoxes(input, monitor, box.getIntLengths(),
				box.getIntPoint(), model.getBoundaryBox());
			
		Dataset offset = DatasetFactory.ones(DoubleDataset.class, fittingBackground[2].getShape());
		
		Dataset intermediateFitTest = Maths.add(offset, fittingBackground[2]);
		Dataset matrix = LinearLeastSquaresServicesForSXRD.polynomial2DLinearLeastSquaresMatrixGenerator(
				model.getFitPower(), fittingBackground[0], fittingBackground[1]);
		
		DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, intermediateFitTest);
		double[] params = test.getData();
		
		DoubleDataset in1Background = g2.getOutputValues0(params, box.getIntLengths(), model.getBoundaryBox(),
				model.getFitPower());
	
		IndexIterator it = in1Background.getIterator();
	
		while (it.hasNext()) {
			double v = in1Background.getElementDoubleAbs(it.index);
			if (model.isAllowNegativeValues() == false && v < 0)
				in1Background.setObjectAbs(it.index, 0);
		}
	
		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
	
		pBackgroundSubtracted.setName("pBackgroundSubtracted");
	
		IndexIterator it1 = pBackgroundSubtracted.getIterator();
	
		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (model.isAllowNegativeValues() == false && q < 0)
				pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}
		
		Dataset output = DatasetUtils.cast(DoubleDataset.class, pBackgroundSubtracted);
		
		output.setName("Region of Interest, polynomial background removed");
		
		return new OperationData(output);
	}

}