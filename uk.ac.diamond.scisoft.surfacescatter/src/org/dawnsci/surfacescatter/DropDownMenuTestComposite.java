package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.AnalaysisMethodologies;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DropDownMenuTestComposite extends Composite {


    
	//	Display display = new Display();
//	Shell shell = new Shell(display);
	private final Combo comboDropDown0;
	private final Combo comboDropDown1;

	public DropDownMenuTestComposite (Composite parent, int style, ExampleModel model) throws Exception {

		super(parent, style);
		
		
		GridLayout gdtest = new GridLayout(2, true);
		
	    this.setLayout(gdtest);
	    
	    new Label(this, SWT.LEFT).setText("Methodology");
	    new Label(this, SWT.LEFT).setText("Fit Power");
	    comboDropDown0 = new Combo(this, SWT.DROP_DOWN | SWT.BORDER | SWT.LEFT);
	    
	    comboDropDown1 = new Combo(this, SWT.DROP_DOWN | SWT.BORDER | SWT.RIGHT);
	    //Combo comboSimple = new Combo(this, SWT.SIMPLE | SWT.BORDER);
	    
	    for(Methodology  t: AnalaysisMethodologies.Methodology.values()){
	    	comboDropDown0.add(AnalaysisMethodologies.toString(t));
	    }
	    
	    for(FitPower  i: AnalaysisMethodologies.FitPower.values()){
	    	comboDropDown1.add(String.valueOf(AnalaysisMethodologies.toInt(i)));
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
	    
	    Group boundaryBoxContents = new Group(this, SWT.NULL);
	    GridLayout gridLayoutBoundaryBox = new GridLayout();
	    gridLayoutBoundaryBox.numColumns = 2;
	    GridData gridDataBoundaryBox = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridDataBoundaryBox.minimumWidth = 50;
	    Label boundaryBoxLabel = new Label(this, SWT.NULL);
	    boundaryBoxLabel.setText("Boundary Box");
	    Text boundaryBoxText = new Text(this, SWT.SINGLE);
	    gridDataBoundaryBox.horizontalSpan = 2;
	    boundaryBoxContents.setLayout(gridLayoutBoundaryBox);
	    boundaryBoxContents.setLayoutData(gridDataBoundaryBox);
	    
	    boundaryBoxText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				model.setBoundaryBox(Integer.parseInt(boundaryBoxText.getText()));
			}
	    	
	    });
	    
	    
	}		

	public String getFitDirection(){
		return comboDropDown0.getText();
		
	}
	
	public String getFitPower(){
		return comboDropDown1.getText();
		
	}

}




