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
 * Computational geometry description of a set of cylinders or (truncated) cones.
 * The radius can either be defined in the radii field or by filling the upper_cap_radii
 * and lower_cap_radii fields respectively. The latter field case can
 * thus be used to represent (truncated) cones.
 * It is possible to define only one of the cap_radii fields
 * to represent half-open cylinder.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the space in which the members are assumed embedded.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of members.</li></ul></p>
 *
 */
public interface NXcg_cylinder extends NXcg_primitive {

	public static final String NX_RADIUS = "radius";
	public static final String NX_RADII = "radii";
	public static final String NX_UPPER_CAP_RADII = "upper_cap_radii";
	public static final String NX_LOWER_CAP_RADII = "lower_cap_radii";
	public static final String NX_LATERAL_SURFACE_AREA = "lateral_surface_area";
	public static final String NX_UPPER_CAP_SURFACE_AREA = "upper_cap_surface_area";
	public static final String NX_LOWER_CAP_SURFACE_AREA = "lower_cap_surface_area";
	public static final String NX_TOTAL_SURFACE_AREA = "total_surface_area";
	/**
	 * A direction vector which is parallel to the cylinder/cone axis
	 * and whose magnitude is the height of the cylinder/cone.
	 * The upper_cap is assumed to represent the end while the
	 * lower_cap is assumed to represent the start of the
	 * respective cylinder instances when inspecting along the
	 * direction vector.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHeight();

	/**
	 * A direction vector which is parallel to the cylinder/cone axis
	 * and whose magnitude is the height of the cylinder/cone.
	 * The upper_cap is assumed to represent the end while the
	 * lower_cap is assumed to represent the start of the
	 * respective cylinder instances when inspecting along the
	 * direction vector.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param heightDataset the heightDataset
	 */
	public DataNode setHeight(IDataset heightDataset);

	/**
	 * A direction vector which is parallel to the cylinder/cone axis
	 * and whose magnitude is the height of the cylinder/cone.
	 * The upper_cap is assumed to represent the end while the
	 * lower_cap is assumed to represent the start of the
	 * respective cylinder instances when inspecting along the
	 * direction vector.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getHeightScalar();

	/**
	 * A direction vector which is parallel to the cylinder/cone axis
	 * and whose magnitude is the height of the cylinder/cone.
	 * The upper_cap is assumed to represent the end while the
	 * lower_cap is assumed to represent the start of the
	 * respective cylinder instances when inspecting along the
	 * direction vector.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param height the height
	 */
	public DataNode setHeightScalar(Number heightValue);

	/**
	 * Radius of the cylinder if all have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadius();

	/**
	 * Radius of the cylinder if all have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radiusDataset the radiusDataset
	 */
	public DataNode setRadius(IDataset radiusDataset);

	/**
	 * Radius of the cylinder if all have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRadiusScalar();

	/**
	 * Radius of the cylinder if all have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radius the radius
	 */
	public DataNode setRadiusScalar(Number radiusValue);

	/**
	 * Radii of the cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadii();

	/**
	 * Radii of the cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param radiiDataset the radiiDataset
	 */
	public DataNode setRadii(IDataset radiiDataset);

	/**
	 * Radii of the cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRadiiScalar();

	/**
	 * Radii of the cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param radii the radii
	 */
	public DataNode setRadiiScalar(Number radiiValue);

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUpper_cap_radii();

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param upper_cap_radiiDataset the upper_cap_radiiDataset
	 */
	public DataNode setUpper_cap_radii(IDataset upper_cap_radiiDataset);

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getUpper_cap_radiiScalar();

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param upper_cap_radii the upper_cap_radii
	 */
	public DataNode setUpper_cap_radiiScalar(Number upper_cap_radiiValue);

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with upper_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLower_cap_radii();

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with upper_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lower_cap_radiiDataset the lower_cap_radiiDataset
	 */
	public DataNode setLower_cap_radii(IDataset lower_cap_radiiDataset);

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with upper_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLower_cap_radiiScalar();

	/**
	 * Radii of the upper circular cap.
	 * This field, combined with upper_cap_radius can be used to describe
	 * (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lower_cap_radii the lower_cap_radii
	 */
	public DataNode setLower_cap_radiiScalar(Number lower_cap_radiiValue);

	/**
	 * Lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLateral_surface_area();

	/**
	 * Lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lateral_surface_areaDataset the lateral_surface_areaDataset
	 */
	public DataNode setLateral_surface_area(IDataset lateral_surface_areaDataset);

	/**
	 * Lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLateral_surface_areaScalar();

	/**
	 * Lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lateral_surface_area the lateral_surface_area
	 */
	public DataNode setLateral_surface_areaScalar(Number lateral_surface_areaValue);

	/**
	 * Area of the upper cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUpper_cap_surface_area();

	/**
	 * Area of the upper cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param upper_cap_surface_areaDataset the upper_cap_surface_areaDataset
	 */
	public DataNode setUpper_cap_surface_area(IDataset upper_cap_surface_areaDataset);

	/**
	 * Area of the upper cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getUpper_cap_surface_areaScalar();

	/**
	 * Area of the upper cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param upper_cap_surface_area the upper_cap_surface_area
	 */
	public DataNode setUpper_cap_surface_areaScalar(Number upper_cap_surface_areaValue);

	/**
	 * Area of the lower cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLower_cap_surface_area();

	/**
	 * Area of the lower cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lower_cap_surface_areaDataset the lower_cap_surface_areaDataset
	 */
	public DataNode setLower_cap_surface_area(IDataset lower_cap_surface_areaDataset);

	/**
	 * Area of the lower cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLower_cap_surface_areaScalar();

	/**
	 * Area of the lower cap of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lower_cap_surface_area the lower_cap_surface_area
	 */
	public DataNode setLower_cap_surface_areaScalar(Number lower_cap_surface_areaValue);

	/**
	 * Sum of upper and lower cap area and lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTotal_surface_area();

	/**
	 * Sum of upper and lower cap area and lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param total_surface_areaDataset the total_surface_areaDataset
	 */
	public DataNode setTotal_surface_area(IDataset total_surface_areaDataset);

	/**
	 * Sum of upper and lower cap area and lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTotal_surface_areaScalar();

	/**
	 * Sum of upper and lower cap area and lateral surface area of each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param total_surface_area the total_surface_area
	 */
	public DataNode setTotal_surface_areaScalar(Number total_surface_areaValue);

}
