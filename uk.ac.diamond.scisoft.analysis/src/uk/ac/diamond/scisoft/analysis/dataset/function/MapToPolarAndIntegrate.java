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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.util.MathUtils;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Map and integrate a 2D dataset from Cartesian to Polar coordinates and return that remapped dataset
 * and an unclipped unit version (for clipping compensation) 
 * 
 * Cartesian coordinate system is x from left to right and y from top to bottom on the display
 * so corresponding polar coordinate is radius from centre and azimuthal angle clockwise from positive x axis
 */
public class MapToPolarAndIntegrate implements DatasetToDatasetFunction {
	double cx, cy;
	double srad, sphi, erad, ephi;
	double dpp;
	boolean clip = true;
	boolean interpolate = true;  // Default: use bilinear interpolation algorithm
	
	AbstractDataset mask;

	/**
	 * Set detector mask used sector profile calulations
	 *  
	 * @param mask
	 */
	public void setMask(AbstractDataset mask) {
		this.mask = mask;
	}
	
	/**
	 * Set clipping compensation flag
	 * 
	 * @param clip
	 */
	public void setClip(boolean clip) {
		this.clip = clip;
	}
	
	/**
	 * Select between simple integration algorithm and the one using bilinear intrpolation
	 * 
	 * @param interpolate
	 * 			if true use bilinear interpolation algorithm
	 */
	public void setInterpolate(boolean interpolate) {
		this.interpolate = interpolate;
	}

	/**
	 * Set up mapping of annular sector of 2D dataset
	 * 
	 * @param x
	 *            centre x
	 * @param y
	 *            centre y
	 * @param sr
	 *            start radius
	 * @param sp
	 *            start phi in degrees
	 * @param er
	 *            end radius
	 * @param ep
	 *            end phi in degrees
	 */
	public MapToPolarAndIntegrate(double x, double y, double sr, double sp, double er, double ep) {
		this(x, y, sr, sp, er, ep, 1.0, true);
	}

	/**
	 * @param x
	 * @param y
	 * @param sr
	 * @param sp
	 * @param er
	 * @param ep
	 * @param isDegrees
	 */
	public MapToPolarAndIntegrate(double x, double y, double sr, double sp, double er, double ep, double dpp, boolean isDegrees) {
		cx = x;
		cy = y;

		srad = sr;
		erad = er;
		
		this.dpp = dpp;

		if (isDegrees) {
			sphi = Math.toRadians(sp);
			ephi = Math.toRadians(ep);
		} else {
			sphi = sp;
			ephi = ep;
		}

		if (sphi > ephi) {
			double tphi = sphi;
			sphi = ephi;
			ephi = tphi;
		}
	}

	/**
	 * Wrapper call that selects the appropriate integration algorithm
	 *
	 * @param datasets
	 *            input 2D dataset
	 * @return 4 1D datasets for integral over radius, integral over azimuth (for given input and a uniform input)
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (interpolate)
			return interpolate_value(datasets);
		return simple_value(datasets);
		
	}
	
	/**
	 * This method implements mapping and integration of a Cartesian grid sampled data (pixels) to polar grid
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return 4 1D datasets for integral over radius, integral over azimuth (for given input and a uniform input)
	 */
	public List<AbstractDataset> interpolate_value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				throw new IllegalArgumentException("operating on 2d arrays only");

			double dr = 1.0/dpp;
			
			//Find maximal radius on the detector
			//int[] shape = ids.getShape();
			//double ymax = Math.max(cy, shape[1] - cy);
			//double xmax = Math.max(cx, shape[0] - cx);
			//erad = Math.min(Math.sqrt(ymax*ymax + xmax*xmax), erad);
			// work out azimuthal resolution as roughly equal to pixel at outer radius
			int nr = (int) Math.ceil((erad - srad) / dr);
			int np = (int) Math.ceil((ephi - sphi) * erad / dr);
			double dphi = (ephi - sphi) / np;
			final double rdphi = dphi * erad;
			if (nr == 0)
				nr = 1;
			if (np == 0)
				np = 1;

			final int dtype = AbstractDataset.getBestFloatDType(ids.elementClass());
			AbstractDataset sump = AbstractDataset.zeros(new int[] { nr }, dtype);
			AbstractDataset sumr = AbstractDataset.zeros(new int[] { np }, dtype);
			AbstractDataset usump = AbstractDataset.zeros(sump);
			AbstractDataset usumr = AbstractDataset.zeros(sumr);
			
			double rad, phi;
			double x, y;
			double csum;			
			
			for (int r = 0; r < nr; r++) {
				rad = srad + r*dr;
				int tnp = (int) Math.ceil((ephi - sphi) * rad / rdphi);
				double tdphi = rdphi / rad;
				// Check if rad == 0
				if (Double.isInfinite(tdphi) || Double.isNaN(tdphi))
					tdphi = dphi;
				if (tnp == 0)
					tnp = 1;
				csum = 0.0;

				double msk = 1.0;
				double tusump = 0;
				double prj = (double)(np)/tnp;
				for (int p = 0; p < tnp; p++) {
					phi = sphi + p * tdphi;
					
					//Project current value on the corresponding range in azimuthal profile
					int qmin = (int)(p*prj);
					int qmax = (int)((p+1)*prj);
					
					x = cx + rad * Math.cos(phi);
					if (x < 0. || x > (ids.getShape()[1] + 1.)) {
						if (!clip) {
							tusump += rad*dr*tdphi;
							for (int q = qmin; q < qmax; q++)
								usumr.set(rad*dr*dphi + usumr.getDouble(q), q);
						}
						continue;
					}
					y = cy + rad * Math.sin(phi);
					if (y < 0. || y > (ids.getShape()[0] + 1.)) {
						if (!clip) {
							tusump += rad*dr*tdphi;
							for (int q = qmin; q < qmax; q++)
								usumr.set(rad*dr*dphi + usumr.getDouble(q), q);
						}
						continue;
					}
					if (mask != null)
						msk = Maths.getBilinear(mask, y ,x);
					final double v = rdphi * dr * Maths.getBilinear(ids, mask, y, x);
					csum += v;
					tusump += rad*dr*tdphi*msk;
					
					for (int q = qmin; q < qmax; q++) {
						sumr.set(v / prj + sumr.getDouble(q), q);
						usumr.set(rad*dr*dphi*msk + usumr.getDouble(q), q);
					}
				}
				sump.set(csum, r);
				usump.set(tusump, r);
			}

			result.add(sumr);
			result.add(sump);
			result.add(usumr);
			result.add(usump);
		}
		return result;
	}

	/**
	 * This method uses applies weighting factors to every sampled data point to calculate integration profiles
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return 4 1D datasets for integral over radius, integral over azimuth (for given input and a uniform input)
	 */
	public List<AbstractDataset> simple_value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				throw new IllegalArgumentException("operating on 2d arrays only");
		
			int npts = (int) (erad - srad + 1);
			int apts = (int) (erad*(ephi - sphi) + 1);
			double dphi = (ephi - sphi) / apts;
			
			// Final intensity is 1D data
			double[] intensity = new double[npts];
			double[] azimuth = new double[apts];
			
			// For each final pixel, frequency will store the number of points participating to the sector integration at point i 
			double[] frequency = new double[npts];
			double[] afrequency = new double[apts];
			
			// Calculate bounding rectangle around the sector
			int nxstart = (int) Math.max(0, cx - erad);
			int nx = (int) Math.min(ids.getShape()[1], cx + erad);
			int nystart = (int) Math.max(0, cy - erad);
			int ny = (int) Math.min(ids.getShape()[0], cy + erad);
			
			for (int j = nystart; j < ny; j++) {
				for (int i = nxstart; i < nx; i++) {

					double Theta = Math.atan2((j - cy) , (i - cx));
					Theta = MathUtils.normalizeAngle(Theta, sphi + Math.PI);

					if ((Theta >= sphi) && (Theta <= ephi )) {			
						double xR = i - cx;
						double yR = j - cy;

						double R = Math.sqrt(xR * xR + yR * yR);

						if ((R >= srad) && (R < erad)) {
							int k = (int) (R - srad);
							if (mask != null && !mask.getBoolean(new int[] {j,i}))
									continue;
							// Each point participating to the sector integration is weighted depending on
							// how far/close it is from the following point i+1
							double fFac = (k + 1) - ( R - srad);
							
							// Evaluates the intensity and the frequency
							double val = ids.getDouble(new int[] {j,i});
							intensity[k] = intensity[k] + fFac * val;
							frequency[k] = frequency[k] + fFac;
							if (k < (npts - 1)) {
								intensity[k + 1] = intensity[k + 1] + (1 - fFac) * val;
								frequency[k + 1] = frequency[k + 1] + (1 - fFac);
							}
							double dk = 1./R;
							int ak1 = Math.max(0, (int) ((Theta - dk/2.0 - sphi)/dphi));
							int ak2 = Math.min(apts - 1, (int) ((Theta + dk/2.0 - sphi)/dphi));
							for (int n = ak1; n <= ak2; n++) {
								fFac = ak2 - ak1 + 1.0;
								azimuth[n] += val / fFac;
								afrequency[n] += 1.0 / fFac;
							}
						}
					}
				}
			}
			
			result.add(new DoubleDataset(azimuth, new int[] {apts})) ;
			result.add(new DoubleDataset(intensity, new int[] {npts})) ;
			result.add(new DoubleDataset(afrequency, new int[] {apts})) ;
			result.add(new DoubleDataset(frequency, new int[] {npts})) ;
		}
		return result;
	}
	
}
