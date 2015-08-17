package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;

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
	
//*****************************************************************************
// Python interface: prepare to pass to python
	
	@Override
	public Map<String, Object> unpackToPython() {
		Map<String, Object> metadataMap = new HashMap<String, Object>();
		metadataMap.put("sample", makeComponentParams(sampleData));
		metadataMap.put("containerData", makeContainersParams(containerData));
		metadataMap.put("beamData", makeBeamParams(beamData));		
		return metadataMap;
	}

	// Map of the containers. Key is the index, value is the TargetComponent map
	Map<String, Object> makeContainersParams(List<XPDFTargetComponent> containerList) {
		Map<String, Object> containerMap = new HashMap<String, Object>();
		int iContainer = 0;
		for (XPDFTargetComponent theXPDFTargetComponent : containerList) {
			containerMap.put(Integer.toString(iContainer), makeComponentParams(theXPDFTargetComponent));
		}
		return containerMap;		
	}
	
	// Map the metadata of a component, either sample or target, including the form (material and shape) and trace metadata.
	private Map<String, Object> makeComponentParams(XPDFTargetComponent comp) {
		Map<String, Object> compParams = new HashMap<String, Object>();
		compParams.put("name", comp.getName());
		compParams.put("form", makeFormParams(comp.getForm()));
		compParams.put("trace", makeTraceParams(comp.getTrace()));
		compParams.put("isSample", comp.isSample());
		return compParams;
	}
	
	// Map the metadata of a beam, including its trace metadata
	private Map<String, Object> makeBeamParams(XPDFBeamData beamData) {
		Map<String, Object> beamParams = null;
		beamParams = new HashMap<String, Object>();
		beamParams.put("beamEnergy", beamData.getBeamEnergy());
		beamParams.put("beamWidth", beamData.getBeamWidth());
		beamParams.put("beamHeight", beamData.getBeamHeight());
		beamParams.put("bgTrace", makeTraceParams(beamData.getBeamBGTrace()));
		
		return beamParams;
		
	}
	// Map the metadata of a trace
	private Map<String, Object> makeTraceParams(XPDFBeamTrace trace) {
		Map<String, Object> bgTraceParams = null;
		if (trace != null) {
			bgTraceParams = new HashMap<String, Object>();
			bgTraceParams.put("countingTime", trace.getCountingTime());
			bgTraceParams.put("monitorRelativeFlux", trace.getMonitorRelativeFlux());
			// Samples have a null trace, since the trace data is the principal dataset
//			if (trace.getTrace() != null) bgTraceParams.put("intensityTrace", trace.getTrace());
			bgTraceParams.put("intensityTrace", (trace.getTrace() != null) ? trace.getTrace() : null);
		}
		return bgTraceParams;
	}

	// Map the form metadata, including the geometry metadata
	private Map<String, Object> makeFormParams(XPDFComponentForm form) {
		Map<String, Object> formParams = new HashMap<String, Object>();
		formParams.put("material", form.getMatName());
		formParams.put("density", form.getDensity());
		formParams.put("volumeFraction", form.getPackingFraction());
		formParams.put("geometry", makeGeomParams(form.getGeom()));
		return formParams;
	}
	
	// Map the geometry metadata
	private Map<String, Object> makeGeomParams(XPDFComponentGeometry geometry) {
		Map<String, Object> geomParams = new HashMap<String, Object>();
		geomParams.put("shape", geometry.getShape());
		geomParams.put("distances", geometry.getDistances());
		geomParams.put("streamality", geometry.getStreamality());
		return geomParams;
	}

// Python interface get data back from python	
	
	@SuppressWarnings("unchecked")
	@Override
	public void packFromPython(Map<String, Object> pyMap) {
		sampleData = this.makeSampleMetadata((Map<String, Object>) pyMap.get("sample"));
		containerData = this.makeContainersMetadata((Map<String, Object>) pyMap.get("containerData"));
		beamData = this.makeBeamMetadata((Map<String, Object>) pyMap.get("beamData"));
	}

	@SuppressWarnings("unchecked")
	private List<XPDFTargetComponent> makeContainersMetadata(Map<String, Object> outputContainers) {
		List<XPDFTargetComponent> containerList = new ArrayList<XPDFTargetComponent>();
		for (int i = 0; !outputContainers.isEmpty(); i++){
			String iStr = Integer.toString(i);
			if (outputContainers.containsKey(iStr)) {
				containerList.add(makeContainerMetadata( (Map<String, Object>) outputContainers.get(iStr)));
				outputContainers.remove(iStr);
			}
		}
		return containerList;
	}

	private XPDFTargetComponent makeContainerMetadata(Map<String, Object> outputContainer) {
		XPDFTargetComponent compMeta = makeTargetComponent(outputContainer);

		compMeta.setSample(false);

		return compMeta;
	}

	private XPDFTargetComponent makeSampleMetadata(Map<String, Object> outputSample) {
		XPDFTargetComponent compMeta = makeTargetComponent(outputSample);

		compMeta.setSample(true);

		return compMeta;
	}

	@SuppressWarnings("unchecked")
	private XPDFTargetComponent makeTargetComponent(Map<String, Object> outputComponent) {
		XPDFTargetComponent compMeta = new XPDFTargetComponent();

		compMeta.setForm(makeFormMetadata((Map<String, Object>) outputComponent.get("form")));
		compMeta.setName((String) outputComponent.get("name"));
		compMeta.setTrace(makeTraceMetadata((Map<String, Object>) outputComponent.get("trace")));		
		return compMeta;
	}

	@SuppressWarnings("unchecked")
	private XPDFComponentForm makeFormMetadata(Map<String, Object> outputForm) {
		XPDFComponentForm formMeta = new XPDFComponentForm();
		
		formMeta.setMatName((String) outputForm.get("material"));
		formMeta.setDensity((double) outputForm.get("density"));
		formMeta.setPackingFraction((double) outputForm.get("volumeFraction"));
		formMeta.setGeom(makeGeomMetadata((Map<String, Object>) outputForm.get("geometry")));
		
		return formMeta;
	}

	private XPDFComponentGeometry makeGeomMetadata(Map<String, Object> outputGeom) {
		XPDFComponentGeometry geomMeta = null;

		// Read shape from the Model
		String shape = (String) outputGeom.get("shape");

		if (shape.equals("cylinder")) {
			geomMeta = new XPDFComponentCylinder();
		} else if (shape.equals("plate")) {
			geomMeta = new XPDFComponentPlate();
		}

		double[] distances = (double[]) outputGeom.get("distances");
		double inner = distances[0];
		double outer = distances[1];
		geomMeta.setDistances(inner, outer);

		boolean[] streamality = (boolean[]) outputGeom.get("streamality");
		boolean is_up = streamality[0], is_down = streamality[1];
		
		// samples have [false, false] streamality
		geomMeta.setStreamality(is_up, is_down);

		return geomMeta;
	}

	@SuppressWarnings("unchecked")
	private XPDFBeamData makeBeamMetadata(Map<String, Object> outputBeam) {
		XPDFBeamData beamMetadata = new XPDFBeamData();
		
		beamMetadata.setBeamEnergy((double) outputBeam.get("beamEnergy"));
		beamMetadata.setBeamHeight((double) outputBeam.get("beamHeight"));
		beamMetadata.setBeamWidth((double) outputBeam.get("beamWidth"));
		beamMetadata.setBeamBGTrace(makeTraceMetadata((Map<String, Object>) outputBeam.get("bgTrace")));

		return beamMetadata;
	}

	private XPDFBeamTrace makeTraceMetadata(Map<String, Object> outputTrace) {
		XPDFBeamTrace bgMetadata = new XPDFBeamTrace();
		
		bgMetadata.setCountingTime((double) outputTrace.get("countingTime"));
		bgMetadata.setMonitorRelativeFlux((double) outputTrace.get("monitorRelativeFlux"));
		bgMetadata.setTrace((IDataset) outputTrace.get("intensityTrace"));

		return bgMetadata;
	}

}
