package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Subtracts either one dataset from another or a scalar value from all values of a dataset.
 * @author fcp94556
 *
 */
public class AddOperation extends AbstractMathsOperation {

	protected IDataset operation(Dataset a, Object value) {
		return a.iadd(value);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.addOperation";
	}

}
