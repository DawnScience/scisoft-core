/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;

public class MaskingBean implements Serializable {

	public BooleanDataset mask = null;
	public Integer max = null;
	public Integer min = null;
	
	public MaskingBean(BooleanDataset maskDataset, Integer minthres, Integer maxthres) {
		mask = maskDataset;
		max = maxthres;
		min = minthres;
	}
	
	public BooleanDataset getMask() {
		return mask;
	}
}