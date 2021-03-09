/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.fitting;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.SliceNDIterator;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IdentifiedPeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PeakType;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.processing.operations.fitting.FitPeakToRowsModel.ResultParameter;

@Atomic
public class FitPeakToRowsOperation extends AbstractOperation<FitPeakToRowsModel, OperationData> implements PropertyChangeListener {

	private static final Logger logger = LoggerFactory.getLogger(FitPeakToRowsOperation.class);

	private OperationLog log = new OperationLog();

	private APeak peak;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fitting.FitPeakToRowsOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public void setModel(FitPeakToRowsModel model) {
		super.setModel(model);
		model.addPropertyChangeListener(this);
		updatePeak();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (FitPeakToRowsModel.PEAK_TYPE.equals(prop)) {
			updatePeak();
		}
	}

	private void updatePeak() {
		peak = PeakType.createPeak(model.getPeakType(), null);
		int n = ResultParameter.values().length;
		int p = peak.getNoOfParameters();
		for (int i = 3; i < n; i++) {
			ResultParameter.values()[i].setName(i < p ? peak.getParameter(i).getName() : FitPeakToRowsModel.INVALID);
		}
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (model.getResultParameter().ordinal() >= peak.getNoOfParameters()) {
			throw new OperationException(this, "Result parameter choice is not valid; choose another parameter");
		}

		ILazyDataset[] firstAxes = getFirstAxes(input);
		Dataset in = DatasetUtils.convertToDataset(input);

		Dataset axis = null;
		if (firstAxes == null || firstAxes[1] == null) {
			axis = DatasetFactory.createRange(IntegerDataset.class, in.getShapeRef()[1]);
			//Add back in as metadata
		} else {
			try {
				axis = DatasetUtils.sliceAndConvertLazyDataset(firstAxes[1]).squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		double[] fitRange = model.getFitRange();
		int[] indexes = new int[2];
		if (fitRange != null) {
			fitRange = fitRange.clone();
			Arrays.sort(fitRange);
			indexes = new int[2];
			for (int i = 0; i < 2; i++) {

				int index;
				double f = fitRange[i];
				if (axis.getRank() == 1) {
					index = ROISliceUtils.findPositionOfClosestValueInAxis(axis, f);
				} else {
					int[] shape = axis.getShapeRef();
					SliceNDIterator it = new SliceNDIterator(new SliceND(shape), 1);
					it.hasNext();
					Dataset a = axis.getSliceView(it.getCurrentSlice());
					Monotonicity m = Comparisons.findMonotonicity(a);
					index = ROISliceUtils.findPositionOfClosestValueInAxis(axis, f);
					boolean isInc = m == Monotonicity.NONDECREASING || m == Monotonicity.STRICTLY_INCREASING;
					
					while (it.hasNext()) {
						a = axis.getSliceView(it.getCurrentSlice());
						if (isInc) {
							index = Math.max(index, ROISliceUtils.findPositionOfClosestValueInAxis(a, f));
						} else {
							index = Math.min(index, ROISliceUtils.findPositionOfClosestValueInAxis(a, f));
						}
					}
				}

				indexes[i] = index;
			}
			Arrays.sort(indexes);
		} else {
			indexes[1] = axis.getShapeRef()[axis.getRank() - 1];
		}
		
		SliceND s = new SliceND(in.getShapeRef());
		s.setSlice(1, indexes[0], indexes[1], 1);
		
		SliceViewIterator it = new SliceViewIterator(input, s, new int[]{1});

		int n = peak.getNoOfParameters();
		Dataset[] results = new Dataset[n];
		Dataset position = DatasetFactory.zeros(in.getShapeRef()[0]);
		position.fill(Double.NaN);
		position.setName(peak.getParameter(0).getName());
		results[0] = position;
		for (int i = 1; i < n; i++) {
			Dataset r = position.clone();
			r.setName(peak.getParameter(i).getName());
			results[i] = r;
		}

		int count = 0;
		IOptimizer op = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		Add f = null;
		while (it.hasNext()) {
			try {
				
				Dataset next = DatasetUtils.convertToDataset(it.next().getSlice()).squeeze();
				Dataset ax = DatasetUtils.convertToDataset(getFirstAxes(next)[0].getSlice());
				if (f == null) {
					IdentifiedPeak foundPeak = Generic1DFitter.parseDataDerivative(ax, next, 1).get(0);
					peak = PeakType.createPeak(model.getPeakType(), foundPeak);
					StraightLine st = new StraightLine();
					f = new Add();
					f.addFunction(peak);
					f.addFunction(st);
				} else {
					IdentifiedPeak foundPeak = Generic1DFitter.parseDataDerivative(ax, next, 1).get(0);
					peak.setParameters(foundPeak);
					f.getFunction(1).setParameterValues(1, 0);
				}

				log.append("Fitting %d-th row to %s", count, model.getPeakType());
				op.optimize(new IDataset[]{ax}, next, f);
				log.appendSuccess("Found function %s", f);
				for (int i = 0; i < n; i++) {
					results[i].set(peak.getParameterValue(i), count);
				}
			} catch (Exception e) {
				log.appendFailure("Could not fit %d-th row", count, e);
			}
			count++;
		}

		if (firstAxes != null && firstAxes[0] != null) {
			AxesMetadata m;
			try {
				m = MetadataFactory.createMetadata(AxesMetadata.class,1);
				m.setAxis(0, firstAxes[0].getSlice().squeeze());
				for (int i = 0; i < n; i++) {
					results[i].setMetadata(m.clone());
				}
			} catch (Exception e) {
				logger.error("Metadata building failed");
			}
			
		}

		OperationData od = new OperationData();
		int r = model.getResultParameter().ordinal();
		od.setData(results[r]);
		Serializable[] summary = new Serializable[n - 1];
		int j = 0;
		for (int i = 0; i < n; i++) {
			if (i != r) {
				summary[j++] = results[i];
			}
		}
		od.setSummaryData(summary);
		od.setLog(log);
		return od;
	}
}
