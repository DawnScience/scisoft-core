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
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

/**
 * This class is generate an array ([2]) datasets of the X-ray flux at given
 * theta values. These are used to correct X-ray reflectivity measurements
 * 
 * 
 */
public class RecoverNormalisationFluxBatchForDialog {

	public static Dataset[] normalisationFlux(String... filepath) {

		IDataset[] fluxes = new IDataset[filepath.length];
		IDataset[] thetas = new IDataset[filepath.length];

		ILazyDataset flux = null;
		ILazyDataset theta = null;
		Dataset[] fluxData = new Dataset[2];

		for (int i = 0; i < filepath.length; i++) {

			try {
				IDataHolder dh1 = LoaderFactory.getData(filepath[i]);

				flux = dh1.getLazyDataset(ReflectivityFluxParametersAliasEnum.FLUX.getFluxAlias());

				String whatsWrong = ReflectivityFluxParametersAliasEnum.FLUX_SCANNED_VARIABLE.getFluxAlias();
				
				theta = dh1.getLazyDataset(ReflectivityFluxParametersAliasEnum.FLUX_SCANNED_VARIABLE.getFluxAlias());

			}

			catch (Exception f) {
				System.out.println("No normalisation data available internally");

			}

			SliceND sliceF = new SliceND(flux.getShape());
			SliceND sliceT = new SliceND(theta.getShape());

			try {
				fluxes[i] = flux.getSlice(sliceF);
				thetas[i] = theta.getSlice(sliceT);
			} catch (DatasetException e) {
				System.out.println(e.getMessage());
			}

		}

		fluxData[0] = DatasetUtils.concatenate(fluxes, 0);
		fluxData[1] = DatasetUtils.concatenate(thetas, 0);

		DatasetUtils.sort(fluxData[1], fluxData[0]);

		return fluxData;

	}

}

// TEST