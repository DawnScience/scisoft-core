package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.widgets.Composite;

public class DummyProcessingClass {
	
	
	@SuppressWarnings("incomplete-switch")
	public static IDataset DummyProcess(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
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
				TwoDTracking twoDTracking = new TwoDTracking();
				output = twoDTracking.TwoDTracking1(sm, input, model, dm, trackingMarker, k, selection);
				break;
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
				output = TwoDFitting.TwoDFitting1(input, model);
				break;
			case SECOND_BACKGROUND_BOX:
				output = SecondConstantROI.secondROIConstantBg(input, model, pS, dm);
				break;
			case OVERLAPPING_BACKGROUND_BOX:
				output = OverlappingBackgroundBox.OverlappingBgBox(input, model, pS, dm);
				break;
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
			} catch (DatasetException e) {
	
			}
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		
		dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
		dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
		dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
		
		return output;
	}
	
	@SuppressWarnings("incomplete-switch")
	public static IDataset DummyProcess0(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
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
				TwoDTracking twoDTracking = new TwoDTracking();
				output = twoDTracking.TwoDTracking1(sm, input, model, dm, trackingMarker, k,selection);
				break;
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
				output = TwoDFitting.TwoDFitting1(input, model);
				break;
			case SECOND_BACKGROUND_BOX:
				output = SecondConstantROI.secondROIConstantBg(input, model, pS, dm);
				break;
			case OVERLAPPING_BACKGROUND_BOX:
				output = OverlappingBackgroundBox.OverlappingBgBox(input, model, pS, dm);
				break;
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
			} catch (DatasetException e) {
	
			}
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
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
	
	
	
	@SuppressWarnings("incomplete-switch")
	public static IDataset DummyProcess1(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
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
				TwoDTracking twoDTracking = new TwoDTracking();
				output = twoDTracking.TwoDTracking1(sm, input, model, dm, trackingMarker, k, selection);
				break;
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
				output = TwoDFitting.TwoDFitting1(input, model);
				break;
			case SECOND_BACKGROUND_BOX:
				output = SecondConstantROI.secondROIConstantBg(input, model, pS, dm);
				break;
			case OVERLAPPING_BACKGROUND_BOX:
				output = OverlappingBackgroundBox.OverlappingBgBox(input, model, pS, dm);
				break;
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
			} catch (DatasetException e) {
	
			}
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		
		dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
		dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
		dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
		
		return output;
	}
	
	
	@SuppressWarnings("incomplete-switch")
	public static IDataset DummyProcess1(SuperModel sm, 
										IDataset input, 
										ExampleModel model, 
										DataModel dm, 
										GeometricParametersModel gm, 
										IPlottingSystem<Composite> pS, 
										int correctionSelector, 
										int k, 
										int trackingMarker,
										int selection,
										double[]locationList){		
		
		IDataset output =null;	
		
		
		
		switch(model.getMethodology()){
			case TWOD_TRACKING:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}				
				TwoDTracking2 twoDTracking = new TwoDTracking2();
				output = twoDTracking.TwoDTracking1(input, 
													model,
													sm,
													dm, 
													trackingMarker, 
													k,
													locationList,
													selection);
				break;
			case TWOD:
				if (pS.getRegion("Background Region")!=null){
					pS.removeRegion(pS.getRegion("Background Region"));
				}
				else{
				}
				output = TwoDFitting.TwoDFitting1(input, model);
				break;
			case SECOND_BACKGROUND_BOX:
				output = SecondConstantROI.secondROIConstantBg(input, model, pS, dm);
				break;
			case OVERLAPPING_BACKGROUND_BOX:
				output = OverlappingBackgroundBox.OverlappingBgBox(input, model, pS, dm);
				break;
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
			} catch (DatasetException e) {
	
			}
		}
		
		else if (correctionSelector ==1){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				correction = Maths.multiply(correction, 
						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), model.getQdcdDat().getDouble(k), model));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else{
			
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
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
	
	
}
