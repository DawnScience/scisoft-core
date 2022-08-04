/*-
 * Copyright © 2020 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package org.eclipse.dawnsci.nexus.device;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;

/**
 * Instances of classes that implement this interface can adapt (i.e. wrap) a device in
 * an object that implements {@link INexusDevice}. This m
 * created for the device and added to the nexus file for the scan
 *
 * @param <T>
 */
public interface INexusDeviceAdapterFactory<T> {

	/**
	 * Returns whether this adapter factory can create an adapter to {@link INexusDevice}
	 * for the given device object.
	 * @param device the device object to adapt
	 * @return <code>true</code> if an adapter can be created for the given device object,
	 * 		<code>false</code> otherwise
	 */
	public boolean canAdapt(Object device);

	/**
	 * Creates and returns an adapter implementing {@link INexusDevice} for the given device object,
	 * @param <N> the type of nexus object returned by the {@link INexusDevice}, a subinterface
	 * 		of {@link NXobject}, e.g. {@link NXdetector}. This method returns <code>null</code> in
	 * 		this method is called with an device object that cannot be adapted by this adapter factory,
	 * 		where {@link #canAdapt(Object)} would return <code>false</code>.
	 * @param device the device object to adapt
	 * @return an {@link INexusDevice} adapting the given device object
	 * @throws NexusException if an error occurs creating the adapter
	 */
	public <N extends NXobject> INexusDevice<N> createNexusDevice(T device) throws NexusException;
	
}
