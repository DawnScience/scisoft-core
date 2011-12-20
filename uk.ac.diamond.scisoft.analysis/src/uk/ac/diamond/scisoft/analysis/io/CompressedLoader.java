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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;


import uk.ac.gda.monitor.IMonitor;
import uk.ac.gda.util.io.FileUtils;
import gda.analysis.io.ScanFileHolderException;

public class CompressedLoader extends AbstractFileLoader  implements IMetaLoader {

	private AbstractFileLoader loader;
	
	public CompressedLoader() {
		
	}
	
	public CompressedLoader(final String file) throws Exception {
		setFile(file);
	}
	
	private static final Pattern ZIP_PATH = Pattern.compile("(.+)\\.(.+)\\."+LoaderFactory.getZipExpression());
	
	public void setFile(final String file) throws Exception {
		
		final Matcher m = ZIP_PATH.matcher((new File(file)).getName());
		if (m.matches()) {
			
			final String name     = m.group(1);
			final String ext      = m.group(2);
			final String zipType  = m.group(3);
			
			final Class<? extends InputStream>   clazz = LoaderFactory.getZipStream(zipType);
			final Constructor<? extends InputStream> c = clazz.getConstructor(InputStream.class);
			
			final InputStream in = c.newInstance(new FileInputStream(file));
			// Hack zip files
			if (in instanceof ZipInputStream) {
				((ZipInputStream)in).getNextEntry();
			}
			
			final File tmp = File.createTempFile(name, "."+ext);
			tmp.deleteOnExit();
			
			// This is slow and unecessary. Should refactor LoaderFactory to 
			// work either from a file path or in memory representation.
			FileUtils.write(new BufferedInputStream(in), tmp);
			
			final Class<? extends AbstractFileLoader> lclass = LoaderFactory.getLoaderClass(ext);
			this.loader = LoaderFactory.getLoader(lclass, tmp.getAbsolutePath());
		}
        
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loader.loadFile();
	}
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		return loader.loadFile(mon);
	}
	
	@Override
	public void loadMetaData(IMonitor mon) throws Exception {
		loader.loadFile(mon);
    }
	
	@Override
	public IMetaData getMetaData() {
		if (loader instanceof IMetaLoader) return ((IMetaLoader)loader).getMetaData();
		return new MetaDataAdapter();
	}
	
}
