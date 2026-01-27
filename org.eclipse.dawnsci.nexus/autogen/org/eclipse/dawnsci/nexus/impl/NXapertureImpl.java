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
 * A beamline aperture.
 * Note, the group was incorrectly documented as deprecated, but it is not and it is in common use.
 * You can specify the geometry of the aperture using either NXoff_geometry or for simpler geometry shape and size.

 */
public class NXapertureImpl extends NXcomponentImpl implements NXaperture {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_POSITIONER,
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_NOTE);

	public NXapertureImpl() {
		super();
	}

	public NXapertureImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXaperture.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APERTURE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXoff_geometry getOff_geometry() {
		// dataNodeName = NX_OFF_GEOMETRY
		return getChild("off_geometry", NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(NXoff_geometry off_geometryGroup) {
		putChild("off_geometry", off_geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry(String name) {
		return getChild(name, NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(String name, NXoff_geometry off_geometry) {
		putChild(name, off_geometry);
	}

	@Override
	public Map<String, NXoff_geometry> getAllOff_geometry() {
		return getChildren(NXoff_geometry.class);
	}

	@Override
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry) {
		setChildren(off_geometry);
	}

	@Override
	public NXpositioner getPositioner() {
		// dataNodeName = NX_POSITIONER
		return getChild("positioner", NXpositioner.class);
	}

	@Override
	public void setPositioner(NXpositioner positionerGroup) {
		putChild("positioner", positionerGroup);
	}

	@Override
	public NXpositioner getPositioner(String name) {
		return getChild(name, NXpositioner.class);
	}

	@Override
	public void setPositioner(String name, NXpositioner positioner) {
		putChild(name, positioner);
	}

	@Override
	public Map<String, NXpositioner> getAllPositioner() {
		return getChildren(NXpositioner.class);
	}

	@Override
	public void setAllPositioner(Map<String, NXpositioner> positioner) {
		setChildren(positioner);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	@Deprecated
	public NXgeometry getBlade_geometry() {
		// dataNodeName = NX_BLADE_GEOMETRY
		return getChild("blade_geometry", NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setBlade_geometry(NXgeometry blade_geometryGroup) {
		putChild("blade_geometry", blade_geometryGroup);
	}

	@Override
	public Dataset getMaterial() {
		return getDataset(NX_MATERIAL);
	}

	@Override
	public String getMaterialScalar() {
		return getString(NX_MATERIAL);
	}

	@Override
	public DataNode setMaterial(IDataset materialDataset) {
		return setDataset(NX_MATERIAL, materialDataset);
	}

	@Override
	public DataNode setMaterialScalar(String materialValue) {
		return setString(NX_MATERIAL, materialValue);
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
	public Dataset getShape() {
		return getDataset(NX_SHAPE);
	}

	@Override
	public String getShapeScalar() {
		return getString(NX_SHAPE);
	}

	@Override
	public DataNode setShape(IDataset shapeDataset) {
		return setDataset(NX_SHAPE, shapeDataset);
	}

	@Override
	public DataNode setShapeScalar(String shapeValue) {
		return setString(NX_SHAPE, shapeValue);
	}

	@Override
	public Dataset getSize() {
		return getDataset(NX_SIZE);
	}

	@Override
	public Number getSizeScalar() {
		return getNumber(NX_SIZE);
	}

	@Override
	public DataNode setSize(IDataset sizeDataset) {
		return setDataset(NX_SIZE, sizeDataset);
	}

	@Override
	public DataNode setSizeScalar(Number sizeValue) {
		return setField(NX_SIZE, sizeValue);
	}

	@Override
	public NXnote getNote() {
		// dataNodeName = NX_NOTE
		return getChild("note", NXnote.class);
	}

	@Override
	public void setNote(NXnote noteGroup) {
		putChild("note", noteGroup);
	}

	@Override
	public NXnote getNote(String name) {
		return getChild(name, NXnote.class);
	}

	@Override
	public void setNote(String name, NXnote note) {
		putChild(name, note);
	}

	@Override
	public Map<String, NXnote> getAllNote() {
		return getChildren(NXnote.class);
	}

	@Override
	public void setAllNote(Map<String, NXnote> note) {
		setChildren(note);
	}

}
