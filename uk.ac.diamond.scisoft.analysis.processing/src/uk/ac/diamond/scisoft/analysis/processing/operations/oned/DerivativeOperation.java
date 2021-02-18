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
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.PositionIterator;

import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;

@Atomic
public class DerivativeOperation extends AbstractOperation<DerivativeModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.DerivativeOperation";
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		int order = model.getDerivative();
		if (order < 1 || order > 5) {
			throw new OperationException(this, "Invalid derivative order!");
		}
		Dataset in = DatasetUtils.convertToDataset(input);
		int a = model.getAxis();
		int rank = in.getRank();
		if (a < 0) {
			a += rank;
		}
		if (a < 0 || a >= rank) {
			throw new OperationException(this, "Invalid dimension to differentiate");
		}

		final int len = in.getShapeRef()[a];
		ILazyDataset[] firstAxes = getFirstAxes(input);
		Dataset ax;
		if (firstAxes == null || firstAxes[a] == null) {
			ax = DatasetFactory.createRange(IntegerDataset.class, len);
		} else {
			try {
				ax = DatasetUtils.sliceAndConvertLazyDataset(firstAxes[a]);
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}

		Dataset out = null;
		final Dataset src = DatasetFactory.zeros(in.getElementsPerItem(), in.getClass(), len);
		final PositionIterator pi = in.getPositionIterator(a);
		final int[] pos = pi.getPos();
		final boolean[] hit = pi.getOmit();

		while (pi.hasNext()) {
			in.copyItemsFromAxes(pos, hit, src);
			Dataset dest = src;
			for (int i = 0; i < order; i++) {
				dest = Maths.derivative(ax, dest, model.getSmoothing());
			}
			if (out == null) {
				out = DatasetFactory.zeros(in, dest.getClass());
			}
			out.setItemsOnAxes(pos, hit, dest.getBuffer());
		}

		if (firstAxes != null) {
			MetadataUtils.setAxes(this, out, firstAxes);
		}
		return new OperationData(out);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
}
