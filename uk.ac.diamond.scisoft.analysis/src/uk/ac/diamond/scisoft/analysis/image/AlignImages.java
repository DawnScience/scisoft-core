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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROIList;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterImage2;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.utils.FileUtils;

public class AlignImages {
	private static final Logger logger = LoggerFactory.getLogger(AlignImages.class);

	/**
	 * Align images
	 * @param images datasets
	 * @param shifted images
	 * @param roi
	 * @param preShift
	 * @param monitor
	 * @return shifts
	 */
	public static List<double[]> align(final IDataset[] images, final List<Dataset> shifted, 
			final RectangularROI roi, double[] preShift, IMonitor monitor) {
		List<IDataset> list = new ArrayList<>();
		Collections.addAll(list, images);

		RegisterImage2 registerImage = new RegisterImage2();
		registerImage.setRectangle(roi);
		registerImage.setMonitor(monitor);

		// low-pass filter by averaging 9x9 pixels
		DoubleDataset filter = DatasetFactory.ones(DoubleDataset.class, 9, 9);
		filter.imultiply(1./ ((Number) filter.sum()).doubleValue());
		registerImage.setFilter(filter);
		registerImage.setShiftImage(shifted != null);

		List<Dataset> output = registerImage.value(images);
		final int length = output.size();
		
		final List<double[]> shift = new ArrayList<>();
		for (int i = 0; i < length; i+=2) {
			double[] s = (double[]) output.get(i).getBuffer();
			// We add the preShift to the shift data
			if (preShift != null) {
				s[0] += preShift[0];
				s[1] += preShift[1];
			}
			shift.add(s);
			if (shifted != null) {
				Dataset data = output.get(i + 1);
				data.setName("aligned_" + images[i/2].getName());
				shifted.add(data);
			}
		}
		return shift;
	}

	/**
	 * 
	 * @param files
	 * @param shifted images
	 * @param roi
	 * @param preShift
	 * @return shifts
	 */
	public static List<double[]> align(final String[] files, final List<Dataset> shifted, final RectangularROI roi, final double[] preShift, IMonitor monitor) {
		IDataset[] images = new IDataset[files.length];

		for (int i = 0; i < files.length; i++) {
			try {
				images[i] = LoaderFactory.getData(files[i], false, null).getDataset(0);
			} catch (Exception e) {
				logger.error("Cannot load file {}", files[i]);
				throw new IllegalArgumentException("Cannot load file " + files[i]);
			}
		}

		return align(images, shifted, roi, preShift, monitor);
	}

	/**
	 * <p>
	 * If mode = 0 then alignment routine is modeless and uses all the images at once.
	 * @param data
	 *            original list of dataset to be aligned
	 * @param shifts
	 *            output where to put resulting shifts
	 * @param roi
	 *            rectangular ROI used for the alignment
	 * @param mode
	 *            number of columns used: can be 0, 2 or 4
	 * @param monitor
	 * @return aligned list of dataset
	 */
	public static List<IDataset> alignWithROI(List<IDataset> data, List<List<double[]>> shifts, RectangularROI roi, int mode, IMonitor monitor) {
		if (mode == 0) {
			return alignWithROI(data, shifts, roi, monitor);
		}

		int nsets = data.size() / mode;

		RectangularROIList rois = new RectangularROIList();
		rois.add(roi);

		if (shifts == null)
			shifts = new ArrayList<>();

		List<IDataset> shiftedImages = new ArrayList<>();

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
			List<Dataset> shifted = new ArrayList<>(nsets);
			// align first images across columns:
			// Example: [0,1,2]-[3,4,5]-[6,7,8]-[9,10,11] for 12 images on 4 columns
			// with images 0,3,6,9 as the top images of each column.

			Dataset anchor = null;
			int index = 0;
			for (int p = 0; p < mode; p++) {
				RectangularROI croi = rois.get(p);
				for (int i = 0; i < nsets; i++) {
					tImages[i] = data.get(index++);
				}

				shifted.clear();
				// align a column
				List<double[]> nshifts = align(tImages, shifted, croi, null, monitor);

				// create anchor by summing
				Dataset canchor = DatasetFactory.zeros(DoubleDataset.class, tImages[0].getShape());
				for (Dataset d : shifted) {
					canchor.iadd(d);
				}
				if (anchor == null) {
					anchor = canchor;
				} else {
					// align with first column's anchor
					double[] topShift = align(new IDataset[] {anchor, canchor}, null, croi, null, monitor).get(1);
					logger.info("Top shift: {}", Arrays.toString(topShift));
					// align with new pre-shift
					if (monitor != null) {
						monitor.subTask("Re-shift images to first image");
					}
					for (int i = 0; i < nsets; i++) {
						double[] cshift = nshifts.get(i);
						cshift[0] += topShift[0];
						cshift[1] += topShift[1];
						logger.info("New shifts for {}: {}", i, Arrays.toString(cshift));
						Dataset cimage = RegisterImage2.shiftImage(DatasetUtils.convertToDataset(tImages[i]), cshift);
						cimage.setName(shifted.get(i).getName());
						shifted.set(i, cimage);
						if (monitor != null) {
							if (monitor.isCancelled()) {
								return shiftedImages;
							}
							monitor.worked(1);
						}
					}
				}
				shifts.add(nshifts);
				shiftedImages.addAll(shifted);

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
	 * @param data
	 *            original list of dataset to be aligned
	 * @param shifts
	 *            output where to put resulting shifts
	 * @param roi
	 *            rectangular ROI used for the alignment
	 * @param monitor
	 * @return aligned list of dataset
	 */
	private static List<IDataset> alignWithROI(List<IDataset> data, List<List<double[]>> shifts, RectangularROI roi, IMonitor monitor) {

		if (shifts == null)
			shifts = new ArrayList<>();

		int nsets = data.size();
		List<IDataset> shiftedImages = new ArrayList<>(nsets);
		IDataset[] tImages = data.toArray(new IDataset[0]);
		List<Dataset> shifted = new ArrayList<>(nsets);

		List<double[]> nshifts = align(tImages, shifted, roi, null, monitor);

		shifts.add(nshifts);
		shiftedImages.addAll(shifted);

		return shiftedImages;
	}

	/**
	 * Aligns images from a lazy dataset and returns a lazy dataset. This alignment process saves the aligned data in an
	 * hdf5 file saved on disk and this method can be used without running into a MemoryOverflowError.
	 * <p>
	 * If mode = 0 then alignment routine is modeless and uses all the images at once.
	 * @param data
	 *            original list of dataset to be aligned
	 * @param shifts
	 *            output where to put resulting shifts
	 * @param roi
	 *            rectangular ROI used for the alignment
	 * @param mode
	 *            number of columns used: can be 0, 2 or 4
	 * @param monitor
	 * @return aligned list of dataset
	 */
	public static ILazyDataset alignLazyWithROI(ILazyDataset data, List<List<double[]>> shifts, RectangularROI roi,
			IDataset darkImageData, IDataset brightImageData, int mode, IMonitor monitor) {

		if (mode == 0) {
			return alignLazyWithROI(data, shifts, roi, monitor);
		}

		int nsets = data.getShape()[0] / mode;

		RectangularROIList rois = new RectangularROIList();
		rois.add(roi);

		if (shifts == null)
			shifts = new ArrayList<>();


		// save on a temp file
		String file = FileUtils.getTempFilePath("aligned.h5");
		String path = "/entry/data/";
		String name = "aligned";
		File tmpFile = new File(file);
		if (tmpFile.exists()) {
			try {
				HDF5FileFactory.releaseFile(file, true);
			} catch (ScanFileHolderException e) {
				logger.error("Could not close file {}", file, e);
			}
			tmpFile.delete();
		}

		int[] shape = data.getShape();
		int[] chunking = new int[] {1, shape[1], shape[2]};
		ILazyWriteableDataset lazy = HDF5Utils.createLazyDataset(file, path, name, shape, null,
				chunking, Dataset.FLOAT32, null, false);

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
			List<Dataset> shifted = new ArrayList<>(nsets);
			// align first images across columns:
			// Example: [0,1,2]-[3,4,5]-[6,7,8]-[9,10,11] for 12 images on 4 columns
			// with images 0,3,6,9 as the top images of each column.
			Dataset anchor = null;
			int index = 0;
			int idx = 0;
			for (int p = 0; p < mode; p++) {
				RectangularROI croi = rois.get(p);
				if (monitor != null) {
					monitor.subTask("Loading images");
				}
				for (int i = 0; i < nsets; i++) {
					try {
						Dataset temp = DatasetUtils.convertToDataset(data.getSlice(monitor, new Slice(index++, index)).squeeze()).cast(DoubleDataset.class);
						
						// Dark field correction image processing
						if(darkImageData != null)
							temp.isubtract(darkImageData);
						
						temp.setByBoolean(0, Comparisons.lessThan(temp, 0));
						
						// Bright field correction image processing
						if(brightImageData != null)
							temp.idivide(brightImageData);
						
						tImages[i] = temp;
						
					} catch (DatasetException e) {
						logger.error("Could not get slice of image", e);
					}
				}

				shifted.clear();
				try {
					// align a column
					List<double[]> nshifts = align(tImages, shifted, croi, null, monitor);

					// create anchor by summing
					Dataset canchor = DatasetFactory.zeros(DoubleDataset.class, tImages[0].getShape());
					for (Dataset d : shifted) {
						canchor.iadd(d);
					}
					if (anchor == null) {
						anchor = canchor;
					} else {
						// align with first column's anchor
						double[] topShift = align(new IDataset[] {anchor, canchor}, null, croi, null, monitor).get(1);
						logger.info("Top shift: {}", Arrays.toString(topShift));
						// align with new pre-shift
						if (monitor != null) {
							monitor.subTask("Re-shift images to first image");
						}
						for (int i = 0; i < nsets; i++) {
							double[] cshift = nshifts.get(i);
							cshift[0] += topShift[0];
							cshift[1] += topShift[1];
							logger.info("New shifts for {}: {}", i, Arrays.toString(cshift));
							Dataset cimage = RegisterImage2.shiftImage(DatasetUtils.convertToDataset(tImages[i]), cshift);
							cimage.setName(shifted.get(i).getName());
							shifted.set(i, cimage);
							if (monitor != null) {
								if (monitor.isCancelled()) {
									return lazy;
								}
								monitor.worked(1);
							}
						}
					}
					shifts.add(nshifts);
					if (monitor != null) {
						monitor.subTask("Writing shifted images");
					}
					for (int i = 0; i < nsets; i++) {
						appendDataset(lazy, shifted.get(i), idx, monitor);
						idx++;
					}
				} catch (DatasetException e) {
					logger.warn("Problem with alignment: " + e);
					return null;
				}

			}
		}
		try {
			HDF5FileFactory.releaseFile(file, true);
		} catch (ScanFileHolderException e) {
			logger.error("Could not close file {}", file, e);
		}
		return lazy;
	}

	/**
	 * Aligns images from a lazy dataset and returns a lazy dataset. This alignment process saves the aligned data in an
	 * hdf5 file saved on disk and this method can be used without running into a MemoryOverflowError.
	 * <p>
	 * This is the modeless alignment routine that uses all the images at once.
	 * 
	 * @param data
	 *            original list of dataset to be aligned
	 * @param shifts
	 *            output where to put resulting shifts
	 * @param roi
	 *            rectangular ROI used for the alignment
	 * @param monitor
	 * @return aligned list of dataset
	 */
	private static ILazyDataset alignLazyWithROI(ILazyDataset data, List<List<double[]>> shifts, RectangularROI roi,
			IMonitor monitor) {

		int nsets = data.getShape()[0];

		if (shifts == null)
			shifts = new ArrayList<>();

		// save on a temp file
		String file = FileUtils.getTempFilePath("aligned.h5");
		String path = "/entry/data/";
		String name = "aligned";
		File tmpFile = new File(file);
		if (tmpFile.exists()) {
			try {
				HDF5FileFactory.releaseFile(file, true);
			} catch (ScanFileHolderException e) {
				logger.error("Could not close file {}", file, e);
			}
			tmpFile.delete();
		}

		int[] shape = data.getShape();
		int[] chunking = new int[] {1, shape[1], shape[2]};
		ILazyWriteableDataset lazy = HDF5Utils.createLazyDataset(file, path, name, shape, null,
				chunking, Dataset.FLOAT32, null, false);

		IDataset[] tImages = new IDataset[nsets];
		List<Dataset> shifted = new ArrayList<>(nsets);
		int index = 0;
		int idx = 0;
		if (monitor != null) {
			monitor.subTask("Loading images");
		}
		for (int i = 0; i < nsets; i++) {
			try {
				tImages[i] = data.getSlice(monitor, new Slice(index++, index)).squeeze();
			} catch (DatasetException e) {
				logger.error("Could not get slice of image", e);
			}
		}
		shifted.clear();
		try {
			// align a column
			List<double[]> nshifts = align(tImages, shifted, roi, null, monitor);

			if (monitor != null) {
				monitor.subTask("Writing shifted images");
			}
			shifts.add(nshifts);
			for (int i = 0; i < nsets; i++) {
				appendDataset(lazy, shifted.get(i), idx, monitor);
				idx++;
			}
		} catch (DatasetException e) {
			logger.warn("Problem with alignment: " + e);
			return null;
		}

		if (monitor != null) {
			if (monitor.isCancelled()) {
				return lazy;
			}
			monitor.worked(1);
		}
		try {
			HDF5FileFactory.releaseFile(file, true);
		} catch (ScanFileHolderException e) {
			logger.error("Could not close file {}", file, e);
		}
		return lazy;
	}

	private static void appendDataset(ILazyWriteableDataset lazy, IDataset data, int idx, IMonitor monitor) throws DatasetException {
		int[] shape = lazy.getShape();
		SliceND ndSlice = new SliceND(shape, new int[] {idx, 0, 0}, new int[] {idx + 1, shape[1], shape[2]}, null);
		lazy.setSlice(monitor, data, ndSlice);
	}
}
