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

package uk.ac.diamond.scisoft.analysis;

import gda.analysis.io.ScanFileHolderException;
import junit.framework.AssertionFailedError;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

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
	public IDataset squeeze() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset squeeze(boolean onlyFromEnd) {
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
	public ILazyDataset getSliceView(int[] start, int[] stop, int[] step) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public ILazyDataset getSliceView(Slice... slice) {
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
	public String getString(int... pos) {
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
	public IMetaData getMetadata() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public MockDataset clone() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public void resize(int... newShape) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public Object mean() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public void setLazyErrors(ILazyDataset errors) {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

	@Override
	public ILazyDataset getLazyErrors() {
		throw new AssertionFailedError("Methods in MockDataset should not be called");
	}

}
