package org.eclipse.dawnsci.nexus.device;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;

/**
 * A simple nexus device that always returns the same {@link NexusObjectProvider}. This class is useful when
 * you have an {@link NexusObjectProvider} and want to use an API that expects an {@link INexusDevice}.
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