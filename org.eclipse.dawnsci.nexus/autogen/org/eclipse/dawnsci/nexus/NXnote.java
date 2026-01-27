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
import org.eclipse.january.dataset.Dataset;
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
	public static final String NX_IDENTIFIERNAME = "identifiername";
	public static final String NX_CHECKSUM = "checksum";
	public static final String NX_ALGORITHM = "algorithm";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_SEQUENCE_INDEX = "sequence_index";
	public static final String NX_DATA = "data";
	/**
	 * Author or creator of note
	 *
	 * @return  the value.
	 */
	public Dataset getAuthor();

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
	public Dataset getDate();

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
	public Dataset getType();

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
	public Dataset getFile_name();

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
	 * Identifier of the resource if that resource that has been serialized.
	 * For example, the identifier to a resource in another database.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifiername();

	/**
	 * Identifier of the resource if that resource that has been serialized.
	 * For example, the identifier to a resource in another database.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param identifiernameDataset the identifiernameDataset
	 */
	public DataNode setIdentifiername(IDataset identifiernameDataset);

	/**
	 * Identifier of the resource if that resource that has been serialized.
	 * For example, the identifier to a resource in another database.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifiernameScalar();

	/**
	 * Identifier of the resource if that resource that has been serialized.
	 * For example, the identifier to a resource in another database.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param identifiername the identifiername
	 */
	public DataNode setIdentifiernameScalar(String identifiernameValue);

	/**
	 * Value of the hash that is obtained when running algorithm
	 * on the content of the resource referred to by ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getChecksum();

	/**
	 * Value of the hash that is obtained when running algorithm
	 * on the content of the resource referred to by ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param checksumDataset the checksumDataset
	 */
	public DataNode setChecksum(IDataset checksumDataset);

	/**
	 * Value of the hash that is obtained when running algorithm
	 * on the content of the resource referred to by ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getChecksumScalar();

	/**
	 * Value of the hash that is obtained when running algorithm
	 * on the content of the resource referred to by ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param checksum the checksum
	 */
	public DataNode setChecksumScalar(String checksumValue);

	/**
	 * Name of the algorithm whereby the ``checksum`` was computed.
	 * Examples: md5, sha256
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAlgorithm();

	/**
	 * Name of the algorithm whereby the ``checksum`` was computed.
	 * Examples: md5, sha256
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param algorithmDataset the algorithmDataset
	 */
	public DataNode setAlgorithm(IDataset algorithmDataset);

	/**
	 * Name of the algorithm whereby the ``checksum`` was computed.
	 * Examples: md5, sha256
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAlgorithmScalar();

	/**
	 * Name of the algorithm whereby the ``checksum`` was computed.
	 * Examples: md5, sha256
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param algorithm the algorithm
	 */
	public DataNode setAlgorithmScalar(String algorithmValue);

	/**
	 * Title of an image or other details of the note
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

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
	public Dataset getSequence_index();

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
	public Dataset getData();

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

}
