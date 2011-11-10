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

package uk.ac.diamond.scisoft.analysis;

import gda.analysis.io.ScanFileHolderException;
import junit.framework.AssertionFailedError;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.gda.monitor.IMonitor;

public class MockDataset implements IDataset {
	@Override
	public String getName() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int getSize() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int[] getShape() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int getRank() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public ILazyDataset squeeze() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public ILazyDataset squeeze(boolean onlyFromEnd) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(int[] start, int[] stop, int[] step) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(IMonitor monitor, int[] start, int[] stop, int[] step)
			throws ScanFileHolderException {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(Slice... slice) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(IMonitor monitor, Slice... slice) throws ScanFileHolderException {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public Class<?> elementClass() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int getElementsPerItem() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int getItemsize() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public void setName(String name) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
		
	}

	@Override
	public void setShape(int... shape) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
		
	}

	@Override
	public Object getObject(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public double getDouble(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public long getLong(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public float getFloat(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int getInt(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public short getShort(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public byte getByte(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public boolean getBoolean(int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public void set(Object obj, int... pos) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
		
	}

	@Override
	public Number min() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public Number max() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int[] minPos() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public int[] maxPos() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public void setMetadata(IMetaData metdada) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
		
	}

	@Override
	public IMetaData getMetaData() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}
}