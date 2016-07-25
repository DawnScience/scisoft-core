package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;

@Atomic
public class PhotonCountingErrorOperation extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.PhotonCountingErrorOperation";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		DoubleDataset er = DatasetFactory.zeros(DoubleDataset.class, input.getShape());
		Dataset in = DatasetUtils.convertToDataset(input.getSliceView());
		
		IndexIterator i = in.getIterator();
		double val = 0;
		while (i.hasNext()) {
			val = in.getElementDoubleAbs(i.index);
			if (val < 0) continue;
			er.setAbs(i.index, Math.sqrt(val));
		}
		
		in.setError(er);

		return new OperationData(in);
	}
	

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
