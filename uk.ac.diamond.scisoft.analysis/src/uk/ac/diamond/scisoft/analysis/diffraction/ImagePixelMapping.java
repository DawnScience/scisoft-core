/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;

import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.diffraction.MillerSpaceMapperBean.OutputMode;

/**
 * Provides method to map from image pixel coordinate to an output space
 */
public interface ImagePixelMapping {

	public enum DimChoice {
		X,
		Y,
		Z
	}

	/**
	 * Set Q and Miller spaces
	 * @param qSpace Q space
	 * @param mSpace Miller space (can be null)
	 */
	public void setSpaces(QSpace qSpace, MillerSpace mSpace);

	/**
	 * @return Q space
	 */
	public QSpace getQSpace();

	/**
	 * @return detector properties
	 */
	public DetectorProperties getDetectorProperties();

	/**
	 * @return scattering angle
	 */
	public double getCosineScatteringAngle();

	/**
	 * @return position vector of pixel given to map
	 */
	public Vector3d getPositionVector();

	/**
	 * Map given pixel coordinate to output space coordinate
	 * @param x fast axis
	 * @param y slow axis
	 * @param o output vector
	 */
	public void map(double x, double y, Vector3d o);

	public ImagePixelMapping clone();

	/**
	 * @return names to use for axes
	 */
	public String[] getAxesName();

	/**
	 * @return units to use for axes (array can have null entries)
	 */
	public String[] getAxesUnits();

	/**
	 * @param volOrientation orientation matrix from volume to lab
	 */
	public void setVolumeOrientation(Matrix3d volOrientation);

	abstract static class BaseMapping implements ImagePixelMapping {
		protected QSpace qSpace;
		protected DetectorProperties detector;
		protected Vector3d beam;
		protected Matrix3d transform;
		protected double cos; // cosine of scattering angle between incident and final
		protected Vector3d p;
		protected Matrix3d volTransform;
		protected boolean toCrystalFrame;

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			this.qSpace = qSpace;
			this.detector = qSpace.getDetectorProperties();
			this.beam = detector.getBeamVector();
		}

		@Override
		public QSpace getQSpace() {
			return qSpace;
		}

		@Override
		public DetectorProperties getDetectorProperties() {
			return detector;
		}

		@Override
		public double getCosineScatteringAngle() {
			return cos;
		}

		@Override
		public Vector3d getPositionVector() {
			return p;
		}

		@Override
		public void setVolumeOrientation(Matrix3d volOrientation) {
			volTransform = new Matrix3d(volOrientation);
			volTransform.invert();
		}

		@Override
		public BaseMapping clone() {
			return null;
		}
	}

	static final String INVERSE_ANGSTROM = "/angstrom";
	static final String DEGREE = "deg";
	static final String RADIAN = "rad";

	/**
	 * Mapping to QxQyQz in the crystal (default) or lab frame
	 */
	public static class QxyzMapping extends BaseMapping {
		protected static final String[] Q_XYZ_AXES = { "x-axis", "y-axis", "z-axis" };
		protected static final String[] Q_XYZ_UNITS = { INVERSE_ANGSTROM, INVERSE_ANGSTROM, INVERSE_ANGSTROM };

		/**
		 * @param toCrystalFrame if true (default), output to crystal frame otherwise leave in lab frame
		 */
		public QxyzMapping(boolean toCrystalFrame) {
			this.toCrystalFrame = toCrystalFrame;
			p = new Vector3d();
		}

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			super.setSpaces(qSpace, mSpace);
			this.transform = mSpace.getLabToCrystalTransform();
		}

		/**
		 * Returns q vector in crystal or lab frame
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			detector.pixelPosition(x, y, p);
			cos = beam.dot(p)/p.length();
			q.set(p);
			qSpace.convertToQ(q);
			if (toCrystalFrame) {
				transform.transform(q);
			}
			if (volTransform != null) {
				volTransform.transform(q);
			}
		}

		@Override
		public String[] getAxesName() {
			return Q_XYZ_AXES;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_XYZ_UNITS;
		}

		@Override
		public QxyzMapping clone() {
			QxyzMapping c = new QxyzMapping(toCrystalFrame);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to HKL
	 */
	public static class HKLMapping extends QxyzMapping {
		protected static final String[] HKL_AXES = { "h-axis", "k-axis", "l-axis" };
		protected static final String[] HKL_UNITS = { null, null, null };

		public HKLMapping() {
			super(true);
		}

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			super.setSpaces(qSpace, mSpace);
			this.transform = mSpace.getMillerTransform();
		}

		@Override
		public QSpace getQSpace() {
			return qSpace;
		}

		@Override
		public String[] getAxesName() {
			return HKL_AXES;
		}

		@Override
		public HKLMapping clone() {
			HKLMapping c = new HKLMapping();
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to QparQper
	 */
	public static class Qpp2DMapping extends QxyzMapping {
		private static final String[] Q_PP_AXES = { "q-per-axis", "q-par-axis" };
		private static final String[] Q_PP_UNITS = { Q_XYZ_UNITS[0], Q_XYZ_UNITS[1] };

		/**
		 * @param toCrystalFrame if true, output to crystal frame
		 */
		public Qpp2DMapping(boolean toCrystalFrame) {
			super(toCrystalFrame);
		}

		/**
		 * Returns parallel, perpendicular components
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			super.map(x, y, q);
			q.y = Math.hypot(q.x, q.y);
			q.x = q.z; // FIXME missing Jacobian???
			q.z = 0;
		}

		@Override
		public String[] getAxesName() {
			return Q_PP_AXES;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_PP_UNITS;
		}

		@Override
		public Qpp2DMapping clone() {
			Qpp2DMapping c = new Qpp2DMapping(toCrystalFrame);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to Q2D by permuting coordinates
	 */
	public static class QPermuted2DMapping extends QxyzMapping {
		private static final String[] Q_2D_UNITS = { Q_XYZ_UNITS[0], Q_XYZ_UNITS[1] };
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in Q space to 2D
		 * @param toCrystalFrame if true, output to crystal frame
		 * @param mode
		 */
		public QPermuted2DMapping(boolean toCrystalFrame, DimChoice mode) {
			super(toCrystalFrame);

			this.mode = mode;
			switch (mode) {
			case X:
				axesNames = new String[] {Q_XYZ_AXES[0], Q_XYZ_AXES[1]};
				break;
			case Y:
				axesNames = new String[] {Q_XYZ_AXES[1], Q_XYZ_AXES[2]};
				break;
			case Z:
				axesNames = new String[] {Q_XYZ_AXES[2], Q_XYZ_AXES[0]};
				break;
			default:
				throw new UnsupportedOperationException("Unsupported mode");
			}
		}

		/**
		 * Returns q vector
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			super.map(x, y, q);

			double t;
			switch (mode) {
			case X:
				break;
			case Y:
				t = q.x;
				q.x = q.y;
				q.y = q.z;
				q.z = t;
				break;
			case Z:
				t = q.y;
				q.y = q.x;
				q.x = q.z;
				q.z = t;
				break;
			}
		}

		@Override
		public String[] getAxesName() {
			return axesNames;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_2D_UNITS;
		}

		@Override
		public QPermuted2DMapping clone() {
			QPermuted2DMapping c = new QPermuted2DMapping(toCrystalFrame, mode);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to cylindrical polar, QparPhiQz
	 */
	public static class QPolarMapping extends QxyzMapping {
		private static final String[] Q_POL_AXES = { "q-par-axis", "phi-axis", "qz-axis" };
		private static final String[] Q_POL_UNITS = { Q_XYZ_UNITS[0], RADIAN, Q_XYZ_UNITS[2] };

		/**
		 * @param toCrystalFrame if true, output to crystal frame
		 */
		public QPolarMapping(boolean toCrystalFrame) {
			super(toCrystalFrame);
		}

		/**
		 * Returns parallel, azimuth, perpendicular components
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			super.map(x, y, q);
			q.x = Math.hypot(q.x, q.y);
			q.y = Math.atan2(q.y, q.x);
		}

		@Override
		public String[] getAxesName() {
			return Q_POL_AXES;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_POL_UNITS;
		}

		@Override
		public QPolarMapping clone() {
			QPolarMapping c = new QPolarMapping(toCrystalFrame);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to equatorial projection in Q (from south pole)
	 */
	public static class QEquatorialStereoMapping extends QxyzMapping {
		private static final String[] Q_ES_AXES = { "ex-axis", "ey-axis", "q-axis" };

		/**
		 * @param toCrystalFrame if true, output to crystal frame
		 */
		public QEquatorialStereoMapping(boolean toCrystalFrame) {
			super(toCrystalFrame);
		}

		/**
		 * Returns equatorial x, y and q
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			super.map(x, y, q);
			double qm = Math.hypot(q.x, Math.hypot(q.y, q.z));
			double f = qm/(qm + q.z);
			q.x *= f;
			q.y *= f;
			q.z = qm;
		}

		@Override
		public String[] getAxesName() {
			return Q_ES_AXES;
		}

		@Override
		public QEquatorialStereoMapping clone() {
			QEquatorialStereoMapping c = new QEquatorialStereoMapping(toCrystalFrame);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to HKL2D by permuting coordinates
	 */
	public static class HKLPermuted2DMapping extends HKLMapping {
		private static final String[] HKL_2D_UNITS = { HKL_UNITS[0], HKL_UNITS[1] };
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in HKL space to 2D
		 * @param mode
		 */
		public HKLPermuted2DMapping(DimChoice mode) {
			super();

			this.mode = mode;
			switch (mode) {
			case X:
				axesNames = new String[] {HKL_AXES[0], HKL_AXES[1]};
				break;
			case Y:
				axesNames = new String[] {HKL_AXES[1], HKL_AXES[2]};
				break;
			case Z:
				axesNames = new String[] {HKL_AXES[2], HKL_AXES[0]};
				break;
			default:
				throw new UnsupportedOperationException("Unsupported mode");
			}
		}

		/**
		 * Returns q vector
		 * @param p
		 */
		@Override
		public void map(double x, double y, Vector3d h) {
			super.map(x, y, h);

			double t;
			switch (mode) {
			case X:
				break;
			case Y:
				t = h.x;
				h.x = h.y;
				h.y = h.z;
				h.z = t;
				break;
			case Z:
				t = h.y;
				h.y = h.x;
				h.x = h.z;
				h.z = t;
				break;
			}
		}

		@Override
		public String[] getAxesName() {
			return axesNames;
		}

		@Override
		public String[] getAxesUnits() {
			return HKL_2D_UNITS;
		}

		@Override
		public HKLPermuted2DMapping clone() {
			HKLPermuted2DMapping c = new HKLPermuted2DMapping(mode);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to Qtheta (in radians)
	 */
	public static class QThetaMapping extends QxyzMapping {
		private static final String[] Q_Theta_AXES = { "q-theta-axis" };
		private static final String[] Q_Theta_UNITS = { "radians" };

		public QThetaMapping() {
			super(false);
		}

		/**
		 * Returns theta component
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			detector.pixelPosition(x, y, p);
			cos = beam.dot(p) / p.length();

			q.x = 0.5*Math.acos(cos); // FIXME missing Jacobian???
			q.y = 0;
			q.z = 0;
		}

		@Override
		public String[] getAxesName() {
			return Q_Theta_AXES;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_Theta_UNITS;
		}

		@Override
		public QThetaMapping clone() {
			QThetaMapping c = new QThetaMapping();
			c.detector = detector;
			c.beam = beam;
			return c;
		}
	}

	/**
	 * Mapping to Q2theta (in degrees)
	 */
	public static class Q2ThetaMapping extends QxyzMapping {
		private static final String[] Q_2Theta_AXES = { "q-2theta-axis" };
		private static final String[] Q_2Theta_UNITS = { DEGREE };

		/**
		 * 
		 */
		public Q2ThetaMapping() {
			super(false);
		}

		/**
		 * Returns 2-theta component (in degrees)
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			detector.pixelPosition(x, y, p);
			cos = beam.dot(p) / p.length();

			q.x = Math.toDegrees(Math.acos(cos)); // FIXME missing Jacobian???
			q.y = 0;
			q.z = 0;
		}

		@Override
		public String[] getAxesName() {
			return Q_2Theta_AXES;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_2Theta_UNITS;
		}

		@Override
		public Q2ThetaMapping clone() {
			Q2ThetaMapping c = new Q2ThetaMapping();
			c.detector = detector;
			c.beam = beam;
			return c;
		}
	}

	/**
	 * Mapping to Q1D by permuting coordinates
	 */
	public static class Q1DMapping extends QxyzMapping {
		private static final String[] Q_1D_UNITS = { Q_XYZ_UNITS[0] };
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in Q space to 2D
		 * @param toCrystalFrame if true, output to crystal frame
		 * @param mode
		 */
		public Q1DMapping(boolean toCrystalFrame, DimChoice mode) {
			super(toCrystalFrame);

			this.mode = mode;
			switch (mode) {
			case X:
				axesNames = new String[] {Q_XYZ_AXES[0]};
				break;
			case Y:
				axesNames = new String[] {Q_XYZ_AXES[1]};
				break;
			case Z:
				axesNames = new String[] {Q_XYZ_AXES[2]};
				break;
			default:
				throw new UnsupportedOperationException("Unsupported mode");
			}
		}

		/**
		 * Returns q vector
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			super.map(x, y, q);

			double t;
			switch (mode) {
			case X:
				break;
			case Y:
				t = q.x;
				q.x = q.y;
				q.y = q.z;
				q.z = t;
				break;
			case Z:
				t = q.y;
				q.y = q.x;
				q.x = q.z;
				q.z = t;
				break;
			}
		}

		@Override
		public String[] getAxesName() {
			return axesNames;
		}

		@Override
		public String[] getAxesUnits() {
			return Q_1D_UNITS;
		}

		@Override
		public Q1DMapping clone() {
			Q1DMapping c = new Q1DMapping(toCrystalFrame, mode);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Mapping to HKL1D by permuting coordinates
	 */
	public static class HKL1DMapping extends HKLMapping {
		private static final String[] HKL_1D_UNITS = { HKL_UNITS[0] };
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in HKL space to 2D
		 * @param mode
		 */
		public HKL1DMapping(DimChoice mode) {
			super();

			this.mode = mode;
			switch (mode) {
			case X:
				axesNames = new String[] {HKL_AXES[0]};
				break;
			case Y:
				axesNames = new String[] {HKL_AXES[1]};
				break;
			case Z:
				axesNames = new String[] {HKL_AXES[2]};
				break;
			default:
				throw new UnsupportedOperationException("Unsupported mode");
			}
		}

		/**
		 * Returns q vector
		 * @param p
		 */
		@Override
		public void map(double x, double y, Vector3d h) {
			super.map(x, y, h);

			double t;
			switch (mode) {
			case X:
				break;
			case Y:
				t = h.x;
				h.x = h.y;
				h.y = h.z;
				h.z = t;
				break;
			case Z:
				t = h.y;
				h.y = h.x;
				h.x = h.z;
				h.z = t;
				break;
			}
		}

		@Override
		public String[] getAxesName() {
			return axesNames;
		}

		@Override
		public String[] getAxesUnits() {
			return HKL_1D_UNITS;
		}

		@Override
		public HKL1DMapping clone() {
			HKL1DMapping c = new HKL1DMapping(mode);
			c.qSpace = qSpace;
			c.detector = detector;
			c.beam = beam;
			c.transform = transform;
			c.volTransform = volTransform;
			return c;
		}
	}

	/**
	 * Create an image pixel mapping
	 * @param outputMode
	 * @param toCrystalFrame if true, output to crystal frame otherwise to lab frame
	 * @return image pixel mapping for output mode
	 */
	public static ImagePixelMapping createPixelMapping(OutputMode outputMode, boolean toCrystalFrame) {
		switch (outputMode) {
		case Line_H:
			return new HKL1DMapping(DimChoice.X);
		case Line_K:
			return new HKL1DMapping(DimChoice.Y);
		case Line_L:
			return new HKL1DMapping(DimChoice.Z);
		case Line_Theta:
			return new QThetaMapping();
		case Line_2Theta:
			return new Q2ThetaMapping();
		case Line_QX:
			return new Q1DMapping(toCrystalFrame, DimChoice.X);
		case Line_QY:
			return new Q1DMapping(toCrystalFrame, DimChoice.Y);
		case Line_QZ:
			return new Q1DMapping(toCrystalFrame, DimChoice.Z);
		case Area_HK:
			return new HKLPermuted2DMapping(DimChoice.X);
		case Area_KL:
			return new HKLPermuted2DMapping(DimChoice.Y);
		case Area_LH:
			return new HKLPermuted2DMapping(DimChoice.Z);
		case Area_QPP:
			return new Qpp2DMapping(toCrystalFrame);
		case Area_QXY:
			return new QPermuted2DMapping(toCrystalFrame, DimChoice.X);
		case Area_QYZ:
			return new QPermuted2DMapping(toCrystalFrame, DimChoice.Y);
		case Area_QZX:
			return new QPermuted2DMapping(toCrystalFrame, DimChoice.Z);
		case Volume_HKL:
		case Coords_HKL:
			return new HKLMapping();
		case Volume_QCP:
			return new QPolarMapping(toCrystalFrame);
		case Volume_QES:
			return new QEquatorialStereoMapping(toCrystalFrame);
		case Volume_Q:
		case Coords_Q:
			return new QxyzMapping(toCrystalFrame);
		default:
			return null;
		}
	}

}
