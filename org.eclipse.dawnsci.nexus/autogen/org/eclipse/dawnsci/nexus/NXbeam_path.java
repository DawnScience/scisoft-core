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
import org.eclipse.january.dataset.Dataset;

/**
 * A beam path consisting of one or more optical elements.
 * NXbeam_path is used in NXopt to describe the beam path, i.e. the arrangement
 * of optical elements between the excitation source and the sample, or between
 * the sample and the detector unit.
 * To describe the order of the elements, use 'order(NXtransformations)', where
 * each element's position points to the preceding element via '@depends_on'.
 * Special case beam splitter: A beam splitter is a device which separates the
 * beam into two or more beams. If such a device is part of the beam path use
 * two or more NXbeam_paths to describe the beam paths after the beam splitter.
 * In this case, in the dependency chain of the new beam paths, the first
 * elements each point to the beam splitter, as this is the previous element.
 * Describe the relevant optical elements in the beam path by using the
 * appropriate base classes. You may use as many elements as needed, also
 * several elements of the same type as long as each element has its own name.
 *
 */
public interface NXbeam_path extends NXobject {

	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Entry point of the dependency chain defined by the NXtransformations
	 * field, i.e. a link to the last element in the beam path.
	 * Example: /entry/instrument/beam_path/detector.
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Entry point of the dependency chain defined by the NXtransformations
	 * field, i.e. a link to the last element in the beam path.
	 * Example: /entry/instrument/beam_path/detector.
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Entry point of the dependency chain defined by the NXtransformations
	 * field, i.e. a link to the last element in the beam path.
	 * Example: /entry/instrument/beam_path/detector.
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Entry point of the dependency chain defined by the NXtransformations
	 * field, i.e. a link to the last element in the beam path.
	 * Example: /entry/instrument/beam_path/detector.
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Specify the order of the optical elements by defining a dependency chain.
	 * For each element, a '@depends_on' attribute should be used to state the
	 * position of the element in the beam path by pointing to the previous
	 * element. For the very first element, use the string "." instead.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Specify the order of the optical elements by defining a dependency chain.
	 * For each element, a '@depends_on' attribute should be used to state the
	 * position of the element in the beam path by pointing to the previous
	 * element. For the very first element, use the string "." instead.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Specify the order of the optical elements by defining a dependency chain.
	 * For each element, a '@depends_on' attribute should be used to state the
	 * position of the element in the beam path by pointing to the previous
	 * element. For the very first element, use the string "." instead.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Specify the order of the optical elements by defining a dependency chain.
	 * For each element, a '@depends_on' attribute should be used to state the
	 * position of the element in the beam path by pointing to the previous
	 * element. For the very first element, use the string "." instead.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Specify the order of the optical elements by defining a dependency chain.
	 * For each element, a '@depends_on' attribute should be used to state the
	 * position of the element in the beam path by pointing to the previous
	 * element. For the very first element, use the string "." instead.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Specify the order of the optical elements by defining a dependency chain.
	 * For each element, a '@depends_on' attribute should be used to state the
	 * position of the element in the beam path by pointing to the previous
	 * element. For the very first element, use the string "." instead.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * Excitation source. One or more may be needed (e.g. two for a pump-probe
	 * setup with one pump and one probe beam).
	 * Depending on the source type, different properties are relevant, which
	 * are provided through the respective base class (e.g. use NXopt_source for
	 * lamps or lasers, NXchem_source for chemical reaction etc.).
	 * Some base classes are incomplete (NXchem_source, NXbio_source); the
	 * expertise of the respective communities is needed.
	 *
	 * @return  the value.
	 */
	public NXsource getSource();

	/**
	 * Excitation source. One or more may be needed (e.g. two for a pump-probe
	 * setup with one pump and one probe beam).
	 * Depending on the source type, different properties are relevant, which
	 * are provided through the respective base class (e.g. use NXopt_source for
	 * lamps or lasers, NXchem_source for chemical reaction etc.).
	 * Some base classes are incomplete (NXchem_source, NXbio_source); the
	 * expertise of the respective communities is needed.
	 *
	 * @param sourceGroup the sourceGroup
	 */
	public void setSource(NXsource sourceGroup);

	/**
	 * Get a NXsource node by name:
	 * <ul>
	 * <li>
	 * Excitation source. One or more may be needed (e.g. two for a pump-probe
	 * setup with one pump and one probe beam).
	 * Depending on the source type, different properties are relevant, which
	 * are provided through the respective base class (e.g. use NXopt_source for
	 * lamps or lasers, NXchem_source for chemical reaction etc.).
	 * Some base classes are incomplete (NXchem_source, NXbio_source); the
	 * expertise of the respective communities is needed.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsource for that node.
	 */
	public NXsource getSource(String name);

	/**
	 * Set a NXsource node by name:
	 * <ul>
	 * <li>
	 * Excitation source. One or more may be needed (e.g. two for a pump-probe
	 * setup with one pump and one probe beam).
	 * Depending on the source type, different properties are relevant, which
	 * are provided through the respective base class (e.g. use NXopt_source for
	 * lamps or lasers, NXchem_source for chemical reaction etc.).
	 * Some base classes are incomplete (NXchem_source, NXbio_source); the
	 * expertise of the respective communities is needed.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param source the value to set
	 */
	public void setSource(String name, NXsource source);

	/**
	 * Get all NXsource nodes:
	 * <ul>
	 * <li>
	 * Excitation source. One or more may be needed (e.g. two for a pump-probe
	 * setup with one pump and one probe beam).
	 * Depending on the source type, different properties are relevant, which
	 * are provided through the respective base class (e.g. use NXopt_source for
	 * lamps or lasers, NXchem_source for chemical reaction etc.).
	 * Some base classes are incomplete (NXchem_source, NXbio_source); the
	 * expertise of the respective communities is needed.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXsource for that node.
	 */
	public Map<String, NXsource> getAllSource();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Excitation source. One or more may be needed (e.g. two for a pump-probe
	 * setup with one pump and one probe beam).
	 * Depending on the source type, different properties are relevant, which
	 * are provided through the respective base class (e.g. use NXopt_source for
	 * lamps or lasers, NXchem_source for chemical reaction etc.).
	 * Some base classes are incomplete (NXchem_source, NXbio_source); the
	 * expertise of the respective communities is needed.</li>
	 * </ul>
	 *
	 * @param source the child nodes to add
	 */

	public void setAllSource(Map<String, NXsource> source);


	/**
	 * Use this field to describe a simple pinhole (round geometry). Define its
	 * dimension using 'diameter'. For more complex geometries, 'NXaperture'
	 * should be used.
	 *
	 * @return  the value.
	 */
	public NXpinhole getPinhole();

	/**
	 * Use this field to describe a simple pinhole (round geometry). Define its
	 * dimension using 'diameter'. For more complex geometries, 'NXaperture'
	 * should be used.
	 *
	 * @param pinholeGroup the pinholeGroup
	 */
	public void setPinhole(NXpinhole pinholeGroup);

	/**
	 * Get a NXpinhole node by name:
	 * <ul>
	 * <li>
	 * Use this field to describe a simple pinhole (round geometry). Define its
	 * dimension using 'diameter'. For more complex geometries, 'NXaperture'
	 * should be used.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpinhole for that node.
	 */
	public NXpinhole getPinhole(String name);

	/**
	 * Set a NXpinhole node by name:
	 * <ul>
	 * <li>
	 * Use this field to describe a simple pinhole (round geometry). Define its
	 * dimension using 'diameter'. For more complex geometries, 'NXaperture'
	 * should be used.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param pinhole the value to set
	 */
	public void setPinhole(String name, NXpinhole pinhole);

	/**
	 * Get all NXpinhole nodes:
	 * <ul>
	 * <li>
	 * Use this field to describe a simple pinhole (round geometry). Define its
	 * dimension using 'diameter'. For more complex geometries, 'NXaperture'
	 * should be used.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpinhole for that node.
	 */
	public Map<String, NXpinhole> getAllPinhole();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Use this field to describe a simple pinhole (round geometry). Define its
	 * dimension using 'diameter'. For more complex geometries, 'NXaperture'
	 * should be used.</li>
	 * </ul>
	 *
	 * @param pinhole the child nodes to add
	 */

	public void setAllPinhole(Map<String, NXpinhole> pinhole);


	/**
	 * Use this field to describe a simple slit (rectangular geometry). Define
	 * its dimensions using 'x_gap' and 'y_gap'. For more complex geometries,
	 * 'NXaperture' should be used.
	 *
	 * @return  the value.
	 */
	public NXslit getSlit();

	/**
	 * Use this field to describe a simple slit (rectangular geometry). Define
	 * its dimensions using 'x_gap' and 'y_gap'. For more complex geometries,
	 * 'NXaperture' should be used.
	 *
	 * @param slitGroup the slitGroup
	 */
	public void setSlit(NXslit slitGroup);

	/**
	 * Get a NXslit node by name:
	 * <ul>
	 * <li>
	 * Use this field to describe a simple slit (rectangular geometry). Define
	 * its dimensions using 'x_gap' and 'y_gap'. For more complex geometries,
	 * 'NXaperture' should be used.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXslit for that node.
	 */
	public NXslit getSlit(String name);

	/**
	 * Set a NXslit node by name:
	 * <ul>
	 * <li>
	 * Use this field to describe a simple slit (rectangular geometry). Define
	 * its dimensions using 'x_gap' and 'y_gap'. For more complex geometries,
	 * 'NXaperture' should be used.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param slit the value to set
	 */
	public void setSlit(String name, NXslit slit);

	/**
	 * Get all NXslit nodes:
	 * <ul>
	 * <li>
	 * Use this field to describe a simple slit (rectangular geometry). Define
	 * its dimensions using 'x_gap' and 'y_gap'. For more complex geometries,
	 * 'NXaperture' should be used.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXslit for that node.
	 */
	public Map<String, NXslit> getAllSlit();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Use this field to describe a simple slit (rectangular geometry). Define
	 * its dimensions using 'x_gap' and 'y_gap'. For more complex geometries,
	 * 'NXaperture' should be used.</li>
	 * </ul>
	 *
	 * @param slit the child nodes to add
	 */

	public void setAllSlit(Map<String, NXslit> slit);


	/**
	 * Use this field to describe an aperture. To specify a window, use the
	 * field 'window_NUMBER(NXaperture)'.
	 *
	 * @return  the value.
	 */
	public NXaperture getAperture_number();

	/**
	 * Use this field to describe an aperture. To specify a window, use the
	 * field 'window_NUMBER(NXaperture)'.
	 *
	 * @param aperture_numberGroup the aperture_numberGroup
	 */
	public void setAperture_number(NXaperture aperture_numberGroup);

	/**
	 * A window, e.g. an entry or exit window of a cryostat.
	 *
	 * @return  the value.
	 */
	public NXaperture getWindow_number();

	/**
	 * A window, e.g. an entry or exit window of a cryostat.
	 *
	 * @param window_numberGroup the window_numberGroup
	 */
	public void setWindow_number(NXaperture window_numberGroup);

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
	public NXfilter getFilter_number();

	/**
	 *
	 * @param filter_numberGroup the filter_numberGroup
	 */
	public void setFilter_number(NXfilter filter_numberGroup);

	/**
	 * A device that reduces the intensity of a beam by attenuation.
	 *
	 * @return  the value.
	 */
	public NXattenuator getAttenuator();

	/**
	 * A device that reduces the intensity of a beam by attenuation.
	 *
	 * @param attenuatorGroup the attenuatorGroup
	 */
	public void setAttenuator(NXattenuator attenuatorGroup);

	/**
	 * Get a NXattenuator node by name:
	 * <ul>
	 * <li>
	 * A device that reduces the intensity of a beam by attenuation.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXattenuator for that node.
	 */
	public NXattenuator getAttenuator(String name);

	/**
	 * Set a NXattenuator node by name:
	 * <ul>
	 * <li>
	 * A device that reduces the intensity of a beam by attenuation.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param attenuator the value to set
	 */
	public void setAttenuator(String name, NXattenuator attenuator);

	/**
	 * Get all NXattenuator nodes:
	 * <ul>
	 * <li>
	 * A device that reduces the intensity of a beam by attenuation.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXattenuator for that node.
	 */
	public Map<String, NXattenuator> getAllAttenuator();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A device that reduces the intensity of a beam by attenuation.</li>
	 * </ul>
	 *
	 * @param attenuator the child nodes to add
	 */

	public void setAllAttenuator(Map<String, NXattenuator> attenuator);

	// Unprocessed group:
	// Unprocessed group:

	/**
	 * A diffraction grating. Define relevant parameters in the corresponding
	 * fields, e.g. order of diffration (diffraction_order) or angular
	 * dispersion (angular_dispersion).
	 *
	 * @return  the value.
	 */
	public NXgrating getGrating();

	/**
	 * A diffraction grating. Define relevant parameters in the corresponding
	 * fields, e.g. order of diffration (diffraction_order) or angular
	 * dispersion (angular_dispersion).
	 *
	 * @param gratingGroup the gratingGroup
	 */
	public void setGrating(NXgrating gratingGroup);

	/**
	 * Get a NXgrating node by name:
	 * <ul>
	 * <li>
	 * A diffraction grating. Define relevant parameters in the corresponding
	 * fields, e.g. order of diffration (diffraction_order) or angular
	 * dispersion (angular_dispersion).</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgrating for that node.
	 */
	public NXgrating getGrating(String name);

	/**
	 * Set a NXgrating node by name:
	 * <ul>
	 * <li>
	 * A diffraction grating. Define relevant parameters in the corresponding
	 * fields, e.g. order of diffration (diffraction_order) or angular
	 * dispersion (angular_dispersion).</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param grating the value to set
	 */
	public void setGrating(String name, NXgrating grating);

	/**
	 * Get all NXgrating nodes:
	 * <ul>
	 * <li>
	 * A diffraction grating. Define relevant parameters in the corresponding
	 * fields, e.g. order of diffration (diffraction_order) or angular
	 * dispersion (angular_dispersion).</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXgrating for that node.
	 */
	public Map<String, NXgrating> getAllGrating();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A diffraction grating. Define relevant parameters in the corresponding
	 * fields, e.g. order of diffration (diffraction_order) or angular
	 * dispersion (angular_dispersion).</li>
	 * </ul>
	 *
	 * @param grating the child nodes to add
	 */

	public void setAllGrating(Map<String, NXgrating> grating);


	/**
	 * A device blocking the beam in a temporal periodic pattern, e.g. a optical
	 * chopper wheel. Specify the frequency range using 'min_frequency' and
	 * 'max_frequency'.
	 *
	 * @return  the value.
	 */
	public NXdisk_chopper getDisk_chopper();

	/**
	 * A device blocking the beam in a temporal periodic pattern, e.g. a optical
	 * chopper wheel. Specify the frequency range using 'min_frequency' and
	 * 'max_frequency'.
	 *
	 * @param disk_chopperGroup the disk_chopperGroup
	 */
	public void setDisk_chopper(NXdisk_chopper disk_chopperGroup);

	/**
	 * Get a NXdisk_chopper node by name:
	 * <ul>
	 * <li>
	 * A device blocking the beam in a temporal periodic pattern, e.g. a optical
	 * chopper wheel. Specify the frequency range using 'min_frequency' and
	 * 'max_frequency'.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdisk_chopper for that node.
	 */
	public NXdisk_chopper getDisk_chopper(String name);

	/**
	 * Set a NXdisk_chopper node by name:
	 * <ul>
	 * <li>
	 * A device blocking the beam in a temporal periodic pattern, e.g. a optical
	 * chopper wheel. Specify the frequency range using 'min_frequency' and
	 * 'max_frequency'.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param disk_chopper the value to set
	 */
	public void setDisk_chopper(String name, NXdisk_chopper disk_chopper);

	/**
	 * Get all NXdisk_chopper nodes:
	 * <ul>
	 * <li>
	 * A device blocking the beam in a temporal periodic pattern, e.g. a optical
	 * chopper wheel. Specify the frequency range using 'min_frequency' and
	 * 'max_frequency'.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdisk_chopper for that node.
	 */
	public Map<String, NXdisk_chopper> getAllDisk_chopper();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A device blocking the beam in a temporal periodic pattern, e.g. a optical
	 * chopper wheel. Specify the frequency range using 'min_frequency' and
	 * 'max_frequency'.</li>
	 * </ul>
	 *
	 * @param disk_chopper the child nodes to add
	 */

	public void setAllDisk_chopper(Map<String, NXdisk_chopper> disk_chopper);


	/**
	 * A monochromator or spectrometer.
	 *
	 * @return  the value.
	 */
	public NXmonochromator getMonochromator();

	/**
	 * A monochromator or spectrometer.
	 *
	 * @param monochromatorGroup the monochromatorGroup
	 */
	public void setMonochromator(NXmonochromator monochromatorGroup);

	/**
	 * Get a NXmonochromator node by name:
	 * <ul>
	 * <li>
	 * A monochromator or spectrometer.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public NXmonochromator getMonochromator(String name);

	/**
	 * Set a NXmonochromator node by name:
	 * <ul>
	 * <li>
	 * A monochromator or spectrometer.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param monochromator the value to set
	 */
	public void setMonochromator(String name, NXmonochromator monochromator);

	/**
	 * Get all NXmonochromator nodes:
	 * <ul>
	 * <li>
	 * A monochromator or spectrometer.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public Map<String, NXmonochromator> getAllMonochromator();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A monochromator or spectrometer.</li>
	 * </ul>
	 *
	 * @param monochromator the child nodes to add
	 */

	public void setAllMonochromator(Map<String, NXmonochromator> monochromator);

	// Unprocessed group:
	// Unprocessed group:

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
	 *
	 * @return  the value.
	 */
	public NXpolarizer_opt getPolarizer_opt();

	/**
	 *
	 * @param polarizer_optGroup the polarizer_optGroup
	 */
	public void setPolarizer_opt(NXpolarizer_opt polarizer_optGroup);

	/**
	 * Get a NXpolarizer_opt node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpolarizer_opt for that node.
	 */
	public NXpolarizer_opt getPolarizer_opt(String name);

	/**
	 * Set a NXpolarizer_opt node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param polarizer_opt the value to set
	 */
	public void setPolarizer_opt(String name, NXpolarizer_opt polarizer_opt);

	/**
	 * Get all NXpolarizer_opt nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpolarizer_opt for that node.
	 */
	public Map<String, NXpolarizer_opt> getAllPolarizer_opt();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param polarizer_opt the child nodes to add
	 */

	public void setAllPolarizer_opt(Map<String, NXpolarizer_opt> polarizer_opt);


	/**
	 *
	 * @return  the value.
	 */
	public NXbeam_splitter getBeam_splitter();

	/**
	 *
	 * @param beam_splitterGroup the beam_splitterGroup
	 */
	public void setBeam_splitter(NXbeam_splitter beam_splitterGroup);

	/**
	 * Get a NXbeam_splitter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXbeam_splitter for that node.
	 */
	public NXbeam_splitter getBeam_splitter(String name);

	/**
	 * Set a NXbeam_splitter node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param beam_splitter the value to set
	 */
	public void setBeam_splitter(String name, NXbeam_splitter beam_splitter);

	/**
	 * Get all NXbeam_splitter nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXbeam_splitter for that node.
	 */
	public Map<String, NXbeam_splitter> getAllBeam_splitter();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param beam_splitter the child nodes to add
	 */

	public void setAllBeam_splitter(Map<String, NXbeam_splitter> beam_splitter);


	/**
	 *
	 * @return  the value.
	 */
	public NXwaveplate getWaveplate();

	/**
	 *
	 * @param waveplateGroup the waveplateGroup
	 */
	public void setWaveplate(NXwaveplate waveplateGroup);

	/**
	 * Get a NXwaveplate node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXwaveplate for that node.
	 */
	public NXwaveplate getWaveplate(String name);

	/**
	 * Set a NXwaveplate node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param waveplate the value to set
	 */
	public void setWaveplate(String name, NXwaveplate waveplate);

	/**
	 * Get all NXwaveplate nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXwaveplate for that node.
	 */
	public Map<String, NXwaveplate> getAllWaveplate();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param waveplate the child nodes to add
	 */

	public void setAllWaveplate(Map<String, NXwaveplate> waveplate);


	/**
	 *
	 * @return  the value.
	 */
	public NXlens_opt getLens_opt();

	/**
	 *
	 * @param lens_optGroup the lens_optGroup
	 */
	public void setLens_opt(NXlens_opt lens_optGroup);

	/**
	 * Get a NXlens_opt node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXlens_opt for that node.
	 */
	public NXlens_opt getLens_opt(String name);

	/**
	 * Set a NXlens_opt node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param lens_opt the value to set
	 */
	public void setLens_opt(String name, NXlens_opt lens_opt);

	/**
	 * Get all NXlens_opt nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_opt for that node.
	 */
	public Map<String, NXlens_opt> getAllLens_opt();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param lens_opt the child nodes to add
	 */

	public void setAllLens_opt(Map<String, NXlens_opt> lens_opt);


	/**
	 *
	 * @return  the value.
	 */
	public NXfiber getFiber();

	/**
	 *
	 * @param fiberGroup the fiberGroup
	 */
	public void setFiber(NXfiber fiberGroup);

	/**
	 * Get a NXfiber node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfiber for that node.
	 */
	public NXfiber getFiber(String name);

	/**
	 * Set a NXfiber node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fiber the value to set
	 */
	public void setFiber(String name, NXfiber fiber);

	/**
	 * Get all NXfiber nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfiber for that node.
	 */
	public Map<String, NXfiber> getAllFiber();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fiber the child nodes to add
	 */

	public void setAllFiber(Map<String, NXfiber> fiber);


}
