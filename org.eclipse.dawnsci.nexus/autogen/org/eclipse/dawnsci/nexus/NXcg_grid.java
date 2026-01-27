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
 * Computational geometry description of a grid of Wigner-Seitz cells in Euclidean space.
 * Three-dimensional grids with cubic cells are if not the most frequently used
 * example of such grids. Numerical methods and models that use grids are used
 * in many cases in the natural sciences and engineering disciplines. Examples are
 * discretizations in space and time used for phase-field, cellular automata, or Monte Carlo
 * modeling.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the grid.</li>
 * <li><b>c</b>
 * The cardinality or total number of cells aka grid points.</li>
 * <li><b>n_b</b>
 * Number of boundaries of the bounding box or primitive housing the grid.</li></ul></p>
 *
 */
public interface NXcg_grid extends NXcg_primitive {

	public static final String NX_ORIGIN = "origin";
	public static final String NX_SYMMETRY = "symmetry";
	public static final String NX_CELL_DIMENSIONS = "cell_dimensions";
	public static final String NX_EXTENT = "extent";
	public static final String NX_POSITION = "position";
	public static final String NX_COORDINATE = "coordinate";
	public static final String NX_NUMBER_OF_BOUNDARIES = "number_of_boundaries";
	public static final String NX_BOUNDARIES = "boundaries";
	public static final String NX_BOUNDARY_CONDITIONS = "boundary_conditions";
	public static final String NX_SURFACE_RECONSTRUCTION = "surface_reconstruction";
	/**
	 * Location of the origin of the grid.
	 * Use the depends_on field that is inherited from the :ref:`NXcg_primitive`
	 * class to specify the coordinate system in which the origin location is defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrigin();

	/**
	 * Location of the origin of the grid.
	 * Use the depends_on field that is inherited from the :ref:`NXcg_primitive`
	 * class to specify the coordinate system in which the origin location is defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param originDataset the originDataset
	 */
	public DataNode setOrigin(IDataset originDataset);

	/**
	 * Location of the origin of the grid.
	 * Use the depends_on field that is inherited from the :ref:`NXcg_primitive`
	 * class to specify the coordinate system in which the origin location is defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOriginScalar();

	/**
	 * Location of the origin of the grid.
	 * Use the depends_on field that is inherited from the :ref:`NXcg_primitive`
	 * class to specify the coordinate system in which the origin location is defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param origin the origin
	 */
	public DataNode setOriginScalar(Number originValue);

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSymmetry();

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param symmetryDataset the symmetryDataset
	 */
	public DataNode setSymmetry(IDataset symmetryDataset);

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSymmetryScalar();

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param symmetry the symmetry
	 */
	public DataNode setSymmetryScalar(String symmetryValue);

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCell_dimensions();

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param cell_dimensionsDataset the cell_dimensionsDataset
	 */
	public DataNode setCell_dimensions(IDataset cell_dimensionsDataset);

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCell_dimensionsScalar();

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param cell_dimensions the cell_dimensions
	 */
	public DataNode setCell_dimensionsScalar(Number cell_dimensionsValue);

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead, use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getExtent();

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead, use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param extentDataset the extentDataset
	 */
	public DataNode setExtent(IDataset extentDataset);

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead, use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getExtentScalar();

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead, use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param extent the extent
	 */
	public DataNode setExtentScalar(Long extentValue);

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPosition();

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPositionScalar();

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Number positionValue);

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCoordinate();

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param coordinateDataset the coordinateDataset
	 */
	public DataNode setCoordinate(IDataset coordinateDataset);

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCoordinateScalar();

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param coordinate the coordinate
	 */
	public DataNode setCoordinateScalar(Long coordinateValue);

	/**
	 * A tight bounding box about the grid.
	 *
	 * @return  the value.
	 */
	public NXcg_polyhedron getBounding_box();

	/**
	 * A tight bounding box about the grid.
	 *
	 * @param bounding_boxGroup the bounding_boxGroup
	 */
	public void setBounding_box(NXcg_polyhedron bounding_boxGroup);

	/**
	 * Number of boundaries distinguished
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_boundaries();

	/**
	 * Number of boundaries distinguished
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_boundariesDataset the number_of_boundariesDataset
	 */
	public DataNode setNumber_of_boundaries(IDataset number_of_boundariesDataset);

	/**
	 * Number of boundaries distinguished
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_boundariesScalar();

	/**
	 * Number of boundaries distinguished
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_boundaries the number_of_boundaries
	 */
	public DataNode setNumber_of_boundariesScalar(Long number_of_boundariesValue);

	/**
	 * Name of domain boundaries of the simulation box/ROI
	 * e.g. left, right, front, back, bottom, top.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBoundaries();

	/**
	 * Name of domain boundaries of the simulation box/ROI
	 * e.g. left, right, front, back, bottom, top.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundariesDataset the boundariesDataset
	 */
	public DataNode setBoundaries(IDataset boundariesDataset);

	/**
	 * Name of domain boundaries of the simulation box/ROI
	 * e.g. left, right, front, back, bottom, top.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getBoundariesScalar();

	/**
	 * Name of domain boundaries of the simulation box/ROI
	 * e.g. left, right, front, back, bottom, top.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundaries the boundaries
	 */
	public DataNode setBoundariesScalar(String boundariesValue);

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBoundary_conditions();

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundary_conditionsDataset the boundary_conditionsDataset
	 */
	public DataNode setBoundary_conditions(IDataset boundary_conditionsDataset);

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getBoundary_conditionsScalar();

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundary_conditions the boundary_conditions
	 */
	public DataNode setBoundary_conditionsScalar(Long boundary_conditionsValue);

	/**
	 * Details about the computational geometry method and implementation
	 * used for discretizing internal surfaces as e.g. obtained with marching methods,
	 * like marching squares or marching cubes.
	 * Documenting which specific version was used helps with understanding how
	 * robust the results are with respect to the topology of the triangulation.
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should ideally be an identifier of a program.
	 * If not possible, an identifier for a paper, technical report, or free-text
	 * description can be used instead.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSurface_reconstruction();

	/**
	 * Details about the computational geometry method and implementation
	 * used for discretizing internal surfaces as e.g. obtained with marching methods,
	 * like marching squares or marching cubes.
	 * Documenting which specific version was used helps with understanding how
	 * robust the results are with respect to the topology of the triangulation.
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should ideally be an identifier of a program.
	 * If not possible, an identifier for a paper, technical report, or free-text
	 * description can be used instead.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param surface_reconstructionDataset the surface_reconstructionDataset
	 */
	public DataNode setSurface_reconstruction(IDataset surface_reconstructionDataset);

	/**
	 * Details about the computational geometry method and implementation
	 * used for discretizing internal surfaces as e.g. obtained with marching methods,
	 * like marching squares or marching cubes.
	 * Documenting which specific version was used helps with understanding how
	 * robust the results are with respect to the topology of the triangulation.
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should ideally be an identifier of a program.
	 * If not possible, an identifier for a paper, technical report, or free-text
	 * description can be used instead.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSurface_reconstructionScalar();

	/**
	 * Details about the computational geometry method and implementation
	 * used for discretizing internal surfaces as e.g. obtained with marching methods,
	 * like marching squares or marching cubes.
	 * Documenting which specific version was used helps with understanding how
	 * robust the results are with respect to the topology of the triangulation.
	 * Reference to the specific implementation of marching cubes used.
	 * See for example the following papers for details about how to identify a
	 * DOI which specifies the implementation used:
	 * * `W. E. Lorensen <https://doi.org/10.1109/MCG.2020.2971284>`_
	 * * `T. S. Newman and H. Yi <https://doi.org/10.1016/j.cag.2006.07.021>`_
	 * The value placed here should ideally be an identifier of a program.
	 * If not possible, an identifier for a paper, technical report, or free-text
	 * description can be used instead.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param surface_reconstruction the surface_reconstruction
	 */
	public DataNode setSurface_reconstructionScalar(String surface_reconstructionValue);

}
