/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.test;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.conversion.ConversionServiceImpl;
import org.eclipse.dawnsci.analysis.api.conversion.IConversionService;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.OperationServiceImpl;

public class ConvertOperationsTest {

	static IConversionService service;
	
	@BeforeClass
	public static void before() throws Exception {
		
		ServiceManager.setService(IOperationService.class, new OperationServiceImpl());
		ServiceManager.setService(IConversionService.class, new ConversionServiceImpl());
		
		service = (IConversionService)ServiceManager.getService(IConversionService.class);
		
	}
	
	@Test
	public void testConvertProcess() throws Exception {
		//TODO uncomment when added test data
//		if (service == null) throw new Exception("Cannot get the service!");
//		
//		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(1000,1000);
//		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1);
//		
//		//Import metadata
//		final IOperation di = new DiffractionMetadataImportOperation();
//		DiffractionMetadataImportModel model = new DiffractionMetadataImportModel();
//		model.setFilePath("/dls/science/groups/das/ExampleData/powder/i18/dataCollectionCal.nxs");
//		di.setModel(model);
//		
//		//pixel integration
//		final IOperation azi = new PixelIntegrationOperation();
//		azi.setModel(new PowderIntegrationModel());
//		
//		final IOperation[] ops = new IOperation[]{di,azi};
//		
//		IConversionContext open = service.open("/dls/science/groups/das/presentation_data/i18/dataCollection.dat");
//		
//		open.setConversionScheme(ConversionScheme.PROCESS);
//		
//		open.setDatasetName("Pilatus");
//		open.addSliceDimension(0, "all");
//		open.setOutputPath(System.getProperty("java.io.tmpdir"));
//		open.setUserObject(new IProcessingConversionInfo() {
//			
//			@Override
//			public IOperation[] getOperationSeries() {
//				return ops;
//			}
//
//			@Override
//			public IExecutionVisitor getExecutionVisitor(String fileName) {
//				return new HierarchicalFileExecutionVisitor(fileName);
//			}
//
//		});
//		
//		service.process(open);
	}
	
}
