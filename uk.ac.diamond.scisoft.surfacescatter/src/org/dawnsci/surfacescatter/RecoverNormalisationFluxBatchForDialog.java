/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
/**This class is generate an array ([2]) datasets of the X-ray flux at given theta values. These are used to correct X-ray reflectivity
 * measurements 
 * 
 * 
 */
public class RecoverNormalisationFluxBatchForDialog {
	
	
	public static Dataset[] normalisationFlux(String path, ExampleModel model){
	
		
		ILazyDataset flux = null;
		ILazyDataset theta = null;
		Dataset[] fluxData = new Dataset[2];
		
		
		if (model.getFlux() == null) {
			try{ 
				IDataHolder dh1 =LoaderFactory.getData(path);
				flux =dh1.getLazyDataset(ReflectivityMetadataTitlesForDialog.getadc2()); 
				model.setFlux(flux);
				
				theta =dh1.getLazyDataset(ReflectivityMetadataTitlesForDialog.getqdcd_()); 
				model.setTheta(theta);
			}
			catch (Exception e){
				System.out.println("No normalisation data availbale externally");
				}
		}
		
		else{
			flux = model.getFlux();
			theta = model.getTheta();
		}
		
		SliceND sliceF = new SliceND(flux.getShape());
		SliceND sliceT = new SliceND(theta.getShape());

		
		try {
			fluxData[0] = (Dataset) theta.getSlice(sliceT);
			fluxData[1]= (Dataset) flux.getSlice(sliceF);;
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fluxData;

	}
}

//TEST