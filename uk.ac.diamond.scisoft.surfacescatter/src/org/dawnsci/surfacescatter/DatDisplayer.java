package org.dawnsci.surfacescatter;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ScrollBar;

public class DatDisplayer extends Composite {
   
    private Combo comboDropDown0;
    private Button massRunner;
    private Button selectFiles;
    private List list;
    private Group datSelector;
        
    public DatDisplayer (Composite parent, int style,
    		SuperModel sm, String[] filepaths) {
        super(parent, style);
        
        new Label(this, SWT.NONE).setText("Source Data");
        
        this.createContents(sm, filepaths); 

        
    }
    
    public void createContents(SuperModel sm, String[] filepaths) {
        
        datSelector = new Group(this, SWT.NULL);
        GridLayout datSelectorLayout = new GridLayout();

	    GridData datSelectorData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    datSelectorData .minimumWidth = 50;
	    datSelectorData .minimumHeight = 100;
	    datSelector.setLayout(datSelectorLayout);
	    datSelector.setLayoutData(datSelectorData);
	    datSelector.setText("Select Data");

	    selectFiles = new Button(datSelector, SWT.PUSH);
	    selectFiles.setText("Select Files");
	    
	    list = new List(datSelector, SWT.V_SCROLL);
	    
	    for (String i : filepaths) {
	      list.add(StringUtils.substringAfterLast(i, File.separator));
	    }
	    
	    // Scroll to the bottom
	    list.select(list.getItemCount() - 1);
	    list.showSelection();
	    

	    ScrollBar sb = list.getVerticalBar();

	    // Add one more item that shows the selection value
	    //comboDropDown0 = new Combo(datSelector, SWT.DROP_DOWN | SWT.BORDER | SWT.LEFT);
	   	
	    massRunner = new Button(datSelector, SWT.NULL);
	    massRunner.setText("Run all");
	    
//	    for(String t: sm.getFilepaths()){
//	    	comboDropDown0.add(StringUtils.substringAfterLast(t, "/"));
//	    	
//	    }
	    
	    
	    list.addSelectionListener(new SelectionListener() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	          int selection = list.getSelectionIndex();
	          sm.setSelection(selection);
//	          System.out.println("!!!!!!!!!!!!!!selection : " + selection +"  !!!!!!!!!!!!!!!!!!!!!!!!!!1");
	        }
	    	@Override
	        public void widgetDefaultSelected(SelectionEvent e) {
	          
	        }
      });
    
    }
    
   public Composite getComposite(){   	
	   return this;
   }
   
   public List getList() {
		return list;
	}
   
   
   public void setList(String[] in ) {
	   list.removeAll();
	   for (String i : in) {
		      list.add(StringUtils.substringAfterLast(i, File.separator));
		    }
	   
	   list.setSize(list.computeSize( SWT.DEFAULT, SWT.DEFAULT));
	   datSelector.setSize(datSelector.computeSize( SWT.DEFAULT,  SWT.DEFAULT));
	   this.redraw();
	}
  
   
   
   public Button getMassRunner(){
	   return massRunner;
   }
   
   public Button getSelectFiles(){
	   return selectFiles;
   }
}
   
