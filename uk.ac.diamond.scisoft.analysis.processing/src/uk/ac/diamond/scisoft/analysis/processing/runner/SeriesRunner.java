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
import org.eclipse.dawnsci.analysis.api.slice.SliceVisitor;
import org.eclipse.dawnsci.analysis.api.slice.Slicer;

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
	private OriginMetadata    originMetadata;

	public void init(IOperationContext context, OriginMetadata originMetadata) {
		this.context        = context;
		this.originMetadata = originMetadata;
	}

	@Override
	public void execute() throws Exception {
		final IExecutionVisitor visitor = context.getVisitor() ==null ? new IExecutionVisitor.Stub() : context.getVisitor();

		// determine data axes to populate origin metadata
		final int[] dataDims = Slicer.getDataDimensions(context.getData().getShape(), context.getSlicing());

		// Create the slice visitor
		SliceVisitor sv = new SliceVisitor() {

			@Override
			public void visit(IDataset slice, Slice[] slices, int[] shape) throws Exception {

				OriginMetadata innerOm = originMetadata;

				if (context.getMonitor() != null && context.getMonitor().isCancelled()) return;
				if (innerOm == null){ 
					innerOm = new OriginMetadataImpl(context.getData(), slices, dataDims,"",context.getData().getName());
				} else {
					((OriginMetadataImpl)innerOm).setCurrentSlice(slices);
				}
				slice.setMetadata(innerOm);

				String path = innerOm.getFilePath();
				if (path == null) path = "";

				OperationData  data = new OperationData(slice, (Serializable[])null);
				long start = System.currentTimeMillis();
				for (IOperation<?,?> i : context.getSeries()) {

					if (context.getMonitor()!=null) {
						context.getMonitor().subTask(path +" : " + i.getName());
					}

					OperationData tmp = i.execute(data.getData(), context.getMonitor());

					visitor.notify(i, tmp, slices, shape, dataDims); // Optionally send intermediate result
					data = i.isPassUnmodifiedData() ? data : tmp;
				}
				logger.debug("Slice ran in: " +(System.currentTimeMillis()-start)/1000. + " s : Thread" +Thread.currentThread().toString());

				visitor.executed(data, context.getMonitor(), slices, shape, dataDims); // Send result.
				if (context.getMonitor() != null) context.getMonitor().worked(1);
			}

			@Override
			public boolean isCancelled() {
				return context.getMonitor()!=null ? context.getMonitor().isCancelled() : false;
			}
		};

		visitor.init(context.getSeries(), originMetadata);
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
