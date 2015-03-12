/*-
 * Copyright 2015 Diamond Light Source Ltd.
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
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.ui.IStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;
import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionUseCaseService;

public class FunctionFactoryStartup implements IStartup {
	
	private static final Logger logger = LoggerFactory.getLogger(FunctionFactoryStartup.class);
	
	//This is the number of possible use cases provided by the extension point.
	private static final int nUseCases = 5;
	private static Set<String> plugins;
	private static boolean     started=false;

	@Override
	public void earlyStartup() {
		plugins = new HashSet<String>();
		registerFunctionExtensionPoints();
		started = true;
	}
	
	public static boolean isStarted() {
		return started;
	}

	public static Set<String> getPlugins() {
		return plugins;
	}

	/**
	 * Get all extension points of type u.a.d.s.a.fitting.function
	 * and register them sequentially.
	 */
	private void registerFunctionExtensionPoints() {
		final IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("uk.ac.diamond.scisoft.analysis.fitting.function");		
		/* Loop through this element array twice since we want to record the 
		 * use cases to sanity check the ones provided in the function extension
		 * points.
		 */
		for (IConfigurationElement ele : elements) {
			if (!ele.getName().equals("usecase")) continue;
			registerUseCase(ele);
		}
		for (IConfigurationElement ele : elements) {
			if (!ele.getName().equals("function")) continue;
			registerFunction(ele);
		}
	}

	
	/**
	 * Take a function extension point, determine it's class and  register it 
	 * with the function factory. Also record the plugin contributing the 
	 * extension point.
	 * @param extPt
	 */
	private final static void registerFunction(IConfigurationElement extPt) {
		try {
			//Get the name and class of the function from the extension point.
			final IFunction function = (IFunction) extPt.createExecutableExtension("class");
			final String fnName = extPt.getAttribute("name");
			Class<? extends IFunction> clazz = function.getClass();
			//Get the use cases
			Set<String> ucidList = new HashSet<String>();
			for (int i = 0; i < nUseCases; i++) { 
				String attrLabel = "usecase"+(i+1);
				String ucid = extPt.getAttribute(attrLabel);
				if (ucid == null) continue;
				ucidList.add(ucid);
			}
			FunctionFactory.registerFunction(clazz, fnName, ucidList);
			
			//Record the contributing plugin
			final String name = extPt.getContributor().getName();
			if (!plugins.contains(name))
				plugins.add(name);
		} catch (Exception e) {
			logger.error("Cannot import function "+extPt.getAttribute("class"), e);
		}
	}
	
	/**
	 * Take a use case extension point and register it with the 
	 * FunctionUseCaseService.
	 * @param extPt
	 */
	private final static void registerUseCase(IConfigurationElement extPt) {
		final String name = extPt.getAttribute("name");
		final String ucid = extPt.getAttribute("id");
		
		FunctionUseCaseService.registerUseCase(ucid, name);
	}


}
