/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.osgi;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunctionFactoryExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;

public class FunctionFactoryExtensionService implements IFunctionFactoryExtensionService {
	
	private static final Logger logger = LoggerFactory.getLogger(FunctionFactoryExtensionService.class);
	
	//Added the following two members following LoaderFactoryExt.Serv.
	static {
		System.out.println("Starting function factory extension service...");
	}
	public FunctionFactoryExtensionService() {
		// Important do nothing here, OSGI may start the service more than once.
	}

	/**
	 * Get all extension points of type u.a.d.s.a.fitting.function
	 * and register them sequentially.
	 */
	@Override
	public void registerExtensionPoints() {
		final IConfigurationElement[] extPts = Platform.getExtensionRegistry().getConfigurationElementsFor("uk.ac.diamond.scisoft.analysis.fitting.function");
		for (IConfigurationElement eP : extPts) {
			registerFunction(eP);
		}
	}
	
	/**
	 * Take an extension point, determine it's class and then register it with
	 * the function factory.
	 * @param extPt
	 */
	private final static void registerFunction(IConfigurationElement extPt) {
		try {
			//Get the name and class of the function in the extension point.
			final IFunction function = (IFunction) extPt.createExecutableExtension("class");
			final String fnName = extPt.getAttribute("name");
			Class<? extends IFunction> clazz = function.getClass();
			FunctionFactory.registerFunction(clazz, fnName);
		} catch (Exception e) {
			logger.error("Cannot import function "+extPt.getAttribute("class"), e);
		}
	}

}
