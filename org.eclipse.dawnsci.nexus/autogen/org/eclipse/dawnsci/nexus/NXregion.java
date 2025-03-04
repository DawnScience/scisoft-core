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
 * Geometry and logical description of a region of data in a parent group. When used, it could be a child group to, say, :ref:`NXdetector`.
 * This can be used to describe a subset of data used to create downsampled data or to derive
 * some data from that subset.
 * Note, the fields for the rectangular region specifiers follow HDF5’s dataspace hyperslab parameters
 * (see https://portal.hdfgroup.org/display/HDF5/H5S_SELECT_HYPERSLAB). Note when **block** :math:`= 1`,
 * then **stride** :math:`\equiv` **step** in Python slicing.
 * For example, a ROI sum of an area starting at index of [20,50] and shape [220,120] in image data::
 * detector:NXdetector/
 * data[60,256,512]
 * region:NXregion/
 * @region_type = "rectangular"
 * parent = "data"
 * start = [20,50]
 * count = [220,120]
 * statistics:NXdata/
 * @signal = "sum"
 * sum[60]
 * the ``sum`` dataset contains the summed areas in each frame.
 * Another example, a hyperspectral image downsampled 16-fold in energy::
 * detector:NXdetector/
 * data[128,128,4096]
 * region:NXregion/
 * @region_type = "rectangular"
 * parent = "data"
 * start = [2]
 * count = [20]
 * stride = [32]
 * block = [16]
 * downsampled:NXdata/
 * @signal = "maximum"
 * @auxiliary_signals = "copy"
 * maximum[128,128,20]
 * copy[128,128,320]
 * the ``copy`` dataset selects 20 16-channel blocks that start 32 channels apart,
 * the ``maximum`` dataset will show maximum values in each 16-channel block
 * in every spectra.
 * <p><b>Symbols:</b>
 * These symbols will denote how the shape of the parent group's data field,
 * .. math:: (D_0, ..., D_{\mathbf{O}-1}, d_0, ..., d_{\mathbf{R}-1})
 * could be split into a left set of **O** outer dimensions, :math:`\boldsymbol{D}`,
 * and a right set of **R** region dimensions, :math:`\boldsymbol{d}`,
 * where the data field has rank **O** + **R**. Note **O** :math:`>= 0`.<ul>
 * <li><b>O</b>
 * Outer rank</li>
 * <li><b>R</b>
 * Region rank</li></ul></p>
 *
 */
public interface NXregion extends NXobject {

	public static final String NX_ATTRIBUTE_REGION_TYPE = "region_type";
	public static final String NX_PARENT = "parent";
	public static final String NX_PARENT_MASK = "parent_mask";
	public static final String NX_START = "start";
	public static final String NX_COUNT = "count";
	public static final String NX_STRIDE = "stride";
	public static final String NX_BLOCK = "block";
	public static final String NX_SCALE = "scale";
	/**
	 * This is ``rectangular`` to describe the region as a hyper-rectangle in
	 * the index space of its parent group's data field.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>rectangular</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAttributeRegion_type();

	/**
	 * This is ``rectangular`` to describe the region as a hyper-rectangle in
	 * the index space of its parent group's data field.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>rectangular</b> </li></ul></p>
	 * </p>
	 *
	 * @param region_typeValue the region_typeValue
	 */
	public void setAttributeRegion_type(String region_typeValue);

	/**
	 * The name of data field in the parent group or the path of a data field relative
	 * to the parent group (so it could be a field in a subgroup of the parent group)
	 *
	 * @return  the value.
	 */
	public Dataset getParent();

	/**
	 * The name of data field in the parent group or the path of a data field relative
	 * to the parent group (so it could be a field in a subgroup of the parent group)
	 *
	 * @param parentDataset the parentDataset
	 */
	public DataNode setParent(IDataset parentDataset);

	/**
	 * The name of data field in the parent group or the path of a data field relative
	 * to the parent group (so it could be a field in a subgroup of the parent group)
	 *
	 * @return  the value.
	 */
	public String getParentScalar();

	/**
	 * The name of data field in the parent group or the path of a data field relative
	 * to the parent group (so it could be a field in a subgroup of the parent group)
	 *
	 * @param parent the parent
	 */
	public DataNode setParentScalar(String parentValue);

	/**
	 * The name of an optional mask field in the parent group with rank :math:`\boldsymbol{R}` and
	 * dimensions :math:`\boldsymbol{d}`. For example, this could be ``pixel_mask`` of an
	 * :ref:`NXdetector`.
	 *
	 * @return  the value.
	 */
	public Dataset getParent_mask();

	/**
	 * The name of an optional mask field in the parent group with rank :math:`\boldsymbol{R}` and
	 * dimensions :math:`\boldsymbol{d}`. For example, this could be ``pixel_mask`` of an
	 * :ref:`NXdetector`.
	 *
	 * @param parent_maskDataset the parent_maskDataset
	 */
	public DataNode setParent_mask(IDataset parent_maskDataset);

	/**
	 * The name of an optional mask field in the parent group with rank :math:`\boldsymbol{R}` and
	 * dimensions :math:`\boldsymbol{d}`. For example, this could be ``pixel_mask`` of an
	 * :ref:`NXdetector`.
	 *
	 * @return  the value.
	 */
	public String getParent_maskScalar();

	/**
	 * The name of an optional mask field in the parent group with rank :math:`\boldsymbol{R}` and
	 * dimensions :math:`\boldsymbol{d}`. For example, this could be ``pixel_mask`` of an
	 * :ref:`NXdetector`.
	 *
	 * @param parent_mask the parent_mask
	 */
	public DataNode setParent_maskScalar(String parent_maskValue);

	/**
	 * The starting position for region in detector data field array.
	 * This is recommended as it also defines the region rank.
	 * If omitted then defined as an array of zeros.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart();

	/**
	 * The starting position for region in detector data field array.
	 * This is recommended as it also defines the region rank.
	 * If omitted then defined as an array of zeros.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param startDataset the startDataset
	 */
	public DataNode setStart(IDataset startDataset);

	/**
	 * The starting position for region in detector data field array.
	 * This is recommended as it also defines the region rank.
	 * If omitted then defined as an array of zeros.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getStartScalar();

	/**
	 * The starting position for region in detector data field array.
	 * This is recommended as it also defines the region rank.
	 * If omitted then defined as an array of zeros.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param start the start
	 */
	public DataNode setStartScalar(Number startValue);

	/**
	 * The number of blocks or items in the hyperslab selection.
	 * If omitted then defined as an array of dimensions that take into account
	 * the other hyperslab selection fields to span the parent data field's shape.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCount();

	/**
	 * The number of blocks or items in the hyperslab selection.
	 * If omitted then defined as an array of dimensions that take into account
	 * the other hyperslab selection fields to span the parent data field's shape.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param countDataset the countDataset
	 */
	public DataNode setCount(IDataset countDataset);

	/**
	 * The number of blocks or items in the hyperslab selection.
	 * If omitted then defined as an array of dimensions that take into account
	 * the other hyperslab selection fields to span the parent data field's shape.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCountScalar();

	/**
	 * The number of blocks or items in the hyperslab selection.
	 * If omitted then defined as an array of dimensions that take into account
	 * the other hyperslab selection fields to span the parent data field's shape.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param count the count
	 */
	public DataNode setCountScalar(Long countValue);

	/**
	 * An optional field to define striding used to downsample data.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStride();

	/**
	 * An optional field to define striding used to downsample data.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param strideDataset the strideDataset
	 */
	public DataNode setStride(IDataset strideDataset);

	/**
	 * An optional field to define striding used to downsample data.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getStrideScalar();

	/**
	 * An optional field to define striding used to downsample data.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param stride the stride
	 */
	public DataNode setStrideScalar(Long strideValue);

	/**
	 * An optional field to define the block size used to copy or downsample data. In the
	 * :math:`i`-th dimension, if :math:`\mathbf{block}[i] < \mathbf{stride}[i]`
	 * then the downsampling blocks have gaps between them; when ``block`` matches ``stride``
	 * then the blocks are contiguous; otherwise the blocks overlap.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBlock();

	/**
	 * An optional field to define the block size used to copy or downsample data. In the
	 * :math:`i`-th dimension, if :math:`\mathbf{block}[i] < \mathbf{stride}[i]`
	 * then the downsampling blocks have gaps between them; when ``block`` matches ``stride``
	 * then the blocks are contiguous; otherwise the blocks overlap.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param blockDataset the blockDataset
	 */
	public DataNode setBlock(IDataset blockDataset);

	/**
	 * An optional field to define the block size used to copy or downsample data. In the
	 * :math:`i`-th dimension, if :math:`\mathbf{block}[i] < \mathbf{stride}[i]`
	 * then the downsampling blocks have gaps between them; when ``block`` matches ``stride``
	 * then the blocks are contiguous; otherwise the blocks overlap.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getBlockScalar();

	/**
	 * An optional field to define the block size used to copy or downsample data. In the
	 * :math:`i`-th dimension, if :math:`\mathbf{block}[i] < \mathbf{stride}[i]`
	 * then the downsampling blocks have gaps between them; when ``block`` matches ``stride``
	 * then the blocks are contiguous; otherwise the blocks overlap.
	 * If omitted then defined as an array of ones.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param block the block
	 */
	public DataNode setBlockScalar(Long blockValue);

	/**
	 * An optional field to define a divisor for scaling of reduced data. For example, in a
	 * downsampled sum, it can reduce the maximum values to fit in the domain of the result
	 * data type. In an image that is downsampled by summing 2x2 blocks, using
	 * :math:`\mathrm{scale}=4` allows the result to fit in the same integer type dataset as
	 * the parent dataset.
	 * If omitted then no scaling occurs.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScale();

	/**
	 * An optional field to define a divisor for scaling of reduced data. For example, in a
	 * downsampled sum, it can reduce the maximum values to fit in the domain of the result
	 * data type. In an image that is downsampled by summing 2x2 blocks, using
	 * :math:`\mathrm{scale}=4` allows the result to fit in the same integer type dataset as
	 * the parent dataset.
	 * If omitted then no scaling occurs.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param scaleDataset the scaleDataset
	 */
	public DataNode setScale(IDataset scaleDataset);

	/**
	 * An optional field to define a divisor for scaling of reduced data. For example, in a
	 * downsampled sum, it can reduce the maximum values to fit in the domain of the result
	 * data type. In an image that is downsampled by summing 2x2 blocks, using
	 * :math:`\mathrm{scale}=4` allows the result to fit in the same integer type dataset as
	 * the parent dataset.
	 * If omitted then no scaling occurs.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getScaleScalar();

	/**
	 * An optional field to define a divisor for scaling of reduced data. For example, in a
	 * downsampled sum, it can reduce the maximum values to fit in the domain of the result
	 * data type. In an image that is downsampled by summing 2x2 blocks, using
	 * :math:`\mathrm{scale}=4` allows the result to fit in the same integer type dataset as
	 * the parent dataset.
	 * If omitted then no scaling occurs.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: R;
	 * </p>
	 *
	 * @param scale the scale
	 */
	public DataNode setScaleScalar(Number scaleValue);

	/**
	 * An optional group containing data copied/downsampled from parent group’s data. Its dataset name
	 * must reflect how the downsampling is done over each block. So it could be a reduction operation
	 * such as sum, minimum, maximum, mean, mode, median, etc. If downsampling is merely copying each
	 * block then use "copy" as the name. Where more than one downsample dataset is written
	 * (specified with ``@signal``) then add ``@auxiliary_signals`` listing the others. In the copy case,
	 * the field should have a shape of :math:`(D_0, ..., D_{\mathbf{O}-1}, \mathbf{block}[0] * \mathbf{count}[0], ..., \mathbf{block}[\mathbf{R}-1] * \mathbf{count}[\mathbf{R}-1])`,
	 * otherwise the expected shape is :math:`(D_0, ..., D_{\mathbf{O}-1}, \mathbf{count}[0], ..., \mathbf{count}[\mathbf{R}-1])`.
	 * The following figure shows how blocks are used in downsampling:
	 * .. figure:: region/NXregion-example.png
	 * :width: 60%
	 * A selection with :math:`\mathbf{start}=2, \mathbf{count}=4, \mathbf{stride}=3, \mathbf{block}=2` from
	 * a dataset with shape [13] will result in the ``reduce`` dataset of shape [4] and a ``copy`` dataset of shape [8].
	 *
	 * @return  the value.
	 */
	public NXdata getDownsampled();

	/**
	 * An optional group containing data copied/downsampled from parent group’s data. Its dataset name
	 * must reflect how the downsampling is done over each block. So it could be a reduction operation
	 * such as sum, minimum, maximum, mean, mode, median, etc. If downsampling is merely copying each
	 * block then use "copy" as the name. Where more than one downsample dataset is written
	 * (specified with ``@signal``) then add ``@auxiliary_signals`` listing the others. In the copy case,
	 * the field should have a shape of :math:`(D_0, ..., D_{\mathbf{O}-1}, \mathbf{block}[0] * \mathbf{count}[0], ..., \mathbf{block}[\mathbf{R}-1] * \mathbf{count}[\mathbf{R}-1])`,
	 * otherwise the expected shape is :math:`(D_0, ..., D_{\mathbf{O}-1}, \mathbf{count}[0], ..., \mathbf{count}[\mathbf{R}-1])`.
	 * The following figure shows how blocks are used in downsampling:
	 * .. figure:: region/NXregion-example.png
	 * :width: 60%
	 * A selection with :math:`\mathbf{start}=2, \mathbf{count}=4, \mathbf{stride}=3, \mathbf{block}=2` from
	 * a dataset with shape [13] will result in the ``reduce`` dataset of shape [4] and a ``copy`` dataset of shape [8].
	 *
	 * @param downsampledGroup the downsampledGroup
	 */
	public void setDownsampled(NXdata downsampledGroup);

	/**
	 * An optional group containing any statistics derived from the region in parent group’s data
	 * such as sum, minimum, maximum, mean, mode, median, rms, variance, etc. Where more than one
	 * statistical dataset is written (specified with ``@signal``) then add ``@auxiliary_signals``
	 * listing the others. All data fields should have shapes of :math:`\boldsymbol{D}`.
	 *
	 * @return  the value.
	 */
	public NXdata getStatistics();

	/**
	 * An optional group containing any statistics derived from the region in parent group’s data
	 * such as sum, minimum, maximum, mean, mode, median, rms, variance, etc. Where more than one
	 * statistical dataset is written (specified with ``@signal``) then add ``@auxiliary_signals``
	 * listing the others. All data fields should have shapes of :math:`\boldsymbol{D}`.
	 *
	 * @param statisticsGroup the statisticsGroup
	 */
	public void setStatistics(NXdata statisticsGroup);

}
