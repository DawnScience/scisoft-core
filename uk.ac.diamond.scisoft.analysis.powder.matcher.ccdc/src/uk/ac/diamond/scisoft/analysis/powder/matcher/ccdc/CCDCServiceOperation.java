package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.python.AbstractPythonScriptOperation;
import uk.ac.diamond.scisoft.analysis.processing.python.OperationToPythonUtils;
import uk.ac.diamond.scisoft.analysis.processing.python.PythonScriptModel;

public class CCDCServiceOperation extends AbstractPythonScriptOperation<PythonScriptModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc.CCDCServiceOperation";
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
	protected Map<String, Object> packInput(IDataset input) {
		return packCCDCConfigData(input);
	}

	

	public static Map<String, Object> packCCDCConfigData(IDataset input) {
		return null;
	}
	
	
	//TODO: each one of these is the data extract from a run
	public static Map<String, Object> unpackCCDCConfigData(Map<String, Object> output) {
		return null;
	}
	
	@Override
	protected OperationData packAndValidateMap(Map<String, Object> output) {
		return (OperationData) CCDCServiceOperation.unpackCCDCConfigData(output);
	}

}
