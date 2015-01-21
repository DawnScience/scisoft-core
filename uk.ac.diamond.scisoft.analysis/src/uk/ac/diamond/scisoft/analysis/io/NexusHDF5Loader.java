/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.tree.NodeLink;

/**
 * This class is a HDF5Loader with extra things associated by the nexus standard. Primarily if an ILazyDataset is
 * loaded, it will attempt to load the errors associated with the dataset.
 */
public class NexusHDF5Loader extends HDF5Loader {
	@Override
	public void augmentLink(NodeLink link) {
		if (!link.isDestinationData()) {
			logger.warn("Cannot augment non-data node: {}", link);
			return;
		}
		try {
			NexusTreeUtils.augmentNodeLink(link, true);
		} catch (Exception e) {
			logger.debug("Problem augmenting node: {}", link, e);
		}
	}
}
