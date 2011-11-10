/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

/**
 * Symbolic link to another node
 */
public class HDF5SymLink extends HDF5Node {
	private HDF5File file;
	private String path;

	/**
	 * @param oid object ID
	 * @param fileWithNode
	 * @param pathToNode (ends in separator if group, otherwise a dataset)
	 */
	public HDF5SymLink(final long oid, final HDF5File fileWithNode, final String pathToNode) {
		super(oid);
		file = fileWithNode;
		path = pathToNode;
	}

	public HDF5Node getNode() {
		HDF5NodeLink l = file.findNodeLink(path);
		return l != null ? l.getDestination() : null;
	}

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
