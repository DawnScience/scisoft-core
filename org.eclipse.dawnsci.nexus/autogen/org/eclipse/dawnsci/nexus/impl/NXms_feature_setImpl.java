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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Set of topological/spatial features in materials build from other features.

 */
public class NXms_feature_setImpl extends NXobjectImpl implements NXms_feature_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS);

	public NXms_feature_setImpl() {
		super();
	}

	public NXms_feature_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXms_feature_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MS_FEATURE_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public String getAttributeDepends_on() {
		return getAttrString(null, NX_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setAttributeDepends_on(String depends_onValue) {
		setAttribute(null, NX_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public Dataset getDimensionality() {
		return getDataset(NX_DIMENSIONALITY);
	}

	@Override
	public Long getDimensionalityScalar() {
		return getLong(NX_DIMENSIONALITY);
	}

	@Override
	public DataNode setDimensionality(IDataset dimensionalityDataset) {
		return setDataset(NX_DIMENSIONALITY, dimensionalityDataset);
	}

	@Override
	public DataNode setDimensionalityScalar(Long dimensionalityValue) {
		return setField(NX_DIMENSIONALITY, dimensionalityValue);
	}

	@Override
	public Dataset getCardinality() {
		return getDataset(NX_CARDINALITY);
	}

	@Override
	public Long getCardinalityScalar() {
		return getLong(NX_CARDINALITY);
	}

	@Override
	public DataNode setCardinality(IDataset cardinalityDataset) {
		return setDataset(NX_CARDINALITY, cardinalityDataset);
	}

	@Override
	public DataNode setCardinalityScalar(Long cardinalityValue) {
		return setField(NX_CARDINALITY, cardinalityValue);
	}

	@Override
	public Dataset getType_dict_keyword() {
		return getDataset(NX_TYPE_DICT_KEYWORD);
	}

	@Override
	public String getType_dict_keywordScalar() {
		return getString(NX_TYPE_DICT_KEYWORD);
	}

	@Override
	public DataNode setType_dict_keyword(IDataset type_dict_keywordDataset) {
		return setDataset(NX_TYPE_DICT_KEYWORD, type_dict_keywordDataset);
	}

	@Override
	public DataNode setType_dict_keywordScalar(String type_dict_keywordValue) {
		return setString(NX_TYPE_DICT_KEYWORD, type_dict_keywordValue);
	}

	@Override
	public Dataset getType_dict_value() {
		return getDataset(NX_TYPE_DICT_VALUE);
	}

	@Override
	public Long getType_dict_valueScalar() {
		return getLong(NX_TYPE_DICT_VALUE);
	}

	@Override
	public DataNode setType_dict_value(IDataset type_dict_valueDataset) {
		return setDataset(NX_TYPE_DICT_VALUE, type_dict_valueDataset);
	}

	@Override
	public DataNode setType_dict_valueScalar(Long type_dict_valueValue) {
		return setField(NX_TYPE_DICT_VALUE, type_dict_valueValue);
	}

	@Override
	public Dataset getNumber_of_parent_identifier() {
		return getDataset(NX_NUMBER_OF_PARENT_IDENTIFIER);
	}

	@Override
	public Long getNumber_of_parent_identifierScalar() {
		return getLong(NX_NUMBER_OF_PARENT_IDENTIFIER);
	}

	@Override
	public DataNode setNumber_of_parent_identifier(IDataset number_of_parent_identifierDataset) {
		return setDataset(NX_NUMBER_OF_PARENT_IDENTIFIER, number_of_parent_identifierDataset);
	}

	@Override
	public DataNode setNumber_of_parent_identifierScalar(Long number_of_parent_identifierValue) {
		return setField(NX_NUMBER_OF_PARENT_IDENTIFIER, number_of_parent_identifierValue);
	}

	@Override
	public Dataset getParent_identifier() {
		return getDataset(NX_PARENT_IDENTIFIER);
	}

	@Override
	public Long getParent_identifierScalar() {
		return getLong(NX_PARENT_IDENTIFIER);
	}

	@Override
	public DataNode setParent_identifier(IDataset parent_identifierDataset) {
		return setDataset(NX_PARENT_IDENTIFIER, parent_identifierDataset);
	}

	@Override
	public DataNode setParent_identifierScalar(Long parent_identifierValue) {
		return setField(NX_PARENT_IDENTIFIER, parent_identifierValue);
	}

	@Override
	public Dataset getIdentifier_offset() {
		return getDataset(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getIdentifier_offsetScalar() {
		return getLong(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset) {
		return setDataset(NX_IDENTIFIER_OFFSET, identifier_offsetDataset);
	}

	@Override
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue) {
		return setField(NX_IDENTIFIER_OFFSET, identifier_offsetValue);
	}

	@Override
	public Dataset getIdentifier() {
		return getDataset(NX_IDENTIFIER);
	}

	@Override
	public Long getIdentifierScalar() {
		return getLong(NX_IDENTIFIER);
	}

	@Override
	public DataNode setIdentifier(IDataset identifierDataset) {
		return setDataset(NX_IDENTIFIER, identifierDataset);
	}

	@Override
	public DataNode setIdentifierScalar(Long identifierValue) {
		return setField(NX_IDENTIFIER, identifierValue);
	}

	@Override
	public NXprocess getPoints() {
		// dataNodeName = NX_POINTS
		return getChild("points", NXprocess.class);
	}

	@Override
	public void setPoints(NXprocess pointsGroup) {
		putChild("points", pointsGroup);
	}
	// Unprocessed group: geometry
	// Unprocessed group: uncertainty

	@Override
	public NXprocess getLines() {
		// dataNodeName = NX_LINES
		return getChild("lines", NXprocess.class);
	}

	@Override
	public void setLines(NXprocess linesGroup) {
		putChild("lines", linesGroup);
	}
	// Unprocessed group: geometry
	// Unprocessed group: uncertainty

	@Override
	public NXprocess getInterfaces() {
		// dataNodeName = NX_INTERFACES
		return getChild("interfaces", NXprocess.class);
	}

	@Override
	public void setInterfaces(NXprocess interfacesGroup) {
		putChild("interfaces", interfacesGroup);
	}
	// Unprocessed group: geometry
	// Unprocessed group: uncertainty

	@Override
	public NXprocess getVolumes() {
		// dataNodeName = NX_VOLUMES
		return getChild("volumes", NXprocess.class);
	}

	@Override
	public void setVolumes(NXprocess volumesGroup) {
		putChild("volumes", volumesGroup);
	}
	// Unprocessed group: geometry
	// Unprocessed group: uncertainty

}
