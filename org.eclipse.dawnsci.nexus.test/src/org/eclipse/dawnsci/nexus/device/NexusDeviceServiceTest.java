package org.eclipse.dawnsci.nexus.device;

import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXaperture;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.builder.AbstractNexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.device.impl.NexusDeviceService;
import org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link NexusDeviceService} and various implementations of {@link INexusDevice} that
 * allow nexus devices to be added to a scan.
 */
public class NexusDeviceServiceTest {
	
	public static class TestPositioner extends AbstractNexusObjectProvider<NXpositioner> {
		
		public TestPositioner() {
			super("testPositioner", NexusBaseClass.NX_POSITIONER, NXdetector.NX_DATA);
		}
		
		protected NXpositioner createNexusObject() {
			NXpositioner positioner = NexusNodeFactory.createNXpositioner();
			positioner.setValueScalar(2.34);
			return positioner;
		}
		
	}
	
	private INexusDeviceService nexusDeviceService;
	
	private NexusDeviceFileBuilder nexusDeviceBuilder;
	
	@Before
	public void setUp() {
		nexusDeviceService = new NexusDeviceService();
		nexusDeviceBuilder = new NexusDeviceFileBuilder(nexusDeviceService); 
	}
	
	@Test
	public void testSimpleNexusDevice() throws Exception {
		final NexusObjectProvider<NXpositioner> nexusObjectProvider = new TestPositioner();
		final SimpleNexusDevice<NXpositioner> nexusDevice = new SimpleNexusDevice<NXpositioner>(nexusObjectProvider); 
		((NexusDeviceService) nexusDeviceService).register(nexusDevice);
		
		assertThat(nexusDeviceService.getNexusDevice(nexusDevice.getName()), is(sameInstance(nexusDevice)));
		
		// construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner("testPositioner", positioner);
		positioner.setValueScalar(2.34);
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(nexusDevice);
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
		final NexusObjectProvider<NXpositioner> nexusObjectProvider = new TestPositioner();
		final String deviceName = nexusObjectProvider.getName();
		final SimpleNexusDevice<NXpositioner> nexusDevice = new SimpleNexusDevice<NXpositioner>(nexusObjectProvider); 
		
		final NexusObjectAppender<NXpositioner> nexusAppender = new NexusObjectAppender<NXpositioner>() {

			@Override
			protected void appendNexusObject(NXpositioner positioner) {
				positioner.setNameScalar("testPositioner");
				positioner.setAcceleration_timeScalar(10.23);
				positioner.setDescriptionScalar("description of positioner");
			}
		};
		nexusAppender.setName("testPositioner");
		
		// create the appender and register it
		((NexusDeviceService) nexusDeviceService).register(nexusAppender);
		assertThat(nexusDeviceService.getNexusDevice(deviceName), is(sameInstance(nexusAppender)));
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner(nexusObjectProvider.getName(), positioner);
		positioner.setValueScalar(2.34);
		positioner.setNameScalar("testPositioner");
		positioner.setAcceleration_timeScalar(10.23);
		positioner.setDescriptionScalar("description of positioner");
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(nexusDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testNexusObjectAppenderWith_consumerLambda() throws Exception {
		final NexusObjectProvider<NXpositioner> nexusObjectProvider = new TestPositioner();
		final String deviceName = nexusObjectProvider.getName();
		final SimpleNexusDevice<NXpositioner> nexusDevice = new SimpleNexusDevice<NXpositioner>(nexusObjectProvider); 
		
		final Consumer<NXpositioner> appenderConsumer = positioner -> {
			positioner.setNameScalar("testPositioner");
			positioner.setAcceleration_timeScalar(10.23);
			positioner.setDescriptionScalar("description of positioner");
		};
		
		// create the appender and register it
		final NexusObjectAppender<NXpositioner> nexusAppender = new NexusObjectAppender<>(deviceName, appenderConsumer);
		((NexusDeviceService) nexusDeviceService).register(nexusAppender);
		assertThat(nexusDeviceService.getNexusDevice(deviceName), is(sameInstance(nexusAppender)));
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner(nexusObjectProvider.getName(), positioner);
		positioner.setValueScalar(2.34);
		appenderConsumer.accept(positioner); // use the same Consumer on the expected tree
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(nexusDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
	@Test
	public void testNexusMetadataAppender() throws Exception {
		final NexusObjectProvider<NXpositioner> nexusObjectProvider = new TestPositioner();
		final String deviceName = nexusObjectProvider.getName();
		final SimpleNexusDevice<NXpositioner> nexusDevice = new SimpleNexusDevice<NXpositioner>(nexusObjectProvider); 
		
		final NexusMetadataAppender<NXpositioner> metadataAppender = new NexusMetadataAppender<>("testPositioner");
		final Map<String, Object> nexusMetadata = new HashMap<>();
		nexusMetadata.put(NXpositioner.NX_NAME, "testPositioner");
		nexusMetadata.put(NXpositioner.NX_ACCELERATION_TIME, 10.23);
		nexusMetadata.put(NXpositioner.NX_DESCRIPTION, "description of positioner");
		metadataAppender.setNexusMetadata(nexusMetadata);
		((NexusDeviceService) nexusDeviceService).register(metadataAppender);
		assertThat(nexusDeviceService.getNexusDevice(deviceName), is(sameInstance(metadataAppender)));
		
		// Construct the expected tree
		final TreeFile expectedTree = nexusDeviceBuilder.buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner(nexusObjectProvider.getName(), positioner);
		positioner.setValueScalar(2.34);
		positioner.setNameScalar("testPositioner");
		positioner.setAcceleration_timeScalar(10.23);
		positioner.setDescriptionScalar("description of positioner");
		
		// build the nexus tree and compare it to the expected tree
		final TreeFile actualTree = nexusDeviceBuilder.buildNexusTree(nexusDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
}
