package org.eclipse.dawnsci.nexus.device.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;

/**
 * Implementation of {@link INexusDeviceService}.
 * 
 * @author Matthew Dickie
 */
public class NexusDeviceService implements INexusDeviceService {

	private Map<String, INexusDevice<?>> nexusDevices = new HashMap<>();
	
	public <N extends NXobject> void register(INexusDevice<N> nexusDevice) {
		nexusDevices.put(nexusDevice.getName(), nexusDevice);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <N extends NXobject> INexusDevice<N> getNexusDevice(String name) {
		if (nexusDevices.containsKey(name)) {
			return (INexusDevice<N>) nexusDevices.get(name);
		}
		
		throw new IllegalArgumentException("Cannot find nexus device with name: " + name);
	}

}
