//package org.dawnsci.surfacescatter;
//
//import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
//import org.dawnsci.surfacescatter.ProcessingMethodsEnum.ProccessingMethod;
//import org.eclipse.dawnsci.analysis.api.processing.OperationData;
//import org.eclipse.january.DatasetException;
//import org.eclipse.january.dataset.Dataset;
//import org.eclipse.january.dataset.DatasetFactory;
//import org.eclipse.january.dataset.DatasetUtils;
//import org.eclipse.january.dataset.IDataset;
//import org.eclipse.january.dataset.SliceND;
//
//public class TheLostDummyProcess {
//
//	public static IDataset DummyProcess1(DirectoryModel drm, int k, int trackingMarker, int selection,
//			double[] locationList, int[][] sspLenPt) {
//
//		//////////////////////////////// NB selection is position in the sorted list of
//		//////////////////////////////// the whole rod k is position in the .dat file
//		IDataset output = null;
//
//		FrameModel fm = drm.getFms().get(selection);
//		IDataset input = DatasetFactory.createFromObject(0);
//		try {
//			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape()));
//			input.squeeze();
//		} catch (DatasetException e) {
//			e.printStackTrace();
//		}
//
//		switch (fm.getBackgroundMethdology()) {
//
//		case TWOD:
//
//			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {
//
//				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
//			}
//
//			else {
//
//				int[] len = drm.getInitialLenPt()[0];
//				int[] pt = drm.getInitialLenPt()[1];
//
//				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));
//
//			}
//
//			if (AnalaysisMethodologies.toInt(fm.getFitPower()) < 5) {
//				outputOD = twoDFittingIOp(fm.getRoiLocation(), fm.getFitPower(), fm.getBoundaryBox(),
//						drm.getInitialLenPt(), input, trackingMarker);
//			} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN) {
//				outputOD = twoDGaussianFittingIOp(fm.getRoiLocation(), drm.getInitialLenPt(), fm.getFitPower(),
//						fm.getBoundaryBox(), input, selection, trackingMarker);
//			} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL) {
//				outputOD = twoDExponentialFittingIOp(fm.getRoiLocation(), input, drm.getInitialLenPt(),
//						fm.getFitPower(), fm.getBoundaryBox(), k, trackingMarker);
//			}
//
//			output = outputOD.getData();
//
//			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];
//
//			drm.setTemporaryBackgroundHolder(temporaryBackground1);
//
//			break;
//
//		case SECOND_BACKGROUND_BOX:
//		case OVERLAPPING_BACKGROUND_BOX:
//
//			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {
//
//				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
//			}
//
//			else {
//
//				int[] len = drm.getInitialLenPt()[0];
//				int[] pt = drm.getInitialLenPt()[1];
//
//				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));
//			}
//
//			output = secondConstantROIMethod(input, drm, fm.getBackgroundMethdology(), selection, trackingMarker, k);
//
//			break;
//
//		case X:
//
//			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {
//
//				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
//
//			}
//
//			else {
//
//				int[] len = drm.getInitialLenPt()[0];
//				int[] pt = drm.getInitialLenPt()[1];
//
//				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));
//
//			}
//
//			OperationData outputOD2 = oneDFittingIOp(// drm.getLenPtForEachDat()[k],
//					drm.getInitialLenPt(), fm.getFitPower(), fm.getRoiLocation(), input, fm.getBoundaryBox(),
//					Methodology.X, trackingMarker);
//
//			output = outputOD2.getData();
//
//			IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
//
//			drm.setTemporaryBackgroundHolder(temporaryBackground2);
//
//			break;
//
//		case Y:
//
//			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {
//
//				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
//
//			}
//
//			else {
//
//				int[] len = drm.getInitialLenPt()[0];
//				int[] pt = drm.getInitialLenPt()[1];
//
//				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));
//
//			}
//
//			OperationData outputOD3 = oneDFittingIOp(// drm.getLenPtForEachDat()[k],
//					drm.getInitialLenPt(), fm.getFitPower(), fm.getRoiLocation(), input, fm.getBoundaryBox(),
//					Methodology.Y, trackingMarker);
//
//			output = outputOD3.getData();
//
//			IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
//			drm.setTemporaryBackgroundHolder(temporaryBackground3);
//
//			break;
//		}
//
//		yValue = correctionMethod(fm, output);
//
//		double intensity = ((Number) DatasetUtils.cast(yValue, Dataset.FLOAT64).sum()).doubleValue();
//		double rawIntensity = ((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue();
//
//		double intensityError = getCorrectionValue(fm)
//				* Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());
//		double rawIntensityError = Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());
//
//		double fhkl = 0.001;
//		if (intensity >= 0) {
//			fhkl = Math.pow(intensity, 0.5);
//		}
//
//		if (trackingMarker != 3) {
//			OutputCurvesDataPackage ocdp = drm.getOcdp();
//			int noOfFrames = drm.getFms().size();
//
//			ocdp.addToYListForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
//					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, intensity);
//			ocdp.addToYListFhklForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
//					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, fhkl);
//			ocdp.addToYListRawForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
//					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, rawIntensity);
//
//			ocdp.addToYListErrorForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
//					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, intensityError);
//			ocdp.addToYListFhklErrorForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
//					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, fhklerror(fhkl, rawIntensity));
//			ocdp.addToYListRawErrorForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
//					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, rawIntensityError);
//
//			ocdp.addyList(noOfFrames, selection, intensity);
//			ocdp.addyListFhkl(noOfFrames, selection, fhkl);
//			ocdp.addYListRawIntensity(noOfFrames, selection, rawIntensity);
//			ocdp.addOutputDatArray(noOfFrames, selection, output);
//
//			fm.setBackgroundSubtractedImage(output);
//
//			fm.setUnspliced_Corrected_Intensity(intensity);
//			fm.setUnspliced_Raw_Intensity(rawIntensity);
//			fm.setUnspliced_Fhkl_Intensity(fhkl);
//
//			if (!drm.isTrackerOn()) {
//				double[] d = LocationLenPtConverterUtils.lenPtToLocationConverter(sspLenPt);
//				fm.setRoiLocation(d);
//			}
//
//			debug("  intensity:  " + intensity + "   k: " + k);
//		}
//		debug("intensity added to dm: " + intensity + "   local k: " + k + "   selection: " + selection);
//
//		return output;
//	}
//
//}
