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
 * A fresnel zone plate
 *
 */
public interface NXfresnel_zone_plate extends NXobject {

	public static final String NX_FOCUS_PARAMETERS = "focus_parameters";
	public static final String NX_OUTER_DIAMETER = "outer_diameter";
	public static final String NX_OUTERMOST_ZONE_WIDTH = "outermost_zone_width";
	public static final String NX_CENTRAL_STOP_DIAMETER = "central_stop_diameter";
	public static final String NX_FABRICATION = "fabrication";
	public static final String NX_ZONE_HEIGHT = "zone_height";
	public static final String NX_ZONE_MATERIAL = "zone_material";
	public static final String NX_ZONE_SUPPORT_MATERIAL = "zone_support_material";
	public static final String NX_CENTRAL_STOP_MATERIAL = "central_stop_material";
	public static final String NX_CENTRAL_STOP_THICKNESS = "central_stop_thickness";
	public static final String NX_MASK_THICKNESS = "mask_thickness";
	public static final String NX_MASK_MATERIAL = "mask_material";
	public static final String NX_SUPPORT_MEMBRANE_MATERIAL = "support_membrane_material";
	public static final String NX_SUPPORT_MEMBRANE_THICKNESS = "support_membrane_thickness";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * list of polynomial coefficients describing the focal length of the zone plate, in increasing powers of photon energy,
	 * that describes the focal length of the zone plate (in microns) at an X-ray photon energy (in electron volts).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFocus_parameters();

	/**
	 * list of polynomial coefficients describing the focal length of the zone plate, in increasing powers of photon energy,
	 * that describes the focal length of the zone plate (in microns) at an X-ray photon energy (in electron volts).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param focus_parametersDataset the focus_parametersDataset
	 */
	public DataNode setFocus_parameters(IDataset focus_parametersDataset);

	/**
	 * list of polynomial coefficients describing the focal length of the zone plate, in increasing powers of photon energy,
	 * that describes the focal length of the zone plate (in microns) at an X-ray photon energy (in electron volts).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getFocus_parametersScalar();

	/**
	 * list of polynomial coefficients describing the focal length of the zone plate, in increasing powers of photon energy,
	 * that describes the focal length of the zone plate (in microns) at an X-ray photon energy (in electron volts).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param focus_parameters the focus_parameters
	 */
	public DataNode setFocus_parametersScalar(Double focus_parametersValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOuter_diameter();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param outer_diameterDataset the outer_diameterDataset
	 */
	public DataNode setOuter_diameter(IDataset outer_diameterDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOuter_diameterScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param outer_diameter the outer_diameter
	 */
	public DataNode setOuter_diameterScalar(Double outer_diameterValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutermost_zone_width();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param outermost_zone_widthDataset the outermost_zone_widthDataset
	 */
	public DataNode setOutermost_zone_width(IDataset outermost_zone_widthDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOutermost_zone_widthScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param outermost_zone_width the outermost_zone_width
	 */
	public DataNode setOutermost_zone_widthScalar(Double outermost_zone_widthValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCentral_stop_diameter();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param central_stop_diameterDataset the central_stop_diameterDataset
	 */
	public DataNode setCentral_stop_diameter(IDataset central_stop_diameterDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCentral_stop_diameterScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param central_stop_diameter the central_stop_diameter
	 */
	public DataNode setCentral_stop_diameterScalar(Double central_stop_diameterValue);

	/**
	 * how the zone plate was manufactured
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>etched</b> </li>
	 * <li><b>plated</b> </li>
	 * <li><b>zone doubled</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFabrication();

	/**
	 * how the zone plate was manufactured
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>etched</b> </li>
	 * <li><b>plated</b> </li>
	 * <li><b>zone doubled</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param fabricationDataset the fabricationDataset
	 */
	public DataNode setFabrication(IDataset fabricationDataset);

	/**
	 * how the zone plate was manufactured
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>etched</b> </li>
	 * <li><b>plated</b> </li>
	 * <li><b>zone doubled</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getFabricationScalar();

	/**
	 * how the zone plate was manufactured
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>etched</b> </li>
	 * <li><b>plated</b> </li>
	 * <li><b>zone doubled</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param fabrication the fabrication
	 */
	public DataNode setFabricationScalar(String fabricationValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getZone_height();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param zone_heightDataset the zone_heightDataset
	 */
	public DataNode setZone_height(IDataset zone_heightDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getZone_heightScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param zone_height the zone_height
	 */
	public DataNode setZone_heightScalar(Double zone_heightValue);

	/**
	 * Material of the zones themselves
	 *
	 * @return  the value.
	 */
	public Dataset getZone_material();

	/**
	 * Material of the zones themselves
	 *
	 * @param zone_materialDataset the zone_materialDataset
	 */
	public DataNode setZone_material(IDataset zone_materialDataset);

	/**
	 * Material of the zones themselves
	 *
	 * @return  the value.
	 */
	public String getZone_materialScalar();

	/**
	 * Material of the zones themselves
	 *
	 * @param zone_material the zone_material
	 */
	public DataNode setZone_materialScalar(String zone_materialValue);

	/**
	 * Material present between the zones. This is usually only present for the "zone doubled" fabrication process
	 *
	 * @return  the value.
	 */
	public Dataset getZone_support_material();

	/**
	 * Material present between the zones. This is usually only present for the "zone doubled" fabrication process
	 *
	 * @param zone_support_materialDataset the zone_support_materialDataset
	 */
	public DataNode setZone_support_material(IDataset zone_support_materialDataset);

	/**
	 * Material present between the zones. This is usually only present for the "zone doubled" fabrication process
	 *
	 * @return  the value.
	 */
	public String getZone_support_materialScalar();

	/**
	 * Material present between the zones. This is usually only present for the "zone doubled" fabrication process
	 *
	 * @param zone_support_material the zone_support_material
	 */
	public DataNode setZone_support_materialScalar(String zone_support_materialValue);

	/**
	 *
	 * @return  the value.
	 */
	public Dataset getCentral_stop_material();

	/**
	 *
	 * @param central_stop_materialDataset the central_stop_materialDataset
	 */
	public DataNode setCentral_stop_material(IDataset central_stop_materialDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getCentral_stop_materialScalar();

	/**
	 *
	 * @param central_stop_material the central_stop_material
	 */
	public DataNode setCentral_stop_materialScalar(String central_stop_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCentral_stop_thickness();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param central_stop_thicknessDataset the central_stop_thicknessDataset
	 */
	public DataNode setCentral_stop_thickness(IDataset central_stop_thicknessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCentral_stop_thicknessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param central_stop_thickness the central_stop_thickness
	 */
	public DataNode setCentral_stop_thicknessScalar(Double central_stop_thicknessValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMask_thickness();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param mask_thicknessDataset the mask_thicknessDataset
	 */
	public DataNode setMask_thickness(IDataset mask_thicknessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMask_thicknessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param mask_thickness the mask_thickness
	 */
	public DataNode setMask_thicknessScalar(Double mask_thicknessValue);

	/**
	 * If no mask is present, set mask_thickness to 0 and omit the mask_material field
	 *
	 * @return  the value.
	 */
	public Dataset getMask_material();

	/**
	 * If no mask is present, set mask_thickness to 0 and omit the mask_material field
	 *
	 * @param mask_materialDataset the mask_materialDataset
	 */
	public DataNode setMask_material(IDataset mask_materialDataset);

	/**
	 * If no mask is present, set mask_thickness to 0 and omit the mask_material field
	 *
	 * @return  the value.
	 */
	public String getMask_materialScalar();

	/**
	 * If no mask is present, set mask_thickness to 0 and omit the mask_material field
	 *
	 * @param mask_material the mask_material
	 */
	public DataNode setMask_materialScalar(String mask_materialValue);

	/**
	 *
	 * @return  the value.
	 */
	public Dataset getSupport_membrane_material();

	/**
	 *
	 * @param support_membrane_materialDataset the support_membrane_materialDataset
	 */
	public DataNode setSupport_membrane_material(IDataset support_membrane_materialDataset);

	/**
	 *
	 * @return  the value.
	 */
	public String getSupport_membrane_materialScalar();

	/**
	 *
	 * @param support_membrane_material the support_membrane_material
	 */
	public DataNode setSupport_membrane_materialScalar(String support_membrane_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSupport_membrane_thickness();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param support_membrane_thicknessDataset the support_membrane_thicknessDataset
	 */
	public DataNode setSupport_membrane_thickness(IDataset support_membrane_thicknessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSupport_membrane_thicknessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param support_membrane_thickness the support_membrane_thickness
	 */
	public DataNode setSupport_membrane_thicknessScalar(Double support_membrane_thicknessValue);

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
	 * Add a definition for the reference point of a fresnel zone plate.
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
	 * Add a definition for the reference point of a fresnel zone plate.
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
	 * Add a definition for the reference point of a fresnel zone plate.
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
	 * Add a definition for the reference point of a fresnel zone plate.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * "Engineering" position of the fresnel zone plate
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * "Engineering" position of the fresnel zone plate
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
	 * "Engineering" position of the fresnel zone plate
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
	 * "Engineering" position of the fresnel zone plate
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
	 * "Engineering" position of the fresnel zone plate
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
	 * "Engineering" position of the fresnel zone plate
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
