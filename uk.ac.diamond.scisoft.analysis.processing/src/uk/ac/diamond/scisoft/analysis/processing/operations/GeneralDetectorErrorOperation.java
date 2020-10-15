/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;

@Atomic
public class GeneralDetectorErrorOperation<T extends GeneralDetectorErrorModel> extends AbstractOperation<T, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.GeneralDetectorErrorOperation";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		DoubleDataset er = DatasetFactory.zeros(DoubleDataset.class, input.getShape());
		Dataset in = DatasetUtils.convertToDataset(input.getSliceView());
		
		IndexIterator i = in.getIterator();
		double gain = model.getGain();
		double gainF = model.getGainFluctuation();
		double qE = model.getQuantumEfficiency();
		double eNoise = model.getElectronicNoise();
		double val = 0;
		while (i.hasNext()) {
			val = in.getElementDoubleAbs(i.index);
			val = (gain*gain+gainF*gainF)*qE*val+eNoise;
			if (val < 0) continue;
			er.setAbs(i.index, Math.sqrt(val));
		}
		
		in.setErrors(er);

		return new OperationData(in);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
