/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.nexus.builder.AbstractNexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.january.dataset.LazyDataset;

/**
 * Any device which can write NeXus should implement this interface.
 * 
 * This can be done easily by extending {@link AbstractNexusObjectProvider}
 * 
 * @param <N> the type of nexus object to be created, a sub-interface of {@link NXobject},
 *   e.g. {@link NXdetector}
 *  
 * @author Matthew Gerring, Matthew Dickie
 *
 */
public interface INexusDevice<N extends NXobject> {
	
	/**
	 * Returns the name of this nexus device.
	 * @return name of nexus device
	 */
	public String getName();
	
	/**
	 * Returns the object provider required for writing correct NeXus files.
	 * 
	 * <p>
	 * In this method you should prepare the {@link LazyDataset}s the device
	 * will fill during the scan. You can also write out static device metadata,
	 * which will not change during the scan.
	 * </p>
	 * <p>
	 * Use the methods on {@link NexusNodeFactory} to create a NeXus object which
	 * matches the device e.g. a {@link NXdetector}.
	 * <code>final NXdetector detector = NexusNodeFactory.createNXdetector();</code>
	 * </p>
	 * <p>
	 * On the detector object you can create {@link LazyDataset}s and keep
	 * references which you can later use during the scan to write data. e.g.
	 * <code>imageData = detector.initializeLazyDataset(NXdetector.NX_DATA, info.getRank() + 2, Double.class);</code>
	 * You should also set chunking on the {@link LazyDataset}s you create e.g. <code>imageData.setChunking(info.createChunk(detectorXSize, detectorYSize));</code>.
	 * </p>
	 * In this method you can also write static metadata such as the detector
	 * exposure e.g.
	 * <code>detector.setField("exposure_time", model.getExposure());</code>. Or
	 * static datasets such as the image axis data
	 * <code>detector.setDataset("image_x_axis", DatasetFactory.createLinearSpace(DoubleDataset.class, minX, maxX, xPoints));</code>
	 * For fields that are defined in the NXDL base class definition for the
	 * returned nexus object, a setXXX or setXXXScalar method may be used as
	 * appropriate, e.g. <code>detector.setLocalName(DatasetFactory.createFromObject("my detector"));</code>
	 * or <code>detector.setLocalNameScalar("my detector");</code>
	 * <p>
	 * If this device is a 'metadata scannable', then the device should write
	 * its data at this point directly into the returned nexus object. This can be
	 * done with the {@link NXobject#setField(String, Object)} method, or the
	 * <code>setXXXScalar</code> methods for fields defined in the appropriate 
	 * NXDL base class definition.
	 * <p>
	 * The default implementation of this method throws {@link UnsupportedOperationException}.
	 * One of either this method or {@link #getNexusProviders(NexusScanInfo)} must be overridden
	 * to create and return the nexus object(s) for this {@link INexusDevice}.
	 * 
	 * @param info information about the scan which can be useful when creating
	 *            datasets e.g. <code>info.getRank()</code>
	 * @return The {@link NXobject} created using the <code>nodeFactory</code>
	 *         to represent this device
	 * @throws NexusException if the nexus object could not be created for any reason
	 */
	public default NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException {
		throw new UnsupportedOperationException("Not implemented"); // override either this method or getNexusProviders
	}
	
	/**
	 * Returns the object providers required for writing correct NeXus files.
	 * Implement this method if your device needs to create multiple nexus objects.
	 * These can implement {@link NexusObjectProvider}
	 * <p>
	 * The default implementation of this method calls {@link #getNexusProvider(NexusScanInfo)} and
	 * returns a {@link List} whose sole element is the {@link NexusObjectProvider} returned from
	 * that method. One of either this method or {@link #getNexusProviders(NexusScanInfo)} must be overridden
	 * to create and return the nexus object(s) for this {@link INexusDevice}.
	 * 
	 * @param info information about the scan which can be useful when creating
	 *            datasets e.g. <code>info.getRank()</code>
	 * @return The {@link NXobject} created using the <code>nodeFactory</code>
	 *         to represent this device
	 * @throws NexusException if the nexus object could not be created for any reason
	 */
	public default List<NexusObjectProvider<?>> getNexusProviders(NexusScanInfo info) throws NexusException {
		return Arrays.asList(getNexusProvider(info)); // override either this method or getNexusProvider
	}
	
	/**
	 * Returns an object that performs a custom modification to
	 * an {@link NXentry}.
	 * <p>
	 * <em>NOTE: Use this method with caution as it can be used to break the central
	 * design concept of the new Nexus writing framework, namely that the nexus framework itself
	 * knows where to put the nexus groups for devices and build any required {@link NXdata} groups.
	 * </em> It is currently used by the new Nexus framework to partially support legacy GDA8 spring
	 * configurations, in particular the 'locationmap'.
	 * <p>
	 * The nexus framework will call this method after {@link #createNexusObject(NexusNodeFactory, NexusScanInfo)},
	 * so this method create links to nodes created in that method if appropriate.
	 * <p>
	 * The easiest way to implement this method is to make this object itself also implement
	 * {@link CustomNexusEntryModification}. This method can then be overridden to simply
	 * return <code>this</code>.
	 * 
	 * @return a {@link CustomNexusEntryModification} that makes a custom modification,
	 *    or <code>null</code> if this device should not make custom modifications
	 */
	public default CustomNexusEntryModification getCustomNexusModification() {
		return null;
	}
	
	/**
	 * Registers this {@link INexusDevice} with the {@link INexusDeviceService}. Clients may use this to register
	 * {@link INexusDevice}s to be looked up later by name. Note that this is not necessary for most devices,
	 * if they can be found some other way. It is however nec for {@link INexusDeviceDecorator}s to call this 
	 * method in order to decorate a device with the same name when
	 * {@link INexusDeviceService#getNexusDevice(INexusDevice)} is called, for example a device that adds metadata. 
	 */
	public default void register() {
		ServiceHolder.getNexusDeviceService().register(this);
	}
	
}
