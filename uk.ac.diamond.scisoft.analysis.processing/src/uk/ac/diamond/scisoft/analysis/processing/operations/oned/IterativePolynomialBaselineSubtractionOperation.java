/*-
 * Copyright 2015 Diamond Light Source Ltd.
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
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.baseline.BaselineGeneration;

@Atomic
public class IterativePolynomialBaselineSubtractionOperation extends
		AbstractOperation<IterativePolynomialBaselineSubtractionModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.IterativePolynomialBaselineSubtractionOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] axes = getFirstAxes(input);
		
		if (axes == null || axes[0] == null) throw new OperationException(this, "Cannot fit without axes");
		
		Dataset axis;
		try {
			axis = DatasetUtils.sliceAndConvertLazyDataset(axes[0]);
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		
		DoubleDataset data = (DoubleDataset)DatasetUtils.cast(input, Dataset.FLOAT64).clone();
		
		Dataset baseline = BaselineGeneration.iterativePolynomialBaseline(data, axis, model.getPolynomialOrder(), model.getnIterations());
		
		IDataset output = Maths.subtract(input, baseline);
		
		AxesMetadata ax;
		try {
			ax = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		ax.setAxis(0, axis);
		output.setMetadata(ax);
		
		return new OperationData(output);
	}

}
