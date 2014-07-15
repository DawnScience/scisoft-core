/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.osgi;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDatasetMathsService;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;

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
	public IDataset convertToAbstractDataset(IDataset data) {
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
