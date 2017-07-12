package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class OverlapDisplayObjects {

	private TableItem tb;
	private String label;
	private Text textCorrected;
	private Text textRaw;
	private Text textFhkl;
	private int odoNumber;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private double textCorrectedContent;
	private double textRawContent;
	private double textFhklContent;
	private OverlapAttenuationObject oAo;
	private boolean modified = false;
	private boolean buttonPushed = false;
	private Button resetOverlap;
	
	
	public OverlapDisplayObjects generateFromOdmAndTable(OverlapDataModel odm,
														int i,
														Table table){
		
		 this.odoNumber = i;
		 
		 OverlapDisplayObjects odo = new OverlapDisplayObjects();
		 
		 label = "Overlap: " + i;
	 
		 textCorrectedContent = (odm.getAttenuationFactor());
	 
		 textRawContent = (odm.getAttenuationFactorRaw());
		 
		 textFhklContent = (odm.getAttenuationFactorFhkl());

	    
		return odo;
	}
	
	public void addResetListener(Button button){
		
		resetOverlap = button;
		  
	    resetOverlap.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				modified = false;
				buttonPushed =true;
				firePropertyChange("modified", 
						OverlapDisplayObjects.this.modified, 
						OverlapDisplayObjects.this.modified = true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	
	}
	
	
	public void buildTextCorrected(Text text){
		
		this.textCorrected = text;
		
	    textCorrected.setText(String.valueOf(textCorrectedContent));
		
	    textCorrected.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				modified = true;
				textCorrectedContent = Double.valueOf(textCorrected.getText());
				
			}
		});
		
	}
	
	
	public void buildTextRaw(Text text){
		
		this.textRaw = text;
		
	    textRaw.setText(String.valueOf(textRawContent));
		
	    textRaw.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				textRawContent = Double.valueOf(textRaw.getText());
				modified = true;
					
			}
		});
		
	}
	
	public void buildTextFhkl(Text text){
		
		this.textFhkl = text;
		
	    textFhkl.setText(String.valueOf(textFhklContent));
		
	    textFhkl.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				textFhklContent = Double.valueOf(textFhkl.getText());
				modified = true;
					
			}
		});
		
	}	
	
	public double getTextCorrectedContentAsDoubleForBuilding(){
		double w =  (textCorrectedContent);
		return w;
	}
	
	public double getTextRawContentAsDoubleForBuilding(){
		double w =  (textRawContent);
		return w;
	}
	
	public double getTextFhklContentAsDoubleForBuilding(){
		double w =  (textFhklContent);
		return w;
	}
	

	public double getTextCorrectedContentAsDoubleForUpdating(){
		double w =  Double.valueOf(textCorrected.getText());
		return w;
	}
	
	public double getTextRawContentAsDoubleForUpdating(){
		double w =  Double.valueOf(textRaw.getText());
		return w;
	}
	
	public double getTextFhklContentAsDoubleForUpdating(){
		double w =  Double.valueOf(textFhkl.getText());
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

	public void setLabel(String label) {
		this.label = label;
	}

	public Text getTextCorrected() {
		return textCorrected;
	}
	
	public OverlapAttenuationObject getOAo(){
		if(oAo == null){
			oAo = new OverlapAttenuationObject();
			
		
			oAo.setAttenuationFactorCorrected(getTextCorrectedContentAsDoubleForBuilding());
			oAo.setAttenuationFactorRaw(getTextRawContentAsDoubleForBuilding());
			oAo.setAttenuationFactorFhkl(getTextFhklContentAsDoubleForBuilding());
				
			oAo.setOdoNumber(odoNumber);
			oAo.setModified(modified);
		}
		else{
			oAo.setAttenuationFactorCorrected(getTextCorrectedContentAsDoubleForUpdating());
			oAo.setAttenuationFactorRaw(getTextRawContentAsDoubleForUpdating());
			oAo.setAttenuationFactorFhkl(getTextFhklContentAsDoubleForUpdating());
			
			oAo.setModified(modified);
		}
		
		return oAo;
	}


	public void setTextCorrected(Text text) {
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

	public Button getResetOverlap() {
		return resetOverlap;
	}

	public void setResetOverlap(Button resetOverlap) {
		this.resetOverlap = resetOverlap;
	}
	
	public Text getTextRaw() {
		return textRaw;
	}

	public void setTextRaw(Text textRaw) {
		this.textRaw = textRaw;
	}

	public Text getTextFhkl() {
		return textFhkl;
	}

	public void setTextFhkl(Text textFhkl) {
		this.textFhkl = textFhkl;
	}

	public boolean isButtonPushed() {
		return buttonPushed;
	}

	public void setButtonPushed(boolean buttonPushed) {
		this.buttonPushed = buttonPushed;
	}
	public double getTextCorrectedContent() {
		return textCorrectedContent;
	}

	public void setTextCorrectedContent(double textCorrectedContent) {
		this.textCorrectedContent = textCorrectedContent;
	}

	public double getTextRawContent() {
		return textRawContent;
	}

	public void setTextRawContent(double textRawContent) {
		this.textRawContent = textRawContent;
	}

	public double getTextFhklContent() {
		return textFhklContent;
	}

	public void setTextFhklContent(double textFhklContent) {
		this.textFhklContent = textFhklContent;
	}

}
