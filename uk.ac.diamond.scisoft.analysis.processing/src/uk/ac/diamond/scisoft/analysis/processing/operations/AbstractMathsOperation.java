package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;

/**
 * Maths operations are bascially just for testing at the moment.
 * 
 * They do not currently operate in a generic way, see FIXMEs below.
 * 
 * @author fcp94556
 *
 */
public abstract class AbstractMathsOperation extends AbstractOperation<IOperationModel, OperationData> {

	private Object         value;
	
	/**
	 * TODO This operation is only an example.
	 */
	@Override
	public OperationData execute(IDataset a, IMonitor monitor) throws OperationException {
		
		try {
			IDataset result= operation(a, value);
			// TODO Need to set up axes and meta correctly.
			return new OperationData(result);
			
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
	}
	
	protected abstract IDataset operation(IDataset a, Object value);

	@Override
	public void setModel(IOperationModel model) {
		super.setModel(model);
		try {
			this.value = model.get("Value");
		} catch (Exception e) {
			value = null;
		}
	}

	
	public OperationRank getInputRank() {
		return OperationRank.ANY; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME; // Addition for instance
	}

}
