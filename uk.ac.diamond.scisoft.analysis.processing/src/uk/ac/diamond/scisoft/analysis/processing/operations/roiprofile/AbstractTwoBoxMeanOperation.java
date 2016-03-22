/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROIUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public abstract class AbstractTwoBoxMeanOperation<T extends TwoBoxModel> extends AbstractOperation<TwoBoxModel, OperationData> {

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
		
		RectangularROI box1 = model.getBox1();
		RectangularROI box2 = model.getBox2();
//		Dataset mask = null;
//		MaskMetadata mmd = input.getFirstMetadata(MaskMetadata.class);
//		if (mmd != null && mmd.getMask() != null) mask = DatasetUtils.sliceAndConvertLazyDataset(mmd.getMask());
//		
//		Dataset data = DatasetUtils.convertToDataset(input);
//		
//		Dataset[] b1 = ROIProfile.boxMean(data, mask, box1, false);
//		Dataset[] b2 = ROIProfile.boxMean(data, mask, box2, false);
		
		long t = System.currentTimeMillis();
		Dataset out = result(getMeanFromBox(input,box1), getMeanFromBox(input,box2));
		System.out.println(System.currentTimeMillis()-t);
		
		return new OperationData(input, out);
	}
	
	abstract protected Dataset result(double mean1, double mean2);
	
	public static double getMeanFromBox(IDataset input, RectangularROI roi){
		
		Dataset slice = getSliceFromBox(input, roi);
		
		MaskMetadata m = slice.getFirstMetadata(MaskMetadata.class);
		
		if (m != null && m.getMask() != null) slice = ROIProfile.nanalize(slice, (BooleanDataset)m.getMask().getSlice());
		
		return (double)slice.mean(true);
	}
	
	public static Dataset getSliceFromBox(IDataset input, RectangularROI roi){
		//data set shape corresponds to plot [y,x]
		int[] spt = roi.getIntPoint();
		int[] len = roi.getIntLengths();

		final int xstart  = Math.max(0,  spt[0]);
		final int xend   = Math.min(spt[0] + len[0],  input.getShape()[1]); 
		final int ystart = Math.max(0,  spt[1]);
		final int yend   = Math.min(spt[1] + len[1],  input.getShape()[0]);
		
		return DatasetUtils.convertToDataset(input.getSlice(new int[]{ystart,   xstart}, 
				new int[]{yend,    xend},
				new int[]{1,1}));
		
	}

}
