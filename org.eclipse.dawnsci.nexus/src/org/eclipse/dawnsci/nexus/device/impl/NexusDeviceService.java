package org.eclipse.dawnsci.nexus.device.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.INexusDeviceDecorator;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.device.INexusDeviceAdapterFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;

/**
 * Implementation of {@link INexusDeviceService}.
 * 
 * @author Matthew Dickie
 */
public class NexusDeviceService implements INexusDeviceService {

	private Map<String, INexusDeviceDecorator<?>> nexusDecorators = new HashMap<>();
	
	private Map<String, INexusDevice<?>> nexusDevices = new HashMap<>();
	
	public <N extends NXobject> void register(INexusDevice<N> nexusDevice) {
		if (nexusDevice.getName() == null) {
			throw new IllegalArgumentException("the nexus device name is not set");
		}
		
		if (nexusDevice instanceof INexusDeviceDecorator<?>) {
			nexusDecorators.put(nexusDevice.getName(), (INexusDeviceDecorator<?>) nexusDevice);
		} else {
			nexusDevices.put(nexusDevice.getName(), nexusDevice);
		}
	}
	
	@Override
	public boolean hasNexusDevice(String name) {
		return nexusDevices.containsKey(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <N extends NXobject> INexusDevice<N> getNexusDevice(String name) throws NexusException {
		if (hasNexusDevice(name)) {
			return (INexusDevice<N>) nexusDevices.get(name);
		}
		
		throw new NexusException("No nexus device is registered with the name: " + name);
	}
	
	@Override
	public boolean hasDecorator(String name) {
		return nexusDecorators.containsKey(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <N extends NXobject> INexusDeviceDecorator<N> getDecorator(String name) throws NexusException {
		if (hasDecorator(name)) {
			return (INexusDeviceDecorator<N>) nexusDecorators.get(name);
		}
		
		throw new NexusException("No nexus decorator is registered with the name: " + name);
	}

	@Override
	public <N extends NXobject> INexusDevice<N> decorateNexusDevice(INexusDevice<N> nexusDevice) {
		final String deviceName = nexusDevice.getName();
		if (!nexusDecorators.containsKey(deviceName)) {
			return nexusDevice;
		}
		
		// Get the existing nexus device if there is one
		@SuppressWarnings("unchecked")
		final INexusDevice<N> existingNexusDevice = (INexusDevice<N>) nexusDecorators.get(deviceName);
		if (existingNexusDevice instanceof INexusDeviceDecorator<?>) {
			// If it is a decorator, set the decorated nexus device to be the one passed in.
			((INexusDeviceDecorator<N>) existingNexusDevice).setDecorated(nexusDevice);
			return existingNexusDevice;
		}
		
		// otherwise use the passed in nexusDevice, replacing the old one in the caches 
		return nexusDevice;
	}

	@Override
	public <N extends NXobject, T> INexusDevice<N> getNexusDevice(T device) throws NexusException {
		@SuppressWarnings("unchecked")
		final INexusDeviceAdapterFactory<T> factory = (INexusDeviceAdapterFactory<T>) ServiceHolder.getNexusDeviceAdapterFactory();
		if (factory != null && factory.canAdapt(device)) {
			return factory.createNexusDevice(device);
		}
		return null;
	}
	
}
