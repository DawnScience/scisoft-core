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
 * An optical fiber, e.g. glass fiber.
 * Specify the quantities that define the fiber. Fiber optics are described in
 * detail [here](https://www.photonics.com/Article.aspx?AID=25151&PID=4), for
 * example.
 * <p><b>Symbols:</b><ul>
 * <li><b>N_spectrum_core</b>
 * Length of the spectrum vector (e.g. wavelength or energy) for which the
 * refractive index of the core material is given.</li>
 * <li><b>N_spectrum_clad</b>
 * Length of the spectrum vector (e.g. wavelength or energy) for which the
 * refractive index of the cladding material is given.</li>
 * <li><b>N_spectrum_attenuation</b>
 * Length of the spectrum vector (e.g. wavelength or energy) for which the
 * attenuation curve is given.</li></ul></p>
 *
 */
public interface NXfiber extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_TYPE = "type";
	public static final String NX_DISPERSION_TYPE = "dispersion_type";
	public static final String NX_DISPERSION = "dispersion";
	public static final String NX_LENGTH = "length";
	public static final String NX_SPECTRAL_RANGE = "spectral_range";
	public static final String NX_SPECTRAL_RANGE_ATTRIBUTE_UNITS = "units";
	public static final String NX_TRANSFER_RATE = "transfer_rate";
	public static final String NX_TRANSFER_RATE_ATTRIBUTE_UNITS = "units";
	public static final String NX_NUMERICAL_APERTURE = "numerical_aperture";
	public static final String NX_ATTENUATION = "attenuation";
	public static final String NX_ATTENUATION_ATTRIBUTE_UNITS = "units";
	public static final String NX_POWER_LOSS = "power_loss";
	public static final String NX_ACCEPTANCE_ANGLE = "acceptance_angle";
	/**
	 * Descriptive name or brief description of the fiber, e.g. by stating its
	 * dimension. The dimension of a fiber can be given as 60/100/200 which
	 * refers to a core diameter of 60 micron, a clad diameter of 100 micron,
	 * and a coating diameter of 200 micron.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Descriptive name or brief description of the fiber, e.g. by stating its
	 * dimension. The dimension of a fiber can be given as 60/100/200 which
	 * refers to a core diameter of 60 micron, a clad diameter of 100 micron,
	 * and a coating diameter of 200 micron.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Descriptive name or brief description of the fiber, e.g. by stating its
	 * dimension. The dimension of a fiber can be given as 60/100/200 which
	 * refers to a core diameter of 60 micron, a clad diameter of 100 micron,
	 * and a coating diameter of 200 micron.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Descriptive name or brief description of the fiber, e.g. by stating its
	 * dimension. The dimension of a fiber can be given as 60/100/200 which
	 * refers to a core diameter of 60 micron, a clad diameter of 100 micron,
	 * and a coating diameter of 200 micron.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Type/mode of the fiber. Modes of fiber transmission are shown in
	 * Fig. 5 [here](https://www.photonics.com/Article.aspx?AID=25151&PID=4).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single mode</b> </li>
	 * <li><b>multimode graded index</b> </li>
	 * <li><b>multimode step index</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type/mode of the fiber. Modes of fiber transmission are shown in
	 * Fig. 5 [here](https://www.photonics.com/Article.aspx?AID=25151&PID=4).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single mode</b> </li>
	 * <li><b>multimode graded index</b> </li>
	 * <li><b>multimode step index</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type/mode of the fiber. Modes of fiber transmission are shown in
	 * Fig. 5 [here](https://www.photonics.com/Article.aspx?AID=25151&PID=4).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single mode</b> </li>
	 * <li><b>multimode graded index</b> </li>
	 * <li><b>multimode step index</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type/mode of the fiber. Modes of fiber transmission are shown in
	 * Fig. 5 [here](https://www.photonics.com/Article.aspx?AID=25151&PID=4).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single mode</b> </li>
	 * <li><b>multimode graded index</b> </li>
	 * <li><b>multimode step index</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Type of dispersion.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>modal</b> </li>
	 * <li><b>material</b> </li>
	 * <li><b>chromatic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDispersion_type();

	/**
	 * Type of dispersion.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>modal</b> </li>
	 * <li><b>material</b> </li>
	 * <li><b>chromatic</b> </li></ul></p>
	 * </p>
	 *
	 * @param dispersion_typeDataset the dispersion_typeDataset
	 */
	public DataNode setDispersion_type(IDataset dispersion_typeDataset);

	/**
	 * Type of dispersion.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>modal</b> </li>
	 * <li><b>material</b> </li>
	 * <li><b>chromatic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDispersion_typeScalar();

	/**
	 * Type of dispersion.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>modal</b> </li>
	 * <li><b>material</b> </li>
	 * <li><b>chromatic</b> </li></ul></p>
	 * </p>
	 *
	 * @param dispersion_type the dispersion_type
	 */
	public DataNode setDispersion_typeScalar(String dispersion_typeValue);

	/**
	 * Spectrum-dependent (or refractive index-dependent) dispersion of the
	 * fiber. Specify in ps/nm*km.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: N_spectrum_core;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDispersion();

	/**
	 * Spectrum-dependent (or refractive index-dependent) dispersion of the
	 * fiber. Specify in ps/nm*km.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: N_spectrum_core;
	 * </p>
	 *
	 * @param dispersionDataset the dispersionDataset
	 */
	public DataNode setDispersion(IDataset dispersionDataset);

	/**
	 * Spectrum-dependent (or refractive index-dependent) dispersion of the
	 * fiber. Specify in ps/nm*km.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: N_spectrum_core;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDispersionScalar();

	/**
	 * Spectrum-dependent (or refractive index-dependent) dispersion of the
	 * fiber. Specify in ps/nm*km.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: N_spectrum_core;
	 * </p>
	 *
	 * @param dispersion the dispersion
	 */
	public DataNode setDispersionScalar(Double dispersionValue);

	/**
	 * Core of the fiber, i.e. the part of the fiber which transmits the light.
	 *
	 * @return  the value.
	 */
	public NXsample getCore();

	/**
	 * Core of the fiber, i.e. the part of the fiber which transmits the light.
	 *
	 * @param coreGroup the coreGroup
	 */
	public void setCore(NXsample coreGroup);

	/**
	 * Core of the fiber, i.e. the part of the fiber which transmits the light.
	 *
	 * @return  the value.
	 */
	public NXsample getCladding();

	/**
	 * Core of the fiber, i.e. the part of the fiber which transmits the light.
	 *
	 * @param claddingGroup the claddingGroup
	 */
	public void setCladding(NXsample claddingGroup);

	/**
	 * Coating of the fiber.
	 *
	 * @return  the value.
	 */
	public NXsample getCoating();

	/**
	 * Coating of the fiber.
	 *
	 * @param coatingGroup the coatingGroup
	 */
	public void setCoating(NXsample coatingGroup);

	/**
	 * Length of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLength();

	/**
	 * Length of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param lengthDataset the lengthDataset
	 */
	public DataNode setLength(IDataset lengthDataset);

	/**
	 * Length of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getLengthScalar();

	/**
	 * Length of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param length the length
	 */
	public DataNode setLengthScalar(Double lengthValue);

	/**
	 * Spectral range for which the fiber is designed. Enter the minimum and
	 * maximum values (lower and upper limit) of the wavelength range.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSpectral_range();

	/**
	 * Spectral range for which the fiber is designed. Enter the minimum and
	 * maximum values (lower and upper limit) of the wavelength range.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param spectral_rangeDataset the spectral_rangeDataset
	 */
	public DataNode setSpectral_range(IDataset spectral_rangeDataset);

	/**
	 * Spectral range for which the fiber is designed. Enter the minimum and
	 * maximum values (lower and upper limit) of the wavelength range.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSpectral_rangeScalar();

	/**
	 * Spectral range for which the fiber is designed. Enter the minimum and
	 * maximum values (lower and upper limit) of the wavelength range.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param spectral_range the spectral_range
	 */
	public DataNode setSpectral_rangeScalar(Double spectral_rangeValue);

	/**
	 * Unit of spectral array (e.g. nanometer or angstrom for wavelength, or
	 * electronvolt for energy etc.).
	 *
	 * @return  the value.
	 */
	public String getSpectral_rangeAttributeUnits();

	/**
	 * Unit of spectral array (e.g. nanometer or angstrom for wavelength, or
	 * electronvolt for energy etc.).
	 *
	 * @param unitsValue the unitsValue
	 */
	public void setSpectral_rangeAttributeUnits(String unitsValue);

	/**
	 * Transfer rate of the fiber (in GB per second).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTransfer_rate();

	/**
	 * Transfer rate of the fiber (in GB per second).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param transfer_rateDataset the transfer_rateDataset
	 */
	public DataNode setTransfer_rate(IDataset transfer_rateDataset);

	/**
	 * Transfer rate of the fiber (in GB per second).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTransfer_rateScalar();

	/**
	 * Transfer rate of the fiber (in GB per second).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param transfer_rate the transfer_rate
	 */
	public DataNode setTransfer_rateScalar(Double transfer_rateValue);

	/**
	 * GB/s
	 *
	 * @return  the value.
	 */
	public String getTransfer_rateAttributeUnits();

	/**
	 * GB/s
	 *
	 * @param unitsValue the unitsValue
	 */
	public void setTransfer_rateAttributeUnits(String unitsValue);

	/**
	 * Numerical aperture (NA) of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumerical_aperture();

	/**
	 * Numerical aperture (NA) of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param numerical_apertureDataset the numerical_apertureDataset
	 */
	public DataNode setNumerical_aperture(IDataset numerical_apertureDataset);

	/**
	 * Numerical aperture (NA) of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getNumerical_apertureScalar();

	/**
	 * Numerical aperture (NA) of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param numerical_aperture the numerical_aperture
	 */
	public DataNode setNumerical_apertureScalar(Double numerical_apertureValue);

	/**
	 * Wavelength-dependent attenuation of the fiber (specify in dB/km).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: N_spectrum_attenuation;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAttenuation();

	/**
	 * Wavelength-dependent attenuation of the fiber (specify in dB/km).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: N_spectrum_attenuation;
	 * </p>
	 *
	 * @param attenuationDataset the attenuationDataset
	 */
	public DataNode setAttenuation(IDataset attenuationDataset);

	/**
	 * Wavelength-dependent attenuation of the fiber (specify in dB/km).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: N_spectrum_attenuation;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAttenuationScalar();

	/**
	 * Wavelength-dependent attenuation of the fiber (specify in dB/km).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: N_spectrum_attenuation;
	 * </p>
	 *
	 * @param attenuation the attenuation
	 */
	public DataNode setAttenuationScalar(Double attenuationValue);

	/**
	 * Use dB/km.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dB/km</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAttenuationAttributeUnits();

	/**
	 * Use dB/km.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dB/km</b> </li></ul></p>
	 * </p>
	 *
	 * @param unitsValue the unitsValue
	 */
	public void setAttenuationAttributeUnits(String unitsValue);

	/**
	 * Power loss of the fiber in percentage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPower_loss();

	/**
	 * Power loss of the fiber in percentage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param power_lossDataset the power_lossDataset
	 */
	public DataNode setPower_loss(IDataset power_lossDataset);

	/**
	 * Power loss of the fiber in percentage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPower_lossScalar();

	/**
	 * Power loss of the fiber in percentage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param power_loss the power_loss
	 */
	public DataNode setPower_lossScalar(Double power_lossValue);

	/**
	 * Acceptance angle of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAcceptance_angle();

	/**
	 * Acceptance angle of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param acceptance_angleDataset the acceptance_angleDataset
	 */
	public DataNode setAcceptance_angle(IDataset acceptance_angleDataset);

	/**
	 * Acceptance angle of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAcceptance_angleScalar();

	/**
	 * Acceptance angle of the fiber.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param acceptance_angle the acceptance_angle
	 */
	public DataNode setAcceptance_angleScalar(Double acceptance_angleValue);

}
