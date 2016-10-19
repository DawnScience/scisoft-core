package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

@Atomic
public class JunkAnyto1DOperation extends Junk1Dto1DOperation {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.JunkAnyto1DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}
	
	@Override
	public String getName(){
		return "JunkAnyto1DOperation";
	}

}
