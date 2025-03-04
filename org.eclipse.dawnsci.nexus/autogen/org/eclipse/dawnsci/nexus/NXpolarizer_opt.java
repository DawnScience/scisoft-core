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
import org.eclipse.january.dataset.Dataset;

/**
 * An optical polarizer.
 * Information on the properties of polarizer is provided e.g.
 * [here](https://www.rp-photonics.com/polarizers.html).
 * <p><b>Symbols:</b><ul>
 * <li><b>N_spectrum</b>
 * Size of the wavelength array for which the refractive index of the material
 * and/or coating is given.</li>
 * <li><b>N_spectrum_RT</b>
 * Size of the wavelength array for which the reflectance or transmission of
 * the polarizer is given.</li></ul></p>
 *
 */
public interface NXpolarizer_opt extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_TYPE_OTHER = "type_other";
	public static final String NX_POLARIZER_ANGLE = "polarizer_angle";
	public static final String NX_ACCEPTANCE_ANGLE = "acceptance_angle";
	public static final String NX_WAVELENGTH_RANGE = "wavelength_range";
	public static final String NX_EXTINCTION_RATIO = "extinction_ratio";
	public static final String NX_REFLECTION = "reflection";
	public static final String NX_TRANSMISSION = "transmission";
	/**
	 * Type of the polarizer (e.g. dichroic, linear, circular etc.)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>circular</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Taylor prism</b> </li>
	 * <li><b>Glan-Focault prism</b> </li>
	 * <li><b>Wollaston prism</b> </li>
	 * <li><b>Normarski prism</b> </li>
	 * <li><b>Senarmont prism</b> </li>
	 * <li><b>thin-film polarizer</b> </li>
	 * <li><b>wire grid polarizer</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type of the polarizer (e.g. dichroic, linear, circular etc.)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>circular</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Taylor prism</b> </li>
	 * <li><b>Glan-Focault prism</b> </li>
	 * <li><b>Wollaston prism</b> </li>
	 * <li><b>Normarski prism</b> </li>
	 * <li><b>Senarmont prism</b> </li>
	 * <li><b>thin-film polarizer</b> </li>
	 * <li><b>wire grid polarizer</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of the polarizer (e.g. dichroic, linear, circular etc.)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>circular</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Taylor prism</b> </li>
	 * <li><b>Glan-Focault prism</b> </li>
	 * <li><b>Wollaston prism</b> </li>
	 * <li><b>Normarski prism</b> </li>
	 * <li><b>Senarmont prism</b> </li>
	 * <li><b>thin-film polarizer</b> </li>
	 * <li><b>wire grid polarizer</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of the polarizer (e.g. dichroic, linear, circular etc.)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dichroic</b> </li>
	 * <li><b>linear</b> </li>
	 * <li><b>circular</b> </li>
	 * <li><b>Glan-Thompson prism</b> </li>
	 * <li><b>Nicol prism</b> </li>
	 * <li><b>Glan-Taylor prism</b> </li>
	 * <li><b>Glan-Focault prism</b> </li>
	 * <li><b>Wollaston prism</b> </li>
	 * <li><b>Normarski prism</b> </li>
	 * <li><b>Senarmont prism</b> </li>
	 * <li><b>thin-film polarizer</b> </li>
	 * <li><b>wire grid polarizer</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * If you selected 'other' in type specify what it is.
	 *
	 * @return  the value.
	 */
	public Dataset getType_other();

	/**
	 * If you selected 'other' in type specify what it is.
	 *
	 * @param type_otherDataset the type_otherDataset
	 */
	public DataNode setType_other(IDataset type_otherDataset);

	/**
	 * If you selected 'other' in type specify what it is.
	 *
	 * @return  the value.
	 */
	public String getType_otherScalar();

	/**
	 * If you selected 'other' in type specify what it is.
	 *
	 * @param type_other the type_other
	 */
	public DataNode setType_otherScalar(String type_otherValue);

	/**
	 * Angle of the polarizer.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPolarizer_angle();

	/**
	 * Angle of the polarizer.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param polarizer_angleDataset the polarizer_angleDataset
	 */
	public DataNode setPolarizer_angle(IDataset polarizer_angleDataset);

	/**
	 * Angle of the polarizer.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPolarizer_angleScalar();

	/**
	 * Angle of the polarizer.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param polarizer_angle the polarizer_angle
	 */
	public DataNode setPolarizer_angleScalar(Number polarizer_angleValue);

	/**
	 * Acceptance angle of the polarizer (range).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAcceptance_angle();

	/**
	 * Acceptance angle of the polarizer (range).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param acceptance_angleDataset the acceptance_angleDataset
	 */
	public DataNode setAcceptance_angle(IDataset acceptance_angleDataset);

	/**
	 * Acceptance angle of the polarizer (range).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAcceptance_angleScalar();

	/**
	 * Acceptance angle of the polarizer (range).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param acceptance_angle the acceptance_angle
	 */
	public DataNode setAcceptance_angleScalar(Number acceptance_angleValue);

	/**
	 * Describe the geometry (shape, dimension etc.) of the device.
	 * Specify the dimensions in 'SHAPE/size'. A sketch of the device should be
	 * provided in the 'sketch(NXdata)' field to clarify (i) the shape and
	 * dimensions of the device, and (ii) the input and outputs (i.e. the
	 * direction of the incoming and outcoming (split) beams).
	 *
	 * @return  the value.
	 */
	public NXshape getShape();

	/**
	 * Describe the geometry (shape, dimension etc.) of the device.
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
	 * Describe the geometry (shape, dimension etc.) of the device.
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
	 * Describe the geometry (shape, dimension etc.) of the device.
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
	 * Describe the geometry (shape, dimension etc.) of the device.
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
	 * Describe the geometry (shape, dimension etc.) of the device.
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
	 * Wavelength range for which the polarizer is designed. Enter the minimum
	 * and maximum wavelength (lower and upper limit) of the range.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWavelength_range();

	/**
	 * Wavelength range for which the polarizer is designed. Enter the minimum
	 * and maximum wavelength (lower and upper limit) of the range.
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
	 * Wavelength range for which the polarizer is designed. Enter the minimum
	 * and maximum wavelength (lower and upper limit) of the range.
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
	 * Wavelength range for which the polarizer is designed. Enter the minimum
	 * and maximum wavelength (lower and upper limit) of the range.
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
	 * Properties of the substrate material of the polarizer. If the device has
	 * a coating specify the coating material and its properties in 'coating'.
	 *
	 * @return  the value.
	 */
	public NXsample getSubstrate();

	/**
	 * Properties of the substrate material of the polarizer. If the device has
	 * a coating specify the coating material and its properties in 'coating'.
	 *
	 * @param substrateGroup the substrateGroup
	 */
	public void setSubstrate(NXsample substrateGroup);

	/**
	 * If the device has a coating describe the material and its properties.
	 * Some basic information can be found e.g. [here]
	 * (https://www.opto-e.com/basics/reflection-transmission-and-coatings).
	 * If the back and front side of the polarizer are coated with different
	 * materials, you may define two coatings (e.g. COATING1 and COATING2).
	 *
	 * @return  the value.
	 */
	public NXsample getCoating();

	/**
	 * If the device has a coating describe the material and its properties.
	 * Some basic information can be found e.g. [here]
	 * (https://www.opto-e.com/basics/reflection-transmission-and-coatings).
	 * If the back and front side of the polarizer are coated with different
	 * materials, you may define two coatings (e.g. COATING1 and COATING2).
	 *
	 * @param coatingGroup the coatingGroup
	 */
	public void setCoating(NXsample coatingGroup);

	/**
	 * Extinction ratio (maximum to minimum transmission).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getExtinction_ratio();

	/**
	 * Extinction ratio (maximum to minimum transmission).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum;
	 * </p>
	 *
	 * @param extinction_ratioDataset the extinction_ratioDataset
	 */
	public DataNode setExtinction_ratio(IDataset extinction_ratioDataset);

	/**
	 * Extinction ratio (maximum to minimum transmission).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getExtinction_ratioScalar();

	/**
	 * Extinction ratio (maximum to minimum transmission).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum;
	 * </p>
	 *
	 * @param extinction_ratio the extinction_ratio
	 */
	public DataNode setExtinction_ratioScalar(Double extinction_ratioValue);

	/**
	 * Reflection of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReflection();

	/**
	 * Reflection of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param reflectionDataset the reflectionDataset
	 */
	public DataNode setReflection(IDataset reflectionDataset);

	/**
	 * Reflection of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getReflectionScalar();

	/**
	 * Reflection of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param reflection the reflection
	 */
	public DataNode setReflectionScalar(Double reflectionValue);

	/**
	 * Transmission of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTransmission();

	/**
	 * Transmission of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param transmissionDataset the transmissionDataset
	 */
	public DataNode setTransmission(IDataset transmissionDataset);

	/**
	 * Transmission of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTransmissionScalar();

	/**
	 * Transmission of the polarizer at given wavelength values.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param transmission the transmission
	 */
	public DataNode setTransmissionScalar(Double transmissionValue);

}
