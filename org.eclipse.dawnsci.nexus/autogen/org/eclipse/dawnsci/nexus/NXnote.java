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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * Any additional freeform information not covered by the other base classes.
 * This class can be used to store additional information in a
 * NeXus file e.g. pictures, movies, audio, additional text logs
 * 
 */
public interface NXnote extends NXobject {

	public static final String NX_AUTHOR = "author";
	public static final String NX_DATE = "date";
	public static final String NX_TYPE = "type";
	public static final String NX_FILE_NAME = "file_name";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_SEQUENCE_INDEX = "sequence_index";
	public static final String NX_DATA = "data";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Author or creator of note
	 * 
	 * @return  the value.
	 */
	public IDataset getAuthor();
	
	/**
	 * Author or creator of note
	 * 
	 * @param authorDataset the authorDataset
	 */
	public DataNode setAuthor(IDataset authorDataset);

	/**
	 * Author or creator of note
	 * 
	 * @return  the value.
	 */
	public String getAuthorScalar();

	/**
	 * Author or creator of note
	 * 
	 * @param author the author
	 */
	public DataNode setAuthorScalar(String authorValue);

	/**
	 * Date note created/added
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDate();
	
	/**
	 * Date note created/added
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param dateDataset the dateDataset
	 */
	public DataNode setDate(IDataset dateDataset);

	/**
	 * Date note created/added
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getDateScalar();

	/**
	 * Date note created/added
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param date the date
	 */
	public DataNode setDateScalar(Date dateValue);

	/**
	 * Mime content type of note data field e.g. image/jpeg, text/plain, text/html
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * Mime content type of note data field e.g. image/jpeg, text/plain, text/html
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Mime content type of note data field e.g. image/jpeg, text/plain, text/html
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Mime content type of note data field e.g. image/jpeg, text/plain, text/html
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Name of original file name if note was read from an external source
	 * 
	 * @return  the value.
	 */
	public IDataset getFile_name();
	
	/**
	 * Name of original file name if note was read from an external source
	 * 
	 * @param file_nameDataset the file_nameDataset
	 */
	public DataNode setFile_name(IDataset file_nameDataset);

	/**
	 * Name of original file name if note was read from an external source
	 * 
	 * @return  the value.
	 */
	public String getFile_nameScalar();

	/**
	 * Name of original file name if note was read from an external source
	 * 
	 * @param file_name the file_name
	 */
	public DataNode setFile_nameScalar(String file_nameValue);

	/**
	 * Title of an image or other details of the note
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * Title of an image or other details of the note
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Title of an image or other details of the note
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Title of an image or other details of the note
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Sequence index of note, for placing a sequence of
	 * multiple **NXnote** groups in an order. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSequence_index();
	
	/**
	 * Sequence index of note, for placing a sequence of
	 * multiple **NXnote** groups in an order. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @param sequence_indexDataset the sequence_indexDataset
	 */
	public DataNode setSequence_index(IDataset sequence_indexDataset);

	/**
	 * Sequence index of note, for placing a sequence of
	 * multiple **NXnote** groups in an order. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getSequence_indexScalar();

	/**
	 * Sequence index of note, for placing a sequence of
	 * multiple **NXnote** groups in an order. Starts with 1.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * </p>
	 * 
	 * @param sequence_index the sequence_index
	 */
	public DataNode setSequence_indexScalar(Long sequence_indexValue);

	/**
	 * Binary note data - if text, line terminator is [CR][LF].
	 * <p>
	 * <b>Type:</b> NX_BINARY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getData();
	
	/**
	 * Binary note data - if text, line terminator is [CR][LF].
	 * <p>
	 * <b>Type:</b> NX_BINARY
	 * </p>
	 * 
	 * @param dataDataset the dataDataset
	 */
	public DataNode setData(IDataset dataDataset);

	/**
	 * Binary note data - if text, line terminator is [CR][LF].
	 * <p>
	 * <b>Type:</b> NX_BINARY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Object getDataScalar();

	/**
	 * Binary note data - if text, line terminator is [CR][LF].
	 * <p>
	 * <b>Type:</b> NX_BINARY
	 * </p>
	 * 
	 * @param data the data
	 */
	public DataNode setDataScalar(Object dataValue);

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
