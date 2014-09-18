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
