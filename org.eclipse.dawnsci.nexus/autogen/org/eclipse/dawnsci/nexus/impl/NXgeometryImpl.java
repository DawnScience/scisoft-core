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
 * legacy class - recommend to use :ref:`NXtransformations` now
 * It is recommended that instances of :ref:`NXgeometry` be converted to
 * use :ref:`NXtransformations`.
 * This is the description for a general position of a component.
 * It is recommended to name an instance of :ref:`NXgeometry` as "geometry"
 * to aid in the use of the definition in simulation codes such as McStas.
 * Also, in HDF, linked items must share the same name.
 * However, it might not be possible or practical in all situations.

 * @deprecated as decided at 2014 NIAC meeting, convert to use :ref:`NXtransformations`
 */
@Deprecated
public class NXgeometryImpl extends NXobjectImpl implements NXgeometry {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SHAPE,
		NexusBaseClass.NX_TRANSLATION,
		NexusBaseClass.NX_ORIENTATION);

	public NXgeometryImpl() {
		super();
	}

	public NXgeometryImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXgeometry.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_GEOMETRY;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXshape getShape() {
		// dataNodeName = NX_SHAPE
		return getChild("shape", NXshape.class);
	}

	@Override
	public void setShape(NXshape shapeGroup) {
		putChild("shape", shapeGroup);
	}

	@Override
	public NXshape getShape(String name) {
		return getChild(name, NXshape.class);
	}

	@Override
	public void setShape(String name, NXshape shape) {
		putChild(name, shape);
	}

	@Override
	public Map<String, NXshape> getAllShape() {
		return getChildren(NXshape.class);
	}

	@Override
	public void setAllShape(Map<String, NXshape> shape) {
		setChildren(shape);
	}

	@Override
	public NXtranslation getTranslation() {
		// dataNodeName = NX_TRANSLATION
		return getChild("translation", NXtranslation.class);
	}

	@Override
	public void setTranslation(NXtranslation translationGroup) {
		putChild("translation", translationGroup);
	}

	@Override
	public NXtranslation getTranslation(String name) {
		return getChild(name, NXtranslation.class);
	}

	@Override
	public void setTranslation(String name, NXtranslation translation) {
		putChild(name, translation);
	}

	@Override
	public Map<String, NXtranslation> getAllTranslation() {
		return getChildren(NXtranslation.class);
	}

	@Override
	public void setAllTranslation(Map<String, NXtranslation> translation) {
		setChildren(translation);
	}

	@Override
	public NXorientation getOrientation() {
		// dataNodeName = NX_ORIENTATION
		return getChild("orientation", NXorientation.class);
	}

	@Override
	public void setOrientation(NXorientation orientationGroup) {
		putChild("orientation", orientationGroup);
	}

	@Override
	public NXorientation getOrientation(String name) {
		return getChild(name, NXorientation.class);
	}

	@Override
	public void setOrientation(String name, NXorientation orientation) {
		putChild(name, orientation);
	}

	@Override
	public Map<String, NXorientation> getAllOrientation() {
		return getChildren(NXorientation.class);
	}

	@Override
	public void setAllOrientation(Map<String, NXorientation> orientation) {
		setChildren(orientation);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public Dataset getComponent_index() {
		return getDataset(NX_COMPONENT_INDEX);
	}

	@Override
	public Long getComponent_indexScalar() {
		return getLong(NX_COMPONENT_INDEX);
	}

	@Override
	public DataNode setComponent_index(IDataset component_indexDataset) {
		return setDataset(NX_COMPONENT_INDEX, component_indexDataset);
	}

	@Override
	public DataNode setComponent_indexScalar(Long component_indexValue) {
		return setField(NX_COMPONENT_INDEX, component_indexValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
