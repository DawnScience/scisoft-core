package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.xpdf.XPDFCalibration;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFQSquaredIntegrator;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Iterate the calibration constant for the XPDF data.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFIterateCalibrationConstantOperation extends
		AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		// The real XPDFIterateCalibrationConstantOperation starts here
		
		XPDFCalibration theCalibration = new XPDFCalibration();
		
		// TODO: get from the model
		int nIterations = 5;
		// The initial value of the calibration constant is 20
		theCalibration.setInitialCalibrationConstant(20.0);
		
		Dataset absCor = null;
		
		XPDFMetadata theXPDFMetadata = null;
		// Get the metadata
		try {
			if (input.getMetadata(XPDFMetadata.class) != null &&
					!input.getMetadata(XPDFMetadata.class).isEmpty() &&
					input.getMetadata(XPDFMetadata.class).get(0) != null)
				theXPDFMetadata = input.getMetadata(XPDFMetadata.class).get(0);
		} catch (Exception e) {
			// No XPDF metadata? Bail out!
			return new OperationData(input);
		}
		
		// TODO: Get the order of the containers and the sample
		List<Dataset> backgroundSubtracted = new ArrayList<Dataset>();
		// The 0th element is the sample
		backgroundSubtracted.add((Dataset) input);
		// Add the containers is in order, innermost to outermost
		for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
			backgroundSubtracted.add(container.getBackgroundSubtractedTrace());
		}
		theCalibration.setBackgroundSubtracted(backgroundSubtracted);
	
		theCalibration.setSampleIlluminatedAtoms(theXPDFMetadata.getSampleIlluminatedAtoms());
		
		// Get 2θ, the axis variable
		Dataset twoTheta = Maths.toRadians(DatasetUtils.convertToDataset(AbstractOperation.getFirstAxes(input)[0]));
		XPDFCoordinates coordinates = new XPDFCoordinates();
		coordinates.setTwoTheta(twoTheta);
		coordinates.setBeamData(theXPDFMetadata.getBeam());
		
		// Set up the q² integrator class
		theCalibration.setqSquaredIntegrator(new XPDFQSquaredIntegrator(coordinates));//twoTheta, theXPDFMetadata.getBeam()));
		
		theCalibration.setSelfScatteringDenominatorFromSample(theXPDFMetadata.getSample(), coordinates);
		
		theCalibration.setAbsorptionMaps(theXPDFMetadata.getAbsorptionMaps(twoTheta.reshape(twoTheta.getSize(), 1), DoubleDataset.zeros(twoTheta.reshape(twoTheta.getSize(), 1))));
		
		for (int i = 0; i < nIterations; i++) 
			absCor = theCalibration.iterate();
		
		copyMetadata(input, absCor);
		
		return new OperationData(absCor);
	}
	

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantOperation";
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
