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

import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
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
	
	public static Dataset[] getAxes(ILazyDataset input) {
		AxesMetadata md = input.getFirstMetadata(AxesMetadata.class);
		
		if (md == null) return null;
		
		ILazyDataset[] axes = md.getAxes();
		
		if (axes.length != input.getRank()) return null;
		
		Dataset[] datasetAxes = new Dataset[axes.length];
		
		for (int i = 0; i < axes.length; i++) {
			if (axes[i] != null)
				try {
					datasetAxes[i] = DatasetUtils.sliceAndConvertLazyDataset(axes[i]);
					datasetAxes[i].setName(axes[i].getName());
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

	public static Dataset[] getAxesAndMakeMissing(ILazyDataset input) {
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

	public static void setAxes(ILazyDataset d, ILazyDataset... axes) {
		setAxes(null, d, axes);
	}

	public static void setAxes(IOperation<?, ?> op, ILazyDataset d, ILazyDataset... axes) {
		if (d.getRank() == 0) {
			return;
		}

		try {
			int r = d.getRank();
			d.clearMetadata(AxesMetadata.class);
			AxesMetadata am = MetadataFactory.createMetadata(AxesMetadata.class, r);
			for (int i = 0; i < r; i++) {
				ILazyDataset a = i < axes.length ? axes[i] : null;
				if (a != null) {
					try {
						am.setAxis(i, a);
					} catch (Exception e) {
						logger.error("{} cf {}", Arrays.toString(a.getShape()), Arrays.toString(d.getShape()));
					}
				}
				
			}
			d.addMetadata(am);
		} catch (Exception e) {
			logger.error("Could not set axes", e);
			if (op != null) {
				throw new OperationException(op, "Could not set axes", e);
			}
		}
	}
}
