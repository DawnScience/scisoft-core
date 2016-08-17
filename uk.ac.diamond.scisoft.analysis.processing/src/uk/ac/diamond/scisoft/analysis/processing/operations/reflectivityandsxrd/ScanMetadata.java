/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/*This class extracts metadata used in reflectivity operations.
 * 
 */

public class ScanMetadata {

	private static final Logger logger = LoggerFactory.getLogger(ScanMetadata.class);

	//
	public static SliceFromSeriesMetadata getSliceMetadata (IDataset input) {
		
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		return ssm;
		
	}
	
	
	public static double getTheta(IDataset input) throws Exception {
				
		IDataset dcdtheta = null;
		
		try{

			ILazyDataset dcdthetaL = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), ReflectivityMetadataTitles.getdcdtheta());
			dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
			
		} catch (Exception e){
		    try{
		    	ILazyDataset dcdthetaL = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), ReflectivityMetadataTitles.getsdcdtheta());
				dcdtheta = getSliceMetadata(input).getMatchingSlice(dcdthetaL);
			} catch (Exception e2){
				System.out.println("No theta parameter");
				throw new Exception("No theta parameter");
			}
		}
		double theta = (double) dcdtheta.getDouble(0);
		return theta;
		
	}
	
	public IDataset  getqdcd(IDataset input) {
		IDataset qdcdDataset=null;
		try {
			qdcdDataset = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), ReflectivityMetadataTitles.getqdcd()).getSlice();
		} catch (OperationException | DatasetException e) {
			// TODO Auto-generated catch block
		}
		return qdcdDataset;
		
	}
	
	
	public ILazyDataset getqdcdLazy (IDataset input){ 
		ILazyDataset getqdcdLazy  = ProcessingUtils.getLazyDataset(null, getSliceMetadata(input).getFilePath(), ReflectivityMetadataTitles.getqdcd());
		return getqdcdLazy;
	}
	

	public ILazyDataset adc2DataLazy (FluxCorrectionModel model) {
		ILazyDataset adc2data= null;
		try {
			adc2data = ProcessingUtils.getLazyDataset(null, model.getPath(), ReflectivityMetadataTitles.getadc2()).getSlice();
		} catch (OperationException | DatasetException e) {
			// TODO Auto-generated catch block
		}
		 return adc2data;
	}
		 
	public ILazyDataset qdcd_dataDataLazy (FluxCorrectionModel model) {	 
		ILazyDataset qdcd_data= null;
		try {
			qdcd_data = ProcessingUtils.getLazyDataset(null, model.getPath(), ReflectivityMetadataTitles.getqdcd_()).getSlice();
		} catch (OperationException | DatasetException e) {
			// TODO Auto-generated catch block
			logger.error("Error getting ddcd data: " + e.getMessage());
		}
		 return qdcd_data;
	}

}

//TEST