/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.ROIVisitor;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceVisitor;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
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
		SumVisitor v = new SumVisitor();
		try {
			ROIVisitor.visitHorizontalSections(data, roi, v);
		} catch (Exception e) {
			throw new OperationException(this,e);
		}
		
		return v.getSum();
	}
	
	
	
	private class SumVisitor implements SliceVisitor {

		private double sum = 0;
		
		@Override
		public void visit(IDataset data) throws Exception {
			sum+=maskedSum(data);
			
		}

		@Override
		public boolean isCancelled() {
			return false;
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

		public double getSum() {
			return sum;
		}
		
	}

}
