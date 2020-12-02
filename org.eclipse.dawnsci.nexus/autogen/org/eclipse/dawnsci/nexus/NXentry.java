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

/**
 * (**required**) :ref:`NXentry` describes the measurement.
 * The top-level NeXus group which contains all the data and associated
 * information that comprise a single measurement.
 * It is mandatory that there is at least one
 * group of this type in the NeXus file.
 * 
 */
public interface NXentry extends NXsubentry {

	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_ATTRIBUTE_IDF_VERSION = "idf_version";
	public static final String NX_TITLE = "title";
	public static final String NX_EXPERIMENT_IDENTIFIER = "experiment_identifier";
	public static final String NX_EXPERIMENT_DESCRIPTION = "experiment_description";
	public static final String NX_COLLECTION_IDENTIFIER = "collection_identifier";
	public static final String NX_COLLECTION_DESCRIPTION = "collection_description";
	public static final String NX_ENTRY_IDENTIFIER = "entry_identifier";
	public static final String NX_FEATURES = "features";
	public static final String NX_DEFINITION = "definition";
	public static final String NX_DEFINITION_ATTRIBUTE_VERSION = "version";
	public static final String NX_DEFINITION_ATTRIBUTE_URL = "url";
	public static final String NX_DEFINITION_LOCAL = "definition_local";
	public static final String NX_DEFINITION_LOCAL_ATTRIBUTE_VERSION = "version";
	public static final String NX_DEFINITION_LOCAL_ATTRIBUTE_URL = "url";
	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_DURATION = "duration";
	public static final String NX_COLLECTION_TIME = "collection_time";
	public static final String NX_RUN_CYCLE = "run_cycle";
	public static final String NX_PROGRAM_NAME = "program_name";
	public static final String NX_PROGRAM_NAME_ATTRIBUTE_VERSION = "version";
	public static final String NX_PROGRAM_NAME_ATTRIBUTE_CONFIGURATION = "configuration";
	public static final String NX_REVISION = "revision";
	public static final String NX_REVISION_ATTRIBUTE_COMMENT = "comment";
	public static final String NX_PRE_SAMPLE_FLIGHTPATH = "pre_sample_flightpath";
	/**
	 * .. index:: plotting
	 * Declares which :ref:`NXdata` (or :ref:`NXsubentry`) group
	 * contains the data to be shown by default.
	 * It is needed to resolve ambiguity when more than one :ref:`NXdata` group exists.
	 * The value is the name of the default :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014 [#]_) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * .. [#] NIAC2014 discussion summary:
	 * https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which :ref:`NXdata` (or :ref:`NXsubentry`) group
	 * contains the data to be shown by default.
	 * It is needed to resolve ambiguity when more than one :ref:`NXdata` group exists.
	 * The value is the name of the default :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014 [#]_) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * .. [#] NIAC2014 discussion summary:
	 * https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

	/**
	 * The data group
	 * .. note:: Before the NIAC2016 meeting [#]_, at least one
	 * :ref:`NXdata` group was required in each :ref:`NXentry` group.
	 * At the NIAC2016 meeting, it was decided to make :ref:`NXdata`
	 * an optional group in :ref:`NXentry` groups for data files that
	 * do not use an application definition.
	 * It is recommended strongly that all NeXus data files provide
	 * a NXdata group.
	 * It is permissable to omit the NXdata group only when
	 * defining the default plot is not practical or possible
	 * from the available data.
	 * For example, neutron event data may not have anything that
	 * makes a useful plot without extensive processing.
	 * Certain application definitions override this decision and
	 * require an :ref:`NXdata` group
	 * in the :ref:`NXentry` group. The ``minOccurs=0`` attribute
	 * in the application definition will indicate the
	 * :ref:`NXdata` group
	 * is optional, otherwise, it is required.
	 * .. [#] NIAC2016:
	 * https://www.nexusformat.org/NIAC2016.html,
	 * https://github.com/nexusformat/NIAC/issues/16
	 * 
	 * @return  the value.
	 */
	public NXdata getData();
	
	/**
	 * The data group
	 * .. note:: Before the NIAC2016 meeting [#]_, at least one
	 * :ref:`NXdata` group was required in each :ref:`NXentry` group.
	 * At the NIAC2016 meeting, it was decided to make :ref:`NXdata`
	 * an optional group in :ref:`NXentry` groups for data files that
	 * do not use an application definition.
	 * It is recommended strongly that all NeXus data files provide
	 * a NXdata group.
	 * It is permissable to omit the NXdata group only when
	 * defining the default plot is not practical or possible
	 * from the available data.
	 * For example, neutron event data may not have anything that
	 * makes a useful plot without extensive processing.
	 * Certain application definitions override this decision and
	 * require an :ref:`NXdata` group
	 * in the :ref:`NXentry` group. The ``minOccurs=0`` attribute
	 * in the application definition will indicate the
	 * :ref:`NXdata` group
	 * is optional, otherwise, it is required.
	 * .. [#] NIAC2016:
	 * https://www.nexusformat.org/NIAC2016.html,
	 * https://github.com/nexusformat/NIAC/issues/16
	 * 
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li>
	 * The data group
	 * .. note:: Before the NIAC2016 meeting [#]_, at least one
	 * :ref:`NXdata` group was required in each :ref:`NXentry` group.
	 * At the NIAC2016 meeting, it was decided to make :ref:`NXdata`
	 * an optional group in :ref:`NXentry` groups for data files that
	 * do not use an application definition.
	 * It is recommended strongly that all NeXus data files provide
	 * a NXdata group.
	 * It is permissable to omit the NXdata group only when
	 * defining the default plot is not practical or possible
	 * from the available data.
	 * For example, neutron event data may not have anything that
	 * makes a useful plot without extensive processing.
	 * Certain application definitions override this decision and
	 * require an :ref:`NXdata` group
	 * in the :ref:`NXentry` group. The ``minOccurs=0`` attribute
	 * in the application definition will indicate the
	 * :ref:`NXdata` group
	 * is optional, otherwise, it is required.
	 * .. [#] NIAC2016:
	 * https://www.nexusformat.org/NIAC2016.html,
	 * https://github.com/nexusformat/NIAC/issues/16</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdata for that node.
	 */
	public NXdata getData(String name);
	
	/**
	 * Set a NXdata node by name:
	 * <ul>
	 * <li>
	 * The data group
	 * .. note:: Before the NIAC2016 meeting [#]_, at least one
	 * :ref:`NXdata` group was required in each :ref:`NXentry` group.
	 * At the NIAC2016 meeting, it was decided to make :ref:`NXdata`
	 * an optional group in :ref:`NXentry` groups for data files that
	 * do not use an application definition.
	 * It is recommended strongly that all NeXus data files provide
	 * a NXdata group.
	 * It is permissable to omit the NXdata group only when
	 * defining the default plot is not practical or possible
	 * from the available data.
	 * For example, neutron event data may not have anything that
	 * makes a useful plot without extensive processing.
	 * Certain application definitions override this decision and
	 * require an :ref:`NXdata` group
	 * in the :ref:`NXentry` group. The ``minOccurs=0`` attribute
	 * in the application definition will indicate the
	 * :ref:`NXdata` group
	 * is optional, otherwise, it is required.
	 * .. [#] NIAC2016:
	 * https://www.nexusformat.org/NIAC2016.html,
	 * https://github.com/nexusformat/NIAC/issues/16</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param data the value to set
	 */
	public void setData(String name, NXdata data);
	
	/**
	 * Get all NXdata nodes:
	 * <ul>
	 * <li>
	 * The data group
	 * .. note:: Before the NIAC2016 meeting [#]_, at least one
	 * :ref:`NXdata` group was required in each :ref:`NXentry` group.
	 * At the NIAC2016 meeting, it was decided to make :ref:`NXdata`
	 * an optional group in :ref:`NXentry` groups for data files that
	 * do not use an application definition.
	 * It is recommended strongly that all NeXus data files provide
	 * a NXdata group.
	 * It is permissable to omit the NXdata group only when
	 * defining the default plot is not practical or possible
	 * from the available data.
	 * For example, neutron event data may not have anything that
	 * makes a useful plot without extensive processing.
	 * Certain application definitions override this decision and
	 * require an :ref:`NXdata` group
	 * in the :ref:`NXentry` group. The ``minOccurs=0`` attribute
	 * in the application definition will indicate the
	 * :ref:`NXdata` group
	 * is optional, otherwise, it is required.
	 * .. [#] NIAC2016:
	 * https://www.nexusformat.org/NIAC2016.html,
	 * https://github.com/nexusformat/NIAC/issues/16</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * The data group
	 * .. note:: Before the NIAC2016 meeting [#]_, at least one
	 * :ref:`NXdata` group was required in each :ref:`NXentry` group.
	 * At the NIAC2016 meeting, it was decided to make :ref:`NXdata`
	 * an optional group in :ref:`NXentry` groups for data files that
	 * do not use an application definition.
	 * It is recommended strongly that all NeXus data files provide
	 * a NXdata group.
	 * It is permissable to omit the NXdata group only when
	 * defining the default plot is not practical or possible
	 * from the available data.
	 * For example, neutron event data may not have anything that
	 * makes a useful plot without extensive processing.
	 * Certain application definitions override this decision and
	 * require an :ref:`NXdata` group
	 * in the :ref:`NXentry` group. The ``minOccurs=0`` attribute
	 * in the application definition will indicate the
	 * :ref:`NXdata` group
	 * is optional, otherwise, it is required.
	 * .. [#] NIAC2016:
	 * https://www.nexusformat.org/NIAC2016.html,
	 * https://github.com/nexusformat/NIAC/issues/16</li>
	 * </ul>
	 * 
	 * @param data the child nodes to add 
	 */
	
	public void setAllData(Map<String, NXdata> data);
	

	/**
	 * ISIS Muon IDF_Version
	 * 
	 * @return  the value.
	 */
	public String getAttributeIdf_version();
	
	/**
	 * ISIS Muon IDF_Version
	 * 
	 * @param idf_versionValue the idf_versionValue
	 */
	public void setAttributeIdf_version(String idf_versionValue);

	/**
	 * Extended title for entry
	 * 
	 * @return  the value.
	 */
	public IDataset getTitle();
	
	/**
	 * Extended title for entry
	 * 
	 * @param titleDataset the titleDataset
	 */
	public DataNode setTitle(IDataset titleDataset);

	/**
	 * Extended title for entry
	 * 
	 * @return  the value.
	 */
	public String getTitleScalar();

	/**
	 * Extended title for entry
	 * 
	 * @param title the title
	 */
	public DataNode setTitleScalar(String titleValue);

	/**
	 * Unique identifier for the experiment,
	 * defined by the facility,
	 * possibly linked to the proposals
	 * 
	 * @return  the value.
	 */
	public IDataset getExperiment_identifier();
	
	/**
	 * Unique identifier for the experiment,
	 * defined by the facility,
	 * possibly linked to the proposals
	 * 
	 * @param experiment_identifierDataset the experiment_identifierDataset
	 */
	public DataNode setExperiment_identifier(IDataset experiment_identifierDataset);

	/**
	 * Unique identifier for the experiment,
	 * defined by the facility,
	 * possibly linked to the proposals
	 * 
	 * @return  the value.
	 */
	public String getExperiment_identifierScalar();

	/**
	 * Unique identifier for the experiment,
	 * defined by the facility,
	 * possibly linked to the proposals
	 * 
	 * @param experiment_identifier the experiment_identifier
	 */
	public DataNode setExperiment_identifierScalar(String experiment_identifierValue);

	/**
	 * Brief summary of the experiment, including key objectives.
	 * 
	 * @return  the value.
	 */
	public IDataset getExperiment_description();
	
	/**
	 * Brief summary of the experiment, including key objectives.
	 * 
	 * @param experiment_descriptionDataset the experiment_descriptionDataset
	 */
	public DataNode setExperiment_description(IDataset experiment_descriptionDataset);

	/**
	 * Brief summary of the experiment, including key objectives.
	 * 
	 * @return  the value.
	 */
	public String getExperiment_descriptionScalar();

	/**
	 * Brief summary of the experiment, including key objectives.
	 * 
	 * @param experiment_description the experiment_description
	 */
	public DataNode setExperiment_descriptionScalar(String experiment_descriptionValue);

	/**
	 * Description of the full experiment (document in pdf, latex, ...)
	 * 
	 * @return  the value.
	 */
	public NXnote getExperiment_documentation();
	
	/**
	 * Description of the full experiment (document in pdf, latex, ...)
	 * 
	 * @param experiment_documentationGroup the experiment_documentationGroup
	 */
	public void setExperiment_documentation(NXnote experiment_documentationGroup);

	/**
	 * User or Data Acquisition defined group of NeXus files or NXentry
	 * 
	 * @return  the value.
	 */
	public IDataset getCollection_identifier();
	
	/**
	 * User or Data Acquisition defined group of NeXus files or NXentry
	 * 
	 * @param collection_identifierDataset the collection_identifierDataset
	 */
	public DataNode setCollection_identifier(IDataset collection_identifierDataset);

	/**
	 * User or Data Acquisition defined group of NeXus files or NXentry
	 * 
	 * @return  the value.
	 */
	public String getCollection_identifierScalar();

	/**
	 * User or Data Acquisition defined group of NeXus files or NXentry
	 * 
	 * @param collection_identifier the collection_identifier
	 */
	public DataNode setCollection_identifierScalar(String collection_identifierValue);

	/**
	 * Brief summary of the collection, including grouping criteria.
	 * 
	 * @return  the value.
	 */
	public IDataset getCollection_description();
	
	/**
	 * Brief summary of the collection, including grouping criteria.
	 * 
	 * @param collection_descriptionDataset the collection_descriptionDataset
	 */
	public DataNode setCollection_description(IDataset collection_descriptionDataset);

	/**
	 * Brief summary of the collection, including grouping criteria.
	 * 
	 * @return  the value.
	 */
	public String getCollection_descriptionScalar();

	/**
	 * Brief summary of the collection, including grouping criteria.
	 * 
	 * @param collection_description the collection_description
	 */
	public DataNode setCollection_descriptionScalar(String collection_descriptionValue);

	/**
	 * unique identifier for the measurement, defined by the facility.
	 * 
	 * @return  the value.
	 */
	public IDataset getEntry_identifier();
	
	/**
	 * unique identifier for the measurement, defined by the facility.
	 * 
	 * @param entry_identifierDataset the entry_identifierDataset
	 */
	public DataNode setEntry_identifier(IDataset entry_identifierDataset);

	/**
	 * unique identifier for the measurement, defined by the facility.
	 * 
	 * @return  the value.
	 */
	public String getEntry_identifierScalar();

	/**
	 * unique identifier for the measurement, defined by the facility.
	 * 
	 * @param entry_identifier the entry_identifier
	 */
	public DataNode setEntry_identifierScalar(String entry_identifierValue);

	/**
	 * Reserved for future use by NIAC.
	 * See https://github.com/nexusformat/definitions/issues/382
	 * 
	 * @return  the value.
	 */
	public IDataset getFeatures();
	
	/**
	 * Reserved for future use by NIAC.
	 * See https://github.com/nexusformat/definitions/issues/382
	 * 
	 * @param featuresDataset the featuresDataset
	 */
	public DataNode setFeatures(IDataset featuresDataset);

	/**
	 * Reserved for future use by NIAC.
	 * See https://github.com/nexusformat/definitions/issues/382
	 * 
	 * @return  the value.
	 */
	public String getFeaturesScalar();

	/**
	 * Reserved for future use by NIAC.
	 * See https://github.com/nexusformat/definitions/issues/382
	 * 
	 * @param features the features
	 */
	public DataNode setFeaturesScalar(String featuresValue);

	/**
	 * (alternate use: see same field in :ref:`NXsubentry` for preferred)
	 * Official NeXus NXDL schema to which this entry conforms.
	 * This field is provided so that :ref:`NXentry` can be the overlay position
	 * in a NeXus data file for an application definition and its
	 * set of groups, fields, and attributes.
	 * *It is advised* to use :ref:`NXsubentry`, instead, as the overlay position.
	 * 
	 * @return  the value.
	 */
	public IDataset getDefinition();
	
	/**
	 * (alternate use: see same field in :ref:`NXsubentry` for preferred)
	 * Official NeXus NXDL schema to which this entry conforms.
	 * This field is provided so that :ref:`NXentry` can be the overlay position
	 * in a NeXus data file for an application definition and its
	 * set of groups, fields, and attributes.
	 * *It is advised* to use :ref:`NXsubentry`, instead, as the overlay position.
	 * 
	 * @param definitionDataset the definitionDataset
	 */
	public DataNode setDefinition(IDataset definitionDataset);

	/**
	 * (alternate use: see same field in :ref:`NXsubentry` for preferred)
	 * Official NeXus NXDL schema to which this entry conforms.
	 * This field is provided so that :ref:`NXentry` can be the overlay position
	 * in a NeXus data file for an application definition and its
	 * set of groups, fields, and attributes.
	 * *It is advised* to use :ref:`NXsubentry`, instead, as the overlay position.
	 * 
	 * @return  the value.
	 */
	public String getDefinitionScalar();

	/**
	 * (alternate use: see same field in :ref:`NXsubentry` for preferred)
	 * Official NeXus NXDL schema to which this entry conforms.
	 * This field is provided so that :ref:`NXentry` can be the overlay position
	 * in a NeXus data file for an application definition and its
	 * set of groups, fields, and attributes.
	 * *It is advised* to use :ref:`NXsubentry`, instead, as the overlay position.
	 * 
	 * @param definition the definition
	 */
	public DataNode setDefinitionScalar(String definitionValue);

	/**
	 * NXDL version number
	 * 
	 * @return  the value.
	 */
	public String getDefinitionAttributeVersion();
	
	/**
	 * NXDL version number
	 * 
	 * @param versionValue the versionValue
	 */
	public void setDefinitionAttributeVersion(String versionValue);

	/**
	 * URL of NXDL file
	 * 
	 * @return  the value.
	 */
	public String getDefinitionAttributeUrl();
	
	/**
	 * URL of NXDL file
	 * 
	 * @param urlValue the urlValue
	 */
	public void setDefinitionAttributeUrl(String urlValue);

	/**
	 * Local NXDL schema extended from the entry
	 * specified in the ``definition`` field.
	 * This contains any locally-defined,
	 * additional fields in the entry.
	 * 
	 * @deprecated see same field in :ref:`NXsubentry` for preferred use
	 * @return  the value.
	 */
	@Deprecated
	public IDataset getDefinition_local();
	
	/**
	 * Local NXDL schema extended from the entry
	 * specified in the ``definition`` field.
	 * This contains any locally-defined,
	 * additional fields in the entry.
	 * 
	 * @deprecated see same field in :ref:`NXsubentry` for preferred use
	 * @param definition_localDataset the definition_localDataset
	 */
	@Deprecated
	public DataNode setDefinition_local(IDataset definition_localDataset);

	/**
	 * Local NXDL schema extended from the entry
	 * specified in the ``definition`` field.
	 * This contains any locally-defined,
	 * additional fields in the entry.
	 * 
	 * @deprecated see same field in :ref:`NXsubentry` for preferred use
	 * @return  the value.
	 */
	@Deprecated
	public String getDefinition_localScalar();

	/**
	 * Local NXDL schema extended from the entry
	 * specified in the ``definition`` field.
	 * This contains any locally-defined,
	 * additional fields in the entry.
	 * 
	 * @deprecated see same field in :ref:`NXsubentry` for preferred use
	 * @param definition_local the definition_local
	 */
	@Deprecated
	public DataNode setDefinition_localScalar(String definition_localValue);

	/**
	 * NXDL version number
	 * 
	 * @return  the value.
	 */
	public String getDefinition_localAttributeVersion();
	
	/**
	 * NXDL version number
	 * 
	 * @param versionValue the versionValue
	 */
	public void setDefinition_localAttributeVersion(String versionValue);

	/**
	 * URL of NXDL file
	 * 
	 * @return  the value.
	 */
	public String getDefinition_localAttributeUrl();
	
	/**
	 * URL of NXDL file
	 * 
	 * @param urlValue the urlValue
	 */
	public void setDefinition_localAttributeUrl(String urlValue);

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getStart_time();
	
	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEnd_time();
	
	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 * 
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * Duration of measurement
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDuration();
	
	/**
	 * Duration of measurement
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param durationDataset the durationDataset
	 */
	public DataNode setDuration(IDataset durationDataset);

	/**
	 * Duration of measurement
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getDurationScalar();

	/**
	 * Duration of measurement
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param duration the duration
	 */
	public DataNode setDurationScalar(Long durationValue);

	/**
	 * Time transpired actually collecting data i.e. taking out time when collection was
	 * suspended due to e.g. temperature out of range
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCollection_time();
	
	/**
	 * Time transpired actually collecting data i.e. taking out time when collection was
	 * suspended due to e.g. temperature out of range
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param collection_timeDataset the collection_timeDataset
	 */
	public DataNode setCollection_time(IDataset collection_timeDataset);

	/**
	 * Time transpired actually collecting data i.e. taking out time when collection was
	 * suspended due to e.g. temperature out of range
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getCollection_timeScalar();

	/**
	 * Time transpired actually collecting data i.e. taking out time when collection was
	 * suspended due to e.g. temperature out of range
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param collection_time the collection_time
	 */
	public DataNode setCollection_timeScalar(Double collection_timeValue);

	/**
	 * Such as "2007-3". Some user facilities organize their beam time into run cycles.
	 * 
	 * @return  the value.
	 */
	public IDataset getRun_cycle();
	
	/**
	 * Such as "2007-3". Some user facilities organize their beam time into run cycles.
	 * 
	 * @param run_cycleDataset the run_cycleDataset
	 */
	public DataNode setRun_cycle(IDataset run_cycleDataset);

	/**
	 * Such as "2007-3". Some user facilities organize their beam time into run cycles.
	 * 
	 * @return  the value.
	 */
	public String getRun_cycleScalar();

	/**
	 * Such as "2007-3". Some user facilities organize their beam time into run cycles.
	 * 
	 * @param run_cycle the run_cycle
	 */
	public DataNode setRun_cycleScalar(String run_cycleValue);

	/**
	 * Name of program used to generate this file
	 * 
	 * @return  the value.
	 */
	public IDataset getProgram_name();
	
	/**
	 * Name of program used to generate this file
	 * 
	 * @param program_nameDataset the program_nameDataset
	 */
	public DataNode setProgram_name(IDataset program_nameDataset);

	/**
	 * Name of program used to generate this file
	 * 
	 * @return  the value.
	 */
	public String getProgram_nameScalar();

	/**
	 * Name of program used to generate this file
	 * 
	 * @param program_name the program_name
	 */
	public DataNode setProgram_nameScalar(String program_nameValue);

	/**
	 * Program version number
	 * 
	 * @return  the value.
	 */
	public String getProgram_nameAttributeVersion();
	
	/**
	 * Program version number
	 * 
	 * @param versionValue the versionValue
	 */
	public void setProgram_nameAttributeVersion(String versionValue);

	/**
	 * configuration of the program
	 * 
	 * @return  the value.
	 */
	public String getProgram_nameAttributeConfiguration();
	
	/**
	 * configuration of the program
	 * 
	 * @param configurationValue the configurationValue
	 */
	public void setProgram_nameAttributeConfiguration(String configurationValue);

	/**
	 * Revision id of the file due to re-calibration, reprocessing, new analysis, new
	 * instrument definition format, ...
	 * 
	 * @return  the value.
	 */
	public IDataset getRevision();
	
	/**
	 * Revision id of the file due to re-calibration, reprocessing, new analysis, new
	 * instrument definition format, ...
	 * 
	 * @param revisionDataset the revisionDataset
	 */
	public DataNode setRevision(IDataset revisionDataset);

	/**
	 * Revision id of the file due to re-calibration, reprocessing, new analysis, new
	 * instrument definition format, ...
	 * 
	 * @return  the value.
	 */
	public String getRevisionScalar();

	/**
	 * Revision id of the file due to re-calibration, reprocessing, new analysis, new
	 * instrument definition format, ...
	 * 
	 * @param revision the revision
	 */
	public DataNode setRevisionScalar(String revisionValue);

	/**
	 * 
	 * @return  the value.
	 */
	public String getRevisionAttributeComment();
	
	/**
	 * 
	 * @param commentValue the commentValue
	 */
	public void setRevisionAttributeComment(String commentValue);

	/**
	 * This is the flightpath before the sample position. This can be determined by a chopper,
	 * by the moderator or the source itself. In other words: it the distance to the component
	 * which gives the T0 signal to the detector electronics. If another component in the
	 * NXinstrument hierarchy provides this information, this should be a link.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPre_sample_flightpath();
	
	/**
	 * This is the flightpath before the sample position. This can be determined by a chopper,
	 * by the moderator or the source itself. In other words: it the distance to the component
	 * which gives the T0 signal to the detector electronics. If another component in the
	 * NXinstrument hierarchy provides this information, this should be a link.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param pre_sample_flightpathDataset the pre_sample_flightpathDataset
	 */
	public DataNode setPre_sample_flightpath(IDataset pre_sample_flightpathDataset);

	/**
	 * This is the flightpath before the sample position. This can be determined by a chopper,
	 * by the moderator or the source itself. In other words: it the distance to the component
	 * which gives the T0 signal to the detector electronics. If another component in the
	 * NXinstrument hierarchy provides this information, this should be a link.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPre_sample_flightpathScalar();

	/**
	 * This is the flightpath before the sample position. This can be determined by a chopper,
	 * by the moderator or the source itself. In other words: it the distance to the component
	 * which gives the T0 signal to the detector electronics. If another component in the
	 * NXinstrument hierarchy provides this information, this should be a link.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param pre_sample_flightpath the pre_sample_flightpath
	 */
	public DataNode setPre_sample_flightpathScalar(Double pre_sample_flightpathValue);

	/**
	 * Notes describing entry
	 * 
	 * @return  the value.
	 */
	public NXnote getNotes();
	
	/**
	 * Notes describing entry
	 * 
	 * @param notesGroup the notesGroup
	 */
	public void setNotes(NXnote notesGroup);

	/**
	 * A small image that is representative of the entry. An example of this is a 640x480
	 * jpeg image automatically produced by a low resolution plot of the NXdata.
	 * 
	 * @return  the value.
	 */
	public NXnote getThumbnail();
	
	/**
	 * A small image that is representative of the entry. An example of this is a 640x480
	 * jpeg image automatically produced by a low resolution plot of the NXdata.
	 * 
	 * @param thumbnailGroup the thumbnailGroup
	 */
	public void setThumbnail(NXnote thumbnailGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXuser getUser();
	
	/**
	 * 
	 * @param userGroup the userGroup
	 */
	public void setUser(NXuser userGroup);

	/**
	 * Get a NXuser node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXuser for that node.
	 */
	public NXuser getUser(String name);
	
	/**
	 * Set a NXuser node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param user the value to set
	 */
	public void setUser(String name, NXuser user);
	
	/**
	 * Get all NXuser nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXuser for that node.
	 */
	public Map<String, NXuser> getAllUser();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param user the child nodes to add 
	 */
	
	public void setAllUser(Map<String, NXuser> user);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXsample getSample();
	
	/**
	 * 
	 * @param sampleGroup the sampleGroup
	 */
	public void setSample(NXsample sampleGroup);

	/**
	 * Get a NXsample node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsample for that node.
	 */
	public NXsample getSample(String name);
	
	/**
	 * Set a NXsample node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param sample the value to set
	 */
	public void setSample(String name, NXsample sample);
	
	/**
	 * Get all NXsample nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXsample for that node.
	 */
	public Map<String, NXsample> getAllSample();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param sample the child nodes to add 
	 */
	
	public void setAllSample(Map<String, NXsample> sample);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXinstrument getInstrument();
	
	/**
	 * 
	 * @param instrumentGroup the instrumentGroup
	 */
	public void setInstrument(NXinstrument instrumentGroup);

	/**
	 * Get a NXinstrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXinstrument for that node.
	 */
	public NXinstrument getInstrument(String name);
	
	/**
	 * Set a NXinstrument node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param instrument the value to set
	 */
	public void setInstrument(String name, NXinstrument instrument);
	
	/**
	 * Get all NXinstrument nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXinstrument for that node.
	 */
	public Map<String, NXinstrument> getAllInstrument();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param instrument the child nodes to add 
	 */
	
	public void setAllInstrument(Map<String, NXinstrument> instrument);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXcollection getCollection();
	
	/**
	 * 
	 * @param collectionGroup the collectionGroup
	 */
	public void setCollection(NXcollection collectionGroup);

	/**
	 * Get a NXcollection node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcollection for that node.
	 */
	public NXcollection getCollection(String name);
	
	/**
	 * Set a NXcollection node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param collection the value to set
	 */
	public void setCollection(String name, NXcollection collection);
	
	/**
	 * Get all NXcollection nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXcollection for that node.
	 */
	public Map<String, NXcollection> getAllCollection();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param collection the child nodes to add 
	 */
	
	public void setAllCollection(Map<String, NXcollection> collection);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXmonitor getMonitor();
	
	/**
	 * 
	 * @param monitorGroup the monitorGroup
	 */
	public void setMonitor(NXmonitor monitorGroup);

	/**
	 * Get a NXmonitor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmonitor for that node.
	 */
	public NXmonitor getMonitor(String name);
	
	/**
	 * Set a NXmonitor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param monitor the value to set
	 */
	public void setMonitor(String name, NXmonitor monitor);
	
	/**
	 * Get all NXmonitor nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmonitor for that node.
	 */
	public Map<String, NXmonitor> getAllMonitor();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param monitor the child nodes to add 
	 */
	
	public void setAllMonitor(Map<String, NXmonitor> monitor);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXparameters getParameters();
	
	/**
	 * 
	 * @param parametersGroup the parametersGroup
	 */
	public void setParameters(NXparameters parametersGroup);

	/**
	 * Get a NXparameters node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXparameters for that node.
	 */
	public NXparameters getParameters(String name);
	
	/**
	 * Set a NXparameters node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param parameters the value to set
	 */
	public void setParameters(String name, NXparameters parameters);
	
	/**
	 * Get all NXparameters nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXparameters for that node.
	 */
	public Map<String, NXparameters> getAllParameters();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param parameters the child nodes to add 
	 */
	
	public void setAllParameters(Map<String, NXparameters> parameters);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXprocess getProcess();
	
	/**
	 * 
	 * @param processGroup the processGroup
	 */
	public void setProcess(NXprocess processGroup);

	/**
	 * Get a NXprocess node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public NXprocess getProcess(String name);
	
	/**
	 * Set a NXprocess node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param process the value to set
	 */
	public void setProcess(String name, NXprocess process);
	
	/**
	 * Get all NXprocess nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public Map<String, NXprocess> getAllProcess();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param process the child nodes to add 
	 */
	
	public void setAllProcess(Map<String, NXprocess> process);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXsubentry getSubentry();
	
	/**
	 * 
	 * @param subentryGroup the subentryGroup
	 */
	public void setSubentry(NXsubentry subentryGroup);

	/**
	 * Get a NXsubentry node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsubentry for that node.
	 */
	public NXsubentry getSubentry(String name);
	
	/**
	 * Set a NXsubentry node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param subentry the value to set
	 */
	public void setSubentry(String name, NXsubentry subentry);
	
	/**
	 * Get all NXsubentry nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXsubentry for that node.
	 */
	public Map<String, NXsubentry> getAllSubentry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param subentry the child nodes to add 
	 */
	
	public void setAllSubentry(Map<String, NXsubentry> subentry);
	

}
