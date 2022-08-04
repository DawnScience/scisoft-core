package org.eclipse.dawnsci.analysis.dataset.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

public class TestOperation1to1 extends AbstractTestOperation {

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		return new OperationData(input,getAux());
	}

}
