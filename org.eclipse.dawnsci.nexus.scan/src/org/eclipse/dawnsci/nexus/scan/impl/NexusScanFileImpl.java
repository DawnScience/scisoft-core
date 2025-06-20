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

package org.eclipse.dawnsci.nexus.scan.impl;

import static java.util.Collections.emptyList;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.SYSTEM_PROPERTY_NAME_VALIDATE_NEXUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.math3.util.Pair;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFactory;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFile;
import org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusMetadataProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.data.AxisDataDevice;
import org.eclipse.dawnsci.nexus.builder.data.DataDevice;
import org.eclipse.dawnsci.nexus.builder.data.DataDeviceBuilder;
import org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder;
import org.eclipse.dawnsci.nexus.builder.data.PrimaryDataDevice;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.device.SimpleNexusDevice;
import org.eclipse.dawnsci.nexus.scan.IDefaultDataGroupCalculator;
import org.eclipse.dawnsci.nexus.scan.NexusScanFile;
import org.eclipse.dawnsci.nexus.scan.NexusScanMetadataWriter;
import org.eclipse.dawnsci.nexus.scan.NexusScanModel;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.eclipse.dawnsci.nexus.validation.NexusValidationService;
import org.eclipse.dawnsci.nexus.validation.ValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.osgi.services.ServiceProvider;

/**
 * An instance of this class knows how to build a {@link NexusBuilderFile} for a given {@link NexusScanModel}.
 */
class NexusScanFileImpl implements NexusScanFile {
	
	private static class PrimaryDeviceWithScanRole {
		private final NexusObjectProvider<?> device;
		private final ScanRole scanRole;
		
		public PrimaryDeviceWithScanRole(NexusObjectProvider<?> primaryDevice, ScanRole scanRole) {
			this.device = primaryDevice;
			this.scanRole = scanRole;
		}
		
		public NexusObjectProvider<?> getDevice() {
			return device;
		}
		
		public ScanRole getScanRole() {
			return scanRole;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(NexusScanFileImpl.class);

	private final NexusScanModel nexusScanModel;
	private final String filePath;
	private NexusFileBuilder fileBuilder;
	private NexusBuilderFile nexusBuilderFile;

	// we need to cache various things as they are used more than once
	/**
	 * A list of the nexus object providers for each category of device.
	 */
	private Map<ScanRole, List<NexusObjectProvider<?>>> nexusObjectProviders = null;

	/**
	 * A map from nexus object provider to the axis data device for that.
	 * This is used for devices added to an NXdata group other than the primary device
	 * (the one that supplies the signal field.)
	 */
	private Map<NexusObjectProvider<?>, AxisDataDevice<?>> dataDevices = new HashMap<>();

	NexusScanFileImpl(NexusScanModel nexusScanModel) throws NexusException {
		this.nexusScanModel = nexusScanModel;
		this.filePath = nexusScanModel.getFilePath();

		if (fileBuilder != null) {
			throw new IllegalStateException("The nexus file has already been created");
		}

		// convert this to a map of nexus object providers for each type
		nexusObjectProviders = extractNexusProviders();
		if (nexusScanModel.getMetadataWriter() instanceof NexusScanMetadataWriter metadataWriter) {
			metadataWriter.setNexusObjectProviders(nexusObjectProviders);
		}
	}
	
	@Override
	public NexusScanModel getNexusScanModel() {
		return nexusScanModel;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public Map<ScanRole, List<NexusObjectProvider<?>>> getNexusObjectProviders() {
		return nexusObjectProviders;
	}

	/**
	 *
	 * @return the paths of all the external files to which we will be writing.
	 */
	public Set<String> getExternalFilePaths() {
		Set<String> paths = new HashSet<>();
		// Big looking loop over small number of things.
		for (List<NexusObjectProvider<?>> provList : nexusObjectProviders.values()) {
			for (NexusObjectProvider<?> prov : provList) {
				paths.addAll(prov.getExternalFileNames());
			}
		}
		return paths;
	}

	public void createNexusFile(boolean async) throws NexusException {
		createNexusFile(async, true);
	}
	
	public void createNexusFile(boolean async, boolean useSwmr) throws NexusException {
		// We use the new nexus framework to join everything up into the scan
		// Create a builder
		fileBuilder = ServiceProvider.getService(NexusBuilderFactory.class).newNexusFileBuilder(nexusScanModel.getFilePath());

		try {
			createEntry(fileBuilder);
			applyTemplates(fileBuilder.getNexusTree());
			
			// create the file from the builder and open it
			nexusBuilderFile = fileBuilder.createFile(async, useSwmr);
			nexusBuilderFile.openToWrite();
		} catch (NexusException e) {
			throw new NexusException("Cannot create nexus file: " + e.getMessage(), e);
		}
	}
	
	@Override
	public int flush() throws NexusException {
		return nexusBuilderFile.flush();
	}

	/**
	 * Writes scan finished and closes the wrapped nexus file.
	 * @throws NexusException 
	 */
	public void scanFinished() throws NexusException {
		validate(fileBuilder);
		nexusBuilderFile.close();
	}

	private void validate(NexusFileBuilder fileBuilder) throws NexusException {
		if (Boolean.getBoolean(SYSTEM_PROPERTY_NAME_VALIDATE_NEXUS) || Boolean.getBoolean("GDA/gda."+SYSTEM_PROPERTY_NAME_VALIDATE_NEXUS)) {
			final ValidationReport validationReport =
					ServiceProvider.getService(NexusValidationService.class).validateNexusTree(fileBuilder.getNexusTree());
			if (validationReport.isError()) { // note we log an error rather than throwing an exception if the nexus file is invalid
				logger.error("The nexus file {} is invalid, see log for details", filePath);
			}
		}
	}

	private void applyTemplates(Tree tree) throws NexusException {
		final NexusTemplateService templateService = ServiceProvider.getService(NexusTemplateService.class);
		for (String templateFilePath : nexusScanModel.getTemplateFilePaths()) {
			final NexusTemplate template = templateService.loadTemplate(templateFilePath);
			template.apply(tree);
		}
		for (NexusTemplate template : nexusScanModel.getNexusTemplates()) {
			template.apply(tree);
		}
	}

	private Map<ScanRole, List<NexusObjectProvider<?>>> extractNexusProviders() throws NexusException {
		logger.trace("extractNexusProviders() called");
		final NexusScanInfo scanInfo = nexusScanModel.getNexusScanInfo();
		final Map<ScanRole, List<INexusDevice<?>>> nexusDevices = getNexusDevices(scanInfo);

		final Map<ScanRole, List<NexusObjectProvider<?>>> nexusObjectProviders = new EnumMap<>(ScanRole.class);
		for (ScanRole deviceType: ScanRole.values()) {
			logger.trace("extractNexusProviders deviceType={}", deviceType);
			final List<INexusDevice<?>> nexusDevicesForType = nexusDevices.getOrDefault(deviceType, emptyList());
			final List<NexusObjectProvider<?>> nexusObjectProvidersForType =
					new ArrayList<>(nexusDevicesForType.size());
			for (INexusDevice<?> nexusDevice : nexusDevicesForType) {
				logger.trace("extractNexusProviders nexusDevice={}", nexusDevice);
				try {
					final NexusObjectProvider<?> nexusProvider = nexusDevice.getNexusProvider(scanInfo);
					if (nexusProvider != null) {
						logger.trace("extractNexusProviders nexusProvider={}", nexusProvider);
						nexusObjectProvidersForType.add(nexusProvider);
					}
				} catch (NexusException e) {
					if (deviceType == ScanRole.MONITOR_PER_SCAN) {
						// A failure to get a Nexus object for a per-scan monitor is not regarded as fatal,
						// so just warn the user.
						logger.warn("Cannot create per-scan monitor {}: {}", nexusDevice.getName(), e.getMessage());
					} else {
						// For all other types of device throw an exception
						throw new NexusException("Cannot create device: " + nexusDevice.getName(), e);
					}
				}
			}

			nexusObjectProviders.put(deviceType, nexusObjectProvidersForType);
		}

		return nexusObjectProviders;
	}

	private Map<ScanRole, List<INexusDevice<?>>> getNexusDevices(NexusScanInfo info) throws NexusException {
		final Map<ScanRole, List<INexusDevice<?>>> oldNexusDevices = nexusScanModel.getNexusDevices();
		final Map<ScanRole, List<INexusDevice<?>>> newNexusDevices = new EnumMap<>(ScanRole.class);
		final INexusDeviceService nexusDeviceService = ServiceProvider.getService(INexusDeviceService.class);

		for (Map.Entry<ScanRole, List<INexusDevice<?>>> nexusDevicesForScanRoleEntry : oldNexusDevices.entrySet()) {
			final ScanRole scanRole = nexusDevicesForScanRoleEntry.getKey();
			final List<INexusDevice<?>> oldNexusDevicesForScanRole = nexusDevicesForScanRoleEntry.getValue();

			try {
				// decorate all nexus devices, expand any multiple nexus devices in the list of devices by scan role
				for (INexusDevice<?> nexusDevice : oldNexusDevicesForScanRole) {
					for (NexusObjectProvider<?> nexusProvider : nexusDevice.getNexusProviders(info)) {
						final ScanRole actualScanRole = nexusProvider.getScanRole() != null ? nexusProvider.getScanRole() : scanRole;
						final INexusDevice<?> newNexusDevice = nexusDeviceService.decorateNexusDevice(
								new SimpleNexusDevice<>(nexusProvider));
						newNexusDevices.computeIfAbsent(actualScanRole, role -> new ArrayList<>()).add(newNexusDevice);
					}
				}
			} catch (Exception e) {
				if (e instanceof RuntimeException && e.getCause() instanceof NexusException) {
					throw (NexusException) ((RuntimeException) e).getCause();
				}
				throw new NexusException("Error getting nexus devices", e);
			}
		}

		return newNexusDevices;
	}
	
	/**
	 * Creates and populates the {@link NXentry} for the NeXus file.
	 * @param fileBuilder a {@link NexusFileBuilder}
	 * @throws NexusException
	 */
	private void createEntry(NexusFileBuilder fileBuilder) throws NexusException {
		final String entryName = nexusScanModel.getEntryName();
		final NexusEntryBuilder entryBuilder = fileBuilder.newEntry(entryName); 
		entryBuilder.addDefaultGroups();

		addScanMetadata(entryBuilder, nexusScanModel.getNexusMetadataProviders());

		// add all the devices to the entry. Per-scan monitors are added first.
		for (ScanRole deviceType : EnumSet.allOf(ScanRole.class)) {
			addDevicesToEntry(entryBuilder, deviceType);
		}
		
		// add the nexus object for the metadata entry (TODO: merge with above)?
		addMetadataDeviceToEntry(entryBuilder, nexusScanModel.getMetadataWriter());

		// create the NXdata groups
		createNexusDataGroups(entryBuilder);
	}

	private void addDevicesToEntry(NexusEntryBuilder entryBuilder, ScanRole deviceType) throws NexusException {
		entryBuilder.addAll(nexusObjectProviders.get(deviceType));

		final List<CustomNexusEntryModification> customModifications =
				nexusScanModel.getNexusDevices().get(deviceType).stream().
				map(INexusDevice::getCustomNexusModification).
				filter(Objects::nonNull).
				toList();
		for (CustomNexusEntryModification customModification : customModifications) {
			entryBuilder.modifyEntry(customModification);
		}
	}
	
	private void addMetadataDeviceToEntry(NexusEntryBuilder entryBuilder, INexusDevice<NXcollection> metadataDevice) throws NexusException {
		if (metadataDevice == null) return;
		entryBuilder.add(metadataDevice.getNexusProvider(nexusScanModel.getNexusScanInfo()));
		if (metadataDevice.getCustomNexusModification() != null) {
			entryBuilder.modifyEntry(metadataDevice.getCustomNexusModification());
		}
	}

	private void addScanMetadata(NexusEntryBuilder entryBuilder, List<NexusMetadataProvider> nexusMetadataProviders) throws NexusException {
		for (NexusMetadataProvider nexusMetadataProvider : nexusMetadataProviders) {
			entryBuilder.addMetadata(nexusMetadataProvider);
		}
	}

	/**
	 * Create the {@link NXdata} groups for the scan.
	 * @param entryBuilder
	 * @throws NexusException
	 */
	private void createNexusDataGroups(final NexusEntryBuilder entryBuilder) throws NexusException {
		final Set<ScanRole> deviceTypes = EnumSet.of(ScanRole.DETECTOR, ScanRole.SCANNABLE, ScanRole.MONITOR_PER_POINT);
		if (deviceTypes.stream().allMatch(t -> nexusObjectProviders.get(t).isEmpty())) {
			throw new NexusException("The scan must include at least one device in order to write a NeXus file.");
		}

		for (PrimaryDeviceWithScanRole primaryDevice : getPrimaryDevices()) {
			createNXDataGroupsForDevice(entryBuilder, primaryDevice);
		}
	}

	private List<PrimaryDeviceWithScanRole> getPrimaryDevices() {
		final List<NexusObjectProvider<?>> detectors = nexusObjectProviders.get(ScanRole.DETECTOR);
		final List<PrimaryDeviceWithScanRole> primaryDevices = detectors.stream() // exceptionally, some detectors may not have a primary field
			.filter(det -> det.getPrimaryDataFieldName() != null)
			.map(det -> new PrimaryDeviceWithScanRole(det, ScanRole.DETECTOR))
			.toList();

		final Optional<PrimaryDeviceWithScanRole> defaultPrimaryDevice = getDefaultPrimaryDevice();
		final List<PrimaryDeviceWithScanRole> primaryDevice = defaultPrimaryDevice.isPresent() ?  List.of(defaultPrimaryDevice.get()) : Collections.emptyList();

		return !primaryDevices.isEmpty() ? primaryDevices : primaryDevice;
	}
	
	/**
	 * Returns the first monitor that can be a primary data device (i.e. has a primary field name set)
	 * or the first scannable if there are no such monitors
	 * @return primary device, with scan role
	 */
	private Optional<PrimaryDeviceWithScanRole> getDefaultPrimaryDevice() {
		return Stream.of(ScanRole.MONITOR_PER_POINT, ScanRole.SCANNABLE)
				.map(this::getFirstPrimaryDevice)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();
	}

	private void createNXDataGroupsForDevice(NexusEntryBuilder entryBuilder,
			PrimaryDeviceWithScanRole primaryDevice) throws NexusException {
		// create the NXdata group for the primary data field
		createNXDataGroup(entryBuilder, primaryDevice,
				primaryDevice.getDevice().getPrimaryDataFieldName());

		// create an NXdata group for each additional primary data field (if any)
		final NexusObjectProvider<?> device = primaryDevice.getDevice();
		for (String dataFieldName : device.getAdditionalPrimaryDataFieldNames()) {
			createNXDataGroup(entryBuilder, primaryDevice, dataFieldName);
		}
		
		// create an NXdata group for each auxiliary group specified in the device
		for (String dataGroupName : device.getAuxiliaryDataGroupNames()) {
			final String signalFieldName = device.getAuxiliaryDataFieldNames(dataGroupName).get(0);
			final LinkedHashSet<String> auxiliarySignalFieldNames =
					new LinkedHashSet<>(device.getAuxiliaryDataFieldNames(dataGroupName));
			auxiliarySignalFieldNames.remove(signalFieldName);
			createNXDataGroup(entryBuilder, primaryDevice, dataGroupName, signalFieldName,
					auxiliarySignalFieldNames); 
		}
		
		setDefaultDataGroupName(entryBuilder);
	}

	private Optional<PrimaryDeviceWithScanRole> getFirstPrimaryDevice(ScanRole scanRole) {
		return nexusObjectProviders.get(scanRole).stream()
				.filter(d -> d.getPrimaryDataFieldName() != null)
				.map(d -> new PrimaryDeviceWithScanRole(d, scanRole))
				.findFirst();
	}
	
	private void setDefaultDataGroupName(NexusEntryBuilder entryBuilder) throws NexusException {
		// get the list of data group names, note as GroupNodeImpl uses a LinkedHashMap for child nodes, insertion order is preserved
		final IDefaultDataGroupCalculator defaultGroupCalculator = ServiceProvider.getOptionalService(
				IDefaultDataGroupCalculator.class).orElse(names -> names.get(0));
		final String defaultDataGroupName = defaultGroupCalculator.getDefaultDataGroupName(
				new ArrayList<>(entryBuilder.getNXentry().getAllData().keySet()));
		entryBuilder.setDefaultDataGroupName(defaultDataGroupName);
	}
	
	/**
	 * Creates the {@link NXdata} group for the primary field of the given device, with the same name as the device.
	 * @param entryBuilder entry builder to add to
	 * @param primaryDevice the primary device (e.g. a detector or monitor) with its scan role
	 * @param signalFieldName the name of the data field that should be marked as the 
	 * 			{@code @signal} field. This is the default plottable field for the NXdata group
	 * @throws NexusException
	 */
	private void createNXDataGroup(NexusEntryBuilder entryBuilder,
			PrimaryDeviceWithScanRole primaryDevice, String signalFieldName)
			throws NexusException {
		final NexusObjectProvider<?> primaryNexusProvider = primaryDevice.getDevice();
		final boolean isPrimaryField = signalFieldName.equals(primaryNexusProvider.getPrimaryDataFieldName());
		final String dataGroupName = isPrimaryField ? primaryNexusProvider.getName() :
			primaryNexusProvider.getName() + "_" + signalFieldName;
		createNXDataGroup(entryBuilder, primaryDevice, dataGroupName, signalFieldName, null);
	}

	/**
	 * Create the {@link NXdata} groups for the given primary device, fo
	 * @param entryBuilder the entry builder to add to
	 * @param primaryDevice the primary device (e.g. a detector or monitor) with its scan role
	 * @param dataGroupName the name of the {@link NXdata} group within the parent {@link NXentry}
	 * @param signalFieldName the name of the data field that should be marked as the 
	 * 			{@code @signal} field. This is the default plottable field for the NXdata group
	 * @param auxilarySignalFieldNames the names of any fields to be marked as
	 * 			{@code @auxiliarySignal} fields. These are additional plottable fields 
	 * @throws NexusException
	 */
	private void createNXDataGroup(NexusEntryBuilder entryBuilder,
			PrimaryDeviceWithScanRole primaryDevice, String dataGroupName,
			String signalFieldName, LinkedHashSet<String> auxiliarySignalFieldNames)
					throws NexusException {
		if (entryBuilder.getNXentry().containsNode(dataGroupName)) {
			dataGroupName += "_data"; // append _data if the node already exists
		}

		// create the data builder and add the primary device
		final NexusDataBuilder dataBuilder = entryBuilder.newData(dataGroupName);

		// the primary device for the NXdata, e.g. a detector
		final PrimaryDataDevice<?> primaryDataDevice = createPrimaryDataDevice(primaryDevice,
				signalFieldName, auxiliarySignalFieldNames);
				
		dataBuilder.setPrimaryDevice(primaryDataDevice);

		// add the monitors to the data builder (if the primary device is a monitor, it is excluded)
		for (NexusObjectProvider<?> monitor : nexusObjectProviders.get(ScanRole.MONITOR_PER_POINT)) {
			if (primaryDevice.getScanRole() != ScanRole.MONITOR_PER_POINT || primaryDevice.getDevice() != monitor) {
				dataBuilder.addAxisDevice(getAxisDataDevice(monitor));
			}
		}

		// add the scannables to the data builder
		for (NexusObjectProvider<?> nexusObjectProvider : nexusObjectProviders.get(ScanRole.SCANNABLE)) {
			dataBuilder.addAxisDevice(getAxisDataDevice(nexusObjectProvider));
		}
	}
	
	private <N extends NXobject> PrimaryDataDevice<N> createPrimaryDataDevice(
			PrimaryDeviceWithScanRole primaryDevice, String signalDataFieldName,
			LinkedHashSet<String> auxiliarySignalFieldNames) throws NexusException {

		@SuppressWarnings("unchecked")
		final NexusObjectProvider<N> dataDevice = (NexusObjectProvider<N>) primaryDevice.device;
		final DataDeviceBuilder<N> dataDeviceBuilder = new DataDeviceBuilder<>(dataDevice, true);
		switch (primaryDevice.getScanRole()) {
			case SCANNABLE: 
				// using scannable as primary device as well as a scannable
				// only use main data field (e.g. value for an NXpositioner)
				dataDeviceBuilder.setAxisFields();
				break;
			case MONITOR_PER_POINT:
				dataDeviceBuilder.setUseDeviceName(true);
				break;
			case DETECTOR:
				dataDeviceBuilder.setSignalField(signalDataFieldName);
				break;
			default:
				throw new IllegalArgumentException("Invalid primary device type: " + primaryDevice.scanRole);
		}
		
		dataDeviceBuilder.setAuxiliarySignalFieldNames(auxiliarySignalFieldNames);

		return (PrimaryDataDevice<N>) dataDeviceBuilder.build();
	}

	/**
	 * Gets the data device for the given {@link NexusObjectProvider},
	 * creating it if it doesn't exist.
	 *
	 * @param nexusObjectProvider nexus object provider
	 * @return the data device
	 * @throws NexusException
	 */
	private AxisDataDevice<?> getAxisDataDevice(NexusObjectProvider<?> nexusObjectProvider) throws NexusException {
		AxisDataDevice<?> dataDevice = dataDevices.get(nexusObjectProvider);
		if (dataDevice == null) {
			dataDevice = createAxisDataDevice(nexusObjectProvider);
			// cache the axis data device to be reused with other NXdata groups
			dataDevices.put(nexusObjectProvider, dataDevice);
		}

		return dataDevice;
	}

	/**
	 * Creates the {@link DataDevice} for the given {@link NexusObjectProvider},
	 * @param nexusObjectProvider
	 * @return
	 * @throws NexusException
	 */
	private <N extends NXobject> AxisDataDevice<N> createAxisDataDevice(
			NexusObjectProvider<N> nexusObjectProvider) throws NexusException {
		final Pair<Integer, Boolean> scanIndex = getScanIndex(nexusObjectProvider.getName());
		return DataDeviceBuilder.newAxisDataDevice(nexusObjectProvider, scanIndex.getFirst(), scanIndex.getSecond());
	}
	
	/**
	 * Finds the index of the given axis name, and whether it is the default axis for that index.
	 * @param axisName axis to search for
	 * @return a {@link Pair} where the first element is an {@link Integer} specifying the index of the dimension for
	 * 		this axis name, or <code>null</code> if none, and the second element is a {@link Boolean} that is
	 * 		{@link Boolean#TRUE} if this axis name is the name of the default axis for that dimension index, and
	 * 		{@link Boolean#FALSE} otherwise
	 */
	private Pair<Integer, Boolean> getScanIndex(String axisName) {
		for (int dimIndex = 0; dimIndex < nexusScanModel.getDimensionNamesByIndex().size(); dimIndex++) {
			final int indexInDim = nexusScanModel.getDimensionNamesByIndex().get(dimIndex).indexOf(axisName);
			if (indexInDim != -1) {
				// the axis name is the default for the dimension index if its first in the list of axes for that index 
				return Pair.create(dimIndex, indexInDim == 0); 
			}
		}
		return Pair.create(null, false); // the axis is not for a dimension, e.g. a monitor
	}

}
