package uk.ac.diamond.scisoft.analysis.processing;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.dataset.SliceVisitor;
import uk.ac.diamond.scisoft.analysis.dataset.Slicer;

/**
 * Do not use this class externally. Instead get the IOperationService
 * from OSGI.
 * 
 * @author fcp94556
 *
 */
public class OperationServiceImpl implements IOperationService {
	
	static {
		System.out.println("Starting operation service");
	}
	private Map<String, IOperation> operations;
	
	public OperationServiceImpl() {
		// Intentionally do nothing
	}

	/**
	 * Uses the Slicer.visitAll(...) method which conversions also use to process
	 * stacks out of the rich dataset passed in.
	 */
	@Override
	public void executeSeries(final IRichDataset dataset, final IExecutionVisitor visitor, final IOperation... series) throws OperationException {

		series[0].setDataset(dataset);
		
		Map<Integer, String> slicing = dataset.getSlicing();
		if (slicing==null) slicing = Collections.emptyMap();
				
		// Jakes slicing from the conversion tool.
		try {
			
			Slicer.visitAll(dataset.getData(), slicing, "Slice", new SliceVisitor() {

				@Override
				public void visit(IDataset slice, Slice... slices) throws Exception {
			        
					boolean required = visitor.isRequired(slice, series);
					if (!required) return;
					
					for (IOperation i : series) slice = i.execute(slice);
					
					visitor.executed(slice);
				}
			});
			
		} catch (OperationException o) {
			throw o;
		} catch (Exception e) {
			throw new OperationException(null, e.getMessage());
		}
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
			IOperation operation = operations.get(id);
			if (matches(id, operation, regex)) {
				ret.add(operation);
			}
		}
		return ret;
	}
	
	@Override
	public IOperation findFirst(String regex) throws Exception {
		checkOperations();
		
		for (String id : operations.keySet()) {
			IOperation operation = operations.get(id);
			if (matches(id, operation, regex)) {
				return operation;
			}
		}
		return null;
	}

     /**
	 * NOTE the regex will be matched as follows on the id of the operation:
	 * 1. if matching on the id
	 * 2. if matching the description in lower case.
	 * 3. if indexOf the regex in the id is >0
	 * 4. if indexOf the regex in the description is >0
	 */
	private boolean matches(String id, IOperation operation, String regex) {
		if (id.matches(regex)) return true;
		final String description = operation.getOperationDescription();
		if (description.matches(regex)) return true;
		if (id.indexOf(regex)>0) return true;
		if (description.indexOf(regex)>0) return true;
		return false;
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

	@Override
	public void createOperations(ClassLoader cl, String pakage) throws Exception {
		
		final List<Class<?>> classes = ClassUtils.getClassesForPackage(cl, pakage);
		for (Class<?> class1 : classes) {
			if (Modifier.isAbstract(class1.getModifiers())) continue;
			if (IOperation.class.isAssignableFrom(class1)) {
				
				IOperation op = (IOperation) class1.newInstance();
				if (operations==null) operations = new HashMap<String, IOperation>(31);
				
				operations.put(op.getId(), op);

			}
		}
	}
 }
