package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class SecondConstantROIUsingIOperation 
	extends AbstractOperation<SecondConstantROIBackgroundSubtractionModel, OperationData> {

		private static Dataset output;
		private static IRegion background;
		private static IRegion ssvsBackground;
		private DoubleDataset in1Background;
		
		@Override
		public String getId() {
			return "uk.ac.diamond.scisoft.surfacescatter.SecondConstantROIUsingIOperation";
		}

		@Override
		public OperationRank getInputRank() {
			return OperationRank.TWO ;
		}

		@Override
		public OperationRank getOutputRank() {
			return OperationRank.TWO ;
		}
		
		
		public OperationData process (IDataset input, IMonitor monitor) 
				throws OperationException {
			
			int[] len = model.getLenPt()[0];
			int[] pt = model.getLenPt()[1];
			
			IPlottingSystem<Composite> pS = model.getPlottingSystem();
			IPlottingSystem<Composite> ssvsPS = model.getSPlottingSystem();
			
			
			
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
				IRectangularROI newROI = (IRectangularROI) model.getBackgroundROI();
				background.setROI(newROI);
				
				background.setRegionColor(magenta);
		        model.setBackgroundROI(newROI);
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
				IRectangularROI ssvsNewROI = (IRectangularROI) model.getBackgroundROI();
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
				IRectangularROI ssvsNewROI = (IRectangularROI) model.getBackgroundROI();
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
					
					model.setBackgroundROI(background.getROI().getBounds());
					
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
					model.setBackgroundROI(background.getROI().getBounds());
				}

			});
	        
	        
	        double[] location = new double[] { (double) model.getBackgroundLenPt()[1][1], 
	        								   (double) model.getBackgroundLenPt()[1][0], 
	        								   (double) (model.getBackgroundLenPt()[1][1] + model.getBackgroundLenPt()[0][1]), 
	        								   (double) (model.getBackgroundLenPt()[1][0]),
	        								   (double) model.getBackgroundLenPt()[1][1], 
	        								   (double) model.getBackgroundLenPt()[1][0] + model.getBackgroundLenPt()[0][0], 
	        								   (double) (model.getBackgroundLenPt()[1][1] + model.getBackgroundLenPt()[0][1]), 
	        								   (double) (model.getBackgroundLenPt()[1][0] + model.getBackgroundLenPt()[0][0]) };
	        	        
			Dataset backgroundDataset = BoxSlicerRodScanUtilsForDialog.rOIBox(input, 
					model.getBackgroundLenPt()[0], 
					model.getBackgroundLenPt()[1]);
	        
	        IndexIterator it0 = backgroundDataset.getIterator();
			
	        double bgSum = 0;
	        
	        while (it0.hasNext()) {
				bgSum += backgroundDataset.getElementDoubleAbs(it0.index);
	        }
	        
	        double bgAv = bgSum/(backgroundDataset.count()); 
	        
	        in1Background = DatasetFactory.zeros(in1.getShape());
	        
			Dataset pBackgroundSubtracted = Maths.subtract(in1, bgAv, null);
					
			IndexIterator it1 = pBackgroundSubtracted.getIterator();
			
			while (it1.hasNext()) {
				double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
				in1Background.setObjectAbs(it1.index, bgAv);
//				if (q < 0)
//					pBackgroundSubtracted.setObjectAbs(it1.index, 0.1);
			}
				
			output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
				
			output.setName("Region of Interest, constant background removed");

			return new OperationData(output, 
									 location, 
									 background.getROI(),
									 in1Background);
			
		}
	
}
