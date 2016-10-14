/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

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
	
	public static Dataset secondROIConstantBg(IDataset input, ExampleModel model, PlotSystemComposite customComposite, DataModel dm){
		
		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];
		
		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);
		
		@SuppressWarnings("unchecked")
		IPlottingSystem<Composite> pS = customComposite.getPlotSystem();
		
		if (pS.getRegion("Background Region")==null){
		
			try {
				background =pS.createRegion("Background Region", RegionType.BOX);
			} catch (Exception e) {
				e.printStackTrace();
			}
			pS.addRegion(background);
			RectangularROI newROI = new RectangularROI(10,10,50,50,0);
			background.setROI(newROI);
			
			Display display = Display.getCurrent();
	        Color blue = display.getSystemColor(SWT.COLOR_BLUE);
			background.setRegionColor(blue);
	        dm.setBackgroundROI(newROI);
		}
		else{
			background = pS.getRegion("Background Region");
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
			}

		});
        
		Dataset backgroundDataset = BoxSlicerRodScanUtilsForDialog.rOIBox(input, 
				dm.getBackgroundLenPt()[0], 
				dm.getBackgroundLenPt()[1]);
        
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
				pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}
			
		output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
			
		output.setName("Region of Interest, constant background removed");

		return output;
	}
	

}