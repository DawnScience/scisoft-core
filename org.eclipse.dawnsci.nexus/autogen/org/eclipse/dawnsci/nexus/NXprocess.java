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

/**
 * Document an event of data processing, reconstruction, or analysis for this data.
 * 
 */
public interface NXprocess extends NXobject {

	public static final String NX_PROGRAM = "program";
	public static final String NX_SEQUENCE_INDEX = "sequence_index";
	public static final String NX_VERSION = "version";
	public static final String NX_DATE = "date";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Name of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getProgram();
	
	/**
	 * Name of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param programDataset the programDataset
	 */
	public DataNode setProgram(IDataset programDataset);

	/**
	 * Name of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getProgramScalar();

	/**
	 * Name of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param program the program
	 */
	public DataNode setProgramScalar(String programValue);

	/**
	 * Sequence index of processing,
	 * for determining the order of multiple **NXprocess** steps.
	 * Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSequence_index();
	
	/**
	 * Sequence index of processing,
	 * for determining the order of multiple **NXprocess** steps.
	 * Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @param sequence_indexDataset the sequence_indexDataset
	 */
	public DataNode setSequence_index(IDataset sequence_indexDataset);

	/**
	 * Sequence index of processing,
	 * for determining the order of multiple **NXprocess** steps.
	 * Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getSequence_indexScalar();

	/**
	 * Sequence index of processing,
	 * for determining the order of multiple **NXprocess** steps.
	 * Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @param sequence_index the sequence_index
	 */
	public DataNode setSequence_indexScalar(Long sequence_indexValue);

	/**
	 * Version of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getVersion();
	
	/**
	 * Version of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param versionDataset the versionDataset
	 */
	public DataNode setVersion(IDataset versionDataset);

	/**
	 * Version of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getVersionScalar();

	/**
	 * Version of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param version the version
	 */
	public DataNode setVersionScalar(String versionValue);

	/**
	 * Date and time of processing.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDate();
	
	/**
	 * Date and time of processing.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param dateDataset the dateDataset
	 */
	public DataNode setDate(IDataset dateDataset);

	/**
	 * Date and time of processing.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getDateScalar();

	/**
	 * Date and time of processing.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param date the date
	 */
	public DataNode setDateScalar(Date dateValue);

	/**
	 * The note will contain information about how the data was processed
	 * or anything about the data provenance.
	 * The contents of the note can be anything that the processing code
	 * can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.
	 * 
	 * @return  the value.
	 */
	public NXnote getNote();
	
	/**
	 * The note will contain information about how the data was processed
	 * or anything about the data provenance.
	 * The contents of the note can be anything that the processing code
	 * can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.
	 * 
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * The note will contain information about how the data was processed
	 * or anything about the data provenance.
	 * The contents of the note can be anything that the processing code
	 * can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.</li>
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
	 * The note will contain information about how the data was processed
	 * or anything about the data provenance.
	 * The contents of the note can be anything that the processing code
	 * can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.</li>
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
	 * The note will contain information about how the data was processed
	 * or anything about the data provenance.
	 * The contents of the note can be anything that the processing code
	 * can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * The note will contain information about how the data was processed
	 * or anything about the data provenance.
	 * The contents of the note can be anything that the processing code
	 * can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.</li>
	 * </ul>
	 * 
	 * @param note the child nodes to add 
	 */
	
	public void setAllNote(Map<String, NXnote> note);
	

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
