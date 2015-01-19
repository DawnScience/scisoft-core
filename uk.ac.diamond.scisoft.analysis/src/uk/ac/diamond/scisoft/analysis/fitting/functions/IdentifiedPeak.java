/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;
import java.util.List;

/**
 * Class describing the parameters of a peak
 */
public class IdentifiedPeak implements Serializable {
	
	public IdentifiedPeak() {
	}

	private double pos, minXVal, maxXVal, area, height, fullWidth;
	private int indexOfDatasetAtMinPos, indexOfDatasetAtMaxPos;
	
	/**
	 * Constructor for a peak identified using a peak finding algorithm.
	 * 
	 * @param position is the x value of the peak
	 * @param minXValue is the lowest x value of the peak
	 * @param maxXValue is the highest x value of the peak
	 * @param area is the area under the peak
	 * @param height is the height of the peak
	 * @param indexOfMinXVal is the position of the lowest x value in the dataset
	 * @param indexofMaxXVal is the position of the highest x value in the dataset
	 * @param crossings The x value of the points where the the y value is half height. i.e. FWHM
	 */
	public IdentifiedPeak(double position, double minXValue, double maxXValue, double area, double height, int indexOfMinXVal,
			int indexofMaxXVal,List<Double> crossings) {
		setPos(position);
		setMinXVal(minXValue);
		setMaxXVal(maxXValue);
		setArea(area);
		setHeight(height);
		setIndexOfDatasetAtMinPos(indexOfMinXVal);
		setIndexOfDatasetAtMaxPos(indexofMaxXVal);
		setFWHM(crossings);
	}

	public double getPos() {
		return pos;
	}

	public void setPos(double pos) {
		this.pos = pos;
	}

	public double getMinXVal() {
		return minXVal;
	}


	public void setMinXVal(double minXVal) {
		this.minXVal = minXVal;
	}

	public double getMaxXVal() {
		return maxXVal;
	}

	public void setMaxXVal(double maxXVal) {
		this.maxXVal = maxXVal;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setIndexOfDatasetAtMinPos(int indexOfDatasetAtMinPos) {
		this.indexOfDatasetAtMinPos = indexOfDatasetAtMinPos;
	}

	public int getIndexOfDatasetAtMinPos() {
		return indexOfDatasetAtMinPos;
	}

	public void setIndexOfDatasetAtMaxPos(int indexOfDatasetAtMaxPos) {
		this.indexOfDatasetAtMaxPos = indexOfDatasetAtMaxPos;
	}

	public int getIndexOfDatasetAtMaxPos() {
		return indexOfDatasetAtMaxPos;
	}

	public void setFWHM(List<Double> crossings) {
		if (crossings == null || crossings.size() == 0) {
			fullWidth = height / (maxXVal - minXVal);
		} else if (crossings.size() == 1) {
			fullWidth = (getPos() - crossings.get(0)) * 2;
		} else {
			// assumes that that is only 2 crossings
			fullWidth = crossings.get(1).doubleValue() - crossings.get(0).doubleValue();
		}
	}

	public void setFWHM(double fWHM) {
		fullWidth = fWHM;
	}

	public double getFWHM() {
		return fullWidth;
	}

	@Override
	public String toString() {
		return "IdentifiedPeak [pos=" + pos + ", minXVal=" + minXVal + ", maxXVal=" + maxXVal + ", area=" + area
				+ ", height=" + height + ", FWHM=" + fullWidth + ", indexOfDatasetAtMinPos=" + indexOfDatasetAtMinPos
				+ ", indexOfDatasetAtMaxPos=" + indexOfDatasetAtMaxPos + "]";
	}
}
