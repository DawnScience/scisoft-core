/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.SliceND;

public class LinearLeastSquaresServicesForSXRD {

	
	public static Dataset polynomial2DLinearLeastSquaresMatrixGenerator (int degree, Dataset X, Dataset Y){
		
		int noParams = (int) Math.pow(degree+1, 2);
		int datasize = X.getShape()[0];
		
		
		Dataset testMatrix = DatasetFactory.ones(new int[] {datasize, noParams}, Dataset.FLOAT64);
		
		SliceND s = new SliceND(testMatrix.getShape());
		s.setSlice(1, 0, 1, 1);
		testMatrix.setSlice(X, s);
		
		int p = 0;
		
		
		for (int k =0; k<datasize; k++){
			for (int i =0; i<degree+1 ; i++){
				for (int j=0; j<degree+1; j++){
					
					double x = X.getDouble(k);
					double y = Y.getDouble(k);
					
					double xFunc = Math.pow(x, i);
					double yFunc = Math.pow(y, j);
					
					
					testMatrix.set(xFunc*yFunc, k, p);
					
					p++;
					if(p == (degree+1)*(degree+1)){
						p=0;
					}
					
				}
			}
		}
		
	return testMatrix;
	
	}
	
	public  static Dataset polynomial2DLinearLeastSquaresSigmaGenerator (Dataset Z){
		
		
		int datasize = Z.getShape()[0];
		
		Dataset sigmaMatrix = DatasetFactory.ones(new int[] {datasize}, Dataset.FLOAT64);
		
		
		for (int k =0; k<datasize; k++){

			double z = Z.getDouble(k);
			double zSigma = Math.pow(z, 0.5);
				
			sigmaMatrix.set(zSigma, k);
		
		}
		
		
		
	return sigmaMatrix;
	
	}
}
