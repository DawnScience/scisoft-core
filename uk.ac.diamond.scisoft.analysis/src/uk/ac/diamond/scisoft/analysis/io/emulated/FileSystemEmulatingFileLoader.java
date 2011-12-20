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

package uk.ac.diamond.scisoft.analysis.io.emulated;

import gda.analysis.io.IFileLoader;
import gda.analysis.io.ScanFileHolderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.gda.monitor.IMonitor;

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
	public DataHolder loadFile() throws ScanFileHolderException {
		waitForFileAvailable();
		long loadStartTime = System.currentTimeMillis();
		DataHolder loadedFile = loader.loadFile();
		waitForFileLoaded(loadStartTime);
		return loadedFile;
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		waitForFileAvailable();
		long loadStartTime = System.currentTimeMillis();
		DataHolder loadedFile = loader.loadFile(mon);
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
		// TODO Auto-generated method stub
		
	}
}
