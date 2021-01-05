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

/**
 * This represents all of the input parameters and options for the mapper
 */
public class MillerSpaceMapperBean implements Cloneable {
	private String[] inputs;
	private String output;
	private String splitterName;
	private double splitterParameter;
	private double scaleFactor;

	private int[] millerShape;
	private double[] millerStart;
	private double[] millerStep;

	private boolean reduceToNonZero;

	private String entryPath;
	private String instrumentName;
	private String attenuatorName;
	private String detectorName;
	private String dataName;
	private String sampleName;
	private String[] otherPaths;

	private boolean listMillerEntries;

	private int[] qShape;
	private double[] qStart;
	private double[] qStep;

	private int[] region; // masking ROI where only point within its bounds contribute to volume
	private String maskFilePath; // file path to image weight 

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
	 * @param scaleFactor upsampling factor
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	/**
	 * @return shape of volume in Miller space (can be null to be autoset)
	 */
	public int[] getMillerShape() {
		return millerShape;
	}

	/**
	 * @param millerShape shape of volume in Miller space (can be null to be autoset)
	 */
	public void setMillerShape(int[] millerShape) {
		this.millerShape = millerShape;
	}

	/**
	 * @return starting coordinates of volume (can be null to be autoset)
	 */
	public double[] getMillerStart() {
		return millerStart;
	}

	/**
	 * @param millerStart starting coordinates of volume (can be null to be autoset)
	 */
	public void setMillerStart(double[] millerStart) {
		this.millerStart = millerStart;
	}

	/**
	 * @return sides of voxels in Miller space
	 */
	public double[] getMillerStep() {
		return millerStep;
	}

	/**
	 * @param millerStep sides of voxels in Miller space
	 */
	public void setMillerStep(double... millerStep) {
		this.millerStep = millerStep;
	}

	/**
	 * @return true if mapper attempts to reduce output to sub-volume with non-zero data
	 */
	public boolean isReduceToNonZero() {
		return reduceToNonZero;
	}

	/**
	 * @param reduceToNonZero if true, attempts to reduce output to sub-volume with non-zero data
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

	public String[] getOtherPaths() {
		return otherPaths;
	}

	/**
	 * @param otherPaths
	 */
	public void setOtherPaths(String... otherPaths) {
		this.otherPaths = otherPaths;
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

	public int[] getQShape() {
		return qShape;
	}

	/**
	 * @param qShape shape of q space volume (can be null)
	 */
	public void setQShape(int[] qShape) {
		this.qShape = qShape;
	}

	public double[] getQStart() {
		return qStart;
	}

	/**
	 * @param qStart starting coordinates of volume of q space (can be null)
	 */
	public void setQStart(double[] qStart) {
		this.qStart = qStart;
	}

	public double[] getQStep() {
		return qStep;
	}

	/**
	 * @param qStep sides of voxels in q space (can be null)
	 */
	public void setQStep(double... qStep) {
		this.qStep = qStep;
	}

	/**
	 * Set rectangular region that defines the area with its bounds that contribute to volume
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

	@Override
	protected MillerSpaceMapperBean clone() {
		MillerSpaceMapperBean copy = null;
		try {
			copy = (MillerSpaceMapperBean) super.clone();
			copy.inputs = Arrays.copyOf(inputs, inputs.length);
			copy.otherPaths = otherPaths == null ? null : Arrays.copyOf(otherPaths, otherPaths.length);
			copy.millerShape = millerShape == null ? null : millerShape.clone();
			copy.millerStart = millerStart == null ? null : millerStart.clone();
			copy.millerStep = millerStep == null ? null : millerStep.clone();
			copy.qShape = qShape == null ? null : qShape.clone();
			copy.qStart = qStart == null ? null : qStart.clone();
			copy.qStep = qStep == null ? null : qStep.clone();
			copy.region = region == null ? null : region.clone();
			copy.maskFilePath = maskFilePath;
		} catch (CloneNotSupportedException e) {
		}
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attenuatorName == null) ? 0 : attenuatorName.hashCode());
		result = prime * result + ((dataName == null) ? 0 : dataName.hashCode());
		result = prime * result + ((detectorName == null) ? 0 : detectorName.hashCode());
		result = prime * result + ((entryPath == null) ? 0 : entryPath.hashCode());
		result = prime * result + Arrays.hashCode(inputs);
		result = prime * result + ((instrumentName == null) ? 0 : instrumentName.hashCode());
		result = prime * result + (listMillerEntries ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(millerShape);
		result = prime * result + Arrays.hashCode(millerStart);
		result = prime * result + Arrays.hashCode(millerStep);
		result = prime * result + Arrays.hashCode(otherPaths);
		result = prime * result + ((output == null) ? 0 : output.hashCode());
		result = prime * result + Arrays.hashCode(qShape);
		result = prime * result + Arrays.hashCode(qStart);
		result = prime * result + Arrays.hashCode(qStep);
		result = prime * result + (reduceToNonZero ? 1231 : 1237);
		result = prime * result + ((sampleName == null) ? 0 : sampleName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(scaleFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((splitterName == null) ? 0 : splitterName.hashCode());
		temp = Double.doubleToLongBits(splitterParameter);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((region == null) ? 0 : Arrays.hashCode(region));
		result = prime * result + ((maskFilePath == null) ? 0 : maskFilePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MillerSpaceMapperBean)) {
			return false;
		}
		MillerSpaceMapperBean other = (MillerSpaceMapperBean) obj;
		if (attenuatorName == null) {
			if (other.attenuatorName != null) {
				return false;
			}
		} else if (!attenuatorName.equals(other.attenuatorName)) {
			return false;
		}
		if (dataName == null) {
			if (other.dataName != null) {
				return false;
			}
		} else if (!dataName.equals(other.dataName)) {
			return false;
		}
		if (detectorName == null) {
			if (other.detectorName != null) {
				return false;
			}
		} else if (!detectorName.equals(other.detectorName)) {
			return false;
		}
		if (entryPath == null) {
			if (other.entryPath != null) {
				return false;
			}
		} else if (!entryPath.equals(other.entryPath)) {
			return false;
		}
		if (!Arrays.equals(inputs, other.inputs)) {
			return false;
		}
		if (instrumentName == null) {
			if (other.instrumentName != null) {
				return false;
			}
		} else if (!instrumentName.equals(other.instrumentName)) {
			return false;
		}
		if (listMillerEntries != other.listMillerEntries) {
			return false;
		}
		if (!Arrays.equals(millerShape, other.millerShape)) {
			return false;
		}
		if (!Arrays.equals(millerStart, other.millerStart)) {
			return false;
		}
		if (!Arrays.equals(millerStep, other.millerStep)) {
			return false;
		}
		if (!Arrays.equals(otherPaths, other.otherPaths)) {
			return false;
		}
		if (output == null) {
			if (other.output != null) {
				return false;
			}
		} else if (!output.equals(other.output)) {
			return false;
		}
		if (!Arrays.equals(qShape, other.qShape)) {
			return false;
		}
		if (!Arrays.equals(qStart, other.qStart)) {
			return false;
		}
		if (!Arrays.equals(qStep, other.qStep)) {
			return false;
		}
		if (reduceToNonZero != other.reduceToNonZero) {
			return false;
		}
		if (sampleName == null) {
			if (other.sampleName != null) {
				return false;
			}
		} else if (!sampleName.equals(other.sampleName)) {
			return false;
		}
		if (Double.doubleToLongBits(scaleFactor) != Double.doubleToLongBits(other.scaleFactor)) {
			return false;
		}
		if (splitterName == null) {
			if (other.splitterName != null) {
				return false;
			}
		} else if (!splitterName.equals(other.splitterName)) {
			return false;
		}
		if (Double.doubleToLongBits(splitterParameter) != Double.doubleToLongBits(other.splitterParameter)) {
			return false;
		}
		if (!Arrays.equals(region, other.region)) {
			return false;
		}
		if (!Objects.equals(maskFilePath, other.maskFilePath)) {
			return false;
		}
		return true;
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
		bean.setMillerShape(null);
		bean.setMillerStart(null);
		bean.setMillerStep(null);
		bean.setQShape(null);
		bean.setQStart(null);
		bean.setQStep(null);
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
	 * @param mDelta sides of voxels in Miller space
	 * @param qDelta sides of voxels in q space
	 * @return bean
	 */
	public static MillerSpaceMapperBean createBeanWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double[] mDelta, double[] qDelta) {
		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(splitter);
		bean.setSplitterParameter(p);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(reduceToNonZero);
		bean.setMillerShape(null);
		bean.setMillerStart(null);
		bean.setMillerStep(mDelta);
		bean.setQShape(null);
		bean.setQStart(null);
		bean.setQStep(qDelta);
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
	 * @param mShape shape of Miller space volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @param qShape shape of q space volume
	 * @param qStart start coordinates in q space
	 * @param qDelta sides of voxels in q space
	 * @return bean
	 */
	public static MillerSpaceMapperBean createBean(String[] inputs, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double[] mDelta, int[] qShape, double[] qStart, double[] qDelta) {
		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(splitter);
		bean.setSplitterParameter(p);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(false);
		bean.setMillerShape(mShape);
		bean.setMillerStart(mStart);
		bean.setMillerStep(mDelta);
		bean.setQShape(qShape);
		bean.setQStart(qStart);
		bean.setQStep(qDelta);
		bean.setListMillerEntries(false);
		return bean;
	}
}