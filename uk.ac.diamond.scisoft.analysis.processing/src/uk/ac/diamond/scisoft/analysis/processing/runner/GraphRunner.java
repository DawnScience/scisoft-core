package uk.ac.diamond.scisoft.analysis.processing.runner;

import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;

/**
 * Builds a graph for executing with Passerelle
 * @author fcp94556
 *
 */
class GraphRunner  implements IOperationRunner {

	private IOperationContext context;
	private OriginMetadata    originMetadata;

	public void init(IOperationContext context, OriginMetadata originMetadata) {
		this.context        = context;
		this.originMetadata = originMetadata;
	}

	public void execute() throws Exception {
		throw new Exception("Graph runner not implemented as yet!");
	}
}
