/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

//Now the testing class
public class ErrorPropagationUtilsTest {
	
	// Numbers to use throughout
	static final double START = 0.;
	static final double STOP = 5.;
	static final double STARTERR = 0.1;
	static final double STOPERR = 0.6;
	static final int LENGTH = 5;
	static final Dataset TESTINPUT = DatasetFactory.createLinearSpace(DoubleDataset.class, START, STOP, LENGTH);
	static final Dataset TESTIERROR = DatasetFactory.createLinearSpace(DoubleDataset.class, STARTERR, STOPERR, LENGTH);
	static final Dataset TESTOPERANDSCALAR = Maths.add(DatasetFactory.zeros(DoubleDataset.class, 1), 0.1);
	static final Dataset TESTOPERANDVECTOR = Maths.add(DatasetFactory.zeros(DoubleDataset.class, LENGTH), 0.1);
	static final Dataset TESTOERRORSCALAR = Maths.add(DatasetFactory.zeros(DoubleDataset.class, 1), 0.01);
	static final Dataset TESTOERRORVECTOR = Maths.add(DatasetFactory.zeros(DoubleDataset.class, LENGTH), 0.01);
	static final Dataset ADDITIONRESULT = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.1, 5.1, LENGTH);
	static final Dataset SUBTRACTIONRESULT = DatasetFactory.createLinearSpace(DoubleDataset.class, -0.1, 4.9, LENGTH);
	static final Dataset MULTIPLICATIONRESULT = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 0.5, LENGTH);
	static final Dataset DIVISIONRESULT = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 50, LENGTH);


	// Thirty-two different iterations of this test must be created
	
	// START ADDITIONS
	// The first will be an addition where the input has an error and 
	// operand does not and the operand is a single value
	@Test
	public void testAddWithUncertaintyA() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.1, 0.6, LENGTH);
		ADDITIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(testInputWithErrors, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
		TestUtils.assertDatasetEquals(ADDITIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an addition where the input has an error and 
	// operand does not and the operand is a vector the same length as the input
	@Test
	public void testAddWithUncertaintyB() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.1, 0.6, LENGTH);
		ADDITIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(testInputWithErrors, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
		TestUtils.assertDatasetEquals(ADDITIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an addition where the input has no error and 
	// operand does and the operand is a single value
	@Test
	public void testAddWithUncertaintyC() {
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);

		Dataset resultError = DatasetFactory.zeros(DoubleDataset.class, 1);
		resultError = Maths.add(resultError, 0.01);
		ADDITIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(TESTINPUT, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
		TestUtils.assertDatasetEquals(ADDITIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an addition where the input has no error and 
	// operand does and the operand is a vector the same length as the input
	@Test
	public void testAddWithUncertaintyD() {
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		Dataset resultError = DatasetFactory.zeros(DoubleDataset.class, 1);
		resultError = Maths.add(resultError, 0.01);
		ADDITIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(TESTINPUT, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
		TestUtils.assertDatasetEquals(ADDITIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an addition where the input and operand both have an error and 
	// and the operand is a scalar
	@Test
	public void testAddWithUncertaintyE() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.1004987562112089);
		resultError.add(0.22522211259110417);
		resultError.add(0.3501428280002319); 
		resultError.add(0.4751052514969709);
		resultError.add(0.6000833275470999);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		ADDITIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(testInputWithErrors, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
		TestUtils.assertDatasetEquals(ADDITIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an addition where the input and operand both have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testAddWithUncertaintyF() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.1004987562112089);
		resultError.add(0.22522211259110417);
		resultError.add(0.3501428280002319); 
		resultError.add(0.4751052514969709);
		resultError.add(0.6000833275470999);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		ADDITIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(testInputWithErrors, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
		TestUtils.assertDatasetEquals(ADDITIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an addition where neither the input nor operand have an error and 
	// and the operand is a scalar
	@Test
	public void testAddWithUncertaintyG() {	
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(TESTINPUT, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
	}
	
	// This will be an addition where neither the input nor operand have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testAddWithUncertaintyH() {				
		DoubleDataset testResult = ErrorPropagationUtils.addWithUncertainty(TESTINPUT, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(ADDITIONRESULT, testResult);
	}
	
	// START SUBTRACTIONS
	// The first will be an subtraction where the input has an error and 
	// operand does not and the operand is a single value
	@Test
	public void testSubtractWithUncertaintyA() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.1, 0.6, LENGTH);
		SUBTRACTIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(testInputWithErrors, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an subtraction where the input has an error and 
	// operand does not and the operand is a vector the same length as the input
	@Test
	public void testSubtractWithUncertaintyB() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.1, 0.6, LENGTH);
		SUBTRACTIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(testInputWithErrors, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an subtraction where the input has no error and 
	// operand does and the operand is a single value
	@Test
	public void testSubtractWithUncertaintyC() {
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		Dataset resultError = DatasetFactory.zeros(DoubleDataset.class, 1);
		resultError = Maths.add(resultError, 0.01);
		SUBTRACTIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(TESTINPUT, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an subtraction where the input has no error and 
	// operand does and the operand is a vector the same length as the input
	@Test
	public void testSubtractWithUncertaintyD() {
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		Dataset resultError = DatasetFactory.zeros(DoubleDataset.class, 1);
		resultError = Maths.add(resultError, 0.01);
		SUBTRACTIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(TESTINPUT, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an subtraction where the input and operand both have an error and 
	// and the operand is a scalar
	@Test
	public void testSubtractWithUncertaintyE() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.1004987562112089);
		resultError.add(0.22522211259110417);
		resultError.add(0.3501428280002319); 
		resultError.add(0.4751052514969709);
		resultError.add(0.6000833275470999);;
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		SUBTRACTIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(testInputWithErrors, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an subtraction where the input and operand both have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testSubtractWithUncertaintyF() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.1004987562112089);
		resultError.add(0.22522211259110417);
		resultError.add(0.3501428280002319); 
		resultError.add(0.4751052514969709);
		resultError.add(0.6000833275470999);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		SUBTRACTIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(testInputWithErrors, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an subtraction where neither the input nor operand have an error and 
	// and the operand is a scalar
	@Test
	public void testSubtractWithUncertaintyG() {
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(TESTINPUT, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
	}
	
	// This will be an subtraction where neither the input nor operand have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testSubtractWithUncertaintyH() {				
		DoubleDataset testResult = ErrorPropagationUtils.subtractWithUncertainty(TESTINPUT, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(SUBTRACTIONRESULT, testResult);
	}
	
	// START MUTLIPLICATIONS
	// The first will be an multiplication where the input has an error and 
	// operand does not and the operand is a single value
	@Test
	public void testMultiplyWithUncertaintyA() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.01, 0.06, LENGTH);
		MULTIPLICATIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(testInputWithErrors, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an multiplication where the input has an error and 
	// operand does not and the operand is a vector the same length as the input
	@Test
	public void testMultiplyWithUncertaintyB() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0.01, 0.06, LENGTH);
		MULTIPLICATIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(testInputWithErrors, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an multiplication where the input has no error and 
	// operand does and the operand is a single value
	@Test
	public void testMultiplyWithUncertaintyC() {
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 0.05, LENGTH);
		MULTIPLICATIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(TESTINPUT, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an multiplication where the input has no error and 
	// operand does and the operand is a vector the same length as the input
	@Test
	public void testMultiplyWithUncertaintyD() {
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 0.05, LENGTH);
		MULTIPLICATIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(TESTINPUT, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an multiplication where the input and operand both have an error and 
	// and the operand is a scalar
	@Test
	public void testMultiplyWithUncertaintyE() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.010000000000000002);
		resultError.add(0.025739075352467503);
		resultError.add(0.04301162633521313); 
		resultError.add(0.06051859218455102);
		resultError.add(0.07810249675906655);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		MULTIPLICATIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(testInputWithErrors, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an multiplication where the input and operand both have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testMultiplyWithUncertaintyF() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.010000000000000002);
		resultError.add(0.025739075352467503);
		resultError.add(0.04301162633521313); 
		resultError.add(0.06051859218455102);
		resultError.add(0.07810249675906655);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		MULTIPLICATIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(testInputWithErrors, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an multiplication where neither the input nor operand have an error and 
	// and the operand is a scalar
	@Test
	public void testMultipyWithUncertaintyG() {				
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(TESTINPUT, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
	}
	
	// This will be an multiplication where neither the input nor operand have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testMultiplyWithUncertaintyH() {				
		DoubleDataset testResult = ErrorPropagationUtils.multiplyWithUncertainty(TESTINPUT, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(MULTIPLICATIONRESULT, testResult);
	}
	
	// START DIVISIONS
	// The first will be an division where the input has an error and 
	// operand does not and the operand is a single value
	@Test
	public void testDivideWithUncertaintyA() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 1, 6, LENGTH);
		DIVISIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(testInputWithErrors, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
		TestUtils.assertDatasetEquals(DIVISIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an division where the input has an error and 
	// operand does not and the operand is a vector the same length as the input
	@Test
	public void testDivideWithUncertaintyB() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset resultError = DatasetFactory.createLinearSpace(DoubleDataset.class, 1, 6, LENGTH);
		DIVISIONRESULT.setErrors(resultError);
		
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(testInputWithErrors, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
		TestUtils.assertDatasetEquals(DIVISIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an division where the input has no error and 
	// operand does and the operand is a single value
	@Test
	public void testDivideWithUncertaintyC() {
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.0);
		resultError.add(1.2499999999999998);
		resultError.add(2.4999999999999996); 
		resultError.add(3.749999999999999);
		resultError.add(4.999999999999999);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		DIVISIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(TESTINPUT, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
		TestUtils.assertDatasetEquals(DIVISIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an division where the input has no error and 
	// operand does and the operand is a vector the same length as the input
	@Test
	public void testDivideWithUncertaintyD() {
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(0.0);
		resultError.add(1.2499999999999998);
		resultError.add(2.4999999999999996); 
		resultError.add(3.749999999999999);
		resultError.add(4.999999999999999);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		DIVISIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(TESTINPUT, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
		TestUtils.assertDatasetEquals(DIVISIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an division where the input and operand both have an error and 
	// and the operand is a scalar
	@Test
	public void testDivideWithUncertaintyE() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandScalarWithErrors = TESTOPERANDSCALAR.clone();
		testOperandScalarWithErrors.setErrors(TESTOERRORSCALAR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(1.0);
		resultError.add(2.57390753524675);
		resultError.add(4.3011626335213125); 
		resultError.add(6.0518592184551006);
		resultError.add(7.810249675906653);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		DIVISIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(testInputWithErrors, testOperandScalarWithErrors);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
		TestUtils.assertDatasetEquals(DIVISIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an division where the input and operand both have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testDivideWithUncertaintyF() {
		Dataset testInputWithErrors = TESTINPUT.clone();
		testInputWithErrors.setErrors(TESTIERROR);
		
		Dataset testOperandVectorWithErrors = TESTOPERANDVECTOR.clone();
		testOperandVectorWithErrors.setErrors(TESTOERRORVECTOR);
		
		List<Double> resultError = new ArrayList<Double>();
		resultError.add(1.0);
		resultError.add(2.57390753524675);
		resultError.add(4.3011626335213125); 
		resultError.add(6.0518592184551006);
		resultError.add(7.810249675906653);
		Dataset resultErrorD = DatasetFactory.createFromList(resultError);
		DIVISIONRESULT.setErrors(resultErrorD);
		
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(testInputWithErrors, testOperandVectorWithErrors);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
		TestUtils.assertDatasetEquals(DIVISIONRESULT.getErrors(), testResult.getErrors());
	}
	
	// This will be an division where neither the input nor operand have an error and 
	// and the operand is a scalar
	@Test
	public void testDivideWithUncertaintyG() {				
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(TESTINPUT, TESTOPERANDSCALAR);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
	}
	
	// This will be an division where neither the input nor operand have an error and 
	// and the operand is a vector the same length as the input
	@Test
	public void testDivideWithUncertaintyH() {				
		DoubleDataset testResult = ErrorPropagationUtils.divideWithUncertainty(TESTINPUT, TESTOPERANDVECTOR);
		TestUtils.assertDatasetEquals(DIVISIONRESULT, testResult);
	} 
	
	// This will be an division where neither the input nor operand have an error and 
	// and the operand is a vector the same length as the input
	@Test(expected = IllegalArgumentException.class)
	public void testWithUncertaintyDifferentLengthArrays() {	
		Dataset testOperandDifferentLength = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 0.5, LENGTH-1);
		ErrorPropagationUtils.addWithUncertainty(TESTINPUT, testOperandDifferentLength);
	} 
}



