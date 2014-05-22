/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.osgi;

import uk.ac.diamond.scisoft.analysis.dataset.IFittingAlgorithmService;
import uk.ac.diamond.scisoft.analysis.fitting.IConicSectionFitter;
import uk.ac.diamond.scisoft.analysis.roi.fitting.CircleFitter;
import uk.ac.diamond.scisoft.analysis.roi.fitting.EllipseFitter;

public class FittingAlgorithmServiceImpl implements IFittingAlgorithmService {

	@Override
	public IConicSectionFitter createCircleFitter() {
		return new CircleFitter();
	}

	@Override
	public IConicSectionFitter createEllipseFitter() {
		return new EllipseFitter();
	}

}
