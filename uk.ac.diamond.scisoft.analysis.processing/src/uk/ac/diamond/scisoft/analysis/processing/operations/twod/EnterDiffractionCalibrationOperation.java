/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public class EnterDiffractionCalibrationOperation extends AbstractOperation<EnterDiffractionCalibrationModel, OperationData> {

	
	private volatile IDiffractionMetadata metadata;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.EnterDiffractionCalibrationOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		input.setMetadata(getMeta(model,input));
		return new OperationData(input);
	}
	
	
	private IDiffractionMetadata getMeta(EnterDiffractionCalibrationModel mod, IDataset input) {

		IDiffractionMetadata lmeta = metadata;
		if (lmeta == null) {
			synchronized(this) {
				lmeta = metadata;
				if (lmeta == null) {
					
					DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(input.getShape());
					dp.setHPxSize(model.getPixelSize());
					dp.setVPxSize(model.getPixelSize());
					dp.setBeamCentreCoords(new double[]{model.getBeamCentreX(),model.getBeamCentreY()});
					dp.setNormalAnglesInDegrees(model.getYaw(), model.getPitch(), model.getRoll());
					dp.setBeamCentreDistance(model.getDetectorDistance());

					DiffractionCrystalEnvironment de = new DiffractionCrystalEnvironment(1);
					de.setWavelengthFromEnergykeV(model.getEnergy());
					
					DiffractionMetadata md = new DiffractionMetadata("", dp, de);
					
					metadata = lmeta = md;
					
				}
			}
		}
		return lmeta;
	}
	
	@Override
	public void setModel(EnterDiffractionCalibrationModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					metadata = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}

}
