/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Stats;

/** 
 * Implementation of the Savitzky Golay filter algorithm.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Savitzky%E2%80%93Golay_filter">Wikipedia page on the Savitzky Golay filter</a>
 */
public class SavitzkyGolay {
	private SavitzkyGolay() {
		
	}
	
	/**
	 * @param data Dataset that will be filtered
	 * @param window The length of the filter window (i.e. the number of coefficients). Must be a positive odd integer.
	 * @param poly The order of the polynomial used to fit the samples. Must be less than window.
	 * @return Filtered dataset
	 */
	public static Dataset filter(IDataset data, int window, int poly) {
		return filter(data, window, poly, 0);
	}

	/**
	 * @param data Dataset that will be filtered
	 * @param window The length of the filter window (i.e. the number of coefficients). Must be a positive odd integer.
	 * @param poly The order of the polynomial used to fit the samples. Must be less than window.
	 * @param deriv The order of the derivative to compute. Must be a non-negative integer. Use zero if no derivative is required. 
	 * @return Filtered dataset
	 */
	public static Dataset filter(IDataset data, int window, int poly, int deriv) {
		Dataset rv = null;
		
		int n;
		
		//sanity check
		if (data.getRank() == 1) {
			n = data.getShape()[0];
		} else if (data.getRank() == 2) {
			n = data.getShape()[1];
		} else {
			throw new IllegalArgumentException("data must be either one- or two-dimensional");
		}
		
		if (window < 3 || window % 2 == 0)
			throw new IllegalArgumentException("window must be a positive odd integer");
		
		if (poly < 1 || poly >= window)
			throw new IllegalArgumentException("poly must be less than window");
		
		if (deriv < 0)
			throw new IllegalArgumentException("deriv must greater than or equal to zero");
		
		int p = (window - 1)/2;
		
		Dataset x1 = LinearAlgebra.outerProduct(DatasetFactory.createRange(DoubleDataset.class, -p, p + 1.0, 1), DatasetFactory.ones(DoubleDataset.class, 1, 1 + poly)).squeeze();
		Dataset x2 = LinearAlgebra.outerProduct(DatasetFactory.ones(DoubleDataset.class, 1, window), DatasetFactory.createRange(DoubleDataset.class, poly + 1.0)).squeeze();
		Dataset x3 = Maths.power(x1, x2);	
		
		// solveSVD seems to correspond to numpy's lstsq
		Dataset weights = LinearAlgebra.solveSVD(x3, DatasetUtils.eye(window, window, 0, Dataset.FLOAT64));

		Dataset coeff = null;
		
		if (deriv > 0) {
			Dataset coeff1 = LinearAlgebra.outerProduct(DatasetFactory.ones(DoubleDataset.class, deriv, 1), DatasetFactory.createRange(DoubleDataset.class, 1, poly + 2.0 - deriv, 1));
			Dataset coeff2 = LinearAlgebra.outerProduct(DatasetFactory.createRange(DoubleDataset.class, deriv), DatasetFactory.ones(DoubleDataset.class, poly + 1 - deriv));
			coeff = Stats.product(Maths.add(coeff1, coeff2), 0).squeeze();
		} else {
			coeff = DatasetFactory.ones(DoubleDataset.class, poly + 1);
		}
	
		Dataset outerTemp = LinearAlgebra.outerProduct(DatasetFactory.ones(DoubleDataset.class, n, 1), weights.getSlice(new int[]{deriv, 0}, new int[]{deriv + 1, weights.getShapeRef()[1]}, null)).imultiply(coeff.getDouble(0)).getTransposedView().squeeze().getSlice();
		
		Dataset arangeTemp = DatasetFactory.createRange(IntegerDataset.class, p, -p-1.0, -1);
		
		OpenMapRealMatrix sparseD = new OpenMapRealMatrix(n, n);
		//fill up the sparse matrix with our data. Basically do what scipy's spdiags does...
		
		for (int i = 0 ; i < arangeTemp.getSize() ; ++i) {
			int diag = arangeTemp.getInt(i);
			
			Dataset diagSlice = outerTemp.getSlice(new int[]{i,0}, new int[]{i+1,outerTemp.getShapeRef()[1]}, null).squeeze();
			
			for (int row = -diag ; row < n ; ++row) {
				int column = row + diag;
				
				if (column < 0 || row < 0 || column >= n || row >= n)
					continue;
				
				sparseD.setEntry(row, column, diagSlice.getDouble(row));
			}
		}
		
		Dataset tail = LinearAlgebra.dotProduct(DatasetUtils.diag(coeff, 0), weights.getSlice(new int[]{deriv, 0}, new int[]{poly + 1, weights.getShapeRef()[1]} , null).squeeze());
		
		Dataset dotTemp = LinearAlgebra.dotProduct(x3.getSlice(null, new int[]{p+1, poly - deriv +1}, null).squeeze(), tail).getTransposedView().getSlice();
		DoubleDataset dotTempDouble = DatasetUtils.cast(DoubleDataset.class, dotTemp);
		double[][] dotTempPrim = convertDoubleDataset2DtoPrimitive(dotTempDouble);
		sparseD.setSubMatrix(dotTempPrim, 0, 0);
		
		Dataset dotTemp2 = LinearAlgebra.dotProduct(x3.getSlice(new int[]{p, 0}, new int[]{window, poly - deriv +1}, null).squeeze(), tail).getTransposedView().getSlice();
		DoubleDataset dotTempDouble2 = DatasetUtils.cast(DoubleDataset.class, dotTemp2);
		sparseD.setSubMatrix(convertDoubleDataset2DtoPrimitive(dotTempDouble2), n-window-1, n-p-2);
		
		RealMatrix dataMatrix = null;
		
		if (data.getRank() == 1) {
			dataMatrix = new Array2DRowRealMatrix(DatasetUtils.cast(DoubleDataset.class, data).getData());
			dataMatrix = dataMatrix.transpose();
		} else {
			dataMatrix = new Array2DRowRealMatrix(convertDoubleDataset2DtoPrimitive(DatasetUtils.cast(DoubleDataset.class, data)), false);
		}
			
		RealMatrix rvMatrix = sparseD.preMultiply(dataMatrix);
		
		rv = DatasetFactory.createFromObject(Stream.of(rvMatrix.getData()).flatMapToDouble(Arrays::stream).toArray(), data.getShape());
		
		return rv;
	}
	
	private static double[][] convertDoubleDataset2DtoPrimitive(DoubleDataset dataset) {
		if (dataset.getRank() != 2)
			throw new IllegalArgumentException("dataset Shape must be 2D");
		
		double[][] rv = new double[dataset.getShape()[0]][dataset.getShape()[1]];
		
		for (int row = 0 ; row < dataset.getShape()[0] ; row++) {
			System.arraycopy(dataset.getData(), row * dataset.getShape()[1], rv[row], 0, dataset.getShape()[1]);
		}
		
		return rv;
	}
	
}
