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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.IMultipleNexusDevice;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusMetadataProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFile;
import org.eclipse.dawnsci.nexus.builder.data.AxisDataDevice;
import org.eclipse.dawnsci.nexus.builder.data.DataDevice;
import org.eclipse.dawnsci.nexus.builder.data.DataDeviceBuilder;
import org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder;
import org.eclipse.dawnsci.nexus.builder.data.PrimaryDataDevice;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.device.SimpleNexusDevice;
import org.eclipse.dawnsci.nexus.scan.NXEntryScanTimestampsWriter;
import org.eclipse.dawnsci.nexus.scan.NexusScanFileBuilder;
import org.eclipse.dawnsci.nexus.scan.NexusScanModel;
import org.eclipse.dawnsci.nexus.scan.ScanMetadataWriter;
import org.eclipse.dawnsci.nexus.scan.ServiceHolder;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An instance of this class knows how to build a {@link NexusBuilderFile} for a given {@link NexusScanModel}.
 */
class NexusScanFileBuilderImpl implements NexusScanFileBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(NexusScanFileBuilderImpl.class);

	private static final Map<NexusBaseClass, ScanRole> DEFAULT_SCAN_ROLES;

	static {
		DEFAULT_SCAN_ROLES = new HashMap<>();// not an enum map as most base classes not mapped
		DEFAULT_SCAN_ROLES.put(NexusBaseClass.NX_DETECTOR, ScanRole.DETECTOR);
		DEFAULT_SCAN_ROLES.put(NexusBaseClass.NX_MONITOR, ScanRole.MONITOR_PER_POINT);
		DEFAULT_SCAN_ROLES.put(NexusBaseClass.NX_POSITIONER, ScanRole.SCANNABLE);
	}

	private final NexusScanModel nexusScanModel;
	private NexusFileBuilder fileBuilder;
	private NexusBuilderFile nexusBuilderFile;
	private ScanMetadataWriter scanMetadataWriter;
	private NXEntryScanTimestampsWriter entryFieldBuilder;

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

	/**
	 * A map from scannable name to the index of the scan for that scannable,
	 * or <code>null</code> if none
	 */
	private Map<String, Integer> defaultAxisIndexForScannable = null;

	private final INexusDeviceService nexusDeviceService = ServiceHolder.getNexusDeviceService();

	NexusScanFileBuilderImpl(NexusScanModel nexusScanModel, ScanMetadataWriter scanMetadataWriter) throws NexusException {
		this.nexusScanModel = nexusScanModel;

		if (fileBuilder != null) {
			throw new IllegalStateException("The nexus file has already been created");
		}

		// add the solstice scan monitor which writes unique keys.
		this.scanMetadataWriter = scanMetadataWriter;

		// convert this to a map of nexus object providers for each type
		nexusObjectProviders = extractNexusProviders();
		scanMetadataWriter.setNexusObjectProviders(nexusObjectProviders);
	}

	@Override
	public String getFilePath() {
		return nexusBuilderFile.getFilePath();
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
		// We use the new nexus framework to join everything up into the scan
		// Create a builder
		fileBuilder = ServiceHolder.getNexusBuilderFactory().newNexusFileBuilder(nexusScanModel.getFilePath());

		try {
			createEntry(fileBuilder);
			applyTemplates(fileBuilder.getNexusTree());
			// create the file from the builder and open it
			nexusBuilderFile = fileBuilder.createFile(async);
			nexusBuilderFile.openToWrite();
		} catch (NexusException e) {
			throw new NexusException("Cannot create nexus file", e);
		}
	}

	@Override
	public int flush() throws NexusException {
		if (nexusBuilderFile == null)
			throw new IllegalStateException("The nexus file either not been created or has already been closed.");
		return nexusBuilderFile.flush();
	}

	/**
	 * Writes scan finished and closes the wrapped nexus file.
	 * @throws NexusException 
	 */
	public void scanFinished() throws NexusException {
		if (nexusBuilderFile == null)
			throw new IllegalStateException("The nexus file either not been created or has already been closed.");
		entryFieldBuilder.end();
		nexusBuilderFile.close();
		nexusBuilderFile = null;
	}

	private void applyTemplates(Tree tree) throws NexusException {
		final NexusTemplateService templateService = ServiceHolder.getTemplateService();
		for (String templateFilePath : nexusScanModel.getTemplateFilePaths()) {
			final NexusTemplate template = templateService.loadTemplate(templateFilePath);
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
			final List<INexusDevice<?>> nexusDevicesForType = nexusDevices.get(deviceType);
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
		// expand any IMultipleNexusDevices
		// TODO: This may be easier if IMultipleNexusDevice returned multiple INexusDevices, but this would be a breaking API change
		// (although the old method could be kept as deprecated and a default implementation provided
		final Map<ScanRole, List<INexusDevice<?>>> oldNexusDevices = nexusScanModel.getNexusDevices();
		final Map<ScanRole, List<INexusDevice<?>>> newNexusDevices = new EnumMap<ScanRole, List<INexusDevice<?>>>(ScanRole.class);

		// if an IMultipleNexusDevice is present in a list of devices by scan role, all nexus objects are assume to have the same role
		for (Map.Entry<ScanRole, List<INexusDevice<?>>> nexusDevicesForScanRoleEntry : oldNexusDevices.entrySet()) {
			final ScanRole scanRole = nexusDevicesForScanRoleEntry.getKey();
			final List<INexusDevice<?>> oldNexusDevicesForScanRole = nexusDevicesForScanRoleEntry.getValue();
			if (oldNexusDevicesForScanRole == null) {
				newNexusDevices.put(scanRole, new ArrayList<>());
				continue;
			}

			try {
				// decorate all nexus devices, expand any multiple nexus devices in the list of devices by scan role
				final List<INexusDevice<?>> newNexusDevicesForScanRole = new ArrayList<>();
				for (INexusDevice<?> nexusDevice : oldNexusDevicesForScanRole) {
					newNexusDevicesForScanRole.add(nexusDeviceService.decorateNexusDevice(nexusDevice));
					if (nexusDevice instanceof IMultipleNexusDevice) {
						newNexusDevicesForScanRole.addAll(createNexusDevicesForMultiple((IMultipleNexusDevice) nexusDevice, info));
					}
				}

				newNexusDevices.put(scanRole, newNexusDevicesForScanRole);
			} catch (Exception e) {
				if (e instanceof RuntimeException && e.getCause() instanceof NexusException) {
					throw (NexusException) ((RuntimeException) e).getCause();
				}
				throw new NexusException("Error getting nexus devices", e);
			}
		}

		// if an IMultipleNexusDevice is present in the ScanModel directly (usuually this will be a malcolm device)
		// then we decide the ScanRole for each nexus device based on the nexus base class of the provider
		if (nexusScanModel.getMultipleNexusDevice().isPresent()) {
			final IMultipleNexusDevice multipleNexusDevice = nexusScanModel.getMultipleNexusDevice().get();
			try {
				for (NexusObjectProvider<?> nexusProvider : multipleNexusDevice.getNexusProviders(info)) {
					final ScanRole scanRole = DEFAULT_SCAN_ROLES.get(nexusProvider.getNexusBaseClass());
					if (scanRole == null) {
						throw new NexusException("Unable to determine scan role for nexus object of type " +
								nexusProvider.getNexusBaseClass());
					}
					// create a new SimpleNexusDevice to wrap the NexusObjectProvider and get the decorated
					final SimpleNexusDevice<?> nexusDevice = new SimpleNexusDevice<>(nexusProvider);
					final INexusDevice<?> decoratedNexusDevice = nexusDeviceService.decorateNexusDevice(nexusDevice);
					newNexusDevices.get(scanRole).add(decoratedNexusDevice);
				}
			} catch (NexusException e) {
				throw new NexusException("Could not get nexus object providers for device: " + multipleNexusDevice.getName(), e);
			}
		}

		return newNexusDevices;
	}

	private List<INexusDevice<?>> createNexusDevicesForMultiple(IMultipleNexusDevice multiNexusDevice, NexusScanInfo info) {
		// convert a multiNexusDevice into multiple INexusDevices wrapping the returned NexusObjectProviders
		// note, this method is only used when all the devices are assumed to have the same role, i.e. not for a malcolm device
		try {
			final List<NexusObjectProvider<?>> nexusObjectProviders = multiNexusDevice.getNexusProviders(info);
			if (nexusObjectProviders == null || nexusObjectProviders.isEmpty()) {
				return Collections.emptyList();
			}

			final List<INexusDevice<?>> nexusDevices = new ArrayList<>(nexusObjectProviders.size());
			for (NexusObjectProvider<?> nexusObjectProvider : nexusObjectProviders) {
				nexusDevices.add(createSimpleNexusDevice(nexusObjectProvider));
			}
			return nexusDevices;
		} catch (NexusException e) {
			throw new RuntimeException(new NexusException("Could not get nexus provider for device: " + multiNexusDevice.getName(), e));
		}
	}

	private <N extends NXobject> INexusDevice<N> createSimpleNexusDevice(NexusObjectProvider<N> nexusObjectProvider) {
		final INexusDevice<N> nexusDevice = new SimpleNexusDevice<>(nexusObjectProvider);
		return nexusDeviceService.decorateNexusDevice(nexusDevice);
	}

	/**
	 * Creates and populates the {@link NXentry} for the NeXus file.
	 * @param fileBuilder a {@link NexusFileBuilder}
	 * @throws NexusException
	 */
	private void createEntry(NexusFileBuilder fileBuilder) throws NexusException {
		final NexusEntryBuilder entryBuilder  = fileBuilder.newEntry();
		entryBuilder.addDefaultGroups();

		addScanMetadata(entryBuilder, nexusScanModel.getNexusMetadataProviders());

		entryFieldBuilder = new NXEntryScanTimestampsWriter(entryBuilder.getNXentry());
		entryFieldBuilder.start();

		// add all the devices to the entry. Per-scan monitors are added first.
		for (ScanRole deviceType : EnumSet.allOf(ScanRole.class)) {
			addDevicesToEntry(entryBuilder, deviceType);
		}
		entryBuilder.add(scanMetadataWriter.getNexusProvider(nexusScanModel.getNexusScanInfo()));

		// create the NXdata groups
		createNexusDataGroups(entryBuilder);
	}

	private void addDevicesToEntry(NexusEntryBuilder entryBuilder, ScanRole deviceType) throws NexusException {
		entryBuilder.addAll(nexusObjectProviders.get(deviceType));

		List<CustomNexusEntryModification> customModifications =
				nexusScanModel.getNexusDevices().get(deviceType).stream().
				map(d -> d.getCustomNexusModification()).
				filter(Objects::nonNull).
				collect(Collectors.toList());
		for (CustomNexusEntryModification customModification : customModifications) {
			entryBuilder.modifyEntry(customModification);
		}
	}

	private void addScanMetadata(NexusEntryBuilder entryBuilder, List<NexusMetadataProvider> nexusMetadataProviders) throws NexusException {
		for (NexusMetadataProvider nexusMetadataProvider : nexusMetadataProviders) {
			entryBuilder.addMetadata(nexusMetadataProvider);
		}
	}

	/**
	 * Create the {@link NXdata} groups for the scan
	 * @param entryBuilder
	 * @throws NexusException
	 */
	private void createNexusDataGroups(final NexusEntryBuilder entryBuilder) throws NexusException {

		Set<ScanRole> deviceTypes = EnumSet.of(ScanRole.DETECTOR, ScanRole.SCANNABLE, ScanRole.MONITOR_PER_POINT);
		if (deviceTypes.stream().allMatch(t -> nexusObjectProviders.get(t).isEmpty())) {
			throw new NexusException("The scan must include at least one device in order to write a NeXus file.");
		}

		List<NexusObjectProvider<?>> detectors = nexusObjectProviders.get(ScanRole.DETECTOR);
		if (detectors.isEmpty()) {
			// create a NXdata groups when there is no detector
			// (uses first monitor, or first scannable if there is no monitor either)
			createNXDataGroups(entryBuilder, null);
		} else {
			// create NXdata groups for each detector
			for (NexusObjectProvider<?> detector : detectors) {
				createNXDataGroups(entryBuilder, detector);
			}
		}
	}

	private void createNXDataGroups(NexusEntryBuilder entryBuilder, NexusObjectProvider<?> detector) throws NexusException {
		List<NexusObjectProvider<?>> scannables = nexusObjectProviders.get(ScanRole.SCANNABLE);
		List<NexusObjectProvider<?>> monitors = new LinkedList<>(nexusObjectProviders.get(ScanRole.MONITOR_PER_POINT));
		monitors.remove(scanMetadataWriter.getNexusProvider(nexusScanModel.getNexusScanInfo()));

		// determine the primary device - i.e. the device whose primary dataset to make the @signal field
		NexusObjectProvider<?> primaryDevice = null;
		final ScanRole primaryDeviceType;
		if (detector != null) {
			// if there's a detector then it is the primary device
			primaryDevice = detector;
			primaryDeviceType = ScanRole.DETECTOR;
		} else if (!monitors.isEmpty()) {
			// otherwise the first monitor is the primary device (and therefore is not a data device)
			primaryDevice = monitors.remove(0);
			primaryDeviceType = ScanRole.MONITOR_PER_POINT;
		} else if (!scannables.isEmpty()) {
			// if there are no monitors either (a rare edge case), where we use the first scannable
			// note that this scannable is also added as data device
			for (NexusObjectProvider<?> scannable : scannables) {
				if (scannable.getPrimaryDataFieldName() != null) {
					primaryDevice = scannable;
					break;
				}
			}
			if (primaryDevice == null) {
				throw new IllegalArgumentException("No suitable dataset could be found to use as the signal dataset of an NXdata group.");
			}
			primaryDeviceType = ScanRole.SCANNABLE;
		} else {
			// the scan has no devices at all (sanity check as this should already have been checked for)
			throw new IllegalStateException("There must be at least one device to create a Nexus file.");
		}

		// create the NXdata group for the primary data field
		String primaryDeviceName = primaryDevice.getName();
		String primaryDataFieldName = primaryDevice.getPrimaryDataFieldName();
		createNXDataGroup(entryBuilder, primaryDevice, primaryDeviceType, monitors,
				scannables, primaryDeviceName, primaryDataFieldName);

		// create an NXdata group for each additional primary data field (if any)
		for (String dataFieldName : primaryDevice.getAdditionalPrimaryDataFieldNames()) {
			String dataGroupName = primaryDeviceName + "_" + dataFieldName;
			createNXDataGroup(entryBuilder, primaryDevice, primaryDeviceType, monitors,
					scannables, dataGroupName, dataFieldName);
		}
	}

	/**
	 * Create the {@link NXdata} groups for the given primary device.
	 * @param entryBuilder the entry builder to add to
	 * @param primaryDevice the primary device (e.g. a detector or monitor)
	 * @param primaryDeviceType the type of the primary device
	 * @param monitors the monitors
	 * @param scannedDevice the devices being scanned
	 * @param dataGroupName the name of the {@link NXdata} group within the parent {@link NXentry}
	 * @param primaryDataFieldName the name that the primary data field name
	 *   (i.e. the <code>@signal</code> field) should have within the NXdata group
	 * @throws NexusException
	 */
	private void createNXDataGroup(NexusEntryBuilder entryBuilder,
			NexusObjectProvider<?> primaryDevice,
			ScanRole primaryDeviceType,
			List<NexusObjectProvider<?>> monitors,
			List<NexusObjectProvider<?>> scannedDevices,
			String dataGroupName,
			String primaryDataFieldName)
			throws NexusException {
		if (entryBuilder.getNXentry().containsNode(dataGroupName)) {
			dataGroupName += "_data"; // append _data if the node already exists
		}

		// create the data builder and add the primary device
		final NexusDataBuilder dataBuilder = entryBuilder.newData(dataGroupName);

		// the primary device for the NXdata, e.g. a detector
		final PrimaryDataDevice<?> primaryDataDevice = createPrimaryDataDevice(
				primaryDevice, primaryDeviceType, primaryDataFieldName);
		dataBuilder.setPrimaryDevice(primaryDataDevice);

		// add the monitors (excludes the first monitor if the scan has no detectors)
		for (NexusObjectProvider<?> monitor : monitors) {
			dataBuilder.addAxisDevice(getAxisDataDevice(monitor, null));
		}

		// Create the map from scannable name to default index of that scannable in the scan
		if (defaultAxisIndexForScannable == null) {
			defaultAxisIndexForScannable = createDefaultAxisMap();
		}

		// add the scannables to the data builder
		Iterator<NexusObjectProvider<?>> scannablesIter = scannedDevices.iterator();
		while (scannablesIter.hasNext()) {
			final NexusObjectProvider<?> scannable = scannablesIter.next();
			final Integer defaultAxisForDimensionIndex =
					defaultAxisIndexForScannable.get(scannable.getName());
			dataBuilder.addAxisDevice(getAxisDataDevice(scannable, defaultAxisForDimensionIndex));
		}
	}

	/**
	 * Creates a map from scannable names to the index of the scan
	 * (and therefore the index of the signal dataset of each NXdata) that this
	 * scannable is the default axis for.
	 *
	 * @return map from scannable name to index that this scannable is the index for
	 */
	private Map<String, Integer> createDefaultAxisMap() {
		final Map<String, Integer> defaultAxisIndexForScannableMap = new HashMap<>();

		// Convert the list into a map from scannable name to index in scan, only including
		// scannable names which are the dimension name for exactly one index of the scan
		int dimensionIndex = 0;
		final Iterator<Collection<String>> dimensionNamesIter = nexusScanModel.getDimensionNamesByIndex().iterator();
		while (dimensionNamesIter.hasNext()) {
			Collection<String> dimensionNamesForIndex = dimensionNamesIter.next();
			//need to iterate or the _indices attibute defaults to [0]
			Iterator<String> it = dimensionNamesForIndex.iterator();
			while (it.hasNext()){
				String scannableName = it.next();
				if (defaultAxisIndexForScannableMap.containsKey(scannableName)) {
					// already seen this scannable name for another index,
					// so this scannable should not be the default axis for any index
					// note: we put null instead of removing the entry in case the scannable
					// because we don't want to add it again if the scannable is encountered again
					defaultAxisIndexForScannableMap.put(scannableName, null);
				}else {
					defaultAxisIndexForScannableMap.put(scannableName, dimensionIndex);
				}
			}

			dimensionIndex++;
		}

		return defaultAxisIndexForScannableMap;
	}

	private <N extends NXobject> PrimaryDataDevice<N> createPrimaryDataDevice(
			NexusObjectProvider<N> nexusObjectProvider,
			ScanRole primaryDeviceType, String signalDataFieldName) throws NexusException {

		if (primaryDeviceType == ScanRole.SCANNABLE) {
			// using scannable as primary device as well as a scannable
			// only use main data field (e.g. value for an NXpositioner)
			DataDeviceBuilder<N> dataDeviceBuilder = DataDeviceBuilder.newPrimaryDataDeviceBuilder(
					nexusObjectProvider);
			dataDeviceBuilder.setAxisFields();
			return (PrimaryDataDevice<N>) dataDeviceBuilder.build();
		}

		return DataDeviceBuilder.newPrimaryDataDevice(nexusObjectProvider, signalDataFieldName);
	}

	/**
	 * Gets the data device for the given {@link NexusObjectProvider},
	 * creating it if it doesn't exist.
	 *
	 * @param nexusObjectProvider nexus object provider
	 * @param scanIndex the index in the scan for the given {@link NexusObjectProvider},
	 *    or <code>null</code> if the device is not being scanned (i.e. is a monitor)
	 * @param isPrimaryDevice <code>true</code> if this is the primary device for
	 *    the scan, <code>false</code> otherwise
	 * @return the data device
	 * @throws NexusException
	 */
	private AxisDataDevice<?> getAxisDataDevice(NexusObjectProvider<?> nexusObjectProvider,
			Integer scanIndex) throws NexusException {
		AxisDataDevice<?> dataDevice = dataDevices.get(nexusObjectProvider);
		if (dataDevice == null) {
			dataDevice = createAxisDataDevice(nexusObjectProvider, scanIndex);
			// cache the non-primary devices for any other NXdata groups
			dataDevices.put(nexusObjectProvider, dataDevice);
		}

		return dataDevice;
	}

	/**
	 * Creates the {@link DataDevice} for the given {@link NexusObjectProvider},
	 * @param nexusObjectProvider
	 * @param scanIndex the index in the scan for the given {@link NexusObjectProvider},
	 *    or <code>null</code> if the device is not being scanned (i.e. is a monitor)
	 * @return
	 * @throws NexusException
	 */
	private <N extends NXobject> AxisDataDevice<N> createAxisDataDevice(
			NexusObjectProvider<N> nexusObjectProvider, Integer scannableIndex) throws NexusException {
		return DataDeviceBuilder.newAxisDataDevice(nexusObjectProvider, scannableIndex);
	}

}
