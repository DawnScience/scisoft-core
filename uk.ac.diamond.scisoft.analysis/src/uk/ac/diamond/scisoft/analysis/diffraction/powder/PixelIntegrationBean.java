/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelIntegrationBean {

	private int nBinsAzimuthal = -1;
	private int nBinsRadial = -1;
	private boolean to1D = true;
	private boolean azimuthalIntegration = true;
	private boolean usePixelSplitting = false;
	private boolean isLog = false;
	private double[] azimuthalRange = null;
	private double[] radialRange = null;
	private XAxis xAxis = XAxis.Q;
	
	public int getNumberOfBinsAzimuthal() {
		return nBinsAzimuthal;
	}

	public void setNumberOfBinsAzimuthal(int nBinsAzimuthal) {
		this.nBinsAzimuthal = nBinsAzimuthal;
	}

	public int getNumberOfBinsRadial() {
		return nBinsRadial;
	}

	public void setNumberOfBinsRadial(int nBinsRadial) {
		this.nBinsRadial = nBinsRadial;
	}

	public boolean isTo1D() {
		return to1D;
	}

	public void setTo1D(boolean to1d) {
		to1D = to1d;
	}

	public boolean isAzimuthalIntegration() {
		return azimuthalIntegration;
	}

	public void setAzimuthalIntegration(boolean azimuthalIntegration) {
		this.azimuthalIntegration = azimuthalIntegration;
	}

	public boolean isUsePixelSplitting() {
		return usePixelSplitting;
	}

	public void setUsePixelSplitting(boolean usePixelSplitting) {
		this.usePixelSplitting = usePixelSplitting;
	}

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

	public double[] getAzimuthalRange() {
		return azimuthalRange;
	}

	public void setAzimuthalRange(double[] azimuthalRange) {
		
		if (azimuthalRange == null) {
			this.azimuthalRange = null;
			return;
		}
		
		double[] range = azimuthalRange.clone();
		
		if (range[0] > range[1]) {
			Arrays.sort(range);
		}
		
		if (range[0] > range[1]) throw new IllegalArgumentException("Start angle must be smaller than end");
		if ((range[1] - range[0]) > 360) throw new IllegalArgumentException("Range must be <= 360");
		
		this.azimuthalRange = range;
	}

	public double[] getRadialRange() {
		return radialRange;
	}

	public void setRadialRange(double[] radialRange) {
		
		if (radialRange == null) {
			this.radialRange = null;
			return;
		}
		
		this.radialRange = radialRange;
		
		if (xAxis == XAxis.RESOLUTION) {
			this.radialRange[0] = (2*Math.PI)/this.radialRange[0];
			this.radialRange[1] = (2*Math.PI)/this.radialRange[1];
		}
	
		Arrays.sort(this.radialRange);
	}

	public XAxis getxAxis() {
		return xAxis;
	}

	public void setxAxis(XAxis xAxis) {
		this.xAxis = xAxis;
	}



	@Override
	public PixelIntegrationBean clone() {
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.nBinsAzimuthal = nBinsAzimuthal;
		bean.nBinsRadial = nBinsRadial;
		bean.to1D = to1D;
		bean.azimuthalIntegration = azimuthalIntegration;
		bean.usePixelSplitting = usePixelSplitting;
		bean.isLog = isLog;
		bean.azimuthalRange = azimuthalRange != null ? azimuthalRange.clone() : null;
		bean.radialRange = radialRange != null ? radialRange.clone() : null;
		bean.xAxis = xAxis;
		
		return bean;
	}
	
}
