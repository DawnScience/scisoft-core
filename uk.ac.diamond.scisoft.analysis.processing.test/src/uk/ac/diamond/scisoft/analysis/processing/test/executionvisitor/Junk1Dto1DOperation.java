package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;

import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;

public class Junk1Dto1DOperation extends AbstractOperation<Junk1DModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk1Dto1DOperation";
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
		return "Junk1Dto1DOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		int x = model.getxDim();

		IDataset out = Random.rand(new int[] {x});
		out.setName("Junk1Dout");
		IDataset ax1 = DatasetFactory.createRange(0, x,1, Dataset.INT16);
		ax1.setShape(new int[]{20,1});
		ax1.setName("Junk1Dax");
		
		AxesMetadataImpl am = new AxesMetadataImpl(1);
		am.addAxis(ax1, 0);
		
		out.setMetadata(am);
		
		return new OperationData(out);
	}

}
