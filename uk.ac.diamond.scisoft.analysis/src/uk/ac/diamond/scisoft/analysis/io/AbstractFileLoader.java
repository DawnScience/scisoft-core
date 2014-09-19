/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;

/**
 * A class which can be extended when implementing IFileLoader
 */
public abstract class AbstractFileLoader implements IFileLoader {
	/** 
	 * Name prefix for an image dataset (should be followed by two digits, starting with 01)
	 */
	public static final String IMAGE_NAME_PREFIX = "image-";

	/**
	 * Default name for first image dataset
	 */
	public static final String DEF_IMAGE_NAME = IMAGE_NAME_PREFIX + "01";

	/**
	 * Name for image stack
	 */
	public static final String STACK_NAME = "image-stack";

	/**
	 * String to separate full file path or file name from dataset name
	 */
	public static final String FILEPATH_DATASET_SEPARATOR = ":";

	protected boolean loadMetadata = true;

	@Override
	public IDataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		return loadFile();
	}

	@Override
	public void setLoadMetadata(boolean willLoadMetadata) {
		loadMetadata = willLoadMetadata;
	}

	/**
	 * @param mon
	 * @return false if cancelled
	 */
	protected boolean monitorIncrement(IMonitor mon) {
		if (mon!=null) {
			mon.worked(1);
			if (mon.isCancelled()) return false;
		}
		return true;
	}
}
