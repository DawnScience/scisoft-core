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

import java.net.URI;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.impl.NXapertureImpl;
import org.eclipse.dawnsci.nexus.impl.NXattenuatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXbeamImpl;
import org.eclipse.dawnsci.nexus.impl.NXbeam_stopImpl;
import org.eclipse.dawnsci.nexus.impl.NXbending_magnetImpl;
import org.eclipse.dawnsci.nexus.impl.NXcapillaryImpl;
import org.eclipse.dawnsci.nexus.impl.NXciteImpl;
import org.eclipse.dawnsci.nexus.impl.NXcollectionImpl;
import org.eclipse.dawnsci.nexus.impl.NXcollimatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXcrystalImpl;
import org.eclipse.dawnsci.nexus.impl.NXcylindrical_geometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXdataImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetector_groupImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetector_moduleImpl;
import org.eclipse.dawnsci.nexus.impl.NXdisk_chopperImpl;
import org.eclipse.dawnsci.nexus.impl.NXentryImpl;
import org.eclipse.dawnsci.nexus.impl.NXenvironmentImpl;
import org.eclipse.dawnsci.nexus.impl.NXevent_dataImpl;
import org.eclipse.dawnsci.nexus.impl.NXfermi_chopperImpl;
import org.eclipse.dawnsci.nexus.impl.NXfilterImpl;
import org.eclipse.dawnsci.nexus.impl.NXflipperImpl;
import org.eclipse.dawnsci.nexus.impl.NXfresnel_zone_plateImpl;
import org.eclipse.dawnsci.nexus.impl.NXgeometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXgratingImpl;
import org.eclipse.dawnsci.nexus.impl.NXguideImpl;
import org.eclipse.dawnsci.nexus.impl.NXinsertion_deviceImpl;
import org.eclipse.dawnsci.nexus.impl.NXinstrumentImpl;
import org.eclipse.dawnsci.nexus.impl.NXlogImpl;
import org.eclipse.dawnsci.nexus.impl.NXmirrorImpl;
import org.eclipse.dawnsci.nexus.impl.NXmoderatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXmonitorImpl;
import org.eclipse.dawnsci.nexus.impl.NXmonochromatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXnoteImpl;
import org.eclipse.dawnsci.nexus.impl.NXoff_geometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXorientationImpl;
import org.eclipse.dawnsci.nexus.impl.NXparametersImpl;
import org.eclipse.dawnsci.nexus.impl.NXpdbImpl;
import org.eclipse.dawnsci.nexus.impl.NXpinholeImpl;
import org.eclipse.dawnsci.nexus.impl.NXpolarizerImpl;
import org.eclipse.dawnsci.nexus.impl.NXpositionerImpl;
import org.eclipse.dawnsci.nexus.impl.NXprocessImpl;
import org.eclipse.dawnsci.nexus.impl.NXreflectionsImpl;
import org.eclipse.dawnsci.nexus.impl.NXrootImpl;
import org.eclipse.dawnsci.nexus.impl.NXsampleImpl;
import org.eclipse.dawnsci.nexus.impl.NXsample_componentImpl;
import org.eclipse.dawnsci.nexus.impl.NXsensorImpl;
import org.eclipse.dawnsci.nexus.impl.NXshapeImpl;
import org.eclipse.dawnsci.nexus.impl.NXslitImpl;
import org.eclipse.dawnsci.nexus.impl.NXsourceImpl;
import org.eclipse.dawnsci.nexus.impl.NXsubentryImpl;
import org.eclipse.dawnsci.nexus.impl.NXtransformationsImpl;
import org.eclipse.dawnsci.nexus.impl.NXtranslationImpl;
import org.eclipse.dawnsci.nexus.impl.NXuserImpl;
import org.eclipse.dawnsci.nexus.impl.NXvelocity_selectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXxraylensImpl;
import org.eclipse.dawnsci.nexus.impl.NXaberrationImpl;
import org.eclipse.dawnsci.nexus.impl.NXaperture_emImpl;
import org.eclipse.dawnsci.nexus.impl.NXcalibrationImpl;
import org.eclipse.dawnsci.nexus.impl.NXchamberImpl;
import org.eclipse.dawnsci.nexus.impl.NXcollectioncolumnImpl;
import org.eclipse.dawnsci.nexus.impl.NXcontainerImpl;
import org.eclipse.dawnsci.nexus.impl.NXcoordinate_system_setImpl;
import org.eclipse.dawnsci.nexus.impl.NXcorrector_csImpl;
import org.eclipse.dawnsci.nexus.impl.NXcsgImpl;
import org.eclipse.dawnsci.nexus.impl.NXdeflectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXdistortionImpl;
import org.eclipse.dawnsci.nexus.impl.NXebeam_columnImpl;
import org.eclipse.dawnsci.nexus.impl.NXelectronanalyserImpl;
import org.eclipse.dawnsci.nexus.impl.NXelectrostatic_kickerImpl;
import org.eclipse.dawnsci.nexus.impl.NXenergydispersionImpl;
import org.eclipse.dawnsci.nexus.impl.NXevent_data_emImpl;
import org.eclipse.dawnsci.nexus.impl.NXevent_data_em_setImpl;
import org.eclipse.dawnsci.nexus.impl.NXibeam_columnImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_adfImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_bfImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_bseImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_chamberImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_dfImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_diffracImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_ecciImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_kikuchiImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_ronchigramImpl;
import org.eclipse.dawnsci.nexus.impl.NXimage_set_em_seImpl;
import org.eclipse.dawnsci.nexus.impl.NXinteraction_vol_emImpl;
import org.eclipse.dawnsci.nexus.impl.NXionImpl;
import org.eclipse.dawnsci.nexus.impl.NXlens_emImpl;
import org.eclipse.dawnsci.nexus.impl.NXmagnetic_kickerImpl;
import org.eclipse.dawnsci.nexus.impl.NXmanipulatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXmanufacturerImpl;
import org.eclipse.dawnsci.nexus.impl.NXoptical_system_emImpl;
import org.eclipse.dawnsci.nexus.impl.NXpeakImpl;
import org.eclipse.dawnsci.nexus.impl.NXpulser_apmImpl;
import org.eclipse.dawnsci.nexus.impl.NXpumpImpl;
import org.eclipse.dawnsci.nexus.impl.NXquadricImpl;
import org.eclipse.dawnsci.nexus.impl.NXquadrupole_magnetImpl;
import org.eclipse.dawnsci.nexus.impl.NXreflectronImpl;
import org.eclipse.dawnsci.nexus.impl.NXregionImpl;
import org.eclipse.dawnsci.nexus.impl.NXregistrationImpl;
import org.eclipse.dawnsci.nexus.impl.NXscanbox_emImpl;
import org.eclipse.dawnsci.nexus.impl.NXseparatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXsolenoid_magnetImpl;
import org.eclipse.dawnsci.nexus.impl.NXsolid_geometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXspectrum_set_em_augerImpl;
import org.eclipse.dawnsci.nexus.impl.NXspectrum_set_em_cathodolumImpl;
import org.eclipse.dawnsci.nexus.impl.NXspectrum_set_em_eelsImpl;
import org.eclipse.dawnsci.nexus.impl.NXspectrum_set_em_xrayImpl;
import org.eclipse.dawnsci.nexus.impl.NXspin_rotatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXspindispersionImpl;
import org.eclipse.dawnsci.nexus.impl.NXstage_labImpl;

/**
 * Factory class for creating instances of NeXus base classes.
 */
public class NexusNodeFactory {
	
	// static field so that we can create unique oids without having access to
	// the same node factory instances. Values should be unique within this VM,
	// except in the unlikely case that the value rolls over
	private static long nextOid = 1l;

	private NexusNodeFactory() {
		// private constructor to prevent instantiation
	}

	public static NXobject createNXobjectForClass(String baseClassName, long oid) {
		final NexusBaseClass baseClass = NexusBaseClass.getBaseClassForName(baseClassName);
		return createNXobjectForClass(baseClass, oid);
	}
	
	public static NXobject createNXobjectForClass(NexusBaseClass baseClass, long oid) {
		switch (baseClass) {
			case NX_ABERRATION:
				return createNXaberration(oid);
			case NX_APERTURE:
				return createNXaperture(oid);
			case NX_APERTURE_EM:
				return createNXaperture_em(oid);
			case NX_ATTENUATOR:
				return createNXattenuator(oid);
			case NX_BEAM:
				return createNXbeam(oid);
			case NX_BEAM_STOP:
				return createNXbeam_stop(oid);
			case NX_BENDING_MAGNET:
				return createNXbending_magnet(oid);
			case NX_CALIBRATION:
				return createNXcalibration(oid);
			case NX_CAPILLARY:
				return createNXcapillary(oid);
			case NX_CHAMBER:
				return createNXchamber(oid);
			case NX_CITE:
				return createNXcite(oid);
			case NX_COLLECTION:
				return createNXcollection(oid);
			case NX_COLLECTIONCOLUMN:
				return createNXcollectioncolumn(oid);
			case NX_COLLIMATOR:
				return createNXcollimator(oid);
			case NX_CONTAINER:
				return createNXcontainer(oid);
			case NX_COORDINATE_SYSTEM_SET:
				return createNXcoordinate_system_set(oid);
			case NX_CORRECTOR_CS:
				return createNXcorrector_cs(oid);
			case NX_CRYSTAL:
				return createNXcrystal(oid);
			case NX_CSG:
				return createNXcsg(oid);
			case NX_CYLINDRICAL_GEOMETRY:
				return createNXcylindrical_geometry(oid);
			case NX_DATA:
				return createNXdata(oid);
			case NX_DEFLECTOR:
				return createNXdeflector(oid);
			case NX_DETECTOR:
				return createNXdetector(oid);
			case NX_DETECTOR_GROUP:
				return createNXdetector_group(oid);
			case NX_DETECTOR_MODULE:
				return createNXdetector_module(oid);
			case NX_DISK_CHOPPER:
				return createNXdisk_chopper(oid);
			case NX_DISTORTION:
				return createNXdistortion(oid);
			case NX_EBEAM_COLUMN:
				return createNXebeam_column(oid);
			case NX_ELECTRONANALYSER:
				return createNXelectronanalyser(oid);
			case NX_ELECTROSTATIC_KICKER:
				return createNXelectrostatic_kicker(oid);
			case NX_ENERGYDISPERSION:
				return createNXenergydispersion(oid);
			case NX_ENTRY:
				return createNXentry(oid);
			case NX_ENVIRONMENT:
				return createNXenvironment(oid);
			case NX_EVENT_DATA:
				return createNXevent_data(oid);
			case NX_EVENT_DATA_EM:
				return createNXevent_data_em(oid);
			case NX_EVENT_DATA_EM_SET:
				return createNXevent_data_em_set(oid);
			case NX_FERMI_CHOPPER:
				return createNXfermi_chopper(oid);
			case NX_FILTER:
				return createNXfilter(oid);
			case NX_FLIPPER:
				return createNXflipper(oid);
			case NX_FRESNEL_ZONE_PLATE:
				return createNXfresnel_zone_plate(oid);
			case NX_GEOMETRY:
				return createNXgeometry(oid);
			case NX_GRATING:
				return createNXgrating(oid);
			case NX_GUIDE:
				return createNXguide(oid);
			case NX_IBEAM_COLUMN:
				return createNXibeam_column(oid);
			case NX_IMAGE_SET_EM_ADF:
				return createNXimage_set_em_adf(oid);
			case NX_IMAGE_SET_EM_BF:
				return createNXimage_set_em_bf(oid);
			case NX_IMAGE_SET_EM_BSE:
				return createNXimage_set_em_bse(oid);
			case NX_IMAGE_SET_EM_CHAMBER:
				return createNXimage_set_em_chamber(oid);
			case NX_IMAGE_SET_EM_DF:
				return createNXimage_set_em_df(oid);
			case NX_IMAGE_SET_EM_DIFFRAC:
				return createNXimage_set_em_diffrac(oid);
			case NX_IMAGE_SET_EM_ECCI:
				return createNXimage_set_em_ecci(oid);
			case NX_IMAGE_SET_EM_KIKUCHI:
				return createNXimage_set_em_kikuchi(oid);
			case NX_IMAGE_SET_EM_RONCHIGRAM:
				return createNXimage_set_em_ronchigram(oid);
			case NX_IMAGE_SET_EM_SE:
				return createNXimage_set_em_se(oid);
			case NX_INSERTION_DEVICE:
				return createNXinsertion_device(oid);
			case NX_INSTRUMENT:
				return createNXinstrument(oid);
			case NX_INTERACTION_VOL_EM:
				return createNXinteraction_vol_em(oid);
			case NX_ION:
				return createNXion(oid);
			case NX_LENS_EM:
				return createNXlens_em(oid);
			case NX_LOG:
				return createNXlog(oid);
			case NX_MAGNETIC_KICKER:
				return createNXmagnetic_kicker(oid);
			case NX_MANIPULATOR:
				return createNXmanipulator(oid);
			case NX_MANUFACTURER:
				return createNXmanufacturer(oid);
			case NX_MIRROR:
				return createNXmirror(oid);
			case NX_MODERATOR:
				return createNXmoderator(oid);
			case NX_MONITOR:
				return createNXmonitor(oid);
			case NX_MONOCHROMATOR:
				return createNXmonochromator(oid);
			case NX_NOTE:
				return createNXnote(oid);
			case NX_OFF_GEOMETRY:
				return createNXoff_geometry(oid);
			case NX_OPTICAL_SYSTEM_EM:
				return createNXoptical_system_em(oid);
			case NX_ORIENTATION:
				return createNXorientation(oid);
			case NX_PARAMETERS:
				return createNXparameters(oid);
			case NX_PDB:
				return createNXpdb(oid);
			case NX_PEAK:
				return createNXpeak(oid);
			case NX_PINHOLE:
				return createNXpinhole(oid);
			case NX_POLARIZER:
				return createNXpolarizer(oid);
			case NX_POSITIONER:
				return createNXpositioner(oid);
			case NX_PROCESS:
				return createNXprocess(oid);
			case NX_PULSER_APM:
				return createNXpulser_apm(oid);
			case NX_PUMP:
				return createNXpump(oid);
			case NX_QUADRIC:
				return createNXquadric(oid);
			case NX_QUADRUPOLE_MAGNET:
				return createNXquadrupole_magnet(oid);
			case NX_REFLECTIONS:
				return createNXreflections(oid);
			case NX_REFLECTRON:
				return createNXreflectron(oid);
			case NX_REGION:
				return createNXregion(oid);
			case NX_REGISTRATION:
				return createNXregistration(oid);
			case NX_ROOT:
				return createNXroot(oid);
			case NX_SAMPLE:
				return createNXsample(oid);
			case NX_SAMPLE_COMPONENT:
				return createNXsample_component(oid);
			case NX_SCANBOX_EM:
				return createNXscanbox_em(oid);
			case NX_SENSOR:
				return createNXsensor(oid);
			case NX_SEPARATOR:
				return createNXseparator(oid);
			case NX_SHAPE:
				return createNXshape(oid);
			case NX_SLIT:
				return createNXslit(oid);
			case NX_SOLENOID_MAGNET:
				return createNXsolenoid_magnet(oid);
			case NX_SOLID_GEOMETRY:
				return createNXsolid_geometry(oid);
			case NX_SOURCE:
				return createNXsource(oid);
			case NX_SPECTRUM_SET_EM_AUGER:
				return createNXspectrum_set_em_auger(oid);
			case NX_SPECTRUM_SET_EM_CATHODOLUM:
				return createNXspectrum_set_em_cathodolum(oid);
			case NX_SPECTRUM_SET_EM_EELS:
				return createNXspectrum_set_em_eels(oid);
			case NX_SPECTRUM_SET_EM_XRAY:
				return createNXspectrum_set_em_xray(oid);
			case NX_SPIN_ROTATOR:
				return createNXspin_rotator(oid);
			case NX_SPINDISPERSION:
				return createNXspindispersion(oid);
			case NX_STAGE_LAB:
				return createNXstage_lab(oid);
			case NX_SUBENTRY:
				return createNXsubentry(oid);
			case NX_TRANSFORMATIONS:
				return createNXtransformations(oid);
			case NX_TRANSLATION:
				return createNXtranslation(oid);
			case NX_USER:
				return createNXuser(oid);
			case NX_VELOCITY_SELECTOR:
				return createNXvelocity_selector(oid);
			case NX_XRAYLENS:
				return createNXxraylens(oid);
		}
		throw new IllegalArgumentException("Unknown base class: " + baseClass);
	}


	public static NXobject createNXobjectForClass(String baseClassName) {
		final NexusBaseClass baseClass = NexusBaseClass.getBaseClassForName(baseClassName);
		return createNXobjectForClass(baseClass);
	}

	public static NXobject createNXobjectForClass(NexusBaseClass baseClass) {
		switch (baseClass) {
			case NX_APERTURE:
				return createNXaperture();
			case NX_ATTENUATOR:
				return createNXattenuator();
			case NX_BEAM:
				return createNXbeam();
			case NX_BEAM_STOP:
				return createNXbeam_stop();
			case NX_BENDING_MAGNET:
				return createNXbending_magnet();
			case NX_CAPILLARY:
				return createNXcapillary();
			case NX_CITE:
				return createNXcite();
			case NX_COLLECTION:
				return createNXcollection();
			case NX_COLLIMATOR:
				return createNXcollimator();
			case NX_CRYSTAL:
				return createNXcrystal();
			case NX_CYLINDRICAL_GEOMETRY:
				return createNXcylindrical_geometry();
			case NX_DATA:
				return createNXdata();
			case NX_DETECTOR:
				return createNXdetector();
			case NX_DETECTOR_GROUP:
				return createNXdetector_group();
			case NX_DETECTOR_MODULE:
				return createNXdetector_module();
			case NX_DISK_CHOPPER:
				return createNXdisk_chopper();
			case NX_ENTRY:
				return createNXentry();
			case NX_ENVIRONMENT:
				return createNXenvironment();
			case NX_EVENT_DATA:
				return createNXevent_data();
			case NX_FERMI_CHOPPER:
				return createNXfermi_chopper();
			case NX_FILTER:
				return createNXfilter();
			case NX_FLIPPER:
				return createNXflipper();
			case NX_FRESNEL_ZONE_PLATE:
				return createNXfresnel_zone_plate();
			case NX_GEOMETRY:
				return createNXgeometry();
			case NX_GRATING:
				return createNXgrating();
			case NX_GUIDE:
				return createNXguide();
			case NX_INSERTION_DEVICE:
				return createNXinsertion_device();
			case NX_INSTRUMENT:
				return createNXinstrument();
			case NX_LOG:
				return createNXlog();
			case NX_MIRROR:
				return createNXmirror();
			case NX_MODERATOR:
				return createNXmoderator();
			case NX_MONITOR:
				return createNXmonitor();
			case NX_MONOCHROMATOR:
				return createNXmonochromator();
			case NX_NOTE:
				return createNXnote();
			case NX_OFF_GEOMETRY:
				return createNXoff_geometry();
			case NX_ORIENTATION:
				return createNXorientation();
			case NX_PARAMETERS:
				return createNXparameters();
			case NX_PDB:
				return createNXpdb();
			case NX_PINHOLE:
				return createNXpinhole();
			case NX_POLARIZER:
				return createNXpolarizer();
			case NX_POSITIONER:
				return createNXpositioner();
			case NX_PROCESS:
				return createNXprocess();
			case NX_REFLECTIONS:
				return createNXreflections();
			case NX_ROOT:
				return createNXroot();
			case NX_SAMPLE:
				return createNXsample();
			case NX_SAMPLE_COMPONENT:
				return createNXsample_component();
			case NX_SENSOR:
				return createNXsensor();
			case NX_SHAPE:
				return createNXshape();
			case NX_SLIT:
				return createNXslit();
			case NX_SOURCE:
				return createNXsource();
			case NX_SUBENTRY:
				return createNXsubentry();
			case NX_TRANSFORMATIONS:
				return createNXtransformations();
			case NX_TRANSLATION:
				return createNXtranslation();
			case NX_USER:
				return createNXuser();
			case NX_VELOCITY_SELECTOR:
				return createNXvelocity_selector();
			case NX_XRAYLENS:
				return createNXxraylens();
			case NX_ABERRATION:
				return createNXaberration();
			case NX_APERTURE_EM:
				return createNXaperture_em();
			case NX_CALIBRATION:
				return createNXcalibration();
			case NX_CHAMBER:
				return createNXchamber();
			case NX_COLLECTIONCOLUMN:
				return createNXcollectioncolumn();
			case NX_CONTAINER:
				return createNXcontainer();
			case NX_COORDINATE_SYSTEM_SET:
				return createNXcoordinate_system_set();
			case NX_CORRECTOR_CS:
				return createNXcorrector_cs();
			case NX_CSG:
				return createNXcsg();
			case NX_DEFLECTOR:
				return createNXdeflector();
			case NX_DISTORTION:
				return createNXdistortion();
			case NX_EBEAM_COLUMN:
				return createNXebeam_column();
			case NX_ELECTRONANALYSER:
				return createNXelectronanalyser();
			case NX_ELECTROSTATIC_KICKER:
				return createNXelectrostatic_kicker();
			case NX_ENERGYDISPERSION:
				return createNXenergydispersion();
			case NX_EVENT_DATA_EM:
				return createNXevent_data_em();
			case NX_EVENT_DATA_EM_SET:
				return createNXevent_data_em_set();
			case NX_IBEAM_COLUMN:
				return createNXibeam_column();
			case NX_IMAGE_SET_EM_ADF:
				return createNXimage_set_em_adf();
			case NX_IMAGE_SET_EM_BF:
				return createNXimage_set_em_bf();
			case NX_IMAGE_SET_EM_BSE:
				return createNXimage_set_em_bse();
			case NX_IMAGE_SET_EM_CHAMBER:
				return createNXimage_set_em_chamber();
			case NX_IMAGE_SET_EM_DF:
				return createNXimage_set_em_df();
			case NX_IMAGE_SET_EM_DIFFRAC:
				return createNXimage_set_em_diffrac();
			case NX_IMAGE_SET_EM_ECCI:
				return createNXimage_set_em_ecci();
			case NX_IMAGE_SET_EM_KIKUCHI:
				return createNXimage_set_em_kikuchi();
			case NX_IMAGE_SET_EM_RONCHIGRAM:
				return createNXimage_set_em_ronchigram();
			case NX_IMAGE_SET_EM_SE:
				return createNXimage_set_em_se();
			case NX_INTERACTION_VOL_EM:
				return createNXinteraction_vol_em();
			case NX_ION:
				return createNXion();
			case NX_LENS_EM:
				return createNXlens_em();
			case NX_MAGNETIC_KICKER:
				return createNXmagnetic_kicker();
			case NX_MANIPULATOR:
				return createNXmanipulator();
			case NX_MANUFACTURER:
				return createNXmanufacturer();
			case NX_OPTICAL_SYSTEM_EM:
				return createNXoptical_system_em();
			case NX_PEAK:
				return createNXpeak();
			case NX_PULSER_APM:
				return createNXpulser_apm();
			case NX_PUMP:
				return createNXpump();
			case NX_QUADRIC:
				return createNXquadric();
			case NX_QUADRUPOLE_MAGNET:
				return createNXquadrupole_magnet();
			case NX_REFLECTRON:
				return createNXreflectron();
			case NX_REGION:
				return createNXregion();
			case NX_REGISTRATION:
				return createNXregistration();
			case NX_SCANBOX_EM:
				return createNXscanbox_em();
			case NX_SEPARATOR:
				return createNXseparator();
			case NX_SOLENOID_MAGNET:
				return createNXsolenoid_magnet();
			case NX_SOLID_GEOMETRY:
				return createNXsolid_geometry();
			case NX_SPECTRUM_SET_EM_AUGER:
				return createNXspectrum_set_em_auger();
			case NX_SPECTRUM_SET_EM_CATHODOLUM:
				return createNXspectrum_set_em_cathodolum();
			case NX_SPECTRUM_SET_EM_EELS:
				return createNXspectrum_set_em_eels();
			case NX_SPECTRUM_SET_EM_XRAY:
				return createNXspectrum_set_em_xray();
			case NX_SPIN_ROTATOR:
				return createNXspin_rotator();
			case NX_SPINDISPERSION:
				return createNXspindispersion();
			case NX_STAGE_LAB:
				return createNXstage_lab();
		}
		throw new IllegalArgumentException("Unknown base class: " + baseClass);
	}

	/**
	 * Get the next unique object id. Note that this value is unique across all instances in
	 * this VM (i.e. it is a static field).
	 * @return the next oid
	 */
	public static long getNextOid() {
		long oid = nextOid++;
		if (oid == 0) { // oids may no longer be unique
			throw new RuntimeException("maximum number of oids reached");
		}
		return oid;
	}
	
	/**
	 * Create a new {@link Tree} with given URI.
	 * @param uri
	 * @return new tree
	 */
	public static Tree createTree(final URI uri) {
		return TreeFactory.createTree(getNextOid(), uri);
	}
	
	/**
	 * Create a new {@link TreeFile} given URI.
	 * @param uri uri
	 * @return new tree file
	 */
	public static TreeFile createTreeFile(final URI uri) {
		return TreeFactory.createTreeFile(getNextOid(), uri);
	}
	
	/**
	 * Create a new tree file with given file name
	 * @param fileName filename
	 * @return new tree file
	 */
	public static TreeFile createTreeFile(final String fileName) {
		return TreeFactory.createTreeFile(getNextOid(), fileName);
	}
	
	/**
	 * Create a new data node.
	 * @return new data node
	 */
	public static DataNode createDataNode() {
		return TreeFactory.createDataNode(getNextOid());
	}
	
	/**
	 * Create a group node that does not have a nexus class, or whose nexus class is not yet known.
	 * Note: the {@link NXobjectImpl#NX_CLASS} attribute must be set before the nexus file is
	 * save to disk for the file to be a valid nexus file.
	 * @return new group node
	 */
	public static GroupNode createGroupNode() {
		return TreeFactory.createGroupNode(getNextOid());
	}
	
	/**
	 * Create a new symbolic node
	 * @param uri uri
	 * @param pathToNode path to node
	 * @return new symbolic node
	 */
	public static SymbolicNode createSymbolicNode(URI uri, String pathToNode) {
		return TreeFactory.createSymbolicNode(getNextOid(), uri, null, pathToNode);
	}
	
	/**
	 * Create a new {@link NXaperture} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXaperture
	 */
	public static NXaperture createNXaperture(long oid) {
		return new NXapertureImpl(oid);
	}

	/**
	 * Create a new {@link NXaperture}.
	 *
	 * @return new NXaperture
	 */
	public static NXaperture createNXaperture() {
		return new NXapertureImpl();
	}

	/**
	 * Create a new {@link NXattenuator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXattenuator
	 */
	public static NXattenuator createNXattenuator(long oid) {
		return new NXattenuatorImpl(oid);
	}

	/**
	 * Create a new {@link NXattenuator}.
	 *
	 * @return new NXattenuator
	 */
	public static NXattenuator createNXattenuator() {
		return new NXattenuatorImpl();
	}

	/**
	 * Create a new {@link NXbeam} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXbeam
	 */
	public static NXbeam createNXbeam(long oid) {
		return new NXbeamImpl(oid);
	}

	/**
	 * Create a new {@link NXbeam}.
	 *
	 * @return new NXbeam
	 */
	public static NXbeam createNXbeam() {
		return new NXbeamImpl();
	}

	/**
	 * Create a new {@link NXbeam_stop} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXbeam_stop
	 */
	public static NXbeam_stop createNXbeam_stop(long oid) {
		return new NXbeam_stopImpl(oid);
	}

	/**
	 * Create a new {@link NXbeam_stop}.
	 *
	 * @return new NXbeam_stop
	 */
	public static NXbeam_stop createNXbeam_stop() {
		return new NXbeam_stopImpl();
	}

	/**
	 * Create a new {@link NXbending_magnet} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXbending_magnet
	 */
	public static NXbending_magnet createNXbending_magnet(long oid) {
		return new NXbending_magnetImpl(oid);
	}

	/**
	 * Create a new {@link NXbending_magnet}.
	 *
	 * @return new NXbending_magnet
	 */
	public static NXbending_magnet createNXbending_magnet() {
		return new NXbending_magnetImpl();
	}

	/**
	 * Create a new {@link NXcapillary} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcapillary
	 */
	public static NXcapillary createNXcapillary(long oid) {
		return new NXcapillaryImpl(oid);
	}

	/**
	 * Create a new {@link NXcapillary}.
	 *
	 * @return new NXcapillary
	 */
	public static NXcapillary createNXcapillary() {
		return new NXcapillaryImpl();
	}

	/**
	 * Create a new {@link NXcite} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcite
	 */
	public static NXcite createNXcite(long oid) {
		return new NXciteImpl(oid);
	}

	/**
	 * Create a new {@link NXcite}.
	 *
	 * @return new NXcite
	 */
	public static NXcite createNXcite() {
		return new NXciteImpl();
	}

	/**
	 * Create a new {@link NXcollection} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcollection
	 */
	public static NXcollection createNXcollection(long oid) {
		return new NXcollectionImpl(oid);
	}

	/**
	 * Create a new {@link NXcollection}.
	 *
	 * @return new NXcollection
	 */
	public static NXcollection createNXcollection() {
		return new NXcollectionImpl();
	}

	/**
	 * Create a new {@link NXcollimator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcollimator
	 */
	public static NXcollimator createNXcollimator(long oid) {
		return new NXcollimatorImpl(oid);
	}

	/**
	 * Create a new {@link NXcollimator}.
	 *
	 * @return new NXcollimator
	 */
	public static NXcollimator createNXcollimator() {
		return new NXcollimatorImpl();
	}

	/**
	 * Create a new {@link NXcrystal} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcrystal
	 */
	public static NXcrystal createNXcrystal(long oid) {
		return new NXcrystalImpl(oid);
	}

	/**
	 * Create a new {@link NXcrystal}.
	 *
	 * @return new NXcrystal
	 */
	public static NXcrystal createNXcrystal() {
		return new NXcrystalImpl();
	}

	/**
	 * Create a new {@link NXcylindrical_geometry} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcylindrical_geometry
	 */
	public static NXcylindrical_geometry createNXcylindrical_geometry(long oid) {
		return new NXcylindrical_geometryImpl(oid);
	}

	/**
	 * Create a new {@link NXcylindrical_geometry}.
	 *
	 * @return new NXcylindrical_geometry
	 */
	public static NXcylindrical_geometry createNXcylindrical_geometry() {
		return new NXcylindrical_geometryImpl();
	}

	/**
	 * Create a new {@link NXdata} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdata
	 */
	public static NXdata createNXdata(long oid) {
		return new NXdataImpl(oid);
	}

	/**
	 * Create a new {@link NXdata}.
	 *
	 * @return new NXdata
	 */
	public static NXdata createNXdata() {
		return new NXdataImpl();
	}

	/**
	 * Create a new {@link NXdetector} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdetector
	 */
	public static NXdetector createNXdetector(long oid) {
		return new NXdetectorImpl(oid);
	}

	/**
	 * Create a new {@link NXdetector}.
	 *
	 * @return new NXdetector
	 */
	public static NXdetector createNXdetector() {
		return new NXdetectorImpl();
	}

	/**
	 * Create a new {@link NXdetector_group} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdetector_group
	 */
	public static NXdetector_group createNXdetector_group(long oid) {
		return new NXdetector_groupImpl(oid);
	}

	/**
	 * Create a new {@link NXdetector_group}.
	 *
	 * @return new NXdetector_group
	 */
	public static NXdetector_group createNXdetector_group() {
		return new NXdetector_groupImpl();
	}

	/**
	 * Create a new {@link NXdetector_module} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdetector_module
	 */
	public static NXdetector_module createNXdetector_module(long oid) {
		return new NXdetector_moduleImpl(oid);
	}

	/**
	 * Create a new {@link NXdetector_module}.
	 *
	 * @return new NXdetector_module
	 */
	public static NXdetector_module createNXdetector_module() {
		return new NXdetector_moduleImpl();
	}

	/**
	 * Create a new {@link NXdisk_chopper} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdisk_chopper
	 */
	public static NXdisk_chopper createNXdisk_chopper(long oid) {
		return new NXdisk_chopperImpl(oid);
	}

	/**
	 * Create a new {@link NXdisk_chopper}.
	 *
	 * @return new NXdisk_chopper
	 */
	public static NXdisk_chopper createNXdisk_chopper() {
		return new NXdisk_chopperImpl();
	}

	/**
	 * Create a new {@link NXentry} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXentry
	 */
	public static NXentry createNXentry(long oid) {
		return new NXentryImpl(oid);
	}

	/**
	 * Create a new {@link NXentry}.
	 *
	 * @return new NXentry
	 */
	public static NXentry createNXentry() {
		return new NXentryImpl();
	}

	/**
	 * Create a new {@link NXenvironment} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXenvironment
	 */
	public static NXenvironment createNXenvironment(long oid) {
		return new NXenvironmentImpl(oid);
	}

	/**
	 * Create a new {@link NXenvironment}.
	 *
	 * @return new NXenvironment
	 */
	public static NXenvironment createNXenvironment() {
		return new NXenvironmentImpl();
	}

	/**
	 * Create a new {@link NXevent_data} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXevent_data
	 */
	public static NXevent_data createNXevent_data(long oid) {
		return new NXevent_dataImpl(oid);
	}

	/**
	 * Create a new {@link NXevent_data}.
	 *
	 * @return new NXevent_data
	 */
	public static NXevent_data createNXevent_data() {
		return new NXevent_dataImpl();
	}

	/**
	 * Create a new {@link NXfermi_chopper} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXfermi_chopper
	 */
	public static NXfermi_chopper createNXfermi_chopper(long oid) {
		return new NXfermi_chopperImpl(oid);
	}

	/**
	 * Create a new {@link NXfermi_chopper}.
	 *
	 * @return new NXfermi_chopper
	 */
	public static NXfermi_chopper createNXfermi_chopper() {
		return new NXfermi_chopperImpl();
	}

	/**
	 * Create a new {@link NXfilter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXfilter
	 */
	public static NXfilter createNXfilter(long oid) {
		return new NXfilterImpl(oid);
	}

	/**
	 * Create a new {@link NXfilter}.
	 *
	 * @return new NXfilter
	 */
	public static NXfilter createNXfilter() {
		return new NXfilterImpl();
	}

	/**
	 * Create a new {@link NXflipper} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXflipper
	 */
	public static NXflipper createNXflipper(long oid) {
		return new NXflipperImpl(oid);
	}

	/**
	 * Create a new {@link NXflipper}.
	 *
	 * @return new NXflipper
	 */
	public static NXflipper createNXflipper() {
		return new NXflipperImpl();
	}

	/**
	 * Create a new {@link NXfresnel_zone_plate} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXfresnel_zone_plate
	 */
	public static NXfresnel_zone_plate createNXfresnel_zone_plate(long oid) {
		return new NXfresnel_zone_plateImpl(oid);
	}

	/**
	 * Create a new {@link NXfresnel_zone_plate}.
	 *
	 * @return new NXfresnel_zone_plate
	 */
	public static NXfresnel_zone_plate createNXfresnel_zone_plate() {
		return new NXfresnel_zone_plateImpl();
	}

	/**
	 * Create a new {@link NXgeometry} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXgeometry
	 */
	public static NXgeometry createNXgeometry(long oid) {
		return new NXgeometryImpl(oid);
	}

	/**
	 * Create a new {@link NXgeometry}.
	 *
	 * @return new NXgeometry
	 */
	public static NXgeometry createNXgeometry() {
		return new NXgeometryImpl();
	}

	/**
	 * Create a new {@link NXgrating} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXgrating
	 */
	public static NXgrating createNXgrating(long oid) {
		return new NXgratingImpl(oid);
	}

	/**
	 * Create a new {@link NXgrating}.
	 *
	 * @return new NXgrating
	 */
	public static NXgrating createNXgrating() {
		return new NXgratingImpl();
	}

	/**
	 * Create a new {@link NXguide} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXguide
	 */
	public static NXguide createNXguide(long oid) {
		return new NXguideImpl(oid);
	}

	/**
	 * Create a new {@link NXguide}.
	 *
	 * @return new NXguide
	 */
	public static NXguide createNXguide() {
		return new NXguideImpl();
	}

	/**
	 * Create a new {@link NXinsertion_device} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXinsertion_device
	 */
	public static NXinsertion_device createNXinsertion_device(long oid) {
		return new NXinsertion_deviceImpl(oid);
	}

	/**
	 * Create a new {@link NXinsertion_device}.
	 *
	 * @return new NXinsertion_device
	 */
	public static NXinsertion_device createNXinsertion_device() {
		return new NXinsertion_deviceImpl();
	}

	/**
	 * Create a new {@link NXinstrument} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXinstrument
	 */
	public static NXinstrument createNXinstrument(long oid) {
		return new NXinstrumentImpl(oid);
	}

	/**
	 * Create a new {@link NXinstrument}.
	 *
	 * @return new NXinstrument
	 */
	public static NXinstrument createNXinstrument() {
		return new NXinstrumentImpl();
	}

	/**
	 * Create a new {@link NXlog} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXlog
	 */
	public static NXlog createNXlog(long oid) {
		return new NXlogImpl(oid);
	}

	/**
	 * Create a new {@link NXlog}.
	 *
	 * @return new NXlog
	 */
	public static NXlog createNXlog() {
		return new NXlogImpl();
	}

	/**
	 * Create a new {@link NXmirror} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmirror
	 */
	public static NXmirror createNXmirror(long oid) {
		return new NXmirrorImpl(oid);
	}

	/**
	 * Create a new {@link NXmirror}.
	 *
	 * @return new NXmirror
	 */
	public static NXmirror createNXmirror() {
		return new NXmirrorImpl();
	}

	/**
	 * Create a new {@link NXmoderator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmoderator
	 */
	public static NXmoderator createNXmoderator(long oid) {
		return new NXmoderatorImpl(oid);
	}

	/**
	 * Create a new {@link NXmoderator}.
	 *
	 * @return new NXmoderator
	 */
	public static NXmoderator createNXmoderator() {
		return new NXmoderatorImpl();
	}

	/**
	 * Create a new {@link NXmonitor} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmonitor
	 */
	public static NXmonitor createNXmonitor(long oid) {
		return new NXmonitorImpl(oid);
	}

	/**
	 * Create a new {@link NXmonitor}.
	 *
	 * @return new NXmonitor
	 */
	public static NXmonitor createNXmonitor() {
		return new NXmonitorImpl();
	}

	/**
	 * Create a new {@link NXmonochromator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmonochromator
	 */
	public static NXmonochromator createNXmonochromator(long oid) {
		return new NXmonochromatorImpl(oid);
	}

	/**
	 * Create a new {@link NXmonochromator}.
	 *
	 * @return new NXmonochromator
	 */
	public static NXmonochromator createNXmonochromator() {
		return new NXmonochromatorImpl();
	}

	/**
	 * Create a new {@link NXnote} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXnote
	 */
	public static NXnote createNXnote(long oid) {
		return new NXnoteImpl(oid);
	}

	/**
	 * Create a new {@link NXnote}.
	 *
	 * @return new NXnote
	 */
	public static NXnote createNXnote() {
		return new NXnoteImpl();
	}

	/**
	 * Create a new {@link NXoff_geometry} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXoff_geometry
	 */
	public static NXoff_geometry createNXoff_geometry(long oid) {
		return new NXoff_geometryImpl(oid);
	}

	/**
	 * Create a new {@link NXoff_geometry}.
	 *
	 * @return new NXoff_geometry
	 */
	public static NXoff_geometry createNXoff_geometry() {
		return new NXoff_geometryImpl();
	}

	/**
	 * Create a new {@link NXorientation} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXorientation
	 */
	public static NXorientation createNXorientation(long oid) {
		return new NXorientationImpl(oid);
	}

	/**
	 * Create a new {@link NXorientation}.
	 *
	 * @return new NXorientation
	 */
	public static NXorientation createNXorientation() {
		return new NXorientationImpl();
	}

	/**
	 * Create a new {@link NXparameters} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXparameters
	 */
	public static NXparameters createNXparameters(long oid) {
		return new NXparametersImpl(oid);
	}

	/**
	 * Create a new {@link NXparameters}.
	 *
	 * @return new NXparameters
	 */
	public static NXparameters createNXparameters() {
		return new NXparametersImpl();
	}

	/**
	 * Create a new {@link NXpdb} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpdb
	 */
	public static NXpdb createNXpdb(long oid) {
		return new NXpdbImpl(oid);
	}

	/**
	 * Create a new {@link NXpdb}.
	 *
	 * @return new NXpdb
	 */
	public static NXpdb createNXpdb() {
		return new NXpdbImpl();
	}

	/**
	 * Create a new {@link NXpinhole} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpinhole
	 */
	public static NXpinhole createNXpinhole(long oid) {
		return new NXpinholeImpl(oid);
	}

	/**
	 * Create a new {@link NXpinhole}.
	 *
	 * @return new NXpinhole
	 */
	public static NXpinhole createNXpinhole() {
		return new NXpinholeImpl();
	}

	/**
	 * Create a new {@link NXpolarizer} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpolarizer
	 */
	public static NXpolarizer createNXpolarizer(long oid) {
		return new NXpolarizerImpl(oid);
	}

	/**
	 * Create a new {@link NXpolarizer}.
	 *
	 * @return new NXpolarizer
	 */
	public static NXpolarizer createNXpolarizer() {
		return new NXpolarizerImpl();
	}

	/**
	 * Create a new {@link NXpositioner} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpositioner
	 */
	public static NXpositioner createNXpositioner(long oid) {
		return new NXpositionerImpl(oid);
	}

	/**
	 * Create a new {@link NXpositioner}.
	 *
	 * @return new NXpositioner
	 */
	public static NXpositioner createNXpositioner() {
		return new NXpositionerImpl();
	}

	/**
	 * Create a new {@link NXprocess} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXprocess
	 */
	public static NXprocess createNXprocess(long oid) {
		return new NXprocessImpl(oid);
	}

	/**
	 * Create a new {@link NXprocess}.
	 *
	 * @return new NXprocess
	 */
	public static NXprocess createNXprocess() {
		return new NXprocessImpl();
	}

	/**
	 * Create a new {@link NXreflections} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXreflections
	 */
	public static NXreflections createNXreflections(long oid) {
		return new NXreflectionsImpl(oid);
	}

	/**
	 * Create a new {@link NXreflections}.
	 *
	 * @return new NXreflections
	 */
	public static NXreflections createNXreflections() {
		return new NXreflectionsImpl();
	}

	/**
	 * Create a new {@link NXroot} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXroot
	 */
	public static NXroot createNXroot(long oid) {
		return new NXrootImpl(oid);
	}

	/**
	 * Create a new {@link NXroot}.
	 *
	 * @return new NXroot
	 */
	public static NXroot createNXroot() {
		return new NXrootImpl();
	}

	/**
	 * Create a new {@link NXsample} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsample
	 */
	public static NXsample createNXsample(long oid) {
		return new NXsampleImpl(oid);
	}

	/**
	 * Create a new {@link NXsample}.
	 *
	 * @return new NXsample
	 */
	public static NXsample createNXsample() {
		return new NXsampleImpl();
	}

	/**
	 * Create a new {@link NXsample_component} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsample_component
	 */
	public static NXsample_component createNXsample_component(long oid) {
		return new NXsample_componentImpl(oid);
	}

	/**
	 * Create a new {@link NXsample_component}.
	 *
	 * @return new NXsample_component
	 */
	public static NXsample_component createNXsample_component() {
		return new NXsample_componentImpl();
	}

	/**
	 * Create a new {@link NXsensor} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsensor
	 */
	public static NXsensor createNXsensor(long oid) {
		return new NXsensorImpl(oid);
	}

	/**
	 * Create a new {@link NXsensor}.
	 *
	 * @return new NXsensor
	 */
	public static NXsensor createNXsensor() {
		return new NXsensorImpl();
	}

	/**
	 * Create a new {@link NXshape} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXshape
	 */
	public static NXshape createNXshape(long oid) {
		return new NXshapeImpl(oid);
	}

	/**
	 * Create a new {@link NXshape}.
	 *
	 * @return new NXshape
	 */
	public static NXshape createNXshape() {
		return new NXshapeImpl();
	}

	/**
	 * Create a new {@link NXslit} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXslit
	 */
	public static NXslit createNXslit(long oid) {
		return new NXslitImpl(oid);
	}

	/**
	 * Create a new {@link NXslit}.
	 *
	 * @return new NXslit
	 */
	public static NXslit createNXslit() {
		return new NXslitImpl();
	}

	/**
	 * Create a new {@link NXsource} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsource
	 */
	public static NXsource createNXsource(long oid) {
		return new NXsourceImpl(oid);
	}

	/**
	 * Create a new {@link NXsource}.
	 *
	 * @return new NXsource
	 */
	public static NXsource createNXsource() {
		return new NXsourceImpl();
	}

	/**
	 * Create a new {@link NXsubentry} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsubentry
	 */
	public static NXsubentry createNXsubentry(long oid) {
		return new NXsubentryImpl(oid);
	}

	/**
	 * Create a new {@link NXsubentry}.
	 *
	 * @return new NXsubentry
	 */
	public static NXsubentry createNXsubentry() {
		return new NXsubentryImpl();
	}

	/**
	 * Create a new {@link NXtransformations} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXtransformations
	 */
	public static NXtransformations createNXtransformations(long oid) {
		return new NXtransformationsImpl(oid);
	}

	/**
	 * Create a new {@link NXtransformations}.
	 *
	 * @return new NXtransformations
	 */
	public static NXtransformations createNXtransformations() {
		return new NXtransformationsImpl();
	}

	/**
	 * Create a new {@link NXtranslation} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXtranslation
	 */
	public static NXtranslation createNXtranslation(long oid) {
		return new NXtranslationImpl(oid);
	}

	/**
	 * Create a new {@link NXtranslation}.
	 *
	 * @return new NXtranslation
	 */
	public static NXtranslation createNXtranslation() {
		return new NXtranslationImpl();
	}

	/**
	 * Create a new {@link NXuser} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXuser
	 */
	public static NXuser createNXuser(long oid) {
		return new NXuserImpl(oid);
	}

	/**
	 * Create a new {@link NXuser}.
	 *
	 * @return new NXuser
	 */
	public static NXuser createNXuser() {
		return new NXuserImpl();
	}

	/**
	 * Create a new {@link NXvelocity_selector} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXvelocity_selector
	 */
	public static NXvelocity_selector createNXvelocity_selector(long oid) {
		return new NXvelocity_selectorImpl(oid);
	}

	/**
	 * Create a new {@link NXvelocity_selector}.
	 *
	 * @return new NXvelocity_selector
	 */
	public static NXvelocity_selector createNXvelocity_selector() {
		return new NXvelocity_selectorImpl();
	}

	/**
	 * Create a new {@link NXxraylens} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXxraylens
	 */
	public static NXxraylens createNXxraylens(long oid) {
		return new NXxraylensImpl(oid);
	}

	/**
	 * Create a new {@link NXxraylens}.
	 *
	 * @return new NXxraylens
	 */
	public static NXxraylens createNXxraylens() {
		return new NXxraylensImpl();
	}

	/**
	 * Create a new {@link NXaberration} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXaberration
	 */
	public static NXaberration createNXaberration(long oid) {
		return new NXaberrationImpl(oid);
	}

	/**
	 * Create a new {@link NXaberration}.
	 *
	 * @return new NXaberration
	 */
	public static NXaberration createNXaberration() {
		return new NXaberrationImpl();
	}

	/**
	 * Create a new {@link NXaperture_em} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXaperture_em
	 */
	public static NXaperture_em createNXaperture_em(long oid) {
		return new NXaperture_emImpl(oid);
	}

	/**
	 * Create a new {@link NXaperture_em}.
	 *
	 * @return new NXaperture_em
	 */
	public static NXaperture_em createNXaperture_em() {
		return new NXaperture_emImpl();
	}

	/**
	 * Create a new {@link NXcalibration} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcalibration
	 */
	public static NXcalibration createNXcalibration(long oid) {
		return new NXcalibrationImpl(oid);
	}

	/**
	 * Create a new {@link NXcalibration}.
	 *
	 * @return new NXcalibration
	 */
	public static NXcalibration createNXcalibration() {
		return new NXcalibrationImpl();
	}

	/**
	 * Create a new {@link NXchamber} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXchamber
	 */
	public static NXchamber createNXchamber(long oid) {
		return new NXchamberImpl(oid);
	}

	/**
	 * Create a new {@link NXchamber}.
	 *
	 * @return new NXchamber
	 */
	public static NXchamber createNXchamber() {
		return new NXchamberImpl();
	}

	/**
	 * Create a new {@link NXcollectioncolumn} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcollectioncolumn
	 */
	public static NXcollectioncolumn createNXcollectioncolumn(long oid) {
		return new NXcollectioncolumnImpl(oid);
	}

	/**
	 * Create a new {@link NXcollectioncolumn}.
	 *
	 * @return new NXcollectioncolumn
	 */
	public static NXcollectioncolumn createNXcollectioncolumn() {
		return new NXcollectioncolumnImpl();
	}

	/**
	 * Create a new {@link NXcontainer} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcontainer
	 */
	public static NXcontainer createNXcontainer(long oid) {
		return new NXcontainerImpl(oid);
	}

	/**
	 * Create a new {@link NXcontainer}.
	 *
	 * @return new NXcontainer
	 */
	public static NXcontainer createNXcontainer() {
		return new NXcontainerImpl();
	}

	/**
	 * Create a new {@link NXcoordinate_system_set} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcoordinate_system_set
	 */
	public static NXcoordinate_system_set createNXcoordinate_system_set(long oid) {
		return new NXcoordinate_system_setImpl(oid);
	}

	/**
	 * Create a new {@link NXcoordinate_system_set}.
	 *
	 * @return new NXcoordinate_system_set
	 */
	public static NXcoordinate_system_set createNXcoordinate_system_set() {
		return new NXcoordinate_system_setImpl();
	}

	/**
	 * Create a new {@link NXcorrector_cs} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcorrector_cs
	 */
	public static NXcorrector_cs createNXcorrector_cs(long oid) {
		return new NXcorrector_csImpl(oid);
	}

	/**
	 * Create a new {@link NXcorrector_cs}.
	 *
	 * @return new NXcorrector_cs
	 */
	public static NXcorrector_cs createNXcorrector_cs() {
		return new NXcorrector_csImpl();
	}

	/**
	 * Create a new {@link NXcsg} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcsg
	 */
	public static NXcsg createNXcsg(long oid) {
		return new NXcsgImpl(oid);
	}

	/**
	 * Create a new {@link NXcsg}.
	 *
	 * @return new NXcsg
	 */
	public static NXcsg createNXcsg() {
		return new NXcsgImpl();
	}

	/**
	 * Create a new {@link NXdeflector} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdeflector
	 */
	public static NXdeflector createNXdeflector(long oid) {
		return new NXdeflectorImpl(oid);
	}

	/**
	 * Create a new {@link NXdeflector}.
	 *
	 * @return new NXdeflector
	 */
	public static NXdeflector createNXdeflector() {
		return new NXdeflectorImpl();
	}

	/**
	 * Create a new {@link NXdistortion} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdistortion
	 */
	public static NXdistortion createNXdistortion(long oid) {
		return new NXdistortionImpl(oid);
	}

	/**
	 * Create a new {@link NXdistortion}.
	 *
	 * @return new NXdistortion
	 */
	public static NXdistortion createNXdistortion() {
		return new NXdistortionImpl();
	}

	/**
	 * Create a new {@link NXebeam_column} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXebeam_column
	 */
	public static NXebeam_column createNXebeam_column(long oid) {
		return new NXebeam_columnImpl(oid);
	}

	/**
	 * Create a new {@link NXebeam_column}.
	 *
	 * @return new NXebeam_column
	 */
	public static NXebeam_column createNXebeam_column() {
		return new NXebeam_columnImpl();
	}

	/**
	 * Create a new {@link NXelectronanalyser} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXelectronanalyser
	 */
	public static NXelectronanalyser createNXelectronanalyser(long oid) {
		return new NXelectronanalyserImpl(oid);
	}

	/**
	 * Create a new {@link NXelectronanalyser}.
	 *
	 * @return new NXelectronanalyser
	 */
	public static NXelectronanalyser createNXelectronanalyser() {
		return new NXelectronanalyserImpl();
	}

	/**
	 * Create a new {@link NXelectrostatic_kicker} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXelectrostatic_kicker
	 */
	public static NXelectrostatic_kicker createNXelectrostatic_kicker(long oid) {
		return new NXelectrostatic_kickerImpl(oid);
	}

	/**
	 * Create a new {@link NXelectrostatic_kicker}.
	 *
	 * @return new NXelectrostatic_kicker
	 */
	public static NXelectrostatic_kicker createNXelectrostatic_kicker() {
		return new NXelectrostatic_kickerImpl();
	}

	/**
	 * Create a new {@link NXenergydispersion} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXenergydispersion
	 */
	public static NXenergydispersion createNXenergydispersion(long oid) {
		return new NXenergydispersionImpl(oid);
	}

	/**
	 * Create a new {@link NXenergydispersion}.
	 *
	 * @return new NXenergydispersion
	 */
	public static NXenergydispersion createNXenergydispersion() {
		return new NXenergydispersionImpl();
	}

	/**
	 * Create a new {@link NXevent_data_em} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXevent_data_em
	 */
	public static NXevent_data_em createNXevent_data_em(long oid) {
		return new NXevent_data_emImpl(oid);
	}

	/**
	 * Create a new {@link NXevent_data_em}.
	 *
	 * @return new NXevent_data_em
	 */
	public static NXevent_data_em createNXevent_data_em() {
		return new NXevent_data_emImpl();
	}

	/**
	 * Create a new {@link NXevent_data_em_set} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXevent_data_em_set
	 */
	public static NXevent_data_em_set createNXevent_data_em_set(long oid) {
		return new NXevent_data_em_setImpl(oid);
	}

	/**
	 * Create a new {@link NXevent_data_em_set}.
	 *
	 * @return new NXevent_data_em_set
	 */
	public static NXevent_data_em_set createNXevent_data_em_set() {
		return new NXevent_data_em_setImpl();
	}

	/**
	 * Create a new {@link NXibeam_column} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXibeam_column
	 */
	public static NXibeam_column createNXibeam_column(long oid) {
		return new NXibeam_columnImpl(oid);
	}

	/**
	 * Create a new {@link NXibeam_column}.
	 *
	 * @return new NXibeam_column
	 */
	public static NXibeam_column createNXibeam_column() {
		return new NXibeam_columnImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_adf} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_adf
	 */
	public static NXimage_set_em_adf createNXimage_set_em_adf(long oid) {
		return new NXimage_set_em_adfImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_adf}.
	 *
	 * @return new NXimage_set_em_adf
	 */
	public static NXimage_set_em_adf createNXimage_set_em_adf() {
		return new NXimage_set_em_adfImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_bf} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_bf
	 */
	public static NXimage_set_em_bf createNXimage_set_em_bf(long oid) {
		return new NXimage_set_em_bfImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_bf}.
	 *
	 * @return new NXimage_set_em_bf
	 */
	public static NXimage_set_em_bf createNXimage_set_em_bf() {
		return new NXimage_set_em_bfImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_bse} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_bse
	 */
	public static NXimage_set_em_bse createNXimage_set_em_bse(long oid) {
		return new NXimage_set_em_bseImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_bse}.
	 *
	 * @return new NXimage_set_em_bse
	 */
	public static NXimage_set_em_bse createNXimage_set_em_bse() {
		return new NXimage_set_em_bseImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_chamber} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_chamber
	 */
	public static NXimage_set_em_chamber createNXimage_set_em_chamber(long oid) {
		return new NXimage_set_em_chamberImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_chamber}.
	 *
	 * @return new NXimage_set_em_chamber
	 */
	public static NXimage_set_em_chamber createNXimage_set_em_chamber() {
		return new NXimage_set_em_chamberImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_df} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_df
	 */
	public static NXimage_set_em_df createNXimage_set_em_df(long oid) {
		return new NXimage_set_em_dfImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_df}.
	 *
	 * @return new NXimage_set_em_df
	 */
	public static NXimage_set_em_df createNXimage_set_em_df() {
		return new NXimage_set_em_dfImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_diffrac} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_diffrac
	 */
	public static NXimage_set_em_diffrac createNXimage_set_em_diffrac(long oid) {
		return new NXimage_set_em_diffracImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_diffrac}.
	 *
	 * @return new NXimage_set_em_diffrac
	 */
	public static NXimage_set_em_diffrac createNXimage_set_em_diffrac() {
		return new NXimage_set_em_diffracImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_ecci} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_ecci
	 */
	public static NXimage_set_em_ecci createNXimage_set_em_ecci(long oid) {
		return new NXimage_set_em_ecciImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_ecci}.
	 *
	 * @return new NXimage_set_em_ecci
	 */
	public static NXimage_set_em_ecci createNXimage_set_em_ecci() {
		return new NXimage_set_em_ecciImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_kikuchi} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_kikuchi
	 */
	public static NXimage_set_em_kikuchi createNXimage_set_em_kikuchi(long oid) {
		return new NXimage_set_em_kikuchiImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_kikuchi}.
	 *
	 * @return new NXimage_set_em_kikuchi
	 */
	public static NXimage_set_em_kikuchi createNXimage_set_em_kikuchi() {
		return new NXimage_set_em_kikuchiImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_ronchigram} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_ronchigram
	 */
	public static NXimage_set_em_ronchigram createNXimage_set_em_ronchigram(long oid) {
		return new NXimage_set_em_ronchigramImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_ronchigram}.
	 *
	 * @return new NXimage_set_em_ronchigram
	 */
	public static NXimage_set_em_ronchigram createNXimage_set_em_ronchigram() {
		return new NXimage_set_em_ronchigramImpl();
	}

	/**
	 * Create a new {@link NXimage_set_em_se} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage_set_em_se
	 */
	public static NXimage_set_em_se createNXimage_set_em_se(long oid) {
		return new NXimage_set_em_seImpl(oid);
	}

	/**
	 * Create a new {@link NXimage_set_em_se}.
	 *
	 * @return new NXimage_set_em_se
	 */
	public static NXimage_set_em_se createNXimage_set_em_se() {
		return new NXimage_set_em_seImpl();
	}

	/**
	 * Create a new {@link NXinteraction_vol_em} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXinteraction_vol_em
	 */
	public static NXinteraction_vol_em createNXinteraction_vol_em(long oid) {
		return new NXinteraction_vol_emImpl(oid);
	}

	/**
	 * Create a new {@link NXinteraction_vol_em}.
	 *
	 * @return new NXinteraction_vol_em
	 */
	public static NXinteraction_vol_em createNXinteraction_vol_em() {
		return new NXinteraction_vol_emImpl();
	}

	/**
	 * Create a new {@link NXion} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXion
	 */
	public static NXion createNXion(long oid) {
		return new NXionImpl(oid);
	}

	/**
	 * Create a new {@link NXion}.
	 *
	 * @return new NXion
	 */
	public static NXion createNXion() {
		return new NXionImpl();
	}

	/**
	 * Create a new {@link NXlens_em} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXlens_em
	 */
	public static NXlens_em createNXlens_em(long oid) {
		return new NXlens_emImpl(oid);
	}

	/**
	 * Create a new {@link NXlens_em}.
	 *
	 * @return new NXlens_em
	 */
	public static NXlens_em createNXlens_em() {
		return new NXlens_emImpl();
	}

	/**
	 * Create a new {@link NXmagnetic_kicker} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmagnetic_kicker
	 */
	public static NXmagnetic_kicker createNXmagnetic_kicker(long oid) {
		return new NXmagnetic_kickerImpl(oid);
	}

	/**
	 * Create a new {@link NXmagnetic_kicker}.
	 *
	 * @return new NXmagnetic_kicker
	 */
	public static NXmagnetic_kicker createNXmagnetic_kicker() {
		return new NXmagnetic_kickerImpl();
	}

	/**
	 * Create a new {@link NXmanipulator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmanipulator
	 */
	public static NXmanipulator createNXmanipulator(long oid) {
		return new NXmanipulatorImpl(oid);
	}

	/**
	 * Create a new {@link NXmanipulator}.
	 *
	 * @return new NXmanipulator
	 */
	public static NXmanipulator createNXmanipulator() {
		return new NXmanipulatorImpl();
	}

	/**
	 * Create a new {@link NXmanufacturer} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmanufacturer
	 */
	public static NXmanufacturer createNXmanufacturer(long oid) {
		return new NXmanufacturerImpl(oid);
	}

	/**
	 * Create a new {@link NXmanufacturer}.
	 *
	 * @return new NXmanufacturer
	 */
	public static NXmanufacturer createNXmanufacturer() {
		return new NXmanufacturerImpl();
	}

	/**
	 * Create a new {@link NXoptical_system_em} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXoptical_system_em
	 */
	public static NXoptical_system_em createNXoptical_system_em(long oid) {
		return new NXoptical_system_emImpl(oid);
	}

	/**
	 * Create a new {@link NXoptical_system_em}.
	 *
	 * @return new NXoptical_system_em
	 */
	public static NXoptical_system_em createNXoptical_system_em() {
		return new NXoptical_system_emImpl();
	}

	/**
	 * Create a new {@link NXpeak} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpeak
	 */
	public static NXpeak createNXpeak(long oid) {
		return new NXpeakImpl(oid);
	}

	/**
	 * Create a new {@link NXpeak}.
	 *
	 * @return new NXpeak
	 */
	public static NXpeak createNXpeak() {
		return new NXpeakImpl();
	}

	/**
	 * Create a new {@link NXpulser_apm} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpulser_apm
	 */
	public static NXpulser_apm createNXpulser_apm(long oid) {
		return new NXpulser_apmImpl(oid);
	}

	/**
	 * Create a new {@link NXpulser_apm}.
	 *
	 * @return new NXpulser_apm
	 */
	public static NXpulser_apm createNXpulser_apm() {
		return new NXpulser_apmImpl();
	}

	/**
	 * Create a new {@link NXpump} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpump
	 */
	public static NXpump createNXpump(long oid) {
		return new NXpumpImpl(oid);
	}

	/**
	 * Create a new {@link NXpump}.
	 *
	 * @return new NXpump
	 */
	public static NXpump createNXpump() {
		return new NXpumpImpl();
	}

	/**
	 * Create a new {@link NXquadric} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXquadric
	 */
	public static NXquadric createNXquadric(long oid) {
		return new NXquadricImpl(oid);
	}

	/**
	 * Create a new {@link NXquadric}.
	 *
	 * @return new NXquadric
	 */
	public static NXquadric createNXquadric() {
		return new NXquadricImpl();
	}

	/**
	 * Create a new {@link NXquadrupole_magnet} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXquadrupole_magnet
	 */
	public static NXquadrupole_magnet createNXquadrupole_magnet(long oid) {
		return new NXquadrupole_magnetImpl(oid);
	}

	/**
	 * Create a new {@link NXquadrupole_magnet}.
	 *
	 * @return new NXquadrupole_magnet
	 */
	public static NXquadrupole_magnet createNXquadrupole_magnet() {
		return new NXquadrupole_magnetImpl();
	}

	/**
	 * Create a new {@link NXreflectron} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXreflectron
	 */
	public static NXreflectron createNXreflectron(long oid) {
		return new NXreflectronImpl(oid);
	}

	/**
	 * Create a new {@link NXreflectron}.
	 *
	 * @return new NXreflectron
	 */
	public static NXreflectron createNXreflectron() {
		return new NXreflectronImpl();
	}

	/**
	 * Create a new {@link NXregion} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXregion
	 */
	public static NXregion createNXregion(long oid) {
		return new NXregionImpl(oid);
	}

	/**
	 * Create a new {@link NXregion}.
	 *
	 * @return new NXregion
	 */
	public static NXregion createNXregion() {
		return new NXregionImpl();
	}

	/**
	 * Create a new {@link NXregistration} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXregistration
	 */
	public static NXregistration createNXregistration(long oid) {
		return new NXregistrationImpl(oid);
	}

	/**
	 * Create a new {@link NXregistration}.
	 *
	 * @return new NXregistration
	 */
	public static NXregistration createNXregistration() {
		return new NXregistrationImpl();
	}

	/**
	 * Create a new {@link NXscanbox_em} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXscanbox_em
	 */
	public static NXscanbox_em createNXscanbox_em(long oid) {
		return new NXscanbox_emImpl(oid);
	}

	/**
	 * Create a new {@link NXscanbox_em}.
	 *
	 * @return new NXscanbox_em
	 */
	public static NXscanbox_em createNXscanbox_em() {
		return new NXscanbox_emImpl();
	}

	/**
	 * Create a new {@link NXseparator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXseparator
	 */
	public static NXseparator createNXseparator(long oid) {
		return new NXseparatorImpl(oid);
	}

	/**
	 * Create a new {@link NXseparator}.
	 *
	 * @return new NXseparator
	 */
	public static NXseparator createNXseparator() {
		return new NXseparatorImpl();
	}

	/**
	 * Create a new {@link NXsolenoid_magnet} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsolenoid_magnet
	 */
	public static NXsolenoid_magnet createNXsolenoid_magnet(long oid) {
		return new NXsolenoid_magnetImpl(oid);
	}

	/**
	 * Create a new {@link NXsolenoid_magnet}.
	 *
	 * @return new NXsolenoid_magnet
	 */
	public static NXsolenoid_magnet createNXsolenoid_magnet() {
		return new NXsolenoid_magnetImpl();
	}

	/**
	 * Create a new {@link NXsolid_geometry} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsolid_geometry
	 */
	public static NXsolid_geometry createNXsolid_geometry(long oid) {
		return new NXsolid_geometryImpl(oid);
	}

	/**
	 * Create a new {@link NXsolid_geometry}.
	 *
	 * @return new NXsolid_geometry
	 */
	public static NXsolid_geometry createNXsolid_geometry() {
		return new NXsolid_geometryImpl();
	}

	/**
	 * Create a new {@link NXspectrum_set_em_auger} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspectrum_set_em_auger
	 */
	public static NXspectrum_set_em_auger createNXspectrum_set_em_auger(long oid) {
		return new NXspectrum_set_em_augerImpl(oid);
	}

	/**
	 * Create a new {@link NXspectrum_set_em_auger}.
	 *
	 * @return new NXspectrum_set_em_auger
	 */
	public static NXspectrum_set_em_auger createNXspectrum_set_em_auger() {
		return new NXspectrum_set_em_augerImpl();
	}

	/**
	 * Create a new {@link NXspectrum_set_em_cathodolum} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspectrum_set_em_cathodolum
	 */
	public static NXspectrum_set_em_cathodolum createNXspectrum_set_em_cathodolum(long oid) {
		return new NXspectrum_set_em_cathodolumImpl(oid);
	}

	/**
	 * Create a new {@link NXspectrum_set_em_cathodolum}.
	 *
	 * @return new NXspectrum_set_em_cathodolum
	 */
	public static NXspectrum_set_em_cathodolum createNXspectrum_set_em_cathodolum() {
		return new NXspectrum_set_em_cathodolumImpl();
	}

	/**
	 * Create a new {@link NXspectrum_set_em_eels} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspectrum_set_em_eels
	 */
	public static NXspectrum_set_em_eels createNXspectrum_set_em_eels(long oid) {
		return new NXspectrum_set_em_eelsImpl(oid);
	}

	/**
	 * Create a new {@link NXspectrum_set_em_eels}.
	 *
	 * @return new NXspectrum_set_em_eels
	 */
	public static NXspectrum_set_em_eels createNXspectrum_set_em_eels() {
		return new NXspectrum_set_em_eelsImpl();
	}

	/**
	 * Create a new {@link NXspectrum_set_em_xray} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspectrum_set_em_xray
	 */
	public static NXspectrum_set_em_xray createNXspectrum_set_em_xray(long oid) {
		return new NXspectrum_set_em_xrayImpl(oid);
	}

	/**
	 * Create a new {@link NXspectrum_set_em_xray}.
	 *
	 * @return new NXspectrum_set_em_xray
	 */
	public static NXspectrum_set_em_xray createNXspectrum_set_em_xray() {
		return new NXspectrum_set_em_xrayImpl();
	}

	/**
	 * Create a new {@link NXspin_rotator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspin_rotator
	 */
	public static NXspin_rotator createNXspin_rotator(long oid) {
		return new NXspin_rotatorImpl(oid);
	}

	/**
	 * Create a new {@link NXspin_rotator}.
	 *
	 * @return new NXspin_rotator
	 */
	public static NXspin_rotator createNXspin_rotator() {
		return new NXspin_rotatorImpl();
	}

	/**
	 * Create a new {@link NXspindispersion} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspindispersion
	 */
	public static NXspindispersion createNXspindispersion(long oid) {
		return new NXspindispersionImpl(oid);
	}

	/**
	 * Create a new {@link NXspindispersion}.
	 *
	 * @return new NXspindispersion
	 */
	public static NXspindispersion createNXspindispersion() {
		return new NXspindispersionImpl();
	}

	/**
	 * Create a new {@link NXstage_lab} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXstage_lab
	 */
	public static NXstage_lab createNXstage_lab(long oid) {
		return new NXstage_labImpl(oid);
	}

	/**
	 * Create a new {@link NXstage_lab}.
	 *
	 * @return new NXstage_lab
	 */
	public static NXstage_lab createNXstage_lab() {
		return new NXstage_labImpl();
	}

}
