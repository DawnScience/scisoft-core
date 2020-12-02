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
 * Container for parameters, usually used in processing or analysis.
 * 
 */
public interface NXparameters extends NXobject {

	public static final String NX_TERM = "term";
	public static final String NX_TERM_ATTRIBUTE_UNITS = "units";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTerm();
	
	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param termDataset the termDataset
	 */
	public DataNode setTerm(IDataset termDataset);

	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTermScalar();

	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param term the term
	 */
	public DataNode setTermScalar(String termValue);

	/**
	 * 
	 * @return  the value.
	 */
	public String getTermAttributeUnits();
	
	/**
	 * 
	 * @param unitsValue the unitsValue
	 */
	public void setTermAttributeUnits(String unitsValue);

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
