package org.eclipse.dawnsci.nexus;

import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;

/**
 * An {@link INexusDevice} that wraps another {@link INexusDevice}. Implementations  pluggable,
 * i.e. it is possible to call {@link #setDecorated(INexusDevice)} again, changing the nexus
 * device that is being wrapped.
 * 
 * @author Matthew Dickie
 *
 * @param <N> the subclass of {@link NXobject} returned by the nexus device and decorator
 */
public interface INexusDeviceDecorator<N extends NXobject> extends INexusDevice<N> {
	
	/**
	 * Set the decorated nexus device. This method should not be called by client code. It is called
	 * by the implementation of {@link INexusDeviceService#getNexusDevice(INexusDevice)} is called
	 * with an {@link INexusDevice} with a name for which this {@link INexusDeviceDecorator} has been
	 * registered with the {@link INexusDeviceService} by calling {@link INexusDeviceService#register(INexusDevice)}.
	 * @param decorated
	 */
	public void setDecorated(INexusDevice<N> decorated);
	
	/**
	 * Returns the currently decorated {@link INexusDevice}, or <code>null</code> if one has not yet been set
	 * @return currently decorated {@link INexusDevice}
	 */
	public INexusDevice<N> getDecorated();
	
	/**
	 * Returns the name of the decorator. Note that this decorator must have the same name as any
	 * device is it intended to wrap. 
	 */
	public String getName();

	/**
	 * Returns the decorated {@link NexusObjectProvider}. 
	 */
	@Override
	public NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException;

	/**
	 * Returns the {@link CustomNexusEntryModification}, if there is one. By default, this
	 * returns the custom modification of the 
	 * This method can be overriden if required, but this is an unlikely use case
	 */
	@Override
	default CustomNexusEntryModification getCustomNexusModification() {
		return INexusDevice.super.getCustomNexusModification();
	} 

}
