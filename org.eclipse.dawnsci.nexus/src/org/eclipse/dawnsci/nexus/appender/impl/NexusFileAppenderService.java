package org.eclipse.dawnsci.nexus.appender.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.appender.INexusFileAppender;
import org.eclipse.dawnsci.nexus.appender.INexusFileAppenderService;

public class NexusFileAppenderService implements INexusFileAppenderService {
	
	private Map<String, INexusFileAppender> appenders = new HashMap<>();

	public void register(INexusFileAppender appender) {
		appenders.put(appender.getName(), appender);
	}
	
	@Override
	public void appendMetadata(String deviceName, NexusFile nexusFile, String path) throws NexusException {
		final GroupNode groupNode = nexusFile.getGroup(path, false);
		if (groupNode == null) {
			throw new NexusException("Cannot add metadata for device " + deviceName +", no such group: " + path);
		}
		appendMetadata(deviceName, nexusFile, groupNode);
	}

	@Override
	public void appendMetadata(String deviceName, NexusFile nexusFile, GroupNode groupNode) throws NexusException {
		final INexusFileAppender appender = appenders.get(deviceName);
		if (appender != null) {
			appender.append(nexusFile, groupNode);
		}
	}

}
