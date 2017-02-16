package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import org.eclipse.january.dataset.DoubleDataset;
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
	private static int DEBUG = 0;
	
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
								
				
				AgnosticTrackerHandler ath = new AgnosticTrackerHandler();
				if(trackingMarker != 3){
					ath.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				OperationData outputOD= TwoDFittingIOp(model,
													   input,
													   sm,
													   selection,
													   trackingMarker);
				output = outputOD.getData();
				
				IDataset temporaryBackground = DatasetFactory.ones(new int[] {1});
				
				try{
					temporaryBackground =  (IDataset) outputOD.getAuxData()[0];
				}
				catch(Exception f){
//					DoubleDataset temporaryBackground1 =  (DoubleDataset) outputOD.getAuxData()[0];
//					temporaryBackground = (IDataset) temporaryBackground1;
				}
				
				sm.setTemporaryBackgroundHolder(temporaryBackground);
				
				break;
				
			case TWOD:
				
				
				
				int[] len = sm.getInitialLenPt()[0];
				int[] pt = sm.getInitialLenPt()[1];
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				else{	
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
					(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				OperationData outputOD1= TwoDFittingIOp(model,
						   input,
						   sm,
						   selection,
						   trackingMarker);
				output = outputOD1.getData();
				
//				double[] loc1 =  (double[]) outputOD1.getAuxData()[0];
//				sm.addLocationList(selection,loc1);	
////				
				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[0];
				
				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
				
			case SECOND_BACKGROUND_BOX:
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
					(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				output = secondConstantROIMethod(sm,
												 model,
										 		 input,  
										 		 dm, 
										 		 pS,
										 		 ssvsPS,
										 		 selection,
										 		 trackingMarker,
										 		 k);	
				
				
				
				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}

				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				

				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		 trackingMarker,
				 		 k);	
				
				break;
				
			case X:
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}

				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X,
						   								trackingMarker,
						   								k);
				output = outputOD2.getData();
//				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
//				sm.addLocationList(selection,loc2);
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);
//				
								
				break;
				
			case Y:
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}

				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y,
						   								trackingMarker,
						   								k);
				output = outputOD3.getData();
//				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
//				sm.addLocationList(selection,loc3);
				
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
		
				break;
		}
		
//		IndexIterator it1 = ((Dataset) output).getIterator();
		
//		while (it1.hasNext()) {
//			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
//			if (q <= 0)
//				((Dataset) output).setObjectAbs(it1.index, 0.1);
//		}
		
		if(Arrays.equals(output.getShape(), (new int[] {2,2}))){
			IndexIterator it11 = ((Dataset) output).getIterator();
			
			while (it11.hasNext()) {
				double q = ((Dataset) output).getElementDoubleAbs(it11.index);
				if (q <= 0)
					((Dataset) output).setObjectAbs(it11.index, 0.1);
			}
			return output;
		}
		
		
		yValue = correctionMethod(model, 
								  sm, 
								  gm, 
								  k, 
								  output,
								  input);
		
		
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}		
//				
		
		if (trackingMarker !=3 ){
			dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
			dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
			dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
			
//			sm.yListReset();
			
			sm.addyList(sm.getImages().length, selection ,intensity);
			sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
			sm.addOutputDatArray(sm.getImages().length, selection ,output);
			
			
//			debug("  intensity:  " + intensity + "   k: " + k);
		}
		
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
													   sm,
													   selection,
													   trackingMarker);
				output = outputOD.getData();
				
//				double[] loc =  (double[]) outputOD.getAuxData()[0];
//				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[0];
				IDataset temporaryBackground = DatasetFactory.ones(new int[] {1});
				
//				try{
					temporaryBackground =  (IDataset) outputOD.getAuxData()[0];
//				}
//				catch(Exception f){
//					DoubleDataset temporaryBackground1 =  (DoubleDataset) outputOD.getAuxData()[0];
//					temporaryBackground = (IDataset) temporaryBackground1;
//				}
				
				
				
				
//				sm.addLocationList(selection,loc);
				sm.setTemporaryBackgroundHolder(temporaryBackground);
				
				
				break;
			case TWOD:
				
//				output = TwoDFitting.TwoDFitting1(input,
//						  						  model,
//						  						  sm,
//						  						  selection);
				
				
				int[] len = sm.getInitialLenPt()[0];
				int[] pt = sm.getInitialLenPt()[1];
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
				}
				
				else{	
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
					(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				
				
				
//					
//				sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
//						(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
//				
				
				OperationData outputOD1= TwoDFittingIOp(model,
						   input,
						   sm,
						   selection,
						   trackingMarker);
				output = outputOD1.getData();
//				double[] loc1 =  (double[]) outputOD1.getAuxData()[0];
//				sm.addLocationList(selection,loc1);	
				
				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[0];

				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				
				break;
			case SECOND_BACKGROUND_BOX:

				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm,
									  input, 
									  model,
									  dm, 
									  trackingMarker, 
									  k,
									  selection);
			
				}

				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		 trackingMarker,
				 		 k);	


				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
		
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm,
									  input, 
									  model,
									  dm, 
									  trackingMarker, 
									  k,
									  selection);
			
				}

				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}

				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		 trackingMarker,
				 		 k);	
				
				
				break;
		
			case X:
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm,
									  input, 
									  model,
									  dm, 
									  trackingMarker, 
									  k,
									  selection);
			
				}
				
				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X,
						   								trackingMarker,
						   								k);
				output = outputOD2.getData();
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);

				break;
											
			case Y:
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath2 = new AgnosticTrackerHandler();
					
					ath2.TwoDTracking3(sm,
									  input, 
									  model,
									  dm, 
									  trackingMarker, 
									  k,
									  selection);
			
					
				}

				else{
					
					len = sm.getInitialLenPt()[0];
					pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y,
						   								trackingMarker,
						   								k);
				output = outputOD3.getData();
									
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
				
				break;
		}
	
//				
//		IndexIterator it1 = ((Dataset) output).getIterator();
//		
//		while (it1.hasNext()) {
//			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
//			if (q <= 0)
//				((Dataset) output).setObjectAbs(it1.index, 0.1);
//		}
		
		yValue = correctionMethod(model, 
				  sm, 
				  gm, 
				  k, 
				  output,
				  input);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}	

		
		if (trackingMarker !=3 ){
			dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
			dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
			dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
			
//			sm.yListReset();
			
			sm.addyList(sm.getImages().length, selection ,intensity);
			sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
			sm.addOutputDatArray(sm.getImages().length, selection ,output);
			
//			debug("  intensity:  " + intensity + "   k: " + k);
		}
//		debug("intensity added to dm: " + intensity + "   local k: " + k + "   selection: " + selection);
		
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
													   sm,
													   selection,
													   trackingMarker);
				output = outputOD.getData();
				
//				double[] loc =  (double[]) outputOD.getAuxData()[0];
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[0];
				
//				sm.addLocationList(selection,loc);
				sm.setTemporaryBackgroundHolder(temporaryBackground);
				
				break;
				
			case TWOD:
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
					
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				
				OperationData outputOD1= TwoDFittingIOp(model,
						   input,
						   sm,
						   selection,
						   trackingMarker);
				
				

				output = outputOD1.getData();
//				double[] loc1 =  (double[]) outputOD1.getAuxData()[0];
//				sm.addLocationList(selection,loc1);		

				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[0];
				
				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
			case SECOND_BACKGROUND_BOX:

				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);
					
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[]  pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		 trackingMarker,
				 		 k);	


				
				break;
				
			case OVERLAPPING_BACKGROUND_BOX:
				
				
//				output = OverlappingBackgroundBox.OverlappingBgBox(input, 
//																   model, 
//																   sm, 
//																   pS, 
//																   ssvsPS,
//																   selection);
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  selection, 
									  selection);
					
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		 trackingMarker,
				 		 k);	
				
				break;
				
			case X:
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection
									  );
					
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X,
						   								trackingMarker,
						   								k);
				output = outputOD2.getData();
//				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
//				sm.addLocationList(selection,loc2);
				
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);
									
				break;
											
			case Y:
				
				

				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking3(sm, 
									  input, 
									  model, 
									  dm, 
									  trackingMarker, 
									  k, 
									  selection);	
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y,
						   								trackingMarker,
						   								k);
				output = outputOD3.getData();
//				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
//				sm.addLocationList(selection,loc3);
				
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
									
				break;
		}
		
//		IndexIterator it1 = ((Dataset) output).getIterator();
//		
//		while (it1.hasNext()) {
//			double q = ((Dataset) output).getElementDoubleAbs(it1.index);
//			if (q <= 0)
//				((Dataset) output).setObjectAbs(it1.index, 0.1);
//		}
		
		yValue = correctionMethod(model, 
				  sm, 
				  gm, 
				  k, 
				  output,
				  input);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		Dataset yValue = Maths.multiply(output, correction.getDouble(k));
		
		
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}	
		
		
		if (trackingMarker !=3 ){
			dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
			dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
			dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
			
//			sm.yListReset();
			
			sm.addyList(sm.getImages().length, selection ,intensity);
			sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
			sm.addOutputDatArray(sm.getImages().length, selection ,output);
			
			debug("  intensity:  " + intensity + "   k: " + k);
		}
		
		
		debug("intensity added to dm: " + intensity + "   local k: " + k + "   selection: " + selection);
		
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
													   sm,
													   selection,
													   trackingMarker);
				output = outputOD.getData();
//				double[] loc =  (double[]) outputOD.getAuxData()[0];
//				sm.addLocationList(selection,loc);
				
				IDataset temporaryBackground = (IDataset) outputOD.getAuxData()[0];
				
				sm.setTemporaryBackgroundHolder(temporaryBackground);

				break;
				
			case TWOD:
//				if (pS.getRegion("Background Region")!=null){
//					pS.removeRegion(pS.getRegion("Background Region"));
//				}
//				else{
//				}
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath2 = new AgnosticTrackerHandler();
					
					ath2.TwoDTracking1(input, 
							model,
							sm,
							dm, 
							trackingMarker, 
							k,
							locationList,
							selection);
					


				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD1= TwoDFittingIOp(model,
													   input,
													   sm,
													   selection,
													   trackingMarker);
				output = outputOD1.getData();
//				double[] loc1 = (double[]) outputOD1.getAuxData()[0] ;
//				sm.addLocationList(selection,loc1);	
				
				IDataset temporaryBackground1 = (IDataset) outputOD1.getAuxData()[0];
				
			
				sm.setTemporaryBackgroundHolder(temporaryBackground1);
				
				break;
			case SECOND_BACKGROUND_BOX:

				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking1(input, 
							model,
							sm,
							dm, 
							trackingMarker, 
							k,
							locationList,
							selection);
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
	
				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		 trackingMarker,
				 		 k);	


				
				break;
			case OVERLAPPING_BACKGROUND_BOX:
//				if (pS.getRegion("Background Region")!=null){
//					pS.removeRegion(pS.getRegion("Background Region"));
//				}
//				output = OverlappingBackgroundBox.OverlappingBgBox(input, 
//																   model, 
//																   sm, 
//																   pS, 
//																   ssvsPS,
//																   selection);
//				
//				

				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking1(input, 
							model,
							sm,
							dm, 
							trackingMarker, 
							k,
							locationList,
							selection);
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				output = secondConstantROIMethod(sm,
						 model,
				 		 input,  
				 		 dm, 
				 		 pS,
				 		 ssvsPS,
				 		 selection,
				 		trackingMarker,
				 		k);	
				
				
				break;
				
			case X:
				
				
				
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath1 = new AgnosticTrackerHandler();
					
					ath1.TwoDTracking1(input, 
							model,
							sm,
							dm, 
							trackingMarker, 
							k,
							locationList,
							selection);
					
				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				
				OperationData outputOD2= OneDFittingIOp(model,
						   								input,
						   								sm,
						   								Methodology.X,
						   								trackingMarker,
						   								k);
				output = outputOD2.getData();
//				double[] loc2 =  (double[]) outputOD2.getAuxData()[0];
//				sm.addLocationList(selection,loc2);
											
				IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground2);
				
											
				break;
											
			case Y:
				
			
				if(sm.getTrackerOn()){
					AgnosticTrackerHandler ath2 = new AgnosticTrackerHandler();
					
					ath2.TwoDTracking1(input, 
							model,
							sm,
							dm, 
							trackingMarker, 
							k,
							locationList,
							selection);
					


				}

				else{
					
					int[] len = sm.getInitialLenPt()[0];
					int[] pt = sm.getInitialLenPt()[1];
					
					sm.addLocationList(selection,new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]), (double) (pt[1]),
							(double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]), (double) (pt[1] + len[1])});
				}
				
				OperationData outputOD3= OneDFittingIOp(model,
														input,
														sm,
														Methodology.Y,
														trackingMarker,
														k);
				output = outputOD3.getData();
//				double[] loc3 =  (double[]) outputOD3.getAuxData()[0];
//				sm.addLocationList(selection,loc3);
									
				IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
				sm.setTemporaryBackgroundHolder(temporaryBackground3);
				
				break;
		}
		
		yValue = correctionMethod(model, 
				  sm, 
				  gm, 
				  k, 
				  output,
				  input);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
				
		Double intensity = (Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum();
		Double fhkl = (double) 0.001;
		if (intensity >=0){
			fhkl =Math.pow((Double) DatasetUtils.cast(yValue,Dataset.FLOAT64).sum(), 0.5);
		}	
		
		if (trackingMarker !=3 ){
			dm.addyList(model.getDatImages().getShape()[0], k ,intensity);
			dm.addyListFhkl(model.getDatImages().getShape()[0], k ,fhkl);
			dm.addOutputDatArray(model.getDatImages().getShape()[0], k ,output);
			
//			sm.yListReset();
			
			sm.addyList(sm.getImages().length, selection ,intensity);
			sm.addyListFhkl(sm.getImages().length, selection ,fhkl);
			sm.addOutputDatArray(sm.getImages().length, selection ,output);
			
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
//			System.out.println("Interesting.....");
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
		
//		tdfm.setLenPt(sm.getInitialLenPt());
		tdfm.setFitPower(model.getFitPower());
		tdfm.setBoundaryBox(model.getBoundaryBox());
		
		Metadata md = new Metadata();
//		IDataset dummyMD = DatasetFactory.zeros(new int [] {2,2});
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
//			System.out.println("Interesting.....");
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
//		IDataset dummyMD = DatasetFactory.zeros(new int [] {2,2});
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
//			System.out.println("Interesting.....");
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
														   pS,
														   ssvsPS,
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
	
	public static OperationData OverlappingBackgroundROIFittingIOp(ExampleModel model,
				  												   IDataset input,
				  												   SuperModel sm,
				  												   IPlottingSystem<Composite> pS,
				  												   IPlottingSystem<Composite> ssvsPS,
				  												   int trackingMarker,
				  												   int k){

		SecondConstantROIBackgroundSubtractionModel scrbm 
			= new SecondConstantROIBackgroundSubtractionModel();
		int[][] a= sm.getInitialLenPt();
		scrbm.setInitialLenPt(sm.getInitialLenPt());
		int[][] b= model.getLenPt();
		scrbm.setLenPt(model.getLenPt());
		scrbm.setFitPower(model.getFitPower());
		scrbm.setBoundaryBox(model.getBoundaryBox());
		scrbm.setTrackerOn(sm.getTrackerOn());
		scrbm.setTrackingMarker(trackingMarker);
		
		if(sm.getBoxOffsetLenPt() != null){
			int[][] e = sm.getBoxOffsetLenPt();
			scrbm.setBoxOffsetLenPt(sm.getBoxOffsetLenPt());
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
				scrbm.setBoxOffsetLenPt(sm.getPermanentBoxOffsetLenPt());
			}
			
		}
		else{
			scrbm.setLenPt(sm.getInitialLenPt());
		}
		
		int[][]  d= sm.getBackgroundLenPt();
		scrbm.setBackgroundLenPt(sm.getBackgroundLenPt());
		
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
		
		OverlappingBgBoxUsingIOperation obbio 
			= new OverlappingBgBoxUsingIOperation();
		
		obbio.setModel(scrbm);
		
		return obbio.execute(input, null);
	
	}
	
	public static Dataset correctionMethod(ExampleModel model, 
									SuperModel sm, 
									GeometricParametersModel gm, 
									int k, 
									IDataset output,
									IDataset input){
		
		Dataset correction = DatasetFactory.zeros(new int[] {1}, Dataset.FLOAT64);
		int correctionSelector = sm.getCorrectionSelection();
		
		yValue = DatasetFactory.zeros(new int[] {1}, Dataset.ARRAYFLOAT64);
		
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
			
//			debug("Correction Value at local k:  " + Double.toString(correction.getDouble(k)) + "  local k: " + k); 
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
		
		
		else if (correctionSelector ==2){

			try {
				correction = DatasetFactory.createFromObject(GeometricCorrectionsReflectivityMethod.reflectivityCorrectionsBatch(model.getDcdtheta(), k, sm, input, gm.getAngularFudgeFactor(), 
						gm.getBeamHeight(), gm.getFootprint()));
				
//				Dataset ref = 
//						ReflectivityFluxCorrectionsForDialog.reflectivityFluxCorrections(gm.getFluxPath(), 
//																						 model.getQdcdDat().getDouble(k), 
//																						 model);
				
//				correction = ref;
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
		
		else if (correctionSelector ==3){

			yValue = Maths.multiply(output, 1);

		}
		
		else{	
		}
	
		Double intensity = (Double) DatasetUtils.cast(output,Dataset.FLOAT64).sum();
	
//		debug("Correction Value:  " + Double.toString(correction.getDouble(0)));
//		debug("output sum: " + Double.toString(intensity));
		
		return yValue;
	}
	
	
	private static void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
	
}
