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

package uk.ac.diamond.scisoft.analysis.io.emulated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.IFileLoader;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

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
		// TODO Auto-generated method stub
		
	}
}
