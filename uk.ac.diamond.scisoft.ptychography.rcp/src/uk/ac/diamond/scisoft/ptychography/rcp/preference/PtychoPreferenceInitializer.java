package uk.ac.diamond.scisoft.ptychography.rcp.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;

public class PtychoPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		store.setDefault(PtychoPreferenceConstants.PIE_RESOURCE_PATH, PtychoConstants.PIE_FOLDER);
		store.setDefault(PtychoPreferenceConstants.FILE_SAVE_PATH, PtychoConstants.TMP_FILE);
	}
}
