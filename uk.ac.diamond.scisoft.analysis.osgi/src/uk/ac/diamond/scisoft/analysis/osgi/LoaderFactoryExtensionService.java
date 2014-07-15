package uk.ac.diamond.scisoft.analysis.osgi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.ILoaderFactoryExtensionService;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class LoaderFactoryExtensionService implements ILoaderFactoryExtensionService {

	private static final Logger logger = LoggerFactory.getLogger(LoaderFactoryExtensionService.class);

	private static final List<String> plugins = new ArrayList<String>();

	private static final List<String> extensions = new ArrayList<String>();
	
	public LoaderFactoryExtensionService() {
		System.out.println("Starting loader factory extension service");
	}

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
			Class<? extends AbstractFileLoader> clazz = loader.getClass();
			for (String ext : exts) {
				String e = ext.trim();
				if (isHigh) {
					LoaderFactory.registerLoader(e, clazz, 0);
				} else {
					LoaderFactory.registerLoader(e, clazz);
				}
				String f = String.format("%s:%s:%d", e, clazz.getCanonicalName(), isHigh ? 0 : 1);
				if (!extensions.contains(f))
					extensions.add(f);
			}
			final String name = i.getContributor().getName();
			if (!plugins.contains(name))
				plugins.add(name);
		} catch (Throwable ne) {
			logger.error("Cannot add loader "+i.getAttribute("class"), ne);
		}
	}

	@Override
	public List<String> getPlugins() {
		return plugins;
	}

	@Override
	public List<String> getExtensions() {
		return extensions;
	}
}
