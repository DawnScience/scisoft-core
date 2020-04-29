package org.eclipse.dawnsci.nexus.device;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.january.INameable;

/**
 * A service to store and retrieve nexus devices. This service is not necessary if devices in a scan
 * directly implement {@link INexusDevice}. It is most useful for nexus devices that provide
 * metadata in a scan.
 * 
 * @author Matthew Dickie 
 */
public interface INexusDeviceService {
	
	/**
	 * Registers the given nexus device with this service.
	 * @param <N> subclass of {@link NXobject} created by this device
	 * @param nexusDevice device with the given name
	 */
	public <N extends NXobject> void register(INexusDevice<N> nexusDevice);

	/**
	 * Gets the nexus device with the given name name.
	 * @param <N> subclass of {@link NXobject} created by this device
	 * @param name name
	 * @return the nexus device with the given name
	 */
	public <N extends NXobject> INexusDevice<N> getNexusDevice(String name);
	
}
