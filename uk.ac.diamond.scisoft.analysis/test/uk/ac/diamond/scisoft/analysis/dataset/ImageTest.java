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

package uk.ac.diamond.scisoft.analysis.dataset;

import gda.analysis.io.ScanFileHolderException;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.PNGScaledSaver;


public class ImageTest {

	@Test
	public void testregrid() throws ScanFileHolderException {
		
		AbstractDataset ds = Random.rand(new int[] {100,100});
		AbstractDataset pow = DoubleDataset.arange(100);
		pow.ipower(2);
		
		AbstractDataset tile = pow.reshape(pow.getShape()[0],1);
		AbstractDataset x = DatasetUtils.tile(tile, 100);
		
		AbstractDataset y = DatasetUtils.transpose(x);
		
		AbstractDataset lin = DoubleDataset.arange(0,1000,5);
		
		// now apply the Transform
		
		AbstractDataset result = Image.regrid(ds, x, y, lin, lin);
		
		
		DataHolder dh = new DataHolder();
		dh.addDataset("image", ds);
		PNGScaledSaver saveorig= new PNGScaledSaver("/tmp/im/orig.png", 0, 255);
		saveorig.saveFile(dh);
		
		dh.clear();
		dh.addDataset("image", result);
		PNGScaledSaver saveremapped = new PNGScaledSaver("/tmp/im/remapped.png", 0, 255);
		saveremapped.saveFile(dh);
	}
	
}
