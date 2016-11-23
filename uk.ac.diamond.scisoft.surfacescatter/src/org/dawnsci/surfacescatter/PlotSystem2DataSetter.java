package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.january.dataset.IDataset;

public class PlotSystem2DataSetter {

    public static IDataset PlotSystem2DataSetter1(ExampleModel model) {

    	IROI box = model.getROI();
		IRectangularROI roi = box.getBounds();
		int[] newLen = roi.getIntLengths();
		int[] newPt = roi.getIntPoint();
		int[][] newLenPt = {newLen, newPt};
		IDataset j = ImageSlicerUtils.ImageSliceUpdate(model.getSliderPos(), model.getDatImages(),newLenPt);

		return j;
    }
    
    public static IDataset PlotSystem2DataSetter1(IROI box, IDataset input) {

		IRectangularROI roi = box.getBounds();
		int[] newLen = roi.getIntLengths();
		int[] newPt = roi.getIntPoint();
		int[][] newLenPt = {newLen, newPt};
		IDataset j = ImageSlicerUtils.ImageSliceUpdate(input, newLenPt);

		return j;
    }
}
