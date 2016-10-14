package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.swt.widgets.Slider;

public class ImageSlicerUtils {

	
		public static IDataset ImageSliceUpdate(Slider slider, ILazyDataset aggDat, IRegion region){
			
			int selection = slider.getSelection();
			SliceND slice1 = new SliceND(aggDat.getShape());
			slice1.setSlice(0, selection, selection+1, 1);
			
			
			//int[] len 
			IROI box = region.getROI();
			IRectangularROI bounds = box.getBounds();
			int[] len = bounds.getIntLengths();
			int[] pt = bounds.getIntPoint();
			

			//slice1.setSlice(2, (int) Math.round(pt[0]-(0.5*len[0])), (int) Math.round(pt[0] + (0.5*len[0])), 1);
			//slice1.setSlice(1, (int) Math.round(pt[1]-(0.5*len[1])), (int) Math.round(pt[1] + (0.5*len[1])), 1);
			
			slice1.setSlice(2, (int) Math.round(pt[0]), (int) Math.round(pt[0] + (len[0])), 1);
			slice1.setSlice(1, (int) Math.round(pt[1]), (int) Math.round(pt[1] + (len[1])), 1);


			
			//IDataset image2 = image.getSlice(slice1);
			
			IDataset j = null;
			try {
				j = aggDat.getSlice(slice1);
			} catch (DatasetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			j.squeeze();
			return j;
			
		}
		
		public static IDataset ImageSliceUpdate(int selection, ILazyDataset aggDat, int[][] LenPt){
			
			SliceND slice1 = new SliceND(aggDat.getShape());
			slice1.setSlice(0, selection, selection+1, 1);
			
			
			//int[] len 
			int[] len = LenPt[0];
			int[] pt = LenPt[1];
			

			//slice1.setSlice(2, (int) Math.round(pt[0]-(0.5*len[0])), (int) Math.round(pt[0] + (0.5*len[0])), 1);
			//slice1.setSlice(1, (int) Math.round(pt[1]-(0.5*len[1])), (int) Math.round(pt[1] + (0.5*len[1])), 1);
			
			slice1.setSlice(2, (int) Math.round(pt[0]), (int) Math.round(pt[0] + (len[0])), 1);
			slice1.setSlice(1, (int) Math.round(pt[1]), (int) Math.round(pt[1] + (len[1])), 1);


			
			//IDataset image2 = image.getSlice(slice1);
			
			IDataset j = null;
			try {
				j = aggDat.getSlice(slice1);
			} catch (DatasetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			j.squeeze();
			return j;
			
		}
		
		
		public static IDataset ImageSliceUpdate(IDataset input, int[][] LenPt){
			
			SliceND slice1 = new SliceND(input.getShape());
//			slice1.setSlice(0, selection, selection+1, 1);
//			
			
			//int[] len 
			int[] len = LenPt[0];
			int[] pt = LenPt[1];
			

			//slice1.setSlice(2, (int) Math.round(pt[0]-(0.5*len[0])), (int) Math.round(pt[0] + (0.5*len[0])), 1);
			//slice1.setSlice(1, (int) Math.round(pt[1]-(0.5*len[1])), (int) Math.round(pt[1] + (0.5*len[1])), 1);
			
			slice1.setSlice(1, (int) Math.round(pt[0]), (int) Math.round(pt[0] + (len[0])), 1);
			slice1.setSlice(0, (int) Math.round(pt[1]), (int) Math.round(pt[1] + (len[1])), 1);


			
			//IDataset image2 = image.getSlice(slice1);
			
			IDataset j = null;
			try {
				j = input.getSlice(slice1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			j.squeeze();
			return j;
			
		}
}
//TEST
