/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataUtils {

	private final static Logger logger = LoggerFactory.getLogger(MetadataUtils.class);
	
	public static IDataset[] getAxes(IDataset input) {
		AxesMetadata md = input.getFirstMetadata(AxesMetadata.class);
		
		if (md == null) return null;
		
		ILazyDataset[] axes = md.getAxes();
		
		if (axes.length != input.getRank()) return null;
		
		IDataset[] datasetAxes = new IDataset[axes.length];
		
		for (int i = 0; i < axes.length; i++) {
			if (axes[i] != null)
				try {
					datasetAxes[i] = axes[i].getSlice();
				} catch (DatasetException e) {
					logger.error("Could not slice axes datasets",e);
				}
		}
		
		boolean allNull = true;
		
		for (IDataset a : datasetAxes) {
			if (a != null) {
				allNull = false;
				break;
			}
		}
		
		if (allNull) return null;
		
		return datasetAxes;
		
	}
	
	public static IDataset[] getAxesAndMakeMissing(IDataset input) {
		IDataset[] axes = getAxes(input);
		
		if (axes == null) axes = new IDataset[input.getRank()];
		
		int[] shape = input.getShape();
		
		for (int i = 0; i < axes.length; i++) {
			if (axes[i] == null) {
				axes[i] = DatasetFactory.createRange(shape[i]);
			}
		}
		
		return axes;
		
	}
	
}
