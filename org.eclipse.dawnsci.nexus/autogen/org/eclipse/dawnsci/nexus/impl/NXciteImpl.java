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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A literature reference
 * Definition to include references for example for detectors,
 * manuals, instruments, acquisition or analysis software used.
 * The idea would be to include this in the relevant NeXus object:
 * :ref:`NXdetector` for detectors, :ref:`NXinstrument` for instruments, etc.
 * 
 */
public class NXciteImpl extends NXobjectImpl implements NXcite {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXciteImpl() {
		super();
	}

	public NXciteImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcite.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CITE;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getDescription() {
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
	public IDataset getUrl() {
		return getDataset(NX_URL);
	}

	@Override
	public String getUrlScalar() {
		return getString(NX_URL);
	}

	@Override
	public DataNode setUrl(IDataset urlDataset) {
		return setDataset(NX_URL, urlDataset);
	}

	@Override
	public DataNode setUrlScalar(String urlValue) {
		return setString(NX_URL, urlValue);
	}

	@Override
	public IDataset getDoi() {
		return getDataset(NX_DOI);
	}

	@Override
	public String getDoiScalar() {
		return getString(NX_DOI);
	}

	@Override
	public DataNode setDoi(IDataset doiDataset) {
		return setDataset(NX_DOI, doiDataset);
	}

	@Override
	public DataNode setDoiScalar(String doiValue) {
		return setString(NX_DOI, doiValue);
	}

	@Override
	public IDataset getEndnote() {
		return getDataset(NX_ENDNOTE);
	}

	@Override
	public String getEndnoteScalar() {
		return getString(NX_ENDNOTE);
	}

	@Override
	public DataNode setEndnote(IDataset endnoteDataset) {
		return setDataset(NX_ENDNOTE, endnoteDataset);
	}

	@Override
	public DataNode setEndnoteScalar(String endnoteValue) {
		return setString(NX_ENDNOTE, endnoteValue);
	}

	@Override
	public IDataset getBibtex() {
		return getDataset(NX_BIBTEX);
	}

	@Override
	public String getBibtexScalar() {
		return getString(NX_BIBTEX);
	}

	@Override
	public DataNode setBibtex(IDataset bibtexDataset) {
		return setDataset(NX_BIBTEX, bibtexDataset);
	}

	@Override
	public DataNode setBibtexScalar(String bibtexValue) {
		return setString(NX_BIBTEX, bibtexValue);
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
