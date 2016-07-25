package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Random;

@Atomic
public class Junk1Dto1DAuxOperation extends Junk1Dto1DOperation {

	int[] auxShape = new int[]{};
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk1Dto1DAuxOperation";
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
		return "Junk1Dto1DAuxOperation";
	}
	
	public void setAuxShape(int[] auxShape) {
		this.auxShape = auxShape;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		OperationData d = super.process(input, monitor);
		
		DoubleDataset rand = Random.rand(auxShape);
//		rand.squeeze();
		rand.setName("singlevalue");
		rand.iadd(10);
		
		d.setAuxData(rand);
		
		return d;

		
	}
}
