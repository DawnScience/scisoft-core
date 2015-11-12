/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.List;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class XPDFSampleView extends ViewPart {
	
	private TableViewer sampleTV;
	
	private List<XPDFSampleParameters> samples;
	
	private Button cifButton;
	private Button eraButton;

	public XPDFSampleView() {
	}
	
	public XPDFSampleView(Composite parent, int style) {
		createPartControl(parent);
	}

	@Override
	public void createPartControl(Composite parent) {
		// Overall composite of the view
		Composite sampleTableCompo = new Composite(parent, SWT.BORDER);
		sampleTableCompo.setLayout(new FormLayout());
		
		// Table viewer to hold the main data table
		sampleTV = new TableViewer(sampleTableCompo);
		FormData formData= new FormData(800, 600);
		sampleTV.getTable().setLayoutData(formData);
		createLoadButtons();
	}

	private void createLoadButtons() {
		int leftMargin = 10;
		int topMargin = 10;
		Composite stCompo = sampleTV.getTable().getParent();
		cifButton = new Button(stCompo, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(stCompo, leftMargin);
		formData.top = new FormAttachment(sampleTV.getControl(), topMargin);
		cifButton.setLayoutData(formData);
		cifButton.setText("New sample from CIF file");
		eraButton = new Button(stCompo, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(stCompo, leftMargin);
		formData.top = new FormAttachment(cifButton, topMargin);
		eraButton.setLayoutData(formData);
		eraButton.setText("New sample from ERA file");
	}

	@Override
	public void setFocus() {
		sampleTV.getControl().setFocus();
	}
	
	// Probably needs more eclipse wrappings
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		XPDFSampleView xSV = new XPDFSampleView(shell, SWT.NONE);
		shell.pack();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
	
}
