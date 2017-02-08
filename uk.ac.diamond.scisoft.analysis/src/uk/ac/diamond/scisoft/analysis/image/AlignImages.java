/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.Image;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROIList;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.MapToShiftedCartesian;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.utils.FileUtils;

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
	 * @param monitor
	 * @return shifts
	 */
	public static List<double[]> align(final IDataset[] images, final List<IDataset> shifted, 
			final RectangularROI roi, final boolean fromStart, double[] preShift, IMonitor monitor) {
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
			if (monitor != null) {
				if(monitor.isCancelled())
					return shift;
				monitor.worked(1);
			}
		}
		return shift;
	}

	/**
	 * 
	 * @param files
	 * @param shifted images
	 * @param roi
	 * @param fromStart
	 * @param preShift
	 * @return shifts
	 */
	public static List<double[]> align(final String[] files, final List<IDataset> shifted, final RectangularROI roi, final boolean fromStart, final double[] preShift, IMonitor monitor) {
		IDataset[] images = new IDataset[files.length];

		for (int i = 0; i < files.length; i++) {
			try {
				images[i] = LoaderFactory.getData(files[i], false, null).getDataset(0);
			} catch (Exception e) {
				logger.error("Cannot load file {}", files[i]);
				throw new IllegalArgumentException("Cannot load file " + files[i]);
			}
		}

		return align(images, shifted, roi, fromStart, preShift, monitor);
	}

	/**
	 * @param data
	 *            original list of dataset to be aligned
	 * @param shifts
	 *            output where to put resulting shifts
	 * @param roi
	 *            rectangular ROI used for the alignment
	 * @param mode
	 *            number of columns used: can be 2 or 4
	 * @param monitor
	 * @return aligned list of dataset
	 */
	public static List<IDataset> alignWithROI(List<IDataset> data, List<List<double[]>> shifts, RectangularROI roi, int mode, IMonitor monitor) {
		int nsets = data.size() / mode;

		if (roi == null)
			return null;
		RectangularROIList rois = new RectangularROIList();
		rois.add(roi);

		if (shifts == null)
			shifts = new ArrayList<List<double[]>>();
		List<IDataset> shiftedImages = new ArrayList<IDataset>();

		int index = 0;
		int nr = rois.size();
		if (nr > 0) {
			if (nr < mode) { // clean up roi list
				if (mode == 2) {
					rois.add(rois.get(0));
				} else {
					switch (nr) {
					case 1:
						rois.add(rois.get(0));
						rois.add(rois.get(0));
						rois.add(rois.get(0));
						break;
					case 2:
					case 3:
						rois.add(2, rois.get(0));
						rois.add(3, rois.get(1));
						break;
					}
				}
			}

			IDataset[] tImages = new IDataset[nsets];
			List<IDataset> shifted = new ArrayList<IDataset>(nsets);
			boolean fromStart = false;
			// align first images across columns:
			// Example: [0,1,2]-[3,4,5]-[6,7,8]-[9,10,11] for 12 images on 4 columns
			// with images 0,3,6,9 as the top images of each column.
			List<double[]> topShifts = new ArrayList<double[]>();
			IDataset[] topImages = new IDataset[mode];
			List<IDataset> anchorList = new ArrayList<IDataset>();
			for (int i = 0; i < mode; i++) {
				topImages[i] = data.get(i * nsets);
			}
			// align top images
			topShifts = align(topImages, anchorList, rois.get(0), true, null, monitor);

			for (int p = 0; p < mode; p++) {
				for (int i = 0; i < nsets; i++) {
					tImages[i] = data.get(index++);
				}
				IDataset anchor = anchorList.get(p);
				shifted.clear();
				// align rest of images
				shifts.add(align(tImages, shifted, rois.get(p), true, topShifts.get(p), monitor));
				shifted.remove(0); // remove unshifted anchor
				shiftedImages.add(anchor); // add shifted anchor
				shiftedImages.addAll(shifted); // add aligned images

				fromStart = !fromStart;
				if (monitor != null) {
					if (monitor.isCancelled())
						return shiftedImages;
					monitor.worked(1);
				}
			}
		}
		return shiftedImages;
	}

	/**
	 * Aligns images from a lazy dataset and returns a lazy dataset. This alignment process saves the aligned data in an
	 * hdf5 file saved on disk and this method can be used without running into a MemoryOverflowError.
	 * 
	 * @param data
	 *            original list of dataset to be aligned
	 * @param shifts
	 *            output where to put resulting shifts
	 * @param roi
	 *            rectangular ROI used for the alignment
	 * @param mode
	 *            number of columns used: can be 2 or 4
	 * @param monitor
	 * @return aligned list of dataset
	 */
	public static ILazyDataset alignLazyWithROI(ILazyDataset data, List<List<double[]>> shifts, RectangularROI roi,
			int mode, IMonitor monitor) {

		int nsets = data.getShape()[0] / mode;

		RectangularROIList rois = new RectangularROIList();
		rois.add(roi);

		if (shifts == null)
			shifts = new ArrayList<List<double[]>>();

		int nr = rois.size();

		// save on a temp file
		String file = FileUtils.getTempFilePath("aligned.h5");
		String path = "/entry/data/";
		String name = "aligned";
		File tmpFile = new File(file);
		if (tmpFile.exists())
			tmpFile.delete();

		int[] shape = data.getShape();
		int[] chunking = new int[] {1, shape[1], shape[2]};
		ILazyWriteableDataset lazy = HDF5Utils.createLazyDataset(file, path, name, shape, null,
				chunking, Dataset.FLOAT32, null, false);

		if (nr > 0) {
			if (nr < mode) { // clean up roi list
				if (mode == 2) {
					rois.add(rois.get(0));
				} else {
					switch (nr) {
					case 1:
						rois.add(rois.get(0));
						rois.add(rois.get(0));
						rois.add(rois.get(0));
						break;
					case 2:
					case 3:
						rois.add(2, rois.get(0));
						rois.add(3, rois.get(1));
						break;
					}
				}
			}

			IDataset[] tImages = new IDataset[nsets];
			List<IDataset> shifted = new ArrayList<IDataset>(nsets);
			boolean fromStart = false;
			// align first images across columns:
			// Example: [0,1,2]-[3,4,5]-[6,7,8]-[9,10,11] for 12 images on 4 columns
			// with images 0,3,6,9 as the top images of each column.
			List<double[]> topShifts = new ArrayList<double[]>();
			IDataset[] topImages = new IDataset[mode];
			List<IDataset> anchorList = new ArrayList<IDataset>();
			for (int i = 0; i < mode; i++) {
				try {
					int n = i * nsets;
					topImages[i] = data.getSlice(new Slice(n, n+1)).squeeze();
				} catch (DatasetException e) {
					logger.error("Could not get slice of image", e);
				}
			}
			// align top images
			topShifts = align(topImages, anchorList, rois.get(0), true, null, monitor);
			int index = 0;
			int idx = 0;
			for (int p = 0; p < mode; p++) {
				for (int i = 0; i < nsets; i++) {
					try {
						tImages[i] = data.getSlice(new Slice(index++, index)).squeeze();
					} catch (DatasetException e) {
						logger.error("Could not get slice of image", e);
					}
				}
				IDataset anchor = anchorList.get(p);
				shifted.clear();
				try {
					// align rest of images
					shifts.add(align(tImages, shifted, rois.get(p), true, topShifts.get(p), monitor));
					shifted.remove(0); // remove unshifted anchor

					appendDataset(lazy, anchor, idx, monitor);
					idx++;
					for (int i = 0; i < shifted.size(); i++) {
						appendDataset(lazy, shifted.get(i), idx, monitor);
						idx++;
					}

				} catch (DatasetException e) {
					logger.warn("Problem with alignment: " + e);
					return null;
				}

				fromStart = !fromStart;
				if (monitor != null) {
					if (monitor.isCancelled()) {
						return lazy;
					}
					monitor.worked(1);
				}
			}
		}
		return lazy;
	}

	private static void appendDataset(ILazyWriteableDataset lazy, IDataset data, int idx, IMonitor monitor) throws DatasetException {
		int[] shape = lazy.getShape();
		SliceND ndSlice = new SliceND(shape, new int[] {idx, 0, 0}, new int[] {idx + 1, shape[1], shape[2]}, null);
		lazy.setSlice(monitor, data, ndSlice);
	}
}
