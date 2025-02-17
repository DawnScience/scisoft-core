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

package org.eclipse.dawnsci.analysis.api.roi;

import java.io.Serializable;

import org.eclipse.january.INameable;

/**
 * Region of interest interface. A region may be one or two dimensional and may enclose an area.
 * If it is open (or does not enclose an area) then a point is consider to be inside it when it
 * lies on its outline to within the accuracy given by that point's coordinates.
 */
public interface IROI extends Serializable, INameable {

	/**
	 * Set the start (or centre) point's i-th value
	 * @param i index of dimension
	 * @param value
	 */
	public void setValue(int i, double value);


	/**
	 * @param point The start (or centre) point to set
	 */
	public void setPoint(double[] point);

	/**
	 * @param point The start (or centre) point to set
	 */
	public void setPoint(int[] point);

	/**
	 * @param x
	 * @param y
	 */
	public void setPoint(int x, int y);

	/**
	 * @param x 
	 * @param y 
	 */
	public void setPoint(double x, double y);

	/**
	 * @return Returns reference to the start (or centre) point
	 */
	public double[] getPointRef();

	/**
	 * @return Returns copy of the start (or centre) point
	 */
	public double[] getPoint();

	/**
	 * @param i index of dimension
	 * @return Returns the start (or centre) point's i-th value
	 */
	public double getValue(int i);

	/**
	 * @return Returns the start (or centre) point's x value
	 */
	public double getPointX();

	/**
	 * @return Returns the start (or centre) point's y value
	 */
	public double getPointY();

	/**
	 * @return Returns the start (or centre) point
	 */
	public int[] getIntPoint();

	/**
	 * Add an offset to start (or centre) point
	 * 
	 * @param pt
	 */
	public void addPoint(int[] pt);

	/**
	 * Add an offset to start (or centre) point
	 * 
	 * @param pt
	 */
	public void addPoint(double[] pt);

	/**
	 * Add an offset to start (or centre) point
	 * 
	 * @param x
	 * @param y
	 */
	void addPoint(double x, double y);

	/**
	 * To account for a down-sampling of the dataset, change ROI
	 * @param subFactor
	 */
	public void downsample(double subFactor);

	/**
	 * @param require set true if plot required 
	 */
	public void setPlot(boolean require);

	/**
	 * @return true if plot is enabled
	 */
	public boolean isPlot();

	/**
	 * @param fixed set true if ROI is not user-changeable
	 */
	public void setFixed(boolean fixed);

	/**
	 * @return true if ROI is not user-changeable
	 */
	public boolean isFixed();

	/**
	 * @return bounding box as rectangular ROI
	 */
	public IRectangularROI getBounds();

	/**
	 * @param x
	 * @param y
	 * @return true if given point is in ROI
	 */
	public boolean containsPoint(double x, double y);

	/**
	 * @param x
	 * @param y
	 * @param distance
	 * @return true if given point within distance of outline of ROI 
	 */
	public boolean isNearOutline(double x, double y, double distance);

	/**
	 * Calculate x values at which ROI will intersect horizontal line of given y
	 * @param y
	 * @return x values in ascending order, is null in the non-intersecting case
	 */
	public double[] findHorizontalIntersections(double y);

	/**
	 * @return a copy of ROI
	 */
	public IROI copy();

}
