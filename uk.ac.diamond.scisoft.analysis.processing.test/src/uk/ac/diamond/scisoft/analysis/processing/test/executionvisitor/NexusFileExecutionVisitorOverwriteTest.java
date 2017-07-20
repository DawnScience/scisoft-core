/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class NexusFileExecutionVisitorOverwriteTest {

	@Test
	public void test() throws Exception {
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
 
		int[] shape = {20,50};
		double firstVal = 1.5;
		
	    NexusFileExecutionVisitor nfev = new NexusFileExecutionVisitor(tmp.getAbsolutePath(),true,null);
	    
	    Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		
		Dataset d = DatasetFactory.zeros(DoubleDataset.class,shape);
		d.iadd(firstVal);
	    
		IOperation<?, ?>[] series = {op22};
	    
	    nfev.init(series, d);
	    
	    SliceND full = new SliceND(d.getShape());
	    
	    SliceInformation sl = new SliceInformation(full, full, full, new int[]{0,1}, 1, 0);
	    SourceInformation so = new SourceInformation("path", "name", d);
	    SliceFromSeriesMetadata md = new SliceFromSeriesMetadata(so,sl);
	    d.setMetadata(md);
	    OperationData opd = new OperationData(d);
	    nfev.notify(series[0], opd);
	    nfev.executed(opd, null);
	    
	    
	    IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
	    ILazyDataset dd = dh.getLazyDataset("/entry/result/data");
	    IDataset slice = dd.getSlice();
	    
	    assertEquals(firstVal, slice.getDouble(new int[]{0,0}), 0.000001);
	    
	    //wait to make sure we flush next write
	    Thread.sleep(5000);
	    
	    double secondVal = 2.5;
	    
	    d.iadd(secondVal-firstVal);
	    
	    nfev.notify(series[0], opd);
	    nfev.executed(opd, null);
	    
	    slice = dd.getSlice();
	    
	    assertEquals(secondVal, slice.getDouble(new int[]{0,0}), 0.000001);
	    
	    nfev.close();
	    
	}

}
