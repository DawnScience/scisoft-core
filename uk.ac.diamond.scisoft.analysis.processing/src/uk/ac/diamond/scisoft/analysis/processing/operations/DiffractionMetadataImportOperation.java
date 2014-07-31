package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class DiffractionMetadataImportOperation extends AbstractOperation {

	DiffractionMetadataImportModel model;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportOperation";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		slice.addMetadata((IDiffractionMetadata)model.getMetadata());
		return new OperationData(slice);
	}

	@Override
	public void setModel(IOperationModel parameters) throws Exception {
		if (!(parameters instanceof DiffractionMetadataImportModel)) throw new IllegalArgumentException("incorrect model");
		
		model = (DiffractionMetadataImportModel)parameters;

	}

	@Override
	public OperationRank getInputRank() {
		// TODO Auto-generated method stub
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		// TODO Auto-generated method stub
		return OperationRank.ANY;
	}

}
