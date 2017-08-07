/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.image;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.function.Centroid;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.SliceND;

public class ImageUtils {

	/**
	 * Find peaks in data that are defined by being within the threshold values
	 * and are the maximum in each window
	 * @param data
	 * @param window size of window
	 * @param low lower threshold value for centre of peak
	 * @param high upper threshold value for centre of peak
	 * @return list of their sum, centroids (as coordinates in N x rank dataset) and centre fraction
	 */
	public static List<Dataset> findWindowedPeaks(Dataset data, int window, double low, double high) {
		int[] shape = data.getShapeRef();
		int rank = shape.length;
		List<Double> sum = new ArrayList<Double>();
		List<Double> centroid = new ArrayList<Double>();
		List<Double> fraction = new ArrayList<Double>();

		IndexIterator it = data.getIterator(true);
		int half = window/2;
		int[] ub = new int[rank];
		for (int i = 0; i < rank; i++) {
			ub[i] = shape[i] - window;
		}
		Dataset base = DatasetFactory.createRange(DoubleDataset.class, window);
		base.iadd(0.5); // shift to centre of pixel
		Dataset[] bases = new Dataset[rank];
		for (int i = 0; i < rank; i++) {
			bases[i] = base;
		}
		Centroid centroidFn = new Centroid(bases);

		int[] centre = new int[rank];
		int[] pos = it.getPos();
		SliceND slice = new SliceND(shape);
		
		while (it.hasNext()) {
			if (windowIsInsideVolume(ub, pos)) {
				for (int i = 0; i < rank; i++) {
					centre[i] = pos[i] + half;
				}
				double v = data.getDouble(centre);
				if (v > low && v < high) {
					for (int i = 0; i < rank; i++) {
						int p = pos[i];
						slice.setSlice(i, p, p + window, 1);
					}
					Dataset sliced = data.getSliceView(slice);
					if (sliced.max().doubleValue() <= v) { // this does not check if other non-central pixels are equal to v
						double total = ((Number) sliced.sum()).doubleValue();
						sum.add(total);
						List<Double> c = centroidFn.value(sliced);
						for (int i = 0; i < rank; i++) {
							centroid.add(pos[i] + c.get(i));
						}
						fraction.add(v/total);
					}
				}
			}
		}

		List<Dataset> list = new ArrayList<>();
		if (sum.size() == 0) {
			list.add(DatasetFactory.zeros(0));
			list.add(DatasetFactory.zeros(0, rank));
			list.add(DatasetFactory.zeros(0));
		} else {
			list.add(DatasetFactory.createFromList(sum));
			list.add(DatasetFactory.createFromList(centroid).reshape(sum.size(), rank));
			list.add(DatasetFactory.createFromList(fraction));
		}
		return list;
	}

	/**
	 * Check if window is entirely within volume
	 * @param ub upper bound on position
	 * @param pos window position
	 * @return true if window does not overlap boundary of volume
	 */
	private static boolean windowIsInsideVolume(int[] ub, int[] pos) {
		for (int i = 0; i < ub.length; i++) {
			if (pos[i] > ub[i]) {
				return false;
			}
		}
		return true;
	}
}
