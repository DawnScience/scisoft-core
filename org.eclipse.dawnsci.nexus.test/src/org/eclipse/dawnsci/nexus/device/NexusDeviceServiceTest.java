package org.eclipse.dawnsci.nexus.device;

import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.appender.CompoundNexusContextAppender;
import org.eclipse.dawnsci.nexus.appender.INexusContextAppender;
import org.eclipse.dawnsci.nexus.appender.NexusNodeCopyAppender;
import org.eclipse.dawnsci.nexus.appender.SimpleNexusMetadataAppender;
import org.eclipse.dawnsci.nexus.appender.NexusObjectAppender;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectWrapper;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.device.impl.NexusDeviceService;
import org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link NexusDeviceService} and various implementations of {@link INexusDevice} that
 * allow nexus devices to be added to a scan.
 */
public class NexusDeviceServiceTest {
	
	private String testScratchDirectoryName;
	
	private INexusDeviceService nexusDeviceService;
	
	private NexusDeviceFileBuilder nexusDeviceBuilder;
	
	private INexusDevice<NXpositioner> testPositioner;
	
	@Before
	public void setUp() throws Exception {
		nexusDeviceService = new NexusDeviceService();
		ServiceHolder serviceHolder = new ServiceHolder();
		serviceHolder.setNexusFileFactory(new NexusFileFactoryHDF5());
		serviceHolder.setNexusDeviceService(nexusDeviceService);
		nexusDeviceBuilder = new NexusDeviceFileBuilder(nexusDeviceService);
		
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		positioner.setValueScalar(2.34);
		testPositioner = createSimpleNexusDevice("testPositioner", positioner);
		((NexusDeviceService) nexusDeviceService).register(testPositioner);
		assertThat(nexusDeviceService.getNexusDevice(testPositioner.getName()), is(sameInstance(testPositioner)));
		
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(NexusDeviceServiceTest.class.getCanonicalName()) + "/";
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}
	
	private <N extends NXobject> INexusDevice<N> createSimpleNexusDevice(String name, N nexusObject) {
		return new SimpleNexusDevice<N>(new NexusObjectWrapper<N>(name, nexusObject));
	}
	
	@Test
	public void testSimpleNexusDevice() throws Exception {
		// construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner("testPositioner", positioner);
		positioner.setValueScalar(2.34);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(testPositioner);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testNexusMetadataDevice() throws Exception {
		final SimpleNexusMetadataDevice<NXaperture> nexusDevice = new SimpleNexusMetadataDevice<>("aperture", NexusBaseClass.NX_APERTURE);
		final Map<String, Object> nexusMetadata = new HashMap<String, Object>();
		nexusMetadata.put(NXaperture.NX_MATERIAL, "cobalt");
		nexusMetadata.put(NXaperture.NX_DESCRIPTION, "description of aperture");
		nexusDevice.setNexusMetadata(nexusMetadata);
		
		((NexusDeviceService) nexusDeviceService).register(nexusDevice);
		assertThat(nexusDeviceService.getNexusDevice(nexusDevice.getName()), is(sameInstance(nexusDevice)));
		
		// construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXaperture aperture = NexusNodeFactory.createNXaperture();
		instrument.setAperture("aperture", aperture);
		aperture.setMaterialScalar("cobalt");
		aperture.setDescriptionScalar("description of aperture");
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(nexusDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testNexusObjectAppender_subclass() throws Exception {
		final NexusObjectAppender<NXpositioner> nexusAppender = new NexusObjectAppender<NXpositioner>() {

			@Override
			protected void appendNexusObject(NXpositioner positioner) {
				positioner.setNameScalar(testPositioner.getName());
				positioner.setAcceleration_timeScalar(10.23);
				positioner.setDescriptionScalar("description of positioner");
			}
		};
		nexusAppender.setName(testPositioner.getName());
		
		// create the appender and register it
		((NexusDeviceService) nexusDeviceService).register(nexusAppender);
		assertThat(nexusDeviceService.getDecorator(testPositioner.getName()), is(sameInstance(nexusAppender)));
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner(testPositioner.getName(), positioner);
		positioner.setValueScalar(2.34);
		positioner.setNameScalar(testPositioner.getName());
		positioner.setAcceleration_timeScalar(10.23);
		positioner.setDescriptionScalar("description of positioner");
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(testPositioner);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testNexusObjectAppenderWith_consumerLambda() throws Exception {
		final Consumer<NXpositioner> appenderConsumer = positioner -> {
			positioner.setNameScalar(testPositioner.getName());
			positioner.setAcceleration_timeScalar(10.23);
			positioner.setDescriptionScalar("description of positioner");
		};
		
		// create the appender and register it
		final NexusObjectAppender<NXpositioner> nexusAppender = new NexusObjectAppender<>(
				testPositioner.getName(), appenderConsumer);
		((NexusDeviceService) nexusDeviceService).register(nexusAppender);
		assertThat(nexusDeviceService.getDecorator(testPositioner.getName()), is(sameInstance(nexusAppender)));
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner(testPositioner.getName(), positioner);
		positioner.setValueScalar(2.34);
		appenderConsumer.accept(positioner); // use the same Consumer on the expected tree
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(testPositioner);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testSimpleNexusMetadataAppender() throws Exception {
		final SimpleNexusMetadataAppender<NXpositioner> metadataAppender = new SimpleNexusMetadataAppender<>("testPositioner");
		final Map<String, Object> nexusMetadata = new HashMap<>();
		nexusMetadata.put(NXpositioner.NX_NAME, "testPositioner");
		nexusMetadata.put(NXpositioner.NX_ACCELERATION_TIME, 10.23);
		nexusMetadata.put(NXpositioner.NX_DESCRIPTION, "description of positioner");
		metadataAppender.setNexusMetadata(nexusMetadata);
		((NexusDeviceService) nexusDeviceService).register(metadataAppender);
		assertThat(nexusDeviceService.getDecorator(testPositioner.getName()), is(sameInstance(metadataAppender)));
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner(testPositioner.getName(), positioner);
		positioner.setValueScalar(2.34);
		positioner.setNameScalar("testPositioner");
		positioner.setAcceleration_timeScalar(10.23);
		positioner.setDescriptionScalar("description of positioner");
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(testPositioner);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testNodeCopyAppender() throws Exception {
		
		/* 
		 *  Create an external file with the following structure
		 * 
		 *  calibration.nxs
		 *  
		 *  
		 *  entry
		 *  	> calibration
		 *  		> boring_group
		 *  			> boring_attribute
		 *  		> calibration_sample
		 *  			> beam
		 *  				> incident_wavelength
		 *  			> calibration_positions
		 *  				> calibrated_x_position
		 *  				> calibrated_y_position
		 *  		> calculated
		 *  			> data
		 *  			> transformations
		 *  	> instrument
		 *  	> sample
		 *  	> user
		 */
		
		NXcollection boringGroup = NexusNodeFactory.createNXcollection();
		boringGroup.setField("boring_data", 9000);
		
		NXcollection calibrationSample = NexusNodeFactory.createNXcollection();
		NXcollection beam = NexusNodeFactory.createNXcollection();
		beam.setField("incident_wavelength", 500);
		
		NXcollection calibrationPositions = NexusNodeFactory.createNXcollection();
		calibrationPositions.setField("calibrated_x_position", 24.11);
		calibrationPositions.setField("calibrated_y_position", -3.45);
		
		calibrationSample.addGroupNode("beam", beam);
		calibrationSample.addGroupNode("calibration_positions", calibrationPositions);
		
		NXdata calculated = NexusNodeFactory.createNXdata();
		DoubleDataset data = Random.rand(8, 5, 64, 64);
		calculated.setData(NXdata.NX_DATA, data);
		calculated.setField("transformations", 10);
		
		NXcollection calibration = NexusNodeFactory.createNXcollection();
		
		calibration.addGroupNode("boring_group", boringGroup);
		calibration.addGroupNode("calibration_sample", calibrationSample);
		calibration.addNode("calculated", calculated);
		
		final INexusDevice<NXcollection> calibrationDevice = createSimpleNexusDevice("calibration", calibration);
		
		final String externalFilePath = testScratchDirectoryName + "calibration.nxs";
		final TreeFile externalTreeFile = nexusDeviceBuilder.buildNexusTree(externalFilePath, calibrationDevice);
		NexusTestUtils.saveNexusFile(externalTreeFile);
		
		
		// Configure the detector
		final NXdetector nxDetector = NexusNodeFactory.createNXdetector();
		nxDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		final INexusDevice<NXdetector> detector = createSimpleNexusDevice("detector", nxDetector);		
		
		
		/* 
		 *   Configure NexusNodeCopyAppender to append to above detector's node
		 * 	 the following structure from the external calibration file
		 * 
		 * 	 detector
		 * 
		 * 		> calibration_data
		 * 			> data 							// copied from /entry/calibration/calculated/data
		 * 
		 * 		> calibration_sample				// the whole group copied from /entry/calibration/calibration_sample
		 *  		> beam
		 *  			> incident_wavelength
		 *  		> calibration_positions
		 *  			> calibrated_x_position
		 *  			> calibrated_y_position
		 *  
		 *  	> transformations					// copied from /entry/calibration/calculated/transformations
		 * 
		 */
		
		final NexusNodeCopyAppender<NXdetector> nodeCopyAppender = new NexusNodeCopyAppender<>();
		nodeCopyAppender.setName(detector.getName());
		
		nodeCopyAppender.setExternalFilePath(externalFilePath);
		
		String calibrationNode = "/entry/calibration";
		String dataNode = "/entry/calibration/calculated/data";
		String calculatedNode = "/entry/calibration/calculated";
		
		nodeCopyAppender.setNodePaths(new HashSet<>(Arrays.asList(dataNode, calibrationNode, calculatedNode)));
		
		Map<String, Set<String>> excludedNodesPerGroup = new HashMap<>();
		excludedNodesPerGroup.put(calibrationNode, new HashSet<>(Arrays.asList(
				"boring_group",
				"calculated")));
		excludedNodesPerGroup.put(calculatedNode, new HashSet<>(Arrays.asList("data")));
		nodeCopyAppender.setExcludedPerNode(excludedNodesPerGroup);
		
		Map<String, String> customTargets = new HashMap<>();
		customTargets.put(dataNode, "calibration_data");
		nodeCopyAppender.setCustomTargetPerNode(customTargets);
		
		((NexusDeviceService) nexusDeviceService).register(nodeCopyAppender);
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXentry entry = ((NXroot) expectedTree.getGroupNode()).getEntry();
		final NXinstrument instrument = entry.getInstrument();
		
		final NXdetector expectedDetector = NexusNodeFactory.createNXdetector();
		expectedDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		expectedDetector.setField("transformations", 10);
		expectedDetector.addGroupNode("calibration_sample", calibrationSample);
		
		GroupNode expectedData = NexusNodeFactory.createGroupNode();
		expectedData.addDataNode("data", calculated.getDataNode("data"));
		expectedDetector.addGroupNode("calibration_data", expectedData);
		
		instrument.setDetector(detector.getName(), expectedDetector);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(detector);		
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testCompoundNexusContextAppender() throws Exception {
		/*
		 * Since the NexusDeviceService only permits one appender per device,
		 * we can use the CompoundNexusContextAppender to register multiple appenders
		 * against a single device.
		 */
		INexusContextAppender appender1 = mock(INexusContextAppender.class);
		INexusContextAppender appender2 = mock(INexusContextAppender.class);
		
		List<INexusContextAppender> innerAppenders = Arrays.asList(appender1, appender2);
		
		// registered as the single appender for a device; contains the inner appenders
		INexusContextAppender compoundAppender = new CompoundNexusContextAppender(innerAppenders);
		
		GroupNode groupNode = mock(GroupNode.class);
		NexusContext nexusContext = mock(NexusContext.class);
		
		// the append call...
		compoundAppender.append(groupNode, nexusContext);
		
		// ...is delegated to the inner appenders
		verify(appender1).append(groupNode, nexusContext);
		verify(appender2).append(groupNode, nexusContext);
	}
	
	@Test
	public void testGetNexusDeviceFromAdapter() throws Exception {
		// a simple test to check that the NexusDeviceService delegates the the configured nexus adapter factory 
		// this factory takes an integer and return an INexusDevice which produces a NexusobjectWrapper
		// wrapping a NXpositioner whose name is "device" + value of integer
		final INexusDeviceAdapterFactory<Integer> nexusDeviceAdapterFactory = new INexusDeviceAdapterFactory<Integer>() {

			@Override
			public boolean canAdapt(Object object) {
				return object instanceof Integer && ((Integer) object).intValue() < 5;
			}

			@SuppressWarnings("unchecked")
			@Override
			public INexusDevice<NXpositioner> createNexusDevice(Integer value) throws NexusException {
				return new INexusDevice<NXpositioner>() {
					
					final String name = "device" + value;

					@Override
					public String getName() {
						return name;
					}

					@Override
					public NexusObjectProvider<NXpositioner> getNexusProvider(NexusScanInfo info) throws NexusException {
						final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
						positioner.setNameScalar(name);
						return new NexusObjectWrapper<NXpositioner>(name, positioner);
					}
				};
			}
		};
		
		new ServiceHolder().setNexusDeviceAdapterFactory(nexusDeviceAdapterFactory);
		
		for (int i = 1; i < 5; i++) {
			final String expectedName = "device" + i;
			final INexusDevice<NXpositioner> nexusDevice = nexusDeviceService.getNexusDevice(i);
			
			assertThat(nexusDevice, is(notNullValue()));
			assertThat(nexusDevice.getName(), is(equalTo(expectedName)));
			final NexusObjectProvider<NXpositioner> nexusObjectProvider = nexusDevice.getNexusProvider(null);
			assertThat(nexusObjectProvider, is(notNullValue()));
			assertThat(nexusObjectProvider.getName(), is(expectedName));
			final NXpositioner positioner = nexusObjectProvider.getNexusObject();
			assertThat(positioner, is(notNullValue()));
			assertThat(positioner.getNameScalar(), is(expectedName));
		}
		
		// check that the nexus device adapter factory is only called with valid values
		assertThat(nexusDeviceService.getNexusDevice(6), is(nullValue()));
		assertThat(nexusDeviceService.getNexusDevice(100), is(nullValue()));
		assertThat(nexusDeviceService.getNexusDevice(2.0), is(nullValue()));
		assertThat(nexusDeviceService.getNexusDevice(new Object()), is(nullValue()));
	}
	
}
