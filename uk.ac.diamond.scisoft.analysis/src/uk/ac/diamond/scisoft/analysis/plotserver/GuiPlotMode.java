/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class GuiPlotMode implements Serializable {
	private String param;

	 // Ordinal of next parameter to be created
	private static int nextOrdinal = 0;

	// Assign an ordinal to this parameter
	private final int ordinal = nextOrdinal++;

	private static HashMap<String, GuiPlotMode> namesToObjects = new HashMap<String, GuiPlotMode>();

	private GuiPlotMode(String parameter) {
		param = parameter;
		namesToObjects.put(parameter, this);
	}

	@Override
	public String toString() {
		return param;
	}

	@Override
	public final boolean equals(Object that) {
		return ordinal == ((GuiPlotMode) that).ordinal;
    }

	@Override
	public final int hashCode() {
		return ordinal;
    }
	

	/**
	 * Provide enum like valueOf method.
	 * @param name String value of GuiPlotMode
	 * @return the matching GuiPlotMode object, or <code>null</code> if no matching GuiPlotMode found
	 */
	public static GuiPlotMode valueOf(String name) {
		return namesToObjects.get(name);
	}	

	/**
	 * Provide enum like values method.
	 * @return the set of all GuiPlotMode objects
	 */
	public static GuiPlotMode[] values() {
		Collection<GuiPlotMode> values = namesToObjects.values();
		return values.toArray(new GuiPlotMode[namesToObjects.size()]);
	}
	
	
	/**
	 * specify 1D plotting mode
	 */
	public final static GuiPlotMode ONED = new GuiPlotMode("ONED");

	/**
	 * specify 1D_3D plotting mode
	 */
	public final static GuiPlotMode ONED_THREED = new GuiPlotMode("ONED_THREED");
	
	/**
	 * specify 2D plotting mode
	 */
	public final static GuiPlotMode TWOD = new GuiPlotMode("TWOD");
	
	/**
	 * specify SURF2D plotting mode
	 */
	public final static GuiPlotMode SURF2D = new GuiPlotMode("SURF2D");

	/**
	 * specify SCATTER2D plotting mode
	 */

	public final static GuiPlotMode SCATTER2D = new GuiPlotMode("SCATTER2D");
	
	/**
	 * specify SCATTER3D plotting mode
	 */

	public final static GuiPlotMode SCATTER3D = new GuiPlotMode("SCATTER3D");
	
	/**
	 * specify MULTI2D plotting mode
	 */
	public final static GuiPlotMode MULTI2D = new GuiPlotMode("MULTI2D");

	/**
	 * specify Image explorer plotting mode
	 */	
	public final static GuiPlotMode IMGEXPL = new GuiPlotMode("IMGEXPL");
	
	/**
	 * specify VOLUME plotting mode
	 */
	public final static GuiPlotMode VOLUME = new GuiPlotMode("VOLUME");

	/**
	 * specify empty / clear plotting mode
	 */	
	public final static GuiPlotMode EMPTY = new GuiPlotMode("EMPTY");
}
