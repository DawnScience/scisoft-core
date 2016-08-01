/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

/**Calculates the geometric correction factors for X-ray reflectivity. Outputs dataset of an image (Dataset)
 * multiplied by the geometric are correction.
 */


public class ReflectivityGeometricCorrectionOperation extends AbstractOperation<Polynomial2DReflectivityModel, OperationData> {

	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.ReflectivityGeometricCorrectionOperation";
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
		
		
		double theta = 0;
		try {
			theta = ScanMetadata.getTheta(input);
		} catch (Exception e) {
		}
		
		NormalDistribution beamfootprint  = new NormalDistribution(0, (1e-3*model.getBeamHeight()/2*Math.sqrt(2*Math.log(2) - 0.5)));
		double areaCorrection = 2*(beamfootprint.cumulativeProbability((model.getFootprint()*Math.sin((theta + model.getAngularFudgeFactor())*Math.PI/180))));
			
		Dataset output = DatasetUtils.cast(input, Dataset.FLOAT64);
		
		output = Maths.multiply(input, areaCorrection);
		
		Dataset outputSum = DatasetFactory.createFromObject((DatasetUtils.cast(output, Dataset.FLOAT64)).sum());
		
		return new OperationData(output, outputSum);
	}
}
//TEST