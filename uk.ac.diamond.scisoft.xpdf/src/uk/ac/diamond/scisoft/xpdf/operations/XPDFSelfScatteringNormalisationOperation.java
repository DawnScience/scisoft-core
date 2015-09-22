package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Apply self-scattering corrections to the data.
 * @author Timothy Spain (rkl37156) thimothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFSelfScatteringNormalisationOperation extends
		AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset absCor, IMonitor monitor) throws OperationException {
		
		IDataset soq = null;

		XPDFMetadata theXPDFMetadata = absCor.getFirstMetadata(XPDFMetadata.class);
		if (theXPDFMetadata == null) throw new OperationException(this, "XPDF metadata not found.");
		if (theXPDFMetadata.getSample() == null) throw new OperationException(this, "XPDF sample metadata not found.");
		XPDFTargetComponent sample = theXPDFMetadata.getSample();
		// Get the x variable
		if (absCor.getFirstMetadata(AxesMetadata.class) == null) throw new OperationException(this, "XPDF axis data not found.");
		XPDFCoordinates coords = new XPDFCoordinates(DatasetUtils.convertToDataset(absCor));
		soq = Maths.divide(Maths.subtract(absCor, sample.getSelfScattering(coords)), sample.getFSquared(coords));
		copyMetadata(absCor, soq);

		return new OperationData(soq);
	}
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFSelfScatteringNormalisationOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
