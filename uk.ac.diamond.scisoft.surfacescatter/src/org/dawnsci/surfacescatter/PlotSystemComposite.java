package org.dawnsci.surfacescatter;
import java.util.ArrayList;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class PlotSystemComposite extends Composite {


    final private Slider slider;
    private IPlottingSystem<Composite> plotSystem;
    private IDataset image;
    private IRegion region;
    private Button outputControl;
    private ExampleModel model;
    private DataModel dm;
    private GeometricParametersModel gm;
    private ArrayList<ExampleModel> models;
    private SuperModel sm;
    
     
    public PlotSystemComposite(Composite parent, int style
    		,ArrayList<ExampleModel> models, SuperModel sm , IDataset image) {
        super(parent, style);
        
        
        
        
        this.sm=sm;
        this.models = models;
        this.model = models.get(sm.getSelection());
        
        
        new Label(this, SWT.NONE).setText("Raw Image");
        //composite = new Composite(parent, SWT.NONE);
        slider = new Slider(this, SWT.HORIZONTAL);
        
     
        try {
			plotSystem = PlottingFactory.createPlottingSystem();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        this.createContents(model, image); 
//        System.out.println("Test line");
        
    }
     
    public void createContents(ExampleModel model, IDataset image) {

    	
    	final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        setLayout(gridLayout);
        
//        slider = new Slider(this, SWT.HORIZONTAL);
        
        slider.setMinimum(0);
	    slider.setMaximum(model.getDatImages().getShape()[0]);
	    slider.setIncrement(1);
	    slider.setThumb(1);
        
        final GridData gd_firstField = new GridData(SWT.FILL, SWT.CENTER, true, false);
        slider.setLayoutData(gd_firstField);
        
        outputControl = new Button (this, SWT.CHECK);
        outputControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        outputControl.setText("Take Output Marker");
        
        ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);;
        
        //plotSystem.createPlotPart(this, "ExamplePlot", actionBarComposite, PlotType.IMAGE, null);
        
        
        final GridData gd_secondField = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_secondField.grabExcessVerticalSpace = true;
        gd_secondField.grabExcessVerticalSpace = true;
        
         System.out.println(plotSystem.getClass());
        

        plotSystem.createPlotPart(this, "ExamplePlot", actionBarComposite, PlotType.IMAGE, null);
        plotSystem.getPlotComposite().setLayoutData(gd_secondField);
        plotSystem.createPlot2D(image, null, null);
        

        try {
			region =plotSystem.createRegion("myRegion", RegionType.BOX);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		plotSystem.addRegion(region);
		
		RectangularROI startROI = new RectangularROI(100,100,50,50,0);
		region.setROI(startROI);
 
        model.setROI(startROI);
		region.addROIListener(new IROIListener() {

			@Override
			public void roiDragged(ROIEvent evt) {
				roiStandard(evt);
				
			}

			@Override
			public void roiChanged(ROIEvent evt) {
				roiStandard(evt);
			}

			@Override
			public void roiSelected(ROIEvent evt) {
				roiStandard(evt);
			}
			
			public void roiStandard(ROIEvent evt) {
				models.get(sm.getSelection()).setROI(region.getROI());
				models.get(sm.getSelection()).setBox(startROI);
				
			}

		});
        
    }
		
   
   public int getSliderPos(){
	   int sliderPos = slider.getSelection();
	   return sliderPos;
   }
   

   
   public Composite getComposite(){
   	
   	return this;
   }
   
   public IPlottingSystem getPlotSystem(){
	   return plotSystem;
   }

   public IDataset getImage(){
	   return image;
   }
   

	public Button getOutputControl(){
		return outputControl;
	}

	public Slider getSlider(){
		return slider;
	}

	public void getBoxPosition(){
		models.get(sm.getSelection()).setROI(region.getROI());
	}
	
	public void getBoxPosition(int g){
		models.get(g).setROI(region.getROI());
	}
	
	public void setModels(ExampleModel model1){
		this.model= model1;

	}
	
	public void updateImage(IDataset image){
		plotSystem.updatePlot2D(image, null, null);
	}
	
   
	public void sliderReset(ExampleModel model1){
		slider.setMinimum(0);
	    slider.setMaximum(model1.getDatImages().getShape()[0]);
	    slider.setIncrement(1);
	    slider.setThumb(1);
	}
}
    




