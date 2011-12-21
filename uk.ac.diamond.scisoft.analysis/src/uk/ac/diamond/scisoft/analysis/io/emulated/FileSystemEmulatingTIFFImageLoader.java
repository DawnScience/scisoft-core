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

import gda.analysis.io.IFileLoader;
import gda.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.TIFFImageLoader;
import uk.ac.gda.monitor.IMonitor;

/**
 * An implementation of {@link IFileLoader} that wraps a 'real' {@link TIFFImageLoader} with a
 * {@link FileSystemEmulatingFileLoader}. File availability latency and file load time are configured with static
 * class variable to allow this loader to substitute for a {@link TIFFImageLoader} without changing the constructor.
 */
public class FileSystemEmulatingTIFFImageLoader implements IFileLoader {

	private static long emulatedFileAvailabilityLatencyMillis = 0;

	private static long emulatedFileLoadTimeMillis = 0;

	private FileSystemEmulatingFileLoader fileSystemEmulatingFileLoader;

	/**
	 * Creates a {@link TIFFImageLoader} wrapped in a{@link FileSystemEmulatingFileLoader}
	 * @param filename
	 */
	public FileSystemEmulatingTIFFImageLoader(String filename) {
		fileSystemEmulatingFileLoader = new FileSystemEmulatingFileLoader(new TIFFImageLoader(filename),
				getEmulatedFileAvailabilityLatencyMillis(), getEmulatedFileLoadTimeMillis());
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return fileSystemEmulatingFileLoader.loadFile();
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		return fileSystemEmulatingFileLoader.loadFile(mon);
	}

	//

	public static void setEmulatedFileAvailabilityLatencyMillis(long emulatedFileAvailabilityLatencyMillis) {
		FileSystemEmulatingTIFFImageLoader.emulatedFileAvailabilityLatencyMillis = emulatedFileAvailabilityLatencyMillis;
	}

	public static long getEmulatedFileAvailabilityLatencyMillis() {
		return emulatedFileAvailabilityLatencyMillis;
	}

	public static void setEmulatedFileLoadTimeMillis(long emulatedFileLoadTimeMillis) {
		FileSystemEmulatingTIFFImageLoader.emulatedFileLoadTimeMillis = emulatedFileLoadTimeMillis;
	}

	public static long getEmulatedFileLoadTimeMillis() {
		return emulatedFileLoadTimeMillis;
	}

	@Override
	public void setLoadMetadata(boolean willLoadMetadata) {
		// TODO Auto-generated method stub
		
	}

}
