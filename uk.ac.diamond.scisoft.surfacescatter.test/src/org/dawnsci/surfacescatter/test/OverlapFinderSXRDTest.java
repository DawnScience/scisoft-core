package org.dawnsci.surfacescatter.test;

import static org.junit.Assert.*;

import org.dawnsci.surfacescatter.OverlapFinderSXRD;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.junit.Test;

public class OverlapFinderSXRDTest {


	@Test
	public void testOverlapFinderSXRD() {
		
		IDataset first = (IDataset) DatasetFactory.createRange(0, 15, 0.025, Dataset.FLOAT64);
		IDataset second = (IDataset) DatasetFactory.createRange(12, 20, 0.02, Dataset.FLOAT64);
		IDataset third = (IDataset) DatasetFactory.createRange(19, 25, 0.005, Dataset.FLOAT64);
		
		IDataset[] testSet = {first, second, third};
		
		double[] firstCheck = {14.975,12.025};
		double[] secondCheck = {19.98,19.02};
		double[] thirdCheck = {0,0};
		
		
		
		double[][] testCheck = {firstCheck, secondCheck, thirdCheck};
		
		double[][] testOutput = OverlapFinderSXRD.overlapFinderOperation(testSet);
		
		assertArrayEquals("testing  overlap finder [0]", testCheck[0], testOutput[0], 0.0001);
		assertArrayEquals("testing  overlap finder [1]", testCheck[1], testOutput[1], 0.0001);
		
		
	}

}
