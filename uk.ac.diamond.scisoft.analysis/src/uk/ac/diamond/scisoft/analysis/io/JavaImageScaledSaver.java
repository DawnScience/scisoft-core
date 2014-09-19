/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * 
 *
 */
public class JavaImageScaledSaver extends JavaImageSaver {
	double gmin = Double.NaN;
	double gmax = Double.NaN;

	/**
	 * @param FileName
	 * @param FileType
	 * @param NumBits
	 */
	public JavaImageScaledSaver(String FileName, String FileType, int NumBits, boolean asUnsigned) {
		super(FileName, FileType, NumBits, asUnsigned);
	}

	/**
	 * 
	 * @param FileName
	 * @param FileType
	 * @param NumBits
	 * @param min value for global minimum
	 * @param max value for global maximum
	 */
	public JavaImageScaledSaver(String FileName, String FileType, int NumBits, boolean asUnsigned, double min, double max) {
		super(FileName, FileType, NumBits, asUnsigned);
		if (min >= max) {
			throw new IllegalArgumentException("Minimum value supplied was not greater than maximum value");
		}
		gmin = min;
		gmax = max;
	}

	@Override
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		DataHolder dhNew = new DataHolder();
		for (int i = 0, imax = dh.size(); i < imax; i++) {
			IDataset idata = dh.getDataset(i);
			Dataset data = DatasetUtils.convertToDataset(idata);
			if (Double.isNaN(gmin)) {
				double min = data.min().doubleValue();
				double max = data.max().doubleValue();
				if (min == max) {
					data = DatasetFactory.zeros(data); // if user saves an image with same value for all pixels
				} else {
					if (unsigned && min >= -maxVal / 2) {
						data = Maths.multiply(data, maxVal / max);
						data = Maths.clip(data, 0, maxVal);
					} else {
						data = DatasetUtils.norm(data);
						data.imultiply(maxVal);
					}
				}
			} else {
				data = Maths.clip(data, gmin, gmax);
				data.isubtract(gmin);
				data.imultiply(maxVal / (gmax - gmin));
			}
			dhNew.addDataset(dh.getName(i), data);
		}
		super.saveFile(dhNew);
	}

}
