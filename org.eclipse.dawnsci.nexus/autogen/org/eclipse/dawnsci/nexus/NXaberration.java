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
 * Quantified aberration coefficient in an aberration_model.
 * For an introduction in the details about aberrations with relevance for electron microscopy
 * see `R. Dunin-Borkowski et al. <https://doi.org/10.1017/9781316337455.022>`_ and
 * `S. J. Pennycock and P. D. Nellist <https://doi.org/10.1007/978-1-4419-7200-2>`_ (page 44ff, and page 118ff)
 * for different definitions available and further details.
 * Table 7-2 of Ibid. publication (page 305ff) documents how to convert from the Nion to the CEOS definitions.
 * Conversion tables are also summarized by `Y. Liao <https://www.globalsino.com/EM/page3740.html>`_ an introduction.
 * The use of the base class is not restricted to electron microscopy but can also be useful for classical optics.
 *
 */
public interface NXaberration extends NXobject {

	public static final String NX_MAGNITUDE = "magnitude";
	public static final String NX_MAGNITUDE_ERRORS = "magnitude_errors";
	public static final String NX_MAGNITUDE_ERRORS_MODEL = "magnitude_errors_model";
	public static final String NX_DELTA_TIME = "delta_time";
	public static final String NX_ANGLE = "angle";
	public static final String NX_NAME = "name";
	public static final String NX_ALIAS = "alias";
	/**
	 * Magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMagnitude();

	/**
	 * Magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param magnitudeDataset the magnitudeDataset
	 */
	public DataNode setMagnitude(IDataset magnitudeDataset);

	/**
	 * Magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMagnitudeScalar();

	/**
	 * Magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param magnitude the magnitude
	 */
	public DataNode setMagnitudeScalar(Number magnitudeValue);

	/**
	 * Uncertainty of the magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMagnitude_errors();

	/**
	 * Uncertainty of the magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param magnitude_errorsDataset the magnitude_errorsDataset
	 */
	public DataNode setMagnitude_errors(IDataset magnitude_errorsDataset);

	/**
	 * Uncertainty of the magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMagnitude_errorsScalar();

	/**
	 * Uncertainty of the magnitude of the aberration
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param magnitude_errors the magnitude_errors
	 */
	public DataNode setMagnitude_errorsScalar(Number magnitude_errorsValue);

	/**
	 * Free-text description how magnitude_errors was quantified
	 * e.g. via the 95% confidence interval, variance, standard deviation,
	 * using which algorithm or statistical model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMagnitude_errors_model();

	/**
	 * Free-text description how magnitude_errors was quantified
	 * e.g. via the 95% confidence interval, variance, standard deviation,
	 * using which algorithm or statistical model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param magnitude_errors_modelDataset the magnitude_errors_modelDataset
	 */
	public DataNode setMagnitude_errors_model(IDataset magnitude_errors_modelDataset);

	/**
	 * Free-text description how magnitude_errors was quantified
	 * e.g. via the 95% confidence interval, variance, standard deviation,
	 * using which algorithm or statistical model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMagnitude_errors_modelScalar();

	/**
	 * Free-text description how magnitude_errors was quantified
	 * e.g. via the 95% confidence interval, variance, standard deviation,
	 * using which algorithm or statistical model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param magnitude_errors_model the magnitude_errors_model
	 */
	public DataNode setMagnitude_errors_modelScalar(String magnitude_errors_modelValue);

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDelta_time();

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param delta_timeDataset the delta_timeDataset
	 */
	public DataNode setDelta_time(IDataset delta_timeDataset);

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDelta_timeScalar();

	/**
	 * Time elapsed since the last measurement.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param delta_time the delta_time
	 */
	public DataNode setDelta_timeScalar(Number delta_timeValue);

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have
	 * no angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAngle();

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have
	 * no angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angleDataset the angleDataset
	 */
	public DataNode setAngle(IDataset angleDataset);

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have
	 * no angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAngleScalar();

	/**
	 * For the CEOS definitions the C aberrations are radial-symmetric and have
	 * no angle entry, while the A, B, D, S, or R aberrations are n-fold
	 * symmetric and have an angle entry.
	 * For the NION definitions the coordinate system differs to the one
	 * used in CEOS and instead two aberration coefficients a and b are used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param angle the angle
	 */
	public DataNode setAngleScalar(Number angleValue);

	/**
	 * Given name to this aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name to this aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name to this aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name to this aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Alias to name or refer to this specific type of aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAlias();

	/**
	 * Alias to name or refer to this specific type of aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param aliasDataset the aliasDataset
	 */
	public DataNode setAlias(IDataset aliasDataset);

	/**
	 * Alias to name or refer to this specific type of aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAliasScalar();

	/**
	 * Alias to name or refer to this specific type of aberration.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param alias the alias
	 */
	public DataNode setAliasScalar(String aliasValue);

}
