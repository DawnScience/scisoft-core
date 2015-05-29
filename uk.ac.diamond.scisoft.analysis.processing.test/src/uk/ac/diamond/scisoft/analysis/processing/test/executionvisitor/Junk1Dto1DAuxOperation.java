package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;

@Atomic
public class Junk1Dto1DAuxOperation extends Junk1Dto1DOperation {

	int[] auxShape = new int[]{1};
	
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
		rand.squeeze();
		rand.setName("singlevalue");
		
		d.setAuxData(rand);
		
		return d;

		
	}
}
