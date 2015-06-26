/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class AverageSectionsOfFastestModel extends AbstractOperationModel {

	Integer start;
	Integer stop;
	Integer number;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		firePropertyChange("start", this.start, this.start = start);
	}
	public Integer getStop() {
		return stop;
	}
	public void setStop(Integer stop) {
		firePropertyChange("stop", this.stop, this.stop = stop);
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		firePropertyChange("step", this.number, this.number = number);
	}
	
	
}
