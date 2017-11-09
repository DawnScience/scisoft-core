package org.dawnsci.surfacescatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.dawnsci.surfacescatter.AnalaysisMethodologies;
import org.dawnsci.surfacescatter.DirectoryModel;
import org.dawnsci.surfacescatter.LocationLenPtConverterUtils;
import org.dawnsci.surfacescatter.OneDFittingModel;
import org.dawnsci.surfacescatter.OneDFittingUsingIOperation;
import org.dawnsci.surfacescatter.OverlappingBgBoxUsingIOperation;
import org.dawnsci.surfacescatter.RefinedTwoDExponentialFittingUsingIOperation;
import org.dawnsci.surfacescatter.SecondConstantROIBackgroundSubtractionModel;
import org.dawnsci.surfacescatter.SecondConstantROIUsingIOperation;
import org.dawnsci.surfacescatter.TwoDFittingModel;
import org.dawnsci.surfacescatter.TwoDFittingUsingIOperation;
import org.dawnsci.surfacescatter.TwoDGaussianFittingUsingIOperation;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.Metadata;

public class DummyProcessBackGrounds {

	public static OperationData twoDFittingIOp(double[] p, FitPower fp, int boundaryBox, int[][] initialLenPt,
			IDataset input) {

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

	public static OperationData twoDGaussianFittingIOp(double[] p, 
			int[][] initialLenPt, 
			FitPower fp, int boundaryBox, IDataset input, int trackingMarker) {

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
			int boundaryBox, int trackingMarker) {

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

	
}
