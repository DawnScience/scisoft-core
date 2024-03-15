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

/**
 * Subclass of NXprocess to describe post-processing calibrations.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays<ul>
 * <li><b>ncoeff</b>
 * Number of coefficients of the calibration function</li>
 * <li><b>nfeat</b>
 * Number of features used to fit the calibration function</li>
 * <li><b>ncal</b>
 * Number of points of the calibrated and uncalibrated axes</li></ul></p>
 *
 */
public interface NXcalibration extends NXobject {

	public static final String NX_LAST_PROCESS = "last_process";
	public static final String NX_APPLIED = "applied";
	public static final String NX_COEFFICIENTS = "coefficients";
	public static final String NX_FIT_FUNCTION = "fit_function";
	public static final String NX_SCALING = "scaling";
	public static final String NX_OFFSET = "offset";
	public static final String NX_CALIBRATED_AXIS = "calibrated_axis";
	public static final String NX_ORIGINAL_AXIS = "original_axis";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getLast_process();

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param last_processDataset the last_processDataset
	 */
	public DataNode setLast_process(IDataset last_processDataset);

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLast_processScalar();

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param last_process the last_process
	 */
	public DataNode setLast_processScalar(String last_processValue);

	/**
	 * Has the calibration been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getApplied();

	/**
	 * Has the calibration been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param appliedDataset the appliedDataset
	 */
	public DataNode setApplied(IDataset appliedDataset);

	/**
	 * Has the calibration been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getAppliedScalar();

	/**
	 * Has the calibration been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param applied the applied
	 */
	public DataNode setAppliedScalar(Boolean appliedValue);

	/**
	 * For non-linear energy calibrations, e.g. in a TOF, a polynomial function is fit
	 * to a set of features (peaks) at well defined energy positions to determine
	 * E(TOF). Here we can store the array of fit coefficients.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncoeff;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCoefficients();

	/**
	 * For non-linear energy calibrations, e.g. in a TOF, a polynomial function is fit
	 * to a set of features (peaks) at well defined energy positions to determine
	 * E(TOF). Here we can store the array of fit coefficients.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncoeff;
	 * </p>
	 *
	 * @param coefficientsDataset the coefficientsDataset
	 */
	public DataNode setCoefficients(IDataset coefficientsDataset);

	/**
	 * For non-linear energy calibrations, e.g. in a TOF, a polynomial function is fit
	 * to a set of features (peaks) at well defined energy positions to determine
	 * E(TOF). Here we can store the array of fit coefficients.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncoeff;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCoefficientsScalar();

	/**
	 * For non-linear energy calibrations, e.g. in a TOF, a polynomial function is fit
	 * to a set of features (peaks) at well defined energy positions to determine
	 * E(TOF). Here we can store the array of fit coefficients.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncoeff;
	 * </p>
	 *
	 * @param coefficients the coefficients
	 */
	public DataNode setCoefficientsScalar(Double coefficientsValue);

	/**
	 * For non-linear energy calibrations. Here we can store the formula of the
	 * fit function.
	 * Use a0, a1, ..., an for the coefficients, corresponding to the values in the coefficients field.
	 * Use x0, x1, ..., xn for the variables.
	 * The formula should be numpy compliant.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getFit_function();

	/**
	 * For non-linear energy calibrations. Here we can store the formula of the
	 * fit function.
	 * Use a0, a1, ..., an for the coefficients, corresponding to the values in the coefficients field.
	 * Use x0, x1, ..., xn for the variables.
	 * The formula should be numpy compliant.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param fit_functionDataset the fit_functionDataset
	 */
	public DataNode setFit_function(IDataset fit_functionDataset);

	/**
	 * For non-linear energy calibrations. Here we can store the formula of the
	 * fit function.
	 * Use a0, a1, ..., an for the coefficients, corresponding to the values in the coefficients field.
	 * Use x0, x1, ..., xn for the variables.
	 * The formula should be numpy compliant.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getFit_functionScalar();

	/**
	 * For non-linear energy calibrations. Here we can store the formula of the
	 * fit function.
	 * Use a0, a1, ..., an for the coefficients, corresponding to the values in the coefficients field.
	 * Use x0, x1, ..., xn for the variables.
	 * The formula should be numpy compliant.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param fit_function the fit_function
	 */
	public DataNode setFit_functionScalar(String fit_functionValue);

	/**
	 * For linear calibration. Scaling parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getScaling();

	/**
	 * For linear calibration. Scaling parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param scalingDataset the scalingDataset
	 */
	public DataNode setScaling(IDataset scalingDataset);

	/**
	 * For linear calibration. Scaling parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getScalingScalar();

	/**
	 * For linear calibration. Scaling parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param scaling the scaling
	 */
	public DataNode setScalingScalar(Double scalingValue);

	/**
	 * For linear calibration. Offset parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOffset();

	/**
	 * For linear calibration. Offset parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param offsetDataset the offsetDataset
	 */
	public DataNode setOffset(IDataset offsetDataset);

	/**
	 * For linear calibration. Offset parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOffsetScalar();

	/**
	 * For linear calibration. Offset parameter.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param offset the offset
	 */
	public DataNode setOffsetScalar(Double offsetValue);

	/**
	 * A vector representing the axis after calibration, matching the data length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCalibrated_axis();

	/**
	 * A vector representing the axis after calibration, matching the data length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @param calibrated_axisDataset the calibrated_axisDataset
	 */
	public DataNode setCalibrated_axis(IDataset calibrated_axisDataset);

	/**
	 * A vector representing the axis after calibration, matching the data length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCalibrated_axisScalar();

	/**
	 * A vector representing the axis after calibration, matching the data length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @param calibrated_axis the calibrated_axis
	 */
	public DataNode setCalibrated_axisScalar(Double calibrated_axisValue);

	/**
	 * Vector containing the data coordinates in the original uncalibrated axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOriginal_axis();

	/**
	 * Vector containing the data coordinates in the original uncalibrated axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @param original_axisDataset the original_axisDataset
	 */
	public DataNode setOriginal_axis(IDataset original_axisDataset);

	/**
	 * Vector containing the data coordinates in the original uncalibrated axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOriginal_axisScalar();

	/**
	 * Vector containing the data coordinates in the original uncalibrated axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @param original_axis the original_axis
	 */
	public DataNode setOriginal_axisScalar(Double original_axisValue);

	/**
	 * A description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * A description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * A description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * A description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

}
