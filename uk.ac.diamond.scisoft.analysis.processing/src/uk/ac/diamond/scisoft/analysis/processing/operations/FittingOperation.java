package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

public class FittingOperation implements IOperation {

	private IRichDataset   dataset;
	private Class<? extends APeak> peakClass;
	private IOptimizer optimizer;
	private IDataset xAxis;
	private Integer smoothing;
	private Integer numPeaks;
	private Double threshold;
	private Boolean autoStopping;
	private Boolean backgroundDominated;

	@Override
	public String getOperationDescription() {
		return "An operation able to run a fit on datasets.";
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
		
		List<CompositeFunction> fittedPeakList = Generic1DFitter.fitPeakFunctions((AbstractDataset)xAxis, 
				                                                                  (AbstractDataset)data.getData(), 
				                                                                  peakClass, optimizer,
				                                                                  smoothing, numPeaks, threshold, 
				                                                                  autoStopping, backgroundDominated);
		
        // Same original data but with some fitted peaks added to auxillary data.
		return new OperationData(data.getData(), (Serializable)fittedPeakList);
	}

	@Override
	public void setParameters(Serializable... parameters) throws IllegalArgumentException {

		// TODO FIXME In order to drive the fitting we have to have all these arguments. 
		// Bit funny at moment and probably other IOptimizer do not have two argument constructors.
		// However the fitting must have new IOptimizers and not reuse the same one because
		// otherwise the parallel execution does not work.
		if (parameters.length!=10) throw new IllegalArgumentException("The parameters accepted must be the same as the Generic1DFitter.fitPeakFunctions(...) [without the data to fit] !");


		this.xAxis     = (IDataset)parameters[0];
		this.peakClass = (Class<? extends APeak>)parameters[1];
		Class<? extends IOptimizer> optimClass = (Class<? extends IOptimizer>)parameters[2];
		final double quality = (Double)parameters[3];
		final long   seed    = (Long)parameters[4];
		try {
			this.optimizer = optimClass.getConstructor(double.class, Long.class).newInstance(quality, seed);
		} catch (Exception e) {
			throw new IllegalArgumentException("The third argument must be the optimizer class, the fourth and fifth, its arguments.");
		}
		
		this.smoothing = (Integer)parameters[5];
		this.numPeaks  = (Integer)parameters[6];
		this.threshold = (Double)parameters[7];
		this.autoStopping        = (Boolean)parameters[8];
		this.backgroundDominated = (Boolean)parameters[9];
	}


}
