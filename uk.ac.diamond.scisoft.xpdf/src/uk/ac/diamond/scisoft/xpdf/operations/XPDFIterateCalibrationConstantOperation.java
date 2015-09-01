package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.XPDFQSquaredIntegrator;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

public class XPDFIterateCalibrationConstantOperation extends
		AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

//		String xyFilePath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceria_dean_data/";
//		// Load the reference background subtracted traces from the designated xy file
//		Dataset subBakRef = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"SUBBAK.xy", "Column_2"));
//		Dataset subBakCapRef = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"SUBBAK_cap.xy", "Column_2"));
//		Dataset absCorRef = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"ABSCOR.xy", "Column_2"));
//		
//		Dataset subBak = DatasetUtils.convertToDataset(input);
//		Dataset subBakCap = null;
		
//		try {
//			if (input.getMetadata(XPDFMetadata.class) != null &&
//				!input.getMetadata(XPDFMetadata.class).isEmpty() &&
//				input.getMetadata(XPDFMetadata.class).get(0) != null &&
//				input.getMetadata(XPDFMetadata.class).get(0).getContainers() != null &&
//				!input.getMetadata(XPDFMetadata.class).get(0).getContainers().isEmpty() &&
//				input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0) != null &&
//				input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0).getTrace() != null &&
//				input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0).getTrace().isBackgroundSubtracted()) 
//				subBakCap = input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0).getTrace().getTrace();
//		} catch (Exception e) {
//			;
//		}
//
//		copyMetadata(input, absCorRef);
		
//		double delta = 1e-6;
//		double sampleDelta = (double) Maths.divide(Maths.square(Maths.subtract(subBakRef, subBak)), subBakRef).sum(true) / subBakRef.getSize();
//		boolean sampleMatch = sampleDelta < delta;
//		double containerDelta = (double) Maths.divide(Maths.square(Maths.subtract(subBakCapRef, subBakCap)), subBakCapRef).sum(true) / subBakRef.getSize();
//		boolean containerMatch = containerDelta < delta;
		
		
		// The real XPDFIterateCalibrationConstantOperation starts here
		
		// TODO: get from the model
		int nIterations = 5;
		LinkedList<Double> calibrationConstants = new LinkedList<Double>();
		// The initial value is 1
		calibrationConstants.add(20.0);

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
		
		// Get 2θ, the axis variable
		Dataset twoTheta = Maths.toRadians(DatasetUtils.convertToDataset(AbstractOperation.getFirstAxes(input)[0]));
		
		// Set up the q² integrator class
		XPDFQSquaredIntegrator qSquaredIntegrator = new XPDFQSquaredIntegrator(twoTheta, theXPDFMetadata.getBeam());
		
		// Difference ofKrogh-Moe sum and integral of Thomson self-scattering
		double selfScatteringDenominator = 
				qSquaredIntegrator.ThomsonIntegral(theXPDFMetadata.getSample().getSelfScattering(twoTheta))
				- theXPDFMetadata.getSample().getKroghMoeSum();
		
		Map<AbstractMap.SimpleImmutableEntry<Integer, Integer>, Dataset> absorptionCorrections = 
				theXPDFMetadata.getAbsorptionMaps(twoTheta.reshape(twoTheta.getSize(), 1), DoubleDataset.zeros(twoTheta.reshape(twoTheta.getSize(), 1)));
		
		for (int i = 0; i < nIterations; i++) {
			absCor = iterateCalibrationConstant(backgroundSubtracted, calibrationConstants, qSquaredIntegrator,
					selfScatteringDenominator, DoubleDataset.zeros((Dataset) input), theXPDFMetadata.getSampleIlluminatedAtoms());			
		}
		
		copyMetadata(input, absCor);
		
		return new OperationData(absCor);
	}
	
	/* Run through one iteration of the calibration. The input list of Datasets
	 * is the background subtracted flux traces for the target components
	 * ordered from innermost (the sample) at [0] to outermost at size-1.
	 * 
	 * The return value is the absorption corrected data. The list of
	 * calibration constants is also altered, adding the latest value to the
	 * end of the list. 
	 *   
	 */
	private Dataset iterateCalibrationConstant(List<Dataset> backgroundSubtracted, 
			LinkedList<Double> calibrationConstants, XPDFQSquaredIntegrator qSquaredIntegrator,
			double selfScatteringDenominator, Dataset multipleScatteringCorrection,
			double sampleAtomCount) {

		// Divide by the calibration constant and subtract the multiple scattering correction
		List<Dataset> calCon = new ArrayList<Dataset>();
		for (Dataset componentTrace : backgroundSubtracted)
			calCon.add(Maths.divide(componentTrace, calibrationConstants.getLast()));
		
		// Mulcor should be a LinkedList, so that we can get the last element simply
		List<Dataset> mulCor = new ArrayList<Dataset>();
		for (Dataset componentTrace : calCon)
			mulCor.add(Maths.subtract(componentTrace, multipleScatteringCorrection));
		
		Dataset absCor = applyCalibrationConstant(mulCor);
// TODO: Add this back when the real calculations are done
		//		absCor.idivide(sampleAtomCount);
		
		// Integrate
		double numerator = qSquaredIntegrator.ThomsonIntegral(absCor);
		// Divide by denominator
		double aMultiplier = numerator/selfScatteringDenominator;
		// Make the new calibration constant
		double updatedCalibration = aMultiplier * calibrationConstants.getLast();
		// Add to the list
		calibrationConstants.add(updatedCalibration);
		
		return absCor;
		
	}
	
	
	private Dataset applyCalibrationConstant(List<Dataset> mulCor) {
		// TODO Do something more than just returning the sample data
		double a;
		String dataPath = "/home/rkl37156/ceria_dean_data/";
		String prefix = "ABSCOR";
		String suffix = ".xy";
		String infix = (mulCor.get(0).getDouble(1) <= 1e15 ) ? ".1" : ""; 

		Dataset AbsCorFake = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, dataPath+prefix+infix+suffix, "Column_2"));

		return AbsCorFake;
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
