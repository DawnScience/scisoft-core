/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.sxrdrods;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.RodScanCorrections;
import uk.ac.diamond.scisoft.analysis.processing.surfacescattering.BackgroundSetting;

public class RodScanPolynomial1DOperation extends AbstractOperation<RodScanPolynomial1DModel, OperationData> {

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.sxrdrods.RodScanPolynomial1DOperation";
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
		
//		int[][] modelInts = RodScanPolynomial1DModelUnpacker.polynomial1DRRodScanUnpackerModelInts(model);
//		double[] modelDouble = RodScanPolynomial1DModelUnpacker.polynomial1DRodScanUnpackerModelDoubles(model);
//		boolean[] modelBooleans =RodScanPolynomial1DModelUnpacker.polynomial1DRodScanUnpackerModelBooleans(model);
//		Direction modelDirection = RodScanPolynomial1DModelUnpacker.polynomial1DRodScanUnpackerModelDirection(model);
		
		RectangularROI box = model.getBox();
		
		Dataset in1 = BoxSlicerRodScans.rOIBox(input, monitor, box.getIntLengths(), box.getIntPoint());
		
		IDataset[] background = new IDataset[2];
				
		background[0] = BoxSlicerRodScans.iBelowOrRightBox(input, monitor, box.getIntLengths(), box.getIntPoint()
													, model.getBoundaryBox(), model.getDirection());
		
		background[1] = BoxSlicerRodScans.iAboveOrLeftBox(input, monitor, box.getIntLengths(), box.getIntPoint()
				, model.getBoundaryBox(), model.getDirection());
		
		
		Dataset in1Background = DatasetFactory.zeros(in1.getShape());
		
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

		pBackgroundSubtracted.setName("pBackgroundSubtracted");

		Dataset correction;
		try {
			correction = Maths.multiply(RodScanCorrections.lorentz(input), RodScanCorrections.areacor(input
					, model.getBeamCor(), model.getSpecular(),  model.getSampleSize()
					, model.getOutPlaneSlits(), model.getInPlaneSlits(), model.getBeamInPlane()
					, model.getBeamOutPlane(), model.getScalingFactor()));
			correction = Maths.multiply(RodScanCorrections.polarisation(input, model.getInPlanePolarisation()
					, model.getOutPlanePolarisation()), correction);
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		
		correction = Maths.multiply(model.getScalingFactor(), correction);
			
		pBackgroundSubtracted = Maths.multiply(pBackgroundSubtracted,correction);
		
		IndexIterator it1 = pBackgroundSubtracted.getIterator();
		
		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (q < 0) pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}
		
		Dataset fhkl = Maths.sqrt(pBackgroundSubtracted);
		
		Dataset in1Sum = DatasetFactory.createFromObject(in1.sum());
		Dataset in1BackgroundSum = DatasetFactory.createFromObject(in1Background.sum());
 		Dataset pBackgroundSubtractedSum = DatasetFactory.createFromObject(pBackgroundSubtracted.sum());
		Dataset fhklSum = DatasetFactory.createFromObject(fhkl.sum());
 	 		
		in1.setName("Region of Interest");
		in1Background.setName("Polynomial background");
		pBackgroundSubtracted.setName("Signal after polynomial background subtracted");
		fhkl.setName("fhkl");
		
		in1Sum.setName("Region of Interest Summed") ;
		in1BackgroundSum.setName("Polynomial background summed");
		pBackgroundSubtractedSum.setName("Signal after polynomial background subtracted summed");
		fhklSum.setName("fhkl summed");
		
		return new OperationData(in1, in1Background, pBackgroundSubtracted, fhkl, in1Sum, in1BackgroundSum, pBackgroundSubtractedSum, fhklSum);

	}
//
}
