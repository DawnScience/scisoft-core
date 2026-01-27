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
 * Contains data structures of an experimental optical setup (i.e., multiple
 * transfer matrix tables). These data structures are used to relate physical
 * properties of two beams (NXbeam) which have one common optical component
 * (NXcomponent) (one specific transfer matrix).
 * One of these beams is an input beam and the other one is an output beam.
 * The data describes the change of beam properties, e.g. the intensity of a
 * beam is reduced because the transmission coefficient of the beam device is
 * lower than 1.
 * <p><b>Symbols:</b>
 * Variables used throughout the document, e.g. dimensions or parameters.<ul>
 * <li><b>N_variables</b>
 * Length of the array associated to the data type.</li></ul></p>
 *
 */
public interface NXbeam_transfer_matrix_table extends NXobject {

	public static final String NX_DATATYPE_N = "datatype_n";
	public static final String NX_MATRIX_ELEMENTS = "matrix_elements";
	public static final String NX_MATRIX_SUFFIX = "_matrix";
	public static final String NX_MATRIX_SUFFIX_ATTRIBUTE_INPUT = "input";
	public static final String NX_MATRIX_SUFFIX_ATTRIBUTE_OUTPUT = "output";
	/**
	 * Select which type of data was recorded, for example aperture and
	 * focal length.
	 * It is possible to have multiple selections. This selection defines
	 * how many columns (N_variables) are stored in the data array.
	 * N in the name, is the index number in which order the given
	 * property is listed.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>aperture</b> </li>
	 * <li><b>focal length</b> </li>
	 * <li><b>orientation</b> </li>
	 * <li><b>jones matrix</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDatatype_n();

	/**
	 * Select which type of data was recorded, for example aperture and
	 * focal length.
	 * It is possible to have multiple selections. This selection defines
	 * how many columns (N_variables) are stored in the data array.
	 * N in the name, is the index number in which order the given
	 * property is listed.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>aperture</b> </li>
	 * <li><b>focal length</b> </li>
	 * <li><b>orientation</b> </li>
	 * <li><b>jones matrix</b> </li></ul></p>
	 * </p>
	 *
	 * @param datatype_nDataset the datatype_nDataset
	 */
	public DataNode setDatatype_n(IDataset datatype_nDataset);

	/**
	 * Select which type of data was recorded, for example aperture and
	 * focal length.
	 * It is possible to have multiple selections. This selection defines
	 * how many columns (N_variables) are stored in the data array.
	 * N in the name, is the index number in which order the given
	 * property is listed.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>aperture</b> </li>
	 * <li><b>focal length</b> </li>
	 * <li><b>orientation</b> </li>
	 * <li><b>jones matrix</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDatatype_nScalar();

	/**
	 * Select which type of data was recorded, for example aperture and
	 * focal length.
	 * It is possible to have multiple selections. This selection defines
	 * how many columns (N_variables) are stored in the data array.
	 * N in the name, is the index number in which order the given
	 * property is listed.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>aperture</b> </li>
	 * <li><b>focal length</b> </li>
	 * <li><b>orientation</b> </li>
	 * <li><b>jones matrix</b> </li></ul></p>
	 * </p>
	 *
	 * @param datatype_n the datatype_n
	 */
	public DataNode setDatatype_nScalar(String datatype_nValue);

	/**
	 * Please list in this array the column and row names used in your actual data.
	 * That is in the case of aperture ['diameter'] or focal length ['focal_length_value']
	 * and for orientation matrix ['OM1', 'OM2', 'OM3'] or for jones matrix
	 * ['JM1','JM2']
	 * <p>
	 * <b>Dimensions:</b> 1: N_variables;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMatrix_elements();

	/**
	 * Please list in this array the column and row names used in your actual data.
	 * That is in the case of aperture ['diameter'] or focal length ['focal_length_value']
	 * and for orientation matrix ['OM1', 'OM2', 'OM3'] or for jones matrix
	 * ['JM1','JM2']
	 * <p>
	 * <b>Dimensions:</b> 1: N_variables;
	 * </p>
	 *
	 * @param matrix_elementsDataset the matrix_elementsDataset
	 */
	public DataNode setMatrix_elements(IDataset matrix_elementsDataset);

	/**
	 * Please list in this array the column and row names used in your actual data.
	 * That is in the case of aperture ['diameter'] or focal length ['focal_length_value']
	 * and for orientation matrix ['OM1', 'OM2', 'OM3'] or for jones matrix
	 * ['JM1','JM2']
	 * <p>
	 * <b>Dimensions:</b> 1: N_variables;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMatrix_elementsScalar();

	/**
	 * Please list in this array the column and row names used in your actual data.
	 * That is in the case of aperture ['diameter'] or focal length ['focal_length_value']
	 * and for orientation matrix ['OM1', 'OM2', 'OM3'] or for jones matrix
	 * ['JM1','JM2']
	 * <p>
	 * <b>Dimensions:</b> 1: N_variables;
	 * </p>
	 *
	 * @param matrix_elements the matrix_elements
	 */
	public DataNode setMatrix_elementsScalar(String matrix_elementsValue);

	/**
	 * Contains the datastructure which relates beam properties of an
	 * input and output beam as result of the input beam interaction
	 * with the beam device.
	 * Transfer matrix relationship between N input beams and M output beams.
	 * It contains a table with the relevant matrices to be used for different
	 * transmitted properties (such as polarization, intensity, phase).
	 * Data structure for all transfermatrices of a beam device in a setup.
	 * For each combination of N input and M output beams and for L physical
	 * concept (i.e. beam intensity), one matrix can be defined.
	 * In this way, the transfer matrix table has the dimension NxM.
	 * For each entry, in this transfer matrix, there are L formalisms.
	 * Each formalism has the dimension math:`dim(L_i)xdim(L_i)`,
	 * whereby math:`L_i` is the specific physical concept (Intensity, polarization, direction).
	 * A beamsplitter with two input laser beams can have a total of
	 * four transfermatrices (2 Input x 2 Output).
	 * The dimension of the transfer matrix depends on the parameters.
	 * Examples are:
	 * 1x1 for intensity/power
	 * 2x2 for jones formalism
	 * 3x3 for direction
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_variables; 2: N_variables;
	 * </p>
	 *
	 * @param transfer the transfer
	 * @return  the value.
	 */
	public Dataset getMatrix(String transfer);

	/**
	 * Contains the datastructure which relates beam properties of an
	 * input and output beam as result of the input beam interaction
	 * with the beam device.
	 * Transfer matrix relationship between N input beams and M output beams.
	 * It contains a table with the relevant matrices to be used for different
	 * transmitted properties (such as polarization, intensity, phase).
	 * Data structure for all transfermatrices of a beam device in a setup.
	 * For each combination of N input and M output beams and for L physical
	 * concept (i.e. beam intensity), one matrix can be defined.
	 * In this way, the transfer matrix table has the dimension NxM.
	 * For each entry, in this transfer matrix, there are L formalisms.
	 * Each formalism has the dimension math:`dim(L_i)xdim(L_i)`,
	 * whereby math:`L_i` is the specific physical concept (Intensity, polarization, direction).
	 * A beamsplitter with two input laser beams can have a total of
	 * four transfermatrices (2 Input x 2 Output).
	 * The dimension of the transfer matrix depends on the parameters.
	 * Examples are:
	 * 1x1 for intensity/power
	 * 2x2 for jones formalism
	 * 3x3 for direction
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_variables; 2: N_variables;
	 * </p>
	 *
	 * @param transfer the transfer
	 * @param matrixDataset the matrixDataset
	 */
	public DataNode setMatrix(String transfer, IDataset matrixDataset);

	/**
	 * Contains the datastructure which relates beam properties of an
	 * input and output beam as result of the input beam interaction
	 * with the beam device.
	 * Transfer matrix relationship between N input beams and M output beams.
	 * It contains a table with the relevant matrices to be used for different
	 * transmitted properties (such as polarization, intensity, phase).
	 * Data structure for all transfermatrices of a beam device in a setup.
	 * For each combination of N input and M output beams and for L physical
	 * concept (i.e. beam intensity), one matrix can be defined.
	 * In this way, the transfer matrix table has the dimension NxM.
	 * For each entry, in this transfer matrix, there are L formalisms.
	 * Each formalism has the dimension math:`dim(L_i)xdim(L_i)`,
	 * whereby math:`L_i` is the specific physical concept (Intensity, polarization, direction).
	 * A beamsplitter with two input laser beams can have a total of
	 * four transfermatrices (2 Input x 2 Output).
	 * The dimension of the transfer matrix depends on the parameters.
	 * Examples are:
	 * 1x1 for intensity/power
	 * 2x2 for jones formalism
	 * 3x3 for direction
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_variables; 2: N_variables;
	 * </p>
	 *
	 * @param transfer the transfer
	 * @return  the value.
	 */
	public Number getMatrixScalar(String transfer);

	/**
	 * Contains the datastructure which relates beam properties of an
	 * input and output beam as result of the input beam interaction
	 * with the beam device.
	 * Transfer matrix relationship between N input beams and M output beams.
	 * It contains a table with the relevant matrices to be used for different
	 * transmitted properties (such as polarization, intensity, phase).
	 * Data structure for all transfermatrices of a beam device in a setup.
	 * For each combination of N input and M output beams and for L physical
	 * concept (i.e. beam intensity), one matrix can be defined.
	 * In this way, the transfer matrix table has the dimension NxM.
	 * For each entry, in this transfer matrix, there are L formalisms.
	 * Each formalism has the dimension math:`dim(L_i)xdim(L_i)`,
	 * whereby math:`L_i` is the specific physical concept (Intensity, polarization, direction).
	 * A beamsplitter with two input laser beams can have a total of
	 * four transfermatrices (2 Input x 2 Output).
	 * The dimension of the transfer matrix depends on the parameters.
	 * Examples are:
	 * 1x1 for intensity/power
	 * 2x2 for jones formalism
	 * 3x3 for direction
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_variables; 2: N_variables;
	 * </p>
	 *
	 * @param transfer the transfer
	 * @param matrix the matrix
	 */
	public DataNode setMatrixScalar(String transfer, Number matrixValue);


	/**
	 * Get all Matrix fields:
	 *
	 * Contains the datastructure which relates beam properties of an
	 * input and output beam as result of the input beam interaction
	 * with the beam device.
	 * Transfer matrix relationship between N input beams and M output beams.
	 * It contains a table with the relevant matrices to be used for different
	 * transmitted properties (such as polarization, intensity, phase).
	 * Data structure for all transfermatrices of a beam device in a setup.
	 * For each combination of N input and M output beams and for L physical
	 * concept (i.e. beam intensity), one matrix can be defined.
	 * In this way, the transfer matrix table has the dimension NxM.
	 * For each entry, in this transfer matrix, there are L formalisms.
	 * Each formalism has the dimension math:`dim(L_i)xdim(L_i)`,
	 * whereby math:`L_i` is the specific physical concept (Intensity, polarization, direction).
	 * A beamsplitter with two input laser beams can have a total of
	 * four transfermatrices (2 Input x 2 Output).
	 * The dimension of the transfer matrix depends on the parameters.
	 * Examples are:
	 * 1x1 for intensity/power
	 * 2x2 for jones formalism
	 * 3x3 for direction
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: N_variables; 2: N_variables;
	 * </p>
	 * <p> <em>Note: this method returns ALL datasets within this group.</em> 
	 *
	 * @return  a map from node names to the ? extends IDataset for that node.
	 */
	public Map<String, ? extends IDataset> getAllMatrix();

	/**
	 * Specific name of input beam which the transfer matrix table is related to.
	 *
	 * @param transfer the transfer
	 * @return  the value.
	 */
	public String getMatrixAttributeInput(String transfer);

	/**
	 * Specific name of input beam which the transfer matrix table is related to.
	 *
	 * @param transfer the transfer
	 * @param inputValue the inputValue
	 */
	public void setMatrixAttributeInput(String transfer, String inputValue);

	/**
	 * Specific name of output beam which the transfer matrix table is related to.
	 *
	 * @param transfer the transfer
	 * @return  the value.
	 */
	public String getMatrixAttributeOutput(String transfer);

	/**
	 * Specific name of output beam which the transfer matrix table is related to.
	 *
	 * @param transfer the transfer
	 * @param outputValue the outputValue
	 */
	public void setMatrixAttributeOutput(String transfer, String outputValue);

}
