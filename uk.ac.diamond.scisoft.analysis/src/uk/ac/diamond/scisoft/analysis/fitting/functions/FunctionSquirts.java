/*-
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

/**
 * Used to transmit functions over rmi, including abstract data sets.
 * 
 * Used as a brain dump for tool data into a place with no UI dependencies.
 */
public class FunctionSquirts implements Serializable {
	
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
		private IROI           bounds;
		private AFunction         function;
		private String            name;
		private AbstractDataset   x,y;
		private AbstractDataset[] peakFunctions;
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
		public AbstractDataset getX() {
			return x;
		}
		public void setX(AbstractDataset x) {
			this.x = x;
		}
		public AbstractDataset getY() {
			return y;
		}
		public void setY(AbstractDataset y) {
			this.y = y;
		}
		public AbstractDataset[] getPeakFunctions() {
			return peakFunctions;
		}
		public void setPeakFunctions(AbstractDataset[] peakFunctions) {
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
