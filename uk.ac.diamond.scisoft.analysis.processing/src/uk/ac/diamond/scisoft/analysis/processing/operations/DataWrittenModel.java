/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class DataWrittenModel extends AbstractOperationModel {
	
	private int nFramesOverwrite = -1;

	public int getnFramesOverwrite() {
		return nFramesOverwrite;
	}

	public void setnFramesOverwrite(int nFramesOverwrite) {
		firePropertyChange("nFramesOverwrite", this.nFramesOverwrite, this.nFramesOverwrite = nFramesOverwrite);
	}
}
