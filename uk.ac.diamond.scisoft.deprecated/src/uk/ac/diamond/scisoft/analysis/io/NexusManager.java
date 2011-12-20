/*
 * Copyright © 2010 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.data.nexus.extractor.NexusGroupData;
import gda.data.nexus.tree.INexusTree;
import gda.data.nexus.tree.NexusTreeTopNode;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Nexus;

/**
 * Simple class which wraps a nexus structure and provides some easy methods for extracting data from it.
 */
public class NexusManager {

	NexusTreeTopNode nexusTreeTopNode = null;

	public NexusManager(NexusTreeTopNode nexusTreeTopNode) {
		this.nexusTreeTopNode = nexusTreeTopNode;
	}

	public NexusTreeTopNode getNexusTreeTopNode() {
		return nexusTreeTopNode;
	}

	/**
	 * Get a list of datasets from tree that come from nodes with matching name
	 * @param name
	 * @return list of datasets (can be empty)
	 */
	public List<AbstractDataset> getDatasets(String name) {
		List<AbstractDataset> datasets = new ArrayList<AbstractDataset>();

		getDatasets(name, nexusTreeTopNode, datasets);

		return datasets;
	}

	@Override
	public String toString() {
		return this.nexusTreeTopNode.toString();
	}

	/**
	 * @param name
	 *            The name of the item which is being looked for
	 * @param node
	 *            the NeXus node to look in
	 * @param datasets
	 *            The list to which the dataset shall be added
	 */
	private void getDatasets(String name, INexusTree node, List<AbstractDataset> datasets) {

		for (int i = 0; i < node.getNumberOfChildNodes(); i++) {
			// if the node has the name we are looking for, then try to get the data out of it
			INexusTree child = node.getChildNode(i);
			if (child.getName().equals(name)) {
				NexusGroupData data = child.getData();
				if (data != null) {
					try {
						datasets.add(Nexus.createDataset(data, false));
					} catch (IllegalArgumentException e) {
						// ignore
					}
				}
			}

			// Anyway, recursively look inside to try to get all the nodes
			getDatasets(name, child, datasets);
		}
	}
}
