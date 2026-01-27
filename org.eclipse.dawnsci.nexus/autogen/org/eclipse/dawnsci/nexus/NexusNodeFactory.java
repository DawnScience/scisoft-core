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
import org.eclipse.dawnsci.nexus.impl.NXcg_gridImpl;
import org.eclipse.dawnsci.nexus.impl.NXcomponentImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_roiImpl;
import org.eclipse.dawnsci.nexus.impl.NXactivityImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_alpha_complexImpl;
import org.eclipse.dawnsci.nexus.impl.NXcrystalImpl;
import org.eclipse.dawnsci.nexus.impl.NXxraylensImpl;
import org.eclipse.dawnsci.nexus.impl.NXaberrationImpl;
import org.eclipse.dawnsci.nexus.impl.NXattenuatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_tetrahedronImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_memoryImpl;
import org.eclipse.dawnsci.nexus.impl.NXsourceImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_instrumentImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_parallelogramImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_prngImpl;
import org.eclipse.dawnsci.nexus.impl.NXactuatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_cylinderImpl;
import org.eclipse.dawnsci.nexus.impl.NXdeflectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXfilterImpl;
import org.eclipse.dawnsci.nexus.impl.NXgeometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXgratingImpl;
import org.eclipse.dawnsci.nexus.impl.NXmonochromatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXnoteImpl;
import org.eclipse.dawnsci.nexus.impl.NXoff_geometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_charge_state_analysisImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_pointImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_triangleImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_ebsdImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_ellipsoidImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_edsImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_face_list_data_structureImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_measurementImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_polygonImpl;
import org.eclipse.dawnsci.nexus.impl.NXentryImpl;
import org.eclipse.dawnsci.nexus.impl.NXenvironmentImpl;
import org.eclipse.dawnsci.nexus.impl.NXfresnel_zone_plateImpl;
import org.eclipse.dawnsci.nexus.impl.NXinstrumentImpl;
import org.eclipse.dawnsci.nexus.impl.NXlogImpl;
import org.eclipse.dawnsci.nexus.impl.NXorientationImpl;
import org.eclipse.dawnsci.nexus.impl.NXsampleImpl;
import org.eclipse.dawnsci.nexus.impl.NXshapeImpl;
import org.eclipse.dawnsci.nexus.impl.NXslitImpl;
import org.eclipse.dawnsci.nexus.impl.NXsubentryImpl;
import org.eclipse.dawnsci.nexus.impl.NXtranslationImpl;
import org.eclipse.dawnsci.nexus.impl.NXvelocity_selectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_rangingImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_polylineImpl;
import org.eclipse.dawnsci.nexus.impl.NXevent_dataImpl;
import org.eclipse.dawnsci.nexus.impl.NXguideImpl;
import org.eclipse.dawnsci.nexus.impl.NXmirrorImpl;
import org.eclipse.dawnsci.nexus.impl.NXmonitorImpl;
import org.eclipse.dawnsci.nexus.impl.NXbeamImpl;
import org.eclipse.dawnsci.nexus.impl.NXbeam_transfer_matrix_tableImpl;
import org.eclipse.dawnsci.nexus.impl.NXcapillaryImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_half_edge_data_structureImpl;
import org.eclipse.dawnsci.nexus.impl.NXciteImpl;
import org.eclipse.dawnsci.nexus.impl.NXcollectionImpl;
import org.eclipse.dawnsci.nexus.impl.NXcylindrical_geometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXfermi_chopperImpl;
import org.eclipse.dawnsci.nexus.impl.NXpolarizerImpl;
import org.eclipse.dawnsci.nexus.impl.NXbeam_stopImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_primitiveImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetector_moduleImpl;
import org.eclipse.dawnsci.nexus.impl.NXmoderatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXpositionerImpl;
import org.eclipse.dawnsci.nexus.impl.NXprocessImpl;
import org.eclipse.dawnsci.nexus.impl.NXreflectionsImpl;
import org.eclipse.dawnsci.nexus.impl.NXrootImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_event_dataImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_hexahedronImpl;
import org.eclipse.dawnsci.nexus.impl.NXinsertion_deviceImpl;
import org.eclipse.dawnsci.nexus.impl.NXpinholeImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_reconstructionImpl;
import org.eclipse.dawnsci.nexus.impl.NXbending_magnetImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_unit_normalImpl;
import org.eclipse.dawnsci.nexus.impl.NXdataImpl;
import org.eclipse.dawnsci.nexus.impl.NXdisk_chopperImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_eelsImpl;
import org.eclipse.dawnsci.nexus.impl.NXparametersImpl;
import org.eclipse.dawnsci.nexus.impl.NXpdbImpl;
import org.eclipse.dawnsci.nexus.impl.NXsample_componentImpl;
import org.eclipse.dawnsci.nexus.impl.NXsensorImpl;
import org.eclipse.dawnsci.nexus.impl.NXtransformationsImpl;
import org.eclipse.dawnsci.nexus.impl.NXuserImpl;
import org.eclipse.dawnsci.nexus.impl.NXapertureImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_simulationImpl;
import org.eclipse.dawnsci.nexus.impl.NXcalibrationImpl;
import org.eclipse.dawnsci.nexus.impl.NXcircuitImpl;
import org.eclipse.dawnsci.nexus.impl.NXcollimatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetector_groupImpl;
import org.eclipse.dawnsci.nexus.impl.NXflipperImpl;
import org.eclipse.dawnsci.nexus.impl.NXatomImpl;
import org.eclipse.dawnsci.nexus.impl.NXcg_polyhedronImpl;
import org.eclipse.dawnsci.nexus.impl.NXchemical_compositionImpl;
import org.eclipse.dawnsci.nexus.impl.NXcollectioncolumnImpl;
import org.eclipse.dawnsci.nexus.impl.NXcoordinate_systemImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_computerImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_filter_boolean_maskImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_processorImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_profiling_eventImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_storageImpl;
import org.eclipse.dawnsci.nexus.impl.NXebeam_columnImpl;
import org.eclipse.dawnsci.nexus.impl.NXelectronanalyzerImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_event_dataImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_instrumentImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_interaction_volumeImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_measurementImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_optical_systemImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_simulationImpl;
import org.eclipse.dawnsci.nexus.impl.NXenergydispersionImpl;
import org.eclipse.dawnsci.nexus.impl.NXfit_functionImpl;
import org.eclipse.dawnsci.nexus.impl.NXibeam_columnImpl;
import org.eclipse.dawnsci.nexus.impl.NXimageImpl;
import org.eclipse.dawnsci.nexus.impl.NXmanipulatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXoptical_lensImpl;
import org.eclipse.dawnsci.nexus.impl.NXoptical_windowImpl;
import org.eclipse.dawnsci.nexus.impl.NXpeakImpl;
import org.eclipse.dawnsci.nexus.impl.NXprogramImpl;
import org.eclipse.dawnsci.nexus.impl.NXpumpImpl;
import org.eclipse.dawnsci.nexus.impl.NXroi_processImpl;
import org.eclipse.dawnsci.nexus.impl.NXrotationsImpl;
import org.eclipse.dawnsci.nexus.impl.NXscan_controllerImpl;
import org.eclipse.dawnsci.nexus.impl.NXspectrumImpl;
import org.eclipse.dawnsci.nexus.impl.NXspindispersionImpl;
import org.eclipse.dawnsci.nexus.impl.NXunit_cellImpl;
import org.eclipse.dawnsci.nexus.impl.NXwaveplateImpl;
import org.eclipse.dawnsci.nexus.impl.NXregistrationImpl;
import org.eclipse.dawnsci.nexus.impl.NXresolutionImpl;
import org.eclipse.dawnsci.nexus.impl.NXcorrector_csImpl;
import org.eclipse.dawnsci.nexus.impl.NXcs_profilingImpl;
import org.eclipse.dawnsci.nexus.impl.NXdetector_channelImpl;
import org.eclipse.dawnsci.nexus.impl.NXdistortionImpl;
import org.eclipse.dawnsci.nexus.impl.NXelectromagnetic_lensImpl;
import org.eclipse.dawnsci.nexus.impl.NXem_imgImpl;
import org.eclipse.dawnsci.nexus.impl.NXfabricationImpl;
import org.eclipse.dawnsci.nexus.impl.NXhistoryImpl;
import org.eclipse.dawnsci.nexus.impl.NXpid_controllerImpl;
import org.eclipse.dawnsci.nexus.impl.NXelectron_detectorImpl;
import org.eclipse.dawnsci.nexus.impl.NXfitImpl;
import org.eclipse.dawnsci.nexus.impl.NXphaseImpl;
import org.eclipse.dawnsci.nexus.impl.NXsubstanceImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructure_odfImpl;
import org.eclipse.dawnsci.nexus.impl.NXsubsampling_filterImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_paraprobe_tool_commonImpl;
import org.eclipse.dawnsci.nexus.impl.NXdelocalizationImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructure_pfImpl;
import org.eclipse.dawnsci.nexus.impl.NXcontainerImpl;
import org.eclipse.dawnsci.nexus.impl.NXsolid_geometryImpl;
import org.eclipse.dawnsci.nexus.impl.NXcsgImpl;
import org.eclipse.dawnsci.nexus.impl.NXelectrostatic_kickerImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructure_ipfImpl;
import org.eclipse.dawnsci.nexus.impl.NXmagnetic_kickerImpl;
import org.eclipse.dawnsci.nexus.impl.NXquadrupole_magnetImpl;
import org.eclipse.dawnsci.nexus.impl.NXsimilarity_groupingImpl;
import org.eclipse.dawnsci.nexus.impl.NXbeam_splitterImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructure_featureImpl;
import org.eclipse.dawnsci.nexus.impl.NXmatch_filterImpl;
import org.eclipse.dawnsci.nexus.impl.NXregionImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructureImpl;
import org.eclipse.dawnsci.nexus.impl.NXoptical_fiberImpl;
import org.eclipse.dawnsci.nexus.impl.NXdispersionImpl;
import org.eclipse.dawnsci.nexus.impl.NXisocontourImpl;
import org.eclipse.dawnsci.nexus.impl.NXsolenoid_magnetImpl;
import org.eclipse.dawnsci.nexus.impl.NXspatial_filterImpl;
import org.eclipse.dawnsci.nexus.impl.NXseparatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXquadricImpl;
import org.eclipse.dawnsci.nexus.impl.NXdispersion_single_parameterImpl;
import org.eclipse.dawnsci.nexus.impl.NXdispersion_functionImpl;
import org.eclipse.dawnsci.nexus.impl.NXdispersion_repeated_parameterImpl;
import org.eclipse.dawnsci.nexus.impl.NXdispersion_tableImpl;
import org.eclipse.dawnsci.nexus.impl.NXspin_rotatorImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_paraprobe_tool_parametersImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructure_mtex_configImpl;
import org.eclipse.dawnsci.nexus.impl.NXmicrostructure_slip_systemImpl;
import org.eclipse.dawnsci.nexus.impl.NXapm_paraprobe_tool_processImpl;
import org.eclipse.dawnsci.nexus.impl.NXoptical_polarizerImpl;

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
			case NX_ACTIVITY:
				return createNXactivity(oid);
			case NX_ACTUATOR:
				return createNXactuator(oid);
			case NX_APERTURE:
				return createNXaperture(oid);
			case NX_APM_CHARGE_STATE_ANALYSIS:
				return createNXapm_charge_state_analysis(oid);
			case NX_APM_EVENT_DATA:
				return createNXapm_event_data(oid);
			case NX_APM_INSTRUMENT:
				return createNXapm_instrument(oid);
			case NX_APM_MEASUREMENT:
				return createNXapm_measurement(oid);
			case NX_APM_PARAPROBE_TOOL_COMMON:
				return createNXapm_paraprobe_tool_common(oid);
			case NX_APM_PARAPROBE_TOOL_PARAMETERS:
				return createNXapm_paraprobe_tool_parameters(oid);
			case NX_APM_PARAPROBE_TOOL_PROCESS:
				return createNXapm_paraprobe_tool_process(oid);
			case NX_APM_RANGING:
				return createNXapm_ranging(oid);
			case NX_APM_RECONSTRUCTION:
				return createNXapm_reconstruction(oid);
			case NX_APM_SIMULATION:
				return createNXapm_simulation(oid);
			case NX_ATOM:
				return createNXatom(oid);
			case NX_ATTENUATOR:
				return createNXattenuator(oid);
			case NX_BEAM:
				return createNXbeam(oid);
			case NX_BEAM_SPLITTER:
				return createNXbeam_splitter(oid);
			case NX_BEAM_STOP:
				return createNXbeam_stop(oid);
			case NX_BEAM_TRANSFER_MATRIX_TABLE:
				return createNXbeam_transfer_matrix_table(oid);
			case NX_BENDING_MAGNET:
				return createNXbending_magnet(oid);
			case NX_CALIBRATION:
				return createNXcalibration(oid);
			case NX_CAPILLARY:
				return createNXcapillary(oid);
			case NX_CG_ALPHA_COMPLEX:
				return createNXcg_alpha_complex(oid);
			case NX_CG_CYLINDER:
				return createNXcg_cylinder(oid);
			case NX_CG_ELLIPSOID:
				return createNXcg_ellipsoid(oid);
			case NX_CG_FACE_LIST_DATA_STRUCTURE:
				return createNXcg_face_list_data_structure(oid);
			case NX_CG_GRID:
				return createNXcg_grid(oid);
			case NX_CG_HALF_EDGE_DATA_STRUCTURE:
				return createNXcg_half_edge_data_structure(oid);
			case NX_CG_HEXAHEDRON:
				return createNXcg_hexahedron(oid);
			case NX_CG_PARALLELOGRAM:
				return createNXcg_parallelogram(oid);
			case NX_CG_POINT:
				return createNXcg_point(oid);
			case NX_CG_POLYGON:
				return createNXcg_polygon(oid);
			case NX_CG_POLYHEDRON:
				return createNXcg_polyhedron(oid);
			case NX_CG_POLYLINE:
				return createNXcg_polyline(oid);
			case NX_CG_PRIMITIVE:
				return createNXcg_primitive(oid);
			case NX_CG_ROI:
				return createNXcg_roi(oid);
			case NX_CG_TETRAHEDRON:
				return createNXcg_tetrahedron(oid);
			case NX_CG_TRIANGLE:
				return createNXcg_triangle(oid);
			case NX_CG_UNIT_NORMAL:
				return createNXcg_unit_normal(oid);
			case NX_CHEMICAL_COMPOSITION:
				return createNXchemical_composition(oid);
			case NX_CIRCUIT:
				return createNXcircuit(oid);
			case NX_CITE:
				return createNXcite(oid);
			case NX_COLLECTION:
				return createNXcollection(oid);
			case NX_COLLECTIONCOLUMN:
				return createNXcollectioncolumn(oid);
			case NX_COLLIMATOR:
				return createNXcollimator(oid);
			case NX_COMPONENT:
				return createNXcomponent(oid);
			case NX_CONTAINER:
				return createNXcontainer(oid);
			case NX_COORDINATE_SYSTEM:
				return createNXcoordinate_system(oid);
			case NX_CORRECTOR_CS:
				return createNXcorrector_cs(oid);
			case NX_CRYSTAL:
				return createNXcrystal(oid);
			case NX_CS_COMPUTER:
				return createNXcs_computer(oid);
			case NX_CS_FILTER_BOOLEAN_MASK:
				return createNXcs_filter_boolean_mask(oid);
			case NX_CS_MEMORY:
				return createNXcs_memory(oid);
			case NX_CS_PRNG:
				return createNXcs_prng(oid);
			case NX_CS_PROCESSOR:
				return createNXcs_processor(oid);
			case NX_CS_PROFILING:
				return createNXcs_profiling(oid);
			case NX_CS_PROFILING_EVENT:
				return createNXcs_profiling_event(oid);
			case NX_CS_STORAGE:
				return createNXcs_storage(oid);
			case NX_CSG:
				return createNXcsg(oid);
			case NX_CYLINDRICAL_GEOMETRY:
				return createNXcylindrical_geometry(oid);
			case NX_DATA:
				return createNXdata(oid);
			case NX_DEFLECTOR:
				return createNXdeflector(oid);
			case NX_DELOCALIZATION:
				return createNXdelocalization(oid);
			case NX_DETECTOR:
				return createNXdetector(oid);
			case NX_DETECTOR_CHANNEL:
				return createNXdetector_channel(oid);
			case NX_DETECTOR_GROUP:
				return createNXdetector_group(oid);
			case NX_DETECTOR_MODULE:
				return createNXdetector_module(oid);
			case NX_DISK_CHOPPER:
				return createNXdisk_chopper(oid);
			case NX_DISPERSION:
				return createNXdispersion(oid);
			case NX_DISPERSION_FUNCTION:
				return createNXdispersion_function(oid);
			case NX_DISPERSION_REPEATED_PARAMETER:
				return createNXdispersion_repeated_parameter(oid);
			case NX_DISPERSION_SINGLE_PARAMETER:
				return createNXdispersion_single_parameter(oid);
			case NX_DISPERSION_TABLE:
				return createNXdispersion_table(oid);
			case NX_DISTORTION:
				return createNXdistortion(oid);
			case NX_EBEAM_COLUMN:
				return createNXebeam_column(oid);
			case NX_ELECTROMAGNETIC_LENS:
				return createNXelectromagnetic_lens(oid);
			case NX_ELECTRON_DETECTOR:
				return createNXelectron_detector(oid);
			case NX_ELECTRONANALYZER:
				return createNXelectronanalyzer(oid);
			case NX_ELECTROSTATIC_KICKER:
				return createNXelectrostatic_kicker(oid);
			case NX_EM_EBSD:
				return createNXem_ebsd(oid);
			case NX_EM_EDS:
				return createNXem_eds(oid);
			case NX_EM_EELS:
				return createNXem_eels(oid);
			case NX_EM_EVENT_DATA:
				return createNXem_event_data(oid);
			case NX_EM_IMG:
				return createNXem_img(oid);
			case NX_EM_INSTRUMENT:
				return createNXem_instrument(oid);
			case NX_EM_INTERACTION_VOLUME:
				return createNXem_interaction_volume(oid);
			case NX_EM_MEASUREMENT:
				return createNXem_measurement(oid);
			case NX_EM_OPTICAL_SYSTEM:
				return createNXem_optical_system(oid);
			case NX_EM_SIMULATION:
				return createNXem_simulation(oid);
			case NX_ENERGYDISPERSION:
				return createNXenergydispersion(oid);
			case NX_ENTRY:
				return createNXentry(oid);
			case NX_ENVIRONMENT:
				return createNXenvironment(oid);
			case NX_EVENT_DATA:
				return createNXevent_data(oid);
			case NX_FABRICATION:
				return createNXfabrication(oid);
			case NX_FERMI_CHOPPER:
				return createNXfermi_chopper(oid);
			case NX_FILTER:
				return createNXfilter(oid);
			case NX_FIT:
				return createNXfit(oid);
			case NX_FIT_FUNCTION:
				return createNXfit_function(oid);
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
			case NX_HISTORY:
				return createNXhistory(oid);
			case NX_IBEAM_COLUMN:
				return createNXibeam_column(oid);
			case NX_IMAGE:
				return createNXimage(oid);
			case NX_INSERTION_DEVICE:
				return createNXinsertion_device(oid);
			case NX_INSTRUMENT:
				return createNXinstrument(oid);
			case NX_ISOCONTOUR:
				return createNXisocontour(oid);
			case NX_LOG:
				return createNXlog(oid);
			case NX_MAGNETIC_KICKER:
				return createNXmagnetic_kicker(oid);
			case NX_MANIPULATOR:
				return createNXmanipulator(oid);
			case NX_MATCH_FILTER:
				return createNXmatch_filter(oid);
			case NX_MICROSTRUCTURE:
				return createNXmicrostructure(oid);
			case NX_MICROSTRUCTURE_FEATURE:
				return createNXmicrostructure_feature(oid);
			case NX_MICROSTRUCTURE_IPF:
				return createNXmicrostructure_ipf(oid);
			case NX_MICROSTRUCTURE_MTEX_CONFIG:
				return createNXmicrostructure_mtex_config(oid);
			case NX_MICROSTRUCTURE_ODF:
				return createNXmicrostructure_odf(oid);
			case NX_MICROSTRUCTURE_PF:
				return createNXmicrostructure_pf(oid);
			case NX_MICROSTRUCTURE_SLIP_SYSTEM:
				return createNXmicrostructure_slip_system(oid);
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
			case NX_OPTICAL_FIBER:
				return createNXoptical_fiber(oid);
			case NX_OPTICAL_LENS:
				return createNXoptical_lens(oid);
			case NX_OPTICAL_POLARIZER:
				return createNXoptical_polarizer(oid);
			case NX_OPTICAL_WINDOW:
				return createNXoptical_window(oid);
			case NX_ORIENTATION:
				return createNXorientation(oid);
			case NX_PARAMETERS:
				return createNXparameters(oid);
			case NX_PDB:
				return createNXpdb(oid);
			case NX_PEAK:
				return createNXpeak(oid);
			case NX_PHASE:
				return createNXphase(oid);
			case NX_PID_CONTROLLER:
				return createNXpid_controller(oid);
			case NX_PINHOLE:
				return createNXpinhole(oid);
			case NX_POLARIZER:
				return createNXpolarizer(oid);
			case NX_POSITIONER:
				return createNXpositioner(oid);
			case NX_PROCESS:
				return createNXprocess(oid);
			case NX_PROGRAM:
				return createNXprogram(oid);
			case NX_PUMP:
				return createNXpump(oid);
			case NX_QUADRIC:
				return createNXquadric(oid);
			case NX_QUADRUPOLE_MAGNET:
				return createNXquadrupole_magnet(oid);
			case NX_REFLECTIONS:
				return createNXreflections(oid);
			case NX_REGION:
				return createNXregion(oid);
			case NX_REGISTRATION:
				return createNXregistration(oid);
			case NX_RESOLUTION:
				return createNXresolution(oid);
			case NX_ROI_PROCESS:
				return createNXroi_process(oid);
			case NX_ROOT:
				return createNXroot(oid);
			case NX_ROTATIONS:
				return createNXrotations(oid);
			case NX_SAMPLE:
				return createNXsample(oid);
			case NX_SAMPLE_COMPONENT:
				return createNXsample_component(oid);
			case NX_SCAN_CONTROLLER:
				return createNXscan_controller(oid);
			case NX_SENSOR:
				return createNXsensor(oid);
			case NX_SEPARATOR:
				return createNXseparator(oid);
			case NX_SHAPE:
				return createNXshape(oid);
			case NX_SIMILARITY_GROUPING:
				return createNXsimilarity_grouping(oid);
			case NX_SLIT:
				return createNXslit(oid);
			case NX_SOLENOID_MAGNET:
				return createNXsolenoid_magnet(oid);
			case NX_SOLID_GEOMETRY:
				return createNXsolid_geometry(oid);
			case NX_SOURCE:
				return createNXsource(oid);
			case NX_SPATIAL_FILTER:
				return createNXspatial_filter(oid);
			case NX_SPECTRUM:
				return createNXspectrum(oid);
			case NX_SPIN_ROTATOR:
				return createNXspin_rotator(oid);
			case NX_SPINDISPERSION:
				return createNXspindispersion(oid);
			case NX_SUBENTRY:
				return createNXsubentry(oid);
			case NX_SUBSAMPLING_FILTER:
				return createNXsubsampling_filter(oid);
			case NX_SUBSTANCE:
				return createNXsubstance(oid);
			case NX_TRANSFORMATIONS:
				return createNXtransformations(oid);
			case NX_TRANSLATION:
				return createNXtranslation(oid);
			case NX_UNIT_CELL:
				return createNXunit_cell(oid);
			case NX_USER:
				return createNXuser(oid);
			case NX_VELOCITY_SELECTOR:
				return createNXvelocity_selector(oid);
			case NX_WAVEPLATE:
				return createNXwaveplate(oid);
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
			case NX_CG_GRID:
				return createNXcg_grid();
			case NX_COMPONENT:
				return createNXcomponent();
			case NX_CG_ROI:
				return createNXcg_roi();
			case NX_ACTIVITY:
				return createNXactivity();
			case NX_CG_ALPHA_COMPLEX:
				return createNXcg_alpha_complex();
			case NX_CRYSTAL:
				return createNXcrystal();
			case NX_XRAYLENS:
				return createNXxraylens();
			case NX_ABERRATION:
				return createNXaberration();
			case NX_ATTENUATOR:
				return createNXattenuator();
			case NX_CG_TETRAHEDRON:
				return createNXcg_tetrahedron();
			case NX_CS_MEMORY:
				return createNXcs_memory();
			case NX_SOURCE:
				return createNXsource();
			case NX_APM_INSTRUMENT:
				return createNXapm_instrument();
			case NX_CG_PARALLELOGRAM:
				return createNXcg_parallelogram();
			case NX_CS_PRNG:
				return createNXcs_prng();
			case NX_ACTUATOR:
				return createNXactuator();
			case NX_CG_CYLINDER:
				return createNXcg_cylinder();
			case NX_DEFLECTOR:
				return createNXdeflector();
			case NX_FILTER:
				return createNXfilter();
			case NX_GEOMETRY:
				return createNXgeometry();
			case NX_GRATING:
				return createNXgrating();
			case NX_MONOCHROMATOR:
				return createNXmonochromator();
			case NX_NOTE:
				return createNXnote();
			case NX_OFF_GEOMETRY:
				return createNXoff_geometry();
			case NX_APM_CHARGE_STATE_ANALYSIS:
				return createNXapm_charge_state_analysis();
			case NX_CG_POINT:
				return createNXcg_point();
			case NX_CG_TRIANGLE:
				return createNXcg_triangle();
			case NX_EM_EBSD:
				return createNXem_ebsd();
			case NX_CG_ELLIPSOID:
				return createNXcg_ellipsoid();
			case NX_EM_EDS:
				return createNXem_eds();
			case NX_CG_FACE_LIST_DATA_STRUCTURE:
				return createNXcg_face_list_data_structure();
			case NX_APM_MEASUREMENT:
				return createNXapm_measurement();
			case NX_CG_POLYGON:
				return createNXcg_polygon();
			case NX_ENTRY:
				return createNXentry();
			case NX_ENVIRONMENT:
				return createNXenvironment();
			case NX_FRESNEL_ZONE_PLATE:
				return createNXfresnel_zone_plate();
			case NX_INSTRUMENT:
				return createNXinstrument();
			case NX_LOG:
				return createNXlog();
			case NX_ORIENTATION:
				return createNXorientation();
			case NX_SAMPLE:
				return createNXsample();
			case NX_SHAPE:
				return createNXshape();
			case NX_SLIT:
				return createNXslit();
			case NX_SUBENTRY:
				return createNXsubentry();
			case NX_TRANSLATION:
				return createNXtranslation();
			case NX_VELOCITY_SELECTOR:
				return createNXvelocity_selector();
			case NX_APM_RANGING:
				return createNXapm_ranging();
			case NX_CG_POLYLINE:
				return createNXcg_polyline();
			case NX_EVENT_DATA:
				return createNXevent_data();
			case NX_GUIDE:
				return createNXguide();
			case NX_MIRROR:
				return createNXmirror();
			case NX_MONITOR:
				return createNXmonitor();
			case NX_BEAM:
				return createNXbeam();
			case NX_BEAM_TRANSFER_MATRIX_TABLE:
				return createNXbeam_transfer_matrix_table();
			case NX_CAPILLARY:
				return createNXcapillary();
			case NX_CG_HALF_EDGE_DATA_STRUCTURE:
				return createNXcg_half_edge_data_structure();
			case NX_CITE:
				return createNXcite();
			case NX_COLLECTION:
				return createNXcollection();
			case NX_CYLINDRICAL_GEOMETRY:
				return createNXcylindrical_geometry();
			case NX_FERMI_CHOPPER:
				return createNXfermi_chopper();
			case NX_POLARIZER:
				return createNXpolarizer();
			case NX_BEAM_STOP:
				return createNXbeam_stop();
			case NX_CG_PRIMITIVE:
				return createNXcg_primitive();
			case NX_DETECTOR:
				return createNXdetector();
			case NX_DETECTOR_MODULE:
				return createNXdetector_module();
			case NX_MODERATOR:
				return createNXmoderator();
			case NX_POSITIONER:
				return createNXpositioner();
			case NX_PROCESS:
				return createNXprocess();
			case NX_REFLECTIONS:
				return createNXreflections();
			case NX_ROOT:
				return createNXroot();
			case NX_APM_EVENT_DATA:
				return createNXapm_event_data();
			case NX_CG_HEXAHEDRON:
				return createNXcg_hexahedron();
			case NX_INSERTION_DEVICE:
				return createNXinsertion_device();
			case NX_PINHOLE:
				return createNXpinhole();
			case NX_APM_RECONSTRUCTION:
				return createNXapm_reconstruction();
			case NX_BENDING_MAGNET:
				return createNXbending_magnet();
			case NX_CG_UNIT_NORMAL:
				return createNXcg_unit_normal();
			case NX_DATA:
				return createNXdata();
			case NX_DISK_CHOPPER:
				return createNXdisk_chopper();
			case NX_EM_EELS:
				return createNXem_eels();
			case NX_PARAMETERS:
				return createNXparameters();
			case NX_PDB:
				return createNXpdb();
			case NX_SAMPLE_COMPONENT:
				return createNXsample_component();
			case NX_SENSOR:
				return createNXsensor();
			case NX_TRANSFORMATIONS:
				return createNXtransformations();
			case NX_USER:
				return createNXuser();
			case NX_APERTURE:
				return createNXaperture();
			case NX_APM_SIMULATION:
				return createNXapm_simulation();
			case NX_CALIBRATION:
				return createNXcalibration();
			case NX_CIRCUIT:
				return createNXcircuit();
			case NX_COLLIMATOR:
				return createNXcollimator();
			case NX_DETECTOR_GROUP:
				return createNXdetector_group();
			case NX_FLIPPER:
				return createNXflipper();
			case NX_ATOM:
				return createNXatom();
			case NX_CG_POLYHEDRON:
				return createNXcg_polyhedron();
			case NX_CHEMICAL_COMPOSITION:
				return createNXchemical_composition();
			case NX_COLLECTIONCOLUMN:
				return createNXcollectioncolumn();
			case NX_COORDINATE_SYSTEM:
				return createNXcoordinate_system();
			case NX_CS_COMPUTER:
				return createNXcs_computer();
			case NX_CS_FILTER_BOOLEAN_MASK:
				return createNXcs_filter_boolean_mask();
			case NX_CS_PROCESSOR:
				return createNXcs_processor();
			case NX_CS_PROFILING_EVENT:
				return createNXcs_profiling_event();
			case NX_CS_STORAGE:
				return createNXcs_storage();
			case NX_EBEAM_COLUMN:
				return createNXebeam_column();
			case NX_ELECTRONANALYZER:
				return createNXelectronanalyzer();
			case NX_EM_EVENT_DATA:
				return createNXem_event_data();
			case NX_EM_INSTRUMENT:
				return createNXem_instrument();
			case NX_EM_INTERACTION_VOLUME:
				return createNXem_interaction_volume();
			case NX_EM_MEASUREMENT:
				return createNXem_measurement();
			case NX_EM_OPTICAL_SYSTEM:
				return createNXem_optical_system();
			case NX_EM_SIMULATION:
				return createNXem_simulation();
			case NX_ENERGYDISPERSION:
				return createNXenergydispersion();
			case NX_FIT_FUNCTION:
				return createNXfit_function();
			case NX_IBEAM_COLUMN:
				return createNXibeam_column();
			case NX_IMAGE:
				return createNXimage();
			case NX_MANIPULATOR:
				return createNXmanipulator();
			case NX_OPTICAL_LENS:
				return createNXoptical_lens();
			case NX_OPTICAL_WINDOW:
				return createNXoptical_window();
			case NX_PEAK:
				return createNXpeak();
			case NX_PROGRAM:
				return createNXprogram();
			case NX_PUMP:
				return createNXpump();
			case NX_ROI_PROCESS:
				return createNXroi_process();
			case NX_ROTATIONS:
				return createNXrotations();
			case NX_SCAN_CONTROLLER:
				return createNXscan_controller();
			case NX_SPECTRUM:
				return createNXspectrum();
			case NX_SPINDISPERSION:
				return createNXspindispersion();
			case NX_UNIT_CELL:
				return createNXunit_cell();
			case NX_WAVEPLATE:
				return createNXwaveplate();
			case NX_REGISTRATION:
				return createNXregistration();
			case NX_RESOLUTION:
				return createNXresolution();
			case NX_CORRECTOR_CS:
				return createNXcorrector_cs();
			case NX_CS_PROFILING:
				return createNXcs_profiling();
			case NX_DETECTOR_CHANNEL:
				return createNXdetector_channel();
			case NX_DISTORTION:
				return createNXdistortion();
			case NX_ELECTROMAGNETIC_LENS:
				return createNXelectromagnetic_lens();
			case NX_EM_IMG:
				return createNXem_img();
			case NX_FABRICATION:
				return createNXfabrication();
			case NX_HISTORY:
				return createNXhistory();
			case NX_PID_CONTROLLER:
				return createNXpid_controller();
			case NX_ELECTRON_DETECTOR:
				return createNXelectron_detector();
			case NX_FIT:
				return createNXfit();
			case NX_PHASE:
				return createNXphase();
			case NX_SUBSTANCE:
				return createNXsubstance();
			case NX_MICROSTRUCTURE_ODF:
				return createNXmicrostructure_odf();
			case NX_SUBSAMPLING_FILTER:
				return createNXsubsampling_filter();
			case NX_APM_PARAPROBE_TOOL_COMMON:
				return createNXapm_paraprobe_tool_common();
			case NX_DELOCALIZATION:
				return createNXdelocalization();
			case NX_MICROSTRUCTURE_PF:
				return createNXmicrostructure_pf();
			case NX_CONTAINER:
				return createNXcontainer();
			case NX_SOLID_GEOMETRY:
				return createNXsolid_geometry();
			case NX_CSG:
				return createNXcsg();
			case NX_ELECTROSTATIC_KICKER:
				return createNXelectrostatic_kicker();
			case NX_MICROSTRUCTURE_IPF:
				return createNXmicrostructure_ipf();
			case NX_MAGNETIC_KICKER:
				return createNXmagnetic_kicker();
			case NX_QUADRUPOLE_MAGNET:
				return createNXquadrupole_magnet();
			case NX_SIMILARITY_GROUPING:
				return createNXsimilarity_grouping();
			case NX_BEAM_SPLITTER:
				return createNXbeam_splitter();
			case NX_MICROSTRUCTURE_FEATURE:
				return createNXmicrostructure_feature();
			case NX_MATCH_FILTER:
				return createNXmatch_filter();
			case NX_REGION:
				return createNXregion();
			case NX_MICROSTRUCTURE:
				return createNXmicrostructure();
			case NX_OPTICAL_FIBER:
				return createNXoptical_fiber();
			case NX_DISPERSION:
				return createNXdispersion();
			case NX_ISOCONTOUR:
				return createNXisocontour();
			case NX_SOLENOID_MAGNET:
				return createNXsolenoid_magnet();
			case NX_SPATIAL_FILTER:
				return createNXspatial_filter();
			case NX_SEPARATOR:
				return createNXseparator();
			case NX_QUADRIC:
				return createNXquadric();
			case NX_DISPERSION_SINGLE_PARAMETER:
				return createNXdispersion_single_parameter();
			case NX_DISPERSION_FUNCTION:
				return createNXdispersion_function();
			case NX_DISPERSION_REPEATED_PARAMETER:
				return createNXdispersion_repeated_parameter();
			case NX_DISPERSION_TABLE:
				return createNXdispersion_table();
			case NX_SPIN_ROTATOR:
				return createNXspin_rotator();
			case NX_APM_PARAPROBE_TOOL_PARAMETERS:
				return createNXapm_paraprobe_tool_parameters();
			case NX_MICROSTRUCTURE_MTEX_CONFIG:
				return createNXmicrostructure_mtex_config();
			case NX_MICROSTRUCTURE_SLIP_SYSTEM:
				return createNXmicrostructure_slip_system();
			case NX_APM_PARAPROBE_TOOL_PROCESS:
				return createNXapm_paraprobe_tool_process();
			case NX_OPTICAL_POLARIZER:
				return createNXoptical_polarizer();
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
	 * Create a new {@link NXcg_grid} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_grid
	 */
	public static NXcg_grid createNXcg_grid(long oid) {
		return new NXcg_gridImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_grid}.
	 *
	 * @return new NXcg_grid
	 */
	public static NXcg_grid createNXcg_grid() {
		return new NXcg_gridImpl();
	}

	/**
	 * Create a new {@link NXcomponent} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcomponent
	 */
	public static NXcomponent createNXcomponent(long oid) {
		return new NXcomponentImpl(oid);
	}

	/**
	 * Create a new {@link NXcomponent}.
	 *
	 * @return new NXcomponent
	 */
	public static NXcomponent createNXcomponent() {
		return new NXcomponentImpl();
	}

	/**
	 * Create a new {@link NXcg_roi} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_roi
	 */
	public static NXcg_roi createNXcg_roi(long oid) {
		return new NXcg_roiImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_roi}.
	 *
	 * @return new NXcg_roi
	 */
	public static NXcg_roi createNXcg_roi() {
		return new NXcg_roiImpl();
	}

	/**
	 * Create a new {@link NXactivity} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXactivity
	 */
	public static NXactivity createNXactivity(long oid) {
		return new NXactivityImpl(oid);
	}

	/**
	 * Create a new {@link NXactivity}.
	 *
	 * @return new NXactivity
	 */
	public static NXactivity createNXactivity() {
		return new NXactivityImpl();
	}

	/**
	 * Create a new {@link NXcg_alpha_complex} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_alpha_complex
	 */
	public static NXcg_alpha_complex createNXcg_alpha_complex(long oid) {
		return new NXcg_alpha_complexImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_alpha_complex}.
	 *
	 * @return new NXcg_alpha_complex
	 */
	public static NXcg_alpha_complex createNXcg_alpha_complex() {
		return new NXcg_alpha_complexImpl();
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
	 * Create a new {@link NXcg_tetrahedron} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_tetrahedron
	 */
	public static NXcg_tetrahedron createNXcg_tetrahedron(long oid) {
		return new NXcg_tetrahedronImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_tetrahedron}.
	 *
	 * @return new NXcg_tetrahedron
	 */
	public static NXcg_tetrahedron createNXcg_tetrahedron() {
		return new NXcg_tetrahedronImpl();
	}

	/**
	 * Create a new {@link NXcs_memory} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_memory
	 */
	public static NXcs_memory createNXcs_memory(long oid) {
		return new NXcs_memoryImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_memory}.
	 *
	 * @return new NXcs_memory
	 */
	public static NXcs_memory createNXcs_memory() {
		return new NXcs_memoryImpl();
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
	 * Create a new {@link NXapm_instrument} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_instrument
	 */
	public static NXapm_instrument createNXapm_instrument(long oid) {
		return new NXapm_instrumentImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_instrument}.
	 *
	 * @return new NXapm_instrument
	 */
	public static NXapm_instrument createNXapm_instrument() {
		return new NXapm_instrumentImpl();
	}

	/**
	 * Create a new {@link NXcg_parallelogram} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_parallelogram
	 */
	public static NXcg_parallelogram createNXcg_parallelogram(long oid) {
		return new NXcg_parallelogramImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_parallelogram}.
	 *
	 * @return new NXcg_parallelogram
	 */
	public static NXcg_parallelogram createNXcg_parallelogram() {
		return new NXcg_parallelogramImpl();
	}

	/**
	 * Create a new {@link NXcs_prng} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_prng
	 */
	public static NXcs_prng createNXcs_prng(long oid) {
		return new NXcs_prngImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_prng}.
	 *
	 * @return new NXcs_prng
	 */
	public static NXcs_prng createNXcs_prng() {
		return new NXcs_prngImpl();
	}

	/**
	 * Create a new {@link NXactuator} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXactuator
	 */
	public static NXactuator createNXactuator(long oid) {
		return new NXactuatorImpl(oid);
	}

	/**
	 * Create a new {@link NXactuator}.
	 *
	 * @return new NXactuator
	 */
	public static NXactuator createNXactuator() {
		return new NXactuatorImpl();
	}

	/**
	 * Create a new {@link NXcg_cylinder} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_cylinder
	 */
	public static NXcg_cylinder createNXcg_cylinder(long oid) {
		return new NXcg_cylinderImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_cylinder}.
	 *
	 * @return new NXcg_cylinder
	 */
	public static NXcg_cylinder createNXcg_cylinder() {
		return new NXcg_cylinderImpl();
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
	 * Create a new {@link NXapm_charge_state_analysis} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_charge_state_analysis
	 */
	public static NXapm_charge_state_analysis createNXapm_charge_state_analysis(long oid) {
		return new NXapm_charge_state_analysisImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_charge_state_analysis}.
	 *
	 * @return new NXapm_charge_state_analysis
	 */
	public static NXapm_charge_state_analysis createNXapm_charge_state_analysis() {
		return new NXapm_charge_state_analysisImpl();
	}

	/**
	 * Create a new {@link NXcg_point} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_point
	 */
	public static NXcg_point createNXcg_point(long oid) {
		return new NXcg_pointImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_point}.
	 *
	 * @return new NXcg_point
	 */
	public static NXcg_point createNXcg_point() {
		return new NXcg_pointImpl();
	}

	/**
	 * Create a new {@link NXcg_triangle} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_triangle
	 */
	public static NXcg_triangle createNXcg_triangle(long oid) {
		return new NXcg_triangleImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_triangle}.
	 *
	 * @return new NXcg_triangle
	 */
	public static NXcg_triangle createNXcg_triangle() {
		return new NXcg_triangleImpl();
	}

	/**
	 * Create a new {@link NXem_ebsd} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_ebsd
	 */
	public static NXem_ebsd createNXem_ebsd(long oid) {
		return new NXem_ebsdImpl(oid);
	}

	/**
	 * Create a new {@link NXem_ebsd}.
	 *
	 * @return new NXem_ebsd
	 */
	public static NXem_ebsd createNXem_ebsd() {
		return new NXem_ebsdImpl();
	}

	/**
	 * Create a new {@link NXcg_ellipsoid} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_ellipsoid
	 */
	public static NXcg_ellipsoid createNXcg_ellipsoid(long oid) {
		return new NXcg_ellipsoidImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_ellipsoid}.
	 *
	 * @return new NXcg_ellipsoid
	 */
	public static NXcg_ellipsoid createNXcg_ellipsoid() {
		return new NXcg_ellipsoidImpl();
	}

	/**
	 * Create a new {@link NXem_eds} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_eds
	 */
	public static NXem_eds createNXem_eds(long oid) {
		return new NXem_edsImpl(oid);
	}

	/**
	 * Create a new {@link NXem_eds}.
	 *
	 * @return new NXem_eds
	 */
	public static NXem_eds createNXem_eds() {
		return new NXem_edsImpl();
	}

	/**
	 * Create a new {@link NXcg_face_list_data_structure} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_face_list_data_structure
	 */
	public static NXcg_face_list_data_structure createNXcg_face_list_data_structure(long oid) {
		return new NXcg_face_list_data_structureImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_face_list_data_structure}.
	 *
	 * @return new NXcg_face_list_data_structure
	 */
	public static NXcg_face_list_data_structure createNXcg_face_list_data_structure() {
		return new NXcg_face_list_data_structureImpl();
	}

	/**
	 * Create a new {@link NXapm_measurement} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_measurement
	 */
	public static NXapm_measurement createNXapm_measurement(long oid) {
		return new NXapm_measurementImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_measurement}.
	 *
	 * @return new NXapm_measurement
	 */
	public static NXapm_measurement createNXapm_measurement() {
		return new NXapm_measurementImpl();
	}

	/**
	 * Create a new {@link NXcg_polygon} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_polygon
	 */
	public static NXcg_polygon createNXcg_polygon(long oid) {
		return new NXcg_polygonImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_polygon}.
	 *
	 * @return new NXcg_polygon
	 */
	public static NXcg_polygon createNXcg_polygon() {
		return new NXcg_polygonImpl();
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
	 * Create a new {@link NXapm_ranging} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_ranging
	 */
	public static NXapm_ranging createNXapm_ranging(long oid) {
		return new NXapm_rangingImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_ranging}.
	 *
	 * @return new NXapm_ranging
	 */
	public static NXapm_ranging createNXapm_ranging() {
		return new NXapm_rangingImpl();
	}

	/**
	 * Create a new {@link NXcg_polyline} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_polyline
	 */
	public static NXcg_polyline createNXcg_polyline(long oid) {
		return new NXcg_polylineImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_polyline}.
	 *
	 * @return new NXcg_polyline
	 */
	public static NXcg_polyline createNXcg_polyline() {
		return new NXcg_polylineImpl();
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
	 * Create a new {@link NXbeam_transfer_matrix_table} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXbeam_transfer_matrix_table
	 */
	public static NXbeam_transfer_matrix_table createNXbeam_transfer_matrix_table(long oid) {
		return new NXbeam_transfer_matrix_tableImpl(oid);
	}

	/**
	 * Create a new {@link NXbeam_transfer_matrix_table}.
	 *
	 * @return new NXbeam_transfer_matrix_table
	 */
	public static NXbeam_transfer_matrix_table createNXbeam_transfer_matrix_table() {
		return new NXbeam_transfer_matrix_tableImpl();
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
	 * Create a new {@link NXcg_half_edge_data_structure} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_half_edge_data_structure
	 */
	public static NXcg_half_edge_data_structure createNXcg_half_edge_data_structure(long oid) {
		return new NXcg_half_edge_data_structureImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_half_edge_data_structure}.
	 *
	 * @return new NXcg_half_edge_data_structure
	 */
	public static NXcg_half_edge_data_structure createNXcg_half_edge_data_structure() {
		return new NXcg_half_edge_data_structureImpl();
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
	 * Create a new {@link NXcg_primitive} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_primitive
	 */
	public static NXcg_primitive createNXcg_primitive(long oid) {
		return new NXcg_primitiveImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_primitive}.
	 *
	 * @return new NXcg_primitive
	 */
	public static NXcg_primitive createNXcg_primitive() {
		return new NXcg_primitiveImpl();
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
	 * Create a new {@link NXapm_event_data} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_event_data
	 */
	public static NXapm_event_data createNXapm_event_data(long oid) {
		return new NXapm_event_dataImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_event_data}.
	 *
	 * @return new NXapm_event_data
	 */
	public static NXapm_event_data createNXapm_event_data() {
		return new NXapm_event_dataImpl();
	}

	/**
	 * Create a new {@link NXcg_hexahedron} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_hexahedron
	 */
	public static NXcg_hexahedron createNXcg_hexahedron(long oid) {
		return new NXcg_hexahedronImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_hexahedron}.
	 *
	 * @return new NXcg_hexahedron
	 */
	public static NXcg_hexahedron createNXcg_hexahedron() {
		return new NXcg_hexahedronImpl();
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
	 * Create a new {@link NXapm_reconstruction} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_reconstruction
	 */
	public static NXapm_reconstruction createNXapm_reconstruction(long oid) {
		return new NXapm_reconstructionImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_reconstruction}.
	 *
	 * @return new NXapm_reconstruction
	 */
	public static NXapm_reconstruction createNXapm_reconstruction() {
		return new NXapm_reconstructionImpl();
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
	 * Create a new {@link NXcg_unit_normal} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_unit_normal
	 */
	public static NXcg_unit_normal createNXcg_unit_normal(long oid) {
		return new NXcg_unit_normalImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_unit_normal}.
	 *
	 * @return new NXcg_unit_normal
	 */
	public static NXcg_unit_normal createNXcg_unit_normal() {
		return new NXcg_unit_normalImpl();
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
	 * Create a new {@link NXem_eels} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_eels
	 */
	public static NXem_eels createNXem_eels(long oid) {
		return new NXem_eelsImpl(oid);
	}

	/**
	 * Create a new {@link NXem_eels}.
	 *
	 * @return new NXem_eels
	 */
	public static NXem_eels createNXem_eels() {
		return new NXem_eelsImpl();
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
	 * Create a new {@link NXapm_simulation} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_simulation
	 */
	public static NXapm_simulation createNXapm_simulation(long oid) {
		return new NXapm_simulationImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_simulation}.
	 *
	 * @return new NXapm_simulation
	 */
	public static NXapm_simulation createNXapm_simulation() {
		return new NXapm_simulationImpl();
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
	 * Create a new {@link NXcircuit} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcircuit
	 */
	public static NXcircuit createNXcircuit(long oid) {
		return new NXcircuitImpl(oid);
	}

	/**
	 * Create a new {@link NXcircuit}.
	 *
	 * @return new NXcircuit
	 */
	public static NXcircuit createNXcircuit() {
		return new NXcircuitImpl();
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
	 * Create a new {@link NXatom} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXatom
	 */
	public static NXatom createNXatom(long oid) {
		return new NXatomImpl(oid);
	}

	/**
	 * Create a new {@link NXatom}.
	 *
	 * @return new NXatom
	 */
	public static NXatom createNXatom() {
		return new NXatomImpl();
	}

	/**
	 * Create a new {@link NXcg_polyhedron} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcg_polyhedron
	 */
	public static NXcg_polyhedron createNXcg_polyhedron(long oid) {
		return new NXcg_polyhedronImpl(oid);
	}

	/**
	 * Create a new {@link NXcg_polyhedron}.
	 *
	 * @return new NXcg_polyhedron
	 */
	public static NXcg_polyhedron createNXcg_polyhedron() {
		return new NXcg_polyhedronImpl();
	}

	/**
	 * Create a new {@link NXchemical_composition} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXchemical_composition
	 */
	public static NXchemical_composition createNXchemical_composition(long oid) {
		return new NXchemical_compositionImpl(oid);
	}

	/**
	 * Create a new {@link NXchemical_composition}.
	 *
	 * @return new NXchemical_composition
	 */
	public static NXchemical_composition createNXchemical_composition() {
		return new NXchemical_compositionImpl();
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
	 * Create a new {@link NXcoordinate_system} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcoordinate_system
	 */
	public static NXcoordinate_system createNXcoordinate_system(long oid) {
		return new NXcoordinate_systemImpl(oid);
	}

	/**
	 * Create a new {@link NXcoordinate_system}.
	 *
	 * @return new NXcoordinate_system
	 */
	public static NXcoordinate_system createNXcoordinate_system() {
		return new NXcoordinate_systemImpl();
	}

	/**
	 * Create a new {@link NXcs_computer} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_computer
	 */
	public static NXcs_computer createNXcs_computer(long oid) {
		return new NXcs_computerImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_computer}.
	 *
	 * @return new NXcs_computer
	 */
	public static NXcs_computer createNXcs_computer() {
		return new NXcs_computerImpl();
	}

	/**
	 * Create a new {@link NXcs_filter_boolean_mask} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_filter_boolean_mask
	 */
	public static NXcs_filter_boolean_mask createNXcs_filter_boolean_mask(long oid) {
		return new NXcs_filter_boolean_maskImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_filter_boolean_mask}.
	 *
	 * @return new NXcs_filter_boolean_mask
	 */
	public static NXcs_filter_boolean_mask createNXcs_filter_boolean_mask() {
		return new NXcs_filter_boolean_maskImpl();
	}

	/**
	 * Create a new {@link NXcs_processor} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_processor
	 */
	public static NXcs_processor createNXcs_processor(long oid) {
		return new NXcs_processorImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_processor}.
	 *
	 * @return new NXcs_processor
	 */
	public static NXcs_processor createNXcs_processor() {
		return new NXcs_processorImpl();
	}

	/**
	 * Create a new {@link NXcs_profiling_event} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_profiling_event
	 */
	public static NXcs_profiling_event createNXcs_profiling_event(long oid) {
		return new NXcs_profiling_eventImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_profiling_event}.
	 *
	 * @return new NXcs_profiling_event
	 */
	public static NXcs_profiling_event createNXcs_profiling_event() {
		return new NXcs_profiling_eventImpl();
	}

	/**
	 * Create a new {@link NXcs_storage} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_storage
	 */
	public static NXcs_storage createNXcs_storage(long oid) {
		return new NXcs_storageImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_storage}.
	 *
	 * @return new NXcs_storage
	 */
	public static NXcs_storage createNXcs_storage() {
		return new NXcs_storageImpl();
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
	 * Create a new {@link NXelectronanalyzer} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXelectronanalyzer
	 */
	public static NXelectronanalyzer createNXelectronanalyzer(long oid) {
		return new NXelectronanalyzerImpl(oid);
	}

	/**
	 * Create a new {@link NXelectronanalyzer}.
	 *
	 * @return new NXelectronanalyzer
	 */
	public static NXelectronanalyzer createNXelectronanalyzer() {
		return new NXelectronanalyzerImpl();
	}

	/**
	 * Create a new {@link NXem_event_data} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_event_data
	 */
	public static NXem_event_data createNXem_event_data(long oid) {
		return new NXem_event_dataImpl(oid);
	}

	/**
	 * Create a new {@link NXem_event_data}.
	 *
	 * @return new NXem_event_data
	 */
	public static NXem_event_data createNXem_event_data() {
		return new NXem_event_dataImpl();
	}

	/**
	 * Create a new {@link NXem_instrument} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_instrument
	 */
	public static NXem_instrument createNXem_instrument(long oid) {
		return new NXem_instrumentImpl(oid);
	}

	/**
	 * Create a new {@link NXem_instrument}.
	 *
	 * @return new NXem_instrument
	 */
	public static NXem_instrument createNXem_instrument() {
		return new NXem_instrumentImpl();
	}

	/**
	 * Create a new {@link NXem_interaction_volume} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_interaction_volume
	 */
	public static NXem_interaction_volume createNXem_interaction_volume(long oid) {
		return new NXem_interaction_volumeImpl(oid);
	}

	/**
	 * Create a new {@link NXem_interaction_volume}.
	 *
	 * @return new NXem_interaction_volume
	 */
	public static NXem_interaction_volume createNXem_interaction_volume() {
		return new NXem_interaction_volumeImpl();
	}

	/**
	 * Create a new {@link NXem_measurement} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_measurement
	 */
	public static NXem_measurement createNXem_measurement(long oid) {
		return new NXem_measurementImpl(oid);
	}

	/**
	 * Create a new {@link NXem_measurement}.
	 *
	 * @return new NXem_measurement
	 */
	public static NXem_measurement createNXem_measurement() {
		return new NXem_measurementImpl();
	}

	/**
	 * Create a new {@link NXem_optical_system} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_optical_system
	 */
	public static NXem_optical_system createNXem_optical_system(long oid) {
		return new NXem_optical_systemImpl(oid);
	}

	/**
	 * Create a new {@link NXem_optical_system}.
	 *
	 * @return new NXem_optical_system
	 */
	public static NXem_optical_system createNXem_optical_system() {
		return new NXem_optical_systemImpl();
	}

	/**
	 * Create a new {@link NXem_simulation} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_simulation
	 */
	public static NXem_simulation createNXem_simulation(long oid) {
		return new NXem_simulationImpl(oid);
	}

	/**
	 * Create a new {@link NXem_simulation}.
	 *
	 * @return new NXem_simulation
	 */
	public static NXem_simulation createNXem_simulation() {
		return new NXem_simulationImpl();
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
	 * Create a new {@link NXfit_function} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXfit_function
	 */
	public static NXfit_function createNXfit_function(long oid) {
		return new NXfit_functionImpl(oid);
	}

	/**
	 * Create a new {@link NXfit_function}.
	 *
	 * @return new NXfit_function
	 */
	public static NXfit_function createNXfit_function() {
		return new NXfit_functionImpl();
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
	 * Create a new {@link NXimage} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXimage
	 */
	public static NXimage createNXimage(long oid) {
		return new NXimageImpl(oid);
	}

	/**
	 * Create a new {@link NXimage}.
	 *
	 * @return new NXimage
	 */
	public static NXimage createNXimage() {
		return new NXimageImpl();
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
	 * Create a new {@link NXoptical_lens} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXoptical_lens
	 */
	public static NXoptical_lens createNXoptical_lens(long oid) {
		return new NXoptical_lensImpl(oid);
	}

	/**
	 * Create a new {@link NXoptical_lens}.
	 *
	 * @return new NXoptical_lens
	 */
	public static NXoptical_lens createNXoptical_lens() {
		return new NXoptical_lensImpl();
	}

	/**
	 * Create a new {@link NXoptical_window} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXoptical_window
	 */
	public static NXoptical_window createNXoptical_window(long oid) {
		return new NXoptical_windowImpl(oid);
	}

	/**
	 * Create a new {@link NXoptical_window}.
	 *
	 * @return new NXoptical_window
	 */
	public static NXoptical_window createNXoptical_window() {
		return new NXoptical_windowImpl();
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
	 * Create a new {@link NXprogram} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXprogram
	 */
	public static NXprogram createNXprogram(long oid) {
		return new NXprogramImpl(oid);
	}

	/**
	 * Create a new {@link NXprogram}.
	 *
	 * @return new NXprogram
	 */
	public static NXprogram createNXprogram() {
		return new NXprogramImpl();
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
	 * Create a new {@link NXroi_process} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXroi_process
	 */
	public static NXroi_process createNXroi_process(long oid) {
		return new NXroi_processImpl(oid);
	}

	/**
	 * Create a new {@link NXroi_process}.
	 *
	 * @return new NXroi_process
	 */
	public static NXroi_process createNXroi_process() {
		return new NXroi_processImpl();
	}

	/**
	 * Create a new {@link NXrotations} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXrotations
	 */
	public static NXrotations createNXrotations(long oid) {
		return new NXrotationsImpl(oid);
	}

	/**
	 * Create a new {@link NXrotations}.
	 *
	 * @return new NXrotations
	 */
	public static NXrotations createNXrotations() {
		return new NXrotationsImpl();
	}

	/**
	 * Create a new {@link NXscan_controller} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXscan_controller
	 */
	public static NXscan_controller createNXscan_controller(long oid) {
		return new NXscan_controllerImpl(oid);
	}

	/**
	 * Create a new {@link NXscan_controller}.
	 *
	 * @return new NXscan_controller
	 */
	public static NXscan_controller createNXscan_controller() {
		return new NXscan_controllerImpl();
	}

	/**
	 * Create a new {@link NXspectrum} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspectrum
	 */
	public static NXspectrum createNXspectrum(long oid) {
		return new NXspectrumImpl(oid);
	}

	/**
	 * Create a new {@link NXspectrum}.
	 *
	 * @return new NXspectrum
	 */
	public static NXspectrum createNXspectrum() {
		return new NXspectrumImpl();
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
	 * Create a new {@link NXunit_cell} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXunit_cell
	 */
	public static NXunit_cell createNXunit_cell(long oid) {
		return new NXunit_cellImpl(oid);
	}

	/**
	 * Create a new {@link NXunit_cell}.
	 *
	 * @return new NXunit_cell
	 */
	public static NXunit_cell createNXunit_cell() {
		return new NXunit_cellImpl();
	}

	/**
	 * Create a new {@link NXwaveplate} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXwaveplate
	 */
	public static NXwaveplate createNXwaveplate(long oid) {
		return new NXwaveplateImpl(oid);
	}

	/**
	 * Create a new {@link NXwaveplate}.
	 *
	 * @return new NXwaveplate
	 */
	public static NXwaveplate createNXwaveplate() {
		return new NXwaveplateImpl();
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
	 * Create a new {@link NXresolution} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXresolution
	 */
	public static NXresolution createNXresolution(long oid) {
		return new NXresolutionImpl(oid);
	}

	/**
	 * Create a new {@link NXresolution}.
	 *
	 * @return new NXresolution
	 */
	public static NXresolution createNXresolution() {
		return new NXresolutionImpl();
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
	 * Create a new {@link NXcs_profiling} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXcs_profiling
	 */
	public static NXcs_profiling createNXcs_profiling(long oid) {
		return new NXcs_profilingImpl(oid);
	}

	/**
	 * Create a new {@link NXcs_profiling}.
	 *
	 * @return new NXcs_profiling
	 */
	public static NXcs_profiling createNXcs_profiling() {
		return new NXcs_profilingImpl();
	}

	/**
	 * Create a new {@link NXdetector_channel} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdetector_channel
	 */
	public static NXdetector_channel createNXdetector_channel(long oid) {
		return new NXdetector_channelImpl(oid);
	}

	/**
	 * Create a new {@link NXdetector_channel}.
	 *
	 * @return new NXdetector_channel
	 */
	public static NXdetector_channel createNXdetector_channel() {
		return new NXdetector_channelImpl();
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
	 * Create a new {@link NXelectromagnetic_lens} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXelectromagnetic_lens
	 */
	public static NXelectromagnetic_lens createNXelectromagnetic_lens(long oid) {
		return new NXelectromagnetic_lensImpl(oid);
	}

	/**
	 * Create a new {@link NXelectromagnetic_lens}.
	 *
	 * @return new NXelectromagnetic_lens
	 */
	public static NXelectromagnetic_lens createNXelectromagnetic_lens() {
		return new NXelectromagnetic_lensImpl();
	}

	/**
	 * Create a new {@link NXem_img} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXem_img
	 */
	public static NXem_img createNXem_img(long oid) {
		return new NXem_imgImpl(oid);
	}

	/**
	 * Create a new {@link NXem_img}.
	 *
	 * @return new NXem_img
	 */
	public static NXem_img createNXem_img() {
		return new NXem_imgImpl();
	}

	/**
	 * Create a new {@link NXfabrication} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXfabrication
	 */
	public static NXfabrication createNXfabrication(long oid) {
		return new NXfabricationImpl(oid);
	}

	/**
	 * Create a new {@link NXfabrication}.
	 *
	 * @return new NXfabrication
	 */
	public static NXfabrication createNXfabrication() {
		return new NXfabricationImpl();
	}

	/**
	 * Create a new {@link NXhistory} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXhistory
	 */
	public static NXhistory createNXhistory(long oid) {
		return new NXhistoryImpl(oid);
	}

	/**
	 * Create a new {@link NXhistory}.
	 *
	 * @return new NXhistory
	 */
	public static NXhistory createNXhistory() {
		return new NXhistoryImpl();
	}

	/**
	 * Create a new {@link NXpid_controller} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXpid_controller
	 */
	public static NXpid_controller createNXpid_controller(long oid) {
		return new NXpid_controllerImpl(oid);
	}

	/**
	 * Create a new {@link NXpid_controller}.
	 *
	 * @return new NXpid_controller
	 */
	public static NXpid_controller createNXpid_controller() {
		return new NXpid_controllerImpl();
	}

	/**
	 * Create a new {@link NXelectron_detector} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXelectron_detector
	 */
	public static NXelectron_detector createNXelectron_detector(long oid) {
		return new NXelectron_detectorImpl(oid);
	}

	/**
	 * Create a new {@link NXelectron_detector}.
	 *
	 * @return new NXelectron_detector
	 */
	public static NXelectron_detector createNXelectron_detector() {
		return new NXelectron_detectorImpl();
	}

	/**
	 * Create a new {@link NXfit} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXfit
	 */
	public static NXfit createNXfit(long oid) {
		return new NXfitImpl(oid);
	}

	/**
	 * Create a new {@link NXfit}.
	 *
	 * @return new NXfit
	 */
	public static NXfit createNXfit() {
		return new NXfitImpl();
	}

	/**
	 * Create a new {@link NXphase} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXphase
	 */
	public static NXphase createNXphase(long oid) {
		return new NXphaseImpl(oid);
	}

	/**
	 * Create a new {@link NXphase}.
	 *
	 * @return new NXphase
	 */
	public static NXphase createNXphase() {
		return new NXphaseImpl();
	}

	/**
	 * Create a new {@link NXsubstance} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsubstance
	 */
	public static NXsubstance createNXsubstance(long oid) {
		return new NXsubstanceImpl(oid);
	}

	/**
	 * Create a new {@link NXsubstance}.
	 *
	 * @return new NXsubstance
	 */
	public static NXsubstance createNXsubstance() {
		return new NXsubstanceImpl();
	}

	/**
	 * Create a new {@link NXmicrostructure_odf} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure_odf
	 */
	public static NXmicrostructure_odf createNXmicrostructure_odf(long oid) {
		return new NXmicrostructure_odfImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure_odf}.
	 *
	 * @return new NXmicrostructure_odf
	 */
	public static NXmicrostructure_odf createNXmicrostructure_odf() {
		return new NXmicrostructure_odfImpl();
	}

	/**
	 * Create a new {@link NXsubsampling_filter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsubsampling_filter
	 */
	public static NXsubsampling_filter createNXsubsampling_filter(long oid) {
		return new NXsubsampling_filterImpl(oid);
	}

	/**
	 * Create a new {@link NXsubsampling_filter}.
	 *
	 * @return new NXsubsampling_filter
	 */
	public static NXsubsampling_filter createNXsubsampling_filter() {
		return new NXsubsampling_filterImpl();
	}

	/**
	 * Create a new {@link NXapm_paraprobe_tool_common} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_paraprobe_tool_common
	 */
	public static NXapm_paraprobe_tool_common createNXapm_paraprobe_tool_common(long oid) {
		return new NXapm_paraprobe_tool_commonImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_paraprobe_tool_common}.
	 *
	 * @return new NXapm_paraprobe_tool_common
	 */
	public static NXapm_paraprobe_tool_common createNXapm_paraprobe_tool_common() {
		return new NXapm_paraprobe_tool_commonImpl();
	}

	/**
	 * Create a new {@link NXdelocalization} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdelocalization
	 */
	public static NXdelocalization createNXdelocalization(long oid) {
		return new NXdelocalizationImpl(oid);
	}

	/**
	 * Create a new {@link NXdelocalization}.
	 *
	 * @return new NXdelocalization
	 */
	public static NXdelocalization createNXdelocalization() {
		return new NXdelocalizationImpl();
	}

	/**
	 * Create a new {@link NXmicrostructure_pf} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure_pf
	 */
	public static NXmicrostructure_pf createNXmicrostructure_pf(long oid) {
		return new NXmicrostructure_pfImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure_pf}.
	 *
	 * @return new NXmicrostructure_pf
	 */
	public static NXmicrostructure_pf createNXmicrostructure_pf() {
		return new NXmicrostructure_pfImpl();
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
	 * Create a new {@link NXmicrostructure_ipf} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure_ipf
	 */
	public static NXmicrostructure_ipf createNXmicrostructure_ipf(long oid) {
		return new NXmicrostructure_ipfImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure_ipf}.
	 *
	 * @return new NXmicrostructure_ipf
	 */
	public static NXmicrostructure_ipf createNXmicrostructure_ipf() {
		return new NXmicrostructure_ipfImpl();
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
	 * Create a new {@link NXsimilarity_grouping} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXsimilarity_grouping
	 */
	public static NXsimilarity_grouping createNXsimilarity_grouping(long oid) {
		return new NXsimilarity_groupingImpl(oid);
	}

	/**
	 * Create a new {@link NXsimilarity_grouping}.
	 *
	 * @return new NXsimilarity_grouping
	 */
	public static NXsimilarity_grouping createNXsimilarity_grouping() {
		return new NXsimilarity_groupingImpl();
	}

	/**
	 * Create a new {@link NXbeam_splitter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXbeam_splitter
	 */
	public static NXbeam_splitter createNXbeam_splitter(long oid) {
		return new NXbeam_splitterImpl(oid);
	}

	/**
	 * Create a new {@link NXbeam_splitter}.
	 *
	 * @return new NXbeam_splitter
	 */
	public static NXbeam_splitter createNXbeam_splitter() {
		return new NXbeam_splitterImpl();
	}

	/**
	 * Create a new {@link NXmicrostructure_feature} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure_feature
	 */
	public static NXmicrostructure_feature createNXmicrostructure_feature(long oid) {
		return new NXmicrostructure_featureImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure_feature}.
	 *
	 * @return new NXmicrostructure_feature
	 */
	public static NXmicrostructure_feature createNXmicrostructure_feature() {
		return new NXmicrostructure_featureImpl();
	}

	/**
	 * Create a new {@link NXmatch_filter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmatch_filter
	 */
	public static NXmatch_filter createNXmatch_filter(long oid) {
		return new NXmatch_filterImpl(oid);
	}

	/**
	 * Create a new {@link NXmatch_filter}.
	 *
	 * @return new NXmatch_filter
	 */
	public static NXmatch_filter createNXmatch_filter() {
		return new NXmatch_filterImpl();
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
	 * Create a new {@link NXmicrostructure} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure
	 */
	public static NXmicrostructure createNXmicrostructure(long oid) {
		return new NXmicrostructureImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure}.
	 *
	 * @return new NXmicrostructure
	 */
	public static NXmicrostructure createNXmicrostructure() {
		return new NXmicrostructureImpl();
	}

	/**
	 * Create a new {@link NXoptical_fiber} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXoptical_fiber
	 */
	public static NXoptical_fiber createNXoptical_fiber(long oid) {
		return new NXoptical_fiberImpl(oid);
	}

	/**
	 * Create a new {@link NXoptical_fiber}.
	 *
	 * @return new NXoptical_fiber
	 */
	public static NXoptical_fiber createNXoptical_fiber() {
		return new NXoptical_fiberImpl();
	}

	/**
	 * Create a new {@link NXdispersion} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdispersion
	 */
	public static NXdispersion createNXdispersion(long oid) {
		return new NXdispersionImpl(oid);
	}

	/**
	 * Create a new {@link NXdispersion}.
	 *
	 * @return new NXdispersion
	 */
	public static NXdispersion createNXdispersion() {
		return new NXdispersionImpl();
	}

	/**
	 * Create a new {@link NXisocontour} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXisocontour
	 */
	public static NXisocontour createNXisocontour(long oid) {
		return new NXisocontourImpl(oid);
	}

	/**
	 * Create a new {@link NXisocontour}.
	 *
	 * @return new NXisocontour
	 */
	public static NXisocontour createNXisocontour() {
		return new NXisocontourImpl();
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
	 * Create a new {@link NXspatial_filter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXspatial_filter
	 */
	public static NXspatial_filter createNXspatial_filter(long oid) {
		return new NXspatial_filterImpl(oid);
	}

	/**
	 * Create a new {@link NXspatial_filter}.
	 *
	 * @return new NXspatial_filter
	 */
	public static NXspatial_filter createNXspatial_filter() {
		return new NXspatial_filterImpl();
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
	 * Create a new {@link NXdispersion_single_parameter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdispersion_single_parameter
	 */
	public static NXdispersion_single_parameter createNXdispersion_single_parameter(long oid) {
		return new NXdispersion_single_parameterImpl(oid);
	}

	/**
	 * Create a new {@link NXdispersion_single_parameter}.
	 *
	 * @return new NXdispersion_single_parameter
	 */
	public static NXdispersion_single_parameter createNXdispersion_single_parameter() {
		return new NXdispersion_single_parameterImpl();
	}

	/**
	 * Create a new {@link NXdispersion_function} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdispersion_function
	 */
	public static NXdispersion_function createNXdispersion_function(long oid) {
		return new NXdispersion_functionImpl(oid);
	}

	/**
	 * Create a new {@link NXdispersion_function}.
	 *
	 * @return new NXdispersion_function
	 */
	public static NXdispersion_function createNXdispersion_function() {
		return new NXdispersion_functionImpl();
	}

	/**
	 * Create a new {@link NXdispersion_repeated_parameter} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdispersion_repeated_parameter
	 */
	public static NXdispersion_repeated_parameter createNXdispersion_repeated_parameter(long oid) {
		return new NXdispersion_repeated_parameterImpl(oid);
	}

	/**
	 * Create a new {@link NXdispersion_repeated_parameter}.
	 *
	 * @return new NXdispersion_repeated_parameter
	 */
	public static NXdispersion_repeated_parameter createNXdispersion_repeated_parameter() {
		return new NXdispersion_repeated_parameterImpl();
	}

	/**
	 * Create a new {@link NXdispersion_table} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXdispersion_table
	 */
	public static NXdispersion_table createNXdispersion_table(long oid) {
		return new NXdispersion_tableImpl(oid);
	}

	/**
	 * Create a new {@link NXdispersion_table}.
	 *
	 * @return new NXdispersion_table
	 */
	public static NXdispersion_table createNXdispersion_table() {
		return new NXdispersion_tableImpl();
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
	 * Create a new {@link NXapm_paraprobe_tool_parameters} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_paraprobe_tool_parameters
	 */
	public static NXapm_paraprobe_tool_parameters createNXapm_paraprobe_tool_parameters(long oid) {
		return new NXapm_paraprobe_tool_parametersImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_paraprobe_tool_parameters}.
	 *
	 * @return new NXapm_paraprobe_tool_parameters
	 */
	public static NXapm_paraprobe_tool_parameters createNXapm_paraprobe_tool_parameters() {
		return new NXapm_paraprobe_tool_parametersImpl();
	}

	/**
	 * Create a new {@link NXmicrostructure_mtex_config} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure_mtex_config
	 */
	public static NXmicrostructure_mtex_config createNXmicrostructure_mtex_config(long oid) {
		return new NXmicrostructure_mtex_configImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure_mtex_config}.
	 *
	 * @return new NXmicrostructure_mtex_config
	 */
	public static NXmicrostructure_mtex_config createNXmicrostructure_mtex_config() {
		return new NXmicrostructure_mtex_configImpl();
	}

	/**
	 * Create a new {@link NXmicrostructure_slip_system} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXmicrostructure_slip_system
	 */
	public static NXmicrostructure_slip_system createNXmicrostructure_slip_system(long oid) {
		return new NXmicrostructure_slip_systemImpl(oid);
	}

	/**
	 * Create a new {@link NXmicrostructure_slip_system}.
	 *
	 * @return new NXmicrostructure_slip_system
	 */
	public static NXmicrostructure_slip_system createNXmicrostructure_slip_system() {
		return new NXmicrostructure_slip_systemImpl();
	}

	/**
	 * Create a new {@link NXapm_paraprobe_tool_process} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXapm_paraprobe_tool_process
	 */
	public static NXapm_paraprobe_tool_process createNXapm_paraprobe_tool_process(long oid) {
		return new NXapm_paraprobe_tool_processImpl(oid);
	}

	/**
	 * Create a new {@link NXapm_paraprobe_tool_process}.
	 *
	 * @return new NXapm_paraprobe_tool_process
	 */
	public static NXapm_paraprobe_tool_process createNXapm_paraprobe_tool_process() {
		return new NXapm_paraprobe_tool_processImpl();
	}

	/**
	 * Create a new {@link NXoptical_polarizer} with the given oid.
	 *
	 * @param oid unique object oid.
	 * @return new NXoptical_polarizer
	 */
	public static NXoptical_polarizer createNXoptical_polarizer(long oid) {
		return new NXoptical_polarizerImpl(oid);
	}

	/**
	 * Create a new {@link NXoptical_polarizer}.
	 *
	 * @return new NXoptical_polarizer
	 */
	public static NXoptical_polarizer createNXoptical_polarizer() {
		return new NXoptical_polarizerImpl();
	}

}
