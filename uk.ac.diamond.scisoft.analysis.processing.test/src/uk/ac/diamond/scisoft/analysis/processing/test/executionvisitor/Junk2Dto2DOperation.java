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

public class Junk2Dto2DOperation extends AbstractOperation<Junk2Dto2Dmodel, OperationData> {

	private boolean withAxes = true;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk2Dto2DOperation";
	}
	
	@Override
	public String getName(){
		return "Junk2Dto2DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	public void setWithAxes(boolean withAxes) {
		this.withAxes = withAxes;
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		int x = model.getxDim();
		int y = model.getyDim();
		
		IDataset out = Random.rand(new int[] {x,y});
		out.setName("Junk2Dto2Dout");
		IDataset ax1 = DatasetFactory.createRange(0, x,1, Dataset.INT16);
		ax1.setShape(new int[]{x,1});
		ax1.setName("Junk2Dto2DAx1");
		IDataset ax2 = DatasetFactory.createRange(0, y,1, Dataset.INT16);
		ax2.setShape(new int[]{1,y});
		ax2.setName("Junk2Dto2DAx2");
		if (withAxes) {
			AxesMetadataImpl am = new AxesMetadataImpl(2);
			am.addAxis(0, ax1);
			am.addAxis(1, ax2);
			
			out.setMetadata(am);
		}
		
		
		return new OperationData(out);
	}

}
