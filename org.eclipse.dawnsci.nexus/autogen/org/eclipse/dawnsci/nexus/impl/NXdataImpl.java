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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * The :ref:`NXdata` class is designed to encapsulate all the information required for a set of data to be plotted.
 * NXdata groups contain plottable data (also referred to as *signals* or *dependent variables*) and their
 * associated axis coordinates (also referred to as *axes* or *independent variables*).
 * The actual names of the :ref:`DATA </NXdata/DATA-field>` and :ref:`AXISNAME </NXdata/AXISNAME-field>` fields
 * can be chosen :ref:`freely <validItemName>`, as indicated by the upper case (this is a common convention in all NeXus classes).
 * .. note:: ``NXdata`` provides data and coordinates to be plotted but
 * does not describe how the data is to be plotted or even the dimensionality of the plot.
 * https://www.nexusformat.org/NIAC2018Minutes.html#nxdata-plottype--attribute
 * .. include:: data/index.rst
 * :start-line: 1
 * .. admonition:: Example of a simple curve plot
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x"]
 * data: float[100]
 * x: float[100]
 * More complex cases are supported
 * * histogram data: ``x`` has one more value than ``data``.
 * * alternative axes: instead of a single ``x`` axis you can have several axes, one of which being the default.
 * * signals with more than one dimension: ``data`` could be 2D with axes ``x`` and ``y`` along each dimension.
 * * axes with more than one dimension: ``data`` could be 2D with axes ``x`` and ``y`` also being 2D, providing a
 * unique ``(x, y)`` coordinate for each ``data`` point.
 * **Signals:**
 * .. index:: plotting
 * .. admonition:: Defined by
 * * :ref:`DATA </NXdata/DATA-field>` fields
 * * the :ref:`signal </NXdata@signal-attribute>` attribute
 * * the :ref:`auxiliary_signals</NXdata@auxiliary_signals-attribute>` attribute
 * The :ref:`DATA </NXdata/DATA-field>` fields contain the signal values to be plotted. The name of the field
 * to be used as the *default plot signal* is provided by the :ref:`signal </NXdata@signal-attribute>` attribute.
 * The names of the fields to be used as *secondary plot signals* are provided by the
 * :ref:`auxiliary_signals</NXdata@auxiliary_signals-attribute>` attribute.
 * .. admonition:: An example with three signals, one of which being the default
 * .. code-block::
 * data:NXdata
 * @signal = "data1"
 * @auxiliary_signals = ["data2", "data3"]
 * data1: float[10,20,30] # the default signal
 * data2: float[10,20,30]
 * data3: float[10,20,30]
 * **Axes:**
 * .. index:: axes (attribute)
 * .. index:: coordinates
 * .. admonition:: Defined by
 * * :ref:`AXISNAME </NXdata/AXISNAME-field>` fields
 * * the :ref:`axes </NXdata@axes-attribute>` attribute
 * * :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` attributes
 * The fields and attributes are defined as follows
 * 1. The :ref:`AXISNAME </NXdata/AXISNAME-field>` fields contain the axis coordinates associated with the signal values.
 * 2. The :ref:`axes </NXdata@axes-attribute>` attribute provides the names of the :ref:`AXISNAME </NXdata/AXISNAME-field>`
 * fields to be used as the `default axis` for each dimension of the :ref:`DATA </NXdata/DATA-field>` fields.
 * 3. The :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` attributes describe the :ref:`DATA </NXdata/DATA-field>`
 * dimensions spanned by the corresponding :ref:`AXISNAME </NXdata/AXISNAME-field>` fields.
 * The fields and attributes have the following constraints
 * 1. The length of the :ref:`axes </NXdata@axes-attribute>` attribute must be equal to the rank of the :ref:`DATA </NXdata/DATA-field>`
 * fields. When a particular dimension has no default axis, the string “.” is used in that position.
 * 2. The number of values in :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` must be equal to the rank of the corresponding
 * :ref:`AXISNAME </NXdata/AXISNAME-field>` field.
 * 3. When :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` is missing for a given
 * :ref:`AXISNAME </NXdata/AXISNAME-field>` field, the positions of the :ref:`AXISNAME </NXdata/AXISNAME-field>`
 * field name in the :ref:`axes </NXdata@axes-attribute>` attribute are used.
 * 4. When :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>` is the same as the indices of "AXISNAME" in the
 * :ref:`axes </NXdata@axes-attribute>` attribute, there is no need to provide
 * :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>`.
 * 5. The indices of "AXISNAME" in the :ref:`axes </NXdata@axes-attribute>` attribute must be a subset of
 * :ref:`AXISNAME_indices </NXdata@AXISNAME_indices-attribute>`.
 * 6. The shape of an :ref:`AXISNAME </NXdata/AXISNAME-field>` field must correspond to the shape of the
 * :ref:`DATA </NXdata/DATA-field>` dimensions it spans. This means that for each dimension ``i`` in ``[0, AXISNAME.ndim)``
 * spanned by axis field :ref:`AXISNAME </NXdata/AXISNAME-field>`, the number of axis values ``AXISNAME.shape[i]``
 * along dimension ``i`` must be equal to the number of data points ``DATA.shape[AXISNAME_indices[i]]`` along dimension ``i``
 * or one more than the number of data points ``DATA.shape[AXISNAME_indices[i]]+1`` in case the
 * :ref:`AXISNAME </NXdata/AXISNAME-field>` field contains histogram bin edges along dimension ``i``.
 * Highlight consequences of these constraints
 * 1. An :ref:`AXISNAME </NXdata/AXISNAME-field>` field can have more than one dimension and can therefore span
 * more than one :ref:`DATA </NXdata/DATA-field>` dimension. Conversely, one :ref:`DATA </NXdata/DATA-field>` dimension
 * can be spanned by more than one :ref:`AXISNAME </NXdata/AXISNAME-field>` field. The default axis name (if any)
 * of each dimension can be found in the :ref:`axes </NXdata@axes-attribute>` attribute.
 * 2. A list of all available axes is not provided directly. All strings in the :ref:`axes </NXdata@axes-attribute>` attribute
 * (excluding the “.” string) are axis field names. In addition the prefix of an attribute ending with the string "_indices" is also
 * an axis field name.
 * .. admonition:: The following example covers all axes features supported (see :ref:`sphx_glr_classes_base_classes_data_plot_fscan2d.py`)
 * .. code-block::
 * data:NXdata
 * @signal = "data"
 * @axes = ["x_set", "y_set", "."] # default axes for all three dimensions
 * @x_encoder_indices = [0, 1]
 * @y_encoder_indices = 1 # or [1]
 * data: float[10,7,1024]
 * x_encoder: float[11,7] # coordinates along the first and second dimensions
 * y_encoder: float[7] # coordinates along the second dimension
 * x_set: float[10] # default coordinates along the first dimension
 * y_set: float[7] # default coordinates along the second dimension
 * **Uncertainties:**
 * .. admonition:: Defined by
 * * :ref:`FIELDNAME_errors </NXdata/FIELDNAME_errors-field>` fields
 * Standard deviations on data values as well as coordinates can be provided by
 * :ref:`FIELDNAME_errors </NXdata/FIELDNAME_errors-field>` fields where ``FIELDNAME`` is the name of a
 * :ref:`DATA </NXdata/DATA-field>` field or an :ref:`AXISNAME </NXdata/AXISNAME-field>` field.
 * .. admonition:: An example of uncertainties on the signal, auxiliary signals and axis coordinates
 * .. code-block::
 * data:NXdata
 * @signal = "data1"
 * @auxiliary_signals = ["data2", "data3"]
 * @axes = ["x", ".", "z"]
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
	public Dataset getAxisname(String axisname) {
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
		} else if (axisnameValue instanceof String) {
			return setString(axisname, (String) axisnameValue);
		} else {
			throw new IllegalArgumentException("Value must be String or Number");
		}
	}

	@Override
	public Map<String, Dataset> getAllAxisname() {
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
	public Dataset getData(String data) {
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
	public Map<String, Dataset> getAllData() {
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
	public Dataset getFieldname_errors() {
		return getDataset(NX_FIELDNAME_ERRORS);
	}

	@Override
	public Number getFieldname_errorsScalar() {
		return getNumber(NX_FIELDNAME_ERRORS);
	}

	@Override
	public DataNode setFieldname_errors(IDataset fieldname_errorsDataset) {
		return setDataset(NX_FIELDNAME_ERRORS, fieldname_errorsDataset);
	}

	@Override
	public DataNode setFieldname_errorsScalar(Number fieldname_errorsValue) {
		return setField(NX_FIELDNAME_ERRORS, fieldname_errorsValue);
	}

	@Override
	@Deprecated
	public Dataset getErrors() {
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
	public Dataset getFieldname_scaling_factor() {
		return getDataset(NX_FIELDNAME_SCALING_FACTOR);
	}

	@Override
	public Number getFieldname_scaling_factorScalar() {
		return getNumber(NX_FIELDNAME_SCALING_FACTOR);
	}

	@Override
	public DataNode setFieldname_scaling_factor(IDataset fieldname_scaling_factorDataset) {
		return setDataset(NX_FIELDNAME_SCALING_FACTOR, fieldname_scaling_factorDataset);
	}

	@Override
	public DataNode setFieldname_scaling_factorScalar(Number fieldname_scaling_factorValue) {
		return setField(NX_FIELDNAME_SCALING_FACTOR, fieldname_scaling_factorValue);
	}

	@Override
	public Dataset getFieldname_offset() {
		return getDataset(NX_FIELDNAME_OFFSET);
	}

	@Override
	public Number getFieldname_offsetScalar() {
		return getNumber(NX_FIELDNAME_OFFSET);
	}

	@Override
	public DataNode setFieldname_offset(IDataset fieldname_offsetDataset) {
		return setDataset(NX_FIELDNAME_OFFSET, fieldname_offsetDataset);
	}

	@Override
	public DataNode setFieldname_offsetScalar(Number fieldname_offsetValue) {
		return setField(NX_FIELDNAME_OFFSET, fieldname_offsetValue);
	}

	@Override
	@Deprecated
	public Dataset getScaling_factor() {
		return getDataset(NX_SCALING_FACTOR);
	}

	@Override
	@Deprecated
	public Double getScaling_factorScalar() {
		return getDouble(NX_SCALING_FACTOR);
	}

	@Override
	@Deprecated
	public DataNode setScaling_factor(IDataset scaling_factorDataset) {
		return setDataset(NX_SCALING_FACTOR, scaling_factorDataset);
	}

	@Override
	@Deprecated
	public DataNode setScaling_factorScalar(Double scaling_factorValue) {
		return setField(NX_SCALING_FACTOR, scaling_factorValue);
	}

	@Override
	@Deprecated
	public Dataset getOffset() {
		return getDataset(NX_OFFSET);
	}

	@Override
	@Deprecated
	public Double getOffsetScalar() {
		return getDouble(NX_OFFSET);
	}

	@Override
	@Deprecated
	public DataNode setOffset(IDataset offsetDataset) {
		return setDataset(NX_OFFSET, offsetDataset);
	}

	@Override
	@Deprecated
	public DataNode setOffsetScalar(Double offsetValue) {
		return setField(NX_OFFSET, offsetValue);
	}

	@Override
	public Dataset getTitle() {
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
	public Dataset getX() {
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
	public Dataset getY() {
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
	public Dataset getZ() {
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
