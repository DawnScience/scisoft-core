package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Apply self-scattering corrections to the data.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFSelfScatteringNormalisationOperation extends
		AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset absCor, IMonitor monitor) throws OperationException {
		
		XPDFOperationChecker.checkXPDFMetadata(this, absCor, true, false, false);
		
		IDataset soq = null;

		XPDFMetadata theXPDFMetadata = absCor.getFirstMetadata(XPDFMetadata.class);
		if (theXPDFMetadata == null) throw new OperationException(this, "XPDF metadata not found.");
		if (theXPDFMetadata.getSample() == null) throw new OperationException(this, "XPDF sample metadata not found.");
		XPDFTargetComponent sample = theXPDFMetadata.getSample();
		// Get the x variable
		if (absCor.getShape().length == 1) {
			if (absCor.getFirstMetadata(AxesMetadata.class) == null) throw new OperationException(this, "Axis metadata not found.");
		} else if (absCor.getShape().length == 2) {
			if (absCor.getFirstMetadata(DiffractionMetadata.class) == null) throw new OperationException(this, "Diffraction metadata not found.");
		}
		
		double comptonScaling = theXPDFMetadata.getComptonScaling();
		if (comptonScaling != 1.0) System.out.println("XPDFSelfScatteringNormalisationOperation: Compton scattering scaled by" + comptonScaling);
		
		XPDFCoordinates coords = new XPDFCoordinates(DatasetUtils.convertToDataset(absCor));
		soq = Maths.divide(Maths.subtract(absCor, Maths.multiply(comptonScaling, sample.getSelfScattering(coords))), sample.getFSquared(coords));
		Dataset soqError = null;
		if (absCor.getErrors() != null)
			soqError = Maths.divide(absCor.getErrors(), sample.getFSquared(coords));
		copyMetadata(absCor, soq);
		if (soqError != null)
			soq.setErrors(soqError);

		soq.setName("S(q)");

		return new OperationData(soq);
	}
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFSelfScatteringNormalisationOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}
