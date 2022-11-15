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

import static org.eclipse.dawnsci.nexus.NexusScanInfo.NexusRole.PER_POINT;
import static org.eclipse.dawnsci.nexus.NexusScanInfo.NexusRole.PER_SCAN;
import static org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole.MONITOR_PER_SCAN;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * This class represents information about the scan which the NeXus device is running in.
 * 
 * For instance, names of scannables in the axes and the rank of the scan.
 * 
 * @author Matthew Gerring
 *
 */
public class NexusScanInfo {
	
	public enum NexusRole {
		PER_POINT, PER_SCAN
	}
	
	public enum ScanRole {
		DETECTOR(PER_POINT),
		SCANNABLE(PER_POINT),
		MONITOR_PER_POINT(PER_POINT),
		MONITOR_PER_SCAN(PER_SCAN),
		NONE(PER_SCAN);
		
		private final NexusRole nexusRole;
		
		private ScanRole(NexusRole nexusRole) {
			this.nexusRole = nexusRole;
		}
		
		public NexusRole getNexusRole() {
			return nexusRole;
		}
		
	}
	
	private final Map<ScanRole, Collection<String>> deviceNames;
	
	private int[] overallShape;
	
	private int[] outerShape;
	
	private String scanCommand;
	
	private List<String> scanFieldNames;
	
	private String filePath;
	
	private String currentScriptName;
	
	private long estimatedScanTime = -1; // in ms, or -1 if not specified
	
	public NexusScanInfo() {
		this(Collections.emptyList());
	}
	
	/**
	 * 
	 * @param axisNames must be ordered correctly into indices
	 */
	public NexusScanInfo(List<String> axisNames) {
		super();
		deviceNames = new EnumMap<>(ScanRole.class);
		deviceNames.put(ScanRole.SCANNABLE, axisNames);
		overallShape = new int[] { 64 };
		outerShape = overallShape;
	}
	
	/**
	 * Returns the overall rank of the scan, including any dimensions controlled by malcolm.
	 * Note that for an acquire scan, where {@link #getOverallShape()} has a length of 0,
	 * this will return 1, as this is the rank of the datasets to be written.
	 * 
	 * @return rank of overall scan
	 */
	public int getOverallRank() {
		return Math.max(overallShape.length, 1);
	}

	/**
	 * Returns the outer rank of the scan, not including any dimensions controlled by malcolm.
	 * Note that for an acquire scan, where {@link #getOverallOuter()} has a length of 0,
	 * this will return 1, as this is the rank of the datasets to be written.
	 * For non-malcolm scans this will return the same value as {@link #getOverallRank()}.
	 *  
	 * @return rank of outer scan
	 */
	public int getOuterRank() {
		return Math.max(outerShape.length, 1);
	}
	
	public void setEstimatedScanTime(long estimatedScanTime) {
		this.estimatedScanTime = estimatedScanTime;
	}
	
	public long getEstimatedScanTime() {
		return estimatedScanTime;
	}
	
	private void setDeviceNames(ScanRole scanRole, Collection<String> names) {
		// private so that we can ensure the correct type of collection for the role
		// e.g. List for Scannables
		deviceNames.put(scanRole, names);
	}
	
	public Collection<String> getDeviceNames(ScanRole scanRole) {
		return deviceNames.get(scanRole);
	}
	
	public void setDetectorNames(Set<String> detectorNames) {
		setDeviceNames(ScanRole.DETECTOR, detectorNames);
	}
	
	public Collection<String> getDetectorNames() {
		final Collection<String> detNames = getDeviceNames(ScanRole.DETECTOR);
		return detNames == null ? Collections.emptyList() : detNames;
	}

	public List<String> getScannableNames() {
		final List<String> scannableNames = (List<String>) getDeviceNames(ScanRole.SCANNABLE);
		return scannableNames == null ? Collections.emptyList() : scannableNames;
	}
	
	public void setScannableNames(List<String> axisNames) {
		setDeviceNames(ScanRole.SCANNABLE, axisNames);
	}
	
	public Set<String> getPerPointMonitorNames() {
		final Set<String> perPointMonitorNames = (Set<String>) getDeviceNames(ScanRole.MONITOR_PER_POINT);
		return perPointMonitorNames == null ? Collections.emptySet() : perPointMonitorNames;
	}
	
	public void setPerPointMonitorNames(Set<String> monitorNames) {
		setDeviceNames(ScanRole.MONITOR_PER_POINT, monitorNames);
	}
	
	public Set<String> getPerScanMonitorNames() {
		final Set<String> perScanMonitorNames = (Set<String>) getDeviceNames(ScanRole.MONITOR_PER_SCAN);
		return perScanMonitorNames == null ? Collections.emptySet() : perScanMonitorNames;
	}
	
	public void setPerScanMonitorNames(Set<String> metadataScannableNames) {
		setDeviceNames(ScanRole.MONITOR_PER_SCAN, metadataScannableNames);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Returns the overall shape of the scan, including any dimensions controlled by malcolm.
	 * @return overall scan shape
	 */
	public int[] getOverallShape() {
		return overallShape;
	}

	public void setOverallShape(int... overallShape) {
		this.overallShape = overallShape;
	}
	
	public void setShape(int... shape) {
		// set both outer and overall shape to the given shape, useful for non-malcolm scans
		setOverallShape(shape);
		setOuterShape(shape);
	}

	/**
	 * Returns the shape of the outer scan, not including any dimensions controlled by malcolm.
	 * For non-malcolm scans this method will return the same value as {@link #getOverallShape()}.
	 * @return shape of outer scan
	 */
	public int[] getOuterShape() {
		return outerShape;
	}

	public void setOuterShape(int... outerShape) {
		this.outerShape = outerShape;
	}

	public String getScanCommand() {
		return scanCommand;
	}

	public void setScanCommand(String scanCommand) {
		this.scanCommand = scanCommand;
	}
	
	public List<String> getScanFieldNames() {
		return scanFieldNames;
	}
	
	public void setScanFieldNames(List<String> scanFieldNames) {
		this.scanFieldNames = scanFieldNames;
	}
	
	public String getCurrentScriptName() {
		return currentScriptName;
	}

	public void setCurrentScriptName(String currentScriptName) {
		this.currentScriptName = currentScriptName;
	}

	/**
	 * Returns the {@link ScanRole} of the device with the given name within the scan. If the device
	 * is not in the scan {@link ScanRole#NONE} is returned.
	 * @param name name of device
	 * @return role or device within scan, never <code>null</code>
	 */
	public ScanRole getScanRole(String name) {
		return deviceNames.entrySet().stream()
			.filter(entry -> entry.getValue() != null && entry.getValue().contains(name))
			.map(Map.Entry::getKey)
			.findFirst()
			.orElse(ScanRole.NONE);
	}
	
	/**
	 * Returns whether the device with the given name should write its data
	 * once for the whole scan, or 
	 * @param name
	 * @return
	 */
	public boolean writeDataPerScan(String name) {
		final ScanRole scanRole = getScanRole(name);
		return scanRole == null || scanRole == MONITOR_PER_SCAN;
	}
	
	public int[] createChunk(int... datashape) {
		return createChunk(true, datashape);
	}

	/**
	 * Attempts to make a chunk size from the scan.
	 * NOTE This assumes that the datashape is a resonable size currently.
	 * If the datashape is small, the chunking can become too small to usefully
	 * read.
	 * 
	 * @param datashape
	 * @return the suggested chunk array
	 */
	public int[] createChunk(boolean append, int... datashape) {
		// Create chunk array of correct length
		final int outerRank = getOuterRank();
		final int[] chunk = append ? new int[outerRank+datashape.length] : new int[outerRank];

		// Initialise the array to all 1
		// TODO this is slightly redundant but ensures no zeros can ever be allowed through
		Arrays.fill(chunk, 1);

		// Change end of chunk array to match datashape
		int index = 0;
		for (int i = datashape.length; i>0; i--) {
			chunk[chunk.length-i] = datashape[index];
			index++;
		}
		return chunk;
	}

	@Override
	public String toString() {
		return "NexusScanInfo [deviceNames=" + deviceNames + ", overallShape=" + Arrays.toString(overallShape)
				+ ", outerShape=" + Arrays.toString(outerShape) + ", scanCommand=" + scanCommand + ", scanFieldNames="
				+ scanFieldNames + ", filePath=" + filePath + ", currentScriptName=" + currentScriptName
				+ ", estimatedScanTime=" + estimatedScanTime + "]";
	}

}
