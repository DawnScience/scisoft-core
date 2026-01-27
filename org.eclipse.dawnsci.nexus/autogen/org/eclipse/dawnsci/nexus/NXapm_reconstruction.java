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
 * Base class for the configuration and results of a reconstruction algorithm.
 * Generating a tomographic reconstruction of the specimen uses selected and
 * calibrated ion hit positions, the evaporation sequence, and voltage curve data.
 * Very often scientists use own software scripts according to published procedures,
 * so-called reconstruction protocols.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n</b>
 * Number of ions spatially filtered from results of the hit_finding algorithm
 * from which an instance of a reconstructed volume has been generated.
 * These ions get new identifier assigned in the process - the so-called
 * evaporation_id, which must not be confused with the pulse_id!</li></ul></p>
 *
 */
public interface NXapm_reconstruction extends NXprocess {

	public static final String NX_RECONSTRUCTED_POSITIONS = "reconstructed_positions";
	public static final String NX_RECONSTRUCTED_POSITIONS_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_QUALITY = "quality";
	public static final String NX_VOLUME = "volume";
	public static final String NX_FIELD_OF_VIEW = "field_of_view";
	/**
	 *
	 * @return  the value.
	 */
	public NXprogram getNXProgram();

	/**
	 *
	 * @param programGroup the programGroup
	 */
	public void setNXProgram(NXprogram programGroup);

	/**
	 * Get a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public NXprogram getNXProgram(String name);

	/**
	 * Set a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param program the value to set
	 */
	public void setNXProgram(String name, NXprogram program);

	/**
	 * Get all NXprogram nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public Map<String, NXprogram> getAllNXProgram();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param program the child nodes to add
	 */

	public void setAllNXProgram(Map<String, NXprogram> program);


	/**
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXnote for that node.
	 */
	public NXnote getNote(String name);

	/**
	 * Set a NXnote node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param note the value to set
	 */
	public void setNote(String name, NXnote note);

	/**
	 * Get all NXnote nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


	/**
	 * Parameters that configure a reconstruction algorithm which takes
	 * hit data and mass-to-charge-state ratio values to construct a model
	 * of the evaporated specimen. This model is called the reconstructed volume.
	 * Researchers in the field of atom probe call these algorithms reconstruction
	 * protocols.
	 * Different such protocols exist. Although these are qualitatively similar,
	 * each protocol uses and interprets the parameters slightly differently.
	 * The majority of reconstructions is performed with the proprietary software
	 * APSuite / IVAS, the source code for the reconstruction protocols that this
	 * software implements in detail is not open but the parameters and their qualitative
	 * effect on the reconstructed volume follows the protocols that are discussed in
	 * the atom probe literature. This group allows to document these parameters in
	 * a standardized manner.
	 *
	 * @return  the value.
	 */
	public NXparameters getConfig();

	/**
	 * Parameters that configure a reconstruction algorithm which takes
	 * hit data and mass-to-charge-state ratio values to construct a model
	 * of the evaporated specimen. This model is called the reconstructed volume.
	 * Researchers in the field of atom probe call these algorithms reconstruction
	 * protocols.
	 * Different such protocols exist. Although these are qualitatively similar,
	 * each protocol uses and interprets the parameters slightly differently.
	 * The majority of reconstructions is performed with the proprietary software
	 * APSuite / IVAS, the source code for the reconstruction protocols that this
	 * software implements in detail is not open but the parameters and their qualitative
	 * effect on the reconstructed volume follows the protocols that are discussed in
	 * the atom probe literature. This group allows to document these parameters in
	 * a standardized manner.
	 *
	 * @param configGroup the configGroup
	 */
	public void setConfig(NXparameters configGroup);

	/**
	 * Three-dimensional positions of the ions in the reconstructed volume.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReconstructed_positions();

	/**
	 * Three-dimensional positions of the ions in the reconstructed volume.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n; 2: 3;
	 * </p>
	 *
	 * @param reconstructed_positionsDataset the reconstructed_positionsDataset
	 */
	public DataNode setReconstructed_positions(IDataset reconstructed_positionsDataset);

	/**
	 * Three-dimensional positions of the ions in the reconstructed volume.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getReconstructed_positionsScalar();

	/**
	 * Three-dimensional positions of the ions in the reconstructed volume.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n; 2: 3;
	 * </p>
	 *
	 * @param reconstructed_positions the reconstructed_positions
	 */
	public DataNode setReconstructed_positionsScalar(Double reconstructed_positionsValue);

	/**
	 * The instance of :ref:`NXcoordinate_system` in which the positions are defined.
	 *
	 * @return  the value.
	 */
	public String getReconstructed_positionsAttributeDepends_on();

	/**
	 * The instance of :ref:`NXcoordinate_system` in which the positions are defined.
	 *
	 * @param depends_onValue the depends_onValue
	 */
	public void setReconstructed_positionsAttributeDepends_on(String depends_onValue);

	/**
	 * Qualitative statement about the reconstruction.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getQuality();

	/**
	 * Qualitative statement about the reconstruction.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param qualityDataset the qualityDataset
	 */
	public DataNode setQuality(IDataset qualityDataset);

	/**
	 * Qualitative statement about the reconstruction.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getQualityScalar();

	/**
	 * Qualitative statement about the reconstruction.
	 * The value can be extracted from the CAnalysis.CResults.fQuality
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param quality the quality
	 */
	public DataNode setQualityScalar(String qualityValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXprocess getNaive_discretization();

	/**
	 *
	 * @param naive_discretizationGroup the naive_discretizationGroup
	 */
	public void setNaive_discretization(NXprocess naive_discretizationGroup);
	// Unprocessed group:
	// Unprocessed group:

	/**
	 * Sum of ion volumes
	 * The value can be extracted from the CAnalysis.CSpatial.fRecoVolume
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * Sum of ion volumes
	 * The value can be extracted from the CAnalysis.CSpatial.fRecoVolume
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * Sum of ion volumes
	 * The value can be extracted from the CAnalysis.CSpatial.fRecoVolume
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getVolumeScalar();

	/**
	 * Sum of ion volumes
	 * The value can be extracted from the CAnalysis.CSpatial.fRecoVolume
	 * field of a CamecaRoot ROOT file.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Double volumeValue);

	/**
	 * The nominal diameter of the specimen ROI which is measured in the
	 * experiment. The physical specimen cannot be measured completely
	 * because ions may launch but hit in locations other than the detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getField_of_view();

	/**
	 * The nominal diameter of the specimen ROI which is measured in the
	 * experiment. The physical specimen cannot be measured completely
	 * because ions may launch but hit in locations other than the detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param field_of_viewDataset the field_of_viewDataset
	 */
	public DataNode setField_of_view(IDataset field_of_viewDataset);

	/**
	 * The nominal diameter of the specimen ROI which is measured in the
	 * experiment. The physical specimen cannot be measured completely
	 * because ions may launch but hit in locations other than the detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getField_of_viewScalar();

	/**
	 * The nominal diameter of the specimen ROI which is measured in the
	 * experiment. The physical specimen cannot be measured completely
	 * because ions may launch but hit in locations other than the detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param field_of_view the field_of_view
	 */
	public DataNode setField_of_viewScalar(Double field_of_viewValue);

	/**
	 * Tight, axis-aligned bounding box about the point cloud of the reconstruction.
	 *
	 * @return  the value.
	 */
	public NXcollection getObb();

	/**
	 * Tight, axis-aligned bounding box about the point cloud of the reconstruction.
	 *
	 * @param obbGroup the obbGroup
	 */
	public void setObb(NXcollection obbGroup);

}
