/*
 * Copyright 2021 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package uk.ac.diamond.scisoft.analysis.processing.test.operations.utils;




// Imports from org.junit
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
// Imports from org.eclipse
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DetectorEfficiencyCalculationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DetectorEfficiencyCalculationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.EnterDiffractionCalibrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.EnterDiffractionCalibrationOperation;
// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.AttenuationCalculationUtils;


// @author Tim Snow


public class AttenuationCalculationUtilsTest {
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testEnergyHandlingLow() {
		AttenuationCalculationUtils.getTransmissionFactor(-1.00, "H2O", 1.00, 1.00);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testEnergyHandlingHigh() {
		AttenuationCalculationUtils.getTransmissionFactor(Double.NaN, "H2O", 1.00, 1.00);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFormulaHandling() {
		AttenuationCalculationUtils.getTransmissionFactor(1.00, "This is not a formula", 1.00, 1.00);
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testDensityHandlingLow() {
		AttenuationCalculationUtils.getTransmissionFactor(1.00, "H2O", -1.00, 1.00);
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void testDensityHandlingHigh() {
		AttenuationCalculationUtils.getTransmissionFactor(1.00, "H2O", Double.NaN, 1.00);
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testLengthHandlingLow() {
		AttenuationCalculationUtils.getTransmissionFactor(1.00, "H2O", 1.00, -1.00);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testLengthHandlingHigh() {
		AttenuationCalculationUtils.getTransmissionFactor(1.00, "H2O", 1.00, Double.NaN);
	}

	
	@Test
	public void testTransmissionFactorCalculation() {
		assertEquals(0.05814, AttenuationCalculationUtils.getTransmissionFactor(12.4, "H2O", 1.00, 1.00), 0.00001);

	}
}
