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

/**
 * Used to store attributes of a 1D plot line
 */
public interface Plot1DMetadata extends MetadataType {
	enum LineStyle {
		NONE,
		SOLID,
		DASHED,
	}

	/**
	 * @return style of line
	 */
	LineStyle getLineStyle();

	/**
	 * @return width of line
	 */
	int getLineWidth();

	/**
	 * @return style of point
	 */
	PointStyle getPointStyle();

	/**
	 * @return size of point
	 */
	int getPointSize();

	/**
	 * @return RGB array
	 */
	int[] getRGBColor();

	/**
	 * @return entry in plot legend
	 */
	String getLegendEntry();

	/**
	 * @return title of plot
	 */
	String getPlotTitle();

	/**
	 * Set title of plot
	 * @param plotTitle
	 */
	void setPlotTitle(String plotTitle);

	/**
	 * @return name of y axis
	 */
	String getXAxisName();

	/**
	 * @return name of y axis
	 */
	String getYAxisName();
}
