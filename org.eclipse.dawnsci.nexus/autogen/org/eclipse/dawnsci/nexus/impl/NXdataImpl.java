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
 * The :ref:`NXdata` class is designed to encapsulate all the information required for a set of data to be plotted.
 * NXdata groups contain plottable data (sometimes referred to as *signals* or *dependent variables*) and their
 * associated axis coordinates (sometimes referred to as *axes* or *independent variables*).
 * The actual names of the :ref:`DATA </NXdata/DATA-field>` and :ref:`AXISNAME </NXdata/AXISNAME-field>` fields
 * can be chosen :ref:`freely <validItemName>`, as indicated by the upper case (this is a common convention in all NeXus classes).
 * .. note:: ``NXdata`` provides data and coordinates to be plotted but
 * does not describe how the data is to be plotted or even the dimensionality of the plot.
 * https://www.nexusformat.org/NIAC2018Minutes.html#nxdata-plottype--attribute
 * **Signals:**
 * .. index:: plotting
 * The :ref:`DATA </NXdata/DATA-field>` fields contain the signal values to be plotted. The name of the field
 * to be used as the *default plot signal* is provided by the :ref:`signal </NXdata@signal-attribute>` attribute.
 * The names of the fields to be used as *secondary plot signals* are provided by the
 * :ref:`auxiliary_signals</NXdata@auxiliary_signals-attribute>` attribute.
 * An example with three signals, one of which being the default
 * .. code-block::
 * data:NXdata
 * @signal = "data1"
 * @auxiliary_signals = ["data2", "data3"]
 * data1: float[10,20,30] --> the default signal
 * data2: float[10,20,30]
 * data3: float[10,20,30]
 * **Axes:**
 * .. index:: axes (attribute)
 * .. index:: coordinates
 * The :ref:`AXISNAME </NXdata/AXISNAME-field>` fields contain the axis coordinates associated with the data values.
 * The names of all :ref:`AXISNAME </NXdata/AXISNAME-field>` fields are listed in the
 * :ref:`axes </NXdata@axes-attribute>` attribute.
 * `Rank`
 * :ref:`AXISNAME </NXdata/AXISNAME-field>` fields are typically one-dimensional arrays, which annotate one of the dimensions.
 * An example of this would be
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x", "y"] --> the order matters
 * data: float[10,20]
 * x: float[10] --> coordinates along the first dimension
 * y: float[20] --> coordinates along the second dimension
 * In this example each data point ``data[i,j]`` has axis coordinates ``[x[i], y[j]]``.
 * However, the fields can also have a rank greater than 1, in which case the rank of each
 * :ref:`AXISNAME </NXdata/AXISNAME-field>` must be equal to the number of data dimensions it spans.
 * An example of this would be
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x", "y"] --> the order does NOT matter
 * @x_indices = [0, 1]
 * @y_indices = [0, 1]
 * data: float[10,20]
 * x: float[10,20] --> coordinates along both dimensions
 * y: float[10,20] --> coordinates along both dimensions
 * In this example each data point ``data[i,j]`` has axis coordinates ``[x[i,j], y[i,j]]``.
 * `Dimensions`
 * The data dimensions annotated by an :ref:`AXISNAME </NXdata/AXISNAME-field>` field are defined by the
 * :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` attribute. When this attribute is missing,
 * the position(s) of the :ref:`AXISNAME </NXdata/AXISNAME-field>` string in the
 * :ref:`axes </NXdata@axes-attribute>` attribute are used.
 * When all :ref:`AXISNAME </NXdata/AXISNAME-field>` fields are one-dimensional, and none of the data dimensions
 * have more than one axis, the :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` attributes
 * are often omitted. If one of the data dimensions has no :ref:`AXISNAME </NXdata/AXISNAME-field>` field,
 * the string “.” can be used in the corresponding index of the axes list.
 * An example of this would be
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x", ".", "z"] --> the order matters
 * data: float[10,20,30]
 * x: float[10] --> coordinates along the first dimension
 * z: float[30] --> coordinates along the third dimension
 * When using :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` this becomes
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x", "z"] --> the order does NOT matter
 * data: float[10,20,30]
 * @x_indices = 0
 * @z_indices = 2
 * x: float[10] --> coordinates along the first dimension
 * z: float[30] --> coordinates along the third dimension
 * When providing :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` attributes it is recommended
 * to do it for all axes.
 * `Non-trivial axes`
 * What follows are two examples where :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` attributes
 * cannot be omitted.
 * The first is an example where data dimensions have alternative axis coordinates. The NXdata group represents
 * a stack of images collected at different energies. The ``wavelength`` is an alternative axis of ``energy``
 * for the last dimension (or vice versa).
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x", "y", "energy", "wavelength"] --> the order does NOT matter
 * @x_indices = 0
 * @y_indices = 1
 * @energy_indices = 2
 * @wavelength_indices = 2
 * data: float[10,20,30]
 * x: float[10] --> coordinates along the first dimension
 * y: float[20] --> coordinates along the second dimension
 * energy: float[30] --> coordinates along the third dimension
 * wavelength: float[30] --> coordinates along the third dimension
 * The second is an example with coordinates that span more than one dimension. The NXdata group represents data
 * from 2D mesh scans performed at multiple energies. Each data point ``data[i,j,k]`` has axis coordinates
 * ``[x[i,j,k], y[i,j,k], energy[k]]``.
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x", "y", "energy"] --> the order does NOT matter
 * @x_indices = [0, 1, 2]
 * @y_indices = [0, 1, 2]
 * @energy_indices = 2
 * data: float[10,20,30]
 * x: float[10,20,30] --> coordinates along all dimensions
 * y: float[10,20,30] --> coordinates along all dimensions
 * energy: float[30] --> coordinates along the third dimension
 * **Uncertainties:**
 * Standard deviations on data values as well as coordinates can be provided by
 * :ref:`FIELDNAME_errors </NXdata/FIELDNAME_errors-field>` fields where ``FIELDNAME`` is the name of a
 * :ref:`DATA </NXdata/DATA-field>` field or an :ref:`AXISNAME </NXdata/AXISNAME-field>` field.
 * An example of uncertainties on the signal, auxiliary signals and axis coordinates
 * .. code-block::
 * data:NXdata
 * @signal = "data1"
 * @auxiliary_signals = ["data2", "data3"]
 * @axes = ["x", "z"]
 * @x_indices = 0
 * @z_indices = 2
 * data1: float[10,20,30]
 * data2: float[10,20,30]
 * data3: float[10,20,30]
 * x: float[10]
 * z: float[30]
 * data1_errors: float[10,20,30]
 * data2_errors: float[10,20,30]
 * data3_errors: float[10,20,30]
 * x_errors: float[10]
 * z_errors: float[30]

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
	public String getAttributeSignal() {
		return getAttrString(null, NX_ATTRIBUTE_SIGNAL);
	}

	@Override
	public void setAttributeSignal(String signalValue) {
		setAttribute(null, NX_ATTRIBUTE_SIGNAL, signalValue);
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
	public String getAttributeDefault_slice() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT_SLICE);
	}

	@Override
	public void setAttributeDefault_slice(String default_sliceValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT_SLICE, default_sliceValue);
	}

	@Override
	public Long getAttributeIndices(String axisname) {
		return getAttrLong(null, axisname + NX_ATTRIBUTE_INDICES);
	}

	@Override
	public void setAttributeIndices(String axisname, Long indicesValue) {
		setAttribute(null, axisname + NX_ATTRIBUTE_INDICES, indicesValue);
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
	public IDataset getAxisname(String axisname) {
		return getDataset(axisname);
	}

	@Override
	public Object getAxisnameScalar(String axisname) {
		return getDataset(axisname).getObject();
	}

	@Override
	public DataNode setAxisname(String axisname, IDataset axisnameDataset) {
		return setDataset(axisname, axisnameDataset);
	}

	@Override
	public DataNode setAxisnameScalar(String axisname, Object axisnameValue) {
		if (axisnameValue instanceof Number) {
			return setField(axisname, axisnameValue);
		} else if (axisname instanceof String) {
			return setString(axisname, (String) axisnameValue);
		} else {
			throw new IllegalArgumentException("Value must be String or Number");
		}
	}

	@Override
	public Map<String, IDataset> getAllAxisname() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public String getAxisnameAttributeLong_name(String axisname) {
		return getAttrString(axisname, NX_AXISNAME_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setAxisnameAttributeLong_name(String axisname, String long_nameValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public String getAxisnameAttributeUnits(String axisname) {
		return getAttrString(axisname, NX_AXISNAME_ATTRIBUTE_UNITS);
	}

	@Override
	public void setAxisnameAttributeUnits(String axisname, String unitsValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_UNITS, unitsValue);
	}

	@Override
	public Boolean getAxisnameAttributeDistribution(String axisname) {
		return getAttrBoolean(axisname, NX_AXISNAME_ATTRIBUTE_DISTRIBUTION);
	}

	@Override
	public void setAxisnameAttributeDistribution(String axisname, Boolean distributionValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_DISTRIBUTION, distributionValue);
	}

	@Override
	public Long getAxisnameAttributeFirst_good(String axisname) {
		return getAttrLong(axisname, NX_AXISNAME_ATTRIBUTE_FIRST_GOOD);
	}

	@Override
	public void setAxisnameAttributeFirst_good(String axisname, Long first_goodValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_FIRST_GOOD, first_goodValue);
	}

	@Override
	public Long getAxisnameAttributeLast_good(String axisname) {
		return getAttrLong(axisname, NX_AXISNAME_ATTRIBUTE_LAST_GOOD);
	}

	@Override
	public void setAxisnameAttributeLast_good(String axisname, Long last_goodValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_LAST_GOOD, last_goodValue);
	}

	@Override
	@Deprecated
	public Long getAxisnameAttributeAxis(String axisname) {
		return getAttrLong(axisname, NX_AXISNAME_ATTRIBUTE_AXIS);
	}

	@Override
	@Deprecated
	public void setAxisnameAttributeAxis(String axisname, Long axisValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_AXIS, axisValue);
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
	public IDataset getErrors(String fieldname) {
		return getDataset(fieldname + NX_ERRORS_SUFFIX);
	}

	@Override
	public Number getErrorsScalar(String fieldname) {
		return getNumber(fieldname + NX_ERRORS_SUFFIX);
	}

	@Override
	public DataNode setErrors(String fieldname, IDataset errorsDataset) {
		return setDataset(fieldname + NX_ERRORS_SUFFIX, errorsDataset);
	}

	@Override
	public DataNode setErrorsScalar(String fieldname, Number errorsValue) {
		return setField(fieldname + NX_ERRORS_SUFFIX, errorsValue);
	}

	@Override
	public Map<String, IDataset> getAllErrors() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	@Deprecated
	public IDataset getErrors() {
		return getDataset(NX_ERRORS);
	}

	@Override
	@Deprecated
	public Number getErrorsScalar() {
		return getNumber(NX_ERRORS);
	}

	@Override
	@Deprecated
	public DataNode setErrors(IDataset errorsDataset) {
		return setDataset(NX_ERRORS, errorsDataset);
	}

	@Override
	@Deprecated
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
