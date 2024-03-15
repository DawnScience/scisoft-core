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
 * A beam splitter, i.e. a device splitting the light into two or more beams.
 * Information about types and properties of beam splitters is provided e.g.
 * [here](https://www.rp-photonics.com/beam_splitters.html).
 * Use two or more NXbeam_paths to describe the beam paths after the beam
 * splitter. In the dependency chain of the new beam paths, the first elements
 * each point to this beam splitter, as this is the previous element.
 * <p><b>Symbols:</b><ul>
 * <li><b>N_spectrum</b>
 * Length of the spectrum vector (e.g. wavelength or energy) for which the
 * refractive index of the beam splitter material and/or coating is defined.</li>
 * <li><b>N_spectrum_RT</b>
 * Length of the spectrum vector (e.g. wavelength or energy) for which the
 * reflectance or transmission of the beam splitter is given.</li>
 * <li><b>N_shapepar</b>
 * Number of parameters needed do descripe the shape of the beam splitter.</li>
 * <li><b>N_objects</b>
 * Number of objects the beam splitter is made up of.</li>
 * <li><b>N_outputs</b>
 * Number of outputs, i.e. number of paths the beam takes after being split by
 * the beam splitter.</li></ul></p>
 *
 */
public interface NXbeam_splitter extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_OTHER_TYPE = "other_type";
	public static final String NX_POLARIZING = "polarizing";
	public static final String NX_MULTIPLE_OUTPUTS = "multiple_outputs";
	public static final String NX_SPLITTING_RATIO = "splitting_ratio";
	public static final String NX_CLEAR_APERTURE = "clear_aperture";
	public static final String NX_WAVELENGTH_RANGE = "wavelength_range";
	public static final String NX_OPTICAL_LOSS = "optical_loss";
	public static final String NX_INCIDENT_ANGLE = "incident_angle";
	public static final String NX_DEFLECTION_ANGLE = "deflection_angle";
	public static final String NX_AOI_RANGE = "aoi_range";
	public static final String NX_REFLECTANCE = "reflectance";
	public static final String NX_TRANSMISSION = "transmission";
	/**
	 * Specify the beam splitter type (e.g. dielectric mirror, pellicle,
	 * dichroic mirror etc.). Shape (e.g. prism, plate, cube) and dimension
	 * should be described in 'geometry'. Define if the beam splitter is
	 * polarizing or not in the field 'polarizing(NX_BOOLEAN)'.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic mirror</b> </li>
	 * <li><b>dielectric mirror</b> </li>
	 * <li><b>metal-coated mirror</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>pellicle mirror</b> </li>
	 * <li><b>Polka dot beam splitter</b> </li>
	 * <li><b>fiber optic splitter</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getType();

	/**
	 * Specify the beam splitter type (e.g. dielectric mirror, pellicle,
	 * dichroic mirror etc.). Shape (e.g. prism, plate, cube) and dimension
	 * should be described in 'geometry'. Define if the beam splitter is
	 * polarizing or not in the field 'polarizing(NX_BOOLEAN)'.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic mirror</b> </li>
	 * <li><b>dielectric mirror</b> </li>
	 * <li><b>metal-coated mirror</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>pellicle mirror</b> </li>
	 * <li><b>Polka dot beam splitter</b> </li>
	 * <li><b>fiber optic splitter</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Specify the beam splitter type (e.g. dielectric mirror, pellicle,
	 * dichroic mirror etc.). Shape (e.g. prism, plate, cube) and dimension
	 * should be described in 'geometry'. Define if the beam splitter is
	 * polarizing or not in the field 'polarizing(NX_BOOLEAN)'.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic mirror</b> </li>
	 * <li><b>dielectric mirror</b> </li>
	 * <li><b>metal-coated mirror</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>pellicle mirror</b> </li>
	 * <li><b>Polka dot beam splitter</b> </li>
	 * <li><b>fiber optic splitter</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Specify the beam splitter type (e.g. dielectric mirror, pellicle,
	 * dichroic mirror etc.). Shape (e.g. prism, plate, cube) and dimension
	 * should be described in 'geometry'. Define if the beam splitter is
	 * polarizing or not in the field 'polarizing(NX_BOOLEAN)'.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic mirror</b> </li>
	 * <li><b>dielectric mirror</b> </li>
	 * <li><b>metal-coated mirror</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>pellicle mirror</b> </li>
	 * <li><b>Polka dot beam splitter</b> </li>
	 * <li><b>fiber optic splitter</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * If you selected 'other' in 'type' use this field to specify which type of
	 * beam splitter was used.
	 *
	 * @return  the value.
	 */
	public IDataset getOther_type();

	/**
	 * If you selected 'other' in 'type' use this field to specify which type of
	 * beam splitter was used.
	 *
	 * @param other_typeDataset the other_typeDataset
	 */
	public DataNode setOther_type(IDataset other_typeDataset);

	/**
	 * If you selected 'other' in 'type' use this field to specify which type of
	 * beam splitter was used.
	 *
	 * @return  the value.
	 */
	public String getOther_typeScalar();

	/**
	 * If you selected 'other' in 'type' use this field to specify which type of
	 * beam splitter was used.
	 *
	 * @param other_type the other_type
	 */
	public DataNode setOther_typeScalar(String other_typeValue);

	/**
	 * Is the beam splitter polarizing?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPolarizing();

	/**
	 * Is the beam splitter polarizing?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param polarizingDataset the polarizingDataset
	 */
	public DataNode setPolarizing(IDataset polarizingDataset);

	/**
	 * Is the beam splitter polarizing?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getPolarizingScalar();

	/**
	 * Is the beam splitter polarizing?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param polarizing the polarizing
	 */
	public DataNode setPolarizingScalar(Boolean polarizingValue);

	/**
	 * Does the beam splitter have multiple outputs (diffractive optical
	 * element), i.e. more than two outputs?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMultiple_outputs();

	/**
	 * Does the beam splitter have multiple outputs (diffractive optical
	 * element), i.e. more than two outputs?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param multiple_outputsDataset the multiple_outputsDataset
	 */
	public DataNode setMultiple_outputs(IDataset multiple_outputsDataset);

	/**
	 * Does the beam splitter have multiple outputs (diffractive optical
	 * element), i.e. more than two outputs?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getMultiple_outputsScalar();

	/**
	 * Does the beam splitter have multiple outputs (diffractive optical
	 * element), i.e. more than two outputs?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param multiple_outputs the multiple_outputs
	 */
	public DataNode setMultiple_outputsScalar(Boolean multiple_outputsValue);

	/**
	 * Describe the geometry (shape, dimension etc.) of the beam splitter.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).
	 *
	 * @return  the value.
	 */
	public NXshape getShape();

	/**
	 * Describe the geometry (shape, dimension etc.) of the beam splitter.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).
	 *
	 * @param shapeGroup the shapeGroup
	 */
	public void setShape(NXshape shapeGroup);

	/**
	 * Get a NXshape node by name:
	 * <ul>
	 * <li>
	 * Describe the geometry (shape, dimension etc.) of the beam splitter.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXshape for that node.
	 */
	public NXshape getShape(String name);

	/**
	 * Set a NXshape node by name:
	 * <ul>
	 * <li>
	 * Describe the geometry (shape, dimension etc.) of the beam splitter.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param shape the value to set
	 */
	public void setShape(String name, NXshape shape);

	/**
	 * Get all NXshape nodes:
	 * <ul>
	 * <li>
	 * Describe the geometry (shape, dimension etc.) of the beam splitter.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXshape for that node.
	 */
	public Map<String, NXshape> getAllShape();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Describe the geometry (shape, dimension etc.) of the beam splitter.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).</li>
	 * </ul>
	 *
	 * @param shape the child nodes to add
	 */

	public void setAllShape(Map<String, NXshape> shape);

	// Unprocessed group:sketch

	/**
	 * Beam splitting ratio(s) for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSplitting_ratio();

	/**
	 * Beam splitting ratio(s) for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @param splitting_ratioDataset the splitting_ratioDataset
	 */
	public DataNode setSplitting_ratio(IDataset splitting_ratioDataset);

	/**
	 * Beam splitting ratio(s) for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSplitting_ratioScalar();

	/**
	 * Beam splitting ratio(s) for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @param splitting_ratio the splitting_ratio
	 */
	public DataNode setSplitting_ratioScalar(Number splitting_ratioValue);

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc, or 90% of
	 * length and height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getClear_aperture();

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc, or 90% of
	 * length and height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param clear_apertureDataset the clear_apertureDataset
	 */
	public DataNode setClear_aperture(IDataset clear_apertureDataset);

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc, or 90% of
	 * length and height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getClear_apertureScalar();

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc, or 90% of
	 * length and height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param clear_aperture the clear_aperture
	 */
	public DataNode setClear_apertureScalar(Double clear_apertureValue);

	/**
	 * Substrate of the beam splitter. Describe the material of the substrate in
	 * substrate/substrate_material and provide its index of refraction in
	 * substrate/index_of_refraction_substrate, if known.
	 *
	 * @return  the value.
	 */
	public NXsample getSubstrate();

	/**
	 * Substrate of the beam splitter. Describe the material of the substrate in
	 * substrate/substrate_material and provide its index of refraction in
	 * substrate/index_of_refraction_substrate, if known.
	 *
	 * @param substrateGroup the substrateGroup
	 */
	public void setSubstrate(NXsample substrateGroup);

	/**
	 * Is the beam splitter coated? If yes, specify the type and material of the
	 * coating and the spectral range for which it is designed. If known, you
	 * may also provide its index of refraction. For a beam splitter cube
	 * consisting of two prisms which are glued together, you may want to
	 * specify the the glue and the coatings of each prism.
	 *
	 * @return  the value.
	 */
	public NXsample getCoating();

	/**
	 * Is the beam splitter coated? If yes, specify the type and material of the
	 * coating and the spectral range for which it is designed. If known, you
	 * may also provide its index of refraction. For a beam splitter cube
	 * consisting of two prisms which are glued together, you may want to
	 * specify the the glue and the coatings of each prism.
	 *
	 * @param coatingGroup the coatingGroup
	 */
	public void setCoating(NXsample coatingGroup);

	/**
	 * Wavelength range for which the beam splitter is designed. Enter the
	 * minimum and maximum values of the wavelength range. Alternatively, or
	 * additionally, you may define the wavelength range for the coating in
	 * coating/wavelength_range_coating.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWavelength_range();

	/**
	 * Wavelength range for which the beam splitter is designed. Enter the
	 * minimum and maximum values of the wavelength range. Alternatively, or
	 * additionally, you may define the wavelength range for the coating in
	 * coating/wavelength_range_coating.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param wavelength_rangeDataset the wavelength_rangeDataset
	 */
	public DataNode setWavelength_range(IDataset wavelength_rangeDataset);

	/**
	 * Wavelength range for which the beam splitter is designed. Enter the
	 * minimum and maximum values of the wavelength range. Alternatively, or
	 * additionally, you may define the wavelength range for the coating in
	 * coating/wavelength_range_coating.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWavelength_rangeScalar();

	/**
	 * Wavelength range for which the beam splitter is designed. Enter the
	 * minimum and maximum values of the wavelength range. Alternatively, or
	 * additionally, you may define the wavelength range for the coating in
	 * coating/wavelength_range_coating.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param wavelength_range the wavelength_range
	 */
	public DataNode setWavelength_rangeScalar(Double wavelength_rangeValue);

	/**
	 * Optical loss of the beam splitter for the various outputs (i.e. the paths
	 * of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOptical_loss();

	/**
	 * Optical loss of the beam splitter for the various outputs (i.e. the paths
	 * of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @param optical_lossDataset the optical_lossDataset
	 */
	public DataNode setOptical_loss(IDataset optical_lossDataset);

	/**
	 * Optical loss of the beam splitter for the various outputs (i.e. the paths
	 * of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOptical_lossScalar();

	/**
	 * Optical loss of the beam splitter for the various outputs (i.e. the paths
	 * of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs;
	 * </p>
	 *
	 * @param optical_loss the optical_loss
	 */
	public DataNode setOptical_lossScalar(Number optical_lossValue);

	/**
	 * Optimized angle of incidence for the desired splitting ratio.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIncident_angle();

	/**
	 * Optimized angle of incidence for the desired splitting ratio.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param incident_angleDataset the incident_angleDataset
	 */
	public DataNode setIncident_angle(IDataset incident_angleDataset);

	/**
	 * Optimized angle of incidence for the desired splitting ratio.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getIncident_angleScalar();

	/**
	 * Optimized angle of incidence for the desired splitting ratio.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param incident_angle the incident_angle
	 */
	public DataNode setIncident_angleScalar(Number incident_angleValue);

	/**
	 * Angle of deflection corresponding to the optimized angle of incidence
	 * defined in incident_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDeflection_angle();

	/**
	 * Angle of deflection corresponding to the optimized angle of incidence
	 * defined in incident_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param deflection_angleDataset the deflection_angleDataset
	 */
	public DataNode setDeflection_angle(IDataset deflection_angleDataset);

	/**
	 * Angle of deflection corresponding to the optimized angle of incidence
	 * defined in incident_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDeflection_angleScalar();

	/**
	 * Angle of deflection corresponding to the optimized angle of incidence
	 * defined in incident_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param deflection_angle the deflection_angle
	 */
	public DataNode setDeflection_angleScalar(Number deflection_angleValue);

	/**
	 * Range of the angles of incidence (AOI) for which the beam splitter can be
	 * operated. Specify the minimum and maximum angles of the range.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAoi_range();

	/**
	 * Range of the angles of incidence (AOI) for which the beam splitter can be
	 * operated. Specify the minimum and maximum angles of the range.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param aoi_rangeDataset the aoi_rangeDataset
	 */
	public DataNode setAoi_range(IDataset aoi_rangeDataset);

	/**
	 * Range of the angles of incidence (AOI) for which the beam splitter can be
	 * operated. Specify the minimum and maximum angles of the range.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAoi_rangeScalar();

	/**
	 * Range of the angles of incidence (AOI) for which the beam splitter can be
	 * operated. Specify the minimum and maximum angles of the range.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param aoi_range the aoi_range
	 */
	public DataNode setAoi_rangeScalar(Number aoi_rangeValue);

	/**
	 * Reflectance of the beam splitter at given spectral values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getReflectance();

	/**
	 * Reflectance of the beam splitter at given spectral values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param reflectanceDataset the reflectanceDataset
	 */
	public DataNode setReflectance(IDataset reflectanceDataset);

	/**
	 * Reflectance of the beam splitter at given spectral values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getReflectanceScalar();

	/**
	 * Reflectance of the beam splitter at given spectral values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param reflectance the reflectance
	 */
	public DataNode setReflectanceScalar(Double reflectanceValue);

	/**
	 * Transmission at given spectral values for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs; 2: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTransmission();

	/**
	 * Transmission at given spectral values for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs; 2: N_spectrum_RT;
	 * </p>
	 *
	 * @param transmissionDataset the transmissionDataset
	 */
	public DataNode setTransmission(IDataset transmissionDataset);

	/**
	 * Transmission at given spectral values for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs; 2: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTransmissionScalar();

	/**
	 * Transmission at given spectral values for the various outputs (i.e. the
	 * paths of the beam after being split by the beam splitter).
	 * The order of the ratios must be consistent with the labels
	 * 1, 2, ... N_outputs defined by the sketch in 'SHAPE/sketch', starting
	 * with 1.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_outputs; 2: N_spectrum_RT;
	 * </p>
	 *
	 * @param transmission the transmission
	 */
	public DataNode setTransmissionScalar(Double transmissionValue);

}
