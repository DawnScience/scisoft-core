/*-
 * Copyright © 2018 Diamond Light Source Ltd.
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

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.eclipse.dawnsci.nexus.scan.NexusScanConstants.MILLISECOND_DATE_FORMAT;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writes scan start time, end time and duration to the NXentry.
 *
 * Note: this cannot currently be done with {@link ScanMetadata} or {@link CustomNexusEntryModification}
 * as we need to use lazy datasets and write data at the end of the scan. TODO find a better way of doing this.
 */
public class NXEntryScanTimestampsWriter {
	
	private static final Logger logger = LoggerFactory.getLogger(NXEntryScanTimestampsWriter.class);
	
	private static final int[] SINGLE_SHAPE = new int[] { 1 };
	private static final int[] START_SHAPE = new int[] { 0 };

	private static final String ATTRIBUTE_NAME_UNITS = "units";
	private static final String ATTRIBUTE_VALUE_MILLISECONDS = "ms";

	private final NXentry entry;
	private ILazyWriteableDataset scanEndTimeDataset;
	private ILazyWriteableDataset scanDurationDataset;
	private ZonedDateTime scanStartTime;

	public NXEntryScanTimestampsWriter(NXentry entry) {
		this.entry = entry;
	}

	public void start() {
		// write and cache scan start time
		// Truncated to millis as the string parser in DateDataset treats microseconds as milliseconds
		scanStartTime = ZonedDateTime.now().truncatedTo(MILLIS);
		entry.setStart_time(DatasetFactory.createFromObject(MILLISECOND_DATE_FORMAT.format(scanStartTime)));

		// create lazy dataset for scan end time
		scanEndTimeDataset = new LazyWriteableDataset(NXentry.NX_END_TIME, String.class, SINGLE_SHAPE, SINGLE_SHAPE, SINGLE_SHAPE, null);
		entry.createDataNode(NXentry.NX_END_TIME, scanEndTimeDataset);

		// create lazy dataset for scan duration, with units attribute
		scanDurationDataset = new LazyWriteableDataset(NXentry.NX_DURATION, Long.class, SINGLE_SHAPE, SINGLE_SHAPE, SINGLE_SHAPE, null);
		final DataNode durationDataNode = entry.createDataNode(NXentry.NX_DURATION, scanDurationDataset);
		final Attribute unitsAttribute = TreeFactory.createAttribute(ATTRIBUTE_NAME_UNITS, ATTRIBUTE_VALUE_MILLISECONDS);
		durationDataNode.addAttribute(unitsAttribute);
	}

	public void end() {
		// write scan end time to lazy dataset
		// Truncated to millis as the string parser in DateDataset treats microseconds as milliseconds
		final ZonedDateTime scanEndTime = ZonedDateTime.now().truncatedTo(MILLIS);
		try {
			scanEndTimeDataset.setSlice(null, DatasetFactory.createFromObject(MILLISECOND_DATE_FORMAT.format(ZonedDateTime.from(scanEndTime))), START_SHAPE, SINGLE_SHAPE, SINGLE_SHAPE);
		} catch (DatasetException e) {
			logger.error("Could not set scan end",e);
		}

		// write scan duration to dataset
		final Duration scanDuration = Duration.between(scanStartTime, scanEndTime);
		try {
			scanDurationDataset.setSlice(null, DatasetFactory.createFromObject(scanDuration.toMillis()), START_SHAPE, SINGLE_SHAPE, SINGLE_SHAPE);
		} catch (DatasetException e) {
			logger.error("Could set scan duration",e);
		}
	}

}
