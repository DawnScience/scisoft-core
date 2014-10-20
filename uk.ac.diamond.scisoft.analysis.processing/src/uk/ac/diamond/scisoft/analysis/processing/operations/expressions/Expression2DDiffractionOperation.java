package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.dawb.common.services.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;

public class Expression2DDiffractionOperation extends Expression2DOperation {
	
	IDiffractionMetadata meta = null;
	IDataset q = null;
	IDataset tth = null;
	IDataset az = null;

	protected void addAdditionalValues(IDataset input, IExpressionEngine engine) throws OperationException {
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);
		if (md == null) throw new OperationException(this, "No detector calibration information!");
		if (!md.equals(meta)) {
			meta = md;
			
			q = PixelIntegrationUtils.generateQArray(meta);
			tth = PixelIntegrationUtils.generate2ThetaArrayRadians(meta);
			az = PixelIntegrationUtils.generateAzimuthalArray(input.getShape(), md, true);
		}
		
		if (meta == null) throw new OperationException(this, "No detector calibration information!");
		
		engine.addLoadedVariable("q", q);
		engine.addLoadedVariable("tth", tth);
		engine.addLoadedVariable("azimuth", az);
		engine.addLoadedVariable("energy", 1/(meta.getDiffractionCrystalEnvironment().getWavelength()*0.0806554465));
		}
	
}
