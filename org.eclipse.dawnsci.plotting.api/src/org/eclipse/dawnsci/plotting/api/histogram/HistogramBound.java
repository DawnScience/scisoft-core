/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.plotting.api.histogram;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Immutable HistogramBound class. Keep immutable so that static
 * bound defaults cannot be modified.
 */
public final class HistogramBound {
	private final static Logger logger = LoggerFactory.getLogger(HistogramBound.class);

	public static final HistogramBound DEFAULT_MAXIMUM;
	public static final HistogramBound DEFAULT_MINIMUM;
	public static final HistogramBound DEFAULT_NAN;

	static {
		/**
		 * DO NOT USE Display.getDefault().getSystemColor(..) here! This causes things to break because
		 * Display.getDefault() may be null at the point where statics are initiated.
		 */
		DEFAULT_MAXIMUM = new HistogramBound(Double.POSITIVE_INFINITY, 255, 0, 0);
		DEFAULT_MINIMUM = new HistogramBound(Double.NEGATIVE_INFINITY, 0, 0, 255);
		DEFAULT_NAN     = new HistogramBound(Double.NaN,               0,255,0);
	}
	
	protected Number bound;
	protected int[]  color;
	
	/**
	 * Constructor used from py4j to create a histogram bound
	 * @param bound
	 * @param r
	 * @param g
	 * @param b
	 */
	public HistogramBound(String bound, int r, int g, int b) {
		this(Double.parseDouble(bound),r,g,b);
	}

	/**
	 * Constructor used from py4j to create a histogram bound
	 * @param bound
	 * @param r
	 * @param g
	 * @param b
	 */
	public HistogramBound(double bound, int r, int g, int b) {
		this.bound = bound;
		this.color = new int[]{r,g,b};
	}

	
	/**
	 * RGB may be null. If it is the last three colours in the palette
	 * are used for the bound directly. For instance RGBs can be set to 
	 * null to avoid special cut bounds colors at all.
	 * 
	 * @param bound
	 * @param color
	 */
	public HistogramBound(Number bound, int... color) {
		this.bound = bound;
		this.color = color;
	}
	public Number getBound() {
		return bound;
	}
	public int[] getColor() {
		return color;
	}

	public boolean hasColor() {
		return color != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bound == null) ? 0 : bound.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistogramBound other = (HistogramBound) obj;
		if (bound == null) {
			if (other.bound != null)
				return false;
		} else if (!bound.equals(other.bound))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!Arrays.equals(color, other.color))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final StringBuilder buf = new StringBuilder();
		buf.append(bound);
		buf.append(",");
		if (color!=null) {
			buf.append(Arrays.toString(getColor()));
			
		} else{
			buf.append("null");
		}
		return buf.toString();
	}

	public static HistogramBound fromString(String encoded) {
		
		if (encoded == null || "null".equals(encoded) || "null,null".equals(encoded) || "".equals(encoded)) {
			return null;
		}

		final String[] sa = encoded.split(",");
		Number bound = null;
		int[] color = null;
		if (sa[0].equals("null")) {
			bound = null;
		} else {
			bound = Double.parseDouble(sa[0]);
		}
		if (sa.length > 1) {
			String sa1 = sa[1];
			if (sa1 != null) {
				sa1 = sa1.trim();
				if (sa1.startsWith("[")) {
					sa1 = sa1.substring(1).trim();
				}
				try {
					if (sa.length < 3) {
						if (!"null".equals(sa1)) {
							int g = Integer.parseInt(sa1);
							color = new int[] {g, g, g};
						}
					} else {
						String sa2 = sa[2];
						if (sa2 != null) {
							sa2 = sa2.trim();
						}
						String sa3 = sa[3];
						if (sa3 != null) {
							sa3 = sa3.trim();
							if (sa3.endsWith("]")) {
								sa3 = sa3.substring(0, sa3.length() - 1).trim();
							}
						}
						color = new int[] {Integer.parseInt(sa1), Integer.parseInt(sa2), Integer.parseInt(sa3)};
					}
				} catch (NumberFormatException e) {
					logger.warn("Problem parsing colour from {}", color, e);
				}
			}
		}
		return new HistogramBound(bound, color);
	}

    /**
     * Used for bound values in python commands.
     * @return
     */
	public String getStringBound() {
		if (bound==null) return "None";
		double bnd = bound.doubleValue();
		if (Double.isInfinite(bnd)) return "'"+bound.toString()+"'";
		return bound.toString();
	}
}

