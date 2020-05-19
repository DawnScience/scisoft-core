package org.eclipse.dawnsci.nexus.template;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_NAME_NX_CLASS;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_SUFFIX;
import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.template.device.NexusTemplateDevice;
import org.eclipse.dawnsci.nexus.template.impl.NexusTemplateServiceImpl;
import org.eclipse.dawnsci.nexus.test.utilities.NexusAssert;
import org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NexusTemplateDeviceTest {
	
	private static final String NEXUS_TESTFILES_DIR = "testfiles/dawnsci/data/nexus/";
	private static final String TEMPLATE_DEVICE_FILE_PATH = NEXUS_TESTFILES_DIR + "template-device.yaml";

	private static String testFilesDirName;
	
	private INexusDeviceService nexusDeviceService;
	
	private NexusDeviceFileBuilder nexusDeviceBuilder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testFilesDirName = TestUtils.generateDirectorynameFromClassname(NexusTemplateDeviceTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testFilesDirName);
	}
	
	@Before
	public void setUp() {
		NexusTestUtils.setUpServices();
		nexusDeviceService = ServiceHolder.getNexusDeviceService();
		nexusDeviceBuilder = new NexusDeviceFileBuilder(nexusDeviceService);
		new TemplateServiceHolder().setNexusTemplateService(new NexusTemplateServiceImpl());
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
		programNameMap.put("value", "gda");
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
	
}
