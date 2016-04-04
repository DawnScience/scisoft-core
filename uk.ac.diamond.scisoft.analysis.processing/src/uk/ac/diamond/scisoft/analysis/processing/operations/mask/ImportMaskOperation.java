/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.MaskMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionMetaReader;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DiffractionMetadataImportModel;

@Atomic
public class ImportMaskOperation<T extends ImportMaskModel> extends AbstractOperation<ImportMaskModel,OperationData>{

	
	private volatile IDataset mask;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ImportMaskOperation";
	}
	
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (model.getFilePath() == null) throw new IllegalArgumentException("mask file path may not be null");
		
		IDataset inM = DatasetUtils.convertToDataset(getFirstMask(input));
		
		IDataset m = getMask(model.getFilePath(), input.getShape());
		
		if (inM == null) {
			inM = m;
		} else {
			inM = Comparisons.logicalAnd(inM, m);
		}
		
		MaskMetadata mm = new MaskMetadataImpl(inM);
		input.setMetadata(mm);

		return new OperationData(input);
	}
	
	private IDataset getMask(String path, int[] shape) {

		IDataset lmask = mask;
		if (lmask == null) {
			synchronized(this) {
				lmask = mask;
				if (lmask == null) {
					try {
						if (HierarchicalDataFactory.isHDF5(path)) {
							IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
							IPersistentFile pf = service.getPersistentFile(path);
							IDataset m = pf.getMask(pf.getMaskNames(null).get(0),null);
							if (Arrays.equals(m.squeeze().getShape(), shape)) {
								mask = lmask = m;
							} else {
								throw new IllegalArgumentException("mask not compatible shape");
							}
							
						} else {
							IDataHolder dh = LoaderFactory.getData(path);
							IDataset ds = dh.getDataset(0);
							if (Arrays.equals(ds.squeeze().getShape(), shape)) {
								mask = lmask = ds;
							} else {
								throw new IllegalArgumentException("mask not compatible shape");
							}
						}
						
						
					} catch (Exception e) {
						throw new OperationException(this, e);
					}
				
					
				}
			}
		}
		return lmask;
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
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
