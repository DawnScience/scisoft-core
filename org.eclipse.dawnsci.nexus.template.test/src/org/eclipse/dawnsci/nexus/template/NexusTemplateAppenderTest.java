package org.eclipse.dawnsci.nexus.template;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.GROUP_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_VALUE;
import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXnote;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.context.NexusContextType;
import org.eclipse.dawnsci.nexus.template.device.NexusTemplateAppender;
import org.eclipse.dawnsci.nexus.template.impl.NexusTemplateServiceImpl;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class NexusTemplateAppenderTest {
	
	private static final String NEXUS_TESTFILES_DIR = "testfiles/dawnsci/data/nexus/";
	private static final String TEMPLATE_APPENDER_FILE_PATH = NEXUS_TESTFILES_DIR + "template-appender.yaml";
	private static final String MALFORMED_TEMPLATE_APPENDER_FILE_PATH = NEXUS_TESTFILES_DIR + "malformed-template-appender.yaml";
	private static final String DETECTOR_GROUP_PATH = "/entry/instrument/detector";
	
	private static INexusFileFactory nexusFileFactory;
	
	private static String testFilesDirName;
	
	private final NexusContextType contextType;
	
	@Parameters(name="nexusContextType= {0}")
	public static Object[] data() {
		return new Object[] { NexusContextType.ON_DISK, NexusContextType.IN_MEMORY };
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testFilesDirName = TestUtils.generateDirectorynameFromClassname(NexusTemplateDeviceTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testFilesDirName);
	}
	
	@Before
	public void setUp() {
		NexusTestUtils.setUpServices();
		nexusFileFactory = ServiceHolder.getNexusFileFactory();
		new TemplateServiceHolder().setNexusTemplateService(new NexusTemplateServiceImpl());
	}
	
	public NexusTemplateAppenderTest(NexusContextType contextType) {
		this.contextType = contextType;
	}

	private NexusFile createInitialFile(String filePath) throws NexusException {
		final TreeFile initialTree = buildInitialTree(filePath);
		final NexusFile nexusFile = nexusFileFactory.newNexusFile(filePath);
		nexusFile.openToWrite(true);
		nexusFile.addNode(Tree.ROOT, initialTree.getGroupNode());
		return nexusFile;
	}
	
	private TreeFile buildInitialTree(String filePath) {
		final NXroot root = NexusNodeFactory.createNXroot();
		final NXentry entry = NexusNodeFactory.createNXentry();
		root.setEntry(entry);
		final NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		entry.setInstrument(instrument);
		final NXdetector detector = NexusNodeFactory.createNXdetector();
		instrument.setDetector("detector", detector);
		detector.setDataScalar(123.456);
		
		final TreeFile treeFile = TreeFactory.createTreeFile(0l, filePath);
		treeFile.setGroupNode(root);
		return treeFile;
	}
	
	private TreeFile createTreeAndAppend(String fileName, String groupPath,
			NexusTemplateAppender<?> appender) throws NexusException {
		final String filePath = testFilesDirName + fileName;
		final GroupNode group;
		switch (contextType) {
			case IN_MEMORY:
				final TreeFile treeFile = buildInitialTree(filePath);
				group = (GroupNode) treeFile.findNodeLink(groupPath).getDestination();
				assertThat(group, is(notNullValue()));
				appender.append(group);
				return treeFile;
			case ON_DISK:
				final NexusFile nexusFile = createInitialFile(filePath);
				group = nexusFile.getGroup(groupPath, false);
				assertThat(group, is(notNullValue()));
				appender.append(nexusFile, group);
				
				// close the file and reload it, to ensure we're testing whats been written to disk
				nexusFile.close();
				return NexusTestUtils.loadNexusFile(filePath, true);
			default:
				throw new IllegalArgumentException("Unknown nexus context type: " + contextType);
		}
	}
	
	@Test
	public void testAppendTemplateFromMap() throws Exception {
		final String fileName = "templateFromMap.nxs";
		final NexusTemplateAppender<NXdetector> appender = new NexusTemplateAppender<>("mapTemplate");
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
		appender.setTemplateMap(templateMap);
		final Map<String, Object> calibrationMethodMap = new HashMap<>();
		calibrationMethodMap.put(NexusConstants.NXCLASS + '@', NXnote.class.getSimpleName());
		calibrationMethodMap.put(NXnote.NX_AUTHOR, "John Smith");
		calibrationMethodMap.put(NXnote.NX_DATE, "2020-05-19");
		calibrationMethodMap.put(NXnote.NX_TYPE, "text/plain");
		calibrationMethodMap.put(NXnote.NX_SEQUENCE_INDEX, 1l);
		calibrationMethodMap.put(NXnote.NX_DATA, "Calibration note");
		templateMap.put("calibration_method" + GROUP_SUFFIX, calibrationMethodMap); // No NXdetector constant for calibration_method, base classes need regenerating?

		final TreeFile actualTree = createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
		
		final TreeFile expectedTree = createExpectedTree(fileName);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testAppendTemplateFromFile() throws Exception {
		final String fileName = "templateFromFile.nxs";
		final NexusTemplateAppender<NXdetector> appender = new NexusTemplateAppender<>("fileTemplate");
		appender.setTemplateFilePath(TEMPLATE_APPENDER_FILE_PATH);
		
		final TreeFile actualTree = createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
		
		final TreeFile expectedTree = createExpectedTree(fileName);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test(expected = NexusException.class)
	public void testAppendTemplateFromMalformedFile() throws Exception {
		final String fileName = "templateFromMalformedFile.nxs";
		final NexusTemplateAppender<NXdetector> appender = new NexusTemplateAppender<>("malformedFileTemplate");
		appender.setTemplateFilePath(MALFORMED_TEMPLATE_APPENDER_FILE_PATH);
		
		createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
	}
	
	@Test
	public void testAppendTemplateFromString() throws Exception {
		final String fileName = "templateFromString.nxs";
		final NexusTemplateAppender<NXdetector> appender = new NexusTemplateAppender<>("stringTemplate");
		final String templateString = "detector_number: !!java.lang.Long 2 # long is not supported by the YAML spec, this is how to specify a long in SnakeYAML\n" + 
				"layout: area\n" + 
				"saturation_value: !!java.lang.Long 999 \n" + 
				"time_of_flight:\n" + 
				"   value: 0.1\n" + 
				"   long_name@: Total time of flight\n" + 
				"raw_time_of_flight:\n" + 
				"   value: !!java.lang.Long 5\n" + 
				"   frequency@: 10000\n" + 
				"calibration_method/:\n" + 
				"   NX_class@: NXnote\n" + 
				"   author: John Smith\n" + 
				"   date: \"2020-05-19\"\n" + 
				"   type: text/plain\n" + 
				"   sequence_index: !!java.lang.Long 1 \n" + 
				"   data: Calibration note";
		appender.setTemplateString(templateString);
		
		final TreeFile actualTree = createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
		
		final TreeFile expectedTree = createExpectedTree(fileName);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test(expected=NexusException.class)
	public void testAppendTemplateFileFromMalformedString() throws Exception {
		final String fileName = "templateFromMalformedString.nxs";
		final NexusTemplateAppender<NXdetector> appender = new NexusTemplateAppender<>("malformedStringTemplate");
		final String templateString = "detector_number: 2\n" + 
				"layout: area\n" + 
				"calibration_method/:\n" + 
				"   @NX_class: NXnote\n" + // @ is a reserved character in yaml, making this string malformed 
				"   author: John Smith"; 
		appender.setTemplateString(templateString);
		
		createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
	}
	
	private TreeFile createExpectedTree(String fileName) throws NexusException {
		final TreeFile expectedTree = buildInitialTree(fileName);
		final NXdetector expectedDetector = (NXdetector) expectedTree.findNodeLink(DETECTOR_GROUP_PATH).getDestination();
		expectedDetector.setDataScalar(123.456);
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
