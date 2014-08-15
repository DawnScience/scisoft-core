/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
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
