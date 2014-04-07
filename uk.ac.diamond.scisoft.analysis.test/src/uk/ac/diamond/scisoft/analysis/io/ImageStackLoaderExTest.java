/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;

public class ImageStackLoaderExTest {

	static final int sizex = 10, sizey = 20, range = sizex * sizey;
	static final double abserr = 2.0; // maximum permitted absolute error (remember that JPEGs are lossy)

	@Before
	public void setUp() {
	}

	@SuppressWarnings("unused")
	@Test
	public void testInvalidArguments() throws Exception {
		try {
			new ImageStackLoaderEx(null, (String[]) null);
			fail();
		} catch (IllegalArgumentException ex) {

		}

		try {
			String testScratchDirectoryName = TestUtils.setUpTest(ImageStackLoaderExTest.class, "test1DFiles", true);


			int[] multipliers= new int[]{2,3};
			String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
			int[] dimensions = new int[] { imageFilenames.length };
			ImageStackLoaderEx loaderEx = new ImageStackLoaderEx(dimensions, imageFilenames);
			int[] step = null;
			int[] shape = loaderEx.getShape();
			int[] stop = null;
			int[] start = shape.clone();
			loaderEx.getDataset(null, shape, start, stop, step);
			fail();
		} catch (IllegalArgumentException ex) {

		}
	}

	private void makeFile(String filePath, Object multiplier) throws ScanFileHolderException {
		AbstractDataset data;
		DataHolder dha = new DataHolder();
		data = DatasetUtils.eye(sizex, sizey, 0, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		data.imultiply(multiplier);
		dha.addDataset("testing data", data);
		new TIFFImageSaver(filePath,16,false).saveFile(dha);
	}

	
	@Test
	public void test1DFiles() throws Exception {
		String testScratchDirectoryName = TestUtils.setUpTest(ImageStackLoaderExTest.class, "test1DFiles", true);


		int[] multipliers= new int[]{2,3};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { imageFilenames.length };
		ImageStackLoaderEx loaderEx = new ImageStackLoaderEx(dimensions, imageFilenames);
		assertEquals(AbstractDataset.INT32, loaderEx.getDtype());
		int[] shape = loaderEx.getShape();
		assertArrayEquals(new int[] { 2, sizex, sizey }, shape);

		//extract each image
		for( int i=0; i< multipliers.length;i++)
		{
			int[] step = null;
			int[] stop = new int[] { i+1, sizex, sizey };
			int[] start = new int[] { i, sizex-1, 0 };
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
			assertArrayEquals(new int[] { 1, 1, sizey }, dataset.getShape());
			int int2 = dataset.getInt(0, 0,sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[i], int2);
		}

		//extract slice of bottom row from images
		for( int i=0; i< multipliers.length;i++)
		{
			int[] step = null;
			int[] stop = new int[] { i+1, sizex, sizey };
			int[] start = new int[] { i, sizex-1, 0 };
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
			assertArrayEquals(new int[] { 1, 1, sizey }, dataset.getShape());
			int int2 = dataset.getInt(0, 0,sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[i], int2);
		}
	
	}

	@Test
	public void test1DFilesSliceAcrossFiles() throws Exception {
		String testScratchDirectoryName = TestUtils.setUpTest(ImageStackLoaderExTest.class, "test1DFilesSliceAcrossFiles", true);


		int[] multipliers= new int[]{2,3,4,5,6};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { imageFilenames.length };
		ImageStackLoaderEx loaderEx = new ImageStackLoaderEx(dimensions, imageFilenames);
		assertEquals(AbstractDataset.INT32, loaderEx.getDtype());
		int[] shape = loaderEx.getShape();
		assertArrayEquals(new int[] { multipliers.length, sizex, sizey }, shape);


		//extract all data
		{
			int[] step = null;
			int[] stop = new int[] { multipliers.length, sizex, sizey };
			int[] start = new int[] { 0, 0, 0 };
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
			assertArrayEquals(stop, dataset.getShape());
			int int2 = dataset.getInt( 0,sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt( multipliers.length-1,sizex-1, sizex-1); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}

		//extract all data - step 2 in y
		{
			int[] step = new int[] { 1, 1, 2 };
			int[] stop = new int[] { multipliers.length, sizex, sizey };
			int[] start = new int[] { 0, 0, 0 };
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
			assertArrayEquals(new int[] {multipliers.length, sizex, sizey/2 }, dataset.getShape());
			int int2 = dataset.getInt( 0,sizex-2, (sizex-1)/2); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt( multipliers.length-1,sizex-2, (sizex-1)/2); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}

		//extract all data - step 2 in x
		{
			int[] step = new int[] { 1, 2, 1 };
			int[] stop = new int[] { multipliers.length, sizex, sizey };
			int[] start = new int[] { 0, 0, 0 };
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
			assertArrayEquals(new int[] {multipliers.length, sizex/2, sizey }, dataset.getShape());
			int int2 = dataset.getInt( 0,sizex/2-1, sizex-2); //eye sets data along diagonal
			assertEquals(multipliers[0], int2);
			int int3 = dataset.getInt( multipliers.length-1,sizex/2-1, sizex-2); //eye sets data along diagonal
			assertEquals(multipliers[multipliers.length-1], int3);
		}
		
		//extract sizex, sizey point from each
		{
			int[] step = null;
			int[] stop = new int[] { multipliers.length, sizex, sizex };
			int[] start = new int[] { 0, sizex-1, sizex-1 };
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
			assertArrayEquals(new int[] {multipliers.length, 1, 1 }, dataset.getShape());
			for( int i=0; i< multipliers.length;i++){
				int int2 = dataset.getInt( i,0, 0); //eye sets data along diagonal
				assertEquals(multipliers[i], int2);
			}
		}
	
	}
	
	String [] makeFiles(String testScratchDirectoryName, int[] multipliers) throws ScanFileHolderException{
		String [] filePaths = new String[multipliers.length];
		for( int i =0 ; i< multipliers.length;i++){
			filePaths[i] = testScratchDirectoryName + File.separatorChar + "test" + i + ".tif";
			makeFile(filePaths[i], new Integer(multipliers[i]));
		}
		return filePaths;
	}

	@Test
	public void test2DFiles() throws Exception {
		String testScratchDirectoryName = TestUtils.setUpTest(ImageStackLoaderExTest.class, "test2DFiles", true);

		final int firstDim=2, secondDim=3;
		int[] multipliers= new int[]{1,2,3,4,5,6};
		String[] imageFilenames = makeFiles(testScratchDirectoryName, multipliers);
		int[] dimensions = new int[] { firstDim,secondDim };
		ImageStackLoaderEx loaderEx = new ImageStackLoaderEx(dimensions, imageFilenames);
		assertEquals(AbstractDataset.INT32, loaderEx.getDtype());
		int[] shape = loaderEx.getShape();
		assertArrayEquals(new int[] { firstDim, secondDim, sizex, sizey }, shape);

		//extract each images
		for( int i=0; i< firstDim; i++)
		{
			for( int j=0; j< secondDim; j++){
				int[] step = null;
				int[] stop = new int[] { i+1, j+1, sizex, sizey };
				int[] start = new int[] { i, j, 0, 0 };
				AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
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
				AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
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
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
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
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
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
			AbstractDataset dataset = loaderEx.getDataset(null, shape, start, stop, step);
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
	
}
