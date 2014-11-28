package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;

/**
 * for now hard codes runners because there are not many
 * @author fcp94556
 *
 */
public class OperationRunnerFactory {

	private static final Map<ExecutionType, Class<? extends IOperationRunner>> runners;
	static {
		runners = new HashMap<ExecutionType, Class<? extends IOperationRunner>>(3);
		runners.put(ExecutionType.GRAPH,    GraphRunner.class);
		runners.put(ExecutionType.SERIES,   SeriesRunner.class);
		runners.put(ExecutionType.PARALLEL, SeriesRunner.class);
	}
	
	public static IOperationRunner getRunner(ExecutionType type) throws Exception {
		return runners.get(type).newInstance();
	}
}
