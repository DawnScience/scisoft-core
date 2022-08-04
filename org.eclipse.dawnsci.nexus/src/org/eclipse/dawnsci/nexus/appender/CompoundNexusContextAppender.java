package org.eclipse.dawnsci.nexus.appender;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.context.NexusContext;

/**
 * This class allows the registration of multiple appenders against a single detector.
 * 
 * @author Douglas Winter
 */
public class CompoundNexusContextAppender extends AbstractNexusContextAppender<NXobject> {
	
	private final List<INexusContextAppender> appenders;
	
	/**
	 * The appenders passed to this object should <em>not</em> be registered
	 * with {@link INexusDeviceService} against the detector name,
	 * since only one appender per detector is currently supported
	 * and it is <em>this</em> appender that should be retrieved.
	 */
	public CompoundNexusContextAppender(List<INexusContextAppender> appenders) {
		this.appenders = appenders;
	}

	@Override
	public void append(GroupNode groupNode, NexusContext context) throws NexusException {
		for (INexusContextAppender appender : appenders) {
			appender.append(groupNode, context);
		}
	}

}
