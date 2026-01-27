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
 * Base class to store an inverse pole figure (IPF) mapping (IPF map).
 * <p><b>Symbols:</b><ul>
 * <li><b>v</b>
 * Number of pixel along the slow direction used for the IPF color key.</li>
 * <li><b>u</b>
 * Number of pixel along the fast direction used for the IPF color key.</li>
 * <li><b>n_z</b>
 * Number of pixel along the slowest direction, typically labeled z or k.</li>
 * <li><b>n_y</b>
 * Number of pixel along the slow direction, typically labeled y or j.</li>
 * <li><b>n_x</b>
 * Number of pixel along the fast direction, typically labeled x or i.</li>
 * <li><b>n_rgb</b>
 * Number of RGB values along the fastest direction, always three.</li></ul></p>
 *
 */
public interface NXmicrostructure_ipf extends NXprocess {

	public static final String NX_DEPENDS_ON = "depends_on";
	public static final String NX_COLOR_MODEL = "color_model";
	public static final String NX_PROJECTION_DIRECTION = "projection_direction";
	public static final String NX_PROJECTION_DIRECTION_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_INTERPOLATION = "interpolation";
	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which the axes axis_z,
	 * axis_y, and axis_x are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which the axes axis_z,
	 * axis_y, and axis_x are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which the axes axis_z,
	 * axis_y, and axis_x are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which the axes axis_z,
	 * axis_y, and axis_x are defined.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * The algorithm whereby orientations are colored.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>tsl</b> </li>
	 * <li><b>mtex</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getColor_model();

	/**
	 * The algorithm whereby orientations are colored.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>tsl</b> </li>
	 * <li><b>mtex</b> </li></ul></p>
	 * </p>
	 *
	 * @param color_modelDataset the color_modelDataset
	 */
	public DataNode setColor_model(IDataset color_modelDataset);

	/**
	 * The algorithm whereby orientations are colored.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>tsl</b> </li>
	 * <li><b>mtex</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getColor_modelScalar();

	/**
	 * The algorithm whereby orientations are colored.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>tsl</b> </li>
	 * <li><b>mtex</b> </li></ul></p>
	 * </p>
	 *
	 * @param color_model the color_model
	 */
	public DataNode setColor_modelScalar(String color_modelValue);

	/**
	 * The direction normal vector along which orientations are projected.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getProjection_direction();

	/**
	 * The direction normal vector along which orientations are projected.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param projection_directionDataset the projection_directionDataset
	 */
	public DataNode setProjection_direction(IDataset projection_directionDataset);

	/**
	 * The direction normal vector along which orientations are projected.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getProjection_directionScalar();

	/**
	 * The direction normal vector along which orientations are projected.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param projection_direction the projection_direction
	 */
	public DataNode setProjection_directionScalar(Number projection_directionValue);

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which the projection_direction is defined.
	 * If the field depends_on is not provided but parents of the instance of this base class or its
	 * specializations define an instance of :ref:`NXcoordinate_system`, projection_direction
	 * is defined in this coordinate system.
	 * If nothing is provided, it is assumed that projection_direction is defined in the McStas coordinate system.
	 *
	 * @return  the value.
	 */
	public String getProjection_directionAttributeDepends_on();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` in which the projection_direction is defined.
	 * If the field depends_on is not provided but parents of the instance of this base class or its
	 * specializations define an instance of :ref:`NXcoordinate_system`, projection_direction
	 * is defined in this coordinate system.
	 * If nothing is provided, it is assumed that projection_direction is defined in the McStas coordinate system.
	 *
	 * @param depends_onValue the depends_onValue
	 */
	public void setProjection_directionAttributeDepends_on(String depends_onValue);

	/**
	 * Details about the original grid, i.e. the grid for which the IPF map was computed
	 * when that IPF map was exported from the tech partner's file format representation.
	 *
	 * @return  the value.
	 */
	public NXcg_grid getInput_grid();

	/**
	 * Details about the original grid, i.e. the grid for which the IPF map was computed
	 * when that IPF map was exported from the tech partner's file format representation.
	 *
	 * @param input_gridGroup the input_gridGroup
	 */
	public void setInput_grid(NXcg_grid input_gridGroup);

	/**
	 * Details about the grid onto which the IPF is recomputed.
	 * Rescaling the visualization of the IPF map may be needed to enable
	 * visualization in specific software tools like H5Web.
	 *
	 * @return  the value.
	 */
	public NXcg_grid getOutput_grid();

	/**
	 * Details about the grid onto which the IPF is recomputed.
	 * Rescaling the visualization of the IPF map may be needed to enable
	 * visualization in specific software tools like H5Web.
	 *
	 * @param output_gridGroup the output_gridGroup
	 */
	public void setOutput_grid(NXcg_grid output_gridGroup);

	/**
	 * How where orientation values at positions of input_grid computed to values on output_grid.
	 * Nearest neighbour means the orientation of the closed (Euclidean distance) grid point of the input_grid was taken.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nearest_neighbour</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInterpolation();

	/**
	 * How where orientation values at positions of input_grid computed to values on output_grid.
	 * Nearest neighbour means the orientation of the closed (Euclidean distance) grid point of the input_grid was taken.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nearest_neighbour</b> </li></ul></p>
	 * </p>
	 *
	 * @param interpolationDataset the interpolationDataset
	 */
	public DataNode setInterpolation(IDataset interpolationDataset);

	/**
	 * How where orientation values at positions of input_grid computed to values on output_grid.
	 * Nearest neighbour means the orientation of the closed (Euclidean distance) grid point of the input_grid was taken.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nearest_neighbour</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getInterpolationScalar();

	/**
	 * How where orientation values at positions of input_grid computed to values on output_grid.
	 * Nearest neighbour means the orientation of the closed (Euclidean distance) grid point of the input_grid was taken.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>nearest_neighbour</b> </li></ul></p>
	 * </p>
	 *
	 * @param interpolation the interpolation
	 */
	public DataNode setInterpolationScalar(String interpolationValue);

	/**
	 * Inverse pole figure mapping.
	 * Instances named phase0 should by definition refer to the null phase notIndexed.
	 * Inspect the definition of :ref:`NXphase` and its field phase_id
	 * for further details.
	 * Details about possible regridding and associated interpolation
	 * during the computation of the IPF map visualization can be stored
	 * using the input_grid, output_grid, and interpolation fields.
	 * The main purpose of this map is to offer a normalized default representation
	 * of the IPF map for consumption by a research data management system (RDMS).
	 *
	 * @return  the value.
	 */
	public NXdata getMap();

	/**
	 * Inverse pole figure mapping.
	 * Instances named phase0 should by definition refer to the null phase notIndexed.
	 * Inspect the definition of :ref:`NXphase` and its field phase_id
	 * for further details.
	 * Details about possible regridding and associated interpolation
	 * during the computation of the IPF map visualization can be stored
	 * using the input_grid, output_grid, and interpolation fields.
	 * The main purpose of this map is to offer a normalized default representation
	 * of the IPF map for consumption by a research data management system (RDMS).
	 *
	 * @param mapGroup the mapGroup
	 */
	public void setMap(NXdata mapGroup);

	/**
	 * The color code which maps color to orientation in the fundamental zone.
	 * For each stereographic standard triangle (SST), i.e. a rendering of the
	 * fundamental zone of the crystal-symmetry-reduced orientation space
	 * SO3, it is possible to define a color model which assigns a color to each
	 * point in the fundamental zone.
	 * Different mapping models are used. These implement (slightly) different
	 * scaling relations. Differences exist across representations of tech partners.
	 * Differences are which base colors of the RGB color model are placed in
	 * which extremal position of the SST and where the white point is located.
	 * For further details see:
	 * * [G. Nolze et al.](https://doi.org/10.1107/S1600576716012942)
	 * * [S. Patala et al.](https://doi.org/10.1016/j.pmatsci.2012.04.002).
	 * Details are implementation-specific and not standardized yet.
	 *
	 * @return  the value.
	 */
	public NXdata getLegend();

	/**
	 * The color code which maps color to orientation in the fundamental zone.
	 * For each stereographic standard triangle (SST), i.e. a rendering of the
	 * fundamental zone of the crystal-symmetry-reduced orientation space
	 * SO3, it is possible to define a color model which assigns a color to each
	 * point in the fundamental zone.
	 * Different mapping models are used. These implement (slightly) different
	 * scaling relations. Differences exist across representations of tech partners.
	 * Differences are which base colors of the RGB color model are placed in
	 * which extremal position of the SST and where the white point is located.
	 * For further details see:
	 * * [G. Nolze et al.](https://doi.org/10.1107/S1600576716012942)
	 * * [S. Patala et al.](https://doi.org/10.1016/j.pmatsci.2012.04.002).
	 * Details are implementation-specific and not standardized yet.
	 *
	 * @param legendGroup the legendGroup
	 */
	public void setLegend(NXdata legendGroup);

}
