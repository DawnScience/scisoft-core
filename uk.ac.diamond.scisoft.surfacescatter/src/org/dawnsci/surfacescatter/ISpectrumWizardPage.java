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

public interface ISpectrumWizardPage {

	
	public List<IContain1DData> process(List<IContain1DData> dataList);
	
}
