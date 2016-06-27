/*
 * Copyright (c) 2012, 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;

/**
 * This class loads an SRS data file where the first column of the srs file might be non-numerical
 * <p>
 */
public class ExtendedSRSLoader2 extends SRSLoader {

	public ExtendedSRSLoader2(String filename) {
		super(filename);
		checkForMoreMetadata = false;
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		return super.loadFile(mon);
	}

	@Override
	public void loadMetadata(IMonitor mon) throws IOException {
		super.loadMetadata(mon);
	}
}
