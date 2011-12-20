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

package uk.ac.diamond.scisoft.analysis.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Image;
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToShiftedCartesian;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

public class AlignImages {
	transient private static final Logger logger = LoggerFactory.getLogger(AlignImages.class);

	/**
	 * Align images 
	 * @param images datasets
	 * @param shifted images
	 * @param roi
	 * @param fromStart
	 * @return shifts
	 */
	public static List<double[]> align(final AbstractDataset[] images, final List<AbstractDataset> shifted, final RectangularROI roi, final boolean fromStart) {
		List<AbstractDataset> list = new ArrayList<AbstractDataset>();
		Collections.addAll(list, images);
		if (!fromStart) {
			Collections.reverse(list);
		}

		final AbstractDataset anchor = list.get(0);
		final int length = list.size();
		final List<double[]> shift = new ArrayList<double[]>();

		shift.add(new double[] {0., 0.});
		shifted.add(anchor);
		for (int i = 1; i < length; i++) {
			AbstractDataset image = list.get(i);
			double[] s = Image.findTranslation2D(anchor, image, roi);
			shift.add(s);
			MapToShiftedCartesian map = new MapToShiftedCartesian(s[0], s[1]);
			shifted.add(map.value(image).get(0));
		}

		return shift;
	}

	/**
	 * Align images 
	 * @param files
	 * @param shifted images
	 * @param roi
	 * @param fromStart
	 * @return shifts
	 */
	public static List<double[]> align(final String[] files, final List<AbstractDataset> shifted, final RectangularROI roi, final boolean fromStart) {
		AbstractDataset[] images = new AbstractDataset[files.length];

		for (int i = 0; i < files.length; i++) {
			try {
				images[i] = LoaderFactory.getData(files[i], false, null).getDataset(0);
			} catch (Exception e) {
				logger.error("Cannot load file {}", files[i]);
				throw new IllegalArgumentException("Cannot load file " + files[i]);
			}
		}

		return align(images, shifted, roi, fromStart);
	}

}
