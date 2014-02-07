/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.coords.SectorCoords;


/**
 * Class for sector region of interest
 */
public class SectorROI extends ROIBase implements Serializable {
	protected double rad[]; // radii
	protected double ang[]; // angles in radians
	protected boolean clippingCompensation; // compensate for clipping
	protected boolean combineSymmetry; // combine symmetry option for profile (where appropriate)
	private boolean averageArea = true;
	protected double dpp; // Sampling rate used for profile calculations in dots per pixel

	protected int symmetry; // symmetry

	/**
	 * No operation
	 */
	public static final int NONE = 0;
	/**
	 * Full circular sector
	 */
	public static final int FULL = 1;
	/**
	 * Reflect sector in y-axis (left/right)
	 */
	public static final int XREFLECT = 2;
	/**
	 * Reflect sector in x-axis (up/down)
	 */
	public static final int YREFLECT = 3;
	/**
	 * Rotate sector by +90 degrees
	 */
	public static final int CNINETY = 4;
	/**
	 * Rotate sector by -90 degrees
	 */
	public static final int ACNINETY = 5;
	/**
	 * Invert sector through centre
	 */
	public static final int INVERT = 6;

	private static Map<Integer, String> symmetryText = new HashMap<Integer, String>();
	static {
		symmetryText.put(SectorROI.NONE,     "None");
		symmetryText.put(SectorROI.FULL,     "Full");
		symmetryText.put(SectorROI.XREFLECT, "L/R");
		symmetryText.put(SectorROI.YREFLECT, "U/D");
		symmetryText.put(SectorROI.CNINETY,  "+90");
		symmetryText.put(SectorROI.ACNINETY, "-90");
		symmetryText.put(SectorROI.INVERT,   "Invert");
	}
	public static Map<Integer, String> getSymmetriesPossible() {
		return symmetryText;
	}

	/**
	 * @param clippingCompensation The clippingCompensation to set.
	 */
	public void setClippingCompensation(boolean clippingCompensation) {
		this.clippingCompensation = clippingCompensation;
	}

	/**
	 * @return Returns the clippingCompensation.
	 */
	public boolean isClippingCompensation() {
		return clippingCompensation;
	}

	/**
	 * @param symmetry The symmetry to set.
	 */
	public void setSymmetry(int symmetry) {
		this.symmetry = symmetry;
	}

	/**
	 * @param symmetry The symmetry to set.
	 */
	public static int getSymmetry(String symmetry) {
		if (symmetryText.containsValue(symmetry))
			for (int key : symmetryText.keySet())
				if (symmetry.equals(symmetryText.get(key))) {
					return key;
				}
		
		return SectorROI.NONE;
	}
	
	/**
	 * @return Returns the symmetry.
	 */
	public int getSymmetry() {
		return symmetry;
	}

	/**
	 * 
	 */
	public SectorROI() {
		this(30., 120., Math.PI*0.25, Math.PI*2./3.);
	}

	private final static double TWO_PI = 2.0 * Math.PI;
	private final static double HALF_PI = 0.5 * Math.PI;

	/**
	 * Create an annulus
	 * @param sr 
	 * @param er
	 */
	public SectorROI(double sr, double er) {
		this(0., 0., sr, er, 0, TWO_PI, 1.0, false, SectorROI.FULL);
	}

	/**
	 * @param sr 
	 * @param er
	 * @param sp
	 * @param ep
	 */
	public SectorROI(double sr, double er, double sp, double ep) {
		this(0., 0., sr, er, sp, ep);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param sp
	 * @param ep
	 */
	public SectorROI(double ptx, double pty, double sr, double er, double sp, double ep) {
		this(ptx, pty, sr, er, sp, ep, 1.0, true);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param sp
	 * @param ep
	 * @param dpp
	 * @param clip 
	 */
	public SectorROI(double ptx, double pty, double sr, double er, double sp, double ep, double dpp, boolean clip) {
		this(ptx, pty, sr, er, sp, ep, dpp, clip, SectorROI.NONE);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param sp
	 * @param ep
	 * @param dpp
	 * @param clip 
	 * @param sym 
	 */
	public SectorROI(double ptx, double pty, double sr, double er, double sp, double ep, double dpp, boolean clip, int sym) {
		spt = new double[] {ptx, pty};
		rad = new double[] {sr, er};
		ang = new double[] {sp, ep};
		this.dpp = dpp;
		clippingCompensation = clip;
		symmetry = sym;
		combineSymmetry = false;
		checkRadii();
		checkAngles();
	}

	/**
	 * @param radius The radii to set
	 */
	public void setRadii(double radius[]) {
		setRadii(radius[0], radius[1]);
	}

	/**
	 * @param startRadius 
	 * @param endRadius 
	 */
	public void setRadii(double startRadius, double endRadius) {
		rad[0] = startRadius;
		rad[1] = endRadius;
		checkRadii();
	}

	/**
	 * @return Returns reference to the radii
	 */
	public double[] getRadii() {
		return rad;
	}

	/**
	 * @param index 
	 * @return Returns the radius
	 */
	public double getRadius(int index) {
		return rad[index];
	}

	/**
	 * @return Returns the radii
	 */
	public int[] getIntRadii() {
		return new int[] { (int) rad[0], (int) rad[1] };
	}

	/**
	 * @param index 
	 * @return Returns the radius
	 */
	public int getIntRadius(int index) {
		return (int) rad[index];
	}

	/**
	 * Set angles
	 * @param startAngle
	 * @param endAngle
	 */
	public void setAngles(double startAngle, double endAngle) {
		ang[0] = startAngle;
		ang[1] = endAngle;
		checkAngles();		
	}

	/**
	 * @param angles The angles to set
	 */
	public void setAngles(double angles[]) {
		setAngles(angles[0], angles[1]);
	}

	/**
	 * @return Returns the angles in degrees
	 */
	public double[] getAnglesDegrees() {
		double[] angles = new double[] { Math.toDegrees(ang[0]), Math.toDegrees(ang[1]) };
		return angles;
	}

	/**
	 * @param index 
	 * @return Returns the angles in degrees
	 */
	public double getAngleDegrees(int index) {
		return Math.toDegrees(ang[index]);
	}

	/**
	 * @param angles The angles in degrees to set
	 */
	public void setAnglesDegrees(double angles[]) {
		setAnglesDegrees(angles[0], angles[1]);
	}

	/**
	 * For Jython
	 * @param angles The angles in degrees to set
	 */
	public void setAnglesdegrees(double[] angles) {
		setAnglesDegrees(angles);
	}

	/**
	 * @param startAngle in degrees
	 * @param endAngle in degrees
	 */
	public void setAnglesDegrees(double startAngle, double endAngle) {
		ang[0] = Math.toRadians(startAngle);
		ang[1] = Math.toRadians(endAngle);
		checkAngles();
	}

	/**
	 * @return Returns reference to the angles
	 */
	public double[] getAngles() {
		return ang;
	}

	/**
	 * @param index 
	 * @return Returns the angles
	 */
	public double getAngle(int index) {
		return ang[index];
	}

	/**
	 * Add an offset to both angle
	 * 
	 * @param angle
	 */
	public void addAngles(double angle) {
		for (int i = 0; i < 2; i++) {
			ang[i] += angle;
		}
		if (ang[0] > TWO_PI) {
			ang[0] -= TWO_PI;
			ang[1] -= TWO_PI;
		}
		if (ang[0] < 0) {
			ang[0] += TWO_PI;
			ang[1] += TWO_PI;
		}
	}

	/**
	 * Add an offset to an angle
	 * @param index 
	 * @param angle
	 */
	public void addAngle(int index, double angle) {
		ang[index] += angle;
		checkAngles();
	}

	/**
	 * Make sure angles lie in permitted ranges:
	 *  0 <= ang0 <= 2*pi, 0 <= ang1 <= 4*pi
	 *  0 <= ang1 - ang0 <= 2*pi
	 */
	protected void checkAngles() {
		// sort out relative values
		double a = ang[0];
		while (a >= ang[1]) {
			ang[1] += TWO_PI;
		}

		a += TWO_PI;
		while (a < ang[1]) {
			ang[1] -= TWO_PI;
		}
		
		// place correctly in absolute terms
		while (ang[0] < 0) {
			ang[0] += TWO_PI;
			ang[1] += TWO_PI;
		}
		
		while (ang[0] > TWO_PI) {
			ang[0] -= TWO_PI;
			ang[1] -= TWO_PI;
		}
	}

	/**
	 * Add an offset to radii
	 * @param radius 
	 */
	public void addRadii(double radius) {
		if (rad[0] + radius < 0)
			radius = -rad[0];
		if (rad[1] + radius < 0)
			radius = -rad[1];
		rad[0] += radius;
		rad[1] += radius;
	}

	/**
	 * Add an offset to a radius
	 * @param index 
	 * @param radius 
	 */
	public void addRadius(int index, double radius) {
		rad[index] += radius;
		checkRadii();
	}

	/**
	 * Make sure radii lie in permitted range:
	 *  0 <= rad0, rad1
	 *  rad0 <= rad1
	 */
	private void checkRadii() {
		if (rad[0] < 0)
			rad[0] = 0;
		if (rad[1] < 0)
			rad[1] = 0;
		if (rad[0] > rad[1]) {
			rad[0] = rad[1];
		}
	}

	/**
	 * @return a copy
	 */
	@Override
	public SectorROI copy() {
		SectorROI c = new SectorROI(spt[0], spt[1], rad[0], rad[1], ang[0], ang[1], dpp, clippingCompensation, symmetry);
		c.setCombineSymmetry(combineSymmetry);
		c.name = name;
		c.plot = plot;
		return c;
	}
	
	/**
	 * @param sym 
	 * @return true if given symmetry is okay with angles
	 */
	public boolean checkSymmetry(int sym) {
		boolean isOK = false;

		switch(sym) {
		case NONE: case FULL:
			isOK = true;
			break;
		case XREFLECT:
			if (0 <= ang[0] && ang[0] < HALF_PI && ang[0] <= ang[1] && ang[1] <= HALF_PI)
				isOK = true;
			else if (HALF_PI <= ang[0] && ang[0] <= 3*HALF_PI && ang[0] <= ang[1] && ang[1] <= 3*HALF_PI)
				isOK = true;
			else if (3*HALF_PI <= ang[0] && ang[0] <= TWO_PI && ang[0] <= ang[1] && ang[1] <= 5*HALF_PI)
				isOK = true;
			break;
		case YREFLECT:
			if (0 <= ang[0] && ang[0] < Math.PI && ang[0] <= ang[1] && ang[1] <= Math.PI)
				isOK = true;
			else if (Math.PI <= ang[0] && ang[0] <= TWO_PI && ang[0] <= ang[1] && ang[1] <= TWO_PI)
				isOK = true;
			break;
		case CNINETY:
			if (ang[1] <= ang[0] + HALF_PI)
				isOK = true;
			break;
		case ACNINETY:
			if (ang[0] >= ang[1] - HALF_PI)
				isOK = true;
			break;
		case INVERT:
			if (ang[1] <= ang[0] + HALF_PI)
				isOK = true;
			break;
		}
		return isOK;
	}

	/**
	 * @return angles from symmetry operations
	 */
	public double[] getSymmetryAngles() {
		double[] nang = new double[] {0, TWO_PI};

		switch (symmetry) {
		case XREFLECT:
			// add in x reflected integral
			nang[0] = Math.PI - ang[1];
			nang[1] = Math.PI - ang[0];
			break;
		case YREFLECT:
			// add in y reflected integral
			nang[0] = TWO_PI - ang[1];
			nang[1] = TWO_PI - ang[0];
			break;
		case CNINETY:
			// add in +90 rotated integral
			nang[0] = ang[0] + HALF_PI;
			nang[1] = ang[1] + HALF_PI;
			break;
		case ACNINETY:
			// add in -90 rotated integral
			nang[0] = ang[0] - HALF_PI;
			nang[1] = ang[1] - HALF_PI;
			break;
		case INVERT:
			// add in inverted integral
			nang[0] = ang[0] + Math.PI;
			nang[1] = ang[1] + Math.PI;
			break;
		case FULL:
			break;
		default:
		case NONE:
			return null;
		}

		return nang;
	}

	/**
	 * @return text for symmetry setting
	 */
	public String getSymmetryText() {
		return getSymmetryText(symmetry);
	}

	/**
	 * @return text for symmetry setting
	 */
	public static String getSymmetryText(int sym) {
		if (symmetryText.containsKey(sym))
			return symmetryText.get(sym);

		return "N";
	}
	/**
	 * @param combineSymmetry The combineSymmetry to set.
	 */
	public void setCombineSymmetry(boolean combineSymmetry) {
		this.combineSymmetry = combineSymmetry;
	}

	/**
	 * @return Returns the combineSymmetry.
	 */
	public boolean isCombineSymmetry() {
		return combineSymmetry;
	}

	/**
	 * Return sampling rate used in profile calculations
	 * 
	 * @return
	 * 			sampling rate in dots per pixel
	 */
	public double getDpp() {
		return dpp;
	}

	/**
	 * Set sampling rate used in profile calculations  
	 * 
	 * @param dpp
	 *			sampling rate in dots per pixel; 
	 */
	public void setDpp(double dpp) {
		this.dpp = dpp;
	}

	/**
	 * Whether the pixel average value shall be calculated instead of integrating
	 * @return averageArea
	 */
	public boolean isAverageArea() {
		return averageArea;
	}

	/**
	 * Set true to not strictly integrate but average over the pixels
	 * @param averageArea
	 */
	public void setAverageArea(boolean averageArea) {
		this.averageArea = averageArea;
	}

	/**
	 * @return Returns true if ROI has separate regions (determined by symmetry and combine flag)
	 */
	public boolean hasSeparateRegions() {
		return !(symmetry == NONE || symmetry == FULL || combineSymmetry); 
	}

	@Override
	public IRectangularROI getBounds() {
		SectorCoords sc = new SectorCoords(rad[0], ang[0], false, false);
		double[] pt = sc.getCartesian();
		double[] max = pt;
		double[] min = max.clone();

		sc = new SectorCoords(rad[0], ang[1], false, false);
		pt = sc.getCartesian();
		ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);

		sc = new SectorCoords(rad[1], ang[1], false, false);
		pt = sc.getCartesian();
		ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);

		sc = new SectorCoords(rad[1], ang[0], false, false);
		pt = sc.getCartesian();
		ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);

		int beg = (int) Math.ceil(ang[0] / HALF_PI);
		int end = (int) Math.floor(ang[1] / HALF_PI);
		for (; beg <= end; beg++) { // angle range spans multiples of pi/2
			sc = new SectorCoords(rad[1], beg*HALF_PI, false, false);
			pt = sc.getCartesian();
			ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);
		}

		double[] angs = getSymmetryAngles();
		if (angs != null) {
			sc = new SectorCoords(rad[0], angs[0], false, false);
			pt = sc.getCartesian();
			ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);

			sc = new SectorCoords(rad[0], angs[1], false, false);
			pt = sc.getCartesian();
			ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);

			sc = new SectorCoords(rad[1], angs[1], false, false);
			pt = sc.getCartesian();
			ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);

			sc = new SectorCoords(rad[1], angs[0], false, false);
			pt = sc.getCartesian();
			ROIUtils.updateMaxMin(max, min, pt[0], pt[1]);
		}

		RectangularROI b = new RectangularROI();
		b.setPoint(min[0] + spt[0], min[1] + spt[1]);
		b.setLengths(max[0] - min[0], max[1] - min[1]);
		return b;
	}

	@Override
	public boolean containsPoint(double x, double y) {
		x -= spt[0];
		y -= spt[1];

		SectorCoords sc = new SectorCoords(x, y, true);
		double[] pol = sc.getPolarRadians();
		double r = pol[0];
		if (r < rad[0] || r > rad[1])
			return false;

		double p = pol[1];
		if (p >= ang[0] && p <= ang[1])
			return true;
		double[] angs = getSymmetryAngles();
		if (angs == null)
			return false;
		return p >= angs[0] && p <= angs[1];
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		x -= spt[0];
		y -= spt[1];

		SectorCoords sc = new SectorCoords(x, y, true);
		double[] pol = sc.getPolarRadians();
		double r = pol[0];
		double p = pol[1];
		if (p >= ang[0] && p <= ang[1]) { // near arcs
			if (Math.abs(r - rad[0]) <= distance || Math.abs(r - rad[1]) <= distance)
				return true;
		}

		// check radials
		sc = new SectorCoords(rad[0], ang[0], false, false);
		double[] pt = sc.getCartesian();
		double px = pt[0];
		double py = pt[1];
		sc = new SectorCoords(rad[1], ang[0], false, false);
		pt = sc.getCartesian();
		if (ROIUtils.isNearSegment(pt[0] - px, pt[1] - py, x - px, y - py, distance))
			return true;

		sc = new SectorCoords(rad[0], ang[1], false, false);
		pt = sc.getCartesian();
		px = pt[0];
		py = pt[1];
		sc = new SectorCoords(rad[1], ang[1], false, false);
		pt = sc.getCartesian();
		return ROIUtils.isNearSegment(pt[0] - px, pt[1] - py, x - px, y - py, distance);
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		rad[0] /= subFactor;
		rad[1] /= subFactor;
	}

	@Override
	public String toString() {
		return super.toString() + String.format("point=%s, radii=%s, angles=[%g, %g]", Arrays.toString(spt), Arrays.toString(rad), getAngleDegrees(0), getAngleDegrees(1));
	}
}
