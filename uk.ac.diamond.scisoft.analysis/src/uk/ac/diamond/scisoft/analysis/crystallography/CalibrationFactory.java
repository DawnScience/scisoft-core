/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.HashSet;

import org.jscience.physics.amount.Amount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.core.util.CompositeClassLoader;

/**
 * CalibrationFactory when we go to e4 like all xxxFactory classes this will become
 * ICalibrationService and be injected.
 */
public class CalibrationFactory {

	private static Logger logger = LoggerFactory.getLogger(CalibrationFactory.class);
	
	/**
	 * Reads the calibration standards from disk, creating a new one if required.
	 * 
	 * Only if save is called will they be persisted for future use.
	 * 
	 * @return CalibrationStandards
	 */
	public static CalibrationStandards getCalibrationStandards() {
		return getCalibrationStandards(false);
	}
	
	private static CalibrationStandards staticInstance;
	/**
	 * 
	 * @param createNew
	 * @return cs
	 */
	public static CalibrationStandards getCalibrationStandards(boolean createNew) {
		if (createNew) {
			return createCalibrationStandards();
		}

		if (staticInstance==null) {
			staticInstance = createCalibrationStandards();
			//staticInstance.setUnmodifiable(true);
		}
		return staticInstance;
	}


	/**
	 * Call to save CalibrationStandards to disk
	 * @param cs
	 * @throws Exception
	 */
	static void saveCalibrationStandards(CalibrationStandards cs) throws Exception {
		
		if (cs == null)                return;
		
		XMLEncoder  encoder       = null;
		try {
			final File calFile = getCalibrantFile();
			calFile.getParentFile().mkdirs();
			calFile.createNewFile();
			encoder = new XMLEncoder(new FileOutputStream(getCalibrantFile()));
			
			encoder.writeObject(cs);
			encoder.flush();
			
		} finally  {
			if (encoder!=null) encoder.close();
		}
		
		CalibrationStandards old = staticInstance;
		staticInstance = cs;
		if (old!=null && old.getSelectedCalibrant()!=null) {
		    fireCalibrantSelectionListeners(cs, cs.getSelectedCalibrant());
		}

	}
	
	static CalibrationStandards readCalibrationStandards() throws Exception {
		final XMLDecoder decoder = new XMLDecoder(new FileInputStream(getCalibrantFile()));
		final ClassLoader originalLoader = setCustomClassLoader();
		try {
			final CalibrationStandards cs = (CalibrationStandards) decoder.readObject();
			cs.setModifiable(true);
			return cs;
		} finally {
			Thread.currentThread().setContextClassLoader(originalLoader);
			decoder.close();
		}
	}

	private static ClassLoader setCustomClassLoader() {
		final ClassLoader originalLoader = Thread.currentThread().getContextClassLoader();

		final CompositeClassLoader customLoader = new CompositeClassLoader();
		customLoader.add( CalibrationStandards.class.getClassLoader());
		customLoader.add(Amount.class.getClassLoader());

		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			@Override
			public Object run() {
				Thread.currentThread().setContextClassLoader(customLoader);
				return null;
			}
		});	
			
		return originalLoader;
	}
			


	/**
	 * TODO Best place to keep it? Seems to work when tested.
	 * @return file
	 */
	private static File getCalibrantFile() {
		File dir = new File(new File(System.getProperty("user.home")), ".dawn");
		try {
			dir.mkdirs();
		} catch (Throwable ne) {
			try {
				logger.error("Cannot store calibration standards!", ne);
				return File.createTempFile("CalibrationStandards", "xml");
			} catch (Throwable neOther) {
				logger.error("Cannot create files! Dawn will not function correctly. Please contact you support representative.", neOther);
			    return null;
			}
		}
		return new File(dir, "CalibrationStandards.xml");
	}

	private static CalibrationStandards createCalibrationStandards() {
		final File file = getCalibrantFile();
		CalibrationStandards cs = null;
		if (file.exists()) {
			try {
				cs = readCalibrationStandards();
			} catch (Exception e) {
				cs = null;
			}
		}
		if (cs==null || cs.isEmpty() || VersionUtils.isOldVersion(CalibrationStandards.CURRENT_VERSION, cs.getVersion())) {
			cs = new CalibrationStandards();
			cs.setVersion(CalibrationStandards.CURRENT_VERSION); // Versions are so that we can wipe out configurations with new Dawn versions if we have to
			                        // TODO consider new file for this instead.
			cs.setCal2peaks(CalibrationStandards.createDefaultCalibrants());
			cs.setSelectedCalibrant("Silicon", false);
			try {
				saveCalibrationStandards(cs);
			} catch (Exception e) {
				logger.error("Cannot save calibration standards!", e);
			}
			return cs;
		}
		
		return cs;
	}

	private static Collection<CalibrantSelectedListener> listeners;
	private static boolean processingListeners = false;
	/**
	 * 
	 * @param calibrant
	 */
	static void fireCalibrantSelectionListeners(CalibrationStandards standards, String calibrant) {
		if (listeners==null)     return;
		if (processingListeners) return;
		
		try {
			processingListeners = true;
			final CalibrantSelectionEvent evt = new CalibrantSelectionEvent(standards, calibrant);
			for (CalibrantSelectedListener l : listeners) {
				try {
				    l.calibrantSelectionChanged(evt);
				} catch (Throwable ne) {
					logger.error("Cannot fire calibrant selection changed!", ne);
				}
			}
		} finally {
			processingListeners = false;
		}
	}

	public static void addCalibrantSelectionListener(CalibrantSelectedListener l) {
		if (listeners==null) listeners = new HashSet<CalibrantSelectedListener>(7);
		listeners.add(l);
	}
	public static void removeCalibrantSelectionListener(CalibrantSelectedListener l) {
		if (listeners==null) return;
		listeners.remove(l);
	}


}
