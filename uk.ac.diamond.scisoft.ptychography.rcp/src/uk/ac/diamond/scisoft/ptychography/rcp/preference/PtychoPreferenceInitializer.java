package uk.ac.diamond.scisoft.ptychography.rcp.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;

public class PtychoPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		store.setDefault(PtychoPreferenceConstants.RECON_SCRIPT_PATH, PtychoConstants.SCRIPT);
		store.setDefault(PtychoPreferenceConstants.ALTERNATE_SCRIPT_PATH, PtychoConstants.ALTERNATE_SCRIPT);
		store.setDefault(PtychoPreferenceConstants.TEMPLATE_FILE_PATH, PtychoConstants.TEMPLATE_FILE);
	}
}
