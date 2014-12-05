package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunnerService;

/**
 * for now hard codes runners because there are not many
 * @author fcp94556
 *
 */
public class OperationRunnerImpl implements IOperationRunnerService {

	private static Map<ExecutionType, IOperationRunner> runners;
	
	/**
	 * Gets runner by reading the extension points.
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IOperationRunner getRunner(ExecutionType type) throws Exception {
		if (runners==null) createRunnersByExtensionPoint();
		return runners.get(type).getClass().newInstance();
	}

	/**
	 * Generally used by testing to setup the various runners.
	 * @param type
	 * @param runner
	 */
	public static void setRunner(ExecutionType type, IOperationRunner runner) {
		if (runners==null) runners = new HashMap<ExecutionType, IOperationRunner>(7);
		runners.put(type, runner);
	}

	private static void createRunnersByExtensionPoint() throws Exception {
		
		runners = new HashMap<ExecutionType, IOperationRunner>(7);
		
		IConfigurationElement[] eles = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.operation");
		for (IConfigurationElement e : eles) {
	    	if (!e.getName().equals("runner")) continue;
			
	    	IOperationRunner runner = (IOperationRunner)e.createExecutableExtension("class");
	    	for (ExecutionType type : runner.getExecutionTypes()) {
	    		runners.put(type, runner);
	    	}
		}

	}
	
}
