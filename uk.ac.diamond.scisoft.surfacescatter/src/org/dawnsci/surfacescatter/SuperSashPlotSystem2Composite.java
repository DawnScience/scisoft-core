package org.dawnsci.surfacescatter;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class SuperSashPlotSystem2Composite extends Composite{

	private IPlottingSystem<Composite> plotSystem1;/////top plot
    private IPlottingSystem<Composite> plotSystem2;/////main image
    private IPlottingSystem<Composite> plotSystem3;/////side image
    private IRegion verticalSlice;
    private IRegion horizontalSlice;
    private IDataset image2;
    private SashForm right; 
    private SashForm left;
    private Button backgroundButton;

	public SuperSashPlotSystem2Composite(Composite parent, int style) throws Exception {
        super(parent, style);
        
        new Label(this, SWT.NONE).setText("Region of Interest");
        
        if(image2 == null){
        	image2 = (DatasetFactory.ones(new int[] {4,4}));
        }
        
        
        try {
        	plotSystem1 = PlottingFactory.createPlottingSystem();
			plotSystem2 = PlottingFactory.createPlottingSystem();
			plotSystem3 = PlottingFactory.createPlottingSystem();
        } 
        catch (Exception e2) {
			e2.printStackTrace();
		}

        this.createContents(); 
        
    }
	
	 public void createContents() throws Exception {
		 	
		SashForm sashForm= new SashForm(this, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			
		left = new SashForm(sashForm, SWT.VERTICAL);
		
		right = new SashForm(sashForm, SWT.VERTICAL);
		
		
		sashForm.setWeights(new int[]{60,40});
		

	/////////////////Left SashForm///////////////////////////////////////////////////
        Group topImage = new Group(left, SWT.NONE);
        topImage.setText("Top Image");
        GridLayout topImageLayout = new GridLayout();
        topImage.setLayout(topImageLayout);
		GridData topImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		topImage.setLayoutData(topImageData);
	
		GridData ld1 = new GridData(SWT.FILL, SWT.FILL, true, true);

		ActionBarWrapper actionBarCompositeTop = ActionBarWrapper.createActionBars(topImage, null);;
        
		plotSystem1.createPlotPart(topImage, "Top Image", actionBarCompositeTop, PlotType.IMAGE, null);
		plotSystem1.getPlotComposite().setLayoutData(ld1);
		
		plotSystem1.getAxis("X-Axis").setTitle("");
        
        Group mainImage = new Group(left, SWT.NONE);
        mainImage.setText("Main Image");
        GridLayout mainImageLayout = new GridLayout();
        mainImage.setLayout(mainImageLayout);
		GridData mainImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		mainImage.setLayoutData(mainImageData);
		
		GridData ld2 = new GridData(SWT.FILL, SWT.FILL, true, true);

		ActionBarWrapper actionBarCompositeMain = ActionBarWrapper.createActionBars(mainImage, null);;
        
		plotSystem2.createPlotPart(mainImage, "Top Image", actionBarCompositeMain, PlotType.IMAGE, null);
		plotSystem2.getPlotComposite().setLayoutData(ld2);
		plotSystem2.createPlot2D(image2, null, null);
//		plotSystem2.getAxis("X-Axis").setTitle("");
		
		
	////////////////////////////////////////////////////////////////////////////////////
	/////////////////Right sashform//////////////////////////////////////////////////////////
//	        

	    backgroundButton = new Button(right, SWT.CHECK);
	    backgroundButton.setText("Background Display");
	    GridData gd = new GridData(GridData.CENTER, GridData.CENTER, true, false);

        backgroundButton.setLayoutData(gd);
//	        
        Group sideImage = new Group(right, SWT.NONE);
        sideImage.setText("Side Image");
        GridLayout sideImageLayout = new GridLayout();
        sideImage.setLayout(sideImageLayout);
		GridData sideImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		sideImage.setLayoutData(sideImageData);
	    
		GridData ld3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		
		ActionBarWrapper actionBarCompositeSide= ActionBarWrapper.createActionBars(sideImage, null);;
        
		plotSystem3.createPlotPart(sideImage, "Side Image", actionBarCompositeSide, PlotType.IMAGE, null);
		plotSystem3.getPlotComposite().setLayoutData(ld3);
		
		plotSystem3.getAxis("X-Axis").setTitle("");
	

	//////////////////////////////////////////////////////////////////////////////////////////////

        left.setWeights(new int[]{40,60});         
		right.setWeights(left.getWeights());  
        
		
		try {
			verticalSlice = plotSystem2.createRegion("Vertical Slice", RegionType.XAXIS);
			
			int[] ad = image2.getShape();
			
			
	 
			
			horizontalSlice = plotSystem2.createRegion("Horizontal Slice", RegionType.YAXIS);
			
			RectangularROI horizROI = new RectangularROI(0,(int) Math.round(ad[1]/2),ad[0],(int) Math.round(ad[1]*0.1),0);
			horizontalSlice.setROI(horizROI);

			RectangularROI vertROI = new RectangularROI((int) Math.round(ad[0]/2),0,(int) Math.round(ad[0]*0.1),ad[1],0);
			verticalSlice.setROI(vertROI);
			plotSystem2.addRegion(horizontalSlice);
			plotSystem2.addRegion(verticalSlice);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   }
	   
	   public Composite getComposite(){   	
	   	return this;
	   }
	   
	   public IPlottingSystem<Composite> getPlotSystem1(){
		   return plotSystem1;
	   }
	   
	   
	   public IPlottingSystem<Composite> getPlotSystem2(){
		   return plotSystem2;
	   }
	   
	   public IPlottingSystem<Composite> getPlotSystem3(){
		   return plotSystem3;
	   }

	   public IDataset getImage(){
		   return image2;
	   }
	   
	   public void setData(IDataset input){
		   image2 = input;
	   }
	   
	   
	   public void setImage(IDataset input){
		   plotSystem2.updatePlot2D(input,  null, null);
		   setData(input);
	   }
	   
	   public void plotSystem2Redraw(){
		   plotSystem2.repaint();
	   }
	   
		public void updateImage(IDataset image){
			plotSystem2.updatePlot2D(image, null, null);
		}
		
		
		public IRegion[] getRegions(){
			IRegion[] hv = new IRegion[] {horizontalSlice, verticalSlice};
			return hv;
		}
		
		public SashForm getRight(){
			return right;
			
		}
		
		public SashForm getLeft(){
			return left;
			
		}
		
		public Button getBackgroundButton(){
			return backgroundButton;
		}

}
