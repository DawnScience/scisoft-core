package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class OverlapDisplayObject {


	private TableItem tb;
	private Label label;
	private Text text;
	private int odoNumber;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String textContent;
	private OverlapAttenuationObject oAo;
	
	
	public OverlapDisplayObject generateFromOdmAndTable(OverlapDataModel odm,
														int i,
														Table table){
		
		 this.odoNumber = i;
		
		 OverlapDisplayObject odo = new OverlapDisplayObject();
		
		 TableItem tb = new TableItem(table, SWT.NONE);
		 
		 TableEditor editorLabel = new TableEditor(table);
		 
		 label = new Label(table, SWT.BORDER);
		 label.setText("Overlap: " + i);
		 editorLabel.grabHorizontal = true;
	     editorLabel.setEditor(label, tb, 0);
		 
		 TableEditor editorText = new TableEditor(table);
		 text = new Text(table, SWT.NONE);
		 
		 textContent =String.valueOf(odm.getAttenuationFactor());
		 
	     text.setText(textContent);
	     editorText.grabHorizontal = true;
	     editorText.setEditor(text, tb, 1);
		
	     text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				oAo = new OverlapAttenuationObject();
				oAo.setOdoNumber(odoNumber);
				
				double r =  getTextContentAsDouble();
				
				oAo.setAttenuationfactor(r);
				
				firePropertyChange("textContent", 
									OverlapDisplayObject.this.textContent, 
									OverlapDisplayObject.this.textContent = text.getText());
				
			}
		});
	     
		return odo;
	}
	
	public double getTextContentAsDouble(){
		double w =  Double.valueOf(text.getText());
		return w;
	}
	
	
	public TableItem getTb() {
		return tb;
	}


	public void setTb(TableItem tb) {
		this.tb = tb;
	}


	public Label getLabel() {
		return label;
	}


	public void setLabel(Label label) {
		this.label = label;
	}


	public Text getText() {
		return text;
	}
	
	public OverlapAttenuationObject getOAo(){
		return oAo;
	}


	public void setText(Text text) {
		this.text = text;
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
}
