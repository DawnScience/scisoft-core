package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.slice.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.api.slice.SliceVisitor;
import org.eclipse.dawnsci.analysis.api.slice.Slicer;
import org.eclipse.dawnsci.analysis.api.slice.SourceInformation;

import uk.ac.diamond.scisoft.analysis.metadata.OriginMetadataImpl;

/**
 * Runs a pipeline by looping the services of operations.
 * 
 * This will not work unless the pipeline is a linear series of operations 
 * with one slug running the length.
 * 
 * If you have averaging or branching, you will need to consider using a 
 * graph to execute your operations.
 * 
 * @author fcp94556
 *
 */
class SeriesRunner implements IOperationRunner {


	private IOperationContext context;

	public void init(IOperationContext context) {
		this.context        = context;
	}

	@Override
	public void execute() throws Exception {
		final IExecutionVisitor visitor = context.getVisitor() ==null ? new IExecutionVisitor.Stub() : context.getVisitor();

		// determine data axes to populate origin metadata
		SourceInformation ssource = null; 
		
		try {
			 ssource = context.getData().getMetadata(SliceFromSeriesMetadata.class).get(0).getSourceInfo();
		} catch (Exception e) {
			logger.error("Source not obtainable. Hope this is just a unit test...");
		}
		
		final SourceInformation finalSource = ssource;
		
		// Create the slice visitor
		SliceVisitor sv = new SliceVisitor() {

			@Override
			public void visit(IDataset slice, Slice[] slices, int[] shape) throws Exception {

				SliceFromSeriesMetadata ssm = slice.getMetadata(SliceFromSeriesMetadata.class).get(0);
				SliceFromSeriesMetadata fullssm = new SliceFromSeriesMetadata(finalSource, ssm.getShapeInfo(), ssm.getSliceInfo());
				slice.setMetadata(fullssm);

				if (context.getMonitor() != null && context.getMonitor().isCancelled()) return;

				String path = fullssm.getSourceInfo().getFilePath();
				if (path == null) path = "";

				OperationData  data = new OperationData(slice, (Serializable[])null);
				long start = System.currentTimeMillis();
				for (IOperation<?,?> i : context.getSeries()) {

					if (context.getMonitor()!=null) {
						context.getMonitor().subTask(path +" : " + i.getName());
					}

					OperationData tmp = i.execute(data.getData(), context.getMonitor());
					tmp.getData().setMetadata(fullssm);
					
					visitor.notify(i, tmp); // Optionally send intermediate result
					data = i.isPassUnmodifiedData() ? data : tmp;
				}
				logger.debug("Slice ran in: " +(System.currentTimeMillis()-start)/1000. + " s : Thread" +Thread.currentThread().toString());

				visitor.executed(data, context.getMonitor()); // Send result.
				if (context.getMonitor() != null) context.getMonitor().worked(1);
			}

			@Override
			public boolean isCancelled() {
				return context.getMonitor()!=null ? context.getMonitor().isCancelled() : false;
			}
		};

		visitor.init(context.getSeries(), context.getData());
		long start = System.currentTimeMillis();
		// Jake's slicing from the conversion tool is now in Slicer.
		if (context.getExecutionType()==ExecutionType.SERIES) {
			Slicer.visitAll(context.getData(), context.getSlicing(), "Slice", sv);

		} else if (context.getExecutionType()==ExecutionType.PARALLEL) {
			Slicer.visitAllParallel(context.getData(), context.getSlicing(), "Slice", sv, context.getParallelTimeout());

		} else {
			throw new OperationException(context.getSeries()[0], "The edges are needed to execute a graph using ptolemy!");
		}
		logger.debug("Data ran in: " +(System.currentTimeMillis()-start)/1000. + " s");
		
	}

}
