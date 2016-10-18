package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.log4j.spi.LoggerFactory;
import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PlotSystem1Composite extends Composite {

//	private final static Logger logger = LoggerFactory.getLogger(PlotSystem1Composite.class);

    private IPlottingSystem<Composite> plotSystem1;
    private IDataset image1;
    private Button button; 
    private Button button1;
    private Button button2;
    private Button button3;
    private Combo comboDropDown0;
	private Combo comboDropDown1;
	private Combo comboDropDown2;
    private Text boundaryBoxText;
    private SuperModel sm;
    private DataModel dm;
    private String[] methodologies;
	
    private ExampleModel model;
    
    public PlotSystem1Composite(Composite parent, int style
    		,ArrayList<ExampleModel> models, ArrayList<DataModel> dms,
    		SuperModel sm, GeometricParametersModel gm, 
    		PlotSystemComposite customComposite, int trackingMarker) {
    	
        super(parent, style);
        new Label(this, SWT.NONE).setText("Operation Window");
        
        this.sm =sm;
        this.model = models.get(sm.getSelection());
        this.dm =dms.get(sm.getSelection());
        int correctionSelection = sm.getCorrectionSelection();
        try {
        	
			plotSystem1 = PlottingFactory.createPlottingSystem();
		} catch (Exception e2) {
			
		}

        this.createContents(model, gm, customComposite, correctionSelection, trackingMarker); 
        
    }
     
    public void createContents(ExampleModel model
    		, GeometricParametersModel gm, PlotSystemComposite customComposite,
    		int cS, int trackingMarker) {
        
        
        Group methodSetting = new Group(this, SWT.NULL);
        GridLayout methodSettingLayout = new GridLayout(2, true);
	    GridData methodSettingData = new GridData(GridData.BEGINNING);
	    methodSettingData .minimumWidth = 50;
	    methodSetting.setLayout(methodSettingLayout);
	    methodSetting.setLayoutData(methodSettingData);
	    
	    comboDropDown0 = new Combo(methodSetting, SWT.DROP_DOWN | SWT.BORDER | SWT.LEFT);
	    comboDropDown0.setText("Methodology"); 
	   	comboDropDown1 = new Combo(methodSetting, SWT.DROP_DOWN | SWT.BORDER | SWT.RIGHT);
	   	comboDropDown1.setText("Fit Power");
	   	comboDropDown2 = new Combo(methodSetting, SWT.DROP_DOWN | SWT.BORDER | SWT.RIGHT);
	   	comboDropDown2.setText("Tracker");
	    boundaryBoxText = new Text(methodSetting, SWT.SINGLE);
	    boundaryBoxText.setText("Boundary Box");

	    
	    //methodologies = new String[AnalaysisMethodologies.Methodology.values().length];
	    
	    for(Methodology  t: AnalaysisMethodologies.Methodology.values()){
	    	comboDropDown0.add(AnalaysisMethodologies.toString(t));
	    }
	    
	    for(FitPower  i: AnalaysisMethodologies.FitPower.values()){
	    	comboDropDown1.add(String.valueOf(AnalaysisMethodologies.toInt(i)));
	    }
	    
	    for(TrackerType1  i: TrackingMethodology.TrackerType1.values()){
	    	comboDropDown2.add(TrackingMethodology.toString(i));
	    }
	    
	    comboDropDown0.addSelectionListener(new SelectionListener() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	          int selection = comboDropDown0.getSelectionIndex();
	          model.setMethodology(Methodology.values()[selection]);
	        }
	    	@Override
	        public void widgetDefaultSelected(SelectionEvent e) {
	          
	        }

	      });
	    
	    comboDropDown1.addSelectionListener(new SelectionListener() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	          int selection1 = comboDropDown1.getSelectionIndex();
	          model.setFitPower(FitPower.values()[selection1]);
	        }
	    	@Override
	        public void widgetDefaultSelected(SelectionEvent e) {
	          
	        }

	      });
	    
	    comboDropDown2.addSelectionListener(new SelectionListener() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	          int selection2 = comboDropDown2.getSelectionIndex();
	          model.setTrackerType(TrackingMethodology.TrackerType1.values()[selection2]);
	        }
	    	@Override
	        public void widgetDefaultSelected(SelectionEvent e) {
	          
	        }

	      });
	    
	    boundaryBoxText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				model.setBoundaryBox(Integer.parseInt(boundaryBoxText.getText()));
			}
	    	
	    });
	    
        Group controlButtons = new Group(this, SWT.NULL);
        controlButtons.setText("Control Buttons");
        GridLayout gridLayoutButtons = new GridLayout();
        gridLayoutButtons.numColumns =2;
        controlButtons.setLayout(gridLayoutButtons);
        GridData gridDataButtons = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridDataButtons.horizontalSpan = 1;
        controlButtons.setLayoutData(gridDataButtons);
        
        button = new Button (controlButtons, SWT.PUSH);
        button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        button1 = new Button (controlButtons, SWT.PUSH);
        button1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        button2 = new Button (controlButtons, SWT.PUSH);
        button2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        button3 = new Button (controlButtons, SWT.PUSH);
        button3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);
        plotSystem1.createPlotPart(this, "ExamplePlot1", actionBarComposite, PlotType.IMAGE, null);
        
		button.setText ("Proceed?");
		SliceND slice = new SliceND(model.getDatImages().getShape());
			
		button.addListener (SWT.Selection, e -> {
			if (button.getSelection()) {
				int selection = model.getImageNumber();
				slice.setSlice(0, selection, selection+1, 1);
				IDataset j = null;
				try {
					j = model.getDatImages().getSlice(slice);
				} catch (Exception e1){
					e1.printStackTrace();
				}
				
				j.squeeze();
				IDataset output = DummyProcessingClass.DummyProcess(sm, j, model,dm, gm, customComposite, cS, selection, trackingMarker);
				plotSystem1.createPlot2D(output, null, null);
				}
			else {
			}
		});
	        
		model.addPropertyChangeListener(new PropertyChangeListener() {
			
			@SuppressWarnings("unused")
			public void widgetSelected(SelectionEvent e) {
				
				int selection = model.getImageNumber();
				
			    try {
			    	if (button.getSelection()){
			    		slice.setSlice(0, selection, selection+1, 1);
			    		IDataset i = model.getDatImages().getSlice(slice);
			    		i.squeeze();
			    		IDataset image1 = i;
						IDataset output = DummyProcessingClass.DummyProcess(sm ,i, model, dm,gm, customComposite, cS, selection, trackingMarker);
						plotSystem1.createPlot2D(output, null, null);
			    	}
				
			    } 
			    catch (Exception f) {
					
					f.printStackTrace();
				}
			}
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				int selection = model.getImageNumber();
				
			    try {
			    	if (button.getSelection()){
			    		slice.setSlice(0, selection, selection+1, 1);
			    		IDataset i = model.getDatImages().getSlice(slice);
			    		i.squeeze();
						IDataset output = DummyProcessingClass.DummyProcess(sm, i, model, dm, gm, customComposite, cS, selection, trackingMarker);
						plotSystem1.createPlot2D(output, null, null);
						plotSystem1.repaint();
			    	}
				
			    } 
			    catch (Exception f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
			}
			
		});
	       
        button1.setText("Run");
        
        button2.setText("Save Parameters");
        button3.setText("Load Parameters");
//        button2.setText("Reset Tracker");


        final GridData gd_firstField = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_firstField.grabExcessVerticalSpace = true;
        gd_firstField.grabExcessVerticalSpace = true;

        gd_firstField.grabExcessVerticalSpace = true;
        gd_firstField.grabExcessVerticalSpace = true;
        gd_firstField.heightHint = 100;
        gd_firstField.horizontalSpan = 2;

        plotSystem1.getPlotComposite().setLayoutData(gd_firstField);
        
        
		}
    
   public Composite getComposite(){   	
	   return this;
   }
   
   public IPlottingSystem<Composite> getPlotSystem(){
	   return plotSystem1;
   }

   public IDataset getImage(){
	   return image1;
   }
   
   public int[] getMethodology(){
	   
	   int[] returns = new int[3]; 
	   
	   Display.getDefault().syncExec(new Runnable() {
		      public void run() {
		    	  returns[0] = comboDropDown0.getSelectionIndex();
				   returns[1] = comboDropDown1.getSelectionIndex();
				   try{
				   returns[2] = Integer.parseInt(boundaryBoxText.getText());
				   }
				   catch(Exception e){
					returns[2] = 10;
				   }
		      }
		    });
		   
	   
	   return returns;
   }
   
   public Combo[] getCombos(){
	   return new Combo[] {comboDropDown0, comboDropDown1, comboDropDown2};
   }
   
   public void setMethodologyDropDown(String in){
	   Methodology in1 = AnalaysisMethodologies.toMethodology(in);
	   setMethodologyDropDown(in1);
   }
   
   public void setMethodologyDropDown(Methodology in){
	   for (int i =0 ; i<AnalaysisMethodologies.Methodology.values().length; i++){
			  if ( in == AnalaysisMethodologies.Methodology.values()[i]){
				  comboDropDown0.select(i);
			  }
		  }
	   }
	   
	   
	   
   
   
   
   
   public void setFitPowerDropDown(FitPower m){
		  for (int i =0 ; i<AnalaysisMethodologies.FitPower.values().length; i++){
			  if ( m == AnalaysisMethodologies.FitPower.values()[i]){
				  comboDropDown1.select(i);
			  }
		  }
   }
   
   public void setFitPowerDropDown(int in){
	   FitPower in1 = AnalaysisMethodologies.toFitPower(in);
	   setFitPowerDropDown(in1);
   }
   
   
   public void setFitPowerDropDown1(String in){
		  FitPower m = AnalaysisMethodologies.toFitPower(Integer.parseInt(in));
		  setFitPowerDropDown(m);
	}
      
	   
   public void setTrackerTypeDropDown(TrackerType1 m){
		  for (int i =0 ; i<TrackingMethodology.TrackerType1.values().length; i++){
			  if ( m == TrackingMethodology.TrackerType1.values()[i]){
				  comboDropDown2.select(i);
			  }
		  }
   }
   
   public void setTrackerTypeDropDown(String in){
	   TrackerType1 m = TrackingMethodology.toTracker1(in);
	   setTrackerTypeDropDown(m);
   }
   
   public void setBoundaryBox (int in){
	   boundaryBoxText.setText(String.valueOf(in));
   }
   

   public void setBoundaryBox (String in){
	   boundaryBoxText.setText(in);
   }
   
   public Button getRunButton(){
	   return button1;
   }
   
   public Button getSaveButton(){
	   return button2;
   }
   
   public Button getLoadButton(){
	   return button3;
   }
   
//	public Button getButton2() {
//		return button2;
//	}
	
	
	public Button getProceedButton(){
		return button;
	}
	
   
class operationJob extends Job {

	private IDataset input;
	

	public operationJob() {
		super("updating image...");
	}

	public void setData(IDataset input) {
		this.input = input;
	}


	
	protected IStatus run(IProgressMonitor monitor) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
			plotSystem1.clear();
			plotSystem1.updatePlot2D(input, null, monitor);
    		plotSystem1.repaint(true);
			}
    	
		});	
	
		return Status.OK_STATUS;
	}
   }
}
