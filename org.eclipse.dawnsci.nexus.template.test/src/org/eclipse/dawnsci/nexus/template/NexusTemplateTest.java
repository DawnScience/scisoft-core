package org.eclipse.dawnsci.nexus.template;

import static org.eclipse.dawnsci.nexus.context.NexusContextType.IN_MEMORY;
import static org.eclipse.dawnsci.nexus.context.NexusContextType.ON_DISK;
import static org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils.getNode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.context.NexusContextType;
import org.eclipse.dawnsci.nexus.template.impl.NexusTemplateServiceImpl;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.StringDataset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests applying a yaml template to nexus file, either on memory or on disk.
 */
@RunWith(value = Parameterized.class)
public class NexusTemplateTest {
	
	private static final String NEXUS_TESTFILES_DIR = "testfiles/dawnsci/data/nexus/";
	private static final String P45_EXAMPLE_NEXUS_FILE_PATH = NEXUS_TESTFILES_DIR + "p45-example.nxs";
	private static final String TEMPLATE_FILE_PATH = NEXUS_TESTFILES_DIR + "test-template.yaml";
	
	private static final String NEW_ENTRY_TEMPLATE_PREFIX = "scan/:\n  NX_class@: NXentry\n";
	private static final String EXISTING_ENTRY_TEMPLATE_PREFIX = "entry/:\n  NX_class@: NXentry\n";
	
	private NexusTemplateService templateService;
	
	private static String testFilesDirName;

	private final NexusContextType contextType;

	@Parameters(name="nexusContextType= {0}")
	public static Object[] data() {
		return new Object[] { NexusContextType.ON_DISK, NexusContextType.IN_MEMORY };
	}
	
	public NexusTemplateTest(NexusContextType contextType) {
		this.contextType = contextType;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testFilesDirName = TestUtils.generateDirectorynameFromClassname(NexusTemplateTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testFilesDirName);
		NexusTestUtils.setUpServices();
	}
	
	@Before
	public void setUp() {
		templateService = new NexusTemplateServiceImpl();
	}
	
	private Tree applyTemplateStringToTree(String templateString, Tree tree) throws Exception {
		final NexusTemplate template = templateService.createTemplateFromString(templateString);
		template.apply(tree);
		return tree;
	}
	
	private NXroot applyTemplateStringToEmptyTree(String templateString) throws Exception {
		final NexusTemplate template = templateService.createTemplateFromString(templateString);
		if (contextType == NexusContextType.IN_MEMORY) {
			Tree tree = TreeFactory.createTree(0, null);
			final NXroot root = NexusNodeFactory.createNXroot();
			tree.setGroupNode(root);
			template.apply(tree);
			return root;
		} else {
			// create a new empty nexus file
			final String filePath = testFilesDirName + "test-" + UUID.randomUUID() + ".nxs";
			NexusFile file = NexusTestUtils.createNexusFile(filePath);

			// uncomment the two lines below to test template.apply(String filePath)
//			file.close();
//			template.apply(filePath);
			
			// uncomment the two lines below to test template.apply(NexusFile nexusFile)
			template.apply(file);
			file.close();
			
			TreeFile treeFile = loadNexusFile(filePath);
			return (NXroot) treeFile.getGroupNode();
		}
	}
	
	private NXroot applyTemplateStringToTestFile(String templateString) throws Exception {
		final Tree tree = applyTemplateStringToFile(templateString, P45_EXAMPLE_NEXUS_FILE_PATH);
		return (NXroot) tree.getGroupNode();
	}
	
	private Tree applyTemplateStringToFile(String templateString, String nexusFilePath) throws Exception {
		final NexusTemplate template = templateService.createTemplateFromString(templateString);
		switch (contextType) {
			case IN_MEMORY:
				final Tree tree = loadNexusFile(nexusFilePath);
				template.apply(tree);
				return tree;
			case ON_DISK:
				final String tempFilePath = testFilesDirName + "test-" + UUID.randomUUID().toString() + ".nxs";
				Files.copy(Paths.get(nexusFilePath), Paths.get(tempFilePath));
				template.apply(tempFilePath);
				TreeFile treeFile = loadNexusFile(tempFilePath);
				return treeFile;
			default:
				throw new IllegalArgumentException("Unknown context type: " + contextType);
		}
	}
	
	private TreeFile loadNexusFile(String fileName) throws Exception {
		// Arrange - load the nexus file and set up the template
		final TreeFile sourceTree = NexusTestUtils.loadNexusFile(fileName, true);
		assertThat(sourceTree, is(notNullValue()));
		return sourceTree;
	}
	
	@Test(expected = NexusException.class)
	public void testMalformedTemplate() throws Exception {
		// @ is a reserved character in yaml, making this template malformed
		applyTemplateStringToEmptyTree("scan:/\n  @NX_class: NXentry");
	}
	
	@Test
	public void testAddGroupNode() throws Exception {
		final NXroot root = applyTemplateStringToEmptyTree(NEW_ENTRY_TEMPLATE_PREFIX);
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
	}

	@Test(expected = NexusException.class)
	public void testAddGroupNodeNoMapping() throws Exception {
		applyTemplateStringToEmptyTree("scan/:");
	}
	
	@Test(expected = NexusException.class)
	public void testAddGroupNodeWithoutNxClass() throws Exception {
		applyTemplateStringToEmptyTree("scan/:\n  foo: bar");
	}
	
	@Test(expected = NexusException.class)
	public void testAddGroupNodeWithInvalidNxClass() throws Exception {
		applyTemplateStringToEmptyTree("scan/:\n  NX_class@: NXnonexist");
	}

	@Test
	public void testAddGroupNodeAlreadyExists() throws Exception {
		applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX);
	}
	
	@Test
	public void testAddGroupNodeToExistingGroupNode() throws Exception {
		final NXroot root = applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX
				+ "  before_scan/:\n    NX_class@: NXcollection\n    foo: bar");
		final NXentry entry = root.getEntry("entry");
		assertThat(entry, is(notNullValue()));
		
		final NXcollection collection = entry.getCollection("before_scan");
		assertThat(collection, is(notNullValue()));
	}
	
	@Test(expected=NexusException.class)
	public void testAddGroupNodeAlreadyExistsDifferentClass() throws Exception {
		applyTemplateStringToTestFile("entry/:\n  NXclass@: NXpositioner");
	}
	
	@Test(expected=NexusException.class)
	public void testAddGroupNodeAtLocationOfExistingDataNode() throws Exception {
		applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX
				+ "  sample/:\n"
				+ "    NX_class@: NXsample\n"
				+ "    description/:\n"
				+ "      NX_class@: NXnote");
	}
	
	@Test
	public void testAddAttribute() throws Exception {
		final NXroot root = applyTemplateStringToEmptyTree(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  myattr@: blah"
				+ "\n  numattr@: 27.45");
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		Attribute myattr = entry.getAttribute("myattr");
		assertThat(myattr, is(notNullValue()));
		assertThat(myattr.getValue().getString(), is(equalTo("blah")));
		
		Attribute numattr = entry.getAttribute("numattr");
		assertThat(numattr, is(notNullValue()));
		assertThat(numattr.getValue().getDouble(), is(closeTo(27.45, 1e-15)));
		// TODO: construct expected tree? 
	}
	
	@Test(expected=NexusException.class)
	public void testAddAttributeToExistingGroupNode() throws Exception {
		applyTemplateStringToTestFile("entry/:\n  default@: data");
	}

	@Test(expected=NexusException.class)
	public void testAddExistingAttributeToGroupNode() throws Exception {
		applyTemplateStringToTestFile("entry/:\n  instrument/:\n    default@: mandelbrot");
	}

	@Test
	public void testAddDataNodeInline() throws Exception {
		final NXroot root = applyTemplateStringToEmptyTree(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  title: my scan\n"
				+ "  duration: 260\n"
				+ "  collection_time: 123.2");
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		// TODO assert datanode?
		assertThat(entry.getTitleScalar(), is(equalTo("my scan")));
		assertThat(entry.getDurationScalar(), is(equalTo(260L)));
		Assert.assertTrue(entry.getDuration() instanceof IntegerDataset);
		assertThat(entry.getCollection_timeScalar(), is(closeTo(123.2, 1e-15)));
	}

	@Test
	public void testAddDataNodeWithAttributes() throws Exception {
		final NXroot root = applyTemplateStringToEmptyTree(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  program_name:\n"
				+ "    value: myprogram\n"
				+ "    configuration@: foo\n"
				+ "    version@: 3");
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		assertThat(entry.getProgram_nameScalar(), is(equalTo("myprogram")));
		assertThat(entry.getProgram_nameAttributeConfiguration(), is(equalTo("foo")));
		assertThat(entry.getProgram_nameAttributeVersion(), is(equalTo("3")));
		Assert.assertTrue(entry.getDataNode("program_name").getAttribute("version").getValue() instanceof IntegerDataset);
	}

	@Test(expected = NexusException.class)
	public void testAddDataNodeWithNoValue() throws Exception {
		applyTemplateStringToEmptyTree(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  program_name:\n"
				+ "    configuration@: myprogram\n"
				+ "    version@: 3");
	}
	
	@Test(expected = NexusException.class)
	public void testAddDataNodeWithIllegalChildMapping() throws Exception {
		applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  beamflux:\n"
				+ "    value: hello\n"
				+ "    foo: bar");
	}
	
	@Ignore
	@Test(expected = NexusException.class)
	public void testAddDataEmptyValue() throws Exception {
//		applyTemplateStringToEmptyTree(BASIC_TEMPLATE
//				+ "  foo: "); // TODO fix
	}
	
	@Test
	public void testAddDataNodeToExistingGroup() throws Exception {
		final NXroot root = applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX + "  foo: bar\n");
		final NXentry entry = root.getEntry("entry");
		final DataNode dataNode = entry.getDataNode("foo");
		assertThat(dataNode, is(notNullValue()));
		final IDataset dataset = dataNode.getDataset().getSlice();
		assertThat(dataset.getString(), is(equalTo("bar")));
	}
	
	@Test(expected=NexusException.class)
	public void testAddDataNodeAlreadyExists() throws Exception {
		// you cannot add a data node that already exists
		applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX + "  experiment_identifier: 5\n");
	}
	
	@Test(expected=NexusException.class)
	public void testAddDataNodeAtLocationOfExistingGroupNode() throws Exception {
		applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX + "  instrument: p45");
	}
	
	@Test
	public void testAddLinkToGroupNode() throws Exception {
		final String linkPath = "/entry/mandelbrot";
		final NXroot root = applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  data/: " + linkPath);
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		NXdata data = entry.getData("data");
		assertThat(data, is(notNullValue()));
		assertThat(data, is(sameInstance(getNode(root, linkPath))));
	}
	
	@Test
	public void testAddLinkToGroupNodeWithTrailingSlash() throws Exception {
		final String linkPath = "/entry/mandelbrot/"; // trailing slash
		final NXroot root = applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  data/: " + linkPath);
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		NXdata data = entry.getData("data");
		assertThat(data, is(notNullValue()));
		assertThat(data, is(sameInstance(getNode(root, linkPath))));
	}
	
	@Test
	public void testCopyNxDataGroupWithAxisSubstitutions() throws Exception {
		final String linkPath = "/entry/mandelbrot";
		final Map<String, String> axisSubstibutions = new HashMap<>();
		axisSubstibutions.put("stagex_value", "x");
		axisSubstibutions.put("stagey_value", "y");
		
		final StringBuilder templateBuilder = new StringBuilder();
		templateBuilder.append(NEW_ENTRY_TEMPLATE_PREFIX);
		templateBuilder.append("  data*:\n");
		templateBuilder.append("    nodePath: ");
		templateBuilder.append(linkPath);
		templateBuilder.append("\n");
		templateBuilder.append("    axisSubstitutions:\n");
		for (Map.Entry<String, String> axisSubstitution : axisSubstibutions.entrySet()) {
			templateBuilder.append("      ");
			templateBuilder.append(axisSubstitution.getKey());
			templateBuilder.append(": ");
			templateBuilder.append(axisSubstitution.getValue());
			templateBuilder.append("\n");
		}
		
		final NXroot root = applyTemplateStringToTestFile(templateBuilder.toString());
		
		final NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		final NXdata newData = entry.getData("data");
		assertThat(newData, is(notNullValue()));
		
		final NXdata origData = (NXdata) getNode(root, linkPath);
		assertThat(newData.getNumberOfAttributes(), is(equalTo(origData.getNumberOfAttributes())));
		assertThat(newData.getNumberOfDataNodes(), is(equalTo(origData.getNumberOfDataNodes())));
		
		// check that the new NXdata has the same data nodes, with axis substitutions applied to their names 
		for (Map.Entry<String, DataNode> dataNodeEntry : origData.getDataNodeMap().entrySet()) {
			final String origDataNodeName = dataNodeEntry.getKey();
			final DataNode origDataNode = dataNodeEntry.getValue();
			String destDataNodeName = origDataNodeName;
			
			for (Map.Entry<String, String> axisSubstitution : axisSubstibutions.entrySet()) {
				if (destDataNodeName.contains(axisSubstitution.getKey())) {
					destDataNodeName = destDataNodeName.replace(axisSubstitution.getKey(), axisSubstitution.getValue());
					break;
				}
			}
			
			assertThat(newData.getDataNode(destDataNodeName), is(sameInstance(origDataNode)));
			
			if (!destDataNodeName.equals("data")) {
				// all axes dataset (i.e. all except 'data' - the signal field) will have a _indices attribute
				assertThat(newData.getAttribute(destDataNodeName + "_indices").getValue(),
						is(equalTo(origData.getAttribute(origDataNodeName + "_indices").getValue())));
			}
		}
		
		assertThat(newData.getAttribute("signal").getValue().getString(), is(equalTo("data")));
		
		assertThat(newData.getAttribute("axes").getValue(), is(equalTo(
				DatasetFactory.createFromObject(new String[] { "y_set", "x_set", "real", "imaginary" }))));
	}
	
	@Test(expected = NexusException.class)
	public void testAddLinkToGroupNodeWithAddedAttributes() throws Exception {
		NXroot root = applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  data:\n"
				+ "    link: /entry/data\n"
				+ "    newAttr@: hello");
		
		// Note: we decided against allowing this for now.
//		NXentry entry = root.getEntry("scan");
//		assertThat(entry, is(notNullValue()));
//		
//		// check that the data group has been linked to
//		NXdata data = entry.getData("data");
//		assertThat(data, is(notNullValue()));
//		assertThat(data, is(sameInstance(getNode(root, "/entry/data"))));
//		Attribute attr = data.getAttribute("newAttr");
//		assertThat(attr, is(notNullValue()));
//		assertThat(attr.getValue().getString(), is(equalTo("hello")));
//		
//		// check that a 'link' node hasn't been added
//		assertThat(data.getNode("link"), is(nullValue()));
	}
	
	@Test(expected = NexusException.class)
	public void testAddGroupNodeLinkWithIllegalChildMapping() throws Exception {
		applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  data:\n"
				+ "    link: /entry/data\n"
				+ "    foo: bar");
	}
	
	@Test
	public void testAddLinkToDataNode() throws Exception {
		final String linkPath = "/entry/sample/beam/extent";
		final NXroot root = applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX +
				"  beamsize: " + linkPath);
		
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		
		DataNode beamflux = entry.getDataNode("beamsize");
		assertThat(beamflux, is(notNullValue()));
		assertThat(beamflux, is(sameInstance(getNode(root, linkPath))));
	}
	
	@Test(expected = NexusException.class)
	public void testAddDataNodeLinkWithAddedAttributes() throws Exception {
		final NXroot root = applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX
				+ "  beamflux:\n"
				+ "    link: /entry/sample/beam/flux\n"
				+ "    newattr@: hello");
		
//		NXentry entry = root.getEntry("scan"); // TODO remove if we're not going to support this
//		assertThat(entry, is(notNullValue()));
//		
//		DataNode beamflux = entry.getDataNode("beamflux");
//		assertThat(beamflux, is(notNullValue()));
//		assertThat(beamflux, is(sameInstance(getNode(root, "/entry/sample/beam/flux"))));
//		beamflux.getAttribute("newattr");
	}
	
	@Test(expected=NexusException.class)
	public void testAddLinkAtLocationOfExistingDataNode() throws Exception {
		applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX
				+ "  start_time: /entry/ample/description");
	}
	
	@Test(expected=NexusException.class)
	public void testAddLinkAtLocationOfExistingGroupNode() throws Exception {
		applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX
				+ "  mandelbrot: /entry/instrument/mandelbrot");
	}
	
	@Test
	public void testAddLinkToAttribute() throws Exception {
		final String linkRoot = "/entry/mandelbrot/";
		final NXroot root = applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX +
				"  data/:\n"
				+ "    NX_class@: NXdata\n"
				+ "    signal@: data\n"
				+ "    data: /entry/mandelbrot/data\n"
				+ "    x_indices@: " + linkRoot + "stagex_value_set_indices@\n"
				+ "    y_indices@: " + linkRoot + "stagey_value_set_indices@\n"
				+ "    x: " + linkRoot + "stagex_value_set\n"
				+ "    y: " + linkRoot + "stagey_value_set\n");
		
		NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));
		NXdata data = entry.getData("data");
		assertThat(data, is(notNullValue()));
		assertThat(data.getAttribute(NXdata.NX_ATTRIBUTE_SIGNAL).getValue().getString(), is(equalTo("data")));
		assertThat(data.getDataNode("x"), is(sameInstance(getNode(root, linkRoot + "stagex_value_set"))));
		assertThat(data.getDataNode("y"), is(sameInstance(getNode(root, linkRoot + "stagey_value_set"))));
		assertThat(data.getAttribute("x_indices").getValue(), is(equalTo(
				getNode(root, linkRoot).getAttribute("stagex_value_set_indices").getValue())));
		assertThat(data.getAttribute("x_indices").getValue(), is(equalTo(DatasetFactory.createFromObject(new int[] { 1 } ))));
		assertThat(data.getAttribute("y_indices").getValue(), is(equalTo(
				getNode(root, linkRoot).getAttribute("stagey_value_set_indices").getValue())));
		assertThat(data.getAttribute("y_indices").getValue(), is(equalTo(DatasetFactory.createFromObject(new int[] { 0 } ))));
	}
	
	@Test(expected = NexusException.class)
	public void testAddLinkFieldToAttribute() throws Exception {
		// an field cannot be linked to an attribute,
		// i.e. there must be an '@' at the end of both the new attribute name and attribute link path
		final String linkRoot = "/entry/mandelbrot/";
		applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX +
				"  data/:\n"
				+ "    NX_class@: NXdata\n"
				+ "    signal@: data\n"
				+ "    data: /entry/mandelbrot/data\n"
				+ "    x_indices: " + linkRoot + "stagex_value_set_indices@\n" // missing '@' at end of new attr names
				+ "    y_indices: " + linkRoot + "stagey_value_set_indices@\n" // should be 'x_indices@'
				+ "    x: " + linkRoot + "stagex_value_set\n"
				+ "    y: " + linkRoot + "stagey_value_set\n");
	}
	
	@Test(expected = NexusException.class)
	public void testAddLinkAttributeToField() throws Exception {
		// Cannot link an attribute to a field 
		final String linkRoot = "/entry/mandelbrot/";
		applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX +
				"  data/:\n"
				+ "    NX_class@: NXdata\n"
				+ "    signal@: data\n"
				+ "    data: /entry/mandelbrot/data\n"
				+ "    x_indices@: " + linkRoot + "stagex_value_set\n"
				+ "    y_indices@: " + linkRoot + "stagey_value_set\n"
				+ "    x: " + linkRoot + "stagex_value_set\n"
				+ "    y: " + linkRoot + "stagey_value_set\n");
	}
	
	@Test(expected = NexusException.class)
	public void testBrokenLink() throws Exception {
		applyTemplateStringToTestFile(NEW_ENTRY_TEMPLATE_PREFIX 
				+ "  beamflux: /entry/sample/nosuchfield");
	}
	
	@Test
	public void testExternalLink() throws Exception {
		final String template = NEW_ENTRY_TEMPLATE_PREFIX
				+ "  data/:\n"
				+ "    NX_class@: NXdata\n"
				+ "    data: /entry/instrument/detector/data";

		// create a nexus tree structure with an external link
		final String filePath = testFilesDirName + "external-links.nxs";
		final String linkedFilePath = testFilesDirName + "linked.h5";
		final String nodePath = "/entry/data";
		final TreeFile treeFile = TreeFactory.createTreeFile(filePath.hashCode(), filePath);
		final NXroot root = NexusNodeFactory.createNXroot();
		treeFile.setGroupNode(root);
		final NXentry entry = NexusNodeFactory.createNXentry();
		root.setEntry(entry);
		final NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		entry.setInstrument(instrument);
		final NXdetector detector = NexusNodeFactory.createNXdetector();
		instrument.setDetector(detector);
		final SymbolicNode externalLinkNode = NexusNodeFactory.createSymbolicNode(
				new File(linkedFilePath).toURI(), nodePath); 
		detector.addSymbolicNode(NXdetector.NX_DATA, externalLinkNode);
		
		final Tree treeWithTemplate;
		if (contextType == IN_MEMORY) {
			treeWithTemplate = applyTemplateStringToTree(template, treeFile);
		} else if (contextType == ON_DISK) {
			// if we're testing on disk, save the nexus file with the link
			// and create the file to link to
			NexusTestUtils.saveNexusFile(treeFile);
			
			final TreeFile linkedFile = TreeFactory.createTreeFile(linkedFilePath.hashCode(), linkedFilePath);
			final NXroot root2 = NexusNodeFactory.createNXroot();
			linkedFile.setGroupNode(root2);
			final NXentry entry2 = NexusNodeFactory.createNXentry();
			root2.setEntry(entry2);
			entry2.setDataset("data", DatasetFactory.createFromObject(IntStream.range(0, 10).toArray()));
			NexusTestUtils.saveNexusFile(linkedFile);
			
			treeWithTemplate = applyTemplateStringToFile(template, filePath);
		} else {
			throw new IllegalArgumentException(); // unknown application mode
		}
		
		final NXroot root2 = (NXroot) treeWithTemplate.getGroupNode();
		final NXentry scanEntry = root2.getEntry("scan");
		assertThat(scanEntry, is(notNullValue()));
		final NXdata data = scanEntry.getData();
		assertThat(data, is(notNullValue()));
		final Node dataNode = data.getNode("data");
		assertThat(dataNode, is(notNullValue()));
		
		if (contextType == IN_MEMORY) {
			assertThat(dataNode.isSymbolicNode(), is(true));
			
			// get the newly created link node. Note it has to be a copy, as NexusFileHDF5 cannot create a
			// link to a symbolic node when saving the tree
			final SymbolicNode newExternalLinkNode = (SymbolicNode) dataNode;
			assertThat(newExternalLinkNode, is(not(sameInstance(externalLinkNode))));
			assertThat(newExternalLinkNode.getSourceURI(), is(equalTo(externalLinkNode.getSourceURI())));
			assertThat(newExternalLinkNode.getPath(), is(equalTo(externalLinkNode.getPath())));
		} else if (contextType == ON_DISK) {
			assertThat(dataNode.isDataNode(), is(true));
			final IDataset dataset = ((DataNode) dataNode).getDataset().getSlice();
			assertThat(dataset.getRank(), is(1));
			assertThat(dataset.getShape(), is(equalTo(new int[] { 10 })));
			assertThat(dataset.getElementClass(), is(equalTo(Integer.class)));
			IntStream.range(0, 10).forEach(i -> assertThat(dataset.getInt(i), is(i)));
		}
	}
	
	@Test
	public void testAddSubentry() throws Exception {
		final NXroot root = applyTemplateStringToTestFile(EXISTING_ENTRY_TEMPLATE_PREFIX
						+ "  myscan/:\n    NX_class@: NXsubentry\n"
						+ "    definition: NXscan\n    scan_command: scan foo 1 2 3 det\n"
						+ "    start_time: /entry/start_time\n    end_time: /entry/end_time\n");
		final NXentry entry = root.getEntry("entry");
		assertThat(entry, is(notNullValue()));
		
		assertThat(entry.getAllSubentry().size(), is(1));
		final NXsubentry subentry = entry.getSubentry("myscan");
		assertThat(subentry, is(notNullValue()));
		assertThat(subentry.getDefinitionScalar(), is(equalTo("NXscan")));
		assertThat(subentry.getDataNode(NXsubentry.NX_START_TIME), is(sameInstance(entry.getDataNode(NXentry.NX_START_TIME))));
		assertThat(subentry.getDataNode(NXsubentry.NX_END_TIME), is(sameInstance(entry.getDataNode(NXentry.NX_END_TIME))));
	}
	
	@Test
	public void testApplyLargeTemplate() throws Exception {
		final NexusTemplate template = templateService.loadTemplate(TEMPLATE_FILE_PATH);
		final TreeFile tree = loadNexusFile(P45_EXAMPLE_NEXUS_FILE_PATH); 
		final NXroot root = (NXroot) tree.getGroupNode();
		
		// apply template
		template.apply(tree);
		
		// The template used is roughly based on the NXscan application definition, but for a mapping scan
		final NXentry entry = root.getEntry("scan");
		assertThat(entry, is(notNullValue()));

		// Note: we can't use entry.getStart_timeScalar as this expects a java.util.Date,
		// whereas the nexus file actually contains a string 
		IDataset startTime = entry.getDataset(NXentry.NX_START_TIME);
		assertThat(startTime, is(notNullValue()));
		assertTrue(startTime instanceof StringDataset);
		assertThat(startTime.getShape(), is(equalTo(new int[0])));
		assertThat(startTime.getString(), is(equalTo("2019-05-28T17:56:40.351+01:00")));
		
		Dataset endTime = DatasetUtils.convertToDataset(entry.getDataset(NXentry.NX_END_TIME));
		assertThat(endTime, is(notNullValue()));
		assertTrue(endTime instanceof StringDataset);
		assertThat(endTime.getShape(), is(equalTo(new int[] { 1 }))); // TODO change to new int[0] when there's a new p45-example.nxs 
		assertThat(endTime.getString(), is(equalTo("2019-05-28T17:56:40.895+01:00")));

		assertThat(entry.getDefinitionScalar(), is(equalTo("NXscan")));
		
		IDataset programName = entry.getProgram_name();
		assertTrue(programName instanceof StringDataset);
		assertThat(programName.getString(), is(equalTo("gda")));
		
		DataNode programNameDataNode = entry.getDataNode(NXentry.NX_PROGRAM_NAME);
		assertThat(entry.getProgram_nameAttributeVersion(), is(equalTo("9.13")));
		assertTrue(programNameDataNode.getAttribute(NXentry.NX_PROGRAM_NAME_ATTRIBUTE_VERSION).getValue() instanceof StringDataset);
		assertThat(entry.getProgram_nameAttributeConfiguration(), is(equalTo("dummy")));
		
		NXinstrument instrument = entry.getInstrument();
		assertThat(instrument, is(notNullValue()));
		
		NXdetector detector = instrument.getDetector();
		assertThat(detector, is(notNullValue()));
		assertThat(detector, is(sameInstance(getNode(root, "/entry/instrument/mandelbrot"))));
		DataNode detectorDataNode = detector.getDataNode(NXdetector.NX_DATA);
		assertThat(detectorDataNode, is(notNullValue()));
		IDataset detectorData = detector.getData();
		assertThat(detectorData, is(notNullValue()));
		
		NXsample sample = entry.getSample();
		assertThat(sample, is(notNullValue()));
		NXbeam beam = sample.getBeam();
		assertThat(beam, is(notNullValue()));
		IDataset dataset = beam.getDataset("extent");
		assertThat(dataset.getDouble(), is(closeTo(0.1, 1e-15)));
		assertThat(beam.getDataNode("extent"), is(sameInstance(getNode(root, "/entry/sample/beam/extent"))));
		
		NXmonitor monitor = entry.getMonitor("somemonitor"); // a per-point monitor
		assertThat(monitor, is(notNullValue()));
		assertThat(monitor.getData(), is(notNullValue()));
		assertThat(monitor.getDataNode(NXmonitor.NX_DATA),
				is(sameInstance(getNode(root, "/entry/instrument/stagex/value"))));
		
		NXdata nxData = entry.getData();
		assertThat(nxData, is(notNullValue()));
		assertThat(nxData.getDataNode("data"), is(sameInstance(detectorDataNode)));
	}

}
