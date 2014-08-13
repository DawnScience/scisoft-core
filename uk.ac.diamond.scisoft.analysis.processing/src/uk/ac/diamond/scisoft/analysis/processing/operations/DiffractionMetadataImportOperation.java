package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionMetaReader;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class DiffractionMetadataImportOperation extends AbstractOperation {

	DiffractionMetadataImportModel model;
	IDiffractionMetadata metadata;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportOperation";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		if (metadata == null) {
			NexusDiffractionMetaReader reader = new NexusDiffractionMetaReader(model.getFilePath());
			IDiffractionMetadata md = reader.getDiffractionMetadataFromNexus(null);
			if (!reader.isPartialRead()) throw new OperationException(this, "File does not contain metadata");
			metadata = md;
		}
		
		slice.addMetadata(metadata);
		return new OperationData(slice);
	}

	@Override
	public void setModel(IOperationModel parameters) throws Exception {
		if (!(parameters instanceof DiffractionMetadataImportModel)) throw new IllegalArgumentException("incorrect model");
		
		model = (DiffractionMetadataImportModel)parameters;

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
