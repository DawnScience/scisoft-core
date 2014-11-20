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
		
		List<AxesMetadata> ml = null;
		try {
			ml = input.getMetadata(AxesMetadata.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (ml != null && !ml.isEmpty() && ml.get(0).getAxes() != null) {
			AxesMetadata axm = new AxesMetadataImpl(2);
			AxesMetadata inm = ml.get(0);
			
			int rank = inm.getAxes().length;
			Downsample dsax = new Downsample(DownsampleMode.MEAN, y,x);
			for (int i = 0; i<rank;i++) {
				ILazyDataset[] axis = inm.getAxis(i);
				if (axis == null) continue;
				ILazyDataset[] o = new ILazyDataset[axis.length];
				for (int j = 0; j < axis.length; j++) {
					if (axis[j] != null) {
						o[j] = dsax.value(axis[j].getSlice()).get(0);
						o[j].setName(axis[j].getName());
					}
				}
				
				axm.setAxis(i, o);
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
