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
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;

public class ProcessingUtils {
	
	public static ILazyDataset getLazyDataset(IOperation op, String filepath, String datasetName) throws OperationException {
		
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

	public static IDataset getDataset(IOperation op, String filepath, String datasetName) throws OperationException {
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
	 * @return 
	 * @throw {@link OperationException}
	 */
	public static GroupNode checkForProcess(IOperation<?, ?> operation, GroupNode entry, String processName) throws OperationException {
		Node node = NexusTreeUtils.findFirstNode(entry, "NXprocess").getDestination();
		if (node == null) {
			throw new OperationException(operation, "No NXprocess node exist");
		}
	
		if (!(node instanceof GroupNode)) {
			throw new OperationException(operation, "NXprocess node must be a group node");
		}
	
		GroupNode group = (GroupNode) node;
		
		for (GroupNode g : group.getGroupNodes()) {
			if (NexusTreeUtils.isNXClass(g, "NXnote")) {
				DataNode n = g.getDataNode("name");
				if (n != null) {
					if (processName.equals(NexusTreeUtils.parseStringArray(n)[0])) {
						return g;
					}
				}
			}
		}
	
		throw new OperationException(operation, "NXprocess node not found: " + processName);
	}

	public static void addAxes(IDataset d, Dataset... axes) {
		if (d.getRank() == 0) {
			return;
		}
		AxesMetadata am;
		try {
			am = MetadataFactory.createMetadata(AxesMetadata.class, d.getRank());
			for (int i = 0; i < axes.length; i++) {
				Dataset a = axes[i];
				if (a != null) {
					try {
						am.addAxis(i, a);
					} catch (Exception e) {
						System.err.println(Arrays.toString(a.getShapeRef()) + " cf " + Arrays.toString(d.getShape()));
					}
				}
				
			}
			d.addMetadata(am);
		} catch (Exception e) {
		}
	}
}
