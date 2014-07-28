/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	@SuppressWarnings("unused")
	private static Activator     activator;
	private static BundleContext context;
	
	@Override
	public void start(BundleContext c) throws Exception {
		activator = this;
		context = c;
	}

	@Override
	public void stop(BundleContext c) throws Exception {
		activator = null;
		context = c;
	}
	
	public static Object getService(final Class<?> serviceClass) {
		// Important that NPE is thrown if context is null. We must have a context to get other services.
		ServiceReference<?> ref = context.getServiceReference(serviceClass);
		if (ref==null) return null;
		return context.getService(ref);
	}
}
