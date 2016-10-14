/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

/**
 * Cuts out the region of interest and fits it with a 2D polynomial background.
 */

public class TwoDTracking {

	private IImageTracker tracker = null;
	private Polynomial2D g2;
	private double[] location;
	private double[] initialLocation;
	private Dataset in1;
	private int[] len;
	private int[] pt;

	public IDataset TwoDTracking1(IDataset input, ExampleModel model, DataModel dm, int trackingMarker, int k) {

		len = model.getLenPt()[0];
		pt = model.getLenPt()[1];

		initialLocation = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]), (double) (pt[0]),
				(double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]), (double) (pt[0] + len[0]) };

		if (trackingMarker == 0) {
			if (model.getInput() == null) {
				len = model.getLenPt()[0];
				pt = model.getLenPt()[1];

//				in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();

				initialLocation = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]),
						(double) (pt[0]), (double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]),
						(double) (pt[0] + len[0]) };
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {

				}
				model.setTrackerCoordinates(
						new double[] { initialLocation[1], initialLocation[0], initialLocation[5], initialLocation[0],
								initialLocation[1], initialLocation[2], initialLocation[5], initialLocation[2] });
				model.setInput(input);
				location = initialLocation;
			}

			else {

				try {

					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					location = tracker.track(input);
					if (location != null) {
						model.setTrackerCoordinates(location);
					}

					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					System.out.println(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		else if (trackingMarker == 1) {
			if (model.getInput() == null) {
				len = model.getLenPt()[0];
				pt = model.getLenPt()[1];

				dm.setInitialDataset(input);
				dm.setInitialLenPt(new int[][] {len, pt });

//				in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();

				initialLocation = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]),
						(double) (pt[0]), (double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]),
						(double) (pt[0] + len[0]) };
				try {
					tracker.initialize(input, initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
				} catch (Exception e) {

				}
				model.setTrackerCoordinates(
						new double[] { initialLocation[1], initialLocation[0], initialLocation[5], initialLocation[0],
								initialLocation[1], initialLocation[2], initialLocation[5], initialLocation[2] });
				model.setInput(input);
				location = initialLocation;
			}

			else {

				try {

					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					location = tracker.track(input);
					if (location != null) {
						model.setTrackerCoordinates(location);
					}

					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					System.out.println(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		else if (trackingMarker == 2) {
			if (model.getInput() == null) {
				len = dm.getInitialLenPt()[0];
				pt = dm.getInitialLenPt()[1];
					
				tracker =null;
				tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();

				initialLocation = new double[] { (double) pt[1], (double) pt[0], (double) (pt[1] + len[1]),
						(double) (pt[0]), (double) pt[1], (double) pt[0] + len[0], (double) (pt[1] + len[1]),
						(double) (pt[0] + len[0]) };

				try {
					
					tracker.initialize(dm.getInitialDataset(), initialLocation, TrackingMethodology.toTT(model.getTrackerType()));
					
					//tracker.initialize(dm.getInitialDataset(), initialLocation, TrackerType.TLD);
					location = tracker.track(input);
					
					if (location != null) {
						model.setTrackerCoordinates(location);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}

//				model.setTrackerCoordinates(new double[] { location[1], location[0], location[5], location[0],
//						location[1], initialLocation[2], location[5], location[2] });
				model.setInput(input);

			}

			else {

				try {

					tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
					tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackingMethodology.toTT(model.getTrackerType()));
					location = tracker.track(input);
					if (location != null) {
						model.setTrackerCoordinates(location);
					}

					int[] len1 = model.getLenPt()[0];

					int[] newPt = new int[] { (int) location[0], (int) location[1] };
					int[][] newLenPt = new int[2][];
					newLenPt[0] = len1;
					newLenPt[1] = newPt;
					model.setLenPt(newLenPt);
					model.setInput(input);
//					System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
				} catch (Exception e) {
					System.out.println(
							"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");
				}

			}
		}

		if (location == null) {
			location = model.getTrackerCoordinates();
		}

		len = model.getLenPt()[0];
		pt = model.getLenPt()[1];

//		in1 = BoxSlicerRodScanUtilsForDialog.rOIHalfBox(input, len, pt);
		in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);

		//in1 = (Dataset) PlotSystem2DataSetter.PlotSystem2DataSetter1(model);
		
		if (g2 == null)
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters())
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));

//		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomHalfBoxes(input, len, pt,
//				model.getBoundaryBox());
		
		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomBoxes(input, len, pt,
				model.getBoundaryBox());

		Dataset offset = DatasetFactory.ones(fittingBackground[2].getShape(), Dataset.FLOAT64);

		System.out.println("Tracker position:  " + location[1] + " , " + location[0]);

		Dataset intermediateFitTest = Maths.add(offset, fittingBackground[2]);
		Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
				AnalaysisMethodologies.toInt(model.getFitPower()), fittingBackground[0], fittingBackground[1]);

		DoubleDataset test = (DoubleDataset) LinearAlgebra.solveSVD(matrix, intermediateFitTest);
		double[] params = test.getData();

		DoubleDataset in1Background = g2.getOutputValues0(params, len, model.getBoundaryBox(),
				AnalaysisMethodologies.toInt(model.getFitPower()));

		IndexIterator it = in1Background.getIterator();

		while (it.hasNext()) {
			double v = in1Background.getElementDoubleAbs(it.index);
			if (v < 0)
				in1Background.setObjectAbs(it.index, 0);
		}

		dm.addBackgroundDatArray(in1Background);

		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);

		pBackgroundSubtracted.setName("pBackgroundSubtracted");

		IndexIterator it1 = pBackgroundSubtracted.getIterator();

		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (q < 0)
				pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}

		Dataset output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);

		// dm.addOutputDatArray(output);

		output.setName("Region of Interest, polynomial background removed");

		return output;
	}

	public void resetTracker() {
		tracker = null;
	}

}