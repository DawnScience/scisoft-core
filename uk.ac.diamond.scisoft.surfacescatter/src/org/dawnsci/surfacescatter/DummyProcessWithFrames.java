package org.dawnsci.surfacescatter;

import java.util.Arrays;
import org.dawnsci.surfacescatter.ProcessingMethodsEnum.ProccessingMethod;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.SliceND;

public class DummyProcessWithFrames {

	private static OperationData outputOD;

	public static IDataset dummyProcess(DirectoryModel drm, int k, int trackingMarker, int selection,
			double[] locationOverride, int[][] sspLenPt) {

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

		if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

			new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, selection);

			locationOverride = pfixer(fm, drm, locationOverride);
		}

		else {

			fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(drm.getInitialLenPt()));
		}

		switch (fm.getBackgroundMethdology()) {

		case TWOD:

			if (AnalaysisMethodologies.toInt(fm.getFitPower()) < 5) {

				locationOverride = pfixer(fm, drm, locationOverride);

				int[][] hi = new int[2][];

				if (fm.getRoiLocation() != null) {
					hi = LocationLenPtConverterUtils.locationToLenPtConverter(fm.getRoiLocation());

				}

				else {
					hi = drm.getInitialLenPt();
				}

				outputOD = DummyProcessBackGrounds.twoDFittingIOp(locationOverride, fm.getFitPower(),
						fm.getBoundaryBox(), hi, input);

			}

			else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_GAUSSIAN) {

				outputOD = DummyProcessBackGrounds.twoDGaussianFittingIOp(locationOverride, drm.getInitialLenPt(),
						fm.getFitPower(), fm.getBoundaryBox(), input, trackingMarker);
			} else if (fm.getFitPower() == AnalaysisMethodologies.FitPower.TWOD_EXPONENTIAL) {
				outputOD = DummyProcessBackGrounds.twoDExponentialFittingIOp(locationOverride, input,
						drm.getInitialLenPt(), fm.getFitPower(), fm.getBoundaryBox(), trackingMarker);
			}

			output = outputOD.getData();

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			break;

		case OVERLAPPING_BACKGROUND_BOX:
		case SECOND_BACKGROUND_BOX:

			output = DummyProcessBackGrounds.secondConstantROIMethod(input, drm, fm.getBackgroundMethdology(),
					selection, trackingMarker, k);

			break;

		case Y:
		case X:

			OperationData outputOD2 = DummyProcessBackGrounds.oneDFittingIOp(
					LocationLenPtConverterUtils.locationToLenPtConverter(locationOverride), fm.getFitPower(),
					fm.getRoiLocation(), input, fm.getBoundaryBox(), fm.getBackgroundMethdology(), trackingMarker);
			output = outputOD2.getData();

			IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];
			drm.setTemporaryBackgroundHolder(temporaryBackground2);

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

		DummyClassUtils.save(drm, fm, k, selection, output, sspLenPt, trackingMarker);

		return output;
	}

	public static IDataset dummyProcess1(DirectoryModel drm, int k, int trackingMarker, int selection,
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

		if (drm.isTrackerOn() && fm.getProcessingMethodSelection() != ProccessingMethod.MANUAL) {

			new ModifiedAgnosticTrackerWithFrames1(drm, trackingMarker, k, locationList, selection);
		}

		else {

			fm.setRoiLocation(LocationLenPtConverterUtils.lenPtToLocationConverter(drm.getInitialLenPt()));
		}

		switch (fm.getBackgroundMethdology()) {

		case TWOD:

			outputOD = DummyClassUtils.twoDMethod(drm, locationList, fm, k, selection, input, trackingMarker);

			output = outputOD.getData();

			IDataset temporaryBackground1 = (IDataset) outputOD.getAuxData()[0];

			drm.setTemporaryBackgroundHolder(temporaryBackground1);

			break;

		case SECOND_BACKGROUND_BOX:
		case OVERLAPPING_BACKGROUND_BOX:

			output = DummyProcessBackGrounds.secondConstantROIMethod(input, drm, fm.getBackgroundMethdology(),
					selection, trackingMarker, k);

			break;

		case Y:
		case X:

			OperationData outputOD2 = DummyProcessBackGrounds.oneDFittingIOp(drm.getInitialLenPt(), fm.getFitPower(),
					fm.getRoiLocation(), input, fm.getBoundaryBox(), fm.getBackgroundMethdology(), trackingMarker);

			output = outputOD2.getData();

			IDataset temporaryBackground2 = (IDataset) outputOD2.getAuxData()[1];

			drm.setTemporaryBackgroundHolder(temporaryBackground2);

			break;

		}

		DummyClassUtils.save(drm, fm, k, selection, output, sspLenPt, trackingMarker);

		return output;
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
