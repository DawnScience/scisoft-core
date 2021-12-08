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

	static abstract class BaseMapping implements ImagePixelMapping {
		protected QSpace qSpace;
		protected Matrix3d transform;

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			this.qSpace = qSpace;
		}

		@Override
		public QSpace getQSpace() {
			return qSpace;
		}

		@Override
		public DetectorProperties getDetectorProperties() {
			return qSpace.getDetectorProperties();
		}

		@Override
		public BaseMapping clone() {
			return null;
		}
	}

	/**
	 * Mapping to QxQyQz
	 */
	public static class QxyzMapping extends BaseMapping {
		private static final String[] Q_XYZ_AXES = { "x-axis", "y-axis", "z-axis" };

		public QxyzMapping() {
		}

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			this.qSpace = qSpace;
			this.transform = mSpace.getLabToCrystalTransform();
		}

		/**
		 * Returns q vector
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			qSpace.qFromPixelPosition(x, y, q);
			transform.transform(q);
		}

		@Override
		public String[] getAxesName() {
			return Q_XYZ_AXES;
		}

		@Override
		public QxyzMapping clone() {
			QxyzMapping c = new QxyzMapping();
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Mapping to HKL
	 */
	public static class HKLMapping extends BaseMapping {
		protected static final String[] HKL_AXES = { "h-axis", "k-axis", "l-axis" };
		protected final Vector3d q;

		public HKLMapping() {
			q = new Vector3d();
		}

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			this.qSpace = qSpace;
			this.transform = mSpace.getMillerTransform();
		}

		@Override
		public QSpace getQSpace() {
			return qSpace;
		}

		/**
		 * Returns h vector
		 * @param h
		 */
		@Override
		public void map(double x, double y, Vector3d h) {
			qSpace.qFromPixelPosition(x, y, q);
			transform.transform(q, h);
		}

		@Override
		public String[] getAxesName() {
			return HKL_AXES;
		}

		@Override
		public HKLMapping clone() {
			HKLMapping c = new HKLMapping();
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Mapping to QparQper
	 */
	public static class Qpp2DMapping extends QxyzMapping {
		private static final String[] Q_PP_AXES = { "q-par-axis", "q-per-axis" };

		public Qpp2DMapping() {
		}

		/**
		 * Returns parallel, perpendicular components
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			super.map(x, y, q);
			q.x = Math.hypot(q.x, q.y);
			q.y = q.z; // FIXME missing Jacobian???
			q.z = 0;
		}

		@Override
		public String[] getAxesName() {
			return Q_PP_AXES;
		}

		@Override
		public Qpp2DMapping clone() {
			Qpp2DMapping c = new Qpp2DMapping();
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Mapping to Q2D by permuting coordinates
	 */
	public static class QPermuted2DMapping extends QxyzMapping {
		static final String[] Q_XYZ_AXES = { "x-axis", "y-axis", "z-axis" };
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in Q space to 2D
		 * @param mode
		 */
		public QPermuted2DMapping(DimChoice mode) {
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
		public QPermuted2DMapping clone() {
			QPermuted2DMapping c = new QPermuted2DMapping(mode);
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Mapping to HKL2D by permuting coordinates
	 */
	public static class HKLPermuted2DMapping extends HKLMapping {
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in Q space to 2D
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
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d h) {
			qSpace.qFromPixelPosition(x, y, q);
			transform.transform(q, h);

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
		public HKLPermuted2DMapping clone() {
			HKLPermuted2DMapping c = new HKLPermuted2DMapping(mode);
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Mapping to Qtheta
	 */
	public static class QThetaMapping extends QxyzMapping {
		private static final String[] Q_Theta_AXES = { "q-theta-axis", };
		private DetectorProperties detector;
		private Vector3d beam;
		
		public QThetaMapping() {
		}

		@Override
		public void setSpaces(QSpace qSpace, MillerSpace mSpace) {
			this.qSpace = qSpace;
			detector = qSpace.getDetectorProperties();
			beam = detector.getBeamVector();
		}

		@Override
		public DetectorProperties getDetectorProperties() {
			return detector;
		}

		/**
		 * Returns theta component
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d q) {
			detector.pixelPosition(x, y, q);
			double theta = 0.5 * Math.acos(beam.dot(q)/q.length());
			q.x = theta; // FIXME missing Jacobian???
			q.y = 0;
			q.z = 0;
		}

		@Override
		public String[] getAxesName() {
			return Q_Theta_AXES;
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
	 * Mapping to Q1D by permuting coordinates
	 */
	public static class Q1DMapping extends QxyzMapping {
		static final String[] Q_XYZ_AXES = { "x-axis", "y-axis", "z-axis" };
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in Q space to 2D
		 * @param mode
		 */
		public Q1DMapping(DimChoice mode) {
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
		public Q1DMapping clone() {
			Q1DMapping c = new Q1DMapping(mode);
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Mapping to HKL1D by permuting coordinates
	 */
	public static class HKL1DMapping extends HKLMapping {
		private final String[] axesNames;
		private DimChoice mode;

		/**
		 * Mapping using given projection in Q space to 2D
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
		 * @param q
		 */
		@Override
		public void map(double x, double y, Vector3d h) {
			qSpace.qFromPixelPosition(x, y, q);
			transform.transform(q, h);

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
		public HKL1DMapping clone() {
			HKL1DMapping c = new HKL1DMapping(mode);
			c.qSpace = qSpace;
			c.transform = transform;
			return c;
		}
	}

	/**
	 * Create an image pixel mapping
	 * @param outputMode
	 * @return image pixel mapping for output mode
	 */
	public static ImagePixelMapping createPixelMapping(OutputMode outputMode) {
		switch (outputMode) {
		case Line_H:
			return new HKL1DMapping(DimChoice.X);
		case Line_K:
			return new HKL1DMapping(DimChoice.Y);
		case Line_L:
			return new HKL1DMapping(DimChoice.Z);
		case Line_Theta:
			return new QThetaMapping();
		case Line_QX:
			return new Q1DMapping(DimChoice.X);
		case Line_QY:
			return new Q1DMapping(DimChoice.Y);
		case Line_QZ:
			return new Q1DMapping(DimChoice.Z);
		case Area_HK:
			return new HKLPermuted2DMapping(DimChoice.X);
		case Area_KL:
			return new HKLPermuted2DMapping(DimChoice.Y);
		case Area_LH:
			return new HKLPermuted2DMapping(DimChoice.Z);
		case Area_QPP:
			return new Qpp2DMapping();
		case Area_QXY:
			return new QPermuted2DMapping(DimChoice.X);
		case Area_QYZ:
			return new QPermuted2DMapping(DimChoice.Y);
		case Area_QZX:
			return new QPermuted2DMapping(DimChoice.Z);
		case Volume_HKL:
		case Coords_HKL:
			return new HKLMapping();
		case Volume_Q:
		case Coords_Q:
			return new QxyzMapping();
		default:
			return null;
		}
	}

}
