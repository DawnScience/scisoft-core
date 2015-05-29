package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

@Atomic
public class Junk2Dto1DOperation extends Junk1Dto1DOperation {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk2Dto1DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}
	
	@Override
	public String getName(){
		return "Junk2Dto1DOperation";
	}

}
