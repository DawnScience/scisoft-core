/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.RunningAverage;

import uk.ac.diamond.osgi.services.ServiceProvider;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.operations.mask.ImportMaskModel;

public class AverageGridScanMaskOperation extends AbstractOperation<ImportMaskModel, OperationData> implements IExportOperation{

	private RunningAverage average;
	private BooleanDataset mask;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AverageGridScanMaskOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (mask == null) {
			//load mask, checking it is the same size as the
			//not-data-frame data dimensions
			int rank = ssm.getParent().getRank();
			int[] lzShape = ssm.getParent().getShape();

			if (rank - ssm.getDataDimensions().length != 2) throw new OperationException(this, "Process requires scan of rank 2");
			
			//get the dimensions of the original dataset which correspond to the processed frame
			int[] dd = ssm.getDataDimensions();
			
			//build an ordered primitive array of the non-data-frame dimensions
			Set<Integer> setData = Arrays.stream(dd).boxed().collect(Collectors.toSet());
			Set<Integer> full = IntStream.range(0, rank).boxed().collect(Collectors.toCollection(LinkedHashSet::new));

			full.removeAll(setData);

			if (full.size() != 2) throw new OperationException(this, "Process requires scan of rank 2");

			//map this to an array of the dimension sizes
			//this should be equal to the shape of the mask
			int[] shape = full.stream().mapToInt(i -> lzShape[i]).toArray();

			mask = getMask(model.getFilePath(), shape);

		}
		
		if (mask.getAbs(ssm.getSliceInfo().getSliceNumber())) {
			if (average == null) {
				average = new RunningAverage(input);
			} else {
				average.update(input);
			}
		}
		
		if (ssm.getSliceInfo().isLastSlice()) {
			IDataset out = average.getCurrentAverage();
			copyMetadata(input, out);
			out.clearMetadata(SliceFromSeriesMetadata.class);
			average = null;
			SliceFromSeriesMetadata outsmm = ssm.clone();
			for (int i = 0; i < ssm.getParent().getRank(); i++) {
				
				if (!outsmm.isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
				
			}
			out.setMetadata(outsmm);
			
			return new OperationData(out);
		}
		
		return null;
	}
	
	private BooleanDataset getMask(String path, int[] shape) {

		try {
			if (HDF5Utils.isHDF5(path)) {
				IPersistenceService service = ServiceProvider.getService(IPersistenceService.class);
				IPersistentFile pf = service.getPersistentFile(path);
				IDataset m = pf.getMask(pf.getMaskNames(null).get(0),null);
				if (Arrays.equals(m.squeeze().getShape(), shape)) {
					return DatasetUtils.cast(BooleanDataset.class, m);
				} else {
					throw new IllegalArgumentException("mask not compatible shape");
				}

			} else {
				IDataHolder dh = LoaderFactory.getData(path);
				IDataset ds = dh.getDataset(0);
				if (Arrays.equals(ds.squeeze().getShape(), shape)) {
					return DatasetUtils.cast(BooleanDataset.class,ds);
				} else {
					throw new IllegalArgumentException("mask not compatible shape");
				}
			}

		} catch (Exception e) {
			throw new OperationException(this, e);
		}

	}
	
	@Override
	public void setModel(ImportMaskModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					mask = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}


}
