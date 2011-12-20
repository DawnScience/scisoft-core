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

package gda.analysis.io;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;

/**
 * This interface specifies the methods required to allow the ScanFileHolder to save data to a file of some
 * description
 */
public interface IFileSaver {

	/**
	 * This function is called when the ScanFileHolder needs to save data from a particular source.
	 * It can also be called on by itself
	 * 
	 * @param holder
	 *            The object storing all the data to be saved
	 * @throws ScanFileHolderException
	 */
	void saveFile(DataHolder holder) throws ScanFileHolderException;
}
