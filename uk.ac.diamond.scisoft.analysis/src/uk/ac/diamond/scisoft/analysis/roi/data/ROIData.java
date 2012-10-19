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

import java.awt.Color;

import org.eclipse.swt.graphics.RGB;

import uk.ac.diamond.scisoft.analysis.axis.AxisValues;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.roi.ROIBase;

/**
 * Base class to contain bare essentials of region of interest data
 */
public class ROIData implements IRowData {
	protected ROIBase roi;
	protected AbstractDataset[] profileData;
	protected double profileSum;
	protected RGB plotColourRGB;
	protected AxisValues[] xAxes;

	/**
	 * @param index
	 * @param xAxis The xAxis to set.
	 */
	public void setXAxis(int index, AxisValues xAxis) {
		this.xAxes[index] = xAxis;
	}

	/**
	 * @return Returns the xAxes.
	 */
	public AxisValues[] getXAxes() {
		return xAxes;
	}

	/**
	 * @param index
	 * @return Returns the xAxis.
	 */
	public AxisValues getXAxis(int index) {
		return xAxes[index];
	}

	/**
	 * @return plot colour
	 */
	@Override
	public RGB getPlotColourRGB() {
		return plotColourRGB;
	}

	/**
	 * @return plot colour
	 */
	public Color getPlotColour() {
		return new Color(plotColourRGB.red, plotColourRGB.green, plotColourRGB.blue);
	}

	/**
	 * @param rgb
	 */
	public void setPlotColourRGB(RGB rgb) {
		plotColourRGB = rgb;
	}

	/**
	 * @param index
	 * @param profileData The profileData to set.
	 */
	public void setProfileData(int index, AbstractDataset profileData) {
		this.profileData[index] = profileData;
	}

	/**
	 * @param index
	 * @return Returns the profileData.
	 */
	public AbstractDataset getProfileData(int index) {
		return profileData[index];
	}

	/**
	 * @return Returns the profileData.
	 */
	public AbstractDataset[] getProfileData() {
		return profileData;
	}

	/**
	 * @param profileSum The profileSum to set.
	 */
	public void setProfileSum(double profileSum) {
		this.profileSum = profileSum;
	}

	/**
	 * @return Returns the profileSum.
	 */
	public double getProfileSum() {
		return profileSum;
	}

	/**
	 * @param require set true if plot required 
	 */
	@Override
	public void setPlot(boolean require) {
		roi.setPlot(require);
	}

	/**
	 * @return true if plot is enabled
	 */
	@Override
	public boolean isPlot() {
		return roi.isPlot();
	}

	/**
	 * @param roi
	 */
	public void setROI(ROIBase roi) {
		this.roi = roi;
	}

	/**
	 * @return region of interest
	 */
	public ROIBase getROI() {
		return roi;
	}
}
