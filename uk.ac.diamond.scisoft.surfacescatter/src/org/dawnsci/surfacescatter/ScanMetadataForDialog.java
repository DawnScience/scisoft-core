/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/*This class extracts metadata used in reflectivity operations.
 * 
 */

public class ScanMetadataForDialog {

	private static final Logger logger = LoggerFactory.getLogger(ScanMetadataForDialog.class);

	//
//	public static SliceFromSeriesMetadata getSliceMetadata (IDataset input) {
//		
//		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
//		return ssm;
//		
//	}
	
	
	public static double getTheta(ILazyDataset dcdtheta, int k, IDataset input, SuperModel sm) throws Exception {
				
		IDataset dcdthetaout = null;
		
//		try{
//
//			ILazyDataset dcdthetaL = ProcessingUtilsForDialog.getLazyDataset(sm.getFilepaths()[sm.getSelection()], ReflectivityMetadataTitlesForDialog.getdcdtheta());
//			
//			dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
//			
//		} catch (Exception e){
//		    try{
//		    	ILazyDataset dcdthetaL = ProcessingUtilsForDialog.getLazyDataset(sm.getFilepaths()[sm.getSelection()], ReflectivityMetadataTitlesForDialog.getsdcdtheta());
//				dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
//			} catch (Exception e2){
//				System.out.println("No theta parameter");
//				throw new Exception("No theta parameter");
//			}
//		}
		SliceND slice = new SliceND(dcdtheta.getShape());
		
		double theta = (double) dcdtheta.getSlice(slice).getDouble(k);
		return theta;
		
	}
	
	public IDataset  getqdcd(IDataset input, SuperModel sm) {
		IDataset qdcdDataset=null;
		try {
			qdcdDataset = ProcessingUtils.getLazyDataset(null, sm.getFilepaths()[sm.getSelection()], ReflectivityMetadataTitlesForDialog.getqdcd()).getSlice();
		} catch (OperationException | DatasetException e) {
			// TODO Auto-generated catch block
		}
		return qdcdDataset;
		
	}
	
	
	public ILazyDataset getqdcdLazy (IDataset input, SuperModel sm){ 
		ILazyDataset getqdcdLazy  = ProcessingUtils.getLazyDataset(null, sm.getFilepaths()[sm.getSelection()], ReflectivityMetadataTitlesForDialog.getqdcd());
		return getqdcdLazy;
	}
	

	public ILazyDataset adc2DataLazy (String path) {
		ILazyDataset adc2data= null;
		try {
			adc2data = ProcessingUtils.getLazyDataset(null, path, ReflectivityMetadataTitlesForDialog.getadc2()).getSlice();
		} catch (OperationException | DatasetException e) {
			// TODO Auto-generated catch block
		}
		 return adc2data;
	}
		 
	public ILazyDataset qdcd_dataDataLazy (String path) {	 
		ILazyDataset qdcd_data= null;
		try {
			qdcd_data = ProcessingUtils.getLazyDataset(null, path, ReflectivityMetadataTitlesForDialog.getqdcd_()).getSlice();
		} catch (OperationException | DatasetException e) {
			// TODO Auto-generated catch block
			logger.error("Error getting ddcd data: " + e.getMessage());
		}
		 return qdcd_data;
	}

}

//TEST