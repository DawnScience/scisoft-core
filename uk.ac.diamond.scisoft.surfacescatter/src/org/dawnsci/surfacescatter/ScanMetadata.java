/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class ScanMetadata {
	
	private static final String adc2 = "adc2";
	private static final String qdcd_ = "qdcd_";
	
	
	//
	public static SliceFromSeriesMetadata getSliceMetadata (IDataset input) {
		
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		return ssm;
		
	}
	
	
	public static double getTheta(IDataset input) throws Exception {
				
		IDataset dcdtheta = null;
		
		try{

			ILazyDataset dcdthetaL = ProcessingUtils.getLazyDataset(null, 
																	getSliceMetadata(input).getFilePath(), 
																	ReflectivityAngleAliasEnum.THETA.getAngleAlias());
			
//			"dcdtheta"
			
			dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
			
		} catch (DatasetException e){
			try {
				ILazyDataset dcdthetaL = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(),
						"sdcdtheta");
				dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
			} catch (DatasetException e2){
				System.out.println("No theta parameter");
				throw new Exception("No theta parameter");
			}
		}
		double theta = (double) dcdtheta.getDouble(0);
		System.out.println("Scan Metadata theta: " + theta);
		return theta;
		
	}
	
	public IDataset  getqdcd(IDataset input) {
		IDataset qdcdDataset = ProcessingUtils.getDataset(null, 
														  getSliceMetadata(input).getFilePath(), 
														  ReflectivityAngleAliasEnum.Q.getAngleAlias());
		return qdcdDataset;
		
	}
	
	
	public ILazyDataset getqdcdLazy (IDataset input){ 
		ILazyDataset getqdcdLazy  = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), "qdcd");
		return getqdcdLazy;
	}
	

//	public IDataset adc2DataLazy (Polynomial1DReflectivityModel model) {
//		IDataset adc2data = ProcessingUtils.getDataset(null, model.getPath(), adc2);
//		 return adc2data;
//	}
		 
	public IDataset qdcd_dataDataLazy (Polynomial1DReflectivityModel model) {	 
		IDataset qdcd_data = ProcessingUtils.getDataset(null, model.getPath(), qdcd_);
		 return qdcd_data;
	}

}
