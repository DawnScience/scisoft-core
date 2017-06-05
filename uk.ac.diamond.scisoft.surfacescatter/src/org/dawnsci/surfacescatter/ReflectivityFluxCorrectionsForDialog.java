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

	
	protected static Dataset reflectivityFluxCorrections (String path, Double k, ExampleModel model){

		Dataset m = DatasetFactory.zeros(new int[] {1});
		
		m.set(k, 0);
		
		
		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(path, model);
//		
//		if ((boolean) (path.equalsIgnoreCase("NO") ||(path.equalsIgnoreCase(null)))){
//			try {
//				SliceND slice = new SliceND(model.getQdcd().getShape());
//				qdcd.getSlice(slice).getDouble(k);
//			} catch (OperationException e) {
//				// TODO Auto-generated catch block
//				
//			} catch (DatasetException e) {
//				// TODO Auto-generated catch block
//			}
//		}
//		
//		else{
//			try {
//				qdcd = ProcessingUtilsForDialog.getLazyDataset(tmp1.getFilePath(), ReflectivityMetadataTitlesForDialog.getqdcd()).getSlice();
//				m = tmp1.getMatchingSlice(qdcd);
//			} catch (OperationException e) {
//				// TODO Auto-generated catch block
//			} catch (DatasetException e) {
//				// TODO Auto-generated catch block
//			}
//		}
		
		
		//test1
		System.out.println("m shape:  " + m.getShape()[0]);
		
		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;

		
		Dataset output = Maths.divide(1,flux);
		
		
		return output;
		}
	
	
	
	protected static double reflectivityFluxCorrectionsDouble (String path, Double k, ExampleModel model){

		Dataset m = DatasetFactory.zeros(new int[] {1});
		
		m.set(k, 0);
		
		
		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(path, model);

		System.out.println("m shape:  " + m.getShape()[0]);
		
		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;

		
		double output = Math.floorDiv((long)1,(long)flux.getDouble(0));
		
		return output;
		}
	
	
	public static double reflectivityFluxCorrectionsDouble (String path, Double k, String filepath){

		Dataset m = DatasetFactory.zeros(new int[] {1});
		
		m.set(k, 0);
		
		
		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(path, filepath);

//		System.out.println("m shape:  " + m.getShape()[0]);
		
		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;

		float fluxLong = (float)flux.getDouble(0);
		
		
		if(fluxLong == 0){
			System.out.println("££££££££££  fluxLong is zero  £$£$£$£");
		}
		
		float  output = 1/fluxLong;
		
		
		
		return (double) output;
		}
	
}
//test

