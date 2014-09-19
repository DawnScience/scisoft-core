/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

import java.io.Serializable;

/**
 * Link two HDF5 nodes together. The name of the link provides a reference for users to the destination node
 */
public class HDF5NodeLink implements Serializable {
	private HDF5Node from;
	private HDF5Node to;
	private String name;
	private String path;
	private HDF5File file;

	/**
	 * A node link
	 * @param path to source
	 * @param link name
	 * @param source node which link starts from (can be null)
	 * @param destination node which link points to
	 */
	public HDF5NodeLink(final HDF5File file, final String path, final String link, final HDF5Node source, final HDF5Node destination) {
		if (link == null || destination == null) {
			throw new IllegalArgumentException("Path name, link name and destination must be defined");
		}

		this.file = file;
		this.path = path == null ? "" : path;
		name = link;
		from = source;
		to = destination;
	}

	public HDF5Node getSource() {
		return from;
	}

	public HDF5Node getDestination() {
		return to;
	}

	public boolean isDestinationADataset() {
		return to instanceof HDF5Dataset;
	}

	public boolean isDestinationAGroup() {
		return to instanceof HDF5Group;
	}

	public boolean isDestinationASymLink() {
		return to instanceof HDF5SymLink;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return path + name + '\n' + to.toString();
	}

	public String getFullName() {
		return path + name;
	}

	public HDF5File getFile() {
		return file;
	}
}
