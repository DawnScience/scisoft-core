package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.util.HashMap;

import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;

import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import uk.ac.diamond.scisoft.analysis.processing.actor.OperationSource;

import com.isencia.passerelle.domain.et.ETDirector;
import com.isencia.passerelle.model.Flow;
import com.isencia.passerelle.model.FlowManager;

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
		
		Flow flow = new Flow("Operations Graph", null);
		
		ETDirector director = new ETDirector(flow, "Director");
		flow.setDirector(director);
		
		buildGraph(flow);
		
		FlowManager flowMgr = new FlowManager();
		flowMgr.executeBlockingErrorLocally(flow, new HashMap<String, String>());		
	}

	private void buildGraph(Flow flow) throws Exception {
		
        final OperationSource source = new OperationSource(flow, "Operation Pipeline");
        source.setContext(context);
        source.setOriginMetadata(originMetadata);
        
        // TODO 
	}
}
