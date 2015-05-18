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

package uk.ac.diamond.scisoft.analysis.io;

import gda.data.nexus.extractor.NexusGroupData;
import gda.data.nexus.tree.INexusTree;
import gda.data.nexus.tree.NexusTreeTopNode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

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
	public List<Dataset> getDatasets(String name) {
		List<Dataset> datasets = new ArrayList<Dataset>();

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
	private void getDatasets(String name, INexusTree node, List<Dataset> datasets) {

		for (int i = 0; i < node.getNumberOfChildNodes(); i++) {
			// if the node has the name we are looking for, then try to get the data out of it
			INexusTree child = node.getChildNode(i);
			if (child.getName().equals(name)) {
				NexusGroupData data = child.getData();
				if (data != null) {
					try {
						datasets.add(data.toDataset(false));
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
