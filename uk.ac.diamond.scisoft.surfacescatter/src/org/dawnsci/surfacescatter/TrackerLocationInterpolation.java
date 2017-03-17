package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class TrackerLocationInterpolation {
	
	public static int DEBUG = 0 ;
	
	public static double[] trackerInterpolationInterpolator0(ArrayList<double[]> trackerLocations, 
														    Dataset sortedX,
														    int[] len,
														    int k){
		
//		sm.getSortedX()
			ArrayList<Double> lList = new ArrayList<Double>();  
			ArrayList<Double> xList = new ArrayList<Double>();
			ArrayList<Double> yList = new ArrayList<Double>();
			
			for(int h = 0 ; h<trackerLocations.size(); h++){
				
				double[] tL = trackerLocations.get(h);
				
				if (Arrays.equals(tL, new double[] {0,0,0,0,0,0,0,0}) != true){
					lList.add(sortedX.getDouble(h));
					yList.add(tL[0]);
					xList.add(tL[1]);
				}
				
			}
		
			
			Dataset yValues = DatasetFactory.zeros(lList.size());
			Dataset xValues = DatasetFactory.zeros(lList.size());
			Dataset lValues = DatasetFactory.zeros(lList.size());
			
			for(int op = 0; op<xList.size(); op++){
				
				double x = xList.get(op);
				double y = yList.get(op);
				double l = lList.get(op);
				
				xValues.set(x, op);
				yValues.set(y, op);
				lValues.set(l, op);

			}
			
			
			
			
			
			
			
			double[] seedLocation = PolynomialOverlap.extrapolatedLocation(sortedX.getDouble(k),
																		   lValues, 
																		   xValues, 
																		   yValues, 
																		   len,
																		   1);
			
			debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{  Tracker Location Invoked}}}}}}}}}!!!!!!!");
			debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{  desiredl: "+ Double.toString(sortedX.getDouble(k)) + "}}}}}}}}}!!!!!!!");
			debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{  Tracker location[0] : " + seedLocation[0] +" + " + "Trackerlocation[1] :" + seedLocation[1]);
		
		return seedLocation;
	}

	
	public static double[] trackerInterpolationInterpolator1(SuperModel sm, 
															 int k){
	
//		if(dm.getLocationList() == null){
//			
//			int seedIndex = 
//					ClosestNoFinder.closestNoWithLocation(sm.getSortedX().getDouble(k),
//														  sm.getSortedX(), 
//														  sm.getLocationList());
//	
//			int nearestCompletedDatFileNo = sm.getFilepathsSortedArray()[seedIndex];
//			
//			
//			ArrayList<double[]> seedList = dms.get(nearestCompletedDatFileNo).getLocationList();
//			ArrayList<Double> lList = dms.get(nearestCompletedDatFileNo).getxList();
//			
//			Dataset yValues = DatasetFactory.zeros(seedList.size());
//			Dataset xValues = DatasetFactory.zeros(seedList.size());
//			Dataset lValues = DatasetFactory.zeros(seedList.size());
//			
//			for(int op = 0; op<seedList.size(); op++){
//				
//				double x = seedList.get(op)[1];
//				double y = seedList.get(op)[0];
//				double l = lList.get(op);
//				
//				xValues.set(x, op);
//				yValues.set(y, op);
//				lValues.set(l, op);
//	
//			}
//			
//			double[] seedLocation = PolynomialOverlap.extrapolatedLocation(sm.getSortedX().getDouble(k),
//																		   lValues, 
//																		   xValues, 
//																		   yValues, 
//																		   sm.getInitialLenPt()[0],
//																		   1);
//			dm.setSeedLocation(seedLocation);
//			
//			debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{ seedlocation[0] : " + seedLocation[0] +" + " + "seedlocation[1] :" + seedLocation[1]);
//		
//		}	
	
		return null;
	}
	
	
	public static double[] trackerInterpolationInterpolator3(ArrayList<double[]> trackerLocations, 
															 Dataset sortedX,
															 int[] len,
															 int k){

	//sm.getSortedX()
		ArrayList<Double> lList = new ArrayList<Double>();  
		ArrayList<Double> xList = new ArrayList<Double>();
		ArrayList<Double> yList = new ArrayList<Double>();
		
		for(int h = 0 ; h<trackerLocations.size(); h++){
		
			double[] tL = trackerLocations.get(h);
			
			if (Arrays.equals(tL, new double[] {0,0,0,0,0,0,0,0}) != true){
				lList.add(sortedX.getDouble(h));
				yList.add(tL[0]);
				xList.add(tL[1]);
			}
		
		}

		Dataset yValues = DatasetFactory.zeros(10);
		Dataset xValues = DatasetFactory.zeros(10);
		Dataset lValues = DatasetFactory.zeros(10);
		
		for(int j= 0; j <10 ; j++){
			
			int op = ClosestNoFinder.closestNoPos(sortedX.getDouble(k), lList);
			
			double x = xList.get(op);
			double y = yList.get(op);
			double l = lList.get(op);
			
			xValues.set(x, j);
			yValues.set(y, j);
			lValues.set(l, j);
			
			xList.remove(op);
			yList.remove(op);
			lList.remove(op);
			
		}
				
//		for(int op = 0; op<xList.size(); op++){
//		
//			double x = xList.get(op);
//			double y = yList.get(op);
//			double l = lList.get(op);
//			
//			xValues.set(x, op);
//			yValues.set(y, op);
//			lValues.set(l, op);
//		
//		}
				
		double[] seedLocation = PolynomialOverlap.extrapolatedLocation(sortedX.getDouble(k),
																	   lValues, 
																	   xValues, 
																	   yValues, 
																	   len,
																	   1);
		
		debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{  Tracker Location Invoked}}}}}}}}}!!!!!!!");
		debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{  desiredl: "+ Double.toString(sortedX.getDouble(k)) + "}}}}}}}}}!!!!!!!");
		debug("!!!!!!!!!!!!!!!     }}}}}{{{{{{{{  Tracker location[0] : " + seedLocation[0] +" + " + "Trackerlocation[1] :" + seedLocation[1]);
		
		return seedLocation;
	}
	
	
	
	private static void debug (String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
}
