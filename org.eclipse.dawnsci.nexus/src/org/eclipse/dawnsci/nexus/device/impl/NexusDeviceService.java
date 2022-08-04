package org.eclipse.dawnsci.nexus.device.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.INexusDeviceDecorator;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.device.INexusDeviceAdapterFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link INexusDeviceService}.
 * 
 * @author Matthew Dickie
 */
public class NexusDeviceService implements INexusDeviceService {
	
	private static final Logger logger = LoggerFactory.getLogger(NexusDeviceService.class);

	private Map<String, INexusDeviceDecorator<?>> nexusDecorators = new HashMap<>();
	
	private Map<String, INexusDevice<?>> nexusDevices = new HashMap<>();
	
	public <N extends NXobject> void register(INexusDevice<N> nexusDevice) {
		final String deviceName = nexusDevice.getName();
		Objects.requireNonNull(deviceName, "The nexus device name is not set");
		
		final boolean overwritten;
		if (nexusDevice instanceof INexusDeviceDecorator<?>) {
			overwritten = nexusDecorators.put(deviceName, (INexusDeviceDecorator<?>) nexusDevice) != null;
		} else {
			overwritten = nexusDevices.put(deviceName, nexusDevice) != null;
		}
		
		if (overwritten) {
			// log a warning if there was already a nexus device or appender with the given name
			logger.warn("Registered {} with name {}, overwriting previous device", nexusDevice.getClass().getSimpleName(), deviceName);
		} else {
			logger.debug("Registered {} with name {}", nexusDevice.getClass().getSimpleName(), deviceName);
		}
	}
	
	public <N extends NXobject> void unregister(INexusDevice<N> nexusDevice) {
		final String deviceName = nexusDevice.getName();
		Objects.requireNonNull(deviceName, "the nexus device name is not set");

		final boolean removed = nexusDevices.remove(deviceName, nexusDevice);
		if (removed) {
			logger.debug("Unregistered {} with name {}", nexusDevice.getClass().getSimpleName(), deviceName);
		} else {
			logger.warn("The registered nexus device with the name {} is not the given {}", deviceName, nexusDevice.getClass().getSimpleName());
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
		final INexusDevice<N> decorator = (INexusDevice<N>) nexusDecorators.get(deviceName);
		((INexusDeviceDecorator<N>) decorator).setDecorated(nexusDevice);
		
		return decorator;
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
