package org.eclipse.dawnsci.nexus.template;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusTemplateApplication implements IApplication {

	private static final Logger logger = LoggerFactory.getLogger(NexusTemplateApplication.class);
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		logger.info("Starting Nexus Template Application");
		final Map<?, ?> args = context.getArguments();
		final String[] configuration = (String[]) args.get("application.args");
		if (configuration.length != 2 || configuration[0].equals("-h")) {
			printUsage();
			return null;
		}

		final String templateFileName = configuration[0];
		final String nexusFileName = configuration[1];
		final String nexusFileFullPath = new File(nexusFileName).getAbsolutePath();
		
		final NexusTemplateService nexusTemplateService = getNexusTemplateService();
		logger.info("Applying template, template file = {}, nexus file = {}", templateFileName, nexusFileName);
		try {
			final NexusTemplate template = nexusTemplateService.loadTemplate(templateFileName);
			template.apply(nexusFileFullPath);
			logger.info("Template applied successfully");
			System.out.println("Template applied successfully");
		} catch (Exception e) {
			logger.error("An error occurred applying the template", e);
			System.err.println("An error occured applying the template");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void printUsage() {
		System.err.println("Usage: run-template <templateName> <nexusFileName>");
		System.err.println("    For guidance on how to write a nexus template file, see: https://confluence.diamond.ac.uk/display/CT/Nexus+Template+Engine");
	}
	
	private NexusTemplateService getNexusTemplateService() {
		final Bundle bundle = Platform.getBundle("org.eclipse.dawnsci.nexus.template");
		final BundleContext context = bundle.getBundleContext();
		final ServiceReference<NexusTemplateService> serviceRef = context.getServiceReference(NexusTemplateService.class);
		Objects.requireNonNull(serviceRef, "Could not get nexus template service");
		NexusTemplateService service = context.getService(serviceRef);
		Objects.requireNonNull(service, "Could not get nexus template service");
		return service;
	}

	@Override
	public void stop() {
		// nothing to do
	}

}
