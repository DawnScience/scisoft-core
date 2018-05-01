package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.util.Arrays;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;

public class XRMCDetector extends XRMCFile {

	Matrix4d transformToLab;

	static final double POSITION_TO_PIXELSIZE = 1000.0;

	/**
	 * Creates a new Detector class, based on the given file
	 * @param fileName
	 * 				file path at which the source file can be found
	 */
	public XRMCDetector(String fileName) {
		super(fileName);
		transformToLab = null;
	}
	
	/**
	 * Checks that what the XRMCDatReader has ingested is an XRMC detector file.
	 * @return true if the structure in the reader matches an XRMC detector file
	 */
	public boolean isDetectorFile() {
		return isValidFile();
	}

	@Override
	protected String getDeviceName() {
		return "detectorarray";
	}
	
	/**
	 * Returns the number of pixels in each dimension of the detector.
	 * @return the integral number of pixels in the detector array.
	 */
	public int[] getNPixels() {
		String pixelString = getValue("NPixels");
		String[] tokens = pixelString.split("\\s+");
		int[] nxny = new int[2];
		nxny[0] = Integer.parseInt(tokens[0]);
		nxny[1] = Integer.parseInt(tokens[1]);

		return nxny;
	}
	
	/**
	 * Returns the size of the pixel in each dimension in μm.
	 * @return The size of the detector pixels in μm.
	 */
	public double[] getPixelSize() {
		// an extra multiplication by 10 000 to go from cm to μm
		return getParseAndScaleValues("PixelSize", 10_000);
	}
	
	/**
	 * Returns the centre of the detector, positioned in mm
	 * @return coordinates of the screen in mm, relative to the origin. 
	 */
	public double[] getDetectorPosition() {
		return getParseAndScaleValues("X", 10);
	}

	public double[] getDetectorNormal() {
		return getAndParseValues("uk");
	}
	
	public double[] getDetectorXVector() {
		return getAndParseValues("ui");
	}
	
	public int getNBins() {
		// Assume there are not more than 2^53 energies
		return (int) getAndParseValues("NBins")[0];
	}
	
	public double getEmin() {
		return Double.parseDouble(getValue("Emin"));
	}
	
	public double getEmax() {
		return Double.parseDouble(getValue("Emax"));
	}

	/**
	 * Returns the solid angle subtended by the detector.
	 * @return the solid angle subtended by the detector (steradians).
	 */
	public double getSolidAngle() {
		// Generate the corner vectors for the detector
		//   Generate the basis vectors for the screen in the lab coordinate system
		Maths3d xScreen = new Maths3d(this.getDetectorXVector());
		Maths3d zScreen = new Maths3d(this.getDetectorNormal());
		Maths3d yScreen = zScreen.cross(xScreen);
		
		// screen centre in mm
		Maths3d screenCentre = new Maths3d(getDetectorPosition());
		// in metres
		screenCentre = screenCentre.times(0.001);

		// half lengths of the screen in metres
		double xHalfLength = this.getNPixels()[0] * this.getPixelSize()[0] / 1_000_000 / 2;
		double yHalfLength = this.getNPixels()[1] * this.getPixelSize()[1] / 1_000_000 / 2;
		
		Maths3d mm = screenCentre.minus(xScreen.times(xHalfLength)).minus(yScreen.times(yHalfLength));
		Maths3d pm = screenCentre.plus(xScreen.times(xHalfLength)).minus(yScreen.times(yHalfLength));
		Maths3d mp = screenCentre.minus(xScreen.times(xHalfLength)).plus(yScreen.times(yHalfLength));
		Maths3d pp = screenCentre.plus(xScreen.times(xHalfLength)).plus(yScreen.times(yHalfLength));
		
		return
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, pp.value, mp.value) +
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, mp.value, mm.value) +
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, mm.value, pm.value) +
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, pm.value, pp.value);
	}
	
	/**
	 * Returns the euler angles of the detector relative to the beam.
	 * @return Euler angles in radians ordered as pitch, yaw, roll.
	 */
	public double[] getEulerAngles() {
		Maths3d xScreen = new Maths3d(this.getDetectorXVector());
		Maths3d zScreen = new Maths3d(this.getDetectorNormal());
		Maths3d yScreen = zScreen.cross(xScreen);

		// Use elements of the full rotation matrix to get the
		// trigonometric functions of the Euler angles.
		
		double sinYaw = zScreen.get().x;
		double yaw = Math.asin(sinYaw);
		
		double pitch = Math.atan2(-zScreen.get().y, zScreen.get().z);
		// When the screen is exactly face on, the pitch will here be 
		// π/2, because of the definitions of the screen and lab
		// coordinate systems.  
		pitch -= Math.PI/2;
		
		double roll = Math.atan2(-yScreen.get().x, xScreen.get().x);
		
		return new double[] {pitch, yaw, roll};
	}
	
	/*
	 * Coordinate frames
	 * 
	 * XRMC lab frame.
	 * A three-dimensional, right-handed orthogonal Cartesian coordinate system
	 * The origin is centred on the beam-sample interaction region
	 * x is horizontal, perpendicular to the beam
	 * y is parallel to the beam, +ve downstream
	 * z is vertically upwards
	 * 
	 * Detector frame
	 * A two-dimensional Cartesian coordinate system
	 * The origin is the top left of the detector (facing in the side of the detector that receives the radiation)
	 * x is horizontal
	 * y is vertical
	 */
	
	/**
	 * Returns the XRMC lab coordinates of a given point on the detector.
	 * <p>
	 * Returns the position (in metres) in the XRMC lab coordinate frame. The 
	 * input is a position on the detector in units of pixels. The origin is 
	 * the top left of the top left pixel. The centre of the top left pixel is 
	 * (0.5, 0.5).
	 */
	public Vector3d labFromPixel(Vector2d x) {
		Vector2d d = new Vector2d(getPixelSize());
		d.scale(1/POSITION_TO_PIXELSIZE);
		Vector4d h = new Vector4d(d.x*x.x, d.y*x.y, 0.0, 1.0);
		getTransform().transform(h);
		return new Vector3d(h.x, h.y, h.z);
	}
	
	/**
	 * Returns the scattering angles (γ,δ) for a given pixel
	 * @param x
	 * 			the location on the screen in units of pixels, with the origin
	 * 			at the top left of the top left pixel.
	 * @return (γ,δ) scattering angles in radians.
	 */
	public Vector2d anglesFromPixel(Vector2d x) {
		return anglesFromPixel(x, false);
	}

	/**
	 * Returns the polar scattering angles (φ,2θ) for a given pixel.
	 * @param x
	 * 			the location on the screen in units of pixels, with the origin
	 * 			at the top left of the top left pixel.
	 * @return (φ,2θ) scattering angles in radians.
	 */
	public Vector2d polarAnglesFromPixel(Vector2d x) {
		return anglesFromPixel(x, true);
	}
	
	/**
	 * Returns the polar (φ,2θ) or Cartesian (γ,δ) scattering angles for a
	 * given pixel.
	 * @param x
	 * 			the location on the screen in units of pixels, with the origin
	 * 			at the top left of the top left pixel.
	 * @param polar
	 * 				if true, return the polar scattering angles (φ,2θ), else 
	 * 				return the Cartesian scattering angles (γ,δ)
	 * @return
	 */
	public Vector2d anglesFromPixel(Vector2d x, boolean polar) {
		Vector3d lab = labFromPixel(x);
		Vector2d result = null;
		if (polar) {
			double phi = Math.atan2(lab.z, lab.x);
			double tth = Math.atan2(quadrate(lab.x, lab.z), lab.y);
			result = new Vector2d(phi, tth);
		} else {
			double gamma = Math.atan2(lab.x, lab.y);
			double delta = Math.atan2(lab.z, quadrate(lab.x, lab.y));
			result = new Vector2d(gamma, delta);
		}
		return result;
	}

	/**
	 * Returns the detector coordinates of a given point in the lab frame.
	 * <p>
	 * The point in the lab frame is transformed into the detectors xyz frame,
	 * where x & y are the fast and slow pixel directions, and z is in the
	 * direction of the detector *anti*-normal. The point is then projected
	 * into the detector plane by zeroing the z component. The output value is
	 * in fractional pixels, with the origin being the top left of the top left
	 * pixel.  
	 */
	public Vector2d pixelFromLab(Vector3d x) {
		Vector4d h = new Vector4d(x.x, x.y, x.z, 1.0);
		Matrix4d inverseTransform = new Matrix4d();
		inverseTransform.invert(getTransform());
		inverseTransform.transform(h);
		Vector2d d = new Vector2d(getPixelSize());
		d.scale(1/POSITION_TO_PIXELSIZE);
		return new Vector2d(h.x/d.x, h.y/d.y);
	}

	/**
	 * Sets the transformation matrix from the detector coordinates to the XRMC lab coordinates x_lab = M x_det
	 */
	private Matrix4d getTransform() {
		if (transformToLab == null) {
			transformToLab = new Matrix4d(
				1., 0., 0., 0.,
				0., 1., 0., 0.,
				0., 0., 1., 0.,
				0., 0., 0., 1.); // identity transform
			// Translation from the beam interaction to the detector centre
			Vector3d centreOffset  = new Vector3d(getDetectorPosition());
			
			// Transform between an aligned detector and the lab frame
			Matrix3d labFromAligned = new Matrix3d(
					1., 0., 0.,
					0., 0., 1.,
					0.,-1., 0.);
			Matrix3d alignedFromLab = new Matrix3d();
			alignedFromLab.invert(labFromAligned);
			
			// Compose the matrix to transform from centred, rotated detector 
			// coordinates to centred, aligned detector coordinates
			Vector3d e1 = new Vector3d(getDetectorXVector());
			Vector3d e3 = new Vector3d(getDetectorNormal());
			// Invert the normal: detectors have the normal going into the screen
			e3.negate();
			// These basis vectors are defined in the lab frame. Convert to the
			// aligned detector frame.
			alignedFromLab.transform(e1);
			alignedFromLab.transform(e3);
			// y axis is mutually perpendicular
			Vector3d e2 = new Vector3d();
			e2.cross(e3, e1);
			
			// rotated to aligned
			Matrix3d alignedFromRotated = new Matrix3d();
			alignedFromRotated.setColumn(0, e1);
			alignedFromRotated.setColumn(1, e2);
			alignedFromRotated.setColumn(2, e3);
			// invert: now aligned to rotated
			Matrix3d rotatedFromAligned = new Matrix3d();
			rotatedFromAligned.invert(alignedFromRotated);
			
			// Composed the translation from the corner origin to the detector centre
			int[] nPx = getNPixels();
			double[] szPx = getPixelSize();
			Vector3d centringTranslation = new Vector3d(nPx[0]*szPx[0]/2/POSITION_TO_PIXELSIZE, nPx[1]*szPx[1]/2/POSITION_TO_PIXELSIZE, 0.);
			centringTranslation.negate();
			
			// Rotation part
			Matrix3d overallRotation = new Matrix3d();
			overallRotation.mul(labFromAligned, alignedFromRotated);
			
			// Translation part: rotate the centring translation
			overallRotation.transform(centringTranslation);
			
			// Translation part: translation of the detector centre
			centringTranslation.add(centreOffset);
			
			transformToLab.set(overallRotation, centringTranslation, 1.0);
		
		}		
		return transformToLab;
	}
	
	/**
	 * A class of non-mutating arithmetic for three-element double vectors
	 */
	private class Maths3d {
		Vector3d value;
		
		public Maths3d(Vector3d v) {
			value = v;
		}
		
		public Maths3d(Maths3d vobj) {
			this(vobj.value);
		}
		
		public Maths3d(double[] darr) {
			this(new Vector3d(Arrays.copyOf(darr, 3)));
		}
		
		public Vector3d get() {
			return value;
		}
		
		public Maths3d plus(Maths3d b) {
			return this.plus(b.value);
		}
		
		public Maths3d plus(Vector3d b) {
			Maths3d a = new Maths3d(new Vector3d(value));
			a.value.add(b);
			return a;
		}
		
		public Maths3d plus(MathsD b) {
			return this.plus(b.get());
		}

		public Maths3d plus(double b) {
			Maths3d a = new Maths3d(new Vector3d(value));
			a.value.add(new Vector3d(b, b, b));
			return a;
		}
		
		public Maths3d minus(Maths3d b) {
			return this.minus(b.value);
		}
		
		public Maths3d minus(Vector3d b) {
			Maths3d a = new Maths3d(new Vector3d(value));
			a.value.sub(b);
			return a;
		}
		
		public Maths3d minus(MathsD b) {
			return this.minus(b.get());
		}

		public Maths3d minus(double b) {
			Maths3d a = new Maths3d(new Vector3d(value));
			a.value.add(new Vector3d(-b, -b, -b));
			return a;
		}
		
		public MathsD dot(Maths3d b) {
			return this.dot(b.value);
		}
		
		public MathsD dot(Vector3d b) {
			return new MathsD(this.value.dot(b));
		}
		
		
		public Maths3d cross(Maths3d b) {
			return this.cross(b.value);
		}
		
		public Maths3d cross(Vector3d b) {
			Vector3d a = new Vector3d();
			a.cross(this.value, b);
			return new Maths3d(a);
		}
		
		public Maths3d times(MathsD b) {
			return this.times(b.get());
		}
		
		public Maths3d times(double b) {
			Maths3d a = new Maths3d(new Vector3d(value));
			a.value.scale(b);
			return a;
		}
		
		
	}
	
	private class MathsD {
		double value;
		
		public MathsD(double v) {
			value = v;
		}
		
		public MathsD(MathsD b) {
			this.value = b.value;
		}
		
		public double get() {
			return value;
		}

		public Maths3d plus(Maths3d b) {
			return b.plus(this);
		}
		
		public Maths3d plus(Vector3d b) {
			Maths3d bobj = new Maths3d(new Vector3d(b));
			return bobj.plus(this);
		}
		
		public MathsD plus(MathsD b) {
			return this.plus(b.get());
		}

		public MathsD plus(double b) {
			MathsD a = new MathsD(this);
			a.value += b;
			return a;
		}
		
		public Maths3d minus(Maths3d b) {
			Maths3d negB = new Maths3d(new Vector3d(b.value));
			negB.get().negate(); // no copying, should be okay
			return this.plus(negB);
		}
		
		public Maths3d minus(Vector3d b) {
			Maths3d bobj = new Maths3d(new Vector3d(b));
			return this.minus(bobj);
		}
		
		public MathsD minus(MathsD b) {
			return this.minus(b.value);
		}

		public MathsD minus(double b) {
			return new MathsD(this.value - b);
		}
		
		public Maths3d times(Maths3d b) {
			return b.times(this);
		}
		
		public Maths3d times(Vector3d b) {
			return new Maths3d(b).times(this);
		}
		
		public MathsD times(MathsD b) {
			return this.times(b.value);
		}
		
		public MathsD times(double b) {
			return new MathsD(this.value * b);
		}

	}

	private double square(double x) { return x*x; }

	private double quadrate(double x, double y) { return Math.sqrt(square(x) + square(y)); }
	
}
