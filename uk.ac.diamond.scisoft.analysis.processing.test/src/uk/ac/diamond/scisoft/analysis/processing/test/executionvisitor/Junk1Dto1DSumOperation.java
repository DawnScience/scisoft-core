package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;

/**
 * Operation that updates a summary on each iteration
 */
@Atomic
public class Junk1Dto1DSumOperation extends Junk1Dto1DOperation {

	int[] sumShape = new int[]{};
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk1Dto1DSumOperation";
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
	public String getName(){
		return "Junk1Dto1DSumOperation";
	}
	
	public void setSumShape(int[] auxShape) {
		this.sumShape = auxShape;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		OperationData d = super.process(input, monitor);

		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();

		int i = si.getSliceNumber();
		IntegerDataset summary = DatasetFactory.zeros(IntegerDataset.class, sumShape);
		summary.fill(i);
		summary.setName("singlevalue");
		d.setSummaryData(summary);

		return d;
	}
}
