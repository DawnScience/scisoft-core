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
package org.eclipse.dawnsci.nexus.builder.data;

import static java.util.Objects.requireNonNull;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.IntStream;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.data.impl.AxisDataDeviceImpl;
import org.eclipse.dawnsci.nexus.builder.data.impl.AxisFieldModel;
import org.eclipse.dawnsci.nexus.builder.data.impl.DataDeviceImpl;
import org.eclipse.dawnsci.nexus.builder.data.impl.DataFieldModel;
import org.eclipse.dawnsci.nexus.builder.data.impl.PrimaryDataDeviceImpl;

/**
 * A builder class for building a {@link DataDevice} from an {@link NexusObjectProvider}.
 * Provides the ability to configure which fields from the underlying nexus object are 
 * are linked to the {@link NXdata} group and how.
 * 
 * @author Matthew Dickie
 *
 * @param <N> the sub-interface of {@link NXobject} that the nexus object was created from 
 */
public class DataDeviceBuilder<N extends NXobject> {
	
	public static <N extends NXobject> PrimaryDataDevice<N> newPrimaryDataDevice(
			NexusObjectProvider<N> nexusObjectProvider) throws NexusException {
		return (PrimaryDataDevice<N>) newPrimaryDataDeviceBuilder(nexusObjectProvider).build();
	}
	
	public static <N extends NXobject> PrimaryDataDevice<N> newPrimaryDataDevice(
			NexusObjectProvider<N> nexusObjectProvider, String signalDataFieldName) throws NexusException {
		final DataDeviceBuilder<N> builder = new DataDeviceBuilder<>(nexusObjectProvider, true);
		builder.setSignalField(signalDataFieldName);
		return builder.build();
	}
	
	public static <N extends NXobject> DataDeviceBuilder<N> newPrimaryDataDeviceBuilder(
			NexusObjectProvider<N> nexusObjectProvider) {
		return new DataDeviceBuilder<>(nexusObjectProvider, true);
	}
	
	public static <N extends NXobject> AxisDataDevice<N> newAxisDataDevice(
			NexusObjectProvider<N> nexusObjectProvider) throws NexusException {
		return (AxisDataDevice<N>) newAxisDataDeviceBuilder(nexusObjectProvider).build();
	}
	
	public static <N extends NXobject> AxisDataDevice<N> newAxisDataDevice(
			NexusObjectProvider<N> nexusObjectProvider, Integer axisDimension, boolean defaultAxis) throws NexusException {
		return (AxisDataDevice<N>) newAxisDataDeviceBuilder(nexusObjectProvider, axisDimension, defaultAxis).build();
	}

	public static <N extends NXobject> AxisDataDevice<N> newAxisDataDevice(
			NexusObjectProvider<N> nexusObjectProvider, String defaultAxisSourceFieldName,
			Integer defaultAxisDimension, boolean defaultAxis) throws NexusException {
		return (AxisDataDevice<N>) newAxisDataDeviceBuilder(nexusObjectProvider,
				defaultAxisSourceFieldName, defaultAxisDimension, defaultAxis).build();
	}
	
	public static <N extends NXobject> DataDeviceBuilder<N> newAxisDataDeviceBuilder(
			NexusObjectProvider<N> nexusObjectProvider) {
		return newAxisDataDeviceBuilder(nexusObjectProvider, null, null, false);
	}
	
	public static <N extends NXobject> DataDeviceBuilder<N> newAxisDataDeviceBuilder(
			NexusObjectProvider<N> nexusObjectProvider, Integer axisDimension, boolean defaultAxis) {
		return newAxisDataDeviceBuilder(nexusObjectProvider, null, axisDimension, defaultAxis);
	}
	
	public static <N extends NXobject> DataDeviceBuilder<N> newAxisDataDeviceBuilder(
			NexusObjectProvider<N> nexusObjectProvider, String defaultAxisSourceFieldName,
			Integer defaultAxisDimension, boolean defaultAxis) {
		final DataDeviceBuilder<N> builder = new DataDeviceBuilder<>(nexusObjectProvider, false);
		if (defaultAxisSourceFieldName != null) {
			builder.setDefaultAxisSourceFieldName(defaultAxisSourceFieldName);
		}
		builder.setAxisDimension(defaultAxisDimension);
		builder.setDefaultAxis(defaultAxis);
		return builder;
	}
	
	private final boolean isPrimary;
	
	private final NexusObjectProvider<N> nexusObjectProvider;
	
	private final N nexusObject;
	
	private String signalFieldSourceName = null;
	
	private boolean includeAddedFieldsOnly = false;
	
	private LinkedHashSet<String> additionalAxisFields = null;
	
	private LinkedHashSet<String> auxiliarySignalFieldNames = null;
	
	private Integer axisDimension = null;
	
	private boolean defaultAxis = false;
	
	private String defaultAxisSourceFieldName = null;
	
	private Map<String, String> overriddenDestinationFieldNames = null;
	
	private Map<String, Integer> overriddenFieldAxisDimensions = null;
	
	private Map<String, int[]> overriddenFieldDimensionMappings = null;
	
	private int[] defaultDimensionMappings = null;
	
	private Boolean useDeviceName = null;
	
	private int numberOfAxisFieldsToAdd;
	
	/**
	 * Create a new {@link DataDeviceBuilder} for the given {@link NexusObjectProvider}.
	 * 
	 * @param nexusObjectProvider nexus object provider wrapping a {@link NXobject}
	 * @param isPrimary <code>true</code> to build a {@link PrimaryDataDevice}, containing
	 *   the <code>@signal</code> field for the {@link NXdata} group,
	 *   <code>false</code> to build an {@link AxisDataDevice}.
	 */
	public DataDeviceBuilder(NexusObjectProvider<N> nexusObjectProvider, boolean isPrimary) {
		requireNonNull(nexusObjectProvider, "nexusObjectProvider cannot be null");
		this.nexusObjectProvider = nexusObjectProvider;
		this.nexusObject = nexusObjectProvider.getNexusObject();
		requireNonNull(nexusObject, "a nexusObjectProvider cannot have a null nexusObject");
		this.isPrimary = isPrimary;
		
		if (isPrimary) {
			signalFieldSourceName = nexusObjectProvider.getPrimaryDataFieldName();
			requireNonNull(signalFieldSourceName,
					"the nexusObjectProvider for the primary device must specify a signal field");
		}
	}
	
	public void setSignalField(String signalFieldSourceName) {
		requireNonNull(signalFieldSourceName,
				"The signal field of an NXdata group cannot be null. Probably you have not set a primary data field for your nexus object.");
		this.signalFieldSourceName = signalFieldSourceName;
	}
	
	public void setAuxiliarySignalFieldNames(LinkedHashSet<String> auxiliarySignalFieldNames) {
		this.auxiliarySignalFieldNames = auxiliarySignalFieldNames;
	}
	
	public void clearAxisFields() {
		additionalAxisFields = null;
		includeAddedFieldsOnly = true;
	}
	
	public void addAxisField(String axisFieldName) {
		requireNonNull(axisFieldName, "Cannot add a null axisFieldName");
		if (additionalAxisFields == null) {
			additionalAxisFields = new LinkedHashSet<>();
		}
		additionalAxisFields.add(axisFieldName);
	}
	
	public void addAxisField(String axisFieldName, int fieldAxisDimension, boolean defaultAxis) {
		addAxisField(axisFieldName);
		setFieldAxisDimension(axisFieldName, fieldAxisDimension, defaultAxis);
	}
	
	public void addAxisField(String axisFieldName, String axisFieldDestinationName) {
		addAxisField(axisFieldName);
		setDestinationFieldName(axisFieldName, axisFieldDestinationName);
	}
	
	public void setFieldAxisDimension(String axisFieldName, int axisDimension, boolean defaultAxis) {
		requireNonNull(axisFieldName);
		if (overriddenFieldAxisDimensions == null) {
			overriddenFieldAxisDimensions = new HashMap<>();
		}
		overriddenFieldAxisDimensions.put(axisFieldName, axisDimension);
	}
	
	public void addAxisField(String axisFieldName, int[] dimensionMappings) {
		addAxisField(axisFieldName);
		setDimensionMappings(axisFieldName, dimensionMappings);
	}
	
	public void setDimensionMappings(String axisFieldName, int... dimensionMappings) {
		requireNonNull(axisFieldName);
		requireNonNull(dimensionMappings);
		if (overriddenFieldDimensionMappings == null) {
			overriddenFieldDimensionMappings = new HashMap<>();
		}
		
		overriddenFieldDimensionMappings.put(axisFieldName, dimensionMappings);
	}
	
	public void addAxisFields(String... axisFieldNames) {
		for (String axisFieldName : axisFieldNames) {
			addAxisField(axisFieldName);
		}
	}
	
	public void setAxisFields(String... axisFieldNames) {
		clearAxisFields();
		addAxisFields(axisFieldNames);
	}
	
	public void setAxisDimension(Integer axisDimension) {
		this.axisDimension = axisDimension;
	}
	
	public void setDefaultAxis(boolean defaultAxis) {
		this.defaultAxis = defaultAxis;
	}
	
	public void setDefaultAxisSourceFieldName(String defaultAxisSourceFieldName) {
		if (defaultAxisSourceFieldName != null) {
			addAxisField(defaultAxisSourceFieldName);
		}
		this.defaultAxisSourceFieldName = defaultAxisSourceFieldName;
	}
	
	public void setDefaultDimensionMappings(int[] defaultDimensionMappings) {
		this.defaultDimensionMappings = defaultDimensionMappings;
	}
	
	public void setUseDeviceName(boolean useDeviceName) {
		this.useDeviceName = useDeviceName;
	}
	
	public void setDestinationFieldName(String sourceFieldName, String destinationFieldName) {
		requireNonNull(sourceFieldName, "sourceFieldName cannot be null");
		requireNonNull(destinationFieldName, "destinationFieldName cannot be null");
		if (overriddenDestinationFieldNames == null) {
			overriddenDestinationFieldNames = new HashMap<>();
		}
		overriddenDestinationFieldNames.put(sourceFieldName, destinationFieldName);
	}

	private DataDeviceImpl<N> createDataDevice() throws NexusException {
		if (isPrimary) {
			DataFieldModel signalFieldModel = createSignalFieldModel();
			return new PrimaryDataDeviceImpl<>(nexusObject, signalFieldModel);
		}
		
		return new AxisDataDeviceImpl<>(nexusObject);
	}
	
	/**
	 * Get the rank of the field with the given name .
	 * @param fieldName field name
	 * @return field rank
	 * @throws NexusException
	 */
	private int getFieldRank(String fieldName) throws NexusException {
		final Node fieldNode = nexusObject.getNode(fieldName);
		if (fieldNode != null) {
			if (fieldNode.isDataNode()) { // the node is a data node
				return ((DataNode) fieldNode).getRank();
			} else if (fieldNode.isSymbolicNode()) {
				// the node is an external link. The rank should have been set in the NexusObjectProvider 
				return nexusObjectProvider.getExternalDatasetRank(fieldName);
			}
		}

		// no such data node or external link
		throw new NexusException(MessageFormat.format(
				"The {0} does not have a data node or symbolic node with the name: {1}",
				nexusObject.getNXclass().getSimpleName(), fieldName));
	}
	
	private Integer getFieldAxisDimension(String axisFieldName) {
		// first check if the value has been overridden (i.e. explicitly set)
		if (overriddenFieldAxisDimensions != null) {
			return overriddenFieldAxisDimensions.get(axisFieldName);
		}
		
		// for a primary device, see if the nexus object provider knows the default axis dimension
		// e.g. the axis field belongs to the same device as the signal field for the NXdata
		if (isPrimary) {
			return nexusObjectProvider.getDefaultAxisDimension(signalFieldSourceName, axisFieldName);
		}
		
		// return the axis dimension for this device (may be null)
		return axisDimension;
	}

	private int[] getDimensionMapping(String axisFieldName, int fieldRank, Integer axisDimension) {
		// first check if the value has been overridden (i.e. explicitly set)
		if (overriddenFieldDimensionMappings != null) {
			return overriddenFieldDimensionMappings.get(axisFieldName);
		}

		if (isPrimary) {
			int[] dimensionMappings = nexusObjectProvider.getDimensionMappings(signalFieldSourceName, axisFieldName);
			if (dimensionMappings != null) {
				return dimensionMappings;
			}
		}

		// dimension mappings not explicitly set, so we calculate a default
		// if this is an axis field and has size 1, this must be the dimension mapping
		if (fieldRank == 1 && axisDimension != null) {
			return new int[] { axisDimension };
		}

		// use the default dimension mappings for the device if set and of the same rank
		if (defaultDimensionMappings != null && fieldRank == defaultDimensionMappings.length) {
			return defaultDimensionMappings;
		}

		// default to [0, 1, ... n] where n is the field rank
		return IntStream.range(0, fieldRank).toArray();
	}

	private boolean isDefaultAxisField(String axisFieldName) {
		// an axis field is the default axis field for a dimension of the signal field of the NXdata group
		// if this device is a default axis, as the axis field name is the default axis field name of this device
		// alternatively if this is a primary device (i.e. detector) it may provide an additional axis field
		// for its primary field (i.e. the signal field of an NXdetector group)
		return (defaultAxis && axisFieldName.equals(defaultAxisSourceFieldName))
				|| (isPrimary && getFieldAxisDimension(axisFieldName) != null);
	}
	
	private String getDestinationFieldName(String sourceFieldName) {
		// first check if the value has been overridden (i.e. explicitly set)
		if (overriddenDestinationFieldNames != null &&
				overriddenDestinationFieldNames.containsKey(sourceFieldName)) {
			return overriddenDestinationFieldNames.get(sourceFieldName);
		}
		
		if (!useDeviceName()) {
			return sourceFieldName;
		}
		
		final String deviceName = nexusObjectProvider.getName();

		// if there's just one field, use the device name as the destination field name, otherwise prepend the device name to the source field name
		return getNumberOfFieldsToAdd() > 1 ? deviceName + '_' + sourceFieldName : deviceName;
	}
	
	private boolean useDeviceName() {
		return useDeviceName != null ? useDeviceName : nexusObjectProvider.isUseDeviceNameInNXdata().orElse(!isPrimary);
	}
	
	private int getNumberOfFieldsToAdd() {
		return numberOfAxisFieldsToAdd + (isPrimary ? 1 : 0);
	}
	
	private DataFieldModel createSignalFieldModel() throws NexusException {
		return createAuxiliarySignalFieldModel(signalFieldSourceName);
	}
	
	private DataFieldModel createAuxiliarySignalFieldModel(String signalFieldSourceName) throws NexusException {
		final int fieldRank = getFieldRank(signalFieldSourceName);
		final String signalDestFieldName = getDestinationFieldName(signalFieldSourceName);
		
		return new DataFieldModel(signalFieldSourceName, signalDestFieldName, fieldRank);
	}
	
	/**
	 * Creates and returns the {@link AxisFieldModel} for the field with the given name.
	 * @param axisFieldName axis field name
	 * @return axis field model
	 * @throws NexusException
	 */
	private AxisFieldModel createAxisFieldModel(String axisFieldName) throws NexusException {
		final Integer fieldAxisDimension = getFieldAxisDimension(axisFieldName);
		final int fieldRank = getFieldRank(axisFieldName);
		final int[] dimensionMapping = getDimensionMapping(axisFieldName, fieldRank, fieldAxisDimension);
		final String destinationFieldName = getDestinationFieldName(axisFieldName);
		final boolean defaultAxisField = isDefaultAxisField(axisFieldName);
		
		final AxisFieldModel axisFieldModel = new AxisFieldModel(axisFieldName, fieldRank);
		axisFieldModel.setAxisDimension(fieldAxisDimension);
		axisFieldModel.setDimensionMapping(dimensionMapping);
		axisFieldModel.setDestinationFieldName(destinationFieldName);
		axisFieldModel.setDefaultAxis(defaultAxisField);
		
		return axisFieldModel;
	}
	
	private void calculateDefaultAxisSourceName() {
		if (defaultAxisSourceFieldName == null && axisDimension != null) {
			defaultAxisSourceFieldName = nexusObjectProvider.getDefaultAxisDataFieldName();
			if (defaultAxisSourceFieldName == null) {
				defaultAxisSourceFieldName = nexusObjectProvider.getPrimaryDataFieldName();
			}
		}
	}

	private LinkedHashSet<String> calculateAxisFieldNamesToAdd() {
		final LinkedHashSet<String> axisFieldNames = new LinkedHashSet<>();

		if (!includeAddedFieldsOnly) {
			// add the default fields according to the nexus object provider
	
			if (isPrimary) {
				// add any axis fields specific to this primary data field (i.e. signal field) 
				axisFieldNames.addAll(nexusObjectProvider.getAxisDataFieldsForPrimaryDataField(
						signalFieldSourceName));
			} else {
				// if this is not a primary device, the primary data field of the
				// nexus object provider is an axis field for the signal field
				// (which is from the primary device)
				if (nexusObjectProvider.getPrimaryDataFieldName() != null) {
					axisFieldNames.add(nexusObjectProvider.getPrimaryDataFieldName());
				}
			}
			if (nexusObjectProvider.getDefaultAxisDataFieldName() != null) {
				axisFieldNames.add(nexusObjectProvider.getDefaultAxisDataFieldName());
			}
			axisFieldNames.addAll(nexusObjectProvider.getAxisDataFieldNames());
		}
		
		// add the fields added by calling addAxisField()
		if (additionalAxisFields != null) {
			axisFieldNames.addAll(additionalAxisFields);
		}
		
		if (isPrimary) {
			// make sure the signal field name isn't included 
			axisFieldNames.remove(signalFieldSourceName);
		}
		
		return axisFieldNames;
	}

	/**
	 * Builds and returns the data device. If primary was set to <code>true</code>, a
	 * {@link PrimaryDataDevice} will be returned, otherwise an {@link AxisDataDevice} will be
	 * returned.
	 * @return data device
	 * @throws NexusException
	 */
	public <D extends DataDevice<N>> D build() throws NexusException {
		// get the names of the axis fields to add
		final LinkedHashSet<String> axisFieldNames = calculateAxisFieldNamesToAdd();
		numberOfAxisFieldsToAdd = axisFieldNames.size();

		final DataDeviceImpl<N> dataDevice = createDataDevice();

		// add any auxiliary signal fields
		if (dataDevice.isPrimary() && auxiliarySignalFieldNames != null) {
			for (String auxiliarySignalFieldName : auxiliarySignalFieldNames) {
				((PrimaryDataDeviceImpl<?>) dataDevice).addAuxiliarySignalField(
						createAuxiliarySignalFieldModel(auxiliarySignalFieldName));
			}
		}
		
		// calculate the default axis source field name, if not set
		calculateDefaultAxisSourceName();
		
		// create and add an axis field model for each field
		for (String axisFieldName : axisFieldNames) {
			dataDevice.addAxisField(createAxisFieldModel(axisFieldName));
		}
		
		@SuppressWarnings("unchecked")
		final D typeDataDevice = (D) dataDevice;
		return typeDataDevice;
	}
	
}
