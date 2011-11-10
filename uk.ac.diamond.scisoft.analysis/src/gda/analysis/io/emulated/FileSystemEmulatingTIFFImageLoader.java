/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.io.emulated;

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
