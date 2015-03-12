/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.osgi;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.osgi.preference.PreferenceConstants;

public class LoaderFactoryStartup implements IStartup {

	private static final Logger logger = LoggerFactory.getLogger(LoaderFactoryStartup.class);

	private static Set<String> plugins;
	private static Set<String> extensions;
	private static boolean     started=false;
		
	@Override
	public void earlyStartup() {
		plugins    = new HashSet<String>();
		extensions = new HashSet<String>();
		registerExtensionPoints();
		LoaderFactory.setStackExpression(getStackExpression());
		started = true;
	}
	
	public static boolean isStarted() {
		return started;
	}
	
	private void registerExtensionPoints() {
		try {
		    final IConfigurationElement[] ele = Platform.getExtensionRegistry().getConfigurationElementsFor("uk.ac.diamond.scisoft.analysis.io.loader");
	        for (IConfigurationElement i : ele) {
	        	registerLoader(i);
	        }
		     
		} catch (Exception ne) {
			logger.error("Cannot notify model listeners");
		}

	}

	/**
	 * Called to register a loader loaded from an extension point
	 * @param i
	 */
	private final static void registerLoader(IConfigurationElement i) {
		try {
			final IFileLoader loader = (IFileLoader) i.createExecutableExtension("class");
			final String[] exts = i.getAttribute("file_extension").split(",");
        	final String high = i.getAttribute("high_priority");
            final boolean isHigh = "true".equals(high);
			Class<? extends IFileLoader> clazz = loader.getClass();
			for (String ext : exts) {
				String e = ext.trim();
				if (isHigh) {
					LoaderFactory.registerLoader(e, clazz, 0);
				} else {
					LoaderFactory.registerLoader(e, clazz);
				}
				String f = String.format("%s:%s:%d", e, clazz.getCanonicalName(), isHigh ? 0 : 1);
				extensions.add(f);
			}
			final String name = i.getContributor().getName();
			if (!plugins.contains(name))
				plugins.add(name);
		} catch (Throwable ne) {
			logger.error("Cannot add loader "+i.getAttribute("class"), ne);
		}
	}

	public static Set<String> getPlugins() {
		return plugins;
	}
	public static Set<String> getExtensions() {
		return extensions;
	}

	private String getStackExpression() {
		
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "uk.ac.diamond.scisoft.analysis.osgi");
		if (System.getProperty(PreferenceConstants.DATASET_REGEXP)!=null) {
			String regexp = System.getProperty(PreferenceConstants.DATASET_REGEXP);
			store.setValue(PreferenceConstants.DATASET_REGEXP, regexp);
		}

		String value = store.getString(PreferenceConstants.DATASET_REGEXP);
		if (value==null || "".equals(value)) value = "(.+)_(\\d+).";

		return value;
	}
}
