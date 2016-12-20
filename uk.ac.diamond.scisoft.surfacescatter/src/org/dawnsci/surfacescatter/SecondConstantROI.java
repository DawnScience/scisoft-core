/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Adds a second ROI to get a constant background.
 */
public class SecondConstantROI{
	
	//private  Polynomial2D g2;
	private static Dataset output;
	private static IRegion background;
	private static IRegion ssvsBackground;
	
	public static Dataset secondROIConstantBg(IDataset input, 
											  ExampleModel model, 
											  SuperModel sm, 
											  IPlottingSystem<Composite> pS,
											  IPlottingSystem<Composite> ssvsPS,
											  DataModel dm,
											  int selection){
		
		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];
		
		Display display = Display.getCurrent();
        Color magenta = display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
		
		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);
		
		if (pS.getRegion("Background Region")==null){
			
			try {
				background =pS.createRegion("Background Region", RegionType.BOX);
			} catch (Exception e) {
				e.printStackTrace();
			}
			pS.addRegion(background);
			IRectangularROI newROI = (IRectangularROI) sm.getBackgroundROI();
			background.setROI(newROI);
			
			background.setRegionColor(magenta);
	        sm.setBackgroundROI(newROI);
		}
		else{
			background = pS.getRegion("Background Region");
		}
		
		
		if (ssvsPS.getRegion("ssvs Background Region")==null){
			
			try {
				ssvsBackground =ssvsPS.createRegion("ssvs Background Region", RegionType.BOX);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ssvsPS.addRegion(ssvsBackground);
			IRectangularROI ssvsNewROI = (IRectangularROI) sm.getBackgroundROI();
			ssvsBackground.setROI(ssvsNewROI);
			
			ssvsBackground.setRegionColor(magenta);
		}
		else{
			ssvsPS.removeRegion(ssvsPS.getRegion("ssvs Background Region"));
			try {
				ssvsBackground =ssvsPS.createRegion("ssvs Background Region", RegionType.BOX);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ssvsPS.addRegion(ssvsBackground);
			IRectangularROI ssvsNewROI = (IRectangularROI) sm.getBackgroundROI();
			ssvsBackground.setROI(ssvsNewROI);
			
			ssvsBackground.setRegionColor(magenta);
		}

        background.addROIListener(new IROIListener() {

			@Override
			public void roiDragged(ROIEvent evt) {
				roiStandard(evt);
			}

			@Override
			public void roiChanged(ROIEvent evt) {
				roiStandard(evt);
			}

			@Override
			public void roiSelected(ROIEvent evt) {
				roiStandard(evt);
			}
			
			public void roiStandard(ROIEvent evt) {
				
				dm.setBackgroundROI(background.getROI());
				
				sm.setBackgroundROI(background.getROI());
				
				IRectangularROI magentaRectangle = background.getROI().getBounds();
				int[] Len = magentaRectangle.getIntLengths();
				int[] Pt = magentaRectangle.getIntPoint();
				int[][] LenPt = {Len,Pt};
				
				RectangularROI newROI = new RectangularROI(LenPt[1][0],
														   LenPt[1][1],
														   LenPt[0][0],
														   LenPt[0][1],0);
				ssvsBackground.setROI(newROI);
				
			}

		});
        
        ssvsBackground.addROIListener(new IROIListener() {

			@Override
			public void roiDragged(ROIEvent evt) {
				roiStandard(evt);
			}

			@Override
			public void roiChanged(ROIEvent evt) {
				roiStandard(evt);
			}

			@Override
			public void roiSelected(ROIEvent evt) {
				roiStandard(evt);
			}
			
			public void roiStandard(ROIEvent evt) {
				sm.setBackgroundROI(background.getROI());
			}

		});
        
        
        double[] location = new double[] { (double) sm.getBackgroundLenPt()[1][1], 
        								   (double) sm.getBackgroundLenPt()[1][0], 
        								   (double) (sm.getBackgroundLenPt()[1][1] + sm.getBackgroundLenPt()[0][1]), 
        								   (double) (sm.getBackgroundLenPt()[1][0]),
        								   (double) sm.getBackgroundLenPt()[1][1], 
        								   (double) sm.getBackgroundLenPt()[1][0] + sm.getBackgroundLenPt()[0][0], 
        								   (double) (sm.getBackgroundLenPt()[1][1] + sm.getBackgroundLenPt()[0][1]), 
        								   (double) (sm.getBackgroundLenPt()[1][0] + sm.getBackgroundLenPt()[0][0]) };
        
        sm.addLocationList(selection, location);
        
		Dataset backgroundDataset = BoxSlicerRodScanUtilsForDialog.rOIBox(input, 
				sm.getBackgroundLenPt()[0], 
				sm.getBackgroundLenPt()[1]);
        
        IndexIterator it0 = backgroundDataset.getIterator();
		
        double bgSum = 0;
        
        while (it0.hasNext()) {
			bgSum += backgroundDataset.getElementDoubleAbs(it0.index);
        }
        
        double bgAv = bgSum/(backgroundDataset.count()); 
        
		Dataset pBackgroundSubtracted = Maths.subtract(in1, bgAv, null);
		
		pBackgroundSubtracted.setName("pBackgroundSubtracted");
		
		IndexIterator it1 = pBackgroundSubtracted.getIterator();
		
		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (q < 0)
				pBackgroundSubtracted.setObjectAbs(it1.index, 0.1);
		}
			
		output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
			
		output.setName("Region of Interest, constant background removed");

		return output;
	}
	

}