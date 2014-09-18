package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.dawb.common.services.IImageFilterService;
import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;

public abstract class AbstractSimpleImageOperation<T extends IOperationModel> extends AbstractOperation<IOperationModel,OperationData> {

	IImageFilterService service = null;

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		if (service == null) {
			try { 
				service = (IImageFilterService)ServiceManager.getService(IImageFilterService.class);
			} catch (Exception e) {
				throw new OperationException(this, "Could not get image processing service");
			}
		}
		
		IDataset out = processImage(slice, service);
		copyMetadata(slice, out);
		return new OperationData(out);
	}
	
	public abstract IDataset processImage(IDataset dataset, IImageFilterService service);

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
