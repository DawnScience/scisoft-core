/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.fitting;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IdentifiedPeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

@Atomic
public class FitPeakToRowsOperation extends AbstractOperation<FitPeakToRowsModel, OperationData> {

	private static final Logger logger = LoggerFactory
			.getLogger(FitPeakToRowsOperation.class);
	
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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] firstAxes = getFirstAxes(input);
		IDataset axis = null;
		
		if (firstAxes == null || firstAxes[1] == null) {
			axis = DatasetFactory.createRange(input.getShape()[1], Dataset.INT32);
			//Add back in as metadata
		} else {
			try {
				axis = firstAxes[1].getSlice().squeeze();
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
			indexes[0]= ROISliceUtils.findPositionOfClosestValueInAxis(axis, fitRange[0]);
			indexes[1]= ROISliceUtils.findPositionOfClosestValueInAxis(axis, fitRange[1]);
			Arrays.sort(indexes);
		} else {
			indexes[0] = 0;
			indexes[1] = axis.getSize()-1;
		}
		
		SliceND s = new SliceND(input.getShape());
		s.setSlice(1, indexes[0], indexes[1], 1);
		
		SliceViewIterator it = new SliceViewIterator(input, s, new int[]{1});
		Dataset position = DatasetFactory.zeros(new int[]{input.getShape()[0]},Dataset.FLOAT64);
		position.fill(Double.NaN);
		position.setName("Position");
		int count = 0;
		IOptimizer op = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		Add f = null;
		while (it.hasNext()) {
			try {
				
				Dataset next = DatasetUtils.convertToDataset(it.next().getSlice()).squeeze();
				Dataset ax = DatasetUtils.convertToDataset(getFirstAxes(next)[0].getSlice());
				if (f == null) {
					IdentifiedPeak foundPeak = Generic1DFitter.parseDataDerivative(ax, next, 1).get(0);
					Gaussian g = new Gaussian(foundPeak.getPos(),foundPeak.getFWHM(),foundPeak.getArea());
					StraightLine st = new StraightLine();
					f = new Add();
					f.addFunction(g);
					f.addFunction(st);
				}
				
				
				op.optimize(new IDataset[]{ax}, next, f);
				position.set(f.getFunctions()[0].getParameter(0).getValue(), count++);
				
			} catch (Exception e) {
				//no logging required, the NaN value shows the fit has failed
			}
		}
		
		if (firstAxes != null && firstAxes[0] != null) {
			AxesMetadata m;
			try {
				m = MetadataFactory.createMetadata(AxesMetadata.class,1);
				m.setAxis(0, firstAxes[0].getSlice().squeeze());
				position.setMetadata(m);
			} catch (Exception e) {
				logger.error("Metadata building failed");
			}
			
		}
		
		return new OperationData(position);
	}

}
