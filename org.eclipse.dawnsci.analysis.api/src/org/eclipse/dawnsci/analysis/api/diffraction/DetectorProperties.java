/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.api.diffraction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorPropertyEvent.EventType;
import org.eclipse.january.dataset.Dataset;

/**
 * This class will contain the information describing the properties of an area detector
 * that are relevant to diffraction calculations. The Diamond reference frame is defined
 * so its origin is at the intersection of the beam and the sample. [This is a volume but
 * I guess it's the centre of this volume.]
 * <p>
 * The laboratory reference frame is oriented so that the z-axis is along the beam
 * direction (or as close to that as possible so it forms a orthogonal basis with its
 * other two axis), its y-axis is anti-parallel to local direction of gravity, and its
 * x-axis is horizontal. The area detector has a frame that describes its orientation
 * relative to the laboratory reference frame. In an idealised case, the detector frame
 * coincides with the laboratory but has its origin situated at the top-left corner of
 * the top-leftmost pixel of the corrected image that recorded by the detector. The
 * image is presented as if seen from the beam source's perspective and image coordinates
 * start off with (0,0) in the top-left corner of the image and end at (width-1,height-1)
 * in the bottom-right corner pixel. Thus the image rows and columns are anti-parallel to
 * the area detector frame's x and y axes; the area detector's outward (to the sample)
 * normal is anti-parallel to its z-axis.
 */
public class DetectorProperties implements Serializable, Cloneable {
	/**
	 * Update this when there are any serious changes to API
	 */
	static final long serialVersionUID = 3928760686423603813L; 

	private Vector3d origin;         // top left corner of detector's (0,0) pixel
	private Vector3d beamVector;     // unit vector in beam direction
	private transient Vector3d normal;         // unit vector perpendicular to detector surface
	private int sx;                  // start x in pixels
	private int sy;                  // start y in pixels
	private int px;                  // width in pixels
	private int py;                  // height in pixels
	private double hPxSize;          // horizontal pixel size (in mm)
	private double vPxSize;          // vertical pixel size (in mm)
	private Matrix3d orientation;    // passive transformation from reference frame to detector frame
	private transient Matrix3d invOrientation; // its inverse, aka active transformation of detector to its final position
	private boolean fire = true;

	private transient Set<IDetectorPropertyListener> detectorPropListeners;

	private transient Vector3d oldCentre;
	private transient Vector3d oldShift;

	private Dataset mask; // non-zero values indicate that where pixels are ignored
	private double lower = Double.NEGATIVE_INFINITY, upper = Double.POSITIVE_INFINITY; // threshold values beyond which to ignore pixels

	private transient double distance;
	private transient Vector3d closestPoint;

	private double epsilon;

	/**
	 * Null constructor
	 */
	public DetectorProperties() {
		normal = new Vector3d(0, 0, -1);
		origin = new Vector3d();
		computeClosestPoint();
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
	 * @param pixelHeightInMM
	 *            pixel height in mm
	 * @param pixelWidthInMM
	 *            pixel width in mm
	 * @param orientation
	 *            matrix describing the orientation of the detector relative to the reference frame. This matrix's
	 *            columns describes the direction of decreasing image rows, the direction of decreasing image columns
	 *            and the detector plane normal.
	 */
	public DetectorProperties(Vector3d origin, final int heightInPixels, final int widthInPixels, final double pixelHeightInMM,
			final double pixelWidthInMM, Matrix3d orientation) {
		this(origin, new Vector3d(0, 0, 1), heightInPixels, widthInPixels, pixelHeightInMM, pixelWidthInMM, orientation);
	}
	
	/**
	 * This assumes beam is along z-axis, detector is square on the beam
	 * 
	 * This method does not require creating plugins to import vecmath
	 * 
	 * @param distance
	 *            In mm. Length of the norm vector pointing from detector surface to sample
	 * @param xorigin
	 *            horizontal displacement in mm of the detector (0,0) from where above norm vector sits on detector surface
	 * @param yorigin
	 *            vertical displacement in mm of the detector (0,0) from where above norm vector sits on detector surface
	 * @param heightInPixels
	 *            Detector height in pixels
	 * @param widthInPixels
	 *            Detector width in pixels
	 * @param pixelHeightInMM
	 *            pixel height in mm
	 * @param pixelWidthInMM
	 *            pixel width in mm
	 */
	public DetectorProperties(double distance, double xorigin, double yorigin, final int heightInPixels, final int widthInPixels, final double pixelHeightInMM,
			final double pixelWidthInMM) {
		this(new Vector3d(xorigin, yorigin, distance), new Vector3d(0, 0, 1), heightInPixels, widthInPixels, pixelHeightInMM, pixelWidthInMM, null);
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
	 * @param pixelHeightInMM
	 *            pixel height in mm
	 * @param pixelWidthInMM
	 *            pixel width in mm
	 * @param detectorRotationX value describing the orientation of the detector relative to the reference frame
	 * @param detectorRotationY value describing the orientation of the detector relative to the reference frame
	 * @param detectorRotationZ value describing the orientation of the detector relative to the reference frame
	 */
	public DetectorProperties(Vector3d origin, final int heightInPixels, final int widthInPixels, final double pixelHeightInMM,
			final double pixelWidthInMM, final double detectorRotationX, final double detectorRotationY, final double detectorRotationZ) {
		this();
		Matrix3d rotX = new Matrix3d();
		rotX.rotX(detectorRotationX);
		Matrix3d rotY = new Matrix3d();
		rotY.rotY(detectorRotationY);
		Matrix3d rotZ = new Matrix3d();
		rotZ.rotZ(detectorRotationZ);

		Matrix3d euler = new Matrix3d();
		euler.mul(rotX, rotY);
		euler.mul(rotZ);
		
		this.origin = origin;
		beamVector = new Vector3d(0, 0, 1);
		px = widthInPixels;
		py = heightInPixels;
		vPxSize = pixelHeightInMM;
		hPxSize = pixelWidthInMM;
		
		orientation = euler;
		if (orientation == null) {
			orientation = new Matrix3d();
			orientation.setIdentity();
		}
		calcNormal(true);
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
	 * @param pixelHeightInMM
	 *            pixel height in mm
	 * @param pixelWidthInMM
	 *            pixel width in mm
	 * @param orientation
	 *            matrix describing the orientation of the detector relative to the reference frame. This matrix's
	 *            columns describes the direction of decreasing image rows, the direction of decreasing image columns
	 *            and the detector plane normal.
	 */
	public DetectorProperties(Vector3d origin, Vector3d beamVector, final int heightInPixels, final int widthInPixels, final double pixelHeightInMM,
			final double pixelWidthInMM, Matrix3d orientation) {
		this();
		this.origin = origin;
		this.beamVector = beamVector;
		this.beamVector.normalize();
		px = widthInPixels;
		py = heightInPixels;
		vPxSize = pixelHeightInMM;
		hPxSize = pixelWidthInMM;
		
		this.orientation = orientation;
		if (this.orientation == null) {
			this.orientation = new Matrix3d();
			this.orientation.setIdentity();
		}
		calcNormal(true);
	}

	/**
	 * @param detprop
	 *            the DetectorProperties to copy
	 */
	private DetectorProperties(DetectorProperties detprop) {
		this();
		if (detprop.origin != null)
			origin = new Vector3d(detprop.origin);
		if (detprop.beamVector != null) {
			beamVector = new Vector3d(detprop.beamVector);
			beamVector.normalize();
		} else {
			beamVector = new Vector3d(0, 0, 1);
		}
		px = detprop.getPx();
		py = detprop.getPy();
		vPxSize = detprop.getVPxSize();
		hPxSize = detprop.getHPxSize();
		
		if (detprop.orientation == null) {
			orientation = new Matrix3d();
			orientation.setIdentity();
		} else {
			orientation = new Matrix3d(detprop.orientation);
		}
		calcNormal(true);
	}

	/**
	 * Produce a Detector properties object populated with sensible default values given image shape.
	 * It produces a detector normal to the beam and centred on the beam with square pixels of 0.1024mm and set 200mm
	 * from the sample.
	 * 
	 * @param shape image shape
	 */
	public static DetectorProperties getDefaultDetectorProperties(int... shape) {
		int heightInPixels = shape[0];
		int widthInPixels = shape[1];
		
		// Set a few default values
		double pixelSizeX = 0.1024;
		double pixelSizeY = 0.1024;
		double distance = 200.00;
		
		// Create identity orientation
		Matrix3d identityMatrix = new Matrix3d();
		identityMatrix.setIdentity();

		// Create the detector origin vector based on the above
		Vector3d dOrigin = new Vector3d((widthInPixels - widthInPixels/2d) * pixelSizeX, (heightInPixels - heightInPixels/2d) * pixelSizeY, distance);

		return new DetectorProperties(dOrigin, heightInPixels, widthInPixels, pixelSizeX, pixelSizeY, identityMatrix);
	}

	@Override
	public  DetectorProperties clone() {
		return new DetectorProperties(this);
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
		} else if (!beamVector.epsilonEquals(other.beamVector, epsilon))
			return false;
		if (Double.doubleToLongBits(hPxSize) != Double.doubleToLongBits(other.hPxSize))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.epsilonEquals(other.orientation, epsilon))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.epsilonEquals(other.origin, epsilon))
			return false;
		if (px != other.px)
			return false;
		if (py != other.py)
			return false;
		if (sx != other.sx)
			return false;
		if (sy != other.sy)
			return false;
		if (Double.doubleToLongBits(vPxSize) != Double.doubleToLongBits(other.vPxSize))
			return false;
		return true;
	}

	/**
	 * Set geometry from another detector
	 * @param other
	 */
	public void setGeometry(DetectorProperties other) {
		origin = other.origin;
		beamVector = other.beamVector;
		normal = other.normal;
		orientation = other.orientation;
		invOrientation = other.invOrientation;

		computeClosestPoint();
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.GEOMETRY));
	}

	private void calcNormal(boolean fromPassive) {
		if (fromPassive) {
			if (invOrientation == null)
				invOrientation = new Matrix3d();

			if (orientation == null) {
				orientation = new Matrix3d();
				orientation.setIdentity();
			}

			invOrientation.transpose(orientation); // assume it's orthogonal
			// invOrientation.invert(orientation);
		} else {
			if (orientation == null)
				orientation = new Matrix3d();

			if (invOrientation != null)
				orientation.transpose(invOrientation); // assume it's orthogonal
			else
				orientation.setIdentity();
		}

		// calculate the vector from the origin of the detector that is perpendicular to the plane of the detector.
		normal.set(0, 0, -1);

		if (invOrientation != null)
			invOrientation.transform(normal); // use active transformation

		computeClosestPoint();
	}

	private void computeClosestPoint() {
		distance = -normal.dot(origin);
		if (closestPoint == null) {
			closestPoint = new Vector3d();
		}
		closestPoint.scale(-distance, normal);
	}

	/**
	 * @return a vector describing the row-wise component of a detector pixel in space.
	 * I.e. the horizontal (in an image) edge of a pixel
	 */
	public Vector3d getPixelRow() {
		Vector3d horVec = new Vector3d(-hPxSize, 0, 0);

		if (invOrientation != null)
			invOrientation.transform(horVec);
		return horVec;
	}

	/**
	 * @return a vector describing the column-wise component of a detector pixel in space
	 * I.e. the vertical (in an image) edge of a pixel
	 */
	public Vector3d getPixelColumn() {
		Vector3d vertVec = new Vector3d(0, -vPxSize, 0);

		if (invOrientation != null)
			invOrientation.transform(vertVec);
		return vertVec;
	}

	/**
	 * @return reference to origin of the detector (top-left corner of (0,0) pixel)
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
		computeClosestPoint();
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.ORIGIN));
	}

	/**
	 * Get distance from sample to beam centre.
	 * @return distance can be infinity if direct beam does not intersect detector.
	 */
	public double getBeamCentreDistance() {
		try {
			return getBeamCentrePosition().length();
		} catch (IllegalStateException e) {
			return Double.POSITIVE_INFINITY;
		}
	}

	/**
	 * Set distance from sample to beam centre.
	 * <p>
	 * Can throw an exception if direct beam does not intersect detector.
	 * @param distance
	 */
	public void setBeamCentreDistance(double distance) {
		distance -= getBeamCentrePosition().length();
		Vector3d b = new Vector3d(beamVector);
		b.scale(distance);
		origin.add(b);
		computeClosestPoint();
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.ORIGIN));
	}

	/**
	 * Get distance from sample to detector
	 * @return distance from sample to closest point on detector 
	 */
	public double getDetectorDistance() {
		return distance;
	}

	/**
	 * @return point on detector closest to origin
	 */
	public Vector3d getClosestPoint() {
		return new Vector3d(closestPoint);
	}

	/**
	 * Set distance from sample to detector
	 * @param distance
	 */
	public void setDetectorDistance(double distance) {
		Vector3d b = new Vector3d(normal);
		b.scale(getDetectorDistance()-distance);
		origin.add(b);
		computeClosestPoint();
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.ORIGIN));
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
	 * @return start pixel value in the x direction
	 */
	public int getStartX() {
		return sx;
	}

	/**
	 * @param sx
	 *            start pixel value in the x direction
	 */
	public void setStartX(final int sx) {
		this.sx = sx;
	}

	/**
	 * @return start pixel value in the y direction
	 */
	public int getStartY() {
		return sy;
	}

	/**
	 * @param sy
	 *            start pixel value in the y direction
	 */
	public void setStartY(final int sy) {
		this.sy = sy;
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
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.VPXSIZE));
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
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.HPXSIZE));
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
	 * @return reference to detector normal (do not change)
	 */
	public Vector3d getNormal() {
		return normal;
	}

	/**
	 * @return tilt of detector normal from beam direction (in radians)
	 */
	public double getTiltAngle() {
		return Math.acos(-normal.dot(beamVector));
	}

	/**
	 * Get orientation of the detector as described by a passive transformation from the laboratory
	 * frame to the detector frame
	 * @return reference to matrix
	 */
	public Matrix3d getOrientation() {
		return orientation;
	}

	/**
	 * Set the detector orientation by a passive transformation from the laboratory
	 * frame to the detector frame
	 * @param orientation passive transformation matrix (vecmath is active)
	 */
	public void setOrientation(Matrix3d orientation) {
		this.orientation = orientation;
		calcNormal(true);
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.NORMAL));
	}

	/**
	 * Set detector orientation using a set of (proper) Euler angles (in radians) in ZXZ order
	 * 
	 * @param alpha first angle about global z
	 * @param beta second angle about local x
	 * @param gamma third angle about local z
	 */
	public void setOrientationEulerZXZ(final double alpha, final double beta, final double gamma) {
		if (invOrientation == null)
			invOrientation = new Matrix3d();
		Matrix3d ta = new Matrix3d();
		Matrix3d tb = new Matrix3d();
		ta.rotZ(gamma);
		tb.rotX(beta);
		tb.mul(ta);
		invOrientation.rotZ(alpha);
		invOrientation.mul(tb);
		calcNormal(false);
	}

	/**
	 * Set detector orientation using a set of (proper) Euler angles (in radians) in ZYZ order
	 * 
	 * @param alpha first angle about global z
	 * @param beta second angle about local y
	 * @param gamma third angle about local z
	 */
	public void setOrientationEulerZYZ(final double alpha, final double beta, final double gamma) {
		invOrientation = MatrixUtils.createOrientationFromEulerZYZRadians(alpha, beta, gamma);
		calcNormal(false);
	}

	/**
	 * Set detector orientation using a set of (proper) Euler angles (in radians) in ZYZ order
	 * 
	 * @param alpha first angle about global x
	 * @param beta second angle about local y
	 * @param gamma third angle about local z
	 */
	public void setOrientationEulerXYZ(final double alpha, final double beta, final double gamma) {
		if (invOrientation == null)
			invOrientation = new Matrix3d();
		Matrix3d ta = new Matrix3d();
		Matrix3d tb = new Matrix3d();
		ta.rotZ(gamma);
		tb.rotY(beta);
		tb.mul(ta);
		invOrientation.rotX(alpha);
		invOrientation.mul(tb);
		calcNormal(false);
	}

	/**
	 * Generate an active transformation matrix from Euler angles given in degrees
	 * @param yaw
	 * @param pitch
	 * @param roll
	 */
	public static Matrix3d activeMatrixFromEulerAngles(final double yaw, final double pitch, final double roll) {
		return MatrixUtils.createOrientationFromYawPitchRoll(Math.toRadians(yaw), -Math.toRadians(pitch), Math.toRadians(roll));
	}

	/**
	 * Set detector normal (from face out to sample) using a set of yaw, pitch and roll angles in degrees.
	 * <p>
	 * Note, if the beam centre exists then this re-orients the detector about the beam centre and
	 * therefore alters the detector origin.
	 * 
	 * @param angles yaw, pitch, roll
	 */
	public void setNormalAnglesInDegrees(double[] angles) {
		setNormalAnglesInDegrees(angles[0], angles[1], angles[2]);
	}

	/**
	 * Set detector normal (from face out to sample) using a set of yaw, pitch and roll angles in degrees.
	 * <p>
	 * Note, if the beam centre exists then this re-orients the detector about the beam centre and
	 * therefore alters the detector origin.
	 * 
	 * @param yaw rotate about vertical axis ((-180, 180] with positive is to the right, east or clockwise looking down)
	 * @param pitch rotate about horizontal axis ([-90, 90] with positive is upwards)
	 * @param roll rotate about normal ((-180, 180] with positive is clockwise looking along normal)
	 */
	public void setNormalAnglesInDegrees(final double yaw, final double pitch, final double roll) {
		Vector3d centre = null;
		Vector3d shift = null;
		try {
			centre = getBeamCentrePosition();
			shift = new Vector3d();
			shift.sub(origin, centre);
			if (orientation != null)
				orientation.transform(shift);  // relative beam centre in image frame

			oldCentre = centre;
			oldShift = shift;
		} catch (IllegalStateException e) {
		}

		invOrientation = activeMatrixFromEulerAngles(yaw, pitch, roll);
		calcNormal(false);

		if (normal.dot(beamVector) != 0) {
			if (shift == null || centre == null) {
				 // reset state from old
				centre = oldCentre;
				shift = oldShift;
			}
			oldCentre = null;
			oldShift = null;
			if (shift != null && centre != null) {
				// set origin back from beam centre
				invOrientation.transform(shift);
				centre.add(shift);
				origin = centre;
			}
			computeClosestPoint();
		}

		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.NORMAL));
	}

	/**
	 * Get detector normal (from face out to sample) as a set of yaw, pitch and roll angles in degrees.
	 * Note, in the gimbal lock case (when pitch is +/- 90 degrees), roll is returned as zero.
	 * @return yaw, pitch and roll as defined in {@link #setNormalAnglesInDegrees(double, double, double)}
	 */
	public double[] getNormalAnglesInDegrees() {
		double[] radians = MatrixUtils.calculateYawPitchRoll(invOrientation);

		double yaw = Math.toDegrees(radians[0]);
		if (yaw <= -180) {
			yaw += 360;
		}
		double roll = Math.toDegrees(radians[2]);
		if (roll <= -180) {
			roll += 360;
		}
		return new double[] {yaw, -Math.toDegrees(radians[1]), roll};
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
	 * @return reference to the beam direction unit vector
	 */
	public Vector3d getBeamVector() {
		return beamVector;
	}

	/**
	 * from image coordinates, work out position of pixel's top-left corner
	 */
	public void pixelPosition(final double x, final double y, Vector3d p) {
		p.set(-hPxSize * (x - sx), -vPxSize * (y - sy), 0);
		if (invOrientation != null)
			invOrientation.transform(p);
		p.add(origin);
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
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @param t
	 *            output vector (x and y components are pixel coordinates)
	 */
	public void pixelCoords(final Vector3d p, Vector3d t) {
		t.sub(p, origin);
		if (orientation != null)
			orientation.transform(t);
		t.x /= -hPxSize;
		t.x += sx;
		t.y /= -vPxSize;
		t.y += sy;
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @param coords
	 *            double pixel coordinates
	 */
	public void pixelCoords(final Vector3d p, double[] coords) {
		Vector3d t = new Vector3d();
		pixelCoords(p, t);
		coords[0] = t.x;
		coords[1] = t.y;
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @param coords
	 *            integer pixel coordinates
	 */
	public void pixelCoords(final Vector3d p, int[] coords) {
		Vector3d t = new Vector3d();
		pixelCoords(p, t);
		coords[0] = (int) Math.floor(t.x);
		coords[1] = (int) Math.floor(t.y);
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @return integer array of pixel coordinates
	 */
	public int[] pixelCoords(final Vector3d p) {
		int[] coords = new int[2];
		pixelCoords(p, coords);
		return coords;
	}

	/**
	 * from position on detector, work out pixel coordinates
	 * 
	 * @param p
	 *            position vector
	 * @return double array of pixel coordinates
	 */
	public double[] pixelPreciseCoords(final Vector3d p) {
		double[] coords = new double[2];
		pixelCoords(p, coords);
		return coords;
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
	 * Get beam centre position.
	 * <p>
	 * Can throw an illegal state exception when there is no intersection
	 * @return position of intersection of direct beam with detector
	 */
	public Vector3d getBeamCentrePosition() {
		try {
			return intersect(beamVector);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException("No intersection when beam is parallel to detector");
		}
	}

	/**
	 * @param v vector
	 * @return point of intersection of vector with detector
	 */
	public Vector3d intersect(final Vector3d v) {
		return intersect(v, null, null);
	}

	/**
	 * Calculate point of intersection of vector from lab frame origin with detector
	 * 
	 * @param v
	 *            vector (does not have to be a unit vector)
	 * @param p
	 *            output position vector of intersection
	 */
	public void intersect(final Vector3d v, Vector3d p) {
		intersect(v, null, p);
	}

	/**
	 * Calculate point of intersection of line with detector
	 * 
	 * @param v
	 *            line direction vector (does not have to be a unit vector)
	 * @param p0
	 *            line position vector (can be null for line through origin)
	 * @param p
	 *            output position vector of intersection (can be null)
	 * @return p (if not null) or new vector with intersection coordinates
	 */
	public Vector3d intersect(final Vector3d v, final Vector3d p0, Vector3d p) {
		double t = normal.dot(v);
		if (t == 0) {
			throw new IllegalStateException("No intersection possible as vector is parallel to detector");
		}
		if (p == null) {
			p = new Vector3d();
		}
		if (p0 == null) {
			t = normal.dot(origin) / t;
			p.scale(t, v);
		} else {
			p.sub(origin, p0);
			t = normal.dot(p) / t;
			p.scaleAdd(t, v, p0);
		}
		return p;
	}

	/**
	 * Calculate intersection coordinates
	 * @param v
	 *            line direction vector (can be null for default beam vector, does not have to be a unit vector)
	 * @param p0
	 *            line position vector (can be null for line through origin)
	 * @param coords
	 *            output pixel coordinates (can be null)
	 * @return coords (if not null) or new array with intersection coordinates
	 */
	public double[] intersectPreciseCoords(Vector3d v, final Vector3d p0, double[] coords) {
		if (coords == null) {
			coords = new double[2];
		}
		if (v == null) {
			v = beamVector;
		}

		try {
			pixelCoords(intersect(v, p0, null), coords);
		} catch (IllegalStateException e) {
			coords[0] = Double.NaN;
			coords[1] = Double.NaN;
		}
		return coords;
	}

	private Vector3d[] cornerPositions() {
		Vector3d[] corners = new Vector3d[4];
		corners[0] = new Vector3d(origin);
		corners[1] = pixelPosition(px + sx, sy);
		corners[2] = pixelPosition(sx, py + sy);
		corners[3] = pixelPosition(px + sx, py + sy);
		return corners;
	}

	/**
	 * Can throw illegal state exception
	 * @return The pixel position on the edge of the detector which is closest to the beam centre
	 */
	private int[] pixelClosestToBeamCentre() {
		if (!inImage(getBeamCentrePosition()))
			throw new IllegalStateException("The beam does not intersect the detector");
		double[] beamCentre = pixelPreciseCoords(getBeamCentrePosition());
		double shortest = Double.MAX_VALUE;
		int[] closestCoords = new int[2];
		double d = distBetweenPix(beamCentre[0], beamCentre[1], beamCentre[0], 0); 
		if (d < shortest) {
			closestCoords[0] = (int) beamCentre[0];
			closestCoords[1] = 0;
			shortest = d;
		}
		d = distBetweenPix(beamCentre[0], beamCentre[1], 0, beamCentre[1]); 
		if (d < shortest) {
			closestCoords[0] = 0;
			closestCoords[1] = (int) beamCentre[1];
			shortest = d;
		}
		d = distBetweenPix(beamCentre[0], beamCentre[1], beamCentre[0] - px, py);
		if (d < shortest) {
			closestCoords[0] = (int) (px - beamCentre[0]);
			closestCoords[1] = py;
			shortest = d;
		}
		d = distBetweenPix(beamCentre[0], beamCentre[1], px, beamCentre[1] - py);
		if (d < shortest) {
			closestCoords[0] = px;
			closestCoords[1] = (int) (py - beamCentre[1]);
			shortest = d;
		}
		return closestCoords;
	}

	/**
	 * @return pixel coordinates of the beam centre (where beam intersects detector).
	 * In the case of it being undefined, NaNs are returned
	 */
	public double[] getBeamCentreCoords() {
		try {
			return pixelPreciseCoords(getBeamCentrePosition());
		} catch (IllegalStateException e) {
			return new double[] {Double.NaN, Double.NaN};
		}
	}

	/**
	 * Set beam centre (where beam intersects detector)
	 * @param pixel coords in image
	 */
	public void setBeamCentreCoords(double... coords) {
		try {
			Vector3d oc = getBeamCentrePosition(); // old beam centre
			oc.sub(pixelPosition(coords[0], coords[1]));
			origin.add(oc); // shift origin accordingly
			computeClosestPoint();
		} catch (IllegalStateException e) {
			// do nothing
		}
		// Tell listeners
		fireDetectorPropertyListeners(new DetectorPropertyEvent(this, EventType.BEAM_CENTRE));
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
		if (detectorPropListeners==null)  return;
		detectorPropListeners.remove(l);
	}

	protected void fireDetectorPropertyListeners(DetectorPropertyEvent evt) {
		if (detectorPropListeners==null || !fire) 
			return;
		for (IDetectorPropertyListener l : detectorPropListeners) {
			l.detectorPropertiesChanged(evt);
		}
	}

	private static double distBetweenPix(double p1x, double p1y, double p2x, double p2y) {
		return Math.hypot(p2x - p1x, p2y - p1y);
	}

//	public Vector3d vectorToClosestPoint() {
//		int[] closestCoords = pixelClosestToBeamCentre();
//		Vector3d beamToClosestPoint = new Vector3d();
//		beamToClosestPoint.sub(pixelPosition(closestCoords[0], closestCoords[1]), getBeamPosition());
//		return beamToClosestPoint;
//	}

	/**
	 * Can throw illegal state exception if beam does not intersect detector
	 * @return distance to closest edge from beam centre in pixels
	 */
	public int distToClosestEdgeInPx() {
		int[] closestCoords = pixelClosestToBeamCentre();
		double[] beamCoords = pixelPreciseCoords(getBeamCentrePosition());
		return (int) distBetweenPix(closestCoords[0], closestCoords[1], beamCoords[0], beamCoords[1]);
	}

	/**
	 * @return longest vector from beam centre to farthest corner
	 */
	public Vector3d getLongestVector() {
		Vector3d[] corners = cornerPositions();
		Vector3d longVec = null;
		double length = -Double.MAX_VALUE;
		for (int i = 0; i < 4; i++) {
			Vector3d c = corners[i];
			c.sub(getBeamCentrePosition());
			double l = c.length();
			if (l > length) {
				longVec = c;
				length = l;
			}
		}
		return longVec;
	}

	/**
	 * @return maximum scattering angle (two-theta) that detector can see
	 */
	public double getMaxScatteringAngle() {
		Vector3d[] corners = cornerPositions();
		double angle = -Double.MAX_VALUE;
		for (int i = 0; i < 4; i++) {
			double a = corners[i].angle(beamVector);
			if (a > angle) {
				angle = a;
			}
		}
		return angle;
	}

	/**
	 * @param coords
	 * @return true if given pixel coordinate is within bounds
	 */
	public boolean inImage(double... coords) {
		if (coords == null || coords.length == 0)
			throw new IllegalArgumentException("Need at least one coordinate");

		final double x = coords[0] - sx;
		if (coords.length == 1) {
			return x >= 0 && x < px;
		}
		final double y = coords[1] - sy;
		return x >= 0 && x < px && y >= 0 && y < py;
	}

	/**
	 * @param p
	 * @return true if given pixel position vector is within bounds
	 */
	public boolean inImage(final Vector3d p) {
		return inImage(pixelPreciseCoords(p));
	}

	/**
	 * Calculate solid angle subtended by pixel at origin
	 * @param x
	 * @param y
	 * @return solid angle
	 */
	public double calculateSolidAngle(final int x, final int y) {
		double[] centre = pixelPreciseCoords(closestPoint);
		double a = hPxSize*(x - centre[0]);
		double b = vPxSize*(y - centre[1]);
		return calculatePixelSolidAngle(a, b, distance, hPxSize, vPxSize);
	}

	/**
	 * Calculate solid angle of rectangle that has corner is perpendicular to origin
	 * @param a one side of rectangle
	 * @param b other side of rectangle
	 * @param c perpendicular distance
	 * @return solid angle
	 */
	public static double calculatePixelSolidAngle(double a, double b, double c, double da, double db) {
		// From Frank S. Crawford Jr, "Solid Angle subtended by a finite rectangular counter", technical report, UCRL-1753,
		// Radiation Laboratory, University of California (1953);
		// also, A. Khadjavi, "Calculation of Solid Angle Subtended by Rectangular Apertures", JOSA, 58, v10, pp1417-8 (1968)
		return calculateRectangleSolidAngle(a + da, b + db, c)
				+ calculateRectangleSolidAngle(a, b, c)
				- calculateRectangleSolidAngle(a + da, b, c)
				- calculateRectangleSolidAngle(a, b + db, c);
	}

	/**
	 * Calculate solid angle of rectangle that has its corner perpendicular to origin
	 * @param a one side of rectangle
	 * @param b other side of rectangle
	 * @param c perpendicular distance
	 * @return solid angle
	 */
	public static double calculateRectangleSolidAngle(double a, double b, double c) {
		double alpha = a/c;
		double beta = b/c;
		double gamma = alpha*beta/Math.sqrt(1 + alpha*alpha + beta*beta);
		if (Math.abs(gamma) < 5/32.) { // < 0.1% accuracy
			return gamma * (1 - gamma*gamma*(1./3));
		}
		return Math.atan(gamma);
	}

	/**
	 * Calculate solid angle subtended by a plane triangle with given vertices
	 * @param a
	 * @param b
	 * @param c
	 * @return solid angle
	 */
	public static double calculatePlaneTriangleSolidAngle(Vector3d a, Vector3d b, Vector3d c) {
		// A. van Oosterom & J. Strackee, "The Solid Angle of a Plane Triangle", IEEE Trans.
		// Biom Eng BME-30(2) pp125-6 (1983)
		// tan(Omega/2) = a . (b x c) / [ a * b * c + (a . b) * c + (b . c) * a + (c . a) *b ]
		double al = a.length();
		double bl = b.length();
		double cl = c.length();
		double denom = al * bl * cl + a.dot(b)*cl + al * b.dot(c) + a.dot(c) * bl; 
		Vector3d bc = new Vector3d();
		bc.cross(b, c);
		double ang = Math.atan(Math.abs(a.dot(bc))/denom);
		if (ang < 0) {
			ang += Math.PI;
		}
		return 2 * ang;
	}

	/**
	 * 
	 * @param original
	 */
	public void restore(DetectorProperties original) {
		fire = false;
		setOrigin(new Vector3d(original.getOrigin()));
		setBeamVector(new Vector3d(original.getBeamVector()));
		setPx(original.getPx());
		setPy(original.getPy());
		setStartX(original.getStartX());
		setStartY(original.getStartY());
		setVPxSize(original.getVPxSize());
		setHPxSize(original.getHPxSize());
		fire = true;
		setOrientation(new Matrix3d(original.getOrientation()));
	}

	@Override
	public String toString() {
		double[] bc = getBeamCentreCoords();
		String text = Double.isNaN(bc[0]) ? "o = " + origin: "bc = " + Arrays.toString(bc);
		return "DP: " + text + ", n = " + normal + ", d = " + getDetectorDistance() + ", th = " + Math.toDegrees(getTiltAngle()) + ", phi = " + getNormalAnglesInDegrees()[2];
	}

	/**
	 * @return masking dataset where non-zero items indicate which pixels to ignore
	 */
	public Dataset getMask() {
		return mask;
	}

	/**
	 * Set a masking dataset where non-zero items indicate which pixels to ignore
	 * @param mask
	 */
	public void setMask(Dataset mask) {
		this.mask = mask;
	}

	/**
	 * @return threshold value below which to ignore pixels
	 */
	public double getLowerThreshold() {
		return lower;
	}

	/**
	 * Set threshold value below which to ignore pixels
	 * @param lower
	 */
	public void setLowerThreshold(double lower) {
		this.lower = lower;
	}

	/**
	 * @return threshold value above which to ignore pixels
	 */
	public double getUpperThreshold() {
		return upper;
	}

	/**
	 * Set threshold value above which to ignore pixels
	 * @param upper
	 */
	public void setUpperThreshold(double upper) {
		this.upper = upper;
	}

	/**
	 * Set epsilon
	 * @param epsilon value used for equality testing of vectors
	 */
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}
}
