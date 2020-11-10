/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.saxs;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.diffraction.NumberOfSymmetryFolds;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.CinaderOrientationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.CinaderOrientationOperation;

public class CinaderOrientationOperationTest {

	
	@Test
	public void testOrientation2fold() throws Exception {
		innerTest(0,NumberOfSymmetryFolds.TWO_FOLD);
		innerTest(45,NumberOfSymmetryFolds.TWO_FOLD);
		innerTest(90,NumberOfSymmetryFolds.TWO_FOLD);
		innerTest(135,NumberOfSymmetryFolds.TWO_FOLD);
		
	}
	
	@Test
	public void testOrientation4fold() throws Exception {

		innerTest(0,NumberOfSymmetryFolds.FOUR_FOLD);
		innerTest(30,NumberOfSymmetryFolds.FOUR_FOLD);
		innerTest(45,NumberOfSymmetryFolds.FOUR_FOLD);
		innerTest(65,NumberOfSymmetryFolds.FOUR_FOLD);
		
	}
	
	@Test
	public void testOrientation6fold() throws Exception {
		
		innerTest(0,NumberOfSymmetryFolds.SIX_FOLD);
		innerTest(30,NumberOfSymmetryFolds.SIX_FOLD);
		innerTest(45,NumberOfSymmetryFolds.SIX_FOLD);
		
	}
	
	
	@Test
	public void testOrientation8fold() throws Exception {

		innerTest(0,NumberOfSymmetryFolds.EIGHT_FOLD);
		innerTest(30,NumberOfSymmetryFolds.EIGHT_FOLD);
		
	}
	
	private void innerTest(double position, NumberOfSymmetryFolds folds) throws Exception {
		
		CinaderOrientationOperation o = new CinaderOrientationOperation();
		CinaderOrientationModel model = new CinaderOrientationModel();
		model.setFoldsOfSymmetry(folds);
		o.setModel(model);
		DoubleDataset r = DatasetFactory.createRange(-180, 180, 1.0);
		
		Dataset d = createTestData(position,r,folds);
		
		AxesMetadata m = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		m.setAxis(0, r);
		d.setMetadata(m);
		
		OperationData process = o.process(d, null);
		
		Dataset angle = (Dataset)process.getAuxData()[1];
		
		assertEquals(position, angle.getDouble(),1);
	}
	
	private Dataset createTestData(double position, DoubleDataset range, NumberOfSymmetryFolds folds) {
		
		double step = 180/folds.getFoldsOfSymmetry();
		
		Dataset output = DatasetFactory.zeros(DoubleDataset.class, range.getShape());
		
		double w = 20;
		double a = 1000;
 		double value = position - 180;
 		
		while (value <= 180) {

			Gaussian g = new Gaussian(value,w,a);
			output.iadd(g.calculateValues(range));
			value += step;
		}
		
		return output;
		
	}
	

	
}
