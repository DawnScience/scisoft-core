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

/**
 * Logical grouping of detectors. When used, describes a group of detectors.
 * Each detector is represented as an NXdetector
 * with its own detector data array. Each detector data array
 * may be further decomposed into array sections by use of
 * NXdetector_module groups. Detectors can be grouped logically
 * together using NXdetector_group. Groups can be further grouped
 * hierarchically in a single NXdetector_group (for example, if
 * there are multiple detectors at an endstation or multiple
 * endstations at a facility). Alternatively, multiple
 * NXdetector_groups can be provided.
 * The groups are defined hierarchically, with names given
 * in the group_names field, unique identifying indices given
 * in the field group_index, and the level in the hierarchy
 * given in the group_parent field. For example if an x-ray
 * detector group, DET, consists of four detectors in a
 * rectangular array::
 * DTL DTR
 * DLL DLR
 * We could have::
 * group_names: ["DET", "DTL", "DTR", "DLL", "DLR"]
 * group_index: [1, 2, 3, 4, 5]
 * group_parent: [-1, 1, 1, 1, 1]
 * 
 */
public interface NXdetector_group extends NXobject {

	public static final String NX_GROUP_NAMES = "group_names";
	public static final String NX_GROUP_INDEX = "group_index";
	public static final String NX_GROUP_PARENT = "group_parent";
	public static final String NX_GROUP_TYPE = "group_type";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * An array of the names of the detectors given in NXdetector
	 * groups or the names of hierarchical groupings of detectors
	 * given as names of NXdetector_group groups or in
	 * NXdetector_group group_names and group_parent fields as
	 * having children.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGroup_names();
	
	/**
	 * An array of the names of the detectors given in NXdetector
	 * groups or the names of hierarchical groupings of detectors
	 * given as names of NXdetector_group groups or in
	 * NXdetector_group group_names and group_parent fields as
	 * having children.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param group_namesDataset the group_namesDataset
	 */
	public DataNode setGroup_names(IDataset group_namesDataset);

	/**
	 * An array of the names of the detectors given in NXdetector
	 * groups or the names of hierarchical groupings of detectors
	 * given as names of NXdetector_group groups or in
	 * NXdetector_group group_names and group_parent fields as
	 * having children.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getGroup_namesScalar();

	/**
	 * An array of the names of the detectors given in NXdetector
	 * groups or the names of hierarchical groupings of detectors
	 * given as names of NXdetector_group groups or in
	 * NXdetector_group group_names and group_parent fields as
	 * having children.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param group_names the group_names
	 */
	public DataNode setGroup_namesScalar(String group_namesValue);

	/**
	 * An array of unique identifiers for detectors or groupings
	 * of detectors.
	 * Each ID is a unique ID for the corresponding detector or group
	 * named in the field group_names. The IDs are positive integers
	 * starting with 1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGroup_index();
	
	/**
	 * An array of unique identifiers for detectors or groupings
	 * of detectors.
	 * Each ID is a unique ID for the corresponding detector or group
	 * named in the field group_names. The IDs are positive integers
	 * starting with 1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @param group_indexDataset the group_indexDataset
	 */
	public DataNode setGroup_index(IDataset group_indexDataset);

	/**
	 * An array of unique identifiers for detectors or groupings
	 * of detectors.
	 * Each ID is a unique ID for the corresponding detector or group
	 * named in the field group_names. The IDs are positive integers
	 * starting with 1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getGroup_indexScalar();

	/**
	 * An array of unique identifiers for detectors or groupings
	 * of detectors.
	 * Each ID is a unique ID for the corresponding detector or group
	 * named in the field group_names. The IDs are positive integers
	 * starting with 1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @param group_index the group_index
	 */
	public DataNode setGroup_indexScalar(Long group_indexValue);

	/**
	 * An array of the hierarchical levels of the parents of detectors
	 * or groupings of detectors.
	 * A top-level grouping has parent level -1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGroup_parent();
	
	/**
	 * An array of the hierarchical levels of the parents of detectors
	 * or groupings of detectors.
	 * A top-level grouping has parent level -1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @param group_parentDataset the group_parentDataset
	 */
	public DataNode setGroup_parent(IDataset group_parentDataset);

	/**
	 * An array of the hierarchical levels of the parents of detectors
	 * or groupings of detectors.
	 * A top-level grouping has parent level -1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getGroup_parentScalar();

	/**
	 * An array of the hierarchical levels of the parents of detectors
	 * or groupings of detectors.
	 * A top-level grouping has parent level -1.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @param group_parent the group_parent
	 */
	public DataNode setGroup_parentScalar(Long group_parentValue);

	/**
	 * Code number for group type, e.g. bank=1, tube=2 etc.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGroup_type();
	
	/**
	 * Code number for group type, e.g. bank=1, tube=2 etc.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @param group_typeDataset the group_typeDataset
	 */
	public DataNode setGroup_type(IDataset group_typeDataset);

	/**
	 * Code number for group type, e.g. bank=1, tube=2 etc.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getGroup_typeScalar();

	/**
	 * Code number for group type, e.g. bank=1, tube=2 etc.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 * 
	 * @param group_type the group_type
	 */
	public DataNode setGroup_typeScalar(Long group_typeValue);

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
