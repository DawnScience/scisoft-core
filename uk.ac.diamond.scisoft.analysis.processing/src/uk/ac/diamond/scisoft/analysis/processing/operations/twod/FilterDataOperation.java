/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Stats;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.twod.FilterDataModel.FilterDataAxis;


public class FilterDataOperation extends AbstractOperation<FilterDataModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.FilterDataOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		IDataset rv = null;
	
		try {
			if (model.getAxis() == FilterDataAxis.X_THEN_Y ) {
				IDataset temp = processOneDirection(input, FilterDataAxis.X);
				rv = processOneDirection(temp, FilterDataAxis.Y);
			} else if (model.getAxis() == FilterDataAxis.Y_THEN_X) {
				IDataset temp = processOneDirection(input, FilterDataAxis.Y);
				rv = processOneDirection(temp, FilterDataAxis.X);
			} else {
				rv = processOneDirection(input, model.getAxis());
			}
		
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
			
		return new OperationData(rv);
	}
	
	private IDataset processOneDirection(IDataset input, FilterDataAxis axis /* must be X or Y */) throws Exception {
		
		Dataset inputD = DatasetUtils.convertToDataset(input);
		
		Dataset reduced = null;
			
		switch (model.getSource()) {
		case AVERAGE:
			reduced = inputD.mean(axis.getAxis());
			break;
		case SUM:
			reduced = inputD.sum(axis.getAxis());
			break;
		case MEDIAN:
			reduced = Stats.median(inputD, axis.getAxis());
			break;
		case MINIMUM:
			reduced = inputD.min(axis.getAxis());
			break;
		case MAXIMUM:
			reduced = inputD.max(axis.getAxis());
			break;
		}
		
		BooleanDataset goodPositions = null;
		Dataset thresholdOnes = DatasetFactory.ones(reduced).imultiply(model.getThreshold());
		
		switch (model.getOperator()) {
		case EQUAL:
			goodPositions = Comparisons.equalTo(reduced, thresholdOnes);
			break;
		case GREATERTHAN:
			goodPositions = Comparisons.greaterThan(reduced, thresholdOnes);
			break;
		case GREATERTHANOREQUAL:
			goodPositions = Comparisons.greaterThanOrEqualTo(reduced, thresholdOnes);
			break;
		case LESSTHAN:
			goodPositions = Comparisons.lessThan(reduced, thresholdOnes);
			break;
		case LESSTHANOREQUAL:
			goodPositions = Comparisons.lessThanOrEqualTo(reduced, thresholdOnes);
			break;
		case NOTEQUAL:
			goodPositions = Comparisons.equalTo(reduced, thresholdOnes);
			// invert
			Comparisons.logicalNot(goodPositions);
			break;
		}
		
		if (goodPositions.all())
			return input;
		
			
		// let the processing begin...
		int count = ((Number) goodPositions.sum()).intValue();
		
		if (count == 0) {
			throw new Exception("No data was found that satisfies the matches the provided data filter parameters");
		}
		
		Dataset extracted = null;
		Dataset mask = null;
		
		if (axis == FilterDataAxis.X) {
			mask = Maths.multiply(DatasetFactory.ones(inputD), goodPositions);
			mask = DatasetUtils.cast(BooleanDataset.class, mask);
			extracted = DatasetUtils.extract(inputD, mask);
			extracted.setShape(inputD.getShape()[0], count);
		} else if (axis == FilterDataAxis.Y) {
			mask = Maths.multiply(DatasetFactory.ones(inputD).getTransposedView().getSlice(), goodPositions).getTransposedView().getSlice();
			mask = DatasetUtils.cast(BooleanDataset.class, mask);
			extracted = DatasetUtils.extract(inputD, mask);
			extracted.setShape(count, inputD.getShape()[1]);
		}
	
		/* The data has been processed, now the axes need updating too if necessary
		 * Start by copying the non-AxesMetadata
		 */
		
		copyMetadata(input, extracted, false);
		
		ILazyDataset[] axes = getFirstAxes(input);
		
		if (axes != null && axes.length == 2) {
			AxesMetadata axma = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		
			ILazyDataset axisX = null, axisY = null;
			
			if (axis == FilterDataAxis.X) {
				if (axes[0] != null) {
					// update the X-axis
					if (axes[0].getShape()[1] == 1) {
						// copy the original X axis if it is 1D (after a hypothetical squeeze)
						axisX = axes[0];
					} else {
						// otherwise, extract new axis with mask
						axisX = DatasetUtils.extract(DatasetUtils.sliceAndConvertLazyDataset(axes[0]), mask);
						axisX.setShape(extracted.getShape());
					}
					axisX.setName(axes[0].getName());
				}
				
				if (axes[1] != null) {
					// update Y-axis
					if (axes[1].getShape()[0] == 1) {
						// 1D axis
						axisY = DatasetUtils.extract(
									DatasetUtils.copy(
											DoubleDataset.class, DatasetUtils.sliceAndConvertLazyDataset(
													axes[1])
											)
									.squeeze(), goodPositions);
						axisY.setShape(1, axisY.getSize());
					} else {
						// 2D axis
						axisY = DatasetUtils.extract(DatasetUtils.sliceAndConvertLazyDataset(axes[1]), mask);
						axisY.setShape(extracted.getShape());
					}
					axisY.setName(axes[1].getName());
				}
			} else if (axis == FilterDataAxis.Y) {
				if (axes[1] != null) {
					if (axes[1].getShape()[0] == 1) {
						axisY = axes[1];
					} else {
						axisY = DatasetUtils.extract(DatasetUtils.sliceAndConvertLazyDataset(axes[1]), mask);
						axisY.setShape(extracted.getShape());
					}
					axisY.setName(axes[1].getName());
				}
				
				if (axes[0] != null) {
					if (axes[0].getShape()[1] == 1) {
						axisX = DatasetUtils.extract(
								DatasetUtils.copy(
										DoubleDataset.class, DatasetUtils.sliceAndConvertLazyDataset(
												axes[0])
										)
								.squeeze(), goodPositions);
						axisX.setShape(axisX.getSize(), 1);
					} else {
						axisX = DatasetUtils.extract(DatasetUtils.sliceAndConvertLazyDataset(axes[0]), mask);
						axisX.setShape(extracted.getShape());
					}
					axisX.setName(axes[0].getName());
				}
			}
			
			axma.setAxis(0, axisX);
			axma.setAxis(1, axisY);
			extracted.addMetadata(axma);
			
		}
		
		return extracted;
	}
}
