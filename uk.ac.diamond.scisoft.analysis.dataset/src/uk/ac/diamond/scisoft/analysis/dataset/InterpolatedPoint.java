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

public class InterpolatedPoint {

	AbstractDataset realPoint;
	AbstractDataset coordPoint;

	public InterpolatedPoint(AbstractDataset realPoint, AbstractDataset coordPoint) {
		this.realPoint = realPoint;
		this.coordPoint = coordPoint;
	}

	public AbstractDataset getRealPoint() {
		return realPoint;
	}

	public AbstractDataset getCoordPoint() {
		return coordPoint;
	}
	
	@Override
	public String toString() {
		String realString = "[ " + realPoint.getDouble(0);
		for(int i = 1; i < realPoint.getShape()[0]; i++) {
			realString += " , " + realPoint.getDouble(i);
		}
		realString += " ]";
		
		String coordString = "[ " + coordPoint.getDouble(0);
		for(int i = 1; i < coordPoint.getShape()[0]; i++) {
			coordString += " , " + coordPoint.getDouble(i) ;
		}
		coordString += " ]";
		
		return realString + " : " + coordString;
	}

}
