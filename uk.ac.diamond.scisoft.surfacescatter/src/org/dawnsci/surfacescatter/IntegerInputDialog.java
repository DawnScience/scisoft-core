/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dawnsci.surfacescatter;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class IntegerInputDialog extends Dialog {

	int min;
	int max;
	int value;
	String message;
	
	public IntegerInputDialog(Shell parentShell, int min, int max,int start, String message) {
		super(parentShell);
		this.min = min;
		this.max = max;
		this.value = start;
		this.message = message;
	}
	
	@Override
	  protected Control createDialogArea(Composite parent) {
	    Composite container = (Composite) super.createDialogArea(parent);
	    container.setLayout(new GridLayout(2, false));
	    Label label = new Label(container, SWT.WRAP);
	    label.setText(message);
	    label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,true,true));
	    
	    final Spinner spinner = new Spinner(container, SWT.None);
	    spinner.setMinimum(this.min);
	    spinner.setMaximum(this.max);
	    spinner.setSelection(value);
	    spinner.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,true,true));
	    spinner.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				value = spinner.getSelection();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
 
	    return container;
	  }

	public int getValue() {
		return value;
	}
	
	  // overriding this methods allows you to set the
	  // title of the custom dialog
	  @Override
	  protected void configureShell(Shell newShell) {
	    super.configureShell(newShell);
	    newShell.setText("Choose Value");
	  }

}
