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
import org.eclipse.january.dataset.Dataset;

/**
 * Base class to describe a software tool or library.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXprogram extends NXobject {

	public static final String NX_PROGRAM = "program";
	public static final String NX_PROGRAM_ATTRIBUTE_VERSION = "version";
	public static final String NX_PROGRAM_ATTRIBUTE_URL = "url";
	/**
	 * Given name of the program. Program can be a commercial one a script,
	 * or a library or a library component.
	 *
	 * @return  the value.
	 */
	public Dataset getProgram();

	/**
	 * Given name of the program. Program can be a commercial one a script,
	 * or a library or a library component.
	 *
	 * @param programDataset the programDataset
	 */
	public DataNode setProgram(IDataset programDataset);

	/**
	 * Given name of the program. Program can be a commercial one a script,
	 * or a library or a library component.
	 *
	 * @return  the value.
	 */
	public String getProgramScalar();

	/**
	 * Given name of the program. Program can be a commercial one a script,
	 * or a library or a library component.
	 *
	 * @param program the program
	 */
	public DataNode setProgramScalar(String programValue);

	/**
	 * Program version plus build number, or commit hash.
	 *
	 * @return  the value.
	 */
	public String getProgramAttributeVersion();

	/**
	 * Program version plus build number, or commit hash.
	 *
	 * @param versionValue the versionValue
	 */
	public void setProgramAttributeVersion(String versionValue);

	/**
	 * Description of an ideally ever persistent resource where the source code
	 * of the program or this specific compiled version of the program can be
	 * found so that the program yields repeatably exactly the same numerical
	 * and categorical results.
	 *
	 * @return  the value.
	 */
	public String getProgramAttributeUrl();

	/**
	 * Description of an ideally ever persistent resource where the source code
	 * of the program or this specific compiled version of the program can be
	 * found so that the program yields repeatably exactly the same numerical
	 * and categorical results.
	 *
	 * @param urlValue the urlValue
	 */
	public void setProgramAttributeUrl(String urlValue);

}
