package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToRotatedCartesian;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

public class SelectROI implements IOperation {

	private RectangularROI roi = null;
	
	public SelectROI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getOperationDescription() {
		return "Passes on only the selected region of interest down the pipeline";
	}
	
	@Override
    public String getName() {
		return "Select Region";
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.selectROI";
	}

	@Override
	public void setDataset(IRichDataset... data)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public OperationData execute(OperationData slice, IMonitor monitor)
			throws OperationException {
		
		// Get the data ROI
		MapToRotatedCartesian map = new MapToRotatedCartesian(roi);
		AbstractDataset dataRegion = map.value(slice.getData()).get(0);
		
		OperationData result = new OperationData(dataRegion);
		
		return result;
	}

	@Override
	public void setModel(IOperationModel model) {
		// TODO Auto-generated method stub

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
