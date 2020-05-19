package org.eclipse.dawnsci.nexus.device;

import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

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
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.builder.NexusObjectWrapper;
import org.eclipse.dawnsci.nexus.device.impl.NexusDeviceService;
import org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.eclipse.january.dataset.DatasetFactory;
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
		final NexusMetadataDevice<NXaperture> nexusDevice = new NexusMetadataDevice<>("aperture", NexusBaseClass.NX_APERTURE);
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
	public void testNexusMetadataAppender() throws Exception {
		final NexusMetadataAppender<NXpositioner> metadataAppender = new NexusMetadataAppender<>("testPositioner");
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
	public void testGroupCopyAppender() throws Exception {
		// create the external file
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put(NXdetector.NX_DETECTOR_NUMBER, 2);
		metadata.put(NXdetector.NX_X_PIXEL_OFFSET, 125);
		metadata.put(NXdetector.NX_Y_PIXEL_OFFSET, 32);
		metadata.put(NXdetector.NX_LAYOUT, "area");
		metadata.put(NXdetector.NX_SATURATION_VALUE, 999.99);
		final NXcollection metadataCollection = NexusNodeFactory.createNXcollection();
		for (Map.Entry<String, Object> metadataEntry : metadata.entrySet()) {
			metadataCollection.setField(metadataEntry.getKey(), metadataEntry.getValue());
		}
		// check that child groups are also copied
		final NXdata calibrationDataGroup = NexusNodeFactory.createNXdata();
		final String calibrationDataName = "calibration_data";
		calibrationDataGroup.setAttribute(null, "signal", NXdata.NX_DATA);
		calibrationDataGroup.setAttribute(null, "data_axes", DatasetFactory.createFromList(Arrays.asList("y", "x", ".", ".")));
		calibrationDataGroup.setData(Random.rand(8, 5, 64, 64));
		calibrationDataGroup.setX(DatasetFactory.createLinearSpace(DoubleDataset.class, 2.3, 5.1, 8));
		calibrationDataGroup.setAttribute(null, "x_indices", DatasetFactory.createFromObject(new int[] { 0, 1 }));
		calibrationDataGroup.setY(DatasetFactory.createLinearSpace(DoubleDataset.class, 9.3, 11.5, 5));
		calibrationDataGroup.setAttribute(null, "y_indices", DatasetFactory.createFromObject(new int[] { 0, 1 }));
		metadataCollection.addGroupNode(calibrationDataName, calibrationDataGroup);
		
		final String metadataDeviceName = "detectorMetadata";
		final INexusDevice<NXcollection> metadataDevice = createSimpleNexusDevice(metadataDeviceName, metadataCollection);
		
		final String externalFilePath = testScratchDirectoryName + "external.nxs";
		final TreeFile externalTreeFile = nexusDeviceBuilder.buildNexusTree(externalFilePath, metadataDevice);
		NexusTestUtils.saveNexusFile(externalTreeFile);

		// the detector
		final NXdetector nxDetector = NexusNodeFactory.createNXdetector();
		nxDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		final INexusDevice<NXdetector> detector = createSimpleNexusDevice("detector", nxDetector);
		
		// a group copy appender that copies the metadata into the detector
		final NexusGroupCopyAppender<NXdetector> groupCopyAppender = new NexusGroupCopyAppender<>();
		groupCopyAppender.setName(detector.getName());
		groupCopyAppender.setNodePath("/entry/" + metadataDeviceName);
		groupCopyAppender.setExternalFilePath(externalFilePath);
		final Set<String> excluded = new HashSet<>(Arrays.asList(NXdetector.NX_LAYOUT));
		groupCopyAppender.setExcluded(excluded);
		((NexusDeviceService) nexusDeviceService).register(groupCopyAppender);
		
		// construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXentry entry = ((NXroot) expectedTree.getGroupNode()).getEntry();
		final NXinstrument instrument = entry.getInstrument();
		
		final NXdetector expectedDetector = NexusNodeFactory.createNXdetector();
		metadata.entrySet().stream().filter(metEntry -> !excluded.contains(metEntry.getKey())).forEach(
				metEntry -> expectedDetector.setField(metEntry.getKey(), metEntry.getValue()));
		expectedDetector.initializeLazyDataset(NXdetector.NX_DATA, 2, Double.class);
		expectedDetector.addGroupNode(calibrationDataName, calibrationDataGroup);
		instrument.setDetector(detector.getName(), expectedDetector);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(detector);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
}
