package uk.ac.diamond.scisoft.xpdf.python;

import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

import uk.ac.diamond.scisoft.analysis.processing.python.AbstractPythonScriptOperation;
import uk.ac.diamond.scisoft.analysis.processing.python.OperationToPythonUtils;
import uk.ac.diamond.scisoft.analysis.processing.python.PythonScriptModel;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

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
		Map<String, Object> theXPDFMetadataMap = null;
		try {
			List<XPDFMetadata> listOfXPDFMetadata = input.getMetadata(XPDFMetadata.class);
			XPDFMetadata theXPDFMetadata = null;
			if (listOfXPDFMetadata != null && !listOfXPDFMetadata.isEmpty() )
				theXPDFMetadata = listOfXPDFMetadata.get(0);
			if (theXPDFMetadata != null)
				theXPDFMetadataMap = theXPDFMetadata.unpackToPython();
		} catch (Exception e) {
			// Exception? Then add nothing
		}
		if (theXPDFMetadataMap != null)
			metadataMap.putAll(theXPDFMetadataMap);

		return metadataMap;
	}	

	@Override
	protected OperationData packAndValidateMap(Map<String, Object> output) {
		IDataset pyData = OperationToPythonUtils.unpackXY(output).getData();
		// Assume the data out is in the format we expect. This should only
		// interface with its related python script. Unchecked casts abound.
		
		XPDFMetadata theXPDFMetadata = new XPDFMetadataImpl();
		theXPDFMetadata.packFromPython(output);
		pyData.setMetadata(theXPDFMetadata);

		return new OperationData(pyData);
	}

}
