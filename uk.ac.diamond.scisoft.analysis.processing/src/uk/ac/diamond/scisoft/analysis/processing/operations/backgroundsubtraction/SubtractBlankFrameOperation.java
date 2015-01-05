package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

public class SubtractBlankFrameOperation extends AbstractImageSubtrationOperation<SubtractBlankFrameModel> {
	
	private ILazyDataset parent = null;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractBlankFrameOperation";
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		// check file is the same
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		if (ssm == null) throw new OperationException(this, "No origin metadata!");
		if (parent != ssm.getSourceInfo().getParent()) {
			parent = ssm.getSourceInfo().getParent();
			image = null;
		}
		
		return super.process(input, monitor);
	}
	
	@Override
	protected Dataset getImage(IDataset input) throws OperationException {
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (ssm  == null) throw new OperationException(this, "No origin metadata!");
		
		ILazyDataset lzBg = ssm.getSourceInfo().getParent();

		int[] dd = ssm.getDataDimensions().clone();
		Arrays.sort(dd);

		int startFrame = model.getStartFrame();
		int end = model.getStartFrame()+1;
		if (model.getEndFrame() != null) {
			end = model.getEndFrame();
		}
		
		if (end <= startFrame) throw new OperationException(this,"End cannot be before or the same as start");

		ILazyDataset bg = lzBg.getSliceView();
		int[] ss = AbstractDataset.squeezeShape(bg.getShape(), false);
		if (ss.length == 2) {
			image = (Dataset)bg.getSlice().squeeze();
		} else {
			image = mean(startFrame, end, bg, dd).squeeze();
		}

		return image;
	}
	
	private Dataset mean(int start, int stop, ILazyDataset data, int... ignoreAxes) {
		int[] shape = data.getShape();
		PositionIterator iter = new PositionIterator(shape, ignoreAxes);
		int[] pos = iter.getPos();
		boolean[] omit = iter.getOmit();

		int rank = shape.length;
		int[] st = new int[rank];
		Arrays.fill(st, 1);
		int[] end = new int[rank];

		Dataset average = null;
		int count = 1;
		int c = 0;
		while (iter.hasNext() && count < stop +1) {
			if (c++ < start) continue;
			
			for (int i = 0; i < rank; i++) {
				end[i] = omit[i] ? shape[i] : pos[i] + 1;
			}

			Dataset ds = DatasetUtils.cast(data.getSlice(pos, end, st), Dataset.FLOAT64);

			if (average == null) {
				average = ds;
				count++;
			} else {
				ds.isubtract(average);
				ds.idivide(count++);
				average.iadd(ds);
			}
		}

		if (average != null)
			average.squeeze();
		return average;
	}

}
