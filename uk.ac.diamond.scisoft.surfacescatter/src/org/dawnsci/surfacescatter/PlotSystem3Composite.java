package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.dawnsci.plotting.api.trace.ITrace;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PlotSystem3Composite extends Composite {

    private IPlottingSystem<Composite> plotSystem3;
    private IPlottingSystem<Composite> plotSystem4;
    private IDataset image1;
    private IDataset image2;
    private ExampleModel model;
    private DataModel dm;
    
    public PlotSystem3Composite(Composite parent, int style
    		, AggregateDataset aggDat, ExampleModel model, DataModel dm) throws Exception {
        super(parent, style);
        //composite = new Composite(parent, SWT.NONE);

        this.model=model;
        this.dm = dm;
        
        new Label(this, SWT.NONE).setText("Region of Interest 3D");
        
        try {
			plotSystem3 = PlottingFactory.createPlottingSystem();
			plotSystem4 = PlottingFactory.createPlottingSystem();
		} 
        catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        this.createContents(aggDat, model, dm); 
        
    }
     
    public void createContents(AggregateDataset aggDat,
    		ExampleModel model, DataModel dm) throws Exception {

    	
    	final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        this.setLayout(gridLayout);
        
        ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);
        
        plotSystem3.createPlotPart(PlotSystem3Composite.this, "ExamplePlot2", actionBarComposite, PlotType.IMAGE, null);
		
		
		model.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				IDataset j = model.getCurrentImage();
				image1= j;
				
				plotSystem3.setPlotType(PlotType.SURFACE);
				plotSystem3.createPlot2D(j, null, null);
			}

		});
        

		
		plotSystem4.createPlotPart(PlotSystem3Composite.this, "Background Plot", actionBarComposite, PlotType.IMAGE, null);
		
		model.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			
				if (dm.getBackgroundDatArray() == null ){
					
					IDataset j = DatasetFactory.ones(new int[] {20,20});
					image2 = j;
					plotSystem4.setPlotType(PlotType.SURFACE);
					plotSystem4.createPlot2D(j, null, null);

					
					
					
				}
				else{
					IDataset j = dm.getBackgroundDatArray().get(model.getSliderPos());
					image2 = j;
					plotSystem4.setPlotType(PlotType.SURFACE);
					ITrace surf = plotSystem4.createPlot2D(j, null, null);
				}
			}
		});
        
        GridData gd_secondField = new GridData(SWT.FILL, SWT.FILL, true, true);

        gd_secondField.grabExcessVerticalSpace = true;
        gd_secondField.grabExcessVerticalSpace = true;
        
        

        
        plotSystem3.getPlotComposite().setLayoutData(gd_secondField);
        plotSystem3.createPlot2D(image1, null, null);
        
        
        plotSystem4.getPlotComposite().setLayoutData(gd_secondField);
        plotSystem4.createPlot2D(image2, null, null);
        
        
	}
    
   public Composite getComposite(){   	
	   return this;
   }
   
   public IPlottingSystem<Composite> getPlotSystem(){
	   return plotSystem3;
   }

   public IDataset getImage(){
	   return image1;
   }
   
   public void updateAll(ExampleModel model, DataModel dm, IDataset j){
	   this.model = model;
	   this.dm = dm;
	   plotSystem3.updatePlot2D(j, null, null);
	   try{
		   plotSystem4.createPlot2D(dm.getBackgroundDatArray().get(model.getSliderPos()), null, null);
	   }
	   catch (Exception en){
		   
	   }
	   
	   }

}
