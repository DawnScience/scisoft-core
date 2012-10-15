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

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for a polyline ROI (really a list of point ROIs)
 */
public class PolylineROI extends PointROI implements Serializable, Iterable<PointROI> {
	protected List<PointROI> pts; // first element should point to this if size > 0

	/**
	 * Zero point line
	 */
	public PolylineROI() {
		super();
		pts = new ArrayList<PointROI>();
	}

	public PolylineROI(double[] start) {
		super(start);
		pts = new ArrayList<PointROI>();
		pts.add(this);
	}

	public PolylineROI(double x, double y) {
		super(x, y);
		pts = new ArrayList<PointROI>();
		pts.add(this);
	}

	@Override
	public void downsample(double subFactor) {
		spt[0] /= subFactor;
		spt[1] /= subFactor;
		for (int i = 1, imax = pts.size(); i < imax; i++) { // don't call for first point
			pts.get(i).downsample(subFactor);
		}
	}

	@Override
	public void setPoint(double[] point) {
		super.setPoint(point);
		if (pts.size() == 0) {
			pts.add(this);
		}
	}

	/**
	 * 
	 * @param point
	 */
	public void setPoint(PointROI point) {
		setPoint(point.spt);
	}

	/**
	 * Set point of polygon at index
	 * @param i index
	 * @param point
	 */
	public void setPoint(int i, PointROI point) {
		if (i == 0)
			setPoint(point.spt);
		else
			pts.set(i, point);
	}

	/**
	 * Set point of polygon at index
	 * @param i index
	 * @param point
	 */
	public void setPoint(int i, double[] point) {
		setPoint(i, new PointROI(point));
	}

	/**
	 * Add point to polygon
	 * @param point
	 */
	public void insertPoint(PointROI point) {
		if (pts.size() == 0) {
			super.setPoint(point.spt);
			pts.add(this);
		} else {
			pts.add(point);
		}
	}

	/**
	 * Add point to polygon
	 * @param point
	 */
	public void insertPoint(double[] point) {
		insertPoint(new PointROI(point));
	}

	/**
	 * Add point to polygon
	 * @param point
	 */
	public void insertPoint(int[] point) {
		insertPoint(new double[] { point[0], point[1] });
	}

	/**
	 * Add point to polygon
	 * @param x
	 * @param y
	 */
	public void insertPoint(double x, double y) {
		insertPoint(new double[] {x, y});
	}

	/**
	 * Insert point to polygon at index
	 * @param i index
	 * @param point
	 */
	public void insertPoint(int i, PointROI point) {
		if (i == 0) {
			if (pts.size() == 0) {
				setPoint(point);
			} else { // copy current and then shift
				pts.set(0, new PointROI(spt));
				spt = point.spt;
				pts.add(0, this);
			}
		} else {
			pts.add(i, point);
		}
	}

	/**
	 * Insert point to polygon at index
	 * @param i index
	 * @param point
	 */
	public void insertPoint(int i, double[] point) {
		insertPoint(i, new PointROI(point));
	}

	/**
	 * Insert point to polygon at index
	 * @param i index
	 * @param x
	 * @param y
	 */
	public void insertPoint(int i, double x, double y) {
		insertPoint(i, new double[] {x, y});
	}

	/**
	 * @return number of sides
	 */
	public int getSides() {
		final int size = pts.size();
		return size == 0 ? 0 : size - 1;
	}

	/**
	 * @return number of points
	 */
	public int getNumberOfPoints() {
		return pts.size();
	}

	/**
	 * @param i
	 * @return x value of i-th point
	 */
	public double getPointX(int i) {
		return pts.get(i).spt[0];
	}

	/**
	 * @param i
	 * @return y value of i-th point
	 */
	public double getPointY(int i) {
		return pts.get(i).spt[1];
	}

	/**
	 * @param i
	 * @return i-th point as point ROI
	 */
	public PointROI getPoint(int i) {
		return pts.get(i);
	}

	/**
	 * @return iterator over points
	 */
	@Override
	public Iterator<PointROI> iterator() {
		return pts.iterator();
	}

	@Override
	public PolylineROI copy() {
		PolylineROI croi = new PolylineROI(spt.clone());
		for (int i = 1, imax = pts.size(); i < imax; i++)
			croi.insertPoint(pts.get(i).spt.clone());

		return croi;
	}

	@Override
	public String toString() {
		/**
		 * You cannot have pts.toString() if the pts contains this! It
		 * is a recursive call. Fix to defect https://trac/scientific_software/ticket/1943
		 */
		if (pts.contains(this)) return super.toString();
		return pts.toString();
	}
}
