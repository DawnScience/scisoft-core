/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;



import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.january.dataset.IDataset;


import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class DataTrackingTest {

	private IImageTracker tracker;

	private String dataname = "image-01";
	private IDataset data;
	private IDataset data2;


	
	
	public void before() throws Exception {
		if (tracker == null)
			tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
		data = LoaderFactory.getData("/dls/i07/data/2015/si10262-1/pilatus3/p3Image1024107.tif", null).getDataset(dataname);
		data2 = LoaderFactory.getData("/dls/i07/data/2015/si10262-1/pilatus3/p3Image1024108.tif", null).getDataset(dataname);
	}
	public void testImageTrackerBasic() throws Exception {
		double[] originalLocation = new double[] { 350, 70, 400, 70, 350, 120, 400, 120 };
		// initialize tracker
		tracker.initialize(data, originalLocation, TrackerType.TLD);
		// run tracker against second image
		double[] location = tracker.track(data2);
		System.out.println("location: " + location[0] + location[1] + location[2] + location[3] + location[4] + location[5] + location[6] + location[7]);
		double[] locationExpected = new double[] { 435.9968422696961, 72.9932498803572, 481.00780718826627,
				72.9932498803572, 481.00780718826627, 104.00080349092775, 435.9968422696961, 104.00080349092775 };

	}
}
