package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class FourierTransformOverlap {

	public static FourierScalingOutputPackage correctionRatioFullPackage(Dataset[] xLowerDataset, Dataset yLowerDataset, Dataset[] xHigherDataset,
			Dataset yHigherDataset, double attenuationFactor) {

		double minXLower = minValue(xLowerDataset);
		double maxXLower = maxValue(xLowerDataset);

		double minXHigher = minValue(xHigherDataset);
		double maxXHigher = maxValue(xHigherDataset);

		int numberOfTestPoints = 100;

		IDataset xLowerTestArray = DatasetFactory.createRange(numberOfTestPoints);
		IDataset xHigherTestArray = DatasetFactory.createRange(numberOfTestPoints);

		for (int i = 0; i < numberOfTestPoints; i++) {

			double p = minXLower + (i) * ((maxXLower - minXLower) / numberOfTestPoints);
			xLowerTestArray.set(p, i);

			double q = minXHigher + (i) * ((maxXHigher - minXHigher) / numberOfTestPoints);
			xHigherTestArray.set(q, i);
		}

		FourierScalingOutputPackage fourierPackageUpper = FastFourierTransform
				.fftModeledYValuesDatasetFullPackage(xLowerTestArray, (IDataset) yHigherDataset);

		Dataset calculatedValuesHigher = (Dataset) fourierPackageUpper.getComputedYOutputDataset();
		
		FourierScalingOutputPackage fourierPackageLower = FastFourierTransform.fftModeledYValuesDatasetFullPackage(xHigherTestArray,
				(IDataset) yLowerDataset);
		
		Dataset calculatedValuesLower = (Dataset) fourierPackageLower.getComputedYOutputDataset();

		double calculatedValuesHigherRMS = FastFourierTransform.fftModeledYValuesDatasetRMS(yHigherDataset);
		double calculatedValuesLowerRMS = FastFourierTransform.fftModeledYValuesDatasetRMS(yLowerDataset);

		double runningSumDelta = 0;
		double runningSumNorm = Double.MIN_VALUE;

		for (int j = 0; j < numberOfTestPoints; j++) {

			double r = (calculatedValuesLower.getDouble(j) / calculatedValuesHigher.getDouble(j));

			double rDelta = r * Math.pow(Math.pow(r, 2) + Math.pow(Math.pow(calculatedValuesLower.getDouble(j), 0.5)
					/ Math.pow(calculatedValuesHigher.getDouble(j), 0.5), 2), 0.5);

			rDelta = 1 / Math.pow(rDelta, 2);

			runningSumNorm += rDelta;
			runningSumDelta += r * rDelta;

		}

		double[] rMSLowerHigher = new double[] { calculatedValuesLowerRMS, calculatedValuesHigherRMS };

		double temp = runningSumDelta / runningSumNorm;

		double[] runningSumNormArray = new double[] { runningSumNorm };

		double[] correction = new double[] { (temp) * attenuationFactor };

		FourierScalingOutputPackage out = new FourierScalingOutputPackage(); 
		
		out.setCorrection(correction);
		out.setrMSLowerHigher(rMSLowerHigher);
		out.setRunningSumNormArray(runningSumNormArray);
		
		out.setLowerBaseFrequency(fourierPackageLower.getBaseFrequency());
		out.setUpperBaseFrequency(fourierPackageUpper.getBaseFrequency());
		
		out.setLowerCoefficients(fourierPackageLower.getCoefficients());
		out.setUpperCoefficients(fourierPackageUpper.getCoefficients());
		
		
		return out;

	}

	public static double[][] correctionRatio(Dataset[] xLowerDataset, Dataset yLowerDataset, Dataset[] xHigherDataset,
			Dataset yHigherDataset, double attenuationFactor) {

		double minXLower = minValue(xLowerDataset);
		double maxXLower = maxValue(xLowerDataset);

		double minXHigher = minValue(xHigherDataset);
		double maxXHigher = maxValue(xHigherDataset);

		int numberOfTestPoints = 100;

		IDataset xLowerTestArray = DatasetFactory.createRange(numberOfTestPoints);
		IDataset xHigherTestArray = DatasetFactory.createRange(numberOfTestPoints);

		for (int i = 0; i < numberOfTestPoints; i++) {

			double p = minXLower + (i) * ((maxXLower - minXLower) / numberOfTestPoints);
			xLowerTestArray.set(p, i);

			double q = minXHigher + (i) * ((maxXHigher - minXHigher) / numberOfTestPoints);
			xHigherTestArray.set(q, i);
		}

		FourierScalingOutputPackage fourierPackage = FastFourierTransform
				.fftModeledYValuesDatasetFullPackage(xLowerTestArray, (IDataset) yHigherDataset);

		Dataset calculatedValuesHigher = (Dataset) fourierPackage.getComputedYOutputDataset();
		Dataset calculatedValuesLower = (Dataset) FastFourierTransform.fftModeledYValuesDataset(xHigherTestArray,
				(IDataset) yLowerDataset);

		double calculatedValuesHigherRMS = FastFourierTransform.fftModeledYValuesDatasetRMS(yHigherDataset);
		double calculatedValuesLowerRMS = FastFourierTransform.fftModeledYValuesDatasetRMS(yLowerDataset);

		double runningSumDelta = 0;
		double runningSumNorm = Double.MIN_VALUE;

		for (int j = 0; j < numberOfTestPoints; j++) {

			double r = (calculatedValuesLower.getDouble(j) / calculatedValuesHigher.getDouble(j));

			double rDelta = r * Math.pow(Math.pow(r, 2) + Math.pow(Math.pow(calculatedValuesLower.getDouble(j), 0.5)
					/ Math.pow(calculatedValuesHigher.getDouble(j), 0.5), 2), 0.5);

			rDelta = 1 / Math.pow(rDelta, 2);

			runningSumNorm += rDelta;
			runningSumDelta += r * rDelta;

		}

		double[] rMSLowerHigher = new double[] { calculatedValuesLowerRMS, calculatedValuesHigherRMS };

		double temp = runningSumDelta / runningSumNorm;

		double[] runningSumNormArray = new double[] { runningSumNorm };

		double[] correction = new double[] { (temp) * attenuationFactor };

		return new double[][] { null, null, correction, runningSumNormArray, rMSLowerHigher };

	}

	public static double maxValue(Dataset[] input) {

		double maxValue = input[0].getDouble(0);

		for (int i = 1; i < input.length; i++) {
			if (input[i].getDouble(0) > maxValue) {
				maxValue = input[i].getDouble(0);
			}
		}

		return maxValue;
	}

	public static double minValue(Dataset[] input) {

		double minValue = input[0].getDouble(0);

		for (int i = 1; i < input.length; i++) {
			if (input[i].getDouble(0) < minValue) {
				minValue = input[i].getDouble(0);
			}
		}

		return minValue;
	}
}
