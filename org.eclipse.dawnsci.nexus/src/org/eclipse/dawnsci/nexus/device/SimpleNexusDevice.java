package org.eclipse.dawnsci.nexus.device;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;

/**
 * 
 * 
 * @author Matthew Dickie
 *
 * @param <N>
 */
public class SimpleNexusDevice<N extends NXobject> implements INexusDevice<N> {
	
	private final NexusObjectProvider<N> nexusObjectProvider;
	
	public SimpleNexusDevice(NexusObjectProvider<N> nexusObjectProvider) {
		this.nexusObjectProvider = nexusObjectProvider;
	}

	@Override
	public String getName() {
		return nexusObjectProvider.getName();
	}

	@Override
	public NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException {
		return nexusObjectProvider;
	}
	
}