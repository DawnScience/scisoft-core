package uk.ac.diamond.scisoft.analysis.processing;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.ILiveOperationInfo;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class OperationContextImpl implements IOperationContext {

	// What we are running, required
	private IOperation<? extends IOperationModel, ? extends OperationData>[] series;
	
	// Required Either
	private ILazyDataset         data;
	// or
	private String  filePath;
	private String  datasetPath;
	private ILiveOperationInfo  liveInfo;
	// And
	private SliceND slicing;
	private int[] dataDimensions;
	
	// May be null
	private IMonitor             monitor;
	private IExecutionVisitor    visitor;
	
	/** 
	 *  The default timeout is 5000 ms for PARALLEL and 10 minutes for GRAPH.
	 *  This default is applied when setExecutionType(..) is called if the parallel timeout is -1
	 *  
	 * 1. ExecutionType.SERIES has NO timeout
	 * 2. ExecutionType.PARALLEL has 5000 ms applied when setExecutionType(...) is called, if parallelTimeout=-1
	 *    You can use setParallelTimeout(...) to change this.
	 * 3. ExecutionType.GRAPH has 10 minutes applied when setExecutionType(...) is called, if parallelTimeout=-1
	 *    You can use setParallelTimeout(...) to change this.
	 *
	 */
	private long                 parallelTimeout=-1;
	
	/**
	 * The size of the thread pool used in GRAPH mode. By default the value is 1 meaning that 1 slice is ]
	 * in the pipeline at a time.
	 */
	private int                  poolSize=1;
	
	/**
	 * Defaults to ExecutionType.SERIES
	 */
	private ExecutionType executionType = ExecutionType.SERIES;
	private int numberOfCores = 1;
	
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#getSeries()
	 */
	@Override
	public IOperation<? extends IOperationModel, ? extends OperationData>[] getSeries() {
		return series;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#setSeries(org.eclipse.dawnsci.analysis.api.processing.IOperation)
	 */
	@Override
	public void setSeries(IOperation<? extends IOperationModel, ? extends OperationData>... series) {
		this.series = series;
	}
	@Override
	public void setSeries(List<IOperation<? extends IOperationModel, ? extends OperationData>> series) {
		this.series = series.toArray(new IOperation[series.size()]);
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#getData()
	 */
	@Override
	public ILazyDataset getData() throws Exception {
		if (data!=null) return data;
		if (filePath!=null && datasetPath!=null) {
			final IDataHolder holder = LoaderFactory.getData(filePath);
			data = holder.getLazyDataset(datasetPath);
		}
		return data;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#setData(org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset)
	 */
	@Override
	public void setData(ILazyDataset data) {
		this.data = data;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#getSlicing()
	 */
	@Override
	public SliceND getSlicing() {
		return slicing;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#setSlicing(java.util.Map)
	 */
	@Override
	public void setSlicing(SliceND slicing) {
		this.slicing = slicing;
	}
	

	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#getMonitor()
	 */
	@Override
	public IMonitor getMonitor() {
		return monitor;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#setMonitor(org.eclipse.dawnsci.analysis.api.monitor.IMonitor)
	 */
	@Override
	public void setMonitor(IMonitor monitor) {
		this.monitor = monitor;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#getVisitor()
	 */
	@Override
	public IExecutionVisitor getVisitor() {
		return visitor;
	}
	/* (non-Javadoc)
	 * @see uk.ac.diamond.scisoft.analysis.processing.IOperationContext#setVisitor(org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor)
	 */
	@Override
	public void setVisitor(IExecutionVisitor visitor) {
		this.visitor = visitor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((datasetPath == null) ? 0 : datasetPath.hashCode());
		result = prime * result
				+ ((executionType == null) ? 0 : executionType.hashCode());
		result = prime * result
				+ (int) (numberOfCores ^ (numberOfCores >>> 32));
		result = prime * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((monitor == null) ? 0 : monitor.hashCode());
		result = prime * result
				+ (int) (parallelTimeout ^ (parallelTimeout >>> 32));
		result = prime * result + Arrays.hashCode(series);
		result = prime * result + ((slicing == null) ? 0 : slicing.hashCode());
		result = prime * result + ((visitor == null) ? 0 : visitor.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationContextImpl other = (OperationContextImpl) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (datasetPath == null) {
			if (other.datasetPath != null)
				return false;
		} else if (!datasetPath.equals(other.datasetPath))
			return false;
		if (executionType != other.executionType)
			return false;
		if (numberOfCores != other.numberOfCores)
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (monitor == null) {
			if (other.monitor != null)
				return false;
		} else if (!monitor.equals(other.monitor))
			return false;
		if (parallelTimeout != other.parallelTimeout)
			return false;
		if (!Arrays.equals(series, other.series))
			return false;
		if (slicing == null) {
			if (other.slicing != null)
				return false;
		} else if (!slicing.equals(other.slicing))
			return false;
		if (visitor == null) {
			if (other.visitor != null)
				return false;
		} else if (!visitor.equals(other.visitor))
			return false;
		return true;
	}
	public ExecutionType getExecutionType() {
		return executionType;
	}
	public void setExecutionType(ExecutionType executionType) {
		this.executionType = executionType;
		if (parallelTimeout<0) parallelTimeout = executionType.getTimeout();
		// To ensure that we enable the same behaviour as before, unless overwritten later
		if (this.executionType == ExecutionType.PARALLEL) {
			this.setNumberOfCores(Runtime.getRuntime().availableProcessors());
		}
	}
	public int getNumberOfCores() {
		return numberOfCores;
	}
	public void setNumberOfCores(int numberOfCores) {
		if (numberOfCores == -1){
			this.setNumberOfCores(Runtime.getRuntime().availableProcessors());
		}
		else {
			this.numberOfCores = numberOfCores;
		}
	}
	public long getParallelTimeout() {
		return parallelTimeout;
	}
	public void setParallelTimeout(long parallelTimeout) {
		this.parallelTimeout = parallelTimeout;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDatasetPath() {
		return datasetPath;
	}
	public void setDatasetPath(String datasetPath) {
		this.datasetPath = datasetPath;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int slugCount) {
		this.poolSize = slugCount;
	}

	@Override
	public int[] getDataDimensions() {
		return dataDimensions;
	}
	@Override
	public void setDataDimensions(int[] dataDimensions) {
		this.dataDimensions = dataDimensions;
		
	}
	@Override
	public void setLiveInfo(ILiveOperationInfo info) {
		this.liveInfo = info;
		
	}
	@Override
	public ILiveOperationInfo getLiveInfo() {
		return liveInfo;
	}

}
