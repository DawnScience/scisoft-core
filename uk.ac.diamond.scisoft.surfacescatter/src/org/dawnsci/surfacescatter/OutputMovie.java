package org.dawnsci.surfacescatter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OutputMovie extends Composite {



    private IPlottingSystem<Composite> outputMovie;
    private IDataset image1;
    private Button button1;

	private MovieJob movieJob;
	private MovieProgress movieProgress;
	private ProgressMonitorDialog dialog;
	private Button outputControl;
	private boolean makeColumnsEqualWidth;
	private int time;
	private Text timeConstantText;

    
    
    public OutputMovie(Composite parent, int style
    		, DataModel dm) throws Exception {
        super(parent, style);
        //composite = new Composite(parent, SWT.NONE);

        new Label(this, SWT.NONE).setText("Output Movie");
        
        try {
        	outputMovie = PlottingFactory.createPlottingSystem();
		} 
        catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        //movie job
//        movieJob = new MovieJob();
//        movieProgress = new MovieProgress();
        this.createContents(dm); 
//        System.out.println("Test line");
        
    }
     
    public void createContents(DataModel dm) throws Exception {

    	
    	final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        setLayout(gridLayout);
        
        Group controlButtons = new Group(this, SWT.NULL);
        controlButtons.setText("Control Buttons");
        GridLayout gridLayoutButtons = new GridLayout(4, false);
        
        controlButtons.setLayout(gridLayoutButtons);
        GridData gridDataButtons = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        //gridDataButtons.horizontalSpan = 1;
        controlButtons.setLayoutData(gridDataButtons);
        
        
        
        
        Label timeConstantLabel = new Label(controlButtons, SWT.NULL);
        timeConstantLabel.setText("Time Constant /ms");
	    timeConstantText = new Text(controlButtons, SWT.SINGLE);
        button1 = new Button (controlButtons, SWT.PUSH);
        button1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        
        ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);
        outputMovie.createPlotPart(this, "Output Movie", actionBarComposite, PlotType.IMAGE, null);
        
        button1.setText("Play Movie");
        
   
    
        outputControl = new Button (controlButtons, SWT.CHECK);
        outputControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        outputControl.setText("Take Output Marker");
        
        final GridData gd_firstField = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_firstField.grabExcessVerticalSpace = true;
        gd_firstField.grabExcessVerticalSpace = true;
        gd_firstField.heightHint = 100;
        
        
        outputMovie.getPlotComposite().setLayoutData(gd_firstField);


		}
   

	public Button getOutputControl(){
		return outputControl;
	}
	
	public Button getPlayButton(){
		return button1;
	}

   public Composite getComposite(){   	
   	return this;
   }
   
   public IPlottingSystem<Composite> getPlotSystem(){
	   return outputMovie;
   }
   
   public Text getTimeConstant(){
	   
	   return timeConstantText;
   }
   
   
////////////////////////////////////////////////////////////////   
   
   
   class MovieJob extends Job {

	private List<IDataset> outputDatArray;
	private int time;

	public MovieJob() {
		super("Playing movie...");
	}

	public void setData(List<IDataset> outputDatArray) {
		this.outputDatArray = outputDatArray;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
	
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
    	for( IDataset t: outputDatArray){
    		java.util.Date date = new java.util.Date();
    	    System.out.println(date);
    		System.out.println("start:" + date);
    		System.out.println("sum: " + (Double) DatasetUtils.cast(DoubleDataset.class, t).sum());
    		//outputMovie.clear();
    		outputMovie.updatePlot2D(t, null, monitor);
    		outputMovie.repaint(true);
    		//outputMovie.repaint();
    		try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		date = new java.util.Date();
    		System.out.println("stop:" + date);
    	}
		return Status.OK_STATUS;
	}
   }

   class MovieProgress implements IRunnableWithProgress {

		private List<IDataset> outputDatArray;
		private int time;

		public void setData(List<IDataset> outputDatArray) {
			this.outputDatArray = outputDatArray;
		}

		public void setTime(int time) {
			this.time = time;
		}

		@Override
		public void run(IProgressMonitor monitor) {
			monitor.beginTask("Playing movie...", -1);

	    	for( IDataset t: outputDatArray){
	    		java.util.Date date = new java.util.Date();
	    	    System.out.println(date);
	    		System.out.println("start:" + date);
	    		System.out.println("sum: " + (Double) DatasetUtils.cast(DoubleDataset.class, t).sum());
	    		//outputMovie.clear();
	    		outputMovie.updatePlot2D(t, null, monitor);
	    		outputMovie.repaint(true);
	    		//outputMovie.repaint();
	    		try {
					TimeUnit.MILLISECONDS.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		date = new java.util.Date();
	    		System.out.println("stop:" + date);
	    	}
		}
	   }
}
