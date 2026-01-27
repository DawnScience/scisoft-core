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

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Computational geometry of alpha complexes (alpha shapes or alpha wrappings) about primitives.
 * For details see:
 * * https://dx.doi.org/10.1109/TIT.1983.1056714 for 2D,
 * * https://dx.doi.org/10.1145/174462.156635 for 3D,
 * * https://dl.acm.org/doi/10.5555/871114 for weighted, and
 * * https://doc.cgal.org/latest/Alpha_shapes_3 for 3D implementation of alpha shapes, and
 * * https://doc.cgal.org/latest/Manual/packages.html#PkgAlphaWrap3 for 3D alpha wrappings
 * in CGAL, the Computational Geometry Algorithms Library respectively.
 * As a starting point, we follow the conventions of the CGAL library.
 * In general, an alpha complex is a not necessarily connected or not necessarily pure complex,
 * i.e. singular faces may exist. The number of cells, faces, and edges depends on how a specific
 * alpha complex is filtered for lower-dimensional simplices. The fields is_regularized and
 * regularization can be used to provide details about regularization procedures.

 */
public class NXcg_alpha_complexImpl extends NXcg_primitiveImpl implements NXcg_alpha_complex {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_POINT,
		NexusBaseClass.NX_CG_TRIANGLE,
		NexusBaseClass.NX_CG_TRIANGLE,
		NexusBaseClass.NX_CG_TETRAHEDRON);

	public NXcg_alpha_complexImpl() {
		super();
	}

	public NXcg_alpha_complexImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_alpha_complex.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_ALPHA_COMPLEX;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public Dataset getRegularization() {
		return getDataset(NX_REGULARIZATION);
	}

	@Override
	public String getRegularizationScalar() {
		return getString(NX_REGULARIZATION);
	}

	@Override
	public DataNode setRegularization(IDataset regularizationDataset) {
		return setDataset(NX_REGULARIZATION, regularizationDataset);
	}

	@Override
	public DataNode setRegularizationScalar(String regularizationValue) {
		return setString(NX_REGULARIZATION, regularizationValue);
	}

	@Override
	public Dataset getIs_regularized() {
		return getDataset(NX_IS_REGULARIZED);
	}

	@Override
	public Boolean getIs_regularizedScalar() {
		return getBoolean(NX_IS_REGULARIZED);
	}

	@Override
	public DataNode setIs_regularized(IDataset is_regularizedDataset) {
		return setDataset(NX_IS_REGULARIZED, is_regularizedDataset);
	}

	@Override
	public DataNode setIs_regularizedScalar(Boolean is_regularizedValue) {
		return setField(NX_IS_REGULARIZED, is_regularizedValue);
	}

	@Override
	public Dataset getAlpha() {
		return getDataset(NX_ALPHA);
	}

	@Override
	public Number getAlphaScalar() {
		return getNumber(NX_ALPHA);
	}

	@Override
	public DataNode setAlpha(IDataset alphaDataset) {
		return setDataset(NX_ALPHA, alphaDataset);
	}

	@Override
	public DataNode setAlphaScalar(Number alphaValue) {
		return setField(NX_ALPHA, alphaValue);
	}

	@Override
	public Dataset getOffset() {
		return getDataset(NX_OFFSET);
	}

	@Override
	public Number getOffsetScalar() {
		return getNumber(NX_OFFSET);
	}

	@Override
	public DataNode setOffset(IDataset offsetDataset) {
		return setDataset(NX_OFFSET, offsetDataset);
	}

	@Override
	public DataNode setOffsetScalar(Number offsetValue) {
		return setField(NX_OFFSET, offsetValue);
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
	public NXcg_triangle getTriangle_soup() {
		// dataNodeName = NX_TRIANGLE_SOUP
		return getChild("triangle_soup", NXcg_triangle.class);
	}

	@Override
	public void setTriangle_soup(NXcg_triangle triangle_soupGroup) {
		putChild("triangle_soup", triangle_soupGroup);
	}

	@Override
	public NXcg_triangle getAlpha_complex() {
		// dataNodeName = NX_ALPHA_COMPLEX
		return getChild("alpha_complex", NXcg_triangle.class);
	}

	@Override
	public void setAlpha_complex(NXcg_triangle alpha_complexGroup) {
		putChild("alpha_complex", alpha_complexGroup);
	}

	@Override
	public NXcg_tetrahedron getTetrahedralization() {
		// dataNodeName = NX_TETRAHEDRALIZATION
		return getChild("tetrahedralization", NXcg_tetrahedron.class);
	}

	@Override
	public void setTetrahedralization(NXcg_tetrahedron tetrahedralizationGroup) {
		putChild("tetrahedralization", tetrahedralizationGroup);
	}

}
