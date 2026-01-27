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

import java.util.Date;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A planned or unplanned action that has a temporal extension and for some time depends on some entity.
 * This class is a super class for all other activities.
 *
 */
public interface NXactivity extends NXobject {

	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_END_TIME_ATTRIBUTE_ESTIMATED = "estimated";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Start time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart_time();

	/**
	 * Start time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * Start time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * Start time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * End time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnd_time();

	/**
	 * End time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * End time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * End time of this activity. It is recommended to include local time zone information.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * In some cases, the end time of an activity can only be estimated. In this case,
	 * this attribute shall be True.
	 *
	 * @return  the value.
	 */
	public Boolean getEnd_timeAttributeEstimated();

	/**
	 * In some cases, the end time of an activity can only be estimated. In this case,
	 * this attribute shall be True.
	 *
	 * @param estimatedValue the estimatedValue
	 */
	public void setEnd_timeAttributeEstimated(Boolean estimatedValue);

	/**
	 * Short description of the activity.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Short description of the activity.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Short description of the activity.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Short description of the activity.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * This can be any data or other descriptor acquired during the activity
	 * (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * Any number of instances of :ref:`NXnote` are allowed for describing extra details of
	 * this activity.
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 * This can be any data or other descriptor acquired during the activity
	 * (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * Any number of instances of :ref:`NXnote` are allowed for describing extra details of
	 * this activity.
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * This can be any data or other descriptor acquired during the activity
	 * (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * Any number of instances of :ref:`NXnote` are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXnote for that node.
	 */
	public NXnote getNote(String name);

	/**
	 * Set a NXnote node by name:
	 * <ul>
	 * <li>
	 * This can be any data or other descriptor acquired during the activity
	 * (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * Any number of instances of :ref:`NXnote` are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param note the value to set
	 */
	public void setNote(String name, NXnote note);

	/**
	 * Get all NXnote nodes:
	 * <ul>
	 * <li>
	 * This can be any data or other descriptor acquired during the activity
	 * (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * Any number of instances of :ref:`NXnote` are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This can be any data or other descriptor acquired during the activity
	 * (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * Any number of instances of :ref:`NXnote` are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


}
