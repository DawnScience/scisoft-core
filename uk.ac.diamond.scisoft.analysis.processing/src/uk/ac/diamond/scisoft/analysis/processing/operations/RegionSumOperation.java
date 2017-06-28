/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.metadata.MaskMetadata;

public class RegionSumOperation extends AbstractOperation<RegionSumModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.RegionSumOperation";
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
		double sum = sumRegion(model.getRegion(), input);
		Dataset ds = DatasetFactory.createFromObject(sum);
		ds.setShape(new int[]{1});
		ds.setName("sum");
		return new OperationData(input, ds);
	}
	
	
	private double sumRegion(IROI roi, IDataset data) {
		double sum = 0;
		IRectangularROI bounds = roi.getBounds();
		int[] shape = data.getShape();
		int iStart = (int)Math.floor(bounds.getPointY());
		int iStop = (int)Math.ceil(bounds.getPointY()+bounds.getLength(1));
		if (iStop > shape[0]) iStop = shape[0];
		if (iStart < 0) iStart = 0;
		
		for (int i = iStart; i < iStop; i++) {
			double[] hi = roi.findHorizontalIntersections(i);
			if (hi != null) {
				boolean cutsStart = roi.containsPoint(0, i);
				boolean cutsEnd = roi.containsPoint(shape[1]-1d, i);
				
				List<Integer> inters = new ArrayList<>();
				if (cutsStart) inters.add(0);
				for (double d : hi) {
					if (!inters.contains((int)d) && d > 0 && d < shape[1]-1) inters.add((int)d);
				}
				if (cutsEnd && !inters.contains(shape[1]-1)) inters.add(shape[1]-1);
				
				int[] start = new int[]{i,0};
				int[] stop = new int[]{i+1,0};
				int[] step = new int[]{1,1};
				
				while (!inters.isEmpty()) {
					
					if (inters.size() == 1) {
						start[1] = inters.get(0);
						stop[1] = start[1]+1;
						IDataset section = data.getSliceView(start, stop, step);
						sum +=maskedSum(section);
						inters.remove(0);
					} else {
						int s = inters.get(0);
						int e = inters.get(1);
						
						if (roi.containsPoint(s+(e-s)/2d, i)) {
							start[1] = s;
							stop[1] = e;
							IDataset section = data.getSliceView(start, stop, step);
							sum +=maskedSum(section);
							inters.remove(0);
						} else {
							start[1] = inters.get(0);
							stop[1] = start[1]+1;
							IDataset section = data.getSliceView(start, stop, step);
							sum +=maskedSum(section);
							inters.remove(0);
						}
					}
				}
			}
		}
		
		return sum;
	}
	
	private double maskedSum(IDataset data){
		
		Dataset slice = DatasetUtils.convertToDataset(data.getSlice());
		
		MaskMetadata mask = slice.getFirstMetadata(MaskMetadata.class);
		if (mask != null) {
			IDataset m = mask.getMask();
			IDataset s = m.getSlice();
			slice.imultiply(s);
		}
		
		return (double)slice.sum(true);
	}

}
