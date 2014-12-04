/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import java.util.Arrays;

import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.MaskMetadataImpl;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class ImportMaskOperation<T extends ImportMaskModel> extends AbstractOperation<ImportMaskModel,OperationData>{

	
	IDataset mask;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ImportMaskOperation";
	}
	
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		IDataset inM = DatasetUtils.convertToDataset(getFirstMask(input));
		
		if (mask == null) {
			String filePath = ((ImportMaskModel)model).getFilePath();
			try {
				
				if (HierarchicalDataFactory.isHDF5(filePath)) {
					IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
					IPersistentFile pf = service.getPersistentFile(filePath);
					IDataset m = pf.getMask(pf.getMaskNames(null).get(0),null);
					if (Arrays.equals(m.squeeze().getShape(), input.getShape())) {
						mask = m;
					} else {
						throw new IllegalArgumentException("mask not compatible shape");
					}
					
				} else {
					IDataHolder dh = LoaderFactory.getData(filePath);
					IDataset ds = dh.getDataset(0);
					if (Arrays.equals(ds.squeeze().getShape(), input.getShape())) {
						mask = ds;
					} else {
						throw new IllegalArgumentException("mask not compatible shape");
					}
				}
				
				
			} catch (Exception e) {
				throw new OperationException(this, e);
			}
			
		}
		
		if (inM == null) {
			inM = mask;
		} else {
			inM = Comparisons.logicalAnd(inM, mask);
		}
		
		MaskMetadata mm = new MaskMetadataImpl(inM);
		input.setMetadata(mm);

		return new OperationData(input);
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
