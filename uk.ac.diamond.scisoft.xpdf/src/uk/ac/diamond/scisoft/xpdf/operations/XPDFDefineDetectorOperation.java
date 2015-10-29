package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;

public class XPDFDefineDetectorOperation extends XPDFInsertXMetadataOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor minotaur) throws OperationException {
		
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		XPDFDetector tect = new XPDFDetector();
		// TODO: Get from the Model
		tect.setSubstance(new XPDFSubstance("Perkin Elmer", "CsI", 4.51, 1.0));
		// TODO: Get from the Model
		tect.setThickness(0.5);
		
		input.setMetadata(theXPDFMetadata);
		
		return new OperationData(input);
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFDefineDetectorOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ANY;
	}

}
