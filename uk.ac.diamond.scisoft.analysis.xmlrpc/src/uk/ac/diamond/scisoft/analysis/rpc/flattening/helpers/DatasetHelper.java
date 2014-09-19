/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.io.File;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class DatasetHelper extends MapFlatteningHelper<IDataset> {
	/** Value to assign in {@link IFlattener#TYPE_KEY} */
	public static final String TYPE_NAME = Dataset.class.getCanonicalName();
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

	public DatasetHelper() {
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
		return obj instanceof Dataset;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		final DataHolder dh = new DataHolder();
		final Dataset data = (Dataset) obj;
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
