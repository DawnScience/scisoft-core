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
 * Metadata of a dataset (tomographic reconstruction) in atom probe microscopy.
 *
 */
public interface NXapm_input_reconstruction extends NXobject {

	public static final String NX_FILENAME = "filename";
	public static final String NX_FILENAME_ATTRIBUTE_VERSION = "version";
	public static final String NX_DATASET_NAME_RECONSTRUCTION = "dataset_name_reconstruction";
	public static final String NX_DATASET_NAME_MASS_TO_CHARGE = "dataset_name_mass_to_charge";
	/**
	 * Name of the (NeXus)/HDF5 file which stores reconstructed ion position
	 * and mass-to-charge-state ratios. Such an HDF5 file can store multiple
	 * reconstructions. Using the information within the dataset_name fields
	 * is the mechanism whereby paraprobe decides which reconstruction to
	 * process. With this design it is possible that the same HDF5
	 * file can store multiple versions of a reconstruction.
	 *
	 * @return  the value.
	 */
	public IDataset getFilename();

	/**
	 * Name of the (NeXus)/HDF5 file which stores reconstructed ion position
	 * and mass-to-charge-state ratios. Such an HDF5 file can store multiple
	 * reconstructions. Using the information within the dataset_name fields
	 * is the mechanism whereby paraprobe decides which reconstruction to
	 * process. With this design it is possible that the same HDF5
	 * file can store multiple versions of a reconstruction.
	 *
	 * @param filenameDataset the filenameDataset
	 */
	public DataNode setFilename(IDataset filenameDataset);

	/**
	 * Name of the (NeXus)/HDF5 file which stores reconstructed ion position
	 * and mass-to-charge-state ratios. Such an HDF5 file can store multiple
	 * reconstructions. Using the information within the dataset_name fields
	 * is the mechanism whereby paraprobe decides which reconstruction to
	 * process. With this design it is possible that the same HDF5
	 * file can store multiple versions of a reconstruction.
	 *
	 * @return  the value.
	 */
	public String getFilenameScalar();

	/**
	 * Name of the (NeXus)/HDF5 file which stores reconstructed ion position
	 * and mass-to-charge-state ratios. Such an HDF5 file can store multiple
	 * reconstructions. Using the information within the dataset_name fields
	 * is the mechanism whereby paraprobe decides which reconstruction to
	 * process. With this design it is possible that the same HDF5
	 * file can store multiple versions of a reconstruction.
	 *
	 * @param filename the filename
	 */
	public DataNode setFilenameScalar(String filenameValue);

	/**
	 * Version identifier of the file (representing an at least SHA256 strong)
	 * hash. Such hashes serve reproducibility as they can be used for tracking
	 * provenance metadata in a workflow.
	 *
	 * @return  the value.
	 */
	public String getFilenameAttributeVersion();

	/**
	 * Version identifier of the file (representing an at least SHA256 strong)
	 * hash. Such hashes serve reproducibility as they can be used for tracking
	 * provenance metadata in a workflow.
	 *
	 * @param versionValue the versionValue
	 */
	public void setFilenameAttributeVersion(String versionValue);

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific reconstructed ion positions to use for this analysis.
	 *
	 * @return  the value.
	 */
	public IDataset getDataset_name_reconstruction();

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific reconstructed ion positions to use for this analysis.
	 *
	 * @param dataset_name_reconstructionDataset the dataset_name_reconstructionDataset
	 */
	public DataNode setDataset_name_reconstruction(IDataset dataset_name_reconstructionDataset);

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific reconstructed ion positions to use for this analysis.
	 *
	 * @return  the value.
	 */
	public String getDataset_name_reconstructionScalar();

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific reconstructed ion positions to use for this analysis.
	 *
	 * @param dataset_name_reconstruction the dataset_name_reconstruction
	 */
	public DataNode setDataset_name_reconstructionScalar(String dataset_name_reconstructionValue);

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific mass-to-charge-state-ratio values to use for this analysis.
	 *
	 * @return  the value.
	 */
	public IDataset getDataset_name_mass_to_charge();

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific mass-to-charge-state-ratio values to use for this analysis.
	 *
	 * @param dataset_name_mass_to_chargeDataset the dataset_name_mass_to_chargeDataset
	 */
	public DataNode setDataset_name_mass_to_charge(IDataset dataset_name_mass_to_chargeDataset);

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific mass-to-charge-state-ratio values to use for this analysis.
	 *
	 * @return  the value.
	 */
	public String getDataset_name_mass_to_chargeScalar();

	/**
	 * Name of the dataset inside the HDF5 file which refers to the
	 * specific mass-to-charge-state-ratio values to use for this analysis.
	 *
	 * @param dataset_name_mass_to_charge the dataset_name_mass_to_charge
	 */
	public DataNode setDataset_name_mass_to_chargeScalar(String dataset_name_mass_to_chargeValue);

}
