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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShapeUtils;

import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;

public class ProcessingUtils {
	
	public static ILazyDataset getLazyDataset(IOperation<?, ?> op, String filepath, String datasetName) throws OperationException {
		
		IDataHolder dh = null;
		
		try {
			dh = LocalServiceManager.getLoaderService().getData(filepath,null);
		} catch (Exception e) {
			//ignore
		}
		
		if (dh == null) throw new OperationException(op,"Error opening file: " + filepath);
		
		ILazyDataset lz = dh.getLazyDataset(datasetName);
		
		if (lz == null) throw new OperationException(op,"Error reading dataset: " + datasetName);
		
		return lz;
	}

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
	 * @param format
	 * @param n
	 * @return dataset
	 */
	public static Dataset createNamedDataset(Serializable obj, String format, int n) {
		return createNamedDataset(obj, format == null ? null : String.format(format, n));
	}

	/**
	 * Create dataset with name
	 * @param obj
	 * @param name
	 * @return dataset
	 */
	public static Dataset createNamedDataset(Serializable obj, String name) {
		Dataset d = DatasetFactory.createFromObject(obj);
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
					if (processName.equals(NexusTreeUtils.parseStringArray(n)[0])) {
						return g;
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
}
