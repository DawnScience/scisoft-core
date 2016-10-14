package org.dawnsci.surfacescatter;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PlotSystem2Composite extends Composite {



    private IPlottingSystem<Composite> plotSystem2;
    private IDataset image1;



    
    
    public PlotSystem2Composite(Composite parent, int style) throws Exception {
        super(parent, style);
        //composite = new Composite(parent, SWT.NONE);
        
        	
        if (image1==null){
        	image1 = DatasetFactory.zeros(new int[] {4,4});
        }
        
        new Label(this, SWT.NONE).setText("Region of Interest");
        
        try {
			plotSystem2 = PlottingFactory.createPlottingSystem();
		} 
        catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        

        this.createContents(); 
//        System.out.println("Test line");
        
    }
     
    public void createContents() throws Exception {

    	
    	final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        setLayout(gridLayout);
        
        ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);;
        
        plotSystem2.createPlotPart(PlotSystem2Composite.this, "ExamplePlot2", actionBarComposite, PlotType.IMAGE, null);
		
		plotSystem2.createPlot2D(image1, null, null);
		
        final GridData gd_secondField = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_secondField.grabExcessVerticalSpace = true;
        gd_secondField.grabExcessVerticalSpace = true;

        plotSystem2.getPlotComposite().setLayoutData(gd_secondField);

		}
      
   public Composite getComposite(){   	
   	return this;
   }
   
   public IPlottingSystem<Composite> getPlotSystem(){
	   return plotSystem2;
   }

   public IDataset getImage(){
	   return image1;
   }
   
//   public IRegion returnRegion(){
//	   return region;
//   }
   
   public void setData(IDataset input){
	   this.image1 = input;
   }
   
   
   public void setImage(IDataset input){
	   plotSystem2.updatePlot2D(input,  null, null);
	   
   }
   
   public void plotSystem2Redraw(){
	   plotSystem2.repaint();
   }
   
	public void updateImage(IDataset image){
		plotSystem2.updatePlot2D(image, null, null);
	}

}
