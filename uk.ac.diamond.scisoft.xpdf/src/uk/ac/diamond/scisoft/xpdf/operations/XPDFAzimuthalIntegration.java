package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;

import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalPixelIntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalPixelIntegrationOperation;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

public class XPDFAzimuthalIntegration<T extends AzimuthalPixelIntegrationModel> extends
		AzimuthalPixelIntegrationOperation<AzimuthalPixelIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFAzimuthalIntegration";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
	
		XPDFMetadata xMeta = input.getFirstMetadata(XPDFMetadata.class);
		OperationData superResult = super.process(input, monitor);
		if (xMeta != null)
			superResult.getData().setMetadata(xMeta);
		return superResult;
	}
	
}
