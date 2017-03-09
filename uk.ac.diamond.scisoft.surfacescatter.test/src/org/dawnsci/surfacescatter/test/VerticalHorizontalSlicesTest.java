package org.dawnsci.surfacescatter.test;

import static org.junit.Assert.*;

import org.dawnsci.surfacescatter.VerticalHorizontalSlices;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.widgets.Composite;
import org.junit.Test;

public class VerticalHorizontalSlicesTest {

	
	private IRectangularROI verticalSliceBounds;
	private IPlottingSystem<Composite> pS2;
	private IPlottingSystem<Composite> pS1;
	private IDataset background;
	private IDataset originalInput;
	private IROI green;
	private IRegion verticalSliceBoundsRegion;
	private IRegion greenRegion;	
	
	public void setupInputs(){
		
		
		background = (IDataset) DatasetFactory.ones(new  int[] {500,200});
		originalInput= (IDataset) DatasetFactory.ones(new  int[] {1000,2000});
		originalInput = Maths.multiply(10, originalInput);
		
		
		try {
			
			pS2 = PlottingFactory.createPlottingSystem();
			pS1 = PlottingFactory.createPlottingSystem();
			
			pS1.createPlot2D(originalInput, null,null);
			pS2.createPlot2D(background, null,null);
			
			verticalSliceBoundsRegion = pS2.createRegion("Test Vertical Slice", RegionType.XAXIS);
			greenRegion = pS1.createRegion("Region of Interest", RegionType.BOX);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		RectangularROI vertROI = new RectangularROI(100, 0, 100, 200, 0);
		verticalSliceBoundsRegion.setROI(vertROI);
		
		RectangularROI startGreenROI = new RectangularROI(100,100,500,200,0);
		greenRegion.setROI(startGreenROI);
		
		green = greenRegion.getROI();
		
		verticalSliceBounds = verticalSliceBoundsRegion.getROI().getBounds();		
	
		
	}	
		
	@Test
	public void testVerticalSlice() {
		
		setupInputs();
		
		VerticalHorizontalSlices vhs = new VerticalHorizontalSlices();
		
		@SuppressWarnings("static-access")
		ILineTrace ltTest= vhs.verticalslice(verticalSliceBounds, background, pS1, green);
		
		
		IDataset xOutput = ltTest.getXData();

		
		assertTrue((double) xOutput.getObject(0) == (double) 0);
		
		assertTrue((double) xOutput.getObject(xOutput.getShape()[0]) == (double) 200);
		
		boolean r = true;
		int y = 0;
		
		for(int t =0; t<ltTest.getYData().getShape()[0]; t++){
			y+=1;
			if((double) ltTest.getYData().getObject(t) != (double) 100){
				r = false;
				
			}
		}
		
		
		assertTrue((r == true) && (y == ltTest.getYData().getShape()[0]));
		
	}

}
