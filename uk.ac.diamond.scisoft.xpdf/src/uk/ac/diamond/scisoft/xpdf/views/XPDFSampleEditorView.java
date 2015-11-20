package uk.ac.diamond.scisoft.xpdf.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class XPDFSampleEditorView extends ViewPart {

	private XPDFSampleEditorTable xSET; 
	@Override
	public void createPartControl(Composite parent) {
		xSET= new XPDFSampleEditorTable(parent, SWT.NONE);
		
	}

	@Override
	public void setFocus() {
		xSET.setFocus();
	}

}
