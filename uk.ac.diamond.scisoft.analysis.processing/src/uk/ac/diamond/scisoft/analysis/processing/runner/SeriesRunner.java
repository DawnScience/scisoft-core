package uk.ac.diamond.scisoft.analysis.processing.runner;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationRunner;
import org.eclipse.dawnsci.analysis.api.processing.ISavesToFile;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.DynamicSliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.ISliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceVisitor;
import org.eclipse.dawnsci.analysis.dataset.slicer.Slicer;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.Slice;

import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadataImpl;

/**
 * Runs a pipeline by looping the services of operations.
 * 
 * This will not work unless the pipeline is a linear series of operations 
 * with one slug running the length.
 * 
 * If you have averaging or branching, you will need to consider using a 
 * graph to execute your operations.
 * 
 * @author Matthew Gerring
 *
 */
public class SeriesRunner implements IOperationRunner {


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
			public void visit(IDataset slice) throws Exception {

				List<SliceFromSeriesMetadata> meta = slice.getMetadata(SliceFromSeriesMetadata.class);
				SliceFromSeriesMetadata ssm = meta!=null && !meta.isEmpty() ? meta.get(0) : null;
				SliceFromSeriesMetadata fullssm = null;
				if (ssm!=null) {
					fullssm = new SliceFromSeriesMetadata(finalSource, ssm.getSliceInfo());
					slice.setMetadata(fullssm);
				}

				if (context.getMonitor() != null && context.getMonitor().isCancelled()) return;

				String current = "";
				if (fullssm != null) {
					try {
						current = Slice.createString(ssm.getSliceFromInput());
					} catch (Exception e) {
						//ignore
					}
				}
				
				String outputFile = null;
				
				if (visitor instanceof ISavesToFile) outputFile = ((ISavesToFile)visitor).getFileName();
				
				OperationData  data = new OperationData(slice, (Serializable[]) null);
				long start = System.currentTimeMillis();
				for (IOperation<?,?> i : context.getSeries()) {

					OperationMetadataImpl operationMeta = new OperationMetadataImpl(outputFile, context.getSeries(), i);
					data.getData().setMetadata(operationMeta);
					if (context.getMonitor() != null) {
						String update = "";
						if (fullssm != null) {
							try {
								String filePath = fullssm.getFilePath();
								File f = new File(filePath);
								String name = f.getName();
								String s = Slice.createString(fullssm.getSliceFromInput());
								update = name+ " ["+ s + "] " + i.getName();
							} catch (Exception e) {
								logger.error("Could not update progress: " + e.getMessage());
							}
						}
						context.getMonitor().subTask(update);
						
					}

					OperationData tmp = i.execute(data.getData(), context.getMonitor());
					//TODO only set metadata if doesnt already contain it!
					//TODO continue if null;
					if (tmp == null || tmp.getData() == null) {
						data = null;
					} else {
						List<SliceFromSeriesMetadata> md = tmp.getData().getMetadata(SliceFromSeriesMetadata.class);
						
						if (md == null || md.isEmpty())  {
							tmp.getData().setMetadata(fullssm);
						} else {
							fullssm = md.get(0);
						}
					}

					if (tmp != null) {
						visitor.notify(i, tmp); // Optionally send intermediate result
					}

					data = i.isPassUnmodifiedData() ? data : tmp;
					if (data == null || data.getData() == null) {
						break;
					}
				}
				logger.debug("Slice {} ran in: {} s : Thread {}", current, (System.currentTimeMillis()-start)/1000., Thread.currentThread());
				if (context.getMonitor() != null) context.getMonitor().worked(1);
				visitor.executed(data, context.getMonitor()); // Send result.
			}

			@Override
			public boolean isCancelled() {
				return context.getMonitor()!=null ? context.getMonitor().isCancelled() : false;
			}
		};

		visitor.init(context.getSeries(), context.getData());
		long start = System.currentTimeMillis();
		
		
		ISliceViewIterator iterator = null;
		
		if (context.getLiveInfo() != null) {
			iterator = new DynamicSliceViewIterator((IDynamicDataset)context.getData(), context.getLiveInfo().getKeys(), context.getLiveInfo().getComplete(), context.getDataDimensions().length, context.getLiveInfo().isMonitorForOverwrite());
			((DynamicSliceViewIterator)iterator).setMaxTimeout((int)context.getParallelTimeout());
		} else {
			iterator = new SliceViewIterator(context.getData(), context.getSlicing(), context.getDataDimensions());
		}
		
		if (context.getExecutionType()==ExecutionType.SERIES) {
			Slicer.visit(iterator,sv);
		} else if (context.getExecutionType()==ExecutionType.PARALLEL) {
			Slicer.visitParallel(iterator,sv,context.getNumberOfCores());
		} else {
			throw new OperationException(context.getSeries()[0], "The edges are needed to execute a graph using ptolemy!");
		}

		logger.debug("Data ran in: {} s", (System.currentTimeMillis()-start)/1000.);
	}

	@Override
	public ExecutionType[] getExecutionTypes() {
		return new ExecutionType[]{ExecutionType.SERIES, ExecutionType.PARALLEL};
	}

}
