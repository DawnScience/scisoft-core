package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.january.dataset.IDataset;

public class PlotSystem2DataSetter {
    
    public static IDataset PlotSystem2DataSetter1(IROI box, IDataset input) {

		IRectangularROI roi = box.getBounds();
		int[] newLen = roi.getIntLengths();
		int[] newPt = roi.getIntPoint();
		int[][] newLenPt = {newLen, newPt};
		return ImageSlicerUtils.ImageSliceUpdate(input, newLenPt);
    }
}
