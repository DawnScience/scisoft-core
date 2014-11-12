package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.dataset.function.Downsample;
import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleMode;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;

public class DownsampleImageOperation extends AbstractOperation<DownsampleImageModel, OperationData> {

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DownsampleImageOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		ILazyDataset mask = getFirstMask(input);
		
		if (mask != null) {
			Dataset m = (Dataset)mask.getSlice();
			((Dataset)input).setByBoolean(Double.NaN, Comparisons.logicalNot(m));
		}
		
		int x = model.getDownsampleSizeX();
		int y = model.getDownsampleSizeY();
		
		Downsample downsample = new Downsample(((DownsampleImageModel)model).getDownsampleMode(), y,x);
		List<Dataset> out = downsample.value(input);
		Dataset dataset = out.get(0);
		dataset.setName("downsampled");
		
		ILazyDataset[] firstAxes = getFirstAxes(input);
		
		if (firstAxes != null && (firstAxes[0] != null || firstAxes[1] != null)) {
			AxesMetadata axm = new AxesMetadataImpl(2);
			
			Downsample dsax = new Downsample(DownsampleMode.MEAN, y,x);
			
			if (firstAxes[0] != null)	{
				List<Dataset> ax1 = dsax.value(firstAxes[0].getSlice());
				ax1.get(0).setName(firstAxes[0].getName());
				axm.setAxis(0, ax1.get(0));
			}
			
			if (firstAxes[1] != null)	{
				List<Dataset> ax2 = dsax.value(firstAxes[1].getSlice());
				ax2.get(0).setName(firstAxes[1].getName());
				axm.setAxis(1, ax2.get(0));
			}
			
			dataset.setMetadata(axm);
		}
		
		return new OperationData(dataset);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
