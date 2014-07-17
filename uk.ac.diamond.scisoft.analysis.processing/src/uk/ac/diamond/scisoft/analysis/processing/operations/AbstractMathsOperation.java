package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;

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
	public IDataset execute(IDataset a) throws OperationException {
		
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
			return result;
			
		} catch (Exception e) {
			throw new OperationException(this, e.getMessage());
		}
	}
	
	protected abstract IDataset operation(IDataset a, Object value);

	@Override
	public void setParameters(Serializable... parameters) throws IllegalArgumentException {
		if (parameters.length!=1) throw new IllegalArgumentException("You can only set one value to subtract "+getClass().getSimpleName());
		this.value = (Number)parameters[0];
	}

}
