package org.eclipse.dawnsci.nexus.device;

import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;
import static org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder.buildEmptyTree;
import static org.eclipse.dawnsci.nexus.test.utilities.NexusDeviceFileBuilder.buildNexusTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.builder.AbstractNexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.device.impl.NexusDeviceService;
import org.junit.BeforeClass;
import org.junit.Test;

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
	
	private static INexusDeviceService nexusDeviceService;
	
	@BeforeClass
	public static void setUp() {
		nexusDeviceService = new NexusDeviceService();
	}
	
	@Test
	public void testSimpleNexusDevice() throws Exception {
		final NexusObjectProvider<NXpositioner> nexusObjectProvider = new TestPositioner();
		final SimpleNexusDevice<NXpositioner> nexusDevice = new SimpleNexusDevice<NXpositioner>(nexusObjectProvider); 
		((NexusDeviceService) nexusDeviceService).register(nexusDevice);
		
		assertThat(nexusDeviceService.getNexusDevice(nexusDevice.getName()), is(sameInstance(nexusDevice)));
		
		// construct the expected tree
		final TreeFile expectedTree = buildEmptyTree();
		final NXinstrument instrument = ((NXroot) expectedTree.getGroupNode()).getEntry().getInstrument();
		final NXpositioner positioner = NexusNodeFactory.createNXpositioner();
		instrument.setPositioner("testPositioner", positioner);
		positioner.setValueScalar(2.34);
		
		final TreeFile actualTree = buildNexusTree(nexusDevice);
		assertNexusTreesEqual(expectedTree, actualTree);
	}
	
}
