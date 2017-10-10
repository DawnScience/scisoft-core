/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.baseline.BaselineGeneration;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

@Atomic
public class RollingBallBaselineOperation extends AbstractOperation<RollingBallBaselineModel, OperationData> {

	@Override
	public String getId() {

		return "uk.ac.diamond.scisoft.analysis.processing.operations.RollingBallBaselineOperation";
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		Dataset cor = rollingBallBaselineCorrection(DatasetUtils.convertToDataset(input), model.getBallRadius());
		
		copyMetadata(input, cor);
		
		return new OperationData(cor);
	}
	

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	private  Dataset rollingBallBaselineCorrection(Dataset y, int width) {

		Dataset t1 = BaselineGeneration.rollingBallBaseline(y, width);
		return Maths.subtract(y, t1);
	}

}
