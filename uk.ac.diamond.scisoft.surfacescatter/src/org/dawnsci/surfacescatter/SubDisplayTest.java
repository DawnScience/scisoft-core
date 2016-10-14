package org.dawnsci.surfacescatter;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

public class SubDisplayTest {
	
	private Shell shell;
	
	public IPlottingSystem<Composite> subDisplayTest(Slider slider, IPlottingSystem<Composite> plotSystem
			,ActionBarWrapper actionBarComposite, ILazyDataset aggDat, SliceND slice) {
        //Display display = new Display();
        shell = new Shell(Display.getCurrent());
        shell.setLayout(new GridLayout(1, false));
        // Create the layout.
        
        
        
        GridData gridData = new GridData();
        gridData.verticalAlignment = SWT.TOP;
        gridData.verticalSpan = 1;
        gridData.grabExcessHorizontalSpace = true;
        
        
        slider.setMinimum(0);
	    slider.setMaximum(aggDat.getShape()[0]);
	    slider.setIncrement(1);
	    slider.setThumb(1);
        slider.setLayoutData(gridData);
        
        slider.addSelectionListener(new SelectionListener() {
			
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				int selection = slider.getSelection();
    				
    			    try {
        	
    			    	//SliceND slice = new SliceND(filenames.getShape());
    					slice.setSlice(0, selection, selection+1, 1);
    					IDataset i = aggDat.getSlice(slice);
    					i.squeeze();
    					plotSystem.createPlot2D(i, null, null);
    				
    			    } 
    			    catch (Exception f) {
    					// TODO Auto-generated catch block
    					f.printStackTrace();
    				}
    			}
    			
    			@Override
    			public void widgetDefaultSelected(SelectionEvent e) {
    			    try {
    			    } 
    			    catch (Exception f) {
    					// TODO Auto-generated catch block
    					f.printStackTrace();
    				}
    			}
    			});
        // Optionally set layout fields.
        
        GridData gridData1 = new GridData();
        gridData1.verticalAlignment = SWT.FILL;
        gridData1.grabExcessHorizontalSpace = true;
        

        
    
        // Create the children of the composite.
        shell.pack();
		return plotSystem;
        
        
        }
	
	public void open(){
		shell.open();
		
	}
}



