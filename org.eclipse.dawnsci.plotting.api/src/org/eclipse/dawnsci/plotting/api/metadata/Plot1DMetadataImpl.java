/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.api.metadata;

import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.PointStyle;
import org.eclipse.january.metadata.MetadataType;

public class Plot1DMetadataImpl implements Plot1DMetadata {

	private static final long serialVersionUID = 3644193851032302413L;
	private LineStyle lineStyle;
	private int lineWidth;
	private PointStyle pointStyle;
	private int pointSize;
	private int[] color;

	private String xAxisName;
	private String yAxisName;
	private String legendEntry;
	private String plotTitle;

	public Plot1DMetadataImpl() {
		this(LineStyle.SOLID, 1, PointStyle.NONE, 4); // default values in Trace
	}

	public Plot1DMetadataImpl(LineStyle lStyle, int lWidth, PointStyle pStyle, int pSize) {
		lineStyle = lStyle;
		lineWidth = lWidth;
		pointStyle = pStyle;
		pointSize = pSize;
	}

	public Plot1DMetadataImpl(Plot1DMetadata pm) {
		lineStyle = pm.getLineStyle();
		lineWidth = pm.getLineWidth();
		pointStyle = pm.getPointStyle();
		pointSize = pm.getPointSize();
		if (color != null) {
			color = color.clone();
		}
		xAxisName = pm.getXAxisName();
		yAxisName = pm.getYAxisName();
		legendEntry = pm.getLegendEntry();
		plotTitle = pm.getPlotTitle();
	}

	@Override
	public MetadataType clone() {
		return new Plot1DMetadataImpl(this);
	}

	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public PointStyle getPointStyle() {
		return pointStyle;
	}

	public void setPointStyle(PointStyle pointStyle) {
		this.pointStyle = pointStyle;
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}

	@Override
	public int[] getRGBColor() {
		return color;
	}

	public void setRGBColor(int[] color) {
		this.color = color;
	}

	@Override
	public String getXAxisName() {
		return xAxisName;
	}

	public void setXAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}

	@Override
	public String getYAxisName() {
		return yAxisName;
	}

	public void setYAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

	@Override
	public String getLegendEntry() {
		return legendEntry;
	}

	public void setLegendEntry(String legendEntry) {
		this.legendEntry= legendEntry;
	}

	@Override
	public String getPlotTitle() {
		return plotTitle;
	}

	public void setPlotTitle(String plotTitle) {
		this.plotTitle = plotTitle;
	}
}
