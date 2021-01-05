/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

/**
 * Split pixel over eight voxels with weight determined by exp(-log(2)*(distance/hm))
 */
public class ExponentialSplitter extends InverseSplitter {
	private double f;

	public ExponentialSplitter(double hm) {
		f = Math.log(2) / hm;
	}

	@Override
	public ExponentialSplitter clone() {
		ExponentialSplitter c = new ExponentialSplitter(1);
		c.f = this.f;
		return c;
	}

	@Override
	protected double calcWeight(double ds) {
		return Math.exp(-Math.sqrt(ds)*f);
	}
}