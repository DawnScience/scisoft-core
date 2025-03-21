package org.eclipse.dawnsci.analysis.dataset;

import java.io.IOException;

import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataListener;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IDatasetChangeChecker;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.LazyDynamicDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.io.ILazyLoader;

public class MockDynamicLazyDataset extends LazyDynamicDataset {

	private static final long serialVersionUID = 1L;
	private int[][] shapeArrays;
	private int count = 1;
	private Dataset parent;
	private boolean allowIncrement = false;


	public MockDynamicLazyDataset(int[][] shapes, Dataset parent) {
		super(null, "mock", parent.getElementsPerItem(), parent.getClass(), shapes[0], parent.getShape(), null);
		this.parent = parent;
		loader = new DynamicLazyLoader();
		shapeArrays = shapes;
		this.maxShape = parent.getShape();
	}

	MockDynamicLazyDataset(MockDynamicLazyDataset other) {
		super(other);

		shapeArrays = other.shapeArrays;
		count = other.count;
		parent = other.parent;
		allowIncrement = other.allowIncrement;
	}

	public void setAllowIncrement(boolean allow) {
		allowIncrement = allow;
	}

	public Dataset getParent() {
		return parent;
	}

	@Override
	public IDynamicDataset getDataset() {
		return this;
	}

	@Override
	public void setMaxShape(int... maxShape) {
		// do nothing
	}

	@Override
	public void startUpdateChecker(int milliseconds, IDatasetChangeChecker checker) {
		// do nothing
	}

	@Override
	public boolean refreshShape() {

		if (!allowIncrement) return true;

		if (count < shapeArrays.length) {
			int[] s = shapeArrays[count++];
			size = ShapeUtils.calcLongSize(s);
			shape = s.clone();
			oShape = shape;
			oShape.toString();
		}

		return true;
	}

	@Override
	public void addDataListener(IDataListener l) {
		// do nothing
	}

	@Override
	public void removeDataListener(IDataListener l) {
		// do nothing
	}

	@Override
	public void fireDataListeners() {
		// do nothing
	}

	@Override
	public MockDynamicLazyDataset clone() {
		return new MockDynamicLazyDataset(this);
	}

	private class DynamicLazyLoader implements ILazyLoader {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isFileReadable() {
			return true;
		}

		@Override
		public IDataset getDataset(IMonitor mon, SliceND slice) throws IOException {
			return parent.getSlice(slice);
		}
	}
}

