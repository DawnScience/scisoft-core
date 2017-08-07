/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.simplemaths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.LoaderServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.DivideExternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.DivideInternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.ExternalDataModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.InternalDatasetNameModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.MultiplyExternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.MultiplyInternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.PlusExternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.PlusInternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractExternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractInternalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.test.TestHDF5DataUtils;

public class InternalAndExternalOperationsTest {

	@Test
	public void test() throws Exception {
		LocalServiceManager.setLoaderService(new LoaderServiceImpl());
		final String DATA = "data";
		final String OTHER = "other_data";
		final String FULL_DATA = TestHDF5DataUtils.ROOT + Node.SEPARATOR+ DATA;
		final String FULL_OTHER = TestHDF5DataUtils.ROOT + Node.SEPARATOR+ OTHER;
		
		File file = File.createTempFile("dividemathstest", ".h5");
		file.deleteOnExit();
		
		Map<String,int[]> nameShapeMap = new HashMap<>();
		nameShapeMap.put(DATA, new int[]{10,10,10});
		nameShapeMap.put(OTHER, new int[]{10});
		
		TestHDF5DataUtils.makeHDF5File(file.getAbsolutePath(), nameShapeMap);
		
		DivideExternalDataOperation<ExternalDataModel> op = new DivideExternalDataOperation<>();
		ExternalDataModel mod = new ExternalDataModel();
		
		mod.setFilePath(file.getAbsolutePath());
		mod.setDatasetName(FULL_OTHER);
		
		op.setModel(mod);
		
		IDataHolder dh = LoaderFactory.getData(file.getAbsolutePath());
		ILazyDataset lazyDataset = dh.getLazyDataset(FULL_DATA);
		SliceFromSeriesMetadata ssm = new SliceFromSeriesMetadata(new SourceInformation(file.getAbsolutePath(), FULL_DATA, lazyDataset));
		lazyDataset.setMetadata(ssm);
		
		SliceViewIterator it = new SliceViewIterator(lazyDataset, null, new int[]{1,2});
		
		testDivide(it,op);
		
		it.reset();
		
		DivideInternalDataOperation op2 = new DivideInternalDataOperation();
		InternalDatasetNameModel inMod = new InternalDatasetNameModel();
		inMod.setDatasetName(FULL_OTHER);
		op2.setModel(inMod);
		
		testDivide(it,op2);
		
		it.reset();
		
		MultiplyExternalDataOperation mop = new MultiplyExternalDataOperation();
		mop.setModel(mod);
		
		testMultiply(it,mop);
		
		it.reset();
		
		MultiplyInternalDataOperation mop2 = new MultiplyInternalDataOperation();
		mop2.setModel(inMod);
		
		testMultiply(it, mop2);
		
		it.reset();
		
		PlusExternalDataOperation aop = new PlusExternalDataOperation();
		aop.setModel(mod);
		
		testAdd(it,aop);
		
		it.reset();
		
		PlusInternalDataOperation aop2 = new PlusInternalDataOperation();
		aop2.setModel(inMod);
		
		testAdd(it, aop2);
		
		it.reset();
		
		SubtractExternalDataOperation sop = new SubtractExternalDataOperation();
		sop.setModel(mod);
		
		testSubtract(it, sop);
		
		it.reset();
		
		SubtractInternalDataOperation sop2 = new SubtractInternalDataOperation();
		sop2.setModel(inMod);
		
		testSubtract(it, sop2);
		
	}
	
	private void testSubtract(SliceViewIterator it, IOperation<?, ?> op) throws Exception{
		IDataset[] d = getData(it,op);
		
		assertEquals(0,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(0,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(100,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(99,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(200,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(198,d[1].getDouble(0,0,0),0.00000001);
	}
	
	private void testAdd(SliceViewIterator it, IOperation<?, ?> op) throws Exception{
		IDataset[] d = getData(it,op);
		
		assertEquals(0,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(0,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(100,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(101,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(200,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(202,d[1].getDouble(0,0,0),0.00000001);
	}
	
	private void testMultiply(SliceViewIterator it, IOperation<?, ?> op) throws Exception{
		IDataset[] d = getData(it,op);
		
		assertEquals(0,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(0,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(100,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(100,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(200,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(400,d[1].getDouble(0,0,0),0.00000001);
	}
	
	private void testDivide(SliceViewIterator it, IOperation<?, ?> op) throws Exception{
		IDataset[] d = getData(it,op);
		
		assertEquals(0,d[0].getDouble(0,0,0),0.00000001);
		double val = d[1].getDouble(0,0,0);
		assertTrue(Double.isNaN(val));
		
		d = getData(it,op);
		assertEquals(100,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(100,d[1].getDouble(0,0,0),0.00000001);
		
		d = getData(it,op);
		assertEquals(200,d[0].getDouble(0,0,0),0.00000001);
		assertEquals(100,d[1].getDouble(0,0,0),0.00000001);
	}
	
	private IDataset[] getData(SliceViewIterator it, IOperation<?, ?> op) throws Exception{
		it.hasNext();
		ILazyDataset next = it.next();
		IDataset slice = next.getSlice();
		OperationData odata = op.execute(slice, null);
		IDataset output = odata.getData();
		return new IDataset[]{slice,output};
	}

}
