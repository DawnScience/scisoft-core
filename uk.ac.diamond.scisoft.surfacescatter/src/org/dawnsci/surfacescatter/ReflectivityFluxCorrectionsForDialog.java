/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;

public class ReflectivityFluxCorrectionsForDialog{

	
//	protected static Dataset reflectivityFluxCorrections (String path, Double k, ExampleModel model){
//
//		Dataset m = DatasetFactory.zeros(new int[] {1});
//		
//		m.set(k, 0);
//		
//		
//		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(path, model);
//
//		
//		
//		//test1
//		System.out.println("m shape:  " + m.getShape()[0]);
//		
//		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;
//
//		
//		Dataset output = Maths.divide(1,flux);
//		
//		
//		return output;
//		}
//	
//	
//	
//	protected static double reflectivityFluxCorrectionsDouble (String path, Double k, ExampleModel model){
//
//		Dataset m = DatasetFactory.zeros(new int[] {1});
//		
//		m.set(k, 0);
//		
//		
//		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(path, model);
//
//		System.out.println("m shape:  " + m.getShape()[0]);
//		
//		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;
//
//		
//		double output = Math.floorDiv((long)1,(long)flux.getDouble(0));
//		
//		return output;
//		}
//	
	
	public static double reflectivityFluxCorrectionsDouble (Double k, String filepath){

		Dataset m = DatasetFactory.zeros(new int[] {1});
		
		m.set(k, 0);
		
		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(filepath);

		for(int i =0; i<fluxData[0].getSize(); i++){
			
			double probe1 = fluxData[0].getDouble(i);
			
			for(int j =0; j<fluxData[0].getSize(); j++){
				
				double probe2 = fluxData[0].getDouble(j);
				
				if(probe1 == probe2 &&
						i !=j){
					
					fluxData[0].set(probe2*1.0000001, j);
				}
			}
		}
		
		
		Dataset flux =  DatasetFactory.createFromObject(0);
		try{
			flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;
		}
		catch(Exception f){
			System.out.println(f.getMessage());
		}
		float fluxLong = (float)flux.getDouble(0);
		
		
		if(fluxLong == 0){
			System.out.println("££££££££££  fluxLong is zero  £$£$£$£");
		}
		
		float  output = 1/fluxLong;
		
		
		
		return (double) output;
	}
	
}
//test

