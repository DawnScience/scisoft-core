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
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;

import uk.ac.diamond.scisoft.analysis.metadata.ARPESMetadataImpl;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.utils.OSUtils;

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

	private static final String NX_ARPES_MANIPULATOR_SAAZIMUTHAL = "/entry1/instrument/manipulator/saazimuthal";
	private static final String NX_ARPES_MANIPULATOR_SATILT = "/entry1/instrument/manipulator/satilt";
	private static final String NX_ARPES_MANIPULATOR_SAPOLAR = "/entry1/instrument/manipulator/sapolar";
	private static final String NX_ARPES_ANALYSER_ENERGIES = "/entry1/instrument/analyser/energies";
	private static final String NX_ARPES_ANALYSER_ANGLES = "/entry1/instrument/analyser/angles";
	private static final String NX_ARPES_SAMPLE_TEMPERATURE = "/entry1/sample/temperature";
	private static final String NX_ARPES_MONOCHROMATOR_ENERGY = "/entry1/instrument/monochromator/energy";
	private static final String NX_ARPES_ANALYSER_PASS_ENERGY = "/entry1/instrument/analyser/pass_energy";
	private static final String NX_ARPES_ANALYSER_DATA = "/entry1/instrument/analyser/data";
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {

		DataHolder dh = super.loadFile(mon);
		if (dh == null)
			return null;

		// We assign errors in ILazyDatasets read by nexus error
		// "standard"
		// TODO FIXME Also there is the attribute way of specifying and error.
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
				data.setError(error);
		}

		// TODO Add in unit metadata information

		// Augment data as required
		// get all data with signal attribute
		try {
			for (String metaKey : dh.getMetadata().getMetaNames()) {
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
					for (String repassKey : dh.getMetadata().getMetaNames()) {
						if (repassKey.startsWith(parentKey)) {
							additionalMetadata.add(repassKey);
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
					axesMetadata.toString();

					// look through the additional metadata for axis information
					// TODO Should take @primary into account when adding axes.
					// TODO also this only deals with 1D axis at the moment.
					int[] dShape = data.getShape();
					for (String goodKey : additionalMetadata) {
						if (goodKey.endsWith("@axis")) {
							String axisName = goodKey.replace("@axis", "");
							ILazyDataset axisData = dh.getLazyDataset(axisName);
							int axisDim = Integer.parseInt((String) dh.getMetadata().getMetaValue(goodKey)) - 1; // zero-based
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
								axesMetadata.addAxis(checkDatasetShapeSlicable(axisDataset, dShape), axisDim);
							} else {
								if (axisDataset.getRank() == data.getRank()){
									axesMetadata.addAxis(axisDataset, axisDim);
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
										
										axesMetadata.addAxis(checkDatasetShapeSlicable(axisDataset, dShape), axisDim);

									} catch (Exception e) {
										logger.warn("Trouble with multidimensional axis {} for {} dim of signal dataset", goodKey, axisDim);
									}
									
								}
								
							}
							
						}
					}

				}
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("Failed to augment data with metadata", e);
		}

		// TODO this should probably be done with an extention point, but possibly wait until an osgi gda server and jython.
		// Add ARPES specific metadata where required.
		if (dh.contains(NX_ARPES_ANALYSER_DATA)) {
			ILazyDataset data = dh.getLazyDataset(NX_ARPES_ANALYSER_DATA);
			ARPESMetadataImpl arpesMetadata = new ARPESMetadataImpl();
			try {
				arpesMetadata.setPassEnergy(Double.parseDouble((String) dh.getMetadata().getMetaValue(
						NX_ARPES_ANALYSER_PASS_ENERGY)));
			} catch (Exception e) {
				System.out.println(e);
			}
			try {
				arpesMetadata.setPhotonEnergy(Double.parseDouble((String) dh.getMetadata().getMetaValue(
						NX_ARPES_MONOCHROMATOR_ENERGY)));
			} catch (Exception e) {
				System.out.println(e);
			}
			try {
				arpesMetadata.setTemperature(Double.parseDouble((String) dh.getMetadata().getMetaValue(
						NX_ARPES_SAMPLE_TEMPERATURE)));
			} catch (Exception e) {
				System.out.println(e);
			}
			
			if (dh.contains(NX_ARPES_ANALYSER_ANGLES)) {
				arpesMetadata.setAnalyserAngles(dh.getLazyDataset(NX_ARPES_ANALYSER_ANGLES));
			} else {
				arpesMetadata.setAnalyserAngles(new DoubleDataset(new double[] {0.0}, new int[] {1}));
			}
			
			if (dh.contains(NX_ARPES_ANALYSER_ENERGIES)) {
				arpesMetadata.setKineticEnergies(dh.getLazyDataset(NX_ARPES_ANALYSER_ENERGIES));
			} else {
				arpesMetadata.setKineticEnergies(new DoubleDataset(new double[] {0.0}, new int[] {1}));
			}
			
			if (dh.contains(NX_ARPES_MANIPULATOR_SAPOLAR)) {
				arpesMetadata.setPolarAngles(dh.getLazyDataset(NX_ARPES_MANIPULATOR_SAPOLAR));
			} else {
				arpesMetadata.setPolarAngles(new DoubleDataset(new double[] {0.0}, new int[] {1}));
			}
			
			if (dh.contains(NX_ARPES_MANIPULATOR_SATILT)) {
				arpesMetadata.setTiltAngles(dh.getLazyDataset(NX_ARPES_MANIPULATOR_SATILT));
			} else {
				arpesMetadata.setTiltAngles(new DoubleDataset(new double[] {0.0}, new int[] {1}));
			}
		
			if (dh.contains(NX_ARPES_MANIPULATOR_SAAZIMUTHAL)) {
				arpesMetadata.setAzimuthalAngles(dh.getLazyDataset(NX_ARPES_MANIPULATOR_SAAZIMUTHAL));
			} else {
				arpesMetadata.setAzimuthalAngles(new DoubleDataset(new double[] {0.0}, new int[] {1}));
			}
			data.setMetadata(arpesMetadata);
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
