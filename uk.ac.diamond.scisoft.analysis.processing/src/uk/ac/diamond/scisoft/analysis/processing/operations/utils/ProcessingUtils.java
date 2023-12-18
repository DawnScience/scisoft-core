/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.utils;

import java.io.Serializable;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.measure.Unit;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ILoaderService;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.OriginMetadata;
import org.eclipse.january.metadata.UnitMetadata;

import uk.ac.diamond.osgi.services.ServiceProvider;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

public class ProcessingUtils {
	/**
	 * Get data from given file
	 * @param op current operation
	 * @param filepath
	 * @return data holder
	 * @throws OperationException file cannot be read
	 */
	public static IDataHolder getData(IOperation<?, ?> op, String filepath) throws OperationException {
		try {
			return ServiceProvider.getService(ILoaderService.class).getData(filepath, null);
		} catch (Exception e) {
			throw new OperationException(op, "Error opening file: " + filepath, e);
		}
	}

	/**
	 * Get tree from given file
	 * @param op current operation
	 * @param filepath
	 * @return tree
	 * @throws OperationException file cannot be read
	 */
	public static Tree getTree(IOperation<?, ?> op, String filepath) throws OperationException {
		return getData(op, filepath).getTree();
	}

	/**
	 * Get lazy dataset from given file
	 * @param op current operation
	 * @param filepath
	 * @param datasetName
	 * @return lazy dataset
	 * @throws OperationException file or dataset cannot be read
	 */
	public static ILazyDataset getLazyDataset(IOperation<?, ?> op, String filepath, String datasetName) throws OperationException {
		IDataHolder dh = getData(op, filepath);
		ILazyDataset lz = dh.getLazyDataset(datasetName);
		if (lz == null) throw new OperationException(op, "Error reading dataset: " + datasetName);
		return lz;
	}

	/**
	 * Get dataset from given file
	 * @param op current operation
	 * @param filepath
	 * @param datasetName
	 * @return dataset
	 * @throws OperationException file or dataset cannot be read
	 */
	public static IDataset getDataset(IOperation<?, ?> op, String filepath, String datasetName) throws OperationException {
		ILazyDataset lz = getLazyDataset(op, filepath, datasetName);
		try {
			return lz.getSlice();
		} catch (DatasetException e) {
			throw new OperationException(op, e);
		}
	}

	/**
	 * Create dataset with name
	 * @param obj
	 * @param name
	 * @return dataset
	 */
	public static Dataset createNamedDataset(Serializable obj, String name) {
		return createNamedDataset(obj, null, name);
	}

	/**
	 * Create dataset with name
	 * @param obj
	 * @param format
	 * @param n
	 * @return dataset
	 */
	public static Dataset createNamedDataset(Serializable obj, String format, int n) {
		return createNamedDataset(obj, null, String.format(format, n));
	}

	/**
	 * Create dataset with name
	 * @param obj
	 * @param unit
	 * @param format
	 * @param n
	 * @return dataset
	 */
	public static Dataset createNamedDataset(Serializable obj, Unit<?> unit, String format, int n) {
		return createNamedDataset(obj, unit, String.format(format, n));
	}

	/**
	 * Create dataset with name
	 * @param obj
	 * @param unit
	 * @param name
	 * @return dataset
	 */
	public static Dataset createNamedDataset(Serializable obj, Unit<?> unit, String name) {
		Dataset d = DatasetFactory.createFromObject(obj);
		if (unit != null) {
			try {
				d.addMetadata(MetadataFactory.createMetadata(UnitMetadata.class, unit));
			} catch (MetadataException e) {
				// do nothing
			}
		}
		if (name != null) {
			d.setName(name);
		}
		return d;
	}

	/**
	 * Check that a named process exists in NeXus tree (under entry/NXprocess/NXnote)
	 * @param operation
	 * @param entry entry in tree
	 * @param processName
	 * @return NXnote group
	 * @throw {@link OperationException}
	 */
	public static GroupNode checkForProcess(IOperation<?, ?> operation, GroupNode entry, String processName) throws OperationException {
		if (entry == null) {
			throw new OperationException(operation, "No entry given");
		}

		NodeLink link = NexusTreeUtils.findFirstNode(entry, NexusConstants.PROCESS);
		Node node = link == null ? null : link.getDestination();
		if (node == null) {
			throw new OperationException(operation, NexusConstants.PROCESS + " node does not exist");
		}
	
		if (!(node instanceof GroupNode)) {
			throw new OperationException(operation, NexusConstants.PROCESS + " node must be a group node");
		}
	
		GroupNode group = (GroupNode) node;
		
		for (GroupNode g : group.getGroupNodes()) {
			if (NexusTreeUtils.isNXClass(g, NexusConstants.NOTE)) {
				DataNode n = g.getDataNode("name");
				if (n != null) {
					try {
						if (processName.equals(NexusTreeUtils.getFirstString(n))) {
							return g;
						}
					} catch (NexusException e) {
						throw new OperationException(operation, "'name' dataset not found in " + NexusConstants.PROCESS);
					}
				}
			}
		}
	
		throw new OperationException(operation, NexusConstants.PROCESS + " node not found: " + processName);
	}
	
	/**
	 * Resolve the full path to a file from a relative path string
	 * and the path to a base file.
	 * <p>
	 * If relative, the path input string must use unix-style forward slash
	 * as a separator.
	 * <p>
	 * If path is an absolute path it will be returned unmodified.
	 * 
	 * If baseFile is null, path will be returned even if relative.
	 * 
	 * @param path
	 * @param baseFile
	 * @return fullPath
	 */
	public static String resolvePath(String path, String baseFile) {
		
		FileSystem fs = FileSystems.getDefault();
		
		Path p = fs.getPath(path);
		
		//hopefully if baseFile is null returning the relative path
		// should show a sensible error
		if (p.isAbsolute() || baseFile == null) {
			return path;
		}
		
		Path pp = fs.getPath(baseFile);
		Path resolved = pp.getParent().resolve(p);
		return resolved.normalize().toString();
	}

	/**
	 * Resolve the full path to a file from a relative path string
	 * and the path to a base file from the {@link SliceFromSeriesMetadata} in the dataset.
	 * <p>
	 * Extracts the path to the file containing the dataset from the metadata, then calls
	 * {@link ProcessingUtils#resolvePath(String, String)}
	 * 
	 * @param path
	 * @param dataset
	 * @return fullPath
	 */
	public static String resolvePath(String path, IDataset dataset) {
		
		SliceFromSeriesMetadata md = dataset.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		String baseFile = null;
		
		if (md != null) {
			baseFile = md.getFilePath();
		}
		
		return resolvePath(path, baseFile);
		
	}
	
	public static IDataset getMatchingValue(IOperation<?,?> op, IDataset input, String filePath, String datasetName) throws OperationException {
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);

		if (ssm == null) {
			throw new OperationException(op, "No Metadata found!");
		}

		ILazyDataset lz = ProcessingUtils.getLazyDataset(op, filePath, datasetName);
		IDataset val = null;

		try {
			if (ShapeUtils.squeezeShape(lz.getShape(), false).length == 0) {
				// scalar lz
				val = lz.getSlice();
			} else {
				// vector lz
				val = ssm.getMatchingSlice(lz);
			}
		} catch (DatasetException e) {
			throw new OperationException(op, e);
		}

		// If a matching val was not found, throw
		if (val == null) throw new OperationException(op, "Dataset " + datasetName + " " + Arrays.toString(lz.getShape()) + 
				" not a compatable shape with " + Arrays.toString(ssm.getParent().getShape()));
		val.squeeze();

		// A non-scalar val is an error at this point
		if (val.getRank() != 0) throw new OperationException(op, "External data shape invalid");

		return val;
	}

	// string that contains a set of digits before a period and another substring 
	private static final Pattern NUMBERED_FILE_REGEX = Pattern.compile(".*?([0-9]+)\\.\\w+");

	/**
	 * Get Diamond scan number from file name
	 * @param name expected to contain a set of digits before a period and another substring
	 * @return scan number or null if name does not match expected pattern
	 */
	public static Integer getScanNumber(String name) {
		Matcher m = NUMBERED_FILE_REGEX.matcher(name);
		if (!m.matches()) {
			return null;
		}
		String digits = m.group(1);
		return Integer.valueOf(digits);
	}

	/**
	 * Get formatted string from retrieved scan number
	 * @param name expected to contain a set of digits before a period and another substring
	 * @param delta change in scan number
	 * @param format string containing formatting to insert prefix, new scan number string, suffix (i.e. must contain three %s)
	 * @return formatted string or null if name does not match expected pattern
	 */
	public static String getNextScanString(String name, int delta, String format) {
		Matcher m = NUMBERED_FILE_REGEX.matcher(name);
		if (!m.matches()) {
			return null;
		}
		String digits = m.group(1);
		int scan = Integer.parseInt(digits) + delta;
		String ss = String.format(String.format("%%0%dd", digits.length()), scan); // preserve zero-padding
		String fmt = String.format(format, name.substring(0, m.start(1)), ss, name.substring(m.end(1)));
		return String.format(fmt, scan);
	}

	/**
	 * Get first originating file from lazy dataset's metadata
	 * @param lazy
	 * @return file or null
	 */
	public static String getFirstOriginatingFile(ILazyDataset lazy) {
		try {
			List<OriginMetadata> oms = lazy.getMetadata(OriginMetadata.class);
			if (oms != null && !oms.isEmpty()) {
				// OM are added depth-first so top most is last
				return oms.get(oms.size() - 1).getFilePath();
			}
		} catch (MetadataException e) {
		}
		return null;
	}

	/**
	 * Get NXdetector group
	 * @param operation
	 * @param filePath
	 * @return group
	 * @throw {@link OperationException}
	 */
	public static GroupNode getNXdetector(IOperation<?, ?> operation, String filePath) throws OperationException {
		try {
			Tree t = ProcessingUtils.getTree(operation, filePath);

			return NexusTreeUtils.findFirstDetector(t);
		} catch (Exception e) {
			throw new OperationException(operation, "Could not parse Nexus file " + filePath + " for an NXdetector group", e);
		}
	}
}
