/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

/**
 * This metadata describes if a dataset has axis information associated with it
 */
public interface ARPESMetadata extends MetadataType {

	// Constants for the scan
	public double getPhotonEnergy();
	public double getWorkFunction();
	
	// Axis associated with detector images
	public ILazyDataset getKineticEnergies();
	public ILazyDataset getAnalyserAngles();
	
	// Axis associated with the scan direction of the data
	public ILazyDataset getPloarAngles();
	public ILazyDataset getTiltAngles();
	public ILazyDataset getAzimuthalAngles();

	// Calibration Information
	public ILazyDataset getEnergyAxisOffset();
	
	// calculated axis accociated with frames
	public ILazyDataset getBindingEnergies();
	public ILazyDataset getPhotoelectronMomentum();
	
	
	// Methods to correct data
	
}
