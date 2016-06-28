/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.downsample.DownsampleMode;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.function.Downsample;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

public class StitchDownsampledGridOperation extends AbstractOperation<DownsampleGridModel, OperationData> implements IExportOperation {
	
	private Dataset full = null;
	int count = 0;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.StitchDownsampledGridOperation";
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
	public void init() {
		full =null;
		count = 0;
	};

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		int[] inShape = input.getShape();
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		int[] shape = ssm.getSubSampledShape();
		int[] dims = getFastestNonSingluarNonDataDimension(ssm);
		
		//single image
		if (dims[0] == -1 && dims[1] == -1) return new OperationData(input);
		
		int xN = shape[dims[0]];
		int yN = (dims[1] == -1 ? 1 : shape[dims[1]]);
		

		Slice[] slice = ssm.getSliceInOutput();
		
		double newX = (xN*inShape[1])/(double)model.getImageSize();
		double newY = (yN*inShape[0])/(double)model.getImageSize();
		
		int downSampleSize = (int)Math.round(Math.max(newX,newY));

		if (downSampleSize == 0) downSampleSize = 1;
		
		Downsample downsample = new Downsample(DownsampleMode.MEAN, downSampleSize,downSampleSize);
		
		Dataset ds = downsample.value(input).get(0);
		
		int x = ds.getShape()[1]* xN;
		int y = ds.getShape()[0]* yN;
		
		if (full == null) {
			full = DatasetFactory.zeros(new int[]{y, x}, ds.getDType());
		}
		
		SliceND s = new SliceND(full.getShape());
		s.setSlice(1, slice[dims[0]].getStart()*ds.getShape()[1], (slice[dims[0]].getStart()+1)*ds.getShape()[1], 1);
		if (dims[1] != -1) s.setSlice(0, slice[dims[1]].getStart()*ds.getShape()[0], (slice[dims[1]].getStart()+1)*ds.getShape()[0], 1);
		full.setSlice(ds, s);
		count++;
		
		if (ssm.getTotalSlices() != count) {
			
			return null;
		}
		
		SliceFromSeriesMetadata outsmm = ssm.clone();
		for (int i = 0; i < ssm.getParent().getRank(); i++) {
			
			if (!outsmm.isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
			
		}
		full.setMetadata(outsmm);
		Dataset f = full;
		full = null;
		
		return new OperationData(f);
	}

	private int[] getFastestNonSingluarNonDataDimension(SliceFromSeriesMetadata ssm) {
		int[] dims = new int[]{-1,-1};
		int[] shape = ssm.getSubSampledShape();
		int[] dataDims = ssm.getSliceInfo().getDataDimensions();
		int[] dd = dataDims.clone();
		Arrays.sort(dd);
		int count = 0;
		for (int i = shape.length-1; i > -1; i--) {
			if (shape[i] == 1) continue;
			int key = Arrays.binarySearch(dd, i);
			if (key < 0) dims[count++] = i;
			if (count > 1) break;
		}
		return dims;
	}

}
