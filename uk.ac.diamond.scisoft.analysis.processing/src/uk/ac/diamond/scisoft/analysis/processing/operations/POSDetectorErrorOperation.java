/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

public class POSDetectorErrorOperation extends GeneralDetectorErrorOperation<POSDetectorErrorModel> {
	
	private Integer nRepetitions = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.POSDetectorErrorOperation";
	}
	
	@Override
	public void init() {
		nRepetitions = null;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// Shamelessly ripped from the old MultiplyExternalDataOperation class
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (!ssm.getSliceInfo().isFirstSlice() && nRepetitions == null) {
			throw new OperationException(this, "Number of Repetitions not determined");
		}
		

		Dataset scale = DatasetUtils.convertToDataset(
				ProcessingUtils.getMatchingValue(this, input, ssm.getFilePath(), model.getDatasetName()));
		
		int s = scale.getInt();
		
		if (ssm.getSliceInfo().isFirstSlice()) {
			nRepetitions = s;
		} else {
			s = s % nRepetitions;
			if (s == 0) {
				s = nRepetitions;
			}
		}
		
		IDataset scaled = Maths.multiply(input, s);
		OperationData process = super.process(scaled, monitor);
		
		IDataset unscale = ErrorPropagationUtils.divideWithUncertainty(DatasetUtils.convertToDataset(process.getData()),
				              DatasetFactory.createFromObject(s));
		
		copyMetadata(input, unscale);
		
		return new OperationData(unscale);

	}
	

}
