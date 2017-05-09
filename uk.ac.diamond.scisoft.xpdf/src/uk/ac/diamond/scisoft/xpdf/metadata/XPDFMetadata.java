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

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.MetadataType;

import uk.ac.diamond.scisoft.xpdf.XPDFAbsorptionMaps;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamTrace;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

/**
 * Interface for the metadata required by the XPDF processing pipeline.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
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
	
	/**
	 * Returns a list of the forms of the target components.
	 * <p>
	 * Returns a List of XPDFComponentForms of the sample, in the first
	 * position, and the containers, in the order they are stored within the 
	 * object.
	 */
	List<XPDFComponentForm> getFormList();
	
	/**
	 * Returns the object describing the detector used in the experiment.
	 * @return The object describing the detector used in the experiment. 
	 */
	XPDFDetector getDetector();
	
	/**
	 * Returns the corrected sample fluorescence.
	 * <p>
	 * Given arrays of the vertical and horizontal scattering angles, this
	 * function returns the fluorescence flux of the sample, corrected for
	 * absorption by the surrounding containers and for the relative
	 * transmission of the various energies of photon by the detector.
	 * @param delta
	 * 			Vertical scattering angle in radians.
	 * @param gamma
	 * 			Horizontal scattering angle in radians.
	 * @return the dataset containing the total sample fluorescence.
	 */
	Dataset getSampleFluorescence(Dataset gamma, Dataset delta);

	/**
	 * Returns the corrected sample fluorescence.
	 * <p>
	 * Given the XPDF coordinate object, this
	 * function returns the fluorescence flux of the sample, corrected for
	 * absorption by the surrounding containers and for the relative
	 * transmission of the various energies of photon by the detector.
	 * @param coords
	 * 			XPDF coordinates object
	 * @return the dataset containing the total sample fluorescence.
	 */
	Dataset getSampleFluorescence(XPDFCoordinates coords);

	
	/**
	 * Defines the geometry of undefined samples and containers.
	 * <p>
	 * Since samples are allowed to be defined by their containers, we need to
	 * define their geometry at some point. This is that point.
	 */
	void defineUndefinedSamplesContainers() throws Exception;
	
	/**
	 * Returns the sample data parameters.
	 * @return the sample data parameters.
	 */
	XPDFBeamTrace getSampleTrace();
	
	/**
	 * Returns the empty beam data parameters.
	 * @return the empty beam data parameters.
	 */
	XPDFBeamTrace getEmptyTrace();
	
	/**
	 * Returns the container data parameters.
	 * <p>
	 * If the requested container exists in the metadata parameters, return
	 * the parameters and data associated with it. Otherwise, return null.
	 * @param container
	 * 					{@link XPDFTargetComponent} object of the container for which the data is requested.
	 * @return the associated data and beam parameters.
	 */
	XPDFBeamTrace getContainerTrace(XPDFTargetComponent container);
	
	/**
	 * Gets the calibration constant used in the processing. 
	 * @return the value of the calibration constant
	 */
	double getCalibrationConstant();
	
	/**
	 * Sets the value of the calibration constant.
	 * @param calCon
	 * 				value of the calibration constant used
	 */
	void setCalibrationConstant(double calCon);
	
	/**
	 * Gets the scaling factor used for subtracting the fluorescence.
	 * @return the fluorescence scaling value used in the processing.
	 */
	double getFluorescenceScale();
	
	/**
	 * Sets the value of the fluorescence scaling
	 * @param scale
	 * 				scaling of the fluorescence
	 */
	void setFluorescenceScale(double scale);
	
	/**
	 * Gets the Lorch cut-off value.
	 * @return point in momentum transfer space at which the Lorch Fourier
	 * Transform was cut-off. In units of reciprocal angstroms.
	 */
	double getLorchCutOff();
	
	/**
	 * Sets the Lorch cut-off value
	 * @param cutOff
	 * 				Value of momentum transfer at which the Lorch Fourier
	 * Transform finished. 
	 */
	void setLorchCutOff(double cutOff);

}
