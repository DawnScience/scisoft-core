package org.eclipse.dawnsci.nexus.device;

import java.util.List;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.INexusDeviceDecorator;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;

public abstract class AbstractNexusDecorator<N extends NXobject> implements INexusDeviceDecorator<N> {

	private String name;
	private INexusDevice<N> decorated;

	public AbstractNexusDecorator() {
		// no-args construtor for spring configuration
	}

	public AbstractNexusDecorator(String name) {
		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setDecorated(INexusDevice<N> decorated) {
		this.decorated = decorated;
	}

	@Override
	public INexusDevice<N> getDecorated() {
		return decorated;
	}

	@Override
	public NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException {
		return decorated.getNexusProvider(info);
	}

	@Override
	public List<NexusObjectProvider<N>> getNexusProviders(NexusScanInfo info) throws NexusException {
		return decorated.getNexusProviders(info);
	}

	@Override
	public CustomNexusEntryModification getCustomNexusModification() {
		return decorated.getCustomNexusModification();
	}
}
