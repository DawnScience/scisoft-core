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

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXbeam_pathImpl extends NXobjectImpl implements NXbeam_path {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_SOURCE,
		NexusBaseClass.NX_PINHOLE,
		NexusBaseClass.NX_SLIT,
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_MIRROR,
		NexusBaseClass.NX_FILTER,
		NexusBaseClass.NX_ATTENUATOR,
		NexusBaseClass.NX_GRATING,
		NexusBaseClass.NX_DISK_CHOPPER,
		NexusBaseClass.NX_MONOCHROMATOR,
		NexusBaseClass.NX_XRAYLENS,
		NexusBaseClass.NX_POLARIZER_OPT,
		NexusBaseClass.NX_BEAM_SPLITTER,
		NexusBaseClass.NX_WAVEPLATE,
		NexusBaseClass.NX_LENS_OPT,
		NexusBaseClass.NX_FIBER);

	public NXbeam_pathImpl() {
		super();
	}

	public NXbeam_pathImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXbeam_path.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_BEAM_PATH;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getDepends_on() {
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
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

	@Override
	public NXsource getSource() {
		// dataNodeName = NX_SOURCE
		return getChild("source", NXsource.class);
	}

	@Override
	public void setSource(NXsource sourceGroup) {
		putChild("source", sourceGroup);
	}

	@Override
	public NXsource getSource(String name) {
		return getChild(name, NXsource.class);
	}

	@Override
	public void setSource(String name, NXsource source) {
		putChild(name, source);
	}

	@Override
	public Map<String, NXsource> getAllSource() {
		return getChildren(NXsource.class);
	}

	@Override
	public void setAllSource(Map<String, NXsource> source) {
		setChildren(source);
	}

	@Override
	public NXpinhole getPinhole() {
		// dataNodeName = NX_PINHOLE
		return getChild("pinhole", NXpinhole.class);
	}

	@Override
	public void setPinhole(NXpinhole pinholeGroup) {
		putChild("pinhole", pinholeGroup);
	}

	@Override
	public NXpinhole getPinhole(String name) {
		return getChild(name, NXpinhole.class);
	}

	@Override
	public void setPinhole(String name, NXpinhole pinhole) {
		putChild(name, pinhole);
	}

	@Override
	public Map<String, NXpinhole> getAllPinhole() {
		return getChildren(NXpinhole.class);
	}

	@Override
	public void setAllPinhole(Map<String, NXpinhole> pinhole) {
		setChildren(pinhole);
	}

	@Override
	public NXslit getSlit() {
		// dataNodeName = NX_SLIT
		return getChild("slit", NXslit.class);
	}

	@Override
	public void setSlit(NXslit slitGroup) {
		putChild("slit", slitGroup);
	}

	@Override
	public NXslit getSlit(String name) {
		return getChild(name, NXslit.class);
	}

	@Override
	public void setSlit(String name, NXslit slit) {
		putChild(name, slit);
	}

	@Override
	public Map<String, NXslit> getAllSlit() {
		return getChildren(NXslit.class);
	}

	@Override
	public void setAllSlit(Map<String, NXslit> slit) {
		setChildren(slit);
	}

	@Override
	public NXaperture getAperture_number() {
		// dataNodeName = NX_APERTURE_NUMBER
		return getChild("aperture_number", NXaperture.class);
	}

	@Override
	public void setAperture_number(NXaperture aperture_numberGroup) {
		putChild("aperture_number", aperture_numberGroup);
	}

	@Override
	public NXaperture getWindow_number() {
		// dataNodeName = NX_WINDOW_NUMBER
		return getChild("window_number", NXaperture.class);
	}

	@Override
	public void setWindow_number(NXaperture window_numberGroup) {
		putChild("window_number", window_numberGroup);
	}

	@Override
	public NXmirror getMirror() {
		// dataNodeName = NX_MIRROR
		return getChild("mirror", NXmirror.class);
	}

	@Override
	public void setMirror(NXmirror mirrorGroup) {
		putChild("mirror", mirrorGroup);
	}

	@Override
	public NXmirror getMirror(String name) {
		return getChild(name, NXmirror.class);
	}

	@Override
	public void setMirror(String name, NXmirror mirror) {
		putChild(name, mirror);
	}

	@Override
	public Map<String, NXmirror> getAllMirror() {
		return getChildren(NXmirror.class);
	}

	@Override
	public void setAllMirror(Map<String, NXmirror> mirror) {
		setChildren(mirror);
	}

	@Override
	public NXfilter getFilter_number() {
		// dataNodeName = NX_FILTER_NUMBER
		return getChild("filter_number", NXfilter.class);
	}

	@Override
	public void setFilter_number(NXfilter filter_numberGroup) {
		putChild("filter_number", filter_numberGroup);
	}

	@Override
	public NXattenuator getAttenuator() {
		// dataNodeName = NX_ATTENUATOR
		return getChild("attenuator", NXattenuator.class);
	}

	@Override
	public void setAttenuator(NXattenuator attenuatorGroup) {
		putChild("attenuator", attenuatorGroup);
	}

	@Override
	public NXattenuator getAttenuator(String name) {
		return getChild(name, NXattenuator.class);
	}

	@Override
	public void setAttenuator(String name, NXattenuator attenuator) {
		putChild(name, attenuator);
	}

	@Override
	public Map<String, NXattenuator> getAllAttenuator() {
		return getChildren(NXattenuator.class);
	}

	@Override
	public void setAllAttenuator(Map<String, NXattenuator> attenuator) {
		setChildren(attenuator);
	}
	// Unprocessed group:
	// Unprocessed group:

	@Override
	public NXgrating getGrating() {
		// dataNodeName = NX_GRATING
		return getChild("grating", NXgrating.class);
	}

	@Override
	public void setGrating(NXgrating gratingGroup) {
		putChild("grating", gratingGroup);
	}

	@Override
	public NXgrating getGrating(String name) {
		return getChild(name, NXgrating.class);
	}

	@Override
	public void setGrating(String name, NXgrating grating) {
		putChild(name, grating);
	}

	@Override
	public Map<String, NXgrating> getAllGrating() {
		return getChildren(NXgrating.class);
	}

	@Override
	public void setAllGrating(Map<String, NXgrating> grating) {
		setChildren(grating);
	}

	@Override
	public NXdisk_chopper getDisk_chopper() {
		// dataNodeName = NX_DISK_CHOPPER
		return getChild("disk_chopper", NXdisk_chopper.class);
	}

	@Override
	public void setDisk_chopper(NXdisk_chopper disk_chopperGroup) {
		putChild("disk_chopper", disk_chopperGroup);
	}

	@Override
	public NXdisk_chopper getDisk_chopper(String name) {
		return getChild(name, NXdisk_chopper.class);
	}

	@Override
	public void setDisk_chopper(String name, NXdisk_chopper disk_chopper) {
		putChild(name, disk_chopper);
	}

	@Override
	public Map<String, NXdisk_chopper> getAllDisk_chopper() {
		return getChildren(NXdisk_chopper.class);
	}

	@Override
	public void setAllDisk_chopper(Map<String, NXdisk_chopper> disk_chopper) {
		setChildren(disk_chopper);
	}

	@Override
	public NXmonochromator getMonochromator() {
		// dataNodeName = NX_MONOCHROMATOR
		return getChild("monochromator", NXmonochromator.class);
	}

	@Override
	public void setMonochromator(NXmonochromator monochromatorGroup) {
		putChild("monochromator", monochromatorGroup);
	}

	@Override
	public NXmonochromator getMonochromator(String name) {
		return getChild(name, NXmonochromator.class);
	}

	@Override
	public void setMonochromator(String name, NXmonochromator monochromator) {
		putChild(name, monochromator);
	}

	@Override
	public Map<String, NXmonochromator> getAllMonochromator() {
		return getChildren(NXmonochromator.class);
	}

	@Override
	public void setAllMonochromator(Map<String, NXmonochromator> monochromator) {
		setChildren(monochromator);
	}
	// Unprocessed group:
	// Unprocessed group:

	@Override
	public NXxraylens getXraylens() {
		// dataNodeName = NX_XRAYLENS
		return getChild("xraylens", NXxraylens.class);
	}

	@Override
	public void setXraylens(NXxraylens xraylensGroup) {
		putChild("xraylens", xraylensGroup);
	}

	@Override
	public NXxraylens getXraylens(String name) {
		return getChild(name, NXxraylens.class);
	}

	@Override
	public void setXraylens(String name, NXxraylens xraylens) {
		putChild(name, xraylens);
	}

	@Override
	public Map<String, NXxraylens> getAllXraylens() {
		return getChildren(NXxraylens.class);
	}

	@Override
	public void setAllXraylens(Map<String, NXxraylens> xraylens) {
		setChildren(xraylens);
	}

	@Override
	public NXpolarizer_opt getPolarizer_opt() {
		// dataNodeName = NX_POLARIZER_OPT
		return getChild("polarizer_opt", NXpolarizer_opt.class);
	}

	@Override
	public void setPolarizer_opt(NXpolarizer_opt polarizer_optGroup) {
		putChild("polarizer_opt", polarizer_optGroup);
	}

	@Override
	public NXpolarizer_opt getPolarizer_opt(String name) {
		return getChild(name, NXpolarizer_opt.class);
	}

	@Override
	public void setPolarizer_opt(String name, NXpolarizer_opt polarizer_opt) {
		putChild(name, polarizer_opt);
	}

	@Override
	public Map<String, NXpolarizer_opt> getAllPolarizer_opt() {
		return getChildren(NXpolarizer_opt.class);
	}

	@Override
	public void setAllPolarizer_opt(Map<String, NXpolarizer_opt> polarizer_opt) {
		setChildren(polarizer_opt);
	}

	@Override
	public NXbeam_splitter getBeam_splitter() {
		// dataNodeName = NX_BEAM_SPLITTER
		return getChild("beam_splitter", NXbeam_splitter.class);
	}

	@Override
	public void setBeam_splitter(NXbeam_splitter beam_splitterGroup) {
		putChild("beam_splitter", beam_splitterGroup);
	}

	@Override
	public NXbeam_splitter getBeam_splitter(String name) {
		return getChild(name, NXbeam_splitter.class);
	}

	@Override
	public void setBeam_splitter(String name, NXbeam_splitter beam_splitter) {
		putChild(name, beam_splitter);
	}

	@Override
	public Map<String, NXbeam_splitter> getAllBeam_splitter() {
		return getChildren(NXbeam_splitter.class);
	}

	@Override
	public void setAllBeam_splitter(Map<String, NXbeam_splitter> beam_splitter) {
		setChildren(beam_splitter);
	}

	@Override
	public NXwaveplate getWaveplate() {
		// dataNodeName = NX_WAVEPLATE
		return getChild("waveplate", NXwaveplate.class);
	}

	@Override
	public void setWaveplate(NXwaveplate waveplateGroup) {
		putChild("waveplate", waveplateGroup);
	}

	@Override
	public NXwaveplate getWaveplate(String name) {
		return getChild(name, NXwaveplate.class);
	}

	@Override
	public void setWaveplate(String name, NXwaveplate waveplate) {
		putChild(name, waveplate);
	}

	@Override
	public Map<String, NXwaveplate> getAllWaveplate() {
		return getChildren(NXwaveplate.class);
	}

	@Override
	public void setAllWaveplate(Map<String, NXwaveplate> waveplate) {
		setChildren(waveplate);
	}

	@Override
	public NXlens_opt getLens_opt() {
		// dataNodeName = NX_LENS_OPT
		return getChild("lens_opt", NXlens_opt.class);
	}

	@Override
	public void setLens_opt(NXlens_opt lens_optGroup) {
		putChild("lens_opt", lens_optGroup);
	}

	@Override
	public NXlens_opt getLens_opt(String name) {
		return getChild(name, NXlens_opt.class);
	}

	@Override
	public void setLens_opt(String name, NXlens_opt lens_opt) {
		putChild(name, lens_opt);
	}

	@Override
	public Map<String, NXlens_opt> getAllLens_opt() {
		return getChildren(NXlens_opt.class);
	}

	@Override
	public void setAllLens_opt(Map<String, NXlens_opt> lens_opt) {
		setChildren(lens_opt);
	}

	@Override
	public NXfiber getFiber() {
		// dataNodeName = NX_FIBER
		return getChild("fiber", NXfiber.class);
	}

	@Override
	public void setFiber(NXfiber fiberGroup) {
		putChild("fiber", fiberGroup);
	}

	@Override
	public NXfiber getFiber(String name) {
		return getChild(name, NXfiber.class);
	}

	@Override
	public void setFiber(String name, NXfiber fiber) {
		putChild(name, fiber);
	}

	@Override
	public Map<String, NXfiber> getAllFiber() {
		return getChildren(NXfiber.class);
	}

	@Override
	public void setAllFiber(Map<String, NXfiber> fiber) {
		setChildren(fiber);
	}

}
