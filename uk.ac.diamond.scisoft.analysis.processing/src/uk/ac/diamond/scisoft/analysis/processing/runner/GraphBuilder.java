package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.io.FileWriter;

import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;

import ptolemy.data.IntToken;
import ptolemy.kernel.util.Location;
import uk.ac.diamond.scisoft.analysis.processing.actor.OperationSource;
import uk.ac.diamond.scisoft.analysis.processing.actor.OperationTransformer;

import com.isencia.passerelle.core.Port;
import com.isencia.passerelle.domain.et.ETDirector;
import com.isencia.passerelle.model.Flow;

public class GraphBuilder {

	private IOperationContext context;

	public GraphBuilder(IOperationContext context) {
		this.context = context;
	}

	/**
	 * Currently we simply build an in memory graph which is a linear
	 * list of operations. Later we might execute a full graph.
	 * 
	 * @throws Exception
	 */
	public Flow createEventDirectorFlow() throws Exception {
		
		Flow flow = new Flow("Operations Graph", null);
		
		ETDirector director = new ETDirector(flow, "Director");
		director.dispatchThreadsParameter.setToken(new IntToken(context.getPoolSize()));
		
		flow.setDirector(director);

        final OperationSource source = new OperationSource(flow, "Data Source");
        source.setContext(context);
        new Location(source, "_location").setLocation(new double[]{25, 100});
        
        Port from = source.output;
        int count = 1;
        for (IOperation<? extends IOperationModel, ? extends OperationData> op : context.getSeries()) {
        	
        	final OperationTransformer opTrans = new OperationTransformer(flow, op.getName());
        	opTrans.setContext(context);
        	opTrans.setOperation((IOperation<IOperationModel, OperationData>)op);
            new Location(opTrans, "_location").setLocation(new double[]{count*200, 100});
            count++;
       	
        	flow.connect(from, opTrans.input);
        	from = opTrans.output;
        }
        
        return flow;
	}

	public void export(String location) throws Exception {
		
		
		final Flow         flo = createEventDirectorFlow();
		final FileWriter   fw = new FileWriter(location);
		try {
		    flo.exportMoML(fw);
		} finally {
			fw.close();
		}
	}

}
