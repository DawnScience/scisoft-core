/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Properties of the neutron or X-ray beam at a given location.
 * This group is intended to be referenced
 * by beamline component groups within the :ref:`NXinstrument` group or by the :ref:`NXsample` group. This group is
 * especially valuable in storing the results of instrument simulations in which it is useful
 * to specify the beam profile, time distribution etc. at each beamline component. Otherwise,
 * its most likely use is in the :ref:`NXsample` group in which it defines the results of the neutron
 * scattering by the sample, e.g., energy transfer, polarizations. Finally, There are cases where the beam is
 * considered as a beamline component and this group may be defined as a subgroup directly inside
 * :ref:`NXinstrument`, in which case it is recommended that the position of the beam is specified by an
 * :ref:`NXtransformations` group, unless the beam is at the origin (which is the sample).
 * Note that incident_wavelength and related fields can be a scalar values or arrays, depending on the use case.
 * To support these use cases, the explicit dimensionality of these fields is not specified, but it can be inferred
 * by the presense of and shape of accompanying fields, such as incident_wavelength_weights for a polychromatic beam.
 * <p><b>Symbols:</b> 
 * These symbols coordinate datasets with the same shape.<ul>
 * <li><b>nP</b> 
 * Number of scan points.</li>
 * <li><b>m</b> 
 * Number of channels in the incident beam spectrum, if known</li>
 * <li><b>c</b> 
 * Number of moments representing beam divergence (x, y, xy, etc.)</li></ul></p>
 * 
 */
public interface NXbeam extends NXobject {

	public static final String NX_DISTANCE = "distance";
	public static final String NX_INCIDENT_ENERGY = "incident_energy";
	public static final String NX_FINAL_ENERGY = "final_energy";
	public static final String NX_ENERGY_TRANSFER = "energy_transfer";
	public static final String NX_INCIDENT_WAVELENGTH = "incident_wavelength";
	public static final String NX_INCIDENT_WAVELENGTH_WEIGHTS = "incident_wavelength_weights";
	public static final String NX_INCIDENT_WAVELENGTH_SPREAD = "incident_wavelength_spread";
	public static final String NX_INCIDENT_BEAM_DIVERGENCE = "incident_beam_divergence";
	public static final String NX_EXTENT = "extent";
	public static final String NX_FINAL_WAVELENGTH = "final_wavelength";
	public static final String NX_INCIDENT_POLARIZATION = "incident_polarization";
	public static final String NX_FINAL_POLARIZATION = "final_polarization";
	public static final String NX_INCIDENT_POLARIZATION_STOKES = "incident_polarization_stokes";
	public static final String NX_FINAL_POLARIZATION_STOKES = "final_polarization_stokes";
	public static final String NX_FINAL_WAVELENGTH_SPREAD = "final_wavelength_spread";
	public static final String NX_FINAL_BEAM_DIVERGENCE = "final_beam_divergence";
	public static final String NX_FLUX = "flux";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Distance from sample. Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDistance();
	
	/**
	 * Distance from sample. Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distanceDataset the distanceDataset
	 */
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * Distance from sample. Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDistanceScalar();

	/**
	 * Distance from sample. Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distance the distance
	 */
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * Energy carried by each particle of the beam on entering the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_energy();
	
	/**
	 * Energy carried by each particle of the beam on entering the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param incident_energyDataset the incident_energyDataset
	 */
	public DataNode setIncident_energy(IDataset incident_energyDataset);

	/**
	 * Energy carried by each particle of the beam on entering the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getIncident_energyScalar();

	/**
	 * Energy carried by each particle of the beam on entering the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param incident_energy the incident_energy
	 */
	public DataNode setIncident_energyScalar(Double incident_energyValue);

	/**
	 * Energy carried by each particle of the beam on leaving the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFinal_energy();
	
	/**
	 * Energy carried by each particle of the beam on leaving the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param final_energyDataset the final_energyDataset
	 */
	public DataNode setFinal_energy(IDataset final_energyDataset);

	/**
	 * Energy carried by each particle of the beam on leaving the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFinal_energyScalar();

	/**
	 * Energy carried by each particle of the beam on leaving the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param final_energy the final_energy
	 */
	public DataNode setFinal_energyScalar(Double final_energyValue);

	/**
	 * Change in particle energy caused by the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEnergy_transfer();
	
	/**
	 * Change in particle energy caused by the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param energy_transferDataset the energy_transferDataset
	 */
	public DataNode setEnergy_transfer(IDataset energy_transferDataset);

	/**
	 * Change in particle energy caused by the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getEnergy_transferScalar();

	/**
	 * Change in particle energy caused by the beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param energy_transfer the energy_transfer
	 */
	public DataNode setEnergy_transferScalar(Double energy_transferValue);

	/**
	 * In the case of a monochromatic beam this is the scalar
	 * wavelength.
	 * Several other use cases are permitted, depending on the
	 * presence or absence of other incident_wavelength_X
	 * fields.
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of wavelengths, with the relative weights
	 * in ``incident_wavelength_weights``.
	 * In the case of a monochromatic beam that varies shot-
	 * to-shot, this is an array of wavelengths, one for each
	 * recorded shot. Here, ``incident_wavelength_weights`` and
	 * incident_wavelength_spread are not set.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is an array of length **m** with the relative
	 * weights in ``incident_wavelength_weights`` as a 2D array.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot and where the channels also vary, this is a 2D array
	 * of dimensions **nP** by **m** (slow to fast) with the
	 * relative weights in ``incident_wavelength_weights`` as a 2D
	 * array.
	 * Note, :ref:`variants <Design-Variants>` are a good way
	 * to represent several of these use cases in a single dataset,
	 * e.g. if a calibrated, single-value wavelength value is
	 * available along with the original spectrum from which it
	 * was calibrated.
	 * Wavelength on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_wavelength();
	
	/**
	 * In the case of a monochromatic beam this is the scalar
	 * wavelength.
	 * Several other use cases are permitted, depending on the
	 * presence or absence of other incident_wavelength_X
	 * fields.
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of wavelengths, with the relative weights
	 * in ``incident_wavelength_weights``.
	 * In the case of a monochromatic beam that varies shot-
	 * to-shot, this is an array of wavelengths, one for each
	 * recorded shot. Here, ``incident_wavelength_weights`` and
	 * incident_wavelength_spread are not set.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is an array of length **m** with the relative
	 * weights in ``incident_wavelength_weights`` as a 2D array.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot and where the channels also vary, this is a 2D array
	 * of dimensions **nP** by **m** (slow to fast) with the
	 * relative weights in ``incident_wavelength_weights`` as a 2D
	 * array.
	 * Note, :ref:`variants <Design-Variants>` are a good way
	 * to represent several of these use cases in a single dataset,
	 * e.g. if a calibrated, single-value wavelength value is
	 * available along with the original spectrum from which it
	 * was calibrated.
	 * Wavelength on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param incident_wavelengthDataset the incident_wavelengthDataset
	 */
	public DataNode setIncident_wavelength(IDataset incident_wavelengthDataset);

	/**
	 * In the case of a monochromatic beam this is the scalar
	 * wavelength.
	 * Several other use cases are permitted, depending on the
	 * presence or absence of other incident_wavelength_X
	 * fields.
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of wavelengths, with the relative weights
	 * in ``incident_wavelength_weights``.
	 * In the case of a monochromatic beam that varies shot-
	 * to-shot, this is an array of wavelengths, one for each
	 * recorded shot. Here, ``incident_wavelength_weights`` and
	 * incident_wavelength_spread are not set.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is an array of length **m** with the relative
	 * weights in ``incident_wavelength_weights`` as a 2D array.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot and where the channels also vary, this is a 2D array
	 * of dimensions **nP** by **m** (slow to fast) with the
	 * relative weights in ``incident_wavelength_weights`` as a 2D
	 * array.
	 * Note, :ref:`variants <Design-Variants>` are a good way
	 * to represent several of these use cases in a single dataset,
	 * e.g. if a calibrated, single-value wavelength value is
	 * available along with the original spectrum from which it
	 * was calibrated.
	 * Wavelength on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getIncident_wavelengthScalar();

	/**
	 * In the case of a monochromatic beam this is the scalar
	 * wavelength.
	 * Several other use cases are permitted, depending on the
	 * presence or absence of other incident_wavelength_X
	 * fields.
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of wavelengths, with the relative weights
	 * in ``incident_wavelength_weights``.
	 * In the case of a monochromatic beam that varies shot-
	 * to-shot, this is an array of wavelengths, one for each
	 * recorded shot. Here, ``incident_wavelength_weights`` and
	 * incident_wavelength_spread are not set.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is an array of length **m** with the relative
	 * weights in ``incident_wavelength_weights`` as a 2D array.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot and where the channels also vary, this is a 2D array
	 * of dimensions **nP** by **m** (slow to fast) with the
	 * relative weights in ``incident_wavelength_weights`` as a 2D
	 * array.
	 * Note, :ref:`variants <Design-Variants>` are a good way
	 * to represent several of these use cases in a single dataset,
	 * e.g. if a calibrated, single-value wavelength value is
	 * available along with the original spectrum from which it
	 * was calibrated.
	 * Wavelength on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param incident_wavelength the incident_wavelength
	 */
	public DataNode setIncident_wavelengthScalar(Double incident_wavelengthValue);

	/**
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of the relative weights of the corresponding
	 * wavelengths in ``incident_wavelength``.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is a 2D array of dimensions **nP** by **m**
	 * (slow to fast) of the relative weights of the
	 * corresponding wavelengths in ``incident_wavelength``.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_wavelength_weights();
	
	/**
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of the relative weights of the corresponding
	 * wavelengths in ``incident_wavelength``.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is a 2D array of dimensions **nP** by **m**
	 * (slow to fast) of the relative weights of the
	 * corresponding wavelengths in ``incident_wavelength``.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @param incident_wavelength_weightsDataset the incident_wavelength_weightsDataset
	 */
	public DataNode setIncident_wavelength_weights(IDataset incident_wavelength_weightsDataset);

	/**
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of the relative weights of the corresponding
	 * wavelengths in ``incident_wavelength``.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is a 2D array of dimensions **nP** by **m**
	 * (slow to fast) of the relative weights of the
	 * corresponding wavelengths in ``incident_wavelength``.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getIncident_wavelength_weightsScalar();

	/**
	 * In the case of a polychromatic beam this is an array of
	 * length **m** of the relative weights of the corresponding
	 * wavelengths in ``incident_wavelength``.
	 * In the case of a polychromatic beam that varies shot-to-
	 * shot, this is a 2D array of dimensions **nP** by **m**
	 * (slow to fast) of the relative weights of the
	 * corresponding wavelengths in ``incident_wavelength``.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 * 
	 * @param incident_wavelength_weights the incident_wavelength_weights
	 */
	public DataNode setIncident_wavelength_weightsScalar(Double incident_wavelength_weightsValue);

	/**
	 * The wavelength spread FWHM for the corresponding
	 * wavelength(s) in incident_wavelength.
	 * In the case of shot-to-shot variation in the wavelength
	 * spread, this is a 2D array of dimension **nP** by
	 * **m** (slow to fast) of the spreads of the
	 * corresponding wavelengths in incident_wavelength.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_wavelength_spread();
	
	/**
	 * The wavelength spread FWHM for the corresponding
	 * wavelength(s) in incident_wavelength.
	 * In the case of shot-to-shot variation in the wavelength
	 * spread, this is a 2D array of dimension **nP** by
	 * **m** (slow to fast) of the spreads of the
	 * corresponding wavelengths in incident_wavelength.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @param incident_wavelength_spreadDataset the incident_wavelength_spreadDataset
	 */
	public DataNode setIncident_wavelength_spread(IDataset incident_wavelength_spreadDataset);

	/**
	 * The wavelength spread FWHM for the corresponding
	 * wavelength(s) in incident_wavelength.
	 * In the case of shot-to-shot variation in the wavelength
	 * spread, this is a 2D array of dimension **nP** by
	 * **m** (slow to fast) of the spreads of the
	 * corresponding wavelengths in incident_wavelength.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getIncident_wavelength_spreadScalar();

	/**
	 * The wavelength spread FWHM for the corresponding
	 * wavelength(s) in incident_wavelength.
	 * In the case of shot-to-shot variation in the wavelength
	 * spread, this is a 2D array of dimension **nP** by
	 * **m** (slow to fast) of the spreads of the
	 * corresponding wavelengths in incident_wavelength.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @param incident_wavelength_spread the incident_wavelength_spread
	 */
	public DataNode setIncident_wavelength_spreadScalar(Double incident_wavelength_spreadValue);

	/**
	 * Beam crossfire in degrees parallel to the laboratory X axis
	 * The dimension **c** is a series of moments of that represent
	 * the standard uncertainty (e.s.d.) of the directions of
	 * of the beam. The first and second moments are in the XZ and YZ
	 * planes around the mean source beam direction, respectively.
	 * Further moments in **c** characterize co-variance terms, so
	 * the next moment is the product of the first two, and so on.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: c;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_beam_divergence();
	
	/**
	 * Beam crossfire in degrees parallel to the laboratory X axis
	 * The dimension **c** is a series of moments of that represent
	 * the standard uncertainty (e.s.d.) of the directions of
	 * of the beam. The first and second moments are in the XZ and YZ
	 * planes around the mean source beam direction, respectively.
	 * Further moments in **c** characterize co-variance terms, so
	 * the next moment is the product of the first two, and so on.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: c;
	 * </p>
	 * 
	 * @param incident_beam_divergenceDataset the incident_beam_divergenceDataset
	 */
	public DataNode setIncident_beam_divergence(IDataset incident_beam_divergenceDataset);

	/**
	 * Beam crossfire in degrees parallel to the laboratory X axis
	 * The dimension **c** is a series of moments of that represent
	 * the standard uncertainty (e.s.d.) of the directions of
	 * of the beam. The first and second moments are in the XZ and YZ
	 * planes around the mean source beam direction, respectively.
	 * Further moments in **c** characterize co-variance terms, so
	 * the next moment is the product of the first two, and so on.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: c;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getIncident_beam_divergenceScalar();

	/**
	 * Beam crossfire in degrees parallel to the laboratory X axis
	 * The dimension **c** is a series of moments of that represent
	 * the standard uncertainty (e.s.d.) of the directions of
	 * of the beam. The first and second moments are in the XZ and YZ
	 * planes around the mean source beam direction, respectively.
	 * Further moments in **c** characterize co-variance terms, so
	 * the next moment is the product of the first two, and so on.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: c;
	 * </p>
	 * 
	 * @param incident_beam_divergence the incident_beam_divergence
	 */
	public DataNode setIncident_beam_divergenceScalar(Double incident_beam_divergenceValue);

	/**
	 * Size of the beam entering this component. Note this represents
	 * a rectangular beam aperture, and values represent FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getExtent();
	
	/**
	 * Size of the beam entering this component. Note this represents
	 * a rectangular beam aperture, and values represent FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param extentDataset the extentDataset
	 */
	public DataNode setExtent(IDataset extentDataset);

	/**
	 * Size of the beam entering this component. Note this represents
	 * a rectangular beam aperture, and values represent FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getExtentScalar();

	/**
	 * Size of the beam entering this component. Note this represents
	 * a rectangular beam aperture, and values represent FWHM
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param extent the extent
	 */
	public DataNode setExtentScalar(Double extentValue);

	/**
	 * Wavelength on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFinal_wavelength();
	
	/**
	 * Wavelength on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param final_wavelengthDataset the final_wavelengthDataset
	 */
	public DataNode setFinal_wavelength(IDataset final_wavelengthDataset);

	/**
	 * Wavelength on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFinal_wavelengthScalar();

	/**
	 * Wavelength on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param final_wavelength the final_wavelength
	 */
	public DataNode setFinal_wavelengthScalar(Double final_wavelengthValue);

	/**
	 * Polarization vector on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_polarization();
	
	/**
	 * Polarization vector on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param incident_polarizationDataset the incident_polarizationDataset
	 */
	public DataNode setIncident_polarization(IDataset incident_polarizationDataset);

	/**
	 * Polarization vector on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getIncident_polarizationScalar();

	/**
	 * Polarization vector on entering beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param incident_polarization the incident_polarization
	 */
	public DataNode setIncident_polarizationScalar(Number incident_polarizationValue);

	/**
	 * Polarization vector on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFinal_polarization();
	
	/**
	 * Polarization vector on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param final_polarizationDataset the final_polarizationDataset
	 */
	public DataNode setFinal_polarization(IDataset final_polarizationDataset);

	/**
	 * Polarization vector on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getFinal_polarizationScalar();

	/**
	 * Polarization vector on leaving beamline component
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param final_polarization the final_polarization
	 */
	public DataNode setFinal_polarizationScalar(Number final_polarizationValue);

	/**
	 * Polarization vector on entering beamline component using Stokes notation
	 * The Stokes parameters are four components labelled I,Q,U,V or S_0,S_1,S_2,S_3.
	 * These are defined with the standard Nexus coordinate frame unless it is
	 * overridden by an NXtransformations field pointed to by a depends_on attribute.
	 * The last component, describing the circular polarization state, is positive
	 * for a right-hand circular state - that is the electric field vector rotates
	 * clockwise at the sample and over time when observed from the source.
	 * I (S_0) is the beam intensity (often normalized to 1). Q, U, and V scale
	 * linearly with the total degree of polarization, and indicate the relative
	 * magnitudes of the pure linear and circular orientation contributions.
	 * Q (S_1) is linearly polarized along the x axis (Q > 0) or y axis (Q < 0).
	 * U (S_2) is linearly polarized along the x==y axis (U > 0) or the
	 * -x==y axis (U < 0).
	 * V (S_3) is circularly polarized. V > 0 when the electric field vector rotates
	 * clockwise at the sample with respect to time when observed from the source;
	 * V < 0 indicates the opposite rotation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getIncident_polarization_stokes();
	
	/**
	 * Polarization vector on entering beamline component using Stokes notation
	 * The Stokes parameters are four components labelled I,Q,U,V or S_0,S_1,S_2,S_3.
	 * These are defined with the standard Nexus coordinate frame unless it is
	 * overridden by an NXtransformations field pointed to by a depends_on attribute.
	 * The last component, describing the circular polarization state, is positive
	 * for a right-hand circular state - that is the electric field vector rotates
	 * clockwise at the sample and over time when observed from the source.
	 * I (S_0) is the beam intensity (often normalized to 1). Q, U, and V scale
	 * linearly with the total degree of polarization, and indicate the relative
	 * magnitudes of the pure linear and circular orientation contributions.
	 * Q (S_1) is linearly polarized along the x axis (Q > 0) or y axis (Q < 0).
	 * U (S_2) is linearly polarized along the x==y axis (U > 0) or the
	 * -x==y axis (U < 0).
	 * V (S_3) is circularly polarized. V > 0 when the electric field vector rotates
	 * clockwise at the sample with respect to time when observed from the source;
	 * V < 0 indicates the opposite rotation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @param incident_polarization_stokesDataset the incident_polarization_stokesDataset
	 */
	public DataNode setIncident_polarization_stokes(IDataset incident_polarization_stokesDataset);

	/**
	 * Polarization vector on entering beamline component using Stokes notation
	 * The Stokes parameters are four components labelled I,Q,U,V or S_0,S_1,S_2,S_3.
	 * These are defined with the standard Nexus coordinate frame unless it is
	 * overridden by an NXtransformations field pointed to by a depends_on attribute.
	 * The last component, describing the circular polarization state, is positive
	 * for a right-hand circular state - that is the electric field vector rotates
	 * clockwise at the sample and over time when observed from the source.
	 * I (S_0) is the beam intensity (often normalized to 1). Q, U, and V scale
	 * linearly with the total degree of polarization, and indicate the relative
	 * magnitudes of the pure linear and circular orientation contributions.
	 * Q (S_1) is linearly polarized along the x axis (Q > 0) or y axis (Q < 0).
	 * U (S_2) is linearly polarized along the x==y axis (U > 0) or the
	 * -x==y axis (U < 0).
	 * V (S_3) is circularly polarized. V > 0 when the electric field vector rotates
	 * clockwise at the sample with respect to time when observed from the source;
	 * V < 0 indicates the opposite rotation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getIncident_polarization_stokesScalar();

	/**
	 * Polarization vector on entering beamline component using Stokes notation
	 * The Stokes parameters are four components labelled I,Q,U,V or S_0,S_1,S_2,S_3.
	 * These are defined with the standard Nexus coordinate frame unless it is
	 * overridden by an NXtransformations field pointed to by a depends_on attribute.
	 * The last component, describing the circular polarization state, is positive
	 * for a right-hand circular state - that is the electric field vector rotates
	 * clockwise at the sample and over time when observed from the source.
	 * I (S_0) is the beam intensity (often normalized to 1). Q, U, and V scale
	 * linearly with the total degree of polarization, and indicate the relative
	 * magnitudes of the pure linear and circular orientation contributions.
	 * Q (S_1) is linearly polarized along the x axis (Q > 0) or y axis (Q < 0).
	 * U (S_2) is linearly polarized along the x==y axis (U > 0) or the
	 * -x==y axis (U < 0).
	 * V (S_3) is circularly polarized. V > 0 when the electric field vector rotates
	 * clockwise at the sample with respect to time when observed from the source;
	 * V < 0 indicates the opposite rotation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @param incident_polarization_stokes the incident_polarization_stokes
	 */
	public DataNode setIncident_polarization_stokesScalar(Number incident_polarization_stokesValue);

	/**
	 * Polarization vector on leaving beamline component using Stokes notation
	 * (see incident_polarization_stokes).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFinal_polarization_stokes();
	
	/**
	 * Polarization vector on leaving beamline component using Stokes notation
	 * (see incident_polarization_stokes).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @param final_polarization_stokesDataset the final_polarization_stokesDataset
	 */
	public DataNode setFinal_polarization_stokes(IDataset final_polarization_stokesDataset);

	/**
	 * Polarization vector on leaving beamline component using Stokes notation
	 * (see incident_polarization_stokes).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getFinal_polarization_stokesScalar();

	/**
	 * Polarization vector on leaving beamline component using Stokes notation
	 * (see incident_polarization_stokes).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nP; 2: 4;
	 * </p>
	 * 
	 * @param final_polarization_stokes the final_polarization_stokes
	 */
	public DataNode setFinal_polarization_stokesScalar(Number final_polarization_stokesValue);

	/**
	 * Wavelength spread FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFinal_wavelength_spread();
	
	/**
	 * Wavelength spread FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param final_wavelength_spreadDataset the final_wavelength_spreadDataset
	 */
	public DataNode setFinal_wavelength_spread(IDataset final_wavelength_spreadDataset);

	/**
	 * Wavelength spread FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFinal_wavelength_spreadScalar();

	/**
	 * Wavelength spread FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param final_wavelength_spread the final_wavelength_spread
	 */
	public DataNode setFinal_wavelength_spreadScalar(Double final_wavelength_spreadValue);

	/**
	 * Divergence FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFinal_beam_divergence();
	
	/**
	 * Divergence FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param final_beam_divergenceDataset the final_beam_divergenceDataset
	 */
	public DataNode setFinal_beam_divergence(IDataset final_beam_divergenceDataset);

	/**
	 * Divergence FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFinal_beam_divergenceScalar();

	/**
	 * Divergence FWHM of beam leaving this component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: nP; 2: 2;
	 * </p>
	 * 
	 * @param final_beam_divergence the final_beam_divergence
	 */
	public DataNode setFinal_beam_divergenceScalar(Double final_beam_divergenceValue);

	/**
	 * flux incident on beam plane area
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFlux();
	
	/**
	 * flux incident on beam plane area
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @param fluxDataset the fluxDataset
	 */
	public DataNode setFlux(IDataset fluxDataset);

	/**
	 * flux incident on beam plane area
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFluxScalar();

	/**
	 * flux incident on beam plane area
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * <b>Dimensions:</b> 1: nP;
	 * </p>
	 * 
	 * @param flux the flux
	 */
	public DataNode setFluxScalar(Double fluxValue);

	/**
	 * Distribution of beam with respect to relevant variable e.g. wavelength. This is mainly
	 * useful for simulations which need to store plottable information at each beamline
	 * component.
	 * 
	 * @return  the value.
	 */
	public NXdata getData();
	
	/**
	 * Distribution of beam with respect to relevant variable e.g. wavelength. This is mainly
	 * useful for simulations which need to store plottable information at each beamline
	 * component.
	 * 
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * Distribution of beam with respect to relevant variable e.g. wavelength. This is mainly
	 * useful for simulations which need to store plottable information at each beamline
	 * component.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdata for that node.
	 */
	public NXdata getData(String name);
	
	/**
	 * Set a NXdata node by name:
	 * <ul>
	 * <li>
	 * Distribution of beam with respect to relevant variable e.g. wavelength. This is mainly
	 * useful for simulations which need to store plottable information at each beamline
	 * component.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param data the value to set
	 */
	public void setData(String name, NXdata data);
	
	/**
	 * Get all NXdata nodes:
	 * <ul>
	 * <li>
	 * Distribution of beam with respect to relevant variable e.g. wavelength. This is mainly
	 * useful for simulations which need to store plottable information at each beamline
	 * component.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Distribution of beam with respect to relevant variable e.g. wavelength. This is mainly
	 * useful for simulations which need to store plottable information at each beamline
	 * component.</li>
	 * </ul>
	 * 
	 * @param data the child nodes to add 
	 */
	
	public void setAllData(Map<String, NXdata> data);
	

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

	/**
	 * The NeXus coordinate system defines the Z axis to be along the nominal beam
	 * direction. This is the same as the McStas coordinate system (see :ref:`Design-CoordinateSystem`).
	 * However, the additional transformations needed to represent an altered beam
	 * direction can be provided using this depends_on field that contains the path
	 * to a NXtransformations group. This could represent redirection of the beam,
	 * or a refined beam direction.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDepends_on();
	
	/**
	 * The NeXus coordinate system defines the Z axis to be along the nominal beam
	 * direction. This is the same as the McStas coordinate system (see :ref:`Design-CoordinateSystem`).
	 * However, the additional transformations needed to represent an altered beam
	 * direction can be provided using this depends_on field that contains the path
	 * to a NXtransformations group. This could represent redirection of the beam,
	 * or a refined beam direction.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * The NeXus coordinate system defines the Z axis to be along the nominal beam
	 * direction. This is the same as the McStas coordinate system (see :ref:`Design-CoordinateSystem`).
	 * However, the additional transformations needed to represent an altered beam
	 * direction can be provided using this depends_on field that contains the path
	 * to a NXtransformations group. This could represent redirection of the beam,
	 * or a refined beam direction.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * The NeXus coordinate system defines the Z axis to be along the nominal beam
	 * direction. This is the same as the McStas coordinate system (see :ref:`Design-CoordinateSystem`).
	 * However, the additional transformations needed to represent an altered beam
	 * direction can be provided using this depends_on field that contains the path
	 * to a NXtransformations group. This could represent redirection of the beam,
	 * or a refined beam direction.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Direction (and location) for the beam. The location of the beam can be given by
	 * any point which it passes through as its offset attribute.
	 * 
	 * @return  the value.
	 */
	public NXtransformations getTransformations();
	
	/**
	 * Direction (and location) for the beam. The location of the beam can be given by
	 * any point which it passes through as its offset attribute.
	 * 
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Direction (and location) for the beam. The location of the beam can be given by
	 * any point which it passes through as its offset attribute.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);
	
	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Direction (and location) for the beam. The location of the beam can be given by
	 * any point which it passes through as its offset attribute.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);
	
	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Direction (and location) for the beam. The location of the beam can be given by
	 * any point which it passes through as its offset attribute.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Direction (and location) for the beam. The location of the beam can be given by
	 * any point which it passes through as its offset attribute.</li>
	 * </ul>
	 * 
	 * @param transformations the child nodes to add 
	 */
	
	public void setAllTransformations(Map<String, NXtransformations> transformations);
	

}
