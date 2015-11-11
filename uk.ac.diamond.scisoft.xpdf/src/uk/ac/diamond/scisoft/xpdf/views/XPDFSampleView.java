package uk.ac.diamond.scisoft.xpdf.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class XPDFSampleView extends ViewPart {
	public XPDFSampleView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		// Overall composite of the view
		Composite sampleTableCompo = new Composite(parent, SWT.BORDER);
		sampleTableCompo.setLayout(new FormLayout());
		
		// Table viewer to hold the main data table
		final TableViewer sampleTV = new TableViewer(sampleTableCompo);
//		FormData sampleTVData = new FormData(800, 600);
		Button cifButton = new Button(sampleTableCompo, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(sampleTableCompo);
		formData.top = new FormAttachment(sampleTV.getControl());
		cifButton.setLayoutData(formData);
		Button eraButton = new Button(sampleTableCompo, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(sampleTableCompo);
		formData.top = new FormAttachment(cifButton);
		eraButton.setLayoutData(formData);
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
