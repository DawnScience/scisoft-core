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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * :ref:`NXdata` describes the plottable data and related dimension scales.
 * .. index:: plotting
 * It is mandatory that there is at least one :ref:`NXdata` group
 * in each :ref:`NXentry` group.
 * Note that the ``variable`` and ``data``
 * can be defined with different names.
 * The ``signal`` and ``axes`` attributes of the
 * ``data`` group define which items
 * are plottable data and which are *dimension scales*, respectively.
 * :ref:`NXdata` is used to implement one of the basic motivations in NeXus,
 * to provide a default plot for the data of this :ref:`NXentry`. The actual data
 * might be stored in another group and (hard) linked to the :ref:`NXdata` group.
 * * Each :ref:`NXdata` group will define only one data set
 * containing plottable data, dimension scales, and
 * possibly associated standard deviations.
 * Other data sets may be present in the group.
 * * The plottable data may be of arbitrary rank up to a maximum
 * of ``NX_MAXRANK=32``.
 * * The plottable data will be named as the value of
 * the group ``signal`` attribute, such as::
 * data:NXdata
 * @signal = "counts"
 * @axes = "mr"
 * @mr_indices = 0
 * counts: float[100] --> the default dependent data
 * mr: float[100] --> the default independent data
 * The field named in the ``signal`` attribute **must** exist, either
 * directly as a dataset or defined through a link.
 * * The group ``axes`` attribute will name the
 * *dimension scale* associated with the plottable data.
 * If available, the standard deviations of the data are to be
 * stored in a data set of the same rank and dimensions, with the name ``errors``.
 * * For each data dimension, there should be a one-dimensional array
 * of the same length.
 * * These one-dimensional arrays are the *dimension scales* of the
 * data, *i.e*. the values of the independent variables at which the data
 * is measured, such as scattering angle or energy transfer.
 * .. index:: link
 * .. index:: axes (attribute)
 * The preferred method to associate each data dimension with
 * its respective dimension scale is to specify the field name
 * of each dimension scale in the group ``axes`` attribute as a string list.
 * Here is an example for a 2-D data set *data* plotted
 * against *time*, and *pressure*. (An additional *temperature* data set
 * is provided and could be selected as an alternate for the *pressure* axis.)::
 * data_2d:NXdata
 * @signal="data"
 * @axes=["time", "pressure"]
 * @pressure_indices=1
 * @temperature_indices=1
 * @time_indices=0
 * data: float[1000,20]
 * pressure: float[20]
 * temperature: float[20]
 * time: float[1000]
 * .. rubric:: Old methods to identify the plottable data
 * There are two older methods of associating
 * each data dimension to its respective dimension scale.
 * Both are now out of date and
 * should not be used when writing new data files.
 * However, client software should expect to see data files
 * written with any of these methods.
 * * One method uses the ``axes``
 * attribute to specify the names of each *dimension scale*.
 * * The oldest method uses the ``axis`` attribute on each
 * *dimension scale* to identify
 * with an integer the axis whose value is the number of the dimension.
 * .. index: !plot; axis label
 * plot, axis units
 * units
 * dimension scale
 * Each axis of the plot may be labeled with information from the
 * dimension scale for that axis. The optional ``@long_name`` attribute
 * is provided as the axis label default. If ``@long_name`` is not
 * defined, then use the name of the dimension scale. A ``@units`` attribute,
 * if available, may be added to the axis label for further description.
 * See the section :ref:`Design-Units` for more information.
 * .. index: !plot; axis title
 * The optional ``title`` field, if available, provides a suggested
 * title for the plot. If no ``title`` field is found in the :ref:`NXdata`
 * group, look for a ``title`` field in the parent :ref:`NXentry` group,
 * with a fallback to displaying the path to the :ref:`NXdata` group.
 * NeXus is about how to find and annotate the data to be plotted
 * but not to describe how the data is to be plotted.
 * (https://www.nexusformat.org/NIAC2018Minutes.html#nxdata-plottype--attribute)
 * 
 */
public class NXdataImpl extends NXobjectImpl implements NXdata {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdataImpl() {
		super();
	}

	public NXdataImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdata.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DATA;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public String getAttributeAuxiliary_signals() {
		return getAttrString(null, NX_ATTRIBUTE_AUXILIARY_SIGNALS);
	}

	@Override
	public void setAttributeAuxiliary_signals(String auxiliary_signalsValue) {
		setAttribute(null, NX_ATTRIBUTE_AUXILIARY_SIGNALS, auxiliary_signalsValue);
	}

	@Override
	public String getAttributeSignal() {
		return getAttrString(null, NX_ATTRIBUTE_SIGNAL);
	}

	@Override
	public void setAttributeSignal(String signalValue) {
		setAttribute(null, NX_ATTRIBUTE_SIGNAL, signalValue);
	}

	@Override
	public String getAttributeAxes() {
		return getAttrString(null, NX_ATTRIBUTE_AXES);
	}

	@Override
	public void setAttributeAxes(String axesValue) {
		setAttribute(null, NX_ATTRIBUTE_AXES, axesValue);
	}

	@Override
	public String getAttributeIndices(String axisname) {
		return getAttrString(null, axisname + NX_ATTRIBUTE_INDICES);
	}

	@Override
	public void setAttributeIndices(String axisname, String indicesValue) {
		setAttribute(null, axisname + NX_ATTRIBUTE_INDICES, indicesValue);
	}

	@Override
	public IDataset getVariable(String variable) {
		return getDataset(variable);
	}

	@Override
	public Number getVariableScalar(String variable) {
		return getNumber(variable);
	}

	@Override
	public DataNode setVariable(String variable, IDataset variableDataset) {
		return setDataset(variable, variableDataset);
	}

	@Override
	public DataNode setVariableScalar(String variable, Number variableValue) {
		return setField(variable, variableValue);
	}

	@Override
	public Map<String, IDataset> getAllVariable() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public String getVariableAttributeLong_name(String variable) {
		return getAttrString(variable, NX_VARIABLE_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setVariableAttributeLong_name(String variable, String long_nameValue) {
		setAttribute(variable, NX_VARIABLE_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public Boolean getVariableAttributeDistribution(String variable) {
		return getAttrBoolean(variable, NX_VARIABLE_ATTRIBUTE_DISTRIBUTION);
	}

	@Override
	public void setVariableAttributeDistribution(String variable, Boolean distributionValue) {
		setAttribute(variable, NX_VARIABLE_ATTRIBUTE_DISTRIBUTION, distributionValue);
	}

	@Override
	public Long getVariableAttributeFirst_good(String variable) {
		return getAttrLong(variable, NX_VARIABLE_ATTRIBUTE_FIRST_GOOD);
	}

	@Override
	public void setVariableAttributeFirst_good(String variable, Long first_goodValue) {
		setAttribute(variable, NX_VARIABLE_ATTRIBUTE_FIRST_GOOD, first_goodValue);
	}

	@Override
	public Long getVariableAttributeLast_good(String variable) {
		return getAttrLong(variable, NX_VARIABLE_ATTRIBUTE_LAST_GOOD);
	}

	@Override
	public void setVariableAttributeLast_good(String variable, Long last_goodValue) {
		setAttribute(variable, NX_VARIABLE_ATTRIBUTE_LAST_GOOD, last_goodValue);
	}

	@Override
	@Deprecated
	public Long getVariableAttributeAxis(String variable) {
		return getAttrLong(variable, NX_VARIABLE_ATTRIBUTE_AXIS);
	}

	@Override
	@Deprecated
	public void setVariableAttributeAxis(String variable, Long axisValue) {
		setAttribute(variable, NX_VARIABLE_ATTRIBUTE_AXIS, axisValue);
	}

	@Override
	public IDataset getErrors(String variable) {
		return getDataset(variable + NX_ERRORS_SUFFIX);
	}

	@Override
	public Number getErrorsScalar(String variable) {
		return getNumber(variable + NX_ERRORS_SUFFIX);
	}

	@Override
	public DataNode setErrors(String variable, IDataset errorsDataset) {
		return setDataset(variable + NX_ERRORS_SUFFIX, errorsDataset);
	}

	@Override
	public DataNode setErrorsScalar(String variable, Number errorsValue) {
		return setField(variable + NX_ERRORS_SUFFIX, errorsValue);
	}

	@Override
	public Map<String, IDataset> getAllErrors() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public IDataset getData(String data) {
		return getDataset(data);
	}

	@Override
	public Number getDataScalar(String data) {
		return getNumber(data);
	}

	@Override
	public DataNode setData(String data, IDataset dataDataset) {
		return setDataset(data, dataDataset);
	}

	@Override
	public DataNode setDataScalar(String data, Number dataValue) {
		return setField(data, dataValue);
	}

	@Override
	public Map<String, IDataset> getAllData() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	@Deprecated
	public Long getDataAttributeSignal(String data) {
		return getAttrLong(data, NX_DATA_ATTRIBUTE_SIGNAL);
	}

	@Override
	@Deprecated
	public void setDataAttributeSignal(String data, Long signalValue) {
		setAttribute(data, NX_DATA_ATTRIBUTE_SIGNAL, signalValue);
	}

	@Override
	@Deprecated
	public String getDataAttributeAxes(String data) {
		return getAttrString(data, NX_DATA_ATTRIBUTE_AXES);
	}

	@Override
	@Deprecated
	public void setDataAttributeAxes(String data, String axesValue) {
		setAttribute(data, NX_DATA_ATTRIBUTE_AXES, axesValue);
	}

	@Override
	public String getDataAttributeLong_name(String data) {
		return getAttrString(data, NX_DATA_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setDataAttributeLong_name(String data, String long_nameValue) {
		setAttribute(data, NX_DATA_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public IDataset getErrors() {
		return getDataset(NX_ERRORS);
	}

	@Override
	public Number getErrorsScalar() {
		return getNumber(NX_ERRORS);
	}

	@Override
	public DataNode setErrors(IDataset errorsDataset) {
		return setDataset(NX_ERRORS, errorsDataset);
	}

	@Override
	public DataNode setErrorsScalar(Number errorsValue) {
		return setField(NX_ERRORS, errorsValue);
	}

	@Override
	public IDataset getScaling_factor() {
		return getDataset(NX_SCALING_FACTOR);
	}

	@Override
	public Double getScaling_factorScalar() {
		return getDouble(NX_SCALING_FACTOR);
	}

	@Override
	public DataNode setScaling_factor(IDataset scaling_factorDataset) {
		return setDataset(NX_SCALING_FACTOR, scaling_factorDataset);
	}

	@Override
	public DataNode setScaling_factorScalar(Double scaling_factorValue) {
		return setField(NX_SCALING_FACTOR, scaling_factorValue);
	}

	@Override
	public IDataset getOffset() {
		return getDataset(NX_OFFSET);
	}

	@Override
	public Double getOffsetScalar() {
		return getDouble(NX_OFFSET);
	}

	@Override
	public DataNode setOffset(IDataset offsetDataset) {
		return setDataset(NX_OFFSET, offsetDataset);
	}

	@Override
	public DataNode setOffsetScalar(Double offsetValue) {
		return setField(NX_OFFSET, offsetValue);
	}

	@Override
	public IDataset getTitle() {
		return getDataset(NX_TITLE);
	}

	@Override
	public String getTitleScalar() {
		return getString(NX_TITLE);
	}

	@Override
	public DataNode setTitle(IDataset titleDataset) {
		return setDataset(NX_TITLE, titleDataset);
	}

	@Override
	public DataNode setTitleScalar(String titleValue) {
		return setString(NX_TITLE, titleValue);
	}

	@Override
	public IDataset getX() {
		return getDataset(NX_X);
	}

	@Override
	public Double getXScalar() {
		return getDouble(NX_X);
	}

	@Override
	public DataNode setX(IDataset xDataset) {
		return setDataset(NX_X, xDataset);
	}

	@Override
	public DataNode setXScalar(Double xValue) {
		return setField(NX_X, xValue);
	}

	@Override
	public IDataset getY() {
		return getDataset(NX_Y);
	}

	@Override
	public Double getYScalar() {
		return getDouble(NX_Y);
	}

	@Override
	public DataNode setY(IDataset yDataset) {
		return setDataset(NX_Y, yDataset);
	}

	@Override
	public DataNode setYScalar(Double yValue) {
		return setField(NX_Y, yValue);
	}

	@Override
	public IDataset getZ() {
		return getDataset(NX_Z);
	}

	@Override
	public Double getZScalar() {
		return getDouble(NX_Z);
	}

	@Override
	public DataNode setZ(IDataset zDataset) {
		return setDataset(NX_Z, zDataset);
	}

	@Override
	public DataNode setZScalar(Double zValue) {
		return setField(NX_Z, zValue);
	}

}
