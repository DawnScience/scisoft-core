/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

public class ImageStackLoaderTest {

	static final int sizex = 10, sizey = 20, range = sizex * sizey;
	static final double abserr = 2.0; // maximum permitted absolute error (remember that JPEGs are lossy)

	@Test
	public void testInvalidArguments() throws Exception {
		try {
			new ImageStackLoader(null, (String[]) null);
			fail();
		} catch (IllegalArgumentException ex) {

		}

		String testScratchDirectoryName = IOTestUtils.setUpTest(ImageStackLoaderTest.class, "testInvalid", true);


		int[] multipliers= new int[]{2,3};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { imageFilenames.length };
		ImageStackLoader loader = new ImageStackLoader(dimensions, imageFilenames);
		int[] step = null;
		int[] shape = loader.getShape();
		int[] stop = null;
		int[] start = shape.clone();
		Dataset d = loader.getDataset(null, new SliceND(shape, start, stop, step));
		assertEquals(0, d.getSize());
	}

	private void makeFile(String filePath, int multiplier) throws ScanFileHolderException {
		DataHolder dha = new DataHolder();
		Dataset data = DatasetUtils.eye(ShortDataset.class, sizex, sizey, 0);
		data.setShape(sizex, sizey);
		data.imultiply(multiplier);
		dha.addDataset("testing data", data);
		new TIFFImageSaver(filePath,16,false).saveFile(dha);
	}

	private void makeSingleValueFile(String filePath, int multiplier) throws ScanFileHolderException {
		DataHolder dha = new DataHolder();
		Dataset data = DatasetFactory.createFromObject(Math.PI);
		data.imultiply(multiplier);
		dha.addDataset("testing data", data);
		new ColumnTextSaver(filePath).saveFile(dha);
	}

	@Test
	public void testSingleFile() throws Exception {
		String testScratchDirectoryName = IOTestUtils.setUpTest(ImageStackLoaderTest.class, "testSingleFile", true);

		int[] multipliers= new int[]{7};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { };
		StringDataset strings = DatasetFactory.createFromObject(StringDataset.class, imageFilenames, dimensions);
		strings.squeeze(true);
		ImageStackLoader loader = new ImageStackLoader(strings, null);
		assertEquals(IntegerDataset.class, loader.getInterface());
		int[] shape = loader.getShape();
		assertArrayEquals(new int[] { sizex, sizey }, shape);

		//extract each image
		for( int i=0; i< multipliers.length;i++)
		{
			int[] step = null;
			int[] stop = new int[] { sizex, sizey };
			int[] start = new int[] { 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { sizex, sizey }, dataset.getShape());
			int int2 = dataset.getInt(sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[i], int2);
		}

		//extract slice of bottom row from images
		for( int i=0; i< multipliers.length;i++)
		{
			int[] step = null;
			int[] stop = new int[] { sizex, sizey };
			int[] start = new int[] { sizex-1, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { 1, sizey }, dataset.getShape());
			int int2 = dataset.getInt(0, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[i], int2);
		}
	
	}
	
	@Test
	public void test1DFiles() throws Exception {
		String testScratchDirectoryName = IOTestUtils.setUpTest(ImageStackLoaderTest.class, "test1DFiles", true);


		int[] multipliers= new int[]{2,3};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { imageFilenames.length };
		ImageStackLoader loader = new ImageStackLoader(dimensions, imageFilenames);
		assertEquals(IntegerDataset.class, loader.getInterface());
		int[] shape = loader.getShape();
		assertArrayEquals(new int[] { 2, sizex, sizey }, shape);

		//extract each image
		for( int i=0; i< multipliers.length;i++)
		{
			int[] step = null;
			int[] stop = new int[] { i+1, sizex, sizey };
			int[] start = new int[] { i, 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { 1, sizex, sizey }, dataset.getShape());
			int int2 = dataset.getInt(0, sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[i], int2);
		}

		//extract slice of bottom row from images
		for( int i=0; i< multipliers.length;i++)
		{
			int[] step = null;
			int[] stop = new int[] { i+1, sizex, sizey };
			int[] start = new int[] { i, sizex-1, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { 1, 1, sizey }, dataset.getShape());
			int int2 = dataset.getInt(0, 0, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[i], int2);
		}
	}

	@Test
	public void test1DFilesSliceAcrossFiles() throws Exception {
		String testScratchDirectoryName = IOTestUtils.setUpTest(ImageStackLoaderTest.class, "test1DFilesSliceAcrossFiles", true);


		int[] multipliers= new int[]{2,3,4,5,6};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { imageFilenames.length };
		ImageStackLoader loader = new ImageStackLoader(dimensions, imageFilenames);
		assertEquals(IntegerDataset.class, loader.getInterface());
		int[] shape = loader.getShape();
		assertArrayEquals(new int[] { multipliers.length, sizex, sizey }, shape);


		//extract all data
		{
			int[] step = null;
			int[] stop = new int[] { multipliers.length, sizex, sizey };
			int[] start = new int[] { 0, 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(stop, dataset.getShape());
			int int2 = dataset.getInt(0, sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt(multipliers.length-1, sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}

		//extract all data - step 2 in y
		{
			int[] step = new int[] { 1, 1, 2 };
			int[] stop = new int[] { multipliers.length, sizex, sizey };
			int[] start = new int[] { 0, 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { multipliers.length, sizex, sizey/2 }, dataset.getShape());
			int int2 = dataset.getInt(0, sizex-2, (sizex-1)/2); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt( multipliers.length-1, sizex-2, (sizex-1)/2); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}

		//extract all data - step 2 in x
		{
			int[] step = new int[] { 1, 2, 1 };
			int[] stop = new int[] { multipliers.length, sizex, sizey };
			int[] start = new int[] { 0, 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { multipliers.length, sizex/2, sizey }, dataset.getShape());
			int int2 = dataset.getInt(0, sizex/2-1, sizex-2); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt(multipliers.length-1,sizex/2-1, sizex-2); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}
		
		//extract sizex, sizey point from each
		{
			int[] step = null;
			int[] stop = new int[] { multipliers.length, sizex, sizex };
			int[] start = new int[] { 0, sizex-1, sizex-1 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { multipliers.length, 1, 1 }, dataset.getShape());
			for( int i=0; i< multipliers.length;i++){
				int int2 = dataset.getInt(i, 0, 0); //eye sets data along diagonal
				assertEquals(multipliers[i], int2);
			}
		}
	
	}
	
	String [] makeFiles(String testScratchDirectoryName, int[] multipliers) throws ScanFileHolderException{
		String [] filePaths = new String[multipliers.length];
		for( int i =0 ; i< multipliers.length;i++){
			filePaths[i] = testScratchDirectoryName + File.separatorChar + "test" + i + ".tif";
			makeFile(filePaths[i], multipliers[i]);
		}
		return filePaths;
	}

	@Test
	public void test2DFiles() throws Exception {
		String testScratchDirectoryName = IOTestUtils.setUpTest(ImageStackLoaderTest.class, "test2DFiles", true);

		final int firstDim=2, secondDim=3;
		int[] multipliers= new int[]{1,2,3,4,5,6};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { firstDim,secondDim };
		ImageStackLoader loader = new ImageStackLoader(dimensions, imageFilenames);
		assertEquals(IntegerDataset.class, loader.getInterface());
		int[] shape = loader.getShape();
		assertArrayEquals(new int[] { firstDim, secondDim, sizex, sizey }, shape);

		//extract each images
		for( int i=0; i< firstDim; i++)
		{
			for( int j=0; j< secondDim; j++){
				int[] step = null;
				int[] stop = new int[] { i+1, j+1, sizex, sizey };
				int[] start = new int[] { i, j, 0, 0 };
				Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
				assertArrayEquals(new int[] { 1, 1, sizex, sizey }, dataset.getShape());
				int int2 = dataset.getInt(0, 0,sizex-1, sizex-1); //eye sets data along diagonal
				assertEquals("Check value for image i:" + i + " j:" +j,multipliers[i*3+j], int2);
			}
		}
		
		
		//extract slice of bottom row from images
		for( int i=0; i< firstDim; i++)
		{
			for( int j=0; j< secondDim; j++){
				int[] step = null;
				int[] stop = new int[] { i+1, j+1, sizex, sizey };
				int[] start = new int[] { i, j, sizex-1, 0 };
				Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
				assertArrayEquals(new int[] { 1, 1, 1, sizey }, dataset.getShape());
				int int2 = dataset.getInt(0, 0, 0, sizex-1); //eye sets data along diagonal
				assertEquals("Check value for image i:" + i + " j:" +j,multipliers[i*3+j], int2);
			}
		}
		
		//extract all data
		{
			int[] step = null;
			int[] stop = new int[] { firstDim, secondDim, sizex, sizey };
			int[] start = new int[] { 0, 0, 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(stop, dataset.getShape());
			int int2 = dataset.getInt( 0, 0,sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt( firstDim-1, secondDim-1,sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}

		//extract all data - step 2 in y
		{
			int[] step = new int[] { 1, 1, 1, 2 };
			int[] stop = new int[] { firstDim, secondDim, sizex, sizey };
			int[] start = new int[] { 0, 0, 0, 0 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] { firstDim, secondDim, sizex, sizey/2 }, dataset.getShape());
			int int2 = dataset.getInt( 0, 0,sizex-2, (sizex-1)/2); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt( firstDim-1, secondDim-1,sizex-2, (sizex-1)/2); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}
		

		
		//extract sizex, sizey point from each
		{
			int[] step = null;
			int[] stop = new int[] {firstDim, secondDim, sizex, sizex };
			int[] start = new int[] { 0,0, sizex-1, sizex-1 };
			Dataset dataset = loader.getDataset(null, new SliceND(shape, start, stop, step));
			assertArrayEquals(new int[] {firstDim, secondDim, 1, 1 }, dataset.getShape());
			for( int i=0; i< firstDim; i++)
			{
				for( int j=0; j< secondDim; j++){
					int int2 = dataset.getInt( i,j,0, 0); //eye sets data along diagonal
					assertEquals(multipliers[i*secondDim +j], int2);
				}
			}
		}
	}

	@Test
	public void testSingleValueFiles() throws Exception {
		String testScratchDirectoryName = IOTestUtils.setUpTest(ImageStackLoaderTest.class, "testSVFiles", true);
		int[] multipliers= new int[]{1,2,3,4,5,6};
		String[] imageFilenames = makeSingleValueFiles(testScratchDirectoryName, multipliers);

		int[] dimensions = new int[] { imageFilenames.length };
		ImageStackLoader loader = new ImageStackLoader(dimensions, imageFilenames);
		assertEquals(DoubleDataset.class, loader.getInterface());
		int[] shape = loader.getShape();
		assertEquals(2, shape.length);
		assertEquals(6, shape[0]);

		// extract each file
		SliceND s = new SliceND(shape, new Slice(1));
		for( int i=0; i< multipliers.length;i++)
		{
			s.setSlice(0, i, i+1, 1);
			Dataset dataset = loader.getDataset(null, s);
			assertArrayEquals(new int[] {1, 1}, dataset.getShape());
			assertEquals(multipliers[i] * Math.PI, dataset.getDouble(), 1e-6);
		}
	}

	String [] makeSingleValueFiles(String testScratchDirectoryName, int[] multipliers) throws ScanFileHolderException{
		String [] filePaths = new String[multipliers.length];
		for( int i =0 ; i< multipliers.length;i++){
			filePaths[i] = testScratchDirectoryName + File.separatorChar + "test" + i + ".dat";
			makeSingleValueFile(filePaths[i], multipliers[i]);
		}
		return filePaths;
	}

}
