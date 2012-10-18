/*
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

package uk.ac.diamond.scisoft.analysis.roi.data;

/**
 * This is a region of interest selection object for the 3D surface plotting 
 * 
 */
public class SurfacePlotROI {

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int xSamplingMode;
	private int ySamplingMode;
	private int xAspect;
	private int yAspect;
	
	public SurfacePlotROI(int startX, int startY,
			              int endX, int endY,
			              int xSamplingMode, int ySamplingMode,
			              int xAspect, int yAspect) {
		
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.xSamplingMode = xSamplingMode;
		this.ySamplingMode = ySamplingMode;
		this.xAspect = xAspect;
		this.yAspect = yAspect;
	}
	
	public int getStartX() {
		return startX; 
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getEndX() {
		return endX;
	}
	
	public int getEndY() {
		return endY;
	}
	
	public int getXSamplingMode() {
		return xSamplingMode;
	}
	
	public int getYSamplingMode() {
		return ySamplingMode;
	}
	
	public int getXAspect() {
		return xAspect;
	}
	
	public int getYAspect() {
		return yAspect;
	}
}
