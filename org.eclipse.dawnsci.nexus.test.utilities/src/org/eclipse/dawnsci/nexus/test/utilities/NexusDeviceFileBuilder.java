package org.eclipse.dawnsci.nexus.test.utilities;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFactory;
import org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.impl.DefaultNexusBuilderFactory;

/**
 * A utility class to build a nexus tree structure from a set of INexusDevices.
 * This is a much simple version of how NexusScanFileBuilder does. In particular it does not build {@link NXdata}
 * groups and does not save the file structure to disk. This is not necessary as it is possible
 * to validate that the structure is created without this.
 * 
 * TODO: if and when DAQ-2392 is completed, to move that nexus building logic to org.eclipse.dawnsci.nexus,
 * hidden behind service, this service should be used directly.
 * @author mpdickie
 */
public class NexusDeviceFileBuilder {
	
	private NexusDeviceFileBuilder() {
		// private constructor to prevent instatiation
	}
	
	public static TreeFile buildEmptyTree() throws NexusException {
		return buildNexusTree();
	}
	
	public static TreeFile buildNexusTree(INexusDevice<?>... nexusDevices) throws NexusException {
		final NexusScanInfo scanInfo = new NexusScanInfo(Arrays.asList("stagey", "stagex"));
		scanInfo.setRank(2);
		// note: we don't save the nexus tree to file as we can check just structure of the nexus tree 
		final NexusBuilderFactory factory = new DefaultNexusBuilderFactory();
		final NexusFileBuilder fileBuilder = factory.newNexusFileBuilder("text.nxs");
		final NexusEntryBuilder entryBuilder = fileBuilder.newEntry();
		entryBuilder.addDefaultGroups();
		for (INexusDevice<?> nexusDevice : nexusDevices) {
			addNexusDevice(entryBuilder, nexusDevice, scanInfo);
		}
		return fileBuilder.getNexusTree();
	}
	
	private static void addNexusDevice(NexusEntryBuilder entryBuilder, INexusDevice<?> nexusDevice, NexusScanInfo scanInfo) throws NexusException {
		final NexusObjectProvider<?> nexusObjectProvider = nexusDevice.getNexusProvider(scanInfo);
		if (nexusObjectProvider != null) {
			entryBuilder.add(nexusObjectProvider);
		}
		final CustomNexusEntryModification customNexusEntryModification = nexusDevice.getCustomNexusModification();
		if (customNexusEntryModification != null) {
			entryBuilder.modifyEntry(customNexusEntryModification);
		}
	}
	


}
