package uk.ac.diamond.scisoft.analysis.osgi;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.ILoaderFactoryExtensionService;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderFactoryExtensionService implements ILoaderFactoryExtensionService{

	private static final Logger logger = LoggerFactory.getLogger(LoaderFactoryExtensionService.class);
	
	
	@Override
	public void registerExtensionPoints() {
		try {
		    final IConfigurationElement[] ele = Platform.getExtensionRegistry().getConfigurationElementsFor("uk.ac.diamond.scisoft.analysis.io.loader");
	        for (IConfigurationElement i : ele) {
	        	registerLoader(i);
	        }
		     
		} catch (Exception ne) {
			logger.error("Cannot notify model listeners");
		}

	}

	/**
	 * Called to register a loader loaded from an extension point
	 * @param i
	 */
	private final static void registerLoader(IConfigurationElement i) {
		try {
			final AbstractFileLoader loader = (AbstractFileLoader)i.createExecutableExtension("class");
			final String[] exts = i.getAttribute("file_extension").split(",");
        	final String high = i.getAttribute("high_priority");
            final boolean isHigh = "true".equals(high);
			for (String ext : exts) {
				if (isHigh) {
					LoaderFactory.registerLoader(ext.trim(), loader.getClass(), 0);
				} else {
					LoaderFactory.registerLoader(ext.trim(), loader.getClass());
				}
			}
		} catch (Throwable ne) {
			logger.error("Cannot add loader "+i.getAttribute("class"), ne);
		}
	}

}
