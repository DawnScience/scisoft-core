package org.dawnsci.surfacescatter;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class RegionSetterZoomed extends Dialog {
	
	private String[] filepaths;
	private PlotSystemComposite customComposite;
	private SuperSashPlotSystem2Composite customComposite2;
	private ArrayList<ExampleModel> models;
	private ArrayList<DataModel> dms;
	private SuperModel sm;
	private Shell parentShell;
	private SashForm right; 
	private SashForm left;
	
	public RegionSetterZoomed(Shell parentShell, int style, 
			SuperModel sm, ArrayList<DataModel> dms, 
			String[] filepaths, ArrayList<ExampleModel> models) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.parentShell = parentShell;
		this.sm = sm;
		this.dms = dms;
		this.filepaths = filepaths;
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		final Composite container = (Composite) super.createDialogArea(parent);
		
		SashForm sashForm= new SashForm(container, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			
		left = new SashForm(sashForm, SWT.VERTICAL);
		
		right = new SashForm(sashForm, SWT.VERTICAL);
		
		sashForm.setWeights(new int[]{50,50});
		
		/////////////////Left SashForm///////////////////////////////////////////////////
		Group topImage = new Group(left, SWT.NONE);
		topImage.setText("Top Image");
		GridLayout topImageLayout = new GridLayout();
		topImage.setLayout(topImageLayout);
		GridData topImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		topImage.setLayoutData(topImageData);
		
		GridData ld1 = new GridData(SWT.FILL, SWT.FILL, true, true);
		
		//////////////////////////Window 2////////////////////////////////////////////////////
				
		customComposite = new PlotSystemComposite(left, SWT.NONE, models, sm,
		PlotSystemCompositeDataSetter.imageSetter(models.get(sm.getSelection()), 0));
			
		customComposite.setLayout(new GridLayout());
		customComposite.setLayoutData(ld1);
	
		//////////////////////////////////////////////////////////

		
		Group mainImage = new Group(left, SWT.NONE);
		mainImage.setText("Main Image");
		GridLayout mainImageLayout = new GridLayout();
		mainImage.setLayout(mainImageLayout);
		GridData mainImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		mainImage.setLayoutData(mainImageData);
		
		GridData ld2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		
		ActionBarWrapper actionBarCompositeMain = ActionBarWrapper.createActionBars(mainImage, null);;
		
		
		//////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////
		
		///////////////////////////////////////////////////////////////////////////////
		/////////////////Right sashform////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
	
		///////////////////////////Window 6////////////////////////////////////////////////////
		try {
			customComposite2 = new SuperSashPlotSystem2Composite(right, SWT.NONE);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IDataset k = PlotSystem2DataSetter.PlotSystem2DataSetter1(models.get(sm.getSelection()));
		customComposite2.setData(k);
		models.get(sm.getSelection()).setCurrentImage(k);
		customComposite2.setLayout(new GridLayout());
		customComposite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//////////////////////////////////////////////////////////////////////////////////
		
///////////////////////////////////////////////////////////////////////////////////////	    
	    
	    return container;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("ExampleDialog");
	}

	@Override
	protected Point getInitialSize() {
		Rectangle rect = PlatformUI.getWorkbench().getWorkbenchWindows()[0].getShell().getBounds();
		int h = rect.height;
		int w = rect.width;
		
		return new Point((int) Math.round(0.6*w), (int) Math.round(0.8*h));
	}
	
	@Override
	  protected boolean isResizable() {
	    return true;
	}
}




