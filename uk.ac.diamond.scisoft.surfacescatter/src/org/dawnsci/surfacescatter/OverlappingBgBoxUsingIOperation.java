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
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

public class OverlappingBgBoxUsingIOperation 
	extends AbstractOperation<SecondConstantROIBackgroundSubtractionModel, OperationData> {

	private static Polynomial2D g2;
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
			
			if (g2 == null)
				g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
			if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters())
				g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
			
			
			Display display = Display.getCurrent();
	        Color red = display.getSystemColor(SWT.COLOR_RED);
			
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
				
				background.setRegionColor(red);
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
				
				ssvsBackground.setRegionColor(red);
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
				
				ssvsBackground.setRegionColor(red);
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
					
					IRectangularROI redRectangle = background.getROI().getBounds();
					int[] Len = redRectangle.getIntLengths();
					int[] Pt = redRectangle.getIntPoint();
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
	        

	        int[] backLen = model.getBackgroundLenPt()[0];
	        int[] backPt = model.getBackgroundLenPt()[1];
	        
	        BackgroundRegionArrays br = new BackgroundRegionArrays();
	        
	        
			for (int i = backPt[0]; i<backPt[0]+backLen[0]; i++){
				for(int j = backPt[1]; j<backPt[1]+backLen[1]; j++){
					
					if((i<pt[0]||i>=(pt[0]+len[0]))||(j<pt[1]||j>=(pt[1]+len[1]))){
					}
					else{
						br.xArrayAdd(i);
						br.yArrayAdd(j);
						br.zArrayAdd(input.getDouble(i,j));
					}
				}
			}
			
			Dataset xBackgroundDat = DatasetFactory.createFromObject(br.getXArray());
			Dataset yBackgroundDat = DatasetFactory.createFromObject(br.getYArray());
			Dataset zBackgroundDat = DatasetFactory.createFromObject(br.getZArray());
			
			Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
					AnalaysisMethodologies.toInt(model.getFitPower()), xBackgroundDat, yBackgroundDat);
			
	        
	        
	        double[] location = new double[] { (double) model.getBackgroundLenPt()[1][1], 
	        								   (double) model.getBackgroundLenPt()[1][0], 
	        								   (double) (model.getBackgroundLenPt()[1][1] + model.getBackgroundLenPt()[0][1]), 
	        								   (double) (model.getBackgroundLenPt()[1][0]),
	        								   (double) model.getBackgroundLenPt()[1][1], 
	        								   (double) model.getBackgroundLenPt()[1][0] + model.getBackgroundLenPt()[0][0], 
	        								   (double) (model.getBackgroundLenPt()[1][1] + model.getBackgroundLenPt()[0][1]), 
	        								   (double) (model.getBackgroundLenPt()[1][0] + model.getBackgroundLenPt()[0][0]) };
	        	  
	        
	        
	        DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, zBackgroundDat);
			double[] params = test.getData();
			
			in1Background = g2.getOutputValues0(params, len, model.getBoundaryBox(),
					AnalaysisMethodologies.toInt(model.getFitPower()));
		
//			IndexIterator it0 = in1Background.getIterator();
//			
//			while (it0.hasNext()) {
//				double q = in1Background.getElementDoubleAbs(it0.index);
//				if (q < 0)
//					in1Background.setObjectAbs(it0.index, 0.1);
//			}
			
			Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
					
			IndexIterator it1 = pBackgroundSubtracted.getIterator();
			
//			while (it1.hasNext()) {
//				double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
//				if (q < 0)
//					pBackgroundSubtracted.setObjectAbs(it1.index, 0.1);
//			}
				
			output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
				
			output.setName("Region of Interest, constant background removed");

			return new OperationData(output, 
									 location, 
									 background.getROI(),
									 in1Background);
			
		}
	
}
