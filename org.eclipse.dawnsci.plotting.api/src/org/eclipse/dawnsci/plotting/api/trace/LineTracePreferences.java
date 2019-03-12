/*
 * Copyright (c) 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.dawnsci.plotting.api.trace;

import org.eclipse.dawnsci.plotting.api.preferences.PlottingConstants;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.ErrorBarType;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.PointStyle;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.TraceType;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

/**
 * Class to retrieve and store line trace preferences
 */
public class LineTracePreferences {

	public static final String ERROR_BAR_ON = PlottingConstants.GLOBAL_SHOW_ERROR_BARS;
	public static final String X_ERROR_BAR_TYPE = "TraceXErrorBarType";
	public static final String Y_ERROR_BAR_TYPE = "TraceYErrorBarType";
	public static final String AREA_ALPHA = "TraceAreaAlpha";
	public static final String POINT_SIZE = "TracePointSize";
	public static final String LINE_WIDTH = "TraceLineWidth";

	IPreferenceStore global;
	XMLMemento local;

	private String xmlifyClass(Class<?> clazz) {
		String name = clazz.getName();
		if (name.contains("$")) {
			return name.replace('$', '_');
		}
		return name;
	}
	/**
	 * @param store
	 * @param memento
	 */
	public LineTracePreferences(IPreferenceStore store, XMLMemento memento) {
		global = store;
		local = memento;
	}

	/**
	 * @param clazz
	 * @return ordinal of Enum stored under its class name
	 */
	public <T extends Enum<T>> int getOrdinal(Class<T> clazz) {
		return getOrdinal(clazz, xmlifyClass(clazz));
	}

	/**
	 * @param clazz
	 * @param key
	 * @return ordinal of Enum stored under given key
	 */
	public <T extends Enum<T>> int getOrdinal(Class<T> clazz, String key) {
		String value = getString(key);
		return value.isEmpty() ? 0 : Enum.valueOf(clazz, value).ordinal();
	}

	/**
	 * @param key
	 * @return string stored under given key
	 */
	public String getString(String key) {
		String value = local.getString(key);
		if (value == null) {
			value = global.getString(key);
		}
		return value;
	}

	/**
	 * @param key
	 * @return integer stored under given key
	 */
	public int getInteger(String key) {
		Integer value = local.getInteger(key);
		if (value == null) {
			value = global.getInt(key);
		}
		return value;
	}

	/**
	 * @param key
	 * @return boolean stored under given key
	 */
	public boolean getBoolean(String key) {
		Boolean value = local.getBoolean(key);
		if (value == null) {
			value = global.getBoolean(key);
		}
		return value;
	}

	/**
	 * Set preferences with enumeration value under its class name
	 * @param eValue
	 */
	public <T extends Enum<T>> void set(T eValue) {
		set(xmlifyClass(eValue.getClass()), eValue);
	}

	/**
	 * Set preferences with given value
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void set(String key, Object value) {
		if (value instanceof Enum) {
			Enum<?> eV = (Enum<?>) value;
			if (getOrdinal(eV.getClass(), key) == eV.ordinal()) {
				return;
			}
			value = eV.name();
		}
		if (value instanceof String) {
			putString(key, (String) value);
		} else if (value instanceof Integer) {
			putInteger(key, (int) value);
		} else if (value instanceof Boolean) {
			putBoolean(key, (boolean) value);
		} else {
			throw new IllegalArgumentException("Preference value class is not supported: " + value.getClass());
		}
	}

	private void putString(String key, String value) {
		String old = getString(key);
		if (!value.equals(old)) {
			local.putString(key, value);
		}
	}

	private void putInteger(String key, int value) {
		int old = getInteger(key);
		if (value != old) {
			local.putInteger(key, value);
		}
	}

	private void putBoolean(String key, boolean value) {
		boolean old = getBoolean(key);
		if (value != old) {
			local.putBoolean(key, value);
		}
	}

	/**
	 * Apply preferences to given line trace
	 * @param trace
	 */
	public void applyPreferences(ILineTrace trace) {
		trace.setTraceType(TraceType.values()[getOrdinal(TraceType.class)]);
		trace.setLineWidth(getInteger(LINE_WIDTH));
		trace.setPointStyle(PointStyle.values()[getOrdinal(PointStyle.class)]);
		trace.setPointSize(getInteger(POINT_SIZE));
		trace.setAreaAlpha(getInteger(AREA_ALPHA));
		trace.setErrorBarEnabled(getBoolean(ERROR_BAR_ON));
		trace.setXErrorBarType(ErrorBarType.values()[getOrdinal(ErrorBarType.class, X_ERROR_BAR_TYPE)]);
		trace.setYErrorBarType(ErrorBarType.values()[getOrdinal(ErrorBarType.class, Y_ERROR_BAR_TYPE)]);
	}

	/**
	 * Reset key to global value
	 * @param key
	 */
	public void resetString(String key) {
		String value = local.getString(key);
		if (value != null && !value.isEmpty()) {
			String old = global.getString(key);
			if (!value.equals(old)) {
				local.putString(key, old);
			}
		}
	}

	/**
	 * Reset key to global value
	 * @param key
	 */
	public void resetInteger(String key) {
		Integer value = local.getInteger(key);
		if (value != null) {
			int old = global.getInt(key);
			if (value != old) {
				local.putInteger(key, old);
			}
		}
	}

	/**
	 * Reset key to global value
	 * @param key
	 */
	public void resetBoolean(String key) {
		Boolean value = local.getBoolean(key);
		if (value != null) {
			boolean old = global.getBoolean(key);
			if (value != old) {
				local.putBoolean(key, old);
			}
		}
	}

	/**
	 * Reset preferences
	 */
	public void resetPreferences() {
		resetString(xmlifyClass(TraceType.class));
		resetInteger(LINE_WIDTH);
		resetString(xmlifyClass(PointStyle.class));
		resetInteger(POINT_SIZE);
		resetInteger(AREA_ALPHA);
		resetBoolean(ERROR_BAR_ON);
		resetString(X_ERROR_BAR_TYPE);
		resetString(Y_ERROR_BAR_TYPE);
	}

	/**
	 * Copy preferences to destination
	 * @param destination
	 */
	public void copyPreferences(IMemento destination) {
		copyString(destination, xmlifyClass(TraceType.class));
		copyInteger(destination, LINE_WIDTH);
		copyString(destination, xmlifyClass(PointStyle.class));
		copyInteger(destination, POINT_SIZE);
		copyInteger(destination, AREA_ALPHA);
		copyBoolean(destination, ERROR_BAR_ON);
		copyString(destination, X_ERROR_BAR_TYPE);
		copyString(destination, Y_ERROR_BAR_TYPE);
	}

	private void copyString(IMemento destination, String key) {
		String value = local.getString(key);
		if (value != null) {
			destination.putString(key, value);
		}
	}

	private void copyInteger(IMemento destination, String key) {
		Integer value = local.getInteger(key);
		if (value != null) {
			destination.putInteger(key, value);
		}
	}

	private void copyBoolean(IMemento destination, String key) {
		Boolean value = local.getBoolean(key);
		if (value != null) {
			destination.putBoolean(key, value);
		}
	}
}
