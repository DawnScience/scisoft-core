/*
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.EventListener;

public interface IDetectorPropertyListener extends EventListener {

	/**
	 * Fired when detector property changes.
	 * @param evt
	 */
	public void detectorPropertiesChanged(DetectorPropertyEvent evt);
}
