/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;


import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

public interface DataSetProvider {

	public IDataset getDataSet(String name, final IMonitor monitor);

	public boolean isDataSetName(String name, IMonitor monitor);

}
