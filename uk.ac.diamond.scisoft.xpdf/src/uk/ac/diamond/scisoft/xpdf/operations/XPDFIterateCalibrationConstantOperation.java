package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MaskMetadata;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.xpdf.XPDFAbsorptionMaps;
import uk.ac.diamond.scisoft.xpdf.XPDFCalibration;
import uk.ac.diamond.scisoft.xpdf.XPDFCalibrationBase;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFQSquaredIntegrator;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Iterate the calibration constant for the XPDF data.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFIterateCalibrationConstantOperation extends
		AbstractOperation<XPDFIterateCalibrationConstantModel, OperationData> {

	
	private XPDFAbsorptionMaps cachedAbsorptionMaps;
	private boolean isCachedMapsSorted;
	@SuppressWarnings("unused")
	private Dataset cachedSampleFluorescence;
	
	private XPDFCalibrationBase cachedCalibration;
	private DiffractionMetadata cacheKeyMetadata;
	
	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFOperationChecker.checkXPDFMetadata(this, input, true, true, true);

		Dataset absCor = null;
		
		XPDFMetadata theXPDFMetadata = null;
		// Get the metadata
		theXPDFMetadata = input.getFirstMetadata(XPDFMetadata.class);
		if (theXPDFMetadata == null) throw new OperationException(this, "XPDFMetadata not found.");

		XPDFCalibrationBase theBase = getCachedCalibration(input);
		

		// The per-data calibration derived from the cached base
		XPDFCalibration theCalibration = new XPDFCalibration(theBase);

		
		// Set the fluorescence parameters for the calibration. 
		if (model.isDoingFluorescence()) {
			theCalibration.setDoFluorescence(true);
			// Check for fixed scale fluorescence in the model, and set the fixed scale if necessary
			if (model.isCalculatingFluorescence())
				theCalibration.performFullFluorescence();
			else
				theCalibration.setFixedFluorescence(model.getFluorescenceScale());
		} else {
			theCalibration.setDoFluorescence(false);
		}

		
		int nIterations = model.getnIterations();
		
		
		List<Dataset> backgroundSubtracted = new ArrayList<Dataset>();
		// The 0th element is the sample
		backgroundSubtracted.add(DatasetUtils.convertToDataset(input));
		// Add the containers in order, innermost to outermost
		for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
			backgroundSubtracted.add(theXPDFMetadata.getContainerTrace(container).getTrace());
		}
		theCalibration.setBackgroundSubtracted(backgroundSubtracted);
	
//		for (int i = 0; i < nIterations; i++) 
//			absCor = theCalibration.iterate(true);
		absCor = theCalibration.calibrate(nIterations, model.getNThreads(),this);
		
		
		// Copy metadata, but preserve the errors, if they exist.
		Dataset absCorError = (absCor.getErrors() != null) ? absCor.getErrors() : null;
		copyMetadata(input, absCor);
		if (absCorError != null)
			absCor.setErrors(absCorError);

		theXPDFMetadata = absCor.getFirstMetadata(XPDFMetadata.class);
		// assign the calibration values to the XPDF metadata object
		theXPDFMetadata.setCalibrationConstant(theCalibration.getCalibrationConstant());
		theXPDFMetadata.setFluorescenceScale(theCalibration.getFluorescenceScale());

		
		absCor.setName("Absorption Corrected");
		
		return new OperationData(absCor);
	}
	
	/**
	 * Orders the list of containers.
	 * <p>
	 * Given a list of container XPDFTargetComponents, orders them by their
	 * larger distance (external radius). Matches with the logic of the python
	 * version.
	 * @param containers
	 * 					the list of containers to order
	 * @return a map keyed by the position in the new list, with a value of the
	 * position in the old list.
	 */
	static private Map<Integer, Integer> orderContainers(
			List<XPDFTargetComponent> containers) {
		final List<Double> outerRadii = new ArrayList<Double>();
		// Populate a list of outer radii of the containers
		for (XPDFTargetComponent aContainer : containers) {
			outerRadii.add(aContainer.getForm().getGeom().getDistances()[1]);
		}
		// Java offers no way of getting the sorted indices from a Collection, 
		// so we have to do it ourselves
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i<outerRadii.size(); i++) {
			indices.add(i, i);
		}
		indices.sort(new Comparator<Integer>() {
			@Override public int compare(final Integer i1, final Integer i2) {
				return Double.compare(outerRadii.get(i1), outerRadii.get(i2));
			}
		});
		Map<Integer, Integer> newOrder = new HashMap<Integer, Integer>();
		for (int i = 0; i < indices.size(); i++) {
			newOrder.put(i, indices.get(i));
		}

		return newOrder;
	}

	
	private XPDFCalibrationBase getCachedCalibration(IDataset input) {

		DiffractionMetadata currentKeyMetadata = input.getFirstMetadata(DiffractionMetadata.class);
		// Invalidate the cache, if the diffraction metadata on the input has changed
		if (cacheKeyMetadata != null && (
				!cacheKeyMetadata.getDetector2DProperties().equals(currentKeyMetadata.getDetector2DProperties()) ||
				!cacheKeyMetadata.getDiffractionCrystalEnvironment().equals(currentKeyMetadata.getDiffractionCrystalEnvironment())
				)) {
			synchronized (this) {
				if (cacheKeyMetadata != null && (
						!cacheKeyMetadata.getDetector2DProperties().equals(currentKeyMetadata.getDetector2DProperties()) ||
						!cacheKeyMetadata.getDiffractionCrystalEnvironment().equals(currentKeyMetadata.getDiffractionCrystalEnvironment())
						)) {
					cachedCalibration = null;
					cacheKeyMetadata = null;
				}
			}
		}
		
		XPDFCalibrationBase theBase;
		if (cachedCalibration == null) {
		
			theBase = new XPDFCalibrationBase();

			// The initial value of the calibration constant is 1e-16
			theBase.initializeCalibrationConstant(1e-16);


			XPDFMetadata theXPDFMetadata = null;
			// Get the metadata
			theXPDFMetadata = input.getFirstMetadata(XPDFMetadata.class);
			if (theXPDFMetadata == null) throw new OperationException(this, "XPDFMetadata not found.");

			// Sort the containers if requested
			if (model.isSortContainers()) {
				theXPDFMetadata.reorderContainers(orderContainers(theXPDFMetadata.getContainers()));
			}
			// Nullify the absorption map cache if the container sorting setting
			// has been changed.
			if (model.isSortContainers() != isCachedMapsSorted) {
				synchronized (this) {
					if (model.isSortContainers() != isCachedMapsSorted) {
						cachedAbsorptionMaps = null;
						isCachedMapsSorted = model.isSortContainers();
					}
				}
			}

			// Define the geometry of any components defined by their container(s).
			try {
				theXPDFMetadata.defineUndefinedSamplesContainers();
			} catch (Exception e) {
				throw new OperationException(this, "Could not define sample geometry: " + e.toString());
			}

			theBase.setSampleIlluminatedAtoms(theXPDFMetadata.getSampleIlluminatedAtoms());

			// Get 2θ, the axis variable
			if (XPDFCoordinates.coordinateMetadataProblems(DatasetUtils.convertToDataset(input)) != null)
				throw new OperationException(this, XPDFCoordinates.coordinateMetadataProblems(DatasetUtils.convertToDataset(input)));
			XPDFCoordinates coordinates = new XPDFCoordinates(DatasetUtils.convertToDataset(input));
			//		Dataset twoTheta = coordinates.getTwoTheta();

			// Set up the q² integrator class
			XPDFQSquaredIntegrator qSquInt = (input.getFirstMetadata(MaskMetadata.class) != null) ?
					new XPDFQSquaredIntegrator(coordinates, DatasetUtils.convertToDataset(input.getFirstMetadata(MaskMetadata.class).getMask())):
						new XPDFQSquaredIntegrator(coordinates);
			theBase.setqSquaredIntegrator(qSquInt);//twoTheta, theXPDFMetadata.getBeam()));
			theBase.setCoordinates(coordinates);

			theBase.setSelfScattering(theXPDFMetadata.getSample());
			theBase.setSelfScatteringDenominatorFromSample(theXPDFMetadata.getSample(), coordinates);


			// localized cache with a double null check with sprinkles on top
			XPDFAbsorptionMaps localAbsMaps = cachedAbsorptionMaps;
			if (localAbsMaps == null || !(localAbsMaps.checkFormList(theXPDFMetadata.getFormList())) || model.getRegenerateAbsorptionMaps() == true) {
				synchronized (this) {
					localAbsMaps = cachedAbsorptionMaps;
					if (localAbsMaps == null || !(localAbsMaps.checkFormList(theXPDFMetadata.getFormList())) || model.getRegenerateAbsorptionMaps() == true) {
						//					cachedAbsorptionMaps = localAbsMaps = theXPDFMetadata.getAbsorptionMaps(twoTheta.reshape(twoTheta.getSize(), 1), DatasetFactory.zeros(DoubleDataset.class, twoTheta.reshape(twoTheta.getSize(), 1)));
						cachedAbsorptionMaps = localAbsMaps = theXPDFMetadata.getAbsorptionMaps(coordinates.getDelta(), coordinates.getGamma());
					}
				}
			}

			theBase.setBeamData(theXPDFMetadata.getBeam());
			theBase.setDetector(theXPDFMetadata.getDetector());
			//		theCalibration.setAbsorptionMaps(theXPDFMetadata.getAbsorptionMaps(twoTheta.reshape(twoTheta.getSize(), 1), DatasetFactory.zeros(DoubleDataset.class, twoTheta.reshape(twoTheta.getSize(), 1))));
			theBase.setAbsorptionMaps(localAbsMaps);

			// Calculate the sample fluorescence, regardless of whether it is needed.
			theBase.setSampleFluorescence(theXPDFMetadata.getSampleFluorescence(coordinates));

			
			synchronized (this) {
				if (cachedCalibration == null) {
					cachedCalibration = theBase;
					cacheKeyMetadata = currentKeyMetadata;
				}
			}
		} else {
			theBase = cachedCalibration;
		}
		
		return theBase;
		
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantOperation";
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
