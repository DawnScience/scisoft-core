/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.exceptions.HDF5LibraryException;

public class HDF5GroupResource extends HDF5BaseResource {

	private static final Logger logger = LoggerFactory.getLogger(HDF5GroupResource.class);

	/**
	 * Wrap the specified group resource identifier
	 * @param resource group identifier to wrap
	 */
	public HDF5GroupResource(long resource) {
		super(resource);
	}

	@Override
	public void close() {
		try {
			H5.H5Gclose(resource);
		} catch (HDF5LibraryException e) {
			logger.error("Could not close HDF5 group", e);
		}
	}

}

