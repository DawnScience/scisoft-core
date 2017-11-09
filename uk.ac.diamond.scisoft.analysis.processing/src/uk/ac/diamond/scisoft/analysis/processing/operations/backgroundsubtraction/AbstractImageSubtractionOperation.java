package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public abstract class AbstractImageSubtractionOperation<T extends AbstractOperationModel> extends AbstractOperation<T, OperationData> {

	protected Dataset image;
	private PropertyChangeListener listener;
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset bg = null;
		if (image == null) {
			bg = getImage(input);
		} else {
			bg = image;
		}
		
		Dataset output = Maths.subtract(input, bg);
		copyMetadata(input, output);
		
		return new OperationData(output);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	protected abstract Dataset getImage(IDataset input) throws OperationException;
	
	@Override
	public void setModel(T model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					image = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
}
