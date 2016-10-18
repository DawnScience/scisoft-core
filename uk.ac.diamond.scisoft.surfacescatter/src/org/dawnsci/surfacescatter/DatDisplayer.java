package org.dawnsci.surfacescatter;

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

public class DatDisplayer extends Composite {
   
    private Combo comboDropDown0;
    private Button massRunner;
        
    public DatDisplayer (Composite parent, int style,
    		SuperModel sm) {
        super(parent, style);
        
        new Label(this, SWT.NONE).setText("Source Data");
        
        this.createContents(sm); 

        
    }
    
    public void createContents(SuperModel sm) {
        
        
        Group datSelector = new Group(this, SWT.NULL);
        GridLayout datSelectorLayout = new GridLayout(1, true);

	    GridData datSelectorData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    datSelectorData .minimumWidth = 50;
	    datSelector.setLayout(datSelectorLayout);
	    datSelector.setLayoutData(datSelectorData);
	    
	    
	    new Label(datSelector, SWT.LEFT).setText("select dat file");
	    
	    comboDropDown0 = new Combo(datSelector, SWT.DROP_DOWN | SWT.BORDER | SWT.LEFT);
	   	
	    massRunner = new Button(this, SWT.NULL);
	    massRunner.setText("Run all");
	    
	    for(String t: sm.getFilepaths()){
	    	comboDropDown0.add(StringUtils.substringAfterLast(t, "/"));
	    	
	    }
	    
	    
	    comboDropDown0.addSelectionListener(new SelectionListener() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	          int selection = comboDropDown0.getSelectionIndex();
	          sm.setSelection(selection);
	          System.out.println("!!!!!!!!!!!!!!selection : " + selection +"  !!!!!!!!!!!!!!!!!!!!!!!!!!1");
	        }
	    	@Override
	        public void widgetDefaultSelected(SelectionEvent e) {
	          
	        }

      });        
		
	 
	  
    
    
    }
    
   public Composite getComposite(){   	
	   return this;
   }
   
   public Combo getSelector() {
		return comboDropDown0;
	}
   
   public Button getMassRunner(){
	   return massRunner;
   }
}
   
