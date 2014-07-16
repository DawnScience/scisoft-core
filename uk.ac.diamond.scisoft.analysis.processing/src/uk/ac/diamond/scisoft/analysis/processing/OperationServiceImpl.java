package uk.ac.diamond.scisoft.analysis.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class OperationServiceImpl implements IOperationService {
	
	static {
		System.out.println("Starting operation service");
	}
	private Map<String, IOperation> operations;
	
	public OperationServiceImpl() {
		// Intentionally do nothing
	}

	@Override
	public IRichDataset executeSeries(IOperation... series) throws OperationException {

		IRichDataset value = series[0].execute();
		if (series.length>1) for (int i = 1; i < series.length; i++) {
			series[i].setData(value);
			value = series[i].execute();
		}
		return value;
	}

	// Reads the declared operations from extension point, if they have not been already.
	private synchronized void checkOperations() throws CoreException {
		if (operations!=null) return;
		
		operations = new HashMap<String, IOperation>(31);
		IConfigurationElement[] eles = Platform.getExtensionRegistry().getConfigurationElementsFor("uk.ac.diamond.scisoft.analysis.api.operation");
		for (IConfigurationElement e : eles) {
			final String     id = e.getAttribute("id");
			final IOperation op = (IOperation)e.createExecutableExtension("class");
			operations.put(id, op);
		}
	}

	@Override
	public Collection<IOperation> find(String regex) throws Exception {
		checkOperations();
		
		Collection<IOperation> ret = new ArrayList<IOperation>(3);
		for (String id : operations.keySet()) {
			if (id.matches(regex) || id.toLowerCase().matches(regex)) {
				ret.add(operations.get(id));
			}
		}
		return ret;
	}

	@Override
	public Collection<String> getRegisteredOperations() throws Exception {
		checkOperations();
		return operations.keySet();
	}

	@Override
	public IOperation create(String operationId) throws Exception {
		checkOperations();
		return operations.get(operationId).getClass().newInstance();
	}

}
