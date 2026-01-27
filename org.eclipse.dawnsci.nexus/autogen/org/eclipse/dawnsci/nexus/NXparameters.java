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
import org.eclipse.january.dataset.Dataset;

/**
 * Container for parameters, usually used in processing or analysis.
 *
 */
public interface NXparameters extends NXobject {

	public static final String NX_PARAMETER = "parameter";
	public static final String NX_PARAMETER_ATTRIBUTE_UNITS = "units";
	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param parameter the parameter
	 * @return  the value.
	 */
	public Dataset getParameter(String parameter);

	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param parameter the parameter
	 * @param parameterDataset the parameterDataset
	 */
	public DataNode setParameter(String parameter, IDataset parameterDataset);

	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param parameter the parameter
	 * @return  the value.
	 */
	public Object getParameterScalar(String parameter);

	/**
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param parameter the parameter
	 * @param parameter the parameter
	 */
	public DataNode setParameterScalar(String parameter, Object parameterValue);


	/**
	 * Get all Parameter fields:
	 *
	 * A parameter (also known as a term) that is used in or results from processing.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 *
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllParameter();

	/**
	 *
	 * @param parameter the parameter
	 * @return  the value.
	 */
	public String getParameterAttributeUnits(String parameter);

	/**
	 *
	 * @param parameter the parameter
	 * @param unitsValue the unitsValue
	 */
	public void setParameterAttributeUnits(String parameter, String unitsValue);

}
