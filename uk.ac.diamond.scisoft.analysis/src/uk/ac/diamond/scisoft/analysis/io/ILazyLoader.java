/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.Serializable;

import gda.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.gda.monitor.IMonitor;

public interface ILazyLoader extends Serializable {

	/**
	 * 
	 * @return true if file is readable
	 */
	public boolean isFileReadable();

	/**
	 * @param mon
	 * @param shape
	 * @param start
	 * @param stop
	 * @param step
	 * @return a slice of a dataset
	 * @throws ScanFileHolderException
	 */
	public AbstractDataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step) throws ScanFileHolderException;
}
