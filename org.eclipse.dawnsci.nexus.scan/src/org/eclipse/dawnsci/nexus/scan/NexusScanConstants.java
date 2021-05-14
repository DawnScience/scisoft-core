package org.eclipse.dawnsci.nexus.scan;

import org.eclipse.dawnsci.nexus.NXentry;

public class NexusScanConstants {
	
	/**
	 * Property name for the path within an external (linked) nexus file to the unique keys dataset.
	 */
	public static final String PROPERTY_NAME_UNIQUE_KEYS_PATH = "uniqueKeys";
	
	/**
	 * Property name for suppressing writing the global unique keys dataset. This dataset will be written
	 * unless this property is set to {@link Boolean#TYPE} for any device in a scan.
	 * The global unique keys dataset is normally written to at the end of each position in a scan. It is
	 * used to indicate that a position in a scan has been written to for all detectors and that processing
	 * can be performed. It should be suppressed if processing is required in between points of the scan.
	 * This can be the case if device in the scan performs an inner scan, i.e. a malcolm device, or a
	 * detector writes data while a position in the scan is being performed that required processing before
	 * the position is completed, e.g. a detector that writes multiple frames for each scan position.
	 */
	public static final String PROPERTY_NAME_SUPPRESS_GLOBAL_UNIQUE_KEYS = "suppressGlobalUniqueKeys";
	
	public static final String GROUP_NAME_DIAMOND_SCAN = "diamond_scan"; // TODO better name?? 
	public static final String GROUP_NAME_UNIQUE_KEYS = "keys";
	
	public static final String FIELD_NAME_SCAN_RANK = "scan_rank";
	public static final String FIELD_NAME_SCAN_SHAPE = "scan_shape";
	public static final String FIELD_NAME_SCAN_FINISHED = "scan_finished";
	public static final String FIELD_NAME_SCAN_DURATION = NXentry.NX_DURATION;
	public static final String FIELD_NAME_SCAN_ESTIMATED_DURATION = "scan_estimated_duration";
	public static final String FIELD_NAME_SCAN_DEAD_TIME = "scan_dead_time";
	public static final String FIELD_NAME_SCAN_DEAD_TIME_PERCENT = "scan_dead_time_percent";
	public static final String FIELD_NAME_UNIQUE_KEYS = "uniqueKeys";
	public static final String FIELD_NAME_SCAN_START_TIME = NXentry.NX_START_TIME;
	public static final String FIELD_NAME_SCAN_END_TIME = NXentry.NX_END_TIME;
	public static final String FIELD_NAME_POINT_START_TIME = "point_start_times";
	public static final String FIELD_NAME_POINT_END_TIME = "point_end_times";
	
	public static final String ATTRIBUTE_NAME_UNITS = "units";
	public static final String ATTRIBUTE_VALUE_MILLISECONDS = "ms";
	
	private NexusScanConstants() {
		// private constructor to prevent instantiation
	}
	
}
