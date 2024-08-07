/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.dataset.roi;

import java.io.Serializable;

/**
 * Class for rectangular region of interest : perimeter type (with coloured edges)
 */
public class PerimeterBoxROI extends RectangularROI implements Serializable {
	private static final long serialVersionUID = -1347119035343644734L;

	/**
	 * Default square of 10 pixels
	 */
	public PerimeterBoxROI() {
		super(10, 0.0);
	}

	/**
	 * Copy constructor
	 * @param orig
	 */
	public PerimeterBoxROI(PerimeterBoxROI orig) {
		super(orig);
	}

	/**
	 * Square constructor
	 * 
	 * @param width
	 * @param angle
	 */
	public PerimeterBoxROI(double width, double angle) {
		super(width, angle);
	}

	/**
	 * @param width
	 * @param height
	 * @param angle
	 */
	public PerimeterBoxROI(double width, double height, double angle) {
		super(width, height, angle);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param width
	 * @param height
	 * @param angle
	 */
	public PerimeterBoxROI(double ptx, double pty, double width, double height, double angle) {
		super(ptx, pty, width, height, angle);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param width
	 * @param height
	 * @param angle
	 * @param clip 
	 */
	public PerimeterBoxROI(double ptx, double pty, double width, double height, double angle, boolean clip) {
		super(ptx, pty, width, height, angle, clip);
	}

	/**
	 * @return a copy
	 */
	@Override
	public PerimeterBoxROI copy() {
		return new PerimeterBoxROI(this);
	}
}
