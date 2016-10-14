/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;

public class ProcessingUtilsForDialog {
	
	public static ILazyDataset getLazyDataset(String filepath, String datasetName) throws OperationException {
		
		IDataHolder dh = null;
		
		try {
			dh = LocalServiceManager.getLoaderService().getData(filepath,null);
		} catch (Exception e) {
			//ignore
		}
		
		ILazyDataset lz = dh.getLazyDataset(datasetName);
		
		return lz;
		
	}

	public static IDataset getDataset(String filepath, String datasetName) throws OperationException {
		ILazyDataset lz = getLazyDataset(filepath, datasetName);
		try {
			return lz.getSlice();
		} catch (DatasetException e) {
			return null;
		}
	}

}