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
 * A set of (eventually directed) edges which connect nodes/vertices of a graph.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_edges</b>
 * The number of edges.</li></ul></p>
 *
 */
public interface NXgraph_edge_set extends NXobject {

	public static final String NX_NUMBER_OF_EDGES = "number_of_edges";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_DIRECTIONALITY = "directionality";
	public static final String NX_NODE_PAIR = "node_pair";
	public static final String NX_IS_A = "is_a";
	public static final String NX_LABEL = "label";
	/**
	 * Total number of edges, counting eventual bidirectional edges only once.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_edges();

	/**
	 * Total number of edges, counting eventual bidirectional edges only once.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_edgesDataset the number_of_edgesDataset
	 */
	public DataNode setNumber_of_edges(IDataset number_of_edgesDataset);

	/**
	 * Total number of edges, counting eventual bidirectional edges only once.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_edgesScalar();

	/**
	 * Total number of edges, counting eventual bidirectional edges only once.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_edges the number_of_edges
	 */
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Integer used to distinguish edges for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier();

	/**
	 * Integer used to distinguish edges for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Integer used to distinguish edges for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Integer used to distinguish edges for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

	/**
	 * Specifier whether each edge is non-directional, one-directional,
	 * or bidirectional. Use the smallest available binary representation
	 * which can store three different states:
	 * * 0 / state 0x00 is non-directional
	 * * 1 / state 0x01 is one-directional
	 * * 2 / state 0x02 is bi-directional
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDirectionality();

	/**
	 * Specifier whether each edge is non-directional, one-directional,
	 * or bidirectional. Use the smallest available binary representation
	 * which can store three different states:
	 * * 0 / state 0x00 is non-directional
	 * * 1 / state 0x01 is one-directional
	 * * 2 / state 0x02 is bi-directional
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @param directionalityDataset the directionalityDataset
	 */
	public DataNode setDirectionality(IDataset directionalityDataset);

	/**
	 * Specifier whether each edge is non-directional, one-directional,
	 * or bidirectional. Use the smallest available binary representation
	 * which can store three different states:
	 * * 0 / state 0x00 is non-directional
	 * * 1 / state 0x01 is one-directional
	 * * 2 / state 0x02 is bi-directional
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDirectionalityScalar();

	/**
	 * Specifier whether each edge is non-directional, one-directional,
	 * or bidirectional. Use the smallest available binary representation
	 * which can store three different states:
	 * * 0 / state 0x00 is non-directional
	 * * 1 / state 0x01 is one-directional
	 * * 2 / state 0x02 is bi-directional
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @param directionality the directionality
	 */
	public DataNode setDirectionalityScalar(Long directionalityValue);

	/**
	 * Pairs of node/vertex identifier. Each pair represents the connection
	 * between two nodes.
	 * In the case that the edge is non- or bi-directional
	 * node identifier should be stored in ascending order is preferred.
	 * In the case of one-directional, for each pair the identifier of the source
	 * node is the first entry in the pair. The identifier of the target is the
	 * second entry in the pair, i.e. the pair encodes the information as
	 * if one traverses the edge from the source node walking to the target node.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNode_pair();

	/**
	 * Pairs of node/vertex identifier. Each pair represents the connection
	 * between two nodes.
	 * In the case that the edge is non- or bi-directional
	 * node identifier should be stored in ascending order is preferred.
	 * In the case of one-directional, for each pair the identifier of the source
	 * node is the first entry in the pair. The identifier of the target is the
	 * second entry in the pair, i.e. the pair encodes the information as
	 * if one traverses the edge from the source node walking to the target node.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges; 2: 2;
	 * </p>
	 *
	 * @param node_pairDataset the node_pairDataset
	 */
	public DataNode setNode_pair(IDataset node_pairDataset);

	/**
	 * Pairs of node/vertex identifier. Each pair represents the connection
	 * between two nodes.
	 * In the case that the edge is non- or bi-directional
	 * node identifier should be stored in ascending order is preferred.
	 * In the case of one-directional, for each pair the identifier of the source
	 * node is the first entry in the pair. The identifier of the target is the
	 * second entry in the pair, i.e. the pair encodes the information as
	 * if one traverses the edge from the source node walking to the target node.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNode_pairScalar();

	/**
	 * Pairs of node/vertex identifier. Each pair represents the connection
	 * between two nodes.
	 * In the case that the edge is non- or bi-directional
	 * node identifier should be stored in ascending order is preferred.
	 * In the case of one-directional, for each pair the identifier of the source
	 * node is the first entry in the pair. The identifier of the target is the
	 * second entry in the pair, i.e. the pair encodes the information as
	 * if one traverses the edge from the source node walking to the target node.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_edges; 2: 2;
	 * </p>
	 *
	 * @param node_pair the node_pair
	 */
	public DataNode setNode_pairScalar(Long node_pairValue);

	/**
	 * A human-readable qualifier which type or e.g. class instance the
	 * edge is an instance of.
	 * <p>
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_a();

	/**
	 * A human-readable qualifier which type or e.g. class instance the
	 * edge is an instance of.
	 * <p>
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_aDataset the is_aDataset
	 */
	public DataNode setIs_a(IDataset is_aDataset);

	/**
	 * A human-readable qualifier which type or e.g. class instance the
	 * edge is an instance of.
	 * <p>
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIs_aScalar();

	/**
	 * A human-readable qualifier which type or e.g. class instance the
	 * edge is an instance of.
	 * <p>
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_a the is_a
	 */
	public DataNode setIs_aScalar(String is_aValue);

	/**
	 * A human-readable label/caption/tag for the edge.
	 * <p>
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLabel();

	/**
	 * A human-readable label/caption/tag for the edge.
	 * <p>
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @param labelDataset the labelDataset
	 */
	public DataNode setLabel(IDataset labelDataset);

	/**
	 * A human-readable label/caption/tag for the edge.
	 * <p>
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLabelScalar();

	/**
	 * A human-readable label/caption/tag for the edge.
	 * <p>
	 * <b>Dimensions:</b> 1: n_edges;
	 * </p>
	 *
	 * @param label the label
	 */
	public DataNode setLabelScalar(String labelValue);

}
