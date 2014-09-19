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

import uk.ac.diamond.scisoft.analysis.io.TIFFImageLoader;

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
	public IDataHolder loadFile() throws ScanFileHolderException {
		return fileSystemEmulatingFileLoader.loadFile();
	}

	@Override
	public IDataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
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
