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

/**
 * ROI bean used to marshall/unmarshall to / from JSON strings <br>
 * A converter can be used to convert these bean to IROI<br> 
 * (See uk.ac.diamond.scisoft.analysis.persistence.bean.roi.ROIBeanConverter) 
 */
public class ROIBean {

	protected String type;

	protected String name;

	protected double startPoint[]; // start or centre coordinates

	public ROIBean(){
		
	}

	/**
	 * Returns the type of roibean
	 * @return type
	 */
	public String getType(){
		return type;
	}

	/**
	 * Returns the ROI name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the start (or centre) point x[0] and y[1]
	 * @return startPoint
	 */
	public double[] getStartPoint() {
		return startPoint;
	}

	/**
	 * Set the type of roibean
	 * @param type
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * Set the name
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Set the Start point of the ROI x,y coordinates with x=[0] and y=[1]
	 * @param startPoint
	 */
	public void setStartPoint(double[] startPoint){
		this.startPoint = startPoint;
	}
}
