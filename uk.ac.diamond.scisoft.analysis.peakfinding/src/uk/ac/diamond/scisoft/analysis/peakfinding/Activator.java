package uk.ac.diamond.scisoft.analysis.peakfinding;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static Object getService(final Class<?> serviceClass) {
		
		if (context==null && serviceClass==IPeakFindingService.class) return new PeakFindingServiceImpl();
		if (context == null) return null;
		ServiceReference<?> ref = context.getServiceReference(serviceClass);
		if (ref==null) return null;
		return context.getService(ref);
	}
}
