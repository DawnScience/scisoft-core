package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Implementation of the XPDF metadata.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 */
@SuppressWarnings("serial")
public class XPDFMetadataImpl implements XPDFMetadata {

	private XPDFTargetComponent sampleData;
	private List<XPDFTargetComponent> containerData;
	private XPDFBeamData beamData;
	private XPDFAbsorptionMaps absorptionCorrectionMaps;
	private XPDFDetector tect;
	
	/**
	 * Empty constructor.
	 */
	public XPDFMetadataImpl() {
		sampleData = null;
		containerData = new ArrayList<XPDFTargetComponent>();
		beamData = null;
		absorptionCorrectionMaps = null;
		tect = null;
	}
	
	/**
	 * Copy constructor.
	 * @param inMeta
	 * 				metadata object to be copied.
	 */
	public XPDFMetadataImpl(XPDFMetadataImpl inMeta) {
		this.sampleData = (inMeta.sampleData != null) ? new XPDFTargetComponent(inMeta.sampleData) : null;
		
		this.containerData = new ArrayList<XPDFTargetComponent>();
		for (XPDFTargetComponent container : inMeta.containerData)
			if (container != null) this.containerData.add(new XPDFTargetComponent(container));
		
		this.beamData = (inMeta.beamData != null) ? new XPDFBeamData(inMeta.beamData) : null;
		this.absorptionCorrectionMaps = (inMeta.absorptionCorrectionMaps != null) ? new XPDFAbsorptionMaps(inMeta.absorptionCorrectionMaps) : null;
		this.tect = (inMeta.tect != null) ? new XPDFDetector(inMeta.tect) : null;
	}
	
	/**
	 * Clone method.
	 */
	@Override
	public MetadataType clone() {
		return new XPDFMetadataImpl(this);		
	}

	/**
	 * Re-orders the containers, from the sample outwards. 
	 */
	@Override
	public void reorderContainers(Map<Integer, Integer> newOrder) {
		// iterate over the keys in order
		int nKeys = newOrder.size();
		List <XPDFTargetComponent> newContainerData = new ArrayList<XPDFTargetComponent>();
		for (int iKey = 0; iKey < nKeys; iKey++) {
			newContainerData.add(iKey, this.containerData.get(newOrder.get(iKey)));
		}
		this.containerData = newContainerData;
	}

	/**
	 * Gets the containers. 
	 */
	@Override
	public List<XPDFTargetComponent> getContainers() {
		return containerData;
	}

	/**
	 *  Gets the sample.
	 */
	@Override
	public XPDFTargetComponent getSample() {
		return sampleData;
	}

	/**
	 * Getter for the beam data.
	 */
	@Override
	public XPDFBeamData getBeam() {
		return beamData;
	}

	/**
	 * Getter for the sample properties.
	 * @return
	 */
	public XPDFTargetComponent getSampleData() {
		return sampleData;
	}

	/**
	 * Setter for the sample properties.
	 * @param sampleData
	 * 					sample properties to be set.
	 */
	public void setSampleData(XPDFTargetComponent sampleData) {
		this.sampleData = sampleData;
	}

	/**
	 * Getter for all the container data.
	 * @return a list of all the containers's properties.
	 */
	public List<XPDFTargetComponent> getContainerData() {
		return containerData;
	}

	/**
	 * Setter for all the container data
	 * @param containerData
	 * 					list of all the containers's properties.
	 */
	public void setContainerData(List<XPDFTargetComponent> containerData) {
		this.containerData = containerData;
	}

	/**
	 * Setter for the beam data.
	 * @param beamData
	 * 				the beam data to be set.
	 */
	public void setBeamData(XPDFBeamData beamData) {
		this.beamData = beamData;
	}

	/**
	 * Adds a container to the container list.
	 * <p>
	 * Add a single container to the end of the container list. If the list is
	 * sorted, this is the outside of the (ordered) set of containers.
	 * @param newContainer
	 * 					container properies to be added.
	 */
	public void addContainer(XPDFTargetComponent newContainer) {
		if (containerData == null)
			containerData = new ArrayList<XPDFTargetComponent>();
		this.containerData.add(newContainer);
	}

	/**
	 * Gets the number of illuminated sample atoms.
	 * <p>
	 * A pass through function to return the number of atoms in the sample
	 * illuminated by the X-ray beam.
	 */
	@Override
	public double getSampleIlluminatedAtoms() {
		return getSample().getForm().getIlluminatedAtoms(beamData);
	}

	/**
	 * Calculates and returns the absorption maps for the target components, as
	 * ordered in the list of containers, with the sample appended in position 0.  
	 */
	@Override
	public XPDFAbsorptionMaps getAbsorptionMaps(Dataset delta, Dataset gamma) {
		// The intention is to be able to recall cached maps, if these are set 
		// in the metadata. They are not, yet, so press on with the direct
		// calculations. The map uses a pair of integers in a string as an
		// index, based on the order in which they come in the list of components 
		if (absorptionCorrectionMaps == null) {
		
			absorptionCorrectionMaps = new XPDFAbsorptionMaps();

			absorptionCorrectionMaps.setGamma(gamma);
			absorptionCorrectionMaps.setDelta(delta);
			absorptionCorrectionMaps.setBeamData(beamData);
			// Add the target component forms, starting with the sample
//			List<XPDFComponentForm> formsList = new ArrayList<XPDFComponentForm>();
//			formsList.add(sampleData.getForm());
//			for (XPDFTargetComponent container : containerData)
//				formsList.add(container.getForm());
//
//			for (XPDFComponentForm form : formsList) {
//				absorptionCorrectionMaps.addForm(form);
//			}
//
//			
			
//			for (XPDFComponentForm formScatterer : formsList) {
//				for (XPDFComponentForm formAttenuator : formsList) {
//					absorptionCorrectionMaps.setAbsorptionMap(formScatterer, formAttenuator,
//							formScatterer.getGeom().calculateAbsorptionCorrections(gamma, delta, formAttenuator.getGeom(), formAttenuator.getAttenuationCoefficient(beamData.getBeamEnergy()), beamData, true, true));
//				}
//			}
			
//			absorptionCorrectionMaps.addForm(sampleData.getForm());
//			for (XPDFTargetComponent container : containerData)
//				absorptionCorrectionMaps.addForm(container.getForm());
			for (XPDFComponentForm targetForm : this.getFormList())
				absorptionCorrectionMaps.addForm(new XPDFComponentForm(targetForm));
			
			
			absorptionCorrectionMaps.calculateAbsorptionMaps();			
			
			// Note the less than or equal to
//			for (int iScatterer = 0; iScatterer <= containerData.size(); iScatterer++) {
//				XPDFTargetComponent scatterer = (iScatterer == 0) ? sampleData : containerData.get(iScatterer-1);
//
//				for (int iAttenuator = 0; iAttenuator <= containerData.size(); iAttenuator++) {
//					XPDFTargetComponent attenuator = (iAttenuator == 0) ? sampleData : containerData.get(iAttenuator-1);
//					absorptionCorrectionMaps.setAbsorptionMap(iScatterer, iAttenuator, 
//							scatterer.getForm().getGeom().calculateAbsorptionCorrections(gamma, delta, attenuator.getForm().getGeom(), attenuator.getForm().getAttenuationCoefficient(beamData.getBeamEnergy()), beamData, true, true));
//				}
//			}

		
		
		}		
		return absorptionCorrectionMaps;
	}

	@Override
	public List<XPDFComponentForm> getFormList() {
		List<XPDFComponentForm> formList = new ArrayList<XPDFComponentForm>();
		formList.add(sampleData.getForm());
		for (XPDFTargetComponent container : containerData)
			formList.add(container.getForm());
		return formList;
	}

	/**
	 * Setter for the detector object.
	 * @param inTect
	 * 				the detector to be assigned.
	 */
	public void setDetector(XPDFDetector inTect) {
		tect = inTect;
	}
	
	@Override
	public XPDFDetector getDetector() {
		return tect;
	}

	@Override
	public Dataset getSampleFluorescence(Dataset gamma, Dataset delta) {
		Dataset totalSampleFluorescence = DoubleDataset.zeros(gamma);
		
		List<Double> fluorescentEnergies = new ArrayList<Double>();
		List<Double> fluorescentXSections = new ArrayList<Double>();
		List<Integer> fluorescentAtomicNumbers = new ArrayList<Integer>();
		// TODO: xraylib fluorescence lines
		// Beginning of hard-coded data
		String chemicalFormula = sampleData.getForm().getSubstance().getMaterialName();
		if (chemicalFormula.equals("CeO2")) {
			fluorescentEnergies = Arrays.asList(34.7196, 34.2788, 39.2576, 4.8401, 5.2629);
			fluorescentXSections = Arrays.asList(444.94994401, 243.00829748, 87.69485581, 48.11886857, 28.32794357);
			fluorescentAtomicNumbers = Arrays.asList(58, 58, 58, 58, 58);
		} else if (chemicalFormula.equals("BaTiO2")) {
			fluorescentEnergies = Arrays.asList(32.1936, 31.817, 36.3784, 4.4663, 4.8275);
			fluorescentXSections = Arrays.asList(388.27213844, 210.42217958, 75.39646264, 37.42569601, 21.92040699);
			fluorescentAtomicNumbers = Arrays.asList(56, 56, 56, 56, 56);
		} else if (chemicalFormula.equals("W")) {
			fluorescentEnergies = Arrays.asList(59.3182, 57.981, 67.244, 8.3976, 9.6724);
			fluorescentXSections = Arrays.asList(1019.37506384, 586.69404138, 219.28240133, 239.26301778, 156.7324558);
			fluorescentAtomicNumbers = Arrays.asList(74, 74, 74, 74, 74);
		} else if (chemicalFormula.equals("Ni")) {
			fluorescentEnergies = Arrays.asList(7.4781);
			fluorescentXSections = Arrays.asList(12.68440462);
			fluorescentAtomicNumbers = Arrays.asList(28);
		} else {
			fluorescentEnergies.clear();
			fluorescentXSections.clear();
		}
		
		// End of hard-coded data
		
		
		
		XPDFCoordinates coords = new XPDFCoordinates();
		coords.setGammaDelta(gamma, delta);
		
		for (int iFluor = 0; iFluor < fluorescentEnergies.size(); iFluor++) {
			List<XPDFComponentGeometry> attenuators = new ArrayList<XPDFComponentGeometry>();
			List<Double> attenuationsIn = new ArrayList<Double>(),
					attenuationsOut = new ArrayList<Double>();
			for (XPDFComponentForm componentForm : this.getFormList()) {
				attenuators.add(componentForm.getGeom());
				attenuationsIn.add(componentForm.getSubstance().getAttenuationCoefficient(beamData.getBeamEnergy()));
				attenuationsOut.add(componentForm.getSubstance().getAttenuationCoefficient(fluorescentEnergies.get(iFluor)));
			}
			Dataset oneLineFluorescence = sampleData.getForm().getGeom().calculateFluorescence(gamma, delta, attenuators, attenuationsIn, attenuationsOut, beamData, true, true);
			double lineXSection = fluorescentXSections.get(iFluor);
			double lineNumberDensity = sampleData.getNumberDensity(fluorescentAtomicNumbers.get(iFluor));
//			oneLineFluorescence.imultiply(fluorescentXSections.get(iFluor)*sampleData.getNumberDensity(fluorescentAtomicNumbers.get(iFluor)));
			oneLineFluorescence.imultiply(lineXSection * lineNumberDensity);
			Dataset detectorCorrectedOLF = tect.applyTransmissionCorrection(oneLineFluorescence, coords.getTwoTheta(), fluorescentEnergies.get(iFluor));
//			totalSampleFluorescence.iadd(tect.applyTransmissionCorrection(oneLineFluorescence, coords.getTwoTheta(), beamData.getBeamEnergy()));
			totalSampleFluorescence.iadd(detectorCorrectedOLF);
		}
		totalSampleFluorescence.imultiply(tect.getSolidAngle());
		return totalSampleFluorescence.squeeze();
	}
	

}
