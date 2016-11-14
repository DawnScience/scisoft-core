/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.dawnsci.analysis.api.persistence.IClassRegistry;
import org.eclipse.scanning.api.event.status.Status;

import uk.ac.diamond.scisoft.analysis.processing.bean.OperationBean;

public class ProcessingClassRegistry implements IClassRegistry {

	private Map<String, Class<?>> idMap;
	public ProcessingClassRegistry() {
		idMap = new HashMap<String, Class<?>>();
		idMap.put(OperationBean.class.getSimpleName(), OperationBean.class);
//		idMap.put(Status.class.getSimpleName(), Status.class);
	}

	@Override
	public Map<String, Class<?>> getIdToClassMap() {
		return idMap;
	}

}
