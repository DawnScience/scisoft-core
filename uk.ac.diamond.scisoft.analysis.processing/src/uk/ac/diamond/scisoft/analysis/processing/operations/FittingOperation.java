package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;

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
