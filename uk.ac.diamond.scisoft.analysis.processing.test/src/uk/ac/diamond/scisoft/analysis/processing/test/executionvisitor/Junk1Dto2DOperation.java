package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

public class Junk1Dto2DOperation extends Junk2Dto2DOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk1Dto2DOperation";
	}
	
	@Override
	public String getName(){
		return "Junk2Dto1DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
}
