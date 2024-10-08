/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;

public class ReflectivityFluxCorrectionsForDialog {

	public static double reflectivityFluxCorrectionsDouble(Double k, boolean useNegativeQ, String... filepath) {

		Dataset m = DatasetFactory.zeros(new int[] { 1 });

		m.set(k, 0);

		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(filepath);

		for (int i = 0; i < fluxData[0].getSize(); i++) {

			double probe1 = fluxData[0].getDouble(i);

			for (int j = 0; j < fluxData[0].getSize(); j++) {

				double probe2 = fluxData[0].getDouble(j);

				if (probe1 == probe2 && i != j) {

					fluxData[0].set(probe2 * 1.0000001, j);
				}
			}
		}

		if (useNegativeQ) {
			fluxData[1] = Maths.multiply(-1, fluxData[1]);
		}

		Dataset flux = DatasetFactory.createFromObject(0);
		float fluxLong = 0;

		try {
			boolean needed = true;

			for (int h = 0; h < fluxData[1].getSize(); h++) {
				if (k == (double) fluxData[1].getObject(h)) {
					fluxLong = (float) fluxData[0].getDouble(h);
					needed = false;
				}
			}

			if (needed) {
				flux = (Dataset) Interpolation1D.splineInterpolation(fluxData[1], fluxData[0], m);
				fluxLong = (float) flux.getDouble(0);
			}
		} catch (Exception f) {
			System.out.println(f.getMessage());
		}

		if (fluxLong == 0) {
			System.out.println("££££££££££  fluxLong is zero  £$£$£$£");
		}

		float output = 1 / fluxLong;

		return (double) output;
	}

	public static Dataset fluxCorrectionDataset(Dataset xNew, boolean useNegativeQ, String... filepath) {

		Dataset[] fluxData = RecoverNormalisationFluxBatchForDialog.normalisationFlux(filepath);

		for (int i = 0; i < fluxData[0].getSize(); i++) {

			double probe1 = fluxData[0].getDouble(i);

			for (int j = 0; j < fluxData[0].getSize(); j++) {

				double probe2 = fluxData[0].getDouble(j);

				if (probe1 == probe2 && i != j) {

					fluxData[0].set(probe2 * 1.0000001, j);
				}
			}
		}

		if (useNegativeQ) {
			fluxData[1] = Maths.multiply(-1, fluxData[1]);
		}

		Dataset flux = DatasetFactory.createFromObject(0);

		try {

			flux = (Dataset) Interpolation1D.splineInterpolation(fluxData[1], fluxData[0], xNew);

		} catch (Exception f) {
			System.out.println(f.getMessage());
		}

		
		for(int i =0 ; i< flux.getSize(); i++) {
			
			double l = 1 / flux.getDouble(i);
			flux.set(l, i);;
		}
		
		return flux;
	}

}
// test
