/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing;

import org.eclipse.dawnsci.analysis.api.io.ILoaderService;
import org.eclipse.dawnsci.analysis.api.persistence.IMarshallerService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;

public class LocalServiceManager {
	
	private static ILoaderService lservice;
	private static IOperationService oservice;
	private static IPersistenceService pservice;
	private static IMarshallerService mservice;
	private static NexusTemplateService tservice;

	public void setLoaderService(ILoaderService s) {
		lservice = s;
	}

	public static ILoaderService getLoaderService() {
		return lservice;
	}
	
	public static IOperationService getOperationService() {
		return oservice;
	}

	public void setOperationService(IOperationService s) {
		oservice = s;
	}

	public static IPersistenceService getPersistenceService() {
		return pservice;
	}

	public void setPersistenceService(IPersistenceService p) {
		pservice = p;
	}

	public static IMarshallerService getMarshallerService() {
		return mservice;
	}

	public void setMarshallerService(IMarshallerService m) {
		mservice = m;
	}

	public static NexusTemplateService getNexusTemplateService() {
		return tservice;
	}

	public void setNexusTemplateService(NexusTemplateService t) {
		tservice = t;
	}
}
