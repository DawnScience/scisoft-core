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

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This bean contains all the information about a GUI which needs to be
 * propagated around between different GUIs and the client. It is important to
 * note that although this can contain any information (i.e. any serializable
 * object) it should be lightweight as it will be passed around regularly. If
 * any heavier data needs to be passed about use the DataBean methodology. It
 * would make sense to extend this for specific GUIs to include easier methods
 * for filling and emptying the data
 */
public class GuiBean extends HashMap<GuiParameters, Serializable> implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(GuiBean.class);

	private boolean warn = true;

	/**
	 * @return a shallow copy of gui bean
	 */
	public GuiBean copy() {
		GuiBean bean = new GuiBean();
		bean.putAll(this);
		return bean;
	}

	/**
	 * Merge entries from another bean
	 * @param bean 
	 */
	public void merge(GuiBean bean) {
		for (Map.Entry<GuiParameters, Serializable> pair: bean.entrySet()) {
			this.put(pair.getKey(), pair.getValue());
		}
	}

	@Override
	public Serializable get(Object key) {
		Serializable returnValue = super.get(key);
		
		if (warn && key instanceof GuiParameters) {
			GuiParameters guiParam = (GuiParameters) key;
			if (returnValue != null && !guiParam.getStorageClass().isInstance(returnValue)) {
				logger.error("Value in GuiBean for key " + guiParam.toString() + " is not of expected type. A ClassCastException is likely");
			}
		}
		
		return returnValue;
	}

	@Override
	public Serializable put(GuiParameters key, Serializable value) {
		if (key == null) {
			throw new NullPointerException("key must not be null");
		}
		if (warn && value != null && !key.getStorageClass().isInstance(value)) {
			logger.error("Value in GuiBean for key " + key.toString() + " is not of expected type. A ClassCastException is likely");
		}
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends GuiParameters, ? extends Serializable> m) {
		for (Map.Entry<? extends GuiParameters, ? extends Serializable> pair: m.entrySet()) {
			this.put(pair.getKey(), pair.getValue());
		}
	}

	public boolean isWarn() {
		return warn;
	}

	public void setWarn(boolean warn) {
		this.warn = warn;
	}
}
