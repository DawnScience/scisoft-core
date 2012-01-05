/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	/**
	 * Construct a HDF5 node link
	 * @param path to source
	 * @param link name (ends in '/' for groups)
	 * @param source node which link starts from (can be null)
	 * @param destination node which link points to
	 */
	public HDF5NodeLink(final String path, final String link, final HDF5Node source, final HDF5Node destination) {
		if (link == null || destination == null) {
			throw new IllegalArgumentException("Path name, link name and destination must be defined");
		}
		this.path = path == null ? "" : path;
		name = link;
		from = source;
		to = destination;
//		if ((to instanceof HDF5Group) && !name.endsWith(HDF5Node.SEPARATOR)) {
//			throw new IllegalArgumentException("If destination is a group then name must end with a separator character");
//		}
	}

	/**
	 * @return source node
	 */
	public HDF5Node getSource() {
		return from;
	}

	/**
	 * @return destination node
	 */
	public HDF5Node getDestination() {
		return to;
	}

	/**
	 * @return true if destination node is a dataset
	 */
	public boolean isDestinationADataset() {
		return to instanceof HDF5Dataset;
	}

	/**
	 * @return true if destination node is a group
	 */
	public boolean isDestinationAGroup() {
		return to instanceof HDF5Group;
	}

	/**
	 * @return true if destination node is a symbolic link
	 */
	public boolean isDestinationASymLink() {
		return to instanceof HDF5SymLink;
	}

	/**
	 * @return name of node link
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return path of node link
	 */
	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return path + name + '\n' + to.toString();
	}

	/**
	 * @return full name of node link
	 */
	public String getFullName() {
		return path + name;
	}
}
