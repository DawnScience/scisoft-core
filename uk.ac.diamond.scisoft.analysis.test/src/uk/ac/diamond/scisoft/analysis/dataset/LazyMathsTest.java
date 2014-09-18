/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyMaths;
import org.junit.Test;

public class LazyMathsTest {

	@Test
	public void testSum() {
		Dataset a = DatasetFactory.createRange(100, Dataset.FLOAT64);

		AbstractDatasetTest.checkDatasets(a.sum(0), LazyMaths.sum(a, 0), 1e-9, 1e-15);

		a.setShape(10, 10);
		AbstractDatasetTest.checkDatasets(a.sum(0), LazyMaths.sum(a, 0), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.sum(1), LazyMaths.sum(a, 1), 1e-9, 1e-15);

		a.setShape(4, 5, 5);
		AbstractDatasetTest.checkDatasets(a.sum(0), LazyMaths.sum(a, 0), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.sum(1), LazyMaths.sum(a, 1), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.sum(2), LazyMaths.sum(a, 2), 1e-9, 1e-15);

		a.setShape(4, 5, 1, 5);
		AbstractDatasetTest.checkDatasets(a.sum(0), LazyMaths.sum(a, 0), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.sum(1), LazyMaths.sum(a, 1), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.sum(2), LazyMaths.sum(a, 2), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.sum(3), LazyMaths.sum(a, 3), 1e-9, 1e-15);
	}

	@Test
	public void testProduct() {
		Dataset a = DatasetFactory.createRange(100, Dataset.FLOAT64);
		a.iadd(1.);
		a.idivide(100.);

		AbstractDatasetTest.checkDatasets(a.product(0), LazyMaths.product(a, 0), 1e-9, 1e-15);

		a.setShape(10, 10);
		AbstractDatasetTest.checkDatasets(a.product(0), LazyMaths.product(a, 0), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.product(1), LazyMaths.product(a, 1), 1e-9, 1e-15);

		a.setShape(4, 5, 5);
		AbstractDatasetTest.checkDatasets(a.product(0), LazyMaths.product(a, 0), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.product(1), LazyMaths.product(a, 1), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.product(2), LazyMaths.product(a, 2), 1e-9, 1e-15);

		a.setShape(4, 5, 1, 5);
		AbstractDatasetTest.checkDatasets(a.product(0), LazyMaths.product(a, 0), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.product(1), LazyMaths.product(a, 1), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.product(2), LazyMaths.product(a, 2), 1e-9, 1e-15);
		AbstractDatasetTest.checkDatasets(a.product(3), LazyMaths.product(a, 3), 1e-9, 1e-15);
	}
}
