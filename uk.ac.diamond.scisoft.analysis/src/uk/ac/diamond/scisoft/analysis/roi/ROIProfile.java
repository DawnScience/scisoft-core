/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.roi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.dawnsci.analysis.dataset.impl.function.LineSample;
import org.eclipse.dawnsci.analysis.dataset.roi.LinearROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;

import uk.ac.diamond.scisoft.analysis.dataset.function.Integrate2D;
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToPolarAndIntegrate;
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToRotatedCartesianAndIntegrate;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

/**
 * Utility methods for calculating region of interest profiles.
 */
public class ROIProfile {
	
	public enum XAxis {
		ANGLE("2theta / deg") {
			@Override
			public double toANGLE(double initVal, Double lambda) throws Exception {
				return initVal; //Do nothing
			}

			@Override
			public double toPIXEL(double initVal) throws Exception {
				throw new Exception("Unimplemented method.");
			}

			@Override
			public double toRESOLUTION(double initVal, Double lambda) throws Exception {
				return lambda/(2*Math.sin(calcThetaInRadians(initVal)));
			}

			@Override
			public double toQ(double initVal, Double lambda) throws Exception {
				return (4*Math.PI/lambda)*Math.sin(calcThetaInRadians(initVal));
			}
		},
		PIXEL("Pixel Number") {
			@Override
			public double toANGLE(double initVal, Double lambda) throws Exception {
				throw new Exception("Unimplemented method.");
			}

			@Override
			public double toPIXEL(double initVal) throws Exception {
				return initVal; //Do nothing
			}

			@Override
			public double toRESOLUTION(double initVal, Double lambda) throws Exception {
				throw new Exception("Unimplemented method.");
			}

			@Override
			public double toQ(double initVal, Double lambda) throws Exception {
				throw new Exception("Unimplemented method.");
			}
		},
		RESOLUTION("d-space") {
			@Override
			public double toANGLE(double initVal, Double lambda) throws Exception {
				Double thRadians = Math.asin(lambda/(2*initVal));
				return calcTwoThetaInDegrees(thRadians);
			}

			@Override
			public double toPIXEL(double initVal) throws Exception {
				throw new Exception("Unimplemented method.");
			}

			@Override
			public double toRESOLUTION(double initVal, Double lambda) throws Exception {
				return initVal; //Do nothing
			}

			@Override
			public double toQ(double initVal, Double lambda) throws Exception {
				return (2*Math.PI)/initVal;
			}
		},
		Q("Q-space") {
			@Override
			public double toANGLE(double initVal, Double lambda) throws Exception {
				double thRadians = Math.asin((initVal*lambda)/(4*Math.PI));
				return calcTwoThetaInDegrees(thRadians);
			}

			@Override
			public double toPIXEL(double initVal) throws Exception {
				throw new Exception("Unimplemented method.");
			}

			@Override
			public double toRESOLUTION(double initVal, Double lambda) throws Exception {
				return (2*Math.PI)/initVal;
			}

			@Override
			public double toQ(double initVal, Double lambda) throws Exception {
				return initVal; //Do nothing
			}
		};
		
		//Enum fields and constructor
		private final String axisLabel;
		private XAxis(String axisLabel){
			this.axisLabel = axisLabel;
		}
		
		//Enum methods
		public final String getAxisLabel() {
			return this.axisLabel;
		}
		
		/**
		 * Calculate value of theta in radians from a given two theta value in degrees
		 * @param tthVal
		 * @return theta (radians)
		 */
		private static double calcThetaInRadians(double tthVal) {
			return Math.toRadians(tthVal/2);
		}
		
		/**
		 * Calculate value of two theta in degrees fomr a given theta value in radians
		 * @param thRadians
		 * @return two theta (degrees)
		 */
		private static double calcTwoThetaInDegrees(double thRadians) {
			return 2*Math.toDegrees(thRadians);
		}
		//Conversion abstract methods
		public abstract double toANGLE(double initVal, Double lambda) throws Exception;
		public abstract double toPIXEL(double initVal) throws Exception;
		public abstract double toRESOLUTION(double initVal, Double lambda) throws Exception;
		public abstract double toQ(double initVal, Double lambda) throws Exception;
		
	}
	
	/**
	 * @param data
	 * @param lroi
	 * @param step
	 *            size
	 * @return line profile
	 */
	public static Dataset[] line(Dataset data, LinearROI lroi, double step) {
		
		return ROIProfile.line(data, null, lroi, step, false);
	}
	
	public static Dataset[] line(Dataset data, Dataset mask, LinearROI lroi, double step, boolean maskWithNans) {

		double[] spt = lroi.getPoint();
		double[] ept = lroi.getEndPoint();
		Dataset[] profiles = new Dataset[] { null, null };

		LineSample ls = new LineSample(spt[0], spt[1], ept[0], ept[1], step);

		if (mask != null && data != null) {
			if (data.isCompatibleWith(mask)) {
				// TODO both multiply and nanalize create copies of the whole data passed in
				if (!maskWithNans || !(mask instanceof BooleanDataset)) {
					data = Maths.multiply(data, mask);
				} else {
					// Masks values to NaN, also changes dtype to Float
					data = nanalize(data, (BooleanDataset)mask);
				}
			}
		}

		List<? extends Dataset> dsets = ls.value(data);

		if (dsets == null)
			return null;

		profiles[0] = dsets.get(0);

		if (lroi.isCrossHair()) {
			spt = lroi.getPerpendicularBisectorPoint(0.0);
			ept = lroi.getPerpendicularBisectorPoint(1.0);

			LineSample bls = new LineSample(spt[0], spt[1], ept[0], ept[1], step);

			dsets = bls.value(data);

			if (dsets != null)
				profiles[1] = dsets.get(0);
		}

		return profiles;
	}

	private static void clippingCompensate(IDataset data, IDataset mask, Dataset[] profiles,
			DatasetToDatasetFunction map) {
		// normalise plot for case when region is clipped to size of image
		// and/or when a mask is specified
		if (mask == null) {
			mask = DatasetFactory.ones(data.getShape(), Dataset.BOOL);
		}

		List<? extends Dataset> dsets = map.value(mask);
		Dataset nintx = dsets.get(1);
		Dataset ninty = dsets.get(0);
		// calculate fraction in each element that was not clipped
		nintx = Maths.dividez(nintx, dsets.get(3));
		ninty = Maths.dividez(ninty, dsets.get(2));

		profiles[0] = Maths.dividez(profiles[0], nintx);
		profiles[1] = Maths.dividez(profiles[1], ninty);
	}

	/**
	 * @param data
	 * @param rroi
	 * @return box profile
	 */
	public static Dataset[] box(Dataset data, RectangularROI rroi) {
		return box(data, null, rroi);
	}

	/**
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param rroi
	 * @return box profile
	 */
	public static Dataset[] box(Dataset data, Dataset mask, RectangularROI rroi) {
		return box(data, mask, rroi, false);
	}

	/**
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param rroi
	 * @param maskWithNans - normally masked pixels will use a multiply with 0 to mask. The plotting
	 *                       deals with NaNs however, in this case we can set maskWithNans true and masked
	 *                       pixels are NaN instead of 0.
	 * @return box profile
	 */
	public static Dataset[] box(Dataset data, Dataset mask, RectangularROI rroi, boolean maskWithNans) {

		int[] spt = rroi.getIntPoint();
		int[] len = rroi.getIntLengths();
		double ang = rroi.getAngle();
		boolean clip = rroi.isClippingCompensation();
		Dataset[] profiles = new Dataset[] { null, null };


		if (len[0] == 0)
			len[0] = 1;
		if (len[1] == 0)
			len[1] = 1;

		if (ang == 0.0) {
			
			final int xtart  = Math.max(0,  spt[1]);
			final int xend   = Math.min(spt[1] + len[1],  data.getShape()[0]);
			final int ystart = Math.max(0,  spt[0]);
			final int yend   = Math.min(spt[0] + len[0],  data.getShape()[1]);
			
			// We slice down data to reduce the work the masking and the integrate needs to do.
			// TODO Does this always work? This makes large images profile better...
			Dataset slicedData = null;
			try {
			    slicedData = data.getSlice(new int[]{xtart,   ystart}, 
					                       new int[]{xend,    yend},
					                       new int[]{1,1});
			} catch (Exception ne) {
				// We cannot process the profiles for a region totally outside the image!
				return null;
			}
			
			final Dataset slicedMask = mask!=null
					                         ? mask.getSlice(new int[]{xtart, ystart}, 
					                                         new int[]{xend,  yend},
					                                         new int[]{1,1})
					                         : null;
			
			
			if (slicedMask != null && slicedData != null) {
				if (slicedData.isCompatibleWith(slicedMask)) {
					clip = true;
					if (!maskWithNans || !(slicedMask instanceof BooleanDataset)) {
						slicedData = Maths.multiply(slicedData, slicedMask);
					} else {
						// Masks values to NaN, also changes dtype to Float
						slicedData = nanalize(slicedData, (BooleanDataset)slicedMask);
					}
				}
			}

			if (slicedData==null){
				slicedData = data;
			}

			
			Integrate2D int2d = new Integrate2D(0, 0, Math.min(len[0], data.getShape()[1]), Math.min(len[1], data.getShape()[0]));

			List<? extends Dataset> dsets = int2d.value(slicedData);
			if (dsets == null) return null;

			profiles[0] = maskWithNans
					    ? processColumnNans(dsets.get(1), slicedData)
					    : dsets.get(1);
			
			profiles[1] = maskWithNans
					    ?  processRowNans(dsets.get(0), slicedData)
					    : dsets.get(0);

		} else {
			
			if (mask != null && data != null) {
				if (data.isCompatibleWith(mask)) {
					clip = true;
					// TODO both multiply and nanalize create copies of the whole data passed in
					if (!maskWithNans || !(mask instanceof BooleanDataset)) {
						data = Maths.multiply(data, mask);
					} else {
						// Masks values to NaN, also changes dtype to Float
						data = nanalize(data, (BooleanDataset)mask);
					}
				}
			}

			MapToRotatedCartesianAndIntegrate rcmapint = new MapToRotatedCartesianAndIntegrate(spt[0], spt[1], len[0],
					len[1], ang, false);
			List<? extends Dataset> dsets = rcmapint.value(data);
			if (dsets == null)
				return null;

			profiles[0] = dsets.get(1);
			profiles[1] = dsets.get(0);

			if (clip) {
				clippingCompensate(data, mask, profiles, rcmapint);
			}
		}

		return profiles;
	}

	/**
	 * Returns the mean of the dataset given a RectangularROI
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param rroi
	 * @param maskWithNans - normally masked pixels will use a multiply with 0 to mask. The plotting
	 *                       deals with NaNs however, in this case we can set maskWithNans true and masked
	 *                       pixels are NaN instead of 0.
	 * @return box profile
	 */
	public static Dataset[] boxMean(Dataset data, Dataset mask, RectangularROI rroi, boolean maskWithNans) {
		if(data == null) return null;
		int[] spt = rroi.getIntPoint();
		int[] len = rroi.getIntLengths();
		double ang = rroi.getAngle();
		boolean clip = rroi.isClippingCompensation();
		Dataset[] profiles = new Dataset[] { null, null };

		if (len[0] == 0)
			len[0] = 1;
		if (len[1] == 0)
			len[1] = 1;

		if (ang == 0.0) {
			
			final int xtart  = Math.max(0,  spt[1]);
			final int xend   = Math.min(spt[1] + len[1],  data.getShape()[0]);
			final int ystart = Math.max(0,  spt[0]);
			final int yend   = Math.min(spt[0] + len[0],  data.getShape()[1]);
			
			// We slice down data to reduce the work the masking and the integrate needs to do.
			// TODO Does this always work? This makes large images profile better...
			Dataset slicedData = null;
			try {
				slicedData = data.getSlice(new int[]{xtart,   ystart}, 
					                       new int[]{xend,    yend},
					                       new int[]{1,1});
			} catch (Exception ne) {
				// We cannot process the profiles for a region totally outside the image!
				return null;
			}
			
			final Dataset slicedMask = mask!=null
					                         ? mask.getSlice(new int[]{xtart, ystart}, 
					                                         new int[]{xend,  yend},
					                                         new int[]{1,1})
					                         : null;
			
			if (slicedMask != null && slicedData != null) {
				if (slicedData.isCompatibleWith(slicedMask)) {
					clip = true;
					if (!maskWithNans || !(slicedMask instanceof BooleanDataset)) {
					} else {
						// Masks values to NaN, also changes dtype to Float
						slicedData = nanalize(slicedData, (BooleanDataset)slicedMask);
					}
				}
			}
			if (slicedData==null){
				slicedData = data;
			}

			Integrate2D int2d = new Integrate2D(0, 0, Math.min(len[0], data.getShape()[1]), Math.min(len[1], data.getShape()[0]));

			List<? extends Dataset> dsets = int2d.value(slicedData);
			if (dsets == null) return null;

			profiles[0] = DatasetUtils.convertToDataset(slicedData.mean(0));
			
			profiles[1] = DatasetUtils.convertToDataset(slicedData.mean(1));

		} else {
			
			if (mask != null) {
				if (data.isCompatibleWith(mask)) {
					clip = true;
					// TODO both multiply and nanalize create copies of the whole data passed in
					if (!maskWithNans || !(mask instanceof BooleanDataset)) {
					} else {
						// Masks values to NaN, also changes dtype to Float
						data = nanalize(data, (BooleanDataset)mask);
					}
				}
			}

			MapToRotatedCartesianAndIntegrate rcmapint = new MapToRotatedCartesianAndIntegrate(spt[0], spt[1], len[0],
					len[1], ang, false);
			List<? extends Dataset> dsets = rcmapint.value(data);
			if (dsets == null)
				return null;

			profiles[0] = dsets.get(1).mean(0);
			profiles[1] = dsets.get(0).mean(1);

			if (clip) {
				clippingCompensate(data, mask, profiles, rcmapint);
			}
		}
		return profiles;
	}

	/**
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param rroi
	 * @return max in box profile
	 */
	public static Dataset[] maxInBox(Dataset data, Dataset mask, RectangularROI rroi) {
		return maxInBox(data, mask, rroi, false);
	}

	/**
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param rroi
	 * @param maskWithNans - normally masked pixels will use a multiply with 0 to mask. The plotting
	 *                       deals with NaNs however, in this case we can set maskWithNans true and masked
	 *                       pixels are NaN instead of 0.
	 * @return max in box profile
	 */
	public static Dataset[] maxInBox(Dataset data, Dataset mask, RectangularROI rroi, boolean maskWithNans) {

		int[] spt = rroi.getIntPoint();
		int[] len = rroi.getIntLengths();
		double ang = rroi.getAngle();
		boolean clip = rroi.isClippingCompensation();
		Dataset[] profiles = new Dataset[] { null, null };


		if (len[0] == 0)
			len[0] = 1;
		if (len[1] == 0)
			len[1] = 1;

		if (ang == 0.0) {
			
			final int xtart  = Math.max(0,  spt[1]);
			final int xend   = Math.min(spt[1] + len[1],  data.getShape()[0]);
			final int ystart = Math.max(0,  spt[0]);
			final int yend   = Math.min(spt[0] + len[0],  data.getShape()[1]);
			
			// We slice down data to reduce the work the masking and the integrate needs to do.
			// TODO Does this always work? This makes large images profile better...
			Dataset slicedData = null;
			try {
			    slicedData = data.getSlice(new int[]{xtart,   ystart}, 
					                       new int[]{xend,    yend},
					                       new int[]{1,1});
			} catch (Exception ne) {
				// We cannot process the profiles for a region totally outside the image!
				return null;
			}
			
			final Dataset slicedMask = mask!=null
					                         ? mask.getSlice(new int[]{xtart, ystart}, 
					                                         new int[]{xend,  yend},
					                                         new int[]{1,1})
					                         : null;
			
			
			if (slicedMask != null && slicedData != null) {
				if (slicedData.isCompatibleWith(slicedMask)) {
					clip = true;
					if (!maskWithNans || !(slicedMask instanceof BooleanDataset)) {
						slicedData = Maths.multiply(slicedData, slicedMask);
					} else {
						// Masks values to NaN, also changes dtype to Float
						slicedData = nanalize(slicedData, (BooleanDataset)slicedMask);
					}
				}
			}

			if (slicedData==null){
				slicedData = data;
			}

			
			List<Dataset> dsets = new ArrayList<Dataset>();
			dsets.add(DatasetUtils.convertToDataset(slicedData.max(1)));
			dsets.add(DatasetUtils.convertToDataset(slicedData.max(0)));

			profiles[0] = maskWithNans
					    ? processColumnNans(dsets.get(1), slicedData)
					    : dsets.get(1);
			
			profiles[1] = maskWithNans
					    ?  processRowNans(dsets.get(0), slicedData)
					    : dsets.get(0);

		} else {
			
			if (mask != null && data != null) {
				if (data.isCompatibleWith(mask)) {
					clip = true;
					// TODO both multiply and nanalize create copies of the whole data passed in
					if (!maskWithNans || !(mask instanceof BooleanDataset)) {
						data = Maths.multiply(data, mask);
					} else {
						// Masks values to NaN, also changes dtype to Float
						data = nanalize(data, (BooleanDataset)mask);
					}
				}
			}

			MapToRotatedCartesianAndIntegrate rcmapint = new MapToRotatedCartesianAndIntegrate(spt[0], spt[1], len[0],
					len[1], ang, false);
			List<? extends Dataset> dsets = rcmapint.maxValue(data);
			if (dsets == null)
				return null;

			profiles[0] = dsets.get(1);
			profiles[1] = dsets.get(0);

			if (clip) {
				clippingCompensate(data, mask, profiles, rcmapint);
			}
		}

		return profiles;
	}

	/**
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param rroi
	 * @param maskWithNans - normally masked pixels will use a multiply with 0 to mask. The plotting
	 *                       deals with NaNs however, in this case we can set maskWithNans true and masked
	 *                       pixels are NaN instead of 0.
	 * @param isVertical
	 * @return box line profiles
	 */
	public static Dataset[] boxLine(Dataset data, Dataset mask, RectangularROI rroi, boolean maskWithNans, boolean isVertical) {

		double[] startpt = rroi.getPoint();
		double[] endpt = rroi.getEndPoint();
		Dataset[] profiles = new Dataset[] { null, null };

		// get the left and right side line profile of the rectangle
		double[] righttoppt = { new Double(endpt[0]), new Double(startpt[1]) };
		double[] leftbottompt = { new Double(startpt[0]), new Double(endpt[1]) };
		LinearROI line1 = null, line2 = null;
		
		if (isVertical) {
			line1 = new LinearROI(startpt, leftbottompt);
			line2 = new LinearROI(righttoppt, endpt);
		} else {
			line1 = new LinearROI(startpt, righttoppt);
			line2 = new LinearROI(leftbottompt, endpt);
		}

		Dataset[] lineProfiles = ROIProfile.line(data, mask, line1, 1d, maskWithNans);
		profiles[0] = lineProfiles != null ? lineProfiles[0] : null;
		if(profiles[0] == null) return null;
		lineProfiles = ROIProfile.line(data, mask, line2, 1d, maskWithNans);
		profiles[1] = lineProfiles != null ? lineProfiles[0] : null;
		if(profiles[1] == null) return null;
		return profiles;
	}

	/**
	 * This method will nanalize any data which the mask has set to false. NOTE
	 * the data and the mask must be precisely the same in size and value order.
	 * 
	 * No compatibility test is done for speed reasons, instead a failure will occur
	 * during the nanalise operation.
	 * 
	 * It always returns a FloatDataset for speed and size reasons, this being the
	 * smallest Dtype which allows NaNs.
	 * 
	 * 
	 * @param data
	 * @param mask
	 * @return clone of dataset with NaNs at the appropriate place.
	 */
	public static FloatDataset nanalize(Dataset data, BooleanDataset mask) {
		
		FloatDataset nanalized = new FloatDataset(data.getShape());
		float[]      buffer    = nanalized.getData();
		IndexIterator it        = data.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			buffer[i] = mask.getElementBooleanAbs(i) // assumes mask is not a view
					  ? (float) data.getElementDoubleAbs(it.index) // NOTE: Do not round, just lose precision.
					  : Float.NaN;
		}
		return nanalized;
	}


	private static Dataset processColumnNans(Dataset cols, Dataset data) {
		
		
        MAIN_LOOP: for (int i = 0; i < cols.getSize(); i++) {
			for (int j = 0, jmax = data.getShape()[0]; j < jmax; j++) {
				if (!Float.isNaN(data.getFloat(j, i))) continue MAIN_LOOP; 
			}
			cols.set(Float.NaN, i);
		}
	    return cols;
	}
	
	private static Dataset processRowNans(Dataset rows, Dataset data) {
		
		
        MAIN_LOOP: for (int i = 0; i < rows.getSize(); i++) {
			for (int j = 0, jmax = data.getShape()[1]; j < jmax; j++) {
				if (!Float.isNaN(data.getFloat(i, j))) continue MAIN_LOOP; 
			}
			rows.set(Float.NaN, i);
		}
	    return rows;
	}



	/**
	 * @param data
	 * @param sroi
	 * @return box profile
	 */
	public static Dataset[] sector(Dataset data, SectorROI sroi) {
		return sector(data, null, sroi, null);
	}

	/**
	 * @param data
	 * @param sroi
	 * @return box profile
	 */
	public static Dataset[] sector(Dataset data, Dataset mask, SectorROI sroi) {
		return sector(data, mask, sroi, true, true, false, null, null, false);
	}

	/**
	 * @param data
	 * @param sroi
	 * @return box profile
	 */
	public static Dataset[] sector(Dataset data, Dataset mask, SectorROI sroi, QSpace qSpace) {
		return sector(data, mask, sroi, true, true, false, qSpace, null, false);
	}

	/**
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param sroi
	 * @param useInterpolateFJ
	 *            uses interpolation and fork/join which might make things faster
	 * @return sector profile
	 */
	public static Dataset[] sector(Dataset data, Dataset mask, SectorROI sroi, boolean doRadial, boolean doAzimuthal, boolean useInterpolateFJ) {
		return sector(data, mask, sroi, doRadial, doAzimuthal, useInterpolateFJ, null, null, false);
	}
	/**
	 * 
	 * @param data
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param sroi
	 * 
	 * @param doRadial
	 * @param doAzimuthal
	 * @param useInterpolateFJ
	 *            uses interpolation and fork/join which might make things faster
	 * @param qSpace
	 * @param axisType
	 * @param doErrors
	 * @return 
	 *     the profile
	 */
	public static Dataset[] sector(Dataset data, Dataset mask, SectorROI sroi, boolean doRadial, boolean doAzimuthal, boolean useInterpolateFJ, QSpace qSpace, XAxis axisType, boolean doErrors) {
		final double[] spt = sroi.getPointRef();
		final double[] rad = sroi.getRadii();
		final double[] ang = sroi.getAngles();
		int symmetry = sroi.getSymmetry();
		boolean clip = sroi.isClippingCompensation();
		boolean aver = sroi.isAverageArea();
		double dpp = sroi.getDpp();

		Dataset[] profiles = new Dataset[8];
		Dataset[] errors = new Dataset[8];

		if (Math.abs(rad[0] - rad[1]) < 1) {
			return null;
		}

		if (symmetry == SectorROI.FULL) {
			MapToPolarAndIntegrate pmapfint = new MapToPolarAndIntegrate(spt[0], spt[1], rad[0], ang[0], rad[1], ang[0]
					+ 2 * Math.PI, dpp, false);
			if (mask != null  && mask.isCompatibleWith(data)) {
				pmapfint.setMask(mask);
			}
			pmapfint.setClip(clip);
			pmapfint.setInterpolate(!useInterpolateFJ);
			pmapfint.setDoRadial(doRadial);
			pmapfint.setDoAzimuthal(doAzimuthal);
			pmapfint.setQSpace(qSpace, axisType);
			pmapfint.setDoErrors(doErrors);
			List<? extends Dataset> dsetsf = pmapfint.value(data);
			if (dsetsf == null) {
				return null;
			}
			
			if (aver) {
				final SectorROI areaSector = sroi.copy();
				areaSector.setAverageArea(false);
				Dataset[] areas = sector(mask != null ? mask : DatasetFactory.ones(data), null,
						areaSector, doRadial, doAzimuthal, useInterpolateFJ, qSpace, axisType, false);
				profiles[0] = Maths.dividez(dsetsf.get(1), areas[0]);
				profiles[1] = Maths.dividez(dsetsf.get(0), areas[1]);
				if (doErrors) {
					errors[0] = Maths.dividez(dsetsf.get(1).getErrorBuffer(), areas[0].ipower(2));
					errors[1] = Maths.dividez(dsetsf.get(0).getErrorBuffer(), areas[1].ipower(2));
					profiles[0].setErrorBuffer(errors[0]);
					profiles[1].setErrorBuffer(errors[1]);
				}
			} else {
				profiles[0] = dsetsf.get(1);
				profiles[1] = dsetsf.get(0);
			}
			if (dsetsf.size() >= 6) {
				profiles[4] = dsetsf.get(5);
				profiles[5] = dsetsf.get(4);
			}
			return profiles;
		}

		if (Math.abs(ang[0] - ang[1]) < 1e-2) {
			return null;
		}

		MapToPolarAndIntegrate pmapint = new MapToPolarAndIntegrate(spt[0], spt[1], rad[0], ang[0], rad[1], ang[1],
				dpp, false);
		if (mask != null && data != null && mask.isCompatibleWith(data)) {
			pmapint.setMask(mask);
		}
		pmapint.setClip(clip);
		pmapint.setInterpolate(!useInterpolateFJ);
		pmapint.setDoRadial(doRadial);
		pmapint.setDoAzimuthal(doAzimuthal);
		pmapint.setQSpace(qSpace, axisType);
		pmapint.setDoErrors(doErrors);
		List<? extends Dataset> dsets = pmapint.value(data);
		if (dsets == null) {
			return null;
		}
		
		profiles[0] = dsets.get(1);
		profiles[1] = dsets.get(0);
		if (doErrors) { // TODO check if errors are datasets
			errors[0] = profiles[0].getErrorBuffer();
			errors[1] = profiles[1].getErrorBuffer();
		}
		if (dsets.size() >= 6) {
			profiles[4] = dsets.get(5);
			profiles[5] = dsets.get(4);
		}
		
		if (symmetry != SectorROI.NONE) {
			double[] nang = sroi.getSymmetryAngles();

			MapToPolarAndIntegrate pmapsint = new MapToPolarAndIntegrate(spt[0], spt[1], rad[0], nang[0], rad[1],
					nang[1], dpp, false);
			if (mask != null && data != null && mask.isCompatibleWith(data)) {
				pmapsint.setMask(mask);
			}
			pmapsint.setClip(clip);
			pmapsint.setInterpolate(!useInterpolateFJ);
			pmapsint.setDoRadial(doRadial);
			pmapsint.setDoAzimuthal(doAzimuthal);
			pmapsint.setQSpace(qSpace, axisType);
			pmapsint.setDoErrors(doErrors);
			List<? extends Dataset> dsetss = pmapsint.value(data);
			if (dsetss != null) {
				if (sroi.isCombineSymmetry()) {
					profiles[0] = Maths.add(profiles[0], dsetss.get(1));
					profiles[1] = Maths.add(profiles[1], dsetss.get(0));
					if (doErrors) {
						errors[0] = Maths.add(errors[0], dsetss.get(1).getErrorBuffer());
						errors[1] = Maths.add(errors[1], dsetss.get(0).getErrorBuffer());
					}
				} else {
					profiles[2] = dsetss.get(1);
					profiles[3] = dsetss.get(0);
					if (doErrors) { // TODO check if errors are datasets
						errors[2] = profiles[2].getErrorBuffer();
						errors[3] = profiles[3].getErrorBuffer();
					}
					if (dsetss.size() >= 6) {
						profiles[6] = dsetss.get(5);
						profiles[7] = dsetss.get(4);
					}
				}
			}
		}
		if (aver) {
			final SectorROI areaSector = sroi.copy();
			areaSector.setAverageArea(false);
			Dataset[] areas = sector(mask != null ? mask : DatasetFactory.ones(data), null,
					areaSector, doRadial, doAzimuthal, useInterpolateFJ, qSpace, axisType, false);
			for (int i = 0; i < 4; i++) {
				if (profiles[i] != null && areas[i] != null) {
					Maths.dividez(profiles[i], areas[i], profiles[i]);
					if (doErrors) {
						Maths.dividez(errors[i], areas[i].ipower(2), errors[i]);
						profiles[i].setErrorBuffer(errors[i]);
					}
				}
			}
		}
		return profiles;
	}
	
	/**
	 * Calculate area values of the selected sector region in pixels.
	 * 
	 * @param shape
	 *            image dimensions
	 * @param sroi
	 * @return sector profile
	 */
	public static Dataset[] area(int[] shape, int dtype, SectorROI sroi) {
		return area(shape, dtype, null, sroi);
	}
	
	/**
	 * Calculate area values of the selected sector region in pixels.
	 * 
	 * @param shape
	 *            image dimensions
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param sroi
	 * @return sector profile
	 */
	public static Dataset[] area(int[] shape, int dtype, Dataset mask, SectorROI sroi) {
		return area(shape, dtype, mask, sroi, true, true, false);
	}
	
	/**
	 * Calculate area values of the selected sector region in pixels.
	 * 
	 * @param shape
	 *            image dimensions
	 * @param mask
	 *            used for clipping compensation (can be null)
	 * @param sroi
	 * @return sector profile
	 */
	public static Dataset[] area(int[] shape, int dtype, Dataset mask, SectorROI sroi, boolean doRadial, boolean doAzimuthal, boolean fast) {
		//TODO: This method only works on pixel axis. It can't be use for normalising data on non-pixel axes (e.g q-space, d-spacing).   
		final SectorROI areaSector = sroi.copy();
		areaSector.setAverageArea(false);
		return sector(mask != null ? mask : DatasetFactory.ones(shape, dtype), null, areaSector, doRadial, doAzimuthal, fast);
	}
}
