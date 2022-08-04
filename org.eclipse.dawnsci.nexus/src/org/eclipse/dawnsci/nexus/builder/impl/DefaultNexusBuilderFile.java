/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.builder.impl;

import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultNexusBuilderFile implements NexusBuilderFile {

	private static final Logger logger = LoggerFactory.getLogger(DefaultNexusBuilderFile.class);
	
	private final String filePath;
	
	private final boolean useSwmr;
	
	private NexusFile nexusFile = null;
	
	protected DefaultNexusBuilderFile(final String filePath, boolean useSwmr) {
		this.filePath = filePath;
		this.useSwmr = useSwmr;
	}
	
	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public void openToWrite() throws NexusException {
		if (nexusFile != null) {
			throw new IllegalStateException("NexusFile is already open.");
		}
		final INexusFileFactory nexusFileFactory = ServiceHolder.getNexusFileFactory();
		nexusFile = nexusFileFactory.newNexusFile(filePath, true);
		nexusFile.openToWrite(false); // file must already exist on disk
		
		if (useSwmr) {
			try {
			    nexusFile.activateSwmrMode();
			} catch (NexusException e) {
				// We are not in SWMR mode so we allow a non-SWMR write, which works but other processes may have issues.
				logger.warn("NexusFile {} is not SWMR ", nexusFile.getFilePath(), e);
			}
		}
	}

	@Override
	public int flush() throws NexusException {
		if (nexusFile == null) return -1;  // Legal flush can be called on non-SWMR files and does nothing.
		return nexusFile.flush();
	}

	@Override
	public void close() throws NexusException {
		if (nexusFile == null) return; // Legal this is an AutoClosable that does if it can.
		nexusFile.flush();
		nexusFile.close();
		nexusFile = null;
	}

}
