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
 * Subclass of NXprocess to describe post-processing calibrations.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays<ul>
 * <li><b>ncal</b>
 * Number of points of the calibrated and uncalibrated axes</li></ul></p>
 *
 */
public interface NXcalibration extends NXprocess {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_PHYSICAL_QUANTITY = "physical_quantity";
	public static final String NX_IDENTIFIER_CALIBRATION_METHOD = "identifier_calibration_method";
	public static final String NX_IDENTIFIER_CALIBRATION_REFERENCE = "identifier_calibration_reference";
	public static final String NX_LAST_PROCESS = "last_process";
	public static final String NX_APPLIED = "applied";
	public static final String NX_ORIGINAL_AXIS = "original_axis";
	public static final String NX_ORIGINAL_AXIS_ATTRIBUTE_SYMBOL = "symbol";
	public static final String NX_ORIGINAL_AXIS_ATTRIBUTE_INPUT_PATH = "input_path";
	public static final String NX_FIT_FORMULA_DESCRIPTION = "fit_formula_description";
	public static final String NX_MAPPING_MAPPING = "mapping_mapping";
	public static final String NX_CALIBRATED_AXIS = "calibrated_axis";
	/**
	 * A description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

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

	/**
	 * The physical quantity of the calibration, e.g.,
	 * energy, momentum, time, etc.
	 *
	 * @return  the value.
	 */
	public Dataset getPhysical_quantity();

	/**
	 * The physical quantity of the calibration, e.g.,
	 * energy, momentum, time, etc.
	 *
	 * @param physical_quantityDataset the physical_quantityDataset
	 */
	public DataNode setPhysical_quantity(IDataset physical_quantityDataset);

	/**
	 * The physical quantity of the calibration, e.g.,
	 * energy, momentum, time, etc.
	 *
	 * @return  the value.
	 */
	public String getPhysical_quantityScalar();

	/**
	 * The physical quantity of the calibration, e.g.,
	 * energy, momentum, time, etc.
	 *
	 * @param physical_quantity the physical_quantity
	 */
	public DataNode setPhysical_quantityScalar(String physical_quantityValue);

	/**
	 * A digital persistent identifier (e.g., DOI, ISO standard) referring to a detailed description of a
	 * calibration method but no actual calibration data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_calibration_method();

	/**
	 * A digital persistent identifier (e.g., DOI, ISO standard) referring to a detailed description of a
	 * calibration method but no actual calibration data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param identifier_calibration_methodDataset the identifier_calibration_methodDataset
	 */
	public DataNode setIdentifier_calibration_method(IDataset identifier_calibration_methodDataset);

	/**
	 * A digital persistent identifier (e.g., DOI, ISO standard) referring to a detailed description of a
	 * calibration method but no actual calibration data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifier_calibration_methodScalar();

	/**
	 * A digital persistent identifier (e.g., DOI, ISO standard) referring to a detailed description of a
	 * calibration method but no actual calibration data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param identifier_calibration_method the identifier_calibration_method
	 */
	public DataNode setIdentifier_calibration_methodScalar(String identifier_calibration_methodValue);

	/**
	 * A digital persistent identifier (e.g., a DOI) referring to a publicly available calibration measurement
	 * used for this instrument, e.g., a measurement of a known standard containing calibration information.
	 * The axis values may be copied or linked in the appropriate NXcalibration fields for reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_calibration_reference();

	/**
	 * A digital persistent identifier (e.g., a DOI) referring to a publicly available calibration measurement
	 * used for this instrument, e.g., a measurement of a known standard containing calibration information.
	 * The axis values may be copied or linked in the appropriate NXcalibration fields for reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param identifier_calibration_referenceDataset the identifier_calibration_referenceDataset
	 */
	public DataNode setIdentifier_calibration_reference(IDataset identifier_calibration_referenceDataset);

	/**
	 * A digital persistent identifier (e.g., a DOI) referring to a publicly available calibration measurement
	 * used for this instrument, e.g., a measurement of a known standard containing calibration information.
	 * The axis values may be copied or linked in the appropriate NXcalibration fields for reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifier_calibration_referenceScalar();

	/**
	 * A digital persistent identifier (e.g., a DOI) referring to a publicly available calibration measurement
	 * used for this instrument, e.g., a measurement of a known standard containing calibration information.
	 * The axis values may be copied or linked in the appropriate NXcalibration fields for reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param identifier_calibration_reference the identifier_calibration_reference
	 */
	public DataNode setIdentifier_calibration_referenceScalar(String identifier_calibration_referenceValue);

	/**
	 * A file serialization of a calibration which may not be publicly available (externally from the NeXus file).
	 * This metadata can be a documentation of the source (file) or database (entry) from which pieces
	 * of information have been extracted for consumption (e.g. in a research data management system (RDMS)).
	 * It is also possible to include the actual file by using the `file` field.
	 * The axis values may be copied or linked in the appropriate NXcalibration fields for reference.
	 *
	 * @return  the value.
	 */
	public NXnote getCalibration_object();

	/**
	 * A file serialization of a calibration which may not be publicly available (externally from the NeXus file).
	 * This metadata can be a documentation of the source (file) or database (entry) from which pieces
	 * of information have been extracted for consumption (e.g. in a research data management system (RDMS)).
	 * It is also possible to include the actual file by using the `file` field.
	 * The axis values may be copied or linked in the appropriate NXcalibration fields for reference.
	 *
	 * @param calibration_objectGroup the calibration_objectGroup
	 */
	public void setCalibration_object(NXnote calibration_objectGroup);

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLast_process();

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
	public Dataset getApplied();

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
	 * Array containing the data coordinates in the original uncalibrated axis
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOriginal_axis();

	/**
	 * Array containing the data coordinates in the original uncalibrated axis
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
	 * Array containing the data coordinates in the original uncalibrated axis
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
	 * Array containing the data coordinates in the original uncalibrated axis
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
	 * The symbol of the axis to be used in the fit_function, e.g., `energy`, `E`.
	 * This should comply to the following naming rules (similar to python's naming rules):
	 * * A variable name must start with a letter or the underscore character
	 * * A variable name cannot start with a number
	 * * A variable name can only contain alpha-numeric characters and underscores (A-z, 0-9, and _ )
	 * * Variable names are case-sensitive (age, Age and AGE are three different variables)
	 *
	 * @return  the value.
	 */
	public String getOriginal_axisAttributeSymbol();

	/**
	 * The symbol of the axis to be used in the fit_function, e.g., `energy`, `E`.
	 * This should comply to the following naming rules (similar to python's naming rules):
	 * * A variable name must start with a letter or the underscore character
	 * * A variable name cannot start with a number
	 * * A variable name can only contain alpha-numeric characters and underscores (A-z, 0-9, and _ )
	 * * Variable names are case-sensitive (age, Age and AGE are three different variables)
	 *
	 * @param symbolValue the symbolValue
	 */
	public void setOriginal_axisAttributeSymbol(String symbolValue);

	/**
	 * The path from which this data is derived, e.g., raw detector axis.
	 * Should be a valid NeXus path name, e.g., /entry/instrument/detector/raw.
	 *
	 * @return  the value.
	 */
	public String getOriginal_axisAttributeInput_path();

	/**
	 * The path from which this data is derived, e.g., raw detector axis.
	 * Should be a valid NeXus path name, e.g., /entry/instrument/detector/raw.
	 *
	 * @param input_pathValue the input_pathValue
	 */
	public void setOriginal_axisAttributeInput_path(String input_pathValue);

	/**
	 * Additional input axis to be used in the formula.
	 *
	 * @return  the value.
	 */
	public NXparameters getFit_formula_inputs();

	/**
	 * Additional input axis to be used in the formula.
	 *
	 * @param fit_formula_inputsGroup the fit_formula_inputsGroup
	 */
	public void setFit_formula_inputs(NXparameters fit_formula_inputsGroup);

	/**
	 * Fit coefficients to be used in ``fit_formula_description``.
	 * As an example, for nonlinear energy calibrations, e.g. in a time-of-flight (TOF) detector, a polynomial
	 * function is fitted to a set of features (peaks) at well defined energy positions to determine
	 * E(TOF). Here we can store the fit coefficients for that procedure.
	 *
	 * @return  the value.
	 */
	public NXparameters getCalibration_parameters();

	/**
	 * Fit coefficients to be used in ``fit_formula_description``.
	 * As an example, for nonlinear energy calibrations, e.g. in a time-of-flight (TOF) detector, a polynomial
	 * function is fitted to a set of features (peaks) at well defined energy positions to determine
	 * E(TOF). Here we can store the fit coefficients for that procedure.
	 *
	 * @param calibration_parametersGroup the calibration_parametersGroup
	 */
	public void setCalibration_parameters(NXparameters calibration_parametersGroup);

	/**
	 * Here we can store a description of the formula used for the fit function.
	 * For polynomial fits, use a0, a1, ..., an for the coefficients, corresponding to the values in the
	 * coefficients group. Use x0, x1, ..., xm for the mth position in the `original_axis` field.
	 * If there is the symbol attribute specified for the `original_axis` this may be used instead of x.
	 * If you want to use the whole axis use `x`.
	 * Alternate axis can also be available as specified by the `fit_formula_inputs` group.
	 * The data should then be referred here by the `SYMBOL` name, e.g., for a field
	 * name ``my_field`` in ``fit_formula_inputs``, it should be referred here by ``my_field`` or ``my_field0``
	 * if you want to read the zeroth element of the array.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFit_formula_description();

	/**
	 * Here we can store a description of the formula used for the fit function.
	 * For polynomial fits, use a0, a1, ..., an for the coefficients, corresponding to the values in the
	 * coefficients group. Use x0, x1, ..., xm for the mth position in the `original_axis` field.
	 * If there is the symbol attribute specified for the `original_axis` this may be used instead of x.
	 * If you want to use the whole axis use `x`.
	 * Alternate axis can also be available as specified by the `fit_formula_inputs` group.
	 * The data should then be referred here by the `SYMBOL` name, e.g., for a field
	 * name ``my_field`` in ``fit_formula_inputs``, it should be referred here by ``my_field`` or ``my_field0``
	 * if you want to read the zeroth element of the array.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param fit_formula_descriptionDataset the fit_formula_descriptionDataset
	 */
	public DataNode setFit_formula_description(IDataset fit_formula_descriptionDataset);

	/**
	 * Here we can store a description of the formula used for the fit function.
	 * For polynomial fits, use a0, a1, ..., an for the coefficients, corresponding to the values in the
	 * coefficients group. Use x0, x1, ..., xm for the mth position in the `original_axis` field.
	 * If there is the symbol attribute specified for the `original_axis` this may be used instead of x.
	 * If you want to use the whole axis use `x`.
	 * Alternate axis can also be available as specified by the `fit_formula_inputs` group.
	 * The data should then be referred here by the `SYMBOL` name, e.g., for a field
	 * name ``my_field`` in ``fit_formula_inputs``, it should be referred here by ``my_field`` or ``my_field0``
	 * if you want to read the zeroth element of the array.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getFit_formula_descriptionScalar();

	/**
	 * Here we can store a description of the formula used for the fit function.
	 * For polynomial fits, use a0, a1, ..., an for the coefficients, corresponding to the values in the
	 * coefficients group. Use x0, x1, ..., xm for the mth position in the `original_axis` field.
	 * If there is the symbol attribute specified for the `original_axis` this may be used instead of x.
	 * If you want to use the whole axis use `x`.
	 * Alternate axis can also be available as specified by the `fit_formula_inputs` group.
	 * The data should then be referred here by the `SYMBOL` name, e.g., for a field
	 * name ``my_field`` in ``fit_formula_inputs``, it should be referred here by ``my_field`` or ``my_field0``
	 * if you want to read the zeroth element of the array.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param fit_formula_description the fit_formula_description
	 */
	public DataNode setFit_formula_descriptionScalar(String fit_formula_descriptionValue);

	/**
	 * Mapping data for calibration.
	 * This can be used to map data points from uncalibrated to calibrated values,
	 * i.e., by multiplying each point in the input axis by the corresponding point in the mapping data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMapping_mapping();

	/**
	 * Mapping data for calibration.
	 * This can be used to map data points from uncalibrated to calibrated values,
	 * i.e., by multiplying each point in the input axis by the corresponding point in the mapping data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param mapping_mappingDataset the mapping_mappingDataset
	 */
	public DataNode setMapping_mapping(IDataset mapping_mappingDataset);

	/**
	 * Mapping data for calibration.
	 * This can be used to map data points from uncalibrated to calibrated values,
	 * i.e., by multiplying each point in the input axis by the corresponding point in the mapping data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMapping_mappingScalar();

	/**
	 * Mapping data for calibration.
	 * This can be used to map data points from uncalibrated to calibrated values,
	 * i.e., by multiplying each point in the input axis by the corresponding point in the mapping data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param mapping_mapping the mapping_mapping
	 */
	public DataNode setMapping_mappingScalar(Double mapping_mappingValue);

	/**
	 * An array representing the axis after calibration, matching the data length
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ncal;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCalibrated_axis();

	/**
	 * An array representing the axis after calibration, matching the data length
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
	 * An array representing the axis after calibration, matching the data length
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
	 * An array representing the axis after calibration, matching the data length
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
	 * Any data acquired/used during the calibration that does not fit the `NX_FLOAT` fields above.
	 * NXdata groups can be used for multidimensional data which are relevant to the calibration
	 *
	 * @return  the value.
	 */
	public NXdata getData();

	/**
	 * Any data acquired/used during the calibration that does not fit the `NX_FLOAT` fields above.
	 * NXdata groups can be used for multidimensional data which are relevant to the calibration
	 *
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * Any data acquired/used during the calibration that does not fit the `NX_FLOAT` fields above.
	 * NXdata groups can be used for multidimensional data which are relevant to the calibration</li>
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
	 * Any data acquired/used during the calibration that does not fit the `NX_FLOAT` fields above.
	 * NXdata groups can be used for multidimensional data which are relevant to the calibration</li>
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
	 * Any data acquired/used during the calibration that does not fit the `NX_FLOAT` fields above.
	 * NXdata groups can be used for multidimensional data which are relevant to the calibration</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Any data acquired/used during the calibration that does not fit the `NX_FLOAT` fields above.
	 * NXdata groups can be used for multidimensional data which are relevant to the calibration</li>
	 * </ul>
	 *
	 * @param data the child nodes to add
	 */

	public void setAllData(Map<String, NXdata> data);


}
