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
 * A bending magnet
 *
 */
public interface NXbending_magnet extends NXobject {

	public static final String NX_CRITICAL_ENERGY = "critical_energy";
	public static final String NX_BENDING_RADIUS = "bending_radius";
	public static final String NX_MAGNETIC_FIELD = "magnetic_field";
	public static final String NX_ACCEPTED_PHOTON_BEAM_DIVERGENCE = "accepted_photon_beam_divergence";
	public static final String NX_SOURCE_DISTANCE_X = "source_distance_x";
	public static final String NX_SOURCE_DISTANCE_Y = "source_distance_y";
	public static final String NX_DIVERGENCE_X_PLUS = "divergence_x_plus";
	public static final String NX_DIVERGENCE_X_MINUS = "divergence_x_minus";
	public static final String NX_DIVERGENCE_Y_PLUS = "divergence_y_plus";
	public static final String NX_DIVERGENCE_Y_MINUS = "divergence_y_minus";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCritical_energy();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param critical_energyDataset the critical_energyDataset
	 */
	public DataNode setCritical_energy(IDataset critical_energyDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCritical_energyScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 *
	 * @param critical_energy the critical_energy
	 */
	public DataNode setCritical_energyScalar(Double critical_energyValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBending_radius();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param bending_radiusDataset the bending_radiusDataset
	 */
	public DataNode setBending_radius(IDataset bending_radiusDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBending_radiusScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param bending_radius the bending_radius
	 */
	public DataNode setBending_radiusScalar(Double bending_radiusValue);

	/**
	 * strength of magnetic field of dipole magnets
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMagnetic_field();

	/**
	 * strength of magnetic field of dipole magnets
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param magnetic_fieldDataset the magnetic_fieldDataset
	 */
	public DataNode setMagnetic_field(IDataset magnetic_fieldDataset);

	/**
	 * strength of magnetic field of dipole magnets
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMagnetic_fieldScalar();

	/**
	 * strength of magnetic field of dipole magnets
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param magnetic_field the magnetic_field
	 */
	public DataNode setMagnetic_fieldScalar(Double magnetic_fieldValue);

	/**
	 * An array of four numbers giving X+, X-, Y+ and Y- half divergence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAccepted_photon_beam_divergence();

	/**
	 * An array of four numbers giving X+, X-, Y+ and Y- half divergence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param accepted_photon_beam_divergenceDataset the accepted_photon_beam_divergenceDataset
	 */
	public DataNode setAccepted_photon_beam_divergence(IDataset accepted_photon_beam_divergenceDataset);

	/**
	 * An array of four numbers giving X+, X-, Y+ and Y- half divergence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAccepted_photon_beam_divergenceScalar();

	/**
	 * An array of four numbers giving X+, X-, Y+ and Y- half divergence
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param accepted_photon_beam_divergence the accepted_photon_beam_divergence
	 */
	public DataNode setAccepted_photon_beam_divergenceScalar(Double accepted_photon_beam_divergenceValue);

	/**
	 * Distance of source point from particle beam waist in X (horizontal) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSource_distance_x();

	/**
	 * Distance of source point from particle beam waist in X (horizontal) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param source_distance_xDataset the source_distance_xDataset
	 */
	public DataNode setSource_distance_x(IDataset source_distance_xDataset);

	/**
	 * Distance of source point from particle beam waist in X (horizontal) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSource_distance_xScalar();

	/**
	 * Distance of source point from particle beam waist in X (horizontal) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param source_distance_x the source_distance_x
	 */
	public DataNode setSource_distance_xScalar(Double source_distance_xValue);

	/**
	 * Distance of source point from particle beam waist in Y (vertical) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSource_distance_y();

	/**
	 * Distance of source point from particle beam waist in Y (vertical) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param source_distance_yDataset the source_distance_yDataset
	 */
	public DataNode setSource_distance_y(IDataset source_distance_yDataset);

	/**
	 * Distance of source point from particle beam waist in Y (vertical) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSource_distance_yScalar();

	/**
	 * Distance of source point from particle beam waist in Y (vertical) direction.
	 * Note, it is recommended to use NXtransformations instead to place component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param source_distance_y the source_distance_y
	 */
	public DataNode setSource_distance_yScalar(Double source_distance_yValue);

	/**
	 * Accepted photon beam divergence in X+ (horizontal outboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDivergence_x_plus();

	/**
	 * Accepted photon beam divergence in X+ (horizontal outboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_x_plusDataset the divergence_x_plusDataset
	 */
	public DataNode setDivergence_x_plus(IDataset divergence_x_plusDataset);

	/**
	 * Accepted photon beam divergence in X+ (horizontal outboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDivergence_x_plusScalar();

	/**
	 * Accepted photon beam divergence in X+ (horizontal outboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_x_plus the divergence_x_plus
	 */
	public DataNode setDivergence_x_plusScalar(Double divergence_x_plusValue);

	/**
	 * Accepted photon beam divergence in X- (horizontal inboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDivergence_x_minus();

	/**
	 * Accepted photon beam divergence in X- (horizontal inboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_x_minusDataset the divergence_x_minusDataset
	 */
	public DataNode setDivergence_x_minus(IDataset divergence_x_minusDataset);

	/**
	 * Accepted photon beam divergence in X- (horizontal inboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDivergence_x_minusScalar();

	/**
	 * Accepted photon beam divergence in X- (horizontal inboard) direction.
	 * Note that divergence_x_plus+divergence_x_minus is the total horizontal beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_x_minus the divergence_x_minus
	 */
	public DataNode setDivergence_x_minusScalar(Double divergence_x_minusValue);

	/**
	 * Accepted photon beam divergence in Y+ (vertical upward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDivergence_y_plus();

	/**
	 * Accepted photon beam divergence in Y+ (vertical upward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_y_plusDataset the divergence_y_plusDataset
	 */
	public DataNode setDivergence_y_plus(IDataset divergence_y_plusDataset);

	/**
	 * Accepted photon beam divergence in Y+ (vertical upward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDivergence_y_plusScalar();

	/**
	 * Accepted photon beam divergence in Y+ (vertical upward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_y_plus the divergence_y_plus
	 */
	public DataNode setDivergence_y_plusScalar(Double divergence_y_plusValue);

	/**
	 * Accepted photon beam divergence in Y- (vertical downward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDivergence_y_minus();

	/**
	 * Accepted photon beam divergence in Y- (vertical downward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_y_minusDataset the divergence_y_minusDataset
	 */
	public DataNode setDivergence_y_minus(IDataset divergence_y_minusDataset);

	/**
	 * Accepted photon beam divergence in Y- (vertical downward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDivergence_y_minusScalar();

	/**
	 * Accepted photon beam divergence in Y- (vertical downward) direction.
	 * Note that divergence_y_plus+divergence_y_minus is the total vertical beam divergence.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param divergence_y_minus the divergence_y_minus
	 */
	public DataNode setDivergence_y_minusScalar(Double divergence_y_minusValue);

	/**
	 * bending magnet spectrum
	 *
	 * @return  the value.
	 */
	public NXdata getSpectrum();

	/**
	 * bending magnet spectrum
	 *
	 * @param spectrumGroup the spectrumGroup
	 */
	public void setSpectrum(NXdata spectrumGroup);

	/**
	 * "Engineering" position of bending magnet
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the bending magnet and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * "Engineering" position of bending magnet
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the bending magnet and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * "Engineering" position of bending magnet</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the bending magnet and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * "Engineering" position of bending magnet</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the bending magnet and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * "Engineering" position of bending magnet</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the bending magnet and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * "Engineering" position of bending magnet</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the bending magnet and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * This group describes the shape of the beam line component
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);

	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);

	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


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

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a bending magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a bending magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a bending magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a bending magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}
