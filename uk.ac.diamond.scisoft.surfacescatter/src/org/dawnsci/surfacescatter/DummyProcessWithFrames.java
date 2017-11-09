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

public class DummyProcessWithFrames {

	private static Dataset yValue;
	private static int DEBUG = 0;
	private static OperationData outputOD;

	public static IDataset dummyProcess(DirectoryModel drm, GeometricParametersModel gm, int k, int trackingMarker,
			int selection, double[] locationOverride, int[][] sspLenPt) {

		FrameModel fm = drm.getFms().get(selection);

		if (locationOverride == null) {
			locationOverride = fm.getRoiLocation();
			if (locationOverride == null) {
				int[][] lenPt = drm.getInitialLenPt();
				locationOverride = LocationLenPtConverterUtils.lenPtToLocationConverter(lenPt);
			}
		}

		IDataset output = null;

		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape())).squeeze();
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (fm.getBackgroundMethdology()) {

		case TWOD:

			int[] len = drm.getInitialLenPt()[0];
			int[] pt = drm.getInitialLenPt()[1];

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, selection);

				locationOverride = pfixer(fm, drm, locationOverride);
			}

			else {
				fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) });
			}

			if (AnalaysisMethodologies.toInt(fm.getFitPower()) < 5) {

				locationOverride = pfixer(fm, drm, locationOverride);

				int[][] hi = new int[2][];

				if (fm.getRoiLocation() != null) {
					hi = LocationLenPtConverterUtils.locationToLenPtConverter(fm.getRoiLocation());

				}

				else {
					hi = drm.getInitialLenPt();
				}

				outputOD = twoDFittingIOp(locationOverride, fm.getFitPower(), fm.getBoundaryBox(), hi, input,
						trackingMarker);

			}

			else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN) {

				outputOD = twoDGaussianFittingIOp(locationOverride, drm.getInitialLenPt(), fm.getFitPower(),
						fm.getBoundaryBox(), input, selection, trackingMarker);
			} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL) {
				outputOD = twoDExponentialFittingIOp(locationOverride, input, drm.getInitialLenPt(), fm.getFitPower(),
						fm.getBoundaryBox(), k, trackingMarker);
			}

			output = outputOD.getData();

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			break;

		case OVERLAPPING_BACKGROUND_BOX:
		case SECOND_BACKGROUND_BOX:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, selection);

				locationOverride = pfixer(fm, drm, locationOverride);
			}

			else {

				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(drm.getInitialLenPt()));
			}

			output = secondConstantROIMethod(input, drm, fm.getBackgroundMethdology(), selection, trackingMarker, k);

			break;

		case X:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, selection);

				locationOverride = pfixer(fm, drm, locationOverride);
			}

			else {

				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(drm.getInitialLenPt()));
			}

			OperationData outputOD2 = oneDFittingIOp(
					LocationLenPtConverterUtils.locationToLenPtConverter(locationOverride), fm.getFitPower(),
					fm.getRoiLocation(), input, fm.getBoundaryBox(), Methodology.X, trackingMarker);
			output = outputOD2.getData();

			IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
			drm.setTemporaryBackgroundHolder(temporaryBackground2);

			break;

		case Y:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, selection);

				locationOverride = pfixer(fm, drm, locationOverride);
			}

			else {

				len = drm.getInitialLenPt()[0];
				pt = drm.getInitialLenPt()[1];

				fm.setRoiLocation(new double[] { (double) pt[0], (double) pt[1], (double) (pt[0] + len[0]),
						(double) (pt[1]), (double) pt[0], (double) pt[1] + len[1], (double) (pt[0] + len[0]),
						(double) (pt[1] + len[1]) });
			}

			OperationData outputOD3 = oneDFittingIOp(
					LocationLenPtConverterUtils.locationToLenPtConverter(locationOverride), fm.getFitPower(),
					fm.getRoiLocation(), input, fm.getBoundaryBox(), Methodology.Y, trackingMarker);
			output = outputOD3.getData();

			IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
			drm.setTemporaryBackgroundHolder(temporaryBackground3);

			break;
		}

		if (Arrays.equals(output.getShape(), (new int[] { 2, 2 }))) {
			IndexIterator it11 = ((Dataset) output).getIterator();

			while (it11.hasNext()) {
				double q = ((Dataset) output).getElementDoubleAbs(it11.index);
				if (q <= 0)
					((Dataset) output).setObjectAbs(it11.index, 0.1);
			}
			return output;
		}

		yValue = correctionMethod(fm, output);

		double intensity = ((Number) DatasetUtils.cast(yValue, Dataset.FLOAT64).sum()).doubleValue();
		double rawIntensity = ((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue();

		double intensityError = getCorrectionValue(fm)
				* ((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue();
		double rawIntensityError = Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());

		double fhkl = 0.001;
		if (intensity >= 0) {
			fhkl = Math.pow(((Number) DatasetUtils.cast(yValue, Dataset.FLOAT64).sum()).doubleValue(), 0.5);
		}

		if (trackingMarker != 3) {

			OutputCurvesDataPackage ocdp = drm.getOcdp();
			int noOfFrames = drm.getFms().size();

			int a = fm.getDatNo();
			int b = drm.getDatFilepaths().length;
			int c = drm.getNoOfImagesInDatFile(fm.getDatNo());

			ocdp.addToYListForEachDat(a, b, c, k, intensity);
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

		return output;
	}

	public static IDataset DummyProcess1(DirectoryModel drm, int k, int trackingMarker, int selection,
			double[] locationList, int[][] sspLenPt) {

		//////////////////////////////// NB selection is position in the sorted list of
		//////////////////////////////// the whole rod k is position in the .dat file
		IDataset output = null;

		FrameModel fm = drm.getFms().get(selection);
		IDataset input = DatasetFactory.createFromObject(0);
		try {
			input = fm.getRawImageData().getSlice(new SliceND(fm.getRawImageData().getShape()));
			input.squeeze();
		} catch (DatasetException e) {
			e.printStackTrace();
		}

		switch (fm.getBackgroundMethdology()) {

		case TWOD:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
			}

			else {

				int[] len = drm.getInitialLenPt()[0];
				int[] pt = drm.getInitialLenPt()[1];

				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));

			}

			if (AnalaysisMethodologies.toInt(fm.getFitPower()) < 5) {
				outputOD = twoDFittingIOp(fm.getRoiLocation(), fm.getFitPower(), fm.getBoundaryBox(),
						drm.getInitialLenPt(), input, trackingMarker);
			} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN) {
				outputOD = twoDGaussianFittingIOp(fm.getRoiLocation(), drm.getInitialLenPt(), fm.getFitPower(),
						fm.getBoundaryBox(), input, selection, trackingMarker);
			} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL) {
				outputOD = twoDExponentialFittingIOp(fm.getRoiLocation(), input, drm.getInitialLenPt(),
						fm.getFitPower(), fm.getBoundaryBox(), k, trackingMarker);
			}

			output = outputOD.getData();

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			break;

		case SECOND_BACKGROUND_BOX:
		case OVERLAPPING_BACKGROUND_BOX:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
			}

			else {

				int[] len = drm.getInitialLenPt()[0];
				int[] pt = drm.getInitialLenPt()[1];

				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));
			}

			output = secondConstantROIMethod(input, drm, fm.getBackgroundMethdology(), selection, trackingMarker, k);

			break;

		case X:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);

			}

			else {

				int[] len = drm.getInitialLenPt()[0];
				int[] pt = drm.getInitialLenPt()[1];

				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));

			}

			OperationData outputOD2 = oneDFittingIOp(// drm.getLenPtForEachDat()[k],
					drm.getInitialLenPt(), fm.getFitPower(), fm.getRoiLocation(), input, fm.getBoundaryBox(),
					Methodology.X, trackingMarker);

			output = outputOD2.getData();

			IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];

			drm.setTemporaryBackgroundHolder(temporaryBackground2);

			break;

		case Y:

			if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

				new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);

			}

			else {

				int[] len = drm.getInitialLenPt()[0];
				int[] pt = drm.getInitialLenPt()[1];

				fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(new int[][] { len, pt }));

			}

			OperationData outputOD3 = oneDFittingIOp(// drm.getLenPtForEachDat()[k],
					drm.getInitialLenPt(), fm.getFitPower(), fm.getRoiLocation(), input, fm.getBoundaryBox(),
					Methodology.Y, trackingMarker);

			output = outputOD3.getData();

			IDataset temporaryBackground3 = (IDataset) outputOD3.getAuxData()[1];
			drm.setTemporaryBackgroundHolder(temporaryBackground3);

			break;
		}

		yValue = correctionMethod(fm, output);

		double intensity = ((Number) DatasetUtils.cast(yValue, Dataset.FLOAT64).sum()).doubleValue();
		double rawIntensity = ((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue();

		double intensityError = getCorrectionValue(fm)
				* Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());
		double rawIntensityError = Math.sqrt(((Number) DatasetUtils.cast(output, Dataset.FLOAT64).sum()).doubleValue());

		double fhkl = 0.001;
		if (intensity >= 0) {
			fhkl = Math.pow(intensity, 0.5);
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

			debug("  intensity:  " + intensity + "   k: " + k);
		}
		debug("intensity added to dm: " + intensity + "   local k: " + k + "   selection: " + selection);

		return output;
	}

	public static OperationData twoDFittingIOp(double[] p, FitPower fp, int boundaryBox, int[][] initialLenPt,
			IDataset input, int trackingMarker) {

		TwoDFittingModel tdfm = new TwoDFittingModel();

		input.squeeze();

		int[][] lenPt = LocationLenPtConverterUtils.locationToLenPtConverter(p);

		if (p[0] != 0 && p[1] != 0) {
			tdfm.setLenPt(lenPt);
		}

		else {
			tdfm.setLenPt(initialLenPt);
		}

		tdfm.setFitPower(fp);
		tdfm.setBoundaryBox(boundaryBox);

		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);

		ILazyDataset ild = null;

		SourceInformation si = new SourceInformation("dummy", "dummy2", ild);

		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);

		input.setMetadata(sfsm);

		input.setMetadata(md);

		TwoDFittingUsingIOperation tdfuio = new TwoDFittingUsingIOperation();
		tdfuio.setModel(tdfm);

		return tdfuio.execute(input, null);

	}

	public static OperationData twoDGaussianFittingIOp(double[] p, // = sm.getLocationList().get(k);,
			int[][] initialLenPt, // sm.getInitialLenPt()
			FitPower fp, int boundaryBox, IDataset input, int k, int trackingMarker) {

		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(initialLenPt);

		if (trackingMarker != 3) {

			int[] pt = new int[] { (int) p[0], (int) p[1] };
			int[] len = initialLenPt[0];
			int[][] lenPt = new int[][] { len, pt };
			if (p[0] != 0 && p[1] != 0) {
				tdfm.setLenPt(lenPt);
			}

			else {
				tdfm.setLenPt(initialLenPt);
			}
		}

		else {
			tdfm.setLenPt(initialLenPt);
		}

		tdfm.setFitPower(fp);
		tdfm.setBoundaryBox(boundaryBox);

		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);

		input.squeeze();

		ILazyDataset ild = null;

		SourceInformation si = new SourceInformation("dummy", "dummy2", ild);

		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);

		input.setMetadata(sfsm);

		input.setMetadata(md);

		TwoDGaussianFittingUsingIOperation tdgfuio = new TwoDGaussianFittingUsingIOperation();
		tdgfuio.setModel(tdfm);

		return tdgfuio.execute(input, null);

	}

	public static OperationData twoDExponentialFittingIOp(double[] p, IDataset input, int[][] initialLenPt, FitPower fp,
			int boundaryBox, int k, int trackingMarker) {

		TwoDFittingModel tdfm = new TwoDFittingModel();
		tdfm.setInitialLenPt(initialLenPt);

		if (trackingMarker != 3) {

			int[] pt = new int[] { (int) p[0], (int) p[1] };
			int[] len = initialLenPt[0];
			int[][] lenPt = new int[][] { len, pt };

			if (p[0] != 0 && p[1] != 0) {
				tdfm.setLenPt(lenPt);
			}

			else {
				tdfm.setLenPt(initialLenPt);
			}
		}

		else {
			tdfm.setLenPt(initialLenPt);
		}

		tdfm.setFitPower(fp);
		tdfm.setBoundaryBox(boundaryBox);

		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);

		ILazyDataset ild = null;

		SourceInformation si = new SourceInformation("dummy", "dummy2", ild);

		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);

		input.setMetadata(sfsm);

		input.setMetadata(md);

		RefinedTwoDExponentialFittingUsingIOperation rtdefuio = new RefinedTwoDExponentialFittingUsingIOperation();
		rtdefuio.setModel(tdfm);

		return rtdefuio.execute(input, null);

	}

	public static OperationData oneDFittingIOp(int[][] initialLenPt, FitPower fp, double[] location, IDataset input,
			int boundaryBox, AnalaysisMethodologies.Methodology am, int trackingMarker) {

		OneDFittingModel odfm = new OneDFittingModel();

		input.squeeze();
		odfm.setInitialLenPt(initialLenPt);

		int[][] mLenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
		odfm.setLenPt(mLenPt);
		odfm.setFitPower(fp);
		odfm.setBoundaryBox(boundaryBox);
		odfm.setDirection(am);

		if (trackingMarker != 3) {

			double[] p = location;
			int[] pt = new int[] { (int) p[0], (int) p[1] };
			int[] len = initialLenPt[0];
			int[][] lenPt = new int[][] { len, pt };

			if (p[0] != 0 && p[1] != 0) {
				odfm.setLenPt(lenPt);
			}

			else {
				odfm.setLenPt(initialLenPt);
			}

		}

		else {
			odfm.setLenPt(initialLenPt);
		}

		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);

		ILazyDataset ild = null;

		SourceInformation si = new SourceInformation("dummy", "dummy2", ild);

		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);

		input.setMetadata(sfsm);

		input.setMetadata(md);

		OneDFittingUsingIOperation odfuio = new OneDFittingUsingIOperation();
		odfuio.setModel(odfm);

		return odfuio.execute(input, null);

	}

	public static OperationData secondConstantBackgroundROIFittingIOp(IDataset input1, double[] p, int[][] initialLenPt,
			int[][] mLenPt, int[][] backgroundLenPt, FitPower fp, int boundaryBox, int trackingMarker) {
		IDataset input = input1.squeeze();

		SecondConstantROIBackgroundSubtractionModel scrbm = new SecondConstantROIBackgroundSubtractionModel();
		scrbm.setInitialLenPt(initialLenPt);
		scrbm.setLenPt(mLenPt);
		scrbm.setFitPower(fp);
		scrbm.setBoundaryBox(boundaryBox);

		if (backgroundLenPt != null) {
			scrbm.setBackgroundLenPt(backgroundLenPt);
		}

		if (trackingMarker != 3) {

			int[] pt = new int[] { (int) p[0], (int) p[1] };
			int[] len = initialLenPt[0];
			int[][] lenPt = new int[][] { len, pt };
			if (p[0] != 0 && p[1] != 0) {
				scrbm.setLenPt(lenPt);
			}

			else {
				scrbm.setLenPt(initialLenPt);
			}

		} else {
			scrbm.setLenPt(initialLenPt);
		}

		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);

		ILazyDataset ild = null;

		SourceInformation si = new SourceInformation("dummy", "dummy2", ild);

		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);

		input.setMetadata(sfsm);

		input.setMetadata(md);
		SecondConstantROIUsingIOperation scrbio = new SecondConstantROIUsingIOperation();
		scrbio.setModel(scrbm);

		return scrbio.execute(input, null);
	}

	public static IDataset secondConstantROIMethod(IDataset input1, DirectoryModel drm,
			AnalaysisMethodologies.Methodology am, int selection, int trackingMarker, int k) {

		IDataset input = input1.squeeze();

		OperationData outputOD4 = null;

		int datNo = drm.getFms().get(selection).getDatNo();

		if (am == Methodology.SECOND_BACKGROUND_BOX) {

			outputOD4 = secondConstantBackgroundROIFittingIOp(input, drm.getFms().get(selection).getRoiLocation(),
					drm.getInitialLenPt(), drm.getLenPtForEachDat()[datNo], drm.getBackgroundLenPt(),
					drm.getFms().get(k).getFitPower(), drm.getFms().get(k).getBoundaryBox(), trackingMarker);
		}

		else if (am == Methodology.OVERLAPPING_BACKGROUND_BOX) {

			double[] a = drm.getFms().get(selection).getRoiLocation();
			int[][] b = drm.getInitialLenPt();

			if (a == null) {
				a = LocationLenPtConverterUtils.lenPtToLocationConverter(b);
				drm.getFms().get(k).setRoiLocation(a);
			}

			outputOD4 = overlappingBackgroundROIFittingIOp(input, drm.getFms().get(selection).getRoiLocation(),
					drm.getInitialLenPt(), drm.getLenPtForEachDat()[datNo], drm.getBackgroundLenPt(),
					drm.getBoxOffsetLenPt(), drm.getPermanentBoxOffsetLenPt(), drm.isTrackerOn(),
					drm.getFms().get(k).getFitPower(), drm.getFms().get(k).getBoundaryBox(), trackingMarker);

			if (outputOD4.getAuxData()[3] != null) {
				drm.setBoxOffsetLenPt((int[][]) outputOD4.getAuxData()[3]);
			}

		}

		IDataset output = outputOD4.getData();

		if ((IROI) outputOD4.getAuxData()[1] != null) {
			IRectangularROI bounds = ((IROI) outputOD4.getAuxData()[1]).getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();

			if (Arrays.equals(len, new int[] { 50, 50 }) == false || Arrays.equals(pt, new int[] { 10, 10 }) == false) {

				drm.setBackgroundROI((IROI) outputOD4.getAuxData()[1]);
				drm.getBackgroundROIForEachDat()[drm.getFms().get(k).getDatNo()] = ((IROI) outputOD4.getAuxData()[1]);
				;
			}
		}

		IDataset check = (IDataset) outputOD4.getAuxData()[2];

		drm.setTemporaryBackgroundHolder(check);

		return output;
	}

	public static OperationData overlappingBackgroundROIFittingIOp(IDataset input, double[] p, int[][] initialLenPt,
			int[][] mLenPt, int[][] backgroundLenPt, int[][] boxOffsetLenPt, int[][] permanentBoxOffsetLenPt,
			boolean trackerOn, FitPower fp, int boundaryBox, int trackingMarker) {

		SecondConstantROIBackgroundSubtractionModel scrbm = new SecondConstantROIBackgroundSubtractionModel();
		int[][] a = initialLenPt;
		scrbm.setInitialLenPt(a);
		int[][] b = mLenPt;
		scrbm.setLenPt(b);
		scrbm.setFitPower(fp);
		scrbm.setBoundaryBox(boundaryBox);
		scrbm.setTrackerOn(trackerOn);
		scrbm.setTrackingMarker(trackingMarker);

		if (boxOffsetLenPt != null) {
			int[][] e = boxOffsetLenPt;
			scrbm.setBoxOffsetLenPt(e);
		}

		if (trackingMarker != 3) {

			int[] pt = new int[] { (int) p[0], (int) p[1] };
			int[] len = initialLenPt[0];
			int[][] lenPt = new int[][] { len, pt };

			if (p[0] != 0 && p[1] != 0) {
				scrbm.setLenPt(lenPt);
			}

			else {
				scrbm.setLenPt(initialLenPt);
			}

			if (permanentBoxOffsetLenPt != null) {
				int[][] c = permanentBoxOffsetLenPt;
				scrbm.setBoxOffsetLenPt(c);
			}
		}

		else {
			scrbm.setLenPt(initialLenPt);
		}

		int[][] d = backgroundLenPt;
		scrbm.setBackgroundLenPt(d);

		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);

		ILazyDataset ild = null;

		input.squeeze();

		SourceInformation si = new SourceInformation("dummy", "dummy2", ild);

		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);

		input.setMetadata(sfsm);

		input.setMetadata(md);

		OverlappingBgBoxUsingIOperation obbio = new OverlappingBgBoxUsingIOperation();

		obbio.setModel(scrbm);

		if (input == null || obbio == null) {
			System.out.println("obbio or input is null");
		}

		OperationData out = obbio.execute(input, null);

		if (out == null) {
			System.out.println("prob in out");
		}

		return out;

	}

	public static Dataset correctionMethod(FrameModel fm, IDataset output) {

		yValue = Maths.multiply(output, getCorrectionValue(fm));

		return yValue;
	}

	public static double getCorrectionValue(FrameModel fm) {

		double correction = 0.001;

		MethodSetting method = fm.getCorrectionSelection();

		yValue = DatasetFactory.createFromObject(0);

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

	private static void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}

	private static double fhklerror(double fhkl, double rawIntensity) {

		return fhkl * 0.5 / Math.sqrt(rawIntensity);

	}

	private static double[] pfixer(FrameModel fm, DirectoryModel drm, double[] locationOverride) {

		double[] p = new double[6];

		if (AnalaysisMethodologies.toInt(fm.getFitPower()) < 5) {

			try {
				if (locationOverride != null) {
					p = locationOverride;
				} else {
					p = fm.getRoiLocation();
				}
			} catch (Exception n) {
				System.out.println(n.getMessage());
				int[][] lenPt = drm.getInitialLenPt();
				p = LocationLenPtConverterUtils.lenPtToLocationConverter(lenPt);
			}
		}
		return p;
	}

}
