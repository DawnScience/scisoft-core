/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.ptychography.rcp.preference;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;

public class PtychoPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoPreferencePage";

	public PtychoPreferencePage() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		 // Add a directory field
		DirectoryFieldEditor epiResourceFolder = new DirectoryFieldEditor(PtychoPreferenceConstants.PIE_RESOURCE_PATH,
				"PIE resource directory:", getFieldEditorParent());
		addField(epiResourceFolder);
		// Add a file field
		FileFieldEditor savedFilePath = new FileFieldEditor(PtychoPreferenceConstants.FILE_SAVE_PATH, "Parameter file saved path:",
				getFieldEditorParent());
		addField(savedFilePath);
	}

	@Override
	public void init(IWorkbench workbench) {
		IPreferenceStore store = Activator.getPtychoPreferenceStore();

		setPreferenceStore(store);
		setDescription("Preferences for the Ptychography perspective:");
	}

	/**
	 * Adjust the layout of the field editors so that
	 * they are properly aligned.
	 */
	@Override
	protected void adjustGridLayout() {
		super.adjustGridLayout();
		((GridLayout) getFieldEditorParent().getLayout()).numColumns = 3;
	}

	@Override
	protected void checkState() {
		super.checkState();
		setErrorMessage(null);
		setValid(true);
	}
}
