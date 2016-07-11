package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.downsample.DownsampleMode;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.function.Downsample;
import org.eclipse.dawnsci.analysis.dataset.function.DownsampleDatatype;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

@Atomic
public class DownsampleImageOperation extends AbstractOperation<DownsampleImageModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DownsampleImageOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		ILazyDataset mask = getFirstMask(input);
		
		if (mask != null && input instanceof Dataset) {
			Dataset m;
			try {
				m = DatasetUtils.sliceAndConvertLazyDataset(mask);
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
			((Dataset)input).setByBoolean(Double.NaN, Comparisons.logicalNot(m));
		}
		
		int x = model.getDownsampleSizeX();
		int y = model.getDownsampleSizeY();
		DownsampleDatatype outputDatatype = model.getDownSampleDatatype();

		Downsample downsample = new Downsample(((DownsampleImageModel)model).getDownsampleMode(), y,x);

		input = DatasetUtils.cast(input, outputDatatype.getDatatype());

		List<Dataset> out = downsample.value(input);
		IDataset dataset = out.get(0);
		dataset.setName("downsampled");
		
		List<AxesMetadata> ml = null;
		try {
			ml = input.getMetadata(AxesMetadata.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (ml != null && !ml.isEmpty() && ml.get(0).getAxes() != null) {
			AxesMetadata axm;
			try {
				axm = MetadataFactory.createMetadata(AxesMetadata.class, 2);
			} catch (MetadataException e1) {
				throw new OperationException(this, e1);
			}
			AxesMetadata inm = ml.get(0);
			
			int rank = inm.getAxes().length;
			Downsample dsax = new Downsample(DownsampleMode.MEAN, y,x);
			for (int i = 0; i<rank;i++) {
				ILazyDataset[] axis = inm.getAxis(i);
				if (axis == null) continue;
				ILazyDataset[] o = new ILazyDataset[axis.length];
				for (int j = 0; j < axis.length; j++) {
					if (axis[j] != null) {
						try {
							o[j] = dsax.value(axis[j].getSlice()).get(0);
						} catch (DatasetException e) {
							throw new OperationException(this, e);
						}
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
