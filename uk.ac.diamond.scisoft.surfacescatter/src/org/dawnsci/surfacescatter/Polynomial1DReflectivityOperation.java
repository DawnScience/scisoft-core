/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class Polynomial1DReflectivityOperation extends AbstractOperation<Polynomial1DReflectivityModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.surfacescattering.Polynomial1DReflectivityOperation";
	}

	private static final String adc2 = "adc2";
	private static final String qdcd_ = "qdcd_";
	@SuppressWarnings("unused")
	private static final String ionc1 = "ionc1";
	@SuppressWarnings("unused")
	private IDataset adc2data;
	@SuppressWarnings("unused")
	private IDataset qdcd_data;
	@SuppressWarnings("unused")
	private Object logger;
	
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO ;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO ;
	}
	
	@Override
	public void init() {
		if (((boolean) (model.getPath()).equalsIgnoreCase("NO")) == false) { 
			adc2data = ProcessingUtils.getDataset(this, model.getPath(), adc2);
			qdcd_data = ProcessingUtils.getDataset(this, model.getPath(), qdcd_);
			
		}
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {

		RectangularROI box = model.getBox();
		
		Dataset in1 = BoxSlicer.rOIBox(input, monitor, box.getIntLengths(), box.getIntPoint());
		
		IDataset[] background = new IDataset[2];
				
		background[0] = BoxSlicer.iBelowOrRightBox(input, monitor, box.getIntLengths(), box.getIntPoint()
													, model.getBoundaryBox(), model.getDirection());
		
		background[1] = BoxSlicer.iAboveOrLeftBox(input, monitor, box.getIntLengths(), box.getIntPoint()
				, model.getBoundaryBox(), model.getDirection());
		
		
		Dataset in1Background = DatasetFactory.zeros(in1.getShape(), Dataset.FLOAT64);
		
		in1Background = BackgroundSetting.rOIBackground1(background, in1Background
				, box.getIntLengths(), box.getIntPoint()
				, model.getBoundaryBox(), model.getFitPower()
				, model.getDirection());
				
		IndexIterator it = in1Background.getIterator();
		
		while (it.hasNext()) {
			double v = in1Background.getElementDoubleAbs(it.index);
			if (v < 0) in1Background.setObjectAbs(it.index, 0);
		}

		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);

		pBackgroundSubtracted.setName("PBackgroundSubtracted");


		double areaCorrection = 0;
		
		try {
			areaCorrection = ReflectivityCorrections.reflectivityCorrectionsBatch(input
					, model.getAngularFudgeFactor() 
					, model.getBeamHeight(), model.getFootprint());
		} catch (Exception e1) {
			System.out.println("failed to get areaCorrection");
		}
		
		pBackgroundSubtracted = Maths.divide(pBackgroundSubtracted,areaCorrection);
		
		SliceFromSeriesMetadata tmp1 = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		IDataset m = null;
		
		Dataset[] fluxData = RecoverNormalisationFluxBatch.normalisationFlux(input, model.getPath());
		
		ILazyDataset qdcd = null;
		if ((boolean) (model.getPath().equalsIgnoreCase("NO") ||(model.getPath().equalsIgnoreCase(null)))){
			qdcd = ProcessingUtils.getDataset(this, model.getPath(), "qsdcd");
		} else {
			qdcd = ProcessingUtils.getDataset(this,tmp1.getFilePath(), "qdcd");
		}
		try {
			m = tmp1.getMatchingSlice(qdcd);
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		
		Dataset flux =  (Dataset) Interpolation1D.splineInterpolation(fluxData[0], fluxData[1], m);;
		
	for ( int n =0; n<flux.getShape()[0]; n++){
			double test2 = flux.getElementDoubleAbs(n);
			System.out.println("testing flux dataset, n: " + n + " , " + " value: " + test2);
		}
		
		pBackgroundSubtracted = Maths.divide(pBackgroundSubtracted,flux);
		
		IndexIterator it1 = pBackgroundSubtracted.getIterator();
		
		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (q < 0) pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}
		
		Dataset in1Sum = DatasetFactory.createFromObject(in1.sum());
		Dataset in1BackgroundSum = DatasetFactory.createFromObject(in1Background.sum());
 		Dataset pBackgroundSubtractedSum = DatasetFactory.createFromObject(pBackgroundSubtracted.sum());
		
		in1.setName("Region of Interest");
		in1Background.setName("Polynomial background");
		pBackgroundSubtracted.setName("Signal after polynomial background subtracted");
		
		in1Sum.setName("Region of Interest Summed") ;
		in1BackgroundSum.setName("Polynomial background summed");
		pBackgroundSubtractedSum.setName("Signal after polynomial background subtracted summed");
		
		
		return new OperationData(in1, in1Background, pBackgroundSubtracted, in1Sum, in1BackgroundSum, pBackgroundSubtractedSum);

	}
}
