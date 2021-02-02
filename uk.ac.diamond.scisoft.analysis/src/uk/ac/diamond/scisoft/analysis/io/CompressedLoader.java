/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.io.IMetaLoader;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;

import uk.ac.diamond.scisoft.analysis.utils.FileUtils;

public class CompressedLoader extends AbstractFileLoader {

	private IFileLoader loader;
	
	public CompressedLoader() {
		
	}
	
	public CompressedLoader(final String file) throws Exception {
		setFile(file);
		initFile();
	}

	@Override
	protected void clearMetadata() {
	}

	private static final Pattern ZIP_PATH = Pattern.compile("(.+)\\.(.+)\\."+LoaderFactory.getZipExpression());
	
	public void initFile() throws Exception {
		
		final Matcher m = ZIP_PATH.matcher((new File(fileName)).getName());
		if (m.matches()) {
			
			final String name     = m.group(1);
			final String ext      = m.group(2);
			final String zipType  = m.group(3);
			
			final Class<? extends InputStream>   clazz = LoaderFactory.getZipStream(zipType);
			final Constructor<? extends InputStream> c = clazz.getConstructor(InputStream.class);
			
			final InputStream in = c.newInstance(new FileInputStream(fileName));
			// Hack zip files
			if (in instanceof ZipInputStream) {
				((ZipInputStream)in).getNextEntry();
			}
			
			final File tmp = File.createTempFile(name, "."+ext);
			tmp.deleteOnExit();
			
			// This is slow and unnecessary. Should refactor LoaderFactory to 
			// work either from a file path or in memory representation.
			FileUtils.write(new BufferedInputStream(in), tmp);
			
			final Class<? extends IFileLoader> lclass = LoaderFactory.getLoaderClass(ext);
			this.loader = LoaderFactory.getLoader(lclass, tmp.getAbsolutePath());
		}
        
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		IDataHolder dh = loader.loadFile();
		return dh instanceof DataHolder ? (DataHolder) dh : null;
	}
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		IDataHolder dh = loader.loadFile(mon);
		return dh instanceof DataHolder ? (DataHolder) dh : null;
	}
	
	@Override
	public void loadMetadata(IMonitor mon) throws IOException {
		try {
			loader.loadFile(mon);
		} catch (ScanFileHolderException e) {
			throw new IOException(e);
		}
	}

	@Override
	public IMetadata getMetadata() {
		if (loader instanceof IMetaLoader) return ((IMetaLoader)loader).getMetadata();
		return new Metadata();
	}
	
}
