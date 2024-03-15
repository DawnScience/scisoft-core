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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Metadata of a dataset (tomographic reconstruction) in atom probe microscopy.

 */
public class NXapm_input_reconstructionImpl extends NXobjectImpl implements NXapm_input_reconstruction {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXapm_input_reconstructionImpl() {
		super();
	}

	public NXapm_input_reconstructionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_input_reconstruction.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_INPUT_RECONSTRUCTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getFilename() {
		return getDataset(NX_FILENAME);
	}

	@Override
	public String getFilenameScalar() {
		return getString(NX_FILENAME);
	}

	@Override
	public DataNode setFilename(IDataset filenameDataset) {
		return setDataset(NX_FILENAME, filenameDataset);
	}

	@Override
	public DataNode setFilenameScalar(String filenameValue) {
		return setString(NX_FILENAME, filenameValue);
	}

	@Override
	public String getFilenameAttributeVersion() {
		return getAttrString(NX_FILENAME, NX_FILENAME_ATTRIBUTE_VERSION);
	}

	@Override
	public void setFilenameAttributeVersion(String versionValue) {
		setAttribute(NX_FILENAME, NX_FILENAME_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public IDataset getDataset_name_reconstruction() {
		return getDataset(NX_DATASET_NAME_RECONSTRUCTION);
	}

	@Override
	public String getDataset_name_reconstructionScalar() {
		return getString(NX_DATASET_NAME_RECONSTRUCTION);
	}

	@Override
	public DataNode setDataset_name_reconstruction(IDataset dataset_name_reconstructionDataset) {
		return setDataset(NX_DATASET_NAME_RECONSTRUCTION, dataset_name_reconstructionDataset);
	}

	@Override
	public DataNode setDataset_name_reconstructionScalar(String dataset_name_reconstructionValue) {
		return setString(NX_DATASET_NAME_RECONSTRUCTION, dataset_name_reconstructionValue);
	}

	@Override
	public IDataset getDataset_name_mass_to_charge() {
		return getDataset(NX_DATASET_NAME_MASS_TO_CHARGE);
	}

	@Override
	public String getDataset_name_mass_to_chargeScalar() {
		return getString(NX_DATASET_NAME_MASS_TO_CHARGE);
	}

	@Override
	public DataNode setDataset_name_mass_to_charge(IDataset dataset_name_mass_to_chargeDataset) {
		return setDataset(NX_DATASET_NAME_MASS_TO_CHARGE, dataset_name_mass_to_chargeDataset);
	}

	@Override
	public DataNode setDataset_name_mass_to_chargeScalar(String dataset_name_mass_to_chargeValue) {
		return setString(NX_DATASET_NAME_MASS_TO_CHARGE, dataset_name_mass_to_chargeValue);
	}

}
