/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.osgi.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
		
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "uk.ac.diamond.scisoft.analysis.osgi");
		store.setDefault(PreferenceConstants.DATASET_REGEXP, "(.+)_(\\d+)");
	}

}
