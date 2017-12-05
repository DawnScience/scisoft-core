/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class SXRDGeometricCorrections {

	/*
	 * Mathematics to perform geometric corrections for SXRD.
	 * 
	 */

	public static ILazyDataset DiffData(String filepath, String choice) {

		IDataHolder dh1 = null;

		try {
			dh1 = LoaderFactory.getData(filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ILazyDataset ild = dh1.getLazyDataset(choice);

		return ild;
	}

	public static ILazyDataset getArb(String filepath, String choice) {
		ILazyDataset arb = null;
		arb = DiffData(filepath, choice);

		return arb;
	}

	public static ILazyDataset geth(String model) {
		return getArb(model, "h");
	}

	public static ILazyDataset getk(String model) {
		return getArb(model, "k");
	}

	public static ILazyDataset getl(String model) {
		return getArb(model, "l");
	}

	public static ILazyDataset getAlpha(String model) {
		return getArb(model, SXRDAngleAliasEnum.ALPHA.getAngleAlias());
	}

	public static ILazyDataset getDelta(String model) {
		String s = SXRDAngleAliasEnum.DELTA.getAngleAlias();

		return getArb(model, s);
	}

	public static ILazyDataset getGamma(String model) {
		return getArb(model, SXRDAngleAliasEnum.GAMMA.getAngleAlias());
	}

	public static ILazyDataset getOmega(String model) {
		return getArb(model, SXRDAngleAliasEnum.OMEGA.getAngleAlias());
	}

	public static ILazyDataset getChi(String model) {
		return getArb(model, SXRDAngleAliasEnum.CHI.getAngleAlias());
	}

	public static ILazyDataset getPhi(String model) {
		return getArb(model, SXRDAngleAliasEnum.PHI.getAngleAlias());
	}

	public static ILazyDataset getExposureTime(String model) {
		return getArb(model, "ExposureTime");
	}

	public static Dataset lorentz(String model) throws DatasetException {

		double pc = Math.PI / 180;

		ILazyDataset alpha3 = getAlpha(model);

		ILazyDataset delta3 = getDelta(model);

		ILazyDataset gamma3 = getGamma(model);

		Dataset a = Maths.multiply(alpha3, pc);
		Dataset d = Maths.multiply(delta3, pc);
		Dataset g = Maths.multiply(gamma3, pc);

		Dataset lorentzcor = Maths.multiply(Maths.cos(d), (Maths.sin(Maths.subtract(g, a))));

		return lorentzcor;

	}



	public static Dataset polarisation(String filepath, double IP, double OP) throws DatasetException {

		double pc = Math.PI / 180;

		ILazyDataset delta3 = getDelta(filepath);

		ILazyDataset gamma3 = getGamma(filepath);

		Dataset d = Maths.multiply(delta3, pc);
		Dataset g = Maths.multiply(gamma3, pc);

		Dataset inplane = Maths.subtract(1, Maths.power(Maths.sin(d), 2));
		Dataset outplane = Maths.subtract(1, (Maths.power((Maths.multiply(Maths.cos(d), Maths.sin(g))), 2)));

		Dataset IPdat = DatasetFactory.zeros(delta3.getShape(), Dataset.FLOAT64);
		IPdat = Maths.add(IPdat, IP);

		Dataset OPdat = DatasetFactory.zeros(delta3.getShape(), Dataset.FLOAT64);
		OPdat = Maths.add(OPdat, OP);

		Dataset polar = Maths.divide(1,
				(Maths.add((Maths.multiply(OPdat, outplane)), (Maths.multiply(IPdat, inplane)))));
		return polar;
	}

	public static double f_beam(double x, double z, double InPlaneSlits, double OutPlaneSlits, double BeamInPlane,
			double BeamOutPlane) {

		double w = (Math.exp(-2.77 * Math.pow(x, 2) / Math.pow(BeamInPlane, 2))
				* Math.exp(-2.77 * Math.pow(z, 2) / Math.pow(BeamOutPlane, 2)));

		if (Math.abs(x) > InPlaneSlits / 2) {
			w = 0;
		}
		if (Math.abs(z) > OutPlaneSlits / 2) {
			w = 0;
		} else {
			w = (Math.exp(((-2.77 * (Math.pow(x, 2))) / (Math.pow(BeamInPlane, 2))))
					* Math.exp(((-2.77 * (Math.pow(z, 2))) / (Math.pow(BeamOutPlane, 2)))));
		}

		return w;
	}

	public static double f_detector(double x, double DetectorSlits) {
		double e = 1;
		if (Math.abs(x) > DetectorSlits / 2)
			;
		e = 0;
		return e;
	}

	public static double f_onsample(double x, double y, double sampleSize) {
		double q = 1;
		if ((Math.pow(x, 2) + (Math.pow(y, 2))) > (0.25 * Math.pow(sampleSize, 2))) {
			q = 0;
		} else {
			q = 1;
		}

		return q;
	}

	public static Dataset areacor(String model, boolean beamCor, boolean specular, double sampleSize,
			double outPlaneSlits, double inPlaneSlits, double beamInPlane, double beamOutPlane, double detectorSlits){

		double pc = Math.PI / 180;

		ILazyDataset alpha3 = getAlpha(model);

		ILazyDataset delta3 = getDelta(model);

		ILazyDataset gamma3 = getGamma(model);

		Dataset a = Maths.multiply(alpha3, pc);
		Dataset d = Maths.multiply(delta3, pc);
		Dataset g = Maths.multiply(gamma3, pc);

		Dataset area_cor = DatasetFactory.zeros(delta3.getShape(), Dataset.FLOAT64);
		Dataset ylimitdat = DatasetFactory.zeros(delta3.getShape(), Dataset.FLOAT64);

		if (beamCor) {
			if (specular) {
				Dataset y_sum = DatasetFactory.createFromObject(0);
				Dataset betain = a;
				double ylimit = 10;

				ylimitdat = Maths.add(ylimitdat, ylimit);

				for (int i = 0; i <= delta3.getShape()[0]; i++) {

					if (sampleSize > 0.01) {
						ylimitdat = Maths.subtract(ylimitdat, ylimit);
						ylimitdat = Maths.add(ylimitdat, sampleSize * 0.6);
					}

					else if (Math.abs((2 * (outPlaneSlits)) / Math.sin(0.001 + betain.getDouble(i))) < ylimit) {
						ylimitdat.set(Math.abs(2 * outPlaneSlits / Math.sin(betain.getDouble(i) + 0.001)), i);
					}
					if (1.1 * outPlaneSlits / (2 * Math.sin(betain.getDouble(i) + 0.001)) < ylimit) {
						ylimitdat.set(1.1 * outPlaneSlits / (2 * Math.sin(betain.getDouble(i))), i);
					}

					double ystep = (ylimit / 50);
					Dataset ystep1 = DatasetFactory.createFromObject(ystep);

					for (double y = (-1 * ylimitdat.getDouble(i)); y <= (ylimitdat.getDouble(i) + ystep); y += ystep) {
						Dataset c = Maths.multiply(f_onsample(0, y, sampleSize),
								f_beam(0, y * Math.sin(betain.getDouble(i)), inPlaneSlits, outPlaneSlits, beamInPlane,
										beamOutPlane));
						y_sum = Maths.add(y_sum, c);
					}
					area_cor.set((Maths.divide(1, Maths.multiply(y_sum, ystep)).getObject(0)), i);
				}
			}

			else {
				Dataset betain = a;
				Dataset c1 = Maths.sin(betain);
				Dataset c2 = Maths.cos(d);
				Dataset c3 = Maths.sin(d);
				double xlimit = 0.1;
				double ylimit = 10;

				ylimitdat = Maths.add(ylimitdat, ylimit);

				if (inPlaneSlits > 0.01) {
					xlimit = inPlaneSlits / 2 + 0.01;
				} else {
					xlimit = 0.1;
				}

				for (int i = 0; i < delta3.getShape()[0]; i++) {

					if (inPlaneSlits > 0.01) {
						xlimit = 0.01 + inPlaneSlits / 2;
					}

					double xstep = xlimit / 50;

					if (sampleSize > 0.01) {
						ylimitdat = Maths.subtract(ylimitdat, ylimit);
						ylimitdat = Maths.add(ylimitdat, sampleSize * 0.6);
					}

					if (Math.abs(2 * outPlaneSlits / Math.sin(0.001 + betain.getDouble(i))) < ylimit) {
						ylimitdat.set(Math.abs(2 * outPlaneSlits / Math.sin(betain.getDouble(i) + 0.001)), i);
					}

					if (1.1 * outPlaneSlits / (2 * Math.sin(0.001 + betain.getDouble(i))) < ylimit) {
						ylimitdat.set(1.1 * outPlaneSlits / (2 * Math.sin(betain.getDouble(i))), i);
					}

					double ystep = ylimit / 50;

					double com = 0;
					double x = (-1 * xlimit);
					double y = (-1 * ylimit);
					double area_sum = 0;

					for (x = (-1 * xlimit); x <= (xlimit + 0.01 + xstep); x += xstep) {
						for (y = -1 * ylimit; y <= ylimit + 0.01 + ystep; y += ystep) {
							double fb = f_beam(x, y * c1.getDouble(i), inPlaneSlits, outPlaneSlits, beamInPlane,
									beamOutPlane);
							double fd = f_detector(x * c2.getDouble(i) - y * c3.getDouble(i), detectorSlits);

							double fo = f_onsample(x, y, sampleSize);

							if (fb != 0 && fo != 0 && fd != 0) {

								if (fb != 0) {
									System.out.println("fb :  " + fb + "  x = " + x + "  y = " + y);
								} else if (fo != 0) {
									System.out.println("fo :  " + fo + "  x = " + x + "  y = " + y);
								} else if (fd != 0) {
									System.out.println("fd :  " + fd + "  x = " + x + "  y = " + y);
								}

							}

							if (fd != 0) {
								System.out.println("fd :  " + fd + "  x = " + x + "  y = " + y);
							}
							area_sum = f_beam(x, y * c1.getDouble(i), inPlaneSlits, outPlaneSlits, beamInPlane,
									beamOutPlane) * f_detector(x * c2.getDouble(i) - y * c3.getDouble(i), detectorSlits)
									* f_onsample(x, y, sampleSize);
							com = com + area_sum;
						}
					}

//					area_sum = com;
					double bs_eff = 0;
					com = 0;

					for (x = -1 * xlimit; x <= xlimit + xstep / 10; x += xstep / 10) {
						bs_eff = f_beam(x, 0, inPlaneSlits, outPlaneSlits, beamInPlane, beamOutPlane);
						com = com + bs_eff;
					}
					
					area_sum = com;
					 
					bs_eff = com;
					bs_eff = bs_eff * (xstep / 10);
					area_cor.set((bs_eff) / (area_sum * xstep * ystep), i);

				}
			}
		}

		else {

			if (specular = false) {
				Dataset sinbetaout = Maths.multiply(Maths.cos(d), Maths.sin(Maths.subtract(g, a)));
				Dataset betaout = Maths.multiply(pc, Maths.arcsin(sinbetaout));
				Dataset cosbetaout = Maths.cos(betaout);
				area_cor = Maths.divide(Maths.sin(d), cosbetaout);
			}

			else {
				area_cor = Maths.sin(a);

			}
		}

		return area_cor;

		//
	}
}