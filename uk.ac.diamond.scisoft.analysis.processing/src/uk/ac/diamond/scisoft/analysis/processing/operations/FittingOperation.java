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
import uk.ac.diamond.scisoft.analysis.processing.IOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class FittingOperation implements IOperation {

	private IRichDataset   dataset;
	private Class<? extends APeak> peakClass;
	private IDataset xAxis;
	private int smoothing;
	private int numPeaks;
	private double threshold;
	private boolean autoStopping;
	private boolean backgroundDominated;
	private Class<? extends IOptimizer> optimClass;
	private double quality;
	private Long   seed;

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
		
		IOptimizer optimizer;
		synchronized(this) { // Seemed to fail thread test without this.
			try {
				optimizer = optimClass.getConstructor(double.class, Long.class).newInstance(quality, seed);
			} catch (Exception e) {
				throw new OperationException(this, e);
			}
		}

		List<CompositeFunction> fittedPeakList = Generic1DFitter.fitPeakFunctions((AbstractDataset)xAxis, 
				                                                                  (AbstractDataset)data.getData(), 
				                                                                  peakClass, optimizer,
				                                                                  smoothing, numPeaks, threshold, 
				                                                                  autoStopping, backgroundDominated, monitor);
		
        // Same original data but with some fitted peaks added to auxillary data.
		return new OperationData(data.getData(), (Serializable)fittedPeakList);
	}

	@Override
	public void setModel(IOperationModel model) throws Exception {

		// TODO FIXME - maybe just store the model and use the values when needed...
		this.xAxis      = (IDataset)model.get("xaxis");
		this.peakClass  = (Class<? extends APeak>)model.get("peak");
		this.optimClass = (Class<? extends IOptimizer>)model.get("optimizer");
		this.quality    = (Double)model.get("quality");
		this.seed       = (Long)model.get("seed");
		
		this.smoothing  = (Integer)model.get("smoothing");
		this.numPeaks   = (Integer)model.get("numberOfPeaks");
		this.threshold  = (Double)model.get("threshold");
		this.autoStopping        = (Boolean)model.get("autoStopping");
		this.backgroundDominated = (Boolean)model.get("backgroundDominated");
	}

	
	public OperationRank getInputRank() {
		return OperationRank.ONE; // XY data
	}
	public OperationRank getOutputRank() {
		return OperationRank.ZERO; 
	}

}
