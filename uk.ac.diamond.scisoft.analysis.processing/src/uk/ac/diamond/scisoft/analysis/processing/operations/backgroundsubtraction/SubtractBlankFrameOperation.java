package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyMaths;

public class SubtractBlankFrameOperation extends AbstractImageSubtrationOperation<SubtractBlankFrameModel> {

	Dataset background = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SubtractBlankFrameOperation";
	}

	@Override
	protected Dataset getImage(IDataset input) throws OperationException {
		OriginMetadata om = getOriginMetadata(input);
		
		if (om == null) throw new OperationException(this, "No origin metadata!");
		
		ILazyDataset lzBg = om.getParent();
		
		if (lzBg.getRank() == 3) {
			
			int[] dd = om.getDataDimensions().clone();
			Arrays.sort(dd);
			Slice[] slice = new Slice[3];
			
			for (int i = 0; i < 3; i++) {
				slice[i] = new Slice();
				if (Arrays.binarySearch(dd,i) < 0) {
					slice[i].setStart(model.getStartFrame());
					int end = model.getStartFrame()+1;
					if (model.getEndFrame() != null) {
						end = model.getEndFrame();
					}
					slice[i].setStop(end);
				}
			}
			
			ILazyDataset bg = lzBg.getSliceView(slice);
			bg = bg.squeeze();
			if (bg.getRank() == 2) {
				image = (Dataset)bg.getSlice();
			} else {
				image = LazyMaths.mean(bg, dd);
			}
			
		}
		
		return image;
	}

}
