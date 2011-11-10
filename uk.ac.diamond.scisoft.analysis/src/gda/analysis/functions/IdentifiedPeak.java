/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.functions;

import java.util.List;


public class IdentifiedPeak {

	/**
	 * Class describing the parameters of a peak found using the parsing method where the first derivative is used to
	 * identify peaks.
	 * 
	 */

	private double pos,minXVal,maxXVal,area, height, FWHM;
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

	public void setFWHM(List<Double> crossings){
		if(crossings.size()<2)
			FWHM=getPos()-crossings.get(0)*2;
		else{
		// assumes that that is only 2 crossings
		FWHM = crossings.get(1).doubleValue()-crossings.get(0).doubleValue();}
		
	}

	public void setFWHM(double fWHM) {
		FWHM = fWHM;
	}

	public double getFWHM() {
		return FWHM;
	}



	@Override
	public String toString() {
		return "IdentifiedPeak [pos=" + pos + ", minXVal=" + minXVal + ", maxXVal=" + maxXVal + ", area=" + area
				+ ", height=" + height + ", FWHM=" + FWHM + ", indexOfDatasetAtMinPos=" + indexOfDatasetAtMinPos
				+ ", indexOfDatasetAtMaxPos=" + indexOfDatasetAtMaxPos + "]";
	}
	
	
	
}