/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.metadata.ARPESMetadataImpl;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;

/**
 * This class is a HDF5Loader with extra things associated by the nexus standard. Primarily if an ILazyDataset is
 * loaded, it will attempt to load the errors associated with the dataset.
 */
public class NexusHDF5Loader extends HDF5Loader {
	/**
	 * Attribute name for a NeXus class
	 */
	public static final String NX_CLASS = "NX_class";

	public static final String NX_AXES = "axes";
	public static final String NX_AXIS = "axis";
	public static final String NX_LABEL = "label";
	public static final String NX_PRIMARY = "primary";
	public static final String NX_SIGNAL = "signal";
	public static final String NX_DATA = "NXdata";
	public static final String NX_ERRORS = "errors";
	public static final String NX_ERRORS_SUFFIX = "_" + NX_ERRORS;
	public static final String NX_NAME = "long_name";
	public static final String NX_INDICES_SUFFIX = "_indices";
	public static final String SDS = "SDS";

	public static final String DATA = "data";
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {

		DataHolder dh = super.loadFile(mon);
		if (dh == null)
			return null;

		// TODO Add in unit metadata information
		// Augment data as required
		// get all data with signal attribute
		try {
			// Parse Metadata.
			List<String> metaNames = new ArrayList<String>(dh.getMetadata().getMetaNames());
			Collections.sort(metaNames);
			for (int dataPos = 0; dataPos < metaNames.size(); dataPos++) {
				String metaKey = metaNames.get(dataPos);
				if (metaKey.contains("@signal")) {
					// find the data
					String key = metaKey.replace("@signal", "");
					ILazyDataset data = dh.getLazyDataset(key);

					// reparse all metadata to see associated metadata
					ArrayList<String> additionalMetadata = new ArrayList<String>(0);
					String[] result = key.split("/");
					String parentKey = "";
					for (int i = 0; i < result.length - 1; i++) {
						if (result[i].length() > 0) {
							parentKey += "/" + result[i];
						}
					}
					
					// Check Forward through list for matching metadata
					for (int axisPos = dataPos+1; axisPos < metaNames.size(); axisPos++) {
						String repassKey = metaNames.get(axisPos);
						if (repassKey.startsWith(parentKey)) {
							additionalMetadata.add(repassKey);
						} else {
							// End of entries so we can exit the loop
							break;
						}
					}
					
					// Check Backwards through list for matching metadata
					for (int axisPos = dataPos-1; axisPos > 0; axisPos--) {
						String repassKey = metaNames.get(axisPos);
						if (repassKey.startsWith(parentKey)) {
							additionalMetadata.add(repassKey);
						} else {
							// End of entries so we can exit the loop
							break;
						}
					}

					// get existing axis metadata
					List<AxesMetadata> list = data.getMetadata(AxesMetadata.class);
					AxesMetadataImpl axesMetadata;
					if (list == null || list.size() == 0) {
						axesMetadata = new AxesMetadataImpl(data.getRank());
						data.addMetadata(axesMetadata);
					} else {
						axesMetadata = (AxesMetadataImpl) list.get(0);
					}

					// look through the additional metadata for axis information
					// TODO Should take @primary into account when adding axes.
					// Good test file which fails if this is not done right is:
					// /dls/i12/data/2014/cm4963-4/rawdata/41781.nxs
					
					int[] dShape = data.getShape();
					for (String goodKey : additionalMetadata) {
						if (goodKey.endsWith("@axis")) {
							String axisName = goodKey.replace("@axis", "");
							ILazyDataset axisData = dh.getLazyDataset(axisName);
							
							// This string is a comma separated list of numbers, normally one number
							// but occasionally, two.
							String axes = (String) dh.getMetadata().getMetaValue(goodKey);
							String[] laxes = axes.split(",");

							for (String axis : laxes) {

								int axisDim = Integer.parseInt(axis) - 1; // zero-based
								ILazyDataset axisDataset = axisData.clone();
								int[] aShape = axisData.getShape();
								if (aShape.length == 1) {
									int[] shape = new int[dShape.length];
									int aLength = aShape[0];
									Arrays.fill(shape, 1);
									if (dShape[axisDim] != aLength) { // sanity check
										if (dShape[dShape.length - 1 - axisDim] == aLength) { // Fortran order!
											axisDim = dShape.length - 1 - axisDim;
										} else {
											logger.warn("Axis attribute of {} does not match dimension {} of signal dataset", goodKey, axisDim);
											axisDim = -1;
											for (int i = 0; i < shape.length ; i++) {
												if (dShape[i] == aLength) {
													axisDim = i;
													break;
												}
											}
										}
										if (axisDim < 0) {
											logger.error("Axis attribute of {} does not match any dimension of signal dataset", goodKey);
											break;
										}
									}
									shape[axisDim] = aLength;
									axisDataset.setShape(shape);
									axesMetadata.addAxis(axisDim, checkDatasetShapeSlicable(axisDataset, dShape));
								} else {
									if (axisDataset.getRank() == data.getRank()){
										axesMetadata.addAxis(axisDim, axisDataset);
									} else {
										//TODO this might need to be generic'd up a bit... try-catch incase anything troublesome happens
										try {
											int[] shape = new int[dShape.length];
											Arrays.fill(shape, 1);

											int[] overlap = Arrays.copyOfRange(dShape, axisDim-aShape.length+1, axisDim+1);

											if (Arrays.equals(aShape, overlap)) {

												for (int i = axisDim-aShape.length+1; i < axisDim+1; i++) shape[i] = dShape[i];
												axisDataset.setShape(shape);
											}

											axesMetadata.addAxis(axisDim, checkDatasetShapeSlicable(axisDataset, dShape));

										} catch (Exception e) {
											logger.warn("Trouble with multidimensional axis {} for {} dim of signal dataset", goodKey, axisDim);
										}

									}

								}
							}

							
						}
					}

				}
			}
		} catch (Exception e) {
			// Non fatal exception, as the file does not support NeXus for axis definitions
			logger.warn("Failed to augment data with axis metadata", e);
		}
		
		// We assign errors in ILazyDatasets read by nexus error
		// "standard"
		// TODO FIXME Also there is the attribute way of specifying and error.
		try {
			for (String name : dh.getNames()) {
				ILazyDataset data = dh.getLazyDataset(name);
				if (data == null)
					continue;
	
				ILazyDataset error = null;
				String errorName = name + NX_ERRORS_SUFFIX;
				if (dh.contains(errorName)) {
					error = dh.getLazyDataset(errorName);
				} else if (name.endsWith("/" + DATA)) {
					final String ep = name.substring(0, name.length() - DATA.length()) + NX_ERRORS;
					error = dh.getLazyDataset(ep);
				}
				if (error != null)
					//TODO need better slicable metadata clearing to stop stack overflow
					error.clearMetadata(AxesMetadata.class);
					data.setError(error);
			}
		} catch (Exception e) {
			// Non fatal exception, as the file does not support NeXus for error definitions
			logger.warn("Failed to augment data with error dataset", e);
		}
		
		return dh;
	}
	
	private ILazyDataset checkDatasetShapeSlicable(ILazyDataset lz, int[] originalShape) {
		
		boolean correct = true;
		
		int[] s = lz.getShape();
		
		if (s.length != originalShape.length) return null;
		
		for (int i = 0; i < originalShape.length; i++) {
			if (s[i] == 1) continue;
			if (s[i] != originalShape[i]) correct = false;
		}
		
		return correct ? lz : null;
	}

}
