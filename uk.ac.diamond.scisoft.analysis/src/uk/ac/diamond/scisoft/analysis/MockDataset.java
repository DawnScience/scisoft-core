/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import java.io.Serializable;
import java.text.Format;
import java.util.List;

import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.MetadataType;

public class MockDataset implements IDataset {
	@Override
	public void setStringFormat(Format format) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int getSize() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int[] getShape() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int getRank() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset squeezeEnds() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset squeeze() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset squeeze(boolean onlyFromEnd) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(int[] start, int[] stop, int[] step) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(IMonitor monitor, int[] start, int[] stop, int[] step)
			throws DatasetException {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(Slice... slice) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(SliceND slice) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(IMonitor monitor, Slice... slice) throws DatasetException {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSlice(IMonitor monitor, SliceND slice) throws DatasetException {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSliceView(int[] start, int[] stop, int[] step) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSliceView(Slice... slice) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getSliceView(SliceND slice) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getTransposedView(int... axes) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public Class<?> getElementClass() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int getElementsPerItem() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int getItemBytes() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
		
	}

	@Override
	public void setShape(int... shape) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public String getString(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public Object getObject(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public double getDouble(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public long getLong(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public float getFloat(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int getInt(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public short getShort(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public byte getByte(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public boolean getBoolean(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public void set(Object obj, int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
		
	}

	@Override
	public Number min(boolean... switches) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public Number max(boolean... switches) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int[] minPos() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public int[] maxPos() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public void addMetadata(MetadataType metadata) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public void setMetadata(MetadataType metdada) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IMetadata getMetadata() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public <T extends MetadataType> List<T> getMetadata(Class<T> clazz) throws MetadataException {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}
	
	@Override
	public <T extends MetadataType> T getFirstMetadata(Class<T> clazz) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}
	
	@Override
	public MockDataset clone() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public void resize(int... newShape) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public Object mean(boolean... switches) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public boolean hasErrors() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public IDataset getError() {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public void setError(Serializable errors) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public double getError(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public double[] getErrorArray(int... pos) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}

	@Override
	public <T extends MetadataType> void clearMetadata(Class<T> clazz) {
		throw new UnsupportedOperationException("Methods in MockDataset should not be called");
	}
}
