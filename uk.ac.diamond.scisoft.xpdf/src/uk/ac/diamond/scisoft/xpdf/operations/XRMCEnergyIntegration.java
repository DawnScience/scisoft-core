package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;
import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCEnergyIntegrator;

public class XRMCEnergyIntegration extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XRMCEnergyIntegration";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Default XRMC file are four dimensional: interactions, energy, x, y
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO; // Just an x-y image
	}

	@Override
	protected OperationData process(IDataset input2d, IMonitor monitor) throws OperationException {

		// Fetch and convert the underlying XRMC data from the 2d processing data
		Dataset xrmcData = null;
		try {
			xrmcData = DatasetUtils.convertToDataset(getSliceSeriesMetadata(input2d).getParent().getSlice());
		} catch (DatasetException e) {
			throw new OperationException(this, e.toString());
		}
		if (xrmcData == null || !(xrmcData instanceof Dataset)) {
			throw new OperationException(this, "Could not get underlying XRMC data.");
		}
		int[] xrmcShape = xrmcData.getShape();
		Dataset lastScattering = xrmcData.getSlice(new int[] {xrmcShape[0]-1, 0, 0, 0}, xrmcShape, new int[] {1, 1, 1, 1}).squeeze();

		XPDFMetadata xpdfMetadata = input2d.getFirstMetadata(XPDFMetadata.class);
		if (!XPDFOperationChecker.hasMetadata(input2d) || !XPDFOperationChecker.hasDetectorMetadata(xpdfMetadata))
			throw new OperationException(this, "XPDF detector metadata not found");
		
		XRMCMetadata xrmcMetadata = input2d.getFirstMetadata(XRMCMetadata.class);
		
		XRMCEnergyIntegrator integrator = new XRMCEnergyIntegrator();
		integrator.setXRMCData(lastScattering);
		integrator.setDetector(xpdfMetadata.getDetector());
		integrator.setXRMCDetector(xrmcMetadata.getDetector());

		Dataset counts = integrator.getDetectorCounts();
		
		return new OperationData(counts);
	}

}
