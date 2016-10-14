package org.dawnsci.surfacescatter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.january.DatasetException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class OutputCurves extends Composite {

    private IPlottingSystem<Composite> plotSystem4;
    private IRegion imageNo;
    private ILineTrace lt;
    private ExampleModel model;
    private DataModel dm;
    
    
    public OutputCurves(Composite parent, int style, ArrayList<ExampleModel> models,  ArrayList<DataModel> dms, SuperModel sm) {
        super(parent, style);
        
        new Label(this, SWT.NONE).setText("Output Curves");
        
        this.model = models.get(sm.getSelection());
        this.dm = dms.get(sm.getSelection());
        try {
			plotSystem4 = PlottingFactory.createPlottingSystem();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        this.createContents(model); 

        
    }
     
    public void createContents(ExampleModel model) {
    	
    	final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        setLayout(gridLayout);
        
        ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);;
        
        final GridData gd_secondField = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_secondField.grabExcessVerticalSpace = true;
        gd_secondField.grabExcessVerticalSpace = true;
        gd_secondField.heightHint = 100;
        
        plotSystem4.createPlotPart(this, "ExamplePlot", actionBarComposite, PlotType.IMAGE, null);
        
        
		
		lt = plotSystem4.createLineTrace("Output Curve");
		if (dm.getyList() == null || dm.getxList() == null ){
			lt.setData(dm.backupDataset(), dm.backupDataset());
		}
		else{
			lt.setData(dm.yIDataset(), dm.xIDataset());
		}	
			
		plotSystem4.addTrace(lt);
		try {
			imageNo = plotSystem4.createRegion("Image", RegionType.XAXIS_LINE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		plotSystem4.getPlotComposite().setLayoutData(gd_secondField);
    
    }
		
    
    public Composite getComposite(){
   	
   	return this;
   }
   
   public IPlottingSystem<Composite> getPlotSystem(){
	   return plotSystem4;
   }
   
   public IRegion getRegionNo(){
	   return imageNo;
   }
   
   public void resetCurve(){

		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
			   plotSystem4.clear();
			   lt = plotSystem4.createLineTrace("Output Curve");
			   lt.setData(dm.backupDataset(), dm.backupDataset());
			   plotSystem4.addTrace(lt);
			   plotSystem4.repaint();
			}
		});
	   
   }
   
   public void updateCurve(){
	   
	   if (lt.getDataName() == null){
		   lt = plotSystem4.createLineTrace("Output Curve");
	   }
	   
	   if (dm.getyList() == null || dm.getxList() == null ){
			lt.setData(dm.backupDataset(), dm.backupDataset());
		}
		else{
			lt.setData(dm.xIDataset(), dm.yIDataset());
		}
	   plotSystem4.clear();
	   plotSystem4.addTrace(lt);
	   plotSystem4.repaint();
	   
   }
   

   
   
}

