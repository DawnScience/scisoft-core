/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.util.HashMap;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers.AbstractDatasetHelper;

/**
 * Use this class to describe a data set that already resides on disk and can be loaded using the file loaders. This
 * object is flattened to a flattened representation that is unflattened by {@link AbstractDatasetHelper}.
 * <p>
 * The unflattened form of this type is an {@link AbstractDataset} in Java or an Numpy.ndarray in Python.
 * <p>
 * When unflattended in Java the filename can be any file that Analysis understands with its file loaders as the
 * filename is passed to {@link LoaderFactory}.
 * <p>
 * When unflattended in Python the filename must be an .npy fileformat that numpy.load can process. .npz file formats
 * are not officially supported.
 * 
 * @see LoaderFactory
 * @see <a href="http://docs.scipy.org/doc/numpy/reference/generated/numpy.load.html">Numpy loading</a>
 */
public class AbstractDatasetDescriptor implements IFlattens {

	private String filename;
	private boolean deleteAfterLoad;
	private Integer index;
	private String name;

	/**
	 * Return full path to the file to load
	 * 
	 * @return file name
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Return whether to delete the file when this object is unflattened to an AbstractDataset
	 * 
	 * @return whether to delete
	 */
	public boolean isDeleteAfterLoad() {
		return deleteAfterLoad;
	}

	/**
	 * Get the index in the loaded data holder to use as the data set if no name is specified.
	 * 
	 * @return index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Get the name in the loaded data holder to use as the data set.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Full path to the file to load.
	 * 
	 * @param filename
	 *            of the file to load.
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Set to true to delete the file when this object is unflattened to an AbstractDataset
	 * 
	 * @param deleteAfterLoad
	 */
	public void setDeleteAfterLoad(boolean deleteAfterLoad) {
		this.deleteAfterLoad = deleteAfterLoad;
	}

	/**
	 * Set the index in the loaded data holder to use as the data set if no name is specified.
	 * 
	 * @param index
	 *            to load
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * Set the name in the loaded data holder to use as the data set.
	 * 
	 * @param name
	 *            to load
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object flatten(IRootFlattener rootFlattener) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put(IFlattener.TYPE_KEY, AbstractDatasetHelper.TYPE_NAME);
		outMap.put(AbstractDatasetHelper.FILENAME, rootFlattener.flatten(filename));
		outMap.put(AbstractDatasetHelper.DELETEFILEAFTERLOAD, rootFlattener.flatten(deleteAfterLoad));
		outMap.put(AbstractDatasetHelper.INDEX, rootFlattener.flatten(index));
		outMap.put(AbstractDatasetHelper.NAME, rootFlattener.flatten(name));
		return outMap;
	}

}
