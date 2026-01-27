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


/**
 * Base class for documenting structuring features of a microstructure.
 * Instances of the class enable sub-grouping of microstructural features
 * as the abstract base class NXobject should not be used for this purpose.
 *
 */
public interface NXmicrostructure_feature extends NXobject {

	/**
	 * The chemical composition of this microstructural feature
	 * or set of such features.
	 *
	 * @return  the value.
	 */
	public NXchemical_composition getChemical_composition();

	/**
	 * The chemical composition of this microstructural feature
	 * or set of such features.
	 *
	 * @param chemical_compositionGroup the chemical_compositionGroup
	 */
	public void setChemical_composition(NXchemical_composition chemical_compositionGroup);

}
