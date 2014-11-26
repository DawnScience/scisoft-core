package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.util.HashMap;

import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;

import uk.ac.diamond.scisoft.analysis.processing.actor.OperationSource;
import uk.ac.diamond.scisoft.analysis.processing.actor.OperationTransformer;

import com.isencia.passerelle.core.Port;
import com.isencia.passerelle.domain.et.ETDirector;
import com.isencia.passerelle.model.Flow;
import com.isencia.passerelle.model.FlowManager;

/**
 * Builds a graph for executing with Passerelle
 * 
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

	/**
	 * Currently we simply build an in memory graph which is a linear
	 * list of operations. Later we might execute a full graph.
	 * 
	 * @param flow
	 * @throws Exception
	 */
	private void buildGraph(Flow flow) throws Exception {
		
        final OperationSource source = new OperationSource(flow, "Operation Pipeline");
        source.setContext(context);
        source.setOriginMetadata(originMetadata);
        
        Port from = source.output;
        for (IOperation<? extends IOperationModel, ? extends OperationData> op : context.getSeries()) {
        	
        	final OperationTransformer opTrans = new OperationTransformer(flow, op.getName());
        	opTrans.setContext(context);
        	opTrans.setOperation((IOperation<IOperationModel, OperationData>)op);
        	
        	flow.connect(from, opTrans.input);
        	from = opTrans.input;
        }
	}
}
