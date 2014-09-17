package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class FittingOperation extends AbstractOperation<FittingModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fittingOperation";
	}


	@Override
	public OperationData execute(IDataset data, IMonitor monitor) throws OperationException {
		
		try {
			List<CompositeFunction> fittedPeakList = Generic1DFitter.fitPeakFunctions((Dataset)model.getxAxis(), 
					                                                                  (Dataset)data, 
					                                                                  model.getPeak(), model.createOptimizer(),
					                                                                  model.getSmoothing(), model.getNumberOfPeaks(),
					                                                                  model.getThreshold(), 
					                                                                  model.isAutostopping(), model.isBackgrounddominated(), monitor);
			
	        // Same original data but with some fitted peaks added to auxillary data.
			return new OperationData(data, (Serializable)fittedPeakList);
		} catch (Exception ne) {
			throw new OperationException(this, ne);
		}
	}
	
	public OperationRank getInputRank() {
		return OperationRank.ONE; // XY data
	}
	public OperationRank getOutputRank() {
		return OperationRank.ZERO; 
	}

}
