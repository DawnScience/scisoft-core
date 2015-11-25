/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;

/**
 * A factory to produce {@link ViewerDropAdapter} objects.
 * <p>
 * When the drop support needs to have some knowledge of things outside the
 * table, a factory class can be created that produces a valid class when the
 * {@link Viewer} is finally known.  
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public interface ViewerDropAdapterFactory {

	public ViewerDropAdapter get(Viewer v);
	
}
