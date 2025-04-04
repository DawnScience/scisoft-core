/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.Arrays;
import java.util.Objects;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

/**
 * This represents all of the input parameters and options for the mapper
 * <p>
 * The (un-deprecated) setters map to possible entries in JSON (with lower cased initial letter).
 */
public class MillerSpaceMapperBean implements Cloneable {
	
	private String[] inputs;
	private String output;
	private String splitterName;
	private double splitterParameter;
	private double scaleFactor;

	private boolean reduceToNonZero;

	private String entryPath;
	private String instrumentName;
	private String attenuatorName;
	private String monitorName;
	private String detectorName;
	private String dataName;
	private String sampleName;

	private boolean listMillerEntries;

	private int[] shape;
	private double[] start;
	private double[] step;

	private int[] region; // masking ROI where only points within its bounds contribute to volume
	private String maskFilePath; // file path to image weight 

	private OutputMode outputMode = OutputMode.Volume_HKL;
	private boolean toCrystalFrame = true;

	private int[][] pixelIndexes; // to used calculate coordinates at given pixels indexes
	private String images;

	private boolean correctPolarization = true;
	private double[] thirdAxis;
	private double[] aziPlaneNormal;

	public enum OutputMode {
		/**
		 * Volume in Miller space
		 */
		Volume_HKL(3, false),
		/**
		 * Volume in q-space (crystal frame)
		 */
		Volume_Q(3, true),
		/**
		 * Volume in cylindrical polar q-space (crystal frame)
		 */
		Volume_QCP(3, true),
		/**
		 * Volume in equatorial stereographic q-space (crystal frame)
		 */
		Volume_QES(3, true),
		/**
		 * Area in Miller-space (H,K)
		 */
		Area_HK(2, false),
		/**
		 * Area in Miller-space (K,L)
		 */
		Area_KL(2, false),
		/**
		 * Area in Miller-space (L,H)
		 */
		Area_LH(2, false),
		/**
		 * Area in q-space (parallel, perpendicular to sample surface)
		 */
		Area_QPP(2, true),
		/**
		 * Area in q-space (X,Y)
		 */
		Area_QXY(2, true),
		/**
		 * Area in q-space (Y,Z)
		 */
		Area_QYZ(2, true),
		/**
		 * Area in q-space (Z,X)
		 */
		Area_QZX(2, true),
		/**
		 * Line in Miller space (H)
		 */
		Line_H(1, false),
		/**
		 * Line in Miller space (K)
		 */
		Line_K(1, false),
		/**
		 * Line in Miller space (L)
		 */
		Line_L(1, false),
		/**
		 * Line in q-space (2 x theta is scattering angle)
		 */
		Line_Theta(1, true),
		/**
		 * Line in q-space (2 x theta is scattering angle, also in degrees)
		 */
		Line_2Theta(1, true),
		/**
		 * Line in q-space (X)
		 */
		Line_QX(1, true),
		/**
		 * Line in q-space (Y)
		 */
		Line_QY(1, true),
		/**
		 * Line in q-space (Z)
		 */
		Line_QZ(1, true),
		/**
		 * Coordinates in Miller space
		 */
		Coords_HKL(0, false),
		/**
		 * Coordinates in q-space (momentum transfer)
		 */
		Coords_Q(0, true),
		;

		private int rank;
		private boolean q;

		private OutputMode(int rank, boolean q) {
			this.rank = rank;
			this.q = q;
		}

		public int getRank() {
			return rank;
		}

		public boolean isVolume() {
			return rank == 3;
		}

		public boolean isArea() {
			return rank == 2;
		}

		public boolean isLine() {
			return rank == 1;
		}

		public boolean isQ() {
			return q;
		}
	}

	public MillerSpaceMapperBean() {
	}

	/**
	 * @return inputs Nexus files
	 */
	public String[] getInputs() {
		return inputs;
	}

	/**
	 * @param inputs Nexus files
	 */
	public void setInputs(String... inputs) {
		if (inputs == null || inputs.length == 0) {
			throw new IllegalArgumentException("One or more file names must be supplied");
		}
		this.inputs = inputs;
	}

	/**
	 * @return output name of HDF5 file to be created
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output name of HDF5 file to be created
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * @return name of pixel splitting algorithm
	 */
	public String getSplitterName() {
		return splitterName;
	}

	/**
	 * @param splitterName name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 */
	public void setSplitterName(String splitterName) {
		this.splitterName = splitterName;
	}

	/**
	 * @return value of pixel splitting parameter
	 */
	public double getSplitterParameter() {
		return splitterParameter;
	}

	/**
	 * @param splitterParameter splitter parameter (usually a scale length)
	 */
	public void setSplitterParameter(double splitterParameter) {
		this.splitterParameter = splitterParameter;
	}

	/**
	 * @return scale upsampling factor
	 */
	public double getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * Set scale for upsampling images
	 * @param scaleFactor upsampling factor
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	/**
	 * @param millerShape shape of volume in Miller space (can be null to be autoset)
	 * @deprecated For deserialization only
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.23")
	private void setMillerShape(int[] millerShape) {
		if (millerShape != null) {
			if (shape != null) {
				throw new IllegalArgumentException("Changing shape after it has already been set is forbidden.");
			}
			shape = millerShape;
		}
	}

	/**
	 * @param millerStart starting coordinates of volume (can be null to be autoset)
	 * @deprecated For deserialization only
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.23")
	private void setMillerStart(double[] millerStart) {
		if (millerStart != null) {
			if (start != null) {
				throw new IllegalArgumentException("Changing start after it has already been set is forbidden.");
			}
			start = millerStart;
		}
	}

	/**
	 * @param millerStep sides of voxels in Miller space
	 * @deprecated For deserialization only
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.23")
	private void setMillerStep(double... millerStep) {
		if (millerStep != null) {
			if (step != null) {
				throw new IllegalArgumentException("Changing step after it has already been set is forbidden.");
			}
			outputMode = OutputMode.Volume_HKL;
			step = millerStep;
		}
	}

	/**
	 * @return true if mapper attempts to reduce output to sub-volume with non-zero data
	 */
	public boolean isReduceToNonZero() {
		return reduceToNonZero;
	}

	/**
	 * Set to reduce output to sub-volume with non-zero data
	 * @param reduceToNonZero
	 */
	public void setReduceToNonZero(boolean reduceToNonZero) {
		this.reduceToNonZero = reduceToNonZero;
	}

	public String getEntryPath() {
		return entryPath;
	}

	/**
	 * @param entryPath (can be null to imply 1st NXentry)
	 */
	public void setEntryPath(String entryPath) {
		this.entryPath = entryPath;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	/**
	 * @param instrumentName name of instrument in entry (can be null to imply 1st NXinstrument)
	 */
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public String getAttenuatorName() {
		return attenuatorName;
	}

	/**
	 * @param attenuatorName name of attenuator in instrument (can be null to imply 1st NXattenuator)
	 */
	public void setAttenuatorName(String attenuatorName) {
		this.attenuatorName = attenuatorName;
	}

	public String getMonitorName() {
		return monitorName;
	}

	/**
	 * @param monitorName name of monitor in instrument (can be null to imply 1st NXmonitor)
	 */
	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getDetectorName() {
		return detectorName;
	}

	/**
	 * @param detectorName name of detector in instrument (can be null to imply 1st NXdetector)
	 */
	public void setDetectorName(String detectorName) {
		this.detectorName = detectorName;
	}

	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName name of data in detector (can be null to imply 1st dataset with signal attribute)
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getSampleName() {
		return sampleName;
	}

	/**
	 * @param sampleName name of sample in entry (can be null to imply 1st NXsample)
	 */
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	/**
	 * @deprecated Does nothing but needed for backward compatibility with old JSON files
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.21")
	private void setOtherPaths(String... otherPaths) {

	}

	public boolean isListMillerEntries() {
		return listMillerEntries;
	}

	/**
	 * @param listMillerEntries if true, output list of hkls and corrected pixel intensities
	 */
	public void setListMillerEntries(boolean listMillerEntries) {
		this.listMillerEntries = listMillerEntries;
	}

	/**
	 * @param qShape shape of q space volume (can be null)
	 * @deprecated For deserialization only
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.23")
	private void setQShape(int[] qShape) {
		if (qShape != null) {
			if (shape != null) {
				throw new IllegalArgumentException("Changing shape after it has already been set is forbidden.");
			}
			shape = qShape;
		}
	}

	/**
	 * @param qStart starting coordinates of volume of q space (can be null)
	 * @deprecated For deserialization only
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.23")
	private void setQStart(double[] qStart) {
		if (qStart != null) {
			if (start!= null) {
				throw new IllegalArgumentException("Changing start after it has already been set is forbidden.");
			}
			start = qStart;
		}
	}

	/**
	 * @param qStep sides of voxels in q space (can be null)
	 * @deprecated For deserialization only
	 */
	@SuppressWarnings("unused")
	@Deprecated(since="Dawn 2.23")
	private void setQStep(double... qStep) {
		if (qStep != null) {
			if (step != null) {
				throw new IllegalArgumentException("Changing step after it has already been set is forbidden.");
			}
			outputMode = OutputMode.Volume_Q;
			step = qStep;
		}
	}

	public int[] getShape() {
		return shape;
	}

	/**
	 * @param shape shape of volume (can be null)
	 */
	public void setShape(int[] shape) {
		this.shape = shape;
	}

	public double[] getStart() {
		return start;
	}

	/**
	 * @param start starting coordinates of volume (can be null)
	 */
	public void setStart(double[] start) {
		this.start = start;
	}

	public double[] getStep() {
		return step;
	}

	/**
	 * @param step sides of voxels (can be null if not in area or volume mode)
	 */
	public void setStep(double... step) {
		this.step = step;
	}

	/**
	 * Set rectangular region that defines the area with its bounds that contribute to output
	 * @param sx start x
	 * @param ex end x (exclusive)
	 * @param sy start y
	 * @param ey end y (exclusive)
	 */
	public void setRegion(int sx, int ex, int sy, int ey) {
		this.region = new int[] {sx, ex, sy, ey};
	}

	public int[] getRegion() {
		return region;
	}

	/**
	 * Set the file path to a mask dataset that will be applied to each pixel in image. If shape is (N,2) then treat as
	 * list of index positions of pixels to ignore
	 * @param mask file path
	 */
	public void setMaskFilePath(String weight) {
		this.maskFilePath = weight;
	}

	public String getMaskFilePath() {
		return maskFilePath;
	}

	public OutputMode getOutputMode() {
		return outputMode;
	}

	/**
	 * Set output mode
	 * @param outputMode
	 */
	public void setOutputMode(OutputMode outputMode) {
		this.outputMode = outputMode;
	}

	public int[][] getPixelIndexes() {
		return pixelIndexes;
	}

	/**
	 * Set input pixel indexes (frame - optional, row, column)
	 * @param pixelIndexes (can be Nx2 for first frame only or Nx3)
	 */
	public void setPixelIndexes(int[][] pixelIndexes) {
		this.pixelIndexes = pixelIndexes;
	}

	/**
	 * Set images selection by slice string
	 * @param images slice string
	 */
	public void setImages(String images) {
		this.images = images;
	}

	public String getImages() {
		return images;
	}

	/**
	 * Set true to correct for polarization factor caused by transformation from laboratory frame to scattering plane
	 * @param correctPolarization
	 */
	public void setCorrectPolarization(boolean correctPolarization) {
		this.correctPolarization = correctPolarization;
	}

	public boolean isCorrectPolarization() {
		return correctPolarization;
	}

	/**
	 * Set third axis direction for volume orientation
	 * @param thirdAxis
	 */
	public void setThirdAxis(double[] thirdAxis) {
		this.thirdAxis = thirdAxis;
	}

	public double[] getThirdAxis() {
		return thirdAxis;
	}

	/**
	 * Set normal to azimuthal plane of reference for volume orientation
	 * @param aziPlane
	 */
	public void setAziPlaneNormal(double[] aziPlane) {
		this.aziPlaneNormal = aziPlane;
	}

	public double[] getAziPlaneNormal() {
		return aziPlaneNormal;
	}

	/**
	 * @return orientation of volume (transforms from volume to lab) or null
	 */
	public static Matrix3d getVolumeOrientation(MillerSpaceMapperBean b) {
		double[] vThirdAxis = b.getThirdAxis();
		double[] vAziPlaneNormal = b.getAziPlaneNormal();

		if (vThirdAxis == null || vAziPlaneNormal == null) {
			return null;
		}
		Vector3d z = new Vector3d(vThirdAxis);
		double ls = z.lengthSquared();
		if (ls == 0) {
			throw new IllegalArgumentException("Third axis must not have zero length");
		}

		z.scale(1. / Math.sqrt(ls));
		Vector3d y = new Vector3d(vAziPlaneNormal);
		y.scaleAdd(-y.dot(z), z, y); // remove component of y parallel to z
		ls = y.lengthSquared();
		if (ls == 0) {
			throw new IllegalArgumentException("Azimuthal plane normal must not be parallel to third axis");
		}

		y.scale(1. / Math.sqrt(ls)); // now y is perpendicular to z
		Vector3d x = new Vector3d();
		x.cross(y, z);
		Matrix3d o = new Matrix3d();
		o.setColumn(0, x);
		o.setColumn(1, y);
		o.setColumn(2, z);
		return o;
	}

	/**
	 * Set output frame to crystal (default)
	 * @param toCrystalFrame if true, output to crystal frame otherwise leave in lab frame
	 */
	public void setToCrystalFrame(boolean toCrystalFrame) {
		this.toCrystalFrame = toCrystalFrame;
	}

	public boolean isToCrystalFrame() {
		return toCrystalFrame;
	}

	@Override
	protected MillerSpaceMapperBean clone() {
		MillerSpaceMapperBean copy = null;
		try {
			copy = (MillerSpaceMapperBean) super.clone();
			copy.inputs = Arrays.copyOf(inputs, inputs.length);
			copy.shape = shape == null ? null : shape.clone();
			copy.start = start == null ? null : start.clone();
			copy.step  = step  == null ? null : step.clone();
			copy.region = region == null ? null : region.clone();
			if (pixelIndexes != null) {
				copy.pixelIndexes = Arrays.stream(pixelIndexes)
						.map(int[]::clone).toArray(int[][]::new);
			}
			copy.images = images;
			copy.attenuatorName = attenuatorName;
			copy.aziPlaneNormal = aziPlaneNormal == null ? null : aziPlaneNormal.clone();
			copy.correctPolarization = correctPolarization;
			copy.dataName = dataName;
			copy.detectorName = detectorName;
			copy.entryPath = entryPath;
			copy.listMillerEntries = listMillerEntries;
			copy.maskFilePath = maskFilePath;
			copy.monitorName = monitorName;
			copy.outputMode = outputMode;
			copy.toCrystalFrame = toCrystalFrame;
			copy.reduceToNonZero = reduceToNonZero;
			copy.sampleName = sampleName;
			copy.scaleFactor = scaleFactor;
			copy.splitterName = splitterName;
			copy.splitterParameter = splitterParameter;
			copy.thirdAxis = thirdAxis == null ? null : thirdAxis.clone();
		} catch (CloneNotSupportedException e) {
		}
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(aziPlaneNormal);
		result = prime * result + Arrays.hashCode(inputs);
		result = prime * result + Arrays.deepHashCode(pixelIndexes);
		result = prime * result + Arrays.hashCode(region);
		result = prime * result + Arrays.hashCode(shape);
		result = prime * result + Arrays.hashCode(start);
		result = prime * result + Arrays.hashCode(step);
		result = prime * result + Arrays.hashCode(thirdAxis);
		result = prime * result + Objects.hash(attenuatorName, correctPolarization, dataName, detectorName, entryPath,
				images, instrumentName, listMillerEntries, maskFilePath, monitorName, output, outputMode,
				reduceToNonZero, sampleName, scaleFactor, splitterName, splitterParameter, toCrystalFrame);
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
		MillerSpaceMapperBean other = (MillerSpaceMapperBean) obj;
		return Objects.equals(attenuatorName, other.attenuatorName)
				&& Arrays.equals(aziPlaneNormal, other.aziPlaneNormal)
				&& correctPolarization == other.correctPolarization && Objects.equals(dataName, other.dataName)
				&& Objects.equals(detectorName, other.detectorName) && Objects.equals(entryPath, other.entryPath)
				&& Objects.equals(images, other.images) && Arrays.equals(inputs, other.inputs)
				&& Objects.equals(instrumentName, other.instrumentName) && listMillerEntries == other.listMillerEntries
				&& Objects.equals(maskFilePath, other.maskFilePath) && Objects.equals(monitorName, other.monitorName)
				&& Objects.equals(output, other.output) && outputMode == other.outputMode && toCrystalFrame == other.toCrystalFrame
				&& Arrays.deepEquals(pixelIndexes, other.pixelIndexes) && reduceToNonZero == other.reduceToNonZero
				&& Arrays.equals(region, other.region) && Objects.equals(sampleName, other.sampleName)
				&& Double.doubleToLongBits(scaleFactor) == Double.doubleToLongBits(other.scaleFactor)
				&& Arrays.equals(shape, other.shape) && Objects.equals(splitterName, other.splitterName)
				&& Double.doubleToLongBits(splitterParameter) == Double.doubleToLongBits(other.splitterParameter)
				&& Arrays.equals(start, other.start) && Arrays.equals(step, other.step)
				&& Arrays.equals(thirdAxis, other.thirdAxis);
	}

	/**
	 * Create bean to process Nexus file with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param scale upsampling factor
	 * @return bean
	 */
	public static MillerSpaceMapperBean createBeanWithList(String[] inputs, String output, double scale) {
		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(null);
		bean.setSplitterParameter(0);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(false);
		bean.setShape(null);
		bean.setStart(null);
		bean.setStep(null);
		bean.setListMillerEntries(true);
		return bean;
	}

	/**
	 * Create bean to process Nexus file with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param isQSpace if true, map to q space otherwise map to Miller space
	 * @param mDelta sides of voxels in volume
	 * @return bean
	 */
	public static MillerSpaceMapperBean createBeanWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, boolean isQSpace, double... mDelta) {
		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(splitter);
		bean.setSplitterParameter(p);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(reduceToNonZero);
		bean.setOutputMode(isQSpace ? OutputMode.Volume_Q : OutputMode.Volume_HKL);
		bean.setStep(mDelta);
		bean.setListMillerEntries(false);
		return bean;
	}

	/**
	 * Create bean to process Nexus files
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mShape shape of volume
	 * @param mStart start coordinates
	 * @param mDelta sides of voxels
	 * @param isQSpace if true then map to q space otherwise to Miller space
	 * @return bean
	 */
	public static MillerSpaceMapperBean createBean(String[] inputs, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double[] mDelta, boolean isQSpace) {
		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(splitter);
		bean.setSplitterParameter(p);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(false);
		if (isQSpace) {
			bean.setOutputMode(OutputMode.Volume_Q);
		}
		bean.setShape(mShape);
		bean.setStart(mStart);
		bean.setStep(mDelta);
		bean.setListMillerEntries(false);
		return bean;
	}

}