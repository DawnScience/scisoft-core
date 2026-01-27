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

import java.util.Date;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class to describe a microstructure, its structural aspects, associated descriptors, properties.
 * Whether one uses a continuum or atomic scale description of materials, these are always a model only of
 * the true internal structure of a material. Such models are useful as they enable a coarse-graining and
 * categorizing of properties and representational aspects from which measured or simulated descriptions
 * can be correlated with properties, i.e. descriptor values. The base class here can be used to describe
 * the structural aspect of a region-of-interest for a specimen that was investigated or a computer
 * simulation that was performed for a virtual specimen.
 * Specimens experience thermo-chemo-mechanical processing (steps) before characterization. Therefore,
 * the characterized microstructure may not turn out to be the same structure as of the untreated
 * sample from which the region-of-interests on the specimen were sampled.
 * Fields such as time and increment enable a quantification of the spatiotemporal evolution of a materials'
 * structure by using multiple instances of NXmicrostructure. Both measurements and simulation virtually
 * always sample this evolution. Most microscopy techniques characterize only a two-dimensional representation
 * (projection) of the characterized material volume. Often materials are characterized only for specific states
 * or via sampling coarsely in time relative to the timescale at which the physical phenomena take place.
 * This is typically a compromise between the research questions and technical surplus practical limitations.
 * The term microstructural feature covers here crystals and all sorts of crystal defects within the material
 * (interfaces, triple junctions, dislocations, pores, etc.).
 * A key challenge with the description of representations and properties of such microstructural features is that
 * they can be represented and view as features with different dimensionality. Furthermore, combinations of features of
 * different dimensionality are frequently expected to be documented with intuitive naming conventions when
 * flat property lists are used. For these key-value dictionaries often folksonomies are used. These can be based
 * on ad hoc documentation of such dictionaries in the literature and the metadata section of public data repositories.
 * NXmicrostructure is an attempt to standardize these descriptions stronger.
 * For crystals the number of typically used technical terms are smaller than for interfaces or line like defects and
 * junctions of different types of crystal defects. The term grain describes a contiguous region of material that is
 * delineated by interfaces (phase or grain boundaries). With its origin motivated by light optical microscopy though
 * a grain is not necessarily a single crystal but can have an internal structure of defect such as dislocations.
 * In this base class we use the term and respective group crystals though for single crystals and grains.
 * The reason why this is possible is that when e.g. materials engineers talk about grains they inherently assume
 * that the internal structure of these grains can be described with homogenized effective properties.
 * If alternatively the individual structural crystalline or features of this grain should be distinguished
 * it is useful to instantiate these as individual instances of crystals.
 * Grain boundaries and phase boundaries are two main categories of interfaces.
 * A grain boundary delineates two regions with similar crystal structure and phase but different orientation.
 * A grain boundary is thus a homophase interface. By contrast, a heterophase boundary delineates two regions with typically
 * but not necessarily dissimilar crystal structure but a different atomic occupation that justifies to distinguish two
 * phases. There is a substantial variety of interfaces whose distinction was classically based on geometrical arguments
 * but considers that atomic segregation is an equally important structural aspect to consider when classifying grain
 * boundaries. A concise overview on theoretical aspect of and the semantics for characterizing interfaces and their properties
 * is provided in e.g. `W. Bollmann <https://doi.org/10.1007/978-3-642-49173-3>`_ and A. Sutton and R. W. Baluffi,
 * Interfaces in Crystalline Materials, Clarendon Press, ISBN 9780198500612.
 * Also for junctions between crystal defects there is a considerable variety of terms. Junctions are features in
 * three-dimensional Euclidean space even if they are formed maybe only through a monolayer or a pearl chain of atoms.
 * Either way their local atomic and electronic environment is different compared to the situation of an ideal crystal,
 * or the adjoining defects, which gives typically rise to a plethora of configurations of which some yield useful material
 * properties or affect material properties.
 * Like crystals and interfaces, junctions are assumed to represent groups of atoms that have specific descriptor values
 * which are different to other features. Taking an example, a triple junction is practically a three-dimensional defect as its atoms
 * are arranged in three-dimensional space but the characteristics of that defect can often be reduced to a lower-dimensional
 * description such as a triple line or a triple point as the projection of a line. Therefore, different representations can
 * be used to describe the location, shape, and structure of such defect.
 * This base class provides definitions for crystals, grains, interfaces, triple junctions, and quadruple junctions thus covering,
 * volumetric, patch, line, and point like features that can serve as examples for future extension.
 * As different types of crystal defects can interact, there is a substantial number of in principle characterizable and representable
 * objects. Take again a triple line as an example. It is a tubular feature built from three adjoining interfaces. However, dislocations
 * as line defects can interact with triple lines. Therefore, one can also argue that along a triple line there exist dislocation-line-
 * triple-line junctions, likewise dislocations form own junctions.
 * The description took inspiration from `E. E. Underwood <https://doi.org/10.1111/j.1365-2818.1972.tb03709.x>`_
 * and E. E. Underwood's book on Quantitative Stereology published in 1970 to categorize features based on their dimensionality.
 * Indices can be defined either implicitly or explicitly. Indices for implicit indexing are defined
 * on the interval :math:`[index\_offset, index\_offset + cardinality - 1]`. Indices can be used as identifiers
 * for distinguishing instances, i.e. indices are equivalent to instance names of individual crystals.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_c</b>
 * The number of crystals or their projections</li>
 * <li><b>n_i</b>
 * The number of interfaces or their projections</li>
 * <li><b>n_tj</b>
 * The number of triple junctions or their projections</li>
 * <li><b>n_qj</b>
 * The number of quadruple junctions or their projections</li>
 * <li><b>n_c_one</b>
 * The number of one-dimensional crystal projections</li>
 * <li><b>n_i_one</b>
 * The number of one-dimensional interface projections</li>
 * <li><b>n_c_two</b>
 * The number of two-dimensional crystal projections</li>
 * <li><b>n_i_two</b>
 * The number of two-dimensional interface projections</li>
 * <li><b>n_tj_two</b>
 * The number of two-dimensional triple line projections</li>
 * <li><b>n_ld_two</b>
 * The number of two-dimensional line defect projections</li>
 * <li><b>n_c_three</b>
 * The number of crystals (grain and sub-grain are exact synonyms for crystal).</li>
 * <li><b>n_i_three</b>
 * The number of interfaces (grain boundary and phase boundary are subclasses of
 * interfaces).</li>
 * <li><b>n_tj_three</b>
 * The number of triple junctions (triple line is a exact synonym for triple
 * junction, triple point is projection of a triple junction).</li>
 * <li><b>n_qj_three</b>
 * The number of quadruple junctions.</li>
 * <li><b>d</b>
 * The dimensionality of the representation that needs to match the value for
 * configuration/dimensionality</li></ul></p>
 *
 */
public interface NXmicrostructure extends NXobject {

	public static final String NX_COMMENT = "comment";
	public static final String NX_DATE = "date";
	public static final String NX_TIME = "time";
	public static final String NX_ITERATION = "iteration";
	/**
	 * Discouraged free-text field for leaving comments
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getComment();

	/**
	 * Discouraged free-text field for leaving comments
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param commentDataset the commentDataset
	 */
	public DataNode setComment(IDataset commentDataset);

	/**
	 * Discouraged free-text field for leaving comments
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCommentScalar();

	/**
	 * Discouraged free-text field for leaving comments
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param comment the comment
	 */
	public DataNode setCommentScalar(String commentValue);

	/**
	 * ISO8601 with offset to local time zone included when a timestamp is required.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDate();

	/**
	 * ISO8601 with offset to local time zone included when a timestamp is required.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param dateDataset the dateDataset
	 */
	public DataNode setDate(IDataset dateDataset);

	/**
	 * ISO8601 with offset to local time zone included when a timestamp is required.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getDateScalar();

	/**
	 * ISO8601 with offset to local time zone included when a timestamp is required.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param date the date
	 */
	public DataNode setDateScalar(Date dateValue);

	/**
	 * Measured or simulated physical time stamp for this microstructure snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTime();

	/**
	 * Measured or simulated physical time stamp for this microstructure snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param timeDataset the timeDataset
	 */
	public DataNode setTime(IDataset timeDataset);

	/**
	 * Measured or simulated physical time stamp for this microstructure snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTimeScalar();

	/**
	 * Measured or simulated physical time stamp for this microstructure snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param time the time
	 */
	public DataNode setTimeScalar(Number timeValue);

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIteration();

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param iterationDataset the iterationDataset
	 */
	public DataNode setIteration(IDataset iterationDataset);

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIterationScalar();

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param iteration the iteration
	 */
	public DataNode setIterationScalar(Long iterationValue);

	/**
	 * Group where to store details about the configuration and parameterization of algorithms
	 * used whereby microstructural features were identified.
	 *
	 * @return  the value.
	 */
	public NXprocess getConfiguration();

	/**
	 * Group where to store details about the configuration and parameterization of algorithms
	 * used whereby microstructural features were identified.
	 *
	 * @param configurationGroup the configurationGroup
	 */
	public void setConfiguration(NXprocess configurationGroup);
	// Unprocessed group:

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_grid getCg_grid();

	/**
	 *
	 * @param cg_gridGroup the cg_gridGroup
	 */
	public void setCg_grid(NXcg_grid cg_gridGroup);

	/**
	 * Get a NXcg_grid node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_grid for that node.
	 */
	public NXcg_grid getCg_grid(String name);

	/**
	 * Set a NXcg_grid node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_grid the value to set
	 */
	public void setCg_grid(String name, NXcg_grid cg_grid);

	/**
	 * Get all NXcg_grid nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_grid for that node.
	 */
	public Map<String, NXcg_grid> getAllCg_grid();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_grid the child nodes to add
	 */

	public void setAllCg_grid(Map<String, NXcg_grid> cg_grid);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_point getCg_point();

	/**
	 *
	 * @param cg_pointGroup the cg_pointGroup
	 */
	public void setCg_point(NXcg_point cg_pointGroup);

	/**
	 * Get a NXcg_point node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_point for that node.
	 */
	public NXcg_point getCg_point(String name);

	/**
	 * Set a NXcg_point node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_point the value to set
	 */
	public void setCg_point(String name, NXcg_point cg_point);

	/**
	 * Get all NXcg_point nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_point for that node.
	 */
	public Map<String, NXcg_point> getAllCg_point();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_point the child nodes to add
	 */

	public void setAllCg_point(Map<String, NXcg_point> cg_point);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_polyline getCg_polyline();

	/**
	 *
	 * @param cg_polylineGroup the cg_polylineGroup
	 */
	public void setCg_polyline(NXcg_polyline cg_polylineGroup);

	/**
	 * Get a NXcg_polyline node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_polyline for that node.
	 */
	public NXcg_polyline getCg_polyline(String name);

	/**
	 * Set a NXcg_polyline node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_polyline the value to set
	 */
	public void setCg_polyline(String name, NXcg_polyline cg_polyline);

	/**
	 * Get all NXcg_polyline nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_polyline for that node.
	 */
	public Map<String, NXcg_polyline> getAllCg_polyline();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_polyline the child nodes to add
	 */

	public void setAllCg_polyline(Map<String, NXcg_polyline> cg_polyline);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_triangle getCg_triangle();

	/**
	 *
	 * @param cg_triangleGroup the cg_triangleGroup
	 */
	public void setCg_triangle(NXcg_triangle cg_triangleGroup);

	/**
	 * Get a NXcg_triangle node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_triangle for that node.
	 */
	public NXcg_triangle getCg_triangle(String name);

	/**
	 * Set a NXcg_triangle node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_triangle the value to set
	 */
	public void setCg_triangle(String name, NXcg_triangle cg_triangle);

	/**
	 * Get all NXcg_triangle nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_triangle for that node.
	 */
	public Map<String, NXcg_triangle> getAllCg_triangle();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_triangle the child nodes to add
	 */

	public void setAllCg_triangle(Map<String, NXcg_triangle> cg_triangle);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_polygon getCg_polygon();

	/**
	 *
	 * @param cg_polygonGroup the cg_polygonGroup
	 */
	public void setCg_polygon(NXcg_polygon cg_polygonGroup);

	/**
	 * Get a NXcg_polygon node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_polygon for that node.
	 */
	public NXcg_polygon getCg_polygon(String name);

	/**
	 * Set a NXcg_polygon node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_polygon the value to set
	 */
	public void setCg_polygon(String name, NXcg_polygon cg_polygon);

	/**
	 * Get all NXcg_polygon nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_polygon for that node.
	 */
	public Map<String, NXcg_polygon> getAllCg_polygon();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_polygon the child nodes to add
	 */

	public void setAllCg_polygon(Map<String, NXcg_polygon> cg_polygon);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_polyhedron getCg_polyhedron();

	/**
	 *
	 * @param cg_polyhedronGroup the cg_polyhedronGroup
	 */
	public void setCg_polyhedron(NXcg_polyhedron cg_polyhedronGroup);

	/**
	 * Get a NXcg_polyhedron node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_polyhedron for that node.
	 */
	public NXcg_polyhedron getCg_polyhedron(String name);

	/**
	 * Set a NXcg_polyhedron node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_polyhedron the value to set
	 */
	public void setCg_polyhedron(String name, NXcg_polyhedron cg_polyhedron);

	/**
	 * Get all NXcg_polyhedron nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_polyhedron for that node.
	 */
	public Map<String, NXcg_polyhedron> getAllCg_polyhedron();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_polyhedron the child nodes to add
	 */

	public void setAllCg_polyhedron(Map<String, NXcg_polyhedron> cg_polyhedron);


	/**
	 * The chemical composition of this microstructure (region).
	 *
	 * @return  the value.
	 */
	public NXchemical_composition getChemical_composition();

	/**
	 * The chemical composition of this microstructure (region).
	 *
	 * @param chemical_compositionGroup the chemical_compositionGroup
	 */
	public void setChemical_composition(NXchemical_composition chemical_compositionGroup);

	/**
	 * Different (thermodynamic) phases can be distinguished for the region-of-
	 * interest.
	 *
	 * @return  the value.
	 */
	public NXmicrostructure_feature getPhases();

	/**
	 * Different (thermodynamic) phases can be distinguished for the region-of-
	 * interest.
	 *
	 * @param phasesGroup the phasesGroup
	 */
	public void setPhases(NXmicrostructure_feature phasesGroup);
	// Unprocessed group:

	/**
	 * One- or two-dimensional projections, or three-dimensional representations of crystals.
	 * An example for a volume bounded by other crystal defects. Crystals can be grains of
	 * different phases, precipitates, dispersoids; there are many terms used specifically in
	 * the materials engineering community.
	 * Typically, crystals are measured on the surface of a sample via optical or electron microscopy.
	 * Using X-ray diffraction methods crystals can be observed in bulk specimens.
	 * Crystals are represented by a set of pixel, voxel, or polygons and their polyline boundaries.
	 * In rare cases the volume bounded gets represented using constructive solid geometry approaches.
	 *
	 * @return  the value.
	 */
	public NXmicrostructure_feature getCrystals();

	/**
	 * One- or two-dimensional projections, or three-dimensional representations of crystals.
	 * An example for a volume bounded by other crystal defects. Crystals can be grains of
	 * different phases, precipitates, dispersoids; there are many terms used specifically in
	 * the materials engineering community.
	 * Typically, crystals are measured on the surface of a sample via optical or electron microscopy.
	 * Using X-ray diffraction methods crystals can be observed in bulk specimens.
	 * Crystals are represented by a set of pixel, voxel, or polygons and their polyline boundaries.
	 * In rare cases the volume bounded gets represented using constructive solid geometry approaches.
	 *
	 * @param crystalsGroup the crystalsGroup
	 */
	public void setCrystals(NXmicrostructure_feature crystalsGroup);
	// Unprocessed group:orientation

	/**
	 * One- or two-dimensional projections or three-dimensional representation of interfaces
	 * between crystals as topological entities equivalent to dual_junctions.
	 * An example for a surface defect. Most important are interfaces such as grain and phase boundaries
	 * but factually interfaces also exist between the environment and crystals exposed at the
	 * surface of the specimen or internal surfaces like between crystals, cracks, or pores.
	 * Interfaces are typically reported as discretized features. For interface projections on the 2D plane
	 * these are most frequently polyline segments. For interface patches in 3D these are most frequently
	 * triangulations. Descriptions with continuous functions are seldom used unless simplified configurations
	 * are studied in modeling and theoretical studies.
	 * When using discretizations the individual interface segments need to be distinguished from the interfaces
	 * themselves. Consequently, there are two sets of indices.
	 *
	 * @return  the value.
	 */
	public NXmicrostructure_feature getInterfaces();

	/**
	 * One- or two-dimensional projections or three-dimensional representation of interfaces
	 * between crystals as topological entities equivalent to dual_junctions.
	 * An example for a surface defect. Most important are interfaces such as grain and phase boundaries
	 * but factually interfaces also exist between the environment and crystals exposed at the
	 * surface of the specimen or internal surfaces like between crystals, cracks, or pores.
	 * Interfaces are typically reported as discretized features. For interface projections on the 2D plane
	 * these are most frequently polyline segments. For interface patches in 3D these are most frequently
	 * triangulations. Descriptions with continuous functions are seldom used unless simplified configurations
	 * are studied in modeling and theoretical studies.
	 * When using discretizations the individual interface segments need to be distinguished from the interfaces
	 * themselves. Consequently, there are two sets of indices.
	 *
	 * @param interfacesGroup the interfacesGroup
	 */
	public void setInterfaces(NXmicrostructure_feature interfacesGroup);

	/**
	 * Projections or representations of junctions at which three interfaces meet.
	 * An example for a line defect. Triple junctions are characterized as triple lines or triple points as their projections,
	 * or junctions observed between crystals (at the specimen surface exposed to an environment)
	 * (including wetting phenomena) or inside the specimen (crack, pores).
	 *
	 * @return  the value.
	 */
	public NXmicrostructure_feature getTriple_junctions();

	/**
	 * Projections or representations of junctions at which three interfaces meet.
	 * An example for a line defect. Triple junctions are characterized as triple lines or triple points as their projections,
	 * or junctions observed between crystals (at the specimen surface exposed to an environment)
	 * (including wetting phenomena) or inside the specimen (crack, pores).
	 *
	 * @param triple_junctionsGroup the triple_junctionsGroup
	 */
	public void setTriple_junctions(NXmicrostructure_feature triple_junctionsGroup);

	/**
	 * Quadruple junctions as a region where four crystals meet.
	 * An example for a point (like) defect.
	 * Thermodynamically such junctions can be unstable.
	 * Specifically when discretizations are used in simulations
	 * that do not address the thermodynamics of and splitting characteristics
	 * of junctions in cases when more than four crystals meet, it is possible
	 * that so-called higher-order junctions are observed.
	 *
	 * @return  the value.
	 */
	public NXmicrostructure_feature getQuadruple_junctions();

	/**
	 * Quadruple junctions as a region where four crystals meet.
	 * An example for a point (like) defect.
	 * Thermodynamically such junctions can be unstable.
	 * Specifically when discretizations are used in simulations
	 * that do not address the thermodynamics of and splitting characteristics
	 * of junctions in cases when more than four crystals meet, it is possible
	 * that so-called higher-order junctions are observed.
	 *
	 * @param quadruple_junctionsGroup the quadruple_junctionsGroup
	 */
	public void setQuadruple_junctions(NXmicrostructure_feature quadruple_junctionsGroup);

}
