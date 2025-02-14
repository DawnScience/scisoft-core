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
 * <p><b>Symbols:</b>
 * These symbols will be used below to coordinate fields with the same shape.<ul>
 * <li><b>dataRank</b>
 * rank of the ``DATA`` field(s)</li>
 * <li><b>nx</b>
 * length of the ``x`` field</li>
 * <li><b>ny</b>
 * length of the ``y`` field</li>
 * <li><b>nz</b>
 * length of the ``z`` field</li></ul></p>
 *
 */
public interface NXdata extends NXobject {

	public static final String NX_ATTRIBUTE_SIGNAL = "signal";
	public static final String NX_ATTRIBUTE_AUXILIARY_SIGNALS = "auxiliary_signals";
	public static final String NX_ATTRIBUTE_DEFAULT_SLICE = "default_slice";
	public static final String NX_ATTRIBUTE_INDICES = "indices";
	public static final String NX_ATTRIBUTE_AXES = "axes";
	public static final String NX_AXISNAME = "axisname";
	public static final String NX_AXISNAME_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_AXISNAME_ATTRIBUTE_UNITS = "units";
	public static final String NX_AXISNAME_ATTRIBUTE_DISTRIBUTION = "distribution";
	public static final String NX_AXISNAME_ATTRIBUTE_FIRST_GOOD = "first_good";
	public static final String NX_AXISNAME_ATTRIBUTE_LAST_GOOD = "last_good";
	public static final String NX_AXISNAME_ATTRIBUTE_AXIS = "axis";
	public static final String NX_DATA = "data";
	public static final String NX_DATA_ATTRIBUTE_SIGNAL = "signal";
	public static final String NX_DATA_ATTRIBUTE_AXES = "axes";
	public static final String NX_DATA_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_ERRORS_SUFFIX = "_errors";
	public static final String NX_ERRORS = "errors";
	public static final String NX_SCALING_FACTOR = "scaling_factor";
	public static final String NX_OFFSET = "offset";
	public static final String NX_TITLE = "title";
	public static final String NX_X = "x";
	public static final String NX_Y = "y";
	public static final String NX_Z = "z";
	/**
	 * .. index:: find the default plottable data
	 * .. index:: plotting
	 * .. index:: signal attribute value
	 * The value is the :ref:`name <validItemName>` of the signal that contains
	 * the default plottable data. This field or link *must* exist and be a direct child
	 * of this NXdata group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * rather than adding a signal attribute to the field.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 *
	 * @return  the value.
	 */
	public String getAttributeSignal();

	/**
	 * .. index:: find the default plottable data
	 * .. index:: plotting
	 * .. index:: signal attribute value
	 * The value is the :ref:`name <validItemName>` of the signal that contains
	 * the default plottable data. This field or link *must* exist and be a direct child
	 * of this NXdata group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * rather than adding a signal attribute to the field.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 *
	 * @param signalValue the signalValue
	 */
	public void setAttributeSignal(String signalValue);

	/**
	 * .. index:: plotting
	 * Array of strings holding the :ref:`names <validItemName>` of additional
	 * signals to be plotted with the :ref:`default signal </NXdata@signal-attribute>`.
	 * These fields or links *must* exist and be direct children of this NXdata group.
	 * Each auxiliary signal needs to be of the same shape as the default signal.
	 * .. NIAC2018:
	 * https://www.nexusformat.org/NIAC2018Minutes.html
	 *
	 * @return  the value.
	 */
	public String getAttributeAuxiliary_signals();

	/**
	 * .. index:: plotting
	 * Array of strings holding the :ref:`names <validItemName>` of additional
	 * signals to be plotted with the :ref:`default signal </NXdata@signal-attribute>`.
	 * These fields or links *must* exist and be direct children of this NXdata group.
	 * Each auxiliary signal needs to be of the same shape as the default signal.
	 * .. NIAC2018:
	 * https://www.nexusformat.org/NIAC2018Minutes.html
	 *
	 * @param auxiliary_signalsValue the auxiliary_signalsValue
	 */
	public void setAttributeAuxiliary_signals(String auxiliary_signalsValue);

	/**
	 * Which slice of data to show in a plot by default. This is useful especially for
	 * datasets with more than 2 dimensions.
	 * Should be an array of length equal to the number of dimensions
	 * in the data, with the following possible values:
	 * * ".": All the data in this dimension should be included
	 * * Integer: Only this slice should be used.
	 * * String: Only this slice should be used. Use if ``AXISNAME`` is a string
	 * array.
	 * Example::
	 * data:NXdata
	 * @signal = "data"
	 * @axes = ["image_id", "channel", ".", "."]
	 * @image_id_indices = 0
	 * @channel_indices = 1
	 * @default_slice = [".", "difference", ".", "."]
	 * image_id = [1, ..., nP]
	 * channel = ["threshold_1", "threshold_2", "difference"]
	 * data = uint[nP, nC, i, j]
	 * Here, a data array with four dimensions, including the number of images
	 * (nP) and number of channels (nC), specifies more dimensions than can be
	 * visualized with a 2D image viewer for a given image. Therefore the
	 * default_slice attribute specifies that the "difference" channel should be
	 * shown by default.
	 * Alternate version using an integer would look like this (note 2 is a string)::
	 * data:NXdata
	 * @signal = "data"
	 * @axes = ["image_id", "channel", ".", "."]
	 * @image_id_indices = 0
	 * @channel_indices = 1
	 * @default_slice = [".", "2", ".", "."]
	 * image_id = [1, ..., nP]
	 * channel = ["threshold_1", "threshold_2", "difference"]
	 * data = uint[nP, nC, i, j]
	 *
	 * @return  the value.
	 */
	public String getAttributeDefault_slice();

	/**
	 * Which slice of data to show in a plot by default. This is useful especially for
	 * datasets with more than 2 dimensions.
	 * Should be an array of length equal to the number of dimensions
	 * in the data, with the following possible values:
	 * * ".": All the data in this dimension should be included
	 * * Integer: Only this slice should be used.
	 * * String: Only this slice should be used. Use if ``AXISNAME`` is a string
	 * array.
	 * Example::
	 * data:NXdata
	 * @signal = "data"
	 * @axes = ["image_id", "channel", ".", "."]
	 * @image_id_indices = 0
	 * @channel_indices = 1
	 * @default_slice = [".", "difference", ".", "."]
	 * image_id = [1, ..., nP]
	 * channel = ["threshold_1", "threshold_2", "difference"]
	 * data = uint[nP, nC, i, j]
	 * Here, a data array with four dimensions, including the number of images
	 * (nP) and number of channels (nC), specifies more dimensions than can be
	 * visualized with a 2D image viewer for a given image. Therefore the
	 * default_slice attribute specifies that the "difference" channel should be
	 * shown by default.
	 * Alternate version using an integer would look like this (note 2 is a string)::
	 * data:NXdata
	 * @signal = "data"
	 * @axes = ["image_id", "channel", ".", "."]
	 * @image_id_indices = 0
	 * @channel_indices = 1
	 * @default_slice = [".", "2", ".", "."]
	 * image_id = [1, ..., nP]
	 * channel = ["threshold_1", "threshold_2", "difference"]
	 * data = uint[nP, nC, i, j]
	 *
	 * @param default_sliceValue the default_sliceValue
	 */
	public void setAttributeDefault_slice(String default_sliceValue);

	/**
	 * The ``AXISNAME_indices`` attribute is a single integer or an array of integers that defines which :ref:`data </NXdata/DATA-field>`
	 * dimension(s) are spanned by the corresponding axis. The first dimension index is ``0`` (zero).
	 * When the ``AXISNAME_indices`` attribute is missing for an :ref:`AXISNAME </NXdata/AXISNAME-field>` field, its value becomes the index
	 * (or indices) of the :ref:`AXISNAME </NXdata/AXISNAME-field>` name in the :ref:`axes </NXdata@axes-attribute>` attribute.
	 * .. note:: When ``AXISNAME_indices`` contains multiple integers, it must be saved as an actual array
	 * of integers and not a comma separated string.
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Long getAttributeIndices(String axisname);

	/**
	 * The ``AXISNAME_indices`` attribute is a single integer or an array of integers that defines which :ref:`data </NXdata/DATA-field>`
	 * dimension(s) are spanned by the corresponding axis. The first dimension index is ``0`` (zero).
	 * When the ``AXISNAME_indices`` attribute is missing for an :ref:`AXISNAME </NXdata/AXISNAME-field>` field, its value becomes the index
	 * (or indices) of the :ref:`AXISNAME </NXdata/AXISNAME-field>` name in the :ref:`axes </NXdata@axes-attribute>` attribute.
	 * .. note:: When ``AXISNAME_indices`` contains multiple integers, it must be saved as an actual array
	 * of integers and not a comma separated string.
	 *
	 * @param axisname the axisname
	 * @param indicesValue the indicesValue
	 */
	public void setAttributeIndices(String axisname, Long indicesValue);

	/**
	 * .. index:: plotting
	 * The ``axes`` attribute is a list of strings which are the names of the :ref:`AXISNAME </NXdata/AXISNAME-field>` fields
	 * that contain the values of the coordinates along the :ref:`data </NXdata/DATA-field>` dimensions.
	 * .. note:: When ``axes`` contains multiple strings, it must be saved as an actual array
	 * of strings and not a single comma separated string.
	 *
	 * @return  the value.
	 */
	public String getAttributeAxes();

	/**
	 * .. index:: plotting
	 * The ``axes`` attribute is a list of strings which are the names of the :ref:`AXISNAME </NXdata/AXISNAME-field>` fields
	 * that contain the values of the coordinates along the :ref:`data </NXdata/DATA-field>` dimensions.
	 * .. note:: When ``axes`` contains multiple strings, it must be saved as an actual array
	 * of strings and not a single comma separated string.
	 *
	 * @param axesValue the axesValue
	 */
	public void setAttributeAxes(String axesValue);

	/**
	 * Coordinate values along one or more :ref:`data </NXdata/DATA-field>` dimensions. The rank must be equal
	 * to the number of dimensions it spans.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute can be used to find all datasets in the
	 * ``NXdata`` that contain coordinate values.
	 * Most AXISNAME fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
	 * an array of NX_CHAR can be provided.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * </p>
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public IDataset getAxisname(String axisname);

	/**
	 * Coordinate values along one or more :ref:`data </NXdata/DATA-field>` dimensions. The rank must be equal
	 * to the number of dimensions it spans.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute can be used to find all datasets in the
	 * ``NXdata`` that contain coordinate values.
	 * Most AXISNAME fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
	 * an array of NX_CHAR can be provided.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * </p>
	 *
	 * @param axisname the axisname
	 * @param axisnameDataset the axisnameDataset
	 */
	public DataNode setAxisname(String axisname, IDataset axisnameDataset);

	/**
	 * Coordinate values along one or more :ref:`data </NXdata/DATA-field>` dimensions. The rank must be equal
	 * to the number of dimensions it spans.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute can be used to find all datasets in the
	 * ``NXdata`` that contain coordinate values.
	 * Most AXISNAME fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
	 * an array of NX_CHAR can be provided.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * </p>
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Object getAxisnameScalar(String axisname);

	/**
	 * Coordinate values along one or more :ref:`data </NXdata/DATA-field>` dimensions. The rank must be equal
	 * to the number of dimensions it spans.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute can be used to find all datasets in the
	 * ``NXdata`` that contain coordinate values.
	 * Most AXISNAME fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
	 * an array of NX_CHAR can be provided.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * </p>
	 *
	 * @param axisname the axisname
	 * @param axisname the axisname
	 */
	public DataNode setAxisnameScalar(String axisname, Object axisnameValue);


	/**
	 * Get all Axisname fields:
	 *
	 * Coordinate values along one or more :ref:`data </NXdata/DATA-field>` dimensions. The rank must be equal
	 * to the number of dimensions it spans.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute can be used to find all datasets in the
	 * ``NXdata`` that contain coordinate values.
	 * Most AXISNAME fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
	 * an array of NX_CHAR can be provided.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 *
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllAxisname();

	/**
	 * Axis label
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public String getAxisnameAttributeLong_name(String axisname);

	/**
	 * Axis label
	 *
	 * @param axisname the axisname
	 * @param long_nameValue the long_nameValue
	 */
	public void setAxisnameAttributeLong_name(String axisname, String long_nameValue);

	/**
	 * Unit in which the coordinate values are expressed.
	 * See the section :ref:`Design-Units` for more information.
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public String getAxisnameAttributeUnits(String axisname);

	/**
	 * Unit in which the coordinate values are expressed.
	 * See the section :ref:`Design-Units` for more information.
	 *
	 * @param axisname the axisname
	 * @param unitsValue the unitsValue
	 */
	public void setAxisnameAttributeUnits(String axisname, String unitsValue);

	/**
	 * ``0|false``: single value,
	 * ``1|true``: multiple values
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Boolean getAxisnameAttributeDistribution(String axisname);

	/**
	 * ``0|false``: single value,
	 * ``1|true``: multiple values
	 *
	 * @param axisname the axisname
	 * @param distributionValue the distributionValue
	 */
	public void setAxisnameAttributeDistribution(String axisname, Boolean distributionValue);

	/**
	 * Index of first good value
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Long getAxisnameAttributeFirst_good(String axisname);

	/**
	 * Index of first good value
	 *
	 * @param axisname the axisname
	 * @param first_goodValue the first_goodValue
	 */
	public void setAxisnameAttributeFirst_good(String axisname, Long first_goodValue);

	/**
	 * Index of last good value
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Long getAxisnameAttributeLast_good(String axisname);

	/**
	 * Index of last good value
	 *
	 * @param axisname the axisname
	 * @param last_goodValue the last_goodValue
	 */
	public void setAxisnameAttributeLast_good(String axisname, Long last_goodValue);

	/**
	 * Index (positive integer) identifying this specific set of numbers.
	 * N.B. The ``axis`` attribute is the old way of designating a link.
	 * Do not use the :ref:`axes </NXdata@axes-attribute>` attribute with the ``axis`` attribute.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute is now preferred.
	 *
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param axisname the axisname
	 * @return  the value.
	 */
	@Deprecated
	public Long getAxisnameAttributeAxis(String axisname);

	/**
	 * Index (positive integer) identifying this specific set of numbers.
	 * N.B. The ``axis`` attribute is the old way of designating a link.
	 * Do not use the :ref:`axes </NXdata@axes-attribute>` attribute with the ``axis`` attribute.
	 * The :ref:`axes </NXdata@axes-attribute>` attribute is now preferred.
	 *
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param axisname the axisname
	 * @param axisValue the axisValue
	 */
	@Deprecated
	public void setAxisnameAttributeAxis(String axisname, Long axisValue);

	/**
	 * .. index:: plotting
	 * Data values to be used as the NeXus *plottable data*. As the upper case ``DATA``
	 * indicates, the names of the ``DATA`` fields can be chosen :ref:`freely <validItemName>`. The :ref:`signal attribute </NXdata@signal-attribute>`
	 * and :ref:`auxiliary_signals attribute</NXdata@auxiliary_signals-attribute>` can be used to find all datasets in the ``NXdata``
	 * that contain data values.
	 * The maximum rank is ``32`` for compatibility with backend file formats.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param data the data
	 * @return  the value.
	 */
	public IDataset getData(String data);

	/**
	 * .. index:: plotting
	 * Data values to be used as the NeXus *plottable data*. As the upper case ``DATA``
	 * indicates, the names of the ``DATA`` fields can be chosen :ref:`freely <validItemName>`. The :ref:`signal attribute </NXdata@signal-attribute>`
	 * and :ref:`auxiliary_signals attribute</NXdata@auxiliary_signals-attribute>` can be used to find all datasets in the ``NXdata``
	 * that contain data values.
	 * The maximum rank is ``32`` for compatibility with backend file formats.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param data the data
	 * @param dataDataset the dataDataset
	 */
	public DataNode setData(String data, IDataset dataDataset);

	/**
	 * .. index:: plotting
	 * Data values to be used as the NeXus *plottable data*. As the upper case ``DATA``
	 * indicates, the names of the ``DATA`` fields can be chosen :ref:`freely <validItemName>`. The :ref:`signal attribute </NXdata@signal-attribute>`
	 * and :ref:`auxiliary_signals attribute</NXdata@auxiliary_signals-attribute>` can be used to find all datasets in the ``NXdata``
	 * that contain data values.
	 * The maximum rank is ``32`` for compatibility with backend file formats.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param data the data
	 * @return  the value.
	 */
	public Number getDataScalar(String data);

	/**
	 * .. index:: plotting
	 * Data values to be used as the NeXus *plottable data*. As the upper case ``DATA``
	 * indicates, the names of the ``DATA`` fields can be chosen :ref:`freely <validItemName>`. The :ref:`signal attribute </NXdata@signal-attribute>`
	 * and :ref:`auxiliary_signals attribute</NXdata@auxiliary_signals-attribute>` can be used to find all datasets in the ``NXdata``
	 * that contain data values.
	 * The maximum rank is ``32`` for compatibility with backend file formats.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param data the data
	 * @param data the data
	 */
	public DataNode setDataScalar(String data, Number dataValue);


	/**
	 * Get all Data fields:
	 *
	 * .. index:: plotting
	 * Data values to be used as the NeXus *plottable data*. As the upper case ``DATA``
	 * indicates, the names of the ``DATA`` fields can be chosen :ref:`freely <validItemName>`. The :ref:`signal attribute </NXdata@signal-attribute>`
	 * and :ref:`auxiliary_signals attribute</NXdata@auxiliary_signals-attribute>` can be used to find all datasets in the ``NXdata``
	 * that contain data values.
	 * The maximum rank is ``32`` for compatibility with backend file formats.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 *
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllData();

	/**
	 * .. index:: plotting
	 * Plottable (independent) axis, indicate index number.
	 * Only one field in a :ref:`NXdata` group may have the
	 * ``signal=1`` attribute.
	 * Do not use the ``signal`` attribute with the ``axis`` attribute.
	 *
	 * @deprecated Use the group ``signal`` attribute   (NIAC2014)
	 * @param data the data
	 * @return  the value.
	 */
	@Deprecated
	public Long getDataAttributeSignal(String data);

	/**
	 * .. index:: plotting
	 * Plottable (independent) axis, indicate index number.
	 * Only one field in a :ref:`NXdata` group may have the
	 * ``signal=1`` attribute.
	 * Do not use the ``signal`` attribute with the ``axis`` attribute.
	 *
	 * @deprecated Use the group ``signal`` attribute   (NIAC2014)
	 * @param data the data
	 * @param signalValue the signalValue
	 */
	@Deprecated
	public void setDataAttributeSignal(String data, Long signalValue);

	/**
	 * Defines the names of the coordinates
	 * (independent axes) for this data set
	 * as a colon-delimited array.
	 * NOTE: The :ref:`axes </NXdata@axes-attribute>` attribute is the preferred
	 * method of designating a link.
	 * Do not use the :ref:`axes </NXdata@axes-attribute>` attribute with the ``axis`` attribute.
	 *
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param data the data
	 * @return  the value.
	 */
	@Deprecated
	public String getDataAttributeAxes(String data);

	/**
	 * Defines the names of the coordinates
	 * (independent axes) for this data set
	 * as a colon-delimited array.
	 * NOTE: The :ref:`axes </NXdata@axes-attribute>` attribute is the preferred
	 * method of designating a link.
	 * Do not use the :ref:`axes </NXdata@axes-attribute>` attribute with the ``axis`` attribute.
	 *
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param data the data
	 * @param axesValue the axesValue
	 */
	@Deprecated
	public void setDataAttributeAxes(String data, String axesValue);

	/**
	 * data label
	 *
	 * @param data the data
	 * @return  the value.
	 */
	public String getDataAttributeLong_name(String data);

	/**
	 * data label
	 *
	 * @param data the data
	 * @param long_nameValue the long_nameValue
	 */
	public void setDataAttributeLong_name(String data, String long_nameValue);

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group (e.g. an axis, signal or auxiliary signal).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname the fieldname
	 * @return  the value.
	 */
	public IDataset getErrors(String fieldname);

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group (e.g. an axis, signal or auxiliary signal).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname the fieldname
	 * @param errorsDataset the errorsDataset
	 */
	public DataNode setErrors(String fieldname, IDataset errorsDataset);

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group (e.g. an axis, signal or auxiliary signal).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname the fieldname
	 * @return  the value.
	 */
	public Number getErrorsScalar(String fieldname);

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group (e.g. an axis, signal or auxiliary signal).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname the fieldname
	 * @param errors the errors
	 */
	public DataNode setErrorsScalar(String fieldname, Number errorsValue);


	/**
	 * Get all Errors fields:
	 *
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group (e.g. an axis, signal or auxiliary signal).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 *
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllErrors();

	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``DATA``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @deprecated Use ``DATA_errors`` instead (NIAC2018)
	 * @return  the value.
	 */
	@Deprecated
	public IDataset getErrors();

	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``DATA``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @deprecated Use ``DATA_errors`` instead (NIAC2018)
	 * @param errorsDataset the errorsDataset
	 */
	@Deprecated
	public DataNode setErrors(IDataset errorsDataset);

	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``DATA``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @deprecated Use ``DATA_errors`` instead (NIAC2018)
	 * @return  the value.
	 */
	@Deprecated
	public Number getErrorsScalar();

	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``DATA``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @deprecated Use ``DATA_errors`` instead (NIAC2018)
	 * @param errors the errors
	 */
	@Deprecated
	public DataNode setErrorsScalar(Number errorsValue);

	/**
	 * The elements in data are usually float values really. For
	 * efficiency reasons these are usually stored as integers
	 * after scaling with a scale factor. This value is the scale
	 * factor. It is required to get the actual physical value,
	 * when necessary.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getScaling_factor();

	/**
	 * The elements in data are usually float values really. For
	 * efficiency reasons these are usually stored as integers
	 * after scaling with a scale factor. This value is the scale
	 * factor. It is required to get the actual physical value,
	 * when necessary.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param scaling_factorDataset the scaling_factorDataset
	 */
	public DataNode setScaling_factor(IDataset scaling_factorDataset);

	/**
	 * The elements in data are usually float values really. For
	 * efficiency reasons these are usually stored as integers
	 * after scaling with a scale factor. This value is the scale
	 * factor. It is required to get the actual physical value,
	 * when necessary.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getScaling_factorScalar();

	/**
	 * The elements in data are usually float values really. For
	 * efficiency reasons these are usually stored as integers
	 * after scaling with a scale factor. This value is the scale
	 * factor. It is required to get the actual physical value,
	 * when necessary.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param scaling_factor the scaling_factor
	 */
	public DataNode setScaling_factorScalar(Double scaling_factorValue);

	/**
	 * An optional offset to apply to the values in data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOffset();

	/**
	 * An optional offset to apply to the values in data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param offsetDataset the offsetDataset
	 */
	public DataNode setOffset(IDataset offsetDataset);

	/**
	 * An optional offset to apply to the values in data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOffsetScalar();

	/**
	 * An optional offset to apply to the values in data.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param offset the offset
	 */
	public DataNode setOffsetScalar(Double offsetValue);

	/**
	 * Title for the plot.
	 *
	 * @return  the value.
	 */
	public IDataset getTitle();

	/**
	 * Title for the plot.
	 *
	 * @param titleDataset the titleDataset
	 */
	public DataNode setTitle(IDataset titleDataset);

	/**
	 * Title for the plot.
	 *
	 * @return  the value.
	 */
	public String getTitleScalar();

	/**
	 * Title for the plot.
	 *
	 * @param title the title
	 */
	public DataNode setTitleScalar(String titleValue);

	/**
	 * This is an array holding the values to use for the x-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nx;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getX();

	/**
	 * This is an array holding the values to use for the x-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nx;
	 * </p>
	 *
	 * @param xDataset the xDataset
	 */
	public DataNode setX(IDataset xDataset);

	/**
	 * This is an array holding the values to use for the x-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nx;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getXScalar();

	/**
	 * This is an array holding the values to use for the x-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nx;
	 * </p>
	 *
	 * @param x the x
	 */
	public DataNode setXScalar(Double xValue);

	/**
	 * This is an array holding the values to use for the y-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ny;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getY();

	/**
	 * This is an array holding the values to use for the y-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ny;
	 * </p>
	 *
	 * @param yDataset the yDataset
	 */
	public DataNode setY(IDataset yDataset);

	/**
	 * This is an array holding the values to use for the y-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ny;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getYScalar();

	/**
	 * This is an array holding the values to use for the y-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ny;
	 * </p>
	 *
	 * @param y the y
	 */
	public DataNode setYScalar(Double yValue);

	/**
	 * This is an array holding the values to use for the z-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nz;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getZ();

	/**
	 * This is an array holding the values to use for the z-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nz;
	 * </p>
	 *
	 * @param zDataset the zDataset
	 */
	public DataNode setZ(IDataset zDataset);

	/**
	 * This is an array holding the values to use for the z-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nz;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getZScalar();

	/**
	 * This is an array holding the values to use for the z-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatiblity.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nz;
	 * </p>
	 *
	 * @param z the z
	 */
	public DataNode setZScalar(Double zValue);

}
