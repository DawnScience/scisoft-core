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
 * <p><b>Symbols:</b> 
 * These symbols will be used below to coordinate datasets with the same shape.<ul>
 * <li><b>dataRank</b> 
 * rank of the ``data`` field</li>
 * <li><b>n</b> 
 * length of the ``variable`` field</li>
 * <li><b>nx</b> 
 * length of the ``x`` field</li>
 * <li><b>ny</b> 
 * length of the ``y`` field</li>
 * <li><b>nz</b> 
 * length of the ``z`` field</li></ul></p>
 * 
 */
public interface NXdata extends NXobject {

	public static final String NX_ATTRIBUTE_AUXILIARY_SIGNALS = "auxiliary_signals";
	public static final String NX_ATTRIBUTE_SIGNAL = "signal";
	public static final String NX_ATTRIBUTE_AXES = "axes";
	public static final String NX_ATTRIBUTE_INDICES = "indices";
	public static final String NX_VARIABLE = "variable";
	public static final String NX_VARIABLE_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_VARIABLE_ATTRIBUTE_DISTRIBUTION = "distribution";
	public static final String NX_VARIABLE_ATTRIBUTE_FIRST_GOOD = "first_good";
	public static final String NX_VARIABLE_ATTRIBUTE_LAST_GOOD = "last_good";
	public static final String NX_VARIABLE_ATTRIBUTE_AXIS = "axis";
	public static final String NX_ERRORS_SUFFIX = "errors";
	public static final String NX_DATA = "data";
	public static final String NX_DATA_ATTRIBUTE_SIGNAL = "signal";
	public static final String NX_DATA_ATTRIBUTE_AXES = "axes";
	public static final String NX_DATA_ATTRIBUTE_LONG_NAME = "long_name";
	public static final String NX_ERRORS = "errors";
	public static final String NX_SCALING_FACTOR = "scaling_factor";
	public static final String NX_OFFSET = "offset";
	public static final String NX_TITLE = "title";
	public static final String NX_X = "x";
	public static final String NX_Y = "y";
	public static final String NX_Z = "z";
	/**
	 * .. index:: plotting
	 * Array of strings holding the names of additional signals to
	 * be plotted with the default signal (specified by the
	 * ``signal`` attribute). Each auxiliary signal needs to be of
	 * the same shape as the default signal.
	 * .. NIAC2018:
	 * https://www.nexusformat.org/NIAC2018Minutes.html
	 * 
	 * @return  the value.
	 */
	public String getAttributeAuxiliary_signals();
	
	/**
	 * .. index:: plotting
	 * Array of strings holding the names of additional signals to
	 * be plotted with the default signal (specified by the
	 * ``signal`` attribute). Each auxiliary signal needs to be of
	 * the same shape as the default signal.
	 * .. NIAC2018:
	 * https://www.nexusformat.org/NIAC2018Minutes.html
	 * 
	 * @param auxiliary_signalsValue the auxiliary_signalsValue
	 */
	public void setAttributeAuxiliary_signals(String auxiliary_signalsValue);

	/**
	 * .. index:: plotting
	 * Declares which dataset is the default.
	 * The value is the name of the dataset to be plotted.
	 * A field of this name *must* exist (either as dataset
	 * or as a link to a dataset).
	 * It is recommended (as of NIAC2014) to use this attribute
	 * rather than adding a signal attribute to the dataset.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeSignal();
	
	/**
	 * .. index:: plotting
	 * Declares which dataset is the default.
	 * The value is the name of the dataset to be plotted.
	 * A field of this name *must* exist (either as dataset
	 * or as a link to a dataset).
	 * It is recommended (as of NIAC2014) to use this attribute
	 * rather than adding a signal attribute to the dataset.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param signalValue the signalValue
	 */
	public void setAttributeSignal(String signalValue);

	/**
	 * .. index:: plotting
	 * String array that defines the independent data fields used in
	 * the default plot for all of the dimensions of the *signal* field
	 * (the *signal* field is the field in this group that is named by
	 * the ``signal`` attribute of this group).
	 * One entry is provided for every dimension in the *signal* field.
	 * The field(s) named as values (known as "axes") of this attribute
	 * *must* exist. An axis slice is specified using a field named
	 * ``AXISNAME_indices`` as described below (where the text shown here
	 * as ``AXISNAME`` is to be replaced by the actual field name).
	 * When no default axis is available for a particular dimension
	 * of the plottable data, use a "." in that position.
	 * Such as::
	 * @axes=["time", ".", "."]
	 * Since there are three items in the list, the the *signal* field
	 * must must be a three-dimensional array (rank=3). The first dimension
	 * is described by the values of a one-dimensional array named ``time``
	 * while the other two dimensions have no fields to be used as dimension scales.
	 * See examples provided on the NeXus wiki:
	 * https://www.nexusformat.org/2014_axes_and_uncertainties.html
	 * If there are no axes at all (such as with a stack of images),
	 * the axes attribute can be omitted.
	 * 
	 * @return  the value.
	 */
	public String getAttributeAxes();
	
	/**
	 * .. index:: plotting
	 * String array that defines the independent data fields used in
	 * the default plot for all of the dimensions of the *signal* field
	 * (the *signal* field is the field in this group that is named by
	 * the ``signal`` attribute of this group).
	 * One entry is provided for every dimension in the *signal* field.
	 * The field(s) named as values (known as "axes") of this attribute
	 * *must* exist. An axis slice is specified using a field named
	 * ``AXISNAME_indices`` as described below (where the text shown here
	 * as ``AXISNAME`` is to be replaced by the actual field name).
	 * When no default axis is available for a particular dimension
	 * of the plottable data, use a "." in that position.
	 * Such as::
	 * @axes=["time", ".", "."]
	 * Since there are three items in the list, the the *signal* field
	 * must must be a three-dimensional array (rank=3). The first dimension
	 * is described by the values of a one-dimensional array named ``time``
	 * while the other two dimensions have no fields to be used as dimension scales.
	 * See examples provided on the NeXus wiki:
	 * https://www.nexusformat.org/2014_axes_and_uncertainties.html
	 * If there are no axes at all (such as with a stack of images),
	 * the axes attribute can be omitted.
	 * 
	 * @param axesValue the axesValue
	 */
	public void setAttributeAxes(String axesValue);

	/**
	 * Each ``AXISNAME_indices`` attribute indicates the dependency
	 * relationship of the ``AXISNAME`` field (where ``AXISNAME``
	 * is the name of a field that exists in this ``NXdata`` group)
	 * with one or more dimensions of the plottable data.
	 * Integer array that defines the indices of the *signal* field
	 * (that field will be a multidimensional array)
	 * which need to be used in the *AXISNAME* dataset in
	 * order to reference the corresponding axis value.
	 * The first index of an array is ``0`` (zero).
	 * Here, *AXISNAME* is to be replaced by the name of each
	 * field described in the ``axes`` attribute.
	 * An example with 2-D data, :math:`d(t,P)`, will illustrate::
	 * data_2d:NXdata
	 * @signal="data"
	 * @axes=["time", "pressure"]
	 * @time_indices=0
	 * @pressure_indices=1
	 * data: float[1000,20]
	 * time: float[1000]
	 * pressure: float[20]
	 * This attribute is to be provided in all situations.
	 * However, if the indices attributes are missing
	 * (such as for data files written before this specification),
	 * file readers are encouraged to make their best efforts
	 * to plot the data.
	 * Thus the implementation of the
	 * ``AXISNAME_indices`` attribute is based on the model of
	 * "strict writer, liberal reader".
	 * .. note:: Attributes potentially containing multiple values
	 * (axes and _indices) are to be written as string or integer arrays,
	 * to avoid string parsing in reading applications.
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public String getAttributeIndices(String axisname);
	
	/**
	 * Each ``AXISNAME_indices`` attribute indicates the dependency
	 * relationship of the ``AXISNAME`` field (where ``AXISNAME``
	 * is the name of a field that exists in this ``NXdata`` group)
	 * with one or more dimensions of the plottable data.
	 * Integer array that defines the indices of the *signal* field
	 * (that field will be a multidimensional array)
	 * which need to be used in the *AXISNAME* dataset in
	 * order to reference the corresponding axis value.
	 * The first index of an array is ``0`` (zero).
	 * Here, *AXISNAME* is to be replaced by the name of each
	 * field described in the ``axes`` attribute.
	 * An example with 2-D data, :math:`d(t,P)`, will illustrate::
	 * data_2d:NXdata
	 * @signal="data"
	 * @axes=["time", "pressure"]
	 * @time_indices=0
	 * @pressure_indices=1
	 * data: float[1000,20]
	 * time: float[1000]
	 * pressure: float[20]
	 * This attribute is to be provided in all situations.
	 * However, if the indices attributes are missing
	 * (such as for data files written before this specification),
	 * file readers are encouraged to make their best efforts
	 * to plot the data.
	 * Thus the implementation of the
	 * ``AXISNAME_indices`` attribute is based on the model of
	 * "strict writer, liberal reader".
	 * .. note:: Attributes potentially containing multiple values
	 * (axes and _indices) are to be written as string or integer arrays,
	 * to avoid string parsing in reading applications.
	 * 
	 * @param axisname the axisname
	 * @param indicesValue the indicesValue
	 */
	public void setAttributeIndices(String axisname, String indicesValue);

	/**
	 * Dimension scale defining an axis of the data.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public IDataset getVariable(String variable);
	
	/**
	 * Dimension scale defining an axis of the data.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @param variableDataset the variableDataset
	 */
	public DataNode setVariable(String variable, IDataset variableDataset);

	/**
	 * Dimension scale defining an axis of the data.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public Number getVariableScalar(String variable);

	/**
	 * Dimension scale defining an axis of the data.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @param variable the variable
	 */
	public DataNode setVariableScalar(String variable, Number variableValue);

	
	/**
	 * Get all Variable fields:
	 *
	 * Dimension scale defining an axis of the data.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 * 
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllVariable();

	/**
	 * Axis label
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public String getVariableAttributeLong_name(String variable);
	
	/**
	 * Axis label
	 * 
	 * @param variable the variable
	 * @param long_nameValue the long_nameValue
	 */
	public void setVariableAttributeLong_name(String variable, String long_nameValue);

	/**
	 * ``0|false``: single value,
	 * ``1|true``: multiple values
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public Boolean getVariableAttributeDistribution(String variable);
	
	/**
	 * ``0|false``: single value,
	 * ``1|true``: multiple values
	 * 
	 * @param variable the variable
	 * @param distributionValue the distributionValue
	 */
	public void setVariableAttributeDistribution(String variable, Boolean distributionValue);

	/**
	 * Index of first good value
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public Long getVariableAttributeFirst_good(String variable);
	
	/**
	 * Index of first good value
	 * 
	 * @param variable the variable
	 * @param first_goodValue the first_goodValue
	 */
	public void setVariableAttributeFirst_good(String variable, Long first_goodValue);

	/**
	 * Index of last good value
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public Long getVariableAttributeLast_good(String variable);
	
	/**
	 * Index of last good value
	 * 
	 * @param variable the variable
	 * @param last_goodValue the last_goodValue
	 */
	public void setVariableAttributeLast_good(String variable, Long last_goodValue);

	/**
	 * Index (positive integer) identifying this specific set of numbers.
	 * N.B. The ``axis`` attribute is the old way of designating a link.
	 * Do not use the ``axes`` attribute with the ``axis`` attribute.
	 * The ``axes`` *group* attribute is now preferred.
	 * 
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param variable the variable
	 * @return  the value.
	 */
	@Deprecated
	public Long getVariableAttributeAxis(String variable);
	
	/**
	 * Index (positive integer) identifying this specific set of numbers.
	 * N.B. The ``axis`` attribute is the old way of designating a link.
	 * Do not use the ``axes`` attribute with the ``axis`` attribute.
	 * The ``axes`` *group* attribute is now preferred.
	 * 
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param variable the variable
	 * @param axisValue the axisValue
	 */
	@Deprecated
	public void setVariableAttributeAxis(String variable, Long axisValue);

	/**
	 * "Errors" (actually *uncertainties*) associated with axis ``VARIABLE``.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances
	 * but is matched with the *VARIABLE* field with ``_errors`` appended.
	 * This pattern of using ``VARIABLE_errors`` can be used
	 * throughout a NeXus data file to associate uncertainties
	 * with a dataset named ``VARIABLE``. This pattern also
	 * applies to other relationships such as ``VARIABLE_resolutions``
	 * to connect additional data with a certain dataset.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public IDataset getErrors(String variable);
	
	/**
	 * "Errors" (actually *uncertainties*) associated with axis ``VARIABLE``.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances
	 * but is matched with the *VARIABLE* field with ``_errors`` appended.
	 * This pattern of using ``VARIABLE_errors`` can be used
	 * throughout a NeXus data file to associate uncertainties
	 * with a dataset named ``VARIABLE``. This pattern also
	 * applies to other relationships such as ``VARIABLE_resolutions``
	 * to connect additional data with a certain dataset.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @param errorsDataset the errorsDataset
	 */
	public DataNode setErrors(String variable, IDataset errorsDataset);

	/**
	 * "Errors" (actually *uncertainties*) associated with axis ``VARIABLE``.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances
	 * but is matched with the *VARIABLE* field with ``_errors`` appended.
	 * This pattern of using ``VARIABLE_errors`` can be used
	 * throughout a NeXus data file to associate uncertainties
	 * with a dataset named ``VARIABLE``. This pattern also
	 * applies to other relationships such as ``VARIABLE_resolutions``
	 * to connect additional data with a certain dataset.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @return  the value.
	 */
	public Number getErrorsScalar(String variable);

	/**
	 * "Errors" (actually *uncertainties*) associated with axis ``VARIABLE``.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances
	 * but is matched with the *VARIABLE* field with ``_errors`` appended.
	 * This pattern of using ``VARIABLE_errors`` can be used
	 * throughout a NeXus data file to associate uncertainties
	 * with a dataset named ``VARIABLE``. This pattern also
	 * applies to other relationships such as ``VARIABLE_resolutions``
	 * to connect additional data with a certain dataset.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param variable the variable
	 * @param errors the errors
	 */
	public DataNode setErrorsScalar(String variable, Number errorsValue);

	
	/**
	 * Get all Errors fields:
	 *
	 * "Errors" (actually *uncertainties*) associated with axis ``VARIABLE``.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances
	 * but is matched with the *VARIABLE* field with ``_errors`` appended.
	 * This pattern of using ``VARIABLE_errors`` can be used
	 * throughout a NeXus data file to associate uncertainties
	 * with a dataset named ``VARIABLE``. This pattern also
	 * applies to other relationships such as ``VARIABLE_resolutions``
	 * to connect additional data with a certain dataset.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 * 
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllErrors();

	/**
	 * .. index:: plotting
	 * This field contains the data values to be used as the
	 * NeXus *plottable data*.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @param data the data
	 * @return  the value.
	 */
	public IDataset getData(String data);
	
	/**
	 * .. index:: plotting
	 * This field contains the data values to be used as the
	 * NeXus *plottable data*.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @param data the data
	 * @param dataDataset the dataDataset
	 */
	public DataNode setData(String data, IDataset dataDataset);

	/**
	 * .. index:: plotting
	 * This field contains the data values to be used as the
	 * NeXus *plottable data*.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @param data the data
	 * @return  the value.
	 */
	public Number getDataScalar(String data);

	/**
	 * .. index:: plotting
	 * This field contains the data values to be used as the
	 * NeXus *plottable data*.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
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
	 * This field contains the data values to be used as the
	 * NeXus *plottable data*.
	 * Client is responsible for defining the dimensions of the data.
	 * The name of this field may be changed to fit the circumstances.
	 * Standard NeXus client tools will use the attributes to determine
	 * how to use this field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
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
	 * Defines the names of the dimension scales
	 * (independent axes) for this data set
	 * as a colon-delimited array.
	 * NOTE: The ``axes`` attribute is the preferred
	 * method of designating a link.
	 * Do not use the ``axes`` attribute with the ``axis`` attribute.
	 * 
	 * @deprecated Use the group ``axes`` attribute   (NIAC2014)
	 * @param data the data
	 * @return  the value.
	 */
	@Deprecated
	public String getDataAttributeAxes(String data);
	
	/**
	 * Defines the names of the dimension scales
	 * (independent axes) for this data set
	 * as a colon-delimited array.
	 * NOTE: The ``axes`` attribute is the preferred
	 * method of designating a link.
	 * Do not use the ``axes`` attribute with the ``axis`` attribute.
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
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``data``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getErrors();
	
	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``data``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @param errorsDataset the errorsDataset
	 */
	public DataNode setErrors(IDataset errorsDataset);

	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``data``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getErrorsScalar();

	/**
	 * Standard deviations of data values -
	 * the data array is identified by the group attribute ``signal``.
	 * The ``errors`` array must have the same dimensions as ``data``.
	 * Client is responsible for defining the dimensions of the data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 0: n;
	 * </p>
	 * 
	 * @param errors the errors
	 */
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
