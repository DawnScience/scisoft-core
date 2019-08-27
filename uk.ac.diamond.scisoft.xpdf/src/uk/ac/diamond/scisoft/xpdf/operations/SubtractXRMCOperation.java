package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.MaskMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.xpdf.XPDFAbsorptionMaps;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFElectronCrossSections;
import uk.ac.diamond.scisoft.xpdf.XPDFQSquaredIntegrator;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

public class SubtractXRMCOperation extends AbstractOperation<SubtractXRMCModel, OperationData> {

	private static final Logger logger = LoggerFactory.getLogger(SubtractXRMCOperation.class);
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.SubtractXRMCOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		XPDFOperationChecker.checkXPDFMetadata(this, input, true, true, true);
		boolean allIncoherent = XPDFOperationChecker.isAllIncoherentScatterPresent(input);
		if (!allIncoherent) throw new OperationException(this, "XRMC scattering data is not present for one or more components");
		
		XPDFMetadata xMeta = input.getFirstMetadata(XPDFMetadata.class);
		XPDFCoordinates coordinates = new XPDFCoordinates(DatasetUtils.convertToDataset(input));
		XPDFAbsorptionMaps absMaps = xMeta.getAbsorptionMaps(coordinates.getDelta(), coordinates.getGamma());
		Dataset mask = DatasetUtils.convertToDataset(input.getFirstMetadata(MaskMetadata.class).getMask());
		XPDFQSquaredIntegrator quint = new XPDFQSquaredIntegrator(coordinates, mask);
		
		// Obtain the initial gain
		Double modelGain = model.getFixedGain();
		if (modelGain != null && (modelGain.isInfinite() || modelGain.isNaN())) {
			logger.info("IterateGainOpertaion: Fixed gain is not finite, ignoring.");
			modelGain = null;
		}
		double gain = (modelGain == null) ? 1.0 : modelGain;
		double gainThreshold = 1e-4;
		
		Dataset sampleSubx;

		boolean isGainFinal = (modelGain != null);

		// Classical size of an electron, in metres or metres squared
		double classicalElectronRadius = 2.8179403227e-15;
		double electronCrossSectionUnits = classicalElectronRadius * classicalElectronRadius;
		
		if (true) {
			XRMCEmpiricalFitting.calculateEmpiricalGain(DatasetUtils.convertToDataset(input), xMeta.getIncoherentScattering(0), isGainFinal);
		}
		
		// Iterate the gain
		while(true) {
			logger.info("IterateGainOperation: Gain = " + gain);
			// Normalize all data by the estimated detector gain
			List<Dataset> normon = new ArrayList<>();
			normon.add(Maths.multiply(input, gain));
			for (XPDFTargetComponent container : xMeta.getContainers())
				normon.add(Maths.multiply(xMeta.getContainerTrace(container).getNormalizedTrace(), gain));
			
			// Subtract the XRMC simulated data
			List<Dataset> subx = new ArrayList<>();
			for (int i  = 0; i < normon.size(); i++)
				subx.add(Maths.subtract(normon.get(i), xMeta.getIncoherentScattering(i)));
			
			
			// Recursively subtract containers
			while (subx.size() > 1) {
				removeOutermostContainer(subx, absMaps);
			}
			sampleSubx = subx.get(0);
			// De-scale distinct scattering
			
			// absorption of sample scattering by all components
			Dataset allComponentTransmission = absMaps.getAbsorptionMap(0, 0);
			for (int iCont = 0; iCont < xMeta.getContainers().size(); iCont++) {
				allComponentTransmission.imultiply(absMaps.getAbsorptionMap(0, iCont));
			}
			sampleSubx.idivide(allComponentTransmission);
			// detector efficiency (detector transmission correction)
			sampleSubx.idivide(xMeta.getDetector().getTransmissionCorrection(coordinates.getTwoTheta(), xMeta.getBeam().getBeamEnergy()));
			// number of atoms
			sampleSubx.idivide(xMeta.getSampleIlluminatedAtoms());
			// Thomson cross-section
			Dataset thomsonXSection = XPDFElectronCrossSections.getThomsonCrossSection(coordinates);
			sampleSubx.idivide(thomsonXSection);
			
			if (isGainFinal == false) {
				double numerator = quint.qSquaredIntegral(sampleSubx);
				double denominator = xMeta.getSample().getKroghMoeSum();
				System.err.println("XRMC gain correction: " + numerator + "/" + denominator + " = " + (numerator/denominator));
				// Recalculate the gain
				double oldGain = gain;
				gain *= numerator/denominator;
				if (Math.abs(gain/oldGain - 1) < gainThreshold) 
					isGainFinal = true;
				if (Math.abs(gain) > Double.MAX_VALUE)
					throw new OperationException(this, "Gain larger than " + Double.MAX_VALUE + ", aborting.");
				if (Math.abs(gain) <= Double.MIN_NORMAL)
					throw new OperationException(this, "Gain less than " + Double.MIN_NORMAL+ ", aborting.");
				if (!Double.isFinite(gain))
					throw new OperationException(this, "Gain is not finite, aborting.");
			}
			
			if (isGainFinal) break;
			
		}
		
		sampleSubx.imultiply(1/electronCrossSectionUnits);
		sampleSubx.imultiply(model.getScaling());
		
		copyMetadata(input, sampleSubx);

		return new OperationData(sampleSubx);
	}

	// Removes the contribution of the outermost container
	private void removeOutermostContainer(List<Dataset> subx, XPDFAbsorptionMaps absMaps) {
		// divide the outermost trace by its own absorption
		int lastIndex = subx.size() - 1;
		Dataset outermostNormed = Maths.divide(subx.get(lastIndex), absMaps.getAbsorptionMap(lastIndex, lastIndex));
		// Transmission of the outermost n components. Here, just the very outermost.
		Dataset transmissionI = absMaps.getAbsorptionMap(lastIndex, lastIndex);
		for (int i = lastIndex; i >= 0; i--) {
			// update the transmission to include this component
			transmissionI.imultiply(absMaps.getAbsorptionMap(lastIndex, i));
			// subtract the contribution of the outermost container, scaled by the absorption
			Dataset absNMN = transmissionI;
			subx.get(i).isubtract(Maths.multiply(absNMN, outermostNormed));
		}
		// the effect of the outermost container should now be removed
		subx.remove(lastIndex);
	}
}
