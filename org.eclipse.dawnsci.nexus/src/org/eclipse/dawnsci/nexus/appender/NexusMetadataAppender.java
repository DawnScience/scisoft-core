package org.eclipse.dawnsci.nexus.appender;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.context.NexusContextFactory;

/**
 * A {@link NexusObjectAppender} that appends metadata as scalar fields according to the
 * {@link Map} as set by {@link #setNexusMetadata(Map)}.
 * 
 * @author Matthew Dickie
 *
 * @param <N> type of nexus object to append
 */
public class NexusMetadataAppender<N extends NXobject> extends AbstractNexusContextAppender<N> {

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
	public void append(GroupNode groupNode, NexusContext context) throws NexusException {
		for (Map.Entry<String, Object> entry : nexusMetadata.entrySet()) {
			context.createDataNode(groupNode, entry.getKey(), entry.getValue());
		}
	}
	
}
