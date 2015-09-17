package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Insert any XPDF related metadata.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 *
 * @param <T>
 * 			the Operation Model.
 * @param <D>
 * 			the Operation data.
 */
public abstract class XPDFInsertXMetadataOperation <T extends IOperationModel, D extends OperationData> extends
		AbstractOperation<T, D> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertXMetadataOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	protected XPDFMetadataImpl getAndRemoveXPDFMetadata( IDataset input) {
		XPDFMetadataImpl theXPDFMetadata = (XPDFMetadataImpl) input.getFirstMetadata(XPDFMetadata.class);
		// Create a new metadata if there is not one
		if (theXPDFMetadata == null)
			theXPDFMetadata = new XPDFMetadataImpl();
		input.clearMetadata(XPDFMetadata.class);
		
		return theXPDFMetadata;
		
	}
	
}
