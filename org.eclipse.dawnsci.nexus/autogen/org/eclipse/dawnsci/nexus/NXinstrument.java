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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Collection of the components of the instrument or beamline.
 * Template of instrument descriptions comprising various beamline components.
 * Each component will also be a NeXus group defined by its distance from the
 * sample. Negative distances represent beamline components that are before the
 * sample while positive distances represent components that are after the sample.
 * This device allows the unique identification of beamline components in a way
 * that is valid for both reactor and pulsed instrumentation.
 * 
 */
public interface NXinstrument extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_NAME_ATTRIBUTE_SHORT_NAME = "short_name";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Name of instrument
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Name of instrument
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of instrument
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of instrument
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * short name for instrument, perhaps the acronym
	 * 
	 * @return  the value.
	 */
	public String getNameAttributeShort_name();
	
	/**
	 * short name for instrument, perhaps the acronym
	 * 
	 * @param short_nameValue the short_nameValue
	 */
	public void setNameAttributeShort_name(String short_nameValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXaperture getAperture();
	
	/**
	 * 
	 * @param apertureGroup the apertureGroup
	 */
	public void setAperture(NXaperture apertureGroup);

	/**
	 * Get a NXaperture node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public NXaperture getAperture(String name);
	
	/**
	 * Set a NXaperture node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param aperture the value to set
	 */
	public void setAperture(String name, NXaperture aperture);
	
	/**
	 * Get all NXaperture nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public Map<String, NXaperture> getAllAperture();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param aperture the child nodes to add 
	 */
	
	public void setAllAperture(Map<String, NXaperture> aperture);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXattenuator getAttenuator();
	
	/**
	 * 
	 * @param attenuatorGroup the attenuatorGroup
	 */
	public void setAttenuator(NXattenuator attenuatorGroup);

	/**
	 * Get a NXattenuator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXattenuator for that node.
	 */
	public NXattenuator getAttenuator(String name);
	
	/**
	 * Set a NXattenuator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param attenuator the value to set
	 */
	public void setAttenuator(String name, NXattenuator attenuator);
	
	/**
	 * Get all NXattenuator nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXattenuator for that node.
	 */
	public Map<String, NXattenuator> getAllAttenuator();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param attenuator the child nodes to add 
	 */
	
	public void setAllAttenuator(Map<String, NXattenuator> attenuator);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXbeam getBeam();
	
	/**
	 * 
	 * @param beamGroup the beamGroup
	 */
	public void setBeam(NXbeam beamGroup);

	/**
	 * Get a NXbeam node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public NXbeam getBeam(String name);
	
	/**
	 * Set a NXbeam node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param beam the value to set
	 */
	public void setBeam(String name, NXbeam beam);
	
	/**
	 * Get all NXbeam nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public Map<String, NXbeam> getAllBeam();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param beam the child nodes to add 
	 */
	
	public void setAllBeam(Map<String, NXbeam> beam);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXbeam_stop getBeam_stop();
	
	/**
	 * 
	 * @param beam_stopGroup the beam_stopGroup
	 */
	public void setBeam_stop(NXbeam_stop beam_stopGroup);

	/**
	 * Get a NXbeam_stop node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXbeam_stop for that node.
	 */
	public NXbeam_stop getBeam_stop(String name);
	
	/**
	 * Set a NXbeam_stop node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param beam_stop the value to set
	 */
	public void setBeam_stop(String name, NXbeam_stop beam_stop);
	
	/**
	 * Get all NXbeam_stop nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXbeam_stop for that node.
	 */
	public Map<String, NXbeam_stop> getAllBeam_stop();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param beam_stop the child nodes to add 
	 */
	
	public void setAllBeam_stop(Map<String, NXbeam_stop> beam_stop);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXbending_magnet getBending_magnet();
	
	/**
	 * 
	 * @param bending_magnetGroup the bending_magnetGroup
	 */
	public void setBending_magnet(NXbending_magnet bending_magnetGroup);

	/**
	 * Get a NXbending_magnet node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXbending_magnet for that node.
	 */
	public NXbending_magnet getBending_magnet(String name);
	
	/**
	 * Set a NXbending_magnet node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param bending_magnet the value to set
	 */
	public void setBending_magnet(String name, NXbending_magnet bending_magnet);
	
	/**
	 * Get all NXbending_magnet nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXbending_magnet for that node.
	 */
	public Map<String, NXbending_magnet> getAllBending_magnet();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param bending_magnet the child nodes to add 
	 */
	
	public void setAllBending_magnet(Map<String, NXbending_magnet> bending_magnet);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXcollimator getCollimator();
	
	/**
	 * 
	 * @param collimatorGroup the collimatorGroup
	 */
	public void setCollimator(NXcollimator collimatorGroup);

	/**
	 * Get a NXcollimator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcollimator for that node.
	 */
	public NXcollimator getCollimator(String name);
	
	/**
	 * Set a NXcollimator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param collimator the value to set
	 */
	public void setCollimator(String name, NXcollimator collimator);
	
	/**
	 * Get all NXcollimator nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXcollimator for that node.
	 */
	public Map<String, NXcollimator> getAllCollimator();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param collimator the child nodes to add 
	 */
	
	public void setAllCollimator(Map<String, NXcollimator> collimator);
	

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
	public NXcapillary getCapillary();
	
	/**
	 * 
	 * @param capillaryGroup the capillaryGroup
	 */
	public void setCapillary(NXcapillary capillaryGroup);

	/**
	 * Get a NXcapillary node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcapillary for that node.
	 */
	public NXcapillary getCapillary(String name);
	
	/**
	 * Set a NXcapillary node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param capillary the value to set
	 */
	public void setCapillary(String name, NXcapillary capillary);
	
	/**
	 * Get all NXcapillary nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXcapillary for that node.
	 */
	public Map<String, NXcapillary> getAllCapillary();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param capillary the child nodes to add 
	 */
	
	public void setAllCapillary(Map<String, NXcapillary> capillary);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXcrystal getCrystal();
	
	/**
	 * 
	 * @param crystalGroup the crystalGroup
	 */
	public void setCrystal(NXcrystal crystalGroup);

	/**
	 * Get a NXcrystal node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcrystal for that node.
	 */
	public NXcrystal getCrystal(String name);
	
	/**
	 * Set a NXcrystal node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param crystal the value to set
	 */
	public void setCrystal(String name, NXcrystal crystal);
	
	/**
	 * Get all NXcrystal nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXcrystal for that node.
	 */
	public Map<String, NXcrystal> getAllCrystal();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param crystal the child nodes to add 
	 */
	
	public void setAllCrystal(Map<String, NXcrystal> crystal);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXdetector getDetector();
	
	/**
	 * 
	 * @param detectorGroup the detectorGroup
	 */
	public void setDetector(NXdetector detectorGroup);

	/**
	 * Get a NXdetector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdetector for that node.
	 */
	public NXdetector getDetector(String name);
	
	/**
	 * Set a NXdetector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param detector the value to set
	 */
	public void setDetector(String name, NXdetector detector);
	
	/**
	 * Get all NXdetector nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdetector for that node.
	 */
	public Map<String, NXdetector> getAllDetector();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param detector the child nodes to add 
	 */
	
	public void setAllDetector(Map<String, NXdetector> detector);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXdetector_group getDetector_group();
	
	/**
	 * 
	 * @param detector_groupGroup the detector_groupGroup
	 */
	public void setDetector_group(NXdetector_group detector_groupGroup);

	/**
	 * Get a NXdetector_group node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdetector_group for that node.
	 */
	public NXdetector_group getDetector_group(String name);
	
	/**
	 * Set a NXdetector_group node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param detector_group the value to set
	 */
	public void setDetector_group(String name, NXdetector_group detector_group);
	
	/**
	 * Get all NXdetector_group nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdetector_group for that node.
	 */
	public Map<String, NXdetector_group> getAllDetector_group();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param detector_group the child nodes to add 
	 */
	
	public void setAllDetector_group(Map<String, NXdetector_group> detector_group);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXdisk_chopper getDisk_chopper();
	
	/**
	 * 
	 * @param disk_chopperGroup the disk_chopperGroup
	 */
	public void setDisk_chopper(NXdisk_chopper disk_chopperGroup);

	/**
	 * Get a NXdisk_chopper node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdisk_chopper for that node.
	 */
	public NXdisk_chopper getDisk_chopper(String name);
	
	/**
	 * Set a NXdisk_chopper node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param disk_chopper the value to set
	 */
	public void setDisk_chopper(String name, NXdisk_chopper disk_chopper);
	
	/**
	 * Get all NXdisk_chopper nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXdisk_chopper for that node.
	 */
	public Map<String, NXdisk_chopper> getAllDisk_chopper();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param disk_chopper the child nodes to add 
	 */
	
	public void setAllDisk_chopper(Map<String, NXdisk_chopper> disk_chopper);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXevent_data getEvent_data();
	
	/**
	 * 
	 * @param event_dataGroup the event_dataGroup
	 */
	public void setEvent_data(NXevent_data event_dataGroup);

	/**
	 * Get a NXevent_data node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXevent_data for that node.
	 */
	public NXevent_data getEvent_data(String name);
	
	/**
	 * Set a NXevent_data node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param event_data the value to set
	 */
	public void setEvent_data(String name, NXevent_data event_data);
	
	/**
	 * Get all NXevent_data nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXevent_data for that node.
	 */
	public Map<String, NXevent_data> getAllEvent_data();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param event_data the child nodes to add 
	 */
	
	public void setAllEvent_data(Map<String, NXevent_data> event_data);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXfermi_chopper getFermi_chopper();
	
	/**
	 * 
	 * @param fermi_chopperGroup the fermi_chopperGroup
	 */
	public void setFermi_chopper(NXfermi_chopper fermi_chopperGroup);

	/**
	 * Get a NXfermi_chopper node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfermi_chopper for that node.
	 */
	public NXfermi_chopper getFermi_chopper(String name);
	
	/**
	 * Set a NXfermi_chopper node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param fermi_chopper the value to set
	 */
	public void setFermi_chopper(String name, NXfermi_chopper fermi_chopper);
	
	/**
	 * Get all NXfermi_chopper nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXfermi_chopper for that node.
	 */
	public Map<String, NXfermi_chopper> getAllFermi_chopper();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param fermi_chopper the child nodes to add 
	 */
	
	public void setAllFermi_chopper(Map<String, NXfermi_chopper> fermi_chopper);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXfilter getFilter();
	
	/**
	 * 
	 * @param filterGroup the filterGroup
	 */
	public void setFilter(NXfilter filterGroup);

	/**
	 * Get a NXfilter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfilter for that node.
	 */
	public NXfilter getFilter(String name);
	
	/**
	 * Set a NXfilter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param filter the value to set
	 */
	public void setFilter(String name, NXfilter filter);
	
	/**
	 * Get all NXfilter nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXfilter for that node.
	 */
	public Map<String, NXfilter> getAllFilter();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param filter the child nodes to add 
	 */
	
	public void setAllFilter(Map<String, NXfilter> filter);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXflipper getFlipper();
	
	/**
	 * 
	 * @param flipperGroup the flipperGroup
	 */
	public void setFlipper(NXflipper flipperGroup);

	/**
	 * Get a NXflipper node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXflipper for that node.
	 */
	public NXflipper getFlipper(String name);
	
	/**
	 * Set a NXflipper node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param flipper the value to set
	 */
	public void setFlipper(String name, NXflipper flipper);
	
	/**
	 * Get all NXflipper nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXflipper for that node.
	 */
	public Map<String, NXflipper> getAllFlipper();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param flipper the child nodes to add 
	 */
	
	public void setAllFlipper(Map<String, NXflipper> flipper);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXguide getGuide();
	
	/**
	 * 
	 * @param guideGroup the guideGroup
	 */
	public void setGuide(NXguide guideGroup);

	/**
	 * Get a NXguide node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXguide for that node.
	 */
	public NXguide getGuide(String name);
	
	/**
	 * Set a NXguide node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param guide the value to set
	 */
	public void setGuide(String name, NXguide guide);
	
	/**
	 * Get all NXguide nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXguide for that node.
	 */
	public Map<String, NXguide> getAllGuide();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param guide the child nodes to add 
	 */
	
	public void setAllGuide(Map<String, NXguide> guide);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXinsertion_device getInsertion_device();
	
	/**
	 * 
	 * @param insertion_deviceGroup the insertion_deviceGroup
	 */
	public void setInsertion_device(NXinsertion_device insertion_deviceGroup);

	/**
	 * Get a NXinsertion_device node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXinsertion_device for that node.
	 */
	public NXinsertion_device getInsertion_device(String name);
	
	/**
	 * Set a NXinsertion_device node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param insertion_device the value to set
	 */
	public void setInsertion_device(String name, NXinsertion_device insertion_device);
	
	/**
	 * Get all NXinsertion_device nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXinsertion_device for that node.
	 */
	public Map<String, NXinsertion_device> getAllInsertion_device();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param insertion_device the child nodes to add 
	 */
	
	public void setAllInsertion_device(Map<String, NXinsertion_device> insertion_device);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXmirror getMirror();
	
	/**
	 * 
	 * @param mirrorGroup the mirrorGroup
	 */
	public void setMirror(NXmirror mirrorGroup);

	/**
	 * Get a NXmirror node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmirror for that node.
	 */
	public NXmirror getMirror(String name);
	
	/**
	 * Set a NXmirror node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param mirror the value to set
	 */
	public void setMirror(String name, NXmirror mirror);
	
	/**
	 * Get all NXmirror nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmirror for that node.
	 */
	public Map<String, NXmirror> getAllMirror();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param mirror the child nodes to add 
	 */
	
	public void setAllMirror(Map<String, NXmirror> mirror);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXmoderator getModerator();
	
	/**
	 * 
	 * @param moderatorGroup the moderatorGroup
	 */
	public void setModerator(NXmoderator moderatorGroup);

	/**
	 * Get a NXmoderator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmoderator for that node.
	 */
	public NXmoderator getModerator(String name);
	
	/**
	 * Set a NXmoderator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param moderator the value to set
	 */
	public void setModerator(String name, NXmoderator moderator);
	
	/**
	 * Get all NXmoderator nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmoderator for that node.
	 */
	public Map<String, NXmoderator> getAllModerator();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param moderator the child nodes to add 
	 */
	
	public void setAllModerator(Map<String, NXmoderator> moderator);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXmonochromator getMonochromator();
	
	/**
	 * 
	 * @param monochromatorGroup the monochromatorGroup
	 */
	public void setMonochromator(NXmonochromator monochromatorGroup);

	/**
	 * Get a NXmonochromator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public NXmonochromator getMonochromator(String name);
	
	/**
	 * Set a NXmonochromator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param monochromator the value to set
	 */
	public void setMonochromator(String name, NXmonochromator monochromator);
	
	/**
	 * Get all NXmonochromator nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public Map<String, NXmonochromator> getAllMonochromator();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param monochromator the child nodes to add 
	 */
	
	public void setAllMonochromator(Map<String, NXmonochromator> monochromator);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXpolarizer getPolarizer();
	
	/**
	 * 
	 * @param polarizerGroup the polarizerGroup
	 */
	public void setPolarizer(NXpolarizer polarizerGroup);

	/**
	 * Get a NXpolarizer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpolarizer for that node.
	 */
	public NXpolarizer getPolarizer(String name);
	
	/**
	 * Set a NXpolarizer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param polarizer the value to set
	 */
	public void setPolarizer(String name, NXpolarizer polarizer);
	
	/**
	 * Get all NXpolarizer nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXpolarizer for that node.
	 */
	public Map<String, NXpolarizer> getAllPolarizer();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param polarizer the child nodes to add 
	 */
	
	public void setAllPolarizer(Map<String, NXpolarizer> polarizer);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXpositioner getPositioner();
	
	/**
	 * 
	 * @param positionerGroup the positionerGroup
	 */
	public void setPositioner(NXpositioner positionerGroup);

	/**
	 * Get a NXpositioner node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public NXpositioner getPositioner(String name);
	
	/**
	 * Set a NXpositioner node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param positioner the value to set
	 */
	public void setPositioner(String name, NXpositioner positioner);
	
	/**
	 * Get all NXpositioner nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public Map<String, NXpositioner> getAllPositioner();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param positioner the child nodes to add 
	 */
	
	public void setAllPositioner(Map<String, NXpositioner> positioner);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXsource getSource();
	
	/**
	 * 
	 * @param sourceGroup the sourceGroup
	 */
	public void setSource(NXsource sourceGroup);

	/**
	 * Get a NXsource node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsource for that node.
	 */
	public NXsource getSource(String name);
	
	/**
	 * Set a NXsource node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param source the value to set
	 */
	public void setSource(String name, NXsource source);
	
	/**
	 * Get all NXsource nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXsource for that node.
	 */
	public Map<String, NXsource> getAllSource();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param source the child nodes to add 
	 */
	
	public void setAllSource(Map<String, NXsource> source);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXtransformations getDiffractometer();
	
	/**
	 * 
	 * @param diffractometerGroup the diffractometerGroup
	 */
	public void setDiffractometer(NXtransformations diffractometerGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXvelocity_selector getVelocity_selector();
	
	/**
	 * 
	 * @param velocity_selectorGroup the velocity_selectorGroup
	 */
	public void setVelocity_selector(NXvelocity_selector velocity_selectorGroup);

	/**
	 * Get a NXvelocity_selector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXvelocity_selector for that node.
	 */
	public NXvelocity_selector getVelocity_selector(String name);
	
	/**
	 * Set a NXvelocity_selector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param velocity_selector the value to set
	 */
	public void setVelocity_selector(String name, NXvelocity_selector velocity_selector);
	
	/**
	 * Get all NXvelocity_selector nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXvelocity_selector for that node.
	 */
	public Map<String, NXvelocity_selector> getAllVelocity_selector();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param velocity_selector the child nodes to add 
	 */
	
	public void setAllVelocity_selector(Map<String, NXvelocity_selector> velocity_selector);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXxraylens getXraylens();
	
	/**
	 * 
	 * @param xraylensGroup the xraylensGroup
	 */
	public void setXraylens(NXxraylens xraylensGroup);

	/**
	 * Get a NXxraylens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXxraylens for that node.
	 */
	public NXxraylens getXraylens(String name);
	
	/**
	 * Set a NXxraylens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param xraylens the value to set
	 */
	public void setXraylens(String name, NXxraylens xraylens);
	
	/**
	 * Get all NXxraylens nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXxraylens for that node.
	 */
	public Map<String, NXxraylens> getAllXraylens();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param xraylens the child nodes to add 
	 */
	
	public void setAllXraylens(Map<String, NXxraylens> xraylens);
	

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}
