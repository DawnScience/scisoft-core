package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Implementation of the XPDF metadata.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 */
@SuppressWarnings("serial")
public class XPDFMetadataImpl implements XPDFMetadata {

	XPDFTargetComponent sampleData;
	List<XPDFTargetComponent> containerData;
	XPDFBeamData beamData;
	XPDFAbsorptionMaps absorptionCorrectionMaps;

	/**
	 * Empty constructor.
	 */
	public XPDFMetadataImpl() {
		sampleData = null;
		containerData = new ArrayList<XPDFTargetComponent>();
		beamData = null;
		absorptionCorrectionMaps = null;
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
	public XPDFAbsorptionMaps getAbsorptionMaps(
			Dataset delta, Dataset gamma) {
		// The intention is to be able to recall cached maps, if these are set 
		// in the metadata. They are not, yet, so press on with the direct
		// calculations. The map uses a pair of integers in a string as an
		// index, based on the order in which they come in the list of components 
		if (absorptionCorrectionMaps == null) {
		
			absorptionCorrectionMaps = new XPDFAbsorptionMaps();

			// Note the less than or equal to
			for (int iScatterer = 0; iScatterer <= containerData.size(); iScatterer++) {
				XPDFTargetComponent scatterer = (iScatterer == 0) ? sampleData : containerData.get(iScatterer-1);

				for (int iAttenuator = 0; iAttenuator <= containerData.size(); iAttenuator++) {
					XPDFTargetComponent attenuator = (iAttenuator == 0) ? sampleData : containerData.get(iAttenuator-1);
					absorptionCorrectionMaps.setAbsorptionMap(iScatterer, iAttenuator, 
							scatterer.getForm().getGeom().calculateAbsorptionCorrections(gamma, delta, attenuator.getForm().getGeom(), attenuator.getForm().getAttenuationCoefficient(beamData.getBeamEnergy()), beamData, true, true));
				}
			}
		}		
		return absorptionCorrectionMaps;
	}
	

}
