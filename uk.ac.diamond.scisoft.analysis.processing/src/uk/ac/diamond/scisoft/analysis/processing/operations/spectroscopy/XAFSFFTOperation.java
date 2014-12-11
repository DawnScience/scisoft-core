/*
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

package uk.ac.diamond.scisoft.analysis.processing.operations.spectroscopy;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.spectroscopy.fitting.XafsFittingMethods;

public class XAFSFFTOperation extends AbstractOperation<XAFSFFTModel, OperationData> {

	@Override
	public String getId() {

		return "uk.ac.diamond.scisoft.spectroscopy.operations.XAFSFFTOperation";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] ax = getFirstAxes(input);
		
		if (ax == null || ax.length == 0 || ax[0] == null) throw new OperationException(this, "No axes found!");
		
		Dataset[] sub = XafsFittingMethods.getFFTfromK((Dataset)ax[0].getSlice(), (Dataset)input, model.isFilter(), model.isOversample());
		
		AxesMetadataImpl axm = new AxesMetadataImpl(1);
		sub[0].setName("R");
		axm.setAxis(0, sub[0]);
		
		
		sub[1].addMetadata(axm);
		
		return new OperationData(sub[1]);
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
