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
 * A literature reference
 * Definition to include references for example for detectors,
 * manuals, instruments, acquisition or analysis software used.
 * The idea would be to include this in the relevant NeXus object:
 * :ref:`NXdetector` for detectors, :ref:`NXinstrument` for instruments, etc.
 * 
 */
public interface NXcite extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_URL = "url";
	public static final String NX_DOI = "doi";
	public static final String NX_ENDNOTE = "endnote";
	public static final String NX_BIBTEX = "bibtex";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * This should describe the reason for including this reference.
	 * For example: The dataset in this group was normalised using the method
	 * which is described in detail in this reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * This should describe the reason for including this reference.
	 * For example: The dataset in this group was normalised using the method
	 * which is described in detail in this reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * This should describe the reason for including this reference.
	 * For example: The dataset in this group was normalised using the method
	 * which is described in detail in this reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * This should describe the reason for including this reference.
	 * For example: The dataset in this group was normalised using the method
	 * which is described in detail in this reference.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * URL referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getUrl();
	
	/**
	 * URL referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param urlDataset the urlDataset
	 */
	public DataNode setUrl(IDataset urlDataset);

	/**
	 * URL referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getUrlScalar();

	/**
	 * URL referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param url the url
	 */
	public DataNode setUrlScalar(String urlValue);

	/**
	 * DOI referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDoi();
	
	/**
	 * DOI referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param doiDataset the doiDataset
	 */
	public DataNode setDoi(IDataset doiDataset);

	/**
	 * DOI referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDoiScalar();

	/**
	 * DOI referencing the document or data.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param doi the doi
	 */
	public DataNode setDoiScalar(String doiValue);

	/**
	 * Bibliographic reference data in EndNote format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEndnote();
	
	/**
	 * Bibliographic reference data in EndNote format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param endnoteDataset the endnoteDataset
	 */
	public DataNode setEndnote(IDataset endnoteDataset);

	/**
	 * Bibliographic reference data in EndNote format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getEndnoteScalar();

	/**
	 * Bibliographic reference data in EndNote format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param endnote the endnote
	 */
	public DataNode setEndnoteScalar(String endnoteValue);

	/**
	 * Bibliographic reference data in BibTeX format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBibtex();
	
	/**
	 * Bibliographic reference data in BibTeX format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param bibtexDataset the bibtexDataset
	 */
	public DataNode setBibtex(IDataset bibtexDataset);

	/**
	 * Bibliographic reference data in BibTeX format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getBibtexScalar();

	/**
	 * Bibliographic reference data in BibTeX format.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param bibtex the bibtex
	 */
	public DataNode setBibtexScalar(String bibtexValue);

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
