package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class TrackerLocationInterpolation {

	public static double[] trackerInterpolationInterpolator0(ArrayList<double[]> trackerLocations, Dataset sortedX,
			int[] len, int k) {

		ArrayList<Double> lList = new ArrayList<>();
		ArrayList<Double> xList = new ArrayList<>();
		ArrayList<Double> yList = new ArrayList<>();

		for (int h = 0; h < trackerLocations.size(); h++) {

			double[] tL = trackerLocations.get(h);

			if (!Arrays.equals(tL, new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 })) {
				lList.add(sortedX.getDouble(h));
				yList.add(tL[0]);
				xList.add(tL[1]);
			}

		}

		Dataset yValues = DatasetFactory.createFromObject(yList);
		Dataset xValues = DatasetFactory.createFromObject(xList);
		Dataset lValues = DatasetFactory.createFromObject(lList);

		return PolynomialOverlap.extrapolatedLocation(sortedX.getDouble(k), lValues, xValues, yValues, len, 1);

	}

}
