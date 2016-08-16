/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

@Atomic
public class SubtractIntegratedXRegionsOperation extends AbstractOperation<SubtractIntegratedXRegionsModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.SubtractIntegratedXRegionsOperation";
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
		
		double[] signalRange = model.getSignal();
		double[] backGroundRange0 = model.getBackground0();
		double[] backGroundRange1 = model.getBackground1();
		
		AxesMetadata axm = input.getFirstMetadata(AxesMetadata.class);
		
		Dataset signal = null;
		Dataset b0 = null;
		Dataset b1 = null;
		ILazyDataset[] axis1 = null;
		int[] nSignal = new int[1];
		int[] nOther = new int[1];
		
		if (axm == null || axm.getAxis(1) == null || axm.getAxis(1)[0] == null) {
			signal = getMean(input,(int)Math.round(signalRange[0]), (int)Math.round(signalRange[1]),nSignal);
			b0 = getMean(input,(int)Math.round(backGroundRange0[0]), (int)Math.round(backGroundRange0[1]),nOther);
			b1 = getMean(input,(int)Math.round(backGroundRange1[0]), (int)Math.round(backGroundRange1[1]),nOther);
		} else {
			
			Dataset x;
			try {
				x = DatasetUtils.sliceAndConvertLazyDataset(axm.getAxis(1)[0]);
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
			x.squeeze();
			
			signal = getMean(input,x,signalRange,nSignal);
			b0 = getMean(input,x,backGroundRange0,nOther);
			b1 = getMean(input,x,backGroundRange1,nOther);
			ILazyDataset[] axis = axm.getAxis(0);
			axis1 = axis.clone();
			for (int i = 0; i < axis1.length; i++) axis1[i] = axis1[i] == null ? null : axis1[i].getSliceView().squeezeEnds();
			
		}
		
		if (signal == null || b0 == null || b1 == null) throw new OperationException(this, "Could not get sections of data!");
		
		Dataset ds = DatasetUtils.convertToDataset(input);
		
		double m = model.isUseFullFrameForRatio() ? (double)ds.sum(true) : (double)signal.sum(true);
		
		signal.isubtract((b0.iadd(b1).idivide(2)));
		
		double m2 = (double)signal.sum(true);
		
		if (axis1 != null) {
			AxesMetadata ax;
			try {
				ax = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			} catch (MetadataException e) {
				throw new OperationException(this, e);
			}
			ax.setAxis(0, axis1);
			signal.addMetadata(ax);
		}
		
		Dataset ratio = DatasetFactory.createFromObject(new Double(m2/m));
		ratio.setName("ratio");
		
		return new OperationData(signal, ratio);
		
	}
	
	private Dataset getMean(IDataset input, Dataset x, double[] vals, int[] number) {
		
		int x0 = Maths.abs(Maths.subtract(x, vals[0])).argMin();
		int x1 = Maths.abs(Maths.subtract(x, vals[1])).argMin();
		return getMean(input, x0, x1, number);
		
	}
	
	private Dataset getMean(IDataset input, int i0, int i1, int[] number) {
		int[] startStop = i0 < i1 ? new int[] {i0,i1} : new int[] {i1,i0};
		number[0] = startStop[1]-startStop[0];
		SliceND s = new SliceND(input.getShape());
		s.setSlice(1, startStop[0], startStop[1], 1);
		Dataset d = DatasetUtils.convertToDataset(input.getSlice(s));
		MaskMetadata mmd = d.getFirstMetadata(MaskMetadata.class);
		if (mmd != null) {
			Dataset m;
			try {
				m = DatasetUtils.sliceAndConvertLazyDataset(mmd.getMask());
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
			if (m instanceof BooleanDataset) {
				d.setByBoolean(Double.NaN, Comparisons.logicalNot(m));
			}
			
		}
		Dataset mean = d.mean(true,1);
		
		DatasetUtils.makeFinite(mean);
		
		return mean;
	}

}
