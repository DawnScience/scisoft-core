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
 * Base class for method-specific generic imaging with electron microscopes.
 * In the majority of cases simple d-dimensional regular scan patterns are used
 * to probe regions-of-interest (ROIs). Examples can be single point aka spot
 * measurements, line profiles, or (rectangular) surface mappings.
 * The latter pattern is the most frequently used.
 * For now the base class provides for scans for which the settings,
 * binning, and energy resolution is the same for each scan point.

 */
public class NXem_imgImpl extends NXprocessImpl implements NXem_img {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_IMAGE);

	public NXem_imgImpl() {
		super();
	}

	public NXem_imgImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_img.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_IMG;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXimage getImage() {
		// dataNodeName = NX_IMAGE
		return getChild("image", NXimage.class);
	}

	@Override
	public void setImage(NXimage imageGroup) {
		putChild("image", imageGroup);
	}

	@Override
	public NXimage getImage(String name) {
		return getChild(name, NXimage.class);
	}

	@Override
	public void setImage(String name, NXimage image) {
		putChild(name, image);
	}

	@Override
	public Map<String, NXimage> getAllImage() {
		return getChildren(NXimage.class);
	}

	@Override
	public void setAllImage(Map<String, NXimage> image) {
		setChildren(image);
	}

}
