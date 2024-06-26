package org.eclipse.dawnsci.nexus.template;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_NAME_NX_CLASS;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.GROUP_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_VALUE;
import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.builder.NexusObjectWrapper;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.device.SimpleNexusDevice;
import org.eclipse.dawnsci.nexus.device.impl.NexusDeviceService;
import org.eclipse.dawnsci.nexus.template.device.NexusTemplateAppender;
import org.eclipse.dawnsci.nexus.template.device.NexusTemplateDevice;
import org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.osgi.services.ServiceProvider;

public class NexusTemplateDeviceTest {
	
	private static final String NEXUS_TESTFILES_DIR = "testfiles/dawnsci/data/nexus/";
	
	private static final String TEMPLATE_DEVICE_FILE_PATH = NEXUS_TESTFILES_DIR + "template-device.yaml";
	private static final String TEMPLATE_APPENDER_FILE_PATH = NEXUS_TESTFILES_DIR + "template-appender.yaml";

	private static String testFilesDirName;
	
	private static INexusDeviceService nexusDeviceService;
	
	private static NexusDeviceFileBuilder nexusDeviceBuilder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		NexusTestUtils.setUpServices();
		
		testFilesDirName = TestUtils.generateDirectorynameFromClassname(NexusTemplateDeviceTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testFilesDirName);
		
		nexusDeviceService = ServiceProvider.getService(INexusDeviceService.class);
		nexusDeviceBuilder = new NexusDeviceFileBuilder(nexusDeviceService);
		ServiceProvider.getService(NexusTemplateService.class);
	}
	
	@AfterClass
	public static void tearDown() {
		ServiceProvider.reset();
	}
	
	@Test
	public void testTemplateDeviceFromMap() throws Exception {
		// Arrange, set up the template device
		final NexusTemplateDevice<NXcollection> templateDevice = new NexusTemplateDevice<>();
		templateDevice.setName("template");
		final Map<String, Object> templateMap = new HashMap<>();
		templateMap.put(ATTRIBUTE_NAME_NX_CLASS + ATTRIBUTE_SUFFIX, NXcollection.class.getSimpleName()); 
		templateMap.put("name", "template");
		templateMap.put("description", "device from a template");
		templateMap.put("attr@", "attrValue");
		final Map<String, Object> programNameMap = new HashMap<>(); // a nested map to add attributes to a field
		programNameMap.put(MAPPING_NAME_VALUE, "gda");
		programNameMap.put("version@", "9.13");
		programNameMap.put("configuration@", "dummy");
		templateMap.put("program_name", programNameMap);
		templateDevice.setTemplateMap(templateMap);
		templateDevice.register();
		
		assertThat(nexusDeviceService.getNexusDevice(templateDevice.getName()), is(sameInstance(templateDevice))); 
		
		// construct the expected tree
		final TreeFile expectedTree = createExpectedTree(templateDevice);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(templateDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testTemplateDeviceFromString() throws Exception {
		final NexusTemplateDevice<NXcollection> templateDevice = new NexusTemplateDevice<>();
		templateDevice.setName("template");
		final String templateString = "NX_class@: NXcollection\n" + 
				"name: template\n" + 
				"description: device from a template\n" + 
				"attr@: attrValue\n" + 
				"program_name:\n" + 
				"   value: gda\n" + 
				"   version@: \"9.13\"\n" + 
				"   configuration@: dummy";
		templateDevice.setTemplateString(templateString);
		templateDevice.register();
		
		assertThat(nexusDeviceService.getNexusDevice(templateDevice.getName()), is(sameInstance(templateDevice)));
		
		// construct the expected tree
		final TreeFile expectedTree = createExpectedTree(templateDevice);
		
		// build the nexus tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(templateDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}

	private TreeFile createExpectedTree(final NexusTemplateDevice<NXcollection> templateDevice) throws NexusException {
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXentry entry = ((NXroot) expectedTree.getGroupNode()).getEntry();
		final NXcollection collection = NexusNodeFactory.createNXcollection();
		entry.setCollection(templateDevice.getName(), collection);
		collection.setField("name", "template");
		collection.setField("description", "device from a template");
		collection.setAttribute(null, "attr", "attrValue");
		collection.setField("program_name", "gda");
		collection.setAttribute("program_name", "version", "9.13");
		collection.setAttribute("program_name", "configuration", "dummy");
		return expectedTree;
	}
	
	@Test
	public void testTemplateDeviceFromFile() throws Exception {
		// Arrange, set up the template device
		final NexusTemplateDevice<NXcollection> templateDevice = new NexusTemplateDevice<>();
		templateDevice.setName("template");
		templateDevice.setTemplateFilePath(TEMPLATE_DEVICE_FILE_PATH);
		templateDevice.register();
		
		assertThat(nexusDeviceService.getNexusDevice(templateDevice.getName()), is(sameInstance(templateDevice))); 
		
		// construct the expected tree
		final TreeFile expectedTree = createExpectedTree(templateDevice);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(templateDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testTemplateAppenderFromMap() throws Exception {
		// create the detector device
		final NXdetector nxDetector = NexusNodeFactory.createNXdetector();
		nxDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		final INexusDevice<NXdetector> detector = new SimpleNexusDevice<>(new NexusObjectWrapper<>(
				"detector", nxDetector));
		
		// create and setup the template appender
		final NexusTemplateAppender<NXdetector> templateAppender = new NexusTemplateAppender<>();
		templateAppender.setName(detector.getName());
		final Map<String, Object> templateMap = new HashMap<>();
		templateMap.put(NXdetector.NX_DETECTOR_NUMBER, 2l);
		templateMap.put(NXdetector.NX_LAYOUT, "area");
		templateMap.put(NXdetector.NX_SATURATION_VALUE, 999l);
		final Map<String, Object> timeOfFlightMap = new HashMap<>();
		timeOfFlightMap.put(MAPPING_NAME_VALUE, 0.1);
		timeOfFlightMap.put(NXdetector.NX_TIME_OF_FLIGHT_ATTRIBUTE_LONG_NAME + '@', "Total time of flight");
		templateMap.put(NXdetector.NX_TIME_OF_FLIGHT, timeOfFlightMap);
		final Map<String, Object> rawTimeOfFlightMap = new HashMap<>();
		rawTimeOfFlightMap.put(MAPPING_NAME_VALUE, 5l);
		rawTimeOfFlightMap.put(NXdetector.NX_RAW_TIME_OF_FLIGHT_ATTRIBUTE_FREQUENCY + '@', 10000);
		templateMap.put(NXdetector.NX_RAW_TIME_OF_FLIGHT, rawTimeOfFlightMap);
		templateAppender.setTemplateMap(templateMap);
		final Map<String, Object> calibrationMethodMap = new HashMap<>();
		calibrationMethodMap.put(NexusConstants.NXCLASS + '@', NXnote.class.getSimpleName());
		calibrationMethodMap.put(NXnote.NX_AUTHOR, "John Smith");
		calibrationMethodMap.put(NXnote.NX_DATE, "2020-05-19");
		calibrationMethodMap.put(NXnote.NX_TYPE, "text/plain");
		calibrationMethodMap.put(NXnote.NX_SEQUENCE_INDEX, 1l);
		calibrationMethodMap.put(NXnote.NX_DATA, "Calibration note");
		templateMap.put("calibration_method" + GROUP_SUFFIX, calibrationMethodMap); // No NXdetector constant for calibration_method, base classes need regenerating?
		((NexusDeviceService) nexusDeviceService).register(templateAppender);
		
		// construct the expected tree
		final TreeFile expectedTree = createExpectedTreeForAppenderTests(detector);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(detector);
		assertNexusTreesEqual(expectedTree, actualTree);
	}

	@Test
	public void testTemplateAppenderFromFile() throws Exception {
		// create the detector device
		final NXdetector nxDetector = NexusNodeFactory.createNXdetector();
		nxDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		final INexusDevice<NXdetector> detector = new SimpleNexusDevice<>(new NexusObjectWrapper<>(
				"detector", nxDetector));
		
		// create and setup the template appender
		final NexusTemplateAppender<NXdetector> templateAppender = new NexusTemplateAppender<>();
		templateAppender.setName(detector.getName());
		templateAppender.setTemplateFilePath(TEMPLATE_APPENDER_FILE_PATH);
		((NexusDeviceService) nexusDeviceService).register(templateAppender);
		
		// construct the expected tree
		final TreeFile expectedTree = createExpectedTreeForAppenderTests(detector);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(detector);
		assertNexusTreesEqual(expectedTree, actualTree);
	}

	private TreeFile createExpectedTreeForAppenderTests(final INexusDevice<NXdetector> detector) throws NexusException {
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXdetector expectedDetector  = NexusNodeFactory.createNXdetector();
		expectedDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		instrument.setDetector(detector.getName(), expectedDetector);
		expectedDetector.setDetector_numberScalar(2l);
		expectedDetector.setLayoutScalar("area");
		expectedDetector.setSaturation_valueScalar(999l);
		expectedDetector.setTime_of_flightScalar(0.1);
		expectedDetector.setTime_of_flightAttributeLong_name("Total time of flight");
		expectedDetector.setRaw_time_of_flightScalar(5l);
		expectedDetector.setRaw_time_of_flightAttributeFrequency(10000);
		final NXnote calibrationNote = NexusNodeFactory.createNXnote();
		calibrationNote.setAuthorScalar("John Smith");
		calibrationNote.setField(NXnote.NX_DATE, "2020-05-19"); // note, use a string instead of a Date as that's easier
		calibrationNote.setTypeScalar("text/plain");
		calibrationNote.setSequence_indexScalar(1l);
		calibrationNote.setDataScalar("Calibration note");
		expectedDetector.setCalibration_method(calibrationNote);
		return expectedTree;
	}
	
}
