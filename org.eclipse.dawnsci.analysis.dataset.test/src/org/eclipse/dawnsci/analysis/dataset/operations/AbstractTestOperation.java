package org.eclipse.dawnsci.analysis.dataset.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public abstract class AbstractTestOperation extends AbstractOperation<EmptyModel, OperationData> {
	
	@Override
	public String getId() {
		return "test";
	}
	
	protected IDataset[] getAux() {
		IDataset aux0 = DatasetFactory.ones(new int[] {1});
		aux0.setName("0");
		IDataset aux1 = DatasetFactory.ones(new int[] {5});
		aux1.setName("1");
		IDataset aux2 = DatasetFactory.ones(new int[] {5,5});
		aux2.setName("2");
		return new IDataset[] {aux0, aux1, aux2};
	}

}
