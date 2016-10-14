/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dawnsci.surfacescatter;

import java.util.List;

import org.dawnsci.spectrum.ui.file.IContain1DData;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class SpectrumWizard extends Wizard {
	
	String outputPath = null;
	List<IContain1DData> outdata = null;
	List<IContain1DData> data;
	
	public SpectrumWizard() {
	}
	
	public void setData(List<IContain1DData> data) {
		this.data = data;
	}
	
	@Override
	public boolean performFinish() {
		IWizardPage page = getPages()[0];
		
		if (page instanceof ISpectrumWizardPage) { 
			outdata = ((ISpectrumWizardPage)page).process(data);
		}
		
		if (page instanceof SaveFileWizardPage) outputPath = ((SaveFileWizardPage)page).getAbsoluteFilePath();
		
		return true;
	}

	public String getOutputPath() {
		return outputPath;
	}
	
	public List<IContain1DData> getOutputData() {
		return outdata;
	}
	
}
