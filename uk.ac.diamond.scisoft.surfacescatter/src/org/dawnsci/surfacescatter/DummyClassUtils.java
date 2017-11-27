package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class DummyClassUtils {

	public static void save(DirectoryModel drm, FrameModel fm, int k, int selection, IDataset output, int[][] sspLenPt,
			int trackingMarker) {

		Dataset yValue = correctionMethod(fm, output);

	
		double intensity = ((Number) DatasetUtils.cast(yValue, Dataset.FLOAT64).sum()).doubleValue();
		double rawIntensity = ((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue();

		double intensityError = getCorrectionValue(fm)
				* Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());
		double rawIntensityError = Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());

		double fhkl =Double.MIN_VALUE;
		if (intensity >= 0) {
			fhkl = Math.pow(((Number) DatasetUtils.cast(yValue, Dataset.FLOAT64).sum()).doubleValue(), 0.5);
		}

		if (trackingMarker != 3) {

			OutputCurvesDataPackage ocdp = drm.getOcdp();
			int noOfFrames = drm.getFms().size();

			ocdp.addToYListForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, intensity);
			ocdp.addToYListFhklForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, fhkl);
			ocdp.addToYListRawForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, rawIntensity);

			ocdp.addToYListErrorForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, intensityError);
			ocdp.addToYListFhklErrorForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, fhklerror(fhkl, rawIntensity));
			ocdp.addToYListRawErrorForEachDat(fm.getDatNo(), drm.getDatFilepaths().length,
					drm.getNoOfImagesInDatFile(fm.getDatNo()), k, rawIntensityError);

			ocdp.addyList(noOfFrames, selection, intensity);
			ocdp.addyListFhkl(noOfFrames, selection, fhkl);
			ocdp.addYListRawIntensity(noOfFrames, selection, rawIntensity);
			ocdp.addOutputDatArray(noOfFrames, selection, output);

			fm.setBackgroundSubtractedImage(output);

			fm.setUnspliced_Corrected_Intensity(intensity);
			fm.setUnspliced_Raw_Intensity(rawIntensity);
			fm.setUnspliced_Fhkl_Intensity(fhkl);

			if (!drm.isTrackerOn()) {
				double[] d = LocationLenPtConverterUtils.lenPtToLocationConverter(sspLenPt);
				fm.setRoiLocation(d);
			}
		}

	}

	private static double fhklerror(double fhkl, double rawIntensity) {

		return fhkl * 0.5 / Math.sqrt(rawIntensity);

	}

	public static OperationData twoDMethod(DirectoryModel drm, double[] locationList, FrameModel fm, int k,
			int selection, IDataset input, int trackingMarker) {


		if (AnalaysisMethodologies.toInt(fm.getFitPower()) < 5) {
			OperationData outputOD = DummyProcessBackGrounds.twoDFittingIOp(fm.getRoiLocation(), fm.getFitPower(),
					fm.getBoundaryBox(), drm.getInitialLenPt(), input);

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			return outputOD;

		} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN) {
			OperationData outputOD = DummyProcessBackGrounds.twoDGaussianFittingIOp(fm.getRoiLocation(),
					drm.getInitialLenPt(), fm.getFitPower(), fm.getBoundaryBox(), input, selection);

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			return outputOD;

		} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL) {
			OperationData outputOD = DummyProcessBackGrounds.twoDExponentialFittingIOp(fm.getRoiLocation(), input,
					drm.getInitialLenPt(), fm.getFitPower(), fm.getBoundaryBox(), trackingMarker);

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			return outputOD;
		}

		
		return null;

	}

	private static Dataset correctionMethod(FrameModel fm, IDataset output) {

		return Maths.multiply(output, getCorrectionValue(fm));
	}

	private static double getCorrectionValue(FrameModel fm) {

		double correction = 0.001;

		MethodSetting method = fm.getCorrectionSelection();

		switch (method) {

		case SXRD:

			double lorentz = fm.getLorentzianCorrection();

			double areaCorrection = fm.getAreaCorrection();

			double polarisation = fm.getPolarisationCorrection();

			correction = lorentz * polarisation * areaCorrection;

			break;

		case Reflectivity_with_Flux_Correction_Gaussian_Profile:

			try {

				double refAreaCorrection = fm.getReflectivityAreaCorrection();

				double refFluxCorrection = fm.getReflectivityFluxCorrection();

				correction = refAreaCorrection * refFluxCorrection;

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case Reflectivity_with_Flux_Correction_Simple_Scaling:

			try {

				double refAreaCorrection = fm.getReflectivityAreaCorrection();

				double refFluxCorrection = fm.getReflectivityFluxCorrection();

				correction = refAreaCorrection * refFluxCorrection;

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case Reflectivity_without_Flux_Correction_Gaussian_Profile:

			try {

				double refAreaCorrection = fm.getReflectivityAreaCorrection();

				correction = refAreaCorrection;

			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case Reflectivity_without_Flux_Correction_Simple_Scaling:

			try {

				double refAreaCorrection = fm.getReflectivityAreaCorrection();

				correction = refAreaCorrection;
			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case Reflectivity_NO_Correction:

			correction = 1;

			break;

		default:

			correction = 1;

			break;

		}

		if (correction == 0) {
			correction = 0.001;
		}

		return correction;

	}

}
