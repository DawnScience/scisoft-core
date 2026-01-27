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
 * Base class documenting parameters for processing used by all tools of the
 * paraprobe-toolbox.
 *
 */
public interface NXapm_paraprobe_tool_parameters extends NXparameters {

	public static final String NX_IDENTIFIER_ANALYSIS = "identifier_analysis";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_analysis();

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_analysisDataset the identifier_analysisDataset
	 */
	public DataNode setIdentifier_analysis(IDataset identifier_analysisDataset);

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_analysisScalar();

	/**
	 * Internal identifier used by the tool to refer to an analysis.
	 * Simulation ID an alias.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_analysis the identifier_analysis
	 */
	public DataNode setIdentifier_analysisScalar(Long identifier_analysisValue);

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * Although offered here for convenience, we strongly encourage to
	 * parameterize such descriptions as much as possible to support
	 * reusage and clearer communication.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * Although offered here for convenience, we strongly encourage to
	 * parameterize such descriptions as much as possible to support
	 * reusage and clearer communication.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * Although offered here for convenience, we strongly encourage to
	 * parameterize such descriptions as much as possible to support
	 * reusage and clearer communication.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * Although offered here for convenience, we strongly encourage to
	 * parameterize such descriptions as much as possible to support
	 * reusage and clearer communication.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Specification of the tomographic reconstruction to use for this analysis.
	 * Typically, reconstructions in the field of atom probe tomography are communicated
	 * via files which store at least reconstructed ion positions and mass-to-charge-state-ratio
	 * values. Container files like HDF5 though can store multiple reconstructions.
	 * Therefore, the position and mass_to_charge concepts point to specific instances
	 * to use for this analysis.
	 *
	 * @return  the value.
	 */
	public NXnote getReconstruction();

	/**
	 * Specification of the tomographic reconstruction to use for this analysis.
	 * Typically, reconstructions in the field of atom probe tomography are communicated
	 * via files which store at least reconstructed ion positions and mass-to-charge-state-ratio
	 * values. Container files like HDF5 though can store multiple reconstructions.
	 * Therefore, the position and mass_to_charge concepts point to specific instances
	 * to use for this analysis.
	 *
	 * @param reconstructionGroup the reconstructionGroup
	 */
	public void setReconstruction(NXnote reconstructionGroup);

	/**
	 * Specification of the ranging definitions to use for this analysis.
	 * Ranging is the process of labeling time-of-flight data with so-called iontypes
	 * (aka ion species). Ideally, iontypes specify the most likely (molecular) ion
	 * that is assumed to have been evaporated given that its mass-to-charge-state ratio
	 * lies within the specific mass-to-charge-state-ratio value interval of the iontype.
	 * The so-called unknown_type iontype represents the null model of an ion
	 * that has not been ranged (for whatever reasons) or is not rangeable.
	 * The identifier of this special iontype is always the reserved value 0.
	 *
	 * @return  the value.
	 */
	public NXnote getRanging();

	/**
	 * Specification of the ranging definitions to use for this analysis.
	 * Ranging is the process of labeling time-of-flight data with so-called iontypes
	 * (aka ion species). Ideally, iontypes specify the most likely (molecular) ion
	 * that is assumed to have been evaporated given that its mass-to-charge-state ratio
	 * lies within the specific mass-to-charge-state-ratio value interval of the iontype.
	 * The so-called unknown_type iontype represents the null model of an ion
	 * that has not been ranged (for whatever reasons) or is not rangeable.
	 * The identifier of this special iontype is always the reserved value 0.
	 *
	 * @param rangingGroup the rangingGroup
	 */
	public void setRanging(NXnote rangingGroup);

	/**
	 * Specification of the triangulated surface mesh to use for this analysis.
	 * Such a surface mesh can be used to define the edge of the reconstructed
	 * volume to account for finite size effects.
	 *
	 * @return  the value.
	 */
	public NXnote getSurface();

	/**
	 * Specification of the triangulated surface mesh to use for this analysis.
	 * Such a surface mesh can be used to define the edge of the reconstructed
	 * volume to account for finite size effects.
	 *
	 * @param surfaceGroup the surfaceGroup
	 */
	public void setSurface(NXnote surfaceGroup);

	/**
	 * Specification of the point-to-triangulated-surface-mesh distances to
	 * use for this analysis.
	 *
	 * @return  the value.
	 */
	public NXnote getSurface_distance();

	/**
	 * Specification of the point-to-triangulated-surface-mesh distances to
	 * use for this analysis.
	 *
	 * @param surface_distanceGroup the surface_distanceGroup
	 */
	public void setSurface_distance(NXnote surface_distanceGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXspatial_filter getSpatial_filter();

	/**
	 *
	 * @param spatial_filterGroup the spatial_filterGroup
	 */
	public void setSpatial_filter(NXspatial_filter spatial_filterGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXsubsampling_filter getEvaporation_id_filter();

	/**
	 *
	 * @param evaporation_id_filterGroup the evaporation_id_filterGroup
	 */
	public void setEvaporation_id_filter(NXsubsampling_filter evaporation_id_filterGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXmatch_filter getIontype_filter();

	/**
	 *
	 * @param iontype_filterGroup the iontype_filterGroup
	 */
	public void setIontype_filter(NXmatch_filter iontype_filterGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXmatch_filter getHit_multiplicity_filter();

	/**
	 *
	 * @param hit_multiplicity_filterGroup the hit_multiplicity_filterGroup
	 */
	public void setHit_multiplicity_filter(NXmatch_filter hit_multiplicity_filterGroup);

}
