/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.nexuswriter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.dawnsci.analysis.tree.impl.SymbolicNodeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinsertion_device;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXmirror;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXslit;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXtransformations;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.dawnsci.nexus.builder.impl.DefaultNexusFileBuilder;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.io.LoaderServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

class PalladateNexusWriter {

	final static String sampleFileName = "palladateComplete.nxs",
			capillaryFileName = "palladateCapillary.nxs",
			beamFileName = "palladteI151.nxs",
			calibrationFileName = "calibration_fixE_CeO2_100s.hdf5",
			darkFileName = "pe_dark.hdf5", 
			maskFileName = "16#9839_0pF5_PxlMask_rot270_mask.nxs", 
			sampleImage1 = "sampleImg00001.jpg",
			sampleImage2 = "sampleImg00002.jpg",
			inputSampleFileName = "K2PdCl6(Alfa)_framebyframe_2h_lastslice.hdf5",
			inputCapillaryFileName = "empty_cap_180x20s_lastslice.hdf5", 
			inputBeamFileName = "emptyXPDF_180x20s_lastslice.hdf5" 
			;
	
	int detectorSize = 4096; 
	
	static String outputDir, inputDir;

	static ZoneId londonTime = ZoneId.of("Europe/London");
	
	static String dataFileName, containerFileName;

	public static void main(String[] args) {

		// Set the Nexus File Factory service, since we are not inside Dawn
		new ServiceHolder().setNexusFileFactory(new NexusFileFactoryHDF5());
		
		// Set the general file loader service
		LocalServiceManager misterManager = new LocalServiceManager();
		misterManager.setLoaderService(new LoaderServiceImpl());
		
		outputDir = System.getProperty("user.home") + "/nexus/";
		inputDir = "/dls/science/groups/das/ExampleData/i15-1/NeXus/files for writer";
//		fileDir = "/dls/science/groups/das/ExampleData/i15-1/NeXus" + "/";
		
		makeSampleNexus();
		makeCapillaryNexus();
		makeI151Nexus();
		return;
	}
	
	private static void makeSampleNexus() {
		String filePath =  outputDir + sampleFileName;
		
		NexusFileBuilder builder = new DefaultNexusFileBuilder(filePath);
		NXroot nxroot = builder.getNXroot();
//		nxroot.setAttribute(null, "file_name", "I really shouldn't be here");

		dataFileName = inputDir + inputSampleFileName;
		containerFileName = capillaryFileName;
		
		// Use the test sample data to make the sample.
		XPDFSampleParameters pallySample = SampleTestData.createTestSample("palladate");
		
		NXsample nxample = pallySample.getNXsample(builder);
		// the NeXus file builder, newly built, will contain one entry, with one sample. Get that one entry.
		NXentry nxentry = nxroot.getChildren(NXentry.class).values().toArray(new NXentry[1])[0];
		
		fillEntry(nxentry, filePath);

		// Instrument entry for I15-1
		NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		fillInstrument(instrument);
		nxentry.addGroupNode("instrument", instrument);
		
		// Remaining data for the sample
		addBeam(nxample);
		addTheoreticalPDF(nxample);
		addSampleContainer(nxample, builder);
		addDark(nxample);
		addCalibration(nxample);
		addMask(nxample);
		addSampleImages(nxample);
		// Data for the data
		NXdata ceriaData = NexusNodeFactory.createNXdata();
		fillData(ceriaData);
		nxentry.addGroupNode("data", ceriaData);
		
		// PI, user, local contact
		fillUsers(nxentry);
		
		try {
			builder.createFile(false).close();
			System.err.println("Success writing NeXus file to " + filePath);
		} catch (NexusException nE) {
			System.err.println("Error writing NeXus file to " + filePath + ": " + nE.toString());
		}
	}
	
	private static void addSampleImages(NXsample nxample) {
		NXcollection sampleImages = NexusNodeFactory.createNXcollection();
		
		NXnote sampleImage = NexusNodeFactory.createNXnote();
		sampleImage.setDateScalar(Date.from(LocalDateTime.of(2016,4,11, 14,54).atZone(londonTime).toInstant()));
		sampleImage.setTypeScalar("image/jpeg");
		sampleImage.setFile_nameScalar(sampleImage1);
		sampleImage.setDescriptionScalar("View of the sample from above");
		sampleImages.addGroupNode("camera1", sampleImage);
		
		sampleImage = NexusNodeFactory.createNXnote();
		sampleImage.setDateScalar(Date.from(LocalDateTime.of(2016,4,11, 15,06).atZone(londonTime).toInstant()));
		sampleImage.setTypeScalar("image/jpeg");
		sampleImage.setFile_nameScalar(sampleImage2);
		sampleImage.setDescriptionScalar("View of the sample from the direction of Mecca");
		
		sampleImages.addGroupNode("camera2", sampleImage);
		
		nxample.addGroupNode("sample_images", sampleImages);
 
	}

	private static void addMask(NXsample nxample) {
		String maskFilePath = inputDir + maskFileName;
		NXnote maskNote = NexusNodeFactory.createNXnote();
		maskNote.setAuthorScalar("Ned Nederlander");
		maskNote.setDateScalar(Date.from(LocalDateTime.of(2015, 7, 17, 15, 20).atZone(londonTime).toInstant()));
		maskNote.setTypeScalar("image/hdf5");
		maskNote.setFile_nameScalar(maskFilePath);
		
		nxample.addGroupNode("mask", maskNote);
	}

	private static void addCalibration(NXsample nxample) {
		String calFilePath = inputDir + calibrationFileName;
		NXnote calNote = NexusNodeFactory.createNXnote();
		calNote.setAuthorScalar("Dusty Bottoms");
		calNote.setDateScalar(Date.from(LocalDateTime.of(2015, 10, 1, 12, 36).atZone(londonTime).toInstant()));
		calNote.setTypeScalar("image/hdf5");
		calNote.setFile_nameScalar(calFilePath);
		calNote.setField("calibrant", "Ceria SRM"); // calibrating ceria with ceria!
		
		nxample.addGroupNode("calibration", calNote);
	}

	private static void addDark(NXsample nxample) {
//		NexusNodeFactory noder = builder.getNodeFactory();
		String darkFilePath = inputDir + darkFileName;
		NXnote darkNote = NexusNodeFactory.createNXnote();
		darkNote.setAuthorScalar("Lucky Day");
		darkNote.setDateScalar(Date.from(LocalDateTime.of(2016, 04, 8, 9, 16).atZone(londonTime).toInstant()));
		darkNote.setTypeScalar("image/hdf5");
		darkNote.setFile_nameScalar(darkFilePath); // Don't need the dark for my processing
		// link to data
		
		nxample.addGroupNode("dark", darkNote);
	}

	// Add a 1 mm quartz capillary to the sample
	private static void addSampleContainer(NXsample nxample, NexusFileBuilder builder) {
		
		String containerFilePath = inputDir + containerFileName;
		GroupNode container = NexusNodeFactory.createNXcontainer();
		// inside node name
		// inside file name
		DataNode inside = new DataNodeImpl(NexusNodeFactory.getNextOid());
		inside.setDataset(DatasetFactory.createFromObject(new String[]{containerFilePath}, new int[]{1}));
		container.addDataNode("inside_of_file_name", inside);
		// container wavelength and distance
		
		
		nxample.addGroupNode("container", container);
	}

	// Add a theoretical PDF to the sample
	private static void addTheoreticalPDF(NXsample nxample) {
		// TODO Add real data
		NXdata pdfData = NexusNodeFactory.createNXdata();
//		Dataset q = DoubleDataset.createRange(0, 25.0, 0.01);
		Dataset q = DatasetFactory.createRange(0, 25.0, 0.01, Dataset.FLOAT64);
//		Dataset trace = DoubleDataset.zeros(q);
		Dataset trace = DatasetFactory.zeros(q);
		pdfData.setX(q);
		pdfData.setAttribute("x", "long_name", "Q");
		pdfData.setAttribute("x", "units", "angstrom-1"); // apparently, see units on NeXus website
		pdfData.setY(trace);
		pdfData.setAttribute(null, "signal", 1);
//		pdfData.setAttribute("data", "axes", ???); // symbolic link to x array
		nxample.addGroupNode("data", pdfData);
	}

	// Add beam data to the sample
	private static void addBeam(NXsample nxample) {
		NXbeam beam = NexusNodeFactory.createNXbeam();
		double beamEnergy = 76.6; // keV
		double hc_q = 12.3984197; // keV Å
		beam.setIncident_energyScalar(beamEnergy);
		beam.setIncident_wavelengthScalar(hc_q/beamEnergy); // Å
		beam.setDistanceScalar(3200.0); // looks about right from the schematic
		beam.setAttribute("distance", "units", "mm");
		// beam size should come from the slits
		
		nxample.setBeam(beam);
	}

	
	private static void fillData(NXdata palladateData) {
		// Set the attributes for a single data collection
		palladateData.setAttribute(null, "signal", "data");
		palladateData.setAttribute(null, "axes", DatasetFactory.createFromObject(new String[] {"start_time", "detector_name", ".", "."}, new int[]{4}));
		palladateData.setAttribute(null, "start_time_indices", "0");
		palladateData.setAttribute(null, "detector_name_indices", "1");
		
		// Time stamp of frame
		// Time zone
		LocalDateTime stamp = LocalDateTime.of(2015, 10, 1, 12, 14); 
		int countTime = 60;
		int nScans = 1, nDetectors = 1;
		Date startTime = Date.from(stamp.atZone(londonTime).toInstant());
		Date endTime = Date.from(stamp.plusSeconds(countTime).atZone(londonTime).toInstant());
		// Only one scan
		palladateData.setField("start_time", DatasetFactory.createFromObject(new Date[] {startTime}, new int[]{nScans}));
		palladateData.setField("end_time", DatasetFactory.createFromObject(new Date[] {endTime}, new int[]{nScans}));
		// Only one scan, one detector
		palladateData.setField("count_time", DatasetFactory.createFromObject(new double[] {(double) countTime}, new int[]{nScans, nDetectors}));
		palladateData.setAttribute("count_time", "units", "s");
		
		// The actual data
		String palladateFileName = dataFileName;
		String palladateDatasetName = "/entry/data/data (indices = 0)";
		try {
			Dataset palladateActualData = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, palladateFileName, palladateDatasetName));
			palladateActualData.resize(new int[]{1,1,palladateActualData.getShape()[0], palladateActualData.getShape()[1]});
			palladateData.setData(palladateActualData);
		} catch (DatasetException dE) {
			;
		}
	}

	private static void fillUsers(NXentry nxentry) {
		// PI
		NXuser magnum = NexusNodeFactory.createNXuser();
		magnum.setNameScalar("Isaac Porthau");
		magnum.setRoleScalar("principal_investigator");
		magnum.setFacility_user_idScalar("msk14411");
		magnum.setAffiliationScalar("Musketeers of the Guard");
		nxentry.addGroupNode("principal_investigator", magnum);
		
		// Actual user
		NXuser user = NexusNodeFactory.createNXuser();
		user.setNameScalar("Armand Athos");
		user.setRoleScalar("User responsible for data collection");
		user.setFacility_user_idScalar("msk41141");
		nxentry.addGroupNode("user", user);
		
		// Local contact
		NXuser contact = NexusNodeFactory.createNXuser();
		contact.setNameScalar("Henri Aramitz");
		contact.setRoleScalar("local_contact");
		contact.setFacility_user_idScalar("msk00003");
		contact.setEmailScalar("aramis@diamond.ac.uk"); // I really hope this is not a real email address
		nxentry.addGroupNode("local_contact", contact);
	}

	private static void fillEntry(NXentry entry, String fileName) {
		// file time
		entry.setAttribute(null, "file_time", new Date());
		// file name
		entry.setAttribute(null, "file_name", fileName);
		
		// Experiment start time (when I started writing this method)
		entry.setStart_timeScalar(new Date());
		// Experiment end time (now)
		entry.setEnd_timeScalar(new Date());
		// Scan number
		entry.setEntry_identifierScalar("00000");
		// Visit ID
		entry.setExperiment_identifierScalar("00000");
		// Program Name
		entry.setProgram_nameScalar("CeriaNexusWriter.main()");
		// Scan command
		DataNode dn = NexusNodeFactory.createDataNode();
		dn.setDataset(DatasetFactory.createFromObject(new String[]{"Run As..."}, new int[]{1}));
		entry.addDataNode("scan_command", dn);

		// Scan dimensions
		dn = NexusNodeFactory.createDataNode();
		dn.setDataset(DatasetFactory.createFromObject(new int[]{1}, new int []{1}));
		entry.addDataNode("scan_dimensions", dn);
		// Scan identifier
		dn = NexusNodeFactory.createDataNode();
		dn.setDataset(DatasetFactory.createFromObject(new String[]{"a"}, new int[]{1}));
		entry.addDataNode("scan_identifier", dn);
		
	}
	
	private static void fillInstrument(NXinstrument tromboon) {
		// Name
		tromboon.setNameScalar("i15-1");
		// Alternative name: alt_name is Short_Name?
		tromboon.setNameAttributeShort_name("XPDF");
		// Source
		NXsource source = NexusNodeFactory.createNXsource();
		tromboon.setSource(source);
		// Insertion device
		NXinsertion_device device = NexusNodeFactory.createNXinsertion_device();
		tromboon.setInsertion_device(device);
		// Optics hutch
		NXcollection optics = NexusNodeFactory.createNXcollection();
		fillOpticsHutch(optics);
		tromboon.addGroupNode("optics_hutch", optics);
		// Experimental hutch
		NXcollection experimental = NexusNodeFactory.createNXcollection();
		fillExperimentalHutch(experimental);
		tromboon.addGroupNode("experimental_hutch", experimental);
	}

	private static void fillExperimentalHutch(NXcollection experimental) {
		// Optical Rail
		NXcollection rail = NexusNodeFactory.createNXcollection();
		experimental.addGroupNode("optical_rail", rail);
		// Beam defining aperture
		NXslit definer = NexusNodeFactory.createNXslit();
		definer.setX_gapScalar(0.07);
		definer.setY_gapScalar(0.07);
		experimental.addGroupNode("beam_defining_aperture", definer);
		// Beam position monitor
		NXmonitor minotaur = NexusNodeFactory.createNXmonitor();
		fillDefaultMonitor(minotaur, "bpm2", -1.2);
		experimental.addGroupNode("beam_position_monitor_2", minotaur);
		// Sample slits
		NXslit sampleSlit = NexusNodeFactory.createNXslit();
		experimental.addGroupNode("first_sample_slit", sampleSlit);
		sampleSlit = NexusNodeFactory.createNXslit();
		experimental.addGroupNode("second_sample_slit", sampleSlit);
		// I0 beam position monitor
		minotaur = NexusNodeFactory.createNXmonitor();
		fillDefaultMonitor(minotaur, "bpm3", -0.8);
		experimental.addGroupNode("i0_beam_position_monitor_3", minotaur);
		// Cleanup aperture
		NXslit cleanup = NexusNodeFactory.createNXslit();
		experimental.addGroupNode("cleanup", cleanup);
		// Sample stage
		NXcollection stage = NexusNodeFactory.createNXcollection();
		experimental.addGroupNode("sample_stage", stage);
		// Detectors
		NXdetector d1 = NexusNodeFactory.createNXdetector(),
				d2 = NexusNodeFactory.createNXdetector();
		fillDetector1(d1);
		experimental.addGroupNode("detector_1", d1);
		experimental.addGroupNode("detector_2", d2);
	}

	private static void fillDetector1(NXdetector d1) {
		d1.setAttribute(null, "local_name", "det1");
		// Cannot interpret the axes line
		// description
		d1.setDescriptionScalar("Perkin Elmer XRD1611 CP3");
		d1.setTypeScalar("CsI scintillator/a-Si TFT pixel detector");
		d1.setLayoutScalar("area");
		// pixel information
		d1.setX_pixel_sizeScalar(0.2);
		d1.setAttribute("x_pixel_size", "units", "mm");
		d1.setY_pixel_sizeScalar(0.2);
		d1.setAttribute("y_pixel_size", "units", "mm");
		// count time
		d1.setCount_timeScalar(60);
		d1.setAttribute("count_time", "units", "s");
		// frame time
		d1.setFrame_timeScalar(8.);
		d1.setAttribute("frame_time", "units", "s");
		// number of cycles
		d1.setNumber_of_cyclesScalar(8L);
		// gain
		d1.setGain_settingScalar("1.0");
		d1.setAttribute("gain_setting", "units", "pF"); // charge to voltage, I guess?
		// material & absorbance
		d1.setSensor_materialScalar("CsI");
		d1.setSensor_thicknessScalar(0.5);
		d1.setAttribute("sensor_thickness", "units", "mm");
		d1.setField("sensor_density", DatasetFactory.createFromObject(new double[]{4.28})); // not in NeXus yet
		d1.setAttribute("sensor_density", "units", "g cm-3");
		// efficiency curve
		// acquisition mode
		d1.setAcquisition_modeScalar("continuous");
		// should data really be here?
		
		// pixel mask
//		d1.setPixel_mask_appliedScalar(true);
		String maskPath = inputDir + maskFileName;
		try {
			Dataset pxMask = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, maskPath, "/entry/mask/mask"));
			d1.setPixel_mask(pxMask);
			// flat field
	//		d1.setFlatfield_appliedScalar(true);
			d1.setFlatfield(DatasetFactory.ones(pxMask));
		} catch (DatasetException dE) {
			; // do nothing, add no mask
		}

		// detector calibration; additional calibration not specified on Confluence
		addCalibration(d1);
		
	}
	private static void addCalibration(NXdetector d1) {
		
		// Transformation, by hand from the calibration file
		d1.setBeam_center_xScalar(189.15395);
		d1.setAttribute("beam_center_x", "units", "mm");
		d1.setBeam_center_yScalar(211.48295);
		d1.setAttribute("beam_center_y", "units", "mm");
		
		// Calibration method copied exactly
		NXnote calNote = NexusNodeFactory.createNXnote();
		calNote.setAuthorScalar("DAWNScience");
		calNote.setDataset("d_space_index", DatasetFactory.createFromObject(new double[]{0.0, 1.0, 2.0, 4.0, 5.0, 7.0, 8.0, 9.0, 10.0, 11.0, 13.0, 14.0, 18.0, 19.0, 20.0, 21.0, 23.0, 24.0}));
		calNote.setDescriptionScalar("Manual powder diffraction image calibration using point parameters");
		calNote.setDataset("residual", DatasetFactory.createFromObject(new double[] {5.9557840e-5}));
		d1.addGroupNode("calibration_method", calNote);
		
		// calibration data
		String calPath = inputDir + calibrationFileName;
		try {
			Dataset calData = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(null, calPath, "/entry1/instrument/detector/data"));
			d1.setData(calData);
		} catch (DatasetException dE) {
			;
		}
		
		NXtransformations transformations = NexusNodeFactory.createNXtransformations();
		transformations.setDataset("euler_a", DatasetFactory.createFromObject(new double[]{180.00}, new int[]{1}));
		transformations.setAttribute("euler_a", "units", "deg");
		transformations.setDataset("euler_b", DatasetFactory.createFromObject(new double[]{1.3543942}, new int[]{1}));
		transformations.setAttribute("euler_b", "units", "deg");
		transformations.setDataset("euler_c", DatasetFactory.createFromObject(new double[]{-34.548280}, new int[]{1}));
		transformations.setAttribute("euler_c", "units", "deg");
		transformations.setDataset("origin_offset", DatasetFactory.createFromObject(new double[]{1.00}, new int[]{1}));
		transformations.setAttribute("origin_offset", "units", "mm");
		
		d1.addGroupNode("transformations", transformations);
		// distance
		d1.setDistanceScalar(191.91839);
		d1.setAttribute("distance", "units", "mm");
		
	}

	private static void fillOpticsHutch(NXcollection optics) {
		// Primary slit
		NXslit primary = NexusNodeFactory.createNXslit();
		optics.addGroupNode("primary_slit", primary);
		// Attenuator
		NXcollection attenuator = NexusNodeFactory.createNXcollection();
		optics.addGroupNode("attenuator", attenuator);
		// Bent-Laue monochromator
		NXmonochromator mono = NexusNodeFactory.createNXmonochromator();
		optics.addGroupNode("bent_laue_monopchromator", mono);
		// Beam position monitor
		NXmonitor minotaur = NexusNodeFactory.createNXmonitor();
		fillDefaultMonitor(minotaur, "bpm1", -3.2);
		optics.addGroupNode("beam_position_monitor_1", minotaur);
		// Secondary slit
		NXslit secondary = NexusNodeFactory.createNXslit();
		optics.addGroupNode("secondary_slit", secondary);
		// multi-layer mirror
		NXmirror mlm = NexusNodeFactory.createNXmirror();
		optics.addGroupNode("multi_layer_mirror", mlm);
	}

	// Fill an NXmonitor object with some plausible values
	private static void fillDefaultMonitor(NXmonitor minotaur, String localName, double distance) {
		minotaur.setAttribute(null, "local_name", localName);
		minotaur.setAttribute(null, "signal", "data"); // Really?
		minotaur.setAttribute(null, "axes", ".");
		
		minotaur.setField("description", "YAG:Ce screen with camera");
		minotaur.setTypeScalar("Fluorescent screen");
		// Data. What is np?
		int np = 1;
		minotaur.setData(DatasetFactory.createFromObject(new double[]{1.0}, new int[]{np}));
		minotaur.setAttribute("data", "units", "counts");
		// Count time
		minotaur.setCount_timeScalar(60.0);
		minotaur.setAttribute("count_time", "units", "s");
		// centre
		minotaur.setField("x_centre", 0.0);
		minotaur.setAttribute("x_centre", "units", "mm");
		minotaur.setField("y_centre", 0.0);
		minotaur.setAttribute("y_centre", "units", "mm");
		// size
		minotaur.setField("x_size", 0.07);
		minotaur.setAttribute("x_size", "units", "mm");
		minotaur.setField("y_size", 0.07);
		minotaur.setAttribute("y_size", "units", "mm");
		// status
		minotaur.setField("status", "in"); // or "out" or "moving"
		minotaur.setAttribute("status", "local_name", localName + "status");
		// distance from the sample
		minotaur.setDistanceScalar(distance);
		minotaur.setAttribute("distance", "units", "m");
	}

	/*************************************************************************/
	
	private static void makeCapillaryNexus() {
		String filePath = outputDir + capillaryFileName;
		
		NexusFileBuilder builder = new DefaultNexusFileBuilder(filePath);
		NXroot nxroot = builder.getNXroot();
		
		XPDFSampleParameters capillarySample = SampleTestData.createTestSample("vitrex");
		
		dataFileName = inputDir + inputCapillaryFileName;
		containerFileName = beamFileName;
		
		NXsample nxample = capillarySample.getNXsample(builder);
		// the NeXus file builder, newly built, will contain one entry, with one sample. Get that one entry.
		NXentry nxentry = nxroot.getChild("entry1", NXentry.class);
		
		fillEntry(nxentry, filePath);
		
		// Instrument entry for I15-1
		NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		fillInstrument(instrument);
		nxentry.addGroupNode("instrument", instrument);

		// Remaining data for the container
		addBeam(nxample);
		addContainerContainer(nxample, builder, capillarySample.getNexusGeometry());
		addDark(nxample);
		addCalibration(nxample);
		addMask(nxample);

		// Data for the data
		NXdata ceriaData = NexusNodeFactory.createNXdata();
		fillData(ceriaData);
		nxentry.addGroupNode("data", ceriaData);

		
		try {
			builder.createFile(false).close();
			System.err.println("Success writing NeXus file to " + filePath);
		} catch (NexusException nE) {
			System.err.println("Error writing NeXus file to " + filePath + ": " + nE.toString());
		}

		
	}

	private static void addContainerContainer(NXsample nxample, NexusFileBuilder builder, Map<String, GroupNode> geometry) {
		// Add the common container information
		addSampleContainer(nxample, builder);
		
		GroupNode container = nxample.getGroupNode("container");
		
		Map<String, String> namePathMap = new HashMap<String, String>();
//		namePathMap.put("name", "../name");
//		namePathMap.put("description", "../description");
//		namePathMap.put("chemical_formula", "../chemical_formula");
//		namePathMap.put("density", "../density");
		
		// Add all the symbolic link nodes
		for (Map.Entry<String, String> entry : namePathMap.entrySet()) {
			container.addSymbolicNode(entry.getKey(), new SymbolicNodeImpl(NexusNodeFactory.getNextOid(), builder.getNexusTree(), nxample, entry.getValue()));
		}
		
		// Add the geometry nodes
		for (Map.Entry<String, GroupNode> entry : geometry.entrySet()) {
			container.addGroupNode(entry.getKey(), entry.getValue());
		}
	}
	
	/*************************************************************************/
	
	private static void makeI151Nexus() {
		String filePath = outputDir + beamFileName;
		
		NexusFileBuilder builder = new DefaultNexusFileBuilder(filePath);
		NXroot nxroot = builder.getNXroot();

		dataFileName = inputDir + inputBeamFileName;
		containerFileName = null;
		
		nxroot.addGroupNode("entry1", NexusNodeFactory.createNXentry());
		// the NeXus file builder, newly built, will contain one entry. Get that one entry.
		NXentry nxentry = nxroot.getChild("entry1", NXentry.class);

		fillEntry(nxentry, filePath);

		// Instrument entry for I15-1
		NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		fillInstrument(instrument);
		nxentry.addGroupNode("instrument", instrument);

		// Data for the data
		NXdata ceriaData = NexusNodeFactory.createNXdata();
		fillData(ceriaData);
		nxentry.addGroupNode("data", ceriaData);

		try {
			builder.createFile(false).close();
			System.err.println("Success writing NeXus file to " + filePath);
		} catch (NexusException nE) {
			System.err.println("Error writing NeXus file to " + filePath + ": " + nE.toString());
		}

	}	
}
