package org.eclipse.dawnsci.nexus.device;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.nexus.NXobject;

/**
 * A {@link NexusObjectAppender} that appends metadata as scalar fields according to the
 * {@link Map} as set by {@link #setNexusMetadata(Map)}.
 * 
 * @author Matthew Dickie
 *
 * @param <N> type of nexus object to append
 */
public class NexusMetadataAppender<N extends NXobject> extends NexusObjectAppender<N> {

	private Map<String, Object> nexusMetadata = new HashMap<>();
	
	public NexusMetadataAppender() {
		// no-arg constructor for easier spring configuration
	}
	
	public NexusMetadataAppender(String name) {
		setName(name);
	}
	
	public Map<String, Object> getNexusMetadata() {
		return nexusMetadata;
	}
	
	public void setNexusMetadata(Map<String, Object> nexusMetadata) {
		this.nexusMetadata = nexusMetadata;
	}
	
	public void addNexusMetadata(String name, Object value) {
		nexusMetadata.put(name, value);
	}
	
	@Override
	protected void appendNexusObject(N nexusObject) {
		for (Map.Entry<String, Object> entry : nexusMetadata.entrySet()) {
			nexusObject.setField(entry.getKey(), entry.getValue());
		}
	}
	
}
