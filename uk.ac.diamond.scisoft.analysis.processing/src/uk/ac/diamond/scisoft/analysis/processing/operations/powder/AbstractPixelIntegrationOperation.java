package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;

public abstract class AbstractPixelIntegrationOperation<T extends PixelIntegrationModel> extends AbstractOperation<T, OperationData> {

	private AbstractPixelIntegration integrator;
	private IDiffractionMetadata metadata;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return this.getClass().getName();
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(slice);
		
		if (metadata == null || !metadata.equals(md)) {
			metadata = md;
			integrator = null;
		}
		
		if (integrator == null) integrator = createIntegrator((PixelIntegrationModel)model, metadata);
		
		ILazyDataset mask = getFirstMask(slice);
		if (mask != null) {
			IDataset m = mask.getSlice().squeeze();
			integrator.setMask((Dataset)m);
		}
		
		ILazyDataset[] axes = getFirstAxes(slice);
		int[] dataDims = getOriginalDataDimensions(slice);
		
		final List<Dataset> out = integrator.integrate(slice);
		
		Dataset data = out.remove(1);
		
		setAxes(data,axes, dataDims, out);
		
		return new OperationData(data);
	}
	
	@Override
	public void setModel(T model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					AbstractPixelIntegrationOperation.this.integrator = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	

	protected abstract void setAxes(IDataset data, ILazyDataset[] axes, int[] dataDims, List<Dataset> out);
	
	protected abstract AbstractPixelIntegration createIntegrator(PixelIntegrationModel model, IDiffractionMetadata md);
}
