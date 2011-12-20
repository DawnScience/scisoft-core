/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

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
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
		DataHolder dhNew = new DataHolder();
		for (int i = 0, imax = dh.size(); i < imax; i++) {
			AbstractDataset data = dh.getDataset(i);
			if (Double.isNaN(gmin)) {
				double min = data.min().doubleValue();
				double max = data.max().doubleValue();
				if (min == max) {
					data = AbstractDataset.zeros(data); // if user saves an image with same value for all pixels
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
