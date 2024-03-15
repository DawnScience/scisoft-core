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
 * Computational geometry description of the marching cubes algorithm.
 * Documenting which specific version was used can help to understand how robust
 * the results are with respect to the topology of the triangulation.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcg_marching_cubes extends NXobject {

	public static final String NX_IMPLEMENTATION = "implementation";
	public static final String NX_PROGRAM = "program";
	public static final String NX_PROGRAM_ATTRIBUTE_VERSION = "version";
	/**
	 * Reference/link to and/or details of the grid on which a specific
	 * marching cubes algorithm implementation is operating.
	 *
	 * @return  the value.
	 */
	public NXcg_grid getGrid();

	/**
	 * Reference/link to and/or details of the grid on which a specific
	 * marching cubes algorithm implementation is operating.
	 *
	 * @param gridGroup the gridGroup
	 */
	public void setGrid(NXcg_grid gridGroup);

	/**
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should be a DOI. If there are no specific DOI or
	 * details write not_further_specified, or give at least a free-text
	 * description.
	 *
	 * @return  the value.
	 */
	public IDataset getImplementation();

	/**
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should be a DOI. If there are no specific DOI or
	 * details write not_further_specified, or give at least a free-text
	 * description.
	 *
	 * @param implementationDataset the implementationDataset
	 */
	public DataNode setImplementation(IDataset implementationDataset);

	/**
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should be a DOI. If there are no specific DOI or
	 * details write not_further_specified, or give at least a free-text
	 * description.
	 *
	 * @return  the value.
	 */
	public String getImplementationScalar();

	/**
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should be a DOI. If there are no specific DOI or
	 * details write not_further_specified, or give at least a free-text
	 * description.
	 *
	 * @param implementation the implementation
	 */
	public DataNode setImplementationScalar(String implementationValue);

	/**
	 * Commercial or otherwise given name to the program which was used.
	 *
	 * @return  the value.
	 */
	public IDataset getProgram();

	/**
	 * Commercial or otherwise given name to the program which was used.
	 *
	 * @param programDataset the programDataset
	 */
	public DataNode setProgram(IDataset programDataset);

	/**
	 * Commercial or otherwise given name to the program which was used.
	 *
	 * @return  the value.
	 */
	public String getProgramScalar();

	/**
	 * Commercial or otherwise given name to the program which was used.
	 *
	 * @param program the program
	 */
	public DataNode setProgramScalar(String programValue);

	/**
	 * Program version plus build number, commit hash, or description of
	 * an ever persistent resource where the source code of the program
	 * and build instructions can be found so that the program can be
	 * configured in such a manner that the result file is ideally
	 * recreatable yielding the same results.
	 *
	 * @return  the value.
	 */
	public String getProgramAttributeVersion();

	/**
	 * Program version plus build number, commit hash, or description of
	 * an ever persistent resource where the source code of the program
	 * and build instructions can be found so that the program can be
	 * configured in such a manner that the result file is ideally
	 * recreatable yielding the same results.
	 *
	 * @param versionValue the versionValue
	 */
	public void setProgramAttributeVersion(String versionValue);

}
