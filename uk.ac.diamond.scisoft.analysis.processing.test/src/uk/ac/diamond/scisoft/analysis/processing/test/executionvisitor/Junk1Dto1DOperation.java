package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.metadata.ErrorMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public class Junk1Dto1DOperation extends AbstractOperation<Junk1DModel, OperationData> implements ITestOperation {

	private boolean withAxes = true;
	private boolean withErrors = true;
	private int sleep = 0;
	
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
	
	public void setWithAxes(boolean withAxes) {
		this.withAxes = withAxes;
	}
	
	public void setWithErrors(boolean withErrors) {
		this.withErrors = withErrors;
	}
	
	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (sleep != 0) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				throw new OperationException(this, "Interruped Exception: " + e.getMessage());
			}
		}
		
		return getTestData();
		
	}

	public OperationData getTestData() {
		
		int x = model.getxDim();

		IDataset out = DatasetFactory.createRange(x,Dataset.INT16);
		out.setName("Junk1Dout");
		
		if (withAxes) {
			IDataset ax1 = DatasetFactory.createRange(0, x,1, Dataset.INT16);
			ax1.setShape(new int[]{x});
			ax1.setName("Junk1Dax");
			
			if (withErrors) {
				ax1.setError(DatasetFactory.createRange(1, x+1,1, Dataset.INT16));
			}
			
			AxesMetadataImpl am = new AxesMetadataImpl(1);
			am.addAxis(0, ax1);
			
			out.setMetadata(am);
		}
		
		if (withErrors) {
			IDataset error = Random.rand(new int[] {x});
			ErrorMetadataImpl em = new ErrorMetadataImpl();
			em.setError(error);
			out.setMetadata(em);
		}
		
		return new OperationData(out);
	}

}
