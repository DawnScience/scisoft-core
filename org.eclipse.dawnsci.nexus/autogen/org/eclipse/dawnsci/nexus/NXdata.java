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
	public static final String NX_FIELDNAME_ERRORS = "fieldname_errors";
	public static final String NX_ERRORS = "errors";
	public static final String NX_FIELDNAME_SCALING_FACTOR = "fieldname_scaling_factor";
	public static final String NX_FIELDNAME_OFFSET = "fieldname_offset";
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
	 * The ``AXISNAME_indices`` attribute is a single integer or an array of integers that defines which :ref:`DATA </NXdata/DATA-field>`
	 * dimensions are spanned by the corresponding axis. The first dimension index is ``0`` (zero).
	 * The number of indices must be equal to the rank of the :ref:`AXISNAME </NXdata/AXISNAME-field>` field.
	 * When the ``AXISNAME_indices`` attribute is missing for a given :ref:`AXISNAME </NXdata/AXISNAME-field>` field, its value becomes
	 * the index (or indices) of the :ref:`AXISNAME </NXdata/AXISNAME-field>` name in the :ref:`axes </NXdata@axes-attribute>` attribute.
	 * .. note:: When ``AXISNAME_indices`` contains multiple integers, it must be saved as an actual array
	 * of integers and not a comma separated string.
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Long getAttributeIndices(String axisname);

	/**
	 * The ``AXISNAME_indices`` attribute is a single integer or an array of integers that defines which :ref:`DATA </NXdata/DATA-field>`
	 * dimensions are spanned by the corresponding axis. The first dimension index is ``0`` (zero).
	 * The number of indices must be equal to the rank of the :ref:`AXISNAME </NXdata/AXISNAME-field>` field.
	 * When the ``AXISNAME_indices`` attribute is missing for a given :ref:`AXISNAME </NXdata/AXISNAME-field>` field, its value becomes
	 * the index (or indices) of the :ref:`AXISNAME </NXdata/AXISNAME-field>` name in the :ref:`axes </NXdata@axes-attribute>` attribute.
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
	 * to be used as the default axis along every :ref:`DATA </NXdata/DATA-field>` dimension. As a result the length must
	 * be equal to the rank of the :ref:`DATA </NXdata/DATA-field>` fields. The string "." can be used for
	 * dimensions without a default axis.
	 * .. note:: When ``axes`` contains multiple strings, it must be saved as an actual array
	 * of strings and not a single comma separated string.
	 * <p>
			 1: dataRank;
		
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAttributeAxes();

	/**
	 * .. index:: plotting
	 * The ``axes`` attribute is a list of strings which are the names of the :ref:`AXISNAME </NXdata/AXISNAME-field>` fields
	 * to be used as the default axis along every :ref:`DATA </NXdata/DATA-field>` dimension. As a result the length must
	 * be equal to the rank of the :ref:`DATA </NXdata/DATA-field>` fields. The string "." can be used for
	 * dimensions without a default axis.
	 * .. note:: When ``axes`` contains multiple strings, it must be saved as an actual array
	 * of strings and not a single comma separated string.
	 * <p>
			 1: dataRank;
		
	 * </p>
	 *
	 * @param axesValue the axesValue
	 */
	public void setAttributeAxes(String axesValue);

	/**
	 * Coordinate values along one or more :ref:`DATA </NXdata/DATA-field>` dimensions.
	 * The shape of an ``AXISNAME`` field must correspond to the shape of the :ref:`DATA </NXdata/DATA-field>`
	 * dimensions it spans. This means that for each ``i`` in ``[0, AXISNAME.ndim)`` the number of data points
	 * ``DATA.shape[AXISNAME_indices[i]]`` must be equal to the number of coordinates ``AXISNAME.shape[i]`` or the
	 * number of bin edges ``AXISNAME.shape[i]+1`` in case of histogram data.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * Most ``AXISNAME`` fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
	 * an array of NX_CHAR can be provided.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * </p>
	 *
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Dataset getAxisname(String axisname);

	/**
	 * Coordinate values along one or more :ref:`DATA </NXdata/DATA-field>` dimensions.
	 * The shape of an ``AXISNAME`` field must correspond to the shape of the :ref:`DATA </NXdata/DATA-field>`
	 * dimensions it spans. This means that for each ``i`` in ``[0, AXISNAME.ndim)`` the number of data points
	 * ``DATA.shape[AXISNAME_indices[i]]`` must be equal to the number of coordinates ``AXISNAME.shape[i]`` or the
	 * number of bin edges ``AXISNAME.shape[i]+1`` in case of histogram data.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * Most ``AXISNAME`` fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
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
	 * Coordinate values along one or more :ref:`DATA </NXdata/DATA-field>` dimensions.
	 * The shape of an ``AXISNAME`` field must correspond to the shape of the :ref:`DATA </NXdata/DATA-field>`
	 * dimensions it spans. This means that for each ``i`` in ``[0, AXISNAME.ndim)`` the number of data points
	 * ``DATA.shape[AXISNAME_indices[i]]`` must be equal to the number of coordinates ``AXISNAME.shape[i]`` or the
	 * number of bin edges ``AXISNAME.shape[i]+1`` in case of histogram data.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * Most ``AXISNAME`` fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
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
	 * Coordinate values along one or more :ref:`DATA </NXdata/DATA-field>` dimensions.
	 * The shape of an ``AXISNAME`` field must correspond to the shape of the :ref:`DATA </NXdata/DATA-field>`
	 * dimensions it spans. This means that for each ``i`` in ``[0, AXISNAME.ndim)`` the number of data points
	 * ``DATA.shape[AXISNAME_indices[i]]`` must be equal to the number of coordinates ``AXISNAME.shape[i]`` or the
	 * number of bin edges ``AXISNAME.shape[i]+1`` in case of histogram data.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * Most ``AXISNAME`` fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
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
	 * Coordinate values along one or more :ref:`DATA </NXdata/DATA-field>` dimensions.
	 * The shape of an ``AXISNAME`` field must correspond to the shape of the :ref:`DATA </NXdata/DATA-field>`
	 * dimensions it spans. This means that for each ``i`` in ``[0, AXISNAME.ndim)`` the number of data points
	 * ``DATA.shape[AXISNAME_indices[i]]`` must be equal to the number of coordinates ``AXISNAME.shape[i]`` or the
	 * number of bin edges ``AXISNAME.shape[i]+1`` in case of histogram data.
	 * As the upper case ``AXISNAME`` indicates, the names of the ``AXISNAME`` fields can be chosen :ref:`freely <validItemName>`.
	 * Most ``AXISNAME`` fields will be sequences of numbers but if an axis is better represented using names, such as channel names,
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
	public Dataset getData(String data);

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
	 * group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the corresponding ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFieldname_errors();

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the corresponding ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname_errorsDataset the fieldname_errorsDataset
	 */
	public DataNode setFieldname_errors(IDataset fieldname_errorsDataset);

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the corresponding ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFieldname_errorsScalar();

	/**
	 * "Errors" (meaning *uncertainties* or *standard deviations*)
	 * associated with any field named ``FIELDNAME`` in this ``NXdata``
	 * group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The dimensions of the ``FIELDNAME_errors`` field must match
	 * the dimensions of the corresponding ``FIELDNAME`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname_errors the fieldname_errors
	 */
	public DataNode setFieldname_errorsScalar(Number fieldname_errorsValue);

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
	public Dataset getErrors();

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
	 * An optional scaling factor to apply to the values in any field named ``FIELDNAME``
	 * in this ``NXdata`` group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The elements stored in NXdata datasets are often stored as integers for efficiency
	 * reasons and need further correction or conversion, generating floats. For example,
	 * raw values could be stored from a device that need to be converted to values that
	 * represent the physical values. The two fields FIELDNAME_scaling_factor and
	 * FIELDNAME_offset allow linear corrections using the following convention:
	 * .. code-block::
	 * corrected values = (FIELDNAME + offset) * scaling_factor
	 * This formula will derive the values to use in downstream applications, when necessary.
	 * When omitted, the scaling factor is assumed to be 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFieldname_scaling_factor();

	/**
	 * An optional scaling factor to apply to the values in any field named ``FIELDNAME``
	 * in this ``NXdata`` group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The elements stored in NXdata datasets are often stored as integers for efficiency
	 * reasons and need further correction or conversion, generating floats. For example,
	 * raw values could be stored from a device that need to be converted to values that
	 * represent the physical values. The two fields FIELDNAME_scaling_factor and
	 * FIELDNAME_offset allow linear corrections using the following convention:
	 * .. code-block::
	 * corrected values = (FIELDNAME + offset) * scaling_factor
	 * This formula will derive the values to use in downstream applications, when necessary.
	 * When omitted, the scaling factor is assumed to be 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname_scaling_factorDataset the fieldname_scaling_factorDataset
	 */
	public DataNode setFieldname_scaling_factor(IDataset fieldname_scaling_factorDataset);

	/**
	 * An optional scaling factor to apply to the values in any field named ``FIELDNAME``
	 * in this ``NXdata`` group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The elements stored in NXdata datasets are often stored as integers for efficiency
	 * reasons and need further correction or conversion, generating floats. For example,
	 * raw values could be stored from a device that need to be converted to values that
	 * represent the physical values. The two fields FIELDNAME_scaling_factor and
	 * FIELDNAME_offset allow linear corrections using the following convention:
	 * .. code-block::
	 * corrected values = (FIELDNAME + offset) * scaling_factor
	 * This formula will derive the values to use in downstream applications, when necessary.
	 * When omitted, the scaling factor is assumed to be 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFieldname_scaling_factorScalar();

	/**
	 * An optional scaling factor to apply to the values in any field named ``FIELDNAME``
	 * in this ``NXdata`` group. This can be a :ref:`DATA </NXdata/DATA-field>` field
	 * (signal or auxiliary signal) or a :ref:`AXISNAME </NXdata/AXISNAME-field>`
	 * field (axis).
	 * The elements stored in NXdata datasets are often stored as integers for efficiency
	 * reasons and need further correction or conversion, generating floats. For example,
	 * raw values could be stored from a device that need to be converted to values that
	 * represent the physical values. The two fields FIELDNAME_scaling_factor and
	 * FIELDNAME_offset allow linear corrections using the following convention:
	 * .. code-block::
	 * corrected values = (FIELDNAME + offset) * scaling_factor
	 * This formula will derive the values to use in downstream applications, when necessary.
	 * When omitted, the scaling factor is assumed to be 1.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname_scaling_factor the fieldname_scaling_factor
	 */
	public DataNode setFieldname_scaling_factorScalar(Number fieldname_scaling_factorValue);

	/**
	 * An optional offset to apply to the values in FIELDNAME (usually the signal).
	 * When omitted, the offset is assumed to be 0.
	 * See :ref:`FIELDNAME_scaling_factor </NXdata/FIELDNAME_scaling_factor-field>` for more information.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFieldname_offset();

	/**
	 * An optional offset to apply to the values in FIELDNAME (usually the signal).
	 * When omitted, the offset is assumed to be 0.
	 * See :ref:`FIELDNAME_scaling_factor </NXdata/FIELDNAME_scaling_factor-field>` for more information.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname_offsetDataset the fieldname_offsetDataset
	 */
	public DataNode setFieldname_offset(IDataset fieldname_offsetDataset);

	/**
	 * An optional offset to apply to the values in FIELDNAME (usually the signal).
	 * When omitted, the offset is assumed to be 0.
	 * See :ref:`FIELDNAME_scaling_factor </NXdata/FIELDNAME_scaling_factor-field>` for more information.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFieldname_offsetScalar();

	/**
	 * An optional offset to apply to the values in FIELDNAME (usually the signal).
	 * When omitted, the offset is assumed to be 0.
	 * See :ref:`FIELDNAME_scaling_factor </NXdata/FIELDNAME_scaling_factor-field>` for more information.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param fieldname_offset the fieldname_offset
	 */
	public DataNode setFieldname_offsetScalar(Number fieldname_offsetValue);

	/**
	 * The scaling_factor and FIELDNAME_scaling_factor fields have similar semantics.
	 * However, scaling_factor is ambiguous in the case of multiple signals. Therefore
	 * scaling_factor is deprecated. Use FIELDNAME_scaling_factor instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_scaling_factor instead
	 * @return  the value.
	 */
	@Deprecated
	public Dataset getScaling_factor();

	/**
	 * The scaling_factor and FIELDNAME_scaling_factor fields have similar semantics.
	 * However, scaling_factor is ambiguous in the case of multiple signals. Therefore
	 * scaling_factor is deprecated. Use FIELDNAME_scaling_factor instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_scaling_factor instead
	 * @param scaling_factorDataset the scaling_factorDataset
	 */
	@Deprecated
	public DataNode setScaling_factor(IDataset scaling_factorDataset);

	/**
	 * The scaling_factor and FIELDNAME_scaling_factor fields have similar semantics.
	 * However, scaling_factor is ambiguous in the case of multiple signals. Therefore
	 * scaling_factor is deprecated. Use FIELDNAME_scaling_factor instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_scaling_factor instead
	 * @return  the value.
	 */
	@Deprecated
	public Double getScaling_factorScalar();

	/**
	 * The scaling_factor and FIELDNAME_scaling_factor fields have similar semantics.
	 * However, scaling_factor is ambiguous in the case of multiple signals. Therefore
	 * scaling_factor is deprecated. Use FIELDNAME_scaling_factor instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_scaling_factor instead
	 * @param scaling_factor the scaling_factor
	 */
	@Deprecated
	public DataNode setScaling_factorScalar(Double scaling_factorValue);

	/**
	 * The offset and FIELDNAME_offset fields have similar semantics.
	 * However, offset is ambiguous in the case of multiple signals. Therefore
	 * offset is deprecated. Use FIELDNAME_offset instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_offset instead
	 * @return  the value.
	 */
	@Deprecated
	public Dataset getOffset();

	/**
	 * The offset and FIELDNAME_offset fields have similar semantics.
	 * However, offset is ambiguous in the case of multiple signals. Therefore
	 * offset is deprecated. Use FIELDNAME_offset instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_offset instead
	 * @param offsetDataset the offsetDataset
	 */
	@Deprecated
	public DataNode setOffset(IDataset offsetDataset);

	/**
	 * The offset and FIELDNAME_offset fields have similar semantics.
	 * However, offset is ambiguous in the case of multiple signals. Therefore
	 * offset is deprecated. Use FIELDNAME_offset instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_offset instead
	 * @return  the value.
	 */
	@Deprecated
	public Double getOffsetScalar();

	/**
	 * The offset and FIELDNAME_offset fields have similar semantics.
	 * However, offset is ambiguous in the case of multiple signals. Therefore
	 * offset is deprecated. Use FIELDNAME_offset instead, even when
	 * only a single signal is present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @deprecated Use FIELDNAME_offset instead
	 * @param offset the offset
	 */
	@Deprecated
	public DataNode setOffsetScalar(Double offsetValue);

	/**
	 * Title for the plot.
	 *
	 * @return  the value.
	 */
	public Dataset getTitle();

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
	 * kept for backward compatibility.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nx;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getX();

	/**
	 * This is an array holding the values to use for the x-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ny;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getY();

	/**
	 * This is an array holding the values to use for the y-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: nz;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getZ();

	/**
	 * This is an array holding the values to use for the z-axis of
	 * data. The units must be appropriate for the measurement.
	 * This is a special case of a :ref:`AXISNAME field </NXdata/AXISNAME-field>`
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
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
	 * kept for backward compatibility.
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
