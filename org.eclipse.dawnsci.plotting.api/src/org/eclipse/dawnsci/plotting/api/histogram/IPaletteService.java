/*-
 *******************************************************************************
 * Copyright (c) 2011-2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *    Baha El Kassaby - inversion and color map type
 *******************************************************************************/

package org.eclipse.dawnsci.plotting.api.histogram;

import java.util.Collection;
import java.util.List;

import org.eclipse.dawnsci.plotting.api.histogram.functions.FunctionContainer;
import org.eclipse.swt.graphics.PaletteData;

/**
 * A service for managing color schemes.
 * 
 * The color schemes are contributed by an extension point contributed by the
 * org.dawnsci.plotting.histogram plugin. This plugin also contribute  this service.
 * The service provides the names of at the color schemes and a way to get the scheme 
 * as a PaletteData object.
 */
public interface IPaletteService {

	/**
	 * Names of schemes
	 * @return
	 */
	public Collection<String> getColorSchemes();
	
	/**
	 * 8-bit Palette data from scheme.
	 * 
	 * @param colorSchemeName
	 * @return
	 */
	public PaletteData getDirectPaletteData(final String colorSchemeName);

	/**
	 * Functions from scheme for 16-bit palette data or more.
	 * @param scheme
	 * @return
	 */
	public FunctionContainer getFunctionContainer(String scheme);

	/**
	 * Inverts the Palette data
	 * 
	 * @param inverted
	 */
	public void setInverted(boolean inverted);

	/**
	 * Returns a list of color of a particular category
	 * 
	 * @param type
	 * @return List of colors
	 */
	public List<String> getColorsByCategory(String category);

	/**
	 * Returns the color category
	 * 
	 * @param color
	 * @return category
	 */
	public String getColorCategory(String color);

	/**
	 * Names of categories
	 * @return
	 */
	public Collection<String> getColorCategories();

	/**
	 * Return the application wide current default color scheme name
	 * @return scheme
	 */
	public String getDefaultColorScheme();

	/**
	 * Set the application wide current default color scheme
	 * by name
	 *
	 * @throws IllegalArgumentException if name is not in the collection returned by {@link #getColorSchemes()}
	 *
	 * @param name
	 */
	public void setDefaultColorScheme(String name);

}
