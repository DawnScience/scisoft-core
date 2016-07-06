package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.LazyMaths;

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
				bg = DatasetUtils.convertToDataset(lzBg.getSlice(ssm.getSliceFromInput())).squeeze();
			} else {
				bg = LazyMaths.mean(lzBg, ssm.getDataDimensions()).squeeze();
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
