package org.eclipse.dawnsci.analysis.dataset.operations;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

public class AbstractOperationTest {

	@Test
	public void test1to1() throws MetadataException {
		IDataset input = buildData();
		
		AbstractOperation<EmptyModel, OperationData> testOperation = new TestOperation1to1();
		
		SliceND testSlice = build1DSlice(input);
		
		int[] dd = new int[] {3};
		
		SliceInformation sliceinfo = new SliceInformation(testSlice, testSlice.clone(), new SliceND(input.getShape()), dd, 5*6*7, 1);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(sliceinfo);
		
		IDataset slice = input.getSlice(testSlice);
		slice.setMetadata(ssm);
		
		OperationData result = testOperation.execute(slice, null);
		
		IDataset data = result.getData();
		assertArrayEquals(new int[] {1,1,1,8}, data.getShape());
		
		AxesMetadata outAx = data.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] axes = outAx.getAxes();
		assertArrayEquals(new int[] {1,1,1,1},axes[0].getShape());
		assertArrayEquals(new int[] {1,1,1,1},axes[1].getShape());
		assertArrayEquals(new int[] {1,1,1,1},axes[2].getShape());
		assertArrayEquals(new int[] {1,1,1,8},axes[3].getShape());
		
		
		Serializable[] auxData = result.getAuxData();
		assertArrayEquals(new int[] {1,1,1,1},((IDataset)auxData[0]).getShape());
		assertArrayEquals(new int[] {1,1,1,5},((IDataset)auxData[1]).getShape());
		assertArrayEquals(new int[] {1,1,1,5,5},((IDataset)auxData[2]).getShape());
		
	}
	
	@Test
	public void test2to2() throws MetadataException {
		IDataset input = buildData();
		
		AbstractOperation<EmptyModel, OperationData> testOperation = new TestOperation2to2();
		
		SliceND testSlice = build2DSlice(input);
		
		int[] dd = new int[] {2,3};
		
		SliceInformation sliceinfo = new SliceInformation(testSlice, testSlice.clone(), new SliceND(input.getShape()), dd, 5*6, 1);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(sliceinfo);
		
		IDataset slice = input.getSlice(testSlice);
		slice.setMetadata(ssm);
		
		OperationData result = testOperation.execute(slice, null);
		
		IDataset data = result.getData();
		assertArrayEquals(new int[] {1,1,7,8}, data.getShape());
		
		AxesMetadata outAx = data.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] axes = outAx.getAxes();
		assertArrayEquals(new int[] {1,1,1,1},axes[0].getShape());
		assertArrayEquals(new int[] {1,1,1,1},axes[1].getShape());
		assertArrayEquals(new int[] {1,1,7,1},axes[2].getShape());
		assertArrayEquals(new int[] {1,1,1,8},axes[3].getShape());
		
		
		Serializable[] auxData = result.getAuxData();
		assertArrayEquals(new int[] {1,1,1},((IDataset)auxData[0]).getShape());
		assertArrayEquals(new int[] {1,1,5},((IDataset)auxData[1]).getShape());
		assertArrayEquals(new int[] {1,1,5,5},((IDataset)auxData[2]).getShape());
		
	}
	
	@Test
	public void test2to2from2D() throws MetadataException {
		IDataset input = build2DData();
		
		AbstractOperation<EmptyModel, OperationData> testOperation = new TestOperation2to2();
		
		SliceND testSlice = new SliceND(input.getShape()); 
		
		int[] dd = new int[] {0,1};
		
		SliceInformation sliceinfo = new SliceInformation(testSlice, testSlice.clone(), new SliceND(input.getShape()), dd, 1, 0);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(sliceinfo);
		
		IDataset slice = input.getSlice(testSlice);
		slice.setMetadata(ssm);
		
		OperationData result = testOperation.execute(slice, null);
		
		IDataset data = result.getData();
		assertArrayEquals(new int[] {7,8}, data.getShape());
		
		AxesMetadata outAx = data.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] axes = outAx.getAxes();
		assertArrayEquals(new int[] {7,1},axes[0].getShape());
		assertArrayEquals(new int[] {1,8},axes[1].getShape());
		
		
		Serializable[] auxData = result.getAuxData();
		assertArrayEquals(new int[] {1},((IDataset)auxData[0]).getShape());
		assertArrayEquals(new int[] {5},((IDataset)auxData[1]).getShape());
		assertArrayEquals(new int[] {5,5},((IDataset)auxData[2]).getShape());
		
	}
	
	@Test
	public void test2to1() throws Exception {
		inner2t01(false);
		inner2t01(true);
	}
	
	private void inner2t01(boolean reverseDD) throws Exception {
		IDataset input = buildData();
		
		AbstractOperation<EmptyModel, OperationData> testOperation = new TestOperation2to1();
		
		SliceND testSlice = build2DSlice(input);
		
		int[] dd = reverseDD ? new int[] {3,2} : new int[] {2,3};
		
		SliceInformation sliceinfo = new SliceInformation(testSlice, testSlice.clone(), new SliceND(input.getShape()), dd, 5*6, 1);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(sliceinfo);
		
		IDataset slice = input.getSlice(testSlice);
		slice.setMetadata(ssm);
		
		OperationData result = testOperation.execute(slice, null);
		
		IDataset data = result.getData();
		assertArrayEquals(new int[] {1,1,8}, data.getShape());
		
		AxesMetadata outAx = data.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] axes = outAx.getAxes();
		assertArrayEquals(new int[] {1,1,1},axes[0].getShape());
		assertArrayEquals(new int[] {1,1,1},axes[1].getShape());
		assertArrayEquals(new int[] {1,1,8},axes[2].getShape());
		
		Serializable[] auxData = result.getAuxData();
		assertArrayEquals(new int[] {1,1,1},((IDataset)auxData[0]).getShape());
		assertArrayEquals(new int[] {1,1,5},((IDataset)auxData[1]).getShape());
		assertArrayEquals(new int[] {1,1,5,5},((IDataset)auxData[2]).getShape());
	}
	
	@Test
	public void test1to2() throws MetadataException {
		IDataset input = buildData();
		
		AbstractOperation<EmptyModel, OperationData> testOperation = new TestOperation1to2();
		
		SliceND testSlice = build1DSlice(input);
		
		int[] dd = new int[] {3};
		
		SliceInformation sliceinfo = new SliceInformation(testSlice, testSlice.clone(), new SliceND(input.getShape()), dd, 5*6, 1);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(sliceinfo);
		
		IDataset slice = input.getSlice(testSlice);
		slice.setMetadata(ssm);
		
		OperationData result = testOperation.execute(slice, null);
		
		IDataset data = result.getData();
		assertArrayEquals(new int[] {1,1,1,8,8}, data.getShape());
		
		AxesMetadata outAx = data.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] axes = outAx.getAxes();
		assertArrayEquals(new int[] {1,1,1,1,1},axes[0].getShape());
		assertArrayEquals(new int[] {1,1,1,1,1},axes[1].getShape());
		assertArrayEquals(new int[] {1,1,1,1,1},axes[2].getShape());
		//The 1 to 2 operation returns data with no axes, so check none
		assertNull(axes[3]);
		assertNull(axes[4]);
		
		
		Serializable[] auxData = result.getAuxData();
		assertArrayEquals(new int[] {1,1,1,1},((IDataset)auxData[0]).getShape());
		assertArrayEquals(new int[] {1,1,1,5},((IDataset)auxData[1]).getShape());
		assertArrayEquals(new int[] {1,1,1,5,5},((IDataset)auxData[2]).getShape());
		
	}
	
	@Test
	public void test2to2slowest() throws MetadataException {
		IDataset input = buildData();
		AbstractOperation<EmptyModel, OperationData> testOperation = new TestOperation2to2();
		
		SliceND testSlice = build2DSliceSlow(input);
		
		int[] dd = new int[] {0,1};
		
		SliceInformation sliceinfo = new SliceInformation(testSlice, testSlice.clone(), new SliceND(input.getShape()), dd, 5*6, 1);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(sliceinfo);
		
		IDataset slice = input.getSlice(testSlice);
		slice.setMetadata(ssm);
		
		OperationData result = testOperation.execute(slice, null);
		
		IDataset data = result.getData();
		assertArrayEquals(new int[] {5,6,1,1}, data.getShape());
		
		AxesMetadata outAx = data.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] axes = outAx.getAxes();
		assertArrayEquals(new int[] {5,1,1,1},axes[0].getShape());
		assertArrayEquals(new int[] {1,6,1,1},axes[1].getShape());
		assertArrayEquals(new int[] {1,1,1,1},axes[2].getShape());
		assertArrayEquals(new int[] {1,1,1,1},axes[3].getShape());
		
		
		Serializable[] auxData = result.getAuxData();
		assertArrayEquals(new int[] {1,1,1},((IDataset)auxData[0]).getShape());
		assertArrayEquals(new int[] {5,1,1},((IDataset)auxData[1]).getShape());
		assertArrayEquals(new int[] {5,5,1,1},((IDataset)auxData[2]).getShape());
		
	}

	private IDataset buildData() throws MetadataException {
		IDataset input = DatasetFactory.ones(new int[] {5,6,7,8});
		IDataset ax0 = DatasetFactory.ones(new int[] {5});
		IDataset ax1 = DatasetFactory.ones(new int[] {6});
		IDataset ax2 = DatasetFactory.ones(new int[] {7});
		IDataset ax3 = DatasetFactory.ones(new int[] {8});
		
		AxesMetadata axm = MetadataFactory.createMetadata(AxesMetadata.class, 4);
		axm.addAxis(0, ax0);
		axm.addAxis(1, ax1);
		axm.addAxis(2, ax2);
		axm.addAxis(3, ax3);
		
		input.setMetadata(axm);
		
		return input;
	}
	
	private IDataset build2DData() throws MetadataException {
		IDataset input = DatasetFactory.ones(new int[] {7,8});
		IDataset ax0 = DatasetFactory.ones(new int[] {7});
		IDataset ax1 = DatasetFactory.ones(new int[] {8});
		
		AxesMetadata axm = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		axm.addAxis(0, ax0);
		axm.addAxis(1, ax1);
		
		input.setMetadata(axm);
		
		return input;
	}
	
	private SliceND build1DSlice(IDataset input) {
		SliceND testSlice = new SliceND(input.getShape());
		testSlice.setSlice(0, 0, 1, 1);
		testSlice.setSlice(1, 0, 1, 1);
		testSlice.setSlice(2, 0, 1, 1);
		return testSlice;
	}
	
	private SliceND build2DSlice(IDataset input) {
		SliceND testSlice = new SliceND(input.getShape());
		testSlice.setSlice(0, 0, 1, 1);
		testSlice.setSlice(1, 0, 1, 1);
		return testSlice;
	}
	
	private SliceND build2DSliceSlow(IDataset input) {
		SliceND testSlice = new SliceND(input.getShape());
		testSlice.setSlice(2, 0, 1, 1);
		testSlice.setSlice(3, 0, 1, 1);
		return testSlice;
	}
}
