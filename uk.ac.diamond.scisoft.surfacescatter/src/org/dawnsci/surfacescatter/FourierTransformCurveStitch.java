package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class FourierTransformCurveStitch {

	private static double attenuationFactor;
	private static double attenuationFactorFhkl;
	private static double attenuationFactorRaw;
	private static double[][] maxMinArray;

	public static IDataset[] curveStitch4(CurveStitchDataPackage csdp, double[][] maxMinArrayIn) {

		return curveStitch4(csdp, maxMinArrayIn, null);

	}

	public static IDataset[] curveStitch4(CurveStitchDataPackage csdp, double[][] maxMinArrayIn,
			ArrayList<OverlapAttenuationObject> oAos) {

		ArrayList<Boolean> modifiedOverlaps = new ArrayList<Boolean>();

		if (oAos != null) {
			for (int g = 0; g < oAos.size(); g++) {
				modifiedOverlaps.add(oAos.get(g).isModified());
			}
		}

		else {
			for (int i = 0; i < csdp.getFilepaths().length; i++) {
				modifiedOverlaps.add(false);
			}
		}

		maxMinArray = maxMinArrayIn;

		ArrayList<OverlapDataModel> overlapDataModels = new ArrayList<>();

		IDataset[] xArray = csdp.getxIDataset();
		IDataset[] qArray = csdp.getqIDataset();
		IDataset[] yArray = csdp.getyIDataset();
		IDataset[] yArrayError = csdp.getyIDatasetError();
		IDataset[] yArrayFhkl = csdp.getyIDatasetFhkl();
		IDataset[] yArrayFhklError = csdp.getyIDatasetFhklError();
		IDataset[] yArrayRaw = csdp.getyRawIDataset();
		IDataset[] yArrayRawError = csdp.getyRawIDatasetError();

		IDataset[] yArrayCorrected = yArray.clone();
		IDataset[] yArrayCorrectedFhkl = yArrayFhkl.clone();
		IDataset[] yArrayCorrectedRaw = yArrayRaw.clone();

		IDataset[] yArrayCorrectedError = yArrayError.clone();
		IDataset[] yArrayCorrectedFhklError = yArrayFhklError.clone();
		IDataset[] yRawErrorArrayCorrected = yArrayRawError.clone();

		IDataset[][] attenuatedDatasets = new IDataset[2][];

		IDataset[][] attenuatedDatasetsFhkl = new IDataset[2][];

		IDataset[][] attenuatedDatasetsRaw = new IDataset[2][];

		int d = (xArray.length);

		if (maxMinArray == null) {
			maxMinArray = new double[d][2];

			maxMinArray = OverlapFinder.overlapFinderOperation(xArray);
		}

		attenuationFactor = 1;
		attenuationFactorFhkl = 1;
		attenuationFactorRaw = 1;

		for (int k = 0; k < yArray.length - 1; k++) {

			OverlapDataModel odm = new OverlapDataModel();

			odm.setLowerDatName(csdp.getFilepaths()[k]);
			odm.setUpperDatName(csdp.getFilepaths()[k + 1]);

			ArrayList<Integer> overlapLower = new ArrayList<>();
			ArrayList<Integer> overlapHigher = new ArrayList<>();

			double[] maxMinArrayhere = new double[2];

			for (double[] mM : maxMinArray) {

				boolean kb = false;
				boolean k1b = false;

				for (int i = 0; i < xArray[k].getSize(); i++) {

					double probe = xArray[k].getDouble(i);

					if (probe > mM[1] && probe < mM[0]) {
						kb = true;
						break;

					}
				}

				for (int j = 0; j < xArray[k + 1].getSize(); j++) {

					double probe = xArray[k + 1].getDouble(j);

					if (probe > mM[1] && probe < mM[0]) {
						k1b = true;
						break;

					}
				}

				if (k1b && kb) {
					maxMinArrayhere = mM;
					break;
				}
			}

			for (int l = 0; l < yArray[k].getSize(); l++) {

				try {
					if ((xArray[k].getDouble(l) >= (maxMinArrayhere[1] - 0.001 * maxMinArrayhere[1]))
							&& (xArray[k].getDouble(l) <= (maxMinArrayhere[0] + 0.001 * maxMinArrayhere[0]))
							&& csdp.getGoodPointIDataset()[k].getBoolean(l)) {
						overlapLower.add(l);
					}
				} catch (Exception p) {
					System.out.println(p.getMessage());
				}

			}

			for (int m = 0; m < yArray[k + 1].getSize(); m++) {
				try {
					if ((xArray[k + 1].getDouble(m) <= (maxMinArrayhere[0] + 0.001 * maxMinArrayhere[0]))
							&& (xArray[k + 1].getDouble(m) >= (maxMinArrayhere[1] - 0.001 * maxMinArrayhere[1]))
							&& csdp.getGoodPointIDataset()[k + 1].getBoolean(m)) {
						overlapHigher.add(m);
					}
				} catch (Exception j) {
					System.out.println(j.getMessage());
				}
			}

			int[] lowerOverlapPositions = new int[overlapLower.size()];

			for (int o = 0; o < overlapLower.size(); o++) {
				lowerOverlapPositions[o] = overlapLower.get(o);
			}

			int[] higherOverlapPositions = new int[overlapHigher.size()];

			for (int o = 0; o < overlapHigher.size(); o++) {
				higherOverlapPositions[o] = overlapHigher.get(o);
			}

			odm.setLowerOverlapPositions(lowerOverlapPositions);
			odm.setUpperOverlapPositions(higherOverlapPositions);

			Dataset[] xLowerDataset = new Dataset[1];
			Dataset yLowerDataset = null;
			Dataset yLowerDatasetFhkl = null;
			Dataset yLowerDatasetRaw = null;
			Dataset[] xHigherDataset = new Dataset[1];
			Dataset yHigherDataset = null;
			Dataset yHigherDatasetFhkl = null;
			Dataset yHigherDatasetRaw = null;

			ArrayList<Double> xLowerList = new ArrayList<>();
			ArrayList<Double> yLowerList = new ArrayList<>();
			ArrayList<Double> yLowerListFhkl = new ArrayList<>();
			ArrayList<Double> yLowerListRaw = new ArrayList<>();
			ArrayList<Double> xHigherList = new ArrayList<>();
			ArrayList<Double> yHigherList = new ArrayList<>();
			ArrayList<Double> yHigherListFhkl = new ArrayList<>();
			ArrayList<Double> yHigherListRaw = new ArrayList<>();

			if (overlapLower.size() > 0 && overlapHigher.size() > 0) {

				for (int l = 0; l < overlapLower.size(); l++) {
					try {
						xLowerList.add(xArray[k].getDouble(overlapLower.get(l)));
					} catch (Exception f) {
						System.out.println(f.getMessage());
					}
					yLowerList.add(yArray[k].getDouble(overlapLower.get(l)));
					yLowerListFhkl.add(yArrayFhkl[k].getDouble(overlapLower.get(l)));
					yLowerListRaw.add(yArrayRaw[k].getDouble(overlapLower.get(l)));

					xLowerDataset[0] = DatasetFactory.createFromObject(xLowerList);
					yLowerDataset = DatasetFactory.createFromObject(yLowerList);
					yLowerDatasetFhkl = DatasetFactory.createFromObject(yLowerListFhkl);
					yLowerDatasetRaw = DatasetFactory.createFromObject(yLowerListRaw);
				}

				double[] lowerOverlapCorrectedValues = new double[overlapLower.size()];
				double[] lowerOverlapRawValues = new double[overlapLower.size()];
				double[] lowerOverlapFhklValues = new double[overlapLower.size()];
				double[] lowerOverlapScannedValues = new double[overlapLower.size()];

				for (int o = 0; o < overlapLower.size(); o++) {
					lowerOverlapScannedValues[o] = xLowerList.get(o);
					lowerOverlapCorrectedValues[o] = yLowerList.get(o);
					lowerOverlapRawValues[o] = yLowerListRaw.get(o);
					lowerOverlapFhklValues[o] = yLowerListFhkl.get(o);
				}

				odm.setLowerOverlapCorrectedValues(lowerOverlapCorrectedValues);
				odm.setLowerOverlapRawValues(lowerOverlapRawValues);
				odm.setLowerOverlapFhklValues(lowerOverlapFhklValues);
				odm.setLowerOverlapScannedValues(lowerOverlapScannedValues);

				for (int l = 0; l < overlapHigher.size(); l++) {
					xHigherList.add(xArray[k + 1].getDouble(overlapHigher.get(l)));
					yHigherList.add(yArray[k + 1].getDouble(overlapHigher.get(l)));
					yHigherListFhkl.add(yArrayFhkl[k + 1].getDouble(overlapHigher.get(l)));
					yHigherListRaw.add(yArrayRaw[k + 1].getDouble(overlapHigher.get(l)));

					xHigherDataset[0] = DatasetFactory.createFromObject(xHigherList);
					yHigherDataset = DatasetFactory.createFromObject(yHigherList);
					yHigherDatasetFhkl = DatasetFactory.createFromObject(yHigherListFhkl);
					yHigherDatasetRaw = DatasetFactory.createFromObject(yHigherListRaw);
				}

				double[] upperOverlapCorrectedValues = new double[overlapHigher.size()];
				double[] upperOverlapRawValues = new double[overlapHigher.size()];
				double[] upperOverlapFhklValues = new double[overlapHigher.size()];
				double[] upperOverlapScannedValues = new double[overlapHigher.size()];

				for (int o = 0; o < overlapHigher.size(); o++) {
					upperOverlapScannedValues[o] = xHigherList.get(o);
					upperOverlapCorrectedValues[o] = yHigherList.get(o);
					upperOverlapRawValues[o] = yHigherListRaw.get(o);

					upperOverlapFhklValues[o] = yHigherListFhkl.get(o);
				}

				odm.setUpperOverlapCorrectedValues(upperOverlapCorrectedValues);
				odm.setUpperOverlapRawValues(upperOverlapRawValues);
				odm.setUpperOverlapFhklValues(upperOverlapFhklValues);
				odm.setUpperOverlapScannedValues(upperOverlapScannedValues);

				if (!modifiedOverlaps.get(k)) {

					double correctionRatioFourier = 0;
					double correctionRatioFhklFourier = 0;
					double correctionRatioRawFourier = 0;

					double fourierRMSMean = 0;
					double polyRMSMean = 0;


					boolean useFourierTransform = true;

					Dataset yLowerDatasetUse = yLowerDataset;
					Dataset yHigherDatasetUse = yHigherDataset;

					Dataset yLowerDatasetFhklUse = yLowerDatasetFhkl;
					Dataset yHigherDatasetFhklUse = yHigherDatasetFhkl;

					Dataset yLowerDatasetRawUse = yLowerDatasetRaw;
					Dataset yHigherDatasetRawUse = yHigherDatasetRaw;

					// - nu is its logarithm in base e
					int n = yLowerDataset.getSize();
					int m = yHigherDataset.getSize();

					// If n is a power of 2, then ld is an integer (_without_ decimals)
					double ldL = Math.log(n) / Math.log(2.0);
					double ldH = Math.log(m) / Math.log(2.0);
					// Here I check if n is a power of 2. If exist decimals in ld, I quit
					// from the function returning null.
					if (((int) ldL) - ldL != 0 || ((int) ldH) - ldH != 0) {

						int twoPowL = (int) Math.floor(ldL);
						int twoPowH = (int) Math.floor(ldH);

						if (twoPowL == 0 || twoPowH == 0) {
							useFourierTransform = false;
						}

						int noPointsReqL = (int) Math.pow(2, twoPowL);
						int noPointsReqH = (int) Math.pow(2, twoPowH);

						yLowerDatasetUse = usedatasetStochastic(yLowerDataset, noPointsReqL);
						yHigherDatasetUse = usedatasetStochastic(yHigherDataset, noPointsReqH);

						yLowerDatasetFhklUse = usedatasetStochastic(yLowerDatasetFhkl, noPointsReqL);
						yHigherDatasetFhklUse = usedatasetStochastic(yHigherDatasetFhkl, noPointsReqH);

						yLowerDatasetRawUse = usedatasetStochastic(yLowerDatasetRaw, noPointsReqL);
						yHigherDatasetRawUse = usedatasetStochastic(yHigherDatasetRaw, noPointsReqH);

					}

					
					FourierScalingOutputPackage correctionRatiosFourier = null;
					FourierScalingOutputPackage correctionRatiosFhklFourier= null;
					FourierScalingOutputPackage correctionRatiosRawFourier= null;
					
					if (useFourierTransform) {
						try {
							correctionRatiosFourier = FourierTransformOverlap.correctionRatioFullPackage(xLowerDataset,
									yLowerDatasetUse, xHigherDataset, yHigherDatasetUse, attenuationFactor);
	
							correctionRatioFourier = correctionRatiosFourier.getCorrection()[0];
	
							fourierRMSMean = (correctionRatiosFourier.getrMSLowerHigher()[0] + correctionRatiosFourier.getrMSLowerHigher()[1]) / 2;
	
							correctionRatiosFhklFourier = FourierTransformOverlap.correctionRatioFullPackage(xLowerDataset,
									yLowerDatasetFhklUse, xHigherDataset, yHigherDatasetFhklUse, attenuationFactor);
	
							correctionRatioFhklFourier = correctionRatiosFhklFourier.getCorrection()[0];
	
							correctionRatiosRawFourier = FourierTransformOverlap.correctionRatioFullPackage(xLowerDataset,
									yLowerDatasetRawUse, xHigherDataset, yHigherDatasetRawUse, attenuationFactorRaw);
	
							correctionRatioRawFourier = correctionRatiosRawFourier.getCorrection()[0];
						}
						catch(NullPointerException pe) {
							useFourierTransform = false;
						}

					}

					double[][] correctionRatiosPoly = PolynomialOverlapSXRD.correctionRatio(xLowerDataset,
							yLowerDataset, xHigherDataset, yHigherDataset, attenuationFactor, 4);

					polyRMSMean = (correctionRatiosPoly[4][0] + correctionRatiosPoly[4][1]) / 2;

					double[][] correctionRatioFhklPoly = PolynomialOverlapSXRD.correctionRatio(xLowerDataset,
							yLowerDatasetFhkl, xHigherDataset, yHigherDatasetFhkl, attenuationFactorFhkl, 4);

					double[][] correctionRatioRawPoly = PolynomialOverlapSXRD.correctionRatio(xLowerDataset,
							yLowerDatasetRaw, xHigherDataset, yHigherDatasetRaw, attenuationFactorRaw, 4);

					if (useFourierTransform && fourierRMSMean < polyRMSMean && !Double.isNaN(correctionRatioFourier)) {
						attenuationFactor = correctionRatioFourier;
						attenuationFactorFhkl = correctionRatioFhklFourier;
						attenuationFactorRaw = correctionRatioRawFourier;
						
						odm.setLowerFFTFitCoefficientsCorrected(correctionRatiosFourier.getLowerCoefficients());
						odm.setUpperFFTFitCoefficientsCorrected(correctionRatiosFourier.getUpperCoefficients());
						
						odm.setUpperBaseFrequencyCorrected(correctionRatiosFourier.getUpperBaseFrequency());
						odm.setLowerBaseFrequencyCorrected(correctionRatiosFourier.getLowerBaseFrequency());
						
						odm.setLowerFFTFitCoefficientsFhkl(correctionRatiosFhklFourier.getLowerCoefficients());
						odm.setUpperFFTFitCoefficientsFhkl(correctionRatiosFhklFourier.getUpperCoefficients());
						
						odm.setUpperBaseFrequencyFhkl(correctionRatiosFhklFourier.getUpperBaseFrequency());
						odm.setLowerBaseFrequencyFhkl(correctionRatiosFhklFourier.getLowerBaseFrequency());
						
						odm.setLowerFFTFitCoefficientsRaw(correctionRatiosRawFourier.getLowerCoefficients());
						odm.setUpperFFTFitCoefficientsRaw(correctionRatiosRawFourier.getUpperCoefficients());
						
						odm.setUpperBaseFrequencyRaw(correctionRatiosRawFourier.getUpperBaseFrequency());
						odm.setLowerBaseFrequencyRaw(correctionRatiosRawFourier.getLowerBaseFrequency());
						
					}
					else {
						attenuationFactor = correctionRatiosPoly[2][0];
						attenuationFactorFhkl = correctionRatioFhklPoly[2][0];
						attenuationFactorRaw = correctionRatioRawPoly[2][0];
						
						odm.setLowerOverlapFitParametersCorrected(correctionRatiosPoly[0]);
						odm.setUpperOverlapFitParametersCorrected(correctionRatiosPoly[1]);
						
						odm.setLowerOverlapFitParametersFhkl(correctionRatioFhklPoly[0]);
						odm.setUpperOverlapFitParametersFhkl(correctionRatioFhklPoly[1]);
						
						odm.setLowerOverlapFitParametersRaw(correctionRatioRawPoly[0]);
						odm.setUpperOverlapFitParametersRaw(correctionRatioRawPoly[1]);
						
					}

					odm.setAttenuationFactor(attenuationFactor);
					odm.setAttenuationFactorFhkl(attenuationFactorFhkl);
					odm.setAttenuationFactorRaw(attenuationFactorRaw);

					

				} else {
					attenuationFactor = oAos.get(k).getAttenuationFactorCorrected();
					attenuationFactorFhkl = oAos.get(k).getAttenuationFactorFhkl();
					attenuationFactorRaw = oAos.get(k).getAttenuationFactorRaw();

				}

				yArrayCorrected[k + 1] = Maths.multiply(yArray[k + 1], attenuationFactor);
				yArrayCorrectedFhkl[k + 1] = Maths.multiply(yArrayFhkl[k + 1], attenuationFactorFhkl);
				yArrayCorrectedRaw[k + 1] = Maths.multiply(yArrayRaw[k + 1], attenuationFactorRaw);

				yArrayCorrectedError[k + 1] = Maths.multiply(yArrayError[k + 1], attenuationFactor);
				yArrayCorrectedFhklError[k + 1] = Maths.multiply(yArrayFhklError[k + 1], attenuationFactorFhkl);
				yRawErrorArrayCorrected[k + 1] = Maths.multiply(yArrayRawError[k + 1], attenuationFactorRaw);

				odm.setAttenuationFactor(attenuationFactor);
				odm.setAttenuationFactorFhkl(attenuationFactorFhkl);
				odm.setAttenuationFactorRaw(attenuationFactorRaw);

				overlapDataModels.add(odm);

			}
			
			else {

				yArrayCorrected[k + 1] = Maths.multiply(yArray[k + 1], attenuationFactor);
				yArrayCorrectedFhkl[k + 1] = Maths.multiply(yArrayFhkl[k + 1], attenuationFactorFhkl);
				yArrayCorrectedRaw[k + 1] = Maths.multiply(yArrayRaw[k + 1], attenuationFactorRaw);

				yArrayCorrectedError[k + 1] = Maths.multiply(yArrayError[k + 1], attenuationFactor);
				yArrayCorrectedFhklError[k + 1] = Maths.multiply(yArrayFhklError[k + 1], attenuationFactorFhkl);
				yRawErrorArrayCorrected[k + 1] = Maths.multiply(yArrayRawError[k + 1], attenuationFactorRaw);

				odm.setAttenuationFactor(attenuationFactor);
				odm.setAttenuationFactorFhkl(attenuationFactorFhkl);
				odm.setAttenuationFactorRaw(attenuationFactorRaw);

				overlapDataModels.add(odm);
				
			}
		}

		////////////////////
		attenuatedDatasets[0] = yArrayCorrected;
		attenuatedDatasets[1] = xArray;

		attenuatedDatasetsFhkl[0] = yArrayCorrectedFhkl;
		attenuatedDatasetsFhkl[1] = xArray;

		attenuatedDatasetsRaw[0] = yArrayCorrectedRaw;
		attenuatedDatasetsRaw[1] = xArray;

		Dataset[] sortedAttenuatedDatasets = new Dataset[10];

		sortedAttenuatedDatasets[0] = localConcatenate(attenuatedDatasets[0], 0);
		sortedAttenuatedDatasets[1] = localConcatenate(attenuatedDatasets[1], 0);

		Dataset xArrayCloned = sortedAttenuatedDatasets[1].clone();
		Dataset xArrayCloned2 = sortedAttenuatedDatasets[1].clone();

		sortedAttenuatedDatasets[2] = localConcatenate(attenuatedDatasetsFhkl[0], 0);
		sortedAttenuatedDatasets[7] = localConcatenate(attenuatedDatasetsRaw[0], 0);

		Dataset sortedYArrayCorrectedError = localConcatenate(yArrayCorrectedError, 0);
		Dataset sortedYArrayCorrectedFhklError = localConcatenate(yArrayCorrectedFhklError, 0);
		Dataset sortedYArrayCorrectedRawError = localConcatenate(yRawErrorArrayCorrected, 0);

		if (sortedAttenuatedDatasets[1].getSize() != sortedAttenuatedDatasets[0].getSize()) {
			System.out.println("error");
		}

		if (sortedAttenuatedDatasets[1].getShape()[0] != sortedAttenuatedDatasets[0].getShape()[0]) {
			System.out.println("error");
		}

		if (sortedAttenuatedDatasets[1].getSize() != sortedAttenuatedDatasets[0].getSize()) {
			System.out.println("error in lengths, in stitcher");
		}

		DatasetUtils.sort(sortedAttenuatedDatasets[1], /// xArray
				sortedAttenuatedDatasets[0]);/// yArray Intensity

		DatasetUtils.sort(xArrayCloned, /// xArray
				sortedAttenuatedDatasets[2]);////// yArray Fhkl

		DatasetUtils.sort(xArrayCloned2, /// xArray
				sortedAttenuatedDatasets[7]); ////// yArray Raw

		DatasetUtils.sort(sortedAttenuatedDatasets[1], sortedYArrayCorrectedError);

		DatasetUtils.sort(sortedAttenuatedDatasets[1], sortedYArrayCorrectedFhklError);

		DatasetUtils.sort(sortedAttenuatedDatasets[1], sortedYArrayCorrectedRawError);

		sortedAttenuatedDatasets[0].setErrors(sortedYArrayCorrectedError);
		sortedAttenuatedDatasets[2].setErrors(sortedYArrayCorrectedFhklError);
		sortedAttenuatedDatasets[7].setErrors(sortedYArrayCorrectedRawError);

		csdp.setSplicedCurveY(sortedAttenuatedDatasets[0]);
		csdp.setSplicedCurveX(sortedAttenuatedDatasets[1]);
		csdp.setSplicedCurveYFhkl(sortedAttenuatedDatasets[2]);
		csdp.setSplicedCurveYError(sortedYArrayCorrectedError);
		csdp.setSplicedCurveYFhklError(sortedYArrayCorrectedFhklError);
		csdp.setSplicedCurveYRaw(sortedAttenuatedDatasets[7]);
		csdp.setSplicedCurveYRawError(sortedYArrayCorrectedRawError);
		csdp.setOverlapDataModels(overlapDataModels);

		try {
			if (qArray != null) {

				Dataset splicedQ = localConcatenate(qArray, 0); /// qArray
				DatasetUtils.sort(sortedAttenuatedDatasets[1], splicedQ);
				csdp.setSplicedCurveQ(splicedQ);
			}
		} catch (Exception f) {

		}

		return sortedAttenuatedDatasets;
	}

	private static Dataset localConcatenate(IDataset[] in, int dim) {

		boolean good = true;

		if (in.length == 0) {
			good = false;
			return null;
		}

		for (IDataset i : in) {

			if (i == null) {
				good = false;
				return null;
			}

			if (i.getSize() == 0) {
				good = false;
				return null;
			}
		}

		if (good) {
			return DatasetUtils.convertToDataset(DatasetUtils.concatenate(in, dim));
		}
		return null;
	}

	private static Dataset usedataset(Dataset candidate, int noPointsReq) {

		Dataset output = DatasetFactory.createRange(noPointsReq);

		boolean[] mask = new boolean[candidate.getSize()];

		for (int u = 0; u < mask.length; u++) {
			mask[u] = false;
		}

		Dataset yLowerDatasetUseMask = DatasetFactory.createFromObject(mask);

		ArrayList<Integer> usedPoints = new ArrayList<>();

		for (int o = 0; o < noPointsReq; o++) {
			yLowerDatasetUseMask.set(true, o);

		}

		// while(usedPoints.size()+1<noPointsReq+1) {
		//
		// int randomNum = (int)(Math.random() * noPointsReq-1);
		//
		// boolean go = true;
		//
		// for(int y: usedPoints) {
		// if(y == randomNum) {
		// go = false;
		// break;
		// }
		// }
		//
		// if(go) {
		// yLowerDatasetUseMask.set(true, randomNum);
		// usedPoints.add(randomNum);
		// }
		// }
		//
		int y = 0;
		for (int p = 0; p < yLowerDatasetUseMask.getSize(); p++) {
			if (yLowerDatasetUseMask.getBoolean(p)) {
				output.set(candidate.getDouble(p), y);
				y++;
			}
		}

		return output;

	}

	private static Dataset usedatasetStochastic(Dataset candidate, int noPointsReq) {

		boolean[] mask = new boolean[candidate.getSize()];

		for (int u = 0; u < mask.length; u++) {
			mask[u] = false;
		}

		Dataset yLowerDatasetUseMask = DatasetFactory.createFromObject(mask);

		ArrayList<Double> usedPoints = new ArrayList<>();

		for (int o = 0; o < noPointsReq; o++) {
			yLowerDatasetUseMask.set(true, o);
		}

		int numberOfPointsUnused = candidate.getSize() - noPointsReq;

		int skippedPoints = 0;

		while (usedPoints.size() < noPointsReq) {
			for (int i = 0; i < candidate.getSize(); i++) {
				if (Math.random() < 0.5 && skippedPoints < numberOfPointsUnused) {
					skippedPoints++;
				} else {
					usedPoints.add(candidate.getDouble(i));
				}
			}
		}

		Dataset output = DatasetFactory.createFromObject(usedPoints);

		return output;

	}

}
