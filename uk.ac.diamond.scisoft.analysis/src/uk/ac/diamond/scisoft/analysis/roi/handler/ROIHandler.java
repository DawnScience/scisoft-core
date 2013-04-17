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

package uk.ac.diamond.scisoft.analysis.roi.handler;

import java.util.ArrayList;

import uk.ac.diamond.scisoft.analysis.roi.IROI;

/**
 * Abstract class for region of interest handles
 * 
 * Its super class holds the primitive IDs for handle areas
 */
abstract public class ROIHandler extends ArrayList<Integer> {
	protected IROI roi;
	protected int handle;
	protected HandleStatus status;

	/**
	 * @param handle
	 * @param size 
	 * @return handle point
	 */
	abstract public double[] getHandlePoint(int handle, int size);

	/**
	 * @param handle
	 * @param size
	 * @return anchor point for scale invariant display
	 */
	abstract public double[] getAnchorPoint(int handle, int size);

	abstract public IROI getROI();


	/**
	 * @param roi The roi to set.
	 */
	public void setROI(IROI roi) {
		this.roi = roi;
	}

	/**
	 * Set handle used and status in dragging
	 * @param handle
	 * @param dragStatus
	 */
	public void configureDragging(int handle, HandleStatus dragStatus) {
		this.handle = handle;
		status = dragStatus;
	}

	/**
	 * Reset configuration for dragging
	 */
	public void unconfigureDragging() {
		handle = -1;
		status = HandleStatus.NONE;
	}

	/**
	 * Interpret mouse dragging
	 * @param spt
	 * @param ept
	 * @return roi
	 */
	abstract public IROI interpretMouseDragging(int[] spt, int[] ept);
}
