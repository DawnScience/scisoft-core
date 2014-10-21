package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyMaths;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class SubtractBackgroupImageOperation extends AbstractOperation<SubtractBackgroundImageModel, OperationData> {

	Dataset background = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SubtractBackgroupImageOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		OriginMetadata om = getOriginMetadata(input);
		
		if (om == null) throw new OperationException(this, "No origin metadata!");
		
		String dsName = om.getDatasetName();
		
		if (background == null) {
			try {
				IDataHolder dh = LoaderFactory.getData(model.getFilePath());
				ILazyDataset lzBg = dh.getLazyDataset(dsName);
				background = LazyMaths.mean(lzBg, om.getDataDimensions());
			} catch (Exception e) {
				throw new OperationException(this, e.getMessage());
			}
		}
		
		Dataset output = Maths.subtract(input, background);
		copyMetadata(input, output);
		
		return new OperationData(output);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
