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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Point2i;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.apache.commons.math3.util.MathUtils;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

/**
 * Map and integrate a 2D dataset from Cartesian to Polar coordinates and return that remapped dataset
 * and an unclipped unit version (for clipping compensation) 
 * 
 * Cartesian coordinate system is x from left to right and y from top to bottom on the display
 * so corresponding polar coordinate is radius from centre and azimuthal angle clockwise from positive x axis
 */
public class MapToPolarAndIntegrate implements DatasetToDatasetFunction {
	private double cx, cy;
	private double srad, sphi, erad, ephi;
	private double dpp;
	private boolean clip = true;
	private boolean interpolate = true;  // Default: use bilinear interpolation algorithm
	private boolean doRadial = true;     // Default: calculate radial profile
	private boolean doAzimuthal = true;  // Default: calculate azimuthal profile
	private boolean doErrors = false;    // Default: calculate error estimates
	private boolean aver = true;         // Default: calculate averaged intensity profiles
	
	private XAxis axisType;
	private AbstractDataset mask;
	
	private QSpace qSpace;

	public void setQSpace(QSpace qSpace, XAxis axisType) {
		this.qSpace = (qSpace == null) ? null : qSpace;
		this.axisType = (qSpace == null || axisType == null) ? XAxis.PIXEL : axisType;
	}

	/**
	 * Set detector mask used sector profile calculations
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
	 * Select between simple integration algorithm and the one using bilinear interpolation
	 * 
	 * @param interpolate
	 * 			if true use bilinear interpolation algorithm
	 */
	public void setInterpolate(boolean interpolate) {
		this.interpolate = interpolate;
	}

	/**
	 * Set flag controlling radial profile calculation
	 * 
	 * @param doRadial
	 * 			if true calculate radial profile
	 */
	public void setDoRadial(boolean doRadial) {
		this.doRadial = doRadial;
	}

	/**
	 * Set flag controlling azimuthal profile calculation
	 * 
	 * @param doAzimuthal
	 * 			if true calculate azimuthal profile
	 */
	public void setDoAzimuthal(boolean doAzimuthal) {
		this.doAzimuthal = doAzimuthal;
	}

	/**
	 * Set flag controlling error estimate calculation
	 * 
	 * @param doErrors
	 * 			if true calculate error estimates
	 */
	public void setDoErrors(boolean doErrors) {
		this.doErrors = doErrors;
	}

	/**
	 * Set flag controlling intensity profile averaging
	 * 
	 * @param aver
	 * 			if true calculate averaged profile
	 * 			if false calculate total intensity profile
	 */
	public void setAverage(boolean aver) {
		this.aver = aver;
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
		if (qSpace != null) {
			if (axisType != null && axisType != XAxis.PIXEL) {
				return simple_qvalue(datasets);
			}
		}
		if (interpolate) {
			return interpolate_value(datasets);
		}
		return simple_value(datasets);
		
	}
	
	/**
	 * Wrapper call that selects the appropriate area calculation algorithm
	 *
	 * @param shape
	 *            image dimensions
	 * @return 2 1D datasets for area integral over radius and over azimuth (for given input and a uniform input)
	 */
	public List<AbstractDataset> area(int[] shape) {
		if (interpolate) {
			return interpolate_area(shape, AbstractDataset.FLOAT32);
		}
		return simple_area(shape);
		
	}
	
	/**
	 * This method implements mapping and integration of a Cartesian grid sampled data (pixels) to polar grid
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return 4 1D datasets for integral over radius, integral over azimuth (for given input and a uniform input)
	 */
	private List<AbstractDataset> interpolate_value(IDataset... datasets) {
		if (datasets.length == 0) {
			return null;
		}
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2) {
				throw new IllegalArgumentException("operating on 2d arrays only");
			}
			
			final double dr = 1.0/dpp;
			
			IDataset errIds = null; 
			if (doErrors && (ids instanceof AbstractDataset)) {
				Serializable errorBuffer = ((AbstractDataset) ids).getErrorBuffer();
				if (errorBuffer instanceof DoubleDataset) {
					errIds = (DoubleDataset) errorBuffer;
				}
			}
			
			//Find maximal radius on the detector
			//int[] shape = ids.getShape();
			//double ymax = Math.max(cy, shape[1] - cy);
			//double xmax = Math.max(cx, shape[0] - cx);
			//erad = Math.min(Math.sqrt(ymax*ymax + xmax*xmax), erad);
			// work out azimuthal resolution as roughly equal to pixel at outer radius
			final int nr = Math.max(1, (int) Math.ceil((erad - srad) / dr));
			final int np = Math.max(1, (int) Math.ceil((ephi - sphi) * erad / dr));
			final double dphi = (ephi - sphi) / np;
			final double rdphi = dphi * erad;
			
			final int dtype = AbstractDataset.getBestFloatDType(ids.elementClass());
			AbstractDataset sump = AbstractDataset.zeros(new int[] { nr }, dtype);
			AbstractDataset sumr = AbstractDataset.zeros(new int[] { np }, dtype);
			AbstractDataset errsump = AbstractDataset.zeros(new int[] { nr }, AbstractDataset.FLOAT64);
			AbstractDataset errsumr = AbstractDataset.zeros(new int[] { np }, AbstractDataset.FLOAT64);
			AbstractDataset usump = AbstractDataset.zeros(sump);
			AbstractDataset usumr = AbstractDataset.zeros(sumr);
			
			//TODO: This list can run out of memory for large sectors!
			List<Map<Point2i, Double>> pvarmap = new ArrayList<Map<Point2i,Double>>(np);
			for (int idx = 0; idx < np; idx++) {
				pvarmap.add(new HashMap<Point2i, Double>());
			}
			
			double csum;			
			
			for (int r = 0; r < nr; r++) {
				final double rad = srad + r*dr;
				final int tnp = Math.max(1, (int) Math.ceil((ephi - sphi) * rad / rdphi));
				final double tdphi = (rad > 0 ? rdphi / rad : dphi);
				
				csum = 0.0;
				Map<Point2i, Double> cvarmap = new HashMap<Point2i, Double>();

				double msk = 1.0;
				double tusump = 0;
				
				final double prj = (double)(np)/tnp;
				int qmin = 0;
				int qmax = 0;
				for (int p = 0; p < tnp; p++) {
					final double phi = sphi + p * tdphi;
					
					//Project current value on the corresponding range in azimuthal profile
					if (doAzimuthal) {
						qmin = (int)(p*prj);
						qmax = (int)((p+1)*prj);
					}
					
					final double x = cx + rad * Math.cos(phi);
					if (x < 0. || x > (ids.getShape()[1] + 1.)) {
						if (!clip && aver) {
							if (doRadial) {
								tusump += rad*dr*tdphi;
							}
							if (doAzimuthal) {
								for (int q = qmin; q < qmax; q++) {
									usumr.set(rad*dr*dphi + usumr.getDouble(q), q);
								}
							}
						}
						continue;
					}
					
					final double y = cy + rad * Math.sin(phi);
					if (y < 0. || y > (ids.getShape()[0] + 1.)) {
						if (!clip && aver) {
							if (doRadial) {
								tusump += rad*dr*tdphi;
							}
							if (doAzimuthal) {
								for (int q = qmin; q < qmax; q++) {
									usumr.set(rad*dr*dphi + usumr.getDouble(q), q);
								}
							}
						}
						continue;
					}
					if (mask != null && aver) {
						msk = Maths.getBilinear(mask, y ,x);
					}
					final double v = rad * dr * tdphi * Maths.getBilinear(ids, mask, y,	x);
					
					Map<Point2i, Double> varmap = null;
					if (errIds != null) {
						varmap = getBilinearWeights(errIds, mask, y,	x);
					}
					if (doRadial) {
						csum += v;
						if (varmap != null) {
							for (Point2i pt : varmap.keySet()) {
								cvarmap.put(pt, (cvarmap.containsKey(pt) ? cvarmap.get(pt) : 0.0) + rad * dr * tdphi * varmap.get(pt));
							}
						}
						if (aver) {
							tusump += rad * dr * tdphi * msk;
						}
					}
					if (doAzimuthal) {
						for (int q = qmin; q < qmax; q++) {
							sumr.set(v / prj + sumr.getDouble(q), q);
							if (varmap != null) {
								Map<Point2i, Double> tmpmap = pvarmap.get(q);
								for (Point2i pt : varmap.keySet()) {
									double vl = rad * dr * dphi * varmap.get(pt) / prj;
									tmpmap.put(pt, (tmpmap.containsKey(pt) ? tmpmap.get(pt) : 0.0) + vl);
								}
							}
							if (aver) {
								usumr.set(rad * dr * dphi * msk + usumr.getDouble(q), q);
							}
						}
					}
				}
				
				if (doRadial) {
					sump.set(csum, r);
					if (errIds != null) {
						double cvarres = 0.0;
						for (Entry<Point2i, Double> tmp : cvarmap.entrySet()) {
							int i0 = tmp.getKey().x;
							int i1 = tmp.getKey().y;
							double vl = tmp.getValue();
							// No need to check here if pixel is masked as they aren't included into the map
							cvarres += vl * vl * errIds.getDouble(i0, i1);
						}
						errsump.set(cvarres, r);
					}
					if(aver) {
						usump.set(tusump, r);
					}
				}
			}

			if (doAzimuthal && errIds != null) {
				for (int q = 0; q < np; q++) {
					Map<Point2i, Double> tmpmap = pvarmap.get(q);
					double cvarres = 0.0;
					for (Entry<Point2i, Double> tmp : tmpmap.entrySet()) {
						int i0 = tmp.getKey().x;
						int i1 = tmp.getKey().y;
						double vl = tmp.getValue();
						// No need to check here if pixel is masked as they aren't included into the map
						cvarres += vl * vl * errIds.getDouble(i0, i1);
					}
					errsumr.set(cvarres, q);
				}
			}
			
			if (errIds != null) {
				sumr.setErrorBuffer(errsumr);
				sump.setErrorBuffer(errsump);
			}
			result.add(sumr);
			result.add(sump);
			result.add(usumr);
			result.add(usump);
		}
		return result;
	}
	
	/**
	 * Map of interpolation coefficients from 2D dataset with mask
	 * @param d input dataset
	 * @param m mask dataset
	 * @param x0 coordinate
	 * @param x1 coordinate
	 * @return bilinear interpolation
	 */
	private Map<Point2i, Double> getBilinearWeights(final IDataset d, final IDataset m, final double x0, final double x1) {
		Map<Point2i, Double> res = new HashMap<Point2i, Double>();
		
		final int[] s = d.getShape();
		if (s.length != 2) {
			throw new IllegalArgumentException("Only 2d datasets allowed");
		}
		
		final int i0 = (int) Math.floor(x0);
		final int i1 = (int) Math.floor(x1);
		
		if (i0 < -1 || i0 >= s[0] || i1 < -1 || i1 >= s[1]) {
			return res;
		}
		
		for (int i : new int[] {i0, i0 + 1}) {
			for (int j : new int[] {i1, i1 + 1}) {
				if (i < 0 || i > (s[0] - 1) || j < 0 || j > (s[1] - 1)) {
					continue;
				}
				if (m == null || m.getBoolean(i, j)) {
					final double u0 = 1.0 - Math.abs(i - x0);
					final double u1 = 1.0 - Math.abs(j - x1);
					final Point2i pt = new Point2i(i, j);
					res.put(pt, u0*u1);
				}
			}
		}		
		return res;
	}
		
	private double pixelToValue(double x, double y) {
		
    	switch (axisType) {
    	case RESOLUTION:
       		Vector3d vect= qSpace.qFromPixelPosition(x, y);
       		return (2*Math.PI)/vect.length();
    	case ANGLE:
       		vect= qSpace.qFromPixelPosition(x, y);
       		return Math.toDegrees(qSpace.scatteringAngle(vect));
    	case Q:
        	vect= qSpace.qFromPixelPosition(x, y);
        	return vect.length();
    	default:
       		Vector2d vect2 = new Vector2d(new double[] {x - cx, y -cy});
    		return vect2.length();

    	}
    	
	}
	
	/**
	 * Calculate axes values in a sector area to find minimum and maximum for creating profile axes
	 * 
	 * @param nr	Number of sampling points in radial direction
	 * @param np	Number of sampling points in azimuthal direction
	 * @param dr	Step size in radial direction
	 * @param dphi	Step size in azimuthal direction
	 * @return axes Axes datasets
	 */	
	private AbstractDataset[] setupSelectedAxes(int nr, int np, double dr, double dphi) {
		
		double vmin = Double.MAX_VALUE;
		double vmax = -Double.MAX_VALUE;
		
		for (int r = 0; r < nr; r++) {
			for (int p = 0; p < np; p++) {
				double rad = srad + r * dr;
				double phi = sphi + p * dphi;
				double x = cx + rad * Math.cos(phi);
				double y = cy + rad * Math.sin(phi);
				double val = pixelToValue(x, y);
				if (val < vmin) vmin = val;
				if (val > vmax) vmax = val;
			}
		}
		
		AbstractDataset rAxis = DatasetUtils.linSpace(vmin, vmax, nr, AbstractDataset.FLOAT32);
		AbstractDataset angAxis = DatasetUtils.linSpace(sphi, ephi, np, AbstractDataset.FLOAT32);
		return new AbstractDataset[] {rAxis, angAxis};
	}
	
	/**
	 * This method calculates normalisation area coefficients for mapping and integration of a Cartesian grid sampled data (pixels) to polar grid.
	 * This values are only dependent on sector geometry and mask dataset and can be reused in mapping different datasets.
	 * 
	 * @param shape
	 *            image dimensions
	 * @param dtype
	 *            results data type
	 * @return 2 1D datasets for integral normalisation over radius and over azimuth (for given input and a uniform input)
	 */
	private List<AbstractDataset> interpolate_area(int[] shape, int dtype) {

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

			final double dr = 1.0/dpp;
			
			//Find maximal radius on the detector
			//int[] shape = ids.getShape();
			//double ymax = Math.max(cy, shape[1] - cy);
			//double xmax = Math.max(cx, shape[0] - cx);
			//erad = Math.min(Math.sqrt(ymax*ymax + xmax*xmax), erad);
			// work out azimuthal resolution as roughly equal to pixel at outer radius
			final int nr = Math.max(1, (int) Math.ceil((erad - srad) / dr));
			final int np = Math.max(1, (int) Math.ceil((ephi - sphi) * erad / dr));
			final double dphi = (ephi - sphi) / np;
			final double rdphi = dphi * erad;
			
			AbstractDataset usump = AbstractDataset.zeros(new int[] { nr }, dtype);
			AbstractDataset usumr = AbstractDataset.zeros(new int[] { np }, dtype);
			
			for (int r = 0; r < nr; r++) {
				final double rad = srad + r*dr;
				int tnp = Math.max(1, (int) Math.ceil((ephi - sphi) * rad / rdphi));
				final double tdphi = (rad > 0 ? rdphi / rad : dphi);

				double msk = 1.0;
				double tusump = 0;
				final double prj = (double)(np)/tnp;
				for (int p = 0; p < tnp; p++) {
					final double phi = sphi + p * tdphi;
					
					//Project current value on the corresponding range in azimuthal profile
					int qmin = (int)(p*prj);
					int qmax = (int)((p+1)*prj);
					
					final double x = cx + rad * Math.cos(phi);
					if (x < 0. || x > (shape[1] + 1.)) {
						if (!clip) {
							if (doRadial) {
								tusump += rad*dr*tdphi;
							}
							if (doAzimuthal) {
								for (int q = qmin; q < qmax; q++) {
									usumr.set(rad*dr*dphi + usumr.getDouble(q), q);
								}
							}
						}
						continue;
					}
					final double y = cy + rad * Math.sin(phi);
					if (y < 0. || y > (shape[0] + 1.)) {
						if (!clip) {
							if (doRadial) {
								tusump += rad*dr*tdphi;
							}
							if (doAzimuthal) {
								for (int q = qmin; q < qmax; q++) {
									usumr.set(rad*dr*dphi + usumr.getDouble(q), q);
								}
							}
						}
						continue;
					}
					if (mask != null) {
						msk = Maths.getBilinear(mask, y ,x);
					}
					if (doRadial) {
						tusump += rad*dr*tdphi*msk;
					}
					if (doAzimuthal) {
						for (int q = qmin; q < qmax; q++) {
							usumr.set(rad*dr*dphi*msk + usumr.getDouble(q), q);
						}
					}
				}
				if (doRadial) {
					usump.set(tusump, r);
				}
			}

			result.add(usumr);
			result.add(usump);
		return result;
	}
	
	/**
	 * This method uses applies weighting factors to every sampled data point to calculate integration profiles
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return 4 1D datasets for integral over radius, integral over azimuth (for given input and a uniform input)
	 */
	private List<AbstractDataset> simple_value(IDataset... datasets) {
		if (datasets.length == 0) {
			return null;
		}
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2) {
				throw new IllegalArgumentException("operating on 2d arrays only");
			}
			int npts = (int) (erad - srad + 1);
			int apts = (int) (erad*(ephi - sphi) + 1);
			double dphi = (ephi - sphi) / apts;
			
			// Final intensity is 1D data
			float[] intensity = new float[npts];
			float[] azimuth = new float[apts];
			
			// For each final pixel, frequency will store the number of points participating to the sector integration at point i 
			float[] frequency = new float[npts];
			float[] afrequency = new float[apts];
			
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
							if (mask != null && !mask.getBoolean(new int[] {j,i})) {
									continue;
							}
							double val = ids.getDouble(new int[] {j,i});
							
							// Each point participating to the sector integration is weighted depending on
							// how far/close it is from the following point i+1
							double fFac = (k + 1) - ( R - srad);
							
							if (doRadial) {
								// Evaluates the intensity and the frequency
								intensity[k] += fFac * val;
								if(aver) {
									frequency[k] += fFac;
								}
								if (k < (npts - 1)) {
									intensity[k + 1] += (1 - fFac) * val;
									if(aver) {
										frequency[k + 1] += (1 - fFac);
									}
								}
							}
							if (doAzimuthal) {
								double dk = 1. / R;
								int ak1 = Math.max(0, (int) ((Theta - dk / 2.0 - sphi) / dphi));
								int ak2 = Math.min(apts - 1, (int) ((Theta + dk / 2.0 - sphi) / dphi));
								for (int n = ak1; n <= ak2; n++) {
									fFac = ak2 - ak1 + 1.0;
									azimuth[n] += val / fFac;
									if(aver) {
										afrequency[n] += 1.0 / fFac;
									}
								}
							}
						}
					}
				}
			}
			
			result.add(new FloatDataset(azimuth, new int[] {apts})) ;
			result.add(new FloatDataset(intensity, new int[] {npts})) ;
			result.add(new FloatDataset(afrequency, new int[] {apts})) ;
			result.add(new FloatDataset(frequency, new int[] {npts})) ;
		}
		return result;
	}
	
	private List<AbstractDataset> simple_qvalue(IDataset... datasets) {
		if (datasets.length == 0 || qSpace == null) {
			return null;
		}
		
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2) {
				throw new IllegalArgumentException("operating on 2d arrays only");
			}
			double dr = 1.0/dpp;
			int npts =  (int) ((erad - srad + 1) * dpp);
			int apts = (int) (erad * (ephi - sphi) * dpp + 1);
			double dphi = (ephi - sphi) / apts;
			
			// Final intensity is 1D data
			float[] intensity = new float[npts];
			float[] azimuth = new float[apts];
			
			// For each final pixel, frequency will store the number of points participating to the sector integration at point i 
			float[] frequency = new float[npts];
			float[] afrequency = new float[apts];
			
			// Calculate bounding rectangle around the sector
			int nxstart = (int) Math.max(0, cx - erad);
			int nx = (int) Math.min(ids.getShape()[1], cx + erad);
			int nystart = (int) Math.max(0, cy - erad);
			int ny = (int) Math.min(ids.getShape()[0], cy + erad);
			
			AbstractDataset[] rAxis = setupSelectedAxes(npts, apts, dr, dphi);
			
			AbstractDataset radAxis = rAxis[0];
			AbstractDataset azAxis = rAxis[1];
			switch (axisType) {
			case RESOLUTION:
	    		radAxis.setName("d-spacing (\u00c5)");
				break;

			case ANGLE:
	    		radAxis.setName("2\u03b8 (\u00b0)");
				break;

			case Q:
	    		radAxis.setName("q (1/\u00c5)");
				break;

			default:
	    		radAxis.setName("Radius (pixel)");
				break;
			}
			azAxis.setName("Angle (\u00b0)");
			
			for (int j = nystart; j < ny; j++) {
				for (int i = nxstart; i < nx; i++) {

					double Theta = Math.atan2((j - cy) , (i - cx));
					Theta = MathUtils.normalizeAngle(Theta, sphi + Math.PI);

					if ((Theta >= sphi) && (Theta <= ephi )) {			
						double xR = i - cx;
						double yR = j - cy;

						double R = Math.sqrt(xR * xR + yR * yR);

						if ((R >= srad) && (R < erad)) {
							R = pixelToValue(i, j);
							int k = DatasetUtils.findIndexGreaterThan(radAxis, R) - 1;
							if (k < 0 || (k + 1) >= radAxis.getSize()) {
								continue;
							}
							if (mask != null && !mask.getBoolean(new int[] {j,i})) {
								continue;
							}
							
							double val = ids.getDouble(new int[] {j,i});
							
							// Each point participating to the sector integration is weighted depending on
							// how far/close it is from the following point i+1
							double r1 = radAxis.getDouble(k);
							double r2 = radAxis.getDouble(k + 1);
							double fFac = (r2 - R) / (r2 - r1);
							
							if (doRadial) {
								// Evaluates the intensity and the frequency
								intensity[k] += fFac * val;
								if(aver)
									frequency[k] += fFac;
								if (k < (npts - 1)) {
									intensity[k + 1] += (1 - fFac) * val;
									if(aver)
										frequency[k + 1] += (1 - fFac);
								}
							}
							if (doAzimuthal) {
								double dk = 1. / R;
								int ak1 = Math.max(0, (int) ((Theta - dk / 2.0 - sphi) / dphi));
								int ak2 = Math.min(apts - 1, (int) ((Theta + dk / 2.0 - sphi) / dphi));
								for (int n = ak1; n <= ak2; n++) {
									fFac = ak2 - ak1 + 1.0;
									azimuth[n] += val / fFac;
									if(aver)
										afrequency[n] += 1.0 / fFac;
								}
							}
						}
					}
				}
			}
			
			result.add(new FloatDataset(azimuth, new int[] {apts})) ;
			result.add(new FloatDataset(intensity, new int[] {npts})) ;
			result.add(new FloatDataset(afrequency, new int[] {apts})) ;
			result.add(new FloatDataset(frequency, new int[] {npts})) ;
			result.add(azAxis) ;
			result.add(radAxis) ;
		}
		return result;
	}
	
	/**
	 * This method calculates normalisation area coefficients for averaged integration profiles using simple weighting coefficients. 
	 * This values are only dependent on sector geometry and mask dataset and can be reused in mapping different datasets.
	 * 
	 * @param shape
	 *            image dimensions
	 * @return 2 1D datasets for integral normalisation over radius and over azimuth (for given input and a uniform input)
	 */
	private List<AbstractDataset> simple_area(int[] shape) {
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		if (shape.length != 2)
			throw new IllegalArgumentException("operating on 2d arrays only");

		int npts = (int) (erad - srad + 1);
		int apts = (int) (erad * (ephi - sphi) + 1);
		double dphi = (ephi - sphi) / apts;

		// For each final pixel, frequency will store the number of points participating to the sector integration at
		// point i
		float[] frequency = new float[npts];
		float[] afrequency = new float[apts];

		// Calculate bounding rectangle around the sector
		int nxstart = (int) Math.max(0, cx - erad);
		int nx = (int) Math.min(shape[1], cx + erad);
		int nystart = (int) Math.max(0, cy - erad);
		int ny = (int) Math.min(shape[0], cy + erad);

		for (int j = nystart; j < ny; j++) {
			for (int i = nxstart; i < nx; i++) {

				double Theta = Math.atan2((j - cy), (i - cx));
				Theta = MathUtils.normalizeAngle(Theta, sphi + Math.PI);

				if ((Theta >= sphi) && (Theta <= ephi)) {
					double xR = i - cx;
					double yR = j - cy;

					double R = Math.sqrt(xR * xR + yR * yR);

					if ((R >= srad) && (R < erad)) {
						int k = (int) (R - srad);
						if (mask != null && !mask.getBoolean(new int[] { j, i }))
							continue;

						// Each point participating to the sector integration is weighted depending on
						// how far/close it is from the following point i+1
						double fFac = (k + 1) - (R - srad);

						if (doRadial) {
							// Evaluates the intensity and the frequency
							frequency[k] += fFac;
							if (k < (npts - 1)) {
								frequency[k + 1] += (1 - fFac);
							}
						}
						if (doAzimuthal) {
							double dk = 1. / R;
							int ak1 = Math.max(0, (int) ((Theta - dk / 2.0 - sphi) / dphi));
							int ak2 = Math.min(apts - 1, (int) ((Theta + dk / 2.0 - sphi) / dphi));
							for (int n = ak1; n <= ak2; n++) {
								fFac = ak2 - ak1 + 1.0;
								afrequency[n] += 1.0 / fFac;
							}
						}
					}
				}
			}
		}

		result.add(new FloatDataset(afrequency, new int[] { apts }));
		result.add(new FloatDataset(frequency, new int[] { npts }));
		return result;
	}
}
