package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class DiffractionMetadataImportModel extends AbstractOperationModel {

	IDiffractionMetadata metadata;

	public IDiffractionMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(IDiffractionMetadata metadata) {
		this.metadata = metadata;
	}
	
}
