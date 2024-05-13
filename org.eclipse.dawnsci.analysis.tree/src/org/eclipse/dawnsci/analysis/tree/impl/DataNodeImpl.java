/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.tree.impl;

import java.io.Serializable;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.metadata.DimensionMetadata;
import org.eclipse.january.metadata.MetadataFactory;

public class DataNodeImpl extends NodeImpl implements DataNode, Serializable {
	protected static final long serialVersionUID = 9089016783319981598L;

	private boolean unsigned = false;
	private boolean string = false;
	private boolean supported = false;
	private boolean augmented = false;
	private ILazyDataset dataset;
	private long[] maxShape;
	private long[] chunkShape;
	private String text;
	private String type;
	private int maxTextLength = -1;
	private int rank = -1;

	/**
	 * Construct a data node with given object ID
	 * @param oid object ID
	 */
	public DataNodeImpl(byte[] oid) {
		super(oid);
	}

	/**
	 * Construct a data node with given object ID
	 * @param oid object ID
	 */
	public DataNodeImpl(long oid) {
		this(toBytes(oid));
	}

	@Override
	public boolean isUnsigned() {
		return unsigned;
	}

	@Override
	public void setUnsigned(boolean isUnsigned) {
		unsigned = isUnsigned;
	}

	@Override
	public boolean isString() {
		return string;
	}

	@Override
	public int getMaxStringLength() {
		return maxTextLength;
	}

	@Override
	public void setMaxStringLength(int length) {
		maxTextLength = length;
	}

	@Override
	public String getTypeName() {
		return type;
	}

	@Override
	public void setTypeName(String name) {
		type = name;
	}

	@Override
	public long[] getMaxShape() {
		if (dataset instanceof IDynamicDataset dynamicDataset) {
			return toLongArray(dynamicDataset.getMaxShape());
		}
		
		return maxShape;
	}

	@Override
	public void setMaxShape(long... maxShape) {
		if (dataset == null) {
			rank = maxShape == null ? 0 : maxShape.length;
		} else if (maxShape != null && maxShape.length != dataset.getRank()) {
			throw new IllegalArgumentException("Maximum shape must match rank of dataset");
		}
		
		this.maxShape = maxShape;
		
		if (dataset instanceof IDynamicDataset dynamicDataset) {
			dynamicDataset.setMaxShape(toIntArray(maxShape));
		}
	}
	
	@Override
	public int getRank() {
		return rank;
	}
	
	@Override
	public long[] getChunkShape() {
		if (dataset instanceof IDynamicDataset dynamicDataset) {
			return toLongArray(dynamicDataset.getChunking());
		}
		
		return chunkShape;
	}

	@Override
	public void setChunkShape(long... chunkShape) {
		if (chunkShape != null && dataset != null && chunkShape.length != dataset.getRank()) {
			throw new IllegalArgumentException("Chunk shape must match rank of dataset");
		}
		this.chunkShape = chunkShape;
		
		if (dataset instanceof IDynamicDataset dynamicDataset) {
			dynamicDataset.setChunking(toIntArray(chunkShape));
		}
	}

	@Override
	public boolean isSupported() {
		return supported;
	}

	@Override
	public void setEmpty() {
		dataset = null;
		supported = true;
	}

	@Override
	public String getString() {
		if (!string)
			return null;
		if (text != null)
			return text;
	
		StringDataset strDataset;
		if (dataset instanceof StringDataset stringDat) {
			strDataset = stringDat;
		} else {
			try {
				strDataset = (StringDataset) dataset.getSlice();
			} catch (DatasetException e) {
				return "Could not get data from lazy dataset";
			}
		}

		final String result;
		int size = strDataset.getSize();
		if (size == 0) {
			result = "";
		} else if (size == 1) {
			result = strDataset.getString();
		} else {
			result = strDataset.toString(true);
		}

		text = result; // cache the value for any subsequent call
		maxTextLength = text.getBytes().length;
		return text;
	}

	@Override
	public void setString(final String text) {
		setDataset(DatasetFactory.createFromObject(StringDataset.class, text));
		this.text = text; // the cached value
		if (text != null) {
			maxTextLength = text.getBytes().length;
			string = true;
			supported = true;
		}
	}

	@Override
	public ILazyDataset getDataset() {
		return dataset;
	}

	@Override
	public ILazyWriteableDataset getWriteableDataset() {
		return (ILazyWriteableDataset) (dataset instanceof ILazyWriteableDataset ? dataset : null);
	}

	@Override
	public void setDataset(final ILazyDataset lazyDataset) {
		dataset = lazyDataset;
		rank = dataset.getRank();

		if (maxShape != null || chunkShape != null) {
			int[] mshape = toIntArray(maxShape);
			int[] cshape = toIntArray(chunkShape);
			try {
				DimensionMetadata dmd = MetadataFactory.createMetadata(DimensionMetadata.class, dataset.getShape(), mshape, cshape);
				dataset.setMetadata(dmd);
			} catch (MetadataException e) {
				e.printStackTrace();
			}
		}

		supported = true;
		string = lazyDataset instanceof StringDataset || lazyDataset.getElementClass() == String.class;
		text = null; // extracted by getString()
	}

	@Override
	public boolean isAugmented() {
		return augmented;
	}

	@Override
	public void setAugmented() {
		augmented = true;
	}
	
	@Override
	public boolean isDataNode() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(super.toString());
	
		out.append(INDENT);
		if (string) {
			out.append(getString());
		} else if (supported) {
			out.append(dataset == null ? "empty" : dataset.toString());
		} else {
			out.append("unsupported");
		}
		return out.toString();
	}
	
	private static int[] toIntArray(long[] longArray) {
		if (longArray == null) return null; // NOSONAR, null is allowed as this is a conversion function

		Arrays.stream(longArray)
			.filter(i -> i > Integer.MAX_VALUE && i != Long.MAX_VALUE) // some files have wrongly written max shape
			.findFirst()
			.ifPresent(i -> { throw new IllegalArgumentException("Dimension size is too large for an integer " + i); });

		return Arrays.stream(longArray).mapToInt(i -> i == Long.MAX_VALUE ? -1 : (int) i).toArray();
	}
	
	private static long[] toLongArray(int[] intArray) {
		if (intArray == null) return null; // NOSONAR, null is allowed as this is a conversion function
		
		return Arrays.stream(intArray).mapToLong(i -> i).toArray();
	}

}
