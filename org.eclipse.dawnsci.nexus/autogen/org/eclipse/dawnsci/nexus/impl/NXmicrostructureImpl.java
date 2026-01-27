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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXmicrostructureImpl extends NXobjectImpl implements NXmicrostructure {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_CG_GRID,
		NexusBaseClass.NX_CG_POINT,
		NexusBaseClass.NX_CG_POLYLINE,
		NexusBaseClass.NX_CG_TRIANGLE,
		NexusBaseClass.NX_CG_POLYGON,
		NexusBaseClass.NX_CG_POLYHEDRON,
		NexusBaseClass.NX_CHEMICAL_COMPOSITION,
		NexusBaseClass.NX_MICROSTRUCTURE_FEATURE,
		NexusBaseClass.NX_MICROSTRUCTURE_FEATURE,
		NexusBaseClass.NX_MICROSTRUCTURE_FEATURE,
		NexusBaseClass.NX_MICROSTRUCTURE_FEATURE,
		NexusBaseClass.NX_MICROSTRUCTURE_FEATURE);

	public NXmicrostructureImpl() {
		super();
	}

	public NXmicrostructureImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmicrostructure.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MICROSTRUCTURE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getComment() {
		return getDataset(NX_COMMENT);
	}

	@Override
	public String getCommentScalar() {
		return getString(NX_COMMENT);
	}

	@Override
	public DataNode setComment(IDataset commentDataset) {
		return setDataset(NX_COMMENT, commentDataset);
	}

	@Override
	public DataNode setCommentScalar(String commentValue) {
		return setString(NX_COMMENT, commentValue);
	}

	@Override
	public Dataset getDate() {
		return getDataset(NX_DATE);
	}

	@Override
	public Date getDateScalar() {
		return getDate(NX_DATE);
	}

	@Override
	public DataNode setDate(IDataset dateDataset) {
		return setDataset(NX_DATE, dateDataset);
	}

	@Override
	public DataNode setDateScalar(Date dateValue) {
		return setDate(NX_DATE, dateValue);
	}

	@Override
	public Dataset getTime() {
		return getDataset(NX_TIME);
	}

	@Override
	public Number getTimeScalar() {
		return getNumber(NX_TIME);
	}

	@Override
	public DataNode setTime(IDataset timeDataset) {
		return setDataset(NX_TIME, timeDataset);
	}

	@Override
	public DataNode setTimeScalar(Number timeValue) {
		return setField(NX_TIME, timeValue);
	}

	@Override
	public Dataset getIteration() {
		return getDataset(NX_ITERATION);
	}

	@Override
	public Long getIterationScalar() {
		return getLong(NX_ITERATION);
	}

	@Override
	public DataNode setIteration(IDataset iterationDataset) {
		return setDataset(NX_ITERATION, iterationDataset);
	}

	@Override
	public DataNode setIterationScalar(Long iterationValue) {
		return setField(NX_ITERATION, iterationValue);
	}

	@Override
	public NXprocess getConfiguration() {
		// dataNodeName = NX_CONFIGURATION
		return getChild("configuration", NXprocess.class);
	}

	@Override
	public void setConfiguration(NXprocess configurationGroup) {
		putChild("configuration", configurationGroup);
	}
	// Unprocessed group:

	@Override
	public NXcg_grid getCg_grid() {
		// dataNodeName = NX_CG_GRID
		return getChild("cg_grid", NXcg_grid.class);
	}

	@Override
	public void setCg_grid(NXcg_grid cg_gridGroup) {
		putChild("cg_grid", cg_gridGroup);
	}

	@Override
	public NXcg_grid getCg_grid(String name) {
		return getChild(name, NXcg_grid.class);
	}

	@Override
	public void setCg_grid(String name, NXcg_grid cg_grid) {
		putChild(name, cg_grid);
	}

	@Override
	public Map<String, NXcg_grid> getAllCg_grid() {
		return getChildren(NXcg_grid.class);
	}

	@Override
	public void setAllCg_grid(Map<String, NXcg_grid> cg_grid) {
		setChildren(cg_grid);
	}

	@Override
	public NXcg_point getCg_point() {
		// dataNodeName = NX_CG_POINT
		return getChild("cg_point", NXcg_point.class);
	}

	@Override
	public void setCg_point(NXcg_point cg_pointGroup) {
		putChild("cg_point", cg_pointGroup);
	}

	@Override
	public NXcg_point getCg_point(String name) {
		return getChild(name, NXcg_point.class);
	}

	@Override
	public void setCg_point(String name, NXcg_point cg_point) {
		putChild(name, cg_point);
	}

	@Override
	public Map<String, NXcg_point> getAllCg_point() {
		return getChildren(NXcg_point.class);
	}

	@Override
	public void setAllCg_point(Map<String, NXcg_point> cg_point) {
		setChildren(cg_point);
	}

	@Override
	public NXcg_polyline getCg_polyline() {
		// dataNodeName = NX_CG_POLYLINE
		return getChild("cg_polyline", NXcg_polyline.class);
	}

	@Override
	public void setCg_polyline(NXcg_polyline cg_polylineGroup) {
		putChild("cg_polyline", cg_polylineGroup);
	}

	@Override
	public NXcg_polyline getCg_polyline(String name) {
		return getChild(name, NXcg_polyline.class);
	}

	@Override
	public void setCg_polyline(String name, NXcg_polyline cg_polyline) {
		putChild(name, cg_polyline);
	}

	@Override
	public Map<String, NXcg_polyline> getAllCg_polyline() {
		return getChildren(NXcg_polyline.class);
	}

	@Override
	public void setAllCg_polyline(Map<String, NXcg_polyline> cg_polyline) {
		setChildren(cg_polyline);
	}

	@Override
	public NXcg_triangle getCg_triangle() {
		// dataNodeName = NX_CG_TRIANGLE
		return getChild("cg_triangle", NXcg_triangle.class);
	}

	@Override
	public void setCg_triangle(NXcg_triangle cg_triangleGroup) {
		putChild("cg_triangle", cg_triangleGroup);
	}

	@Override
	public NXcg_triangle getCg_triangle(String name) {
		return getChild(name, NXcg_triangle.class);
	}

	@Override
	public void setCg_triangle(String name, NXcg_triangle cg_triangle) {
		putChild(name, cg_triangle);
	}

	@Override
	public Map<String, NXcg_triangle> getAllCg_triangle() {
		return getChildren(NXcg_triangle.class);
	}

	@Override
	public void setAllCg_triangle(Map<String, NXcg_triangle> cg_triangle) {
		setChildren(cg_triangle);
	}

	@Override
	public NXcg_polygon getCg_polygon() {
		// dataNodeName = NX_CG_POLYGON
		return getChild("cg_polygon", NXcg_polygon.class);
	}

	@Override
	public void setCg_polygon(NXcg_polygon cg_polygonGroup) {
		putChild("cg_polygon", cg_polygonGroup);
	}

	@Override
	public NXcg_polygon getCg_polygon(String name) {
		return getChild(name, NXcg_polygon.class);
	}

	@Override
	public void setCg_polygon(String name, NXcg_polygon cg_polygon) {
		putChild(name, cg_polygon);
	}

	@Override
	public Map<String, NXcg_polygon> getAllCg_polygon() {
		return getChildren(NXcg_polygon.class);
	}

	@Override
	public void setAllCg_polygon(Map<String, NXcg_polygon> cg_polygon) {
		setChildren(cg_polygon);
	}

	@Override
	public NXcg_polyhedron getCg_polyhedron() {
		// dataNodeName = NX_CG_POLYHEDRON
		return getChild("cg_polyhedron", NXcg_polyhedron.class);
	}

	@Override
	public void setCg_polyhedron(NXcg_polyhedron cg_polyhedronGroup) {
		putChild("cg_polyhedron", cg_polyhedronGroup);
	}

	@Override
	public NXcg_polyhedron getCg_polyhedron(String name) {
		return getChild(name, NXcg_polyhedron.class);
	}

	@Override
	public void setCg_polyhedron(String name, NXcg_polyhedron cg_polyhedron) {
		putChild(name, cg_polyhedron);
	}

	@Override
	public Map<String, NXcg_polyhedron> getAllCg_polyhedron() {
		return getChildren(NXcg_polyhedron.class);
	}

	@Override
	public void setAllCg_polyhedron(Map<String, NXcg_polyhedron> cg_polyhedron) {
		setChildren(cg_polyhedron);
	}

	@Override
	public NXchemical_composition getChemical_composition() {
		// dataNodeName = NX_CHEMICAL_COMPOSITION
		return getChild("chemical_composition", NXchemical_composition.class);
	}

	@Override
	public void setChemical_composition(NXchemical_composition chemical_compositionGroup) {
		putChild("chemical_composition", chemical_compositionGroup);
	}

	@Override
	public NXmicrostructure_feature getPhases() {
		// dataNodeName = NX_PHASES
		return getChild("phases", NXmicrostructure_feature.class);
	}

	@Override
	public void setPhases(NXmicrostructure_feature phasesGroup) {
		putChild("phases", phasesGroup);
	}
	// Unprocessed group:

	@Override
	public NXmicrostructure_feature getCrystals() {
		// dataNodeName = NX_CRYSTALS
		return getChild("crystals", NXmicrostructure_feature.class);
	}

	@Override
	public void setCrystals(NXmicrostructure_feature crystalsGroup) {
		putChild("crystals", crystalsGroup);
	}
	// Unprocessed group: orientation

	@Override
	public NXmicrostructure_feature getInterfaces() {
		// dataNodeName = NX_INTERFACES
		return getChild("interfaces", NXmicrostructure_feature.class);
	}

	@Override
	public void setInterfaces(NXmicrostructure_feature interfacesGroup) {
		putChild("interfaces", interfacesGroup);
	}

	@Override
	public NXmicrostructure_feature getTriple_junctions() {
		// dataNodeName = NX_TRIPLE_JUNCTIONS
		return getChild("triple_junctions", NXmicrostructure_feature.class);
	}

	@Override
	public void setTriple_junctions(NXmicrostructure_feature triple_junctionsGroup) {
		putChild("triple_junctions", triple_junctionsGroup);
	}

	@Override
	public NXmicrostructure_feature getQuadruple_junctions() {
		// dataNodeName = NX_QUADRUPLE_JUNCTIONS
		return getChild("quadruple_junctions", NXmicrostructure_feature.class);
	}

	@Override
	public void setQuadruple_junctions(NXmicrostructure_feature quadruple_junctionsGroup) {
		putChild("quadruple_junctions", quadruple_junctionsGroup);
	}

}
