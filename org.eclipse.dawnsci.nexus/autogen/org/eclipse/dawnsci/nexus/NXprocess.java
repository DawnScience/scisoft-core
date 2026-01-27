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
 * The :ref:`NXprocess` class describes an operation used to
 * process data as part of an analysis workflow, providing
 * information such as the software used, the date of the
 * operation, the input parameters, and the resulting data.
 *
 */
public interface NXprocess extends NXobject {

	public static final String NX_PROGRAM = "program";
	public static final String NX_SEQUENCE_INDEX = "sequence_index";
	public static final String NX_VERSION = "version";
	public static final String NX_DATE = "date";
	/**
	 * Name of the program used
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getProgram();

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
	 * Sequence index of processing, for determining the order of
	 * multiple **NXprocess** steps. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSequence_index();

	/**
	 * Sequence index of processing, for determining the order of
	 * multiple **NXprocess** steps. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 *
	 * @param sequence_indexDataset the sequence_indexDataset
	 */
	public DataNode setSequence_index(IDataset sequence_indexDataset);

	/**
	 * Sequence index of processing, for determining the order of
	 * multiple **NXprocess** steps. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getSequence_indexScalar();

	/**
	 * Sequence index of processing, for determining the order of
	 * multiple **NXprocess** steps. Starts with 1.
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
	public Dataset getVersion();

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
	public Dataset getDate();

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
	 * The note will contain information about how the data was
	 * processed or anything about the data provenance. The
	 * contents of the note can be anything that the processing
	 * code can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 * The note will contain information about how the data was
	 * processed or anything about the data provenance. The
	 * contents of the note can be anything that the processing
	 * code can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * The note will contain information about how the data was
	 * processed or anything about the data provenance. The
	 * contents of the note can be anything that the processing
	 * code can understand, or simple text.
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
	 * The note will contain information about how the data was
	 * processed or anything about the data provenance. The
	 * contents of the note can be anything that the processing
	 * code can understand, or simple text.
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
	 * The note will contain information about how the data was
	 * processed or anything about the data provenance. The
	 * contents of the note can be anything that the processing
	 * code can understand, or simple text.
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
	 * The note will contain information about how the data was
	 * processed or anything about the data provenance. The
	 * contents of the note can be anything that the processing
	 * code can understand, or simple text.
	 * The name will be numbered to allow for ordering of steps.</li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


	/**
	 * Parameters used in performing the data analysis.
	 *
	 * @return  the value.
	 */
	public NXparameters getParameters();

	/**
	 * Parameters used in performing the data analysis.
	 *
	 * @param parametersGroup the parametersGroup
	 */
	public void setParameters(NXparameters parametersGroup);

	/**
	 * Get a NXparameters node by name:
	 * <ul>
	 * <li>
	 * Parameters used in performing the data analysis.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXparameters for that node.
	 */
	public NXparameters getParameters(String name);

	/**
	 * Set a NXparameters node by name:
	 * <ul>
	 * <li>
	 * Parameters used in performing the data analysis.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param parameters the value to set
	 */
	public void setParameters(String name, NXparameters parameters);

	/**
	 * Get all NXparameters nodes:
	 * <ul>
	 * <li>
	 * Parameters used in performing the data analysis.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXparameters for that node.
	 */
	public Map<String, NXparameters> getAllParameters();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Parameters used in performing the data analysis.</li>
	 * </ul>
	 *
	 * @param parameters the child nodes to add
	 */

	public void setAllParameters(Map<String, NXparameters> parameters);


	/**
	 * The data resulting from the operation.
	 *
	 * @return  the value.
	 */
	public NXdata getData();

	/**
	 * The data resulting from the operation.
	 *
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * The data resulting from the operation.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdata for that node.
	 */
	public NXdata getData(String name);

	/**
	 * Set a NXdata node by name:
	 * <ul>
	 * <li>
	 * The data resulting from the operation.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param data the value to set
	 */
	public void setData(String name, NXdata data);

	/**
	 * Get all NXdata nodes:
	 * <ul>
	 * <li>
	 * The data resulting from the operation.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * The data resulting from the operation.</li>
	 * </ul>
	 *
	 * @param data the child nodes to add
	 */

	public void setAllData(Map<String, NXdata> data);


}
