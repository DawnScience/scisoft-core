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
		if (adapterUserObjects == null) {
			if (adapterDataNames == null) {
				return new MetaDataAdapter();
			}
			return new MetaDataAdapter(adapterDataNames);
		}
		return new MetaDataAdapter(adapterDataNames, adapterUserObjects);
	}
}
