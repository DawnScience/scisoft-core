package uk.ac.diamond.scisoft.xpdf.python;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFBeamMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFContainerMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetFormMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetGeometryMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTraceMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

import uk.ac.diamond.scisoft.analysis.processing.python.AbstractPythonScriptOperation;
import uk.ac.diamond.scisoft.analysis.processing.python.OperationToPythonUtils;
import uk.ac.diamond.scisoft.analysis.processing.python.PythonScriptModel;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFBeamMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFContainersMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetAbstractGeometryMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetComponentMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetCylinderMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetFormMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetPlateMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTraceMetadataImpl;

public class PythonXPDFShim extends AbstractPythonScriptOperation<PythonScriptModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.python.PythonXPDFShim";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected Map<String, Object> packInput(IDataset input) {

		// Get the standard data and axes metadata
		Map<String, Object> metadataMap = OperationToPythonUtils.packXY(input);
		// Get the XPDF specific metadata
		metadataMap.put("beamData", makeBeamParams(input));
		metadataMap.put("sample", makeSampleParams(input));
		metadataMap.put("containerData", makeContainerParams(input));
		return metadataMap;
	}

	// Extract the ordered list of containers from the Dataset metadata. Write them to a String, Object map where the keys are their original positions in the list (!) 
	private Map<String, Object> makeContainerParams(IDataset input) {
		Map<String, Object> containerMetaMap = new HashMap<String, Object>();
		XPDFContainerMetadata containersList = null;
		try {
			List<XPDFContainerMetadata> containersListList = input.getMetadata(XPDFContainerMetadata.class);
			if (containersListList != null && !containersListList.isEmpty())
				containersList = containersListList.get(0);
		} catch (Exception e) {
			// Exception? Then keep the list of containers null
		}
		if (containersList != null) {
			for (int i = 0; i < containersList.size(); i++) {
				containerMetaMap.put(String.valueOf(i), makeComponentParams((XPDFTargetComponentMetadata) containersList.getContainer(i)));
			}
		}
		
		return containerMetaMap;
	}

	// Map the metadata of a component, either sample or target, including the form (material and shape) and trace metadata.
	private Map<String, Object> makeComponentParams(XPDFTargetComponentMetadata compMeta) {
		Map<String, Object> compParams = new HashMap<String, Object>();
		compParams.put("name", compMeta.getName());
		compParams.put("form", makeFormParams(compMeta.getForm()));
		compParams.put("trace", makeTraceParams(compMeta.getTrace()));
		compParams.put("isSample", compMeta.isSample());
		return compParams;
	}

	// Map the metadata of a trace
	private Map<String, Object> makeTraceParams(XPDFTraceMetadata traceMeta) {
		Map<String, Object> bgTraceParams = null;
		if (traceMeta != null) {
			bgTraceParams = new HashMap<String, Object>();
			bgTraceParams.put("countingTime", traceMeta.getCountingTime());
			bgTraceParams.put("monitorRelativeFlux", traceMeta.getMonitorRelativeFlux());
			bgTraceParams.put("intensityTrace", traceMeta.getTrace());
		}
		return bgTraceParams;
	}

	// Map the metadata of a beam, including its trace metadata
	private Map<String, Object> makeBeamParams(IDataset input) {
		XPDFBeamMetadata beamMeta = getFirstBeamMetadata(input);
		Map<String, Object> beamParams = null;
		if (beamMeta != null) {
			beamParams = new HashMap<String, Object>();
			beamParams.put("beamEnergy", beamMeta.getBeamEnergy());
			beamParams.put("beamWidth", beamMeta.getBeamWidth());
			beamParams.put("beamHeight", beamMeta.getBeamHeight());
			beamParams.put("bgTrace", makeTraceParams(beamMeta.getTrace()));
		}
		
		return beamParams;
		
	}
	
	// Why is there more than one set of beam metadata? (there isn't, and shouldn't be)
	private XPDFBeamMetadata getFirstBeamMetadata(IDataset input) {
		List<XPDFBeamMetadata> beamMetaList;
		try {
			beamMetaList = input.getMetadata(XPDFBeamMetadata.class);
			if (beamMetaList == null || beamMetaList.isEmpty())
				return null;
		} catch (Exception e) {
			return null;
		}
		return beamMetaList.get(0);
	}

	// Find the first (there should only be one) component metadata in the root of the Dataset metadata. This is the component metadata of the sample. Map it.
	private Map<String, Object> makeSampleParams(IDataset input) {
		Map<String, Object> sampleParams = null;
		List<XPDFTargetComponentMetadataImpl> sampleMetaList;
		// There should only be one lot of sample metadata. Take the first.
		try {
			sampleMetaList = input.getMetadata(XPDFTargetComponentMetadataImpl.class);
			if (sampleMetaList == null || sampleMetaList.isEmpty())
				return null;
		} catch (Exception e) {
			return null;
		}
		if (sampleMetaList.get(0) != null) {
			sampleParams = makeComponentParams(sampleMetaList.get(0));
		}
		return sampleParams;
		
	}
	
	// Map the form metadata, including the geometry metadata
	private Map<String, Object> makeFormParams(XPDFTargetFormMetadata form) {
		Map<String, Object> formParams = new HashMap<String, Object>();
		formParams.put("material", form.getMaterialName());
		formParams.put("density", form.getDensity());
		formParams.put("volumeFraction", form.getPackingFraction());
		formParams.put("geometry", makeGeomParams(form.getGeometry()));
		return formParams;
	}
	
	// Map the geometry metadata
	private Map<String, Object> makeGeomParams(XPDFTargetGeometryMetadata geometry) {
		Map<String, Object> geomParams = new HashMap<String, Object>();
		geomParams.put("shape", geometry.getShape());
		geomParams.put("distances", geometry.getDistances());
		geomParams.put("streamality", geometry.getStreamality());
		return geomParams;
	}

	@Override
	protected OperationData packAndValidateMap(Map<String, Object> output) {
		IDataset pyData = OperationToPythonUtils.unpackXY(output).getData();
		// Assume the data out is in the format we expect. This should only
		// interface with its related python script. Unchecked casts abound.
		
		pyData.setMetadata(makeBeamMetadata((Map<String, Object>) output.get("beamData")));
		pyData.setMetadata(makeSampleMetadata((Map<String, Object>) output.get("sample")));
		pyData.setMetadata(makeContainersMetadata((Map<String, Object>) output.get("containerData")));

		return new OperationData(pyData);
	}

	private XPDFContainerMetadata makeContainersMetadata(Map<String, Object> outputContainers) {
		XPDFContainerMetadata containerList = new XPDFContainersMetadataImpl();
		for (int i = 0; !outputContainers.isEmpty(); i++) { 
			String iStr = Integer.toString(i);
			if (outputContainers.containsKey(iStr)) {
				containerList.addContainer(makeContainerMetadata(
							(Map<String, Object>) outputContainers.get(iStr)));
				outputContainers.remove(iStr);
			}
		}
		return containerList;
	}

	private XPDFTargetComponentMetadata makeContainerMetadata(Map<String, Object> outputContainer) {
		XPDFTargetComponentMetadataImpl compMeta = new XPDFTargetComponentMetadataImpl();

		compMeta.setForm(makeFormMetadata((Map<String, Object>) outputContainer.get("form")));
		compMeta.setName((String) outputContainer.get("name"));
		compMeta.setTrace(makeTraceMetadata((Map<String, Object>) outputContainer.get("trace")));		
		compMeta.setSample(false);

		return compMeta;
	}

	private XPDFTargetComponentMetadata makeSampleMetadata(Map<String, Object> outputSample) {
		XPDFTargetComponentMetadataImpl compMeta = new XPDFTargetComponentMetadataImpl();

		compMeta.setForm(makeFormMetadata((Map<String, Object>) outputSample.get("form")));
		compMeta.setName((String) outputSample.get("name"));
		compMeta.setTrace(makeTraceMetadata((Map<String, Object>) outputSample.get("trace")));		
		compMeta.setSample(true);

		return compMeta;
	}

	private XPDFTargetFormMetadata makeFormMetadata(Map<String, Object> outputForm) {
		XPDFTargetFormMetadataImpl formMeta = new XPDFTargetFormMetadataImpl();
		
		formMeta.setMaterialName((String) outputForm.get("material"));
		formMeta.setDensity((double) outputForm.get("density"));
		formMeta.setPackingFraction((double) outputForm.get("volumeFraction"));
		formMeta.setGeometry(makeGeomMetadata((Map<String, Object>) outputForm.get("geometry")));
		
		return formMeta;
	}

	private XPDFTargetGeometryMetadata makeGeomMetadata(Map<String, Object> outputGeom) {
		XPDFTargetAbstractGeometryMetadataImpl geomMeta = null;

		// Read shape from the Model
		String shape = (String) outputGeom.get("shape");

		if (shape.equals("cylinder")) {
			geomMeta = new XPDFTargetCylinderMetadataImpl();
		} else if (shape.equals("plate")) {
			geomMeta = new XPDFTargetPlateMetadataImpl();
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

	private XPDFBeamMetadata makeBeamMetadata(Map<String, Object> outputBeam) {
		XPDFBeamMetadataImpl beamMetadata = new XPDFBeamMetadataImpl();
		
		beamMetadata.setBeamEnergy((double) outputBeam.get("beamEnergy"));
		beamMetadata.setBeamHeight((double) outputBeam.get("beamHeight"));
		beamMetadata.setBeamWidth((double) outputBeam.get("beamWidth"));
		beamMetadata.setTrace(makeTraceMetadata( (Map<String, Object>) outputBeam.get("bgTrace")));

		return beamMetadata;
	}

	private XPDFTraceMetadata makeTraceMetadata(Map<String, Object> outputTrace) {
		XPDFTraceMetadataImpl bgMetadata = new XPDFTraceMetadataImpl();
		
		bgMetadata.setCountingTime((double) outputTrace.get("countingTime"));
		bgMetadata.setMonitorRelativeFlux((double) outputTrace.get("monitorRelativeFlux"));
		bgMetadata.setTrace((IDataset) outputTrace.get("intensityTrace"));

		return bgMetadata;
	}

}
