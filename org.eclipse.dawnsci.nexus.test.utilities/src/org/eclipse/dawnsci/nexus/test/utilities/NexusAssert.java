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
package org.eclipse.dawnsci.nexus.test.utilities;

import static org.eclipse.dawnsci.nexus.NexusConstants.DATA_AXES;
import static org.eclipse.dawnsci.nexus.NexusConstants.DATA_INDICES_SUFFIX;
import static org.eclipse.dawnsci.nexus.NexusConstants.DATA_SIGNAL;
import static org.eclipse.dawnsci.nexus.NexusConstants.TARGET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DateDataset;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.PositionIterator;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.StringDataset;
import org.hamcrest.Matchers;

public class NexusAssert {
	
	private static final String ATTR_NAME_UNITS = "units";
	private static final String FIELD_NAME_UNIQUE_KEYS   = "uniqueKeys";
	private static final String FIELD_NAME_SCAN_FINISHED = "scan_finished";
	private static final String FIELD_NAME_SCAN_ESTIMATED_DURATION = "scan_estimated_duration";
	private static final String FIELD_NAME_SCAN_DEAD_TIME = "scan_dead_time";
	private static final String FIELD_NAME_SCAN_DEAD_TIME_PERCENT = "scan_dead_time_percent";
	private static final String FIELD_NAME_SCAN_SHAPE = "scan_shape";
	private static final String FIELD_NAME_POINT_START_TIME = "point_start_times";
	private static final String FIELD_NAME_POINT_END_TIME = "point_end_times";
	private static final String GROUP_NAME_DIAMOND_SCAN = "diamond_scan";
	private static final String GROUP_NAME_KEYS = "keys";

	private static final String MOCK_VISIT_ID = "test-mock";
	
	private static final String LAZY_DATA_EXCEPTION = "Could not get data from lazy dataset";
	private static final String LAZY_TIMESTAMP_EXCEPTION = "Could not get timestamp data from lazy dataset";
	
	private static final int[] EMPTY_SHAPE = new int[] { };
	private static final int[] SINGLE_SHAPE = new int[] { 1 };
	
	private NexusAssert() {
		// Hide implicit constructor
	}

	public static void assertNexusTreesEqual(final TreeFile expectedTree, final TreeFile actualTree) throws Exception {
		assertGroupNodesEqual("/", expectedTree.getGroupNode(), actualTree.getGroupNode());
	}
	
	public static void assertNodesEquals(String path, final Node expectedNode, final Node actualNode) throws Exception {
		if (expectedNode.isGroupNode()) {
			assertThat(path, actualNode.isGroupNode(), is(true));
			assertGroupNodesEqual(path, (GroupNode) expectedNode, (GroupNode) actualNode);
		} else if (expectedNode.isDataNode()) {
			assertThat(path, actualNode.isDataNode(), is(true));
			assertDataNodesEqual(path, (DataNode) expectedNode, (DataNode) actualNode);
		} else if (expectedNode.isSymbolicNode()) {
			assertThat(path, actualNode.isSymbolicNode(), is(true));
			assertSymbolicNodesEqual(path, (SymbolicNode) expectedNode, (SymbolicNode) actualNode);
		} else {
			assertThat("Unknown node type", false); // sanity check, shouldn't be possible
		}
	}

	public static void assertGroupNodesEqual(String path,
			final GroupNode expectedGroup, final GroupNode actualGroup) throws Exception {
		if (expectedGroup == actualGroup) {
			return; // same object, trivial case
		}

		// check the groups have the same NXclass
		if (expectedGroup instanceof NXobject) {
			assertThat(path, actualGroup, Matchers.instanceOf(NXobject.class)); 
			assertThat(path, ((NXobject) actualGroup).getNXclass(), is(equalTo(((NXobject) expectedGroup).getNXclass())));
		}
		
		// check numbers of data nodes and group nodes are the same
		assertThat(path, actualGroup.getNumberOfDataNodes(), is(expectedGroup.getNumberOfDataNodes()));
		assertThat(path, actualGroup.getNumberOfGroupNodes(), is(expectedGroup.getNumberOfGroupNodes()));

		// check number of attributes same (i.e. actualGroup has no additional attributes)
		// The additional attribute "target" is allowed.
		int expectedNumAttributes = expectedGroup.getNumberOfAttributes();
		if (expectedGroup.containsAttribute(TARGET)) {
			if (!actualGroup.containsAttribute(TARGET)) {
				expectedNumAttributes--;
			}
		} else if (actualGroup.containsAttribute(TARGET)) {
			expectedNumAttributes++;
		}
		assertThat(path, actualGroup.getNumberOfAttributes(), is(expectedNumAttributes));
		
		// check attribute properties same for each attribute
		for (String attributeName : expectedGroup.getAttributeNames()) {
			final String attrPath = path + Node.ATTRIBUTE + attributeName;
			final Attribute expectedAttr = expectedGroup.getAttribute(attributeName);
			final Attribute actualAttr = actualGroup.getAttribute(attributeName);
			if (!expectedAttr.getName().equals(TARGET) && !expectedAttr.getName().equals("file_name")) {
				assertThat(attrPath, actualAttr, is(notNullValue()));
				assertAttributesEquals(attrPath, expectedAttr, actualAttr);
			}
		}

		// check child nodes same
		final Iterator<String> nodeNameIterator = expectedGroup.getNodeNameIterator();
		while (nodeNameIterator.hasNext()) {
			final String nodeName = nodeNameIterator.next();
			final String nodePath = path + Node.SEPARATOR + nodeName;
			assertNodesEquals(nodePath, expectedGroup.getNode(nodeName), actualGroup.getNode(nodeName));
		}
	}

	public static void assertDataNodesEqual(final String path,
			final DataNode expectedDataNode, final DataNode actualDataNode) {
		// check number of attributes same (i.e. actualDataNode has no additional attributes)
		// additional attribute "target" is allowed, this is added automatically when saving the file
		int expectedNumAttributes = expectedDataNode.getNumberOfAttributes();
		if (expectedDataNode.containsAttribute(TARGET)) {
			if (!actualDataNode.containsAttribute(TARGET)) {
				expectedNumAttributes--;
			}
		} else if (actualDataNode.containsAttribute(TARGET)) {
			expectedNumAttributes++;
		}
		assertThat(path, actualDataNode.getNumberOfAttributes(), is(expectedNumAttributes));
		
		// check attributes properties same for each attribute
		for (String attributeName : expectedDataNode.getAttributeNames()) {
			String attrPath = path + Node.ATTRIBUTE + attributeName;
			final Attribute expectedAttr = expectedDataNode.getAttribute(attributeName);
			final Attribute actualAttr = actualDataNode.getAttribute(attributeName);
			if (!expectedAttr.getName().equals(TARGET)) {
				assertThat(attrPath, expectedAttr, is(notNullValue()));
				assertAttributesEquals(attrPath, expectedAttr, actualAttr);
			}
		}

		assertThat(path, actualDataNode.getTypeName(), is(equalTo(expectedDataNode.getTypeName())));
		assertThat(path, actualDataNode.isAugmented(), is(expectedDataNode.isAugmented()));
		assertThat(path, actualDataNode.isString(), is(expectedDataNode.isString())); 
		assertThat(path, actualDataNode.isSupported(), is(expectedDataNode.isSupported()));
		assertThat(path, actualDataNode.isUnsigned(), is(expectedDataNode.isUnsigned())); 
		assertThat(path, actualDataNode.getMaxStringLength(), is(expectedDataNode.getMaxStringLength()));
		if (expectedDataNode.getMaxShape() != null) {
			assertThat(path, actualDataNode.getMaxShape(), is(equalTo(expectedDataNode.getMaxShape())));
		} 
		if (expectedDataNode.getChunkShape() != null) { 
			assertThat(path, actualDataNode.getChunkShape(), is(equalTo(expectedDataNode.getChunkShape())));
		}
		assertThat(path, actualDataNode.getString(), is(equalTo(expectedDataNode.getString())));
		assertDatasetsEqual(path, expectedDataNode.getDataset(), actualDataNode.getDataset());
	}

	public static void assertAttributesEquals(final String path, final Attribute expectedAttr,
			final Attribute actualAttr) {
		assertThat(path, actualAttr.getName(), is(equalTo(expectedAttr.getName())));
		assertThat(path, actualAttr.getTypeName(), is(equalTo(expectedAttr.getTypeName())));
		assertThat(path, actualAttr.getFirstElement(), is(equalTo(expectedAttr.getFirstElement())));
		assertThat(path, actualAttr.getSize(), is(expectedAttr.getSize()));
		if (expectedAttr.getSize() == 1 && expectedAttr.getRank() == 1 && actualAttr.getRank() == 0) {
			// TODO fix examples now that we can save scalar (or zero-ranked) datasets
			actualAttr.getValue().setShape(1);
		}
		assertThat(path, actualAttr.getRank(), is(expectedAttr.getRank()));
		assertThat(path, actualAttr.getShape(), is(expectedAttr.getShape()));
		assertDatasetsEqual(path, expectedAttr.getValue(), actualAttr.getValue());
	}

	public static void assertDatasetValue(Object expectedValue, ILazyDataset dataset) {
		assertDatasetsEqual(null, DatasetFactory.createFromObject(expectedValue), dataset);
	}

	public static void assertDatasetsEqual(final String path, final ILazyDataset expectedDataset,
			final ILazyDataset actualDataset) {
		// Note: we permit dataset names and classes to different, as long as the containing data node names are the same
//		assertThat(path, actualDataset.getName(), is(equalTo(expectedDataset.getName())));
//		assertThat(path, actualDataset.getClass(), is(equalTo(expectedDataset.getClass())));
		assertThat(path, actualDataset.getElementClass(), is(equalTo(expectedDataset.getElementClass())));
		assertThat(path, actualDataset.getElementsPerItem(), is(equalTo(expectedDataset.getElementsPerItem()))); 
		assertThat(path, actualDataset.getSize(), is(expectedDataset.getSize())); 
		assertThat(path, actualDataset.getRank(), is(expectedDataset.getRank()));
		assertThat(path, actualDataset.getShape(), is(equalTo(expectedDataset.getShape()))); 
		assertDatasetDataEqual(path, expectedDataset, actualDataset);
	}

	private static void assertDatasetDataEqual(final String path,
			final ILazyDataset expectedDataset, final ILazyDataset actualDataset) {
		if (expectedDataset instanceof Dataset && actualDataset instanceof Dataset) {
			assertThat(path, actualDataset, is(equalTo(expectedDataset))); // uses Dataset.equals() method
		} else {
			// compare the properties of the two datasets
			assertThat(path, actualDataset.getSize(), is(expectedDataset.getSize()));
			if (expectedDataset.getSize() == 0) return;
			
			// getSlice() with no args loads whole dataset if a lazy dataset
			final IDataset expectedSlice;
			final IDataset actualSlice;
			try {
				expectedSlice = expectedDataset.getSlice();
				actualSlice = actualDataset.getSlice();
			} catch (DatasetException e) {
				throw new AssertionError(LAZY_DATA_EXCEPTION + ", path = " + path, e.getCause());
			}

			final Class<? extends Dataset> clazz = InterfaceUtils.getInterface(actualDataset);
			final PositionIterator positionIterator = new PositionIterator(actualDataset.getShape());
			while (positionIterator.hasNext()) {
				int[] position = positionIterator.getPos();
				if (BooleanDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getBoolean(position), is(equalTo(expectedSlice.getBoolean(position))));
				} else if (ByteDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getByte(position), is(equalTo(expectedSlice.getByte(position))));
				} else if (ShortDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getShort(position), is(equalTo(expectedSlice.getShort(position)))); 
				} else if (IntegerDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getInt(position), is(equalTo(expectedSlice.getInt(position))));
				} else if (LongDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getLong(position), is(equalTo(expectedSlice.getLong(position)))); 
				} else if (FloatDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, (double) actualSlice.getFloat(position), closeTo(expectedSlice.getFloat(position), 1e-7));
				} else if (DoubleDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getDouble(position), closeTo(expectedSlice.getDouble(position), 1e-15));
				} else if (StringDataset.class.isAssignableFrom(clazz) || DateDataset.class.isAssignableFrom(clazz)) {
					assertThat(path, actualSlice.getString(position), is(equalTo(expectedSlice.getString(position))));
				} else {
					assertThat(path, actualSlice.getObject(position), is(equalTo(expectedSlice.getObject(position))));
				}
			}
		}
	}
	
	public static void assertSymbolicNodesEqual(final String path,
			final SymbolicNode expectedSymbolicNode, final SymbolicNode actualSymbolicNode) {
		assertThat(path, actualSymbolicNode, is(equalTo(expectedSymbolicNode)));
	}
	
	public static void assertSignal(NXdata nxData, String expectedSignalFieldName) {
		final Attribute signalAttr = nxData.getAttribute(DATA_SIGNAL);
		assertThat(signalAttr, is(notNullValue()));
		assertThat(signalAttr.getRank(), is(0));
		assertThat(signalAttr.getFirstElement(), is(equalTo(expectedSignalFieldName)));
		assertThat(nxData.getNode(expectedSignalFieldName), is(notNullValue()));
	}

	public static void assertAxes(NXdata nxData, String... expectedValues) {
		if (expectedValues.length == 0)
			return; // axes not written if no axes to write (a scalar signal field)
		final Attribute axesAttr = nxData.getAttribute(DATA_AXES);
		assertThat(axesAttr, is(notNullValue()));
		assertThat(axesAttr.getRank(), is(1));
		assertThat(axesAttr.getShape()[0], is(expectedValues.length));
		final IDataset value = axesAttr.getValue();
		for (int i = 0; i < expectedValues.length; i++) {
			assertThat(value.getString(i), is(equalTo(expectedValues[i])));
		}
	}

	public static void assertShape(NXdata nxData, String fieldName, int... expectedShape) {
		final DataNode dataNode = nxData.getDataNode(fieldName);
		assertThat(fieldName, is(notNullValue()));
		final int[] actualShape = dataNode.getDataset().getShape();
		assertThat(actualShape, is(equalTo(expectedShape)));
	}

	public static void assertIndices(NXdata nxData, String axisName, int... indices) {
		final Attribute indicesAttr = nxData.getAttribute(axisName + DATA_INDICES_SUFFIX);
		assertThat(indicesAttr, is(notNullValue()));
		assertThat(indicesAttr.getRank(), is(1));
		assertThat(indicesAttr.getShape()[0], is(indices.length));
		final IDataset value = indicesAttr.getValue();
		for (int i = 0; i < indices.length; i++) {
			assertThat(value.getInt(i), is(equalTo(indices[i])));
		}
	}
	
	public static void assertTarget(NXdata nxData, String destName, NXroot nxRoot, String targetPath) {
		final DataNode dataNode = nxData.getDataNode(destName);
		assertThat(dataNode, is(notNullValue()));
		final Attribute targetAttr = dataNode.getAttribute(TARGET);
		assertThat(targetAttr, is(notNullValue()));
		assertThat(targetAttr.getSize(), is(1));
		assertThat(targetAttr.getFirstElement(), is(equalTo(targetPath)));
		
		final NodeLink nodeLink = nxRoot.findNodeLink(targetPath);
		assertThat(nodeLink.isDestinationData(), is(true));
		assertThat(nodeLink.getDestination(), is(sameInstance(dataNode)));
	}
	
	public static void assertNXTimeStamps(NXobject object) {
		final DataNode startTimeNode = object.getDataNode(NXentry.NX_START_TIME);
		final DataNode endTimeNode = object.getDataNode(NXentry.NX_END_TIME);
		final DataNode durationNode = object.getDataNode(NXentry.NX_DURATION);
		assertScanTimeStamps(startTimeNode, endTimeNode, durationNode);
	}

	public static void assertDiamondScanGroup(NXentry entry, boolean snake, boolean foldedGrid, int... sizes) {
		assertDiamondScanGroup(entry, false, snake, foldedGrid, sizes);
	}

	public static void assertDiamondScanGroup(NXentry entry, boolean malcolmScan, boolean snake, boolean foldedGrid,
			int... sizes) {
		assertDiamondScanGroup(entry, malcolmScan, snake, foldedGrid, null, sizes);
	}

	public static void assertDiamondScanGroup(NXentry entry, boolean malcolmScan, boolean snake, boolean foldedGrid,
			List<String> expectedUniqueKeysPath, int... sizes) {
		assertScanFinished(entry);

		final NXcollection diamondScanCollection = entry.getCollection(GROUP_NAME_DIAMOND_SCAN);
		assertThat(diamondScanCollection, is(notNullValue()));
		assertTimesMatch(entry, diamondScanCollection);

		assertScanShape(diamondScanCollection, sizes);
		assertScanTimes(diamondScanCollection);

		final NXcollection keysCollection = (NXcollection) diamondScanCollection.getGroupNode(GROUP_NAME_KEYS);
		assertThat(keysCollection, is(notNullValue()));

		assertUniqueKeys(malcolmScan, snake, foldedGrid, expectedUniqueKeysPath, keysCollection, sizes);

		assertNXTimeStamps(diamondScanCollection);
		if (!(sizes.length == 0 || malcolmScan)) {
			assertPointTimeStamps(diamondScanCollection, sizes, snake, foldedGrid);
		}
	}
	
	private static void assertTimesMatch(NXentry entry, NXcollection metadataCollection) {
		// We shouldn't have 2 different values for Start/End/Duration
		final String startTimeString = metadataCollection.getString(NXentry.NX_START_TIME);
		final String endTimeString = metadataCollection.getString(NXentry.NX_END_TIME);
		final Long duration = metadataCollection.getLong(NXentry.NX_DURATION);
		assertThat(startTimeString, is(equalTo(entry.getStart_time().getString()))); // scalar field
		assertThat(endTimeString, is(equalTo(entry.getEnd_time().getString()))); // scalar field
		assertThat(duration, is(equalTo(entry.getDurationScalar())));
	}
	
	private static void assertScanTimeStamps(final DataNode startTimeNode, final DataNode endTimeNode,
			final DataNode durationNode) {
		assertThat(startTimeNode, is(notNullValue()));
		assertThat(endTimeNode, is(notNullValue()));
		assertThat(durationNode, is(notNullValue()));

		final IDataset startTimeDataset;
		final IDataset endTimeDataset;
		final IDataset durationDataset;
		try {
			startTimeDataset = startTimeNode.getDataset().getSlice();
			endTimeDataset = endTimeNode.getDataset().getSlice();
			durationDataset = durationNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_TIMESTAMP_EXCEPTION, e);
		}

		assertThat(startTimeDataset.getElementClass(), is(equalTo(String.class)));
		assertThat(endTimeDataset.getElementClass(), is(equalTo(String.class)));
		assertThat(durationDataset.getElementClass(), is(equalTo(Long.class)));

		final DateDataset startTimeDateDataset = DatasetUtils.cast(DateDataset.class, startTimeDataset);
		final DateDataset endTimeDateDataset = DatasetUtils.cast(DateDataset.class, endTimeDataset);
		// As truncating to milliseconds, could end the same milli as starting, so check that end is at least not before start.
		assertThat(endTimeDateDataset.getDate(), is(greaterThanOrEqualTo(startTimeDateDataset.getDate())));
		
		final long scanDuration = Duration.between(startTimeDateDataset.getDate().toInstant(),
				endTimeDateDataset.getDate().toInstant()).toMillis();
		assertThat(durationDataset.getLong(), is(scanDuration));
	}

	private static void assertPointTimeStamps(NXcollection diamondScanCollection, int[] sizes,
			boolean snake, boolean foldedGrid) {
		final DataNode pointStartTimesNode = diamondScanCollection.getDataNode(FIELD_NAME_POINT_START_TIME);
		final DataNode pointEndTimesNode = diamondScanCollection.getDataNode(FIELD_NAME_POINT_END_TIME);

		assertThat(pointStartTimesNode, is(notNullValue()));
		assertThat(pointEndTimesNode, is(notNullValue()));

		final IDataset pointStartTimesDataset;
		final IDataset pointEndTimesDataset;
		try {
			pointStartTimesDataset = pointStartTimesNode.getDataset().getSlice();
			pointEndTimesDataset = pointEndTimesNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_TIMESTAMP_EXCEPTION, e);
		}

		assertThat(pointStartTimesDataset.getElementClass(), is(equalTo(String.class)));
		assertThat(pointEndTimesDataset.getElementClass(), is(equalTo(String.class)));

		assertThat(pointStartTimesDataset.getShape(), is(equalTo(sizes)));
		assertThat(pointEndTimesDataset.getShape(), is(equalTo(sizes)));

		final Dataset startTimes = DatasetUtils.convertToDataset(pointStartTimesDataset);
		final Dataset endTimes = DatasetUtils.convertToDataset(pointEndTimesDataset);
		
		final DateDataset startTimeDateDataset = DatasetUtils.cast(DateDataset.class, startTimes);
		final DateDataset endTimeDateDataset = DatasetUtils.cast(DateDataset.class, endTimes);

		final IndexIterator iterator = startTimes.getIterator(true);

		if (!snake || sizes.length == 1) {
			Date prevEnd = null;
			while (iterator.hasNext()) {
				final Date start = startTimeDateDataset.getDate(iterator.getPos());
				final Date end = endTimeDateDataset.getDate(iterator.getPos());
				assertThat(end, is(greaterThanOrEqualTo(start)));
				if (prevEnd != null) {
					assertThat(start, is(greaterThanOrEqualTo(prevEnd)));
				}
				prevEnd = end;
			}
		} else {
			final Date[] flatStartTimes = flattenSnakeDataset(startTimeDateDataset, foldedGrid, Date.class);
			final Date[] flatEndTimes = flattenSnakeDataset(endTimeDateDataset, foldedGrid, Date.class);

			Date previousEnd = null;
			for (int index = 0; index < flatStartTimes.length; index++) {
				final Date start = flatStartTimes[index];
				final Date end = flatEndTimes[index];
				assertThat(end, is(greaterThanOrEqualTo(start)));
				if (previousEnd != null) {
					assertThat(start, is(greaterThanOrEqualTo(previousEnd)));
				}
				previousEnd = end;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T[] flattenSnakeDataset(IDataset dataset, boolean foldedGrid, Class<T> datasetType) {
		final int[] shape = dataset.getShape();
		int pointIndex = 1;
		int flatArraySize = 1;
		for (int axisPoints : shape) {
			flatArraySize *= axisPoints;
		}
		
		final T[] flatDataset = (T[]) Array.newInstance(datasetType, flatArraySize);
		final PositionIterator iter = new PositionIterator(shape);

		// the PositionIterator iterates through all points top to bottom, left to right
		// whereas the snake scan alternates first horizontally, and then and the end of
		// each inner scan vertically
		final int lineSize = shape[shape.length - 1];
		final int numRows = shape[shape.length - 2];
		final boolean oddNumRows = numRows % 2 == 1;
		final int innerScanSize = lineSize * numRows; // not used for folded grid scans
		boolean isBackwardLine = false;
		boolean isBottomToTopInnerScan = false;
		int expectedLineEnd = lineSize;
		int expectedInnerScanEnd = innerScanSize - (oddNumRows ? 0 : lineSize - 1);
		while (iter.hasNext()) { // hasNext also increments the position iterator (ugh!)
			if (datasetType.equals(Date.class)) {
				// DateDatasetImpl does not override getObject from StringDataset
				flatDataset[pointIndex - 1] = (T) ((DateDataset) dataset).getDate(iter.getPos());
			} else {
				flatDataset[pointIndex - 1] = (T) dataset.getObject(iter.getPos());
			}

			if (!foldedGrid && !isBottomToTopInnerScan && pointIndex == expectedInnerScanEnd) {
				// end of top to bottom inner scan, next is bottom to top
				isBottomToTopInnerScan = true;
				isBackwardLine = true; // top line of bottom to top scan is always backward
				pointIndex += innerScanSize + (oddNumRows ? 0 : lineSize - 1);
				expectedLineEnd = pointIndex - lineSize + 1;
				expectedInnerScanEnd = (pointIndex - innerScanSize) + (oddNumRows ? 1 : lineSize);
			} else if (!foldedGrid && isBottomToTopInnerScan && pointIndex == expectedInnerScanEnd) {
				// end of bottom to top inner scan, next is top to bottom
				isBottomToTopInnerScan = false;
				isBackwardLine = false; // top line of top to bottom scan is always forward
				pointIndex += innerScanSize - (oddNumRows ? 0 : lineSize - 1);
				expectedLineEnd = pointIndex + lineSize - 1;
				expectedInnerScanEnd = pointIndex + innerScanSize - (oddNumRows ? 1 : lineSize);
			} else if (!isBackwardLine && pointIndex == expectedLineEnd) {
				// end of forward line
				isBackwardLine = true; // next line is backward
				pointIndex += (isBottomToTopInnerScan ? -lineSize : lineSize);
				expectedLineEnd += 1;
			} else if (isBackwardLine && pointIndex == expectedLineEnd) {
				// end of backward line
				isBackwardLine = false; // next line is forward
				pointIndex += (isBottomToTopInnerScan ? -lineSize : lineSize);
				expectedLineEnd += (isBottomToTopInnerScan ? -1 : (lineSize * 2) - 1);
			} else if (isBackwardLine) {
				// a point on a backward line
				pointIndex--;
			} else {
				// a point on a forward line
				pointIndex++;
			}
		}
		return flatDataset;
	}

	private static void assertScanShape(NXcollection diamondScanCollection, int... sizes) {
		final DataNode shapeDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_SHAPE);
		assertThat(shapeDataNode, is(notNullValue()));
		final IDataset shapeDataset;
		try {
			shapeDataset = shapeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}
		assertThat(shapeDataset.getElementClass(), is(equalTo(Integer.class)));
		if (sizes.length == 0) {
			assertThat(shapeDataset.getRank(), is(0));
			assertThat(shapeDataset.getShape(), is(equalTo(EMPTY_SHAPE)));
		} else {
			assertThat(shapeDataset.getRank(), is(1));
			assertThat(shapeDataset.getShape(), is(equalTo(new int[] { sizes.length })));
			for (int i = 0; i < sizes.length; i++) {
				assertThat(shapeDataset.getInt(i), is(equalTo(sizes[i])));
			}
		}
	}

	private static void assertScanTimes(NXcollection diamondScanCollection) {
		// check the estimated scan duration dataset
		final DataNode estimatedTimeDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_ESTIMATED_DURATION);
		assertThat(estimatedTimeDataNode, is(notNullValue()));
		final IDataset estimatedTimeDataset;
		try {
			estimatedTimeDataset = estimatedTimeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		assertThat(estimatedTimeDataset.getElementClass(), is(equalTo(Long.class)));
		assertThat(estimatedTimeDataset.getRank(), is(0));
		assertThat(estimatedTimeDataset.getShape(), is(EMPTY_SHAPE));
		final long estimatedTime = estimatedTimeDataset.getLong();

		// check the actual scan duration dataset
		final DataNode actualTimeDataNode = diamondScanCollection.getDataNode(NXentry.NX_DURATION);
		assertThat(actualTimeDataNode, is(notNullValue()));
		IDataset actualTimeDataset;
		try {
			actualTimeDataset = actualTimeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		assertThat(actualTimeDataset.getElementClass(), is(equalTo(Long.class))); 
		assertThat(actualTimeDataset.getRank(), is(0));
		assertThat(actualTimeDataset.getShape(), is(equalTo(EMPTY_SHAPE)));
		
		final long scanDurationMs = actualTimeDataset.getLong();

		// check the scan dead time dataset
		final DataNode deadTimeDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_DEAD_TIME);
		assertThat(deadTimeDataNode, is(notNullValue()));
		final IDataset deadTimeDataset;
		try {
			deadTimeDataset = deadTimeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		// written as a 1d dataset of rank 1, as we can't write a scalar lazy writeable
		// dataset
		assertThat(deadTimeDataset.getElementClass(), is(equalTo(Long.class)));
		assertThat(deadTimeDataset.getRank(), is(0));
		assertThat(deadTimeDataset.getShape(), is(equalTo(EMPTY_SHAPE)));
		final long deadTime = deadTimeDataset.getLong();

		// The scan duration should be equal to the estimated time plus the dead time
		assertThat(scanDurationMs, is(equalTo(estimatedTime + deadTime)));

		// check the percentage dead time
		final DataNode deadTimePercentDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_DEAD_TIME_PERCENT);
		assertThat(deadTimePercentDataNode, is(notNullValue()));
		IDataset deadTimePercentDataset;
		try {
			deadTimePercentDataset = deadTimePercentDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		assertThat(deadTimePercentDataset.getElementClass(), is(equalTo(Float.class)));
		assertThat(deadTimePercentDataset.getRank(), is(0));
		assertThat(deadTimePercentDataset.getShape(), is(equalTo(EMPTY_SHAPE)));
		final double deadTimePercent = deadTimePercentDataset.getFloat();

		assertThat(deadTimePercent, is(closeTo(100 * ((double) deadTime / scanDurationMs), 0.001)));
		
	}

	private static void assertUniqueKeys(boolean malcolmScan, boolean snake, boolean foldedGrid,
			List<String> expectedUniqueKeysPath, NXcollection keysCollection, int[] sizes) {
		if (sizes.length == 0) {
			sizes = new int[] { 1 };
		}
		if (!malcolmScan) {
			assertUniqueKeys(keysCollection, snake, foldedGrid, sizes);
		}
		if (expectedUniqueKeysPath != null && !expectedUniqueKeysPath.isEmpty()) {
			assertUniqueKeysExternalFileLinks(keysCollection, expectedUniqueKeysPath, sizes);
		}
	}

	private static void assertUniqueKeys(NXcollection keysCollection, boolean snake, boolean foldedGrid, int[] sizes) {
		// workaround for StaticGenerator with StaticModel of size 1 producing scan of
		// rank 1 and shape { 1 }
		if (sizes.length == 0)
			sizes = new int[] { 1 };

		// check the unique keys field - contains the step number for each scan point
		final DataNode dataNode = keysCollection.getDataNode(FIELD_NAME_UNIQUE_KEYS);
		assertThat(dataNode, is(notNullValue()));
		IDataset dataset;
		try {
			dataset = dataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}
		assertThat(dataset, is(instanceOf(IntegerDataset.class)));
		assertThat(dataset.getRank(), is(sizes.length));
		final int[] shape = dataset.getShape();
		assertThat(shape, is(equalTo(sizes)));

		// iterate through the points
		int expectedPos = 1;
		final PositionIterator iter = new PositionIterator(shape);
		if (!snake || sizes.length == 1) {
			// not a snake scan, the order of points will be the same as the position
			// iterator gives them
			while (iter.hasNext()) { // hasNext also increments the position iterator (ugh!)
				assertThat(dataset.getInt(iter.getPos()), is(expectedPos));
				expectedPos++;
			}
		} else {
			// iterate through the points comparing them with their expected values
			// the PositionIterator iterates through all points top to bottom, left to right
			// whereas the snake scan alternates first horizontally, and then and the end of
			// each inner scan vertically
			final int lineSize = shape[shape.length - 1];
			final int numRows = shape[shape.length - 2];
			final boolean oddNumRows = numRows % 2 == 1;
			final int innerScanSize = lineSize * numRows; // not used for folded grid scans
			boolean isBackwardLine = false;
			boolean isBottomToTopInnerScan = false;
			int expectedLineEnd = lineSize;
			int expectedInnerScanEnd = innerScanSize - (oddNumRows ? 0 : lineSize - 1);
			while (iter.hasNext()) { // hasNext also increments the position iterator (ugh!)
				assertThat(dataset.getInt(iter.getPos()), is(expectedPos));

				if (!foldedGrid && !isBottomToTopInnerScan && expectedPos == expectedInnerScanEnd) {
					// end of top to bottom inner scan, next is bottom to top
					isBottomToTopInnerScan = true;
					isBackwardLine = true; // top line of bottom to top scan is always backward
					expectedPos += innerScanSize + (oddNumRows ? 0 : lineSize - 1);
					expectedLineEnd = expectedPos - lineSize + 1;
					expectedInnerScanEnd = (expectedPos - innerScanSize) + (oddNumRows ? 1 : lineSize);
				} else if (!foldedGrid && isBottomToTopInnerScan && expectedPos == expectedInnerScanEnd) {
					// end of bottom to top inner scan, next is top to bottom
					isBottomToTopInnerScan = false;
					isBackwardLine = false; // top line of top to bottom scan is always forward
					expectedPos += innerScanSize - (oddNumRows ? 0 : lineSize - 1);
					expectedLineEnd = expectedPos + lineSize - 1;
					expectedInnerScanEnd = expectedPos + innerScanSize - (oddNumRows ? 1 : lineSize);
				} else if (!isBackwardLine && expectedPos == expectedLineEnd) {
					// end of forward line
					isBackwardLine = true; // next line is backward
					expectedPos += (isBottomToTopInnerScan ? -lineSize : lineSize);
					expectedLineEnd += 1;
				} else if (isBackwardLine && expectedPos == expectedLineEnd) {
					// end of backward line
					isBackwardLine = false; // next line is forward
					expectedPos += (isBottomToTopInnerScan ? -lineSize : lineSize);
					expectedLineEnd += (isBottomToTopInnerScan ? -1 : (lineSize * 2) - 1);
				} else if (isBackwardLine) {
					// a point on a backward line
					expectedPos--;
				} else {
					// a point on a forward line
					expectedPos++;
				}
			}
		}
	}

	private static void assertUniqueKeysExternalFileLinks(NXcollection keysCollection,
			List<String> expectedUniquekeyPaths, int[] sizes) {
		for (String expectedUniquekeyPath : expectedUniquekeyPaths) {
			final DataNode dataNode = keysCollection.getDataNode(expectedUniquekeyPath);
			assertThat(dataNode, is(notNullValue()));
			assertThat(dataNode.getRank(), is(sizes.length));
		}
	}

	public static void assertScanFinished(NXentry entry) {
		assertScanFinished(entry, true);
	}

	public static void assertScanNotFinished(NXentry entry) {
		assertScanFinished(entry, false);
	}

	private static void assertScanFinished(NXentry entry, boolean finished) {
		final NXcollection scanPointsCollection = entry.getCollection(GROUP_NAME_DIAMOND_SCAN);
		assertThat(scanPointsCollection, is(notNullValue()));

		// check the scan finished boolean is set to true
		final DataNode dataNode = scanPointsCollection.getDataNode(FIELD_NAME_SCAN_FINISHED);
		assertThat(dataNode, is(notNullValue()));
		final IDataset dataset;
		try {
			dataset = dataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}
		assertThat(dataset, is(instanceOf(IntegerDataset.class))); // HDF5 doesn't support boolean datasets
		assertThat(dataset.getRank(), is(1));
		assertThat(dataset.getShape(), is(SINGLE_SHAPE));
		assertThat(dataset.getBoolean(0), is(finished));
	}
	
	public static void assertUnits(NXobject nexusObject, String fieldName, String expectedUnits) {
		final DataNode dataNode = nexusObject.getDataNode(fieldName);
		assertThat(dataNode, is(notNullValue()));
		assertUnits(dataNode, expectedUnits);
	}
	
	public static void assertUnits(DataNode dataNode, String expectedUnits) {
		if (expectedUnits == null) {
			assertThat(dataNode.getAttribute(ATTR_NAME_UNITS), is(nullValue()));
		} else {
			final Attribute expectedUnitsAttr = TreeFactory.createAttribute(ATTR_NAME_UNITS, expectedUnits);
			final Attribute actualUnitsAttr = dataNode.getAttribute(ATTR_NAME_UNITS);
			assertThat("units not specified, expected "  + expectedUnits, actualUnitsAttr, is(notNullValue()));
			assertAttributesEquals(null, expectedUnitsAttr, actualUnitsAttr);
		}
	}
	
	public static void assertNXentryMetadata(NXentry entry) {
		assertThat(entry.getExperiment_identifierScalar(), is(equalTo(MOCK_VISIT_ID)));
		assertNXTimeStamps(entry);
	}
	
}
