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
 * Quantified aberration coefficient in an aberration_model.
 *
 */
public interface NXaberration extends NXobject {

	public static final String NX_MAGNITUDE = "magnitude";
	public static final String NX_UNCERTAINTY = "uncertainty";
	public static final String NX_UNCERTAINTY_MODEL = "uncertainty_model";
	public static final String NX_DELTA_TIME = "delta_time";
	public static final String NX_ANGLE = "angle";
	public static final String NX_NAME = "name";
	public static final String NX_ALIAS = "alias";
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMagnitude();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param magnitudeDataset the magnitudeDataset
	 */
	public DataNode setMagnitude(IDataset magnitudeDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMagnitudeScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param magnitude the magnitude
	 */
	public DataNode setMagnitudeScalar(Double magnitudeValue);

	/**
	 * Confidence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getUncertainty();

	/**
	 * Confidence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param uncertaintyDataset the uncertaintyDataset
	 */
	public DataNode setUncertainty(IDataset uncertaintyDataset);

	/**
	 * Confidence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUncertaintyScalar();

	/**
	 * Confidence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param uncertainty the uncertainty
	 */
	public DataNode setUncertaintyScalar(Double uncertaintyValue);

	/**
	 * How was the uncertainty quantified e.g. via the 95% confidence interval.
	 *
	 * @return  the value.
	 */
	public IDataset getUncertainty_model();

	/**
	 * How was the uncertainty quantified e.g. via the 95% confidence interval.
	 *
	 * @param uncertainty_modelDataset the uncertainty_modelDataset
	 */
	public DataNode setUncertainty_model(IDataset uncertainty_modelDataset);

	/**
	 * How was the uncertainty quantified e.g. via the 95% confidence interval.
	 *
	 * @return  the value.
	 */
	public String getUncertainty_modelScalar();

	/**
	 * How was the uncertainty quantified e.g. via the 95% confidence interval.
	 *
	 * @param uncertainty_model the uncertainty_model
	 */
	public DataNode setUncertainty_modelScalar(String uncertainty_modelValue);

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDelta_time();

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param delta_timeDataset the delta_timeDataset
	 */
	public DataNode setDelta_time(IDataset delta_timeDataset);

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDelta_timeScalar();

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param delta_time the delta_time
	 */
	public DataNode setDelta_timeScalar(Double delta_timeValue);

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have no
	 * angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAngle();

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have no
	 * angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angleDataset the angleDataset
	 */
	public DataNode setAngle(IDataset angleDataset);

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have no
	 * angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAngleScalar();

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have no
	 * angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angle the angle
	 */
	public DataNode setAngleScalar(Double angleValue);

	/**
	 *
	 * @return  the value.
	 */
	public IDataset getName();

	/**
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 *
	 * @return  the value.
	 */
	public IDataset getAlias();

	/**
	 *
	 * @param aliasDataset the aliasDataset
	 */
	public DataNode setAlias(IDataset aliasDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getAliasScalar();

	/**
	 *
	 * @param alias the alias
	 */
	public DataNode setAliasScalar(String aliasValue);

}
