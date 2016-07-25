package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;

/**
 * Defines the detector for XPDF processing.
 * <p>
 * An Operation class to that gathers the definition of a detector and adds
 * that information to the {@link XPDFMetadata} metadata.
 * 
 * @author Timothy Spain, timothy.spain@dimaond.ac.uk
 *
 */
public class XPDFDefineDetectorOperation extends XPDFInsertXMetadataOperation<XPDFDefineDetectorModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor minotaur) throws OperationException {
		
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		XPDFDetector tect = new XPDFDetector();
		tect.setSubstance(new XPDFSubstance(model.getDetectorName(), model.getDetectorMaterial(), model.getDensity(), 1.0));
		tect.setThickness(model.getThickness());
		tect.setSolidAngle(model.getSolidAngle());
		
		theXPDFMetadata.setDetector(tect);
		
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
