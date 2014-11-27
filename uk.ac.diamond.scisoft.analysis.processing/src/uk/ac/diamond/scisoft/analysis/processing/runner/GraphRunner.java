package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dawb.passerelle.common.actors.ActorUtils;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;

import ptolemy.data.IntToken;
import uk.ac.diamond.scisoft.analysis.processing.actor.OperationSource;
import uk.ac.diamond.scisoft.analysis.processing.actor.OperationTransformer;

import com.isencia.passerelle.core.Port;
import com.isencia.passerelle.domain.cap.Director;
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
		if (context.getVisitor()!=null) {
			try {
				context.getVisitor().init(context.getSeries(), context.getData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void execute() throws Exception {
		
		Flow flow = new Flow("Operations Graph", null);
		
		Director director = new Director(flow, "Director");
		flow.setDirector(director);
		
		buildGraph(flow);
		
		try {
		    // We switch off being able to remotely debug the workflow
		    ActorUtils.setCanNotify(false);
		    
		    // We run the workflow
			FlowManager flowMgr = new FlowManager();
			
			if (context.getParallelTimeout()<1) { // No timeout
			    flowMgr.executeBlockingErrorLocally(flow, new HashMap<String, String>());	
			} else {
				executeGraphWithTimeout(flowMgr, flow);
			}
			
		} finally {
			// We put it back 
		    ActorUtils.setCanNotify(true);
		}
	}

	/**
	 * Executes the workflow in another thread but throws an exception if 
	 * the timeout is reached.
	 * 
	 * @param flowMgr
	 * @param flow
	 * @throws Exception
	 */
	private void executeGraphWithTimeout(final FlowManager flowMgr, final Flow flow) throws Exception {
		
		final Thread mainThread   = Thread.currentThread();
		final List<Exception> except = new ArrayList<Exception>(1); 
		
		final Thread workerThread  = new Thread(new Runnable() {
			public void run() {
				try {
				    flowMgr.executeBlockingErrorLocally(flow, new HashMap<String, String>());	
				} catch (Exception ne) {
					except.add(ne);
				} finally {
					mainThread.interrupt();
				}
			}
		});
		
		workerThread.start();
		
		try {
			Thread.sleep(context.getParallelTimeout());
			
			throw new Exception("The timeout of "+context.getParallelTimeout()+" ms has been exceeded!");
			
		} catch (InterruptedException expected) {
			
			if (!except.isEmpty()) throw except.get(0);
			
			// Otherwise it worked ok
		}
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
        	opTrans.receiverQueueCapacityParam.setToken(new IntToken(context.getQueueSize()));
        	
        	flow.connect(from, opTrans.input);
        	from = opTrans.output;
        }
	}
}
