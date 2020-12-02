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
 * Collection of axis-based translations and rotations to describe a geometry.
 * May also contain axes that do not move and therefore do not have a transformation
 * type specified, but are useful in understanding coordinate frames within which
 * transformations are done, or in documenting important directions, such as the
 * direction of gravity.
 * A nested sequence of transformations lists the offset and rotation steps
 * needed to describe the position and orientation of any movable or fixed device.
 * There will be one or more transformations (axes) defined by one or more fields
 * for each transformation. The all-caps name ``AXISNAME`` designates the
 * particular axis generating a transformation (e.g. a rotation axis or a translation
 * axis or a general axis). The attribute ``units="NX_TRANSFORMATION"`` designates the
 * units will be appropriate to the ``transformation_type`` attribute:
 * * ``NX_LENGTH`` for ``translation``
 * * ``NX_ANGLE`` for ``rotation``
 * * ``NX_UNITLESS`` for axes for which no transformation type is specified
 * This class will usually contain all axes of a sample stage or goniometer or
 * a detector. The NeXus default McSTAS coordinate frame is assumed, but additional
 * useful coordinate axes may be defined by using axes for which no transformation
 * type has been specified.
 * The entry point (``depends_on``) will be outside of this class and point to a
 * field in here. Following the chain may also require following ``depends_on``
 * links to transformations outside, for example to a common base table. If
 * a relative path is given, it is relative to the group enclosing the ``depends_on``
 * specification.
 * For a chain of three transformations, where :math:`T_1` depends on :math:`T_2`
 * and that in turn depends on :math:`T_3`, the final transformation :math:`T_f` is
 * .. math:: T_f = T_3 T_2 T_1
 * In explicit terms, the transformations are a subset of affine transformations
 * expressed as 4x4 matrices that act on homogeneous coordinates, :math:`w=(x,y,z,1)^T`.
 * For rotation and translation,
 * .. math:: T_r &= \begin{pmatrix} R & o \\ 0_3 & 1 \end{pmatrix} \\ T_t &= \begin{pmatrix} I_3 & t + o \\ 0_3 & 1 \end{pmatrix}
 * where :math:`R` is the usual 3x3 rotation matrix, :math:`o` is an offset vector,
 * :math:`0_3` is a row of 3 zeros, :math:`I_3` is the 3x3 identity matrix and
 * :math:`t` is the translation vector.
 * :math:`o` is given the ``offset`` attribute, :math:`t` is given by the ``vector``
 * attribute multiplied by the field value, and :math:`R` is defined as a rotation
 * about an axis in the direction of ``vector``, of angle of the field value.
 * NOTE
 * One possible use of ``NXtransformations`` is to define the motors and
 * transformations for a diffractometer (goniometer). Such use is mentioned
 * in the ``NXinstrument`` base class. Use one ``NXtransformations`` group
 * for each diffractometer and name the group appropriate to the device.
 * Collecting the motors of a sample table or xyz-stage in an NXtransformation
 * group is equally possible.
 * 
 */
public interface NXtransformations extends NXobject {

	public static final String NX_AXISNAME = "axisname";
	public static final String NX_AXISNAME_ATTRIBUTE_TRANSFORMATION_TYPE = "transformation_type";
	public static final String NX_AXISNAME_ATTRIBUTE_VECTOR = "vector";
	public static final String NX_AXISNAME_ATTRIBUTE_OFFSET = "offset";
	public static final String NX_AXISNAME_ATTRIBUTE_OFFSET_UNITS = "offset_units";
	public static final String NX_AXISNAME_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_END_SUFFIX = "end";
	public static final String NX_INCREMENT_SET_SUFFIX = "increment_set";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Units need to be appropriate for translation or rotation
	 * The name of this field is not forced. The user is free to use any name
	 * that does not cause confusion. When using more than one ``AXISNAME`` field,
	 * make sure that each field name is unique in the same group, as required
	 * by HDF5.
	 * The values given should be the start points of exposures for the corresponding
	 * frames. The end points should be given in ``AXISNAME_end``.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public IDataset getAxisname(String axisname);
	
	/**
	 * Units need to be appropriate for translation or rotation
	 * The name of this field is not forced. The user is free to use any name
	 * that does not cause confusion. When using more than one ``AXISNAME`` field,
	 * make sure that each field name is unique in the same group, as required
	 * by HDF5.
	 * The values given should be the start points of exposures for the corresponding
	 * frames. The end points should be given in ``AXISNAME_end``.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param axisnameDataset the axisnameDataset
	 */
	public DataNode setAxisname(String axisname, IDataset axisnameDataset);

	/**
	 * Units need to be appropriate for translation or rotation
	 * The name of this field is not forced. The user is free to use any name
	 * that does not cause confusion. When using more than one ``AXISNAME`` field,
	 * make sure that each field name is unique in the same group, as required
	 * by HDF5.
	 * The values given should be the start points of exposures for the corresponding
	 * frames. The end points should be given in ``AXISNAME_end``.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Number getAxisnameScalar(String axisname);

	/**
	 * Units need to be appropriate for translation or rotation
	 * The name of this field is not forced. The user is free to use any name
	 * that does not cause confusion. When using more than one ``AXISNAME`` field,
	 * make sure that each field name is unique in the same group, as required
	 * by HDF5.
	 * The values given should be the start points of exposures for the corresponding
	 * frames. The end points should be given in ``AXISNAME_end``.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param axisname the axisname
	 */
	public DataNode setAxisnameScalar(String axisname, Number axisnameValue);

	
	/**
	 * Get all Axisname fields:
	 *
	 * Units need to be appropriate for translation or rotation
	 * The name of this field is not forced. The user is free to use any name
	 * that does not cause confusion. When using more than one ``AXISNAME`` field,
	 * make sure that each field name is unique in the same group, as required
	 * by HDF5.
	 * The values given should be the start points of exposures for the corresponding
	 * frames. The end points should be given in ``AXISNAME_end``.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 * 
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllAxisname();

	/**
	 * The transformation_type may be ``translation``, in which case the
	 * values are linear displacements along the axis, ``rotation``,
	 * in which case the values are angular rotations around the axis.
	 * If this attribute is omitted, this is an axis for which there
	 * is no motion to be specifies, such as the direction of gravity,
	 * or the direction to the source, or a basis vector of a
	 * coordinate frame.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li>
	 * <li><b>rotation</b> </li></ul></p>
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public String getAxisnameAttributeTransformation_type(String axisname);
	
	/**
	 * The transformation_type may be ``translation``, in which case the
	 * values are linear displacements along the axis, ``rotation``,
	 * in which case the values are angular rotations around the axis.
	 * If this attribute is omitted, this is an axis for which there
	 * is no motion to be specifies, such as the direction of gravity,
	 * or the direction to the source, or a basis vector of a
	 * coordinate frame.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>translation</b> </li>
	 * <li><b>rotation</b> </li></ul></p>
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param transformation_typeValue the transformation_typeValue
	 */
	public void setAxisnameAttributeTransformation_type(String axisname, String transformation_typeValue);

	/**
	 * Three values that define the axis for this transformation.
	 * The axis should be normalized to unit length, making it
	 * dimensionless. For ``rotation`` axes, the direction should be
	 * chosen for a right-handed rotation with increasing angle.
	 * For ``translation`` axes the direction should be chosen for
	 * increasing displacement.
	 * <p>
				 1: 3;
			
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Number getAxisnameAttributeVector(String axisname);
	
	/**
	 * Three values that define the axis for this transformation.
	 * The axis should be normalized to unit length, making it
	 * dimensionless. For ``rotation`` axes, the direction should be
	 * chosen for a right-handed rotation with increasing angle.
	 * For ``translation`` axes the direction should be chosen for
	 * increasing displacement.
	 * <p>
				 1: 3;
			
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param vectorValue the vectorValue
	 */
	public void setAxisnameAttributeVector(String axisname, Number vectorValue);

	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * <p>
				 1: 3;
			
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Number getAxisnameAttributeOffset(String axisname);
	
	/**
	 * A fixed offset applied before the transformation (three vector components).
	 * <p>
				 1: 3;
			
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param offsetValue the offsetValue
	 */
	public void setAxisnameAttributeOffset(String axisname, Number offsetValue);

	/**
	 * Units of the offset. Values should be consistent with NX_LENGTH.
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public String getAxisnameAttributeOffset_units(String axisname);
	
	/**
	 * Units of the offset. Values should be consistent with NX_LENGTH.
	 * 
	 * @param axisname the axisname
	 * @param offset_unitsValue the offset_unitsValue
	 */
	public void setAxisnameAttributeOffset_units(String axisname, String offset_unitsValue);

	/**
	 * Points to the path to a field defining the axis on which this
	 * depends or the string ".".
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public String getAxisnameAttributeDepends_on(String axisname);
	
	/**
	 * Points to the path to a field defining the axis on which this
	 * depends or the string ".".
	 * 
	 * @param axisname the axisname
	 * @param depends_onValue the depends_onValue
	 */
	public void setAxisnameAttributeDepends_on(String axisname, String depends_onValue);

	/**
	 * ``AXISNAME_end`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_end`` has been appended.
	 * The values in this field are the end points of the motions that start
	 * at the corresponding positions given in the ``AXISNAME`` field.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public IDataset getEnd(String axisname);
	
	/**
	 * ``AXISNAME_end`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_end`` has been appended.
	 * The values in this field are the end points of the motions that start
	 * at the corresponding positions given in the ``AXISNAME`` field.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param endDataset the endDataset
	 */
	public DataNode setEnd(String axisname, IDataset endDataset);

	/**
	 * ``AXISNAME_end`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_end`` has been appended.
	 * The values in this field are the end points of the motions that start
	 * at the corresponding positions given in the ``AXISNAME`` field.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Number getEndScalar(String axisname);

	/**
	 * ``AXISNAME_end`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_end`` has been appended.
	 * The values in this field are the end points of the motions that start
	 * at the corresponding positions given in the ``AXISNAME`` field.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param end the end
	 */
	public DataNode setEndScalar(String axisname, Number endValue);

	
	/**
	 * Get all End fields:
	 *
	 * ``AXISNAME_end`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_end`` has been appended.
	 * The values in this field are the end points of the motions that start
	 * at the corresponding positions given in the ``AXISNAME`` field.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 * 
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllEnd();

	/**
	 * ``AXISNAME_increment_set`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_increment_set`` has been appended.
	 * The value of this optional field is the intended average range through which
	 * the corresponding axis moves during the exposure of a frame. Ideally, the
	 * value of this field added to each value of ``AXISNAME`` would agree with the
	 * corresponding values of ``AXISNAME_end``, but there is a possibility of significant
	 * differences. Use of ``AXISNAME_end`` is recommended.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public IDataset getIncrement_set(String axisname);
	
	/**
	 * ``AXISNAME_increment_set`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_increment_set`` has been appended.
	 * The value of this optional field is the intended average range through which
	 * the corresponding axis moves during the exposure of a frame. Ideally, the
	 * value of this field added to each value of ``AXISNAME`` would agree with the
	 * corresponding values of ``AXISNAME_end``, but there is a possibility of significant
	 * differences. Use of ``AXISNAME_end`` is recommended.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param increment_setDataset the increment_setDataset
	 */
	public DataNode setIncrement_set(String axisname, IDataset increment_setDataset);

	/**
	 * ``AXISNAME_increment_set`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_increment_set`` has been appended.
	 * The value of this optional field is the intended average range through which
	 * the corresponding axis moves during the exposure of a frame. Ideally, the
	 * value of this field added to each value of ``AXISNAME`` would agree with the
	 * corresponding values of ``AXISNAME_end``, but there is a possibility of significant
	 * differences. Use of ``AXISNAME_end`` is recommended.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @return  the value.
	 */
	public Number getIncrement_setScalar(String axisname);

	/**
	 * ``AXISNAME_increment_set`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_increment_set`` has been appended.
	 * The value of this optional field is the intended average range through which
	 * the corresponding axis moves during the exposure of a frame. Ideally, the
	 * value of this field added to each value of ``AXISNAME`` would agree with the
	 * corresponding values of ``AXISNAME_end``, but there is a possibility of significant
	 * differences. Use of ``AXISNAME_end`` is recommended.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * 
	 * @param axisname the axisname
	 * @param increment_set the increment_set
	 */
	public DataNode setIncrement_setScalar(String axisname, Number increment_setValue);

	
	/**
	 * Get all Increment_set fields:
	 *
	 * ``AXISNAME_increment_set`` is a placeholder for a name constructed from the actual
	 * name of an axis to which ``_increment_set`` has been appended.
	 * The value of this optional field is the intended average range through which
	 * the corresponding axis moves during the exposure of a frame. Ideally, the
	 * value of this field added to each value of ``AXISNAME`` would agree with the
	 * corresponding values of ``AXISNAME_end``, but there is a possibility of significant
	 * differences. Use of ``AXISNAME_end`` is recommended.
	 * <p>
	 * <b>Units:</b> NX_TRANSFORMATION
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 * 
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllIncrement_set();

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}
