package org.eclipse.dawnsci.nexus.scan;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.stream.Collectors.toList;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.ATTRIBUTE_NAME_UNITS;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.ATTRIBUTE_VALUE_MILLISECONDS;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_POINT_END_TIME;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_POINT_START_TIME;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_COMMAND;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_DEAD_TIME;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_DEAD_TIME_PERCENT;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_DURATION;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_END_TIME;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_ESTIMATED_DURATION;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_FINISHED;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_RANK;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_SHAPE;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_SCAN_START_TIME;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.FIELD_NAME_UNIQUE_KEYS;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.GROUP_NAME_DIAMOND_SCAN;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.GROUP_NAME_UNIQUE_KEYS;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.MILLISECOND_DATE_FORMAT;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.PROPERTY_NAME_SUPPRESS_GLOBAL_UNIQUE_KEYS;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.PROPERTY_NAME_UNIQUE_KEYS_PATH;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectWrapper;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusScanMetadataWriter implements INexusDevice<NXcollection> {
	
	private static final Logger logger = LoggerFactory.getLogger(NexusScanMetadataWriter.class);
	
	private static final String DEFAULT_NAME = NexusScanMetadataWriter.class.getSimpleName();
	
	public static final int[] SINGLE_SHAPE = new int[] { 1 };
	public static final int[] START_SHAPE = new int[] { 0 };
	public static final int[] ONE_D_UNLIMITED_SHAPE = new int[] { -1 };
	
	// Writing Datasets. Protected fields to allow overwriting by Bluesky Implementation for tests/futureproofing
	protected ILazyWriteableDataset uniqueKeysDataset;
	protected ILazyWriteableDataset scanFinishedDataset;
	protected ILazyWriteableDataset scanDurationDataset;
	protected ILazyWriteableDataset scanDeadTimeDataset;
	protected ILazyWriteableDataset scanDeadTimePercentDataset;
	protected ILazyWriteableDataset scanStartTimeDataset;
	protected ILazyWriteableDataset scanEndTimeDataset;
	protected ILazyWriteableDataset pointStartTimeStamps;
	protected ILazyWriteableDataset pointEndTimeStamps;
	
	private NexusObjectProvider<NXcollection> nexusProvider;
	private List<NexusObjectProvider<?>> nexusObjectProviders = null;
	
	protected NexusScanInfo scanInfo = null;
	private final String name;
	private ZonedDateTime scanStartTime = null;
	
	private boolean hardwareScan = false;
	private boolean writeGlobalUniqueKeys;

	private NXcollection scanMetadataCollection;
	
	public NexusScanMetadataWriter() {
		this(DEFAULT_NAME);
	}
	
	public NexusScanMetadataWriter(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setHardwareScan(boolean hardwareScan) {
		// if this is a hardware scan, this object can't write the timestamps or unique keys at each point
		this.hardwareScan = hardwareScan;
	}
	
	public boolean isHardwareScan() {
		return hardwareScan;
	}
	
	public void setNexusObjectProviders(Map<ScanRole, List<NexusObjectProvider<?>>> nexusObjectProviderMap) {
		final EnumSet<ScanRole> deviceTypes = EnumSet.complementOf(EnumSet.of(ScanRole.MONITOR_PER_SCAN, ScanRole.NONE));
		final List<NexusObjectProvider<?>> nexusObjectProviderList = nexusObjectProviderMap.entrySet().stream()
				.filter(e -> deviceTypes.contains(e.getKey()))
				.flatMap(e -> e.getValue().stream())
				.collect(toList());
		
		setNexusObjectProviders(nexusObjectProviderList);
	}
	
	public void setNexusObjectProviders(List<NexusObjectProvider<?>> nexusObjectProviders) {
		this.nexusObjectProviders = nexusObjectProviders;
		writeGlobalUniqueKeys = shouldWriteGlobalUniqueKeys();
	}
	
	private boolean shouldWriteGlobalUniqueKeys() {
		// the global unique keys dataset is used to determine whether processing can be performed for each point in the scan
		// we don't write it if a device performs an inner scan (i.e. a malcolm device) or performs multiple exposures
		// within each position that should be processed before the position has been completed
		return !(hardwareScan ||
				nexusObjectProviders.stream()
				.map(prov -> prov.getPropertyValue(PROPERTY_NAME_SUPPRESS_GLOBAL_UNIQUE_KEYS))
				.filter(Objects::nonNull).anyMatch(Boolean.TRUE::equals));
	}

	@Override
	public NexusObjectProvider<NXcollection> getNexusProvider(NexusScanInfo info) throws NexusException {
		if (nexusProvider == null) {
			final NXcollection scanMetadataCollection = createNexusObject(info);
			nexusProvider = new NexusObjectWrapper<>(GROUP_NAME_DIAMOND_SCAN, scanMetadataCollection);
		}
		
		return nexusProvider;
	}
	
	protected NXcollection createNexusObject(NexusScanInfo scanInfo) {
		this.scanInfo = scanInfo;
		
		scanStartTime = ZonedDateTime.now().truncatedTo(MILLIS); // record the current time

		scanMetadataCollection = NexusNodeFactory.createNXcollection();
		
		// write the scan rank
		scanMetadataCollection.setField(FIELD_NAME_SCAN_RANK, scanInfo.getRank());
		
		// write the scan command, if available
		if (scanInfo.getScanCommand() != null) {
			scanMetadataCollection.setField(FIELD_NAME_SCAN_COMMAND, scanInfo.getScanCommand());
		}
		
		// write the scan shape
		logger.info("Estimated scan shape {}", scanInfo.getShape());
		scanMetadataCollection.setDataset(FIELD_NAME_SCAN_SHAPE, DatasetFactory.createFromObject(scanInfo.getShape()));
		
		final long estimatedScanTimeMillis = scanInfo.getEstimatedScanTime();
		logger.info("Estimated scan time {}ms", estimatedScanTimeMillis);
		final DataNode estimatedDurationNode = scanMetadataCollection.setField(FIELD_NAME_SCAN_ESTIMATED_DURATION, estimatedScanTimeMillis);
		setUnits(estimatedDurationNode, ATTRIBUTE_VALUE_MILLISECONDS);
		
		// Create lazy dataset for actual scan duration, dead time and percent dead time. 
		// These are written at the end of the scan
		scanDurationDataset = createScalarWriteableDataset(scanMetadataCollection,
				FIELD_NAME_SCAN_DURATION, Long.class, ATTRIBUTE_VALUE_MILLISECONDS);
		scanDeadTimeDataset = createScalarWriteableDataset(scanMetadataCollection,
				FIELD_NAME_SCAN_DEAD_TIME, Long.class, ATTRIBUTE_VALUE_MILLISECONDS);
		scanDeadTimePercentDataset = createScalarWriteableDataset(scanMetadataCollection,
				FIELD_NAME_SCAN_DEAD_TIME_PERCENT, Float.class, null);
		
		// create the scan finished dataset and initialize the value to false (0)
		// Note: we can't set the shape to [1] as DAWN assumes that fixed sized datasets can be cached
		// instead we set the shape to [-1] (where -1 means unlimited) - DO NOT CHANGE
		scanFinishedDataset = new LazyWriteableDataset(FIELD_NAME_SCAN_FINISHED, Integer.class,
				SINGLE_SHAPE, ONE_D_UNLIMITED_SHAPE, SINGLE_SHAPE, null);
		scanFinishedDataset.setFillValue(0);
		scanMetadataCollection.createDataNode(FIELD_NAME_SCAN_FINISHED, scanFinishedDataset);
		
		// create the collection for the unique keys
		final NXcollection keysCollection = createUniqueKeysCollection(scanInfo);
		scanMetadataCollection.addGroupNode(GROUP_NAME_UNIQUE_KEYS, keysCollection);

		scanStartTimeDataset = createScalarWriteableDataset(scanMetadataCollection, FIELD_NAME_SCAN_START_TIME, String.class, null);
		scanEndTimeDataset = createScalarWriteableDataset(scanMetadataCollection, FIELD_NAME_SCAN_END_TIME, String.class, null);
		
		if (!hardwareScan) {
			pointStartTimeStamps = scanMetadataCollection.initializeLazyDataset(FIELD_NAME_POINT_START_TIME, scanInfo.getRank(), String.class);
			pointEndTimeStamps = scanMetadataCollection.initializeLazyDataset(FIELD_NAME_POINT_END_TIME, scanInfo.getRank(), String.class);
		}
		
		return scanMetadataCollection;
	}

	private NXcollection createUniqueKeysCollection(NexusScanInfo scanInfo) {
		final NXcollection keysCollection = NexusNodeFactory.createNXcollection();
		if (writeGlobalUniqueKeys) {
			uniqueKeysDataset = keysCollection.initializeLazyDataset(FIELD_NAME_UNIQUE_KEYS, scanInfo.getRank(), Integer.class);
			uniqueKeysDataset.setFillValue(0);
			if (scanInfo.getRank() > 0) {
				final int[] chunk = scanInfo.createChunk(false, 8);
				uniqueKeysDataset.setChunking(chunk);
			}
		}
		
		addLinksToExternalFiles(keysCollection);
		return keysCollection;
	}
	
	/**
	 * For each device, if that device writes to an external file, create an external link within
	 * the unique keys collection to the unique keys dataset in that file.
	 * The unique keys are required in order for live processing to take place. We need to know
	 * when the data for a point has been written for each file point
	 * how much data has been written to each file as devices
	 * @param uniqueKeysCollection
	 */
	private void addLinksToExternalFiles(final NXobject uniqueKeysCollection) {
		if (nexusObjectProviders == null) throw new IllegalStateException("nexusObjectProviders not set");
		
		for (NexusObjectProvider<?> nexusObjectProvider : nexusObjectProviders) {
			final String uniqueKeysPath = (String) nexusObjectProvider.getPropertyValue(PROPERTY_NAME_UNIQUE_KEYS_PATH);
			if (uniqueKeysPath != null) {
				final Set<String> externalFiles = nexusObjectProvider.getExternalFileNames();
				if (externalFiles.isEmpty()) {
					addLinkToInternalDataset(uniqueKeysCollection, nexusObjectProvider, uniqueKeysPath);
				} else {
					for (String externalFileName : nexusObjectProvider.getExternalFileNames()) {
						addLinkToExternalFile(uniqueKeysCollection, externalFileName, uniqueKeysPath, nexusObjectProvider.getName());
					}
				}
			}
		}
	}
	
	private void addLinkToInternalDataset(final NXobject uniqueKeysCollection,
			NexusObjectProvider<?> nexusObjectProvider, String uniqueKeysPath) {
		final String deviceName = nexusObjectProvider.getName();
		final NXobject obj = nexusObjectProvider.getNexusObject();
		final NodeLink nodeLink = obj.findNodeLink(uniqueKeysPath);
		if (nodeLink == null || !nodeLink.isDestinationData()) {
			throw new IllegalArgumentException("No such data node: " + uniqueKeysPath);
		}
		
		final DataNode dataNode = (DataNode) nodeLink.getDestination();
		uniqueKeysCollection.addDataNode(deviceName, dataNode);		
	}
	
	/**
	 * Add a link to the unique keys dataset of the external file if not already present
	 * @param uniqueKeysCollection unique keuys collection to add link to
	 * @param externalFileName name of external file
	 * @param uniqueKeysPath path to unique keys dataset in external file
	 */
	private void addLinkToExternalFile(final NXobject uniqueKeysCollection,
			String externalFileName, String uniqueKeysPath, String deviceName) {
		// If the external file contains the name of the device, we use the name of the
		// device, otherwise we try to find the most useful part of the file name
		// Note: the name doesn't matter for processing purposes for DAWN, but not
		// having changing indices within the key makes it easier to use elsewhere.
		final String datasetName = getDatasetName(deviceName, Paths.get(externalFileName).getFileName().toString());
		if (uniqueKeysCollection.getSymbolicNode(datasetName) == null) {
			uniqueKeysCollection.addExternalLink(datasetName, externalFileName, uniqueKeysPath);
		}
	}
	
	private String getDatasetName(String deviceName, String externalFileName) {
		deviceName = deviceName.toLowerCase();
		externalFileName = externalFileName.toLowerCase();
		if (externalFileName.contains(deviceName)) return deviceName;
		externalFileName = externalFileName.split("\\.")[0];
		final int substringStart = externalFileName.lastIndexOf("-") + 1;
		return externalFileName.substring(substringStart);
	}

	private ILazyWriteableDataset createScalarWriteableDataset(final NXobject groupNode, String fieldName,
			Class<?> dataClass, String unitsStr) {
		final ILazyWriteableDataset writeableDataset = new LazyWriteableDataset(fieldName, dataClass,
				SINGLE_SHAPE, SINGLE_SHAPE, SINGLE_SHAPE, null);
		final DataNode dataNode = groupNode.createDataNode(fieldName, writeableDataset);
		if (unitsStr != null) {
			setUnits(dataNode, unitsStr);
		}
		
		return writeableDataset;
	}
	
	private void setUnits(DataNode dataNode, String unitsStr) {
		final Attribute unitsAttribute = TreeFactory.createAttribute(ATTRIBUTE_NAME_UNITS, unitsStr);
		dataNode.addAttribute(unitsAttribute);
	}
	
	public void writePosition(SliceND scanSlice, int scanPointNum) {
		if (!writeGlobalUniqueKeys) return;
		
		final int uniqueKey = scanPointNum + 1; // unique keys start from 1, not 0
		final Dataset newActualPosition = DatasetFactory.createFromObject(uniqueKey);
		try {
			final SliceND pointSlice = createPointSlice(uniqueKeysDataset, scanSlice);
			uniqueKeysDataset.setSlice(null, newActualPosition, pointSlice);
		} catch (DatasetException e) {
			logger.error("Could not write unique key for position {}", uniqueKey);
		}
	}
	
	private SliceND createPointSlice(ILazyWriteableDataset writeableDataset, SliceND scanSlice) {
		return new SliceND(writeableDataset.getShape(), writeableDataset.getMaxShape(),
				scanSlice.getStart(), scanSlice.getStop(), scanSlice.getStep());
	}
	
	public void pointStarted(SliceND scanSlice) {
		addTimestampToDataset(pointStartTimeStamps, scanSlice);
	}
	
	public void pointFinished(SliceND scanSlice) {
		addTimestampToDataset(pointEndTimeStamps, scanSlice);
	}
	
	private void addTimestampToDataset(ILazyWriteableDataset writeableDataset, SliceND scanSlice) {
		if (!writeGlobalUniqueKeys) return;
		
		try {
			final SliceND pointSlice = createPointSlice(writeableDataset, scanSlice);
			writeableDataset.setSlice(null, createTimestamp(), pointSlice);
		} catch (DatasetException e) {
			logger.error("Could not write timestamp", e);
		}
	}
	
	private Dataset createTimestamp() {
		return createDataset(ZonedDateTime.now().truncatedTo(MILLIS));
	}
	
	public Dataset createDataset(Object data) {
		if (data instanceof ZonedDateTime) {
			// write timestamps is ISO-8601 format
			return DatasetFactory.createFromObject(MILLISECOND_DATE_FORMAT.format((ZonedDateTime) data));
		}
		return DatasetFactory.createFromObject(data);
	}
	
	public void scanFinished() throws NexusException {
		final ZonedDateTime scanEndTime = ZonedDateTime.now().truncatedTo(MILLIS);
		final Duration scanDuration = Duration.between(scanStartTime, scanEndTime);
		final long estimateScanTimeMillis = scanInfo.getEstimatedScanTime();
		final Duration scanDeadTime = scanDuration.minus(estimateScanTimeMillis, ChronoUnit.MILLIS);
		final float deadTimePercent = ((float) scanDeadTime.toMillis() / scanDuration.toMillis()) * 100.0f;

		// mark the scan as finishing and write scan timing information,  
		writeScalarData("scan finished", scanFinishedDataset, 1);
		writeScalarData("start time", scanStartTimeDataset, scanStartTime);
		writeScalarData("end time", scanEndTimeDataset, scanEndTime);
		writeScalarData("duration", scanDurationDataset, scanDuration.toMillis());
		writeScalarData("scan dead time", scanDeadTimeDataset, scanDeadTime.toMillis());
		writeScalarData("scan dead time percent", scanDeadTimePercentDataset, deadTimePercent);
		
		// log a summary of the scan
		final String filePath = scanInfo.getFilePath();
		final String shapeStr = Arrays.toString(scanInfo.getShape());
		logger.info("Scan completed: scan file = {}, shape = {}, estimated time = {}ms, actual time = {}, dead time = {} ({}%)",
				filePath, shapeStr, estimateScanTimeMillis, scanDuration, scanDeadTime, deadTimePercent);
		logger.info("Scan completed in {}", scanDuration);
	}
	
	protected void writeScalarData(String datasetName, ILazyWriteableDataset dataset, Object data) throws NexusException {
		final Dataset datasetToWrite = createDataset(data);
		try {
			dataset.setSlice(null, datasetToWrite, START_SHAPE, SINGLE_SHAPE, SINGLE_SHAPE);
		} catch (Exception e) {
			throw new NexusException("Could not write " + datasetName + " to NeXus file");
		}
	}
	
	@Override
	public CustomNexusEntryModification getCustomNexusModification() {
		return this::modifyEntry;
	}
	
	public void modifyEntry(NXentry entry) {
		// Write timestamps also to the /entry/
		entry.addDataNode(NXentry.NX_START_TIME, scanMetadataCollection.getDataNode(FIELD_NAME_SCAN_START_TIME));
		entry.addDataNode(NXentry.NX_END_TIME, scanMetadataCollection.getDataNode(FIELD_NAME_SCAN_END_TIME));
		entry.addDataNode(NXentry.NX_DURATION, scanMetadataCollection.getDataNode(FIELD_NAME_SCAN_DURATION));
		entry.addDataNode(FIELD_NAME_SCAN_SHAPE, scanMetadataCollection.getDataNode(FIELD_NAME_SCAN_SHAPE));
		if (scanMetadataCollection.containsDataNode(FIELD_NAME_SCAN_COMMAND)) {
			entry.addDataNode(FIELD_NAME_SCAN_COMMAND, scanMetadataCollection.getDataNode(FIELD_NAME_SCAN_COMMAND)); 
		}
	}

}
