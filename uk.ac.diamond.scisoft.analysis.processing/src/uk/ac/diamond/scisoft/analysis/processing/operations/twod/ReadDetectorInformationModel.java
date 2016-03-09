/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class ReadDetectorInformationModel extends AbstractOperationModel {

	private boolean readGeometry = true;
	private boolean readMask = true;
	
	public boolean isReadGeometry() {
		return readGeometry;
	}
	public void setReadGeometry(boolean readGeometry) {
		firePropertyChange("readGeometry", this.readGeometry, this.readGeometry = readGeometry);
	}
	public boolean isReadMask() {
		return readMask;
	}
	public void setReadMask(boolean readMask) {
		firePropertyChange("readMask", this.readMask, this.readMask = readMask);
	}
}
