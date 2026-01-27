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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A waveplate or retarder.
 * <p><b>Symbols:</b><ul>
 * <li><b>N_spectrum</b>
 * Size of the wavelength array for which the refractive index of the material
 * and/or coating is given.</li>
 * <li><b>N_wavelengths</b>
 * Number of discrete wavelengths for which the waveplate is designed. If it
 * operates for a range of wavelengths then N_wavelengths = 2 and the minimum
 * and maximum values of the range should be provided.</li></ul></p>
 *
 */
public interface NXwaveplate extends NXcomponent {

	public static final String NX_TYPE = "type";
	public static final String NX_RETARDANCE = "retardance";
	public static final String NX_WAVELENGTHS = "wavelengths";
	public static final String NX_DIAMETER = "diameter";
	public static final String NX_CLEAR_APERTURE = "clear_aperture";
	public static final String NX_REFLECTANCE = "reflectance";
	/**
	 * Type of waveplate (e.g. achromatic or zero-order).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>zero-order</b> </li>
	 * <li><b>achromatic</b> </li>
	 * <li><b>multiple-order</b> </li>
	 * <li><b>dual-wavelength</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type of waveplate (e.g. achromatic or zero-order).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>zero-order</b> </li>
	 * <li><b>achromatic</b> </li>
	 * <li><b>multiple-order</b> </li>
	 * <li><b>dual-wavelength</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of waveplate (e.g. achromatic or zero-order).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>zero-order</b> </li>
	 * <li><b>achromatic</b> </li>
	 * <li><b>multiple-order</b> </li>
	 * <li><b>dual-wavelength</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of waveplate (e.g. achromatic or zero-order).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>zero-order</b> </li>
	 * <li><b>achromatic</b> </li>
	 * <li><b>multiple-order</b> </li>
	 * <li><b>dual-wavelength</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Specify the retardance of the waveplate (e.g. full-wave, half-wave
	 * (lambda/2), quarter-wave (lambda/4)).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>full-wave</b> </li>
	 * <li><b>half-wave</b> </li>
	 * <li><b>quarter-wave</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRetardance();

	/**
	 * Specify the retardance of the waveplate (e.g. full-wave, half-wave
	 * (lambda/2), quarter-wave (lambda/4)).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>full-wave</b> </li>
	 * <li><b>half-wave</b> </li>
	 * <li><b>quarter-wave</b> </li></ul></p>
	 * </p>
	 *
	 * @param retardanceDataset the retardanceDataset
	 */
	public DataNode setRetardance(IDataset retardanceDataset);

	/**
	 * Specify the retardance of the waveplate (e.g. full-wave, half-wave
	 * (lambda/2), quarter-wave (lambda/4)).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>full-wave</b> </li>
	 * <li><b>half-wave</b> </li>
	 * <li><b>quarter-wave</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getRetardanceScalar();

	/**
	 * Specify the retardance of the waveplate (e.g. full-wave, half-wave
	 * (lambda/2), quarter-wave (lambda/4)).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>full-wave</b> </li>
	 * <li><b>half-wave</b> </li>
	 * <li><b>quarter-wave</b> </li></ul></p>
	 * </p>
	 *
	 * @param retardance the retardance
	 */
	public DataNode setRetardanceScalar(String retardanceValue);

	/**
	 * Discrete wavelengths for which the waveplate is designed. If the
	 * waveplate operates over an entire range of wavelengths, enter the minimum
	 * and maximum values of the wavelength range (in this case
	 * N_wavelengths = 2). In this case, also use type="achromatic".
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_wavelengths;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWavelengths();

	/**
	 * Discrete wavelengths for which the waveplate is designed. If the
	 * waveplate operates over an entire range of wavelengths, enter the minimum
	 * and maximum values of the wavelength range (in this case
	 * N_wavelengths = 2). In this case, also use type="achromatic".
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_wavelengths;
	 * </p>
	 *
	 * @param wavelengthsDataset the wavelengthsDataset
	 */
	public DataNode setWavelengths(IDataset wavelengthsDataset);

	/**
	 * Discrete wavelengths for which the waveplate is designed. If the
	 * waveplate operates over an entire range of wavelengths, enter the minimum
	 * and maximum values of the wavelength range (in this case
	 * N_wavelengths = 2). In this case, also use type="achromatic".
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_wavelengths;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWavelengthsScalar();

	/**
	 * Discrete wavelengths for which the waveplate is designed. If the
	 * waveplate operates over an entire range of wavelengths, enter the minimum
	 * and maximum values of the wavelength range (in this case
	 * N_wavelengths = 2). In this case, also use type="achromatic".
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_wavelengths;
	 * </p>
	 *
	 * @param wavelengths the wavelengths
	 */
	public DataNode setWavelengthsScalar(Number wavelengthsValue);

	/**
	 * Wavelength resolved retardance of the waveplate.
	 *
	 * @return  the value.
	 */
	public NXdata getRetardance_distribution();

	/**
	 * Wavelength resolved retardance of the waveplate.
	 *
	 * @param retardance_distributionGroup the retardance_distributionGroup
	 */
	public void setRetardance_distribution(NXdata retardance_distributionGroup);

	/**
	 * Diameter of the waveplate (if the waveplate is circular).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDiameter();

	/**
	 * Diameter of the waveplate (if the waveplate is circular).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameterDataset the diameterDataset
	 */
	public DataNode setDiameter(IDataset diameterDataset);

	/**
	 * Diameter of the waveplate (if the waveplate is circular).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDiameterScalar();

	/**
	 * Diameter of the waveplate (if the waveplate is circular).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param diameter the diameter
	 */
	public DataNode setDiameterScalar(Double diameterValue);

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc or 90% of
	 * length/height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getClear_aperture();

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc or 90% of
	 * length/height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param clear_apertureDataset the clear_apertureDataset
	 */
	public DataNode setClear_aperture(IDataset clear_apertureDataset);

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc or 90% of
	 * length/height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getClear_apertureScalar();

	/**
	 * Clear aperture of the device (e.g. 90% of diameter for a disc or 90% of
	 * length/height for square geometry).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param clear_aperture the clear_aperture
	 */
	public DataNode setClear_apertureScalar(Double clear_apertureValue);

	/**
	 * Describe the material of the substrate of the waveplate in
	 * substrate/substrate_material and provide its index of refraction in
	 * substrate/index_of_refraction_substrate, if known.
	 *
	 * @return  the value.
	 */
	public NXsample getSubstrate();

	/**
	 * Describe the material of the substrate of the waveplate in
	 * substrate/substrate_material and provide its index of refraction in
	 * substrate/index_of_refraction_substrate, if known.
	 *
	 * @param substrateGroup the substrateGroup
	 */
	public void setSubstrate(NXsample substrateGroup);

	/**
	 * Is the waveplate coated? If yes, specify the type and material of the
	 * coating and the wavelength range for which it is designed. If known, you
	 * may also provide its index of refraction.
	 *
	 * @return  the value.
	 */
	public NXsample getCoating();

	/**
	 * Is the waveplate coated? If yes, specify the type and material of the
	 * coating and the wavelength range for which it is designed. If known, you
	 * may also provide its index of refraction.
	 *
	 * @param coatingGroup the coatingGroup
	 */
	public void setCoating(NXsample coatingGroup);

	/**
	 * Average reflectance of the waveplate in percentage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReflectance();

	/**
	 * Average reflectance of the waveplate in percentage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param reflectanceDataset the reflectanceDataset
	 */
	public DataNode setReflectance(IDataset reflectanceDataset);

	/**
	 * Average reflectance of the waveplate in percentage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getReflectanceScalar();

	/**
	 * Average reflectance of the waveplate in percentage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param reflectance the reflectance
	 */
	public DataNode setReflectanceScalar(Number reflectanceValue);

}
