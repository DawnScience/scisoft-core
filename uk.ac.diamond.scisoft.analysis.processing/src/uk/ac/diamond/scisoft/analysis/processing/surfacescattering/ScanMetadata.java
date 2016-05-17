/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.surfacescattering;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

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

			ILazyDataset dcdthetaL = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), "dcdtheta");
			dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
			
		} catch (Exception e){
		    try{
		    	ILazyDataset dcdthetaL = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), "sdcdtheta");
				dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
			} catch (Exception e2){
				System.out.println("No theta parameter");
				throw new Exception("No theta parameter");
			}
		}
		double theta = (double) dcdtheta.getDouble(0);
		System.out.println("Scan Metadata theta: " + theta);
		return theta;
		
	}
	
	public IDataset  getqdcd(IDataset input) {
		IDataset qdcdDataset = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), "qdcd").getSlice();
		return qdcdDataset;
		
	}
	
	
	public ILazyDataset getqdcdLazy (IDataset input){ 
		ILazyDataset getqdcdLazy  = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), "qdcd");
		return getqdcdLazy;
	}
	

	public ILazyDataset adc2DataLazy (Polynomial1DReflectivityModel model) {
		ILazyDataset adc2data = ProcessingUtils.getLazyDataset(null, model.getPath(), adc2).getSlice();
		 return adc2data;
	}
		 
	public ILazyDataset qdcd_dataDataLazy (Polynomial1DReflectivityModel model) {	 
		ILazyDataset qdcd_data = ProcessingUtils.getLazyDataset(null, model.getPath(), qdcd_).getSlice();
		 return qdcd_data;
	}

}
