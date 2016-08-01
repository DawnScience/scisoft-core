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
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
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

public class OneDPolynomialBackgroundFitAndSubtract extends AbstractOperation<BoxSlicer1DModel, OperationData> {

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.OneDPolynomialBackgroundFitAndSubtract";
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
		
		IDataset[] background = new IDataset[2];
				
		background[0] = BoxSlicerRodScanUtils.iBelowOrRightBox(input, monitor, box.getIntLengths(), box.getIntPoint()
													, model.getBoundaryBox(), model.getDirection());
		
		background[1] = BoxSlicerRodScanUtils.iAboveOrLeftBox(input, monitor, box.getIntLengths(), box.getIntPoint()
				, model.getBoundaryBox(), model.getDirection());
		
		
		Dataset in1Background = DatasetFactory.zeros(in1.getShape(), Dataset.FLOAT64);
		
		in1Background = BackgroundSetting.rOIBackground1(background, in1Background
				, box.getIntLengths(), box.getIntPoint()
				, model.getBoundaryBox(), model.getFitPower()
				, model.getDirection());
		
		IndexIterator it = in1Background.getIterator();
		
		while (it.hasNext()) {
			double v = in1Background.getElementDoubleAbs(it.index);
			if (v < 0) in1Background.setObjectAbs(it.index, 0);
		}

		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);

		pBackgroundSubtracted.setName("Region of Interest, polynomial background removed");


		
		IndexIterator it1 = pBackgroundSubtracted.getIterator();
		
		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (q < 0) pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}
		
		
		return new OperationData(pBackgroundSubtracted);

	}
	
}
//Test

