/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.metadata;

import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(MetadataUtils.class);
	
	/**
	 * Convert all the ILazyDataset in AxesMetadata to Datasets
	 * @param data
	 */
	public static void sliceAxesMetadata(IDataset data) {
		AxesMetadata am = data.getFirstMetadata(AxesMetadata.class);
		if (am == null) {
			return;
		}

		int rank = data.getRank();
		for (int i = 0; i < rank; i++) {
			ILazyDataset[] axes = am.getAxis(i);
			if (axes != null) {
				for (int j = 0, jmax = axes.length; j < jmax; j++) {
					try {
						axes[j] = DatasetUtils.sliceAndConvertLazyDataset(axes[j]);
					} catch (DatasetException e) {
						logger.error("Could not replace lazydataset!");
					}
				}
				am.setAxis(i, axes);
			}
		}
	}

}
