package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;

public abstract class AbstractPixelIntegrationOperation extends
		AbstractOperation {

	AbstractPixelIntegration integrator;
	IDiffractionMetadata metadata;
	PixelIntegrationModel model;
	
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
		
		if (integrator == null) integrator = createIntegrator(model, metadata);
		
		ILazyDataset mask = getFirstMask(slice);
		if (mask != null) {
			IDataset m = mask.getSlice().squeeze();
			integrator.setMask((AbstractDataset)m);
		}
		
		ILazyDataset[] axes = getFirstAxes(slice);
		int[] dataDims = getOriginalDataDimensions(slice);
		
		final List<Dataset> out = integrator.integrate(slice);
		
		Dataset data = out.remove(1);
		
		setAxes(data,axes, dataDims, out);
		
		return new OperationData(data);
	}

	protected abstract void setAxes(IDataset data, ILazyDataset[] axes, int[] dataDims, List<Dataset> out);
	
	protected abstract AbstractPixelIntegration createIntegrator(PixelIntegrationModel model, IDiffractionMetadata md);

}
