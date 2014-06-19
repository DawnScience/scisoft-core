/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * 
 * This class is a HDF5Loader with extra things associated by
 * the nexus standard. Primarily if an ILazyDataset is loaded,
 * it will attempt to load the errors associated with the dataset.
 *  
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

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		
		DataHolder dh = super.loadFile(mon);
		if (dh==null) return null;
		
		// We assign errors in ILazyDatasets read by nexus error
		// "standard"
		// TODO FIXME Also there is the attribute way of specifying and error.
		for (String name : dh.getNames()) {
			ILazyDataset data  = dh.getLazyDataset(name);
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
				data.setLazyErrors(error);
		}
		return dh;
	}

}
