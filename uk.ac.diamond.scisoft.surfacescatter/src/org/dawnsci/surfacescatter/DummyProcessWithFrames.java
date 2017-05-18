package org.dawnsci.surfacescatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.dawnsci.surfacescatter.ProcessingMethodsEnum.ProccessingMethod;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.Metadata;
import org.eclipse.swt.widgets.Composite;

public class DummyProcessWithFrames {
	
	
	private static Dataset yValue;
	private static int DEBUG = 0;
	private static OperationData outputOD;
	
	public static IDataset DummyProcess(DirectoryModel drm,  
										GeometricParametersModel gm, 
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection){		
		
		IDataset output =null;	
		
		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape()));
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(fm.getBackgroundMethdology()){
			case TWOD_TRACKING:
								
				
				AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
				if(trackingMarker != 3 && trackingMarker != 4 && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					ath.TwoDTracking3(drm, 
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
								  outputOD= TwoDFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
														   fm.getFitPower(),
														   fm.getBoundaryBox(),
														   drm.getInitialLenPt(),
														   input,
														   k,
														   trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   						 drm.getInitialLenPt(),
							   						 fm.getFitPower(),
							   						 fm.getBoundaryBox(),
													 input,
							   						 selection,	
							   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   							input,
							   							drm.getInitialLenPt(),
							   							fm.getFitPower(),
							   							fm.getBoundaryBox(),
							   							k,
							   							trackingMarker);
				}
			
				output = outputOD.getData();
				
				IDataset temporaryBackground = DatasetFactory.ones(new int[] {1});
				
				try{
					temporaryBackground =  (IDataset) outputOD.getAuxData()[0];
				}
				catch(Exception f){
//					DoubleDataset temporaryBackground1 =  (DoubleDataset) outputOD.getAuxData()[0];
//					temporaryBackground = (IDataset) temporaryBackground1;
				}
				
				drm.setTemporaryBackgroundHolder(temporaryBackground);
				
				break;
				
			case TWOD:
				
				
				
				int[] len = drm.getInitialLenPt()[0];
				int[] pt = drm.getInitialLenPt()[1];
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm,
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				else{	
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
					(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				

				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					
					double[] p = new double[6];
						
					try{
						p =drm.getLocationList().get(fm.getDatNo()).get(selection);
					}
					catch(Exception n){
						System.out.println(n.getMessage());
						p= null;
					}
					
					outputOD= TwoDFittingIOp(p,
										     fm.getFitPower(),
										     fm.getBoundaryBox(),
										     drm.getInitialLenPt(),
										     input,
										     k,
										     trackingMarker);
				}
				
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
			
				output = outputOD.getData();
				

				IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];
				
				drm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
				
			case SECOND_BACKGROUND_BOX:
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
					(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				output = secondConstantROIMethod(input,
  						 						 drm,
  						 						 fm.getBackgroundMethdology(), 
  						 						 selection,
  						 						 trackingMarker,
  						 						 k);
	
				
				
				
				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
									  trackingMarker, 
									  k, 
									  selection);
				}

				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				

				output = secondConstantROIMethod(input,
						   						 drm,
						   						 fm.getBackgroundMethdology(), 
						   						 selection,
						   						 trackingMarker,
						   						 k);
				
				break;
				
			case X:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm,
							  trackingMarker, 
							  k, 
							  selection);
				}

				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				OperationData outputOD2= OneDFittingIOp(drm.getLenPtForEachDat()[k],
						   								drm.getInitialLenPt(),
						   								fm.getFitPower(),
						   								drm.getLocationList().get(fm.getDatNo()).get(selection),
						   								input,
						   								fm.getBoundaryBox(),
						   								Methodology.X,
						   								trackingMarker,
						   								k);
				output = outputOD2.getData();
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground2);		
								
				break;
				
			case Y:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm,
							  trackingMarker, 
							  k, 
							  selection);
				}

				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(drm.getLenPtForEachDat()[k],
														drm.getInitialLenPt(),
														fm.getFitPower(),
														drm.getLocationList().get(fm.getDatNo()).get(selection),
														input,
														fm.getBoundaryBox(),
														Methodology.Y,
														trackingMarker,
														k);
				output = outputOD3.getData();
				
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground3);
				
		
				break;
		}
		

		
		if(Arrays.equals(output.getShape(), (new int[] {2,2}))){
			IndexIterator it11 = ((Dataset) output).getIterator();
			
			while (it11.hasNext()) {
				double q = ((Dataset) output).getElementDoubleAbs(it11.index);
				if (q <= 0)
					((Dataset) output).setObjectAbs(it11.index, 0.1);
			}
			return output;
		}
		
		
		yValue = correctionMethod(fm, 
								  input);
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double rawIntensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		
		Double fhkl = (double) 0.001;
			if (intensity >=0){
				fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}		
			
		
		if (trackingMarker !=3 ){
			
			
			OutputCurvesDataPackage ocdp =  drm.getOcdp();
			int noOfFrames = drm.getFms().size();
			
			ocdp.addToYListForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,intensity);
			ocdp.addToYListFhklForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,fhkl);
			ocdp.addToYListRawForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,rawIntensity);
			
			ocdp.addyList(noOfFrames, selection ,intensity);
			ocdp.addyListFhkl(noOfFrames, selection ,fhkl);
			ocdp.addYListRawIntensity(noOfFrames, selection ,rawIntensity);
			ocdp.addOutputDatArray(noOfFrames, selection ,output);
			
			fm.setBackgroundSubtractedImage(output);
//			fm.setRoiLocation(roiLocation);
			
		}
		
		return output;
	}
	
	public static IDataset DummyProcess0(DirectoryModel drm,
										GeometricParametersModel gm, 
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection){		
		
		IDataset output =null;	
		
		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape()));
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		switch(fm.getBackgroundMethdology()){
			case TWOD_TRACKING:
								
				
				AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
				
				ath1.TwoDTracking3(drm,
								  trackingMarker, 
								  k, 
								  selection);
				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					
					double[] p = new double[6];
					
					try{
						p =drm.getLocationList().get(fm.getDatNo()).get(selection);
					}
					catch(Exception n){
						System.out.println(n.getMessage());
						p= null;
					}
					
					outputOD= TwoDFittingIOp(p,
						     fm.getFitPower(),
						     fm.getBoundaryBox(),
						     drm.getInitialLenPt(),
						     input,
						     k,
						     trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
			
				
				
				
				
				output = outputOD.getData();
				
			
				IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];
			

				drm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				
				break;
			case TWOD:
				
				int[] len = drm.getInitialLenPt()[0];
				int[] pt = drm.getInitialLenPt()[1];
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
					
					ath.TwoDTracking3(drm,
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				else{	
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
					(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					outputOD= TwoDFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   fm.getFitPower(),
							   fm.getBoundaryBox(),
							   drm.getInitialLenPt(),
							   input,
							   k,
							   trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
				
				
				
				
				output = outputOD.getData();
				
			
				temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];
				drm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				
				break;
			case SECOND_BACKGROUND_BOX:

				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
					
					ath.TwoDTracking3(drm,
									  trackingMarker, 
									  k, 
									  selection);
			
				}

				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				

				output = secondConstantROIMethod(input,
						   						 drm,
						   						 fm.getBackgroundMethdology(), 
						   						 selection,
						   						 trackingMarker,
						   						 k);


				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
		
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
					
					ath.TwoDTracking3(drm,
									  trackingMarker, 
									  k, 
									  selection);
			
				}

				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}

				output = secondConstantROIMethod(input,
						   						 drm,
						   						 fm.getBackgroundMethdology(), 
						   						 selection,
						   						 trackingMarker,
						   						 k);
				
				
				break;
		
			case X:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
					
					ath.TwoDTracking3(drm,
									  trackingMarker, 
									  k, 
									  selection);
			
				}
				
				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD2= OneDFittingIOp(drm.getLenPtForEachDat()[k],
							drm.getInitialLenPt(),
							fm.getFitPower(),
							drm.getLocationList().get(fm.getDatNo()).get(selection),
							input,
							fm.getBoundaryBox(),
							Methodology.X,
							trackingMarker,
							k);
				output = outputOD2.getData();
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground2);

				break;
											
			case Y:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath2 = new AgnosticTrackerWithFrames();
					
					ath2.TwoDTracking3(drm,
									  trackingMarker, 
									  k, 
									  selection);
			
					
				}

				else{
					
					len = drm.getInitialLenPt()[0];
					pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(drm.getLenPtForEachDat()[k],
							drm.getInitialLenPt(),
							fm.getFitPower(),
							drm.getLocationList().get(fm.getDatNo()).get(selection),
							input,
							fm.getBoundaryBox(),
							Methodology.Y,
							trackingMarker,
							k);
				output = outputOD3.getData();
									
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground3);
				
				
				break;
		}
	
		
		yValue = correctionMethod(fm, 
				  input);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double rawIntensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}	

		
		if (trackingMarker !=3 ){
			
			
			OutputCurvesDataPackage ocdp =  drm.getOcdp();
			int noOfFrames = drm.getFms().size();
			
			ocdp.addToYListForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,intensity);
			ocdp.addToYListFhklForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,fhkl);
			ocdp.addToYListRawForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,rawIntensity);
			
			ocdp.addyList(noOfFrames, selection ,intensity);
			ocdp.addyListFhkl(noOfFrames, selection ,fhkl);
			ocdp.addYListRawIntensity(noOfFrames, selection ,rawIntensity);
			ocdp.addOutputDatArray(noOfFrames, selection ,output);
			
			fm.setBackgroundSubtractedImage(output);
			
			
			
//			dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
//			dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
//			dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
//			dm.addYListRaw(model.getDatImages().getShape()[0], k ,sm.getCurrentRawIntensity());
//			
//			sm.addyList(sm.getImages().length, selection ,intensity);
//			sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
//			sm.addYListRawIntensity(sm.getImages().length, selection ,rawIntensity);
//			sm.addOutputDatArray(sm.getImages().length, selection ,output);
			
		}

		return output;
	}
	
	
	
	public static IDataset DummyProcess1(DirectoryModel drm, 
										GeometricParametersModel gm, 
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection){		
		
		IDataset output =null;	
		
		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape()));
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		switch(fm.getBackgroundMethdology()){
			case TWOD_TRACKING:
												
				AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
				
				ath.TwoDTracking3(drm, 
									  trackingMarker, 
									  k, 
									  selection);
				
				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					double[] p = new double[6];
					
					try{
						p =drm.getLocationList().get(fm.getDatNo()).get(selection);
					}
					catch(Exception n){
						System.out.println(n.getMessage());
						p= null;
					}
					
					  outputOD= TwoDFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   fm.getFitPower(),
							   fm.getBoundaryBox(),
							   drm.getInitialLenPt(),
							   input,
							   k,
							   trackingMarker);
					  
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
				
				
				
				
				output = outputOD.getData();
				
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[0];

				drm.setTemporaryBackgroundHolder(temporaryBackground);
				
				break;
				
			case TWOD:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					
					
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
										  trackingMarker, 
										  k, 
										  selection);
					
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				
				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					  outputOD= TwoDFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   fm.getFitPower(),
							   fm.getBoundaryBox(),
							   drm.getInitialLenPt(),
							   input,
							   k,
							   trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
				
				
				
				
				output = outputOD.getData();
				
			
				IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];
				
				drm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
			case SECOND_BACKGROUND_BOX:

				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					
					

					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
										  trackingMarker, 
										  k, 
										  selection);
					
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[]  pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				output = secondConstantROIMethod(input,
						   						 drm,
						   						 fm.getBackgroundMethdology(), 
						   						 selection,
						   						 trackingMarker,
						   						 k);
				
				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){

					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
										  trackingMarker, 
										  k, 
										  selection);
					
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}

				output = secondConstantROIMethod(input,
						   						 drm,
						   						 fm.getBackgroundMethdology(), 
						   						 selection,
						   						 trackingMarker,
						   						 k);
				
				break;
				
			case X:
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){

					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
										  trackingMarker, 
										  k, 
										  selection);
					
					
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD2= OneDFittingIOp(drm.getLenPtForEachDat()[k],
							drm.getInitialLenPt(),
							fm.getFitPower(),
							drm.getLocationList().get(fm.getDatNo()).get(selection),
							input,
							fm.getBoundaryBox(),
							Methodology.X,
							trackingMarker,
							k);
				
				output = outputOD2.getData();
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground2);
									
				break;
											
			case Y:
				
				

				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking3(drm, 
										  trackingMarker, 
										  k, 
										  selection);
					
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					
					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(drm.getLenPtForEachDat()[k],
							drm.getInitialLenPt(),
							fm.getFitPower(),
							drm.getLocationList().get(fm.getDatNo()).get(selection),
							input,
							fm.getBoundaryBox(),
							Methodology.Y,
							trackingMarker,
							k);
				
				output = outputOD3.getData();
				
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground3);
				
									
				break;
		}
				
		yValue = correctionMethod(fm, 
				  input);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double rawIntensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}	
		
		
		if (trackingMarker !=3 ){
			
			OutputCurvesDataPackage ocdp =  drm.getOcdp();
			int noOfFrames = drm.getFms().size();
			
			ocdp.addToYListForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,intensity);
			ocdp.addToYListFhklForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,fhkl);
			ocdp.addToYListRawForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,rawIntensity);
			
			ocdp.addyList(noOfFrames, selection ,intensity);
			ocdp.addyListFhkl(noOfFrames, selection ,fhkl);
			ocdp.addYListRawIntensity(noOfFrames, selection ,rawIntensity);
			ocdp.addOutputDatArray(noOfFrames, selection ,output);
			
			fm.setBackgroundSubtractedImage(output);
			
			
//			dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
//			dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
//			dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
//			dm.addYListRaw(model.getDatImages().getShape()[0], k ,sm.getCurrentRawIntensity());
//			
//			sm.addyList(sm.getImages().length, selection ,intensity);
//			sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
//			sm.addYListRawIntensity(sm.getImages().length, selection ,rawIntensity);
//			sm.addOutputDatArray(sm.getImages().length, selection ,output);
			
			debug("  intensity:  " + intensity + "   k: " + k);
		}
		
		
		debug("intensity added to dm: " + intensity + "   local k: " + k + "   selection: " + selection);
		
		return output;
	}
	
	
	public static IDataset DummyProcess1(DirectoryModel drm,  
										GeometricParametersModel gm, 
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection,
										double[]locationList){		
		
		////////////////////////////////NB selection is position in the sorted list of the whole rod k is position in the .dat file
		IDataset output =null;	
		
		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape()));
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		switch(fm.getBackgroundMethdology()){
			case TWOD_TRACKING:
								
				AgnosticTrackerWithFrames ath = new AgnosticTrackerWithFrames();
				
				ath.TwoDTracking1(drm, 
						trackingMarker, 
						k,
						locationList,
						selection);
				

				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					  outputOD= TwoDFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   fm.getFitPower(),
							   fm.getBoundaryBox(),
							   drm.getInitialLenPt(),
							   input,
							   k,
							   trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
				
				
				
				
				output = outputOD.getData();
				
			
				IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

				
				drm.setTemporaryBackgroundHolder(temporaryBackground1);

				break;
				
			case TWOD:
			
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath2 = new AgnosticTrackerWithFrames();
					
					ath2.TwoDTracking1(drm, 
							trackingMarker, 
							k,
							locationList,
							selection);
					


				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					

					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				if(AnalaysisMethodologies.toInt(fm.getFitPower())<5){
					  outputOD= TwoDFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
							   fm.getFitPower(),
							   fm.getBoundaryBox(),
							   drm.getInitialLenPt(),
							   input,
							   k,
							   trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN){
					outputOD= TwoDGaussianFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
	   						 drm.getInitialLenPt(),
	   						 fm.getFitPower(),
	   						 fm.getBoundaryBox(),
							 input,
	   						 selection,	
	   						 trackingMarker);
				}
				else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL){
					outputOD= TwoDExponentialFittingIOp(drm.getLocationList().get(fm.getDatNo()).get(selection),
   							input,
   							drm.getInitialLenPt(),
   							fm.getFitPower(),
   							fm.getBoundaryBox(),
   							k,
   							trackingMarker);
				}
				
				
				
				
				
				output = outputOD.getData();
				
			
				temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];				
			
				drm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
			case SECOND_BACKGROUND_BOX:

				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath2 = new AgnosticTrackerWithFrames();
					
					ath2.TwoDTracking1(drm, 
							trackingMarker, 
							k,
							locationList,
							selection);
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					

					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
	
				output = secondConstantROIMethod(input,
 						 drm,
 						 fm.getBackgroundMethdology(), 
 						 selection,
 						 trackingMarker,
 						 k);

				
				break;
			case OVERLAPPING_BACKGROUND_BOX:

				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking1(drm, 
							trackingMarker, 
							k,
							locationList,
							selection);
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					

					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				output = secondConstantROIMethod(input,
  						 drm,
  						 fm.getBackgroundMethdology(), 
  						 selection,
  						 trackingMarker,
  						 k);
				
				break;
				
			case X:
				
				
				
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath1 = new AgnosticTrackerWithFrames();
					
					ath1.TwoDTracking1(drm, 
							trackingMarker, 
							k,
							locationList,
							selection);
					
				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					

					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				OperationData outputOD2= OneDFittingIOp(drm.getLenPtForEachDat()[k],
						drm.getInitialLenPt(),
						fm.getFitPower(),
						drm.getLocationList().get(fm.getDatNo()).get(selection),
						input,
						fm.getBoundaryBox(),
						Methodology.X,
						trackingMarker,
						k);
			
				output = outputOD2.getData();

				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				
				drm.setTemporaryBackgroundHolder(temporaryBackground2);
				
											
				break;
											
			case Y:
				
			
				if(drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL){
					AgnosticTrackerWithFrames ath2 = new AgnosticTrackerWithFrames();
					
					ath2.TwoDTracking1(drm, 
							trackingMarker, 
							k,
							locationList,
							selection);

				}

				else{
					
					int[] len = drm.getInitialLenPt()[0];
					int[] pt = drm.getInitialLenPt()[1];
					

					fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(drm.getLenPtForEachDat()[k],
						drm.getInitialLenPt(),
						fm.getFitPower(),
						drm.getLocationList().get(fm.getDatNo()).get(selection),
						input,
						fm.getBoundaryBox(),
						Methodology.Y,
						trackingMarker,
						k);
			
			output = outputOD3.getData();

				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				drm.setTemporaryBackgroundHolder(temporaryBackground3);
				
				break;
		}
		
		yValue = correctionMethod(fm, 
				  input);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
				
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double rawIntensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}	
		
		if (trackingMarker !=3 ){
			OutputCurvesDataPackage ocdp =  drm.getOcdp();
			int noOfFrames = drm.getFms().size();
			
			ocdp.addToYListForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,intensity);
			ocdp.addToYListFhklForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k, fhkl);
			ocdp.addToYListRawForEachDat(fm.getDatNo(),drm.getDatFilepaths().length, drm.getNoOfImagesInDatFile(fm.getDatNo()), k ,rawIntensity);
			
			ocdp.addyList(noOfFrames, selection ,intensity);
			ocdp.addyListFhkl(noOfFrames, selection ,fhkl);
			ocdp.addYListRawIntensity(noOfFrames, selection ,rawIntensity);
			ocdp.addOutputDatArray(noOfFrames, selection ,output);
			
			fm.setBackgroundSubtractedImage(output);
			debug("  intensity:  " + intensity + "   k: " + k);
		}
		debug("intensity added to dm: " + intensity + "   local k: " + k + "   selection: " + selection);
		
		return output;
	}
	
	
	public static OperationData TwoDFittingIOp(ExampleModel model,
										IDataset input,
										SuperModel sm,
										int k,
										int trackingMarker){
		
		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(sm.getInitialLenPt());
		
		if (trackingMarker != 3){
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
				tdfm.setLenPt(lenPt);
			}
			else{
				tdfm.setLenPt(sm.getInitialLenPt());
			}
			
		}
		else{
			tdfm.setLenPt(sm.getInitialLenPt());
		}
		
		tdfm.setFitPower(model.getFitPower());
		tdfm.setBoundaryBox(model.getBoundaryBox());
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		TwoDFittingUsingIOperation tdfuio = new TwoDFittingUsingIOperation();
		tdfuio.setModel(tdfm);
		
		return tdfuio.execute(input, null);
		
	}
	
	public static OperationData TwoDFittingIOp(double[] p,   // = sm.getLocationList().get(k)
											   FitPower fp,  //model.getFitPower()
											   int boundaryBox, //model.getBoundaryBox()
											   int[][] initialLenPt,  //sm.getInitialLenPt()
											   IDataset input,
											   int k,
											   int trackingMarker){

		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(initialLenPt);
		
		input.squeeze();
		
		try{
			
			if (trackingMarker != 3){
	
				int[] pt = new int[]{(int) p[0], (int) p[1]}; 
				int[] len = initialLenPt[0]; 
				int[][] lenPt = new int[][] {len,pt};
				
				if(p[0] != 0 && p[1] != 0){
					tdfm.setLenPt(lenPt);
				}
			
				else{
					tdfm.setLenPt(initialLenPt);
				}	
			}
			else{
				tdfm.setLenPt(initialLenPt);
			}
		}
		
		
		catch(Exception o){
			System.out.println(o.getMessage());
			tdfm.setLenPt(initialLenPt);
		}
		
		tdfm.setFitPower(fp);
		tdfm.setBoundaryBox(boundaryBox);
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		TwoDFittingUsingIOperation tdfuio = new TwoDFittingUsingIOperation();
		tdfuio.setModel(tdfm);
		
		return tdfuio.execute(input, null);
	
	}
		
	
	
	
	public static OperationData TwoDGaussianFittingIOp(ExampleModel model,
														IDataset input,
														SuperModel sm,
														int k,
														int trackingMarker){

		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(sm.getInitialLenPt());
		
		if (trackingMarker != 3){
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
				tdfm.setLenPt(lenPt);
			}
			else{
				tdfm.setLenPt(sm.getInitialLenPt());
			}
		}
		
		else{
			tdfm.setLenPt(sm.getInitialLenPt());
		}
		
		tdfm.setFitPower(model.getFitPower());
		tdfm.setBoundaryBox(model.getBoundaryBox());
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		TwoDGaussianFittingUsingIOperation tdgfuio = new TwoDGaussianFittingUsingIOperation();
		tdgfuio.setModel(tdfm);
		
		return tdgfuio.execute(input, null);
		
	}
	
	public static OperationData TwoDGaussianFittingIOp(double[] p, // = sm.getLocationList().get(k);,
													   int[][] initialLenPt, //sm.getInitialLenPt() 
													   FitPower fp,
													   int boundaryBox,
													   IDataset input,
													   int k,
													   int trackingMarker){
	
		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(initialLenPt);
		
		if (trackingMarker != 3){
			

			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = initialLenPt[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
				tdfm.setLenPt(lenPt);
			}
			
			else{
				tdfm.setLenPt(initialLenPt);
			}
		}
		
		else{
			tdfm.setLenPt(initialLenPt);
		}
		
		tdfm.setFitPower(fp);
		tdfm.setBoundaryBox(boundaryBox);
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		TwoDGaussianFittingUsingIOperation tdgfuio = new TwoDGaussianFittingUsingIOperation();
		tdgfuio.setModel(tdfm);
		
		return tdgfuio.execute(input, null);

	}
	
	
	
	public static OperationData TwoDExponentialFittingIOp(ExampleModel model,
			IDataset input,
			SuperModel sm,
			int k,
			int trackingMarker){


		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(sm.getInitialLenPt());
		
		if (trackingMarker != 3){
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			
			if(p[0] != 0 && p[1] != 0){
				tdfm.setLenPt(lenPt);
			}
			
			else{
				tdfm.setLenPt(sm.getInitialLenPt());
			}
		}
		
		else{
			tdfm.setLenPt(sm.getInitialLenPt());
		}
		
		tdfm.setFitPower(model.getFitPower());
		tdfm.setBoundaryBox(model.getBoundaryBox());
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		RefinedTwoDExponentialFittingUsingIOperation rtdefuio = new RefinedTwoDExponentialFittingUsingIOperation();
		rtdefuio.setModel(tdfm);
		
		return rtdefuio.execute(input, null);
		
	}
	
	
	public static OperationData TwoDExponentialFittingIOp(double[] p, // = sm.getLocationList().get(k);
														  IDataset input,
														  int[][] initialLenPt, // = sm.getInitialLenPt()[0];
														  FitPower fp,
														  int boundaryBox,
														  int k,
														  int trackingMarker){


		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(initialLenPt);
		
		if (trackingMarker != 3){
			
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = initialLenPt[0]; 
			int[][] lenPt = new int[][] {len,pt};
			
			if(p[0] != 0 && p[1] != 0){
				tdfm.setLenPt(lenPt);
			}
			
			else{
				tdfm.setLenPt(initialLenPt);
			}
		}
		
		else{
			tdfm.setLenPt(initialLenPt);
		}
		
		tdfm.setFitPower(fp);
		tdfm.setBoundaryBox(boundaryBox);
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		RefinedTwoDExponentialFittingUsingIOperation rtdefuio = new RefinedTwoDExponentialFittingUsingIOperation();
		rtdefuio.setModel(tdfm);
		
		return rtdefuio.execute(input, null);
		
	}
	
	
	
	public static IDataset caseXY(SuperModel sm,
								  ExampleModel model,
								  DataModel dm,
								  int k,
								  int trackingMarker,
								  int selection,
								  double[] locationList,
								  IDataset input,
								  IPlottingSystem<Composite> pS,
								  AnalaysisMethodologies.Methodology am){
		
		
		
		
		OperationData outputOD2= OneDFittingIOp(model,
				   								input,
				   								sm,
				   								am,
				   								trackingMarker,
				   								k);
		IDataset output = outputOD2.getData();

									
		IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
		sm.setTemporaryBackgroundHolder(temporaryBackground2);
		
		return output;
	}
	
	
	public static OperationData OneDFittingIOp(ExampleModel model,
											   IDataset input,
					                           SuperModel sm,
					                           AnalaysisMethodologies.Methodology am,
					                           int trackingMarker,
					                           int k){
		
		OneDFittingModel odfm = new OneDFittingModel();
		odfm.setInitialLenPt(sm.getInitialLenPt());
		odfm.setLenPt(model.getLenPt());
		odfm.setFitPower(model.getFitPower());
		odfm.setBoundaryBox(model.getBoundaryBox());
		odfm.setDirection(am);
		
		if (trackingMarker != 3){
			
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
				odfm.setLenPt(lenPt);
			}
			else{
				odfm.setLenPt(sm.getInitialLenPt());
			}
			
		}
		else{
			odfm.setLenPt(sm.getInitialLenPt());
		}
		
				
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		OneDFittingUsingIOperation odfuio = new OneDFittingUsingIOperation();
		odfuio.setModel(odfm);
		
		return odfuio.execute(input, null);

	}
	
	
	public static OperationData OneDFittingIOp(int[][] mLenPt, //model.getLenPt()
											   int[][] initialLenPt, // sm.getInitialLenPt()
											   FitPower fp, // model.getFitPower()
											   double[]location, // 
											   IDataset input, //sm.getLocationList().get(k)
											   int boundaryBox, //model.getBoundaryBpx
									           AnalaysisMethodologies.Methodology am,
									           int trackingMarker,
									           int k){

		OneDFittingModel odfm = new OneDFittingModel();
		odfm.setInitialLenPt(initialLenPt);
		odfm.setLenPt(mLenPt);
		odfm.setFitPower(fp);
		odfm.setBoundaryBox(boundaryBox);
		odfm.setDirection(am);
		
		if (trackingMarker != 3){
		
			double[] p = location;
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = initialLenPt[0]; 
			int[][] lenPt = new int[][] {len,pt};
			
			if(p[0] != 0 && p[1] != 0){
				odfm.setLenPt(lenPt);
			}
			
			else{
			odfm.setLenPt(initialLenPt);
			}
	
		}
		
		else{
			odfm.setLenPt(initialLenPt);
		}
		
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		OneDFittingUsingIOperation odfuio = new OneDFittingUsingIOperation();
		odfuio.setModel(odfm);
		
		return odfuio.execute(input, null);
	
	}
		
	

	public static OperationData SecondConstantBackgroundROIFittingIOp(ExampleModel model,
			   														  IDataset input,
			   														  SuperModel sm,
			   														  int trackingMarker,
			   														  int k
			   														  ){

		SecondConstantROIBackgroundSubtractionModel scrbm 
					= new SecondConstantROIBackgroundSubtractionModel();
		scrbm.setInitialLenPt(sm.getInitialLenPt());
		scrbm.setLenPt(model.getLenPt());
		scrbm.setFitPower(model.getFitPower());
		scrbm.setBoundaryBox(model.getBoundaryBox());
		
		if (sm.getBackgroundLenPt() != null){
				scrbm.setBackgroundLenPt(sm.getBackgroundLenPt());
		}
		
		if (trackingMarker != 3){
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
				scrbm.setLenPt(lenPt);
			}
			else{
				scrbm.setLenPt(sm.getInitialLenPt());
			}
			
		}
		else{
			scrbm.setLenPt(sm.getInitialLenPt());
		}
		
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		SecondConstantROIUsingIOperation scrbio 
				= new SecondConstantROIUsingIOperation();
		scrbio.setModel(scrbm);
		
		return scrbio.execute(input, null);

	}
	
	
		
	public static OperationData SecondConstantBackgroundROIFittingIOp(IDataset input,
																	  double[] p, //  = sm.getLocationList().get(k);
																	  int[][] initialLenPt, // sm.getInitialLenPt()
																	  int[][] mLenPt, //model.getLenPt()
																	  int[][] backgroundLenPt,  //sm.getBackgroundLenPt()
																	  FitPower fp,
																	  int boundaryBox,
																	  int trackingMarker,
																	  int k){
		
		SecondConstantROIBackgroundSubtractionModel scrbm 
		= new SecondConstantROIBackgroundSubtractionModel();
		scrbm.setInitialLenPt(initialLenPt);
		scrbm.setLenPt(mLenPt);
		scrbm.setFitPower(fp);
		scrbm.setBoundaryBox(boundaryBox);
		
		if (backgroundLenPt != null){
			scrbm.setBackgroundLenPt(backgroundLenPt);
		}
		
		if (trackingMarker != 3){
		
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = initialLenPt[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
			scrbm.setLenPt(lenPt);
		}
			
		else{
			scrbm.setLenPt(initialLenPt);
		}
		
		}
		else{
			scrbm.setLenPt(initialLenPt);
		}
		
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		SecondConstantROIUsingIOperation scrbio 
				= new SecondConstantROIUsingIOperation();
		
		scrbio.setModel(scrbm);
		
		return scrbio.execute(input, null);
		
	}
	

	
	public static IDataset secondConstantROIMethod(SuperModel sm, 
												   ExampleModel model,
												   IDataset input,  
												   DataModel dm, 
												   int selection,
												   int trackingMarker,
												   int k){		
		

		
		OperationData outputOD4 = null;
		
		if(model.getMethodology() == Methodology.SECOND_BACKGROUND_BOX){
		
			
			
			outputOD4 = SecondConstantBackgroundROIFittingIOp(model, 
															  input, 
															  sm,
															  trackingMarker,
															  k
															  );
		}
		
		else if(model.getMethodology() == Methodology.OVERLAPPING_BACKGROUND_BOX){
			

			
			outputOD4 = OverlappingBackgroundROIFittingIOp(model, 
														   input, 
														   sm,
														   trackingMarker,
														   k);
			if(outputOD4.getAuxData()[3] != null){
				sm.setBoxOffsetLenPt((int[][]) outputOD4.getAuxData()[3]);
			}
			
		}
		
		IDataset output = outputOD4.getData();

		if ((IROI) outputOD4.getAuxData()[1] != null){
			IRectangularROI bounds = ((IROI) outputOD4.getAuxData()[1]).getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();
		
			if (Arrays.equals(len,new int[] {50, 50}) == false || 
					Arrays.equals(pt,new int[] {10, 10}) == false){
			
				sm.setBackgroundROI((IROI) outputOD4.getAuxData()[1]);
				dm.setBackgroundROI((IROI) outputOD4.getAuxData()[1]);;
			}
		}
		
		
		sm.setTemporaryBackgroundHolder((IDataset) outputOD4.getAuxData()[2]);
		
		
		return output;
	}
	
	public static IDataset secondConstantROIMethod(IDataset input,
												   DirectoryModel drm,
												   AnalaysisMethodologies.Methodology am, 
												   int selection,
												   int trackingMarker,
												   int k){		
		
		
		
		OperationData outputOD4 = null;
		
		int datNo = drm.getFms().get(k).getDatNo();
		
		if(am == Methodology.SECOND_BACKGROUND_BOX){
		
		
		
			outputOD4 = SecondConstantBackgroundROIFittingIOp(input,
															  drm.getLocationList().get(datNo).get(selection),
															  drm.getInitialLenPt(),
															  drm.getLenPtForEachDat()[datNo],
															  drm.getBackgroundLenPt(),
															  drm.getFms().get(k).getFitPower(),
															  drm.getFms().get(k).getBoundaryBox(),
															  trackingMarker,
															  k);
		}
		
		else if(am == Methodology.OVERLAPPING_BACKGROUND_BOX){
		
		
		
			outputOD4 = OverlappingBackgroundROIFittingIOp(input,
					  									   drm.getLocationList().get(datNo).get(selection),
					  									   drm.getInitialLenPt(),
					  									   drm.getLenPtForEachDat()[datNo],
					  									   drm.getBackgroundLenPt(),
					  									   drm.getBoxOffsetLenPt(),
					  									   drm.getPermanentBoxOffsetLenPt(),
					  									   drm.isTrackerOn(),
					  									   drm.getFms().get(k).getFitPower(),
					  									   drm.getFms().get(k).getBoundaryBox(),
					  									   trackingMarker,
					  									   k);
			
			if(outputOD4.getAuxData()[3] != null){
				drm.setBoxOffsetLenPt((int[][]) outputOD4.getAuxData()[3]);
			}
			
		}
		
		IDataset output = outputOD4.getData();
		
		if ((IROI) outputOD4.getAuxData()[1] != null){
			IRectangularROI bounds = ((IROI) outputOD4.getAuxData()[1]).getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();
			
			if (Arrays.equals(len,new int[] {50, 50}) == false || 
					Arrays.equals(pt,new int[] {10, 10}) == false){
			
				drm.setBackgroundROI((IROI) outputOD4.getAuxData()[1]);
				drm.getBackgroundROIForEachDat()[drm.getFms().get(k).getDatNo()]=((IROI) outputOD4.getAuxData()[1]);;
			}
		}
			
		
		drm.setTemporaryBackgroundHolder((IDataset) outputOD4.getAuxData()[2]);
		
		
		return output;
	}
	
	
	public static OperationData OverlappingBackgroundROIFittingIOp(ExampleModel model,
				  												   IDataset input,
				  												   SuperModel sm,
				  												   int trackingMarker,
				  												   int k){

		SecondConstantROIBackgroundSubtractionModel scrbm 
			= new SecondConstantROIBackgroundSubtractionModel();
		int[][] a= sm.getInitialLenPt();
		scrbm.setInitialLenPt(a);
		int[][] b= model.getLenPt();
		scrbm.setLenPt(b);
		scrbm.setFitPower(model.getFitPower());
		scrbm.setBoundaryBox(model.getBoundaryBox());
		scrbm.setTrackerOn(sm.getTrackerOn());
		scrbm.setTrackingMarker(trackingMarker);
		
		if(sm.getBoxOffsetLenPt() != null){
			int[][] e = sm.getBoxOffsetLenPt();
			scrbm.setBoxOffsetLenPt(e);
		}
		
		
		
		if (trackingMarker != 3){
			
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			
			if(p[0] != 0 && p[1] != 0){
				scrbm.setLenPt(lenPt);
			}
			
			else{
				scrbm.setLenPt(sm.getInitialLenPt());
			}
			
			if(sm.getPermanentBoxOffsetLenPt()!= null){
				int[][] c = sm.getPermanentBoxOffsetLenPt();
				scrbm.setBoxOffsetLenPt(c);
			}
			
		}
		else{
			scrbm.setLenPt(sm.getInitialLenPt());
		}
		
		int[][]  d= sm.getBackgroundLenPt();
		scrbm.setBackgroundLenPt(d);
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		OverlappingBgBoxUsingIOperation obbio 
			= new OverlappingBgBoxUsingIOperation();
		
		obbio.setModel(scrbm);
		
		return obbio.execute(input, null);
	
	}
	
	public static OperationData OverlappingBackgroundROIFittingIOp(IDataset input,
																   double[] p, //  = sm.getLocationList().get(k);
																   int[][] initialLenPt, // sm.getInitialLenPt()
																   int[][] mLenPt, //model.getLenPt()
																   int[][] backgroundLenPt,  //sm.getBackgroundLenPt()
																   int[][] boxOffsetLenPt, //sm.getBoxOffsetLenPt()
																   int[][] permanentBoxOffsetLenPt, //sm.getPermanentBoxOffsetLenPt
																   boolean trackerOn,
																   FitPower fp,
																   int boundaryBox,
																   int trackingMarker,
																   int k){
		
		SecondConstantROIBackgroundSubtractionModel scrbm 
				= new SecondConstantROIBackgroundSubtractionModel();
		int[][] a= initialLenPt;
		scrbm.setInitialLenPt(a);
		int[][] b= mLenPt;
		scrbm.setLenPt(b);
		scrbm.setFitPower(fp);
		scrbm.setBoundaryBox(boundaryBox);
		scrbm.setTrackerOn(trackerOn);
		scrbm.setTrackingMarker(trackingMarker);
		
		if(boxOffsetLenPt != null){
			int[][] e = boxOffsetLenPt;
			scrbm.setBoxOffsetLenPt(e);
		}
		
		
		
		if (trackingMarker != 3){
		
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = initialLenPt[0]; 
			int[][] lenPt = new int[][] {len,pt};
			
			if(p[0] != 0 && p[1] != 0){
				scrbm.setLenPt(lenPt);
			}
		
			else{
				scrbm.setLenPt(initialLenPt);
			}
		
			if(permanentBoxOffsetLenPt != null){
				int[][] c = permanentBoxOffsetLenPt;
				scrbm.setBoxOffsetLenPt(c);
			}
		}
		
		else{
				scrbm.setLenPt(initialLenPt);
		}
		
		int[][]  d= backgroundLenPt;
		scrbm.setBackgroundLenPt(d);
		
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		OverlappingBgBoxUsingIOperation obbio 
			= new OverlappingBgBoxUsingIOperation();
		
		obbio.setModel(scrbm);
		
		return obbio.execute(input, null);
		
	}
	
	
	
	public static Dataset correctionMethod(ExampleModel model, 
										   SuperModel sm, 
										   GeometricParametersModel gm, 
										   int k,
										   int selection,
										   IDataset output,
										   IDataset input){
		
		double correction = 0.001;
		int correctionSelector = MethodSetting.toInt(sm.getCorrectionSelection());
		
		yValue = DatasetFactory.zeros(new int[] {1}, Dataset.ARRAYFLOAT64);
		
		if (correctionSelector == 0){
			
			try {
				
				double lorentz = SXRDGeometricCorrections.lorentz(model).getDouble(k);
				sm.addLorentz(sm.getImages().length, k, lorentz);
				sm.setCurrentLorentzCorrection(lorentz);
				
				double areaCorrection = SXRDGeometricCorrections.areacor(model
						, gm.getBeamCorrection(), gm.getSpecular(),  gm.getSampleSize()
						, gm.getOutPlaneSlits(), gm.getInPlaneSlits(), gm.getBeamInPlane()
						, gm.getBeamOutPlane(), gm.getDetectorSlits()).getDouble(k);
				sm.addAreaCorrection(sm.getImages().length, k, areaCorrection);
				sm.setCurrentAreaCorrection(areaCorrection);
				
				double polarisation = SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation()
						, gm.getOutplanePolarisation()).getDouble(k);
				sm.addPolarisation(sm.getImages().length, k, polarisation);
				sm.setCurrentPolarisationCorrection(polarisation);
				
				correction = lorentz* polarisation * areaCorrection;

				if (correction ==0){
					correction = 0.001;
				}
			} catch (DatasetException e) {
	
			}
			
			yValue = Maths.multiply(output, correction);
			
		}
		
		else if (correctionSelector ==1){

			try {
				correction = GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint());
				
				double ref = 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrectionsDouble(gm.getFluxPath(), 
																						 model.getQdcdDat().getDouble(k), 
																						 model);
				
				correction = Math.multiplyExact((long)correction, (long)ref);
				
				sm.addReflectivityAreaCorrection(sm.getImages().length, k, correction);
				sm.addReflectivityFluxCorrection(sm.getImages().length, k, ref);
				
				sm.setCurrentReflectivityAreaCorrection(correction);
				sm.setCurrentReflectivityFluxCorrection(ref);
				
				if (correction ==0){
					correction = 0.001;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			yValue = Maths.multiply(output, correction);
		}
		
		
		else if (correctionSelector ==2){

			try {
				correction = (GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				
				sm.addReflectivityAreaCorrection(sm.getImages().length, k, correction);
				sm.setCurrentReflectivityAreaCorrection(correction);
				
				
				if (correction ==0){
					correction = 0.001;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			yValue = Maths.multiply(output, correction);

		}
		
		else if (correctionSelector ==3){

			yValue = Maths.multiply(output, 1);

		}
		
		else{	
		}
	
		Double intensity = (Double) DatasetUtils.cast(output,Dataset.FLOAT64).sum();
		
		sm.setCurrentRawIntensity(intensity);
		sm.addYListRawIntensity(sm.getNumberOfImages(),
								selection,
								intensity);
		
		
		return yValue;
	}
	
	
	public static Dataset correctionMethod(FrameModel fm,
										   IDataset output){

		double correction = 0.001;
		int correctionSelector = MethodSetting.toInt(fm.getCorrectionSelection());
		
		yValue = DatasetFactory.zeros(new int[] {1}, Dataset.ARRAYFLOAT64);
		
		if (correctionSelector == 0){
		
//		try {
		
			double lorentz = fm.getLorentzianCorrection();
			
//			SXRDGeometricCorrections.lorentz(model).getDouble(k);
//			sm.addLorentz(sm.getImages().length, k, lorentz);
//			sm.setCurrentLorentzCorrection(lorentz);
//			
			double areaCorrection = fm.getAreaCorrection(); 
			
//			SXRDGeometricCorrections.areacor(model
//			, gm.getBeamCorrection(), gm.getSpecular(),  gm.getSampleSize()
//			, gm.getOutPlaneSlits(), gm.getInPlaneSlits(), gm.getBeamInPlane()
//			, gm.getBeamOutPlane(), gm.getDetectorSlits()).getDouble(k);
//			sm.addAreaCorrection(sm.getImages().length, k, areaCorrection);
//			sm.setCurrentAreaCorrection(areaCorrection);
//			
			double polarisation = fm.getPolarisationCorrection();
			
//			SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation()
//			, gm.getOutplanePolarisation()).getDouble(k);
//			sm.addPolarisation(sm.getImages().length, k, polarisation);
//			sm.setCurrentPolarisationCorrection(polarisation);
			
			correction = lorentz* polarisation * areaCorrection;
			
			if (correction ==0){
				correction = 0.001;
			}
//		} 
//		catch (DatasetException e) {
//		
//		}
		
		yValue = Maths.multiply(output, correction);
		
		}
		
//		else if (correctionSelector ==1){
		
//		try {
//			correction = GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, gm.getAngularFudgeFactor(), 
//			gm.getBeamHeight(), gm.getFootprint());
//			
//			double ref = 
//			ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrectionsDouble(gm.getFluxPath(), 
//																		 model.getQdcdDat().getDouble(k), 
//																		 model);
//			
//			correction = Math.multiplyExact((long)correction, (long)ref);
//			
//			sm.addReflectivityAreaCorrection(sm.getImages().length, k, correction);
//			sm.addReflectivityFluxCorrection(sm.getImages().length, k, ref);
//			
//			sm.setCurrentReflectivityAreaCorrection(correction);
//			sm.setCurrentReflectivityFluxCorrection(ref);
//			
//			if (correction ==0){
//				correction = 0.001;
//			}
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		yValue = Maths.multiply(output, correction);
//		}
//		
//		
//		else if (correctionSelector ==2){
//		
//		try {
//		
//			correction = (GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, gm.getAngularFudgeFactor(), 
//			gm.getBeamHeight(), gm.getFootprint()));
//			
//			sm.addReflectivityAreaCorrection(sm.getImages().length, k, correction);
//			sm.setCurrentReflectivityAreaCorrection(correction);
//			
//		
//			if (correction ==0){
//				correction = 0.001;
//			}
//		} 
//		
//		catch (Exception e) {
//		// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		yValue = Maths.multiply(output, correction);
//		
//		}
//		
//		else if (correctionSelector ==3){
//		
//		yValue = Maths.multiply(output, 1);
//		
//		}
//		
//		else{	
//		}
//		
//		Double intensity = (Double) DatasetUtils.cast(output,Dataset.FLOAT64).sum();
//		
//		sm.setCurrentRawIntensity(intensity);
//		sm.addYListRawIntensity(sm.getNumberOfImages(),
//			selection,
//			intensity);
		
		
		return yValue;
		
	}
	
	
	
	
	
	private static void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
	
}
