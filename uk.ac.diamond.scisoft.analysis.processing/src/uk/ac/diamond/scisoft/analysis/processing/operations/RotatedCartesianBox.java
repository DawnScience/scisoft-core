package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.function.MapToRotatedCartesian;

public class RotatedCartesianBox extends AbstractOperation<RotatedCartesianBoxModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.selectROI";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		
		// Get the data ROI
		IRectangularROI roi;
		try {
			roi = (IRectangularROI)model.get("roi");
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		MapToRotatedCartesian map = new MapToRotatedCartesian(roi);
		Dataset dataRegion = map.value(slice).get(0);
		
		OperationData result = new OperationData(dataRegion);
		
		return result;
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
