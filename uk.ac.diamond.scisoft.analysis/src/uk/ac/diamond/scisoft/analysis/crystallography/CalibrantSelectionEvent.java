/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import java.util.EventObject;

public class CalibrantSelectionEvent extends EventObject {

	private String calibrant;

	public CalibrantSelectionEvent(Object source, String calibrant) {
		super(source);
		this.calibrant = calibrant;
	}

	public String getCalibrant() {
		return calibrant;
	}

	public void setCalibrant(String calibrant) {
		this.calibrant = calibrant;
	}

}
