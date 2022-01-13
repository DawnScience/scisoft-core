/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.decomposition;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.simple.SimpleMatrix;

public class PCA {
	
	/**
	 * Perform PCA on data of [Samples, Data], where Data is e.g. a spectrum or diffraction pattern,
	 * and Samples is the number of samples.
	 * 
	 * @param data
	 * @param nComponents
	 * @return pcaResult - result object
	 */
	public static PCAResult fit(Dataset data, int nComponents){
		Dataset mean = data.mean(0, true);
		Dataset centered = Maths.subtract(data, mean);
		int[] shape = centered.getShape();
		
//		DenseMatrix64F matrix = 
		DenseMatrix64F matrix = new DenseMatrix64F(shape[0], shape[1], true, DatasetUtils.cast(DoubleDataset.class, centered).getData());
 		
		SingularValueDecomposition<DenseMatrix64F> svd = DecompositionFactory.svd(shape[0], shape[1], false, true, true);
		svd.decompose(matrix);
		
		DenseMatrix64F V = svd.getV(null, true);
		DenseMatrix64F S = svd.getW(null);
		
		Dataset out = DatasetFactory.createFromObject(V.getData());
		out.setShape(new int[]{V.numRows, V.numCols});
		
		SliceND s = new SliceND(out.getShapeRef());
		s.setSlice(0,0,nComponents,1);
		
		Dataset comps = out.getSlice(s);
		
		Dataset scores = LinearAlgebra.dotProduct(centered, comps.transpose());
		
//		 Get variance explained by singular values
		SimpleMatrix simple = SimpleMatrix.wrap(S).extractDiag();
	    SimpleMatrix explainedVar = simple.elementMult(simple).divide((shape[0] - 1));
	    double totalVar = explainedVar.elementSum();
	    SimpleMatrix varRatio = explainedVar.divide(totalVar);
	    Dataset vExp = DatasetFactory.createFromObject(varRatio.getMatrix().getData());
		
		return new PCAResult(comps, scores, mean, vExp.getSlice(new Slice(0,nComponents,1)));
	}
	

}
