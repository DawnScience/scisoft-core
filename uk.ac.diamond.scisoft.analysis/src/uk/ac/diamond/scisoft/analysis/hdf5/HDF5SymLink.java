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
	 * Get node referenced by symbolic link
	 * @return node
	 */
	public HDF5Node getNode() {
		HDF5NodeLink l = file.findNodeLink(path);
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
