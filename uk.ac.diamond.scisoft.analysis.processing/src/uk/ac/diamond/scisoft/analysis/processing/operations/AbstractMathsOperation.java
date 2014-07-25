package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

/**
 * Maths operations are bascially just for testing at the moment.
 * 
 * They do not currently operate in a generic way, see FIXMEs below.
 * 
 * @author fcp94556
 *
 */
public abstract class AbstractMathsOperation implements IOperation {

	private IRichDataset[] data;
	private Number         value;

	@Override
	public String getOperationDescription() {
		return "Dataset mathematics operation "+getClass().getSimpleName();
	}

	@Override
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {
		if (data.length<1 || data.length>2) throw new IllegalArgumentException("You can only set one or two datasets for "+getClass().getSimpleName());
	    this.data = data;
	}

	/**
	 * TODO This operation is only an example.
	 */
	@Override
	public OperationData execute(OperationData a, IMonitor monitor) throws OperationException {
		
		try {
			IDataset result;
			if (value!=null) {
				// TODO FIXME We simply get all data out of the lazy and return it
			    result = operation(a, value);
			} else {
				final Dataset b = (Dataset)data[1].getData().getSlice((Slice)null);
				result = operation(a, b);

			}
			// TODO Need to set up axes and meta correctly.
			return new OperationData(result);
			
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
	}
	
	protected abstract IDataset operation(OperationData a, Object value);

	@Override
	public void setModel(IOperationModel model) throws Exception {
		this.value = (Number)model.get("Value");
	}

	
	public OperationRank getInputRank() {
		return OperationRank.ANY; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME; // Addition for instance
	}

}
