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

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

/**
 * This class will contain the information describing the properties of the detector that are relevant to diffraction
 * calculations. The Diamond reference frame is defined so its origin is at the intersection of the beam and the sample.
 * [This is a volume but I guess it's the centre of this volume.]
 * <p>
 * The laboratory reference frame is oriented so that the z-axis is along the beam direction (or as close to that as
 * possible so it forms a orthogonal basis with its other two axis), its y-axis is anti-parallel to local direction of
 * gravity, and its x-axis is horizontal. The area detector has a reference frame that describes its orientation where
 * the image rows and columns are anti-parallel to the frame's x and y axes and the z-axis is anti-parallel to the area
 * detector's outward normal. The detector centre locates the top-left corner of the (0,0) pixel of the image where the
 * image coordinates start off with (0,0) in the top-left corner of the image and end at (width-1,height-1) in the
 * bottom-right corner pixel.
 */
public class DetectorProperties {
	private Vector3d origin; // top left corner of detector's (0,0) pixel
	private Vector3d beamVector; // unit vector in beam direction
	private Vector3d normal; // unit vector perpendicular to detector surface
	private int px;
	private int py;
	private double vPxSize;
	private double hPxSize;
	private Matrix3d orientation; // transformation from reference frame to detector frame
	private Matrix3d invOrientation; // its inverse
	private Matrix3d ta;
	private Matrix3d tb;
	private HashSet<IDetectorPropertyListener> detectorPropListeners; 
	private DetectorProperties detectorPropertiesOriginal;
	
	/**
	 * Null constructor
	 */
	public DetectorProperties() {
		ta = new Matrix3d();
		tb = new Matrix3d();
	}
	
	/**
	 * This assumes beam is along z-axis
	 * 
	 * @param origin
	 *            The local origin of the detector plane relative to the reference frame. This origin indicates the top
	 *            left corner of the detector's (0,0) pixel. Distances in mm
	 * @param heightInPixels
	 *            Detector height in pixels
	 * @param widthInPixels
	 *            Detector width in pixels
	 * @param pixelHeightInmm
	 *            pixel height in mm
	 * @param pixelWidthInmm
	 *            pixel width in mm
	 * @param orientation
	 *            matrix describing the orientation of the detector relative to the reference frame. This matrix's
	 *            columns describes the direction of decreasing image rows, the direction of decreasing image columns
	 *            and the detector plane normal.
	 */
	public DetectorProperties(Vector3d origin, final int heightInPixels, final int widthInPixels, final double pixelHeightInmm,
			final double pixelWidthInmm, Matrix3d orientation) {
		this(origin, new Vector3d(0, 0, 1), heightInPixels, widthInPixels, pixelHeightInmm, pixelWidthInmm, orientation);
	}
	
	/**
	 * @param origin
	 *            The local origin of the detector plane relative to the reference frame. This origin indicates the top
	 *            left corner of the detector's (0,0) pixel. Distances in mm
	 * @param beamVector
	 *            A unit vector describing the beam position.
	 * @param heightInPixels
	 *            Detector height in pixels
	 * @param widthInPixels
	 *            Detector width in pixels
	 * @param pixelHeightInmm
	 *            pixel height in mm
	 * @param pixelWidthInmm
	 *            pixel width in mm
	 * @param orientation
	 *            matrix describing the orientation of the detector relative to the reference frame. This matrix's
	 *            columns describes the direction of decreasing image rows, the direction of decreasing image columns
	 *            and the detector plane normal.
	 */
	public DetectorProperties(Vector3d origin, Vector3d beamVector, final int heightInPixels, final int widthInPixels, final double pixelHeightInmm,
			final double pixelWidthInmm, Matrix3d orientation) {
		
		this.origin = origin;
		this.beamVector = beamVector;
		this.beamVector.normalize();
		px = widthInPixels;
		py = heightInPixels;
		vPxSize = pixelHeightInmm;
		hPxSize = pixelWidthInmm;
		
		this.orientation = orientation;
		if (this.orientation == null) {
			this.orientation = new Matrix3d();
			this.orientation.setIdentity();
		}
		calcInverse();
		detectorPropertiesOriginal = new DetectorProperties(this);
	}

	/**
	 * @param detprop
	 *            the DetectorProperties to copy
	 */
	protected DetectorProperties(DetectorProperties detprop) {
		origin = (Vector3d) detprop.origin.clone();
		beamVector = (Vector3d) detprop.beamVector.clone();
		beamVector.normalize();
		px = detprop.getPx();
		py = detprop.getPy();
		vPxSize = detprop.getVPxSize();
		hPxSize = detprop.getHPxSize();
		
		orientation = (Matrix3d) detprop.orientation.clone();
		if (orientation == null) {
			orientation = new Matrix3d();
			orientation.setIdentity();
		}
		calcInverse();
		// Avoid recursive loop 
		if (detprop.getOriginal() != null)
			detectorPropertiesOriginal = new DetectorProperties(this); 
	}

	@Override
	public  DetectorProperties clone() {
		return new DetectorProperties((Vector3d)origin.clone(), (Vector3d)beamVector.clone(), px, py, vPxSize, hPxSize, (Matrix3d)orientation.clone());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beamVector == null) ? 0 : beamVector.hashCode());
		long temp;
		temp = Double.doubleToLongBits(hPxSize);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + px;
		result = prime * result + py;
		temp = Double.doubleToLongBits(vPxSize);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		DetectorProperties other = (DetectorProperties) obj;
		if (beamVector == null) {
			if (other.beamVector != null)
				return false;
		} else if (!beamVector.equals(other.beamVector))
			return false;
		if (Double.doubleToLongBits(hPxSize) != Double.doubleToLongBits(other.hPxSize))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.equals(other.orientation))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (px != other.px)
			return false;
		if (py != other.py)
			return false;
		if (Double.doubleToLongBits(vPxSize) != Double.doubleToLongBits(other.vPxSize))
			return false;
		return true;
	}

	

	private void calcInverse() {
		// calculate the vector from the origin of the detector that is perpendicular to the plane of the detector.
		if (normal == null)
			normal = new Vector3d(0, 0, 1);
		else
			normal.set(0, 0, 1);

		orientation.transform(normal);

		if (invOrientation == null)
			invOrientation = new Matrix3d(orientation);
		else
			invOrientation.set(orientation);

		invOrientation.transpose(); // assume it's orthogonal
		// invOrientation.invert();
	}

	/**
	 * @return a vector describing the horizontal component of the detector in space. This vector describes the
	 *         horizontal component of a pixel.
	 */
	public Vector3d getHorizontalVector() {
		Vector3d horVec = new Vector3d(-hPxSize, 0, 0);

		orientation.transform(horVec);
		return horVec;
	}

	/**
	 * @return a vector describing the vertical component of the detector in space. This vector describes the vertical
	 *         component of a pixel.
	 */
	public Vector3d getVerticalVector() {
		Vector3d vertVec = new Vector3d(0, -vPxSize, 0);

		orientation.transform(vertVec);
		return vertVec;
	}

	/**
	 * @return origin of the detector (top-left corner of (0,0) pixel)
	 */
	public Vector3d getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            of the detector (top-left corner of (0,0) pixel)
	 */
	public void setOrigin(Vector3d origin) {
		this.origin = origin;
		// Tell listeners
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, "Origin"));
	}

	/**
	 * @return number of pixels in the x direction
	 */
	public int getPx() {
		return px;
	}

	/**
	 * @param px
	 *            number of pixels in the x direction
	 */
	public void setPx(final int px) {
		this.px = px;
	}

	/**
	 * @return number of pixels in the y direction
	 */
	public int getPy() {
		return py;
	}

	/**
	 * @param py
	 *            number of pixels in the y direction
	 */
	public void setPy(final int py) {
		this.py = py;
	}

	/**
	 * @return vertical size of pixels in mm
	 */
	public double getVPxSize() {
		return vPxSize;
	}

	/**
	 * @param pxSize
	 *            vertical size of pixels in mm
	 */
	public void setVPxSize(final double pxSize) {
		vPxSize = pxSize;
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, "VPxSize"));
	}

	/**
	 * @return horizontal pixel size in mm
	 */
	public double getHPxSize() {
		return hPxSize;
	}

	/**
	 * @param pxSize
	 *            horizontal pixel size in mm
	 */
	public void setHPxSize(final double pxSize) {
		hPxSize = pxSize;
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, "HPxSize"));
	}

	/**
	 * @return size of detector in mm
	 */
	public double getDetectorSizeV() {
		return vPxSize * py;
	}

	/**
	 * @return size of detector in mm
	 */
	public double getDetectorSizeH() {
		return hPxSize * px;
	}

	/**
	 * @return detector normal
	 */
	public Vector3d getNormal() {
		return normal;
	}

	/**
	 * @param orientation
	 *            matrix describing the orientation of the detector relative to the origin of the detector that
	 *            describes the position of the detector relative to the crystal in space.
	 */
	public void setOrientation(Matrix3d orientation) {
		this.orientation = orientation;
		calcInverse();
	}

	/**
	 * Set detector orientation using a set of Euler angles in ZXZ order
	 * 
	 * @param alpha
	 * @param beta
	 * @param gamma
	 */
	public void setOrientationEulerZXZ(final double alpha, final double beta, final double gamma) {
		if (orientation == null)
			orientation = new Matrix3d();
		ta.rotZ(alpha);
		tb.rotX(beta);
		tb.mul(ta);
		orientation.rotZ(gamma);
		orientation.mul(tb);
		calcInverse();
	}

	/**
	 * Set detector orientation using a set of Euler angles in ZYZ order
	 * 
	 * @param alpha
	 * @param beta
	 * @param gamma
	 */
	public void setOrientationEulerZYZ(final double alpha, final double beta, final double gamma) {
		if (orientation == null)
			orientation = new Matrix3d();
		ta.rotZ(alpha);
		tb.rotY(beta);
		tb.mul(ta);
		orientation.rotZ(gamma);
		orientation.mul(tb);
		calcInverse();
	}

	/**
	 * @return orientation matrix describing the orientation of the detector relative to the origin of the detector that
	 *         describes the position of the detector relative to the crystal in space.
	 */
	public Matrix3d getOrientation() {
		return orientation;
	}

	/**
	 * @param beamVector
	 *            The beam vector to set.
	 */
	public void setBeamVector(Vector3d beamVector) {
		this.beamVector = beamVector;
		beamVector.normalize();
	}

	/**
	 * @return Returns the beam unit vector.
	 */
	public Vector3d getBeamVector() {
		return beamVector;
	}

	/**
	 * from image coordinates, work out position of pixel's top-left corner
	 */
	public void pixelPosition(final double x, final double y, Vector3d p) {
		p.set(-hPxSize * x, -vPxSize * y, 0);
		orientation.transform(p);
		p.add(origin);
	}

	/**
	 * from image coordinates, work out position of pixel's top-left corner
	 */
	public void pixelPosition(final int x, final int y, Vector3d p) {
		pixelPosition((double) x, (double) y, p);
	}

	/**
	 * @return position vector of pixel's top-left corner
	 */
	public Vector3d pixelPosition(final double x, final double y) {
		Vector3d pos = new Vector3d();
		pixelPosition(x, y, pos);
		return pos;
	}

	/**
	 * @return position vector of pixel's top-left corner
	 */
	public Vector3d pixelPosition(final int x, final int y) {
		return pixelPosition((double) x, (double) y);
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @param t
	 *            output vector (x and y components are pixel coordinates)
	 */
	public void pixelCoords(final Vector3d p, Vector3d t) {
		t.set(p);
		t.sub(origin);
		invOrientation.transform(t);
		t.x /= -hPxSize;
		t.y /= -vPxSize;
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @param t
	 *            output vector (x and y components are pixel coordinates)
	 * @param pos
	 *            integer pixel coordinates
	 */
	public void pixelCoords(final Vector3d p, Vector3d t, int[] pos) {
		pixelCoords(p, t);
		pos[0] = (int) Math.floor(t.x);
		pos[1] = (int) Math.floor(t.y);
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @param pos
	 *            integer pixel coordinates
	 */
	public void pixelCoords(final Vector3d p, int[] pos) {
		Vector3d t = new Vector3d();
		pixelCoords(p, t, pos);
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @return integer array of pixel coordinates
	 */
	public int[] pixelCoords(final Vector3d p) {
		int[] pos = new int[2];
		pixelCoords(p, pos);
		return pos;
	}

	/**
	 * @return scattering angle (two-theta) associated with pixel
	 */
	public double pixelScatteringAngle(final double x, final double y) {
		Vector3d p = pixelPosition(x, y);
		p.normalize();
		return Math.acos(p.dot(beamVector));
	}

	/**
	 * @return scattering angle (two-theta) associated with pixel
	 */
	public double pixelScatteringAngle(final int x, final int y) {
		return pixelScatteringAngle((double) x, (double) y);
	}

	/**
	 * @return position of intersection of direct beam with detector
	 */
	public Vector3d getBeamPosition() {
		try {
			return intersect(beamVector);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException("No intersection when beam is parallel to detector");
		}
	}

	// TODO: Implement this ....
//	public void setBeamPosition(Vector3d d) {
//		try {
//			setBeamVector(d);
//		} catch (IllegalArgumentException e) {
//			throw new IllegalStateException("No intersection when beam is parallel to detector");
//		}
//	}
	
	/**
	 * @param d
	 * @return point of intersection of direction vector with detector
	 */
	public Vector3d intersect(final Vector3d d) {
		Vector3d pos = new Vector3d(d);
		intersect(d, pos);
		return pos;
	}

	/**
	 * calculate point of intersection of direction vector with detector
	 * 
	 * @param d
	 *            direction vector (does not have to be a unit vector)
	 * @param p
	 *            position vector of intersection
	 */
	public void intersect(final Vector3d d, Vector3d p) {
		p.set(d);
		double t = normal.dot(d);
		if (t == 0) {
			throw new IllegalArgumentException("No intersection possible as direction vector is parallel to detector");
		}
		t = normal.dot(origin) / t;
		p.scale(t);
	}

	private Vector3d[] cornerPositions() {
		Vector3d[] corners = new Vector3d[4];
		corners[0] = (Vector3d) origin.clone();
		corners[1] = pixelPosition(px, 0);
		corners[2] = pixelPosition(0, py);
		corners[3] = pixelPosition(px, py);
		return corners;
	}

	/**
	 * 
	 * @return The pixel position on the edge of the detector which is closest to the beam centre
	 */
	public int[] pixelClosestToBeamCentre() {
		if (!inImage(getBeamPosition()))
			throw new IllegalArgumentException(
					"The beam does not intersect the detector. There is no complete resolution ring");
		int[] beamPos = pixelCoords(getBeamPosition());
		double shortest = Double.MAX_VALUE;
		int[] closestCoords = new int[2];
		double d = distBetweenPix(beamPos[0], beamPos[1], beamPos[0], 0); 
		if (d < shortest) {
			closestCoords[0] = beamPos[0];
			closestCoords[1] = 0;
			shortest = d;
		}
		d = distBetweenPix(beamPos[0], beamPos[1], 0, beamPos[1]); 
		if (d < shortest) {
			closestCoords[0] = 0;
			closestCoords[1] = beamPos[1];
			shortest = d;
		}
		d = distBetweenPix(beamPos[0], beamPos[1], beamPos[0] - px, py);
		if (d < shortest) {
			closestCoords[0] = px - beamPos[0];
			closestCoords[1] = py;
			shortest = d;
		}
		d = distBetweenPix(beamPos[0], beamPos[1], px, beamPos[1] - py);
		if (d < shortest) {
			closestCoords[0] = px;
			closestCoords[1] = py - beamPos[1];
			shortest = d;
		}
		return closestCoords;
	}

	/**
	 * @return The 2D location of the beam centre on the image.
	 */
	public double[] getBeamLocation() {
		
        final Vector3d cen = new Vector3d();
		pixelCoords(getBeamPosition(), cen);
		return new double[]{cen.x, cen.y};
	}
	
	// TODO FIXME Karl to check
	public void setBeamLocation(double[] loc) {
		// Code to set beam center
		// Get position vector of loc's top-left corner
		Vector3d d = pixelPosition(loc[0], loc[1]);
		// Use this as the new beam vector
		setBeamVector(d);
		
		// Tell listeners
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, "Beam Center"));
	}
	
	public void addDetectorPropertyListener(IDetectorPropertyListener l) {
		if (detectorPropListeners==null) 
			detectorPropListeners = new HashSet<IDetectorPropertyListener>(5);
		detectorPropListeners.add(l);
	}
	/**
	 * Call from dispose of part listening to listen to detector properties changing
	 * @param l
	 */
	public void removeDetectorPropertyListener(IDetectorPropertyListener l) {
		if (detectorPropListeners==null) 
			return;
		detectorPropListeners.remove(l);
	}
	
	protected void fireDetectorPropertyListeners(DetectorPropertyEvent evt) {
		if (detectorPropListeners==null) 
			return;
		for (IDetectorPropertyListener l : detectorPropListeners) {
			l.detectorPropertiesChanged(evt);
		}
	}

	private double distBetweenPix(int p1x, int p1y, int p2x, int p2y) {
		return Math.hypot(p2x - p1x, p2y - p1y);
	}

//	public Vector3d vectorToClosestPoint() {
//		int[] closestCoords = pixelClosestToBeamCentre();
//		Vector3d beamToClosestPoint = new Vector3d();
//		beamToClosestPoint.sub(pixelPosition(closestCoords[0], closestCoords[1]), getBeamPosition());
//		return beamToClosestPoint;
//	}

	/**
	 * @return distance to closest edge from beam centre in pixels
	 */
	public int distToClosestEdgeInPx() {
		int[] closestCoords = pixelClosestToBeamCentre();
		int[] beamPos = pixelCoords(getBeamPosition());
		return (int) distBetweenPix(closestCoords[0], closestCoords[1], beamPos[0], beamPos[1]);
	}

	/**
	 * @return longest vector from beam centre to farthest corner
	 */
	public Vector3d getLongestVector() {
		Vector3d[] corners = cornerPositions();
		Vector3d longVec = new Vector3d();
		double length = -Double.MAX_VALUE;
		for (int i = 0; i < 4; i++) {
			Vector3d tempVec = new Vector3d();
			tempVec.sub(corners[i], getBeamPosition());
			double vecLength = tempVec.length();
			if (vecLength > length) {
				longVec = tempVec;
				length = longVec.length();
			}
		}
		return longVec;
	}

	/**
	 * @return maximum scattering angle (two-theta) that detector can see
	 */
	public double getMaxScatteringAngle() {
		Vector3d[] corners = cornerPositions();
		List<Double> dots = new ArrayList<Double>();

		for (int i = 0; i < 4; i++) {
			corners[i].normalize();
			dots.add(corners[i].dot(beamVector));
		}
		Collections.sort(dots);
		return Math.acos(dots.get(0)); // use smallest cos(two-theta)
	}

	/**
	 * @param x
	 * @param y
	 * @return true if given pixel coordinate is within bounds
	 */
	public boolean inImage(final int x, final int y) {
		return x >= 0 && y < px && y >= 0 && y < py;
	}

	/**
	 * @param pos
	 * @return true if given pixel coordinate is within bounds
	 */
	public boolean inImage(int[] pos) {
		return inImage(pos[0], pos[1]);
	}

	/**
	 * @param p
	 * @return true if given pixel position vector is within bounds
	 */
	public boolean inImage(final Vector3d p) {
		return inImage(pixelCoords(p));
	}

	public void restoreOrigin() {
		setOrigin((Vector3d) detectorPropertiesOriginal.getOrigin().clone());
	}

	public void restoreBeamVector() {
		setBeamVector((Vector3d) detectorPropertiesOriginal.getBeamVector().clone());
	}

	public void restorePx() {
		setPx(detectorPropertiesOriginal.getPx());
	}

	public void restorePy() {
		setPy(detectorPropertiesOriginal.getPy());
	}

	public void restoreVPxSize() {
		setVPxSize(detectorPropertiesOriginal.getVPxSize());
	}

	public void restoreHPxSize() {
		setHPxSize(detectorPropertiesOriginal.getHPxSize());
	}

	public void restoreOrientation() {
		setOrientation((Matrix3d) detectorPropertiesOriginal.getOrientation().clone());
	}
	
	public void restoreOriginal() {
		restoreOrigin();
		restoreBeamVector();
		restoreBeamVector();
		restorePx();
		restorePy();
		restoreVPxSize();
		restoreHPxSize();
		restoreOrientation();
	}

	public DetectorProperties getOriginal() {
		return detectorPropertiesOriginal;
	}
}
