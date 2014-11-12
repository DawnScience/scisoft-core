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
		OriginMetadata om = getOriginMetadata(input);
		
		if (om == null) throw new OperationException(this, "No origin metadata!");
		Dataset bg = null;
		
		try {
			String dsName = om.getDatasetName();
			if (model.getFilePath() == null) throw new OperationException(this,"File path not set!");
			IDataHolder dh = LoaderFactory.getData(model.getFilePath());
			ILazyDataset lzBg = dh.getLazyDataset(dsName);
			
			if (Arrays.equals(lzBg.getShape(), om.getParent().getShape())) {
				bg = (Dataset)lzBg.getSliceView(om.getInitialSlice()).getSlice(om.getCurrentSlice());
			} else {
				bg = LazyMaths.mean(lzBg, om.getDataDimensions());
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
