/*-
 * Copyright 2015, 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package org.eclipse.dawnsci.nexus;

import static org.eclipse.dawnsci.nexus.NexusFile.NXCLASS_SEPARATOR;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.osgi.services.ServiceProvider;

/**
 * Utility methods for dealing with NeXus files.
 */
public class NexusUtils {

	private static final int CHUNK_TARGET_SIZE = 1024 * 1024; // 1 MB

	public static final ChunkingStrategy DEFAULT_CHUNK_STRATEGY = ChunkingStrategy.SKEW_LAST;

	
	/**
	 * Simple wrapper around DatasetFactory.createFromObject to perform a null check.
	 * <p>
	 * This is necessary because by default if null is passed to this method an ObjectDataset
	 * is returned, which cannot be saved to a HDF5 file. See DAQ-4649 for more details.
	 * @param object argument passed to DatasetFactory.createFromObject.
	 * @param datasetName the name of the data set
	 */
	public static Dataset createFromObject(Object object, String datasetName) {
		if (object == null) {
			throw new IllegalArgumentException("Cannot create %s dataset from null".formatted(datasetName));
		}
		
		final Dataset dataset = DatasetFactory.createFromObject(object);
		dataset.setName(datasetName);
		return dataset;
	}
	
	/**
	 * Possible strategies for estimating chunking
	 */
	public enum ChunkingStrategy {
		/**
		 * Approximately balance chunking across all dimensions
		 * Better for slicing across multiple dimensions when processing.
		 */
		BALANCE,

		/**
		 * Skew chunking toward later dimensions - maximally reduce earlier dimensions first
		 * Good for writing large detector images frame by frame.
		 */
		SKEW_LAST
	}
	
	/**
	 * An element of an augmented NeXus node path, i.e. where each group element specifies
	 * the Nexus class, e.g. {@code /entry:NXentry/instrument:NXinstrument/det:NXdetector/data}
	 */
	public static final class AugmentedPathElement {
		
		public final String name;
		public final String nxClass;
		
		public AugmentedPathElement(String name, String nxClass) {
			this.name = name;
			this.nxClass = nxClass;
		}
	}
	
	private static NexusBaseClass getBaseClassForName(String nxClass) {
		// NXgoniometer is a special case - some beamlines use it, but it's not an
		// official NeXus base class
		if (nxClass.equals("NXgoniometer")) {
			return NexusBaseClass.NX_COLLECTION;
		}

		return NexusBaseClass.getBaseClassForName(nxClass);
	}
	
	public static AugmentedPathElement[] parseAugmentedPath(String path) {
		return parseAugmentedPath(path, false);
	}
	
	public static AugmentedPathElement[] parseAugmentedPath(String path, boolean relative) {
		if (!relative && !path.startsWith(Tree.ROOT)) {
			throw new IllegalArgumentException("Path (" + path + ") must be absolute");
		}
		
		return Arrays.stream(path.split(Node.SEPARATOR)) // split segments by delimiter '/'
			.skip(relative ? 0: 1)
			.map(p -> p.split(NXCLASS_SEPARATOR, 2)) // split each segment by Nx_class separator ':' to produce pairs (String[] of size 2, or 1 if no NXclass) 
			.map(pair -> new AugmentedPathElement(pair[0], pair.length > 1 ? pair[1] : "")) // convert pairs to AugmentedPathElement
			.toArray(AugmentedPathElement[]::new); // convert to array
	}
	
	/**
	 * Create a (top-level) NeXus augmented path
	 * @param name
	 * @param nxClass
	 * @return augmented path
	 */
	public static String createAugmentPath(String name, String nxClass) {
		StringBuilder b = new StringBuilder();
		if (!name.startsWith(Tree.ROOT))
			b.append(Tree.ROOT);
		return addToAugmentPath(b, name, nxClass).toString();
	}

	/**
	 * Add to a NeXus augmented path
	 * @param path
	 * @param name
	 * @param nxClass
	 * @return augmented path
	 */
	public static String addToAugmentPath(String path, String name, String nxClass) {
		return addToAugmentPath(new StringBuilder(path), name, nxClass).toString();
	}

	/**
	 * Add to a NeXus augmented path
	 * @param path
	 * @param name
	 * @param nxClass
	 * @return augmented path
	 */
	public static StringBuilder addToAugmentPath(StringBuilder path, String name, String nxClass) {
		if (name == null) {
			throw new IllegalArgumentException("Name must not be null");
		}
		if (path.length() == 0) {
			path.append(Tree.ROOT);
		} else if (path.lastIndexOf(Node.SEPARATOR) != path.length() - 1) {
			path.append(Node.SEPARATOR);
		}
		path.append(name);
		if (nxClass != null) {
			path.append(NXCLASS_SEPARATOR).append(nxClass);
		}
		return path;
	}

	/**
	 * Create a plain path by stripping out NXclasses
	 * @param augmentedPath
	 * @return plain path
	 */
	public static String stripAugmentedPath(String augmentedPath) {
		int i;
		while ((i = augmentedPath.indexOf(NXCLASS_SEPARATOR)) >= 0) {
			int j = augmentedPath.indexOf(Node.SEPARATOR, i);
			augmentedPath = j >= 0 ? augmentedPath.substring(0, i) + augmentedPath.substring(j)
					: augmentedPath.substring(0, i);
		}
		return augmentedPath;
	}
	
	/**
	 * Adds the given {@link DataNode} at the given augmented path (a path where the NXclass for
	 * group nodes are specified. 
	 * <p><em>Note:</em>This method operates on an in-memory nexus tree, no changes are saved to disk.
	 * @param nexusObject nexus object
	 * @param dataNode data node to add
	 * @param dataNodePath path at which to add the data node, an as augmented pat
	 * @throws NexusException 
	 */
	public static void addDataNode(NXobject nexusObject, DataNode dataNode, String dataNodePath) throws NexusException {
		final int lastSeparatorIndex = dataNodePath.lastIndexOf(Node.SEPARATOR);
		final NXobject parentGroup;
		final String dataNodeName;
		if (lastSeparatorIndex == -1) { // simple case, path is just a name
			parentGroup = nexusObject;
			dataNodeName = dataNodePath;
		} else {
			final String parentGroupPath = dataNodePath.substring(0, lastSeparatorIndex);
			parentGroup = getGroupNode(nexusObject, parentGroupPath, true);
			dataNodeName = dataNodePath.substring(lastSeparatorIndex + 1);
		}
		
		parentGroup.addDataNode(dataNodeName, dataNode);
	}
	
	/**
	 * Gets the given {@link DataNode} at the given augmented path (a path where the NXclass for
	 * group nodes are specified. 
	 * @param nexusObject nexus object
	 * @param dataNodePath path at which to add the data node, an as augmented pat
	 * @return the DataNode at the given path, or null if none
	 * @throws NexusException 
	 */
	public static DataNode getDataNode(NXobject nexusObject, String dataNodePath) throws NexusException {
		final int lastSeparatorIndex = dataNodePath.lastIndexOf(Node.SEPARATOR);
		final NXobject parentGroup;
		final String dataNodeName;
		if (lastSeparatorIndex == -1) { // simple case, path is just a name
			parentGroup = nexusObject;
			dataNodeName = dataNodePath;
		} else {
			final String parentGroupPath = dataNodePath.substring(0, lastSeparatorIndex);
			parentGroup = getGroupNode(nexusObject, parentGroupPath, false);
			dataNodeName = dataNodePath.substring(lastSeparatorIndex + 1);
		}
		
		return parentGroup == null ? null : parentGroup.getDataNode(dataNodeName);
	}
	/**
	 * Get the group node with the given augmented path within the given root node,
	 * optionally creating it if it does not already exist.
	 * <p><em>Note:</em>This method operates on an in-memory nexus tree, no changes are saved to disk.
	 * @param root the root node from which
	 * @param augmentedPath
	 * @return group node with given augmented path
	 * @throws NexusException if no node exists as the given path and {@code create} is false, or
	 *    the node at the given path is not a group node
	 */
	public static NXobject getGroupNode(NXobject root, String augmentedPath, boolean create) throws NexusException {
		// check to see if the group node already exists and we can just return it
		final String plainPath = NexusUtils.stripAugmentedPath(augmentedPath);
		final NodeLink link = root.findNodeLink(plainPath);
		if (link == null) {
			if (!create) {
				throw new NexusException("No group found at given path.");
			}
		} else {
			if (link.isDestinationGroup()) {
				GroupNode dest = (GroupNode) link.getDestination();
				if (!(dest instanceof NXobject)) {
					throw new NexusException("Group node is not an NXobject " + augmentedPath);
				}
				return (NXobject) dest;
			}
			throw new NexusException("A node already exists at the specified path which is not a group node: " + augmentedPath);
		}

		// we have to create the group node
		// first parse the augmented path into segments
		final AugmentedPathElement[] parsedPath = NexusUtils.parseAugmentedPath(augmentedPath, true);
		NXobject groupNode = root;
		for (AugmentedPathElement parsedPathSegment : parsedPath) {
			final String name = parsedPathSegment.name;
			final String nxClass = parsedPathSegment.nxClass;

			GroupNode childNode = groupNode.getGroupNode(name);
			if (childNode == null) {
				// group node does not exist, so we create it
				final NexusBaseClass baseClass = getBaseClassForName(nxClass);
				childNode = NexusNodeFactory.createNXobjectForClass(baseClass);
				groupNode.addGroupNode(name, childNode);
			} else {
				// group already exists, check it has the expected NX_class
				if (!(childNode instanceof NXobject)) {
					throw new NexusException(MessageFormat.format("The group ''{0}'' already exists and is not an NXobject", name));
				}

				final NexusBaseClass expectedBaseClass = getBaseClassForName(nxClass);
				final NexusBaseClass actualNexusBaseClass = ((NXobject) childNode).getNexusBaseClass();
				if (expectedBaseClass != actualNexusBaseClass) {
					throw new NexusException(MessageFormat.format("The group ''{0}'' already exists and has NX_class ''{1}'', expected ''{2}''",
							name, actualNexusBaseClass, expectedBaseClass));
				}
			}

			groupNode = (NXobject) childNode;
		}

		return groupNode;
	}

	/**
	 * Get name of last part
	 * @param path
	 * @return name or null if path does not contain any {@value Node#SEPARATOR} or ends in that
	 */
	public static String getName(String path) {
		if (path.endsWith(Node.SEPARATOR) || !path.contains(Node.SEPARATOR))
			return null;
		return path.substring(path.lastIndexOf(Node.SEPARATOR) + 1);
	}

	/**
	 * Create a lazy writeable dataset
	 * @param name
	 * @param clazz dataset element class
	 * @param shape
	 * @param maxShape
	 * @param chunks
	 * @return lazy writeable dataset
	 */
	public static ILazyWriteableDataset createLazyWriteableDataset(String name, Class<?> clazz, int[] shape, int[] maxShape, int[] chunks) {
		return new LazyWriteableDataset(name, clazz, shape, maxShape, chunks, null);
	}

	/**
	 * Write the string into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static DataNode writeString(NexusFile file, GroupNode group, String name, String value) throws NexusException {
		if (name == null || name.isEmpty() || value == null || value.isEmpty())
			return null;
		return write(file, group, name, value);
	}

	/**
	 * Write the integer into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static DataNode writeInteger(NexusFile file, GroupNode group, String name, int value) throws NexusException {
		return write(file, group, name, value);
	}

	/**
	 * Write the integer array into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static DataNode writeIntegerArray(NexusFile file, GroupNode group, String name, int[] value) throws NexusException {
		return write(file, group, name, value);
	}

	/**
	 * Write the double into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static DataNode writeDouble(NexusFile file, GroupNode group, String name, double value) throws NexusException {
		return write(file, group, name, value);
	}

	/**
	 * Write the double into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static DataNode writeDoubleArray(NexusFile file, GroupNode group, String name, double[] value) throws NexusException {
		return write(file, group, name, value);
	}

	/**
	 * Write the double into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static DataNode writeDoubleArray(NexusFile file, GroupNode group, String name, Double[] value) throws NexusException {
		return write(file, group, name, value);
	}

	/**
	 * Write the double into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group
	 * @param name
	 * @param value
	 * @param units
	 * @throws NexusException
	 */
	public static DataNode writeDouble(NexusFile file, GroupNode group, String name, double value, String units) throws NexusException {
		DataNode node = write(file, group, name, value);
		if (units != null) {
			writeStringAttribute(file, node, NexusConstants.UNITS, units);
		}
		return node;
	}

	/**
	 * Write the object into a field called 'name' at the group in the NeXus file.
	 *
	 * @param file
	 * @param group (can be null for root)
	 * @param name
	 * @param value
	 * @return data node
	 * @throws NexusException
	 */
	public static DataNode write(NexusFile file, GroupNode group, String name, Object value) throws NexusException {
		if (value == null || name == null || name.isEmpty())
			return null;

		Dataset a = createFromObject(value, name);

		DataNode d = null;
		try {
			d = file.createData(group, a);
		} catch (NexusException e) {
			d = file.getData(group, name);
			ILazyWriteableDataset wd = d.getWriteableDataset();
			try {
				wd.setSlice(null, a, null);
			} catch (Exception ex) {
				throw new NexusException("Could not write to dataset: " + name, ex);
			}
		}

		return d;
	}

	/**
	 * @param file
	 * @param node
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static void writeStringAttribute(NexusFile file, Node node, String name, String value) throws NexusException {
		writeAttribute(file, node, name, value);
	}

	/**
	 * @param file
	 * @param node
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static void writeIntegerAttribute(NexusFile file, Node node, String name, int... value) throws NexusException {
		writeAttribute(file, node, name, value);
	}

	/**
	 * @param file
	 * @param node
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static void writeDoubleAttribute(NexusFile file, Node node, String name, double... value) throws NexusException {
		writeAttribute(file, node, name, value);
	}

	/**
	 * @param file
	 * @param node
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static void writeDoubleAttribute(NexusFile file, Node node, String name, Double... value) throws NexusException {
		writeAttribute(file, node, name, value);
	}

	/**
	 * @param file
	 * @param node
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	public static void writeAttribute(NexusFile file, Node node, String name, Object value) throws NexusException {
		if (value == null || name == null || name.isEmpty())
			return;

		Dataset a = createFromObject(value, name);
		Attribute attr = file.createAttribute(a);
		file.addAttribute(node, attr);
	}
	
	/**
	 * Loads and returns the entire nexus tree for the file at the given path.
	 * @param filePath file path
	 * @return nexus tree
	 * @throws NexusException if the file could not be loaded for any reason
	 */
	public static TreeFile loadNexusTree(String filePath) throws NexusException {
		try (NexusFile nexusFile = ServiceProvider.getService(INexusFileFactory.class).newNexusFile(filePath)) {
			nexusFile.openToRead();
			return loadNexusTree(nexusFile);
		}
	}

	/**
	 * Loads the entire nexus tree structure into memory. Note that this does not
	 * necessarily load the contents of every dataset within the nexus file into memory,
	 * as some may be {@link ILazyDataset}s.
	 * @param filePath file path
	 * @return TreeFile tree file representing nexus tree
	 * @throws NexusException if the file could not be loaded for any reason
	 */
	public static TreeFile loadNexusTree(NexusFile nexusFile) throws NexusException {
		// TODO FIXME mattd 2016-02-11 should it be possible to do this without
		// a NexusFile? or perhaps this method should even be on NexusFile itself
		final String filePath = nexusFile.getFilePath();
		final TreeFile treeFile = TreeFactory.createTreeFile(filePath.hashCode(), filePath);
		final GroupNode rootNode = nexusFile.getGroup("/", false);
		treeFile.setGroupNode(rootNode);
		recursivelyLoadNexusTree(nexusFile, rootNode);

		return treeFile;
	}

	private static void recursivelyLoadNexusTree(NexusFile nexusFile, GroupNode group) throws NexusException {
		final Iterator<String> nodeNames = group.getNodeNameIterator();
		while (nodeNames.hasNext()) {
			String nodeName = nodeNames.next();
			if (group.containsGroupNode(nodeName)) {
				// nexusFile.getGroup() causes that group to be populated
				GroupNode childGroup = nexusFile.getGroup(group, nodeName, null, false);
				recursivelyLoadNexusTree(nexusFile, childGroup);
			} else if (group.containsDataNode(nodeName)) {
				nexusFile.getData(group, nodeName);
			}
		}
	}
	
	private static int[] estimateChunkingBalanced(int[] expectedMaxShape,
			int dataByteSize,
			int[] fixedChunkDimensions,
			int targetSize) {
		if (expectedMaxShape == null) {
			throw new NullPointerException("Must provide an expected shape");
		}
		if (fixedChunkDimensions != null && (expectedMaxShape.length != fixedChunkDimensions.length)) {
			throw new IllegalArgumentException("Shape estimation and provided chunk information have different dimensions");
		}
		for (int d : expectedMaxShape) {
			if (d <= 0) {
				throw new IllegalArgumentException("Shape estimation must have dimensions greater than zero");
			}
		}
		int[] chunks = Arrays.copyOf(expectedMaxShape, expectedMaxShape.length);
		int[] fixed;
		if (fixedChunkDimensions != null) {
			fixed = fixedChunkDimensions;
		} else {
			fixed = new int[chunks.length];
			Arrays.fill(fixed, -1);
		}
		for (int i = 0; i < chunks.length; i++) {
			if (fixed[i] > 0) {
				chunks[i] = fixed[i];
			}
		}
		long currentSize = dataByteSize;
		for (int i : chunks) {
			currentSize *= (long) i;
		}
		int idx = 0;
		boolean minimal = false;
		while (currentSize > targetSize && !minimal) {
			//check that our chunk estimation can continue being reduced
			for (int i = 0; i < fixed.length; i++) {
				minimal = true;
				if (fixed[i] <= 0 && chunks[i] > 1) {
					minimal = false;
					break;
				}
			}
			if (fixed[idx] <= 0) {
				chunks[idx] = (int) Math.round(chunks[idx] / 2.0);
			}
			idx++;
			idx %= chunks.length;
			currentSize = dataByteSize;
			for (int i : chunks) {
				currentSize *= (long) i;
			}
		}
		return chunks;
	}

	private static int[] estimateChunkingSkewed(int[] expectedMaxShape,
			int dataByteSize,
			int[] fixedChunkDimensions,
			int targetSize) {
		if (expectedMaxShape == null) {
			throw new NullPointerException("Must provide an expected shape");
		}
		if (fixedChunkDimensions != null && (expectedMaxShape.length != fixedChunkDimensions.length)) {
			throw new IllegalArgumentException("Shape estimation and provided chunk information have different dimensions");
		}
		for (int d : expectedMaxShape) {
			if (d <= 0) {
				throw new IllegalArgumentException("Shape estimation must have dimensions greater than zero");
			}
		}

		int[] chunk = Arrays.copyOf(expectedMaxShape, expectedMaxShape.length);
		int[] fixed = fixedChunkDimensions;
		if (fixed == null) {
			fixed = new int[chunk.length];
			Arrays.fill(fixed, -1);
		}
		for (int i = 0; i < chunk.length; i++) {
			if (fixed[i] > 0) {
				chunk[i] = fixed[i];
			}
		}
		long currentSize = dataByteSize;
		for (int i : chunk) {
			currentSize *= (long) i;
		}
		ArrayList<Integer> toReduce = new ArrayList<Integer>();
		for (int i = 0; i < fixed.length; i++) {
			if (fixed[i] < 1) toReduce.add(i);
		}
		outer_loop:
		for (int idx : toReduce) {
			while (chunk[idx] > 1) {
				if (currentSize > targetSize) {
					// round up to avoid needing an extra chunk to hold a tiny amount of data
					chunk[idx] = (int) Math.ceil(chunk[idx] / 2.0);

					currentSize = dataByteSize;
					for (int c : chunk) {
						currentSize *= (long) c;
					}
				} else {
					// finished reducing chunk
					break outer_loop;
				}
			}
		}
		return chunk;
	}

	/**
	 * Estimate suitable chunk parameters based on the expected final size of a dataset
	 *
	 * @param expectedMaxShape
	 *            expected final size of the dataset
	 * @param dataByteSize
	 *            size of each element in bytes
	 * @param fixedChunkDimensions
	 *            provided dimensions in a chunk to be kept constant (-1 for no provided chunk)
	 * @param strategy
	 *            strategy to use for estimating
	 * @return chunk estimate
	 */
	public static int[] estimateChunking(int[] expectedMaxShape,
			int dataByteSize,
			int[] fixedChunkDimensions,
			ChunkingStrategy strategy) {
		switch (strategy) {
		case BALANCE:
			return estimateChunkingBalanced(expectedMaxShape, dataByteSize, fixedChunkDimensions, CHUNK_TARGET_SIZE);
		case SKEW_LAST:
		default:
			return estimateChunkingSkewed(expectedMaxShape, dataByteSize, fixedChunkDimensions, CHUNK_TARGET_SIZE);
		}
	}

	/**
	 * Estimate suitable chunk parameters based on the expected final size of a dataset
	 * @param expectedMaxShape
	 *            expected final size of the dataset
	 * @param dataByteSize
	 *            size of each element in bytes
	 * @param strategy
	 *            strategy to use for estimating
	 * @return chunk estimate
	 */
	public static int[] estimateChunking(int[] expectedMaxShape, int dataByteSize, ChunkingStrategy strategy) {
		return estimateChunking(expectedMaxShape, dataByteSize, null, strategy);
	}

	/**
	 * Estimate suitable chunk parameters based on the expected final size of a dataset
	 *
	 * @param expectedMaxShape
	 *            expected final size of the dataset
	 * @param dataByteSize
	 *            size of each element in bytes
	 * @param fixedChunkDimensions
	 *            provided dimensions in a chunk to be kept constant (-1 for no provided chunk)
	 * @return chunk estimate
	 */
	public static int[] estimateChunking(int[] expectedMaxShape, int dataByteSize, int[] fixedChunkDimensions) {
		return estimateChunking(expectedMaxShape, dataByteSize, fixedChunkDimensions, DEFAULT_CHUNK_STRATEGY);
	}

	/**
	 * Estimate suitable chunk parameters based on the expected final size of a dataset
	 *
	 * @param expectedMaxShape
	 *            expected final size of the dataset
	 * @param dataByteSize
	 *            size of each element in bytes
	 * @return chunk estimate
	 */
	public static int[] estimateChunking(int[] expectedMaxShape, int dataByteSize) {
		return estimateChunking(expectedMaxShape, dataByteSize, null, DEFAULT_CHUNK_STRATEGY);
	}

	/**
	 * Returns names of axes in group at same level as name passed in.
	 * 
	 * This opens and safely closes a nexus file if one is not already open for this
	 * location.
	 * 
	 * @param filePath
	 * @param nexusPath
	 *            - path to signal dataset
	 * @param dimension,
	 *            the dimension we want the axis for starting with 1
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAxisNames(String filePath, String nexusPath, int dimension) throws Exception {

		if (filePath == null || nexusPath == null)
			return null;
		if (dimension < 1)
			return null;
		try (NexusFile file = ServiceProvider.getService(INexusFileFactory.class).newNexusFile(filePath)) {
			file.openToRead();
			DataNode signal = file.getData(nexusPath);

			final List<String> axesTmp = new ArrayList<String>(3);
			final Map<Integer, String> axesMap = new TreeMap<Integer, String>();

			int[] shape = signal.getDataset().getShape();
			if (dimension > shape.length)
				return null;
			final long size = shape[dimension - 1];

			final String parentPath = file.getPath(signal).substring(0, file.getPath(signal).lastIndexOf(Node.SEPARATOR));

			final GroupNode parent = file.getGroup(parentPath, false);

			int fakePosValue = Integer.MAX_VALUE;

			for (NodeLink child : parent) {
				Node node = child.getDestination();
				if (node instanceof DataNode) {
					DataNode dataNode = (DataNode) node;
					final Iterator<? extends Attribute> att = dataNode.getAttributeIterator();
					if (!(child.isDestinationData()))
						continue;
					if (child.getName().equals(signal.getDataset().getName()))
						continue;

					String axis = null;
					int pos = -1;
					boolean isSignal = false;
					while (att.hasNext()) {
						Attribute attribute = att.next();
						if ("axis".equals(attribute.getName())) {
							int iaxis = getAttributeIntValue(attribute);
							if (iaxis < 0) { // We look for comma separated string
								final int[] axesDims = attribute.getShape();

								final int[] shapeAxes = dataNode.getDataset().getShape();
								final int[] shapeData = shape;
								if (axesDims != null && axesCompatible(axesDims, shapeAxes, shapeData)) {
									for (int dim : axesDims) {
										if (dim == dimension) {
											axis = parentPath + Node.SEPARATOR + dataNode.getDataset().getName() + ":" + dimension;
											break;
										}
									}
								}

							}
							if (iaxis == dimension) {

								final int[] dims = dataNode.getDataset().getShape();

								if ((dims.length == 1 && dims[0] == size) || dims.length != 1) {
									axis = parentPath + Node.SEPARATOR + dataNode.getDataset().getName();
								}

							}

						} else if ("primary".equals(attribute.getName())) {
							if (pos != 0)
								pos = getAttributeIntValue(attribute);

						} else if ("label".equals(attribute.getName())) {
							int labelAxis = getAttributeIntValue(attribute);
							if (labelAxis == dimension)
								pos = 0;

						} else if ("signal".equals(attribute.getName())) {
							isSignal = true;
							axis = null;
							pos = -1;
							break;
						}
					}

					// prioritise datasets that specify an axes (even with no primary attribute)
					// over other datasets
					if (axis != null && pos == -1) {
						pos = fakePosValue--;
					}

					// Add any the same shape as this dimension
					// providing that they are not signals
					// Some nexus files set axis wrong
					if (axis == null && !isSignal) {
						final int[] dims = dataNode.getDataset().getShape();
						if (dims[0] == size && dims.length == 1) {
							axis = parentPath + Node.SEPARATOR + dataNode.getDataset().getName();
						}
					}

					if (axis != null) {
						if (pos < 0) {
							axesTmp.add(axis);
						} else {
							axesMap.put(pos, axis);
						}
					}
				}
			}

			final List<String> axes = new ArrayList<String>(3);
			if (!axesMap.isEmpty()) {
				for (Integer pos : axesMap.keySet()) {
					axes.add(axesMap.get(pos));
				}
			}
			axes.addAll(axesTmp);

			return axes;
		}
	}

	/**
	 * Gets the int value or returns -1 (Can only be used for values which are not
	 * allowed to be -1!)
	 * 
	 * @param attribute
	 * @return
	 */
	private static int getAttributeIntValue(Attribute attribute) {
		if (attribute.isString()) {
			return Integer.parseInt(attribute.getFirstElement());
		}
		final Dataset ob = DatasetUtils.convertToDataset(attribute.getValue());
		return ob.getInt();
	}

	private static boolean axesCompatible(int[] axesDims, int[] shapeAxes, int[] shapeData) {
		if (axesDims == null)
			return false;
		if (Arrays.equals(shapeAxes, shapeData))
			return true;

		for (int i = 0; i < axesDims.length; i++) {
			if (shapeAxes[i] != shapeData[axesDims[i] - 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Compare object a as a scalar to String b by first converting b to a type
	 * that matches a.
	 *
	 * @see Comparable#compareTo(Object)
	 * @throws NumberFormatException
	 *             if b can not be converted to the same type as a
	 */
	public static int compareScalarToString(Object a, String b)
			throws NumberFormatException {
		if (a instanceof Short) {
			return ((Short) a).compareTo(Short.parseShort(b));
		}
		if (a instanceof Integer) {
			return ((Integer) a).compareTo(Integer.parseInt(b));
		}
		if (a instanceof Long) {
			return ((Long) a).compareTo(Long.parseLong(b));
		}
		if (a instanceof Character) {
			return ((Character) a).toString().compareTo(b);
		}
		if (a instanceof Float) {
			return ((Float) a).compareTo(Float.parseFloat(b));
		}
		if (a instanceof Double) {
			return ((Double) a).compareTo(Double.parseDouble(b));
		}
		if (a instanceof Boolean) {
			return ((Boolean) a).compareTo(Boolean.parseBoolean(b));
		}
		if (a instanceof Byte) {
			return ((Byte) a).compareTo(Byte.valueOf(b));
		}
		if (a instanceof String) {
			return ((String) a).compareTo(b);
		}
		String name;
		if (a == null) {
			name = "null";
		} else {
			name = "a.getClass().getName()";
		}
		throw new NumberFormatException("a has unknown type for conversion: "
				+ name);
	}

	/**
	 * Compares the two scalar objects if they are the same type and Comparable
	 * using their compareTo method, else compares the toString value.
	 *
	 * @see Comparable#compareTo(Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int compareScalars(Object a, Object b) {
		if (a instanceof Comparable && b instanceof Comparable) {
			Comparable ca = (Comparable) a;
			Comparable cb = (Comparable) b;
			if (a.getClass() == b.getClass())
				return ca.compareTo(cb);
		}
		return a.toString().compareTo(b.toString());
	}

	/**
	 * Convert the size == 1 array to a scalar. Works on January data types Dataset
	 *
	 * @param value
	 *         a January Dataset is expected
	 * @return the scalar value, or <code>null</code> if no scalar can be
	 *         extracted
	 */
	public static Object extractScalarFromDataset(Object value) {
		if (value instanceof Dataset) {
			Dataset d = (Dataset) value;
			return d.getSize() == 1 ? d.getObject() : null;
		}
		return null;
	}

	/**
	 * Append data to a dataset in group node. If data node does not exist then create one. This assumes
	 * that data is of a consistent rank R and will be appended to a shape with rank R+1 along the first
	 * dimension 
	 * @param f Nexus file
	 * @param g group node
	 * @param a dataset
	 * @return data node
	 * @throws NexusException
	 * @throws DatasetException 
	 */
	public static DataNode appendData(NexusFile f, GroupNode g, Dataset a) throws NexusException, DatasetException {
		int r = a.getRank();
		int[] ns = new int[r + 1];
		ns[0] = 1;
		System.arraycopy(a.getShapeRef(), 0, ns, 1, r);
		String name = a.getName();
		a = a.reshape(ns);
		DataNode d = g.getDataNode(name);
		if (d == null) {
			int[] ms = ns.clone();
			ms[0] = -1;
			LazyWriteableDataset w = new LazyWriteableDataset(null, name, a.getClass(), ns, ms, null);
			return f.createData(g, w);
		}
	
		ILazyWriteableDataset w = d.getWriteableDataset();
		if (r != w.getRank() - 1) {
			throw new IllegalArgumentException("Rank of input dataset must be " + (w.getRank() - 1) + " not " + r);
		}
		int[] cs = w.getShape();
		SliceND slice = new SliceND(cs, w.getMaxShape(), new Slice(cs[0], cs[0] + 1));
		w.setSlice(null, a, slice);
		return d;
	}

	/**
	 * Load group and its subgroups to given maximum depth
	 * @param file
	 * @param path
	 * @param maxDepth
	 * @return group
	 * @throws NexusException
	 */
	public static GroupNode loadGroupFully(NexusFile file, String path, int maxDepth) throws NexusException {
		// populate tree
		GroupNode g = file.getGroup(path, false);
		loadGroup(file, g, maxDepth);
		return g;
	}

	private static void loadGroup(NexusFile file, GroupNode g, int maxDepth) throws NexusException {
		if (maxDepth-- > 0) {
			for (String n : g.getNames()) {
				if (g.containsGroupNode(n)) {
					loadGroup(file, file.getGroup(g, n, null, false), maxDepth);
				}
			}
		}
	}

	public static Object[] getFillValue(Class<?> clazz) {
		if (clazz == Integer.class) {
			return new Integer[] {0};
		} else if (clazz == Long.class) {
			return new Long[] {0L};
		} else if (clazz == Double.class) {
			return new Double[] {Double.NaN};
		} else if (clazz == Float.class) {
			return new Float[] {Float.NaN};
		} else if (clazz == Short.class) {
			return new Short[] {0};
		} else if (clazz == Byte.class) {
			return new Byte[] {0};
		} else if (clazz == String.class) {
			//TODO: change to "" when HDF supports strings as fill value
			return null;
		} else if (clazz == Boolean.class) {
			return new Boolean[] {false};
		}
		return null;
	}

	/**
	 * Create NeXus class attribute
	 * @param nxClass
	 * @return attribute
	 */
	public static Attribute createNXclassAttribute(String nxClass) {
		return TreeFactory.createAttribute(NexusConstants.NXCLASS, nxClass);
	}

	/**
	 * Create NeXus class
	 * @param nxClass
	 * @return NeXus class
	 */
	public static GroupNode createNXclass(String nxClass) {
		GroupNode gn = TreeFactory.createGroupNode(1);
		gn.addAttribute(createNXclassAttribute(nxClass));
		return gn;
	}

	/**
	 * Create and write new NeXus class in file
	 * @param file
	 * @param group location of new group (can be null for root)
	 * @param name name of NeXus class
	 * @param nxClass type of class from {@link NexusConstants}
	 * @return NeXus class
	 * @throws NexusException
	 */
	public static GroupNode writeNXclass(NexusFile file, GroupNode group, String name, String nxClass) throws NexusException {
		return file.getGroup(group, name, nxClass, true);
	}

	/**
	 * Create and write an NXdata group in file
	 * @param file
	 * @param group location of new group (can be null for root)
	 * @param name name of new NXdata group
	 * @param data signal data to place in new group (must be IDataset or ILazyWriteableDataset)
	 * @param axes axes to place in new group
	 * @return NXdata group
	 * @throws NexusException
	 */
	public static GroupNode writeNXdata(NexusFile file, GroupNode group, String name, ILazyDataset data, ILazyDataset... axes) throws NexusException {
		GroupNode g = file.getGroup(group, name, NexusConstants.DATA, true);
		String dName = data.getName();
		if (dName.isEmpty()) {
			dName = NexusConstants.DATA_DATA;
			data.setName(dName);
		}
		if (data instanceof IDataset) {
			file.createData(g, (IDataset) data);
		} else if (data instanceof ILazyWriteableDataset) {
			file.createData(g, (ILazyWriteableDataset) data);
		} else {
			throw new IllegalArgumentException("Dataset must be IDataset or ILazyWriteableDataset");
		}
		if (axes == null || axes.length == 0) {
			AxesMetadata am = data.getFirstMetadata(AxesMetadata.class);
			if (am != null) {
				axes = am.getAxes();
			} else {
				axes = new ILazyDataset[0];
			}
		}

		List<String> axisNames = new ArrayList<>();
		List<int[]> indices = new ArrayList<>();
		int rank = data.getRank();
		int[] stdIndices = new int[rank];
		int i = 0;
		for (; i < rank; i++) {
			stdIndices[i] = i;
		}

		i = 0;
		for (ILazyDataset a : axes) {
			if (a == null) {
				axisNames.add(NexusConstants.DATA_AXESEMPTY);
				indices.add(null);
			} else {
				String aName = a.getName();
				if (aName.isEmpty()) {
					aName = "axis-" + i;
				}
				try {
					Dataset d = DatasetUtils.sliceAndConvertLazyDataset(a);
					if (d.getRank() > 1 && ShapeUtils.squeezeShape(d.getShapeRef(), false).length == 1) {
						d.squeeze();
						indices.add(new int[] {i});
					} else {
						indices.add(stdIndices);
					}
					d.setName(aName);
					axisNames.add(aName);
					file.createData(g, d);
				} catch (DatasetException e) {
					throw new NexusException("Could not slice axis " + aName, e);
				}
			}
			i++;
		}

		// axes attributes
		writeAttribute(file, g, NexusConstants.DATA_SIGNAL, dName);
		i = 0;
		for (String a : axisNames) {
			int[] ind = indices.get(i++);
			if (ind != null || !a.equals(NexusConstants.DATA_AXESEMPTY)) {
				writeAttribute(file, g, a + NexusConstants.DATA_INDICES_SUFFIX, ind);
			}
		}
		while (i++ < data.getRank()) {
			axisNames.add(NexusConstants.DATA_AXESEMPTY);
		}
		writeAttribute(file, g, NexusConstants.DATA_AXES, DatasetFactory.createFromList(axisNames));

		return g;
	}
}
