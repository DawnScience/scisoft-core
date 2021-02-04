/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.utils;

import java.io.File;

import org.eclipse.dawnsci.analysis.dataset.roi.ROIUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.ILazyDataset;

/**
 * Known detectors
 */
public enum KnownDetector {
	ANDOR(2048, 2048),
	XCAM(1610, 3304),
	XCAM_OLD(true, 1610, 3304);

	private int[] shape;
	private String subname;
	private boolean swapLR;

	KnownDetector(int... shape) {
		this.shape = shape;
		this.subname = name().toLowerCase();
	}

	KnownDetector(boolean swapLeftRight, int... shape) {
		this.swapLR = swapLeftRight;
		this.shape = shape;
		this.subname = name().toLowerCase();
	}

	public int[] getShape() {
		return shape;
	}

	public boolean isSwapLeftRight() {
		return swapLR;
	}

	private static final String I21_PREFIX = "i21-";
	private static final int I21_SWAPPED_XCAM_SCAN = 157052;

	/**
	 * Get detector
	 * @param file
	 * @param name
	 * @param input
	 * @return detector or null if unable to identify (name or shape mismatch)
	 */
	public static KnownDetector getDetector(String file, String name, ILazyDataset input) {
		int[] shape = input instanceof Dataset ? ((Dataset) input).getShapeRef() : input.getShape();

		KnownDetector known = null;
		int e = shape.length - 2;
		if (e >= 0) {
			for (KnownDetector d : KnownDetector.values()) {
				if (name.contains(d.subname) && shape[e] == d.shape[0] && shape[e + 1] == d.shape[1]) {
					known = d;
					break;
				}
			}
		}

		if (known == KnownDetector.XCAM) {
			// 
			File f = new File(file);
			String n = f.getName();
			if (n.startsWith(I21_PREFIX)) {
				Integer scan = ProcessingUtils.getScanNumber(n);
				if (scan != null && scan < I21_SWAPPED_XCAM_SCAN) {
					known = KnownDetector.XCAM_OLD;
				}
			}
		}
		return known;
	}

	/**
	 * Get default ROI
	 * @param detector can be null if oshape is defined
	 * @param oshape original shape (used when detector is null)
	 * @param max maximum number of regions
	 * @param r region number
	 * @param margin amount to reduce on all sides
	 * @return region of interest
	 */
	public static RectangularROI getDefaultROI(KnownDetector detector, int[] oshape, int max, int r, int margin) {
		int[] shape = detector == null ? oshape : detector.getShape();
		if (shape == null) {
			throw new IllegalArgumentException("Detector or oshape must be defined");
		}

		int hX = shape[1]/2;
		int lY = shape[0];
		RectangularROI roi = null;
		if (max == 1) {
			if (detector == KnownDetector.XCAM_OLD) { // use right CCD
				roi = new RectangularROI(hX, 0, hX, lY, 0);
			} else if (detector == KnownDetector.XCAM) { // use left CCD
				roi = new RectangularROI(0, 0, hX, lY, 0);
			} else {
				roi = new RectangularROI(0, 0, shape[1], lY, 0);
			}
		} else {
			roi = new RectangularROI(0, 0, hX, lY, 0);
			if (r == 1 && detector != KnownDetector.XCAM_OLD) { // shift to right half
				roi.setPoint(hX, 0);
			}
		}
		ROIUtils.trimRectangularROI(roi, margin);
		return roi;
	}
}
