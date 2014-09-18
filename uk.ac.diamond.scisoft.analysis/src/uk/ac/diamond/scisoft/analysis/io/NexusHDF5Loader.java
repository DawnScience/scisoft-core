/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
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

					// look through the additional metadata for axis information
					// TODO Should take @primary into account when adding axes.
					// TODO also this only deals with 1D axis at the moment.
					for (String goodKey : additionalMetadata) {
						if (goodKey.endsWith("@axis")) {
							String axisName = goodKey.replace("@axis", "");
							ILazyDataset axisData = dh.getLazyDataset(axisName);
							int axisDim = Integer.parseInt((String) dh.getMetadata().getMetaValue(goodKey)) - 1; // zero-based
							ILazyDataset axisDataset = axisData.clone();

							int[] shape = new int[data.getShape().length];
							for (int i = 0; i < shape.length ; i++) {
								if (i == axisDim) {
									shape[i] = axisData.getShape()[0];
								} else {
									shape[i] = 1;
								}
							}
							axisDataset.setShape(shape);
							axesMetadata.addAxis(axisDataset, axisDim);
						}
					}

					data.setMetadata(null);
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

}
