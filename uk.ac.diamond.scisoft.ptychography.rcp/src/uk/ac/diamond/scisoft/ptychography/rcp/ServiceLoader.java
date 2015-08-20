package uk.ac.diamond.scisoft.ptychography.rcp;

import org.eclipse.dawnsci.analysis.api.EventTracker;

public class ServiceLoader {

	private static EventTracker serviceTracker;

	/**
	 * used by OSGI
	 */
	public ServiceLoader() {
		
	}

	public static void setEventTracker(EventTracker et) {
		serviceTracker = et;
	}

	public static EventTracker getEventTracker() {
		return serviceTracker;
	}
}
