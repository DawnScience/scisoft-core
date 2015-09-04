package uk.ac.diamond.scisoft.xpdf;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

@SuppressWarnings("serial")
public class XPDFMetadataImpl implements XPDFMetadata {

	XPDFTargetComponent sampleData;
	List<XPDFTargetComponent> containerData;
	XPDFBeamData beamData;
	XPDFAbsorptionMaps absorptionCorrectionMaps;

	
	public XPDFMetadataImpl() {
		sampleData = null;
		containerData = new ArrayList<XPDFTargetComponent>();
		beamData = null;
		absorptionCorrectionMaps = null;
	}
	
	public XPDFMetadataImpl(XPDFMetadataImpl inMeta) {
		this.sampleData = (inMeta.sampleData != null) ? new XPDFTargetComponent(inMeta.sampleData) : null;
		
		this.containerData = new ArrayList<XPDFTargetComponent>();
		for (XPDFTargetComponent container : inMeta.containerData)
			if (container != null) this.containerData.add(new XPDFTargetComponent(container));
		
		this.beamData = (inMeta.beamData != null) ? new XPDFBeamData(inMeta.beamData) : null;
		this.absorptionCorrectionMaps = (inMeta.absorptionCorrectionMaps != null) ? new XPDFAbsorptionMaps(inMeta.absorptionCorrectionMaps) : null;
	}
	
	@Override
	public MetadataType clone() {
		return new XPDFMetadataImpl(this);		
	}

	@Override
	public void reorderContainers(Map<Integer, Integer> newOrder) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<XPDFTargetComponent> getContainers() {
		return containerData;
	}

	@Override
	public XPDFTargetComponent getSample() {
		return sampleData;
	}

	@Override
	public XPDFBeamData getBeam() {
		return beamData;
	}

	public XPDFTargetComponent getSampleData() {
		return sampleData;
	}

	public void setSampleData(XPDFTargetComponent sampleData) {
		this.sampleData = sampleData;
	}

	public List<XPDFTargetComponent> getContainerData() {
		return containerData;
	}

	public void setContainerData(List<XPDFTargetComponent> containerData) {
		this.containerData = containerData;
	}

	public void setBeamData(XPDFBeamData beamData) {
		this.beamData = beamData;
	}

	public void addContainer(XPDFTargetComponent newContainer) {
		if (containerData == null)
			containerData = new ArrayList<XPDFTargetComponent>();
		this.containerData.add(newContainer);
	}

	@Override
	public double getSampleIlluminatedAtoms() {
		return getSample().getForm().getIlluminatedAtoms(beamData);
	}

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
