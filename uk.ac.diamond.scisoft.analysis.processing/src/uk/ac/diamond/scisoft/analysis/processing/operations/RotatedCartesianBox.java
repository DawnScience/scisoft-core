package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.lang.reflect.InvocationTargetException;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToRotatedCartesian;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;
import uk.ac.diamond.scisoft.analysis.roi.IRectangularROI;

public class RotatedCartesianBox extends AbstractOperation {

	private AbstractOperationModel model;
	
	@Override
    public String getName() {
		return "Select Region";
	}

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
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new OperationException(this, e);
		}
		MapToRotatedCartesian map = new MapToRotatedCartesian(roi);
		AbstractDataset dataRegion = map.value(slice).get(0);
		
		OperationData result = new OperationData(dataRegion);
		
		return result;
	}

	@Override
	public void setModel(IOperationModel model) {
		this.model = (AbstractOperationModel)model;
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
