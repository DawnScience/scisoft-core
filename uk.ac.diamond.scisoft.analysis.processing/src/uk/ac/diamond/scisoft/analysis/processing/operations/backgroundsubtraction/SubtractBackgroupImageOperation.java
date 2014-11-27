package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.slice.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyMaths;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class SubtractBackgroupImageOperation extends AbstractImageSubtrationOperation<SubtractBackgroundImageModel> {

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SubtractBackgroupImageOperation";
	}
	
	@Override
	protected Dataset getImage(IDataset input) throws OperationException {
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (ssm == null) throw new OperationException(this, "No origin metadata!");
		Dataset bg = null;
		
		try {
			String dsName = ssm.getSourceInfo().getDatasetName();
			if (model.getFilePath() == null) throw new OperationException(this,"File path not set!");
			IDataHolder dh = LoaderFactory.getData(model.getFilePath());
			ILazyDataset lzBg = dh.getLazyDataset(dsName);
			
			if (Arrays.equals(lzBg.getShape(), ssm.getSourceInfo().getParent().getShape())) {
				bg = (Dataset)lzBg.getSliceView(ssm.getSliceInfo().getViewSlice()).getSlice(ssm.getSliceInfo().getCurrentSlice()).squeeze();
			} else {
				bg = LazyMaths.mean(lzBg, ssm.getShapeInfo().getDataDimensions()).squeeze();
				image = bg;
			}
			
			
		}catch (FileNotFoundException e) {
			throw new OperationException(this, "Background file not found!");
		} catch (Exception e) {
			throw new OperationException(this, e.getMessage());
		}
		
		//TODO if average set member background else dont
		return bg;
	}

}
