/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.dataset.DatasetException;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IDataBasedFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.fitting.FittingConstants.FIT_ALGORITHMS;
import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

@Atomic
public class FunctionFittingOperation extends AbstractOperation<FunctionFittingModel, OperationData> {

	
	private volatile FitInformation info;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.FunctionFittingOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		FitInformation finfo = getFitInformation(model);
		Add copy = null;
		try {
			copy = (Add)finfo.original.copy();
		} catch (Exception e1) {
			throw new OperationException(this, "Could not copy function!");
		}
				

			ILazyDataset[] firstAxes = getFirstAxes(input);
			Dataset x = null;
			if (firstAxes != null && firstAxes[0] != null) {
				try {
					x = DatasetUtils.sliceAndConvertLazyDataset(firstAxes[0]);
				} catch (DatasetException e) {
					throw new OperationException(this, e);
				}
			} else {
				x = DatasetFactory.createRange(input.getSize(), Dataset.FLOAT64);
			}
			
			Dataset[] traceROI;
			
			try {
				traceROI = Generic1DFitter.selectInRange(x, DatasetUtils.convertToDataset(input),
						finfo.points[0],finfo.points[1]);
			} catch (Throwable npe) {
				throw new OperationException(this,npe.getMessage());
			}
			
			Add outfit = null;
			boolean success = true;
			
			try {
				outfit = doFit(traceROI[0], traceROI[1], copy, model.getOptimiser());
			} catch (Exception e) {
				success = false;
				outfit = copy;
			}
				
				List<IDataset> params = new ArrayList<IDataset>();
				Map<String,Integer> nameMap = new HashMap<String,Integer>();
				for (IFunction f : outfit.getFunctions()) {
					String n = f.getName();
					if (nameMap.containsKey(n)){
						int c = nameMap.get(n);
						c++;
						nameMap.put(n, c);
						n = n+c;
					} else {
						nameMap.put(n,0);
						n=n+0;
					}
					for (IParameter p : f.getParameters()) {
						String pn = p.getName();
						double v = p.getValue();
						if (!success) v = Double.NaN;
						String fullName = n +"_"+pn;
						DoubleDataset d = new DoubleDataset(new double[]{v},new int[]{1});
						d.setName(fullName);
						d.squeeze();
						params.add(d);
					}
					
				}
				double r = outfit.residual(true, traceROI[1], null, new IDataset[] {traceROI[0]});
				if (!success) r = Double.NaN;
				Dataset residual = DatasetFactory.createFromObject(r);
				residual.squeeze();
				residual.setName("rms");
				params.add(residual);
				
				IDataset outx = traceROI[0].getSliceView();
				
				String xName = x.getName();
				if (xName == null || xName.isEmpty()) {
					xName = "xAxis";
				}
				
				outx.setName(xName);
				Dataset vals = outfit.calculateValues(outx);
				vals.setName("function");
				Dataset res = Maths.subtract(traceROI[1], vals);
				res.setName("residual");
				
				AxesMetadataImpl ax = new AxesMetadataImpl(1);
				ax.addAxis(0, outx);
				vals.addMetadata(ax);
				ax = new AxesMetadataImpl(1);
				ax.addAxis(0, outx);
				res.addMetadata(ax);
				params.add(vals);
				params.add(res);
				
				return new OperationData(input, (Serializable[])params.toArray(new IDataset[params.size()]));
				
	}
	
	private FitInformation getFitInformation(FunctionFittingModel model) {
		FitInformation localInfo = info;
		if (localInfo == null) {
			synchronized(this) {
				localInfo = info;
				if (localInfo == null) {

					try {

						IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
						IPersistentFile pf = service.getPersistentFile(model.getFilePath());
						Map<String, IFunction> functions = pf.getFunctions(null);
						Map<String, IROI> rois = pf.getROIs(null);

						IROI roi = rois.get("fit_region");
						RectangularROI rr = null;

						if (roi instanceof RectangularROI) {
							rr = (RectangularROI)roi;
						}

						final double[] p1 = rr.getPointRef();
						final double[] p2 = rr.getEndPoint();

						double[] points = new double[]{p1[0],p2[0]};

						Add resultFunc = new Add();
						for (Entry<String,IFunction> f : functions.entrySet()){
							if (f.getKey().contains("initial")) continue;
							resultFunc.addFunction(f.getValue());
						}

						if (resultFunc.getFunctions().length == 0) {
							for (Entry<String,IFunction> f : functions.entrySet()){
								resultFunc.addFunction(f.getValue());
							}
						}
						
						Add original = (Add)resultFunc.copy();

						if (model.getOptimiser() == FIT_ALGORITHMS.APACHELEVENBERGMAQUARDT) clearRanges(original);
						
						FitInformation i = new FitInformation();
						i.original = original;
						i.points = points;

						info = localInfo = i;;

					} catch (Exception e) {
						throw new OperationException(this, "Could not load functions from file");
					}
				}
			}
		}
		
		return localInfo;
	}

	private void clearRanges(Add function) {
		IFunction[] functions = function.getFunctions();
		for (IFunction f : functions) {
			IParameter[] ps = f.getParameters();
			for (IParameter p :ps) {
				p.setLimits(-Double.MAX_VALUE, Double.MAX_VALUE);
			}
		}
	}
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	private Add doFit(Dataset x, Dataset y, Add resultFunction, FIT_ALGORITHMS algorithm) throws Exception{
		double accuracy = 10e-5;
		IFunction[] functionCopies = resultFunction.getFunctions();
		for (IFunction function : functionCopies) {
			if (function instanceof IDataBasedFunction) {
				IDataBasedFunction dataBasedFunction = (IDataBasedFunction) function;
				dataBasedFunction.setData(x, y);
			}
		}
		IOptimizer optimizer = null;
		switch (algorithm) {
		default:
		case APACHENELDERMEAD:
			optimizer = new ApacheOptimizer(Optimizer.SIMPLEX_NM);
			break;
		case GENETIC:
			optimizer = new GeneticAlg(accuracy);
			break;
		case APACHECONJUGATEGRADIENT:
			optimizer = new ApacheOptimizer(Optimizer.CONJUGATE_GRADIENT);
			break;
		case APACHELEVENBERGMAQUARDT:
			optimizer = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			break;
		}
		optimizer.optimize(new IDataset[] {x}, y, resultFunction);

		return resultFunction;
	}
	
	@Override
	public void setModel(FunctionFittingModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					info = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
	private class FitInformation {
		public Add original;
		public double[] points;
	}
}
