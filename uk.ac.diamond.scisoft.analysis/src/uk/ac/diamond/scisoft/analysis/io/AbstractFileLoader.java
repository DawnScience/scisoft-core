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

import gda.analysis.io.IFileLoader;
import gda.analysis.io.ScanFileHolderException;
import uk.ac.gda.monitor.IMonitor;

/**
 * A class which can be extended when implementing IFileLoader
 */
public abstract class AbstractFileLoader implements IFileLoader {
	protected boolean loadMetadata = true;

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		return loadFile();
	}

	@Override
	public void setLoadMetadata(boolean willLoadMetadata) {
		loadMetadata = willLoadMetadata;
		return;
	}

	protected boolean monitorIncrement(IMonitor mon) {
		if (mon!=null) {
			mon.worked(1);
			if (mon.isCancelled()) return false;
		}
		return true;
	}
}
