/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.crypto.Data;

import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IDataBasedFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.FittingConstants;
import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.FittingConstants.FIT_ALGORITHMS;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class FunctionFittingOperation extends AbstractOperation<FunctionFittingModel, OperationData> {

	
	private Add original;
	private double[] points;
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.FunctionFittingOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (original == null) {
			try {
				IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
				IPersistentFile pf = service.getPersistentFile(model.getFilePath());
				Map<String, IFunction> functions = pf.getFunctions(monitor);
				Map<String, IROI> rois = pf.getROIs(monitor);

				IROI roi = rois.get("fit_region");
				RectangularROI rr = null;

				if (roi instanceof RectangularROI) {
					rr = (RectangularROI)roi;
				}

				final double[] p1 = rr.getPointRef();
				final double[] p2 = rr.getEndPoint();

				points = new double[]{p1[0],p2[0]};

				Add resultFunc = new Add();
				for (Entry<String,IFunction> f : functions.entrySet()){
					if (f.getKey().contains("initial")) continue;
					resultFunc.addFunction(f.getValue());
				}

				original = (Add)resultFunc.copy();

			} catch (Exception e) {
				throw new OperationException(this, "Could not load functions from file");
			}
		}

			ILazyDataset[] firstAxes = getFirstAxes(input);
			Dataset x = null;
			if (firstAxes != null && firstAxes[0] != null) {
				x = DatasetUtils.convertToDataset(firstAxes[0].getSlice());
			} else {
				x = DatasetFactory.createRange(input.getSize(), Dataset.FLOAT64);
			}
			
			Dataset[] traceROI = new Dataset[]{x, (Dataset) input};
			
			try {
				traceROI = Generic1DFitter.xintersection(traceROI[0], traceROI[1],
						points[0],points[1]);
			} catch (Throwable npe) {
				throw new OperationException(this,npe.getMessage());
			}
			
			Add outfit = null;
			boolean success = true;
			
			try {
				outfit = doFit(traceROI[0], traceROI[1], original, FIT_ALGORITHMS.APACHENELDERMEAD);
			} catch (Exception e) {
				success = false;
				outfit = original;
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
						String fullName = n +":"+pn;
						DoubleDataset d = new DoubleDataset(new double[]{v},new int[]{1});
						d.setName(fullName);
						d.squeeze();
						params.add(d);
					}
					
					IDataset outx = traceROI[0].getSliceView();
					outx.setName(x.getName());
					Dataset vals = outfit.calculateValues(outx);
					vals = Maths.subtract(traceROI[1], vals);
					vals.setName("residual");
					
					AxesMetadataImpl ax = new AxesMetadataImpl(1);
					ax.addAxis(0, outx);
					vals.addMetadata(ax);
					params.add(vals);
					
				}
				
				return new OperationData(input, (Serializable[])params.toArray(new IDataset[params.size()]));
				
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
		//	logger.debug("Accuracy is set to {}", accuracy);
		//	int algoId = prefs.getInt(FittingConstants.FIT_ALGORITHM);
		//	FIT_ALGORITHMS algorithm = FIT_ALGORITHMS.fromId(algoId);

		// We need to run the fit on a copy of the compFunction
		//	// otherwise the fit will affect the input values.
		//	Add compFunctionCopy = (Add) compFunction.copy();
		//	IFunction[] functionCopies = compFunctionCopy.getFunctions();
		switch (algorithm) {
		default:
		case APACHENELDERMEAD:
			resultFunction = new Add();
			for (IFunction function : functionCopies) {
//				if (function instanceof APeak) {
//					APeak p = (APeak)function;
//					for (IParameter par : p.getParameters()) {
//						if (!par.getName().equals("area")) {
//							par.setFixed(true);
//						}
//					}
//					
//				}
				resultFunction.addFunction(function);
				if (function instanceof IDataBasedFunction) {
					IDataBasedFunction dataBasedFunction = (IDataBasedFunction) function;
					dataBasedFunction.setData(x, y);
				}
			}
			
			Fitter.ApacheNelderMeadFit(new Dataset[] { x }, y,
					resultFunction, 100000);
			break;
		case GENETIC:
			IOptimizer fitMethod = new GeneticAlg(accuracy);
			resultFunction = Fitter
					.fit(x, y, fitMethod, functionCopies);
			break;
		}

		return resultFunction;
	}
}
