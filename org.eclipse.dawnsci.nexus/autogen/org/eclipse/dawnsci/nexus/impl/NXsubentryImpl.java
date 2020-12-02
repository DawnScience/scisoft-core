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

import org.eclipse.dawnsci.nexus.*;

/**
 * Group of multiple application definitions for "multi-modal" (e.g. SAXS/WAXS) measurements.
 * ``NXsubentry`` is a base class virtually identical to :ref:`NXentry`
 * and is used as the (overlay) location for application definitions.
 * Use a separate ``NXsubentry`` for each application definition.
 * To use ``NXsubentry`` with a hypothetical application definition
 * called ``NXmyappdef``:
 * * Create a group with attribute ``NX_class="NXsubentry"``
 * * Within that group, create a field called ``definition="NXmyappdef"``.
 * * There are two optional attributes of definition: ``version`` and ``URL``
 * The intended use is to define application definitions for a
 * multi-modal (a.k.a. multi-technique) :ref:`NXentry`.
 * Previously, an application definition
 * replaced :ref:`NXentry` with its own definition.
 * With the increasing popularity of instruments combining
 * multiple techniques for data collection (such as SAXS/WAXS instruments),
 * it was recognized the application definitions must be entered in the NeXus
 * data file tree as children of :ref:`NXentry`.
 * 
 */
public class NXsubentryImpl extends NXobjectImpl implements NXsubentry {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_USER,
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_INSTRUMENT,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_MONITOR,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_PROCESS);

	public NXsubentryImpl() {
		super();
	}

	public NXsubentryImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsubentry.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SUBENTRY;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

	@Override
	public String getAttributeIdf_version() {
		return getAttrString(null, NX_ATTRIBUTE_IDF_VERSION);
	}

	@Override
	public void setAttributeIdf_version(String idf_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_IDF_VERSION, idf_versionValue);
	}

	@Override
	public IDataset getTitle() {
		return getDataset(NX_TITLE);
	}

	@Override
	public String getTitleScalar() {
		return getString(NX_TITLE);
	}

	@Override
	public DataNode setTitle(IDataset titleDataset) {
		return setDataset(NX_TITLE, titleDataset);
	}

	@Override
	public DataNode setTitleScalar(String titleValue) {
		return setString(NX_TITLE, titleValue);
	}

	@Override
	public IDataset getExperiment_identifier() {
		return getDataset(NX_EXPERIMENT_IDENTIFIER);
	}

	@Override
	public String getExperiment_identifierScalar() {
		return getString(NX_EXPERIMENT_IDENTIFIER);
	}

	@Override
	public DataNode setExperiment_identifier(IDataset experiment_identifierDataset) {
		return setDataset(NX_EXPERIMENT_IDENTIFIER, experiment_identifierDataset);
	}

	@Override
	public DataNode setExperiment_identifierScalar(String experiment_identifierValue) {
		return setString(NX_EXPERIMENT_IDENTIFIER, experiment_identifierValue);
	}

	@Override
	public IDataset getExperiment_description() {
		return getDataset(NX_EXPERIMENT_DESCRIPTION);
	}

	@Override
	public String getExperiment_descriptionScalar() {
		return getString(NX_EXPERIMENT_DESCRIPTION);
	}

	@Override
	public DataNode setExperiment_description(IDataset experiment_descriptionDataset) {
		return setDataset(NX_EXPERIMENT_DESCRIPTION, experiment_descriptionDataset);
	}

	@Override
	public DataNode setExperiment_descriptionScalar(String experiment_descriptionValue) {
		return setString(NX_EXPERIMENT_DESCRIPTION, experiment_descriptionValue);
	}

	@Override
	public NXnote getExperiment_documentation() {
		// dataNodeName = NX_EXPERIMENT_DOCUMENTATION
		return getChild("experiment_documentation", NXnote.class);
	}

	@Override
	public void setExperiment_documentation(NXnote experiment_documentationGroup) {
		putChild("experiment_documentation", experiment_documentationGroup);
	}

	@Override
	public IDataset getCollection_identifier() {
		return getDataset(NX_COLLECTION_IDENTIFIER);
	}

	@Override
	public String getCollection_identifierScalar() {
		return getString(NX_COLLECTION_IDENTIFIER);
	}

	@Override
	public DataNode setCollection_identifier(IDataset collection_identifierDataset) {
		return setDataset(NX_COLLECTION_IDENTIFIER, collection_identifierDataset);
	}

	@Override
	public DataNode setCollection_identifierScalar(String collection_identifierValue) {
		return setString(NX_COLLECTION_IDENTIFIER, collection_identifierValue);
	}

	@Override
	public IDataset getCollection_description() {
		return getDataset(NX_COLLECTION_DESCRIPTION);
	}

	@Override
	public String getCollection_descriptionScalar() {
		return getString(NX_COLLECTION_DESCRIPTION);
	}

	@Override
	public DataNode setCollection_description(IDataset collection_descriptionDataset) {
		return setDataset(NX_COLLECTION_DESCRIPTION, collection_descriptionDataset);
	}

	@Override
	public DataNode setCollection_descriptionScalar(String collection_descriptionValue) {
		return setString(NX_COLLECTION_DESCRIPTION, collection_descriptionValue);
	}

	@Override
	public IDataset getEntry_identifier() {
		return getDataset(NX_ENTRY_IDENTIFIER);
	}

	@Override
	public String getEntry_identifierScalar() {
		return getString(NX_ENTRY_IDENTIFIER);
	}

	@Override
	public DataNode setEntry_identifier(IDataset entry_identifierDataset) {
		return setDataset(NX_ENTRY_IDENTIFIER, entry_identifierDataset);
	}

	@Override
	public DataNode setEntry_identifierScalar(String entry_identifierValue) {
		return setString(NX_ENTRY_IDENTIFIER, entry_identifierValue);
	}

	@Override
	public IDataset getDefinition() {
		return getDataset(NX_DEFINITION);
	}

	@Override
	public String getDefinitionScalar() {
		return getString(NX_DEFINITION);
	}

	@Override
	public DataNode setDefinition(IDataset definitionDataset) {
		return setDataset(NX_DEFINITION, definitionDataset);
	}

	@Override
	public DataNode setDefinitionScalar(String definitionValue) {
		return setString(NX_DEFINITION, definitionValue);
	}

	@Override
	public String getDefinitionAttributeVersion() {
		return getAttrString(NX_DEFINITION, NX_DEFINITION_ATTRIBUTE_VERSION);
	}

	@Override
	public void setDefinitionAttributeVersion(String versionValue) {
		setAttribute(NX_DEFINITION, NX_DEFINITION_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public String getDefinitionAttributeUrl() {
		return getAttrString(NX_DEFINITION, NX_DEFINITION_ATTRIBUTE_URL);
	}

	@Override
	public void setDefinitionAttributeUrl(String urlValue) {
		setAttribute(NX_DEFINITION, NX_DEFINITION_ATTRIBUTE_URL, urlValue);
	}

	@Override
	public IDataset getDefinition_local() {
		return getDataset(NX_DEFINITION_LOCAL);
	}

	@Override
	public String getDefinition_localScalar() {
		return getString(NX_DEFINITION_LOCAL);
	}

	@Override
	public DataNode setDefinition_local(IDataset definition_localDataset) {
		return setDataset(NX_DEFINITION_LOCAL, definition_localDataset);
	}

	@Override
	public DataNode setDefinition_localScalar(String definition_localValue) {
		return setString(NX_DEFINITION_LOCAL, definition_localValue);
	}

	@Override
	public String getDefinition_localAttributeVersion() {
		return getAttrString(NX_DEFINITION_LOCAL, NX_DEFINITION_LOCAL_ATTRIBUTE_VERSION);
	}

	@Override
	public void setDefinition_localAttributeVersion(String versionValue) {
		setAttribute(NX_DEFINITION_LOCAL, NX_DEFINITION_LOCAL_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public String getDefinition_localAttributeUrl() {
		return getAttrString(NX_DEFINITION_LOCAL, NX_DEFINITION_LOCAL_ATTRIBUTE_URL);
	}

	@Override
	public void setDefinition_localAttributeUrl(String urlValue) {
		setAttribute(NX_DEFINITION_LOCAL, NX_DEFINITION_LOCAL_ATTRIBUTE_URL, urlValue);
	}

	@Override
	public IDataset getStart_time() {
		return getDataset(NX_START_TIME);
	}

	@Override
	public Date getStart_timeScalar() {
		return getDate(NX_START_TIME);
	}

	@Override
	public DataNode setStart_time(IDataset start_timeDataset) {
		return setDataset(NX_START_TIME, start_timeDataset);
	}

	@Override
	public DataNode setStart_timeScalar(Date start_timeValue) {
		return setDate(NX_START_TIME, start_timeValue);
	}

	@Override
	public IDataset getEnd_time() {
		return getDataset(NX_END_TIME);
	}

	@Override
	public Date getEnd_timeScalar() {
		return getDate(NX_END_TIME);
	}

	@Override
	public DataNode setEnd_time(IDataset end_timeDataset) {
		return setDataset(NX_END_TIME, end_timeDataset);
	}

	@Override
	public DataNode setEnd_timeScalar(Date end_timeValue) {
		return setDate(NX_END_TIME, end_timeValue);
	}

	@Override
	public IDataset getDuration() {
		return getDataset(NX_DURATION);
	}

	@Override
	public Long getDurationScalar() {
		return getLong(NX_DURATION);
	}

	@Override
	public DataNode setDuration(IDataset durationDataset) {
		return setDataset(NX_DURATION, durationDataset);
	}

	@Override
	public DataNode setDurationScalar(Long durationValue) {
		return setField(NX_DURATION, durationValue);
	}

	@Override
	public IDataset getCollection_time() {
		return getDataset(NX_COLLECTION_TIME);
	}

	@Override
	public Double getCollection_timeScalar() {
		return getDouble(NX_COLLECTION_TIME);
	}

	@Override
	public DataNode setCollection_time(IDataset collection_timeDataset) {
		return setDataset(NX_COLLECTION_TIME, collection_timeDataset);
	}

	@Override
	public DataNode setCollection_timeScalar(Double collection_timeValue) {
		return setField(NX_COLLECTION_TIME, collection_timeValue);
	}

	@Override
	public IDataset getRun_cycle() {
		return getDataset(NX_RUN_CYCLE);
	}

	@Override
	public String getRun_cycleScalar() {
		return getString(NX_RUN_CYCLE);
	}

	@Override
	public DataNode setRun_cycle(IDataset run_cycleDataset) {
		return setDataset(NX_RUN_CYCLE, run_cycleDataset);
	}

	@Override
	public DataNode setRun_cycleScalar(String run_cycleValue) {
		return setString(NX_RUN_CYCLE, run_cycleValue);
	}

	@Override
	public IDataset getProgram_name() {
		return getDataset(NX_PROGRAM_NAME);
	}

	@Override
	public String getProgram_nameScalar() {
		return getString(NX_PROGRAM_NAME);
	}

	@Override
	public DataNode setProgram_name(IDataset program_nameDataset) {
		return setDataset(NX_PROGRAM_NAME, program_nameDataset);
	}

	@Override
	public DataNode setProgram_nameScalar(String program_nameValue) {
		return setString(NX_PROGRAM_NAME, program_nameValue);
	}

	@Override
	public String getProgram_nameAttributeVersion() {
		return getAttrString(NX_PROGRAM_NAME, NX_PROGRAM_NAME_ATTRIBUTE_VERSION);
	}

	@Override
	public void setProgram_nameAttributeVersion(String versionValue) {
		setAttribute(NX_PROGRAM_NAME, NX_PROGRAM_NAME_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public String getProgram_nameAttributeConfiguration() {
		return getAttrString(NX_PROGRAM_NAME, NX_PROGRAM_NAME_ATTRIBUTE_CONFIGURATION);
	}

	@Override
	public void setProgram_nameAttributeConfiguration(String configurationValue) {
		setAttribute(NX_PROGRAM_NAME, NX_PROGRAM_NAME_ATTRIBUTE_CONFIGURATION, configurationValue);
	}

	@Override
	public IDataset getRevision() {
		return getDataset(NX_REVISION);
	}

	@Override
	public String getRevisionScalar() {
		return getString(NX_REVISION);
	}

	@Override
	public DataNode setRevision(IDataset revisionDataset) {
		return setDataset(NX_REVISION, revisionDataset);
	}

	@Override
	public DataNode setRevisionScalar(String revisionValue) {
		return setString(NX_REVISION, revisionValue);
	}

	@Override
	public String getRevisionAttributeComment() {
		return getAttrString(NX_REVISION, NX_REVISION_ATTRIBUTE_COMMENT);
	}

	@Override
	public void setRevisionAttributeComment(String commentValue) {
		setAttribute(NX_REVISION, NX_REVISION_ATTRIBUTE_COMMENT, commentValue);
	}

	@Override
	public IDataset getPre_sample_flightpath() {
		return getDataset(NX_PRE_SAMPLE_FLIGHTPATH);
	}

	@Override
	public Double getPre_sample_flightpathScalar() {
		return getDouble(NX_PRE_SAMPLE_FLIGHTPATH);
	}

	@Override
	public DataNode setPre_sample_flightpath(IDataset pre_sample_flightpathDataset) {
		return setDataset(NX_PRE_SAMPLE_FLIGHTPATH, pre_sample_flightpathDataset);
	}

	@Override
	public DataNode setPre_sample_flightpathScalar(Double pre_sample_flightpathValue) {
		return setField(NX_PRE_SAMPLE_FLIGHTPATH, pre_sample_flightpathValue);
	}

	@Override
	public NXnote getNotes() {
		// dataNodeName = NX_NOTES
		return getChild("notes", NXnote.class);
	}

	@Override
	public void setNotes(NXnote notesGroup) {
		putChild("notes", notesGroup);
	}

	@Override
	public NXnote getThumbnail() {
		// dataNodeName = NX_THUMBNAIL
		return getChild("thumbnail", NXnote.class);
	}

	@Override
	public void setThumbnail(NXnote thumbnailGroup) {
		putChild("thumbnail", thumbnailGroup);
	}

	@Override
	public NXuser getUser() {
		// dataNodeName = NX_USER
		return getChild("user", NXuser.class);
	}

	@Override
	public void setUser(NXuser userGroup) {
		putChild("user", userGroup);
	}

	@Override
	public NXuser getUser(String name) {
		return getChild(name, NXuser.class);
	}

	@Override
	public void setUser(String name, NXuser user) {
		putChild(name, user);
	}

	@Override
	public Map<String, NXuser> getAllUser() {
		return getChildren(NXuser.class);
	}
	
	@Override
	public void setAllUser(Map<String, NXuser> user) {
		setChildren(user);
	}

	@Override
	public NXsample getSample() {
		// dataNodeName = NX_SAMPLE
		return getChild("sample", NXsample.class);
	}

	@Override
	public void setSample(NXsample sampleGroup) {
		putChild("sample", sampleGroup);
	}

	@Override
	public NXsample getSample(String name) {
		return getChild(name, NXsample.class);
	}

	@Override
	public void setSample(String name, NXsample sample) {
		putChild(name, sample);
	}

	@Override
	public Map<String, NXsample> getAllSample() {
		return getChildren(NXsample.class);
	}
	
	@Override
	public void setAllSample(Map<String, NXsample> sample) {
		setChildren(sample);
	}

	@Override
	public NXinstrument getInstrument() {
		// dataNodeName = NX_INSTRUMENT
		return getChild("instrument", NXinstrument.class);
	}

	@Override
	public void setInstrument(NXinstrument instrumentGroup) {
		putChild("instrument", instrumentGroup);
	}

	@Override
	public NXinstrument getInstrument(String name) {
		return getChild(name, NXinstrument.class);
	}

	@Override
	public void setInstrument(String name, NXinstrument instrument) {
		putChild(name, instrument);
	}

	@Override
	public Map<String, NXinstrument> getAllInstrument() {
		return getChildren(NXinstrument.class);
	}
	
	@Override
	public void setAllInstrument(Map<String, NXinstrument> instrument) {
		setChildren(instrument);
	}

	@Override
	public NXcollection getCollection() {
		// dataNodeName = NX_COLLECTION
		return getChild("collection", NXcollection.class);
	}

	@Override
	public void setCollection(NXcollection collectionGroup) {
		putChild("collection", collectionGroup);
	}

	@Override
	public NXcollection getCollection(String name) {
		return getChild(name, NXcollection.class);
	}

	@Override
	public void setCollection(String name, NXcollection collection) {
		putChild(name, collection);
	}

	@Override
	public Map<String, NXcollection> getAllCollection() {
		return getChildren(NXcollection.class);
	}
	
	@Override
	public void setAllCollection(Map<String, NXcollection> collection) {
		setChildren(collection);
	}

	@Override
	public NXmonitor getMonitor() {
		// dataNodeName = NX_MONITOR
		return getChild("monitor", NXmonitor.class);
	}

	@Override
	public void setMonitor(NXmonitor monitorGroup) {
		putChild("monitor", monitorGroup);
	}

	@Override
	public NXmonitor getMonitor(String name) {
		return getChild(name, NXmonitor.class);
	}

	@Override
	public void setMonitor(String name, NXmonitor monitor) {
		putChild(name, monitor);
	}

	@Override
	public Map<String, NXmonitor> getAllMonitor() {
		return getChildren(NXmonitor.class);
	}
	
	@Override
	public void setAllMonitor(Map<String, NXmonitor> monitor) {
		setChildren(monitor);
	}

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXdata getData(String name) {
		return getChild(name, NXdata.class);
	}

	@Override
	public void setData(String name, NXdata data) {
		putChild(name, data);
	}

	@Override
	public Map<String, NXdata> getAllData() {
		return getChildren(NXdata.class);
	}
	
	@Override
	public void setAllData(Map<String, NXdata> data) {
		setChildren(data);
	}

	@Override
	public NXparameters getParameters() {
		// dataNodeName = NX_PARAMETERS
		return getChild("parameters", NXparameters.class);
	}

	@Override
	public void setParameters(NXparameters parametersGroup) {
		putChild("parameters", parametersGroup);
	}

	@Override
	public NXparameters getParameters(String name) {
		return getChild(name, NXparameters.class);
	}

	@Override
	public void setParameters(String name, NXparameters parameters) {
		putChild(name, parameters);
	}

	@Override
	public Map<String, NXparameters> getAllParameters() {
		return getChildren(NXparameters.class);
	}
	
	@Override
	public void setAllParameters(Map<String, NXparameters> parameters) {
		setChildren(parameters);
	}

	@Override
	public NXprocess getProcess() {
		// dataNodeName = NX_PROCESS
		return getChild("process", NXprocess.class);
	}

	@Override
	public void setProcess(NXprocess processGroup) {
		putChild("process", processGroup);
	}

	@Override
	public NXprocess getProcess(String name) {
		return getChild(name, NXprocess.class);
	}

	@Override
	public void setProcess(String name, NXprocess process) {
		putChild(name, process);
	}

	@Override
	public Map<String, NXprocess> getAllProcess() {
		return getChildren(NXprocess.class);
	}
	
	@Override
	public void setAllProcess(Map<String, NXprocess> process) {
		setChildren(process);
	}

}
