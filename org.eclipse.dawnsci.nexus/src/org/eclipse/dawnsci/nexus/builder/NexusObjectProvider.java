/*-
 *******************************************************************************
 * Copyright (c) 2015 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Dickie - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.nexus.builder;

import static java.util.Collections.emptySet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXpositioner;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;
import org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder;

/**
 * Defines the interface for a class that can create a a NeXus object of a particular type.
 * (i.e. an instance of a NeXus base class).
 * For example, this interface can be implemented by classes representing devices such
 * as positioners or detectors; alternatively a wrapper class implementing this class
 * could contain objects representing such devices, this allows for greater decoupling. 
 * 
 * @param <N> a subinterface of {@link NXobject} that an object 
 */
public interface NexusObjectProvider<N extends NXobject> extends NexusEntryModification {

	/**
	 * Get the name of the provider. This is used as the name of the 
	 * NeXus object (i.e. group) in the parent group to which it is added.
	 * @return name of base
	 */
	public String getName();

	/**
	 * Return the NeXus base class enum value for the type NeXus object this
	 * provider creates.
	 * @return the {@link NexusBaseClass} for this object provider
	 */
	public NexusBaseClass getNexusBaseClass();
	
	/**
	 * Returns the NeXus object for this provider, creating it if necessary
	 * The same NeXus object must be returned each time this method is invoked
	 * @return NeXus object or <code>null</code>
	 */
	public N getNexusObject();

	/**
	 * Returns the category for this {@link NexusObjectProvider}. When adding
	 * a nexus object to a {@link NexusEntryBuilder}, the nexus object will be added
	 * to a group of this type, if one exists in the skeleton tree. For example
	 * a {@link NexusObjectProvider} that provides an {@link NXpositioner} would normally
	 * be added to the {@link NXinstrument} group, but if
	 * this method returns {@link NexusBaseClass#NX_SAMPLE}, then it will instead be added
	 * to the {@link NXsample} group.
	 * @return category for this object
	 */
	public NexusBaseClass getCategory();
	
	/**
	 * Returns the name of the collection for this {@link NexusObjectProvider}. When adding
	 * a nexus object to a {@link NexusEntryBuilder}, if this method does not return
	 * <code>null</code>, then the nexus object will be added to the {@link NXcollection} with this
	 * name within the group that it would have been added to otherwise. The collection will be
	 * created if it does not already exist.
	 * @return collection name or <code>null</code>
	 */
	public String getCollectionName();
	
	/**
	 * Returns a {@link ScanRole} defining the role of the device for this nexus object provider
	 * performs in the scan. 
	 * 
	 * This affects how the fields within the nexus object returned by
	 * {@link #getNexusObject()} are added to the {@link NXdata} group, if any. For example and {@link NXdata}
	 * group will be created for each primary field of each detector, and axis fields
	 * 
	 * The nexus writing framework already knows the {@link ScanRole} of the {@link INexusDevice} whose
	 * {@link INexusDevice#getNexusProviders(org.eclipse.dawnsci.nexus.NexusScanInfo) method returned
	 * this NexusObjectProvider, as the INexusDevices are grouped by ScanRole in the NexusScanModel.
	 * However, some INexusDevices may correspond to multiple devices with different roles in the scan -
	 * a malcolm device is an example of this. Where this is the case, this method can returns a non-null
	 * value to override the scan ro  
	 *  
	 * @return the role of this device in the scan, where <code>null</code> tells the nexus writing framework
	 *     to use the scan role of the {@link INexusDevice} in the NexusScanModel which produced this provider 
	 */
	public ScanRole getScanRole();
	
	/**
	 * Returns the names of the external HDF5 file(s) that this device writes
	 * its data to, or <code>null</code> if none.
	 * @return name of external file, or <code>null</code>
	 */
	public Set<String> getExternalFileNames();
	
	/**
	 * Returns the rank of the external dataset with the given field name.
	 * @param fieldName field name
	 * @return rank of external dataset with given field name
	 * @throws IllegalArgumentException if no such field exists
	 */
	public int getExternalDatasetRank(String fieldName);

	// methods below this line are relevant only when adding this object to a NexusDataBuilder 
	
	/**
	 * Returns the axis data field names for this object. These are the fields
	 * that will be linked to when this this object is added to
	 * an {@link NexusDataBuilder} to construct an {@link NXdata} group.
	 * 
	 * This method should not return the names of primary data fields, nor should it
	 * return the names of data fields which should only be added to the {@link NXdata} groups
	 * for a particular primary data field (an {@link NXdata} group is added to the scan for
	 * each primary data field, as returned by {@link #getPrimaryDataFieldName()} and
	 * {@link #getAdditionalPrimaryDataFieldNames()}). The primary data field will also be linked
	 * to in an {@link NXdata} group, except where this device is the primary device for the scan
	 * where it is not linked to in the {@link NXdata} groups for additional primary data fields.
	 *  
	 * @return name of data fields for this object
	 */
	public List<String> getAxisDataFieldNames();
	
	/**
	 * Returns the name of the default data field to write to within the nexus object.
	 * If this object is added as the primary device to an {@link NXdata} group, then
	 * this is the field name of the default field, i.e. the field referred to by
	 * the <code>@signal</code> attribute.
	 * <p>
	 * If additional {@link NXdata} groups should be created for other fields in this scan,
	 * then the names of these fields should be returned by {@link #getAdditionalPrimaryDataFieldNames()}.
	 *
	 * @return default data field name, this cannot be <code>null</code>
	 */
	public String getPrimaryDataFieldName();
	
	/**
	 * Returns the names of any additional primary data fields for this device.
	 * This method indicates that if this device is to be used to create an
	 * {@link NXdata} with the field {@link #getPrimaryDataFieldName()}
	 * as the default (signal field), then additional {@link NXdata} groups
	 * should be created for each of these fields.
	 * 
	 * @return additional primary data field names
	 */
	public List<String> getAdditionalPrimaryDataFieldNames();
	
	/**
	 * Returns the names of any data fields that are axes for the primary data field with
	 * the given name. These data fields are those that should be added to the {@link NXdata}
	 * group for the primary data field with the given name (and not those for other primary
	 * data fields), in addition to the data fields returned by {@link #getAxisDataFieldNames()}
	 * @param primaryDataFieldName primary data field name
	 * @return names of data fields
	 */
	public List<String> getAxisDataFieldsForPrimaryDataField(String primaryDataFieldName);
	
	/**
	 * Returns the name of the default axis field for this nexus object, if any.
	 * If this object is added as a device to an {@link NXdata} then this
	 * is the field that will be added as a default axis of the <code>@signal</code> field,
	 * for example for a positioner this may be the demand field.
	 * @return name of demand field, or <code>null</code> if none.
	 */
	public String getDefaultAxisDataFieldName();
	
	/**
	 * Returns the dimension of the given primary data field for which the data field with the
	 * given name is a default axis, or <code>null</code> if this field does
	 * not provide a default axis to the default data field.
	 * This method is required only when this device provides the default data field
	 * of an {@link NXdata} group (i.e. that referred to by the <code>@signal</code> attribute),
	 * and additional data fields within this device provide default axis for that data field
	 * @param primaryDataFieldName name of primary data field
	 * @param axisDataFieldName axis data field name
	 * @return dimension of the default data field for which the field with the
	 *   given name provides a default axis, or <code>null</code> if none
	 */
	public Integer getDefaultAxisDimension(String primaryDataFieldName, String axisDataFieldName);

	/**
	 * Returns the dimension mappings between the data field and
	 * the primary data field with the given names.
	 * This method is required only when this device provides the default data
	 * field of an {@link NXdata} group (i.e. that referred to by the <code>signal</code>
	 * attribute), and additional data fields within that 
	 * and the default data field of this device.
	 * @param primaryDataFieldName field name
	 * @param axisDataFieldName axis data field name
	 * @return dimension mappings between the field with the given name and the
	 *    default data field
	 */
	public int[] getDimensionMappings(String primaryDataFieldName, String axisDataFieldName);
	
	/**
	 * Returns the names of any auxiliary data groups to create. An auxiliary data group is
	 * one that groups a number of data group with the same rank together. A typical use
	 * case would be for statistics about the main data field for the device,
	 * such as min, max, mean, sum, etc.
	 * @return names of auxiliary data groups
	 */
	public default Set<String> getAuxiliaryDataGroupNames() {
		return emptySet();
	}
	
	/**
	 * Returns the names of the data fields for the given auxiliary data group as a
	 * {@link List}. These are the plottable fields for that data group.
	 * In the typical use case of an auxiliary data group for statistics.
	 * @param dataGroupName name of the auxiliary data group
	 * @return list of names of data fields for the auxiliary data group with the given name,
	 * 		or <code>null</code> if there is no such auxiliary data group
	 */
	public default List<String> getAuxiliaryDataFieldNames(String dataGroupName) {
		return null; // NOSONAR return null instead of empty list
	}
		
	/**
	 * Returns whether the name of this device name (as returned by {@link #getName()}
	 * should be used in the names of the links created within {@link NXdata} groups to
	 * the fields within the nexus object for this instance. If this is not the
	 * case, the links to the fields created in the {@link NXdata} group will have
	 * the same name as they do in the nexus object for this instance (i.e.
	 * that returned by {@link #getNexusObject()}.
	 * <p>
	 * If this method returns an empty {@link Optional} then the default behaviour
	 * is to use the device name in the names of the links in the {@link NXdata}
	 * for this device for the axes device for the {@link NXdata} group (often these
	 * are the devices being scanned, whose nexus object is an {@link NXpositioner}),
	 * but not for the primary data device
	 * <p>    
	 * If the device name is to be used in the name of the links created then:
	 * <ul>
	 * <li>If a single field from this device is added to the {@link NXdata} group
	 * then that link will simply have the same name as this device.</li>
	 * <li>If multiple fields from this devices are added to an {@link NXdata} group
	 * then name of the link created within the {@link NXdata} group will be the
	 * name of the device, followed by an underscore, followed by the name of the
	 * field within the nexus object for this instance.   
	 * </ul>
	 * 
	 * @return an {@link Optional} containing {@link Boolean#TRUE} to use the device
	 *     name when linking fields in an {@link NXdata} group, {@link Boolean#FALSE}
	 *     to not use the device name, and an empty optional to use the default behaviour,
	 *     i.e. use the device name for axis devices only  
	 */
	public Optional<Boolean> isUseDeviceNameInNXdata();
	
	/**
	 * Returns the value of the application defined property of this object with the given name.
	 * This allows arbitrary application or implementation specific information to be
	 * associated with this object.
	 * <p>
	 * Note that these properties should not be confused with the fields of the
	 * NeXus object returned by {@link #getNexusObject()}.
	 * 
	 * @param propertyName name of property
	 * @return the value of the property with the given name, or <code>null</code> if no property
	 *   with the given name is set
	 */
	public Object getPropertyValue(String propertyName);
	
}
