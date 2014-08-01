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

package uk.ac.diamond.scisoft.analysis.processing.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Extend this class for your model to avoid having to implement the get and set manually.
 * Do not put non-pojo methods in your models, keep them vanilla.
 * 
 * BE WARNED the get and set are not especially fast - do not call them from big loops!
 * 
 * This class MUST define a no argument constructor with getters and setters.
 * 
 */
public abstract class AbstractOperationModel implements IOperationModel {
	
	/**
	 * Tries to find the no-argument getter for this field, ignoring case
	 * so that camel case may be used in method names. This means that this
	 * method is not particularly fast, so avoid calling in big loops!
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Override
	public Object get(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		final String getter = getGetterName(name).toLowerCase();
		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals(getter)) {
				if (method.getParameterTypes().length<1) {
					method.setAccessible(true);
					return method.invoke(this);
				}
			}
		}
		
		final String isser  = getIsserName(name).toLowerCase();
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals(isser)) {
				if (method.getParameterTypes().length<1) {
					method.setAccessible(true);
					return method.invoke(this);
				}
			}
		}

		return null;
	}
	
	@Override
	public boolean isModelField(String name) throws NoSuchFieldException, SecurityException {
		
		final Field field = getClass().getDeclaredField(name);
		OperationModelField omf = (OperationModelField)field.getAnnotation(OperationModelField.class);

		
		final String getter = getGetterName(name).toLowerCase();
		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals(getter)) {
				if (method.getParameterTypes().length<1) {
					return true;
				}
			}
		}
		
		final String isser  = getIsserName(name).toLowerCase();
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals(isser)) {
				if (method.getParameterTypes().length<1) {
					return true;
				}
			}
		}

	    return false;
	}

	/**
	 * Set a field by name using reflection.
	 * @param name
	 * @return fields old value, or null
	 */
	@Override
	public Object set(String name, Object value)throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object oldValue = get(name);
		
		final String setter = getSetterName(name).toLowerCase();
		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals(setter)) {
				if (method.getParameterTypes().length==1) {
					method.setAccessible(true);
					method.invoke(this, value);
				}
			}
		}
		return oldValue;
	}

	
	private static String getSetterName(final String fieldName) {
		if (fieldName == null) return null;
		return getName("set", fieldName);
	}
	/**
	 * There must be a smarter way of doing this i.e. a JDK method I cannot find. However it is one line of Java so
	 * after spending some time looking have coded self.
	 * 
	 * @param fieldName
	 * @return String
	 */
	private static String getGetterName(final String fieldName) {
		if (fieldName == null) return null;
		return getName("get", fieldName);
	}
	
	private static String getIsserName(final String fieldName) {
		if (fieldName == null)
			return null;
		return getName("is", fieldName);
	}
	private static String getName(final String prefix, final String fieldName) {
		return prefix + getFieldWithUpperCaseFirstLetter(fieldName);
	}
	public static String getFieldWithUpperCaseFirstLetter(final String fieldName) {
		return fieldName.substring(0, 1).toUpperCase(Locale.US) + fieldName.substring(1);
	}

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

}
