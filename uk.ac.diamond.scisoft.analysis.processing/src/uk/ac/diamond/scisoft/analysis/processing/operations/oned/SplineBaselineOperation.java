/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;

public class SplineBaselineOperation extends AbstractOperation<SplineBaselineModel, OperationData> {

	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset cor = subtractSplineBaseline(DatasetUtils.convertToDataset(input), getControlPoints());
		
		if (!cor.equals(input)) copyMetadata(input, cor);
		
		return new OperationData(cor);
	}

	
	private Dataset getControlPoints() {

		double[] xknots = model.getXControlPoints();
		double[] yknots = model.getYControlPoints();

		if (xknots == null || yknots == null) return null;
		
		int nknots = Math.min(xknots.length, yknots.length);
		Dataset knots = new DoubleDataset(2, nknots);
		SortedMap<Double, Double> xyknots = new TreeMap<Double, Double>();
		for (int i = 0; i < nknots; i++)
			xyknots.put(xknots[i], yknots[i]);

		int i = 0;
		for (Map.Entry<Double, Double> knot : xyknots.entrySet()) {
			knots.set(knot.getKey(), 0, i);
			knots.set(knot.getValue(), 1, i);
			i++;
		}
		// TODO?: Possibly sort the arrays as held in the model		
		return knots;
	}



	private Dataset subtractSplineBaseline(Dataset input, Dataset knots) throws OperationException {
		if (knots == null || knots.getSize() <= 0 || knots.getShape().length < 2 )
		// Without sufficient data points for a fit, return the original data
			return input;
		
		switch (knots.getShape()[1]) {
		case 0:
			// Original data
			return input;
		case 1:
			// Subtract a constant
			return Maths.subtract(input, knots.getDouble(1, 0));
		case 2:
			// Subtract a linear fit
			// control points
			IDataset xControl = knots.getSlice((new int[]{0, 0}), (new int[]{1, knots.getShape()[1]}), (new int[]{1,1})).reshape(knots.getShape()[1]);
			IDataset yControl = knots.getSlice((new int[]{1, 0}), (new int[]{2, knots.getShape()[1]}), (new int[]{1,1})).reshape(knots.getShape()[1]);
			
			IDataset xaxis = DatasetUtils.convertToDataset(input.getFirstMetadata(AxesMetadata.class).getAxes()[0].getSlice());

			Dataset ylinear = (Dataset) Interpolation1D.linearInterpolation(xControl, yControl, xaxis);
			return Maths.subtract(input, ylinear);
		default:
			break;
		}
		
		// spline control points
		IDataset xControl = knots.getSlice((new int[]{0, 0}), (new int[]{1, knots.getShape()[1]}), (new int[]{1,1})).reshape(knots.getShape()[1]);
		IDataset yControl = knots.getSlice((new int[]{1, 0}), (new int[]{2, knots.getShape()[1]}), (new int[]{1,1})).reshape(knots.getShape()[1]);
		
		IDataset xaxis;
		if (input.getFirstMetadata(AxesMetadata.class) != null &&
				input.getFirstMetadata(AxesMetadata.class).getAxes().length != 0 &&
				input.getFirstMetadata(AxesMetadata.class).getAxes()[0] != null)
			xaxis = DatasetUtils.convertToDataset(input.getFirstMetadata(AxesMetadata.class).getAxes()[0].getSlice());
		else
			xaxis = DoubleDataset.createRange(input.getShape()[0]);
		
		Dataset yspline = (Dataset) Interpolation1D.splineInterpolation(xControl, yControl, xaxis);
		
		return Maths.subtract(input, yspline);
	}





	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SplineBaselineOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
