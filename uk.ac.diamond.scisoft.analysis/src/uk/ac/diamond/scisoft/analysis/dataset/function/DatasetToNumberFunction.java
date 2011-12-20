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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * This interface is to define functions that take a dataset or an array of datasets and returns numbers
 * (which could be complex and so returned as pairs)
 */
public interface DatasetToNumberFunction {

	/**
	 * @param datasets
	 * @return list of objects
	 */
	public List<? extends Number> value(IDataset... datasets);

}
