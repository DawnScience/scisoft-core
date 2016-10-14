package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class VerticalHorizontalSlices {

	public static ILineTrace horizontalslice(SuperSashPlotSystem2Composite customComposite2){
		
		IRectangularROI horizontalSliceBounds = customComposite2.getRegions()[0].getROI().getBounds();
		
//		System.out.println("slice moved");
		
		int[] lenh =horizontalSliceBounds.getIntLengths();
		int[] pth = horizontalSliceBounds.getIntPoint();
		int[][] lenpth = new int[][] {lenh,pth};
		
		IDataset iih = ImageSlicerUtils.ImageSliceUpdate(customComposite2.getImage(), lenpth);
		
		IDataset iihdata  = DatasetFactory.zeros(lenh[0]);
		
		for(int iy = 0;iy<lenh[0];iy++){
			
//			System.out.println("iy : " +iy);
			
			double ixsum = 0;
			
			for(int ix = 0; ix<lenh[1];ix++){
				ixsum += iih.getDouble(ix, iy);
//				System.out.println("ix : " +ix);
			}
			
			iihdata.set(ixsum, iy);
		}
		
		IDataset xhrange = DatasetFactory.createRange(pth[0], pth[0]+lenh[0], 1, Dataset.FLOAT64);
		
		ILineTrace lt1 = customComposite2.getPlotSystem1().createLineTrace("horizontal slice");
		lt1.setData(xhrange, iihdata);
		
	 
		
		
		return lt1;
	}
	
	public static ILineTrace verticalslice(SuperSashPlotSystem2Composite customComposite2){
		
		IRectangularROI verticalSliceBounds = customComposite2.getRegions()[1].getROI().getBounds();
		
		int[] lenv =verticalSliceBounds.getIntLengths();
		int[] ptv = verticalSliceBounds.getIntPoint();
		int[][] lenptv = new int[][] {lenv,ptv};
		
		IDataset ii = ImageSlicerUtils.ImageSliceUpdate(customComposite2.getImage(), lenptv);
		
		IDataset iivdata  = DatasetFactory.zeros(lenv[1]);
		
		for(int iy = 0;iy<lenv[1];iy++){
			
			double ixsum = 0;
			
			for(int ix = 0; ix<lenv[0];ix++){
				ixsum += ii.getDouble(iy, ix);
			}
			
			iivdata.set(ixsum, iy);
		}
		
		IDataset xvrange = DatasetFactory.createRange(ptv[1]+lenv[1], ptv[1], -1, Dataset.FLOAT64);
		
		ILineTrace lt3 = customComposite2.getPlotSystem3().createLineTrace("vertical slice");
		lt3.setData(iivdata, xvrange);
		
		return lt3;
	}
	
	public static ILineTrace horizontalsliceBackgroundSubtracted(SuperSashPlotSystem2Composite customComposite2, IDataset background){
		
		IRectangularROI horizontalSliceBounds = customComposite2.getRegions()[0].getROI().getBounds();
		
//		System.out.println("slice moved");
		
		int[] lenh =horizontalSliceBounds.getIntLengths();
		int[] pth = horizontalSliceBounds.getIntPoint();
		int[][] lenpth = new int[][] {lenh,pth};
		
		IDataset iih = ImageSlicerUtils.ImageSliceUpdate(background, lenpth);
		
		IDataset iihdata  = DatasetFactory.zeros(lenh[0]);
		
		for(int iy = 0;iy<lenh[0];iy++){
			
//			System.out.println("iy : " +iy);
			
			double ixsum = 0;
			
			for(int ix = 0; ix<lenh[1];ix++){
				ixsum += iih.getDouble(ix, iy);
//				System.out.println("ix : " +ix);
			}
			
			iihdata.set(ixsum, iy);
		}
		
		IDataset xhrange = DatasetFactory.createRange(pth[0], pth[0]+lenh[0], 1, Dataset.FLOAT64);
		
		ILineTrace lt1 = customComposite2.getPlotSystem1().createLineTrace("background subtracted slice");
		lt1.setData(xhrange, iihdata);
		
	 
		
		
		return lt1;
	}
	
	public static ILineTrace verticalsliceBackgroundSubtracted(SuperSashPlotSystem2Composite customComposite2, IDataset background){
		
		IRectangularROI verticalSliceBounds = customComposite2.getRegions()[1].getROI().getBounds();
		
		int[] lenv =verticalSliceBounds.getIntLengths();
		int[] ptv = verticalSliceBounds.getIntPoint();
		int[][] lenptv = new int[][] {lenv,ptv};
		
		IDataset ii = ImageSlicerUtils.ImageSliceUpdate(background, lenptv);
		
		IDataset iivdata  = DatasetFactory.zeros(lenv[1]);
		
		for(int iy = 0;iy<lenv[1];iy++){
			
			double ixsum = 0;
			
			for(int ix = 0; ix<lenv[0];ix++){
				ixsum += ii.getDouble(iy, ix);
			}
			
			iivdata.set(ixsum, iy);
		}
		
		IDataset xvrange = DatasetFactory.createRange(ptv[1]+lenv[1], ptv[1], -1, Dataset.FLOAT64);
		
		ILineTrace lt3 = customComposite2.getPlotSystem3().createLineTrace("background subtracted slice");
		lt3.setData(iivdata, xvrange);
		
		return lt3;
	}
	
	
	
	
	
}
