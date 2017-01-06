package org.dawnsci.surfacescatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
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
import org.eclipse.january.metadata.Metadata;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class DummyProcessingClass {
	
	
	private static Dataset yValue;
	
	public static IDataset DummyProcess(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS,
										IPlottingSystem<Composite> ssvsPS,
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection){		
		
		IDataset output =null;	
		
		switch(model.getMethodology()){
			case TWOD_TRACKING:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}				
//				TwoDTracking twoDTracking = new TwoDTracking();
//				output = twoDTracking.TwoDTracking1(sm, input, model, dm, trackingMarker, k, selection);
				
				AgnosticTrackerHandler ath = new AgnosticTrackerHandler();
				
				ath.TwoDTracking3(sm, 
								  input, 
								  model, 
								  dm, 
								  trackingMarker, 
								  k, 
								  selection);
				
				OperationData outputOD= TwoDFittingIOp(model,
													   input,
													   sm);
				output = outputOD.getData();
				
				double[] loc =  (double[]) outputOD.getAuxData()[0];
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[1];
				
				sm.addLocationList(selection,loc);
				sm.setTemporaryBackgroundHolder(temporaryBackground);
				
				
				break;
				
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
				
				OperationData outputOD1= TwoDFittingIOp(model,
						   input,
						   sm);
				output = outputOD1.getData();
				
				double[] loc1 =  (double[]) outputOD1.getAuxData()[0];
				sm.addLocationList(selection,loc1);	
				
				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[1];
				
				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
				
			case SECOND_BACKGROUND_BOX:
				
				output = secondConstantROIMethod(sm,
												 model,
										 		 input,  
										 		 dm, 
										 		 pS,
										 		 ssvsPS,
										 		 selection);	
				
				
				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				output = OverlappingBackgroundBox.OverlappingBgBox(input, 
																   model, 
																   sm, 
																   pS, 
																   ssvsPS,
																   selection);
				break;
				
			case X:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
						
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X);
				output = outputOD2.getData();
				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
				sm.addLocationList(selection,loc2);
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);
								
				break;
				
			case Y:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y);
				output = outputOD3.getData();
				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
				sm.addLocationList(selection,loc3);
				
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
		
				break;
		}
		
		IndexIterator it1 = ((Dataset) output).getIterator();
		
		while (it1.hasNext()) {
			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
			if (q <= 0)
				((Dataset) output).setObjectAbs(it1.index, 0.1);
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
		
		
		Dataset correction = DatasetFactory.zeros(new int[] {1}, Dataset.FLOAT64);
		
		if (correctionSelector == 0){
			
			try {
				correction = Maths.multiply(SXRDGeometricCorrections.lorentz(model), SXRDGeometricCorrections.areacor(model
						, gm.getBeamCorrection(), gm.getSpecular(),  gm.getSampleSize()
						, gm.getOutPlaneSlits(), gm.getInPlaneSlits(), gm.getBeamInPlane()
						, gm.getBeamOutPlane(), gm.getDetectorSlits()));
				correction = Maths.multiply(SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation()
						, gm.getOutplanePolarisation()), correction);
				correction = Maths.multiply(
						SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation(), gm.getOutplanePolarisation()),
						correction);
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
			} catch (DatasetException e) {
	
			}
			
			yValue = Maths.multiply(output, correction.getDouble(k));
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				
				Dataset ref = 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), 
																						 model.getQdcdDat().getDouble(k), 
																						 model);
				
				correction = Maths.multiply(correction, ref);
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			yValue = Maths.multiply(output, correction.getDouble(0));
//			double normalisation  = 1/output.getDouble(0);
//			yValue = Maths.multiply(normalisation, yValue);
		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		
		dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
		dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
		dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
		
		
		
		return output;
	}
	
	public static IDataset DummyProcess0(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
										IPlottingSystem<Composite> ssvsPS,
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection){		
		
		IDataset output =null;	
		
		
		
		switch(model.getMethodology()){
			case TWOD_TRACKING:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}				
//				TwoDTracking twoDTracking = new TwoDTracking();
//				output = twoDTracking.TwoDTracking1(sm, input, model, dm, trackingMarker, k,selection);
				
				AgnosticTrackerHandler ath = new AgnosticTrackerHandler();
				
				ath.TwoDTracking3(sm,
								  input, 
								  model,
								  dm, 
								  trackingMarker, 
								  k,
								  selection);
				

				OperationData outputOD= TwoDFittingIOp(model,
													   input,
													   sm);
				output = outputOD.getData();
				
				double[] loc =  (double[]) outputOD.getAuxData()[0];
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[1];
				
				sm.addLocationList(selection,loc);
				sm.setTemporaryBackgroundHolder(temporaryBackground);
				
				
				break;
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
//				output = TwoDFitting.TwoDFitting1(input,
//						  						  model,
//						  						  sm,
//						  						  selection);
				
				OperationData outputOD1= TwoDFittingIOp(model,
						   input,
						   sm);
				output = outputOD1.getData();
				double[] loc1 =  (double[]) outputOD1.getAuxData()[0];
				sm.addLocationList(selection,loc1);	
				
				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[1];

				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				
				break;
			case SECOND_BACKGROUND_BOX:

				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection);	


				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				output = OverlappingBackgroundBox.OverlappingBgBox(input, 
																   model, 
																   sm, 
																   pS, 
																   ssvsPS,
																   selection);
				break;
		
			case X:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
													
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X);
				output = outputOD2.getData();
				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
				sm.addLocationList(selection,loc2);
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);

				break;
											
			case Y:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
											
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y);
				output = outputOD3.getData();
				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
				sm.addLocationList(selection,loc3);
									
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
				
				break;
		}
	
				
		IndexIterator it1 = ((Dataset) output).getIterator();
		
		while (it1.hasNext()) {
			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
			if (q <= 0)
				((Dataset) output).setObjectAbs(it1.index, 0.1);
		}
		
		Dataset correction = DatasetFactory.zeros(new int[] {1}, Dataset.FLOAT64);
		
		if (correctionSelector == 0){
			
			try {
				correction = Maths.multiply(SXRDGeometricCorrections.lorentz(model), SXRDGeometricCorrections.areacor(model
						, gm.getBeamCorrection(), gm.getSpecular(),  gm.getSampleSize()
						, gm.getOutPlaneSlits(), gm.getInPlaneSlits(), gm.getBeamInPlane()
						, gm.getBeamOutPlane(), gm.getDetectorSlits()));
				correction = Maths.multiply(SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation()
						, gm.getOutplanePolarisation()), correction);
				correction = Maths.multiply(
						SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation(), gm.getOutplanePolarisation()),
						correction);
				
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
			} catch (DatasetException e) {
	
			}
			
			yValue = Maths.multiply(output, correction.getDouble(k));
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			yValue = Maths.multiply(output, correction.getDouble(0));
//			double normalisation  = 1/output.getDouble(0);
//			yValue = Maths.multiply(normalisation, yValue);
		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		

		
		
		dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
		dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
		dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
		
		sm.yListReset();
		
		sm.addyList(sm.getImages().length, selection ,intensity);
		sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
		sm.addOutputDatArray(sm.getImages().length, selection ,output);
		
		
		return output;
	}
	
	
	
	public static IDataset DummyProcess1(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
										IPlottingSystem<Composite> ssvsPS,
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection){		
		
		IDataset output =null;	
		
		switch(model.getMethodology()){
			case TWOD_TRACKING:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}				
//				TwoDTracking twoDTracking = new TwoDTracking();
//				output = twoDTracking.TwoDTracking1(sm, input, model, dm, trackingMarker, k, selection);
				
				AgnosticTrackerHandler ath = new AgnosticTrackerHandler();
							
				ath.TwoDTracking3(sm, 
								  input, 
								  model, 
								  dm, 
								  trackingMarker, 
								  k, 
								  selection);
				
				OperationData outputOD= TwoDFittingIOp(model,
													   input,
													   sm);
				output = outputOD.getData();
				
				double[] loc =  (double[]) outputOD.getAuxData()[0];
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[1];
				
				sm.addLocationList(selection,loc);
				sm.setTemporaryBackgroundHolder(temporaryBackground);
				
				break;
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
				
				OperationData outputOD1= TwoDFittingIOp(model,
						   input,
						   sm);
				
				

				output = outputOD1.getData();
				double[] loc1 =  (double[]) outputOD1.getAuxData()[0];
				sm.addLocationList(selection,loc1);		

				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[1];
				
				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
			case SECOND_BACKGROUND_BOX:

				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection);	


				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				output = OverlappingBackgroundBox.OverlappingBgBox(input, 
																   model, 
																   sm, 
																   pS, 
																   ssvsPS,
																   selection);
				break;
				
			case X:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
													
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X);
				output = outputOD2.getData();
				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
				sm.addLocationList(selection,loc2);
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);
									
				break;
											
			case Y:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
											
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y);
				output = outputOD3.getData();
				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
				sm.addLocationList(selection,loc3);
				
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
									
				break;
		}
		
		IndexIterator it1 = ((Dataset) output).getIterator();
		
		while (it1.hasNext()) {
			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
			if (q <= 0)
				((Dataset) output).setObjectAbs(it1.index, 0.1);
		}
		
		Dataset correction = DatasetFactory.zeros(new int[] {1}, Dataset.FLOAT64);
		
		if (correctionSelector == 0){
			
			try {
				correction = Maths.multiply(SXRDGeometricCorrections.lorentz(model), SXRDGeometricCorrections.areacor(model
						, gm.getBeamCorrection(), gm.getSpecular(),  gm.getSampleSize()
						, gm.getOutPlaneSlits(), gm.getInPlaneSlits(), gm.getBeamInPlane()
						, gm.getBeamOutPlane(), gm.getDetectorSlits()));
				correction = Maths.multiply(SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation()
						, gm.getOutplanePolarisation()), correction);
				correction = Maths.multiply(
						SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation(), gm.getOutplanePolarisation()),
						correction);
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
			} catch (DatasetException e) {
	
			}
			yValue = Maths.multiply(output, correction.getDouble(k));
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			double normalisation  = 1/output.getDouble(0);
			
			yValue = Maths.multiply(output, correction.getDouble(0));
//			yValue = Maths.multiply(normalisation, yValue);
		}
		else{
			
			
			
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		
		dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
		dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
		dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
		
		return output;
	}
	
	
	public static IDataset DummyProcess1(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
										IPlottingSystem<Composite> ssvsPS,
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection,
										double[]locationList){		
		////////////////////////////////NB selection is position in the sorted list of the whole rod k is position in the .dat file
		IDataset output =null;	
		
		
		
		switch(model.getMethodology()){
			case TWOD_TRACKING:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}				
//				TwoDTracking2 twoDTracking = new TwoDTracking2();
				AgnosticTrackerHandler ath = new AgnosticTrackerHandler();
				
				ath.TwoDTracking1(input, 
						model,
						sm,
						dm, 
						trackingMarker, 
						k,
						locationList,
						selection);
				

				OperationData outputOD= TwoDFittingIOp(model,
													   input,

													   
													   sm);
				output = outputOD.getData();
				double[] loc =  (double[]) outputOD.getAuxData()[0];
				sm.addLocationList(selection,loc);
				
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[1];
				
				sm.setTemporaryBackgroundHolder(temporaryBackground);

				break;
				
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}

				
				
				OperationData outputOD1= TwoDFittingIOp(model,
													   input,
													   sm);
				output = outputOD1.getData();
				double[] loc1 = (double[]) outputOD1.getAuxData()[0] ;
				sm.addLocationList(selection,loc1);	
				
				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[1];
				
			
				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
			case SECOND_BACKGROUND_BOX:

				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection);	


				
				break;
			case OVERLAPPING_BACKGROUND_BOX:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				output = OverlappingBackgroundBox.OverlappingBgBox(input, 
																   model, 
																   sm, 
																   pS, 
																   ssvsPS,
																   selection);
				break;
				
			case X:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
													
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X);
				output = outputOD2.getData();
				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
				sm.addLocationList(selection,loc2);
											
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);
				
											
				break;
											
			case Y:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
											
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y);
				output = outputOD3.getData();
				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
				sm.addLocationList(selection,loc3);
									
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
				break;
		}
		
		IndexIterator it1 = ((Dataset) output).getIterator();
		
		while (it1.hasNext()) {
			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
			if (q <= 0)
				((Dataset) output).setObjectAbs(it1.index, 0.1);
		}
		
		
		Dataset correction = DatasetFactory.zeros(new int[] {1}, Dataset.FLOAT64);
		if (correctionSelector == 0){
					
			try {
				correction = Maths.multiply(SXRDGeometricCorrections.lorentz(model), SXRDGeometricCorrections.areacor(model
						, gm.getBeamCorrection(), gm.getSpecular(),  gm.getSampleSize()
						, gm.getOutPlaneSlits(), gm.getInPlaneSlits(), gm.getBeamInPlane()
						, gm.getBeamOutPlane(), gm.getDetectorSlits()));
				correction = Maths.multiply(SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation()
						, gm.getOutplanePolarisation()), correction);
				correction = Maths.multiply(
						SXRDGeometricCorrections.polarisation(model, gm.getInplanePolarisation(), gm.getOutplanePolarisation()),
						correction);
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
			} catch (DatasetException e) {
	
			}
			yValue = Maths.multiply(output, correction.getDouble(k));
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
				if (correction.getDouble(0) ==0){
					correction.set(0.001, 0);
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			yValue = Maths.multiply(output, correction.getDouble(0));

		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
				
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		
		dm.addyList(model.getDatImages().getShape()[0], k, intensity);
		dm.addyListFhkl(model.getDatImages().getShape()[0], k, fhkl);
		dm.addOutputDatArray(model.getDatImages().getShape()[0], k,output);
		
		sm.yListReset();
		
		sm.addyList(sm.getImages().length, selection ,intensity);
		sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
		sm.addOutputDatArray(sm.getImages().length, selection ,output);
		
		return output;
	}
	
	
	public static OperationData TwoDFittingIOp(ExampleModel model,
										IDataset input,
										SuperModel sm){
		
		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(sm.getInitialLenPt());
		tdfm.setLenPt(model.getLenPt());
		tdfm.setFitPower(model.getFitPower());
		tdfm.setBoundaryBox(model.getBoundaryBox());
		
		Metadata md = new Metadata();
		IDataset dummyMD = DatasetFactory.zeros(new int [] {2,2});
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
		
//		return outputOD.getData();
		
	}
	
	
	public static OperationData OneDFittingIOp(ExampleModel model,
											   IDataset input,
					                           SuperModel sm,
					                           AnalaysisMethodologies.Methodology am){
		
		OneDFittingModel odfm = new OneDFittingModel();
		odfm.setInitialLenPt(sm.getInitialLenPt());
		odfm.setLenPt(model.getLenPt());
		odfm.setFitPower(model.getFitPower());
		odfm.setBoundaryBox(model.getBoundaryBox());
		odfm.setDirection(am);
		
		Metadata md = new Metadata();
		IDataset dummyMD = DatasetFactory.zeros(new int [] {2,2});
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
			   														  IPlottingSystem<Composite> pS,
			   														  IPlottingSystem<Composite> ssvsPS){

		SecondConstantROIBackgroundSubtractionModel scrbm 
					= new SecondConstantROIBackgroundSubtractionModel();
		scrbm.setInitialLenPt(sm.getInitialLenPt());
		scrbm.setLenPt(model.getLenPt());
		scrbm.setFitPower(model.getFitPower());
		scrbm.setBoundaryBox(model.getBoundaryBox());
		scrbm.setPlottingSystem(pS);
		scrbm.setSPlottingSystem(ssvsPS);
		
		if (sm.getBackgroundROI() != null){
			IRectangularROI bounds = sm.getBackgroundROI().getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();
		
			if (Arrays.equals(len,new int[] {50, 50}) == false || 
					Arrays.equals(pt,new int[] {10, 10}) == false){
			
				scrbm.setBackgroundROI(sm.getBackgroundROI().getBounds());
			}
		}
		
		Metadata md = new Metadata();
		IDataset dummyMD = DatasetFactory.zeros(new int [] {2,2});
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
											IPlottingSystem<Composite> pS,
											IPlottingSystem<Composite> ssvsPS,
											int selection){		
		
		Display display = Display.getCurrent();
        Color magenta = display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
		
		if (pS.getRegion("Background Region")!=null){
			IRectangularROI bounds = pS.getRegion("Background Region").getROI().getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();
		
			if (Arrays.equals(len,new int[] {50, 50}) == false || 
					Arrays.equals(pt,new int[] {10, 10}) == false){
			
				sm.setBackgroundROI((IROI) pS.getRegion("Background Region").getROI());
				dm.setBackgroundROI((IROI) pS.getRegion("Background Region").getROI());;
			}
			
			pS.getRegion("Background Region").setRegionColor(magenta);
			
		}
		
		if (ssvsPS.getRegion("ssvs Background Region")!=null){
			
			IRectangularROI bounds = ssvsPS.getRegion("ssvs Background Region").getROI().getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();
		
			if (Arrays.equals(len,new int[] {50, 50}) == false || 
					Arrays.equals(pt,new int[] {10, 10}) == false){
			
				sm.setBackgroundROI((IROI) ssvsPS.getRegion("ssvs Background Region").getROI());
				dm.setBackgroundROI((IROI) ssvsPS.getRegion("ssvs Background Region").getROI());;
			}
			

			ssvsPS.getRegion("ssvs Background Region").setRegionColor(magenta);
			
		}			
		
		OperationData outputOD4 = SecondConstantBackgroundROIFittingIOp(model, 
																		input, 
																		sm,
																		pS,
																		ssvsPS);

		IDataset output = outputOD4.getData();
		double[] loc4 =  (double[]) outputOD4.getAuxData()[0];
		sm.addLocationList(selection,loc4);

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

}
