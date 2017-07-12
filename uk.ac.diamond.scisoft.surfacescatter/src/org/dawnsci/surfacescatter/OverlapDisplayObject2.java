package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class OverlapDisplayObject2 {


	private TableItem tb;
	private String label;
	private String textCorrected;
	private String textRaw;
	private String textFhkl;
	private int odoNumber;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String textCorrectedContent;
	private String textRawContent;
	private String textFhklContent;
	private OverlapAttenuationObject oAo;
	private boolean modified = false;
	private boolean buttonPushed = false;
	private Button resetOverlap;
	private Button go;
	
	
	public OverlapDisplayObject2 generateFromOdmAndTable(OverlapDataModel odm,
														int i,
														Table table){
		
		 this.odoNumber = i;
		
		 OverlapDisplayObject2 odo = new OverlapDisplayObject2();
		
//		 tb = new TableItem(table, SWT.NONE);
		 
//		 TableEditor editorLabel = new TableEditor(table);
		 
		 
//		 label = ("Overlap: " + i);
//		 editorLabel.grabHorizontal = true;
//	     editorLabel.setEditor(label, tb, 0);
//		 
//		 TableEditor editorTextCorrected = new TableEditor(table);
		 
		 textCorrectedContent =String.valueOf(odm.getAttenuationFactor());
		 
	     textCorrected = (textCorrectedContent);
//	     editorTextCorrected.grabHorizontal = true;
//	     editorTextCorrected.setEditor(textCorrected, tb, 1);
//		
//	     TableEditor editorTextRaw = new TableEditor(table);
//		 textRaw = new Text(table, SWT.NONE);
		 
		 textRawContent =String.valueOf(odm.getAttenuationFactorRaw());
		 
	     textRaw  = (textRawContent);
//	     editorTextRaw.grabHorizontal = true;
//	     editorTextRaw.setEditor(textRaw, tb, 2);
//		
//	     TableEditor editorTextFhkl = new TableEditor(table);
//		 textFhkl = new Text(table, SWT.NONE);
		 
		 textFhklContent =String.valueOf(odm.getAttenuationFactorFhkl());
		 
	     textFhkl = (textFhklContent);
//	     editorTextFhkl.grabHorizontal = true;
//	     editorTextFhkl.setEditor(textFhkl, tb, 3);
		
//	     
//	     textCorrected.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				modified = true;
//					
//			}
//		});
//	     
//	     
//	    textRaw.addModifyListener(new ModifyListener() {
//				
//				@Override
//				public void modifyText(ModifyEvent e) {
//					modified = true;
//	
//				}
//		});
//	    
//	    textFhkl.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				modified = true;
//							
//			}
//		});
	    
//	    TableEditor editorResetButton = new TableEditor(table);
//	    resetOverlap = new Button(null, SWT.PUSH);
//		 
//	    resetOverlap.setText("Reset Overlap");
//		
//	    editorResetButton.grabHorizontal = true;
//	    editorResetButton.setEditor(resetOverlap, tb, 4);
//		
	    

	    
		return odo;
	}
	
	public double getTextCorrectedContentAsDouble(){
		double w =  Double.valueOf(textCorrected);
		return w;
	}
	
	public double getTextRawContentAsDouble(){
		double w =  Double.valueOf(textRaw);
		return w;
	}
	
	public double getTextFhklContentAsDouble(){
		double w =  Double.valueOf(textFhkl);
		return w;
	}
	
	
	public TableItem getTb() {
		return tb;
	}


	public void setTb(TableItem tb) {
		this.tb = tb;
	}


	public String getLabel() {
		return label;
	}


	public void addResetListener(Button button){
		
		resetOverlap = button;
		  
	    resetOverlap.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				modified = false;
				buttonPushed =true;
				firePropertyChange("modified", 
						OverlapDisplayObject2.this.modified, 
						OverlapDisplayObject2.this.modified = true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	
	}
	
	
//	public void setLabel(Label label) {
//		this.label = label;
//	}


	public String getTextCorrected() {
		return textCorrected;
	}
	
	public OverlapAttenuationObject getOAo(){
		if(oAo == null){
			oAo = new OverlapAttenuationObject();
		}	
		
		oAo.setAttenuationFactorCorrected(getTextCorrectedContentAsDouble());
		oAo.setAttenuationFactorRaw(getTextRawContentAsDouble());
		oAo.setAttenuationFactorFhkl(getTextFhklContentAsDouble());
			
		oAo.setOdoNumber(odoNumber);
		oAo.setModified(modified);
		
		return oAo;
	}


	public void setTextCorrected(String text) {
		modified = true;
		this.textCorrected = text;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

	public int getOdoNumber() {
		return odoNumber;
	}

	public void setOdoNumber(int odoNumber) {
		this.odoNumber = odoNumber;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	private void modifiedText(){
		
		oAo = new OverlapAttenuationObject();
		oAo.setOdoNumber(odoNumber);
		
		double r =  getTextCorrectedContentAsDouble();
		double s =  getTextRawContentAsDouble();
		double t =  getTextFhklContentAsDouble();
		
		oAo.setAttenuationFactorCorrected(r);
		oAo.setAttenuationFactorRaw(s);
		oAo.setAttenuationFactorFhkl(t);
		
		oAo.setModified(true);
		
	}

	public Button getResetOverlap() {
		return resetOverlap;
	}

	public void setResetOverlap(Button resetOverlap) {
		this.resetOverlap = resetOverlap;
	}
	
	public String getTextRaw() {
		return textRaw;
	}

	public void setTextRaw(String textRaw) {
		modified = true;
		this.textRaw = textRaw;
	}

	public String getTextFhkl() {
		return textFhkl;
	}

	public void setTextFhkl(String textFhkl) {
		modified = true;
		this.textFhkl = textFhkl;
	}

	public boolean isButtonPushed() {
		return buttonPushed;
	}

	public void setButtonPushed(boolean buttonPushed) {
		this.buttonPushed = buttonPushed;
	}

}
