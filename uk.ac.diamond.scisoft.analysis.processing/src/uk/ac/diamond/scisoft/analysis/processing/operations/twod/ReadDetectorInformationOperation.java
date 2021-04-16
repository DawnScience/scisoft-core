/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;

@Atomic
public class ReadDetectorInformationOperation extends AbstractOperation<ReadDetectorInformationModel, OperationData> {

	private volatile DetectorInformation info;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.ReadDetectorInformationOperation";
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
		info = null;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		input = input.getSliceView();
		DetectorInformation d = getDetectorInformation(model,ssm.getFilePath(), ssm.getParent(), ssm.getDatasetName());
		input.setMetadata(d.metadata);
		if (d.mask != null) {
			MaskMetadata mm;
			try {
				mm = MetadataFactory.createMetadata(MaskMetadata.class, d.mask);
			} catch (MetadataException e) {
				throw new OperationException(this, e);
			}
			input.setMetadata(mm);
		}
		
		return new OperationData(input);
	}
	
	private DetectorInformation getDetectorInformation(ReadDetectorInformationModel mod, String path, ILazyDataset parent, String name) {

		DetectorInformation localInfo = info;
		if (localInfo == null) {
			synchronized(this) {
				localInfo = info;
				if (localInfo == null) {
					DetectorInformation i = new DetectorInformation();
					try {
						if (model.isReadGeometry()) {
							IDiffractionMetadata md = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(path, parent, name);
							i.metadata = md;
						}
						
						if (model.isReadMask()) {
							IDataset d = NexusDiffractionCalibrationReader.getDetectorPixelMaskFromNexus(path, parent);
							if (d != null) {
								i.mask = Comparisons.equalTo(d, 0);
							}
						}
					} catch (Exception e) {
						throw new OperationException(this, e);
					}
					
					if (i.mask == null && i.metadata == null) i = null;;
					
					info = localInfo = i;
					
				}
			}
		}
		return localInfo;
	}
	
	
	@Override
	public void setModel(ReadDetectorInformationModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					info = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
	private class DetectorInformation {
		public IDiffractionMetadata metadata;
		public IDataset mask;
	}
	
}
