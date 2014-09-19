/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

/**
 * Symbolic link to another node
 */
public class HDF5SymLink extends HDF5Node {
	private HDF5File file;
	private String path;

	/**
	 * Construct a HDF5 symbolic link with given object ID, file name and node path
	 * @param oid object ID
	 * @param fileWithNode
	 * @param pathToNode (ends in separator if group, otherwise a dataset)
	 */
	public HDF5SymLink(final long oid, final HDF5File fileWithNode, final String pathToNode) {
		super(oid);
		file = fileWithNode;
		path = pathToNode;
	}

	/**
	 * Get node link referenced by symbolic link
	 * @return node
	 */
	public HDF5NodeLink getNodeLink() {
		return file.findNodeLink(path);
	}

	/**
	 * Get node referenced by symbolic link
	 * @return node
	 */
	public HDF5Node getNode() {
		HDF5NodeLink l = getNodeLink();
		return l != null ? l.getDestination() : null;
	}

	/**
	 * @return true if linked node is a dataset
	 */
	public boolean isDataset() {
		return !path.endsWith(SEPARATOR);
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		String attrs = super.toString();
		out.append(attrs);
		return out.toString();
	}
}
