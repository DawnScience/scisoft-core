/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.osgi.preference;

import org.dawb.common.ui.widgets.LabelFieldEditor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class LoadingPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public LoadingPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	public LoadingPreferencePage(int style) {
		super(style);
	}

	public LoadingPreferencePage(String title, int style) {
		super(title, style);
	}

	public LoadingPreferencePage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "uk.ac.diamond.scisoft.analysis.osgi"));
	}

	@Override
	protected void createFieldEditors() {
		
		addField(new LabelFieldEditor("Experts only: set the regular expression for collapsing groups of images into virtual stacks know as 'lazy' datasets.\n"+
				                      "You can also ask your support representative to set the system propery:\n"+
				                      "'uk.ac.diamond.scisoft.analysis.osgi.preference.loader.stack.regexp' to the value of the regular expression for parsing data names.\n"+
				                      "This will then automatically default the stack loading.\n"+
				                      "The pattern is of the form <pattern>.<file extension> so there is no need to as a trailing '.'\n\n"+
				                      "Warning: entering an invalid reqular expression here might stop DAWN from opening files.", getFieldEditorParent()));
        addField(new StringFieldEditor(PreferenceConstants.DATASET_REGEXP, "Regular Expression for Dataset Stacks", getFieldEditorParent()));
	}

}
