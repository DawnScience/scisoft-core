/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.emulated;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of {@link IFileLoader} that wraps a 'real' loader to emulate the latency of file availability and load time. 
 */
public class FileSystemEmulatingFileLoader implements IFileLoader {

	transient private static final Logger logger = LoggerFactory.getLogger(FileSystemEmulatingFileLoader.class);

	private final IFileLoader loader;
	private long emulatedFileAvailableTimeMillis;
	private final long emulatedFileLoadTimeMillis;

	/**
	 * Creates a FileSystem emulator that wraps a real {@link IFileLoader} loader.
	 * @param loader the real loader to wrap
	 * @param emulatedFileAvailabilityLatencyMillis
	 * @param emulatedFileLoadTimeMillis
	 */
	public FileSystemEmulatingFileLoader(IFileLoader loader, long emulatedFileAvailabilityLatencyMillis,
			long emulatedFileLoadTimeMillis) {
		this.loader = loader;
		this.emulatedFileLoadTimeMillis = emulatedFileLoadTimeMillis;
		long currentTimeMillis = System.currentTimeMillis();
		emulatedFileAvailableTimeMillis = currentTimeMillis + emulatedFileAvailabilityLatencyMillis;
	}

	@Override
	public IDataHolder loadFile() throws ScanFileHolderException {
		waitForFileAvailable();
		long loadStartTime = System.currentTimeMillis();
		IDataHolder loadedFile = loader.loadFile();
		waitForFileLoaded(loadStartTime);
		return loadedFile;
	}

	@Override
	public IDataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		waitForFileAvailable();
		long loadStartTime = System.currentTimeMillis();
		IDataHolder loadedFile = loader.loadFile(mon);
		waitForFileLoaded(loadStartTime);
		return loadedFile;
	}

	private void waitForFileAvailable() throws ScanFileHolderException {
		try {
			sleepUntil(emulatedFileAvailableTimeMillis);
		} catch (InterruptedException e) {
			throw new ScanFileHolderException("Interupted while sleeping to emulate file availability", e);
		}
	}

	private void waitForFileLoaded(long fileLoadStartTime) throws ScanFileHolderException {
		long physicalLoadDeltaTime = System.currentTimeMillis() - fileLoadStartTime;
		if (physicalLoadDeltaTime <= emulatedFileLoadTimeMillis) {
			try {
				Thread.sleep(emulatedFileLoadTimeMillis - physicalLoadDeltaTime);
			} catch (InterruptedException e) {
				throw new ScanFileHolderException("Interupted while sleeping to emulate file load time", e);
			}
		} else {
			logger.warn("File took longer to load ({}ms) than the desired emulated time ({}ms)", physicalLoadDeltaTime,emulatedFileLoadTimeMillis);
		}
	}

	private void sleepUntil(long timeMillis) throws InterruptedException {
		long sleepForMillis = timeMillis - System.currentTimeMillis();
		if (sleepForMillis > 0) {
			Thread.sleep(sleepForMillis);
		}
	}

	@Override
	public void setLoadMetadata(boolean willLoadMetadata) {
	}

	@Override
	public void setLoadAllLazily(boolean willLoadLazily) {
	}
}
