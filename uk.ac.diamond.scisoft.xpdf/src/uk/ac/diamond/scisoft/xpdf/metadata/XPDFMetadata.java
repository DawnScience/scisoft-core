/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.xpdf.XPDFAbsorptionMaps;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

/**
 * Interface for the metadata required by the XPDF processing pipeline.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 *
 */
public interface XPDFMetadata extends MetadataType {

	/**
	 * Gets a list of the containers in the target.
	 * @return a list containing all the containers making up the target.
	 */
	List<XPDFTargetComponent> getContainers();

	/**
	 * Reorders the containers in the metadata.
	 * <p>
	 * Takes a mapping of the new ordinal to the original ordinal. The key is
	 * the new ordinal, the value is the original ordinal.
	 * @param newOrder
	 * 				a map of the new ordinal to the old ordinal.
	 */
	void reorderContainers(Map<Integer, Integer> newOrder);
	
	/**
	 * Getter for the sample properties.
	 * @return the sample properties.
	 */
	XPDFTargetComponent getSample();

	/**
	 * Getter for the beam properties.
	 * @return the beam properties.
	 */
	XPDFBeamData getBeam();

	/**
	 * Returns the number of illuminated sample atoms.
	 * <p>
	 * Returns the number of atoms in the sample illuminated by the X-ray beam.
	 * @return the number of illuminated atoms in the sample.
	 */
	double getSampleIlluminatedAtoms();
	
	/**
	 * 
	 * The absorption correction maps of the system. The metadata knows whether
	 * to use the cached values, or whether to call the absorption mapping
	 * function. The key is (index of scatterer, index of attenuator), ordered
	 * with the sample in position 0, and the other containers in order as one 
	 * goes outward 
	 */

	/**
	 * Returns the absorption maps object.
	 * <p>
	 * The maps within the absorption maps object are indexed by scatterer and
	 * attenuator index. The order of these indices matches the order of
	 * containers returned in this.getContainers(). 
	 * @param delta
	 * 			Vertical scattering angle in radians.
	 * @param gamma
	 * 			Horizontal scattering angle in radians.
	 * @return the absorption maps between each possible pair of target
	 * 			components, wrapped in their own class.
	 */
	XPDFAbsorptionMaps getAbsorptionMaps(Dataset delta, Dataset gamma);
}
