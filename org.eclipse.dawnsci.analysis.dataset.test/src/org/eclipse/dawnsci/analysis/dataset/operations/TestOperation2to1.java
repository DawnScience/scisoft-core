package org.eclipse.dawnsci.analysis.dataset.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;

public class TestOperation2to1 extends AbstractTestOperation{

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		SliceND s = new SliceND(input.getShape());
		s.setSlice(0, 0, 1, 1);
		
		IDataset slice = input.getSlice(s).squeeze();
		
		return new OperationData(slice,getAux());
	}

}
