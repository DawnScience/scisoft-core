/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
	transient private static final Logger logger = LoggerFactory.getLogger(GuiBean.class);
	
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
		
		if (key instanceof GuiParameters) {
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
		if (value != null && !key.getStorageClass().isInstance(value)) {
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

}
