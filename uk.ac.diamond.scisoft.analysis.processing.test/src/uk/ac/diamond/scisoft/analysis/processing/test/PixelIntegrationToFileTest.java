/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.operations.PixelIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.PowderIntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.visitors.HierarchicalFileExecutionVisitor;

public class PixelIntegrationToFileTest {
	
private static IOperationService service;
	
	/**
	 * Manually creates the service so that no extension points have to be read.
	 * 
	 * We do this use annotations
	 * @throws Exception 
	 */
	@BeforeClass
	public static void before() throws Exception {
		service = (IOperationService)Activator.getService(IOperationService.class);
		
		// Just read all these operations.
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.operations");
	}
	
	@Test
	public void testIntegration() throws Exception {
		
		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(1000,1000);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1);
		final IDataset innerDS = Random.rand(0.0, 1000.0, 24, 1000, 1000);
		int[] dsShape = new int[]{24, 1000, 1000};
		ILazyDataset lz = new LazyDataset("test", Dataset.FLOAT64, dsShape, new ILazyLoader() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isFileReadable() {
				return true;
			}
			
			@Override
			public IDataset getDataset(IMonitor mon, int[] shape, int[] start,
					int[] stop, int[] step) throws Exception {
				// TODO Auto-generated method stub
				return innerDS.getSlice(mon, start, stop, step);
			}
		});
		final RichDataset   rand = new RichDataset(lz, null, null, null, null);
		Map<Integer, String> slMap = new HashMap<Integer, String>();
		slMap.put(0, "all");
////		slMap.put(1, "all");
//		slMap.put(0, "2:10:2");
//		slMap.put(1, "0:10:3");
		
		rand.setSlicing(slMap);
		
		final IDataset axDataset1 = DatasetFactory.createRange(24,Dataset.INT16);
		axDataset1.setShape(new int[] {24,1,1});
		axDataset1.setName("z");
		
		final IDataset axDataset2 = DatasetFactory.createRange(1000,Dataset.INT32);
		axDataset2.setShape(new int[] {1,1000,1});
		axDataset2.setName("y");
		
		final IDataset axDataset3 = DatasetFactory.createRange(1000,Dataset.INT32);
		axDataset3.setShape(new int[] {1,1,1000});
		axDataset3.setName("x");
		
		AxesMetadataImpl am = new AxesMetadataImpl(3);
		am.addAxis(axDataset1, 0);
		am.addAxis(axDataset2, 1);
		am.addAxis(axDataset3, 2);
		
		lz.addMetadata(am);
		
		final IOperation di = new DiffractionMetadataTestImportOperation();
		
		//pixel integration
		final IOperation azi = new PixelIntegrationOperation();
		azi.setModel(new PowderIntegrationModel());
		
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			long time =  System.currentTimeMillis();
			
			service.executeSeries(rand, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), di,azi);
			
			System.out.println( System.currentTimeMillis()  - time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("debug");
			
	}

}
