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
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class SXRDRodScansGeometricCorrectionsOperation extends AbstractOperation<RodScanPolynomial2DModel, OperationData> {
		
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.SXRDRodScansGeometricCorrectionsOperation";
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
		
	/*Generates geometrical corrections for SXRD rod scans.
	 * 
	 * 
	 */
		Dataset correction = null;
		try {
			correction = Maths.multiply(RodScanCorrections.lorentz(input), RodScanCorrections.areacor(input
					, model.getBeamCor(), model.getSpecular(),  model.getSampleSize()
					, model.getOutPlaneSlits(), model.getInPlaneSlits(), model.getBeamInPlane()
					, model.getBeamOutPlane(), model.getScalingFactor()));
			correction = Maths.multiply(RodScanCorrections.polarisation(input, model.getInPlanePolarisation()
					, model.getOutPlanePolarisation()), correction);
			correction = Maths.multiply(
					RodScanCorrections.polarisation(input, model.getInPlanePolarisation(), model.getOutPlanePolarisation()),
					correction);
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
		}
	
		correction = Maths.multiply(model.getScalingFactor(), correction);
		
		input = Maths.multiply(input, correction);
		
		Dataset input1 = DatasetUtils.cast(input, Dataset.FLOAT64);
		
		Dataset input1Sum = DatasetFactory.createFromObject(input1.sum());
		
		input1.setName("Region of Interest, corrected for geometric factors");
		input1Sum.setName("Summed Region of Interest, corrected for geometric factors");
		
		
		return new OperationData (input1, input1Sum);
	}
	
	
}
//TEST
