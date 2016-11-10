/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

/**
 * Adds a second ROI to get a constant background.
 */
public class OverlappingBackgroundBox{
	
	private static Polynomial2D g2;
	private static Dataset output;
	private static IRegion background;
	
	public static Dataset OverlappingBgBox(IDataset input, ExampleModel model, IPlottingSystem<Composite> pS, DataModel dm){
		
		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];
		
		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);
		
		if (g2 == null)
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters())
			g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
		
//		@SuppressWarnings("unchecked")
//		 = customComposite.getPlotSystem();
		
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
	        Color red = display.getSystemColor(SWT.COLOR_RED);
			background.setRegionColor(red);
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
        
        
        int[] backLen = dm.getBackgroundLenPt()[0];
        int[] backPt = dm.getBackgroundLenPt()[1];
        
//		Dataset backgroundDataset = BoxSlicerRodScanUtilsForDialog.rOIBox(input, 
//				dm.getBackgroundLenPt()[0], 
//				dm.getBackgroundLenPt()[1]);
        
        BackgroundRegionArrays br = new BackgroundRegionArrays();
        
        
		for (int i = backPt[0]; i<backPt[0]+backLen[0]; i++){
			for(int j = backPt[1]; i<backPt[1]+backLen[1]; i++)
				if((i<pt[0]||i>=(pt[0]+len[0]))&&(j<pt[1]||j>=(pt[1]+len[1]))){
				}
				else{
					br.xArrayAdd(i);
					br.yArrayAdd(j);
					br.zArrayAdd(input.getDouble(i,j));
				}
		}
		
		
		Dataset xBackgroundDat = DatasetFactory.createFromObject(br.getXArray());
		Dataset yBackgroundDat = DatasetFactory.createFromObject(br.getYArray());
		Dataset zBackgroundDat = DatasetFactory.createFromObject(br.getZArray());
		
		Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
				AnalaysisMethodologies.toInt(model.getFitPower()), xBackgroundDat, yBackgroundDat);
		
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
			DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, zBackgroundDat);
			double[] params = test.getData();
			
			DoubleDataset in1Background = g2.getOutputValues0(params, len, model.getBoundaryBox(),
					AnalaysisMethodologies.toInt(model.getFitPower()));
		
		
		
			IndexIterator it = in1Background.getIterator();
			
			while (it.hasNext()) {
				double v = in1Background.getElementDoubleAbs(it.index);
				if (v < 0)
					in1Background.setObjectAbs(it.index, 0);
			}
		
			Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
		
			pBackgroundSubtracted.setName("pBackgroundSubtracted");
		
			IndexIterator it1 = pBackgroundSubtracted.getIterator();
		
			while (it1.hasNext()) {
				double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
				if (q < 0)
					pBackgroundSubtracted.setObjectAbs(it1.index, 0);
			}
			
			output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
			
			output.setName("Region of Interest, polynomial background removed");
			
			}
		});
		return output;
	}
	
///////////////////////////////////////////////////
	
	
}