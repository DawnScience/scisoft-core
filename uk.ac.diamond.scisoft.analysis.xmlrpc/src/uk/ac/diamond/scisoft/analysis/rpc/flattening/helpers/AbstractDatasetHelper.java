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

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.io.File;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class AbstractDatasetHelper extends MapFlatteningHelper<IDataset> {
	/** Value to assign in {@link IFlattener#TYPE_KEY} */
	public static final String TYPE_NAME = AbstractDataset.class.getCanonicalName();
	/** File to load/save */
	public static final String FILENAME = "filename";
	/**
	 * True if the file should be deleted after it is loaded, false to preserve it. If a new temp file is created, will
	 * be true
	 */
	public static final String DELETEFILEAFTERLOAD = "deletefile";
	/** Index of data set to load from data holder. Name takes precedence over index. If absent, loads index 0. */
	public static final String INDEX = "index";
	/** Name of data set to load from data holder. Takes precedence over index. If absent, loads index 0. */
	public static final String NAME = "name";

	public AbstractDatasetHelper() {
		super(IDataset.class);
	}

	@Override
	public IDataset unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		final String fileName = (String) rootFlattener.unflatten(thisMap.get(FILENAME));
		final Boolean deleteFile = (Boolean) rootFlattener.unflatten(thisMap.get(DELETEFILEAFTERLOAD));
		final Integer index = (Integer) rootFlattener.unflatten(thisMap.get(INDEX));
		final String name = (String) rootFlattener.unflatten(thisMap.get(NAME));
		try {
			final IDataHolder dataHolder = LoaderFactory.getData(fileName, false, null);

			if (deleteFile != null && deleteFile) {
				File file = new File(fileName);
				boolean success = file.delete();
				if (!success) {
					file.deleteOnExit();
				}
			}

			final IDataset data;
			if (name != null) {
				data = dataHolder.getDataset(name);
			} else if (index != null) {
				data = dataHolder.getDataset(index);
			} else {
				data = dataHolder.getDataset(0);
			}

			return data;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Failed to load Dataset from " + thisMap, e);
		}
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof AbstractDataset;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		final DataHolder dh = new DataHolder();
		final AbstractDataset data = (AbstractDataset) obj;
		dh.addDataset("", data);
		final File tempFile;
		try {
			tempFile = File.createTempFile("scisofttmp-", ".npy", rootFlattener.getTempLocation());
			new NumPyFileSaver(tempFile.toString()).saveFile(dh);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unable to save Dataset", e);
		}

		Map<String, Object> outMap = createMap(TYPE_NAME);
		outMap.put(FILENAME, tempFile.toString());
		outMap.put(DELETEFILEAFTERLOAD, true);
		outMap.put(INDEX, 0);
		return outMap;
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> thisMap = (Map<?, ?>) obj;
			return TYPE_NAME.equals(thisMap.get(IFlattener.TYPE_KEY));
		}

		return false;
	}

}
