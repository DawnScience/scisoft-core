/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.osgi;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.IDatasetMathsService;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Stats;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

/**
 * Implementation of service IDatasetMathsService which allows plugins not to 
 * directly depend on everything required to do file loading.
 */
public class DatasetMathsServiceImpl extends AbstractServiceFactory implements IDatasetMathsService {

	static {
		System.out.println("Starting dataset maths service");
	}
	public DatasetMathsServiceImpl() {
		// Important do nothing here, OSGI may start the service more than once.
	}
	
	@Override
	public IDataset createRange(double stop, int dtype) {
		return DatasetFactory.createRange(stop, dtype);
	}

	@Override
	public IDataset createRange(double start, double stop, double step, int dtype) {
		return DatasetFactory.createRange(start, stop, step, dtype);
	}

	@Override
	public IDataset createDoubleDataset(double[] da, int... shape) {
		return new DoubleDataset(da, shape);
	}

	@Override
	public IDataset convertToDataset(IDataset data) {
		return DatasetUtils.convertToDataset(data);
	}

	@Override
	public IDataset sum(IDataset data, int axis) {
		return DatasetUtils.convertToDataset(data).sum(axis);
	}

	@Override
	public IDataset transpose(IDataset data) {
		return DatasetUtils.convertToDataset(data).transpose();
	}

	@Override
	public IDataset mean(IDataset data, int axis) {
		return DatasetUtils.convertToDataset(data).mean(axis);
	}

	@Override
	public IDataset max(IDataset data, int axis) {
		return DatasetUtils.convertToDataset(data).max(axis);
	}

	@Override
	public IDataset min(IDataset data, int axis) {
		return DatasetUtils.convertToDataset(data).min(axis);
	}

	@Override
	public IDataset median(IDataset data, int axis) {
		return Stats.median(DatasetUtils.convertToDataset(data), axis);
	}

	@Override
	public IDataset mode(IDataset data, int axis) {
		//FIXME
		throw new RuntimeException("Mode not implemented!");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator, IServiceLocator locator) {
        if (serviceInterface==IDatasetMathsService.class) {
        	return new DatasetMathsServiceImpl();
        }
		return null;
	}

}
