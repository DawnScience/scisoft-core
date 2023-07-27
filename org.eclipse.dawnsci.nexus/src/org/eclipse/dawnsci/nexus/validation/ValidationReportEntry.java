package org.eclipse.dawnsci.nexus.validation;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * An individual entry in the {@link ValidationReport} produced by validated a nexus tree
 * according to an application definition. 
 */
public class ValidationReportEntry {
	
	public enum Level {
		INFO, WARNING, ERROR
	}
	
	public enum NodeType {
		
		GROUP_NODE("Group node"), DATA_NODE("Data node"), ATTRIBUTE("Attribute");
		
		final String name;
		
		NodeType(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	private final Level level;

	private final String applicationDefinitionName;

	private final NodeType nodeType;
	
	private final String nodeName;
	
	private final String message;
	
	public ValidationReportEntry(Level level, String applicationDefinitionName, NodeType nodeType, String nodeName, String message) {
		this.level = level;
		this.applicationDefinitionName = applicationDefinitionName;
		this.nodeType = nodeType;
		this.nodeName = nodeName;
		this.message = message;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getLogMessage() {
		return MessageFormat.format("{0}: {1} ''{2}'': {3}", applicationDefinitionName, nodeType, nodeName == null ? "unnamed" : nodeName, message);
	}

	public String toString() {
		return level + ", " + getLogMessage();
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicationDefinitionName, level, message, nodeName, nodeType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationReportEntry other = (ValidationReportEntry) obj;
		return Objects.equals(applicationDefinitionName, other.applicationDefinitionName) && level == other.level
				&& Objects.equals(message, other.message) && Objects.equals(nodeName, other.nodeName)
				&& nodeType == other.nodeType;
	}
	
}
