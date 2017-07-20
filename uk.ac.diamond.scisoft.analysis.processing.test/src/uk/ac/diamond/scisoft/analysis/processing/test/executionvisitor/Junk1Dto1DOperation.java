package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.ErrorMetadata;
import org.eclipse.january.metadata.MetadataFactory;

@Atomic
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

		IDataset out = DatasetFactory.createRange(ShortDataset.class, x);
		out.setName("Junk1Dout");
		
		if (withAxes) {
			IDataset ax1 = DatasetFactory.createRange(ShortDataset.class, 0, x,1);
			ax1.setShape(new int[]{x});
			ax1.setName("Junk1Dax");
			
			if (withErrors) {
				ax1.setErrors(DatasetFactory.createRange(ShortDataset.class, 1, x+1,1));
			}
			
			AxesMetadata am;
			try {
				am = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			} catch (MetadataException e) {
				throw new OperationException(this, e);
			}
			am.addAxis(0, ax1);
			
			out.setMetadata(am);
		}
		
		if (withErrors) {
			IDataset error = Random.rand(new int[] {x});
			ErrorMetadata em;
			try {
				em = MetadataFactory.createMetadata(ErrorMetadata.class);
			} catch (MetadataException e) {
				throw new OperationException(this, e);
			}
			em.setError(error);
			out.setMetadata(em);
		}
		
		return new OperationData(out);
	}
	
	public Class<Junk1DModel> getModelClass() {
		return Junk1DModel.class;
	}

	
}
