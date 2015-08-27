package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

@SuppressWarnings("serial")
public class XPDFMetadataImpl implements XPDFMetadata {

	XPDFTargetComponent sampleData;
	List<XPDFTargetComponent> containerData;
	XPDFBeamData beamData;
	
	
	public XPDFMetadataImpl() {
		sampleData = null;
		containerData = new ArrayList<XPDFTargetComponent>();
		beamData = null;
	}
	
	public XPDFMetadataImpl(XPDFMetadataImpl inMeta) {
		this.sampleData = (inMeta.sampleData != null) ? new XPDFTargetComponent(inMeta.sampleData) : null;
		
		this.containerData = new ArrayList<XPDFTargetComponent>();
		for (XPDFTargetComponent container : inMeta.containerData)
			if (container != null) this.containerData.add(new XPDFTargetComponent(container));
		
		this.beamData = (inMeta.beamData != null) ? new XPDFBeamData(inMeta.beamData) : null;
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
	

}
