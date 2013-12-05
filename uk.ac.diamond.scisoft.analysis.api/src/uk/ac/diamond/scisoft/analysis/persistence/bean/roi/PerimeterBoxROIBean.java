/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.persistence.bean.roi;

import java.util.Arrays;

public class PerimeterBoxROIBean extends ROIBean {

	private double[] lengths; // width and height

	private double angle;   // angle in radians

	private double[] endPoint; // end point

	public PerimeterBoxROIBean(){
		type = "PerimeterBoxROI";
	}

	/**
	 * Returns the lengths (width[0] and height[1])
	 * @return double[]
	 */
	public double[] getLengths(){
		return lengths;
	}

	/**
	 * Returns the angle
	 * @return double
	 */
	public double getAngle(){
		return angle;
	}

	/**
	 * Returns the End point of the rectangle
	 * @return double[]
	 */
	public double[] getEndPoint(){
		return endPoint;
	}

	/**
	 * Set the width[0] and height[1] 
	 * @param lengths
	 */
	public void setLengths(double[] lengths){
		this.lengths = lengths;
	}

	/**
	 * Set the angle
	 * @param angle
	 */
	public void setAngle(double angle){
		this.angle = angle;
	}

	/**
	 * Set the end point of the Rectangle
	 * @param endPoint
	 */
	public void setEndPoint(double[] endPoint){
		this.endPoint = endPoint;
	}

	@Override
	public String toString(){
		return String.format("{\"type\": \"%s\", \"name\": \"%s\", \"startPoint\": \"%s\", \"endPoint\": \"%s\", \"angle\": \"%s\"}", 
				type, name, Arrays.toString(startPoint), Arrays.toString(endPoint), angle);
	}
}
