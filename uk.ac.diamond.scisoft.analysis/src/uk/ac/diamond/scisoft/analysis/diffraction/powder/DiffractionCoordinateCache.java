/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class DiffractionCoordinateCache {
	
	private static final Map<DiffractionCoordiateCacheKey, WeakReference<Object>> CACHE = Collections.synchronizedMap(new LinkedHashMap<>(6));
	
	private static final DiffractionCoordinateCache instance = new DiffractionCoordinateCache();
	
	private DiffractionCoordinateCache() {
		
	}
	
	public static DiffractionCoordinateCache getInstance() {
		return instance;
	}
	
	public void put(IDiffractionMetadata md, XAxis axis, boolean centre, Object value) {
		DiffractionCoordiateCacheKey key = new DiffractionCoordiateCacheKey(md, axis, centre);
		CACHE.put(key, new WeakReference<Object>(value));
	}

	public Object get(IDiffractionMetadata md, XAxis axis, boolean centre) {
		DiffractionCoordiateCacheKey key = new DiffractionCoordiateCacheKey(md, axis, centre);
		WeakReference<Object> wr = CACHE.get(key);
		return wr != null ? wr.get() : null;
	}
	
	class DiffractionCoordiateCacheKey {
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((axis == null) ? 0 : axis.hashCode());
			result = prime * result + Arrays.hashCode(beamCentre);
			result = prime * result + (centre ? 1231 : 1237);
			result = prime * result + ((dce == null) ? 0 : dce.hashCode());
			result = prime * result + ((dp == null) ? 0 : dp.hashCode());
			result = prime * result + (radians ? 1231 : 1237);
			System.out.println(result);
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
			DiffractionCoordiateCacheKey other = (DiffractionCoordiateCacheKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (axis != other.axis)
				return false;
			if (!Arrays.equals(beamCentre, other.beamCentre))
				return false;
			if (centre != other.centre)
				return false;
			if (dce == null) {
				if (other.dce != null)
					return false;
			} else if (!dce.equals(other.dce))
				return false;
			if (dp == null) {
				if (other.dp != null)
					return false;
			} else if (!dp.equals(other.dp))
				return false;
			if (radians != other.radians)
				return false;
			return true;
		}

		private DetectorProperties dp;
		private DiffractionCrystalEnvironment dce;
		private XAxis axis;
		private boolean centre;
		
		private double[] beamCentre;
		private boolean radians;
		
		public DiffractionCoordiateCacheKey(IDiffractionMetadata md, XAxis axis, boolean centre) {
			dp = md.getDetector2DProperties().clone();
			dce = md.getDiffractionCrystalEnvironment().clone();
			this.axis = axis;
			this.centre = centre;
		}
		
		public DiffractionCoordiateCacheKey(double[] beamCentre, boolean centre, boolean radians) {
			this.beamCentre = beamCentre.clone();
			this.centre = centre;
			this.radians = radians;
		}

		private DiffractionCoordinateCache getOuterType() {
			return DiffractionCoordinateCache.this;
		}


		
		
		
	}

}
