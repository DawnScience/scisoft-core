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
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class FluxDatasetGetter {
	
	
	public static Dataset[] normalisationFlux(IDataset input, String path){
	
		SliceFromSeriesMetadata tmp = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		Dataset flux = null;
		Dataset theta = null;
		Dataset[] fluxData = new Dataset[2];
		
		
		if ((boolean) (path.equalsIgnoreCase("NO") ||(path.equalsIgnoreCase(null)))) {
			try { 
				flux = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, tmp.getFilePath(), "ionc1")); 
				theta = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, tmp.getFilePath(), "qsdcd"));
			
			}
			catch (Exception e){
				System.out.println("No normalisation data available internally");
				
			}
		}
		else {
			try{
				flux = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, path, "adc2")); 
				theta = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, path, "qdcd_"));
				
			}
			catch (Exception e){
				System.out.println("No normalisation data available externally");
				}
		}
		
		fluxData[0] = theta;
		fluxData[1]= flux;
		
		return fluxData;

	}
}