/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.utils;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class ProcessingUtils {
	
	public static ILazyDataset getLazyDataset(IOperation op, String filepath, String datasetName) throws OperationException {
		
		IDataHolder dh = null;
		
		try {
			dh = LoaderFactory.getData(filepath);
		} catch (Exception e) {
			//ignore
		}
		
		if (dh == null) throw new OperationException(op,"Error opening file: " + filepath);
		
		ILazyDataset lz = dh.getLazyDataset(datasetName);
		
		if (lz == null) throw new OperationException(op,"Error reading dataset: " + datasetName);
		
		return lz;
		
	}

	public static IDataset getDataset(IOperation op, String filepath, String datasetName) throws OperationException {
		ILazyDataset lz = getLazyDataset(op, filepath, datasetName);
		try {
			return lz.getSlice();
		} catch (DatasetException e) {
			throw new OperationException(op, e);
		}
	}

}
