/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.Arrays;

import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataUtils {

	private final static Logger logger = LoggerFactory.getLogger(MetadataUtils.class);
	
	public static Dataset[] getAxes(IDataset input) {
		AxesMetadata md = input.getFirstMetadata(AxesMetadata.class);
		
		if (md == null) return null;
		
		ILazyDataset[] axes = md.getAxes();
		
		if (axes.length != input.getRank()) return null;
		
		Dataset[] datasetAxes = new Dataset[axes.length];
		
		for (int i = 0; i < axes.length; i++) {
			if (axes[i] != null)
				try {
					datasetAxes[i] = DatasetUtils.sliceAndConvertLazyDataset(axes[i]);
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
	
	public static Dataset[] getAxesAndMakeMissing(IDataset input) {
		Dataset[] axes = getAxes(input);
		
		if (axes == null) axes = new Dataset[input.getRank()];
		
		int[] shape = input.getShape();
		
		for (int i = 0; i < axes.length; i++) {
			if (axes[i] == null) {
				axes[i] = DatasetFactory.createRange(shape[i]);
			}
		}
		
		return axes;
	}

	public static void setAxes(IDataset d, Dataset... axes) {
		if (d.getRank() == 0) {
			return;
		}
		AxesMetadata am;
		try {
			am = MetadataFactory.createMetadata(AxesMetadata.class, d.getRank());
			for (int i = 0; i < axes.length; i++) {
				Dataset a = axes[i];
				if (a != null) {
					try {
						am.setAxis(i, a);
					} catch (Exception e) {
						System.err.println(Arrays.toString(a.getShapeRef()) + " cf " + Arrays.toString(d.getShape()));
					}
				}
				
			}
			d.addMetadata(am);
		} catch (Exception e) {
		}
	}

}
