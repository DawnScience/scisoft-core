package org.dawnsci.surfacescatter;

import org.apache.commons.lang.StringUtils;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
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
		private static IRegion offsetCheat;
		private DoubleDataset in1Background;
		private int[][] newOffsetLenPt;
//		private int[][] newOffsetLenPt1;
		private static int DEBUG =1;
		
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
			
			debug("pt[0]: " + pt[0]);
			debug("pt[1]: " + pt[1]);
			
//			
//			if (model.getBoxOffsetLenPt() != null){
//				newOffsetLenPt = model.getBoxOffsetLenPt();
//			}
//			
//			IPlottingSystem<Composite> pS = model.getPlottingSystem();
//			IPlottingSystem<Composite> ssvsPS = model.getSPlottingSystem();
//			
			if (g2 == null)
				g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
			if ((int) Math.pow(AnalaysisMethodologies.toInt(model.getFitPower()) + 1, 2) != g2.getNoOfParameters())
				g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));
			
			
//			Display display = Display.getCurrent();
//	        Color red = display.getSystemColor(SWT.COLOR_RED);
//	        Color gray = display.getSystemColor(SWT.COLOR_GRAY);
//	        
			
			Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);
			
//			if(model.getTrackingMarker()!=3){
//				for(IRegion oc :ssvsPS.getRegions()){
//					
//					CharSequence s= "offsetCheat";
//					String name = oc.getName();
//					
//					if(name.contains(s)){
//
//						String arraySubString = StringUtils.substringBetween(name, "||");
//						String[] offsetLenPtAsStrings = StringUtils.split(arraySubString);
//						
//						int[] offsetLen = new int[2];
//						int[] offsetPt = new int[2];
//						newOffsetLenPt = new int[2][2];
//						
//						offsetLen[0] = Integer.valueOf(offsetLenPtAsStrings[0]);
//						offsetLen[1] = Integer.valueOf(offsetLenPtAsStrings[1]);
//						
//						offsetPt[0] = Integer.valueOf(offsetLenPtAsStrings[2]);
//						offsetPt[1] = Integer.valueOf(offsetLenPtAsStrings[3]);
//						
//						newOffsetLenPt[0] = offsetLen;
//						newOffsetLenPt[1] = offsetPt;
//						
//						model.setBoxOffsetLenPt(newOffsetLenPt);
//					}
//				}
//			}
//			if (pS.getRegion("Background Region")==null){
//				
//				try {
//					background =pS.createRegion("Background Region", RegionType.BOX);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				pS.addRegion(background);
//				
//				if (model.getBoxOffsetLenPt()!=null){
//				
//					int[] offsetLen = newOffsetLenPt[0];
//					int[] offsetPt = newOffsetLenPt[1];
//					
//					int pt0 = pt[0] + offsetPt[0];
//					int pt1 = pt[1] + offsetPt[1];
//					
//					int len0 = len[0] + offsetLen[0];
//					int len1 = len[1] + offsetLen[1];
//				
//					IRectangularROI newROI = new RectangularROI(pt0,pt1,len0,len1,0);
//
//					background.setROI(newROI);
//				}
//				
//				else{
//		
//					int pt0 = pt[0] + 25;
//					int pt1 = pt[1] + 25;
//					
//					int len0 = len[0] + 0;
//					int len1 = len[1] + 0;
//				
//					IRectangularROI newROI = new RectangularROI(pt0,pt1,len0,len1,0);
//				
//					background.setROI(newROI);
//				}
//				
//				background.setRegionColor(red);
//				
//			}
//			else{
//				background = pS.getRegion("Background Region");
//				background.setRegionColor(red);
//				
//				if (model.getBoxOffsetLenPt()!=null){
////					model.getTrackingMarker() == 3 &&  
//					int[] offsetLen = model.getBoxOffsetLenPt()[0];
//					int[] offsetPt = model.getBoxOffsetLenPt()[1];
//					
//					int pt0 = pt[0] + offsetPt[0];
//					int pt1 = pt[1] + offsetPt[1];
//					
//					int len0 = len[0] + offsetLen[0];
//					int len1 = len[1] + offsetLen[1];
//					
//					IRectangularROI newROI = new RectangularROI(pt0,pt1,len0,len1,0);
//				
//					background.setROI(newROI);
//				}
//
//				else{
//					
//				}
//				
//				background.setRegionColor(red);
//			}
//			
//			
//			if (ssvsPS.getRegion("ssvs Background Region")==null){
//				
//				try {
//					ssvsBackground =ssvsPS.createRegion("ssvs Background Region", RegionType.BOX);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				ssvsPS.addRegion(ssvsBackground);
//				
//				if (model.getBoxOffsetLenPt()!=null){
//					
//					int[] offsetLen = model.getBoxOffsetLenPt()[0];
//					int[] offsetPt = model.getBoxOffsetLenPt()[1];
//					
//					int pt0 = pt[0] + offsetPt[0];
//					int pt1 = pt[1] + offsetPt[1];
//					
//					int len0 = len[0] + offsetLen[0];
//					int len1 = len[1] + offsetLen[1];
//				
//					IRectangularROI newROI = new RectangularROI(pt0,pt1,len0,len1,0);
//					
//					ssvsBackground.setROI(newROI);
//				}
//				
//				else{
//		
//					int pt0 = pt[0] + 25;
//					int pt1 = pt[1] + 25;
//					
//					int len0 = len[0] + 0;
//					int len1 = len[1] + 0;
//				
//					IRectangularROI newROI = new RectangularROI(pt0,pt1,len0,len1,0);
//				
//					ssvsBackground.setROI(newROI);
//				}
//				
//				ssvsBackground.setRegionColor(red);
//			}
//			
//			else{
//				ssvsPS.removeRegion(ssvsPS.getRegion("ssvs Background Region"));
//				try {
//					ssvsBackground =ssvsPS.createRegion("ssvs Background Region", RegionType.BOX);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				ssvsPS.addRegion(ssvsBackground);
//				ssvsBackground.setRegionColor(red);
//			}
//			
//			if(model.getBoxOffsetLenPt() != null){
//				
//				int[] offsetLen = model.getBoxOffsetLenPt()[0];
//				int[] offsetPt = model.getBoxOffsetLenPt()[1];
//				
//				int pt0 = pt[0] + offsetPt[0];
//				int pt1 = pt[1] + offsetPt[1];
//				
//				int len0 = len[0] + offsetLen[0];
//				int len1 = len[1] + offsetLen[1];
//				
////				IRectangularROI offsetBackgroundROI = new RectangularROI(pt0,pt1,len0,len1,0);
//				IRectangularROI ssvsOffsetBackgroundROI = new RectangularROI(pt0,pt1,len0,len1,0);
//
//				ssvsBackground.setROI(ssvsOffsetBackgroundROI);
//			}
//			else{
//				
//				int pt0 = pt[0] + 25;
//				int pt1 = pt[1] + 25;
//				
//				int len0 = len[0] + 0;
//				int len1 = len[1] + 0;
//			
//				IRectangularROI newROI = new RectangularROI(pt0,pt1,len0,len1,0);
//			
//				ssvsBackground.setROI(newROI);
//			}
			

//	        background.addROIListener(new IROIListener() {
//
//				@Override
//				public void roiDragged(ROIEvent evt) {
//					roiStandard(evt);
//				}
//
//				@Override
//				public void roiChanged(ROIEvent evt) {
//					roiStandard(evt);
//				}
//
//				@Override
//				public void roiSelected(ROIEvent evt) {
//					roiStandard(evt);
//				}
//				
//				public void roiStandard(ROIEvent evt) {
//					
////					model.setBackgroundROI(background.getROI().getBounds());
//					
//					IRectangularROI redRectangle = background.getROI().getBounds();
//					int[] redLen = redRectangle.getIntLengths();
//					int[] redPt = redRectangle.getIntPoint();//getIntPoint();
//					int[][] redLenPt = {redLen,redPt};
//					
//					RectangularROI newROI = new RectangularROI(redLenPt[1][0],
//															   redLenPt[1][1],
//															   redLenPt[0][0],
//															   redLenPt[0][1],0);
//					ssvsBackground.setROI(newROI);
//					
//					newOffsetLenPt = new int[2][2];
//					
//					newOffsetLenPt[0][0]  =  -len[0] + redLen[0];
//					newOffsetLenPt[0][1]  =  -len[1] + redLen[1];
//					
//					
//					newOffsetLenPt[1][0]  = -pt[0] + redPt[0];
//					newOffsetLenPt[1][1]  = -pt[1] + redPt[1];
//					
//					 
//					model.setBoxOffsetLenPt(newOffsetLenPt);
//					
//					try {
//						
//						int probe = 0;
//						
//						for(IRegion oc :ssvsPS.getRegions()){
//							
//							CharSequence s= "offsetCheat";
//							String name = oc.getName();
//							
//							if(name.contains(s)){
//								probe+=1;
//							}
//						}
//						
//						if (probe == 0){ 
//							offsetCheat =ssvsPS.createRegion("offsetCheat||" +String.valueOf(model.getBoxOffsetLenPt()[0][0]) + " "
//																		 +String.valueOf(model.getBoxOffsetLenPt()[0][1]) + " " 
//																		 +String.valueOf(model.getBoxOffsetLenPt()[1][0]) + " "
//																		 +String.valueOf(model.getBoxOffsetLenPt()[1][1]) + " "
//																		 +"||", 
//																		 RegionType.BOX);
//							
//							IRectangularROI newROI1 = new RectangularROI(10,10,10,10,0);
//						
//							offsetCheat.setROI(newROI1);
//						}
//						
//						else{
//							offsetCheat.setName("offsetCheat||" +String.valueOf(model.getBoxOffsetLenPt()[0][0]) + " "
//																		 +String.valueOf(model.getBoxOffsetLenPt()[0][1]) + " " 
//																		 +String.valueOf(model.getBoxOffsetLenPt()[1][0]) + " "
//																		 +String.valueOf(model.getBoxOffsetLenPt()[1][1]) + " "
//																		 +"||");
//						}
//						offsetCheat.setRegionColor(red);
//						
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					ssvsPS.addRegion(offsetCheat);
//		        
//					
//				}
//
//			});
	        
//	        ssvsBackground.addROIListener(new IROIListener() {
//
//				@Override
//				public void roiDragged(ROIEvent evt) {
//					roiStandard(evt);
//				}
//
//				@Override
//				public void roiChanged(ROIEvent evt) {
//					roiStandard(evt);
//				}
//
//				@Override
//				public void roiSelected(ROIEvent evt) {
//					roiStandard(evt);
//				}
//				
//				public void roiStandard(ROIEvent evt) {
//					model.setBackgroundROI(background.getROI().getBounds());
//				}
//
//			});
	        
	        
//	        if(newOffsetLenPt!=null){
//	        	
//	        	try {
//					
//					int probe = 0;
//					
//					for(IRegion oc :ssvsPS.getRegions()){
//						
//						CharSequence s= "offsetCheat";
//						String name = oc.getName();
//						
//						if(name.contains(s)){
//							probe+=1;
//						}
//					}
//					
//					if (probe == 0){ 
//						offsetCheat =ssvsPS.createRegion("offsetCheat||" +String.valueOf(model.getBoxOffsetLenPt()[0][0]) + " "
//																	 +String.valueOf(model.getBoxOffsetLenPt()[0][1]) + " " 
//																	 +String.valueOf(model.getBoxOffsetLenPt()[1][0]) + " "
//																	 +String.valueOf(model.getBoxOffsetLenPt()[1][1]) + " "
//																	 +"||", 
//																	 RegionType.BOX);
//						
//						IRectangularROI newROI1 = new RectangularROI(10,10,10,10,0);
//					
//						offsetCheat.setROI(newROI1);
//					}
//					
//					else{
//						offsetCheat.setName("offsetCheat||" +String.valueOf(model.getBoxOffsetLenPt()[0][0]) + " "
//																	 +String.valueOf(model.getBoxOffsetLenPt()[0][1]) + " " 
//																	 +String.valueOf(model.getBoxOffsetLenPt()[1][0]) + " "
//																	 +String.valueOf(model.getBoxOffsetLenPt()[1][1]) + " "
//																	 +"||");
//					}
//					offsetCheat.setRegionColor(red);
//					ssvsPS.addRegion(offsetCheat);
//					
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        
//				
//			}
	        
	        int[] backLen = model.getBackgroundLenPt()[0];
	        int[] backPt = model.getBackgroundLenPt()[1];
	        
	        if(model.getBoxOffsetLenPt() != null){
				
				int[] offsetLen = model.getBoxOffsetLenPt()[0];
				int[] offsetPt = model.getBoxOffsetLenPt()[1];
				
				int pt0 = pt[0] + offsetPt[0];
				int pt1 = pt[1] + offsetPt[1];
				backPt = new int[] {pt0, pt1}; 
				
				
				int len0 = len[0] + offsetLen[0];
				int len1 = len[1] + offsetLen[1];
				backLen = new int[] {len0, len1}; 
				
	        }
	        
	        debug("backPt[0]: " + backPt[0]);
			debug("backPt[1]: " + backPt[1]);
			
	        BackgroundRegionArrays br = new BackgroundRegionArrays();
	        
	        
			for (int i = backPt[0]; i<backPt[0]+backLen[0]; i++){
				for(int j = backPt[1]; j<backPt[1]+backLen[1]; j++){
					
					if((i<pt[0]||i>=(pt[0]+len[0]))||(j<pt[1]||j>=(pt[1]+len[1]))){
					}
					else{
						br.xArrayAdd(i);
						br.yArrayAdd(j);
						br.zArrayAdd(input.getDouble(j,i));
					}
				}
			}
			
			Dataset xBackgroundDat = DatasetFactory.createFromObject(br.getXArray());
			Dataset yBackgroundDat = DatasetFactory.createFromObject(br.getYArray());
			Dataset zBackgroundDat = DatasetFactory.createFromObject(br.getZArray());
			
			Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
					AnalaysisMethodologies.toInt(model.getFitPower()), xBackgroundDat, yBackgroundDat);
			double[] location = null;
	        
//	        
//	        double[] location = new double[] { (double) model.getBackgroundLenPt()[1][1], 
//	        								   (double) model.getBackgroundLenPt()[1][0], 
//	        								   (double) (model.getBackgroundLenPt()[1][1] + model.getBackgroundLenPt()[0][1]), 
//	        								   (double) (model.getBackgroundLenPt()[1][0]),
//	        								   (double) model.getBackgroundLenPt()[1][1], 
//	        								   (double) model.getBackgroundLenPt()[1][0] + model.getBackgroundLenPt()[0][0], 
//	        								   (double) (model.getBackgroundLenPt()[1][1] + model.getBackgroundLenPt()[0][1]), 
//	        								   (double) (model.getBackgroundLenPt()[1][0] + model.getBackgroundLenPt()[0][0]) };
//	        	  
	        
	        
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
			Dataset pBackgroundSubtracted = DatasetFactory.zeros(new int[] {2}, Dataset.ARRAYFLOAT64);
			
			try{
				pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
			}
			catch(Exception e){
				debug("uh fuck");
			}
			
			
//			IndexIterator it1 = pBackgroundSubtracted.getIterator();
			
//			while (it1.hasNext()) {
//				double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
//				if (q < 0)
//					pBackgroundSubtracted.setObjectAbs(it1.index, 0.1);
//			}
				
			output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
				
			output.setName("Region of Interest, constant background removed");

			return new OperationData(output, 
									 location, 
//									 background.getROI(),
									 null,
									 in1Background,
									 newOffsetLenPt);
			
		}
	
		private void debug (String output) {
			if (DEBUG == 1) {
				System.out.println(output);
			}
		}
		
}
