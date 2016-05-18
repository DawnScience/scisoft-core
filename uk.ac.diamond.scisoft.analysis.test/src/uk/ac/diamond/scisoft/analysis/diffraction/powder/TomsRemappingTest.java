/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.List;
import java.util.Formatter.BigDecimalLayoutForm;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyWriteableDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyWriteableDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation2D;
import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation2D.BicubicInterpolationOutput;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class TomsRemappingTest {

	@Test
	public void testRemap() throws Exception{
		
		String path1 = "/scratch/stuff/stitching/61130_1.nxs";
		String path2 = "/scratch/stuff/stitching/61131_1.nxs";
		String path3 = "/scratch/stuff/stitching/61132_1.nxs";
		
		IDataHolder dh1 = LoaderFactory.getData(path1,null);
		IDataHolder dh2 = LoaderFactory.getData(path2,null);
		IDataHolder dh3 = LoaderFactory.getData(path3,null);
		String ds = "/entry1/medipix/Region1_maxvalue";
		String bs = "/entry1/medipix/bragg1";
		String es = "/entry1/medipix/XESEnergy";
		
		
		//read in all datasets
		IDataset d1 = dh1.getLazyDataset(ds).getSlice();
		IDataset b1  = dh1.getLazyDataset(bs).getSlice();
		IDataset e1  = dh1.getLazyDataset(es).getSlice();
		IDataset d2 = dh2.getLazyDataset(ds).getSlice();
		IDataset b2  = dh2.getLazyDataset(bs).getSlice();
		IDataset e2  = dh2.getLazyDataset(es).getSlice();
		IDataset d3 = dh3.getLazyDataset(ds).getSlice();
		IDataset b3  = dh3.getLazyDataset(bs).getSlice();
		IDataset e3  = dh3.getLazyDataset(es).getSlice();
	
		
		Dataset dcc = DatasetUtils.concatenate(new IDataset[]{d1, d2, d3}, 1);
		Dataset bcc = DatasetUtils.concatenate(new IDataset[]{b1, b2, b3}, 1);
		Dataset ecc = DatasetUtils.concatenate(new IDataset[]{e1, e2, e3}, 1);
		
		//for bcc and ecc, check out the minimum and maximum stepsizes
		IDataset bcc_slice = bcc.getSlice(null, new int[]{bcc.getShape()[0],  bcc.getShape()[1]-1}, null);
		IDataset bcc_diff = Maths.subtract(bcc.getSlice(new int[]{0, 1}, null, null), bcc_slice);
		
		Dataset bcc_mean = bcc.mean(0);
		
		Dataset linspaced = DatasetFactory.createLinearSpace(bcc_mean.getDouble(0), bcc_mean.getDouble(bcc_mean.getSize()-1), bcc_mean.getSize(), Dataset.FLOAT64);
		
		System.out.println("mean[0]: " + bcc_mean.getDouble(0));
		System.out.println("mean[last]: " + bcc_mean.getDouble(bcc_mean.getSize()-1));
		System.out.println("linspaced[0]: " + linspaced.getDouble(0));
		System.out.println("linspaced[last]: " + linspaced.getDouble(linspaced.getSize()-1));
		
		Dataset ecc_mean = ecc.mean(1);
		System.out.println("mean[0]: " + ecc_mean.getDouble(0));
		System.out.println("mean[last]: " + ecc_mean.getDouble(ecc_mean.getSize()-1));

		System.out.println("dcc shape: " + ArrayUtils.toString(dcc.getShape()));
		System.out.println("bcc shape: " + ArrayUtils.toString(bcc_mean.getShape()));
		System.out.println("ecc shape: " + ArrayUtils.toString(ecc_mean.getShape()));
		
		Dataset dcc_new = Interpolation2D.bicubicInterpolation(ecc_mean, bcc_mean, dcc, ecc_mean, linspaced, BicubicInterpolationOutput.TWOD);
		
		/*for (int i = 0 ; i < dcc_new.getShape()[0] ; i++) {
			dcc_new.set
		}*/
		
	//	((Dataset)b1).flatten();
		
		//XYImagePixelCache cache = new XYImagePixelCache(bcc, ecc, new double[]{bcc.min().doubleValue(),bcc.max().doubleValue()}, new double[]{ecc.min().doubleValue(),ecc.max().doubleValue()}, bcc.getShape()[1], bcc.getShape()[0]);
		
		//List<Dataset> out = PixelIntegration.integrate(dcc, null, cache);
		
		Dataset rv = dcc_new;
		/*AxesMetadataImpl axma = new AxesMetadataImpl(1);
		axma.setAxis(0, out.get(0));
		rv.addMetadata(axma);
		axma = new AxesMetadataImpl(1);
		axma.setAxis(0, out.get(2));
		rv.addMetadata(axma);
		*/
		NexusFile nf =new NexusFileHDF5("/scratch/stuff/stitching/output.nxs");
		nf.createAndOpenToWrite();
		GroupNode group = nf.getGroup(Node.SEPARATOR, true);
		
		ILazyWriteableDataset lwds = new LazyWriteableDataset("data", rv.getDtype(), rv.getShape(), rv.getShape(), rv.getShape(), null);
		
		nf.createData(group, lwds, NexusFile.COMPRESSION_LZW_L1);
		lwds.setSlice(null, rv, new SliceND(rv.getShape()));
		
		nf.close();

		
		rv.toString();
		
	}
	
}
