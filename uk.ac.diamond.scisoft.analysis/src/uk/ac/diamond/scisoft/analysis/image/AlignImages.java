/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Image;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.MapToShiftedCartesian;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class AlignImages {
	private static final Logger logger = LoggerFactory.getLogger(AlignImages.class);

	/**
	 * Align images
	 * @param images datasets
	 * @param shifted images
	 * @param roi
	 * @param fromStart direction of image alignment: should currently be set to true 
	 * (the method needs to be re-modified to take into account the flip mode)
	 * @param preShift
	 * @return shifts
	 */
	public static List<double[]> align(final IDataset[] images, final List<IDataset> shifted, 
			final RectangularROI roi, final boolean fromStart, double[] preShift) {
		List<IDataset> list = new ArrayList<IDataset>();
		Collections.addAll(list, images);
		if (!fromStart) {
			Collections.reverse(list);
		}
		final IDataset anchor = list.get(0);
		final int length = list.size();
		final List<double[]> shift = new ArrayList<double[]>();
		if (preShift != null) {
			shift.add(preShift);
		} else {
			shift.add(new double[] {0., 0.});
		}
		shifted.add(anchor);
		for (int i = 1; i < length; i++) {
			IDataset image = list.get(i);
			
			double[] s = Image.findTranslation2D(anchor, image, roi);
			// We add the preShift to the shift data
			if (preShift != null) {
				s[0] += preShift[0];
				s[1] += preShift[1];
			}
			shift.add(s);
			MapToShiftedCartesian map = new MapToShiftedCartesian(s[0], s[1]);
			Dataset data = map.value(image).get(0);
			data.setName("aligned_" + image.getName());
			shifted.add(data);
		}
		return shift;
	}

	/**
	 * Align images 
	 * @param files
	 * @param shifted images
	 * @param roi
	 * @param fromStart
	 * @param preShift
	 * @return shifts
	 */
	public static List<double[]> align(final String[] files, final List<IDataset> shifted, final RectangularROI roi, final boolean fromStart, final double[] preShift) {
		IDataset[] images = new IDataset[files.length];

		for (int i = 0; i < files.length; i++) {
			try {
				images[i] = LoaderFactory.getData(files[i], false, null).getDataset(0);
			} catch (Exception e) {
				logger.error("Cannot load file {}", files[i]);
				throw new IllegalArgumentException("Cannot load file " + files[i]);
			}
		}

		return align(images, shifted, roi, fromStart, preShift);
	}
}
