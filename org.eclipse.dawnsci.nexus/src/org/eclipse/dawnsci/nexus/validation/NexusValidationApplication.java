package org.eclipse.dawnsci.nexus.validation;

import java.util.Map;

import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusValidationApplication implements IApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(NexusValidationApplication.class);

	@Override
	public Object start(IApplicationContext context) throws Exception {
		logger.info("Starting Nexus Valdation Application");
		final Map<?, ?> args = context.getArguments();
		final String[] configuration = (String[]) args.get("application.args");
		if (configuration.length != 1) {
			System.err.println("Usage: validate-nexus <filename>");
			return null;
		}
		
		try {
			final String filePath = configuration[0];
			final ValidationReport validationReport = ServiceHolder.getNexusValidationService().validateNexusFile(filePath);
			printValidationReport(validationReport);
		} catch (Exception e) {
			System.out.println("An exception occurred " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void printValidationReport(ValidationReport validationReport) {
		if (validationReport.isOk()) {
			System.out.println("Nexus validation successful!");
		} else {
			System.out.println("Nexus validation failed!");
			validationReport.getValidationEntries().forEach(System.out::println);
		}
	}
	
	@Override
	public void stop() {
		// nothing to do
	}

}
