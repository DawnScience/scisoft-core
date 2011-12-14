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

package uk.ac.diamond.scisoft.analysis.io;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class MetaDataAdapter implements IMetaData {

	protected Collection<String> adapterDataNames;
	protected Collection<Object> adapterUserObjects;

	public MetaDataAdapter() {

	}

	public MetaDataAdapter(Collection<String> names) {
		this.adapterDataNames = names;
	}

	public MetaDataAdapter(Collection<String> names, final Collection<Object> userObjects) {
		this.adapterDataNames = names;
		this.adapterUserObjects = userObjects;
	}

	@Override
	public Collection<String> getDataNames() {
		return adapterDataNames;
	}

	@Override
	public Collection<Object> getUserObjects() {
		return adapterUserObjects;
	}

	@Override
	public Map<String, Integer> getDataSizes() {
		return null;
	}

	@Override
	public Map<String, int[]> getDataShapes() {
		return null;
	}

	@Override
	public Serializable getMetaValue(String key) throws Exception {
		return null;
	}

	@Override
	public Collection<String> getMetaNames() throws Exception {
		return null;
	}

	@Override
	public MetaDataAdapter clone() {
		return null;
	}

}
