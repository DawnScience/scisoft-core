package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

/**
 * Maths operations are bascially just for testing at the moment.
 * 
 * They do not currently operate in a generic way, see FIXMEs below.
 * 
 * @author fcp94556
 *
 */
public abstract class AbstractMathsOperation extends AbstractOperation {

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
	public void setModel(IOperationModel model) throws Exception {
		this.value = model.get("Value");
	}

	
	public OperationRank getInputRank() {
		return OperationRank.ANY; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME; // Addition for instance
	}

}
