/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class StackTraceElementHelper extends
		MapFlatteningHelper<StackTraceElement> {
	public static final String DECLARINGCLASS = "declaringClass";
	public static final String METHODNAME = "methodName";
	public static final String FILENAME = "fileName";
	public static final String LINENUMBER = "lineNumber";

	// Java 9+ fields
	public static final String CLASSLOADERNAME = "classLoaderName";
	public static final String MODULENAME = "moduleName";
	public static final String MODULEVERSION = "moduleVersion";

	public StackTraceElementHelper() {
		super(StackTraceElement.class);
	}

	@Override
	public StackTraceElement unflatten(Map<?, ?> thisMap,
			IRootFlattener rootFlattener) {
		String declaringClass = (String) rootFlattener.unflatten(thisMap.get(DECLARINGCLASS));
		String methodName = (String) rootFlattener.unflatten(thisMap.get(METHODNAME));
		String fileName = (String) rootFlattener.unflatten(thisMap.get(FILENAME));
		int lineNumber = (Integer) rootFlattener.unflatten(thisMap.get(LINENUMBER));

		String classLoaderName = (String) rootFlattener.unflatten(thisMap.get(CLASSLOADERNAME));
		String moduleName = (String) rootFlattener.unflatten(thisMap.get(MODULENAME));
		String moduleVersion = (String) rootFlattener.unflatten(thisMap.get(MODULEVERSION));
		return new StackTraceElement(classLoaderName, moduleName, moduleVersion, declaringClass, methodName, fileName,
				lineNumber);
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		StackTraceElement ste = (StackTraceElement) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(DECLARINGCLASS, rootFlattener.flatten(ste.getClassName()));
		outMap.put(METHODNAME, rootFlattener.flatten(ste.getMethodName()));
		outMap.put(FILENAME, rootFlattener.flatten(ste.getFileName()));
		outMap.put(LINENUMBER, rootFlattener.flatten(ste.getLineNumber()));
		outMap.put(CLASSLOADERNAME, rootFlattener.flatten(ste.getClassLoaderName()));
		outMap.put(MODULENAME, rootFlattener.flatten(ste.getModuleName()));
		outMap.put(MODULEVERSION, rootFlattener.flatten(ste.getModuleVersion()));
		return outMap;
	}
}
