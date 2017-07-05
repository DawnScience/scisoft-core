//package org.dawnsci.surfacescatter;
//
//import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
//import org.eclipse.january.DatasetException;
//import org.eclipse.january.dataset.DatasetFactory;
//import org.eclipse.january.dataset.IDataset;
//import org.eclipse.january.dataset.SliceND;
//
//public class ModifiedAgnosticTrackerWithFrames3 extends AbstractTracker{
//
//	
//
//	public ModifiedAgnosticTrackerWithFrames3(DirectoryModel drm, 
//											  int trackingMarker, 
//											  int k,
//											  int selection) {
//
//		
//		super.init(drm, 
//				   trackingMarker, 
//				   k, 
//				   selection);
//		
//		FrameModel fm = super.getFrame();
//		
//		
//		
//		if(fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.INTERPOLATION 
//				&& fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION
//				&& fm.getTrackingMethodology() != TrackingMethodology.TrackerType1.USE_SET_POSITIONS){
//			
//			
//			
//			if (trackingMarker == 3){
//				drm.getInputForEachDat()[fm.getDatNo()] = null;
//				super.setTrackingMarker(0);
//				super.setTheTrackingMarkerNot3(false);
//			}
//			
//			if (trackingMarker == 0) {
//				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
//					initialiseTracker();
//				}
//			
//				else {
//			
//					followUp();
//				}
//			}
//			
//			else if (trackingMarker == 1) {
//				if (drm.getInputForEachDat()[fm.getDatNo()] == null) {
//					initialiseTracker();
//				}
//			
//				else {
//					followUp();
//				}
//			}
//			
//			else if (trackingMarker == 2) {
//				restartInOppositeDirection();
//			}
//			
//		
//			
//			if (isTheTrackingMarkerNot3){
//			
//				int[] localPt = new int[] {(int) (location[0]), (int) (location[1])};
////				int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
//				int[] localLen = drm.getInitialLenPt()[0];
//				int[] localLocation = new int[] { localPt[0], localPt[1], (localPt[0] + localLen[0]),
//						(localPt[1]), localPt[0], localPt[1] + localLen[1], (localPt[0] + localLen[0]),
//						(localPt[1] + localLen[1]) };
//				
//				 
//				drm.addLocationList(fm.getDatNo(),
//						drm.getNoOfImagesInDatFile(fm.getDatNo()), 
//						k, 
//						localLocation);
//				
//				int a = fm.getDatNo();
//				int b = drm.getNoOfImagesInDatFile(a);
//				
//				drm.addLocationList(a,
//									b, 
//									k, 
//									localLocation);
//				
//				fm.setRoiLocation(localLocation);
//				
//				int[][] localLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(localLocation);
//				
//				drm.getLenPtForEachDat()[fm.getDatNo()] = localLenPt;
//				
//				debug("location[0]:  " + location[0] + "  location[1]:  " + location[1] + "  selection" + selection);
//			}
//		}
//		
//		else if (fm.getTrackingMethodology() == TrackingMethodology.TrackerType1.INTERPOLATION 
//				|| fm.getTrackingMethodology() == TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION){
//			
//			///////start the interpolation tracker
//			
//			try{
//				if(isTheTrackingMarkerNot3){
//					len = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[0][1])};
//					pt = new int[] {(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][0]),(int) Math.round(drm.getInterpolatedLenPts().get(selection)[1][1])};
//					
//					double[] localLocation = new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
//							(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
//							(double) (pt[1] + len[1]) };
//					
//					drm.addLocationList(fm.getDatNo(),
//							drm.getNoOfImagesInDatFile(fm.getDatNo()), 
//							k, 
//							localLocation);
//					
//					fm.setRoiLocation(localLocation);
//					
//					drm.getLenPtForEachDat()[fm.getDatNo()] = new int[][] {len, pt};
//				}
//				else{
//					int[] localPt = drm.getInitialLenPt()[1];
//	//				int[] localLen = new int[] {(int) ((int)location[2] - (int)location[0]), (int) ((int)location[5] -(int)location[1])};
//					int[] localLen = drm.getInitialLenPt()[0];
//					
//					
//					double[] localLocation = LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] {localLen, localPt});
//					
//					drm.addLocationList(fm.getDatNo(),
//							drm.getNoOfImagesInDatFile(fm.getDatNo()), 
//							k, 
//							localLocation);
//					drm.addLocationList(fm.getDatNo(),
//										drm.getNoOfImagesInDatFile(fm.getDatNo()), 
//										k, 
//										localLocation);
//					
//					drm.getLenPtForEachDat()[fm.getDatNo()] = new int[][]{len, pt};
//					
////					fm.setRoiLocation(localLocation);
//					
//				}
//			}
//			catch(Exception r){
//				
//			}
//		}	
//	}
//		
//
//	
//	private void debug (String output) {
//		if (DEBUG == 1) {
//			System.out.println(output);
//		}
//	}
//}
//			