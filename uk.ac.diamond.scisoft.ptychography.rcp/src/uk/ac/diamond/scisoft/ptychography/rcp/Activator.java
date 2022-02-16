package uk.ac.diamond.scisoft.ptychography.rcp;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "uk.ac.diamond.scisoft.ptychography.rcp"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static IPreferenceStore ptychoPreferences;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Get image from given path and add dispose listener so caller does not need to dispose
	 * @param w widget
	 * @param path plugin relative path of image file
	 * @return image
	 */
	public static Image getImageAndAddDisposeListener(Widget w, String path) {
		Image i = getImageDescriptor(path).createImage();
		w.addDisposeListener(e -> i.dispose());
		return i;
	}

	public static IPreferenceStore getPtychoPreferenceStore() {
		if (ptychoPreferences != null)
			return ptychoPreferences;
		ptychoPreferences = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"uk.ac.diamond.scisoft.ptychography.rcp");
		return ptychoPreferences;
	}
}
