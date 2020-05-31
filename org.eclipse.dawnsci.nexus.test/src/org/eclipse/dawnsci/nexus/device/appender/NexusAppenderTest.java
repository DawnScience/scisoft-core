package org.eclipse.dawnsci.nexus.device.appender;

import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.appender.AbstractNexusContextAppender;
import org.eclipse.dawnsci.nexus.appender.NexusMetadataAppender;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.context.NexusContextFactory;
import org.eclipse.dawnsci.nexus.context.NexusContextType;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class NexusAppenderTest {
	
	private static final String DETECTOR_GROUP_PATH = "/entry/instrument/detector";
	
	private static INexusFileFactory nexusFileFactory = new NexusFileFactoryHDF5();
	
	private static String testScratchDirectoryName;
	
	private final NexusContextType contextType;

	@Parameters(name="nexusContextType= {0}")
	public static Object[] data() {
		return new Object[] { NexusContextType.ON_DISK, NexusContextType.IN_MEMORY };
	}
	
	public NexusAppenderTest(NexusContextType contextType) {
		this.contextType = contextType;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(NexusAppenderTest.class.getCanonicalName()) + "/";
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
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
			AbstractNexusContextAppender<?> appender) throws NexusException {
		final String filePath = testScratchDirectoryName + fileName;
		final GroupNode group;
		switch (contextType) {
			case IN_MEMORY:
				final TreeFile treeFile = buildInitialTree(filePath);
				group = (GroupNode) treeFile.findNodeLink(groupPath).getDestination();
				assertThat(group, is(notNullValue()));
				appender.append(group, NexusContextFactory.createLocalNodeInMemoryContext(group));
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
	public void testAppendCustom() throws Exception {
		// Arrange
		final String fileName = "custom.nxs";
		final String localName = "mydetector";
		final String description = "A description of this detector";
		final String type = "pixel";
		final double detectorReadoutTime = 0.123;
		final String longName = "long name of detector";
		final String collectionName = "collection";
		
		final AbstractNexusContextAppender<NXdetector> appender = new AbstractNexusContextAppender<NXdetector>() {
			
			@Override
			public void append(GroupNode groupNode, NexusContext context) throws NexusException {
				context.createDataNode(groupNode, NXdetector.NX_LOCAL_NAME, localName);
				context.createDataNode(groupNode, NXdetector.NX_DESCRIPTION, description);
				context.createDataNode(groupNode, NXdetector.NX_TYPE, type);
				context.createDataNode(groupNode, NXdetector.NX_DETECTOR_READOUT_TIME, detectorReadoutTime);
				final DataNode data = groupNode.getDataNode(NXdetector.NX_DATA);
				context.createAttribute(data, NXdetector.NX_DATA_ATTRIBUTE_LONG_NAME, longName);
				final GroupNode collection = context.createGroupNode(groupNode, collectionName, NexusBaseClass.NX_COLLECTION);
				context.createDataNode(collection, "foo", "bar");
			}
		};
		
		// Act
		final TreeFile actualTree = createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
		
		// Assert
		final TreeFile expectedTree = buildInitialTree(fileName);
		final NXdetector expectedGroupNode = (NXdetector) expectedTree.findNodeLink(DETECTOR_GROUP_PATH).getDestination();
		expectedGroupNode.setLocal_nameScalar(localName);
		expectedGroupNode.setDescriptionScalar(description);
		expectedGroupNode.setTypeScalar(type);
		expectedGroupNode.setDetector_readout_timeScalar(detectorReadoutTime);
		expectedGroupNode.setAttribute(NXdetector.NX_DATA, NXdetector.NX_DATA_ATTRIBUTE_LONG_NAME, longName);
		final NXcollection collection = NexusNodeFactory.createNXcollection();
		collection.setField("foo", "bar");
		expectedGroupNode.setCollection(collectionName, collection);
		
		assertNexusTreesEqual(expectedTree, actualTree);		
	}

	@Test
	public void testAppendNexusMetadata() throws Exception {
		// Arrange
		final String fileName = "metadata.nxs";
		final NexusMetadataAppender<NXdetector> appender = new NexusMetadataAppender<>("detector");
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put(NXdetector.NX_LOCAL_NAME, "mydetector");
		metadata.put(NXdetector.NX_DETECTOR_NUMBER, 2);
		metadata.put(NXdetector.NX_DESCRIPTION, "A description of my detector");
		metadata.put(NXdetector.NX_TYPE, "pixel");
		metadata.put(NXdetector.NX_DETECTOR_READOUT_TIME, 0.123);
		appender.setNexusMetadata(metadata);
		
		// Act
		final TreeFile actualTree = createTreeAndAppend(fileName, DETECTOR_GROUP_PATH, appender);
		
		// Assert
		final TreeFile expectedTree = buildInitialTree(fileName);
		final NXdetector expectedGroupNode = (NXdetector) expectedTree.findNodeLink(DETECTOR_GROUP_PATH).getDestination();
		for (Map.Entry<String, Object> entry : metadata.entrySet()) {
			expectedGroupNode.setField(entry.getKey(), entry.getValue());
		}
		
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
}
