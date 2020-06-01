package org.eclipse.dawnsci.nexus.appender;

import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.INexusDeviceDecorator;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.AbstractNexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.device.AbstractNexusDecorator;

/**
 * A type of {@link INexusDeviceDecorator} that appends (by adding fields and/or attributes),
 * to the nexus object returned by calling {@link INexusDevice#getNexusProvider(NexusScanInfo)}
 * on the decorated {@link INexusDevice}.
 * <p>
 * There are two ways this class can be used. Either:<ul>
 *   <li>Create a subclass of this class and override {@link #appendNexusObject(NXobject)}, or another
 *   {@code appendNexusObject} method if it is necessary to make changes to the {@link AbstractNexusObjectProvider}
 *   or information from the {@link NexusScanInfo} is needed;</li>
 *   <li>Supply a {@link Consumer} for the appropriate kind of nexus object.</li>
 * </ul>
 * The first option is more suitable to creating an appender via spring configuration, the second is more
 * easier to use when doing so programmatically in Java code.
 * 
 * @author Matthew Dickie
 *
 * @param <N> subclass of {@link NXobject} to be appended
 */
public class NexusObjectAppender<N extends NXobject> extends AbstractNexusDecorator<N> {
	
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
	 * is required, or it is required to make changes to the {@link AbstractNexusObjectProvider},
	 * Otherwise it is preferred to override {@link #appendNexusObject(NXobject)}.
	 * @param nexusObject nexus object to append
	 * @param provider nexus object provider with more information about how to use the nexus object, update if necesssary
	 * @param scanInfo scan information with information about the scan which may be of use, such as the scan size,
	 *    and the devices in the scan 
	 * @throws NexusException
	 */
	protected void appendNexusObject(N nexusObject, AbstractNexusObjectProvider<N> provider, NexusScanInfo info) throws NexusException {
		appendNexusObject(nexusObject, provider);
	}
	
	/**
	 * Override this method to append to a nexus object if it is required to make changes to the {@link AbstractNexusObjectProvider}. 
	 * @param nexusObject nexus object to append
	 * @param provider nexus object provider with more information about how to use the nexus object, update if necessary
	 * @throws NexusException 
	 */
	protected void appendNexusObject(N nexusObject, AbstractNexusObjectProvider<N> wrapper) throws NexusException {
		appendNexusObject(nexusObject);
	}
	
	
	/**
	 * Appends a nexus object. Override this method if no changes need to be made to the
	 * {@link AbstractNexusObjectProvider} (e.g. names of data fields, etc).
	 * @param nexusObject nexus object to append
	 */
	protected void appendNexusObject(N nexusObject) throws NexusException {
		appender.ifPresent(dec -> dec.accept(nexusObject));
	}
	
}
