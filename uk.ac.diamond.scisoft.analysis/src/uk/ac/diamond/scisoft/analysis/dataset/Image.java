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

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.MapToRotatedCartesian;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

/**
 * Image processing package
 */
public class Image {
	/**
	 * Setup the logging facilities
	 */
	transient protected static final Logger logger = LoggerFactory.getLogger(Image.class);

	/**
	 * Find translation shift between two 2D datasets
	 * @param a
	 * @param b
	 * @param r rectangular region of interest to use for alignment
	 * @return a vector containing the translation needed to be applied to dataset b to align with dataset a
	 */
	public static double[] findTranslation2D(AbstractDataset a, AbstractDataset b, RectangularROI r) {
		if (a.getRank() != 2 || b.getRank() != 2) {
			logger.error("Both datasets should be two-dimensional");
			throw new IllegalArgumentException("Both datasets should be two-dimensional");
		}
		AbstractDataset f, g;
		if (r == null) {
			f = a;
			g = b;
		} else {
			MapToRotatedCartesian rcmap = new MapToRotatedCartesian(r);
			f = rcmap.value(a).get(0);
			g = rcmap.value(b).get(0);
		}

//		logger.info("f {} {}", f.shape, f.getElementDoubleAbs(0));
//		logger.info("g {} {}", g.shape, g.getElementDoubleAbs(0));

		List<AbstractDataset> corrs = Signal.phaseCorrelate(f, g, null, true);
		AbstractDataset pcorr = corrs.get(0);
		int[] maxpos = pcorr.maxPos(); // peak pos
		int[] xshape = pcorr.shape;
		double mvalue = pcorr.max().doubleValue();

		logger.info("Max at {} is {}", maxpos, mvalue);
		double[] shift = new double[2];

		// Refine shift using inverse of cross-power spectrum
		// Foroosh et al, "Extension of Phase Correlation to Subpixel Registration",
		// IEEE Trans. Image Processing, v11n3, 188-200 (2002)
		AbstractDataset xcorr = corrs.get(1);

		double c0 = xcorr.getDouble(maxpos);

		for (int i = 0; i < 2; i++) {
			maxpos[i]++;
			if (maxpos[i] < xshape[i]) {
				final double c1 = xcorr.getDouble(maxpos);
				shift[i] = c1/(c1-c0);
				if (Math.abs(shift[i]) > 1)
					shift[i] = c1/(c1+c0);
			} 
			maxpos[i]--;
		}
		logger.info("Partial shift is {}", shift);

		for (int i = 0; i < 2; i++) {
			shift[i] += maxpos[i];
			if (shift[i] > xshape[i]/2) {
				shift[i] -= xshape[i];
				logger.info("  Unwrapped position to {}", shift[i] );
			}
		}
		logger.info("Shift is {}", shift);

		return shift;
	}
}
