package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.widgets.Composite;

public class VerticalHorizontalSlices {

	public static ILineTrace horizontalslice(IRectangularROI horizontalSliceBounds,
			IPlottingSystem<Composite> pS, IDataset ii,  IROI green){
	
		int[] lenh =horizontalSliceBounds.getIntLengths();
		lenh[0]= green.getBounds().getIntLengths()[0];
		if (green.getBounds().getIntLengths()[1] < lenh[1]){
			lenh[1] = green.getBounds().getIntLengths()[1];
		}
 
		int[] pth = horizontalSliceBounds.getIntPoint();
		int[][] lenpth = new int[][] {lenh,pth};
		
		IDataset iih = ImageSlicerUtils.ImageSliceUpdate(ii, lenpth);
		
		IDataset iihdata  = DatasetFactory.zeros(lenh[0]);
		
		int a =lenh[1];
		
		if(lenh[1]>iih.getShape()[0]){
			a = iih.getShape()[0];
		}
		
		for(int iy = 0;iy<lenh[0];iy++){
			
//			System.out.println("iy : " +iy);
			
			double ixsum = 0;
			
			for(int ix = 0; ix<a;ix++){
				ixsum += iih.getDouble(ix, iy);
//				System.out.println("ix : " +ix);
			}
			
			iihdata.set(ixsum, iy);
		}
		
		IDataset xhrange = DatasetFactory.createRange(pth[0], pth[0]+lenh[0], 1, Dataset.FLOAT64);
		
		ILineTrace lt1 = pS.createLineTrace("horizontal slice");
		lt1.setData(xhrange, iihdata);
		
	 
		
		
		return lt1;
	}
	
	public static ILineTrace verticalslice(IRectangularROI verticalSliceBounds,
			IDataset ii, IPlottingSystem<Composite> pS, IROI green){
		

		
		int[] lenv =verticalSliceBounds.getIntLengths();
		lenv[1]= green.getBounds().getIntLengths()[1];
		if (green.getBounds().getIntLengths()[0] < lenv[0]){
			lenv[0] = green.getBounds().getIntLengths()[0];
		}
//		lenv[1] = lenv[1]-1;
		int[] ptv = verticalSliceBounds.getIntPoint();
		int[][] lenptv = new int[][] {lenv,ptv};
		
		IDataset iiv= ImageSlicerUtils.ImageSliceUpdate(ii, lenptv);
		
		IDataset iivdata  = DatasetFactory.zeros(lenv[1]);
		
		int a =lenv[0];
		
		if(lenv[0]>iiv.getShape()[1]){
			a = iiv.getShape()[1];
		}

		
		
		for(int iy = 0;iy<lenv[1];iy++){
			
			double ixsum = 0;
			
			for(int ix = 0; ix<a;ix++){
				ixsum += iiv.getDouble(iy, ix);
			}
			
			iivdata.set(ixsum, iy);
		}
		
		IDataset xvrange = DatasetFactory.createRange(ptv[1]+lenv[1], ptv[1], -1, Dataset.FLOAT64);
		
		ILineTrace lt3 = pS.createLineTrace("vertical slice");
		lt3.setData(iivdata, xvrange);
		
		return lt3;
	}
	
	public static ILineTrace horizontalsliceBackgroundSubtracted(IRectangularROI horizontalSliceBounds,
			IPlottingSystem<Composite> pS, 
			IDataset background,
			IROI green){
		
		
		int[] lenh =horizontalSliceBounds.getIntLengths();
		
		lenh[0]= green.getBounds().getIntLengths()[0];
		
		if (green.getBounds().getIntLengths()[1] < lenh[1]){
			lenh[1] = green.getBounds().getIntLengths()[1];
		}
		int[] pth = horizontalSliceBounds.getIntPoint();
		int[][] lenpth = new int[][] {lenh,pth};
		
		IDataset iih = ImageSlicerUtils.ImageSliceUpdate(background, lenpth);
		
		IDataset iihdata  = DatasetFactory.zeros(lenh[0]);
		
		int a =lenh[1];
		
		if(lenh[1]>iih.getShape()[0]){
			a = iih.getShape()[0];
		}
				
		for(int iy = 0;iy<lenh[0];iy++){
			
			double ixsum = 0;
			
			for(int ix = 0; ix<a;ix++){
				ixsum += iih.getDouble(ix, iy);
//				System.out.println("ix : " +ix);
			}
			
			iihdata.set(ixsum, iy);
		}
		
		IDataset xhrange = DatasetFactory.createRange(pth[0], pth[0]+lenh[0], 1, Dataset.FLOAT64);
		
		ILineTrace lt1 = pS.createLineTrace("background sub slice");
		lt1.setData(xhrange, iihdata);
		
	 
		
		
		return lt1;
	}
	
	public static ILineTrace verticalsliceBackgroundSubtracted(IRectangularROI verticalSliceBounds,
			IPlottingSystem<Composite> pS, 
			IDataset background,
			IROI green){
		
		int[] lenv =verticalSliceBounds.getIntLengths();
		lenv[1]= green.getBounds().getIntLengths()[1];
		if (green.getBounds().getIntLengths()[0] < lenv[0]){
			lenv[0] = green.getBounds().getIntLengths()[0];
		}
		int[] ptv = verticalSliceBounds.getIntPoint();
		int[][] lenptv = new int[][] {lenv,ptv};
		
		IDataset iiv = ImageSlicerUtils.ImageSliceUpdate(background, lenptv);
		
		IDataset iivdata  = DatasetFactory.zeros(lenv[1]);
		
		int a =lenv[0];
		
		if(lenv[0]>iiv.getShape()[1]){
			a = iiv.getShape()[1];
		}
		
		for(int iy = 0;iy<lenv[1];iy++){
			
			double ixsum = 0;
			
			for(int ix = 0; ix<a;ix++){
				ixsum += iiv.getDouble(iy, ix);
			}
			
			iivdata.set(ixsum, iy);
		}
		
		IDataset xvrange = DatasetFactory.createRange(ptv[1]+lenv[1], ptv[1], -1, Dataset.FLOAT64);
		
		ILineTrace lt3 = pS.createLineTrace("background sub slice");
		lt3.setData(iivdata, xvrange);
		
		return lt3;
	}
	
	
	
	
	
}
