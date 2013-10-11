/*
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		return new StackTraceElement(declaringClass, methodName, fileName,
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
		return outMap;
	}

}
