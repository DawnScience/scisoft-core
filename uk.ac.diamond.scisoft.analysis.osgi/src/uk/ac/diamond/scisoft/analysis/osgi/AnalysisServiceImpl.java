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

import uk.ac.diamond.scisoft.analysis.IAnalysisService;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;

/**
 * Implementation of service IAnalysisService which allows plugins not to 
 * directly depend on everything required to do file loading.
 */
public class AnalysisServiceImpl extends AbstractServiceFactory implements IAnalysisService {

	@Override
	public IDataset arange(double stop, int dtype) {
		return AbstractDataset.arange(stop, dtype);
	}

	@Override
	public IDataset arange(double start, double stop, double step, int dtype) {
		return AbstractDataset.arange(stop, stop, step, dtype);
	}

	@Override
	public IDataset createDoubleDataset(double[] da, int... shape) {
		return new DoubleDataset(da, da.length);
	}

	@Override
	public IDataset convertToAbstractDataset(IDataset set) {
		return DatasetUtils.convertToAbstractDataset(set);
	}

	@Override
	public IDataset sum(IDataset sum, int i) {
		return ((AbstractDataset)sum).sum(i);
	}

	@Override
	public IDataset transpose(IDataset trans) {
		return ((AbstractDataset)trans).transpose();
	}

	@Override
	public IDataset mean(IDataset slice, int i) {
		return ((AbstractDataset)slice).mean(i);
	}

	@Override
	public IDataset max(IDataset slice, int i) {
		return ((AbstractDataset)slice).max(i);
	}

	@Override
	public IDataset min(IDataset slice, int i) {
		return ((AbstractDataset)slice).min(i);
	}

	@Override
	public IDataset median(IDataset slice, int i) {
		return Stats.median((AbstractDataset)slice, i);
	}

	@Override
	public IDataset mode(IDataset slice, int i) {
		//FIXME
		throw new RuntimeException("Mode not implemented!");
	}

	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator, IServiceLocator locator) {
        if (serviceInterface==IAnalysisService.class) {
        	return new AnalysisServiceImpl();
        }
		return null;
	}

}
