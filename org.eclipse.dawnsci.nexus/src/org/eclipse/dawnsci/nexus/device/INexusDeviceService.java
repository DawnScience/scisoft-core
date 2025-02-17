package org.eclipse.dawnsci.nexus.device;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.INexusDeviceDecorator;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;

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
	 * @param nexusDevice the nexus device to register
	 * @param <N> subclass of {@link NXobject} created by this device
	 */
	public <N extends NXobject> void register(INexusDevice<N> nexusDevice);
	
	/**
	 * Unregisters the given nexus device from this service.
	 * @param nexusDevice the nexus device to unregister
	 * @param <N> subclass of {@link NXobject} created by this device
	 */
	public <N extends NXobject> void unregister(INexusDevice<N> nexusDevice);
	/**
	 * Returns whether a nexus device is registered with the given name. 
	 * @param name name
	 * @return <code>true</code> if a nexus device with the given name is registered,
	 *     <code>false</code> otherwise
	 */
	public boolean hasNexusDevice(String name);

	/**
	 * Gets the {@link INexusDevice} with the given name.
	 * @param name name
	 * @param <N> subclass of {@link NXobject} created by this device
	 * @return the nexus device with the given name
	 * @throws NexusException if there is no nexus device with the given name
	 */
	public <N extends NXobject> INexusDevice<N> getNexusDevice(String name) throws NexusException;
	
	/**
	 * Gets the {@link INexusDevice} for the given object, if possible, otherwise <code>null</code>.
	 * @param device the device to get the {@link INexusDevice} for
	 * @param <N> subclass of {@link NXobject} created by this device
	 * @param scanRole the role the device is taking part in the scan
	 * @return the nexus device with the given name, or <code>null</code> if no nexus device can be created
	 * @throws NexusException 
	 */
	public <N extends NXobject, T> INexusDevice<N> getNexusDevice(T device, ScanRole scanRole) throws NexusException;
	
	/**
	 * Returns whether a {@link INexusDeviceDecorator} is registered with the given name.
	 * @param name name
	 * @return <code>true</code> if a decorator is registered with the given name,
	 * 		<code>false</code> otherwise
	 */
	public boolean hasDecorator(String name);
	
	/**
	 * Returns the decorator for the given name, if one is registered. This can be used
	 * to query or update the decorators properties. It should not be used to decorate a
	 * nexus device as the method {@link #decorateNexusDevice(INexusDevice)} is provided
	 * for that purpose.
	 * @param <N> subclass of {@link NXobject} created by the returned decorator
	 * @param the decorator for the given name
	 * @return the decorator for the given name 
	 * @throws NexusException if no such decorator exists
	 */
	public <N extends NXobject> INexusDeviceDecorator<N> getDecorator(String name) throws NexusException;
	
	/**
	 * Returns the decorated nexus device for the given nexus device if one is registered,
	 * otherwise returns the given nexus device as is. The decorator should be an
	 * instance of {@link INexusDeviceDecorator}. It will be looked up by name
	 * and its {@link INexusDeviceDecorator#setDecorated(INexusDevice)} called with the
	 * given nexus device.
	 *  
	 * @param <N> subclass of {@link NXobject} created by the given device, and the decorator (if applicable)
	 * @param nexusDevice the nexus device to decorate, if a decorator is registered
	 * @return the decorated nexus device
	 */
	public <N extends NXobject> INexusDevice<N> decorateNexusDevice(INexusDevice<N> nexusDevice);

}
