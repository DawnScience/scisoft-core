package org.eclipse.dawnsci.nexus.validation;

import java.text.MessageFormat;

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
	
	private final NodeType nodeType;
	
	private final String nodeName;
	
	private final String message;
	
	public ValidationReportEntry(Level level, NodeType nodeType, String nodeName, String message) {
		this.level = level;
		this.nodeType = nodeType;
		this.nodeName = nodeName;
		this.message = message;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getLogMessage() {
		return MessageFormat.format("{0} {1}: {2}", nodeType, nodeName == null ? "unnamed" : nodeName, message);
	}

	public String toString() {
		return level + ", " + getLogMessage();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + ((nodeType == null) ? 0 : nodeType.hashCode());
		return result;
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
		if (level != other.level)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (nodeType != other.nodeType)
			return false;
		return true;
	}
	
}
