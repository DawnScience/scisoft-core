/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class ReflectivityFluxCorrectionOperation extends AbstractOperation<Polynomial2DReflectivityModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.ReflectivityFluxCorrectionOperation";
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO ;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO ;
	}
	
	
	protected OperationData process(IDataset input, IMonitor monitor){

		/*
		 * Method takes in a 2D image (Dataset) and returns flux corrected image.
		 */
		
		SliceFromSeriesMetadata tmp1 = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		IDataset m = null;
		
		Dataset[] fluxData = RecoverNormalisationFluxBatch.normalisationFlux(input, model.getPath());
		
		ILazyDataset qdcd = null;
		
		if ((boolean) (model.getPath().equalsIgnoreCase("NO") ||(model.getPath().equalsIgnoreCase(null)))){
			try {
				qdcd = ProcessingUtils.getLazyDataset(this, model.getPath(), ReflectivityMetadataTitles.getqsdcd()).getSlice();
				m = tmp1.getMatchingSlice(qdcd);
			} catch (OperationException e) {
				// TODO Auto-generated catch block
				
			} catch (DatasetException e) {
				// TODO Auto-generated catch block
			}
		}
		
		else{
			try {
				qdcd = ProcessingUtils.getLazyDataset(this,tmp1.getFilePath(), ReflectivityMetadataTitles.getqdcd()).getSlice();
				m = tmp1.getMatchingSlice(qdcd);
			} catch (OperationException e) {
				// TODO Auto-generated catch block
			} catch (DatasetException e) {
				// TODO Auto-generated catch block
			}
		}
		
		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;

		
		Dataset output = Maths.divide(input,flux);
		
		return new OperationData(output);
		}
}
//test

