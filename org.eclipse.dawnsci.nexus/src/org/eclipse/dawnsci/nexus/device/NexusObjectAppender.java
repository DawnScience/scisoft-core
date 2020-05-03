package org.eclipse.dawnsci.nexus.device;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.INexusDeviceDecorator;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.AbstractNexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectWrapper;

/**
 * A type of {@link INexusDeviceDecorator} that appends (by adding fields and/or attributes),
 * to the nexus object returned by calling {@link INexusDevice#getNexusProvider(NexusScanInfo)}
 * on the decorated {@link INexusDevice}.
 * 
 * @author Matthew Dickie
 *
 * @param <N> subclass of {@link NXobject} to be appended
 */
public class NexusObjectAppender<N extends NXobject> extends AbstactNexusDecorator<N> {
	
	private final Optional<Consumer<N>> appender;
	
	public NexusObjectAppender() {
		appender = Optional.empty();
	}
	
	public NexusObjectAppender(String name, Consumer<N> decorator) {
		setName(name);
		this.appender = Optional.of(decorator); 
	}

	@Override
	public final NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException {
		final NexusObjectProvider<N> nexusObjectProvider = super.getNexusProvider(info);
		
		// NOTE: we can only copy a subclass of AbstractNexusObjectProvider. If we need to support other possible
		// implementations, we may need to wrap the NexusObjectProvider instead of copying it, at least in that case
		// This is because the decorator may need to continue to configure the nexus provider, and NexusObjectProvider
		// alone doesn't provide the setter methods to do that
		if (!(nexusObjectProvider instanceof AbstractNexusObjectProvider<?>)) {
			throw new IllegalArgumentException(); 
		}
		
		final N nexusObject = nexusObjectProvider.getNexusObject();
		appendNexusObject(nexusObject, (AbstractNexusObjectProvider<N>) nexusObjectProvider, info);

		return nexusObjectProvider;
	}
	
	/**
	 * Override this method to append to a nexus object if information from the {@link NexusScanInfo}
	 * is required. Otherwise it is preferred to override {@link #appendNexusObject(NXobject)}.
	 * @param nexusObject
	 * @param scanInfo
	 * @return
	 */
	protected void appendNexusObject(N nexusObject, AbstractNexusObjectProvider<N> wrapper, NexusScanInfo info) {
		appendNexusObject(nexusObject, wrapper);
	}
	
	protected void appendNexusObject(N nexusObject, AbstractNexusObjectProvider<N> wrapper) {
		appendNexusObject(nexusObject);
	}
	
	
	/**
	 * Appends a nexus object.  
	 * @param nexusObject
	 * @return
	 */
	protected void appendNexusObject(N nexusObject) {
		appender.ifPresent(dec -> dec.accept(nexusObject));
	}
	
}
