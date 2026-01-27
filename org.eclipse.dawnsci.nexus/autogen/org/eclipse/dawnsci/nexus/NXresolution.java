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
 * Describes the resolution of a physical quantity.
 *
 */
public interface NXresolution extends NXobject {

	public static final String NX_PHYSICAL_QUANTITY = "physical_quantity";
	public static final String NX_TYPE = "type";
	public static final String NX_RESOLUTION = "resolution";
	public static final String NX_RESOLUTION_ERRORS = "resolution_errors";
	public static final String NX_RELATIVE_RESOLUTION = "relative_resolution";
	public static final String NX_RELATIVE_RESOLUTION_ERRORS = "relative_resolution_errors";
	public static final String NX_RESOLUTION_FORMULA_DESCRIPTION = "resolution_formula_description";
	/**
	 * The physical quantity of the resolution, e.g.,
	 * energy, momentum, time, area, etc.
	 *
	 * @return  the value.
	 */
	public Dataset getPhysical_quantity();

	/**
	 * The physical quantity of the resolution, e.g.,
	 * energy, momentum, time, area, etc.
	 *
	 * @param physical_quantityDataset the physical_quantityDataset
	 */
	public DataNode setPhysical_quantity(IDataset physical_quantityDataset);

	/**
	 * The physical quantity of the resolution, e.g.,
	 * energy, momentum, time, area, etc.
	 *
	 * @return  the value.
	 */
	public String getPhysical_quantityScalar();

	/**
	 * The physical quantity of the resolution, e.g.,
	 * energy, momentum, time, area, etc.
	 *
	 * @param physical_quantity the physical_quantity
	 */
	public DataNode setPhysical_quantityScalar(String physical_quantityValue);

	/**
	 * The process by which the resolution was determined.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>estimated</b> </li>
	 * <li><b>derived</b> </li>
	 * <li><b>calibrated</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * The process by which the resolution was determined.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>estimated</b> </li>
	 * <li><b>derived</b> </li>
	 * <li><b>calibrated</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * The process by which the resolution was determined.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>estimated</b> </li>
	 * <li><b>derived</b> </li>
	 * <li><b>calibrated</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * The process by which the resolution was determined.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>estimated</b> </li>
	 * <li><b>derived</b> </li>
	 * <li><b>calibrated</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Additional details of the estimate or description of the calibration procedure
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 * Additional details of the estimate or description of the calibration procedure
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * The resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getResolution();

	/**
	 * The resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param resolutionDataset the resolutionDataset
	 */
	public DataNode setResolution(IDataset resolutionDataset);

	/**
	 * The resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getResolutionScalar();

	/**
	 * The resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param resolution the resolution
	 */
	public DataNode setResolutionScalar(Double resolutionValue);

	/**
	 * Standard deviation of the resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getResolution_errors();

	/**
	 * Standard deviation of the resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param resolution_errorsDataset the resolution_errorsDataset
	 */
	public DataNode setResolution_errors(IDataset resolution_errorsDataset);

	/**
	 * Standard deviation of the resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getResolution_errorsScalar();

	/**
	 * Standard deviation of the resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param resolution_errors the resolution_errors
	 */
	public DataNode setResolution_errorsScalar(Double resolution_errorsValue);

	/**
	 * Ratio of the resolution at a specified measurand value to that measurand value.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRelative_resolution();

	/**
	 * Ratio of the resolution at a specified measurand value to that measurand value.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param relative_resolutionDataset the relative_resolutionDataset
	 */
	public DataNode setRelative_resolution(IDataset relative_resolutionDataset);

	/**
	 * Ratio of the resolution at a specified measurand value to that measurand value.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRelative_resolutionScalar();

	/**
	 * Ratio of the resolution at a specified measurand value to that measurand value.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param relative_resolution the relative_resolution
	 */
	public DataNode setRelative_resolutionScalar(Double relative_resolutionValue);

	/**
	 * Standard deviation of the relative resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRelative_resolution_errors();

	/**
	 * Standard deviation of the relative resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param relative_resolution_errorsDataset the relative_resolution_errorsDataset
	 */
	public DataNode setRelative_resolution_errors(IDataset relative_resolution_errorsDataset);

	/**
	 * Standard deviation of the relative resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRelative_resolution_errorsScalar();

	/**
	 * Standard deviation of the relative resolution of the physical quantity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param relative_resolution_errors the relative_resolution_errors
	 */
	public DataNode setRelative_resolution_errorsScalar(Double relative_resolution_errorsValue);

	/**
	 * The response of the instrument or part of the instrument to a infinitesimally sharp input signal
	 * along the physical quantity of this group.
	 * This is also sometimes called instrument response function for time resolution or
	 * point spread function for spatial response.
	 * The resolution is typically determined by taking the full width at half maximum (FWHM)
	 * of the response function.
	 * This could have an AXISNAME field ```input``` (the input axis or grid of the response function)
	 * and a ``DATA`` field ```magnitude```. Both of these should have the same unit. The dimensions
	 * should match those of the :ref:`resolution </NXresolution/resolution-field>` field.
	 *
	 * @return  the value.
	 */
	public NXdata getResponse_function();

	/**
	 * The response of the instrument or part of the instrument to a infinitesimally sharp input signal
	 * along the physical quantity of this group.
	 * This is also sometimes called instrument response function for time resolution or
	 * point spread function for spatial response.
	 * The resolution is typically determined by taking the full width at half maximum (FWHM)
	 * of the response function.
	 * This could have an AXISNAME field ```input``` (the input axis or grid of the response function)
	 * and a ``DATA`` field ```magnitude```. Both of these should have the same unit. The dimensions
	 * should match those of the :ref:`resolution </NXresolution/resolution-field>` field.
	 *
	 * @param response_functionGroup the response_functionGroup
	 */
	public void setResponse_function(NXdata response_functionGroup);

	/**
	 * Symbols linking to another path in the NeXus tree to be referred to from the
	 * `resolution_formula_description` field. The ``TERM`` should be a valid path inside this application
	 * definition, i.e., of the form /entry/instrument/my_part/my_field.
	 *
	 * @return  the value.
	 */
	public NXparameters getFormula_symbols();

	/**
	 * Symbols linking to another path in the NeXus tree to be referred to from the
	 * `resolution_formula_description` field. The ``TERM`` should be a valid path inside this application
	 * definition, i.e., of the form /entry/instrument/my_part/my_field.
	 *
	 * @param formula_symbolsGroup the formula_symbolsGroup
	 */
	public void setFormula_symbols(NXparameters formula_symbolsGroup);

	/**
	 * A description of the resolution formula to determine the resolution from a set of symbols as
	 * entered by the `formula_...` fields. This should be an english description of the math used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getResolution_formula_description();

	/**
	 * A description of the resolution formula to determine the resolution from a set of symbols as
	 * entered by the `formula_...` fields. This should be an english description of the math used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param resolution_formula_descriptionDataset the resolution_formula_descriptionDataset
	 */
	public DataNode setResolution_formula_description(IDataset resolution_formula_descriptionDataset);

	/**
	 * A description of the resolution formula to determine the resolution from a set of symbols as
	 * entered by the `formula_...` fields. This should be an english description of the math used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getResolution_formula_descriptionScalar();

	/**
	 * A description of the resolution formula to determine the resolution from a set of symbols as
	 * entered by the `formula_...` fields. This should be an english description of the math used.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param resolution_formula_description the resolution_formula_description
	 */
	public DataNode setResolution_formula_descriptionScalar(String resolution_formula_descriptionValue);

	/**
	 * For storing details and data of a calibration to derive a resolution from data.
	 *
	 * @return  the value.
	 */
	public NXcalibration getCalibration();

	/**
	 * For storing details and data of a calibration to derive a resolution from data.
	 *
	 * @param calibrationGroup the calibrationGroup
	 */
	public void setCalibration(NXcalibration calibrationGroup);

	/**
	 * Get a NXcalibration node by name:
	 * <ul>
	 * <li>
	 * For storing details and data of a calibration to derive a resolution from data.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcalibration for that node.
	 */
	public NXcalibration getCalibration(String name);

	/**
	 * Set a NXcalibration node by name:
	 * <ul>
	 * <li>
	 * For storing details and data of a calibration to derive a resolution from data.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param calibration the value to set
	 */
	public void setCalibration(String name, NXcalibration calibration);

	/**
	 * Get all NXcalibration nodes:
	 * <ul>
	 * <li>
	 * For storing details and data of a calibration to derive a resolution from data.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcalibration for that node.
	 */
	public Map<String, NXcalibration> getAllCalibration();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * For storing details and data of a calibration to derive a resolution from data.</li>
	 * </ul>
	 *
	 * @param calibration the child nodes to add
	 */

	public void setAllCalibration(Map<String, NXcalibration> calibration);


}
