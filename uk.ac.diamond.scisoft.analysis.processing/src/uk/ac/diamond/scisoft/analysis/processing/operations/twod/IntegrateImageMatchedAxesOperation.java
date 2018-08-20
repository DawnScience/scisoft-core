/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;

@Atomic
public class IntegrateImageMatchedAxesOperation extends AbstractOperation<IntegrateImageMatchedAxesModel, OperationData> {

	private volatile IPixelIntegrationCache cache;
	private PropertyChangeListener listener;
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.IntegrateImageMatchedAxesOperation";
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
		
		IDataset[] axes = MetadataUtils.getAxesAndMakeMissing(input);
		
		IDataset mask = null;
		
		MaskMetadata mm = input.getFirstMetadata(MaskMetadata.class);
		
		if (mm != null && mm.getMask() != null) {
			mask = mm.getMask();
		}
		
		
		IPixelIntegrationCache cache = getCache(model,axes);
		
		List<Dataset> out = PixelIntegration.integrate(input, mask, cache);
		
		IDataset data = out.get(1);
		
		AxesMetadata amd;
		try {
			amd = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		amd.setAxis(0, out.get(0));
		data.setMetadata(amd);
		
		
		return new OperationData(data);
	}
	
	
	private IPixelIntegrationCache getCache(
			IntegrateImageMatchedAxesModel model, IDataset[] axes) {

		IPixelIntegrationCache lcache = cache;
		if (lcache == null) {
			synchronized(this) {
				lcache = cache;
				if (lcache == null) {
					int nBins = model.getnBins() != null ? model.getnBins() : (int)Math.hypot(axes[0].getSize(), axes[1].getSize());
					
					
					ImageIntegrationCache c = new ImageIntegrationCache(nBins, model.getRange(), axes[1].squeeze(), axes[0].squeeze());

					cache = lcache = c;
				}
			}
		}
		return lcache;
	}

	@Override
	public void setModel(IntegrateImageMatchedAxesModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					IntegrateImageMatchedAxesOperation.this.cache = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
	private class ImageIntegrationCache implements IPixelIntegrationCache {
		
		private Dataset xArray;
		
		private Dataset xAxis;
		
		private DoubleDataset binEdgesX = null;
		
		private double[] xRange;
		
		private int nBinsX;
		
		public ImageIntegrationCache(int nBinsX, double[] xRange, IDataset x, IDataset y) {
			
			this.xRange = xRange;
			this.nBinsX = nBinsX;
			
			initialize(DatasetUtils.convertToDataset(x),DatasetUtils.convertToDataset(y));
			
		}
		

		private void initialize(Dataset x, Dataset y) {
			
			int ys = y.getShape()[0];
			int xs = x.getShape()[0];
			
			xArray = DatasetFactory.zeros(DoubleDataset.class, new int[]{ys,  xs});
			DoubleDataset dx = (DoubleDataset)xArray;
			
			int[] pos = new int[2];
			
			for (int j = 0; j < ys; j++) {
				pos[0] = j;
				double jval = y.getElementDoubleAbs(j);
				for (int i = 0; i < xs; i++) {
					pos[1] = i;
					double ival = x.getElementDoubleAbs(i);
					dx.set(Math.hypot(ival, jval), pos);
				}
			}
			
			binEdgesX = calculateBins(xArray, this.nBinsX, xRange);
			xAxis = calculateAxis(binEdgesX, "distance");
			
		}


		private Dataset calculateAxis(DoubleDataset binEdges, String name) {
			Dataset axis = null;

			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);

			axis.setName(name);

			return axis;
		}


		private DoubleDataset calculateBins(Dataset data, int numBins, double[] binRange) {
			if (binRange != null) {
				double shift = 0;
				//				range corresponds to bin centres
				shift = (binRange[1]- binRange[0])/(2*numBins);
				return (DoubleDataset) DatasetFactory.createLinearSpace(binRange[0]-shift, binRange[1]+shift, numBins + 1, Dataset.FLOAT64);
			}


			double min = Double.MAX_VALUE;
			double max = -Double.MAX_VALUE;

			double n = data.min(true).doubleValue();
			double x = data.max(true).doubleValue();
			min = n < min ? n : min;
			max = x > max ? x : max;

			//default range corresponds to bin edges
			return (DoubleDataset) DatasetFactory.createLinearSpace(min, max, numBins + 1, Dataset.FLOAT64);
		}


		@Override
		public Dataset[] getXAxisArray() {
			return new Dataset[]{xArray};
		}

		@Override
		public Dataset[] getYAxisArray() {
			//not used
			return null;
		}

		@Override
		public double getXBinEdgeMax() {
			return binEdgesX.getAbs(binEdgesX.getSize()-1);
		}

		@Override
		public double getXBinEdgeMin() {
			return binEdgesX.getAbs(0);
		}

		@Override
		public double getYBinEdgeMax() {
			return 0;
		}

		@Override
		public double getYBinEdgeMin() {
			return 0;
		}

		@Override
		public int getNumberOfBinsXAxis() {
			return nBinsX;
		}

		@Override
		public int getNumberOfBinsYAxis() {
			return 0;
		}

		@Override
		public double[] getYAxisRange() {
			return null;
		}

		@Override
		public double[] getXAxisRange() {
			return xRange;
		}

		@Override
		public Dataset getXAxis() {
			return xAxis;
		}

		@Override
		public Dataset getYAxis() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isPixelSplitting() {
			return false;
		}

		@Override
		public boolean isTo1D() {
			return true;
		}

		@Override
		public boolean sanitise() {
			return true;
		}

		@Override
		public boolean provideLookup() {
			return false;
		}
		
	}

}
