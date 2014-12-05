package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationExporter;
import org.eclipse.dawnsci.analysis.api.processing.IOperationExporterService;

/**
 * for now hard codes runners because there are not many
 * @author fcp94556
 *
 */
public class OperationExporterImpl implements IOperationExporterService {

	private static Map<ExecutionType, IOperationExporter> exporters;
	
	/**
	 * Gets runner by reading the extension points.
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IOperationExporter getExporter(ExecutionType type) throws Exception {
		if (exporters==null) createExportersByExtensionPoint();
		return exporters.get(type).getClass().newInstance();
	}

	/**
	 * Generally used by testing to setup the various runners.
	 * @param type
	 * @param runner
	 */
	public static void setRunner(ExecutionType type, IOperationExporter exporter) {
		if (exporters==null) exporters = new HashMap<ExecutionType, IOperationExporter>(7);
		exporters.put(type, exporter);
	}

	private static void createExportersByExtensionPoint() throws Exception {
		
		exporters = new HashMap<ExecutionType, IOperationExporter>(7);
		
		IConfigurationElement[] eles = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.operation");
		for (IConfigurationElement e : eles) {
	    	if (!e.getName().equals("exporter")) continue;
			
	    	IOperationExporter exporter = (IOperationExporter)e.createExecutableExtension("class");
	    	for (ExecutionType type : exporter.getExecutionTypes()) {
	    		exporters.put(type, exporter);
	    	}
		}

	}
	
}
