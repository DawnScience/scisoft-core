/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.january.dataset.Dataset;

/**
 * Used to transmit functions over rmi, including abstract data sets.
 * 
 * Used as a brain dump for tool data into a place with no UI dependencies.
 */
public class FunctionSquirts implements Serializable {
	private static final long serialVersionUID = 5051827819880365431L;

	private List<IdentifiedPeak> identifiedPeaks;
	private IROI                 fitBounds;
	
	private List<Squirt> squirts;
	private Squirt       selected;

	public List<Squirt> getSquirts() {
		return squirts;
	}

	public void setSquirts(List<Squirt> squirts) {
		this.squirts = squirts;
	}

	public void addSquirt(Squirt squirt) {
		if (this.squirts==null) squirts = new ArrayList<Squirt>(7);
		squirts.add(squirt);
	}
	
	/**
	 * Data to describe one thing like a peak fit or a function fit.
	 * This thing may consist of multiple items of data.
	 */
	public static final class Squirt  implements Serializable {
		private static final long serialVersionUID = 6939920699781991404L;

		private IROI           bounds;
		private AFunction         function;
		private String            name;
		private Dataset   x,y;
		private Dataset[] peakFunctions;
		private List<IROI>     regions;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
			result = prime * result + ((function == null) ? 0 : function.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + Arrays.hashCode(peakFunctions);
			result = prime * result + ((regions == null) ? 0 : regions.hashCode());
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Squirt other = (Squirt) obj;
			if (bounds == null) {
				if (other.bounds != null)
					return false;
			} else if (!bounds.equals(other.bounds))
				return false;
			if (function == null) {
				if (other.function != null)
					return false;
			} else if (!function.equals(other.function))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (!Arrays.equals(peakFunctions, other.peakFunctions))
				return false;
			if (regions == null) {
				if (other.regions != null)
					return false;
			} else if (!regions.equals(other.regions))
				return false;
			if (x == null) {
				if (other.x != null)
					return false;
			} else if (!x.equals(other.x))
				return false;
			if (y == null) {
				if (other.y != null)
					return false;
			} else if (!y.equals(other.y))
				return false;
			return true;
		}
		public IROI getBounds() {
			return bounds;
		}
		public void setBounds(IROI bounds) {
			this.bounds = bounds;
		}
		public AFunction getFunction() {
			return function;
		}
		public void setFunction(AFunction function) {
			this.function = function;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Dataset getX() {
			return x;
		}
		public void setX(Dataset x) {
			this.x = x;
		}
		public Dataset getY() {
			return y;
		}
		public void setY(Dataset y) {
			this.y = y;
		}
		public Dataset[] getPeakFunctions() {
			return peakFunctions;
		}
		public void setPeakFunctions(Dataset[] peakFunctions) {
			this.peakFunctions = peakFunctions;
		}
		public List<IROI> getRegions() {
			return regions;
		}
		public void setRegions(List<IROI> regions) {
			this.regions = regions;
		}
	}

	public Squirt getSelected() {
		return selected;
	}

	public void setSelected(Squirt selected) {
		this.selected = selected;
	}

	public List<IdentifiedPeak> getIdentifiedPeaks() {
		return identifiedPeaks;
	}

	public void setIdentifiedPeaks(List<IdentifiedPeak> identifiedPeaks) {
		this.identifiedPeaks = identifiedPeaks;
	}

	public IROI getFitBounds() {
		return fitBounds;
	}

	public void setFitBounds(IROI fitBounds) {
		this.fitBounds = fitBounds;
	}

}
