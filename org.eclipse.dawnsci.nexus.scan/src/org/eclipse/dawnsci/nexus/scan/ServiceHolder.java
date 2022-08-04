package org.eclipse.dawnsci.nexus.scan;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.eclipse.dawnsci.nexus.validation.NexusValidationService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ServiceHolder {
	
	private static NexusBuilderFactory nexusBuilderFactory;
	
	private static NexusTemplateService templateService;

	private static INexusDeviceService nexusDeviceService;
	
	private static NexusValidationService nexusValidationService;
	
	private static IDefaultDataGroupCalculator defaultDataGroupCalculator;
	
	public static NexusBuilderFactory getNexusBuilderFactory() {
		return nexusBuilderFactory;
	}
	
	public void setNexusBuilderFactory(NexusBuilderFactory nexusBuilderFactory) {
		ServiceHolder.nexusBuilderFactory = nexusBuilderFactory;
	}
	
	public static INexusDeviceService getNexusDeviceService() {
		return nexusDeviceService;
	}
	
	public void setNexusDeviceService(INexusDeviceService nexusDeviceService) {
		ServiceHolder.nexusDeviceService = nexusDeviceService;
	}
	
	public static NexusTemplateService getTemplateService() {
		return templateService;
	}
	
	public void setTemplateService(NexusTemplateService templateService) {
		ServiceHolder.templateService = templateService;
	}
	
	public static NexusValidationService getNexusValidationService() {
		return nexusValidationService;
	}
	
	public void setNexusValidationService(NexusValidationService nexusValidationService) {
		ServiceHolder.nexusValidationService = nexusValidationService;
	}
	
	public void setDefaultDataGroupConfiguration(IDefaultDataGroupCalculator defaultDataGroupCalculator) {
		// note that this class is not typically set by OSGi, as this bean is declared in spring which is loaded after OSGi wiring
		// this method is intended to be called by unit tests where required
		ServiceHolder.defaultDataGroupCalculator = defaultDataGroupCalculator;
	}

	public static synchronized IDefaultDataGroupCalculator getDefaultDataGroupConfiguration() {
		if (defaultDataGroupCalculator == null) {
			defaultDataGroupCalculator = getService(IDefaultDataGroupCalculator.class);
			if (defaultDataGroupCalculator == null) {
				// default implementation, just return the first data group name
				defaultDataGroupCalculator = dataGroupNames -> dataGroupNames.get(0);
			}
		}

		return defaultDataGroupCalculator;
	}
	
	private static <T> T getService(Class<T> serviceClass) {
		if (!Platform.isRunning()) return null;
		final Bundle bundle = FrameworkUtil.getBundle(ServiceHolder.class);
		if (bundle == null) return null; // OSGi framework may not be running, this is the case for JUnit tests
		final BundleContext context = bundle.getBundleContext();
		final ServiceReference<T> serviceRef = context.getServiceReference(serviceClass);
		if (serviceRef == null) return null;
		return context.getService(serviceRef);
	}

}
