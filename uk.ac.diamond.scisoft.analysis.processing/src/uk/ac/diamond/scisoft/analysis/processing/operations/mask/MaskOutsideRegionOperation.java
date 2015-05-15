package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.MaskMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public class MaskOutsideRegionOperation extends AbstractOperation<MaskOutsideRegionModel, OperationData> {

	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		IDataset mask = DatasetUtils.convertToDataset(getFirstMask(input));
		
		if (mask != null && !Arrays.equals(input.getShape(), mask.getShape())) {
			throw new OperationException(this, "Mask is incorrect shape!");
		}
		
		IDataset m = generateMaskFromROI(model.getRegion(), input.getShape());
		if (mask != null) m = Comparisons.logicalAnd(mask, m);

		
		MaskMetadata mm = new MaskMetadataImpl(m);
		input.setMetadata(mm);
		
		return new OperationData(input);
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.mask.MaskOutsideRegionOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	private IDataset generateMaskFromROI(IROI roi, int[] shape) {
		Dataset mask = DatasetFactory.zeros(shape, Dataset.BOOL);
		for (int i = 0; i < shape[0]; i++) {
			double[] hi = roi.findHorizontalIntersections(i);
			if (hi != null) {
				boolean cutsStart = roi.containsPoint(0, i);
				boolean cutsEnd = roi.containsPoint(shape[1]-1, i);
				
				List<Integer> inters = new ArrayList<Integer>();
				if (cutsStart) inters.add(0);
				for (double d : hi) {
					if (!inters.contains((int)d) && d > 0 && d < shape[1]-1) inters.add((int)d);
				}
				if (cutsEnd && !inters.contains(shape[1]-1)) inters.add(shape[1]-1);
				
				int[] start = new int[]{i,0};
				int[] stop = new int[]{i+1,0};
				int[] step = new int[]{1,1};
				
				while (!inters.isEmpty()) {
					
					if (inters.size() == 1) {
						start[1] = inters.get(0);
						stop[1] = start[1]+1;
						mask.setSlice(true, start, stop, step);
						inters.remove(0);
					} else {
						int s = inters.get(0);
						int e = inters.get(1);
						
						if (roi.containsPoint(s+(e-s)/2, i)) {
							start[1] = s;
							stop[1] = e;
							mask.setSlice(true, start, stop, step);
							inters.remove(0);
						} else {
							start[1] = inters.get(0);
							stop[1] = start[1]+1;
							mask.setSlice(true, start, stop, step);
							inters.remove(0);
						}
					}
				}
			}

		}
		return mask;
	}

}
