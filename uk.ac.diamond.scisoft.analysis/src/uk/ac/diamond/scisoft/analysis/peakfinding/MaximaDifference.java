/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

public class MaximaDifference extends AbstractPeakFinder {
	
	private final static String NAME = "Maxima Difference";
	
	@Override
	protected void setName() {
		this.name = NAME;
		
	}
	
	

}
