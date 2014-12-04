package uk.ac.diamond.scisoft.analysis.processing.actor.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dawb.passerelle.common.actors.ActorUtils;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;

import ptolemy.moml.MoMLParser;

import com.isencia.passerelle.model.Flow;
import com.isencia.passerelle.model.FlowManager;

/**
 * Builds a graph for executing with Passerelle
 * 
 * @author fcp94556
 *
 */
public class GraphRunner  implements IOperationRunner {

	private IOperationContext context;

	public void init(IOperationContext context) throws Exception {
		this.context        = context;
		if (context.getVisitor()!=null) {
		    context.getVisitor().init(context.getSeries(), context.getData());
		}
	}

	public void execute() throws Exception {
		
        MoMLParser.purgeAllModelRecords();

		GraphBuilder builder = new GraphBuilder();
		builder.init(context);
		Flow flow = builder.createEventDirectorFlow();
		
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
	
	@Override
	public ExecutionType[] getExecutionTypes() {
		return new ExecutionType[]{ExecutionType.GRAPH};
	}

}
