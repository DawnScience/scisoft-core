/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.osgi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunctionFactoryExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;

public class FunctionFactoryExtensionService implements IFunctionFactoryExtensionService {
	
	private static final Logger logger = LoggerFactory.getLogger(FunctionFactoryExtensionService.class);
	
	//This is the number of possible use cases provided by the extension point.
	private static final int nUseCases = 5;
	
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
	 * Take an extension point, determine it's class and then register it with
	 * the function factory.
	 * @param extPt
	 */
	private final static void registerFunction(IConfigurationElement extPt) {
		try {
			//Get the name and class of the function from the extension point.
			final IFunction function = (IFunction) extPt.createExecutableExtension("class");
			final String fnName = extPt.getAttribute("name");
			Class<? extends IFunction> clazz = function.getClass();
			//Get the use cases
			List<String> ucidList = new ArrayList<String>();
			for (int i = 0; i < nUseCases; i++) { 
				String attrLabel = "usecase"+(i+1);
				String ucid = extPt.getAttribute(attrLabel);
				if (ucid == null) continue;
				ucidList.add(ucid);
			}
			FunctionFactory.registerFunction(clazz, fnName, ucidList);
		} catch (Exception e) {
			logger.error("Cannot import function "+extPt.getAttribute("class"), e);
		}
	}
	
	private final static void registerUseCase(IConfigurationElement extPt) {
		final String name = extPt.getAttribute("name");
		final String ucid = extPt.getAttribute("id");
		
		FunctionFactory.registerUseCase(ucid, name);
	}

}
