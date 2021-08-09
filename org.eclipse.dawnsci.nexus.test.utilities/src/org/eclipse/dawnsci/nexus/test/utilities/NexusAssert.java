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

import static org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder.ATTR_NAME_AXES;
import static org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder.ATTR_NAME_SIGNAL;
import static org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder.ATTR_NAME_TARGET;
import static org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder.ATTR_SUFFIX_INDICES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.junit.Assert;

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
	
	private static final int[] SINGLE_SHAPE = new int[] { 1 };
	private static final int[] EMPTY_SHAPE = new int[] { };
	
	private NexusAssert() {
		// Hide implicit constructor
	}

	public static void assertNexusTreesEqual(final TreeFile expectedTree, final TreeFile actualTree) throws Exception {
		assertGroupNodesEqual("/", expectedTree.getGroupNode(), actualTree.getGroupNode());
	}
	
	public static void assertNodesEquals(String path, final Node expectedNode, final Node actualNode) throws Exception {
		if (expectedNode.isGroupNode()) {
			assertTrue(path, actualNode.isGroupNode());
			assertGroupNodesEqual(path, (GroupNode) expectedNode, (GroupNode) actualNode);
		} else if (expectedNode.isDataNode()) {
			assertTrue(path, actualNode.isDataNode());
			assertDataNodesEqual(path, (DataNode) expectedNode, (DataNode) actualNode);
		} else if (expectedNode.isSymbolicNode()) {
			assertTrue(path, actualNode.isSymbolicNode());
			assertSymbolicNodesEqual(path, (SymbolicNode) expectedNode, (SymbolicNode) actualNode);
		} else {
			fail("Unknown node type"); // sanity check, shouldn't be possible
		}
	}

	public static void assertGroupNodesEqual(String path,
			final GroupNode expectedGroup, final GroupNode actualGroup) throws Exception {
		if (expectedGroup == actualGroup) {
			return; // same object, trivial case
		}

		// check the groups have the same NXclass
		if (expectedGroup instanceof NXobject) {
			assertTrue(path, actualGroup instanceof NXobject);
			assertEquals(path, ((NXobject) expectedGroup).getNXclass(), ((NXobject) actualGroup).getNXclass());
		}
		
		// check numbers of data nodes and group nodes are the same
		assertEquals(path, expectedGroup.getNumberOfDataNodes(), actualGroup.getNumberOfDataNodes());
		assertEquals(path, expectedGroup.getNumberOfGroupNodes(), actualGroup.getNumberOfGroupNodes());

		// check number of attributes same (i.e. actualGroup has no additional attributes)
		// The additional attribute "target" is allowed.
		int expectedNumAttributes = expectedGroup.getNumberOfAttributes();
		if (expectedGroup.containsAttribute(ATTR_NAME_TARGET)) {
			if (!actualGroup.containsAttribute(ATTR_NAME_TARGET)) {
				expectedNumAttributes--;
			}
		} else if (actualGroup.containsAttribute(ATTR_NAME_TARGET)) {
			expectedNumAttributes++;
		}
		assertEquals(path, expectedNumAttributes, actualGroup.getNumberOfAttributes());
		
		// check attribute properties same for each attribute
		Iterator<String> attributeNameIterator = expectedGroup.getAttributeNameIterator();
		while (attributeNameIterator.hasNext()) {
			String attributeName = attributeNameIterator.next();
			String attrPath = path + Node.ATTRIBUTE + attributeName;
			Attribute expectedAttr = expectedGroup.getAttribute(attributeName);
			Attribute actualAttr = actualGroup.getAttribute(attributeName);
			if (!expectedAttr.getName().equals(ATTR_NAME_TARGET) && !expectedAttr.getName().equals("file_name")) {
				assertNotNull(attrPath, actualAttr);
				assertAttributesEquals(attrPath, expectedAttr, actualAttr);
			}
		}

		// check child nodes same
		final Iterator<String> nodeNameIterator = expectedGroup.getNodeNameIterator();
		while (nodeNameIterator.hasNext()) {
			String nodeName = nodeNameIterator.next();
			String nodePath = path + "/" + nodeName;
			assertNodesEquals(nodePath, expectedGroup.getNode(nodeName), actualGroup.getNode(nodeName));
		}
	}

	public static void assertDataNodesEqual(final String path,
			final DataNode expectedDataNode, final DataNode actualDataNode) {
		// check number of attributes same (i.e. actualDataNode has no additional attributes)
		// additional attribute "target" is allowed, this is added automatically when saving the file
		int expectedNumAttributes = expectedDataNode.getNumberOfAttributes();
		if (expectedDataNode.containsAttribute(ATTR_NAME_TARGET)) {
			if (!actualDataNode.containsAttribute(ATTR_NAME_TARGET)) {
				expectedNumAttributes--;
			}
		} else if (actualDataNode.containsAttribute(ATTR_NAME_TARGET)) {
			expectedNumAttributes++;
		}
		assertEquals(expectedNumAttributes, actualDataNode.getNumberOfAttributes());
		
		// check attributes properties same for each attribute
		Iterator<String> attributeNameIterator = expectedDataNode.getAttributeNameIterator();
		while (attributeNameIterator.hasNext()) {
			String attributeName = attributeNameIterator.next();
			String attrPath = path + Node.ATTRIBUTE + attributeName;
			Attribute expectedAttr = expectedDataNode.getAttribute(attributeName);
			Attribute actualAttr = actualDataNode.getAttribute(attributeName);
			if (!expectedAttr.getName().equals(ATTR_NAME_TARGET)) {
				assertNotNull(attrPath, expectedAttr);
				assertAttributesEquals(attrPath, expectedAttr, actualAttr);
			}
		}

		assertEquals(path, expectedDataNode.getTypeName(), actualDataNode.getTypeName());
		assertEquals(path, expectedDataNode.isAugmented(), actualDataNode.isAugmented());
		assertEquals(path, expectedDataNode.isString(), actualDataNode.isString());
		assertEquals(path, expectedDataNode.isSupported(), actualDataNode.isSupported());
		assertEquals(path, expectedDataNode.isUnsigned(), actualDataNode.isUnsigned());
		assertEquals(path, expectedDataNode.getMaxStringLength(), actualDataNode.getMaxStringLength());
		// TODO reinstate lines below and check why they break - actualDataNode is null
//		assertArrayEquals(path, expectedDataNode.getMaxShape(), actualDataNode.getMaxShape());
//		assertArrayEquals(path, expectedDataNode.getChunkShape(), actualDataNode.getChunkShape());
		assertEquals(path, expectedDataNode.getString(), actualDataNode.getString());
		assertDatasetsEqual(path, expectedDataNode.getDataset(), actualDataNode.getDataset());
	}

	public static void assertAttributesEquals(final String path, final Attribute expectedAttr,
			final Attribute actualAttr) {
		assertEquals(path, expectedAttr.getName(), actualAttr.getName());
		assertEquals(path, expectedAttr.getTypeName(), actualAttr.getTypeName());
		assertEquals(path, expectedAttr.getFirstElement(), actualAttr.getFirstElement());
		assertEquals(path, expectedAttr.getSize(), actualAttr.getSize());
		if (expectedAttr.getSize() == 1 && expectedAttr.getRank() == 1 && actualAttr.getRank() == 0) {
			// TODO fix examples now that we can save scalar (or zero-ranked) datasets
			actualAttr.getValue().setShape(1);
		}
		assertEquals(path, expectedAttr.getRank(), actualAttr.getRank());
		assertArrayEquals(path, expectedAttr.getShape(), actualAttr.getShape());
		assertDatasetsEqual(path, expectedAttr.getValue(), actualAttr.getValue());
	}

	public static void assertDatasetValue(Object expectedValue, ILazyDataset dataset) {
		assertDatasetsEqual(null, DatasetFactory.createFromObject(expectedValue), dataset);
	}

	public static void assertDatasetsEqual(final String path, final ILazyDataset expectedDataset,
			final ILazyDataset actualDataset) {
		// Note: dataset names can be different, as long as the containing data node names are the same
//		assertEquals(path, expectedDataset.getName(), actualDataset.getName());
//		assertEquals(path, expectedDataset.getClass(), actualDataset.getClass());
		assertEquals(path, expectedDataset.getElementClass(), actualDataset.getElementClass());
		assertEquals(path, expectedDataset.getElementsPerItem(), actualDataset.getElementsPerItem());
		assertEquals(path, expectedDataset.getSize(), actualDataset.getSize());
		assertEquals(path, expectedDataset.getRank(), actualDataset.getRank());
		assertArrayEquals(path, expectedDataset.getShape(), actualDataset.getShape());
		assertDatasetDataEqual(path, expectedDataset, actualDataset);

		// TODO: in future also check metadata
	}

	private static void assertDatasetDataEqual(final String path,
			final ILazyDataset expectedDataset, final ILazyDataset actualDataset) {
		if (expectedDataset instanceof Dataset && actualDataset instanceof Dataset) {
			assertEquals(path, expectedDataset, actualDataset); // uses Dataset.equals() method
		} else {
			assertEquals(expectedDataset.getSize(), actualDataset.getSize());
			if (expectedDataset.getSize() == 0) {
				return;
			}
			
			// getSlice() with no args loads whole dataset if a lazy dataset
			IDataset expectedSlice;
			IDataset actualSlice;
			try {
				expectedSlice = expectedDataset.getSlice();
				actualSlice = actualDataset.getSlice();
			} catch (DatasetException e) {
				throw new AssertionError(LAZY_DATA_EXCEPTION, e.getCause());
			}

			Class<? extends Dataset> clazz = InterfaceUtils.getInterface(actualDataset);
			PositionIterator positionIterator = new PositionIterator(actualDataset.getShape());
			while (positionIterator.hasNext()) {
				int[] position = positionIterator.getPos();
				if (BooleanDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getBoolean(position), actualSlice.getBoolean(position));
				} else if (ByteDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getByte(position), actualSlice.getByte(position));
				} else if (ShortDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getShort(position), actualSlice.getShort(position));
				} else if (IntegerDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getInt(position), actualSlice.getInt(position));
				} else if (LongDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getLong(position), actualSlice.getLong(position));
				} else if (FloatDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getFloat(position), actualSlice.getFloat(position), 1e-7);
				} else if (DoubleDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getDouble(position), actualSlice.getDouble(position), 1e-15);
				} else if (StringDataset.class.isAssignableFrom(clazz) || DateDataset.class.isAssignableFrom(clazz)) {
					assertEquals(path, expectedSlice.getString(position), actualSlice.getString(position));
				} else {
					assertEquals(path, expectedSlice.getObject(position), actualSlice.getObject(position));
				}
			}
		}
	}
	
	public static void assertSymbolicNodesEqual(final String path,
			final SymbolicNode expectedSymbolicNode, final SymbolicNode actualSymbolicNode) {
		assertEquals(path, expectedSymbolicNode, actualSymbolicNode);
	}
	
	public static void assertSignal(NXdata nxData, String expectedSignalFieldName) {
		Attribute signalAttr = nxData.getAttribute(ATTR_NAME_SIGNAL);
		assertThat(signalAttr, is(notNullValue()));
		assertThat(signalAttr.getRank(), is(0));
		assertThat(signalAttr.getFirstElement(), is(equalTo(expectedSignalFieldName)));
		assertThat(nxData.getNode(expectedSignalFieldName), is(notNullValue()));
	}

	public static void assertAxes(NXdata nxData, String... expectedValues) {
		if (expectedValues.length == 0)
			return; // axes not written if no axes to write (a scalar signal field)
		Attribute axesAttr = nxData.getAttribute(ATTR_NAME_AXES);
		assertThat(axesAttr, is(notNullValue()));
		assertThat(axesAttr.getRank(), is(1));
		assertThat(axesAttr.getShape()[0], is(expectedValues.length));
		IDataset value = axesAttr.getValue();
		for (int i = 0; i < expectedValues.length; i++) {
			assertThat(value.getString(i), is(equalTo(expectedValues[i])));
		}
	}

	public static void assertShape(NXdata nxData, String fieldName, int... expectedShape) {
		DataNode dataNode = nxData.getDataNode(fieldName);
		assertThat(fieldName, is(notNullValue()));
		int[] actualShape = dataNode.getDataset().getShape();
		assertArrayEquals(expectedShape, actualShape);
	}

	public static void assertIndices(NXdata nxData, String axisName, int... indices) {
		Attribute indicesAttr = nxData.getAttribute(axisName + ATTR_SUFFIX_INDICES);
		assertThat(indicesAttr, is(notNullValue()));
		assertThat(indicesAttr.getRank(), is(1));
		assertThat(indicesAttr.getShape()[0], is(indices.length));
		IDataset value = indicesAttr.getValue();
		for (int i = 0; i < indices.length; i++) {
			assertThat(value.getInt(i), is(equalTo(indices[i])));
		}
	}
	
	public static void assertTarget(NXdata nxData, String destName, NXroot nxRoot, String targetPath) {
		DataNode dataNode = nxData.getDataNode(destName);
		assertThat(dataNode, is(notNullValue()));
		Attribute targetAttr = dataNode.getAttribute(ATTR_NAME_TARGET);
		assertThat(targetAttr, is(notNullValue()));
		assertThat(targetAttr.getSize(), is(1));
		assertThat(targetAttr.getFirstElement(), is(equalTo(targetPath)));
		
		NodeLink nodeLink = nxRoot.findNodeLink(targetPath);
		assertTrue(nodeLink.isDestinationData());
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
			List<String> expectedExternalFiles, int... sizes) {
		assertScanFinished(entry);

		final NXcollection diamondScanCollection = entry.getCollection(GROUP_NAME_DIAMOND_SCAN);
		assertNotNull(diamondScanCollection);
		assertTimesMatch(entry, diamondScanCollection);

		assertScanShape(diamondScanCollection, sizes);
		assertScanTimes(diamondScanCollection);

		final NXcollection keysCollection = (NXcollection) diamondScanCollection.getGroupNode(GROUP_NAME_KEYS);
		assertNotNull(keysCollection);

		// workaround for StaticGenerator with StaticModel of size 1 producing scan of
		// rank 1 and shape { 1 }
		assertUniqueKeys(malcolmScan, snake, foldedGrid, expectedExternalFiles, keysCollection, sizes);

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
		assertThat(startTimeString, is(equalTo(entry.getStart_time().getString(0))));
		assertThat(endTimeString, is(equalTo(entry.getEnd_time().getString(0))));
		assertThat(duration, is(equalTo(entry.getDurationScalar())));
	}
	
	private static void assertScanTimeStamps(final DataNode startTimeNode, final DataNode endTimeNode,
			final DataNode durationNode) {
		assertNotNull(startTimeNode);
		assertNotNull(endTimeNode);
		assertNotNull(durationNode);

		IDataset startTimeDataset, endTimeDataset, durationDataset;
		try {
			startTimeDataset = startTimeNode.getDataset().getSlice();
			endTimeDataset = endTimeNode.getDataset().getSlice();
			durationDataset = durationNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_TIMESTAMP_EXCEPTION, e);
		}

		assertEquals(String.class, startTimeDataset.getElementClass());
		assertEquals(String.class, endTimeDataset.getElementClass());
		assertEquals(Long.class, durationDataset.getElementClass());

		final DateDataset startTimeDateDataset = DatasetUtils.cast(DateDataset.class, startTimeDataset);
		final DateDataset endTimeDateDataset = DatasetUtils.cast(DateDataset.class, endTimeDataset);
		// As truncating to milliseconds, could end the same milli as starting, so check that end is at least not before start.
		assertTrue(!endTimeDateDataset.getDate().before(startTimeDateDataset.getDate()));

		final long scanDuration = Duration.between(startTimeDateDataset.getDate().toInstant(), 
				endTimeDateDataset.getDate().toInstant()).toMillis();		
		assertEquals(scanDuration, durationDataset.getLong(0));
		
	}

	private static void assertPointTimeStamps(NXcollection diamondScanCollection, int[] sizes,
			boolean snake, boolean foldedGrid) {
		final DataNode pointStartTimesNode = diamondScanCollection.getDataNode(FIELD_NAME_POINT_START_TIME);
		final DataNode pointEndTimesNode = diamondScanCollection.getDataNode(FIELD_NAME_POINT_END_TIME);

		assertNotNull(pointStartTimesNode);
		assertNotNull(pointEndTimesNode);

		IDataset pointStartTimesDataset, pointEndTimesDataset;
		try {
			pointStartTimesDataset = pointStartTimesNode.getDataset().getSlice();
			pointEndTimesDataset = pointEndTimesNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_TIMESTAMP_EXCEPTION, e);
		}

		assertEquals(String.class, pointStartTimesDataset.getElementClass());
		assertEquals(String.class, pointEndTimesDataset.getElementClass());

		assertArrayEquals(sizes, pointStartTimesDataset.getShape());
		assertArrayEquals(sizes, pointEndTimesDataset.getShape());

		final Dataset startTimes = DatasetUtils.convertToDataset(pointStartTimesDataset);
		final Dataset endTimes = DatasetUtils.convertToDataset(pointEndTimesDataset);
		
		final DateDataset startTimeDateDataset = DatasetUtils.cast(DateDataset.class, startTimes);
		final DateDataset endTimeDateDataset = DatasetUtils.cast(DateDataset.class, endTimes);

		final IndexIterator iterator = startTimes.getIterator(true);

		if (!snake || sizes.length == 1) {
			Date prevEnd = null;
			while (iterator.hasNext()) {
				Date start = startTimeDateDataset.getDate(iterator.getPos());
				Date end = endTimeDateDataset.getDate(iterator.getPos());
				assertTrue(!end.before(start));
				if (prevEnd != null) {
					assertTrue(!start.before(prevEnd));
				}
				prevEnd = end;
			}
		} else {
			final Date[] flatStartTimes = flattenSnakeDataset(startTimeDateDataset, foldedGrid, Date.class);
			final Date[] flatEndTimes = flattenSnakeDataset(endTimeDateDataset, foldedGrid, Date.class);

			Date previousEnd = null;
			for (int index = 0; index < flatStartTimes.length; index++) {
				Date start = flatStartTimes[index];
				Date end = flatEndTimes[index];
				assertTrue(!start.after(end));
				if (previousEnd != null) {
					assertTrue(!previousEnd.after(start));
				}
				previousEnd = end;
			}
		}

	}

	@SuppressWarnings("unchecked")
	private static <T> T[] flattenSnakeDataset(IDataset dataset, boolean foldedGrid, Class<T> datasetType) {
		int pointIndex = 1;
		int[] shape = dataset.getShape();
		int flatArraySize = 1;
		for (int axisPoints : shape) {
			flatArraySize *= axisPoints;
		}
		
		T[] flatDataset = (T[]) Array.newInstance(datasetType, flatArraySize);
		PositionIterator iter = new PositionIterator(shape);

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
		assertNotNull(shapeDataNode);
		final IDataset shapeDataset;
		try {
			shapeDataset = shapeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}
		assertEquals(Integer.class, shapeDataset.getElementClass());
		if (sizes.length == 0) {
			// TODO remove this workaround when january updated
			assertEquals(0, shapeDataset.getRank());
			assertArrayEquals(new int[0], shapeDataset.getShape());
		} else {
			assertEquals(1, shapeDataset.getRank());
			assertArrayEquals(new int[] { sizes.length }, shapeDataset.getShape());
			for (int i = 0; i < sizes.length; i++) {
				assertEquals(sizes[i], shapeDataset.getInt(i));
			}
		}
	}

	private static void assertScanTimes(NXcollection diamondScanCollection) {
		// check the estimated scan duration dataset
		final DataNode estimatedTimeDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_ESTIMATED_DURATION);
		assertNotNull(estimatedTimeDataNode);
		final IDataset estimatedTimeDataset;
		try {
			estimatedTimeDataset = estimatedTimeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		assertEquals(Long.class, estimatedTimeDataset.getElementClass());
		assertEquals(0, estimatedTimeDataset.getRank());
		assertArrayEquals(EMPTY_SHAPE, estimatedTimeDataset.getShape());
		final long estimatedtime = estimatedTimeDataset.getLong();

		// check the actual scan duration dataset
		final DataNode actualTimeDataNode = diamondScanCollection.getDataNode(NXentry.NX_DURATION);
		assertNotNull(actualTimeDataNode);
		IDataset actualTimeDataset;
		try {
			actualTimeDataset = actualTimeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		// written as a 1d dataset of rank 1, as we can't write a scalar lazy writeable
		// dataset
		// TODO: is this now possible?
		assertEquals(Long.class, actualTimeDataset.getElementClass());
		assertEquals(1, actualTimeDataset.getRank());
		assertArrayEquals(SINGLE_SHAPE, actualTimeDataset.getShape());
		
		final long scanDurationMs = actualTimeDataset.getLong(0);

		// check the scan dead time dataset
		final DataNode deadTimeDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_DEAD_TIME);
		assertNotNull(deadTimeDataNode);
		final IDataset deadTimeDataset;
		try {
			deadTimeDataset = deadTimeDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		// written as a 1d dataset of rank 1, as we can't write a scalar lazy writeable
		// dataset
		assertEquals(Long.class, deadTimeDataset.getElementClass());
		assertEquals(1, deadTimeDataset.getRank());
		assertArrayEquals(SINGLE_SHAPE, deadTimeDataset.getShape());
		final long deadTime = deadTimeDataset.getLong(0);

		// The scan duration should be equal to the estimated time plus the dead time
		assertEquals(estimatedtime + deadTime, scanDurationMs);

		// check the percentage dead time
		final DataNode deadTimePercentDataNode = diamondScanCollection.getDataNode(FIELD_NAME_SCAN_DEAD_TIME_PERCENT);
		assertNotNull(deadTimePercentDataNode);
		IDataset deadTimePercentDataset;
		try {
			deadTimePercentDataset = deadTimePercentDataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}

		assertEquals(Float.class, deadTimePercentDataset.getElementClass());
		assertEquals(1, deadTimePercentDataset.getRank());
		assertArrayEquals(SINGLE_SHAPE, deadTimePercentDataset.getShape());
		final float deadTimePercent = deadTimePercentDataset.getFloat(0);

		assertEquals((float) deadTime / scanDurationMs, deadTimePercent / 100, 0.001);
	}

	private static void assertUniqueKeys(boolean malcolmScan, boolean snake, boolean foldedGrid,
			List<String> expectedExternalFiles, NXcollection keysCollection, int[] sizes) {
		if (sizes.length == 0) {
			sizes = new int[] { 1 };
		}
		if (!malcolmScan) {
			assertUniqueKeys(keysCollection, snake, foldedGrid, sizes);
		}
		if (expectedExternalFiles != null && !expectedExternalFiles.isEmpty()) {
			assertUniqueKeysExternalFileLinks(keysCollection, expectedExternalFiles, sizes);
		}
	}

	private static void assertUniqueKeys(NXcollection keysCollection, boolean snake, boolean foldedGrid, int[] sizes) {
		// workaround for StaticGenerator with StaticModel of size 1 producing scan of
		// rank 1 and shape { 1 }
		if (sizes.length == 0)
			sizes = new int[] { 1 };

		// check the unique keys field - contains the step number for each scan point
		DataNode dataNode = keysCollection.getDataNode(FIELD_NAME_UNIQUE_KEYS);
		assertNotNull(dataNode);
		IDataset dataset;
		try {
			dataset = dataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}
		assertTrue(dataset instanceof IntegerDataset);
		assertEquals(sizes.length, dataset.getRank());
		final int[] shape = dataset.getShape();
		assertArrayEquals(sizes, shape);

		// iterate through the points
		int expectedPos = 1;
		PositionIterator iter = new PositionIterator(shape);
		if (!snake || sizes.length == 1) {
			// not a snake scan, the order of points will be the same as the position
			// iterator gives them
			while (iter.hasNext()) { // hasNext also increments the position iterator (ugh!)
				assertEquals(expectedPos, dataset.getInt(iter.getPos()));
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
				assertEquals(expectedPos, dataset.getInt(iter.getPos()));

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
			List<String> expectedExternalFiles, int[] sizes) {
		for (String externalFileName : expectedExternalFiles) {
			String datasetName = externalFileName.replace("/", "__");
			DataNode dataNode = keysCollection.getDataNode(datasetName);
			assertNotNull(dataNode);
			assertEquals(sizes.length, dataNode.getRank());
		}
	}

	public static void assertScanFinished(NXentry entry) {
		assertScanFinished(entry, true);
	}

	public static void assertScanNotFinished(NXentry entry) {
		assertScanFinished(entry, false);
	}

	private static void assertScanFinished(NXentry entry, boolean finished) {
		NXcollection scanPointsCollection = entry.getCollection(GROUP_NAME_DIAMOND_SCAN);
		assertNotNull(scanPointsCollection);

		// check the scan finished boolean is set to true
		DataNode dataNode = scanPointsCollection.getDataNode(FIELD_NAME_SCAN_FINISHED);
		assertNotNull(dataNode);
		IDataset dataset;
		try {
			dataset = dataNode.getDataset().getSlice();
		} catch (DatasetException e) {
			throw new AssertionError(LAZY_DATA_EXCEPTION, e);
		}
		assertTrue(dataset instanceof IntegerDataset); // HDF5 doesn't support boolean datasets
		assertEquals(1, dataset.getRank());
		assertArrayEquals(new int[] { 1 }, dataset.getShape());
		assertEquals(finished, dataset.getBoolean(0));
	}
	
	public static void assertUnits(NXobject nexusObject, String fieldName, String expectedUnits) {
		final DataNode dataNode = nexusObject.getDataNode(fieldName);
		assertNotNull(dataNode);
		assertUnits(dataNode, expectedUnits);
	}
	
	public static void assertUnits(DataNode dataNode, String expectedUnits) {
		if (expectedUnits == null) {
			assertNull(dataNode.getAttribute(ATTR_NAME_UNITS));
		} else {
			final Attribute expectedUnitsAttr = TreeFactory.createAttribute(ATTR_NAME_UNITS, expectedUnits);
			final Attribute actualUnitsAttr = dataNode.getAttribute(ATTR_NAME_UNITS);
			assertNotNull("units not specified, expected " + expectedUnits, actualUnitsAttr);
			assertAttributesEquals(null, expectedUnitsAttr, actualUnitsAttr);
		}
	}
	
	public static void assertNXentryMetadata(NXentry entry) {
		assertEquals(MOCK_VISIT_ID, entry.getExperiment_identifierScalar());
		assertNXTimeStamps(entry);
	}
	
}
