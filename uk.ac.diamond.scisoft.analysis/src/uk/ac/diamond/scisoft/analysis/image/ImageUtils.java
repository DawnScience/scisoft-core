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
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;

public class ImageUtils {

	/**
	 * Find peaks in data that are defined by being within the threshold values
	 * and are the maximum in each window
	 * @param data
	 * @param window size of window
	 * @param low lower threshold value for centre of peak
	 * @param high upper threshold value for centre of peak
	 * @return list of their sum, centroids (as coordinates in N x rank dataset), centre fraction and all values
	 */
	public static List<Dataset> findWindowedPeaks(Dataset data, int window, double low, double high) {
		int[] shape = data.getShapeRef();
		int rank = shape.length;
		List<Double> sum = new ArrayList<Double>();
		List<Double> centroid = new ArrayList<Double>();
		List<Double> fraction = new ArrayList<Double>();
		List<Double> values = new ArrayList<Double>();

		IndexIterator it = data.getIterator(true);
		int half = window/2;
		int[] wShape = new int[rank];
		int[] ub = new int[rank];
		for (int i = 0; i < rank; i++) {
			ub[i] = shape[i] - window;
			wShape[i] = window;
		}
		Dataset base = DatasetFactory.createRange(DoubleDataset.class, window);
		data = data.cast(DoubleDataset.class);
		DoubleDataset sliced = DatasetFactory.zeros(wShape);
		int ssize = sliced.getSize();
		double[] sdata = sliced.getData();

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
					// TODO update to 2.1
					data.fillDataset(sliced, data.getSliceIterator(slice.getStart(), slice.getStop(), slice.getStep()));
					sliced.setDirty(); // remove for 2.1
					if (sliced.max().doubleValue() <= v) { // this does not check if other non-central pixels are equal to v
						double total = ((Number) sliced.sum()).doubleValue();
						sum.add(total);
						List<Double> c = centroidFn.value(sliced);
						for (int i = 0; i < rank; i++) {
							centroid.add(pos[i] + c.get(i));
						}
						fraction.add(v/total);
						for (int i = 0; i < ssize; i++) {
							values.add(sdata[i]);
						}
						// bump along to next window
						for (int i = 0; i < window; i++) {
							if (!it.hasNext() || !windowIsInsideVolume(ub, pos)) {
								break;
							}
						}
					}
				}
			}
		}

		List<Dataset> list = new ArrayList<>();
		if (sum.size() == 0) {
			list.add(DatasetFactory.zeros(0));
			list.add(DatasetFactory.zeros(0, rank));
			list.add(DatasetFactory.zeros(0));
			list.add(DatasetFactory.zeros(0));
		} else {
			list.add(DatasetFactory.createFromList(sum));
			list.add(DatasetFactory.createFromList(centroid).reshape(sum.size(), rank));
			list.add(DatasetFactory.createFromList(fraction));
			list.add(DatasetFactory.createFromList(values));
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
		for (int i = ub.length - 1; i >= 0; i--) {
			if (pos[i] > ub[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Shift image using linear interpolation
	 * @param im
	 * @param shifts
	 * @return shifted image
	 */
	public static Dataset shiftImage(Dataset im, double[] shifts) {
		if (im.getRank() != 2) {
			throw new IllegalArgumentException("Dataset must be 2d");
		}
		if (shifts.length < 2) {
			throw new IllegalArgumentException("Shift array must have at least two entries");
		}
		Dataset newImage = DatasetFactory.zeros(im, DoubleDataset.class);
		int[] shape = im.getShapeRef();
	
		double cx0, cx1;
		for (int x0 = 0; x0 < shape[0]; x0++) {
			cx0 = x0 + shifts[0];
			for (int x1 = 0; x1 < shape[1]; x1++) {
				cx1 = x1 + shifts[1];
				newImage.set(Maths.interpolate(im, cx0, cx1), x0, x1);
			}
		}
	
		return newImage;
	}
}
