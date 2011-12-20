/*
 * Copyright © 2011 Diamond Light Source Ltd.
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
