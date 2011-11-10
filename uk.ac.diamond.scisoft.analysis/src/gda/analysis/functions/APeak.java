/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.functions;


public abstract class APeak extends AFunction implements IPeak {

	public APeak(int numParms) {
		super(numParms);
	}

	public APeak(double... params) {
		super(params);
	}

	public APeak(Parameter[] params) {
		super(params);
	}

	@Override
	public double getHeight() {
		return this.val(this.getPosition());
	}
	
	
}
