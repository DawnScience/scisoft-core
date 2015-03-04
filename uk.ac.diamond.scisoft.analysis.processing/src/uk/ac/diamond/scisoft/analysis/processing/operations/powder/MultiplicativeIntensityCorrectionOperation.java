/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;

public class MultiplicativeIntensityCorrectionOperation extends
		AbstractOperation<MultiplicativeIntensityCorrectionModel, OperationData> {

	private Dataset correction;
	private IDiffractionMetadata metadata;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return this.getClass().getName();
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (!model.isApplyDetectorTransmissionCorrection() && !model.isApplyPolarisationCorrection() &&
				!model.isApplySolidAngleCorrection()) return new OperationData(input);
		
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);
		
		if (metadata == null || !(metadata.getDetector2DProperties().equals(md.getDetector2DProperties()) &&
				metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment()))) {
			metadata = md;
			correction = null;
		}
		
		if (correction == null) correction = calculateCorrectionArray(input, metadata);
		
		Dataset corrected = Maths.multiply(input,correction);
		
		copyMetadata(input, corrected);
		
		return new OperationData(corrected);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	@Override
	public void setModel(MultiplicativeIntensityCorrectionModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					MultiplicativeIntensityCorrectionOperation.this.correction = null;
				}
			};
		} else {
			model.removePropertyChangeListener(listener);
		}
		
		model.addPropertyChangeListener(listener);
	}
	
	private Dataset calculateCorrectionArray(IDataset data, IDiffractionMetadata md) {
		
		MultiplicativeIntensityCorrectionModel m = (MultiplicativeIntensityCorrectionModel)model;
		
		DoubleDataset cor = new DoubleDataset(data.getShape());

		Dataset tth = PixelIntegrationUtils.generate2ThetaArrayRadians(data.getShape(), md);
		
		Dataset az = null;
		if (m.isApplyPolarisationCorrection()) {
			az = PixelIntegrationUtils.generateAzimuthalArray(data.getShape(), md, true);
			az.iadd(Math.toRadians(m.getPolarisationAngularOffset()));
		}

		IndexIterator it = cor.getIterator();
		
		while (it.hasNext()) {
			double val = 1;
			double tthval = tth.getElementDoubleAbs(it.index);
			
			if (m.isApplySolidAngleCorrection()) {
				val = PixelIntegrationUtils.solidAngleCorrection(val, tthval);
			}
			
			if (m.isApplyDetectorTransmissionCorrection()) {
				val = PixelIntegrationUtils.detectorTranmissionCorrection(val, tthval, m.getTransmittedFraction());
			}
			
			if (m.isApplyPolarisationCorrection()) {
				double azval = az.getElementDoubleAbs(it.index);
				val = PixelIntegrationUtils.polarisationCorrection(val, tthval, azval, m.getPolarisationFactor());
			}
			cor.setAbs(it.index, val);
			
		}
		
		return cor;
	}

}
