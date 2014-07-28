package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class FittingOperation implements IOperation {

	private IRichDataset   dataset;
	private FittingModel model;

	@Override
	public String getOperationDescription() {
		return "An operation able to run a fit on datasets.";
	}
		
	@Override
    public String getName() {
		return "Peak fit";
	}


	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fittingOperation";
	}

	@Override
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {

		if (data.length!=1) throw new IllegalArgumentException("The function operation can only operate on one dataset at a time!");
		this.dataset = data[0];
	}

	@Override
	public OperationData execute(OperationData data, IMonitor monitor) throws OperationException {
		
		try {
			List<CompositeFunction> fittedPeakList = Generic1DFitter.fitPeakFunctions((AbstractDataset)model.getxAxis(), 
					                                                                  (AbstractDataset)data.getData(), 
					                                                                  model.getPeak(), model.createOptimizer(),
					                                                                  model.getSmoothing(), model.getNumberOfPeaks(),
					                                                                  model.getThreshold(), 
					                                                                  model.isAutostopping(), model.isBackgrounddominated(), monitor);
			
	        // Same original data but with some fitted peaks added to auxillary data.
			return new OperationData(data.getData(), (Serializable)fittedPeakList);
		} catch (Exception ne) {
			throw new OperationException(this, ne);
		}
	}

	@Override
	public void setModel(IOperationModel model) throws Exception {

		this.model = (FittingModel)model;
	}

	
	public OperationRank getInputRank() {
		return OperationRank.ONE; // XY data
	}
	public OperationRank getOutputRank() {
		return OperationRank.ZERO; 
	}

}
