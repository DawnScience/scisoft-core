package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.PositionIterator;
import org.eclipse.january.dataset.ShapeUtils;

public class SubtractBlankFrameOperation extends AbstractImageSubtractionOperation<SubtractBlankFrameModel> {
	
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
		int[] ss = ShapeUtils.squeezeShape(bg.getShape(), false);
		if (ss.length == 2) {
			try {
				image = DatasetUtils.sliceAndConvertLazyDataset(bg).squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
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

			Dataset ds;
			try {
				ds = DatasetUtils.cast(DoubleDataset.class, data.getSlice(pos, end, st));
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}

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
