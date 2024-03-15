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
 * Subclass of NXprocess to describe post-processing distortion correction.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays<ul>
 * <li><b>nsym</b>
 * Number of symmetry points used for distortion correction</li>
 * <li><b>ndx</b>
 * Number of points of the matrix distortion field (x direction)</li>
 * <li><b>ndy</b>
 * Number of points of the matrix distortion field (y direction)</li></ul></p>
 *
 */
public interface NXdistortion extends NXobject {

	public static final String NX_LAST_PROCESS = "last_process";
	public static final String NX_APPLIED = "applied";
	public static final String NX_SYMMETRY = "symmetry";
	public static final String NX_ORIGINAL_CENTRE = "original_centre";
	public static final String NX_ORIGINAL_POINTS = "original_points";
	public static final String NX_CDEFORM_FIELD = "cdeform_field";
	public static final String NX_RDEFORM_FIELD = "rdeform_field";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getLast_process();

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param last_processDataset the last_processDataset
	 */
	public DataNode setLast_process(IDataset last_processDataset);

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLast_processScalar();

	/**
	 * Indicates the name of the last operation applied in the NXprocess sequence.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param last_process the last_process
	 */
	public DataNode setLast_processScalar(String last_processValue);

	/**
	 * Has the distortion correction been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getApplied();

	/**
	 * Has the distortion correction been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param appliedDataset the appliedDataset
	 */
	public DataNode setApplied(IDataset appliedDataset);

	/**
	 * Has the distortion correction been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getAppliedScalar();

	/**
	 * Has the distortion correction been applied?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param applied the applied
	 */
	public DataNode setAppliedScalar(Boolean appliedValue);

	/**
	 * For `symmetry-guided distortion correction`_,
	 * where a pattern of features is mapped to the regular geometric structure expected
	 * from the symmetry. Here we record the number of elementary symmetry operations.
	 * .. _symmetry-guided distortion correction: https://www.sciencedirect.com/science/article/abs/pii/S0304399118303474?via%3Dihub
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSymmetry();

	/**
	 * For `symmetry-guided distortion correction`_,
	 * where a pattern of features is mapped to the regular geometric structure expected
	 * from the symmetry. Here we record the number of elementary symmetry operations.
	 * .. _symmetry-guided distortion correction: https://www.sciencedirect.com/science/article/abs/pii/S0304399118303474?via%3Dihub
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param symmetryDataset the symmetryDataset
	 */
	public DataNode setSymmetry(IDataset symmetryDataset);

	/**
	 * For `symmetry-guided distortion correction`_,
	 * where a pattern of features is mapped to the regular geometric structure expected
	 * from the symmetry. Here we record the number of elementary symmetry operations.
	 * .. _symmetry-guided distortion correction: https://www.sciencedirect.com/science/article/abs/pii/S0304399118303474?via%3Dihub
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getSymmetryScalar();

	/**
	 * For `symmetry-guided distortion correction`_,
	 * where a pattern of features is mapped to the regular geometric structure expected
	 * from the symmetry. Here we record the number of elementary symmetry operations.
	 * .. _symmetry-guided distortion correction: https://www.sciencedirect.com/science/article/abs/pii/S0304399118303474?via%3Dihub
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param symmetry the symmetry
	 */
	public DataNode setSymmetryScalar(Long symmetryValue);

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * symmetry centre point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOriginal_centre();

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * symmetry centre point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param original_centreDataset the original_centreDataset
	 */
	public DataNode setOriginal_centre(IDataset original_centreDataset);

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * symmetry centre point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOriginal_centreScalar();

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * symmetry centre point.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param original_centre the original_centre
	 */
	public DataNode setOriginal_centreScalar(Double original_centreValue);

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * relevant symmetry points.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: nsym; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOriginal_points();

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * relevant symmetry points.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: nsym; 2: 2;
	 * </p>
	 *
	 * @param original_pointsDataset the original_pointsDataset
	 */
	public DataNode setOriginal_points(IDataset original_pointsDataset);

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * relevant symmetry points.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: nsym; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOriginal_pointsScalar();

	/**
	 * For symmetry-guided distortion correction. Here we record the coordinates of the
	 * relevant symmetry points.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: nsym; 2: 2;
	 * </p>
	 *
	 * @param original_points the original_points
	 */
	public DataNode setOriginal_pointsScalar(Double original_pointsValue);

	/**
	 * Column deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the column information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCdeform_field();

	/**
	 * Column deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the column information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @param cdeform_fieldDataset the cdeform_fieldDataset
	 */
	public DataNode setCdeform_field(IDataset cdeform_fieldDataset);

	/**
	 * Column deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the column information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCdeform_fieldScalar();

	/**
	 * Column deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the column information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @param cdeform_field the cdeform_field
	 */
	public DataNode setCdeform_fieldScalar(Double cdeform_fieldValue);

	/**
	 * Row deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the row information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getRdeform_field();

	/**
	 * Row deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the row information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @param rdeform_fieldDataset the rdeform_fieldDataset
	 */
	public DataNode setRdeform_field(IDataset rdeform_fieldDataset);

	/**
	 * Row deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the row information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRdeform_fieldScalar();

	/**
	 * Row deformation field for general non-rigid distortion corrections. 2D matrix
	 * holding the row information of the mapping of each original coordinate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: ndx; 2: ndy;
	 * </p>
	 *
	 * @param rdeform_field the rdeform_field
	 */
	public DataNode setRdeform_fieldScalar(Double rdeform_fieldValue);

	/**
	 * Description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * Description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of the procedures employed.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

}
