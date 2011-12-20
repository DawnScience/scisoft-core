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

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;

import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

/**
 * Note that the following GuiParameters in GuiBean are unsupported:
 * <ul>
 * <li> {@link GuiParameters#FILEOPERATION}: FileOperationBean is an unsupported type</li>
 * <li> {@link GuiParameters#GRIDPREFERENCES}: GridPreferences is an unsupported type</li>
 * <li> {@link GuiParameters#MASKING}: has no type</li>
 * <li> {@link GuiParameters#CALIBRATIONPEAKS}: has no type</li>
 * <li> {@link GuiParameters#CALIBRATIONFUNCTIONNCD}: has no type</li>
 * </ul>
 */
public class GuiBeanHelper extends MapFlatteningHelper<GuiBean> {

	static Set<GuiParameters> unsupported;
	{
		unsupported = new HashSet<GuiParameters>();
		unsupported.add(GuiParameters.FILEOPERATION);
		unsupported.add(GuiParameters.GRIDPREFERENCES);
		unsupported.add(GuiParameters.MASKING);
		unsupported.add(GuiParameters.CALIBRATIONFUNCTIONNCD);
		unsupported.add(GuiParameters.CALIBRATIONPEAKS);
	}

	public GuiBeanHelper() {
		super(GuiBean.class);
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		GuiBean inMap = (GuiBean) obj;
		Map<String, Object> returnMap = getFlattenedOutMap(GuiBean.class);
		returnMap.put(IFlattener.TYPE_KEY, GuiBean.class.getCanonicalName());
		Set<Entry<GuiParameters, Serializable>> entrySet = inMap.entrySet();
		for (Entry<GuiParameters, Serializable> entry : entrySet) {
			GuiParameters keyObj = entry.getKey();
			String key = keyObj.toString();
			if (!unsupported.contains(keyObj)) {
				Object value = rootFlattener.flatten(entry.getValue());
				returnMap.put(key, value);
			}
		}
		return returnMap;

	}

	@Override
	public GuiBean unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		GuiBean returnBean = new GuiBean();
		for (Object entryObj : thisMap.entrySet()) {
			Entry<?, ?> entry = (Entry<?, ?>) entryObj;
			String keyObj = (String) entry.getKey();
			Serializable valueObj = (Serializable) rootFlattener.unflatten(entry.getValue());
			if (!keyObj.equals(IFlattener.TYPE_KEY)) {
				GuiParameters thisParam = GuiParameters.valueOf(keyObj);
				if (thisParam.toString().equals(keyObj)) {
					returnBean.put(thisParam, unflattenParam(thisParam, valueObj));
				}
			}
		}
		return returnBean;
	}

	/**
	 * We know the intended type, so in many cases we can upgrade from flattened to the expected type e.g. Object[] to
	 * List<Object>.
	 * 
	 * @param params
	 * @param valueObj
	 * @return upgraded valueObj, or the original if it matches
	 */
	private Serializable unflattenParam(GuiParameters params, Serializable valueObj) {
		Class<?> clazz = params.getStorageClass();
		if (clazz.isInstance(valueObj)) {
			return valueObj;
		} else if (clazz.equals(List.class)) {
			return new ArrayList<Object>(Arrays.asList((Object[]) valueObj));
		} else if (clazz.equals(Integer[].class)) {
			return ArrayUtils.toObject((int[]) valueObj);
		} else if (clazz.equals(GuiPlotMode.class)) {
			return GuiPlotMode.valueOf((String) valueObj);
		} else if (clazz.equals(UUID.class)) {
			return UUID.fromString(valueObj.toString());
		}
		return valueObj;
	}

}
