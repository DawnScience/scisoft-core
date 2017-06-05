///*-
// * Copyright 2016 Diamond Light Source Ltd.
// *
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// */
//
//package org.dawnsci.surfacescatter;
//
//import java.util.Arrays;
//
//import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
//import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
//import org.eclipse.january.dataset.Dataset;
//import org.eclipse.january.dataset.DatasetFactory;
//import org.eclipse.january.dataset.IDataset;
//import org.eclipse.january.dataset.IndexIterator;
//import org.eclipse.january.dataset.Maths;
//
//public class Polynomial1DXBgSubtraction {
//	
//	private int[] len;
//	private int[] pt;
//	private RectangularROI box;
//
//	
//	
//	public IDataset Polynomial1DXBgSubtraction1 (SuperModel sm, 
//												 IDataset input, 
//												 ExampleModel model, 
//												 DataModel dm, 
//												 int k,
//												 int selection) {
//
//		
//		len = sm.getInitialLenPt()[0];
//		pt = sm.getInitialLenPt()[1];
//
//		
//		RectangularROI box = sm.getBackgroundBox();
//		
//		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
//		
//		IDataset[] background = new IDataset[2];
//				
//		background[0] = BoxSlicerRodScanUtilsForDialog.iBelowOrRightBox(input, 
//																		len, 
//																		pt, 
//																		model.getBoundaryBox(), 
//																		Methodology.X);
//		
//		background[1] = BoxSlicerRodScanUtilsForDialog.iAboveOrLeftBox(input, 
//																	   len, 
//																	   pt, 
//																	   model.getBoundaryBox(), 
//																	   Methodology.X);
//		
//		
//		Dataset in1Background = DatasetFactory.zeros(in1.getShape(), Dataset.FLOAT64);
//		
//		in1Background = BackgroundSetting.rOIBackground1(background, 
//														 in1Background,
//														 len,
//														 pt, 
//														 model.getBoundaryBox(), 
//														 AnalaysisMethodologies.toInt(model.getFitPower()), 
//														 Methodology.X);
//		
//		
//		if(Arrays.equals(in1Background.getShape() , new int[] {2,2}) ){
//			return (IDataset) in1Background;
//		}
//		
//		IndexIterator it = in1Background.getIterator();
//		
//		while (it.hasNext()) {
//			double v = in1Background.getElementDoubleAbs(it.index);
//			if (v < 0){
//				in1Background.setObjectAbs(it.index, 0.1);
//			}
//		}
//
//		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
//
//		pBackgroundSubtracted.setName("PBackgroundSubtracted");
//
//		
//		IndexIterator it1 = pBackgroundSubtracted.getIterator();
//		
//		while (it1.hasNext()) {
//			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
//			if (q < 0){
//				pBackgroundSubtracted.setObjectAbs(it1.index, 0);
//			}
//		}
//		
//		
//		double[] location = new double[] { (double) sm.getInitialLenPt()[1][1], 
//				   							(double) sm.getInitialLenPt()[1][0], 
//				   							(double) (sm.getInitialLenPt()[1][1] + sm.getInitialLenPt()[0][1]), 
//				   							(double) (sm.getInitialLenPt()[1][0]),
//				   							(double) sm.getInitialLenPt()[1][1], 
//				   							(double) sm.getInitialLenPt()[1][0] + sm.getInitialLenPt()[0][0], 
//				   							(double) (sm.getInitialLenPt()[1][1] + sm.getInitialLenPt()[0][1]), 
//				   							(double) (sm.getInitialLenPt()[1][0] + sm.getInitialLenPt()[0][0]) };
//
//		sm.addLocationList(selection, location);
//	
//		dm.addBackgroundDatArray(in1Background);
//		dm.addLocationList(model.getDatImages().getShape()[0], k, location);
//		
//		return pBackgroundSubtracted;
//
//	}
//}
