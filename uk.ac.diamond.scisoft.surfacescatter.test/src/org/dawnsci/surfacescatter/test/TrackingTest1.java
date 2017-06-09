///**
// * 
// */
//package org.dawnsci.surfacescatter.test;
//
//import static org.junit.Assert.*;
//import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
//import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
//import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
//import org.eclipse.january.dataset.DatasetFactory;
//import org.eclipse.january.dataset.IDataset;
//import org.junit.Test;
//
///**
// * @author awq68923
// *
// */
//public class TrackingTest1 {
//
//	private IImageTracker tracker;
//
////	private String dataname = "image-01";
//	private IDataset data;
//	private IDataset data2;
//
//	
//	
//	public void dataGenerator(){
//		
//		data = DatasetFactory.ones(new int[] {1000, 1000});
//		data2 = DatasetFactory.ones(new int[] {1000, 1000});
//		
//		for(int i = 100; i<110; i++){
//			for(int j = 100; j<110; j++){
//				data.set(100, i,j);
//			}
//		}
//		
//		for(int i1 = 120; i1<130; i1++){
//			for(int j1 = 120; j1<130; j1++){
//				data2.set(100, i1,j1);
//			}
//		}
//		
//	}
//	
//
////	public void before() throws Exception {
////		if (tracker == null)
////			tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
//////		data = LoaderFactory.getData("/dls/i07/data/2015/si10262-1/pilatus3/p3Image1024107.tif", null).getDataset(dataname);
//////		data2 = LoaderFactory.getData("/dls/i07/data/2015/si10262-1/pilatus3/p3Image1024108.tif", null).getDataset(dataname);
////	}
//	
//	
//	@Test
//	public void testImageTrackingTLD() throws Exception {
//		
//		dataGenerator();
//		
//		int[] localPt = new int[] {90,90};
//		int[] localLen = new int[] {120,120};
//		
//		
//		int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
//				(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
//				(localPt[1] + localLen[1]) };
//		
//		double[] startLocation = new double[8];
//		
//		for(int i =0; i<8; i++){
//			startLocation[i] = (double) localLocation[i];
//		}
//	
//		// initialize tracker
//		
//		tracker = BoofCVImageTrackerServiceCreator.createImageTrackerService();
//		tracker.initialize(data, startLocation , TrackerType.TLD);
//		// run tracker against second image
//		double[] location = tracker.track(data2);
////		System.out.println("location: " + location[0] +" , "
////										+ location[1] + " , "
////										+location[2] + " , "
////										+location[3] + " , "
////										+location[4] + " , "+
////										location[5] + " , "+
////										location[6] + " , "+
////										location[7]);
////		
//		
//		Double[] trackedLocation = new Double[8];
//		
//		for(int i =0; i<8; i++){
//			trackedLocation[i] = (double) ((int) Math.round(location[i]));
//		}
//		
//		
//		
//		Double[] locationExpected = new Double[] { (double) 110, (double) 110, (double) 230,
//				(double) 110,(double) 230, (double) 230, (double)110,(double) 230};
//		
//		
//		assertArrayEquals(trackedLocation,locationExpected);
//
//	}
//	
//	
////	@Test
////	public void test() {
////		fail("Not yet implemented");
////	}
//
//}
